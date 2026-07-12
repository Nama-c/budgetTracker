package controllers;

import models.Budget;
import services.BudgetService;

import java.util.List;

public class BudgetController {
    private final BudgetService budgetService;
    public BudgetController(){
        budgetService = new BudgetService();
    }
    public boolean ajouter(Budget budget){
        return budgetService.ajouter(budget);
    }
    public boolean modifier(Budget budget){
        return budgetService.modifier(budget);
    }
    public boolean supprimer(int idBudget){
        return budgetService.supprimer(idBudget);
    }
    public List<Budget> lister(){
        return budgetService.lister();
    }
    public Budget rechercherParCategorie(int idCategorie){
        return budgetService.rechercherParCategorie(idCategorie);
    }
}