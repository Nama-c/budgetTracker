<?php

namespace App\Repository;

use App\Config\AbstractRepository;
use App\Entity\Categorie;
use PDO;

class CategorieRepository extends AbstractRepository
{

    public function lister(int $idUtilisateur): array
    {

        $sql = "SELECT * FROM categorie WHERE id_utilisateur = :id OR est_commune = true ORDER BY nom";
        $statement = $this->database->prepare($sql);
        $statement->bindValue(":id", $idUtilisateur);
        $statement->execute();
        $categories = [];
        while ($result = $statement->fetch(PDO::FETCH_ASSOC)) {
            $categories[] = $this->hydrate($result);
        }
        return $categories;
    }

    public function ajouter(Categorie $categorie): bool
    {
        $sql = "INSERT INTO categorie
                (
                    nom,
                    type,
                    est_commune,
                    id_utilisateur
                )
                VALUES
                (
                    :nom,
                    :type,
                    :est_commune,
                    :idUtilisateur
                )";
        $statement = $this->database->prepare($sql);
        return $statement->execute([
            "nom" => $categorie->getNom(),
            "type" => $categorie->getType(),
            "est_commune" => $categorie->isParDefaut(),
            "idUtilisateur" => $categorie->getIdUtilisateur()
        ]);
    }

    public function modifier(Categorie $categorie): bool
    {
        $sql = "UPDATE categorie SET nom = :nom, type = :type WHERE id_categorie = :id";
        $statement = $this->database->prepare($sql);
        return $statement->execute([
            "nom" => $categorie->getNom(),
            "type" => $categorie->getType(),
            "id" => $categorie->getIdCategorie()
        ]);
    }

    public function supprimer(int $idCategorie): bool
    {
        $sql = "DELETE FROM categorie WHERE id_categorie = :id";
        $statement = $this->database->prepare($sql);
        return $statement->execute(["id" => $idCategorie]);
    }

    private function hydrate(array $data): Categorie
    {
        $categorie = new Categorie();
        $categorie->setIdCategorie($data["id_categorie"]);
        $categorie->setNom($data["nom"]);
        $categorie->setType($data["type"]);
        $categorie->setParDefaut($data["est_commune"]);
        $categorie->setIdUtilisateur($data["id_utilisateur"]);
        return $categorie;
    }
    public function rechercherParId(int $idCategorie): ?Categorie
    {
        $sql = "SELECT * FROM categorie WHERE id_categorie=:id";

        $statement = $this->database->prepare($sql);
        $statement->execute(["id" => $idCategorie]);
        $result = $statement->fetch(PDO::FETCH_ASSOC);
        if (!$result) {
            return null;
        }
        return $this->hydrate($result);
    }

    public function creerCategoriesParDefaut(int $idUtilisateur): void {
        $categories = [
            ["Salaire", "REVENU"],
            ["Prime", "REVENU"],
            ["Transport", "DEPENSE"],
            ["Alimentation", "DEPENSE"],
            ["Logement", "DEPENSE"],
            ["Santé", "DEPENSE"],
            ["Loisirs", "DEPENSE"]

        ];
        $sql = "
        INSERT INTO categorie
        (
            nom,
            type,
            est_commune,
            id_utilisateur
        )
        VALUES
        (
            :nom,
            :type,
            false,
            :idUtilisateur
        )";
        $statement = $this->database->prepare($sql);
        foreach ($categories as $categorie) {
            $statement->execute([
                "nom" => $categorie[0],
                "type" => $categorie[1],
                "idUtilisateur" => $idUtilisateur
            ]);
        }
    }
}
