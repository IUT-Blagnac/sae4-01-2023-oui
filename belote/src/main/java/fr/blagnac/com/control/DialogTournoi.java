package fr.blagnac.com.control;


import javax.swing.*;

import fr.blagnac.com.model.tournoi.Tournoi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;


public class DialogTournoi {

    private DialogDataBase ddb;
    private static DialogTournoi dt;

    private DialogTournoi() throws Exception {
        this.ddb = DialogDataBase.getInstance();
    }

    public static DialogTournoi getInstance() throws Exception {
        if (dt == null) {
            dt = new DialogTournoi();
        }
        return dt;
    }

    public int creerTournoi(){
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
                    rs = this.ddb.getTournoisParNom(s);
                    if(rs.next()){
                        JOptionPane.showMessageDialog(null, "Le tournoi n'a pas �t� cr��. Un tournoi du m�me nom existe d�j�");
                        return 2;
                    }
                    this.ddb.insertTournoi(null, 10, s, 0);
                } catch (SQLException e) {
                    System.out.println("Erreur requete insertion nouveau tournoi:" + e.getMessage()); // TODO : popup
                    //e.printStackTrace();
                }
                //s2.executeUpdate("INSERT INTO tournois (id")
            }
        }
        return 0;
    }

    public int deleteTournoi(String nomtournoi){
        try {
            int idt;
            ResultSet rs = this.ddb.getTournoisParNom(nomtournoi);
            rs.next();
            idt = rs.getInt("id_tournoi");
            rs.close();
            this.ddb.deleteMatch(idt);
            this.ddb.deleteEquipe(idt);
            this.ddb.deleteTournoi(idt);
        } catch (SQLException e) {
            System.out.println("Erreur suppression" + e.getMessage()); // TODO : popup
        } catch (Exception e) {
            System.out.println("Erreur inconnue"); // TODO : popup
        }
        return 0;
    }

}
