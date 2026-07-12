<?php

namespace App\Services;

use App\Entity\Transaction;
use App\Repository\TransactionRepository;
use App\Repository\BudgetRepository;
use App\Repository\categorieRepository;
use App\Repository\UtilisateurRepository;
use App\Config\Database;


class TransactionService
{
    private TransactionRepository $transactionRepository;
    private BudgetRepository $budgetRepository;
    private CategorieRepository $categorieRepository;
    private UtilisateurRepository $utilisateurRepository;
    public function __construct()
    {
        $this->transactionRepository = new TransactionRepository();
        $this->budgetRepository = new BudgetRepository();
        $this->categorieRepository = new CategorieRepository();
        $this->utilisateurRepository = new UtilisateurRepository();
    }

    public function lister(int $idUtilisateur): array
    {
        return $this->transactionRepository->lister($idUtilisateur);
    }

    public function rechercherParId(int $id): ?Transaction
    {
        return $this->transactionRepository->rechercherParId($id);
    }

    public function ajouter(Transaction $transaction): bool
    {
        $pdo = Database::getInstance()->getConnection();
        $budget = null;
        try {
            $pdo->beginTransaction();
            $categorie = $this->categorieRepository->rechercherParId($transaction->getIdCategorie());
            if ($categorie == null) {
                $pdo->rollBack();
                return false;
            }
            $transaction->setType($categorie->getType());
            $utilisateur = $this->utilisateurRepository->rechercherParId($transaction->getIdUtilisateur());
            if ($utilisateur == null) {
                $pdo->rollBack();
                return false;
            }
            if ($categorie->getType() == "DEPENSE") {
                if ($utilisateur->getSolde() < $transaction->getMontant()) {
                    $pdo->rollBack();
                    return false;
                }
                $nouveauSolde = $utilisateur->getSolde() - $transaction->getMontant();
            } else {
                $nouveauSolde = $utilisateur->getSolde() + $transaction->getMontant();
            }
            if (!$this->transactionRepository->ajouter($transaction)) {
                $pdo->rollBack();
                return false;
            }
            if (!$this->utilisateurRepository->modifierSolde(
                $utilisateur->getIdUtilisateur(),
                $nouveauSolde
            )) {
                $pdo->rollBack();
                return false;
            }
            if ($categorie->getType() == "DEPENSE") {
                $budget = $this->budgetRepository->rechercherParCategorie($utilisateur->getIdUtilisateur(), $categorie->getIdCategorie());
                if ($budget != null) {
                    $budget->setMontantConsomme($budget->getMontantConsomme() + $transaction->getMontant());
                    $budget->setMontantRestant($budget->getMontantMaximum() - $budget->getMontantConsomme());
                    if (!$this->budgetRepository->modifierMontants($budget)) {
                        $pdo->rollBack();
                        return false;
                    }
                }
            }
            if ($budget != null) {
                if ($budget->getMontantRestant() <= 0) {
                    $_SESSION["warning"] = "⚠ Budget dépassé.";
                } elseif ($budget->getMontantRestant() <= ($budget->getMontantMaximum() * 0.20)) {
                    $_SESSION["warning"] = "⚠ Il reste moins de 20% du budget.";
                }
            }
            $pdo->commit();
            return true;
        } catch (\Exception $e) {
            $pdo->rollBack();
            return false;
        }
    }
    public function modifier(Transaction $transaction): bool
    {
        $pdo = Database::getInstance()->getConnection();
        try {
            $pdo->beginTransaction();
            $ancienneTransaction = $this->transactionRepository->rechercherParId($transaction->getIdTransaction());
            if ($ancienneTransaction == null) {
                $pdo->rollBack();
                return false;
            }
            $utilisateur = $this->utilisateurRepository->rechercherParId($ancienneTransaction->getIdUtilisateur());
            if ($utilisateur == null) {
                $pdo->rollBack();
                return false;
            }
            $categorie = $this->categorieRepository->rechercherParId($transaction->getIdCategorie());
            if ($categorie == null) {
                $pdo->rollBack();
                return false;
            }
            $transaction->setType($categorie->getType());
            $solde = $utilisateur->getSolde();
            if ($ancienneTransaction->getType() == "REVENU") {
                $solde -= $ancienneTransaction->getMontant();
            } else {
                $solde += $ancienneTransaction->getMontant();
            }
            $ancienBudget = $this->budgetRepository->rechercherParCategorie($utilisateur->getIdUtilisateur(),$ancienneTransaction->getIdCategorie());
            if ($ancienBudget != null && $ancienneTransaction->getType() == "DEPENSE") {
                if (!$this->budgetRepository->retirerConsommation($ancienBudget->getIdBudget(),$ancienneTransaction->getMontant())) {
                    $pdo->rollBack();
                    return false;
                }
            }
            if ($transaction->getType() == "REVENU") {
                $solde += $transaction->getMontant();
            } else {
                $solde -= $transaction->getMontant();
            }
            $nouveauBudget = $this->budgetRepository->rechercherParCategorie($utilisateur->getIdUtilisateur(),$transaction->getIdCategorie());
            if ($nouveauBudget != null && $transaction->getType() == "DEPENSE") {
                if (!$this->budgetRepository->ajouterConsommation($nouveauBudget->getIdBudget(),$transaction->getMontant())) {
                    $pdo->rollBack();
                    return false;
                }
            }
            if (!$this->transactionRepository->modifier($transaction)) {
                $pdo->rollBack();
                return false;
            }
            if (!$this->utilisateurRepository->modifierSolde($utilisateur->getIdUtilisateur(),$solde)) {
                $pdo->rollBack();
                return false;
            }
            $pdo->commit();
            return true;
        } catch (\Exception $e) {
            $pdo->rollBack();
            return false;
        }
    }
    public function supprimer(int $id): bool
    {
        $pdo = Database::getInstance()->getConnection();
        try {
            $pdo->beginTransaction();
            $transaction = $this->transactionRepository->rechercherParId($id);
            if ($transaction == null) {
                $pdo->rollBack();
                return false;
            }
            $utilisateur = $this->utilisateurRepository->rechercherParId($transaction->getIdUtilisateur());
            if ($utilisateur == null) {
                $pdo->rollBack();
                return false;
            }
            if ($transaction->getType() == "DEPENSE") {
                $budget = $this->budgetRepository->rechercherParCategorie($utilisateur->getIdUtilisateur(),$transaction->getIdCategorie());
                if ($budget != null) {$budget->setMontantConsomme($budget->getMontantConsomme()- $transaction->getMontant());
                    $budget->setMontantRestant($budget->getMontantMaximum() - $budget->getMontantConsomme());
                    if (!$this->budgetRepository->modifierMontants($budget)) {
                        $pdo->rollBack();
                        return false;
                    }
                }
            }
            $solde = $utilisateur->getSolde();
            if ($transaction->getType() == "REVENU") {
                $solde -= $transaction->getMontant();
            } else {
                $solde += $transaction->getMontant();
            }
            if (!$this->transactionRepository->supprimer($id)) {
                $pdo->rollBack();
                return false;
            }
            if (!$this->utilisateurRepository->modifierSolde($utilisateur->getIdUtilisateur(),$solde)) {
                $pdo->rollBack();
                return false;
            }
            $pdo->commit();
            return true;
        } catch (\Exception $e) {
            $pdo->rollBack();
            return false;
        }
    }
}
