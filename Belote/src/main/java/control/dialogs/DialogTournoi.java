package control.dialogs;


import control.actors.Actor;
import control.actors.ActorFactory;
import resources.Tools;
import types.ActorType;
import view.Fenetre;
import types.StatutTournoi;
import types.TableAttributType;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class DialogTournoi {

    private Actor actorTournoi;
    private DialogMatch dialogMatch;
    private DialogEquipe dialogEquipe;

    public DialogTournoi() {
        try {
            this.dialogMatch = new DialogMatch();
            this.dialogEquipe = new DialogEquipe();
            this.actorTournoi = ActorFactory.getActor(ActorType.TOURNOI);
        } catch (Exception e) {
            Fenetre.afficherErreur("Erreur lors de la récupération de l'acteur Tournoi.");
        }
    }

    public ResultSet getTournoiParNom(String nomTournois) throws Exception {
        Map<TableAttributType, String> parametresWhere = new HashMap<>();
        parametresWhere.put(TableAttributType.NOM_TOURNOI, nomTournois);
        return this.actorTournoi.get(null, parametresWhere, null);
    }

    public ResultSet getTousLesTournois() throws Exception {
        return this.actorTournoi.get(null, null, null);
    }

    public void addTournoi(Integer idTournoi, Integer nbMatchs, String nomTournoi, StatutTournoi statut) throws Exception {
        Map<TableAttributType, String> parametresValues = new HashMap<>();
        parametresValues.put(TableAttributType.ID_TOURNOI, idTournoi+"");
        parametresValues.put(TableAttributType.NB_MATCHS, nbMatchs+"");
        parametresValues.put(TableAttributType.NOM_TOURNOI, nomTournoi);
        parametresValues.put(TableAttributType.STATUT, statut.getOrdre()+"");
        this.actorTournoi.add(parametresValues);
    }

    public void setStatutTournoi(StatutTournoi statut, Integer idTournoi) throws Exception {
        Map<TableAttributType, String> parametresValues = new HashMap<>();
        parametresValues.put(TableAttributType.STATUT, statut.getOrdre()+"");
        Map<TableAttributType, String> parametresWhere = new HashMap<>();
        parametresWhere.put(TableAttributType.ID_TOURNOI, idTournoi+"");
        this.actorTournoi.set(parametresValues, parametresWhere);
    }

    public void removeUnTournoi(Integer idTournoi) throws Exception {
        Map<TableAttributType, String> parametresWhere = new HashMap<>();
        parametresWhere.put(TableAttributType.ID_TOURNOI, idTournoi+"");
        this.actorTournoi.remove(parametresWhere);
    }

    public void creerTournoi(){
        String nomTournoi = Fenetre.saisieNomTournoi();
        if(nomTournoi == null || nomTournoi.equals("")){
            return;
        }
        nomTournoi = Tools.mysql_real_escape_string(nomTournoi);
        if(nomTournoi.length() < 3){
            Fenetre.afficherErreur("Erreur lors de la création d'un tournoi, le nom donné est trop court.");
            return;
        }
        if(Objects.equals(nomTournoi, "")){
            Fenetre.afficherErreur("Erreur lors de la création d'un tournoi, le nom donné ne peut pas contenir de caractères spéciaux ni de caractères accentués.");
        }else{
            ResultSet rs;
            try {
                rs = this.getTournoiParNom(nomTournoi);
                if(rs.next()){
                    Fenetre.afficherErreur("Erreur lors de la création d'un tournoi, un tournoi du même nom existe déjà.");
                    return;
                }
                this.addTournoi(null, 10, nomTournoi, StatutTournoi.INSCRIPTION);
            } catch (Exception e) {
                Fenetre.afficherErreur("Erreur lors de l'ajout du tournoi.");
                System.out.println(e.getMessage()); // Message développeur
            }
        }
    }

    public void supprimerTournoi(String nomTournoi) {
        try {
            int idTournoi;
            ResultSet rs = this.getTournoiParNom(nomTournoi);
            rs.next();
            idTournoi = rs.getInt(TableAttributType.ID_TOURNOI.getColumnName());
            rs.close();
            this.dialogMatch.removeMatchsDUnTournoi(idTournoi);
            this.dialogEquipe.removeToutesEquipesDUnTournoi(idTournoi);
            this.removeUnTournoi(idTournoi);
        } catch (Exception e) {
            Fenetre.afficherErreur("Erreur lors de la suppression du tournoi.");
        }
    }

}
