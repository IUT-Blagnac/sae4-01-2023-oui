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
    NOM_J2("nom_j2"),

    // Match
    ID_MATCH("id_match"),
    NUM_TOUR("num_tour"),
    EQUIPE1("equipe1"),
    EQUIPE2("equipe2"),
    SCORE1("score1"),
    SCORE2("score2"),
    TERMINE("termine"),

    // Spécial (alias)
    EQUIPE("equipe"),
    SCORE("score"),
    MATCHS_GAGNES("matchs_gagnes"),
    MATCHS_JOUES("matchs_joues"),
    TOTAL("total"),
    MAX_NUM_TOUR("max_num_tour");


    private final String columnName;

    TableAttributType(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return this.columnName;
    }

}
