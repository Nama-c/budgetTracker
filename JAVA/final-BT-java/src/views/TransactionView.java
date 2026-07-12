package views;

import controllers.TransactionController;
import models.Transaction;
import controllers.CategorieController;
import models.Categorie;
import session.Session;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class TransactionView {

    private final TransactionController controller;
    private final CategorieController categorieController;
    private final Scanner scanner;

    public TransactionView() {

        controller = new TransactionController();
        categorieController = new CategorieController();
        scanner = new Scanner(System.in);

    }

    public void afficher() {

        int choix;

        do {

            System.out.println("\n========== TRANSACTIONS ==========");
            System.out.println("1 - Lister les transactions");
            System.out.println("2 - Ajouter une transaction");
            System.out.println("3 - Modifier une transaction");
            System.out.println("4 - Supprimer une transaction");
            System.out.println("0 - Retour");
            System.out.print("Choix : ");

            choix = Integer.parseInt(scanner.nextLine());

            switch (choix) {

                case 1 -> lister();

                case 2 -> ajouter();

                case 3 -> modifier();

                case 4 -> supprimer();

            }

        } while (choix != 0);

    }

    private void lister() {

        List<Transaction> transactions = controller.lister();

        System.out.println();

        for (Transaction transaction : transactions) {

            System.out.println(transaction);

        }

    }

    private void ajouter() {
        Transaction transaction = new Transaction();
        transaction.setIdUtilisateur(Session.getInstance().getUtilisateurConnecte().getIdUtilisateur());
        System.out.print("Description : ");
        transaction.setDescription(scanner.nextLine());
        System.out.print("Montant : ");
        transaction.setMontant(new BigDecimal(scanner.nextLine()));
        System.out.println("\n===== Catégories =====");
        List<Categorie> categories = categorieController.listerUtilisateur();
        for (Categorie categorie : categories) {
            System.out.println(categorie.getIdCategorie() + " - " + categorie.getNom());
        }
        System.out.print("Choisir une catégorie : ");
        transaction.setIdCategorie(Integer.parseInt(scanner.nextLine()));
        System.out.print("Date (AAAA-MM-JJ) : ");
        transaction.setDateTransaction(LocalDate.parse(scanner.nextLine()));
        if (controller.ajouter(transaction)) {
            System.out.println("\n Transaction ajoutée.");
        } else {
            System.out.println("\n Ajout impossible.");
        }
    }

    private void modifier() {

        Transaction transaction = new Transaction();
        System.out.println("\n===== Catégories =====");
        List<Categorie> categories = categorieController.listerUtilisateur();
        for (Categorie categorie : categories) {
            System.out.println( categorie.getIdCategorie()+ " - "+ categorie.getNom());
        }
        System.out.print("Id : ");
        transaction.setIdTransaction(Integer.parseInt(scanner.nextLine()));

        System.out.print("Description : ");
        transaction.setDescription(scanner.nextLine());

        System.out.print("Montant : ");
        transaction.setMontant(
                new BigDecimal(scanner.nextLine()));

        System.out.print("Id catégorie : ");
        transaction.setIdCategorie(
                Integer.parseInt(scanner.nextLine()));

        System.out.print("Date (AAAA-MM-JJ) : ");
        transaction.setDateTransaction(
                LocalDate.parse(scanner.nextLine()));

        if (controller.modifier(transaction)) {

            System.out.println("Transaction modifiée.");

        } else {

            System.out.println("Modification impossible.");

        }

    }

    private void supprimer() {

        System.out.print("Id de la transaction : ");

        int id = Integer.parseInt(scanner.nextLine());

        controller.supprimer(id);

    }

}