package views;

import java.util.Scanner;

import controllers.AuthController;
import controllers.DashboardController;
import models.Utilisateur;
import session.Session;

public class DashboardView {
    private final Scanner scanner;
    

    public DashboardView() {
        scanner = new Scanner(System.in);
    }

    public void afficher() {
        int choix;
        DashboardController dashboardController = new DashboardController();
        do {
            Utilisateur utilisateur = Session.getInstance()
                    .getUtilisateurConnecte();

            System.out.println("\n==========================================================");
            System.out.println("                 BUDGET TRACKER");
            System.out.println("==========================================================");
            System.out.println("👤 Utilisateur : "+ utilisateur.getPrenom()+ " "+ utilisateur.getNom());
            System.out.println("📧 Email       : "+ utilisateur.getEmail());
            System.out.println("💰 Solde       : "+ utilisateur.getSolde()+ " FCFA");
            System.out.println("==========================================================");
            System.out.println("               TABLEAU DE BORD");
            System.out.println("==========================================================");

            System.out.println("Revenus du mois : "+ dashboardController.revenus()+ " FCFA");
            System.out.println("Dépenses du mois : "+ dashboardController.depenses()+ " FCFA");
            System.out.println("Épargne          : "+ Session.getInstance().getUtilisateurConnecte().getSolde()+ " FCFA");

            System.out.println("==========================================================");
            System.out.println("1. Transactions");
            System.out.println("2. Budgets");
            System.out.println("3. Catégories");
            System.out.println("4. Statistiques");
            System.out.println("5. Profil");
            System.out.println("6. Déconnexion");
            System.out.print("Choix : ");
            choix = Integer.parseInt(scanner.nextLine());
            switch (choix) {
                case 1:
                    new TransactionView().afficher();
                    break;
                case 2:
                    new BudgetView().afficher();
                    break;
                case 3:
                    new CategorieView().afficher();
                    break;
                case 4:
                    new StatistiqueView().afficher();
                    break;
                case 5:
                    new ProfilView().afficher();
                    break;
                case 6:
                    AuthController authController = new AuthController();
                    authController.deconnexion();
                    System.out.println("\nDéconnexion réussie.");
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        } while (choix != 6);
    }
}