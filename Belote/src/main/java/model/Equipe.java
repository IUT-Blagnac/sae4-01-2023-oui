/**
 * Cette classe représente une équipe dans un tournoi.
 * Elle contient l'identifiant de l'équipe, son numéro, ainsi que les noms des deux joueurs qui la composent.
 */

package model;


public 	class Equipe {

	/**
	 * Identifiant de l'équipe.
	 */
	private int id;
	/**
	 * Numéro de l'équipe.
	 */
	private int numero;
	/**
	 * Nom du premier joueur de l'équipe.
	 */
	private String equipe1;
	/**
	 * Nom du deuxième joueur de l'équipe.
	 */
	private String equipe2;

	/**
	 * Constructeur de la classe Equipe.
	 * 
	 * @param pfId      L'identifiant de l'équipe.
	 * @param pfNumero  Le numéro de l'équipe.
	 * @param pfEquipe1 Le nom du premier joueur de l'équipe.
	 * @param pfEquipe2 Le nom du deuxième joueur de l'équipe.
	 */
	public Equipe(int pfId, int pfNumero, String pfEquipe1, String pfEquipe2) {
		this.id = pfId;
		this.numero = pfNumero;
		this.equipe1 = pfEquipe1;
		this.equipe2 = pfEquipe2;
	}

	// ----- Getters -----

	/**
	 * Getter pour l'identifiant de l'équipe.
	 * 
	 * @return L'identifiant de l'équipe.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Getter pour le numéro de l'équipe.
	 * 
	 * @return Le numéro de l'équipe.
	 */
	public int getNumero() {
		return numero;
	}

	/**
	 * Getter pour le nom du premier joueur de l'équipe.
	 * 
	 * @return Le nom du premier joueur de l'équipe.
	 */
	public String getEquipe1() {
		return equipe1;
	}

	/**
	 * Getter pour le nom du deuxième joueur de l'équipe.
	 * 
	 * @return Le nom du deuxième joueur de l'équipe.
	 */
	public String getEquipe2() {
		return equipe2;
	}

	// ----- Setters -----

	/**
	 * Setter pour l'identifiant de l'équipe.
	 * 
	 * @param id Le nouvel identifiant de l'équipe.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Setter pour l'identifiant de l'équipe.
	 * 
	 * @param numero Le nouvel identifiant de l'équipe.
	 */
	public void setNumero(int numero) {
		this.numero = numero;
	}

	/**
	 * Setter pour le nom du premier joueur de l'équipe.
	 * 
	 * @param equipe1 Le nouveau nom du premier joueur de l'équipe.
	 */
	public void setEquipe1(String equipe1) {
		this.equipe1 = equipe1;
	}

	/**
	 * Setter pour le nom du deuxième joueur de l'équipe.
	 * 
	 * @param equipe2 Le nouveau nom du deuxième joueur de l'équipe.
	 */
	public void setEquipe2(String equipe2) {
		this.equipe2 = equipe2;
	}
}
