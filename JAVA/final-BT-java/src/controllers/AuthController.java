package controllers;

import models.Utilisateur;
import services.AuthService;

public class AuthController {
    private final AuthService authService;
    public AuthController() {
        authService = new AuthService();
    }
    public boolean inscription(Utilisateur utilisateur) {
        return authService.inscription(utilisateur);
    }
    public boolean connexion(String email, String mdp) {
        return authService.connexion(email, mdp);
    }
    public void deconnexion() {
        authService.deconnexion();
    }

}