package control.dialogs;


import control.actors.Actor;
import control.actors.ActorFactory;
import types.ActorType;
import types.QueryType;
import types.SpecialQueryType;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DialogEquipe {

    private final Actor actorEquipe;

    public DialogEquipe() {
        this.actorEquipe = ActorFactory.getActor(ActorType.EQUIPE);
    }

    public ResultSet getEquipeDUnTournoi(int idEquipe) throws Exception {
        List<String> filtres = new ArrayList<>();
        Map<String, String> parametresWhere = new HashMap<>();
        parametresWhere.put("id_equipe", ""+idEquipe);
        return this.actorEquipe.get(filtres, parametresWhere);
    }

    public ResultSet getNumDUneEquipe(int idEquipe) throws Exception {
        List<String> filtres = new ArrayList<>();
        filtres.add("num_equipe");
        Map<String, String> parametresWhere = new HashMap<>();
        parametresWhere.put("id_equipe", ""+idEquipe);
        return this.actorEquipe.get(filtres, parametresWhere);
    }

    public void addEquipe(Integer idEquipe, Integer numEquipe, Integer idTournoi, String nomJ1, String nomJ2) throws Exception {
        Map<String, String> parametresValues = new HashMap<>();
        parametresValues.put("id_equipe", ""+idEquipe);
        parametresValues.put("num_equipe", ""+numEquipe);
        parametresValues.put("id_tournoi", ""+idTournoi);
        parametresValues.put("nom_j1", "'"+nomJ1+"'"); // TODO : déplacer prise en compte des ''
        parametresValues.put("nom_j2", "'"+nomJ2+"'");
        this.actorEquipe.add(parametresValues);
    }

    public void setNomsJoueursDUneEquipe(int idEquipe, String nomJ1, String nomJ2) throws Exception {
        Map<String, String> parametresValues = new HashMap<>();
        parametresValues.put("nom_j1", "'"+nomJ1+"'"); // TODO : déplacer prise en compte des ''
        parametresValues.put("nom_j2", "'"+nomJ2+"'");
        Map<String, String> parametresWhere = new HashMap<>();
        parametresWhere.put("id_equipe", ""+idEquipe);
        this.actorEquipe.set(parametresValues, parametresWhere);
    }

    public void setNumEquipesDUnTournoi(int idTournoi, int numEquipe) throws Exception {
        List<String> parametres = new ArrayList<>();
        parametres.add(""+idTournoi);
        parametres.add(""+numEquipe);
        this.actorEquipe.specialQuery(SpecialQueryType.SetNumEquipesDUnTournoi, QueryType.UPDATE, parametres);
    }

    public void removeUneEquipe(int idTournoi, int idEquipe) throws Exception {
        Map<String, String> parametresWhere = new HashMap<>();
        parametresWhere.put("id_tournoi", ""+idTournoi);
        parametresWhere.put("id_equipe", ""+idEquipe);
        this.actorEquipe.remove(parametresWhere);
    }

    public void removeToutesEquipesDUnTournoi(int idTournoi) throws Exception {
        Map<String, String> parametresWhere = new HashMap<>();
        parametresWhere.put("id_tournoi", ""+idTournoi);
        this.actorEquipe.remove(parametresWhere);
    }

}
