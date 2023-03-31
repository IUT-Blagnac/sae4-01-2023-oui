package control.actors;


import control.actors.specialQueries.SpecialQuery;
import control.actors.specialQueries.SpecialQueryFactory;
import control.dialogs.DialogDataBase;
import types.QueryType;
import types.SpecialQueryType;
import types.TableAttributType;
import view.Fenetre;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class Actor {

    private Statement ddbStatement = null;
    private String tableName = null;

    public Actor(String tableName) {
        try {
            this.ddbStatement = DialogDataBase.getStatement();
            this.tableName = tableName;
        } catch (Exception e) {
            Fenetre.afficherErreur("Erreur lors de la création d'un acteur pour le type " + tableName +
                ", un acteur ne peut pas être créé sans une connexion à la base de données.");
        }
    }

    public ResultSet get(List<TableAttributType> filtres, Map<TableAttributType, String> parametresWhere, List<TableAttributType> orderingColumns) throws Exception {
        String strSQL = "SELECT ";
        if (filtres != null && filtres.size() > 0) {
            String strFiltres = "";
            int i = 0;
            for (TableAttributType column: filtres) {
                strFiltres += column.getColumnName() + (i < filtres.size() - 1 ? "," : "");
                i++;
            }
            strSQL += strFiltres;
        } else {
            strSQL += "*";
        }
        strSQL += " FROM " + this.tableName + " ";
        if (parametresWhere != null && parametresWhere.size() > 0) {
            parametresWhere = this.formateParametresMap(parametresWhere);
            String strWhere = "";
            int i = 0;
            for (Map.Entry<TableAttributType, String> pw: parametresWhere.entrySet()) {
                strWhere += pw.getKey().getColumnName() + "=" + pw.getValue() + (i < parametresWhere.size() - 1 ? " AND " : "");
                i++;
            }
            strSQL += "WHERE " + strWhere;
        }
        if (orderingColumns != null && orderingColumns.size() > 0) {
            String strOrdering = " ORDER BY ";
            int i = 0;
            for (TableAttributType column: orderingColumns) {
                strOrdering += column.getColumnName() + (i < parametresWhere.size() - 1 ? "," : "");
                i++;
            }
            strSQL += strOrdering;
        }
        return this.ddbStatement.executeQuery(strSQL);
    }

    public void add(Map<TableAttributType, String> parametresValues) throws Exception {
        if (parametresValues == null || parametresValues.size() == 0) {
            throw new Exception("Au moins un paramètre est attendu pour l'ajout d'un enregistrement dans la table " + this.tableName + ".");
        }
        parametresValues = this.formateParametresMap(parametresValues);
        String strColumn = "", strValues = "";
        int i = 0;
        for (Map.Entry<TableAttributType, String> pv: parametresValues.entrySet()) {
            strColumn += pv.getKey().getColumnName() + (i < parametresValues.size() - 1 ? "," : "");
            strValues += pv.getValue() + (i < parametresValues.size() - 1 ? "," : "");
            i++;
        }
        this.ddbStatement.executeUpdate("INSERT INTO " + this.tableName + " (" + strColumn + ") VALUES (" + strValues + ")");
    }

    public void set(Map<TableAttributType, String> parametresValues, Map<TableAttributType, String> parametresWhere) throws Exception {
        if (parametresValues == null || parametresValues.size() == 0) {
            throw new Exception("Au moins un paramètre est attendu pour la modification d'un enregistrement dans la table " + this.tableName + ".");
        }
        String strSQL = "UPDATE " + tableName + " SET ";
        parametresValues = this.formateParametresMap(parametresValues);
        String strValues = "", strWhere = "";
        int i = 0;
        for (Map.Entry<TableAttributType, String> pv: parametresValues.entrySet()) {
            strValues += pv.getKey().getColumnName() + "=" + pv.getValue() + (i < parametresValues.size() - 1 ? "," : "");
            i++;
        }
        strSQL += strValues;
        if (parametresWhere != null && parametresWhere.size() > 0) {
            parametresWhere = this.formateParametresMap(parametresWhere);
            i = 0;
            for (Map.Entry<TableAttributType, String> pw: parametresWhere.entrySet()) {
                strWhere += pw.getKey().getColumnName() + "=" + pw.getValue() + (i < parametresWhere.size() - 1 ? " AND " : "");
                i++;
            }
            strSQL += " WHERE " + strWhere;
        }
        this.ddbStatement.executeUpdate(strSQL);
    }

    public void remove(Map<TableAttributType, String> parametresWhere) throws Exception {
        String strSQL = "DELETE FROM " + this.tableName;
        if (parametresWhere != null && parametresWhere.size() > 0) {
            parametresWhere = this.formateParametresMap(parametresWhere);
            String strWhere = "";
            int i = 0;
            for (Map.Entry<TableAttributType, String> pw: parametresWhere.entrySet()) {
                strWhere += pw.getKey().getColumnName() + "=" + pw.getValue() + (i < parametresWhere.size() - 1 ? " AND " : "");
                i++;
            }
            strSQL += " WHERE " + strWhere;
        }
        this.ddbStatement.executeUpdate(strSQL);
    }

    public ResultSet specialQuery(SpecialQueryType sqt, QueryType qt, List<String> parametres) throws Exception {
        parametres = this.formateParametresList(parametres);
        SpecialQuery sq = SpecialQueryFactory.getSpecialQuery(sqt, parametres);
        switch (qt) {
            case QUERY:
                return this.ddbStatement.executeQuery(sq.getStrSQL());
            case UPDATE:
                this.ddbStatement.executeUpdate(sq.getStrSQL());
                break;
            default:
                throw new Exception("Le type de requête " + qt + " n'a pas été trouvé.");
        }
        return null;
    }

    // Formatage SQL des paramètres

    private Map<TableAttributType, String> formateParametresMap(Map<TableAttributType, String> parametres) {
        Map<TableAttributType, String> formatedParametres = new HashMap<>();
        for (Map.Entry<TableAttributType, String> p: parametres.entrySet()) {
            formatedParametres.put(p.getKey(), this.formateParametresValue(p.getValue()));
        }
        return formatedParametres;
    }

    private List<String> formateParametresList(List<String> parametres) {
        List<String> formatedParametres = new ArrayList<>();
        for (String value: parametres) {
            formatedParametres.add(this.formateParametresValue(value));
        }
        return formatedParametres;
    }

    private String formateParametresValue(String value) {
        String formatedValue;
        try {
            formatedValue = ""+Integer.parseInt(value);
        } catch (NumberFormatException e) {
            if (value.equals("null")) {
                formatedValue = "NULL";
            } else {
                formatedValue = "'" + value + "'";
            }
        }
        return formatedValue;
    }

}
