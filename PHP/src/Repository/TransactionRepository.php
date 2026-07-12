<?php

namespace App\Repository;

use App\Config\AbstractRepository;
use App\Entity\Transaction;
use PDO;

class TransactionRepository extends AbstractRepository
{

    public function lister(int $idUtilisateur): array
    {

        $sql = 'SELECT
                    t.*,
                    c.nom AS nom_categorie
                FROM "transaction" t
                INNER JOIN categorie c
                    ON c.id_categorie = t.id_categorie
                WHERE t.id_utilisateur = :id
                ORDER BY t.date_transaction DESC';

        $statement = $this->database->prepare($sql);
        $statement->bindValue(":id", $idUtilisateur);
        $statement->execute();
        $transactions = [];
        while ($result = $statement->fetch(PDO::FETCH_ASSOC)) {
            $transactions[] = $this->hydrate($result);
        }
        return $transactions;
    }

    public function rechercherParId(int $idTransaction): ?Transaction
    {
        $sql = 'SELECT
                    t.*,
                    c.nom AS nom_categorie
                FROM "transaction" t
                INNER JOIN categorie c
                    ON c.id_categorie = t.id_categorie
                WHERE t.id_transaction = :id';
        $statement = $this->database->prepare($sql);
        $statement->bindValue(":id", $idTransaction);
        $statement->execute();
        $result = $statement->fetch(PDO::FETCH_ASSOC);
        if (!$result) {
            return null;
        }
        return $this->hydrate($result);
    }

    public function ajouter(Transaction $transaction): bool
    {
        $sql = 'INSERT INTO "transaction"
                (
                    montant,
                    date_transaction,
                    type,
                    description,
                    id_utilisateur,
                    id_categorie
                )
                VALUES
                (
                    :montant,
                    :dateTransaction,
                    :type,
                    :description,
                    :utilisateur,
                    :categorie
                )';
        $statement = $this->database->prepare($sql);
        return $statement->execute([
            "montant" => $transaction->getMontant(),
            "dateTransaction" => $transaction->getDateTransaction(),
            "type" => $transaction->getType(),
            "description" => $transaction->getDescription(),
            "utilisateur" => $transaction->getIdUtilisateur(),
            "categorie" => $transaction->getIdCategorie()
        ]);
    }

    public function modifier(Transaction $transaction): bool
    {
        $sql = '
        UPDATE "transaction"
        SET
            montant = :montant,
            date_transaction = :dateTransaction,
            type = :type,
            description = :description,
            id_categorie = :idCategorie
        WHERE id_transaction = :id';
        $statement = $this->database->prepare($sql);
        return $statement->execute([
            "montant" => $transaction->getMontant(),
            "dateTransaction" => $transaction->getDateTransaction(),
            "type" => $transaction->getType(),
            "description" => $transaction->getDescription(),
            "idCategorie" => $transaction->getIdCategorie(),
            "id" => $transaction->getIdTransaction()
        ]);
    }

    public function supprimer(int $idTransaction): bool
    {
        $sql = 'DELETE FROM "transaction" WHERE id_transaction=:id';
        $statement = $this->database->prepare($sql);
        return $statement->execute([ "id" => $idTransaction]);
    }

    private function hydrate(array $data): Transaction
    {
        $transaction = new Transaction();
        $transaction->setIdTransaction($data["id_transaction"]);
        $transaction->setMontant($data["montant"]);
        $transaction->setDateTransaction($data["date_transaction"]);
        $transaction->setType($data["type"]);
        $transaction->setDescription($data["description"]);
        $transaction->setIdUtilisateur($data["id_utilisateur"]);
        $transaction->setIdCategorie($data["id_categorie"]);
        if (isset($data["nom_categorie"])) {
            $transaction->setNomCategorie($data["nom_categorie"]);
        }
        return $transaction;
    }
}
