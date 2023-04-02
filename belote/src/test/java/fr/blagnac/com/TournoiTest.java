package fr.blagnac.com;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;

public class TournoiTest {
    private Tournoi tournoi;
    private Statement statement;
    private String nom;

    @Before
    public void setUp() throws Exception {
        String dos = System.getProperty("user.dir");
		String beloteFolder = dos + "/jBelote";
        if (!new File(beloteFolder).isDirectory()) {
			new File(beloteFolder).mkdir();
		}
        Class.forName("org.hsqldb.jdbcDriver").newInstance();
        Connection connection = DriverManager.getConnection("jdbc:hsqldb:file:"+ beloteFolder +"/belote", "sa", "");
        InputStream createFileInputStream = Belote.class.getResourceAsStream("/create.sql");
        this.nom = "Test";
        this.statement = connection.createStatement();
        Belote.importSQL(connection, createFileInputStream);
        this.tournoi = new Tournoi(nom, statement);
    }

    @Test
    public void constructor() throws SQLException, Exception {
        assertEquals(nom, tournoi.getNom());
        assertEquals(statement, tournoi.st); // TODO: Changer st en getStatement() plus tard

        ResultSet resultSet = this.statement.executeQuery("SELECT * FROM tournois WHERE nom_tournoi = '" + Tournoi.mysql_real_escape_string(nom) + "';");
        assertTrue(resultSet.next());

        int status = resultSet.getInt("statut");
        int idTournoi = resultSet.getInt("id_tournoi");
        resultSet.close();

        assertEquals(status, tournoi.statut);
        assertEquals(idTournoi, tournoi.id_tournoi);

		switch(status){
		case 0:
            assertEquals("Inscription des joueurs", this.tournoi.statuttnom);
		break;
		case 1:
            assertEquals("Génération des matchs", this.tournoi.statuttnom);
		break;
		case 2:
            assertEquals("Matchs en cours", this.tournoi.statuttnom);
		break;
		case 3:
            assertEquals("Terminé", this.tournoi.statuttnom);
		break;
		default:
            assertEquals("Inconnu", this.tournoi.statuttnom);
            break;
		}
    }
}
