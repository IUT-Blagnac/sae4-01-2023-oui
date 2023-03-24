package fr.blagnac.com;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Belote {

	// --PAS A TESTER-----------------------------------------
	static class Match {
		public int eq1, eq2;

		public Match(int e1, int e2) {
			eq1 = e1;
			eq2 = e2;
		}

		public String toString() {
			if (eq1 < eq2) {
				return "  " + eq1 + " contre " + eq2;
			} else {
				return "  " + eq2 + " contre " + eq1;
			}
		}
	}

	public static void main(String[] args) throws SQLException {

		// Connection and statement creation
		Connection connection = null;
		Statement statement = null;
		String createFile = "/create.sql";
		String beloteDir = System.getProperty("user.dir") + "/jBelote";

		try {
			Class.forName("org.hsqldb.jdbcDriver").newInstance();

			if (!new File(beloteDir).isDirectory()) {
				new File(beloteDir).mkdir();
			}
			connection = DriverManager.getConnection("jdbc:hsqldb:file:" + beloteDir + "/belote", "sa", "");
			statement = connection.createStatement();

			// Get the SQL script
			InputStream createFileInputStream = Belote.class.getResourceAsStream(createFile);
			importSQL(connection, createFileInputStream);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Impossible de se connecter à la base de donnée. Vérifier qu'une autre instance du logiciel n'est pas déjà ouverte.");
			System.out.println(e.getMessage());
			System.exit(0);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erreur lors de l'initialisation du logiciel. Vérifiez votre installation Java et vos droits d'acc�s sur le dossier AppData.");
			System.out.println(e.getMessage());
			System.exit(0);
		}

		// Graphical interface
		Fenetre fenetre = new Fenetre(statement);
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Close the connection to the database
		statement.execute("SHUTDOWN;");
		statement.close();
		connection.close();
	}

	public static void importSQL(Connection conn, InputStream in) throws SQLException, FileNotFoundException {
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
				if (line.trim().length() > 0) st.execute(line);
			}
		} finally {
			// release resources
			if (st != null) st.close();
			scaner.close();
		}
	}
}
