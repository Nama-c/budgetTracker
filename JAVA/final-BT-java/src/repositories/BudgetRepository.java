package repositories;

import config.DatabaseConnection;
import models.Budget;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BudgetRepository {

    private final Connection connection;

    public BudgetRepository() {
        this(DatabaseConnection.getInstance().getConnection());
    }

    public BudgetRepository(Connection connection) {
        this.connection = connection;
    }

    public int ajouter(Budget budget) {
        String sql = """
                INSERT INTO budget
                (id_utilisateur,
                 id_categorie,
                 montant_maximum,
                 montant_consomme,
                 montant_restant)
                VALUES (?,?,?,?,?)
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, budget.getIdUtilisateur());
            statement.setInt(2, budget.getIdCategorie());
            statement.setBigDecimal(3, budget.getMontantMaximum());
            statement.setBigDecimal(4, budget.getMontantConsomme());
            statement.setBigDecimal(5, budget.getMontantRestant());
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int modifier(Budget budget) {
        String sql = """
                UPDATE budget
                SET montant_maximum = ?,
                    montant_consomme = ?,
                    montant_restant = ?,
                    derniere_maj = CURRENT_TIMESTAMP
                WHERE id_budget = ?
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBigDecimal(1, budget.getMontantMaximum());
            statement.setBigDecimal(2, budget.getMontantConsomme());
            statement.setBigDecimal(3, budget.getMontantRestant());
            statement.setInt(4, budget.getIdBudget());
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int supprimer(int idBudget) {
        String sql = """
                DELETE FROM budget
                WHERE id_budget = ?
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idBudget);
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Budget rechercherParCategorie(
            int idUtilisateur,
            int idCategorie) {
        String sql = """
                SELECT *
                FROM budget
                WHERE id_utilisateur = ?
                AND id_categorie = ?
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idUtilisateur);
            statement.setInt(2, idCategorie);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return construireBudget(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Budget> lister(int idUtilisateur) {
        List<Budget> budgets = new ArrayList<>();
        String sql = """
                SELECT *
                FROM budget
                WHERE id_utilisateur = ?
                ORDER BY id_budget
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idUtilisateur);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                budgets.add(construireBudget(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return budgets;
    }

    private Budget construireBudget(ResultSet rs)
            throws SQLException {

        Budget budget = new Budget();

        budget.setIdBudget(rs.getInt("id_budget"));
        budget.setIdUtilisateur(rs.getInt("id_utilisateur"));
        budget.setIdCategorie(rs.getInt("id_categorie"));
        budget.setMontantMaximum(rs.getBigDecimal("montant_maximum"));
        budget.setMontantConsomme(rs.getBigDecimal("montant_consomme"));
        budget.setMontantRestant(rs.getBigDecimal("montant_restant"));
        Timestamp timestamp = rs.getTimestamp("derniere_maj");
        if (timestamp != null) {
            budget.setDerniereMaj(timestamp.toLocalDateTime());
        }
        return budget;
    }

    public int modifierMontants(int idBudget,
            BigDecimal montantConsomme,
            BigDecimal montantRestant) {
        String sql = """
                UPDATE budget
                SET montant_consomme = ?,
                    montant_restant = ?,
                    derniere_maj = CURRENT_TIMESTAMP
                WHERE id_budget = ?
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBigDecimal(1, montantConsomme);
            statement.setBigDecimal(2, montantRestant);
            statement.setInt(3, idBudget);
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Budget rechercherParId(int idBudget) {

        String sql = """
                SELECT *
                FROM budget
                WHERE id_budget = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, idBudget);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return construireBudget(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}