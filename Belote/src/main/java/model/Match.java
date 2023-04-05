package model;


/**
 * La classe Match représente un match dans un tournoi.
 */
public class Match {

	private boolean termine;
	private int idMatch;
	private int equipe1;
	private int equipe2;
	private int score1;
	private int score2;
	private int numeroTour;

	/**
	 * Constructeur pour initialiser tous les attributs d'un match.
	 * @param pfIdMatch : l'identifiant unique du match.
	 * @param pfEquipe1 : l'identifiant de la première équipe.
	 * @param pfEquipe2 : l'identifiant de la seconde équipe.
	 * @param pfScore1 : le score de la première équipe.
	 * @param pfScore2 : le score de la seconde équipe.
	 * @param pfNumeroTour : le numéro de tour auquel appartient le match.
	 * @param pfTermine : Indique si le match est terminé ou non.
	 */
	public Match(int pfIdMatch, int pfEquipe1, int pfEquipe2, int pfScore1, int pfScore2, int pfNumeroTour, boolean pfTermine) {
		this.idMatch = pfIdMatch;
		this.equipe1 = pfEquipe1;
		this.equipe2 = pfEquipe2;
		this.score1 = pfScore1;
		this.score2 = pfScore2;
		this.numeroTour = pfNumeroTour;
		this.termine = pfTermine;
	}

	/**
	 * Constructeur pour initialiser les identifiants des deux équipes.
	 * @param pfEquipe1 L'identifiant de la première équipe.
	 * @param pfEquipe2 L'identifiant de la seconde équipe.
	 */
	public Match(int pfEquipe1, int pfEquipe2) {
		this.equipe1 = pfEquipe1;
		this.equipe2 = pfEquipe2;
	}

	/**
	 * Retourne une chaîne de caractères décrivant le match.
	 * @return Une chaîne de caractères décrivant le match.
	 */
	public String toString() {
		if (this.equipe1 < this.equipe2) {
			return "  " + this.equipe1 + " contre " + this.equipe2;
		} else {
			return "  " + this.equipe2 + " contre " + this.equipe1;
		}
	}

	// ----- Getters -----

	/**
	 * Retourne une chaîne de caractères décrivant le match.
	 * @return Une chaîne de caractères décrivant le match.
	 */
	public int getIdMatch() {
		return idMatch;
	}

	/**
	 * Retourne le numéro de tour auquel appartient le match.
	 * @return Le numéro de tour auquel appartient le match.
	 */
	public int getNumeroTour() {
		return numeroTour;
	}

	/**
	 * Indique si le match est terminé ou non.
	 * @return Vrai si le match est terminé, faux sinon.
	 */
	public boolean isTermine() {
		return termine;
	}

	/**
	 * Retourne l'identifiant de la première équipe.
	 * @return L'identifiant de la première équipe.
	 */
	public int getEquipe1() {
		return equipe1;
	}

	/**
	 * Retourne l'identifiant de la seconde équipe.
	 * @return L'identifiant de la seconde équipe.
	 */
	public int getEquipe2() {
		return equipe2;
	}

	/**
	 * Retourne le score de la première équipe.
	 * @return le score de la première équipe.
	 */
	public int getScore1() {
		return score1;
	}

	/**
	 * Retourne le score de la deuxième équipe.
	 * @return le score de la deuxième équipe.
	 */
	public int getScore2() {
		return score2;
	}

	// ----- Setters -----

	/**
	 * Modifie l'identifiant unique du match.
	 * @param idMatch : le nouvel identifiant unique du match.
	 */
	public void setIdMatch(int idMatch) {
		this.idMatch = idMatch;
	}

	/**
	 * Met à jour le numéro du tour du match.
	 * @param numTour : le nouveau numéro de tour
	 */
	public void setNumeroTour(int numTour) {
		this.numeroTour = numTour;
	}

	/**
	 * Met à jour le statut du match (terminé ou non).
	 * @param termine : le nouveau statut du match
	 */
	public void setTermine(boolean termine) {
		this.termine = termine;
	}

	/**
	 * Met à jour l'équipe 1 du match.
	 * @param equipe1 : le nouvel identifiant de l'équipe 1
	 */
	public void setEquipe1(int equipe1) {
		this.equipe1 = equipe1;
	}

	/**
	 * Met à jour l'équipe 2 du match.
	 * @param equipe2 : le nouvel identifiant de l'équipe 2
	 */
	public void setEquipe2(int equipe2) {
		this.equipe2 = equipe2;
	}

	/**
	 * Met à jour le score de l'équipe 1 du match.
	 * @param score1 : le nouveau score de l'équipe 1
	 */
	public void setScore1(int score1) {
		this.score1 = score1;
	}

	/**
	 * Met à jour le score de l'équipe 2 du match.
	 * @param score2 : le nouveau score de l'équipe 2
	 */
	public void setScore2(int score2) {
		this.score2 = score2;
	}

}