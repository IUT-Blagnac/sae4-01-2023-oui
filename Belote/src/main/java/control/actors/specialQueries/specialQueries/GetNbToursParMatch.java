/**

Cette classe hérite de la classe abstraite SpecialQuery et permet de récupérer le nombre de tours de
chaque match d'un tournoi spécifique ainsi que le nombre de matchs joués et terminés pour chaque tour.
*/
package control.actors.specialQueries.specialQueries;

import control.actors.specialQueries.SpecialQuery;
import java.util.List;

public class GetNbToursParMatch extends SpecialQuery {
    /**
     * Constructeur prenant en paramètre une liste de chaînes de caractères qui
     * contient l'identifiant du tournoi.
     * La méthode récupère le nombre de tours de chaque match d'un tournoi
     * spécifique ainsi que le nombre de matchs joués
     * et terminés pour chaque tour. La requête SQL est stockée dans la variable
     * strSQL.
     *
     * @param parametres une liste de chaînes de caractères qui contient
     *                   l'identifiant du tournoi.
     */
    public GetNbToursParMatch(List<String> parametres) {
        this.strSQL = "Select num_tour, count(*) as nb_matchs, (Select count(*) from matchs m2 WHERE m2.id_tournoi = m.id_tournoi AND m2.num_tour=m.num_tour AND m2.termine='oui' ) as termine from matchs m WHERE m.id_tournoi="
                + parametres.get(0) + " GROUP BY m.num_tour,m.id_tournoi";
    }
}