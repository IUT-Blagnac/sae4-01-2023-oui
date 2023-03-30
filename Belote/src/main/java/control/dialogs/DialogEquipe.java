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


public class DialogEquipe {

    private Actor actorEquipe;

    public DialogEquipe() {
        try {
            this.actorEquipe = ActorFactory.getActor(ActorType.EQUIPE);
        } catch (Exception e) {
            Fenetre.afficherInformation("Erreur lors de la récupération de l'acteur Equipe.");
        }
    }

    public ResultSet getEquipeDUnTournoi(Integer idTournoi) throws Exception {
        Map<TableAttributType, String> parametresWhere = new HashMap<>();
        parametresWhere.put(TableAttributType.ID_TOURNOI, idTournoi.toString());
        return this.actorEquipe.get(null, parametresWhere);
    }

    public ResultSet getNumDUneEquipe(Integer idEquipe) throws Exception {
        List<TableAttributType> filtres = new ArrayList<>();
        filtres.add(TableAttributType.NUM_EQUIPE);
        Map<TableAttributType, String> parametresWhere = new HashMap<>();
        parametresWhere.put(TableAttributType.ID_EQUIPE, idEquipe.toString());
        return this.actorEquipe.get(filtres, parametresWhere);
    }

    public void addEquipe(Integer idEquipe, Integer numEquipe, Integer idTournoi, String nomJ1, String nomJ2) throws Exception {
        Map<TableAttributType, String> parametresValues = new HashMap<>();
        parametresValues.put(TableAttributType.ID_EQUIPE, idEquipe.toString());
        parametresValues.put(TableAttributType.NUM_EQUIPE, numEquipe.toString());
        parametresValues.put(TableAttributType.ID_TOURNOI, idTournoi.toString());
        parametresValues.put(TableAttributType.NOM_J1, "'"+nomJ1+"'"); // TODO : déplacer prise en compte des ''
        parametresValues.put(TableAttributType.NOM_J2, "'"+nomJ2+"'");
        this.actorEquipe.add(parametresValues);
    }

    public void setNomsJoueursDUneEquipe(Integer idEquipe, String nomJ1, String nomJ2) throws Exception {
        Map<TableAttributType, String> parametresValues = new HashMap<>();
        parametresValues.put(TableAttributType.NOM_J1, "'"+nomJ1+"'"); // TODO : déplacer prise en compte des ''
        parametresValues.put(TableAttributType.NOM_J2, "'"+nomJ2+"'");
        Map<TableAttributType, String> parametresWhere = new HashMap<>();
        parametresWhere.put(TableAttributType.ID_EQUIPE, idEquipe.toString());
        this.actorEquipe.set(parametresValues, parametresWhere);
    }

    public void setNumEquipesDUnTournoi(Integer idTournoi, Integer numEquipe) throws Exception {
        List<String> parametres = new ArrayList<>();
        parametres.add(idTournoi.toString());
        parametres.add(numEquipe.toString());
        this.actorEquipe.specialQuery(SpecialQueryType.SetNumEquipesDUnTournoi, QueryType.UPDATE, parametres);
    }

    public void removeUneEquipe(Integer idTournoi, Integer idEquipe) throws Exception {
        Map<TableAttributType, String> parametresWhere = new HashMap<>();
        parametresWhere.put(TableAttributType.ID_TOURNOI, idTournoi.toString());
        parametresWhere.put(TableAttributType.ID_EQUIPE, idEquipe.toString());
        this.actorEquipe.remove(parametresWhere);
    }

    public void removeToutesEquipesDUnTournoi(Integer idTournoi) throws Exception {
        Map<TableAttributType, String> parametresWhere = new HashMap<>();
        parametresWhere.put(TableAttributType.ID_TOURNOI, idTournoi.toString());
        this.actorEquipe.remove(parametresWhere);
    }

}
