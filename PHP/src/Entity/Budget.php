<?php

namespace App\Entity;

class Budget
{
    private ?int $idBudget = null;
    private int $idCategorie;
    private int $idUtilisateur;
    private float $montantMaximum;
    private float $montantConsomme;
    private float $montantRestant;
    private string $nomCategorie;

    public function getIdBudget(): ?int
    {
        return $this->idBudget;
    }

    public function setIdBudget(?int $idBudget): void
    {
        $this->idBudget = $idBudget;
    }

    public function getIdCategorie(): int
    {
        return $this->idCategorie;
    }

    public function setIdCategorie(int $idCategorie): void
    {
        $this->idCategorie = $idCategorie;
    }

    public function getIdUtilisateur(): int
    {
        return $this->idUtilisateur;
    }

    public function setIdUtilisateur(int $idUtilisateur): void
    {
        $this->idUtilisateur = $idUtilisateur;
    }

    public function getMontantMaximum(): float
    {
        return $this->montantMaximum;
    }

    public function setMontantMaximum(float $montantMaximum): void
    {
        $this->montantMaximum = $montantMaximum;
    }

    public function getMontantConsomme(): float
    {
        return $this->montantConsomme;
    }

    public function setMontantConsomme(float $montantConsomme): void
    {
        $this->montantConsomme = $montantConsomme;
    }

    public function getMontantRestant(): float
    {
        return $this->montantRestant;
    }

    public function setMontantRestant(float $montantRestant): void
    {
        $this->montantRestant = $montantRestant;
    }
    public function getNomCategorie(): string
    {
        return $this->nomCategorie;
    }

    public function setNomCategorie(string $nomCategorie): void
    {
        $this->nomCategorie = $nomCategorie;
    }
}
