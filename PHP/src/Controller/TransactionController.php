<?php

namespace App\Controller;

use App\Config\AbstractController;
use App\Entity\Transaction;
use App\Services\CategorieService;
use App\Services\TransactionService;

class TransactionController extends AbstractController
{
    private TransactionService $transactionService;
    private CategorieService $categorieService;

    public function __construct()
    {
        $this->transactionService = new TransactionService();
        $this->categorieService = new CategorieService();
    }

    public function index()
    {
        $utilisateur = $_SESSION["user"];
        $transactions = $this->transactionService->lister($utilisateur->getIdUtilisateur());
        $this->render("transaction/list",compact("transactions"));
    }

    public function create()
    {
        $utilisateur = $_SESSION["user"];
        $categories = $this->categorieService->lister($utilisateur->getIdUtilisateur());
        if ($_SERVER["REQUEST_METHOD"] == "POST") {
            $transaction = new Transaction();
            $transaction->setMontant($_POST["montant"]);
            $transaction->setDescription($_POST["description"]);
            $transaction->setType($_POST["type"]);
            $transaction->setDateTransaction($_POST["dateTransaction"]);
            $transaction->setIdCategorie($_POST["idCategorie"]);
            $transaction->setIdUtilisateur($utilisateur->getIdUtilisateur());
            $this->transactionService->ajouter($transaction);
            $this->redirect("?page=transaction");
        }
        $this->render("transaction/form",compact("categories"));
    }

    public function delete()
    {
        $this->transactionService->supprimer($_GET["id"]);
        $this->redirect("?page=transaction");
    }
    public function edit()
    {
        $transaction = $this->transactionService->rechercherParId($_GET["id"]);
        if ($_SERVER["REQUEST_METHOD"] === "POST") {
            $transaction->setMontant($_POST["montant"]);
            $transaction->setType($_POST["type"]);
            $transaction->setDescription($_POST["description"]);
            $transaction->setDateTransaction($_POST["dateTransaction"]);
            $transaction->setIdCategorie($_POST["idCategorie"]);
            $this->transactionService->modifier($transaction);
            $this->redirect("?page=transaction");
        }
        $categories = $this->categorieService->lister($_SESSION["user"]->getIdUtilisateur());
        $this->render("transaction/form",compact("transaction","categories"));
    }
}