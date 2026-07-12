<?php

namespace App\Config;

use PDO;
use PDOException;

class Database
{
    private static ?Database $instance = null;

    private PDO $connection;

    private function __construct()
    {
        $host = "localhost";
        $port = "5432";
        $dbname = "BudgetTracker";
        $user = "postgres";
        $password = "7534470144";

        try {

            $this->connection = new PDO(
                "pgsql:host=$host;port=$port;dbname=$dbname",
                $user,
                $password
            );

            $this->connection->setAttribute(
                PDO::ATTR_ERRMODE,
                PDO::ERRMODE_EXCEPTION
            );

            $this->connection->setAttribute(
                PDO::ATTR_DEFAULT_FETCH_MODE,
                PDO::FETCH_ASSOC
            );

        } catch (PDOException $e) {

            die("Erreur de connexion : " . $e->getMessage());

        }
    }

    public static function getInstance(): Database
    {
        if (self::$instance == null) {
            self::$instance = new Database();
        }

        return self::$instance;
    }

    public function getConnection(): PDO
    {
        return $this->connection;
    }
}