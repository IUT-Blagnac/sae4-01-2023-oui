package fr.blagnac.com.control.dialogs;


import fr.blagnac.com.control.actors.ActorEquipe;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DialogEquipe extends ActorEquipe {

    private final List<String> tableColumns; // TODO : expérimental

    public DialogEquipe() throws Exception { // TODO : rediriger l'exception pour popup
        super();
        this.tableColumns = this.getColumnsFromTable();
    }

    /*
    // TODO : mettre dans une classe Tool/appart ?
    private void verifierParametres(String parametre) throws SQLException {
        if (!this.tableColumns.contains(parametre)) {
            throw new SQLException("Une des colonnes renseignées n'existe pas dans la table Equipes.");
        }
    }
    // TODO : mettre dans une classe Tool/appart ?
    private void verifierParametres(Map<String, String> parametres) throws SQLException {
        for (String column : parametres.keySet()) {
            verifierParametres(column);
        }
    }*/

    public ResultSet getEquipeDUnTournoi(int idTournoi) throws SQLException {
        List<String> filtres = new ArrayList<>();
        Map<String, String> parametresWhere = new HashMap<>();
        parametresWhere.put("id_tournois", ""+idTournoi);
        return this.get(filtres, parametresWhere);
    }

    public ResultSet getNumDUneEquipe(int idEquipe) throws SQLException {
        List<String> filtres = new ArrayList<>();
        filtres.add("num_equipe");
        Map<String, String> parametresWhere = new HashMap<>();
        parametresWhere.put("id_equipe", ""+idEquipe);
        return this.get(filtres, parametresWhere);
    }

    public void addEquipe(Integer idEquipe, Integer numEquipe, Integer idTournoi, String nomJ1, String nomJ2) throws SQLException, Exception {
        Map<String, String> parametresValues = new HashMap<>();
        parametresValues.put("id_equipe", ""+idEquipe);
        parametresValues.put("num_equipe", ""+numEquipe);
        parametresValues.put("id_tournoi", ""+idTournoi);
        parametresValues.put("nom_j1", "'"+nomJ1+"'"); // TODO : déplacer prise en compte des ''
        parametresValues.put("nom_j2", "'"+nomJ2+"'");
        this.add(parametresValues);
    }

    public void setNomsJoueursDUneEquipe(int idEquipe, String nomJ1, String nomJ2) throws SQLException, Exception {
        Map<String, String> parametresValues = new HashMap<>();
        parametresValues.put("nom_j1", "'"+nomJ1+"'"); // TODO : déplacer prise en compte des ''
        parametresValues.put("nom_j2", "'"+nomJ2+"'");
        Map<String, String> parametresWhere = new HashMap<>();
        parametresWhere.put("id_equipe", ""+idEquipe);
        this.set(parametresValues, parametresWhere);
    }

    // TODO !
    /*public void setNumEquipe(int idTournoi, int numEquipe) throws SQLException, Exception {
        Map<String, String> parametresValues = new HashMap<>();
        parametresValues.put("id_tournoi", ""+idTournoi);
        Map<String, String> parametresWhere = new HashMap<>();
        parametresWhere.put("id_equipe", ""+idEquipe);
        this.set(parametresValues, parametresWhere);
    }*/
    public void setNumEquipesDUnTournoi(int idTournoi, int numEquipe) throws SQLException {
        this.getStatement().executeUpdate("UPDATE equipes SET num_equipe = num_equipe-1  WHERE id_tournoi = " + idTournoi + " AND num_equipe > " + numEquipe);
    }

    public void removeUneEquipe(int idTournoi, int idEquipe) throws SQLException {
        Map<String, String> parametresWhere = new HashMap<>();
        parametresWhere.put("id_tournoi", ""+idTournoi);
        parametresWhere.put("id_equipe", ""+idEquipe);
        this.remove(parametresWhere);
    }

    public void removeToutesEquipesDUnTournoi(int idTournoi) throws SQLException {
        Map<String, String> parametresWhere = new HashMap<>();
        parametresWhere.put("id_tournoi", ""+idTournoi);
        this.remove(parametresWhere);
    }

}
