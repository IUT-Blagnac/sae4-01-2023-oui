/**
 * Cette classe gère les dialogues et les interactions avec la base de données pour les tournois.
*/

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

    /**
     * Constructeur de la classe DialogTournoi qui initialise les objets nécessaires pour la gestion des tournois.
     */
    public DialogTournoi() {
        try {
            this.dialogMatch = new DialogMatch();
            this.dialogEquipe = new DialogEquipe();
            this.actorTournoi = ActorFactory.getActor(ActorType.TOURNOI);
        } catch (Exception e) {
            Fenetre.afficherErreur("Erreur lors de la récupération de l'acteur Tournoi.");
        }
    }

    /**
     * Retourne un ResultSet contenant les informations d'un tournoi donné en paramètre.
     * @param nomTournois le nom du tournoi à récupérer
     * @return un ResultSet contenant les informations du tournoi récupéré
     * @throws Exception si une erreur survient lors de la récupération des informations dans la base de données
     */
    public ResultSet getTournoiParNom(String nomTournois) throws Exception {
        Map<TableAttributType, String> parametresWhere = new HashMap<>();
        parametresWhere.put(TableAttributType.NOM_TOURNOI, nomTournois);
        return this.actorTournoi.get(null, parametresWhere, null);
    }

    /**
     * Retourne un ResultSet contenant toutes les informations des tournois présents dans la base de données.
     * @return un ResultSet contenant toutes les informations des tournois présents dans la base de données
     * @throws Exception si une erreur survient lors de la récupération des informations dans la base de données
     */
    public ResultSet getTousLesTournois() throws Exception {
        return this.actorTournoi.get(null, null, null);
    }

    /**
     * Ajoute un tournoi dans la base de données avec les informations données en paramètres.
     * @param idTournoi : l'identifiant unique du tournoi à ajouter
     * @param nbMatchs : le nombre de matchs du tournoi à ajouter
     * @param nomTournoi : le nom du tournoi à ajouter
     * @param statut : le statut du tournoi à ajouter
     * @throws Exception si une erreur survient lors de l'ajout du tournoi dans la base de données
     */
    public void addTournoi(Integer idTournoi, Integer nbMatchs, String nomTournoi, StatutTournoi statut) throws Exception {
        Map<TableAttributType, String> parametresValues = new HashMap<>();
        parametresValues.put(TableAttributType.ID_TOURNOI, idTournoi + "");
        parametresValues.put(TableAttributType.NB_MATCHS, nbMatchs + "");
        parametresValues.put(TableAttributType.NOM_TOURNOI, nomTournoi);
        parametresValues.put(TableAttributType.STATUT, statut.getOrdre() + "");
        this.actorTournoi.add(parametresValues);
    }

    /**
     * 
     * Modifie le statut d'un tournoi donné par son identifiant.
     * @param statut : le nouveau statut du tournoi.
     * @param idTournoi : l'identifiant du tournoi à modifier.
     * @throws Exception Si une erreur survient lors de la modification du statut du tournoi.
     */
    public void setStatutTournoi(StatutTournoi statut, Integer idTournoi) throws Exception {
        Map<TableAttributType, String> parametresValues = new HashMap<>();
        parametresValues.put(TableAttributType.STATUT, statut.getOrdre() + "");
        Map<TableAttributType, String> parametresWhere = new HashMap<>();
        parametresWhere.put(TableAttributType.ID_TOURNOI, idTournoi + "");
        this.actorTournoi.set(parametresValues, parametresWhere);
    }

    /**
     * Supprime un tournoi de la base de données en fonction de son identifiant.
     * @param idTournoi : l'identifiant du tournoi à supprimer
     * @throws Exception si une erreur survient lors de la suppression du tournoi
     */
    public void removeUnTournoi(Integer idTournoi) throws Exception {
        Map<TableAttributType, String> parametresWhere = new HashMap<>();
        parametresWhere.put(TableAttributType.ID_TOURNOI, idTournoi + "");
        this.actorTournoi.remove(parametresWhere);
    }

    /**
     * Crée un nouveau tournoi en demandant à l'utilisateur de saisir son nom et en vérifiant que les conditions de création sont remplies.
     * Si les conditions sont remplies, le tournoi est ajouté à la base de données avec le statut "inscription".
     * Sinon, une erreur est affichée.
     */
    public void creerTournoi() {
        String nomTournoi = Fenetre.saisieNomTournoi();
        if (nomTournoi == null || nomTournoi.equals("")) {
            return;
        }
        nomTournoi = Tools.mysql_real_escape_string(nomTournoi);
        if (nomTournoi.length() < 3) {
            Fenetre.afficherErreur("Erreur lors de la création d'un tournoi, le nom donné est trop court.");
            return;
        }
        if (Objects.equals(nomTournoi, "")) {
            Fenetre.afficherErreur("Erreur lors de la création d'un tournoi, le nom donné ne peut pas contenir de caractères spéciaux ni de caractères accentués.");
        } else {
            ResultSet rs;
            try {
                rs = this.getTournoiParNom(nomTournoi);
                if (rs.next()) {
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

    /**
     * 
     * Supprime un tournoi en fonction de son nom en cascade, c'est-à-dire en supprimant également tous les matchs et équipes associés.
     * @param nomTournoi le nom du tournoi à supprimer
     */
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
