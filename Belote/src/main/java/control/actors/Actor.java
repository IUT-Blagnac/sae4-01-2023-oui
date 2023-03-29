package control.actors;


import control.actors.specialQueries.SpecialQuery;
import control.actors.specialQueries.SpecialQueryFactory;
import control.dialogs.DialogDataBase;
import types.QueryType;
import types.SpecialQueryType;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public abstract class Actor {

    private Statement ddbStatement = null;
    private String tableName = null;
    private final List<String> tableColumns = null; // Experimental

    public Actor(String tableName) {
        try {
            this.ddbStatement = DialogDataBase.getStatement();
            this.tableName = tableName;
            // this.tableColumns = this.getColumnsFromTable();
        } catch (Exception e) {
            System.out.println(e.getMessage()); // TODO : popup
        }
    }

    public ResultSet get(List<String> filtres, Map<String, String> parametresWhere) throws Exception {
        String strSQL = "SELECT ";
        if (filtres.size() > 0) {
            String strFiltres = "";
            int i = 0;
            for (String column: filtres) {
                strFiltres += column + (i < filtres.size() - 1 ? "," : "");
                i++;
            }
            strSQL += strFiltres;
        } else {
            strSQL += "*";
        }
        strSQL += " FROM " + this.tableName + " ";
        if (parametresWhere.size() > 0) {
            String strWhere = "";
            int i = 0;
            for (Map.Entry<String, String> pw : parametresWhere.entrySet()) {
                strWhere += pw.getKey() + "=" + pw.getValue() + (i < parametresWhere.size() - 1 ? " AND " : "");
                i++;
            }
            strSQL += "WHERE " + strWhere;
        }
        return this.ddbStatement.executeQuery(strSQL); // + " ORDER BY num_equipe");
    }

    public void add(Map<String, String> parametresValues) throws Exception {
        if (parametresValues.size() == 0) {
            throw new Exception("Au moins un paramètre est attendu pour l'ajout d'un enregistrement dans la table " + this.tableName + ".");
        }
        String strColumn = "", strValues = "";
        int i = 0;
        for (Map.Entry<String, String> pv : parametresValues.entrySet()) {
            strColumn += pv.getKey() + (i < parametresValues.size() - 1 ? "," : "");
            strValues += pv.getValue() + (i < parametresValues.size() - 1 ? "," : "");
            i++;
        }
        this.ddbStatement.executeUpdate("INSERT INTO " + this.tableName + " (" + strColumn + ") VALUES (" + strValues + ")");
    }

    public void set(Map<String, String> parametresValues, Map<String, String> parametresWhere) throws Exception {
        String strSQL = "UPDATE " + tableName + " SET ";
        if (parametresValues.size() == 0) {
            throw new Exception("Au moins un paramètre est attendu pour la modification d'un enregistrement dans la table " + this.tableName + ".");
        }
        String strValues = "", strWhere = "";
        int i = 0;
        for (Map.Entry<String, String> pv : parametresValues.entrySet()) {
            strValues += pv.getKey() + "=" + pv.getValue() + (i < parametresValues.size() - 1 ? "," : "");
            i++;
        }
        strSQL += strValues;
        if (parametresWhere.size() > 0) {
            i = 0;
            for (Map.Entry<String, String> pw : parametresWhere.entrySet()) {
                strWhere += pw.getKey() + "=" + pw.getValue() + (i < parametresWhere.size() - 1 ? " AND " : "");
                i++;
            }
            strSQL += " WHERE " + strWhere;
        }
        this.ddbStatement.executeUpdate(strSQL);
    }

    public void remove(Map<String, String> parametresWhere) throws Exception {
        String strSQL = "DELETE FROM " + this.tableName;
        if (parametresWhere.size() > 0) {
            String strWhere = "";
            int i = 0;
            for (Map.Entry<String, String> pw : parametresWhere.entrySet()) {
                strWhere += pw.getKey() + "=" + pw.getValue() + (i < parametresWhere.size() - 1 ? " AND " : "");
                i++;
            }
            strSQL += " WHERE " + strWhere;
        }
        this.ddbStatement.executeUpdate(strSQL);
    }

    public ResultSet specialQuery(SpecialQueryType sqt, QueryType qt, List<String> parametres) throws Exception {
        SpecialQuery sq = SpecialQueryFactory.getSpecialQuery(sqt, parametres);
        switch (qt) {
            case QUERY:
                return this.ddbStatement.executeQuery(sq.getStrSQL());
            case UPDATE:
                this.ddbStatement.executeUpdate(sq.getStrSQL());
                break;
            default:
                throw new Exception("Le type de requête " + qt.toString() + " n'a pas été trouvé.");
        }
        return null;
    }

    // Méthodes utiles (EXPERIMENTAL)

    public List<String> getColumnsFromTable() throws Exception {
        List<String> strColumns = new ArrayList<>();
        ResultSet columns = this.ddbStatement.executeQuery("SELECT column_name FROM user_tab_columns WHERE table_name = '" + this.tableName.toUpperCase() + "';");
        int i=1;
        while (columns.next()) {
            strColumns.add(columns.getString(i));
            i++;
        }
        return strColumns;
    }

    private void verifierParametres(Map<String, String> parametres) throws Exception {
        for (String column : parametres.keySet()) {
            if (!this.tableColumns.contains(column.toLowerCase())) {
                throw new Exception("La colonne " + column + " renseignées n'existe pas dans la table " + this.tableName + ".");
            }
        }
    }

    // TODO : EXPERIMENTAL, à supprimer !
    public Statement getStatement() {
        return this.ddbStatement;
    }

}
