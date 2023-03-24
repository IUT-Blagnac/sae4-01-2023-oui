package fr.blagnac.com.control;


import fr.blagnac.com.BeloteApp;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;
import java.util.Scanner;


// TODO : singleton
public class DialogDataBase {

    private Statement statement;
    private Connection connection;
    private static DialogDataBase ddb;

    private DialogDataBase(String beloteDir) throws SQLException, FileNotFoundException {
        this.connection = DriverManager.getConnection("jdbc:hsqldb:file:" + beloteDir + "/belote", "sa", "");
        this.statement = this.connection.createStatement();
        InputStream createFileInputStream = BeloteApp.class.getResourceAsStream("/create.sql");
        importSQL(this.connection, createFileInputStream);
    }

    public static DialogDataBase getDialogDataBaseInstance(String beloteDir) throws SQLException, FileNotFoundException {
        if (ddb == null) {
            ddb = new DialogDataBase(beloteDir);
        }
        return ddb;
    }

    public static DialogDataBase getDialogDataBaseInstance() throws Exception {
        if (ddb == null) {
            throw new Exception("Le répertoire de stockage des données de l'application est attendu en paramètre.");
        }
        return ddb;
    }

    public Statement getStatement() {
        return this.statement;
    }

    private void importSQL(Connection conn, InputStream in) throws SQLException, FileNotFoundException {
        Scanner scaner = new Scanner(in);
        scaner.useDelimiter("(;(\r)?\n)|(--\n)");
        Statement st = null;
        try {
            // create a statement
            st = conn.createStatement();

            // execute each request
            while (scaner.hasNext()) {
                String line = scaner.next();
                if (line.startsWith("/*!") && line.endsWith("*/")) {
                    line = line.substring(line.indexOf(' ') + 1, line.length() - " */".length());
                }
                if (line.trim().length() > 0) {
                    st.execute(line);
                }
            }
        } finally {
            // release resources
            if (st != null) {
                st.close();
            }
            scaner.close();
        }
    }

    // Methods

    public ResultSet getNbTournoiFinishedMatchs(int idTournoi) throws SQLException {
        return this.statement.executeQuery("Select count(*) as total, (Select count(*) from matchs m2  WHERE m2.id_tournoi = m.id_tournoi  AND m2.termine='oui' ) as termines from matchs m  WHERE m.id_tournoi=" + idTournoi + " GROUP by id_tournoi ;");
   }

    public ResultSet getAllTournois() throws SQLException {
        return this.statement.executeQuery("SELECT * FROM tournois;");
    }

    public ResultSet getTournoisByName(String nomTournoi) throws SQLException {
        return this.statement.executeQuery("SELECT * FROM tournois WHERE nom_tournoi = '" + nomTournoi + "';");
    }

    // TODO : rennomer correctement
    public ResultSet getMatchsData(int idTournoi) throws SQLException {
        return this.statement.executeQuery("SELECT equipe,(SELECT nom_j1 FROM equipes e WHERE e.id_equipe = equipe AND e.id_tournoi = " + idTournoi + ") as joueur1,(SELECT nom_j2 FROM equipes e WHERE e.id_equipe = equipe AND e.id_tournoi = " + idTournoi + ") as joueur2, SUM(score) as score, (SELECT count(*) FROM matchs m WHERE (m.equipe1 = equipe AND m.score1 > m.score2  AND m.id_tournoi = id_tournoi) OR (m.equipe2 = equipe AND m.score2 > m.score1 )) as matchs_gagnes, (SELECT COUNT(*) FROM matchs m WHERE m.equipe1 = equipe OR m.equipe2=equipe) as matchs_joues FROM  (select equipe1 as equipe,score1 as score from matchs where id_tournoi=" + idTournoi + " UNION select equipe2 as equipe,score2 as score from matchs where id_tournoi=" + idTournoi + ") GROUP BY equipe ORDER BY matchs_gagnes DESC;");
    }

    public ResultSet getTournoiEquipes(int idTournoi) throws SQLException {
        return this.statement.executeQuery("SELECT * FROM equipes WHERE id_tournoi = " + idTournoi + " ORDER BY num_equipe;");
    }

    public ResultSet getTournoiMatchs(int idTournoi) throws SQLException {
        return this.statement.executeQuery("SELECT * FROM matchs WHERE id_tournoi="+ idTournoi + ";");
    }

    public ResultSet getMaxNbToursTournoiMatch(int idTournoi) throws SQLException {
        return this.statement.executeQuery("SELECT MAX (num_tour)  FROM matchs WHERE id_tournoi="+idTournoi+"; ");
    }

}
