import control.dialogs.DialogDataBase;
import view.Fenetre;
import java.io.File;
import java.sql.SQLException;
import javax.swing.JFrame;


/**
 * Classe principale de l'application de Belote.
 * Cette classe contient la méthode "main" qui lance l'application. Elle initialise le répertoire de l'application,
 * crée les tables de la base de données si nécessaire, et lance l'interface graphique de l'application.
 * Les paramètres nécessaires pour la création de la base de données sont stockés dans un fichier de propriétés.
 * En cas d'erreur lors de la connexion à la base de données ou lors du lancement de l'application, un message
 * d'erreur est affiché à l'utilisateur.
 */
public class BeloteApp {

	public static void main(String[] args) {

		String beloteDir = System.getProperty("user.dir") + "/jBelote";
		String createFile = "/create.sql";
		String dataBaseConfigFile = "/database.properties";

		try {
			Class.forName("org.hsqldb.jdbcDriver").newInstance();
			if (!new File(beloteDir).isDirectory()) {new File(beloteDir).mkdir();
			}

			// Initialisation du Dialog de la base de données
			DialogDataBase.initialize(beloteDir, createFile, dataBaseConfigFile);

			// Graphical interface
			Fenetre fenetre = new Fenetre();
			fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		} catch (SQLException e) {
			Fenetre.afficherErreur("Erreur lors de la connexion à la base de données, une autre instance du logiciel est peut-être déjà ouverte.");
			System.exit(0);
		} catch (Exception e) {
			Fenetre.afficherErreur("Erreur lors du lancement de l'application, vérifier l'installation Java.");
			System.exit(0);
		}
	}

}
