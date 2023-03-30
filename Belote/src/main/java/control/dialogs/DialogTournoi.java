package control.dialogs;


import control.actors.Actor;
import control.actors.ActorFactory;
import model.Tournoi;
import types.ActorType;
import types.StatutTournoi;
import types.TableAttributType;
import view.Fenetre;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class DialogTournoi {

    private Actor actorTournoi;
    private DialogMatch dialogMatch;
    private DialogEquipe dialogEquipe;

    public DialogTournoi() {
        try {
            this.dialogMatch = new DialogMatch();
            this.dialogEquipe = new DialogEquipe();
            this.actorTournoi = ActorFactory.getActor(ActorType.TOURNOI);
        } catch (Exception e) {
            System.out.println(e.getMessage()); // TODO : popup
        }
    }

    public ResultSet getTournoiParNom(String nomTournois) throws Exception {
        Map<TableAttributType, String> parametresWhere = new HashMap<>();
        parametresWhere.put(TableAttributType.NOM_TOURNOI, nomTournois);
        return this.actorTournoi.get(null, parametresWhere);
    }

    public ResultSet getTousLesTournois() throws Exception {
        return this.actorTournoi.get(null, null);
    }

    public void addTournoi(Integer idTournoi, Integer nbMatchs, String nomTournoi, StatutTournoi statut) throws Exception {
        Map<TableAttributType, String> parametresValues = new HashMap<>();
        parametresValues.put(TableAttributType.ID_TOURNOI, idTournoi.toString());
        parametresValues.put(TableAttributType.NB_MATCHS, nbMatchs.toString());
        parametresValues.put(TableAttributType.NOM_TOURNOI, "'"+nomTournoi+"'"); // TODO : gérer ''
        parametresValues.put(TableAttributType.STATUT, statut.getOrdre().toString());
        this.actorTournoi.add(parametresValues);
    }

    public void setStatutTournoi(StatutTournoi statut, Integer idTournoi) throws Exception {
        Map<TableAttributType, String> parametresValues = new HashMap<>();
        parametresValues.put(TableAttributType.STATUT, statut.getOrdre().toString());
        Map<TableAttributType, String> parametresWhere = new HashMap<>();
        parametresWhere.put(TableAttributType.ID_TOURNOI, idTournoi.toString());
        this.actorTournoi.set(parametresValues, parametresWhere);
    }

    public void removeUnTournoi(Integer idTournoi) throws Exception {
        Map<TableAttributType, String> parametresWhere = new HashMap<>();
        parametresWhere.put(TableAttributType.ID_TOURNOI, idTournoi.toString());
        this.actorTournoi.remove(parametresWhere);
    }

    public int creerTournoi(){ // TODO : refactorer
        String s = (String) JOptionPane.showInputDialog( // TODO : mettre popup dans Fenetre
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
                    Fenetre.afficherInformation("Erreur lors de la création d'un tournoi, le nom donné est trop court.");
                    return 2;
                }
            } catch (Exception e) {
                Fenetre.afficherInformation("Erreur lors de la création d'un tournoi.");
            }
            if(Objects.equals(s, "")){
                Fenetre.afficherInformation("Erreur lors de la création d'un tournoi, le nom donné ne peut pas contenir de caractères spéciaux ni de caractères accentués.");
                return 2;
            }else{
                ResultSet rs;
                try {
                    rs = this.getTournoiParNom(s);
                    if(rs.next()){
                        Fenetre.afficherInformation("Erreur lors de la création d'un tournoi, un tournoi du même nom existe déjà.");
                        return 2;
                    }
                    this.addTournoi(null, 10, s, StatutTournoi.INSCRIPTION);
                } catch (Exception e) {
                    Fenetre.afficherInformation("Erreur lors de l'ajout du tournoi.");
                }
            }
        }
        return 0;
    }

    public int supprimerTournoi(String nomtournoi) { // TODO : refactorer
        try {
            int idt;
            ResultSet rs = this.getTournoiParNom(nomtournoi);
            rs.next();
            idt = rs.getInt("id_tournoi");
            rs.close();
            this.dialogMatch.deleteMatch(idt);
            this.dialogEquipe.removeToutesEquipesDUnTournoi(idt);
            this.removeUnTournoi(idt);
        } catch (Exception e) {
            Fenetre.afficherInformation("Erreur lors de l'ajout du tournoi.");
        }
        return 0;
    }

}
