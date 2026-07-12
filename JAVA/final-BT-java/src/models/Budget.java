package models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Budget {
    private int idBudget;
    private int idUtilisateur;
    private int idCategorie;
    private BigDecimal montantMaximum;
    private BigDecimal montantConsomme;
    private BigDecimal montantRestant;
    private LocalDateTime derniereMaj;
    public Budget(int idBudget, int idUtilisateur, int idCategorie, BigDecimal montantMaximum,
            BigDecimal montantConsomme, BigDecimal montantRestant, LocalDateTime derniereMaj) {
        this.idBudget = idBudget;
        this.idUtilisateur = idUtilisateur;
        this.idCategorie = idCategorie;
        this.montantMaximum = montantMaximum;
        this.montantConsomme = montantConsomme;
        this.montantRestant = montantRestant;
        this.derniereMaj = derniereMaj;
    }
    public Budget() {
    }
    @Override
    public String toString() {
        return "Budget [idBudget=" + idBudget + ", idUtilisateur=" + idUtilisateur + ", idCategorie=" + idCategorie
                + ", montantMaximum=" + montantMaximum + ", montantConsomme=" + montantConsomme + ", montantRestant="
                + montantRestant + ", derniereMaj=" + derniereMaj + "]";
    }
    public int getIdBudget() {
        return idBudget;
    }
    public void setIdBudget(int idBudget) {
        this.idBudget = idBudget;
    }
    public int getIdUtilisateur() {
        return idUtilisateur;
    }
    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }
    public int getIdCategorie() {
        return idCategorie;
    }
    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }
    public BigDecimal getMontantMaximum() {
        return montantMaximum;
    }
    public void setMontantMaximum(BigDecimal montantMaximum) {
        this.montantMaximum = montantMaximum;
    }
    public BigDecimal getMontantConsomme() {
        return montantConsomme;
    }
    public void setMontantConsomme(BigDecimal montantConsomme) {
        this.montantConsomme = montantConsomme;
    }
    public BigDecimal getMontantRestant() {
        return montantRestant;
    }
    public void setMontantRestant(BigDecimal montantRestant) {
        this.montantRestant = montantRestant;
    }
    public LocalDateTime getDerniereMaj() {
        return derniereMaj;
    }
    public void setDerniereMaj(LocalDateTime derniereMaj) {
        this.derniereMaj = derniereMaj;
    }
}