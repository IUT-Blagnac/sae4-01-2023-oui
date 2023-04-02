package control.dialogs;


import control.actors.Actor;
import control.actors.ActorFactory;
import types.ActorType;
import types.QueryType;
import types.SpecialQueryType;
import types.TableAttributType;
import view.Fenetre;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public ResultSet getResultatsMatch(int idTournoi) throws Exception {
        List<String> parametres = new ArrayList<>();
        parametres.add(idTournoi+"");
        return this.actorMatch.specialQuery(SpecialQueryType.GetResultatsMatch, QueryType.QUERY, parametres);
    }

    public ResultSet getDonneesTours(int idTournoi) throws Exception {
        List<String> parametres = new ArrayList<>();
        parametres.add(idTournoi+"");
        return this.actorMatch.specialQuery(SpecialQueryType.GetDonneesTours, QueryType.QUERY, parametres);
    }

    public ResultSet getNbMatchsParEquipes(int equipe1, int equipe2) throws Exception {
        List<String> parametres = new ArrayList<>();
        parametres.add(equipe1+"");
        parametres.add(equipe2+"");
        return this.actorMatch.specialQuery(SpecialQueryType.GetNbMatchsParEquipes, QueryType.QUERY, parametres);
    }

    public ResultSet getNbMatchsTermines(int idTournoi) throws Exception {
        List<String> parametres = new ArrayList<>();
        parametres.add(idTournoi+"");
        return this.actorMatch.specialQuery(SpecialQueryType.GetNbMatchsTermines, QueryType.QUERY, parametres);
    }

    public ResultSet getNbToursMaxMatch(int idTournoi) throws Exception {
        List<String> parametres = new ArrayList<>();
        parametres.add(idTournoi+"");
        return this.actorMatch.specialQuery(SpecialQueryType.GetNbToursMaxMatch, QueryType.QUERY, parametres);
    }

    public ResultSet getNbToursParMatch(int idTournoi) throws Exception {
        List<String> parametres = new ArrayList<>();
        parametres.add(idTournoi+"");
        return this.actorMatch.specialQuery(SpecialQueryType.GetNbToursParMatchParTournoi, QueryType.QUERY, parametres);
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
