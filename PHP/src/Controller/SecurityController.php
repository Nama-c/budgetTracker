<?php

namespace App\Controller;

use App\Config\AbstractController;
use App\Entity\Utilisateur;
use App\Services\AuthService;

class SecurityController extends AbstractController
{

    private AuthService $authService;

    public function __construct()
    {
        $this->authService = new AuthService();
    }

    public function login()
    {
        if ($_SERVER["REQUEST_METHOD"] === "POST") {
            $utilisateur =$this->authService->connexion( $_POST["email"],$_POST["motDePasse"]);
            if ($utilisateur != null) {
                $_SESSION["user"] = $utilisateur;
                $this->redirect("?page=dashboard");
            }
            $erreur = "Email ou mot de passe incorrect.";
            $this->render("security/login",[],"guest");
            return;
        }
        $this->render("security/login",[],"guest");
    }
    public function register()
    {
        if ($_SERVER["REQUEST_METHOD"] === "POST") {
            if (
                empty($_POST["nom"]) ||
                empty($_POST["prenom"]) ||
                empty($_POST["email"]) ||
                empty($_POST["motDePasse"]) ||
                empty($_POST["confirmMotDePasse"])
            ) {
                $erreur = "Tous les champs sont obligatoires.";
                $this->render("security/register", [], "guest");
                return;
            }
            if ($_POST["motDePasse"] !== $_POST["confirmMotDePasse"]) {
                $erreur = "Les mots de passe ne correspondent pas.";
                $this->render("security/register", [], "guest");
                return;
            }
            $utilisateur = new Utilisateur();
            $utilisateur->setNom(trim($_POST["nom"]));
            $utilisateur->setPrenom(trim($_POST["prenom"]));
            $utilisateur->setEmail(trim($_POST["email"]));
            $utilisateur->setMotDePasse($_POST["motDePasse"]);
            $utilisateur->setSolde(0);
            $utilisateur->setRole("USER");
            $utilisateur->setActif(true);
            if ($this->authService->inscription($utilisateur)) {
                $this->redirect("?page=login");
            }
            $erreur = "Cette adresse email existe déjà.";
            $this->render("security/register", [], "guest");
            return;
        }
        $this->render("security/register", [], "guest");
    }

    public function logout()
    {
        session_destroy();
        $this->redirect("?page=login");
    }
}
