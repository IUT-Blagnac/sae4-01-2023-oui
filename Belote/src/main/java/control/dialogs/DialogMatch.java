package control.dialogs;


import control.actors.Actor;
import control.actors.ActorFactory;
import types.ActorType;
import types.TableAttributType;
import view.Fenetre;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class DialogMatch  {

    private Actor actorMatch;

    public DialogMatch() {
        try {
            this.actorMatch = ActorFactory.getActor(ActorType.MATCH);
        } catch (Exception e) {
            Fenetre.afficherErreur("Erreur lors de la récupération de l'acteur Match.");
        }
    }

    // TODO : faire requêtes spéciales
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
    public ResultSet getNbMatchsTerminesParTournois(int idTournoi) throws SQLException {
        return this.actorMatch.getStatement().executeQuery(
                "Select count(*) as total, (Select count(*) from matchs m2  WHERE m2.id_tournoi = m.id_tournoi  AND m2.termine='oui' ) as termines from matchs m  WHERE m.id_tournoi="
                        + idTournoi + " GROUP by id_tournoi ;");
    }
    public ResultSet getNbToursMaxMatchParTournoi(int idTournoi) throws SQLException {
        return this.actorMatch.getStatement().executeQuery("SELECT MAX (num_tour)  FROM matchs WHERE id_tournoi=" + idTournoi + "; ");
    }
    public ResultSet getNbToursParMatchParTournoi(int idTournoi) throws SQLException {
        return this.actorMatch.getStatement().executeQuery("Select num_tour,count(*) as tmatchs, (Select count(*) from matchs m2 WHERE m2.id_tournoi = m.id_tournoi AND m2.num_tour=m.num_tour AND m2.termine='oui' ) as termines from matchs m WHERE m.id_tournoi=" + idTournoi + " GROUP BY m.num_tour,m.id_tournoi;");
    }

    public ResultSet getMatchsDUnTournoi(int idTournoi) throws Exception {
        Map<TableAttributType, String> parametresWhere = new HashMap<>();
        parametresWhere.put(TableAttributType.ID_TOURNOI, idTournoi+"");
        return this.actorMatch.get(null, parametresWhere, null);
    }

    public void addMatch(Integer idMatch, Integer idTournoi, Integer numTour, Integer equipe1, Integer equipe2, String termine) throws Exception {
        Map<TableAttributType, String> parametresValues = new HashMap<>();
        parametresValues.put(TableAttributType.ID_MATCH, idMatch+"");
        parametresValues.put(TableAttributType.ID_TOURNOI, idTournoi+"");
        parametresValues.put(TableAttributType.NUM_TOUR, numTour+"");
        parametresValues.put(TableAttributType.EQUIPE1, equipe1+"");
        parametresValues.put(TableAttributType.EQUIPE2, equipe2+"");
        parametresValues.put(TableAttributType.TERMINE, termine+"");
        this.actorMatch.add(parametresValues);
    }

    public void setScoresMatch(int idMatch, Integer equipe1, Integer equipe2, Integer scoreEq1, Integer scoreEq2, String termine) throws Exception {
        Map<TableAttributType, String> parametresValues = new HashMap<>();
        parametresValues.put(TableAttributType.ID_MATCH, idMatch+"");
        parametresValues.put(TableAttributType.EQUIPE1, equipe1+"");
        parametresValues.put(TableAttributType.EQUIPE2, equipe2+"");
        parametresValues.put(TableAttributType.SCORE1, scoreEq1+"");
        parametresValues.put(TableAttributType.SCORE2, scoreEq2+"");
        parametresValues.put(TableAttributType.TERMINE, termine+"");
        Map<TableAttributType, String> parametresWhere = new HashMap<>();
        parametresWhere.put(TableAttributType.ID_MATCH, idMatch+"");
        this.actorMatch.set(parametresValues, parametresWhere);
    }

    public void removeMatchDUnTour(int idTournoi, int numTour) throws Exception {
        Map<TableAttributType, String> parametresWhere = new HashMap<>();
        parametresWhere.put(TableAttributType.ID_TOURNOI, idTournoi+"");
        parametresWhere.put(TableAttributType.NUM_TOUR, numTour+"");
        this.actorMatch.remove(parametresWhere);
    }

    public void removeMatchsDUnTournoi(int idTournoi) throws Exception {
        Map<TableAttributType, String> parametresWhere = new HashMap<>();
        parametresWhere.put(TableAttributType.ID_TOURNOI, idTournoi+"");
        this.actorMatch.remove(parametresWhere);
    }

}
