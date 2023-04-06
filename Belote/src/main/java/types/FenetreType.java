package types;

public enum FenetreType {
    TOURNOIS("Tournois"),
    DETAIL("Paramètres du tournoi"),
    EQUIPES("Equipes"),
    TOURS("Tours"),
    MATCHS("Matchs"),
    RESULTATS("Resultats");

    private String name;

    FenetreType(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

}
