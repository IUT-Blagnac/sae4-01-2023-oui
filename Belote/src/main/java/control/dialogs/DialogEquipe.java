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


/**
 * Cette classe permet de gérer les dialogues relatifs à la table Equipe de la base de données.
 */
public class DialogEquipe {

    /**
     * Acteur pour la table Equipe
     */
    private Actor actorEquipe;

    /**
     * Constructeur de la classe DialogEquipe.
     * Initialise l'acteur pour la table Equipe.
     */
    public DialogEquipe() {
        try {
            this.actorEquipe = ActorFactory.getActor(ActorType.EQUIPE);
        } catch (Exception e) {
            Fenetre.afficherErreur("Erreur lors de la récupération de l'acteur Equipe.");
            System.out.println(e.getMessage()); // Message développeur
        }
    }

    /**
     * Récupère les équipes d'un tournoi donné.
     * @param idTournoi : ID du tournoi.
     * @return Résultat de la requête SQL.
     * @throws Exception Si une erreur se produit lors de l'exécution de la requête.
     */
    public ResultSet getEquipeDUnTournoi(Integer idTournoi) throws Exception {
        Map<TableAttributType, String> parametresWhere = new HashMap<>();
        parametresWhere.put(TableAttributType.ID_TOURNOI, idTournoi + "");
        List<TableAttributType> orderingColumns = new ArrayList<>();
        orderingColumns.add(TableAttributType.NUM_EQUIPE);
        return this.actorEquipe.get(null, parametresWhere, orderingColumns);
    }

    /**
     * Récupère le numéro d'une équipe donnée.
     * @param idEquipe : ID de l'équipe.
     * @return Résultat de la requête SQL.
     * @throws Exception Si une erreur se produit lors de l'exécution de la requête.
     */
    public ResultSet getNumDUneEquipe(Integer idEquipe) throws Exception {
        List<TableAttributType> filtres = new ArrayList<>();
        filtres.add(TableAttributType.NUM_EQUIPE);
        Map<TableAttributType, String> parametresWhere = new HashMap<>();
        parametresWhere.put(TableAttributType.ID_EQUIPE, idEquipe + "");
        return this.actorEquipe.get(filtres, parametresWhere, null);
    }

    /**
     * Ajoute une nouvelle équipe dans la base de données.
     * @param idEquipe : ID de l'équipe.
     * @param numEquipe : numéro de l'équipe.
     * @param idTournoi : ID du tournoi auquel appartient l'équipe.
     * @param nomJ1 : nom du joueur 1 de l'équipe.
     * @param nomJ2 : nom du joueur 2 de l'équipe.
     * @throws Exception Si une erreur se produit lors de l'exécution de la requête.
     */
    public void addEquipe(Integer idEquipe, Integer numEquipe, Integer idTournoi, String nomJ1, String nomJ2) throws Exception {
        Map<TableAttributType, String> parametresValues = new HashMap<>();
        parametresValues.put(TableAttributType.ID_EQUIPE, idEquipe + "");
        parametresValues.put(TableAttributType.NUM_EQUIPE, numEquipe + "");
        parametresValues.put(TableAttributType.ID_TOURNOI, idTournoi + "");
        parametresValues.put(TableAttributType.NOM_J1, nomJ1);
        parametresValues.put(TableAttributType.NOM_J2, nomJ2);
        this.actorEquipe.add(parametresValues);
    }

    /**
     * Met à jour les noms des joueurs d'une équipe donnée.
     * @param idEquipe ID de l'équipe.
     * @param nomJ1 : nouveau nom du joueur 1.
     * @param nomJ2 : nouveau nom du joueur 2.
     * @throws Exception Si une erreur se produit lors de l'exécution de la requête.
     */
    public void setNomsJoueursDUneEquipe(Integer idEquipe, String nomJ1, String nomJ2) throws Exception {
        Map<TableAttributType, String> parametresValues = new HashMap<>();
        parametresValues.put(TableAttributType.NOM_J1, nomJ1);
        parametresValues.put(TableAttributType.NOM_J2, nomJ2);
        Map<TableAttributType, String> parametresWhere = new HashMap<>();
        parametresWhere.put(TableAttributType.ID_EQUIPE, idEquipe + "");
        this.actorEquipe.set(parametresValues, parametresWhere);
    }

    /**
     * Met à jour le numéro des équipes d'un tournoi donné.
     * @param idTournoi : ID du tournoi.
     * @param numEquipe : nouveau numéro d'équipe.
     * @throws Exception Si une erreur se produit lors de l'exécution de la requête.
     */
    public void setNumEquipesDUnTournoi(Integer idTournoi, Integer numEquipe) throws Exception {
        List<String> parametres = new ArrayList<>();
        parametres.add(idTournoi + "");
        parametres.add(numEquipe + "");
        this.actorEquipe.specialQuery(SpecialQueryType.SetNumEquipesDUnTournoi, QueryType.UPDATE, parametres);
    }

    /**
     * Supprime une équipe de la base de données.
     * @param idTournoi : ID du tournoi auquel appartient l'équipe.
     * @param idEquipe : ID de l'équipe à supprimer.
     * @throws Exception Si une erreur se produit lors de l'exécution de la requête.
     */
    public void removeUneEquipe(Integer idTournoi, Integer idEquipe) throws Exception {
        Map<TableAttributType, String> parametresWhere = new HashMap<>();
        parametresWhere.put(TableAttributType.ID_TOURNOI, idTournoi + "");
        parametresWhere.put(TableAttributType.ID_EQUIPE, idEquipe + "");
        this.actorEquipe.remove(parametresWhere);
    }

    /**
     * Supprime toutes les équipes d'un tournoi donné.
     * @param idTournoi : ID du tournoi.
     * @throws Exception Si une erreur se produit lors de l'exécution de la requête.
     */
    public void removeToutesEquipesDUnTournoi(Integer idTournoi) throws Exception {
        Map<TableAttributType, String> parametresWhere = new HashMap<>();
        parametresWhere.put(TableAttributType.ID_TOURNOI, idTournoi + "");
        this.actorEquipe.remove(parametresWhere);
    }

}
