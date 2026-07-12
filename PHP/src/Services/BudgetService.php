<?php

namespace App\Services;

use App\Entity\Budget;
use App\Repository\BudgetRepository;

class BudgetService
{
    private BudgetRepository $budgetRepository;

    public function __construct()
    {
        $this->budgetRepository = new BudgetRepository();
    }

    public function lister(int $idUtilisateur): array
    {
        return $this->budgetRepository->lister($idUtilisateur);
    }

    public function rechercherParId(int $idBudget): ?Budget
    {
        return $this->budgetRepository->rechercherParId($idBudget);
    }

    public function ajouter(Budget $budget): bool
    {
        return $this->budgetRepository->ajouter($budget);
    }

    public function modifier(Budget $budget): bool
    {
        return $this->budgetRepository->modifier($budget);
    }

    public function supprimer(int $idBudget): bool
    {
        return $this->budgetRepository->supprimer($idBudget);
    }
}