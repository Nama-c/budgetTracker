<?php

namespace App\Controller;

use App\Config\AbstractController;
use App\Entity\Categorie;
use App\Services\CategorieService;

class CategorieController extends AbstractController
{
    private CategorieService $categorieService;

    public function __construct()
    {
        $this->categorieService = new CategorieService();
    }

    public function index()
    {
        $utilisateur = $_SESSION["user"];
        $categories = $this->categorieService->lister($utilisateur->getIdUtilisateur());
        $this->render("categorie/list",compact("categories"));
    }

    public function create()
    {
        if ($_SERVER["REQUEST_METHOD"] === "POST") {
            $categorie = new Categorie();
            $categorie->setNom($_POST["nom"]);
            $categorie->setType($_POST["type"]);
            $categorie->setParDefaut(false);
            $categorie->setIdUtilisateur($_SESSION["user"]->getIdUtilisateur());
            $this->categorieService->ajouter($categorie);
            $this->redirect("?page=categorie");
        }
        $this->render("categorie/form");
    }

    public function edit()
    {
        $categorie = $this->categorieService->rechercherParId($_GET["id"]);
        if ($_SERVER["REQUEST_METHOD"] === "POST") {
            $categorie->setNom($_POST["nom"]);
            $categorie->setType($_POST["type"]);
            $this->categorieService->modifier($categorie);
            $this->redirect("?page=categorie");
        }
        $this->render("categorie/form",compact("categorie"));
    }

    public function delete()
    {
        $this->categorieService->supprimer($_GET["id"]);
        $this->redirect("?page=categorie");
    }
}