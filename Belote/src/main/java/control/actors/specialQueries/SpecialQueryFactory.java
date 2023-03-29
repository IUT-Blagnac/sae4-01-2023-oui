package control.actors.specialQueries;


import types.SpecialQueryType;
import java.util.List;


public class SpecialQueryFactory {

    public static SpecialQuery getSpecialQuery(SpecialQueryType sqt, List<String> parametres) {
        switch (sqt) {
            case SetNumEquipesDUnTournoi:
                return new SetNumEquipesDUnTournoi(parametres);
            default:
                return null;
        }
    }

}
