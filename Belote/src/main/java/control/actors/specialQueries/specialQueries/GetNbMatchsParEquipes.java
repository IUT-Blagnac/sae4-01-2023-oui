package control.actors.specialQueries.specialQueries;


import control.actors.specialQueries.SpecialQuery;
import java.util.List;


/**
 * La classe GetNbMatchsParEquipes est une requête spéciale qui permet d'obtenir le nombre de matchs disputés entre deux équipes spécifiées.
 * Elle hérite de la classe abstraite SpecialQuery.
 */
public class GetNbMatchsParEquipes extends SpecialQuery {

    /**
     * Constructeur qui prend une liste de paramètres pour créer la requête SQL correspondante.
     * La requête récupère le nombre de matchs disputés entre deux équipes spécifiées par les deux premiers paramètres.
     * @param parametres : une liste de paramètres pour créer la requête SQL.
     *   Le premier paramètre est l'ID de la première équipe.
     *   Le deuxième paramètre est l'ID de la deuxième équipe.
     */
    public GetNbMatchsParEquipes(List<String> parametres) {
        this.strSQL = "SELECT COUNT(*) as nb_matchs FROM matchs m WHERE ( (m.equipe1 = " + parametres.get(0)
            + " AND m.equipe2 = " + parametres.get(1) + ") OR (m.equipe2 = " + parametres.get(0)
            + " AND m.equipe1 = " + parametres.get(1) + "))";
    }

}
