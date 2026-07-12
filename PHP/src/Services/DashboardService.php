<?php

namespace App\Services;

use App\Repository\DashboardRepository;

class DashboardService
{

    private DashboardRepository $dashboardRepository;

    public function __construct()
    {
        $this->dashboardRepository=new DashboardRepository();
    }

    public function statistiques(int $idUtilisateur):array
    {
        return $this->dashboardRepository ->getDashboard($idUtilisateur);
    }

}