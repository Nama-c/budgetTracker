package models;

public class Categorie {

    private int idCategorie;
    private String nom;
    private TypeCategorie type;
    private int idUtilisateur;
    private boolean estCommune;

    public Categorie(int idCategorie, String nom, TypeCategorie type, int idUtilisateur, boolean estCommune) {
        this.idCategorie = idCategorie;
        this.nom = nom;
        this.type = type;
        this.idUtilisateur = idUtilisateur;
        this.estCommune = estCommune;
    }

    public Categorie() {
    }

    public int getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public TypeCategorie getType() {
        return type;
    }

    public void setType(TypeCategorie type) {
        this.type = type;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public boolean isEstCommune() {
        return estCommune;
    }

    public void setEstCommune(boolean estCommune) {
        this.estCommune = estCommune;
    }

    @Override
    public String toString() {
        return "Categorie [idCategorie=" + idCategorie + ", nom=" + nom + ", type=" + type + ", idUtilisateur="
                + idUtilisateur + ", estCommune=" + estCommune + "]";
    }

}