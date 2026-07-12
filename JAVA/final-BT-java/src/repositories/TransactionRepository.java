package repositories;

import config.DatabaseConnection;
import models.Transaction;
import models.TypeCategorie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {

    private final Connection connection;

    public TransactionRepository() {
        this(DatabaseConnection.getInstance().getConnection());
    }

    public TransactionRepository(Connection connection) {
        this.connection = connection;
    }
    public int ajouter(Transaction transaction) {

        String sql = """
                INSERT INTO "transaction"
                (
                    montant,
                    date_transaction,
                    type,
                    description,
                    id_utilisateur,
                    id_categorie
                )
                VALUES (?,?,?,?,?,?)
                """;

        try (PreparedStatement statement =connection.prepareStatement(sql)) {
            statement.setBigDecimal(1, transaction.getMontant());
            statement.setDate( 2,Date.valueOf(transaction.getDateTransaction()));
            statement.setString(3,transaction.getType().name());
            statement.setString(4,transaction.getDescription());
            statement.setInt(5,transaction.getIdUtilisateur());
            statement.setInt(6,transaction.getIdCategorie());
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;

    }

    public int modifier(Transaction transaction) {

        String sql = """
                UPDATE "transaction"
                SET montant = ?,
                    date_transaction = ?,
                    type = ?,
                    description = ?,
                    id_categorie = ?
                WHERE id_transaction = ?
                """;

        try (PreparedStatement statement =connection.prepareStatement(sql)) {
            statement.setBigDecimal(1,transaction.getMontant());
            statement.setDate(2,Date.valueOf(transaction.getDateTransaction()));
            statement.setString(3,transaction.getType().name());
            statement.setString(4,transaction.getDescription());
            statement.setInt(5,transaction.getIdCategorie());
            statement.setInt(6,transaction.getIdTransaction());
            return statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;

    }
    public int supprimer(int idTransaction) {

        String sql = """
                DELETE FROM "transaction"
                WHERE id_transaction = ?
                """;

        try (PreparedStatement statement =connection.prepareStatement(sql)) {
            statement.setInt(1, idTransaction);
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public Transaction rechercherParId(int idTransaction) {

        String sql = """
                SELECT
                    t.*,
                    c.nom AS nom_categorie
                FROM "transaction" t
                INNER JOIN categorie c
                    ON c.id_categorie = t.id_categorie
                WHERE t.id_transaction = ?
                """;

        try (PreparedStatement statement =connection.prepareStatement(sql)) {
            statement.setInt(1, idTransaction);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return construireTransaction(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }
    public List<Transaction> listerParUtilisateur(int idUtilisateur) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = """
                SELECT
                    t.*,
                    c.nom AS nom_categorie
                FROM "transaction" t
                INNER JOIN categorie c
                    ON c.id_categorie = t.id_categorie
                WHERE t.id_utilisateur = ?
                ORDER BY t.date_transaction DESC
                """;

        try (PreparedStatement statement =connection.prepareStatement(sql)) {
            statement.setInt(1, idUtilisateur);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                transactions.add(construireTransaction(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }
    public List<Transaction> listerParCategorie(int idCategorie) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = """
                SELECT
                    t.*,
                    c.nom AS nom_categorie
                FROM "transaction" t
                INNER JOIN categorie c
                    ON c.id_categorie = t.id_categorie
                WHERE t.id_categorie = ?
                ORDER BY t.date_transaction DESC
                """;

        try (PreparedStatement statement =connection.prepareStatement(sql)) {
            statement.setInt(1, idCategorie);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                transactions.add(construireTransaction(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }
    private Transaction construireTransaction(ResultSet rs)throws SQLException {

        Transaction transaction = new Transaction();
        transaction.setIdTransaction(rs.getInt("id_transaction"));
        transaction.setMontant(rs.getBigDecimal("montant"));
        transaction.setDateTransaction(rs.getDate("date_transaction").toLocalDate());
        transaction.setType( TypeCategorie.valueOf(rs.getString("type")));
        transaction.setDescription(rs.getString("description"));
        transaction.setIdUtilisateur(rs.getInt("id_utilisateur"));
        transaction.setIdCategorie(rs.getInt("id_categorie"));
        transaction.setNomCategorie(rs.getString("nom_categorie"));
        Timestamp timestamp =rs.getTimestamp("date_creation");
        if (timestamp != null) {
            transaction.setDateCreation(timestamp.toLocalDateTime());
        }
        return transaction;
    }
}