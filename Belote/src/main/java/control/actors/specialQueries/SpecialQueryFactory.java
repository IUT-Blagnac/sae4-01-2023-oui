/**
 * 
 * Cette classe est utilisée pour obtenir une instance de la classe SpecialQuery
 * en fonction du type de requête spéciale et des paramètres fournis.
 */

package control.actors.specialQueries;


import control.actors.specialQueries.specialQueries.*;
import types.SpecialQueryType;
import java.util.List;


public class SpecialQueryFactory {

    /**
     * 
     * Obtient une instance de la classe SpecialQuery en fonction du type de requête
     * spéciale et des paramètres fournis.
     * 
     * @param sqt        le type de requête spéciale à exécuter.
     * @param parametres une liste de chaînes de caractères contenant les paramètres
     *                   de la requête spéciale.
     * @return une instance de la classe SpecialQuery correspondant au type de
     *         requête spéciale et aux paramètres fournis.
     * @throws Exception si le type de requête spéciale n'est pas trouvé.
     */
    public static SpecialQuery getSpecialQuery(SpecialQueryType sqt, List<String> parametres) throws Exception {
        switch (sqt) {
            case SetNumEquipesDUnTournoi:
                return new SetNumEquipesDUnTournoi(parametres);
            case GetResultatsMatch:
                return new GetResultatsMatch(parametres);
            case GetDonneesTours:
                return new GetDonneesTours(parametres);
            case GetNbMatchsParEquipes:
                return new GetNbMatchsParEquipes(parametres);
            case GetNbMatchsTermines:
                return new GetNbMatchsTermines(parametres);
            case GetNbToursMaxMatch:
                return new GetNbToursMaxMatch(parametres);
            case GetNbToursParMatchParTournoi:
                return new GetNbToursParMatch(parametres);
            default:
                throw new Exception("Le type de requête spéciale " + sqt + " n'a pas été trouvé.");
        }
    }
}