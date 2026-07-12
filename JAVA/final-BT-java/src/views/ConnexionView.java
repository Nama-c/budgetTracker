package views;

import controllers.AuthController;

import java.util.Scanner;

public class ConnexionView {

    private final Scanner scanner;
    private final AuthController authController;

    public ConnexionView() {
        scanner = new Scanner(System.in);
        authController = new AuthController();
    }

    public void afficher() {
        System.out.println("\n===== CONNEXION =====");
        System.out.print("Email : ");
        String email = scanner.nextLine();
        System.out.print("Mot de passe : ");
        String mdp = scanner.nextLine();
        boolean connexion = authController.connexion(email, mdp);
        if (connexion) {
            System.out.println("\nConnexion réussie.");
            DashboardView dashboardView = new DashboardView();
            dashboardView.afficher();
        } else {
            System.out.println("\nEmail ou mot de passe incorrect.");
        }
    }
}