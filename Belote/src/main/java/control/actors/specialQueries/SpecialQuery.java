package control.actors.specialQueries;


/**
 * Cette classe abstraite est utilisée pour définir une requête spéciale à exécuter sur la base de données.
 */
public abstract class SpecialQuery {

    /**
     * Requête SQL à exécuter sur la base de données.
     */
    protected String strSQL = null;

    /**
     * Getter pour la requête SQL à exécuter.
     * @return la requête SQL à exécuter.
     */
    public String getStrSQL() {
        return this.strSQL;
    }

}
