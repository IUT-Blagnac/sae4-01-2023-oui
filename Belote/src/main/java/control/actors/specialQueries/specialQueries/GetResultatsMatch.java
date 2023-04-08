package control.actors.specialQueries.specialQueries;


import control.actors.specialQueries.SpecialQuery;
import java.util.List;


/**
 * Cette classe hérite de la classe SpecialQuery et permet d'obtenir les résultats d'un match
 * pour un tournoi donné. Elle exécute une requête SQL pour récupérer les équipes, le nom des joueurs,
 * le score total, le nombre de matchs gagnés et le nombre de matchs joués.
 * Les résultats sont triés par ordre décroissant du nombre de matchs gagnés.
 */
public class GetResultatsMatch extends SpecialQuery {

    /**
     * Constructeur de la classe GetResultatsMatch qui prend en paramètre une liste de chaînes de caractères
     * représentant les paramètres nécessaires pour exécuter la requête SQL.
     * Les résultats de la requête sont stockés dans la variable strSQL de la superclasse SpecialQuery.
     * @param parametres la liste de paramètres nécessaires pour exécuter la requête SQL
     */
    public GetResultatsMatch(List<String> parametres) {
        this.strSQL = "SELECT equipe,(SELECT nom_j1 FROM equipes e WHERE e.id_equipe = equipe AND e.id_tournoi = "
            + parametres.get(0)
            + ") as nom_j1,(SELECT nom_j2 FROM equipes e WHERE e.id_equipe = equipe AND e.id_tournoi = "
            + parametres.get(0)
            + ") as nom_j2, SUM(score) as score, (SELECT count(*) FROM matchs m WHERE (m.equipe1 = equipe AND m.score1 > m.score2 "
            + "AND m.id_tournoi = id_tournoi) OR (m.equipe2 = equipe AND m.score2 > m.score1 )) as matchs_gagnes, (SELECT COUNT(*) "
            + "FROM matchs m WHERE m.equipe1 = equipe OR m.equipe2=equipe) as matchs_joues FROM  (select equipe1 as equipe,score1 as "
            + "score from matchs where id_tournoi=" + parametres.get(0)
            + " UNION select equipe2 as equipe,score2 as score from matchs "
            + "where id_tournoi=" + parametres.get(0) + ") GROUP BY equipe ORDER BY matchs_gagnes DESC";
    }

}