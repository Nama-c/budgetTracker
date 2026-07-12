package services;

import models.Categorie;
import repositories.CategorieRepository;
import session.Session;

import java.util.List;

public class CategorieService {
    private final CategorieRepository categorieRepository;
    public CategorieService() {
        categorieRepository = new CategorieRepository();
    }
    public boolean ajouter(Categorie categorie) {
        categorie.setIdUtilisateur(Session.getInstance().getUtilisateurConnecte().getIdUtilisateur());
        categorie.setEstCommune(false);
        return categorieRepository.ajouter(categorie) > 0;
    }
    public boolean modifier(Categorie categorie) {
        return categorieRepository.modifier(categorie) > 0;
    }
    public boolean supprimer(int idCategorie) {
        return categorieRepository.supprimer(idCategorie) > 0;
    }
    public List<Categorie> listerUtilisateur() {
        int idUtilisateur =Session.getInstance().getUtilisateurConnecte().getIdUtilisateur();
        return categorieRepository.listerUtilisateur(idUtilisateur);
    }
    public List<Categorie> listerCommunes() {
        return categorieRepository.listerCommunes();
    }
}