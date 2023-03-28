package fr.blagnac.com.control.actors;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import fr.blagnac.com.control.dialogs.DialogDataBase;


public abstract class ActorMatch {

    private Statement ddbStatement;

    public ActorMatch() {
        // TODO A changer en un throw exception
        try {
            this.ddbStatement = DialogDataBase.getStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet getResultatsMatch(int idTournoi) throws SQLException {
        return this.ddbStatement.executeQuery(
                "SELECT equipe,(SELECT nom_j1 FROM equipes e WHERE e.id_equipe = equipe AND e.id_tournoi = " + idTournoi
                        + ") as joueur1,(SELECT nom_j2 FROM equipes e WHERE e.id_equipe = equipe AND e.id_tournoi = "
                        + idTournoi
                        + ") as joueur2, SUM(score) as score, (SELECT count(*) FROM matchs m WHERE (m.equipe1 = equipe AND m.score1 > m.score2  AND m.id_tournoi = id_tournoi) OR (m.equipe2 = equipe AND m.score2 > m.score1 )) as matchs_gagnes, (SELECT COUNT(*) FROM matchs m WHERE m.equipe1 = equipe OR m.equipe2=equipe) as matchs_joues FROM  (select equipe1 as equipe,score1 as score from matchs where id_tournoi="
                        + idTournoi + " UNION select equipe2 as equipe,score2 as score from matchs where id_tournoi="
                        + idTournoi + ") GROUP BY equipe ORDER BY matchs_gagnes DESC;");
    }

    public ResultSet getMatchsDataCount(int idTournoi) throws SQLException { // TODO : renommer correctement
        return this.ddbStatement.executeQuery("SELECT equipe, (SELECT count(*) FROM matchs m WHERE (m.equipe1 = equipe AND m.score1 > m.score2  AND m.id_tournoi = id_tournoi) OR (m.equipe2 = equipe AND m.score2 > m.score1 )) as matchs_gagnes FROM  (select equipe1 as equipe,score1 as score from matchs where id_tournoi=" + idTournoi + " UNION select equipe2 as equipe,score2 as score from matchs where id_tournoi=" + idTournoi + ") GROUP BY equipe  ORDER BY matchs_gagnes DESC;");
    }

    public ResultSet getNbMatchsParEquipes(int equipe1, int equipe2) throws SQLException {
        return this.ddbStatement.executeQuery("SELECT COUNT(*) FROM matchs m WHERE ( (m.equipe1 = " + equipe1 + " AND m.equipe2 = " + equipe2 + ") OR (m.equipe2 = " + equipe1 + " AND m.equipe1 = " + equipe2 + ")  )");
    }

    public void insertMatch(Integer idMatch, Integer idTournoi, Integer numTour, Integer equipe1, Integer equipe2, String termine) throws SQLException {
        this.ddbStatement.executeUpdate(
                "INSERT INTO matchs ( id_match, id_tournoi, num_tour, equipe1, equipe2, termine ) VALUES ("
                + (idMatch != null ? idMatch : "NULL") + ", " + idTournoi + ", " + numTour + ", " + equipe1
                + ", " + equipe2 + ", '" + termine + "')");
    }

    public void updateMatch(int idMatch, Integer equipe1, Integer equipe2, Integer scoreEq1, Integer scoreEq2, String termine) throws SQLException {
        this.ddbStatement.executeUpdate("UPDATE matchs SET equipe1=" + equipe1 + ", equipe2=" + equipe2 + ",  score1="
                + scoreEq1 + ",  score2=" + scoreEq2 + ", termine='" + termine + "' WHERE id_match = " + idMatch);
    }

    public void deleteMatch(int idTournoi, int numTour) throws SQLException {
        this.ddbStatement.executeUpdate("DELETE FROM matchs WHERE id_tournoi=" + idTournoi + " AND num_tour=" + numTour);
    }

    public void deleteMatch(int idTournoi) throws SQLException {
        this.ddbStatement.executeUpdate("DELETE FROM matchs WHERE id_tournoi=" + idTournoi);
    }

}
