<?php

namespace App\Repository;

use App\Config\AbstractRepository;
use PDO;

class DashboardRepository extends AbstractRepository
{

    public function getDashboard(int $idUtilisateur): array
{
    $sql = "
    SELECT

        u.solde,

        COALESCE((
            SELECT SUM(montant)
            FROM transaction t
            WHERE t.id_utilisateur = :id
            AND t.type = 'REVENU'
            AND DATE_TRUNC('month', t.date_transaction) =
                DATE_TRUNC('month', CURRENT_DATE)
        ),0) AS revenus,

        COALESCE((
            SELECT SUM(montant)
            FROM transaction t
            WHERE t.id_utilisateur = :id
            AND t.type = 'DEPENSE'
            AND DATE_TRUNC('month', t.date_transaction) =
                DATE_TRUNC('month', CURRENT_DATE)
        ),0) AS depenses,

        COALESCE((
            SELECT COUNT(*)
            FROM budget
            WHERE id_utilisateur = :id
        ),0) AS budgets,

        COALESCE((
            SELECT COUNT(*)
            FROM transaction
            WHERE id_utilisateur = :id
        ),0) AS transactions

    FROM utilisateur u
    WHERE u.id_utilisateur = :id
    ";
    $statement = $this->database->prepare($sql);
    $statement->execute(["id" => $idUtilisateur]);
    return $statement->fetch(PDO::FETCH_ASSOC);
}
}
