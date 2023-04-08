package types;


/**
 * Constantes utilisées pour la classe view Fenetre
 */
public enum FenetreConstant {

    TOURNOIS("Tournois"),
    DETAIL("Paramètres du tournoi"),
    EQUIPES("Equipes"),
    TOURS("Tours"),
    MATCHS("Matchs"),
    RESULTATS("Resultats");


    private String name;

    FenetreConstant(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

}
