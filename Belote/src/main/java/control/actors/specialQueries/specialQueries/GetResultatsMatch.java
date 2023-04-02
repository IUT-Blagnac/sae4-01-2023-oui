package control.actors.specialQueries.specialQueries;


import control.actors.specialQueries.SpecialQuery;
import java.util.List;


public class GetResultatsMatch extends SpecialQuery {

    public GetResultatsMatch(List<String> parametres) {
        this.strSQL = "SELECT equipe,(SELECT nom_j1 FROM equipes e WHERE e.id_equipe = equipe AND e.id_tournoi = " + parametres.get(0)
            + ") as nom_j1,(SELECT nom_j2 FROM equipes e WHERE e.id_equipe = equipe AND e.id_tournoi = " + parametres.get(0)
            + ") as nom_j2, SUM(score) as score, (SELECT count(*) FROM matchs m WHERE (m.equipe1 = equipe AND m.score1 > m.score2 "
            + "AND m.id_tournoi = id_tournoi) OR (m.equipe2 = equipe AND m.score2 > m.score1 )) as matchs_gagnes, (SELECT COUNT(*) "
            + "FROM matchs m WHERE m.equipe1 = equipe OR m.equipe2=equipe) as matchs_joues FROM  (select equipe1 as equipe,score1 as "
            + "score from matchs where id_tournoi=" + parametres.get(0) + " UNION select equipe2 as equipe,score2 as score from matchs "
            + "where id_tournoi=" + parametres.get(0) + ") GROUP BY equipe ORDER BY matchs_gagnes DESC";
    }

}
