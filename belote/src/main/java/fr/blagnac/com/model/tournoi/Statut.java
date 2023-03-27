package fr.blagnac.com.model.tournoi;

public enum Statut {
    INCONNU(-1, "Inconnu"),
    INSCRIPTION(0, "Inscription des joueurs"),
    GENERATION(1, "Génération des matchs"),
    EN_COURS(2, "Matchs en cours"),
    TERMINE(3, "Terminé");

    private final String libelle;
    private final int ordre;

    Statut(int pfOrdre, String pfLibelle) {
        this.libelle = pfLibelle;
        this.ordre = pfOrdre;
    }

    public String getLibelle() {
        return libelle;
    }

    public int getOrdre() {
        return ordre;
    }

    public static Statut getStatut(int pfOrdre) {
        for (Statut statut : Statut.values()) {
            if (statut.getOrdre() == pfOrdre) {
                return statut;
            }
        }
        return INCONNU;
    }
}