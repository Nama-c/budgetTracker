<?php

namespace App\Controller;

use App\Config\AbstractController;
use App\Services\DashboardService;

class DashboardController extends AbstractController
{

    private DashboardService $dashboardService;

    public function __construct()
    {
        $this->dashboardService = new DashboardService();
    }

    public function index()
    {
        $utilisateur = $_SESSION["user"];
        $dashboard = $this->dashboardService->statistiques($utilisateur->getIdUtilisateur());
        $this->render("dashboard/index", compact("dashboard"));
    }
}
