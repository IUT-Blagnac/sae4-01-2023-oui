/**
 * Cette classe abstraite représente un acteur de la base de données. Les acteurs sont des classes
 * abstraites qui contiennent des méthodes pour exécuter des requêtes sur les tables de la base de données.
 * Les classes dérivées d'Actor sont utilisées pour les requêtes spéciales et les tables spécifiques de la base de données.
 * 
 * Cette classe contient des méthodes pour exécuter les requêtes SELECT, INSERT, UPDATE et DELETE sur la table
 * correspondante en utilisant des paramètres fournis par l'utilisateur. Elle possède également un constructeur
 * qui initialise une connexion à la base de données et un nom de table pour l'acteur.
 */

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Actor {

    private Statement ddbStatement = null;
    private String tableName = null;

    /**
     * Constructeur de la classe Actor. Initialise une connexion à la base de
     * données et définit le nom de table pour l'acteur.
     * 
     * @param tableName le nom de la table correspondante dans la base de données
     */
    public Actor(String tableName) {
        try {
            this.ddbStatement = DialogDataBase.getStatement();
            this.tableName = tableName;
        } catch (Exception e) {
            Fenetre.afficherErreur("Erreur lors de la création d'un acteur pour le type " + tableName +
                    ", un acteur ne peut pas être créé sans une connexion à la base de données.");
        }
    }

    /**
     * Exécute une requête SELECT sur la table correspondante avec les filtres, les
     * paramètres de la clause WHERE et les colonnes
     * d'ordonnancement fournis en paramètres. Retourne le résultat sous forme de
     * ResultSet.
     * 
     * @param filtres         la liste des colonnes à sélectionner
     * @param parametresWhere la map des paramètres de la clause WHERE
     * @param orderingColumns la liste des colonnes d'ordonnancement
     * @return le résultat de la requête sous forme de ResultSet
     * @throws Exception si une erreur se produit lors de l'exécution de la requête
     */
    public ResultSet get(List<TableAttributType> filtres, Map<TableAttributType, String> parametresWhere,
            List<TableAttributType> orderingColumns) throws Exception {
        String strSQL = "SELECT ";
        if (filtres != null && filtres.size() > 0) {
            String strFiltres = "";
            int i = 0;
            for (TableAttributType column : filtres) {
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
            for (Map.Entry<TableAttributType, String> pw : parametresWhere.entrySet()) {
                strWhere += pw.getKey().getColumnName() + "=" + pw.getValue()
                        + (i < parametresWhere.size() - 1 ? " AND " : "");
                i++;
            }
            strSQL += "WHERE " + strWhere;
        }
        if (orderingColumns != null && orderingColumns.size() > 0) {
            String strOrdering = " ORDER BY ";
            int i = 0;
            for (TableAttributType column : orderingColumns) {
                strOrdering += column.getColumnName() + (i < parametresWhere.size() - 1 ? "," : "");
                i++;
            }
            strSQL += strOrdering;
        }
        return this.ddbStatement.executeQuery(strSQL);
    }

    /**
     * 
     * Ajoute un enregistrement dans la table avec les paramètres donnés.
     * 
     * @param parametresValues Une map contenant les paramètres et leur valeur
     *                         associée.
     * @throws Exception Si parametresValues est null ou vide.
     */
    public void add(Map<TableAttributType, String> parametresValues) throws Exception {
        if (parametresValues == null || parametresValues.size() == 0) {
            throw new Exception("Au moins un paramètre est attendu pour l'ajout d'un enregistrement dans la table "
                    + this.tableName + ".");
        }
        parametresValues = this.formateParametresMap(parametresValues);
        String strColumn = "", strValues = "";
        int i = 0;
        for (Map.Entry<TableAttributType, String> pv : parametresValues.entrySet()) {
            strColumn += pv.getKey().getColumnName() + (i < parametresValues.size() - 1 ? "," : "");
            strValues += pv.getValue() + (i < parametresValues.size() - 1 ? "," : "");
            i++;
        }
        this.ddbStatement
                .executeUpdate("INSERT INTO " + this.tableName + " (" + strColumn + ") VALUES (" + strValues + ")");
    }

    /**
     * 
     * Modifie un ou plusieurs enregistrements de la table avec les valeurs passées
     * en paramètre.
     * Si le paramètre parametresValues est nul ou vide, une exception est levée.
     * Si le paramètre parametresWhere est non nul et non vide, la modification se
     * fait seulement sur les enregistrements qui correspondent aux conditions
     * spécifiées dans parametresWhere.
     * Les paramètres passés en argument doivent être valides selon les attributs de
     * la table correspondante.
     * 
     * @param parametresValues un Map de TableAttributType et String contenant les
     *                         valeurs à modifier pour chaque attribut de la table.
     * @param parametresWhere  un Map de TableAttributType et String contenant les
     *                         conditions pour la sélection des enregistrements à
     *                         modifier.
     * @throws Exception si le paramètre parametresValues est nul ou vide.
     * @throws Exception si une erreur SQL survient lors de l'exécution de la
     *                   requête de modification.
     */
    public void set(Map<TableAttributType, String> parametresValues, Map<TableAttributType, String> parametresWhere)
            throws Exception {
        if (parametresValues == null || parametresValues.size() == 0) {
            throw new Exception(
                    "Au moins un paramètre est attendu pour la modification d'un enregistrement dans la table "
                            + this.tableName + ".");
        }
        String strSQL = "UPDATE " + tableName + " SET ";
        parametresValues = this.formateParametresMap(parametresValues);
        String strValues = "", strWhere = "";
        int i = 0;
        for (Map.Entry<TableAttributType, String> pv : parametresValues.entrySet()) {
            strValues += pv.getKey().getColumnName() + "=" + pv.getValue()
                    + (i < parametresValues.size() - 1 ? "," : "");
            i++;
        }
        strSQL += strValues;
        if (parametresWhere != null && parametresWhere.size() > 0) {
            parametresWhere = this.formateParametresMap(parametresWhere);
            i = 0;
            for (Map.Entry<TableAttributType, String> pw : parametresWhere.entrySet()) {
                strWhere += pw.getKey().getColumnName() + "=" + pw.getValue()
                        + (i < parametresWhere.size() - 1 ? " AND " : "");
                i++;
            }
            strSQL += " WHERE " + strWhere;
        }
        this.ddbStatement.executeUpdate(strSQL);
    }

    /**
     * 
     * Supprime un ou plusieurs enregistrements dans la table correspondante en
     * fonction des paramètres de filtrage fournis.
     * 
     * @param parametresWhere un Map contenant les colonnes de la table à utiliser
     *                        comme filtres et leur valeur correspondante
     * @throws Exception si aucun paramètre de filtrage n'est fourni, ou si une
     *                   erreur survient lors de l'exécution de la requête SQL
     */
    public void remove(Map<TableAttributType, String> parametresWhere) throws Exception {
        String strSQL = "DELETE FROM " + this.tableName;
        if (parametresWhere != null && parametresWhere.size() > 0) {
            parametresWhere = this.formateParametresMap(parametresWhere);
            String strWhere = "";
            int i = 0;
            for (Map.Entry<TableAttributType, String> pw : parametresWhere.entrySet()) {
                strWhere += pw.getKey().getColumnName() + "=" + pw.getValue()
                        + (i < parametresWhere.size() - 1 ? " AND " : "");
                i++;
            }
            strSQL += " WHERE " + strWhere;
        }
        this.ddbStatement.executeUpdate(strSQL);
    }

    /**
     * 
     * Exécute une requête spéciale en fonction du type de requête spéciale et du
     * type de requête spécifié. Les paramètres de requête sont optionnels et
     * dépendent du type de requête spéciale.
     * 
     * @param sqt        Le type de requête spéciale à exécuter.
     * @param qt         Le type de requête à exécuter (QUERY ou UPDATE).
     * @param parametres Les paramètres de requête pour la requête spéciale
     *                   (optionnels).
     * @return Le résultat de la requête sous forme de ResultSet (si le type de
     *         requête est QUERY) ou null (si le type de requête est UPDATE).
     * @throws Exception si une erreur se produit lors de l'exécution de la requête.
     */
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

    // ----------------- Formatage SQL des paramètres -----------------

    /**
     * 
     * Formate les valeurs d'un map de paramètres en appelant la méthode
     * "formateParametresValue" pour chaque valeur.
     * Les clés du map sont de type "TableAttributType" et les valeurs sont de type
     * "String".
     * 
     * @param parametres Map de paramètres à formater.
     * @return Map de paramètres formatés.
     */
    private Map<TableAttributType, String> formateParametresMap(Map<TableAttributType, String> parametres) {
        Map<TableAttributType, String> formatedParametres = new HashMap<>();
        for (Map.Entry<TableAttributType, String> p : parametres.entrySet()) {
            formatedParametres.put(p.getKey(), this.formateParametresValue(p.getValue()));
        }
        return formatedParametres;
    }

    /**
     * 
     * Cette méthode prend une liste de paramètres et renvoie une nouvelle liste où
     * chaque paramètre est formaté pour être utilisé dans une requête SQL.
     * 
     * @param parametres La liste de paramètres à formater.
     * @return La liste de paramètres formatée pour une utilisation dans une requête
     *         SQL.
     */
    private List<String> formateParametresList(List<String> parametres) {
        List<String> formatedParametres = new ArrayList<>();
        for (String value : parametres) {
            formatedParametres.add(this.formateParametresValue(value));
        }
        return formatedParametres;
    }

    /**
     * 
     * Formate une valeur de paramètre selon le type de données attendu dans la base
     * de données.
     * Si la valeur est un entier, elle est convertie en chaîne de caractères. Si la
     * valeur est "null", elle est
     * remplacée par le mot-clé SQL "NULL". Sinon, la valeur est entourée de
     * guillemets simples pour
     * être considérée comme une chaîne de caractères en SQL.
     * 
     * @param value la valeur à formater
     * @return la valeur formatée
     */
    private String formateParametresValue(String value) {
        String formatedValue;
        try {
            formatedValue = "" + Integer.parseInt(value);
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
