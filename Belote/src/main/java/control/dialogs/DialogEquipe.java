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
            Fenetre.afficherErreur("Erreur lors de la récupération de l'acteur Equipe.");
            System.out.println(e.getMessage()); // Message développeur
        }
    }

    public ResultSet getEquipeDUnTournoi(Integer idTournoi) throws Exception {
        Map<TableAttributType, String> parametresWhere = new HashMap<>();
        parametresWhere.put(TableAttributType.ID_TOURNOI, idTournoi+"");
        List<TableAttributType> orderingColumns = new ArrayList<>();
        orderingColumns.add(TableAttributType.NUM_EQUIPE);
        return this.actorEquipe.get(null, parametresWhere, orderingColumns);
    }

    public ResultSet getNumDUneEquipe(Integer idEquipe) throws Exception {
        List<TableAttributType> filtres = new ArrayList<>();
        filtres.add(TableAttributType.NUM_EQUIPE);
        Map<TableAttributType, String> parametresWhere = new HashMap<>();
        parametresWhere.put(TableAttributType.ID_EQUIPE, idEquipe+"");
        return this.actorEquipe.get(filtres, parametresWhere, null);
    }

    public void addEquipe(Integer idEquipe, Integer numEquipe, Integer idTournoi, String nomJ1, String nomJ2) throws Exception {
        Map<TableAttributType, String> parametresValues = new HashMap<>();
        parametresValues.put(TableAttributType.ID_EQUIPE, idEquipe+"");
        parametresValues.put(TableAttributType.NUM_EQUIPE, numEquipe+"");
        parametresValues.put(TableAttributType.ID_TOURNOI, idTournoi+"");
        parametresValues.put(TableAttributType.NOM_J1, nomJ1);
        parametresValues.put(TableAttributType.NOM_J2, nomJ2);
        this.actorEquipe.add(parametresValues);
    }

    public void setNomsJoueursDUneEquipe(Integer idEquipe, String nomJ1, String nomJ2) throws Exception {
        Map<TableAttributType, String> parametresValues = new HashMap<>();
        parametresValues.put(TableAttributType.NOM_J1, nomJ1);
        parametresValues.put(TableAttributType.NOM_J2, nomJ2);
        Map<TableAttributType, String> parametresWhere = new HashMap<>();
        parametresWhere.put(TableAttributType.ID_EQUIPE, idEquipe+"");
        this.actorEquipe.set(parametresValues, parametresWhere);
    }

    public void setNumEquipesDUnTournoi(Integer idTournoi, Integer numEquipe) throws Exception {
        List<String> parametres = new ArrayList<>();
        parametres.add(idTournoi+"");
        parametres.add(numEquipe+"");
        this.actorEquipe.specialQuery(SpecialQueryType.SetNumEquipesDUnTournoi, QueryType.UPDATE, parametres);
    }

    public void removeUneEquipe(Integer idTournoi, Integer idEquipe) throws Exception {
        Map<TableAttributType, String> parametresWhere = new HashMap<>();
        parametresWhere.put(TableAttributType.ID_TOURNOI, idTournoi+"");
        parametresWhere.put(TableAttributType.ID_EQUIPE, idEquipe+"");
        this.actorEquipe.remove(parametresWhere);
    }

    public void removeToutesEquipesDUnTournoi(Integer idTournoi) throws Exception {
        Map<TableAttributType, String> parametresWhere = new HashMap<>();
        parametresWhere.put(TableAttributType.ID_TOURNOI, idTournoi+"");
        this.actorEquipe.remove(parametresWhere);
    }

}
