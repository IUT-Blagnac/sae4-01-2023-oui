package control.actors.specialQueries.specialQueries;


import control.actors.specialQueries.SpecialQuery;
import java.util.List;


/**
 * Cette classe est une requête spéciale qui permet de définir le nombre d'équipes pour un tournoi spécifique dans la base de données.
 */
public class SetNumEquipesDUnTournoi extends SpecialQuery {

    /**
     * Constructeur de la classe SetNumEquipesDUnTournoi.
     * @param parametres une liste de chaînes de caractères contenant deux paramètres :
     *  l'identifiant du tournoi et le nombre maximal
     *  d'équipes autorisées.
     */
    public SetNumEquipesDUnTournoi(List<String> parametres) {
        this.strSQL = "UPDATE equipes SET num_equipe = num_equipe-1 WHERE id_tournoi = " + parametres.get(0) + " AND num_equipe > " + parametres.get(1);
    }

}