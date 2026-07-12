package services;

import models.Budget;
import repositories.BudgetRepository;
import session.Session;

import java.math.BigDecimal;
import java.util.List;

public class BudgetService {
    private final BudgetRepository budgetRepository;

    public BudgetService() {
        budgetRepository = new BudgetRepository();
    }

    public boolean ajouter(Budget budget) {
        int idUtilisateur = Session.getInstance().getUtilisateurConnecte().getIdUtilisateur();
        budget.setIdUtilisateur(idUtilisateur);
        if (budget.getMontantMaximum().compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        Budget existe = budgetRepository.rechercherParCategorie(idUtilisateur, budget.getIdCategorie());
        if (existe != null) {
            return false;
        }
        budget.setMontantConsomme(BigDecimal.ZERO);
        budget.setMontantRestant(budget.getMontantMaximum());
        return budgetRepository.ajouter(budget) > 0;
    }

    public boolean modifier(Budget budget) {
        Budget ancien = budgetRepository.rechercherParId(budget.getIdBudget());
        if (ancien == null) {
            return false;
        }
        if (budget.getMontantMaximum().compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        ancien.setMontantMaximum(budget.getMontantMaximum());
        ancien.setMontantRestant(ancien.getMontantMaximum().subtract(ancien.getMontantConsomme())
        );
        return budgetRepository.modifier(ancien) > 0;
    }

    public boolean supprimer(int idBudget) {
        return budgetRepository.supprimer(idBudget) > 0;
    }

    public List<Budget> lister() {
        int idUtilisateur = Session.getInstance().getUtilisateurConnecte().getIdUtilisateur();
        return budgetRepository.lister(idUtilisateur);
    }

    public Budget rechercherParCategorie(int idCategorie) {
        int idUtilisateur = Session.getInstance().getUtilisateurConnecte().getIdUtilisateur();
        return budgetRepository.rechercherParCategorie(idUtilisateur, idCategorie);
    }
}