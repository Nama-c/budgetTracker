package models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Transaction {
    private int idTransaction;
    private BigDecimal montant;
    private LocalDate dateTransaction;
    private String description;
    private int idUtilisateur;
    private int idCategorie;
    private LocalDateTime dateCreation;
    private TypeCategorie type;
    private String nomCategorie;

    public Transaction() {
    }

    public Transaction(int idTransaction, BigDecimal montant, LocalDate dateTransaction,
            String description, int idUtilisateur, int idCategorie, LocalDateTime dateCreation, TypeCategorie type,
            String nomCategorie) {
        this.idTransaction = idTransaction;
        this.montant = montant;
        this.dateTransaction = dateTransaction;
        this.description = description;
        this.idUtilisateur = idUtilisateur;
        this.idCategorie = idCategorie;
        this.dateCreation = dateCreation;
        this.type = type;
        this.nomCategorie = nomCategorie;
    }

    @Override
    public String toString() {
        return idTransaction + " | "+ dateTransaction+ " | "+ nomCategorie+ " | "+ montant + " FCFA";
    }

    public int getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(int idTransaction) {
        this.idTransaction = idTransaction;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public LocalDate getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(LocalDate dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public TypeCategorie getType() {
        return type;
    }

    public void setType(TypeCategorie type) {
        this.type = type;
    }

    public String getNomCategorie() {
        return nomCategorie;
    }

    public void setNomCategorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
    }
}