package repositories;

import config.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DashboardRepository {

    private final Connection connection;

    public DashboardRepository() {
        connection = DatabaseConnection
                .getInstance()
                .getConnection();
    }

    public BigDecimal getRevenus(int idUtilisateur) {

        String sql = """
            SELECT COALESCE(SUM(montant),0)
            FROM transaction
            WHERE id_utilisateur=?
            AND type='REVENU'
            AND DATE_TRUNC('month',date_transaction)
            =DATE_TRUNC('month',CURRENT_DATE)
            """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1,idUtilisateur);

            ResultSet rs = statement.executeQuery();

            if(rs.next())
                return rs.getBigDecimal(1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return BigDecimal.ZERO;
    }

    public BigDecimal getDepenses(int idUtilisateur){

        String sql="""
            SELECT COALESCE(SUM(montant),0)
            FROM transaction
            WHERE id_utilisateur=?
            AND type='DEPENSE'
            AND DATE_TRUNC('month',date_transaction)
            =DATE_TRUNC('month',CURRENT_DATE)
            """;

        try(PreparedStatement statement=connection.prepareStatement(sql)){

            statement.setInt(1,idUtilisateur);

            ResultSet rs=statement.executeQuery();

            if(rs.next())
                return rs.getBigDecimal(1);

        }catch(Exception e){
            e.printStackTrace();
        }

        return BigDecimal.ZERO;
    }

}