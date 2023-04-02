/**
 * Cette classe gère les dialogues relatifs aux matches.
 */

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

public class DialogMatch {

    /**
     * Acteur pour la table Match.
     */
    private Actor actorMatch;

    /**
     * Constructeur de la classe DialogMatch.
     * Il récupère l'acteur Match avec ActorFactory et gère les erreurs.
     */
    public DialogMatch() {
        try {
            this.actorMatch = ActorFactory.getActor(ActorType.MATCH);
        } catch (Exception e) {
            Fenetre.afficherErreur("Erreur lors de la récupération de l'acteur Match.");
        }
    }

    /**
     * Cette méthode retourne les résultats d'un match pour un tournoi donné.
     *
     * @param idTournoi l'identifiant du tournoi
     * @return un objet ResultSet contenant les résultats du match
     * @throws Exception si une erreur se produit
     */
    public ResultSet getResultatsMatch(int idTournoi) throws Exception {
        List<String> parametres = new ArrayList<>();
        parametres.add(idTournoi + "");
        return this.actorMatch.specialQuery(SpecialQueryType.GetResultatsMatch, QueryType.QUERY, parametres);
    }

    /**
     * Cette méthode retourne les données des tours pour un tournoi donné.
     *
     * @param idTournoi l'identifiant du tournoi
     * @return un objet ResultSet contenant les données des tours
     * @throws Exception si une erreur se produit
     */
    public ResultSet getDonneesTours(int idTournoi) throws Exception {
        List<String> parametres = new ArrayList<>();
        parametres.add(idTournoi + "");
        return this.actorMatch.specialQuery(SpecialQueryType.GetDonneesTours, QueryType.QUERY, parametres);
    }

    /**
     * Cette méthode retourne les données des tours pour un tournoi donné.
     *
     * @param idTournoi l'identifiant du tournoi
     * @return un objet ResultSet contenant les données des tours
     * @throws Exception si une erreur se produit
     */
    public ResultSet getNbMatchsParEquipes(int equipe1, int equipe2) throws Exception {
        List<String> parametres = new ArrayList<>();
        parametres.add(equipe1 + "");
        parametres.add(equipe2 + "");
        return this.actorMatch.specialQuery(SpecialQueryType.GetNbMatchsParEquipes, QueryType.QUERY, parametres);
    }

    /**
     * Cette méthode retourne les données des tours pour un tournoi donné.
     *
     * @param idTournoi l'identifiant du tournoi
     * @return un objet ResultSet contenant les données des tours
     * @throws Exception si une erreur se produit
     */
    public ResultSet getNbMatchsTermines(int idTournoi) throws Exception {
        List<String> parametres = new ArrayList<>();
        parametres.add(idTournoi + "");
        return this.actorMatch.specialQuery(SpecialQueryType.GetNbMatchsTermines, QueryType.QUERY, parametres);
    }

    /**
     * Cette méthode retourne le nombre maximum de tours pour un match dans un
     * tournoi donné.
     *
     * @param idTournoi l'identifiant du tournoi
     * @return un objet ResultSet contenant le nombre maximum de tours
     * @throws Exception si une erreur se produit
     */
    public ResultSet getNbToursMaxMatch(int idTournoi) throws Exception {
        List<String> parametres = new ArrayList<>();
        parametres.add(idTournoi + "");
        return this.actorMatch.specialQuery(SpecialQueryType.GetNbToursMaxMatch, QueryType.QUERY, parametres);
    }

    /**
     * Cette méthode retourne le nombre de tours par match pour un tournoi donné.
     *
     * @param idTournoi l'identifiant du tournoi
     * @return un objet ResultSet contenant le nombre de tours par match
     * @throws Exception si une erreur se produit
     */
    public ResultSet getNbToursParMatch(int idTournoi) throws Exception {
        List<String> parametres = new ArrayList<>();
        parametres.add(idTournoi + "");
        return this.actorMatch.specialQuery(SpecialQueryType.GetNbToursParMatchParTournoi, QueryType.QUERY, parametres);
    }

    /**
     * Cette méthode retourne les matchs d'un tournoi donné.
     *
     * @param idTournoi l'identifiant du tournoi
     * @return un objet ResultSet contenant les matchs du tournoi
     * @throws Exception si une erreur se produit
     */
    public ResultSet getMatchsDUnTournoi(int idTournoi) throws Exception {
        Map<TableAttributType, String> parametresWhere = new HashMap<>();
        parametresWhere.put(TableAttributType.ID_TOURNOI, idTournoi + "");
        return this.actorMatch.get(null, parametresWhere, null);
    }

    /**
     * 
     * Ajoute un nouveau match à la base de données.
     * 
     * @param idMatch   l'identifiant unique du match
     * @param idTournoi l'identifiant unique du tournoi associé
     * @param numTour   le numéro du tour du tournoi associé
     * @param equipe1   l'identifiant unique de la première équipe
     * @param equipe2   l'identifiant unique de la deuxième équipe
     * @param termine   l'état du match (terminé ou non)
     * @throws Exception si une erreur se produit lors de l'ajout du match à la base
     *                   de données
     */
    public void addMatch(Integer idMatch, Integer idTournoi, Integer numTour, Integer equipe1, Integer equipe2,
            String termine) throws Exception {
        Map<TableAttributType, String> parametresValues = new HashMap<>();
        parametresValues.put(TableAttributType.ID_MATCH, idMatch + "");
        parametresValues.put(TableAttributType.ID_TOURNOI, idTournoi + "");
        parametresValues.put(TableAttributType.NUM_TOUR, numTour + "");
        parametresValues.put(TableAttributType.EQUIPE1, equipe1 + "");
        parametresValues.put(TableAttributType.EQUIPE2, equipe2 + "");
        parametresValues.put(TableAttributType.TERMINE, termine + "");
        this.actorMatch.add(parametresValues);
    }

    /**
     * 
     * Modifie les scores du match identifié par l'ID donné.
     * 
     * @param idMatch  L'ID du match à modifier.
     * @param equipe1  L'ID de la première équipe.
     * @param equipe2  L'ID de la deuxième équipe.
     * @param scoreEq1 Le score de la première équipe.
     * @param scoreEq2 Le score de la deuxième équipe.
     * @param termine  L'état du match (terminé ou non terminé).
     * @throws Exception si une erreur se produit lors de l'accès à la base de
     *                   données.
     */
    public void setScoresMatch(int idMatch, Integer equipe1, Integer equipe2, Integer scoreEq1, Integer scoreEq2,
            String termine) throws Exception {
        Map<TableAttributType, String> parametresValues = new HashMap<>();
        parametresValues.put(TableAttributType.ID_MATCH, idMatch + "");
        parametresValues.put(TableAttributType.EQUIPE1, equipe1 + "");
        parametresValues.put(TableAttributType.EQUIPE2, equipe2 + "");
        parametresValues.put(TableAttributType.SCORE1, scoreEq1 + "");
        parametresValues.put(TableAttributType.SCORE2, scoreEq2 + "");
        parametresValues.put(TableAttributType.TERMINE, termine + "");
        Map<TableAttributType, String> parametresWhere = new HashMap<>();
        parametresWhere.put(TableAttributType.ID_MATCH, idMatch + "");
        this.actorMatch.set(parametresValues, parametresWhere);
    }

    /**
     * 
     * Supprime tous les matches d'un tour spécifié dans un tournoi donné.
     * 
     * @param idTournoi l'identifiant du tournoi concerné
     * @param numTour   le numéro du tour à supprimer
     * @throws Exception si une erreur se produit lors de la suppression des matches
     */
    public void removeMatchDUnTour(int idTournoi, int numTour) throws Exception {
        Map<TableAttributType, String> parametresWhere = new HashMap<>();
        parametresWhere.put(TableAttributType.ID_TOURNOI, idTournoi + "");
        parametresWhere.put(TableAttributType.NUM_TOUR, numTour + "");
        this.actorMatch.remove(parametresWhere);
    }

    /**
     * 
     * Supprime tous les matchs d'un tournoi donné en utilisant l'ID du tournoi.
     * 
     * @param idTournoi l'ID du tournoi dont les matchs doivent être supprimés.
     * @throws Exception si une erreur survient lors de la suppression des matchs.
     */
    public void removeMatchsDUnTournoi(int idTournoi) throws Exception {
        Map<TableAttributType, String> parametresWhere = new HashMap<>();
        parametresWhere.put(TableAttributType.ID_TOURNOI, idTournoi + "");
        this.actorMatch.remove(parametresWhere);
    }

}
