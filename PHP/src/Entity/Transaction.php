<?php

namespace App\Entity;

class Transaction
{
    private ?int $idTransaction = null;
    private float $montant;
    private string $description;
    private string $type;
    private string $dateTransaction;
    private int $idUtilisateur;
    private int $idCategorie;
    private ?string $nomCategorie = null;

    public function getIdTransaction(): ?int
    {
        return $this->idTransaction;
    }

    public function setIdTransaction(?int $idTransaction): void
    {
        $this->idTransaction = $idTransaction;
    }

    public function getMontant(): float
    {
        return $this->montant;
    }

    public function setMontant(float $montant): void
    {
        $this->montant = $montant;
    }

    public function getDescription(): string
    {
        return $this->description;
    }

    public function setDescription(string $description): void
    {
        $this->description = $description;
    }

    public function getType(): string
    {
        return $this->type;
    }

    public function setType(string $type): void
    {
        $this->type = $type;
    }

    public function getDateTransaction(): string
    {
        return $this->dateTransaction;
    }

    public function setDateTransaction(string $dateTransaction): void
    {
        $this->dateTransaction = $dateTransaction;
    }

    public function getIdUtilisateur(): int
    {
        return $this->idUtilisateur;
    }

    public function setIdUtilisateur(int $idUtilisateur): void
    {
        $this->idUtilisateur = $idUtilisateur;
    }

    public function getIdCategorie(): int
    {
        return $this->idCategorie;
    }

    public function setIdCategorie(int $idCategorie): void
    {
        $this->idCategorie = $idCategorie;
    }

    public function getNomCategorie(): ?string
    {
        return $this->nomCategorie;
    }

    public function setNomCategorie(?string $nomCategorie): void
    {
        $this->nomCategorie = $nomCategorie;
    }
}