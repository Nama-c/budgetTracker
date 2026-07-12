package views;

import controllers.AuthController;
import models.Role;
import models.StatutUtilisateur;
import models.Utilisateur;

import java.math.BigDecimal;
import java.util.Scanner;

public class InscriptionView {

    private final Scanner scanner;
    private final AuthController authController;

    public InscriptionView() {

        scanner = new Scanner(System.in);
        authController = new AuthController();

    }

    public void afficher() {

        Utilisateur utilisateur = new Utilisateur();

        System.out.println("\n===== INSCRIPTION =====");

        System.out.print("Nom : ");
        utilisateur.setNom(scanner.nextLine());

        System.out.print("Prénom : ");
        utilisateur.setPrenom(scanner.nextLine());

        System.out.print("Email : ");
        utilisateur.setEmail(scanner.nextLine());

        System.out.print("Mot de passe : ");
        utilisateur.setMdp(scanner.nextLine());
        utilisateur.setRole(Role.USER);
        utilisateur.setStatut(StatutUtilisateur.ACTIF);
        utilisateur.setSolde(BigDecimal.ZERO);
        boolean inscription = authController.inscription(utilisateur);
        if (inscription) {
            System.out.println("\nCompte créé avec succès.");
        } else {
            System.out.println("\nCet email existe déjà.");
        }
    }
}