package fr.blagnac.com.model;

public class Match {
    private int equipe1;
    private int equipe2;

    public Match(int pfEquipe1, int pfEquipe2) {
        this.equipe1 = pfEquipe1;
        this.equipe2 = pfEquipe2;
    }

    public String toString() {
        if (this.equipe1 < this.equipe2) {
            return "  " + this.equipe1 + " contre " + this.equipe2;
        } else {
            return "  " + this.equipe1 + " contre " + this.equipe2;
        }
    }
}
