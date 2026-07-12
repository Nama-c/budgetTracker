package repositories;

import config.DatabaseConnection;
import models.Categorie;
import models.TypeCategorie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategorieRepository {
    private final Connection connection;
    public CategorieRepository() {
        this(DatabaseConnection.getInstance().getConnection());
    }
    public CategorieRepository(Connection connection) {
        this.connection = connection;
    }

    public int ajouter(Categorie categorie) {
        String sql = """
            INSERT INTO categorie
            (nom, type, id_utilisateur, est_commune)
            VALUES (?, ?, ?, ?)
            """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, categorie.getNom());
            statement.setString(2, categorie.getType().name());
            if (categorie.getIdUtilisateur() == 0) {
                statement.setNull(3, Types.INTEGER);
            } else {
                statement.setInt(3, categorie.getIdUtilisateur());
            }
            statement.setBoolean(4, categorie.isEstCommune());
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int modifier(Categorie categorie) {
        String sql = """
            UPDATE categorie
            SET nom=?,
                type=?
            WHERE id_categorie=?
            """;
        try (PreparedStatement statement =connection.prepareStatement(sql)) {
            statement.setString(1, categorie.getNom());
            statement.setString(2, categorie.getType().name());
            statement.setInt(3, categorie.getIdCategorie());
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int supprimer(int idCategorie) {
        String sql = """
            DELETE FROM categorie
            WHERE id_categorie=?
            """;
        try (PreparedStatement statement =connection.prepareStatement(sql)) {
            statement.setInt(1, idCategorie);
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Categorie rechercherParId(int idCategorie) {
        String sql = """
            SELECT *
            FROM categorie
            WHERE id_categorie=?
            """;
        try (PreparedStatement statement =connection.prepareStatement(sql)) {
            statement.setInt(1, idCategorie);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return construireCategorie(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Categorie> listerUtilisateur(int idUtilisateur) {
        List<Categorie> categories = new ArrayList<>();
        String sql = """
            SELECT *
            FROM categorie
            WHERE id_utilisateur=?
            ORDER BY nom
            """;
        try (PreparedStatement statement =connection.prepareStatement(sql)) {
            statement.setInt(1, idUtilisateur);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                categories.add(construireCategorie(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    public List<Categorie> listerCommunes() {
        List<Categorie> categories = new ArrayList<>();
        String sql = """
            SELECT *
            FROM categorie
            WHERE est_commune=true
            ORDER BY nom
            """;
        try (PreparedStatement statement =connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                categories.add(construireCategorie(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    private Categorie construireCategorie(ResultSet rs)throws SQLException {
        Categorie categorie = new Categorie();
        categorie.setIdCategorie(rs.getInt("id_categorie"));
        categorie.setNom(rs.getString("nom"));
        categorie.setType(TypeCategorie.valueOf(rs.getString("type")));
        categorie.setIdUtilisateur(rs.getInt("id_utilisateur"));
        categorie.setEstCommune(rs.getBoolean("est_commune"));
        return categorie;
    }

    public boolean existe(int idCategorie){
        String sql = """
            SELECT id_categorie
            FROM categorie
            WHERE id_categorie = ?
            """;
        try(PreparedStatement statement =connection.prepareStatement(sql)){
            statement.setInt(1,idCategorie);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}