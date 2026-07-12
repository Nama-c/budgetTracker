package session;

import models.Utilisateur;

public class Session {
    private static Session instance;
    private Utilisateur utilisateurConnecte;
    private Session() {
    }
    public static Session getInstance() {
        if(instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public Utilisateur getUtilisateurConnecte() {
        return utilisateurConnecte;
    }

    public void setUtilisateurConnecte(Utilisateur utilisateurConnecte) {
        this.utilisateurConnecte = utilisateurConnecte;
    }

    public void deconnecter() {
        utilisateurConnecte = null;
    }

    public boolean estConnecte() {
        return utilisateurConnecte != null;
    }

}