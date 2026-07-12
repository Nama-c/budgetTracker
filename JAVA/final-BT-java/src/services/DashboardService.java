package services;

import repositories.DashboardRepository;

import java.math.BigDecimal;

public class DashboardService {

    private final DashboardRepository dashboardRepository;

    public DashboardService(){

        dashboardRepository=new DashboardRepository();

    }

    public BigDecimal revenus(int id){

        return dashboardRepository.getRevenus(id);

    }

    public BigDecimal depenses(int id){

        return dashboardRepository.getDepenses(id);

    }

}