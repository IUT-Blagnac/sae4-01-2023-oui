package fr.blagnac.com.control.actors;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import fr.blagnac.com.control.dialogs.DialogDataBase;


public abstract class ActorEquipe {

    private Statement ddbStatement;

    public ActorEquipe() {
        // TODO A changer en un throw exception
        try {
            this.ddbStatement = DialogDataBase.getStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet getEquipesParTournoi(int idTournoi) throws SQLException {
        return this.ddbStatement.executeQuery("SELECT * FROM equipes WHERE id_tournoi = " + idTournoi + " ORDER BY num_equipe;");
    }

    public ResultSet getEquipe(int idEquipe) throws SQLException {
        return this.ddbStatement.executeQuery("SELECT num_equipe FROM equipes WHERE id_equipe = " + idEquipe);
    }

    public void insertEquipe(Integer idEquipe, Integer numEquipe, Integer idTournoi, String nomJ1, String nomJ2) throws SQLException {
        String sql = "INSERT INTO equipes (id_equipe,num_equipe,id_tournoi,nom_j1,nom_j2) VALUES ("
                + (idEquipe != null ? idEquipe : "NULL") + ", " + numEquipe + ", " + idTournoi + ", '" + nomJ1 + "', '"
                + nomJ2 + "')";
        this.ddbStatement.executeUpdate(sql);
    }

    public void setNomsJoueursEquipe(int idEquipe, String nomJ1, String nomJ2) throws SQLException {
        this.ddbStatement.executeUpdate("UPDATE equipes SET nom_j1 = '" + nomJ1 + "', nom_j2 = '" + nomJ2 + "' WHERE id_equipe = " + idEquipe);
    }

    public void setNumEquipe(int idTournoi, int numEquipe) throws SQLException {
        this.ddbStatement.executeUpdate("UPDATE equipes SET num_equipe = num_equipe - 1 WHERE id_tournoi = " + idTournoi + " AND num_equipe > " + numEquipe);
    }

    public void deleteEquipe(int idTournoi, int idEquipe) throws SQLException {
        this.ddbStatement.executeUpdate("DELETE FROM equipes WHERE id_tournoi=" + idTournoi + " AND id_equipe=" + idEquipe);
    }

    public void deleteEquipe(int idTournoi) throws SQLException {
        this.ddbStatement.executeUpdate("DELETE FROM equipes WHERE id_tournoi=" + idTournoi);
    }

}
