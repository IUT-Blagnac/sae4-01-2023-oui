/**
 * Cette classe permet de dialoguer avec la base de données de l'application. Elle est implémentée comme un Singleton.
 * Elle utilise un fichier de configuration pour charger les paramètres de connexion à la base de données.
 */

package control.dialogs;

import view.Fenetre;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class DialogDataBase {

    /**
     * Instance unique de la classe DialogDataBase.
     */
    private static DialogDataBase ddb;
    /**
     * Objet Statement permettant d'exécuter des requêtes SQL.
     */
    private static Statement statement;
    /**
     * Objet Connection permettant de se connecter à la base de données.
     */
    private Connection connection;
    /**
     * Objet Properties permettant de charger le fichier de configuration de la base
     * de données.
     */
    private Properties properties = new Properties();

    /**
     * Constructeur privé de la classe DialogDataBase.
     * 
     * @param beloteDir          répertoire de stockage des données de
     *                           l'application.
     * @param createFile         chemin du script SQL de création des tables de la
     *                           base de données de l'application.
     * @param databaseConfigFile chemin du fichier de configuration de la base de
     *                           données.
     * @throws SQLException          en cas d'erreur lors de la connexion à la base
     *                               de données.
     * @throws FileNotFoundException si le fichier de création des tables n'est pas
     *                               trouvé.
     */
    private DialogDataBase(String beloteDir, String createFile, String databaseConfigFile)
            throws SQLException, FileNotFoundException {
        this.loadConfig(databaseConfigFile);
        this.connection = DriverManager.getConnection(
                properties.getProperty("DBURL") + beloteDir + "/" + properties.getProperty("DBName"),
                properties.getProperty("DBUser"), properties.getProperty("DBPassword"));
        statement = this.connection.createStatement();
        InputStream createFileInputStream = this.getClass().getResourceAsStream(createFile);
        importSQL(this.connection, createFileInputStream);
    }

    /**
     * Constructeur privé de la classe DialogDataBase.
     * 
     * @param beloteDir          répertoire de stockage des données de
     *                           l'application.
     * @param createFile         chemin du script SQL de création des tables de la
     *                           base de données de l'application.
     * @param databaseConfigFile chemin du fichier de configuration de la base de
     *                           données.
     * @throws SQLException          en cas d'erreur lors de la connexion à la base
     *                               de données.
     * @throws FileNotFoundException si le fichier de création des tables n'est pas
     *                               trouvé.
     */
    public static DialogDataBase initialize(String beloteDir, String createFile, String databaseConfigFile)
            throws SQLException, FileNotFoundException {
        if (ddb == null) {
            ddb = new DialogDataBase(beloteDir, createFile, databaseConfigFile);
        }
        return ddb;
    }

    /**
     * Retourne le statement de la connexion à la base de données.
     * 
     * @return le statement de la connexion à la base de données.
     * @throws Exception si le DialogDataBase n'a pas été initialisé.
     */
    public static Statement getStatement() throws Exception {
        if (ddb == null) {
            throw new Exception("Le DialogDataBase doit être initialisé avec les paramètres suivants :\n" +
                    "1) Répertoire de stockage des données de l'application\n" +
                    "2) Chemin du script SQL de création des tables de la base de données de l'application");
        }
        return statement;
    }

    /**
     * Importe un fichier SQL dans la base de données.
     * 
     * @param conn la connexion à la base de données.
     * @param in   le fichier SQL à importer.
     * @throws SQLException          en cas d'erreur lors de l'exécution des
     *                               requêtes SQL.
     * @throws FileNotFoundException si le fichier SQL n'est pas trouvé.
     */
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

    /**
     * Importe un fichier SQL dans la base de données.
     * 
     * @param conn la connexion à la base de données.
     * @param in   le fichier SQL à importer.
     * @throws SQLException          en cas d'erreur lors de l'exécution des
     *                               requêtes SQL.
     * @throws FileNotFoundException si le fichier SQL n'est pas trouvé.
     */
    private void loadConfig(String dataBaseConfigFile) {
        try {
            InputStream inputStream = this.getClass().getResourceAsStream(dataBaseConfigFile);
            properties.load(inputStream);
        } catch (Exception e) {
            Fenetre.afficherErreur("Erreur lors du chargement du fichier de configuration de la base de données "
                    + dataBaseConfigFile + ".");
        }
    }

}
