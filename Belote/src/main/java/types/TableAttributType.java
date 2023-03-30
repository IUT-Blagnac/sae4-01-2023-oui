package types;


public enum TableAttributType {

    // Tournoi
    ID_TOURNOI("id_tournoi"),
    NB_MATCHS("nb_matchs"),
    NOM_TOURNOI("nom_tournoi"),
    STATUT("statut"),

    // Equipe
    ID_EQUIPE("id_equipe"),
    NUM_EQUIPE("num_equipe"),
    NOM_J1("nom_j1"),
    NOM_J2("nom_j2");


    private final String columnName;

    TableAttributType(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return this.columnName;
    }

}
