package models;

import java.math.BigDecimal;

public class Statistique {
    private BigDecimal totalDepenses;
    private BigDecimal totalRevenus;
    private BigDecimal solde;
    private String periode;

    public Statistique(BigDecimal totalDepenses, BigDecimal totalRevenus, BigDecimal solde, String periode) {
        this.totalDepenses = totalDepenses;
        this.totalRevenus = totalRevenus;
        this.solde = solde;
        this.periode = periode;
    }

    public Statistique() {
    }

    public BigDecimal getTotalDepenses() {
        return totalDepenses;
    }

    public BigDecimal getTotalRevenus() {
        return totalRevenus;
    }

    public BigDecimal getSolde() {
        return solde;
    }

    public String getPeriode() {
        return periode;
    }

    public void setTotalDepenses(BigDecimal totalDepenses) {
        this.totalDepenses = totalDepenses;
    }

    public void setTotalRevenus(BigDecimal totalRevenus) {
        this.totalRevenus = totalRevenus;
    }

    public void setSolde(BigDecimal solde) {
        this.solde = solde;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }

    @Override
    public String toString() {
        return "Statistique [totalDepenses=" + totalDepenses + ", totalRevenus=" + totalRevenus + ", solde=" + solde
                + ", periode=" + periode + "]";
    }
}