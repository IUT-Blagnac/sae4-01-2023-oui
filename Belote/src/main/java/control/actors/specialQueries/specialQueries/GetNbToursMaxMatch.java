package control.actors.specialQueries.specialQueries;


import control.actors.specialQueries.SpecialQuery;
import java.util.List;


/**
 * La classe GetNbToursMaxMatch est une requête spéciale qui permet d'obtenir le numéro de tour maximum dans un tournoi spécifié.
 * Elle hérite de la classe abstraite SpecialQuery.
 */
public class GetNbToursMaxMatch extends SpecialQuery {

    /**
     * Constructeur qui prend une liste de paramètres pour créer la requête SQL correspondante.
     * La requête récupère le numéro de tour maximum dans un tournoi spécifié par le
     * premier paramètre.
     * @param parametres une liste de paramètres pour créer la requête SQL.
     *  Le premier paramètre est l'ID du tournoi.
     */
    public GetNbToursMaxMatch(List<String> parametres) {
        this.strSQL = "SELECT MAX(num_tour) as max_num_tour FROM matchs WHERE id_tournoi=" + parametres.get(0);
    }

}
