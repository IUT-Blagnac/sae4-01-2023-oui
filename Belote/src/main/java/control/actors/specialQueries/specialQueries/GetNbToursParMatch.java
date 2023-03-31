package control.actors.specialQueries.specialQueries;


import control.actors.specialQueries.SpecialQuery;
import java.util.List;


public class GetNbToursParMatch extends SpecialQuery {

    public GetNbToursParMatch(List<String> parametres) {
        this.strSQL = "Select num_tour, count(*) as nb_matchs, (Select count(*) from matchs m2 WHERE m2.id_tournoi = m.id_tournoi AND m2.num_tour=m.num_tour AND m2.termine='oui' ) as termine from matchs m WHERE m.id_tournoi=" + parametres.get(0) + " GROUP BY m.num_tour,m.id_tournoi";
    }

}
