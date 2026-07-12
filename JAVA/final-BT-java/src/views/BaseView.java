package views;

import java.math.BigDecimal;
import java.util.Scanner;

public abstract class BaseView {
    protected final Scanner scanner;
    public BaseView() {
        scanner = new Scanner(System.in);
    }
    protected String lireTexte(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }
    protected int lireEntier(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Veuillez saisir un entier valide.");
            }
        }
    }
    protected BigDecimal lireMontant(String message) {
        while (true) {
            try {
                System.out.print(message);
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Montant invalide.");
            }
        }
    }
    protected boolean confirmation(String message) {
        while (true) {
            System.out.print(message + " (O/N) : ");
            String reponse = scanner.nextLine();
            if (reponse.equalsIgnoreCase("O")) {
                return true;
            }
            if (reponse.equalsIgnoreCase("N")) {
                return false;
            }
            System.out.println("Réponse invalide.");
        }
    }
}