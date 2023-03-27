package fr.blagnac.com;


import fr.blagnac.com.control.database.DialogDataBase;
import fr.blagnac.com.view.Fenetre;
import java.io.File;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class BeloteApp {

	public static void main(String[] args) throws SQLException {

		String beloteDir = System.getProperty("user.dir") + "/jBelote";
		DialogDataBase ddb = null;

		try {
			Class.forName("org.hsqldb.jdbcDriver").newInstance();
			if (!new File(beloteDir).isDirectory()) {
				new File(beloteDir).mkdir();
			}

			// Get the DB dialog
			ddb = DialogDataBase.getInstance(beloteDir, "/create.sql");

			// Graphical interface
			Fenetre fenetre = new Fenetre(); //new Fenetre(statement);
			fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Impossible de se connecter à la base de donnée. Vérifier qu'une autre instance du logiciel n'est pas déjà ouverte.");
			System.out.println(e.getMessage());
			System.exit(0);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erreur lors de l'initialisation du logiciel. Vérifiez votre installation Java et vos droits d'acc�s sur le dossier AppData.");
			System.out.println(e.getMessage());
			System.exit(0);
		} /*finally {
			// Close the connection to the database
			System.out.println("Fermeture de la connexion avec la BD");
			statement.execute("SHUTDOWN;");
			statement.close();
			connection.close();
		}*/
	}

}
