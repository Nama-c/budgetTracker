package controllers;

import models.Transaction;
import services.TransactionService;

import java.util.List;

public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController() {

        transactionService = new TransactionService();

    }

    public boolean ajouter(Transaction transaction){

        return transactionService.ajouter(transaction);

    }

    public boolean modifier(Transaction transaction){

        return transactionService.modifier(transaction);

    }

    public boolean supprimer(int idTransaction){

        return transactionService.supprimer(idTransaction);

    }

    public List<Transaction> lister(){

        return transactionService.lister();

    }

}