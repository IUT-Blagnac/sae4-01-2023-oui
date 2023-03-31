package control.actors.specialQueries;


import control.actors.specialQueries.specialQueries.*;
import types.SpecialQueryType;
import java.util.List;


public class SpecialQueryFactory {

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
