package control.actors.specialQueries.specialQueries;


import control.actors.specialQueries.SpecialQuery;
import java.util.List;


/**
 * La classe GetNbMatchsTermines est une requête spéciale qui permet d'obtenir le nombre total de matchs disputés
 *   dans un tournoi spécifié, ainsi que le nombre de matchs terminés. Elle hérite de la classe abstraite SpecialQuery.
 */
public class GetNbMatchsTermines extends SpecialQuery {

    /**
     * Constructeur qui prend une liste de paramètres pour créer la requête SQL correspondante.
     * La requête récupère le nombre total de matchs disputés dans un tournoi spécifié par le premier paramètre, ainsi que le nombre de matchs terminés.
     * @param parametres une liste de paramètres pour créer la requête SQL.
     *   Le premier paramètre est l'ID du tournoi.
     */
    public GetNbMatchsTermines(List<String> parametres) {
        this.strSQL = "Select count(*) as total, (Select count(*) from matchs m2  WHERE m2.id_tournoi = m.id_tournoi  AND m2.termine='oui' ) as termine from matchs m  WHERE m.id_tournoi="
            + parametres.get(0) + " GROUP by id_tournoi";
    }

}
