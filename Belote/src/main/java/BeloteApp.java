import control.dialogs.DialogDataBase;
import view.Fenetre;
import java.io.File;
import java.sql.SQLException;
import javax.swing.JFrame;


public class BeloteApp {

	public static void main(String[] args) {

		String beloteDir = System.getProperty("user.dir") + "/jBelote";
		String createFile = "/create.sql";
		String dataBaseConfigFile = "/database.properties";

		try {
			Class.forName("org.hsqldb.jdbcDriver").newInstance();
			if (!new File(beloteDir).isDirectory()) {
				new File(beloteDir).mkdir();
			}

			// Initialisation du Dialog de la base de données
			DialogDataBase.initialize(beloteDir, createFile, dataBaseConfigFile);

			// Graphical interface
			Fenetre fenetre = new Fenetre();
			fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		} catch (SQLException e) {
			Fenetre.afficherInformation(
					"Erreur lors de la connexion à la base de données, une autre instance du logiciel est peut-être déjà ouverte.");
			System.exit(0);
		} catch (Exception e) {
			Fenetre.afficherInformation(
					"Erreur lors du lancement de l'application, vérifier l'installation Java.");
			System.exit(0);
		}
	}

}
