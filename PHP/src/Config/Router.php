<?php

namespace App\Config;

use App\Controller\DashboardController;
use App\Controller\SecurityController;
use App\Controller\CategorieController;
use App\Controller\BudgetController;
use App\Controller\TransactionController;

class Router
{
    public static function run()
    {
        session_start();
        $page = $_GET["page"] ?? "login";
        switch ($page) {
            case "login":
                (new SecurityController())->login();
                break;

            case "register":
                (new SecurityController())->register();
                break;

            case "logout":
                (new SecurityController())->logout();
                break;

            case "dashboard":
                (new DashboardController())->index();
                break;

            case "transaction-edit":
                (new TransactionController())->edit();
                break;
            case "categorie":
                (new CategorieController())->index();
                break;

            case "categorie-create":
                (new CategorieController())->create();
                break;

            case "categorie-edit":
                (new CategorieController())->edit();
                break;

            case "categorie-delete":
                (new CategorieController())->delete();
                break;

            case "budget":
                (new BudgetController())->index();
                break;

            case "budget-create":
                (new BudgetController())->create();
                break;

            case "budget-delete":
                (new BudgetController())->delete();
                break;

            case "transaction":
                (new TransactionController())->index();
                break;

            case "transaction-create":
                (new TransactionController())->create();
                break;

            case "transaction-delete":
                (new TransactionController())->delete();
                break;

            default:
                (new SecurityController())->login();
        }
    }
}
