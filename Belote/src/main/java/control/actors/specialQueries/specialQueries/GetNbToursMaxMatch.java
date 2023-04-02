package control.actors.specialQueries.specialQueries;


import control.actors.specialQueries.SpecialQuery;
import java.util.List;


public class GetNbToursMaxMatch extends SpecialQuery {

    public GetNbToursMaxMatch(List<String> parametres) {
        this.strSQL = "SELECT MAX(num_tour) as max_num_tour FROM matchs WHERE id_tournoi=" + parametres.get(0);
    }

}
