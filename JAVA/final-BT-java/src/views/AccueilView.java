package views;

import java.util.Scanner;

public class AccueilView {

    private final Scanner scanner;

    public AccueilView() {

        scanner = new Scanner(System.in);

    }

    public void afficher() {
        int choix;
        do {
            System.out.println("\n============================");
            System.out.println("      BUDGET TRACKER");
            System.out.println("============================");
            System.out.println("1. Connexion");
            System.out.println("2. Inscription");
            System.out.println("3. Quitter");
            System.out.print("Choix : ");
            choix = Integer.parseInt(scanner.nextLine());
            switch (choix) {
                case 1:
                    new ConnexionView().afficher();
                    break;
                case 2:
                    new InscriptionView().afficher();
                    break;
                case 3:
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        } while (choix != 3);
    }
}