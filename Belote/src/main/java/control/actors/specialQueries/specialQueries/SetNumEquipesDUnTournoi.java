package control.actors.specialQueries.specialQueries;


import control.actors.specialQueries.SpecialQuery;
import java.util.List;


public class SetNumEquipesDUnTournoi extends SpecialQuery {

    public SetNumEquipesDUnTournoi(List<String> parametres) {
        this.strSQL = "UPDATE equipes SET num_equipe = num_equipe-1  WHERE id_tournoi = " + parametres.get(0) + " AND num_equipe > " + parametres.get(1);
    }

}
