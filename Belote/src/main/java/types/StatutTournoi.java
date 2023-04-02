/**

Enumération représentant les différents statuts possibles pour un tournoi.
*/

package types;


public enum StatutTournoi {

    INCONNU(-1, "Inconnu"),
    INSCRIPTION(0, "Inscription des joueurs"),
    GENERATION(1, "Génération des matchs"),
    EN_COURS(2, "Matchs en cours"),
    TERMINE(3, "Terminé");


    private final String libelle;
    private final Integer ordre;

    /**
     * 
     * Constructeur privé de l'énumération.
     * 
     * @param pfOrdre   ordre du statut
     * @param pfLibelle libellé du statut
     */
    StatutTournoi(int pfOrdre, String pfLibelle) {
        this.libelle = pfLibelle;
        this.ordre = pfOrdre;
    }

    /**
     * 
     * Retourne le libellé du statut.
     * 
     * @return le libellé du statut
     */
    public String getLibelle() {
        return libelle;
    }

    /**
     * 
     * Retourne l'ordre du statut.
     * 
     * @return l'ordre du statut
     */
    public Integer getOrdre() {
        return ordre;
    }

    /**
     * 
     * Retourne l'objet StatutTournoi correspondant à l'ordre donné.
     * Si aucun statut correspondant n'est trouvé, retourne le statut "Inconnu".
     * 
     * @param pfOrdre l'ordre du statut recherché
     * @return l'objet StatutTournoi correspondant à l'ordre donné
     */
    public static StatutTournoi getStatut(int pfOrdre) {
        for (StatutTournoi statut : StatutTournoi.values()) {
            if (statut.getOrdre() == pfOrdre) {
                return statut;
            }
        }
        return INCONNU;
    }

}
