package control.actors.specialQueries;


import types.SpecialQueryType;
import java.util.List;


public class SpecialQueryFactory {

    public static SpecialQuery getSpecialQuery(SpecialQueryType sqt, List<String> parametres) throws Exception {
        switch (sqt) {
            case SetNumEquipesDUnTournoi:
                return new SetNumEquipesDUnTournoi(parametres);
            default:
                throw new Exception("Le type de requête spéciale " + sqt + " n'a pas été trouvé.");
        }
    }

}
