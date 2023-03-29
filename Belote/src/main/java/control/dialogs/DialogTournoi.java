package control.dialogs;


import javax.swing.*;
import control.actors.Actor;
import control.actors.ActorFactory;
import types.ActorType;
import model.Tournoi;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;


public class DialogTournoi {

    private final DialogMatch dialogMatch;
    private final DialogEquipe dialogEquipe;
    private final Actor actorTournoi;

    public DialogTournoi() {
        this.dialogMatch = new DialogMatch();
        this.dialogEquipe = new DialogEquipe();
        this.actorTournoi = ActorFactory.getActor(ActorType.TOURNOI);
    }

    public int creerTournoi(){ // TODO : refactorer
        String s = (String) JOptionPane.showInputDialog(
                null,
                "Entrez le nom du tournoi",
                "Nom du tournoi",
                JOptionPane.PLAIN_MESSAGE);
        if(s == null || s.equals("")){
            return 1;
        }else{
            try {
                s = Tournoi.mysql_real_escape_string(s); // TODO : déplacer fonction
                if(s.length() < 3){
                    JOptionPane.showMessageDialog(null, "Le tournoi n'a pas �t� cr��. Nom trop court.");
                    return 2;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(Objects.equals(s, "")){
                JOptionPane.showMessageDialog(null, "Le tournoi n'a pas �t� cr��. Ne pas mettre de caract�res sp�ciaux ou accents dans le nom");
                return 2;
            }else{
                ResultSet rs;
                try {
                    rs = this.getTournoisParNom(s);
                    if(rs.next()){
                        JOptionPane.showMessageDialog(null, "Le tournoi n'a pas �t� cr��. Un tournoi du m�me nom existe d�j�");
                        return 2;
                    }
                    this.insertTournoi(null, 10, s, 0);
                } catch (SQLException e) {
                    System.out.println("Erreur requete insertion nouveau tournoi:" + e.getMessage()); // TODO : popup
                    //e.printStackTrace();
                }
                //s2.executeUpdate("INSERT INTO tournois (id")
            }
        }
        return 0;
    }

    public int deleteTournoi(String nomtournoi) { // TODO : refactorer
        try {
            int idt;
            ResultSet rs = this.getTournoisParNom(nomtournoi);
            rs.next();
            idt = rs.getInt("id_tournoi");
            rs.close();
            dialogMatch.deleteMatch(idt);
            dialogEquipe.removeToutesEquipesDUnTournoi(idt);
            this.deleteTournoi(idt);
        } catch (SQLException e) {
            System.out.println("Erreur suppression" + e.getMessage()); // TODO : popup
        } catch (Exception e) {
            System.out.println("Erreur inconnue"); // TODO : popup
            System.out.println(e.getMessage());
        }
        return 0;
    }

    // TODO : bonnes utilisations de actorMatch !

    public ResultSet getNbMatchsTerminesParTournois(int idTournoi) throws SQLException {
        return this.actorTournoi.getStatement().executeQuery(
                "Select count(*) as total, (Select count(*) from matchs m2  WHERE m2.id_tournoi = m.id_tournoi  AND m2.termine='oui' ) as termines from matchs m  WHERE m.id_tournoi="
                        + idTournoi + " GROUP by id_tournoi ;");
    }

    public ResultSet getEquipesParTournoi(int idTournoi) throws SQLException {
        return this.actorTournoi.getStatement()
                .executeQuery("SELECT * FROM equipes WHERE id_tournoi = " + idTournoi + " ORDER BY num_equipe;");
    }

    public ResultSet getMatchsParTournoi(int idTournoi) throws SQLException {
        return this.actorTournoi.getStatement().executeQuery("SELECT * FROM matchs WHERE id_tournoi=" + idTournoi + ";");
    }

    public ResultSet getNbToursMaxMatchParTournoi(int idTournoi) throws SQLException {
        return this.actorTournoi.getStatement().executeQuery("SELECT MAX (num_tour)  FROM matchs WHERE id_tournoi=" + idTournoi + "; ");
    }

    public ResultSet getTournoisParNom(String nomTournois) throws SQLException {
        return this.actorTournoi.getStatement().executeQuery("SELECT * FROM tournois WHERE nom_tournoi = '" + nomTournois + "';");
    }

    public ResultSet getTousLesTournois() throws SQLException {
        return this.actorTournoi.getStatement().executeQuery("SELECT * FROM tournois;");
    }

    public void insertTournoi(Integer idTournoi, Integer nbMatchs, String nomTournoi, Integer statut) throws SQLException {
        this.actorTournoi.getStatement().executeUpdate("INSERT INTO tournois (id_tournoi, nb_matchs, nom_tournoi, statut) VALUES ("
                + (idTournoi != null ? idTournoi : "NULL") + ", " + nbMatchs + ", '" + nomTournoi + "', " + statut
                + ")");
    }

    public void setStatutTournoi(int statut, int idTournoi) throws SQLException {
        this.actorTournoi.getStatement().executeUpdate("UPDATE tournois SET statut=" + statut + " WHERE id_tournoi=" + idTournoi);
    }

    public void deleteTournoi(int idTournoi) throws SQLException {
        this.actorTournoi.getStatement().executeUpdate("DELETE FROM tournois WHERE id_tournoi=" + idTournoi);
    }

    public ResultSet getNbToursParMatchParTournoi(int idTournoi) throws SQLException {
        return this.actorTournoi.getStatement().executeQuery("Select num_tour,count(*) as tmatchs, (Select count(*) from matchs m2 WHERE m2.id_tournoi = m.id_tournoi AND m2.num_tour=m.num_tour AND m2.termine='oui' ) as termines from matchs m WHERE m.id_tournoi=" + idTournoi + " GROUP BY m.num_tour,m.id_tournoi;");
    }

}
