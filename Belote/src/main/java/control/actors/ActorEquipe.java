package control.actors;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import control.dialogs.DialogDataBase;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public abstract class ActorEquipe implements Actor {

    private final Statement ddbStatement;

    public ActorEquipe() throws Exception {
        this.ddbStatement = DialogDataBase.getStatement();
    }

    @Override
    public ResultSet get(List<String> filtres, Map<String, String> parametresWhere) throws SQLException {
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
        if (parametresWhere.size() > 0) {
            String strWhere = "";
            int i = 0;
            for (Map.Entry<String, String> pW : parametresWhere.entrySet()) {
                strWhere += pW.getKey() + "=" + pW.getValue() + (i < parametresWhere.size() - 1 ? "," : "");
                i++;
            }
            strSQL += " WHERE " + strWhere;
        }
        return this.ddbStatement.executeQuery(strSQL + " ORDER BY num_equipe");
    }

    @Override
    public void add(Map<String, String> parametresValues) throws SQLException, Exception {
        if (parametresValues.size() == 0) {
            throw new Exception("Au moins un paramètre est attendu pour l'ajout d'une équipe dans la base de données.");
        }
        String strColumn = "", strValues = "";
        int i = 0;
        for (Map.Entry<String, String> pV : parametresValues.entrySet()) {
            strColumn += pV.getKey() + (i < parametresValues.size() - 1 ? "," : "");
            strValues += pV.getValue() + (i < parametresValues.size() - 1 ? "," : "");
            i++;
        }
        this.ddbStatement.executeUpdate("INSERT INTO Equipes (" + strColumn + ") VALUES (" + strValues + ")");
    }

    @Override
    public void set(Map<String, String> parametresValues, Map<String, String> parametresWhere) throws SQLException, Exception {
        String strSQL = "UPDATE Equipes SET ";
        if (parametresValues.size() == 0) {
            throw new Exception("Au moins un paramètre est attendu pour la modification d'une équipe dans la base de données.");
        }
        String strValues = "", strWhere = "";
        int i = 0;
        for (Map.Entry<String, String> pV : parametresValues.entrySet()) {
            strValues += pV.getKey() + "=" + (i < parametresValues.size() - 1 ? "," : "");
            i++;
        }
        strSQL += strValues;
        if (parametresWhere.size() > 0) {
            i = 0;
            for (Map.Entry<String, String> pW : parametresWhere.entrySet()) {
                strWhere += pW.getKey() + "=" + (i < parametresValues.size() - 1 ? "," : "");
                i++;
            }
            strSQL += " WHERE " + strWhere;
        }
        this.ddbStatement.executeUpdate(strSQL);
    }

    @Override
    public void remove(Map<String, String> parametresWhere) throws SQLException {
        String strSQL = "DELETE FROM Equipes";
        if (parametresWhere.size() > 0) {
            String strWhere = "";
            int i = 0;
            for (Map.Entry<String, String> pW : parametresWhere.entrySet()) {
                strWhere += pW.getKey() + "=" + pW.getValue() + (i < parametresWhere.size() - 1 ? "," : "");
                i++;
            }
            strSQL += " WHERE " + strWhere;
        }
        this.ddbStatement.executeUpdate(strSQL);
    }

    // TODO : mettre dans une classe Tool/appart ?
    public List<String> getColumnsFromTable() throws SQLException {
        List<String> strColumns = new ArrayList<>();
        ResultSet columns = this.ddbStatement.executeQuery("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'Equipes' ORDER BY ORDINAL_POSITION");
        int i=1;
        while (columns.next()) {
            strColumns.add(columns.getString(i));
            i++;
        }
        return strColumns;
    }

    // TODO : EXPERIMENTAL, à supprimer !
    public Statement getStatement() {
        return this.ddbStatement;
    }

}
