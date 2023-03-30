package control.dialogs;


import control.actors.Actor;
import control.actors.ActorFactory;
import types.ActorType;
import view.Fenetre;

import java.sql.ResultSet;
import java.sql.SQLException;


public class DialogMatch  {

    private Actor actorMatch;

    public DialogMatch() {
        try {
            this.actorMatch = ActorFactory.getActor(ActorType.MATCH);
        } catch (Exception e) {
            Fenetre.afficherInformation("Erreur lors de la récupération de l'acteur Match.");
        }
    }

    // TODO : bonnes utilisations de actorMatch !

    public ResultSet getResultatsMatch(int idTournoi) throws SQLException {
        return this.actorMatch.getStatement().executeQuery(
                "SELECT equipe,(SELECT nom_j1 FROM equipes e WHERE e.id_equipe = equipe AND e.id_tournoi = " + idTournoi
                        + ") as joueur1,(SELECT nom_j2 FROM equipes e WHERE e.id_equipe = equipe AND e.id_tournoi = "
                        + idTournoi
                        + ") as joueur2, SUM(score) as score, (SELECT count(*) FROM matchs m WHERE (m.equipe1 = equipe AND m.score1 > m.score2  AND m.id_tournoi = id_tournoi) OR (m.equipe2 = equipe AND m.score2 > m.score1 )) as matchs_gagnes, (SELECT COUNT(*) FROM matchs m WHERE m.equipe1 = equipe OR m.equipe2=equipe) as matchs_joues FROM  (select equipe1 as equipe,score1 as score from matchs where id_tournoi="
                        + idTournoi + " UNION select equipe2 as equipe,score2 as score from matchs where id_tournoi="
                        + idTournoi + ") GROUP BY equipe ORDER BY matchs_gagnes DESC;");
    }

    public ResultSet getMatchsDataCount(int idTournoi) throws SQLException { // TODO : renommer correctement
        return this.actorMatch.getStatement().executeQuery("SELECT equipe, (SELECT count(*) FROM matchs m WHERE (m.equipe1 = equipe AND m.score1 > m.score2  AND m.id_tournoi = id_tournoi) OR (m.equipe2 = equipe AND m.score2 > m.score1 )) as matchs_gagnes FROM  (select equipe1 as equipe,score1 as score from matchs where id_tournoi=" + idTournoi + " UNION select equipe2 as equipe,score2 as score from matchs where id_tournoi=" + idTournoi + ") GROUP BY equipe  ORDER BY matchs_gagnes DESC;");
    }

    public ResultSet getNbMatchsParEquipes(int equipe1, int equipe2) throws SQLException {
        return this.actorMatch.getStatement().executeQuery("SELECT COUNT(*) FROM matchs m WHERE ( (m.equipe1 = " + equipe1 + " AND m.equipe2 = " + equipe2 + ") OR (m.equipe2 = " + equipe1 + " AND m.equipe1 = " + equipe2 + ")  )");
    }

    public void insertMatch(Integer idMatch, Integer idTournoi, Integer numTour, Integer equipe1, Integer equipe2, String termine) throws SQLException {
        this.actorMatch.getStatement().executeUpdate(
                "INSERT INTO matchs ( id_match, id_tournoi, num_tour, equipe1, equipe2, termine ) VALUES ("
                        + (idMatch != null ? idMatch : "NULL") + ", " + idTournoi + ", " + numTour + ", " + equipe1
                        + ", " + equipe2 + ", '" + termine + "')");
    }

    public void updateMatch(int idMatch, Integer equipe1, Integer equipe2, Integer scoreEq1, Integer scoreEq2, String termine) throws SQLException {
        this.actorMatch.getStatement().executeUpdate("UPDATE matchs SET equipe1=" + equipe1 + ", equipe2=" + equipe2 + ",  score1="
                + scoreEq1 + ",  score2=" + scoreEq2 + ", termine='" + termine + "' WHERE id_match = " + idMatch);
    }

    public void deleteMatch(int idTournoi, int numTour) throws SQLException {
        this.actorMatch.getStatement().executeUpdate("DELETE FROM matchs WHERE id_tournoi=" + idTournoi + " AND num_tour=" + numTour);
    }

    public void deleteMatch(int idTournoi) throws SQLException {
        this.actorMatch.getStatement().executeUpdate("DELETE FROM matchs WHERE id_tournoi=" + idTournoi);
    }

    public ResultSet getNbMatchsTerminesParTournois(int idTournoi) throws SQLException {
        return this.actorMatch.getStatement().executeQuery(
                "Select count(*) as total, (Select count(*) from matchs m2  WHERE m2.id_tournoi = m.id_tournoi  AND m2.termine='oui' ) as termines from matchs m  WHERE m.id_tournoi="
                        + idTournoi + " GROUP by id_tournoi ;");
    }

    public ResultSet getMatchsParTournoi(int idTournoi) throws SQLException {
        return this.actorMatch.getStatement().executeQuery("SELECT * FROM matchs WHERE id_tournoi=" + idTournoi + ";");
    }

    public ResultSet getNbToursMaxMatchParTournoi(int idTournoi) throws SQLException {
        return this.actorMatch.getStatement().executeQuery("SELECT MAX (num_tour)  FROM matchs WHERE id_tournoi=" + idTournoi + "; ");
    }

    public ResultSet getNbToursParMatchParTournoi(int idTournoi) throws SQLException {
        return this.actorMatch.getStatement().executeQuery("Select num_tour,count(*) as tmatchs, (Select count(*) from matchs m2 WHERE m2.id_tournoi = m.id_tournoi AND m2.num_tour=m.num_tour AND m2.termine='oui' ) as termines from matchs m WHERE m.id_tournoi=" + idTournoi + " GROUP BY m.num_tour,m.id_tournoi;");
    }

}
