package services;

import config.DatabaseConnection;
import models.*;
import repositories.*;
import session.Session;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TransactionService {

    private final Connection connection;

    private final TransactionRepository transactionRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final BudgetRepository budgetRepository;
    private final CategorieRepository categorieRepository;

    public TransactionService() {
        connection = DatabaseConnection.getInstance().getConnection();
        transactionRepository = new TransactionRepository(connection);
        utilisateurRepository = new UtilisateurRepository(connection);
        budgetRepository = new BudgetRepository(connection);
        categorieRepository = new CategorieRepository(connection);
    }

    public boolean ajouter(Transaction transaction) {
        Utilisateur utilisateur = Session.getInstance().getUtilisateurConnecte();
        transaction.setIdUtilisateur(utilisateur.getIdUtilisateur());
        Categorie categorie = categorieRepository.rechercherParId(transaction.getIdCategorie());
        if (categorie == null) {
            return false;
        }
        transaction.setType(categorie.getType());
        if (transaction.getMontant().compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("\nLe montant doit être supérieur à 0.");
            return false;
        }
        if (categorie.getType() == TypeCategorie.DEPENSE
                && utilisateur.getSolde().compareTo(transaction.getMontant()) < 0) {
            System.out.println("\nSolde insuffisant.");
            return false;
        }
        try {
            connection.setAutoCommit(false);
            // ==========================
            // INSERT TRANSACTION
            // ==========================
            if (transactionRepository.ajouter(transaction) == 0) {
                connection.rollback();
                return false;
            }
            // ==========================
            // CALCUL DU NOUVEAU SOLDE
            // ==========================
            BigDecimal nouveauSolde;
            if (categorie.getType() == TypeCategorie.DEPENSE) {
                nouveauSolde = utilisateur.getSolde().subtract(transaction.getMontant());
            } else {
                nouveauSolde = utilisateur.getSolde().add(transaction.getMontant());
            }
            // ==========================
            // UPDATE UTILISATEUR
            // ==========================
            if (utilisateurRepository.modifierSolde(utilisateur.getIdUtilisateur(), nouveauSolde) == 0) {
                connection.rollback();
                return false;
            }
            // Mise à jour de la session
            utilisateur.setSolde(nouveauSolde);
            // ==========================
            // UPDATE BUDGET
            // ==========================
            if (categorie.getType() == TypeCategorie.DEPENSE) {
                Budget budget = budgetRepository.rechercherParCategorie(utilisateur.getIdUtilisateur(),
                        transaction.getIdCategorie());
                if (budget != null) {
                    BigDecimal nouveauConsomme = budget.getMontantConsomme().add(transaction.getMontant());
                    BigDecimal nouveauRestant = budget.getMontantMaximum().subtract(nouveauConsomme);
                    if (budgetRepository.modifierMontants(budget.getIdBudget(), nouveauConsomme, nouveauRestant) == 0) {
                        connection.rollback();
                        return false;
                    }
                    budget.setMontantConsomme(nouveauConsomme);
                    budget.setMontantRestant(nouveauRestant);
                    // ==========================
                    // ALERTE BUDGET
                    // ==========================
                    BigDecimal seuil = budget.getMontantMaximum().divide(new BigDecimal("5"));
                    if (nouveauRestant.compareTo(BigDecimal.ZERO) < 0) {
                        System.out.println("\nBUDGET DÉPASSÉ");
                        System.out.println("Catégorie : " + categorie.getNom());
                        System.out.println("Dépassement : " + nouveauRestant.abs() + " FCFA");
                    } else if (nouveauRestant.compareTo(seuil) <= 0) {
                        System.out.println("\n ATTENTION");
                        System.out.println("Le budget \"" + categorie.getNom() + "\" est presque épuisé.");
                        System.out.println("Montant restant : " + nouveauRestant + " FCFA");
                    }
                }
            }
            connection.commit();
            return true;
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Transaction> lister() {

        int idUtilisateur = Session.getInstance()
                .getUtilisateurConnecte()
                .getIdUtilisateur();

        return transactionRepository.listerParUtilisateur(idUtilisateur);

    }

    public boolean modifier(Transaction transaction) {

        return transactionRepository.modifier(transaction) > 0;

    }

    public boolean supprimer(int idTransaction) {

        Transaction transaction = transactionRepository.rechercherParId(idTransaction);

        if (transaction == null) {
            return false;
        }

        return transactionRepository.supprimer(idTransaction) > 0;

    }
}