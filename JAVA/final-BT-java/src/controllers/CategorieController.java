package controllers;

import models.Categorie;
import services.CategorieService;

import java.util.List;

public class CategorieController {
    private final CategorieService categorieService;
    public CategorieController() {
        categorieService = new CategorieService();
    }
    public boolean ajouter(Categorie categorie) {
        return categorieService.ajouter(categorie);
    }
    public boolean modifier(Categorie categorie) {
        return categorieService.modifier(categorie);
    }
    public boolean supprimer(int idCategorie) {
        return categorieService.supprimer(idCategorie);
    }
    public List<Categorie> listerUtilisateur() {
        return categorieService.listerUtilisateur();
    }
    public List<Categorie> listerCommunes() {
        return categorieService.listerCommunes();
    }
}