package control.dialogs;


import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;
import java.util.Scanner;


public class DialogDataBase {

    private static DialogDataBase ddb;
    private static Statement statement;
    private Connection connection;

    private DialogDataBase(String beloteDir, String createFile) throws SQLException, FileNotFoundException {
        this.connection = DriverManager.getConnection("jdbc:hsqldb:file:" + beloteDir + "/belote", "sa", "");
        statement = this.connection.createStatement();
        InputStream createFileInputStream = this.getClass().getResourceAsStream(createFile);
        importSQL(this.connection, createFileInputStream);
    }

    public static DialogDataBase initialize(String beloteDir, String createFile) throws SQLException, FileNotFoundException {
        if (ddb == null) {
            ddb = new DialogDataBase(beloteDir, createFile);
        }
        return ddb;
    }

    public static Statement getStatement() throws Exception {
        if (ddb == null) {
            throw new Exception("Le DialogDataBase doit être initialisé avec les paramètres suivants :\n" +
                    "1) Répertoire de stockage des données de l'application\n" +
                    "2) Chemin du script SQL de création des tables de la base de données de l'application");
        }
        return statement;
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

    // // Requêtes SQL-LID (SELECT)

    // public ResultSet getNbMatchsTerminesParTournois(int idTournoi) throws SQLException {
    //     return this.statement.executeQuery("Select count(*) as total, (Select count(*) from matchs m2  WHERE m2.id_tournoi = m.id_tournoi  AND m2.termine='oui' ) as termines from matchs m  WHERE m.id_tournoi=" + idTournoi + " GROUP by id_tournoi ;");
    // }

    // public ResultSet getResultatsMatch(int idTournoi) throws SQLException {
    //     return this.statement.executeQuery("SELECT equipe,(SELECT nom_j1 FROM equipes e WHERE e.id_equipe = equipe AND e.id_tournoi = " + idTournoi + ") as joueur1,(SELECT nom_j2 FROM equipes e WHERE e.id_equipe = equipe AND e.id_tournoi = " + idTournoi + ") as joueur2, SUM(score) as score, (SELECT count(*) FROM matchs m WHERE (m.equipe1 = equipe AND m.score1 > m.score2  AND m.id_tournoi = id_tournoi) OR (m.equipe2 = equipe AND m.score2 > m.score1 )) as matchs_gagnes, (SELECT COUNT(*) FROM matchs m WHERE m.equipe1 = equipe OR m.equipe2=equipe) as matchs_joues FROM  (select equipe1 as equipe,score1 as score from matchs where id_tournoi=" + idTournoi + " UNION select equipe2 as equipe,score2 as score from matchs where id_tournoi=" + idTournoi + ") GROUP BY equipe ORDER BY matchs_gagnes DESC;");
    // }

    // public ResultSet getMatchsDataCount(int idTournoi) throws SQLException { // TODO : renommer correctement
    //     return this.statement.executeQuery("SELECT equipe, (SELECT count(*) FROM matchs m WHERE (m.equipe1 = equipe AND m.score1 > m.score2  AND m.id_tournoi = id_tournoi) OR (m.equipe2 = equipe AND m.score2 > m.score1 )) as matchs_gagnes FROM  (select equipe1 as equipe,score1 as score from matchs where id_tournoi=\" + this.id_tournoi + \" UNION select equipe2 as equipe,score2 as score from matchs where id_tournoi=\" + this.id_tournoi + \") GROUP BY equipe  ORDER BY matchs_gagnes DESC;");
    // }

    // public ResultSet getEquipesParTournoi(int idTournoi) throws SQLException {
    //     return this.statement.executeQuery("SELECT * FROM equipes WHERE id_tournoi = " + idTournoi + " ORDER BY num_equipe;");
    // }

    // public ResultSet getMatchsParTournoi(int idTournoi) throws SQLException {
    //     return this.statement.executeQuery("SELECT * FROM matchs WHERE id_tournoi="+ idTournoi + ";");
    // }

    // public ResultSet getNbToursMaxMatchParTournoi(int idTournoi) throws SQLException {
    //     return this.statement.executeQuery("SELECT MAX (num_tour)  FROM matchs WHERE id_tournoi="+idTournoi+"; ");
    // }

    // public ResultSet getNbToursParMatchParTournoi(int idTournoi) throws SQLException {
    //     return this.statement.executeQuery("Select num_tour,count(*) as tmatchs, (Select count(*) from matchs m2  WHERE m2.id_tournoi = m.id_tournoi  AND m2.num_tour=m.num_tour  AND m2.termine='oui' ) as termines from matchs m  WHERE m.id_tournoi=" + idTournoi + " GROUP BY m.num_tour,m.id_tournoi;");
    // }

    // public ResultSet getTournoisParNom(String nomTournois) throws SQLException {
    //     return this.statement.executeQuery("SELECT * FROM tournois WHERE nom_tournoi = '" + nomTournois + "';");
    // }

    // public ResultSet getTousLesTournois() throws SQLException {
    //     return this.statement.executeQuery("SELECT * FROM tournois;");
    // }

    // public ResultSet getNbMatchsParEquipes(int equipe1, int equipe2) throws SQLException {
    //     return this.statement.executeQuery("SELECT COUNT(*) FROM matchs m WHERE ( (m.equipe1 = " + equipe1 + " AND m.equipe2 = " + equipe2 + ") OR (m.equipe2 = " + equipe1 + " AND m.equipe1 = " + equipe2 + ")  )");
    // }

    // public ResultSet getEquipe(int idEquipe) throws SQLException {
    //     return this.statement.executeQuery("SELECT num_equipe FROM equipes WHERE id_equipe = " + idEquipe);
    // }

    // // Requêtes SQL-LMD (INSERT, UPDATE, DELETE)

    // public void insertMatch(Integer idMatch, Integer idTournoi, Integer numTour, Integer equipe1, Integer equipe2, String termine) throws SQLException {
    //     this.statement.executeUpdate("INSERT INTO matchs ( id_match, id_tournoi, num_tour, equipe1, equipe2, termine ) VALUES (" + (idMatch != null ? idMatch : "NULL") + ", " + idTournoi + ", "+ numTour + ", " + equipe1 + ", " +  equipe2 + ", '" + termine + "')");
    // }

    // public void updateMatch(int idMatch, Integer equipe1, Integer equipe2, Integer scoreEq1, Integer scoreEq2, String termine) throws SQLException {
    //     this.statement.executeUpdate("UPDATE matchs SET equipe1=" + equipe1 + ", equipe2=" + equipe2 + ",  score1=" + scoreEq1 + ",  score2=" + scoreEq2 + ", termine='" + termine + "' WHERE id_match = " + idMatch);
    // }

    // public void insertEquipe(Integer idEquipe, Integer numEquipe, Integer idTournoi, String nomJ1, String nomJ2) throws SQLException {
    //     String sql = "INSERT INTO equipes (id_equipe,num_equipe,id_tournoi,nom_j1,nom_j2) VALUES (" + (idEquipe != null ? idEquipe : "NULL") + ", " + numEquipe + ", " + idTournoi + ", '" + nomJ1 + "', '" + nomJ2 + "')";
    //     System.out.println(sql);
    //     this.statement.executeUpdate(sql);
    // }

    // public void setNomsJoueursEquipe(int idEquipe, String nomJ1, String nomJ2) throws SQLException {
    //     this.statement.executeUpdate("UPDATE equipes SET nom_j1 = '" + nomJ1 + "', nom_j2 = '" + nomJ2 + "' WHERE id_equipe = " + idEquipe);
    // }

    // public void setNumEquipe(int idTournoi, int numEquipe) throws SQLException {
    //     this.statement.executeUpdate("UPDATE equipes SET num_equipe = num_equipe - 1 WHERE id_tournoi = " + idTournoi + " AND num_equipe > " + numEquipe);
    // }

    // public void insertTournoi(Integer idTournoi, Integer nbMatchs, String nomTournoi, Integer statut) throws SQLException {
    //     this.statement.executeUpdate("INSERT INTO tournois (id_tournoi, nb_matchs, nom_tournoi, statut) VALUES (" + (idTournoi != null ? idTournoi : "NULL") + ", " + nbMatchs + ", '" + nomTournoi + "', " + statut + ")");
    // }

    // public void setStatutTournoi(int statut, int idTournoi) throws SQLException {
    //     this.statement.executeUpdate("UPDATE tournois SET statut=" + statut + " WHERE id_tournoi=" + idTournoi);
    // }

    // public void deleteMatch(int idTournoi, int numTour) throws SQLException {
    //     this.statement.executeUpdate("DELETE FROM matchs WHERE id_tournoi="+ idTournoi+" AND num_tour=" + numTour);
    // }

    // public void deleteMatch(int idTournoi) throws SQLException {
    //     this.statement.executeUpdate("DELETE FROM matchs WHERE id_tournoi=" + idTournoi);
    // }

    // public void deleteEquipe(int idTournoi, int idEquipe) throws SQLException {
    //     this.statement.executeUpdate("DELETE FROM equipes WHERE id_tournoi=" + idTournoi + " AND id_equipe=" + idEquipe);
    // }

    // public void deleteEquipe(int idTournoi) throws SQLException {
    //     this.statement.executeUpdate("DELETE FROM equipes WHERE id_tournoi=" + idTournoi);
    // }

    // public void deleteTournoi(int idTournoi) throws SQLException {
    //     this.statement.executeUpdate("DELETE FROM tournois WHERE id_tournoi=" + idTournoi);
    // }

}
