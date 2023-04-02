package model;


public 	class Equipe {

	private int id;
	private int numero;
	private String equipe1;
	private String equipe2;

	public Equipe(int pfId, int pfNumero, String pfEquipe1, String pfEquipe2) {
		this.id = pfId;
		this.numero = pfNumero;
		this.equipe1 = pfEquipe1;
		this.equipe2 = pfEquipe2;
	}

	// ----- Getters -----

	public int getId() {
		return id;
	}

	public int getNumero() {
		return numero;
	}

	public String getEquipe1() {
		return equipe1;
	}

	public String getEquipe2() {
		return equipe2;
	}

	// ----- Setters -----

	public void setId(int id) {
		this.id = id;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public void setEquipe1(String equipe1) {
		this.equipe1 = equipe1;
	}

	public void setEquipe2(String equipe2) {
		this.equipe2 = equipe2;
	}





}
