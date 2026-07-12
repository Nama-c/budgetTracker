package controllers;

import java.math.BigDecimal;

import services.DashboardService;
import session.Session;

public class DashboardController {
    private final DashboardService dashboardService;

    public DashboardController() {

        dashboardService = new DashboardService();

    }

    public BigDecimal revenus() {
        return dashboardService.revenus(Session.getInstance().getUtilisateurConnecte().getIdUtilisateur());
    }

    public BigDecimal depenses() {
        return dashboardService.depenses(Session.getInstance().getUtilisateurConnecte().getIdUtilisateur());
    }
}
