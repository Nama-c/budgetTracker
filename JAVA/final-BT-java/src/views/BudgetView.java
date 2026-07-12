package views;

import controllers.BudgetController;
import controllers.CategorieController;
import models.Budget;
import models.Categorie;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class BudgetView {

    private final BudgetController controller;
    private final CategorieController categorieController;
    private final Scanner scanner;

    public BudgetView() {

        controller = new BudgetController();
        categorieController = new CategorieController();
        scanner = new Scanner(System.in);

    }

    public void afficher() {

        int choix;

        do {

            System.out.println("\n========== BUDGETS ==========");
            System.out.println("1 - Lister");
            System.out.println("2 - Ajouter");
            System.out.println("3 - Modifier");
            System.out.println("4 - Supprimer");
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

        List<Budget> budgets = controller.lister();

        System.out.println();

        budgets.forEach(System.out::println);

    }

    private void ajouter() {

        Budget budget = new Budget();

        System.out.println("\n===== Catégories =====");

        List<Categorie> categories =
                categorieController.listerUtilisateur();

        for (Categorie categorie : categories) {

            System.out.println(

                    categorie.getIdCategorie()

                            + " - "

                            + categorie.getNom()

            );

        }

        System.out.print("Catégorie : ");

        budget.setIdCategorie(

                Integer.parseInt(scanner.nextLine())

        );

        System.out.print("Montant maximum : ");

        budget.setMontantMaximum(

                new BigDecimal(scanner.nextLine())

        );

        if (controller.ajouter(budget)) {

            System.out.println("\n Budget ajouté.");

        } else {

            System.out.println("\nAjout impossible.");

        }

    }

    private void modifier() {

        Budget budget = new Budget();

        System.out.print("Id budget : ");

        budget.setIdBudget(

                Integer.parseInt(scanner.nextLine())

        );

        System.out.print("Nouveau montant : ");

        budget.setMontantMaximum(

                new BigDecimal(scanner.nextLine())

        );

        if (controller.modifier(budget)) {

            System.out.println("\nBudget modifié.");

        } else {

            System.out.println("\nModification impossible.");

        }

    }

    private void supprimer() {

        System.out.print("Id budget : ");

        int id = Integer.parseInt(scanner.nextLine());

        if (controller.supprimer(id)) {

            System.out.println("\nBudget supprimé.");

        } else {

            System.out.println("\n Suppression impossible.");

        }

    }

}