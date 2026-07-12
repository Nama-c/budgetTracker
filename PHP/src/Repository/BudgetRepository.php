<?php

namespace App\Repository;

use App\Config\AbstractRepository;
use App\Entity\Budget;
use PDO;

class BudgetRepository extends AbstractRepository
{
    public function lister(int $idUtilisateur): array
    {
        $sql = "SELECT b.*,c.nom AS nom_categorie FROM budget b INNER JOIN categorie c 
        ON c.id_categorie = b.id_categorie WHERE b.id_utilisateur = :id";
        $statement = $this->database->prepare($sql);
        $statement->bindValue(":id", $idUtilisateur);
        $statement->execute();
        $budgets = [];
        while ($result = $statement->fetch(PDO::FETCH_ASSOC)) {
            $budgets[] = $this->hydrate($result);
        }
        return $budgets;
    }

    public function rechercherParId(int $idBudget): ?Budget
    {
        $sql = "SELECT * FROM budget WHERE id_budget = :id";
        $statement = $this->database->prepare($sql);
        $statement->bindValue(":id", $idBudget);
        $statement->execute();
        $result = $statement->fetch(PDO::FETCH_ASSOC);
        if (!$result) {
            return null;
        }
        return $this->hydrate($result);
    }

    public function rechercherParCategorie(int $idUtilisateur,int $idCategorie): ?Budget {
        $sql = "SELECT * FROM budget WHERE id_utilisateur = :idUtilisateur AND id_categorie = :idCategorie";
        $statement = $this->database->prepare($sql);
        $statement->execute([
            "idUtilisateur" => $idUtilisateur,
            "idCategorie" => $idCategorie
        ]);
        $result = $statement->fetch(PDO::FETCH_ASSOC);
        if (!$result) {
            return null;
        }
        return $this->hydrate($result);
    }
    public function ajouter(Budget $budget): bool
    {

        $sql = "INSERT INTO budget
                (
                    montant_maximum,
                    montant_consomme,
                    montant_restant,
                    id_utilisateur,
                    id_categorie
                )
                VALUES
                (
                    :maximum,
                    :consomme,
                    :restant,
                    :utilisateur,
                    :categorie
                )";
        $statement = $this->database->prepare($sql);
        return $statement->execute([
            "maximum" => $budget->getMontantMaximum(),
            "consomme" => $budget->getMontantConsomme(),
            "restant" => $budget->getMontantRestant(),
            "utilisateur" => $budget->getIdUtilisateur(),
            "categorie" => $budget->getIdCategorie()
        ]);
    }
    public function modifier(Budget $budget): bool
    {
        $sql = "UPDATE budget
                SET montant_maximum = :maximum,
                    montant_consomme = :consomme,
                    montant_restant = :restant
                WHERE id_budget = :id";
        $statement = $this->database->prepare($sql);
        return $statement->execute([
            "maximum" => $budget->getMontantMaximum(),
            "consomme" => $budget->getMontantConsomme(),
            "restant" => $budget->getMontantRestant(),
            "id" => $budget->getIdBudget()
        ]);
    }
    public function supprimer(int $idBudget): bool
    {
        $sql = "DELETE FROM budget WHERE id_budget = :id";
        $statement = $this->database->prepare($sql);
        return $statement->execute(["id" => $idBudget]);
    }

    private function hydrate(array $data): Budget
    {
        $budget = new Budget();
        $budget->setIdBudget($data["id_budget"]);
        $budget->setMontantMaximum($data["montant_maximum"]);
        $budget->setMontantConsomme($data["montant_consomme"]);
        $budget->setMontantRestant($data["montant_restant"]);
        $budget->setIdUtilisateur($data["id_utilisateur"]);
        $budget->setIdCategorie($data["id_categorie"]);
        if (isset($data["nom_categorie"])) {
            $budget->setNomCategorie($data["nom_categorie"]);
        }
        return $budget;
    }

    public function modifierMontants(
        Budget $budget
    ): bool {

        $sql = "UPDATE budget
          SET montant_consomme=:consomme,
              montant_restant=:restant
          WHERE id_budget=:id";
        $statement = $this->database->prepare($sql);
        return $statement->execute([
            "consomme" => $budget->getMontantConsomme(),
            "restant" => $budget->getMontantRestant(),
            "id" => $budget->getIdBudget()
        ]);
    }
    public function ajouterConsommation(int $idBudget,float $montant): bool {
        $budget = $this->rechercherParId($idBudget);
        if ($budget == null) {
            return false;
        }
        $budget->setMontantConsomme($budget->getMontantConsomme() + $montant);
        $budget->setMontantRestant($budget->getMontantMaximum() - $budget->getMontantConsomme());
        return $this->modifierMontants($budget);
    }
    public function retirerConsommation(int $idBudget,float $montant): bool {
        $budget = $this->rechercherParId($idBudget);
        if ($budget == null) {
            return false;
        }
        $budget->setMontantConsomme($budget->getMontantConsomme() - $montant);
        $budget->setMontantRestant($budget->getMontantMaximum()- $budget->getMontantConsomme());
        return $this->modifierMontants($budget);
    }
}
