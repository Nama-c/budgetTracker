package repositories;

import config.DatabaseConnection;
import models.Role;
import models.StatutUtilisateur;
import models.Utilisateur;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurRepository {

    private final Connection connection;

    public UtilisateurRepository() {
        this(DatabaseConnection.getInstance().getConnection());
    }

    public UtilisateurRepository(Connection connection) {
        this.connection = connection;
    }

    public int ajouter(Utilisateur utilisateur) {
        String sql = """
                INSERT INTO utilisateur
                (nom, prenom, email, mdp, role, statut, solde)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, utilisateur.getNom());
            statement.setString(2, utilisateur.getPrenom());
            statement.setString(3, utilisateur.getEmail());
            statement.setString(4, utilisateur.getMdp());
            statement.setString(5, utilisateur.getRole().name());
            statement.setBoolean(6, utilisateur.getStatut() == StatutUtilisateur.ACTIF);
            statement.setBigDecimal(7, utilisateur.getSolde());
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Utilisateur rechercherParEmail(String email) {
        String sql = """
                SELECT *
                FROM utilisateur
                WHERE email = ?
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return construireUtilisateur(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Utilisateur rechercherParId(int idUtilisateur) {
        String sql = """
                SELECT *
                FROM utilisateur
                WHERE id_utilisateur = ?
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idUtilisateur);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return construireUtilisateur(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int modifier(Utilisateur utilisateur) {
        String sql = """
                UPDATE utilisateur
                SET nom=?,
                    prenom=?,
                    email=?,
                    mdp=?,
                    role=?,
                    statut=?,
                    solde=?
                WHERE id_utilisateur=?
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, utilisateur.getNom());
            statement.setString(2, utilisateur.getPrenom());
            statement.setString(3, utilisateur.getEmail());
            statement.setString(4, utilisateur.getMdp());
            statement.setString(5, utilisateur.getRole().name());
            statement.setBoolean(6,
                    utilisateur.getStatut() == StatutUtilisateur.ACTIF);
            statement.setBigDecimal(7, utilisateur.getSolde());
            statement.setInt(8, utilisateur.getIdUtilisateur());
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int supprimer(int idUtilisateur) {
        String sql = """
                DELETE FROM utilisateur
                WHERE id_utilisateur = ?
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idUtilisateur);
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Utilisateur> lister() {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String sql = """
                SELECT *
                FROM utilisateur
                ORDER BY id_utilisateur
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                utilisateurs.add(construireUtilisateur(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utilisateurs;
    }

    private Utilisateur construireUtilisateur(ResultSet rs) throws SQLException {
        Utilisateur utilisateur = new Utilisateur();

        utilisateur.setIdUtilisateur(rs.getInt("id_utilisateur"));
        utilisateur.setNom(rs.getString("nom"));
        utilisateur.setPrenom(rs.getString("prenom"));
        utilisateur.setEmail(rs.getString("email"));
        utilisateur.setMdp(rs.getString("mdp"));
        utilisateur.setRole(
                Role.valueOf(rs.getString("role")));
        utilisateur.setStatut(
                rs.getBoolean("statut")
                        ? StatutUtilisateur.ACTIF
                        : StatutUtilisateur.INACTIF);
        utilisateur.setSolde(
                rs.getBigDecimal("solde"));
        Timestamp timestamp = rs.getTimestamp("date_inscription");
        if (timestamp != null) {
            utilisateur.setDateInscription(
                    timestamp.toLocalDateTime());
        }
        return utilisateur;
    }

    public int modifierSolde(int idUtilisateur, BigDecimal solde) {
        String sql = """
                UPDATE utilisateur
                SET solde = ?
                WHERE id_utilisateur = ?
                """;
        try (PreparedStatement statement =connection.prepareStatement(sql)) {
            statement.setBigDecimal(1, solde);
            statement.setInt(2, idUtilisateur);
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}