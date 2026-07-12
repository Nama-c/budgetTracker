<?php

namespace App\Services;

use App\Entity\Categorie;
use App\Repository\CategorieRepository;

class CategorieService
{
    private CategorieRepository $categorieRepository;

    public function __construct()
    {
        $this->categorieRepository = new CategorieRepository();
    }

    public function lister(int $idUtilisateur): array
    {
        return $this->categorieRepository->lister($idUtilisateur);
    }

    public function rechercherParId(int $idCategorie): ?Categorie
    {
        return $this->categorieRepository->rechercherParId($idCategorie);
    }

    public function ajouter(Categorie $categorie): bool
    {
        return $this->categorieRepository->ajouter($categorie);
    }

    public function modifier(Categorie $categorie): bool
    {
        return $this->categorieRepository->modifier($categorie);
    }

    public function supprimer(int $idCategorie): bool
    {
        return $this->categorieRepository->supprimer($idCategorie);
    }
    public function creerCategoriesParDefaut(
        int $idUtilisateur
    ): void {
        $this->categorieRepository
            ->creerCategoriesParDefaut(
                $idUtilisateur
            );
    }
}
