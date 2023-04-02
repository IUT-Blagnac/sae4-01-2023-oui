package control.actors.specialQueries.specialQueries;


import control.actors.specialQueries.SpecialQuery;
import java.util.List;


public class GetNbMatchsTermines extends SpecialQuery {

    public GetNbMatchsTermines(List<String> parametres) {
        this.strSQL = "Select count(*) as total, (Select count(*) from matchs m2  WHERE m2.id_tournoi = m.id_tournoi  AND m2.termine='oui' ) as termine from matchs m  WHERE m.id_tournoi=" + parametres.get(0) + " GROUP by id_tournoi";
    }

}
