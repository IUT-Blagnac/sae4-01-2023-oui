package control.actors;


import control.actors.specialQueries.SpecialQuery;
import control.actors.specialQueries.SpecialQueryFactory;
import control.dialogs.DialogDataBase;
import types.QueryType;
import types.SpecialQueryType;
import types.TableAttributType;
import view.Fenetre;
import java.sql.ResultSet;
import java.sql.Statement;
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
            Fenetre.afficherInformation("Erreur lors de la création d'un acteur pour le type " + tableName +
                ", un acteur ne peut pas être créé sans une connexion à la base de données.");
        }
    }

    public ResultSet get(List<TableAttributType> filtres, Map<TableAttributType, String> parametresWhere) throws Exception {
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
            String strWhere = "";
            int i = 0;
            for (Map.Entry<TableAttributType, String> pw : parametresWhere.entrySet()) {
                strWhere += pw.getKey().getColumnName() + "=" + pw.getValue() + (i < parametresWhere.size() - 1 ? " AND " : "");
                i++;
            }
            strSQL += "WHERE " + strWhere;
        }
        return this.ddbStatement.executeQuery(strSQL);
    }

    public void add(Map<TableAttributType, String> parametresValues) throws Exception {
        if (parametresValues == null || parametresValues.size() == 0) {
            throw new Exception("Au moins un paramètre est attendu pour l'ajout d'un enregistrement dans la table " + this.tableName + ".");
        }
        String strColumn = "", strValues = "";
        int i = 0;
        for (Map.Entry<TableAttributType, String> pv : parametresValues.entrySet()) {
            strColumn += pv.getKey().getColumnName() + (i < parametresValues.size() - 1 ? "," : "");
            strValues += pv.getValue() + (i < parametresValues.size() - 1 ? "," : "");
            i++;
        }
        this.ddbStatement.executeUpdate("INSERT INTO " + this.tableName + " (" + strColumn + ") VALUES (" + strValues + ")");
    }

    public void set(Map<TableAttributType, String> parametresValues, Map<TableAttributType, String> parametresWhere) throws Exception {
        String strSQL = "UPDATE " + tableName + " SET ";
        if (parametresValues == null || parametresValues.size() == 0) {
            throw new Exception("Au moins un paramètre est attendu pour la modification d'un enregistrement dans la table " + this.tableName + ".");
        }
        String strValues = "", strWhere = "";
        int i = 0;
        for (Map.Entry<TableAttributType, String> pv : parametresValues.entrySet()) {
            strValues += pv.getKey().getColumnName() + "=" + pv.getValue() + (i < parametresValues.size() - 1 ? "," : "");
            i++;
        }
        strSQL += strValues;
        if (parametresWhere.size() > 0) {
            i = 0;
            for (Map.Entry<TableAttributType, String> pw : parametresWhere.entrySet()) {
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
            String strWhere = "";
            int i = 0;
            for (Map.Entry<TableAttributType, String> pw : parametresWhere.entrySet()) {
                strWhere += pw.getKey().getColumnName() + "=" + pw.getValue() + (i < parametresWhere.size() - 1 ? " AND " : "");
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
                throw new Exception("Le type de requête " + qt + " n'a pas été trouvé.");
        }
        return null;
    }

    // TODO : EXPERIMENTAL, à supprimer !
    public Statement getStatement() {
        return this.ddbStatement;
    }

}
