package views;

import controllers.CategorieController;
import models.Categorie;
import models.TypeCategorie;

import java.util.List;
import java.util.Scanner;

public class CategorieView {
    private final Scanner scanner;
    private final CategorieController categorieController;
    public CategorieView() {
        scanner = new Scanner(System.in);
        categorieController = new CategorieController();
    }
    public void afficher() {
        int choix;
        do {
            System.out.println("\n==============================");
            System.out.println("        CATEGORIES");
            System.out.println("==============================");
            System.out.println("1. Ajouter");
            System.out.println("2. Modifier");
            System.out.println("3. Supprimer");
            System.out.println("4. Mes catégories");
            System.out.println("5. Catégories communes");
            System.out.println("6. Retour");
            System.out.print("Choix : ");
            choix = Integer.parseInt(scanner.nextLine());
            switch (choix) {
                case 1:
                    ajouter();
                    break;
                case 2:
                    modifier();
                    break;
                case 3:
                    supprimer();
                    break;
                case 4:
                    afficherMesCategories();
                    break;
                case 5:
                    afficherCategoriesCommunes();
                    break;
                case 6:
                    System.out.println("Retour...");
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        } while (choix != 6);
    }

    private void ajouter() {
        Categorie categorie = new Categorie();
        System.out.print("\nNom : ");
        categorie.setNom(scanner.nextLine());

        System.out.println("\nType");
        System.out.println("1. Dépense");
        System.out.println("2. Revenu");
        System.out.print("Choix : ");

        int type = Integer.parseInt(scanner.nextLine());
        if(type == 1)
            categorie.setType(TypeCategorie.DEPENSE);
        else
            categorie.setType(TypeCategorie.REVENU);
        boolean ajout =categorieController.ajouter(categorie);
        if(ajout)
            System.out.println("\nCatégorie créée.");
        else
            System.out.println("\nErreur.");
    }

    private void modifier() {
        afficherMesCategories();
        System.out.print("\nID catégorie : ");
        int id = Integer.parseInt(scanner.nextLine());
        Categorie categorie = new Categorie();
        categorie.setIdCategorie(id);
        System.out.print("Nouveau nom : ");
        categorie.setNom(scanner.nextLine());
        System.out.println("1. Dépense");
        System.out.println("2. Revenu");
        int type = Integer.parseInt(scanner.nextLine());
        if(type == 1)
            categorie.setType(TypeCategorie.DEPENSE);
        else
            categorie.setType(TypeCategorie.REVENU);
        if(categorieController.modifier(categorie))
            System.out.println("\nModification réussie.");
        else
            System.out.println("\nErreur.");
    }

    private void supprimer() {
        afficherMesCategories();
        System.out.print("\nID catégorie : ");
        int id =Integer.parseInt(scanner.nextLine());
        if(categorieController.supprimer(id))
            System.out.println("\nSuppression effectuée.");
        else
            System.out.println("\nSuppression impossible.");
    }

    private void afficherMesCategories() {
        List<Categorie> categories =categorieController.listerUtilisateur();
        System.out.println();
        for(Categorie categorie : categories){
            System.out.println(categorie.getIdCategorie()+ " | "+ categorie.getNom()+ " | "+ categorie.getType());
        }
    }

    private void afficherCategoriesCommunes(){
        List<Categorie> categories =categorieController.listerCommunes();
        System.out.println();
        for(Categorie categorie : categories){
            System.out.println(categorie.getIdCategorie()+ " | "+ categorie.getNom()+ " | "+ categorie.getType());
        }
    }
}