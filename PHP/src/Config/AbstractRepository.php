<?php

namespace App\Config;

use PDO;

abstract class AbstractRepository
{
    protected PDO $database;

    public function __construct()
    {
        $this->database = Database::getInstance()->getConnection();
    }
    protected function getDatabase(): \PDO
    {
        return $this->database;
    }
}
