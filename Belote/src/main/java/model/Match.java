package model;


public class Match {

	private boolean termine;
	private int idMatch;
	private int equipe1;
	private int equipe2;
	private int score1;
	private int score2;
	private int numeroTour;

	public Match(int pfIdMatch, int pfEquipe1, int pfEquipe2, int pfScore1,
				 int pfScore2, int pfNumeroTour, boolean pfTermine) {
		this.idMatch = pfIdMatch;
		this.equipe1 = pfEquipe1;
		this.equipe2 = pfEquipe2;
		this.score1 = pfScore1;
		this.score2 = pfScore2;
		this.numeroTour = pfNumeroTour;
		this.termine = pfTermine;
	}

	public Match(int pfEquipe1, int pfEquipe2) {
		this.equipe1 = pfEquipe1;
		this.equipe2 = pfEquipe2;
	}

	public String toString() {
		if (this.equipe1 < this.equipe2) {
			return "  " + this.equipe1 + " contre " + this.equipe2;
		} else {
			return "  " + this.equipe2 + " contre " + this.equipe1;
		}
	}

	public boolean isTermine() {
		return termine;
	}

	public void setTermine(boolean termine) {
		this.termine = termine;
	}

	public int getIdMatch() {
		return idMatch;
	}

	public void setIdMatch(int idMatch) {
		this.idMatch = idMatch;
	}

	public int getEquipe1() {
		return equipe1;
	}

	public void setEquipe1(int equipe1) {
		this.equipe1 = equipe1;
	}

	public int getEquipe2() {
		return equipe2;
	}

	public void setEquipe2(int equipe2) {
		this.equipe2 = equipe2;
	}

	public int getScore1() {
		return score1;
	}

	public void setScore1(int score1) {
		this.score1 = score1;
	}

	public int getScore2() {
		return score2;
	}

	public void setScore2(int score2) {
		this.score2 = score2;
	}

	public int getNumeroTour() {
		return numeroTour;
	}

	public void setNumeroTour(int numTour) {
		this.numeroTour = numTour;
	}

}