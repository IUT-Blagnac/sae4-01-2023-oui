import control.dialogs.DialogDataBase;
import view.Fenetre;
import java.io.File;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class BeloteApp {

	public static void main(String[] args) {

		String beloteDir = System.getProperty("user.dir") + "/jBelote";

		try {
			Class.forName("org.hsqldb.jdbcDriver").newInstance();
			if (!new File(beloteDir).isDirectory()) {
				new File(beloteDir).mkdir();
			}

			// Initialisation du Dialog de la base de données
			DialogDataBase.initialize(beloteDir, "/create.sql");

			// Graphical interface
			Fenetre fenetre = new Fenetre();
			fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		} catch (SQLException e) { // TODO : rediriger vers la classe Fenetre
			JOptionPane.showMessageDialog(null,
					"Impossible de se connecter à la base de donnée. Vérifier qu'une autre instance du logiciel n'est pas déjà ouverte.");
			System.out.println(e.getMessage());
			System.exit(0);
		} catch (Exception e) { // TODO : rediriger vers la classe Fenetre
			JOptionPane.showMessageDialog(null,
					"Erreur lors de l'initialisation du logiciel. Vérifiez votre installation Java et vos droits d'acc�s sur le dossier AppData.");
			System.out.println(e.getMessage());
			System.exit(0);
		}
	}

}
