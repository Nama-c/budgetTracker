<?php

namespace App\Repository;

use App\Config\AbstractRepository;
use App\Entity\Utilisateur;
use PDO;

class UtilisateurRepository extends AbstractRepository
{
    public function __construct()
    {
        parent::__construct();
    }

    public function seConnecter(string $email): ?Utilisateur
    {
        $sql = "SELECT * FROM utilisateur WHERE email = :email LIMIT 1";

        $statement = $this->database->prepare($sql);

        $statement->bindValue(":email", $email);

        $statement->execute();

        $result = $statement->fetch(PDO::FETCH_ASSOC);

        if (!$result) {
            return null;
        }

        return $this->hydrate($result);
    }

    public function rechercherParId(int $idUtilisateur): ?Utilisateur
    {
        $sql = "SELECT * FROM utilisateur WHERE id_utilisateur = :id";

        $statement = $this->database->prepare($sql);

        $statement->bindValue(":id", $idUtilisateur);

        $statement->execute();

        $result = $statement->fetch(PDO::FETCH_ASSOC);

        if (!$result) {
            return null;
        }

        return $this->hydrate($result);
    }

    public function ajouter(Utilisateur $utilisateur): bool
    {
        $sql = "INSERT INTO utilisateur(nom,prenom,email,mdp,role,statut,solde)VALUES(:nom,:prenom,:email,:mdp,:role,:statut,:solde)
        RETURNING id_utilisateur";

        $statement = $this->database->prepare($sql);
        try {
            $statement->execute([
                "nom" => $utilisateur->getNom(),
                "prenom" => $utilisateur->getPrenom(),
                "email" => $utilisateur->getEmail(),
                "mdp" => $utilisateur->getMotDePasse(),
                "solde" => $utilisateur->getSolde(),
                "role" => $utilisateur->getRole(),
                "statut" => $utilisateur->isActif()
            ]);
            $id = $statement->fetchColumn();
            $utilisateur->setIdUtilisateur($id);
            return true;
        } catch (\PDOException $e) {

            die($e->getMessage());
        }
    }

    public function modifier(Utilisateur $utilisateur): bool
    {
        $sql = "UPDATE utilisateur
              SET nom=:nom,
                  prenom=:prenom,
                  email=:email,
                  solde=:solde
              WHERE id_utilisateur=:id";

        $statement = $this->database->prepare($sql);

        return $statement->execute([

            "nom" => $utilisateur->getNom(),
            "prenom" => $utilisateur->getPrenom(),
            "email" => $utilisateur->getEmail(),
            "solde" => $utilisateur->getSolde(),
            "id" => $utilisateur->getIdUtilisateur()

        ]);
    }

    public function modifierMotDePasse(
        int $id,
        string $motDePasse
    ): bool {

        $sql = "UPDATE utilisateur
              SET mdp=:motDePasse
              WHERE id_utilisateur=:id";

        $statement = $this->database->prepare($sql);

        return $statement->execute([

            "motDePasse" => $motDePasse,
            "id" => $id

        ]);
    }

    public function hydrate(array $data): Utilisateur
    {

        $utilisateur = new Utilisateur();

        $utilisateur->setIdUtilisateur($data["id_utilisateur"]);
        $utilisateur->setNom($data["nom"]);
        $utilisateur->setPrenom($data["prenom"]);
        $utilisateur->setEmail($data["email"]);
        $utilisateur->setMotDePasse($data["mdp"]);
        $utilisateur->setSolde($data["solde"]);
        $utilisateur->setRole($data["role"]);
        $utilisateur->setActif($data["statut"]);

        return $utilisateur;
    }

    public function modifierSolde(
        int $idUtilisateur,
        float $solde
    ): bool {

        $sql = "UPDATE utilisateur
          SET solde=:solde
          WHERE id_utilisateur=:id";

        $statement = $this->database->prepare($sql);

        return $statement->execute([

            "solde" => $solde,
            "id" => $idUtilisateur

        ]);
    }
}
