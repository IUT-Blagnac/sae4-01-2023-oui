package types;


public enum StatutTournoi {

    INCONNU(-1, "Inconnu"),
    INSCRIPTION(0, "Inscription des joueurs"),
    GENERATION(1, "Génération des matchs"),
    EN_COURS(2, "Matchs en cours"),
    TERMINE(3, "Terminé");


    private final String libelle;
    private final Integer ordre;

    StatutTournoi(int pfOrdre, String pfLibelle) {
        this.libelle = pfLibelle;
        this.ordre = pfOrdre;
    }

    public String getLibelle() {
        return libelle;
    }

    public Integer getOrdre() {
        return ordre;
    }

    public static StatutTournoi getStatut(int pfOrdre) {
        for (StatutTournoi statut : StatutTournoi.values()) {
            if (statut.getOrdre() == pfOrdre) {
                return statut;
            }
        }
        return INCONNU;
    }

}
