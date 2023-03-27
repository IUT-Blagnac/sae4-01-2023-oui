package fr.blagnac.com.control.database.actors;

import java.sql.ResultSet;
import java.sql.SQLException;

import fr.blagnac.com.control.database.DialogDataBase;

public abstract class ActorTournoi {

    private DialogDataBase bdd;

    public ActorTournoi() {
        // TODO A changer en un throw exception
        try {
            this.bdd = DialogDataBase.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet getNbMatchsTerminesParTournois(int idTournoi) throws SQLException {
        return this.bdd.getStatement().executeQuery(
                "Select count(*) as total, (Select count(*) from matchs m2  WHERE m2.id_tournoi = m.id_tournoi  AND m2.termine='oui' ) as termines from matchs m  WHERE m.id_tournoi="
                        + idTournoi + " GROUP by id_tournoi ;");
    }

    public ResultSet getEquipesParTournoi(int idTournoi) throws SQLException {
        return this.bdd.getStatement()
                .executeQuery("SELECT * FROM equipes WHERE id_tournoi = " + idTournoi + " ORDER BY num_equipe;");
    }

    public ResultSet getMatchsParTournoi(int idTournoi) throws SQLException {
        return this.bdd.getStatement().executeQuery("SELECT * FROM matchs WHERE id_tournoi=" + idTournoi + ";");
    }

    public ResultSet getNbToursMaxMatchParTournoi(int idTournoi) throws SQLException {
        return this.bdd.getStatement().executeQuery("SELECT MAX (num_tour)  FROM matchs WHERE id_tournoi=" + idTournoi + "; ");
    }

    public ResultSet getTournoisParNom(String nomTournois) throws SQLException {
        return this.bdd.getStatement().executeQuery("SELECT * FROM tournois WHERE nom_tournoi = '" + nomTournois + "';");
    }

    public ResultSet getTousLesTournois() throws SQLException {
        return this.bdd.getStatement().executeQuery("SELECT * FROM tournois;");
    }

    public void insertTournoi(Integer idTournoi, Integer nbMatchs, String nomTournoi, Integer statut) throws SQLException {
        this.bdd.getStatement().executeUpdate("INSERT INTO tournois (id_tournoi, nb_matchs, nom_tournoi, statut) VALUES ("
                + (idTournoi != null ? idTournoi : "NULL") + ", " + nbMatchs + ", '" + nomTournoi + "', " + statut
                + ")");
    }

    public void setStatutTournoi(int statut, int idTournoi) throws SQLException {
        this.bdd.getStatement().executeUpdate("UPDATE tournois SET statut=" + statut + " WHERE id_tournoi=" + idTournoi);
    }

    public void deleteTournoi(int idTournoi) throws SQLException {
        this.bdd.getStatement().executeUpdate("DELETE FROM tournois WHERE id_tournoi=" + idTournoi);
    }

    public ResultSet getNbToursParMatchParTournoi(int idTournoi) throws
    SQLException {
    return this.bdd.getStatement().executeQuery("Select num_tour,count(*) as tmatchs, (Select count(*) from matchs m2 WHERE m2.id_tournoi = m.id_tournoi AND m2.num_tour=m.num_tour AND m2.termine='oui' ) as termines from matchs m WHERE m.id_tournoi=" + idTournoi + " GROUP BY m.num_tour,m.id_tournoi;");}
}
