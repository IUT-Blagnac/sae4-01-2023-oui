package control.dialogs;


import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;


public class DialogDataBase {

    private static DialogDataBase ddb;
    private static Statement statement;
    private Connection connection;
    private Properties properties = new Properties();

    private DialogDataBase(String beloteDir, String createFile) throws SQLException, FileNotFoundException {
        loadConfig();
        this.connection = DriverManager.getConnection(properties.getProperty("DBURL") + beloteDir + "/" + properties.getProperty("DBName"), properties.getProperty("DBUser"), properties.getProperty("DBPassword"));
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

    private void loadConfig() {
        try {
            InputStream inputStream = this.getClass().getResourceAsStream("/database.properties");
            properties.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
