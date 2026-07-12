package services;

import models.StatutUtilisateur;
import models.Utilisateur;
import repositories.UtilisateurRepository;
import session.Session;
import org.mindrot.jbcrypt.BCrypt;

public class AuthService {

    private final UtilisateurRepository utilisateurRepository;

    public AuthService() {

        utilisateurRepository = new UtilisateurRepository();

    }

    public boolean inscription(Utilisateur utilisateur) {
        Utilisateur existe = utilisateurRepository.rechercherParEmail(utilisateur.getEmail());
        if (existe != null) {
            return false;
        }
        utilisateur.setMdp(BCrypt.hashpw(utilisateur.getMdp(), BCrypt.gensalt()));
        return utilisateurRepository.ajouter(utilisateur) > 0;
    }

    public boolean connexion(String email, String mdp) {

        Utilisateur utilisateur = utilisateurRepository.rechercherParEmail(email);

        if (utilisateur == null) {
            return false;
        }

        String hash = utilisateur.getMdp();

        if (hash.startsWith("$2y$")) {
            hash = "$2a$" + hash.substring(4);
        }

        if (!BCrypt.checkpw(mdp, hash)) {
            return false;
        }

        if (utilisateur.getStatut() == StatutUtilisateur.INACTIF) {
            return false;
        }

        Session.getInstance().setUtilisateurConnecte(utilisateur);

        return true;
    }

    public void deconnexion() {
        Session.getInstance().deconnecter();
    }
}