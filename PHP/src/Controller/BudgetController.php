<?php

namespace App\Controller;

use App\Config\AbstractController;
use App\Entity\Budget;
use App\Services\BudgetService;
use App\Services\CategorieService;

class BudgetController extends AbstractController
{
    private BudgetService $budgetService;
    private CategorieService $categorieService;

    public function __construct()
    {
        $this->budgetService = new BudgetService();
        $this->categorieService = new CategorieService();
    }

    public function index()
    {
        if (!isset($_SESSION["user"])) {
            $this->redirect("?page=login");
        }
        $utilisateur = $_SESSION["user"];
        $budgets = $this->budgetService->lister($utilisateur->getIdUtilisateur());
        $this->render("budget/list",compact("budgets"));
    }

    public function create()
    {
        $utilisateur = $_SESSION["user"];
        $categories = $this->categorieService->lister($utilisateur->getIdUtilisateur());
        if ($_SERVER["REQUEST_METHOD"] == "POST") {
            $budget = new Budget();
            $budget->setMontantMaximum($_POST["montant"]);
            $budget->setMontantConsomme(0);
            $budget->setMontantRestant($_POST["montant"]);
            $budget->setIdCategorie($_POST["categorie"]);
            $budget->setIdUtilisateur($utilisateur->getIdUtilisateur());
            $this->budgetService->ajouter($budget);
            $this->redirect("?page=budget");
        }
        $this->render("budget/form",compact("categories"));
    }

    public function delete()
    {
        $this->budgetService->supprimer($_GET["id"]);
        $this->redirect("?page=budget");
    }
}