package control.actors.specialQueries.specialQueries;


import control.actors.specialQueries.SpecialQuery;
import java.util.List;


public class GetNbMatchsParEquipes extends SpecialQuery {

    public GetNbMatchsParEquipes(List<String> parametres) {
        this.strSQL = "SELECT COUNT(*) as nb_matchs FROM matchs m WHERE ( (m.equipe1 = " + parametres.get(0) + " AND m.equipe2 = " + parametres.get(1) + ") OR (m.equipe2 = " + parametres.get(0) + " AND m.equipe1 = " + parametres.get(1) + "))";
    }

}
