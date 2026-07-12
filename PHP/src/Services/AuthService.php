<?php

namespace App\Services;

use App\Entity\Utilisateur;
use App\Repository\UtilisateurRepository;
use App\Services\CategorieService;

class AuthService
{
    private UtilisateurRepository $utilisateurRepository;
    private CategorieService $categorieService;
    public function __construct()
    {
        $this->utilisateurRepository = new UtilisateurRepository();
        $this->categorieService = new CategorieService();
    }

    public function connexion(string $email, string $motDePasse): ?Utilisateur
    {

        $utilisateur = $this->utilisateurRepository->seConnecter($email);

        if ($utilisateur == null) {
            return null;
        }

        if (!password_verify(
            $motDePasse,
            $utilisateur->getMotDePasse()
        )) {

            return null;
        }

        return $utilisateur;
    }

    public function inscription(Utilisateur $utilisateur): bool
    {

        if ($this->utilisateurRepository->seConnecter($utilisateur->getEmail()) != null) {
            return false;
        }

        $utilisateur->setMotDePasse(
            password_hash(
                $utilisateur->getMotDePasse(),
                PASSWORD_DEFAULT
            )
        );

        if (!$this->utilisateurRepository->ajouter($utilisateur)) {

            return false;
        }

        $this->categorieService
            ->creerCategoriesParDefaut(
                $utilisateur->getIdUtilisateur()
            );

        return true;
    }
}
