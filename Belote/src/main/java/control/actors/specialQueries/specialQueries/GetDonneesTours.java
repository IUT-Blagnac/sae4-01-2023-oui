package control.actors.specialQueries.specialQueries;


import control.actors.specialQueries.SpecialQuery;
import java.util.List;


public class GetDonneesTours extends SpecialQuery {

    public GetDonneesTours(List<String> parametres) {
        this.strSQL = "SELECT equipe, (SELECT count(*) FROM matchs m WHERE (m.equipe1 = equipe AND m.score1 > " +
            "m.score2  AND m.id_tournoi = id_tournoi) OR (m.equipe2 = equipe AND m.score2 > m.score1 )) as " +
            "matchs_gagnes FROM  (select equipe1 as equipe,score1 as score from matchs where id_tournoi=" +
            parametres.get(0) + " UNION select equipe2 as equipe,score2 as score from matchs where id_tournoi=" +
            parametres.get(0) + ") GROUP BY equipe  ORDER BY matchs_gagnes DESC";
    }

}
