package model;


import control.dialogs.DialogEquipe;
import control.dialogs.DialogMatch;
import control.dialogs.DialogTournoi;
import resources.Tools;
import types.StatutTournoi;
import types.TableAttributType;
import view.Fenetre;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Vector;


/**
 * Cette classe représente un tournoi avec un nom, un statut, un identifiant,
 *   une liste d'équipes, une liste de matchs et une liste d'identifiants d'équipes.
 */
public class Tournoi {

	String nomTournoi;
	StatutTournoi statut;
	int id_tournoi;
	private Vector<Equipe> dataeq = null;
	private Vector<Match> datam  = null;
	private Vector<Integer>ideqs  = null;

	// Dialogs
	private final DialogEquipe dialogEquipe = new DialogEquipe();
	private final DialogMatch dialogMatch = new DialogMatch();
	private final DialogTournoi dialogTournoi = new DialogTournoi();

	/**
	 * Constructeur de la classe Tournoi.
	 * @param nt : le nom du tournoi.
	 */
	public Tournoi(String nt) {
		try {
			ResultSet rs = dialogTournoi.getTournoiParNom(Tools.mysql_real_escape_string(nt));
			if (!rs.next()) {
				return ;
			}
			this.statut = StatutTournoi.getStatut(rs.getInt(TableAttributType.STATUT.getColumnName()));
			this.id_tournoi = rs.getInt(TableAttributType.ID_TOURNOI.getColumnName());
			rs.close();
		} catch (Exception e) {
			Fenetre.afficherErreur("Erreur lors du test de l'existence du tournoi.");
			System.out.println(e.getMessage()); // Message développeur
		}
		if (this.statut == null) {
			this.statut = StatutTournoi.INCONNU;
        }
	}

	/**
	 * Met à jour la liste des équipes du tournoi.
	 */
	public void majEquipes() {
		dataeq = new Vector<>();
		ideqs = new Vector<>();
		try {
			ResultSet rs = dialogEquipe.getEquipeDUnTournoi(this.id_tournoi);
			while (rs.next()) {
				dataeq.add(new Equipe(rs.getInt(TableAttributType.ID_EQUIPE.getColumnName()),rs.getInt(TableAttributType.NUM_EQUIPE.getColumnName()), rs.getString(TableAttributType.NOM_J1.getColumnName()), rs.getString(TableAttributType.NOM_J2.getColumnName())));
				ideqs.add(rs.getInt(TableAttributType.NUM_EQUIPE.getColumnName()));
			}
			rs.close();
		} catch (Exception e) {
			Fenetre.afficherErreur("Erreur lors de la mise à jour des équipes.");
			System.out.println(e.getMessage()); // Message développeur
		}
	}

	/**
	 * Met à jour les matchs d'un tournoi en récupérant les données depuis la base de données et en les stockant dans un vecteur.
	 * Les données stockées dans le vecteur datam sont des objets Match.
	 * Si une exception est levée lors de la récupération des données depuis la base de données,
	 *   une erreur est affichée dans la fenêtre.
	 */
	public void majMatch() {
		datam = new Vector<>();
		try {
			ResultSet rs= dialogMatch.getMatchsDUnTournoi(id_tournoi);
			while(rs.next()) datam.add(new Match(rs.getInt(TableAttributType.ID_MATCH.getColumnName()),rs.getInt(TableAttributType.EQUIPE1.getColumnName()),rs.getInt(TableAttributType.EQUIPE2.getColumnName()), rs.getInt(TableAttributType.SCORE1.getColumnName()),rs.getInt(TableAttributType.SCORE2.getColumnName()),rs.getInt(TableAttributType.NUM_TOUR.getColumnName()),rs.getString(TableAttributType.TERMINE.getColumnName()).equals("oui")));
			rs.close();
		} catch (Exception e) {
			Fenetre.afficherErreur("Erreur lors de la mise à jour des matchs.");
			System.out.println(e.getMessage()); // Message développeur
		}
	}

	// ----- Getters -----

	/**
	 * Renvoie l'identifiant du tournoi.
	 * @return L'identifiant du tournoi.
	 */
	public int getIdTournoi() {
		return this.id_tournoi;
	}

	/**
	 * Renvoie le match à l'index spécifié. Si les données des matchs n'ont pas été mises à jour,
	 *   la fonction met à jour les données avant de renvoyer le match.
	 * @param index : l'index du match.
	 * @return Le match à l'index spécifié.
	 */
	public Match getMatch(int index) {
		if (datam == null) majMatch();
		return datam.get(index);
	}

	/**
	 * Renvoie le nombre de matchs. Si les données des matchs n'ont pas été mises à jour,
	 *   la fonction met à jour les données avant de renvoyer le nombre de matchs.
	 * @return Le nombre de matchs.
	 */
	public int getNbMatchs() {
		if (datam == null) majMatch();
		return datam.size();
	}

	/**
	 * Renvoie l'équipe à l'index spécifié. Si les données des équipes n'ont pas été
	 * mises à jour, la fonction met à jour les données avant de renvoyer l'équipe.
	 * @param index : l'index de l'équipe.
	 * @return l'équipe à l'index spécifié.
	 */
	public Equipe getEquipe(int index) {
		if (dataeq == null) majEquipes();
		return dataeq.get(index);
	}

	/**
	 * Renvoie le nombre d'équipes. Si les données des équipes n'ont pas été mises à jour,
	 *   la fonction met à jour les données avant de renvoyer le nombre d'équipes.
	 * @return Le nombre d'équipes.
	 */
	public int getNbEquipes() {
		if (dataeq == null) majEquipes();
		return dataeq.size();
	}

	/**
	 * Renvoie le statut du tournoi.
	 * @return Le statut du tournoi.
	 */
	public StatutTournoi getStatut() {
		return statut;
	}

	/**
	 * Renvoie le nom du tournoi.
	 * @return Le nom du tournoi.
	 */
	public String getNom() {
		return nomTournoi;
	}

	/**
	 * Cette fonction récupère le nombre de tours pour le tournoi courant à partir de la base de données.
	 * Elle retourne le nombre de tours si l'opération s'est bien déroulée,
	 *   sinon elle retourne -1 et affiche une erreur.
	 * @return Le nombre de tours du tournoi courant ou -1 en cas d'erreur.
	 */
	public int getNbTours() {
		try {
			ResultSet rs = dialogMatch.getNbToursMaxMatch(id_tournoi);
			rs.next();
			return rs.getInt(TableAttributType.MAX_NUM_TOUR.getColumnName());
		} catch (Exception e) {
			Fenetre.afficherErreur("Erreur lors de la récupération du nombre de tours du tournoi.");
			System.out.println(e.getMessage()); // Message développeur
			return -1;
		}
	}

	/**
	 * Cette fonction génère les matchs du tournoi courant à partir de la base de données.
	 */
	public void genererMatchs() {
		int nbt = 1;
		Vector<Vector<Match>> ms;
		ms = Tournoi.getMatchsToDo(getNbEquipes(), nbt);
		int z = 1;
		try {
			for (Vector<Match> t :ms) {
				for (Match m:t) {
					dialogMatch.addMatch(null, this.id_tournoi, z, m.getEquipe1(), m.getEquipe2(), "non");
				}
				z++;
			}
			dialogTournoi.setStatutTournoi(StatutTournoi.EN_COURS, this.id_tournoi);
			this.statut = StatutTournoi.getStatut(2);
		} catch(Exception e){
			Fenetre.afficherErreur("Erreur lors de la validation des équipes du tournoi.");
			System.out.println(e.getMessage()); // Message développeur
		}
	}

	/**
	 * Cette fonction ajoute un tour de plus au tournoi courant en générant les matchs à jouer pour ce tour.
	 * Si le nombre maximum de tours a déjà été atteint, la fonction ne fait rien.
	 * Si une erreur se produit lors de l'opération, une erreur s'affiche à l'écran.
	 */
	public void ajouterTour() {
		int nbtoursav;
		if (getNbTours() >=  (getNbEquipes() -1)) return;
		try {
			ResultSet rs = dialogMatch.getNbToursMaxMatch(this.id_tournoi);
			rs.next();
			nbtoursav = rs.getInt(TableAttributType.MAX_NUM_TOUR.getColumnName());
			rs.close();
		} catch (Exception e) {
			Fenetre.afficherErreur("Erreur lors de la récupération du nombre de tours du tournoi.");
			System.out.println(e.getMessage()); // Message développeur
			return;
		}
		if (nbtoursav == 0) {
			Vector<Match> ms;
			ms = Objects.requireNonNull(Tournoi.getMatchsToDo(getNbEquipes(), nbtoursav + 1)).lastElement();
			try {
				for(Match m:ms){
					dialogMatch.addMatch(null, this.id_tournoi, (nbtoursav + 1), m.getEquipe1(), m.getEquipe2(), "non");
				}
			} catch(Exception e){
				Fenetre.afficherErreur("Erreur lors de l'insertion du match.");
				System.out.println(e.getMessage()); // Message développeur
			}
		} else {
			try {
				ResultSet rs;
				rs = dialogMatch.getDonneesTours(this.id_tournoi);
				ArrayList<Integer> ordreeq= new ArrayList<>();
				while (rs.next()) {
					ordreeq.add(rs.getInt(TableAttributType.EQUIPE.getColumnName()));
				}
				int i;
				boolean fini;
				while (ordreeq.size() > 1) {
					int j=0;
					while (j<ordreeq.size()) {
						j++;
					}
					i=1;
					do {
						rs = dialogMatch.getNbMatchsParEquipes(ordreeq.get(0), ordreeq.get(i-1));
						rs.next();
						if (rs.getInt(TableAttributType.NB_MATCHS.getColumnName()) > 0) {
							i++;
							fini = false;
						} else {
							fini = true;
							dialogMatch.addMatch(null, this.id_tournoi, (nbtoursav + 1), ordreeq.get(0), ordreeq.get(i), "non");
							ordreeq.remove(0);
							ordreeq.remove(i-1);
						}
					} while(!fini);
				}
			} catch (Exception e) {
				Fenetre.afficherErreur("Erreur lors de la récupération des matchs du tournoi.");
				System.out.println(e.getMessage()); // Message développeur
			}
		}
	}

	/**
	 * Cette méthode permet de supprimer le dernier tour d'un tournoi ainsi que les matchs associés.
	 */
	public void supprimerTour() {
		int nbtoursav;
		try {
			ResultSet rs = dialogMatch.getNbToursMaxMatch(this.id_tournoi);
			rs.next();
			nbtoursav = rs.getInt(TableAttributType.MAX_NUM_TOUR.getColumnName());
			rs.close();
		} catch (Exception e) {
			Fenetre.afficherErreur("Erreur lors de la récupération du nombre de tours du tournoi.");
			System.out.println(e.getMessage()); // Message développeur
			return ;
		}
		try {
			dialogMatch.removeMatchDUnTour(this.id_tournoi, nbtoursav);
		} catch (Exception e) {
			Fenetre.afficherErreur("Erreur lors de la suppression du match.");
			System.out.println(e.getMessage()); // Message développeur
		}
	}

	/**
	 * Cette méthode permet d'ajouter une équipe dans le tournoi.
	 * L'indice de l'équipe est déterminé automatiquement.
	 */
	public void ajouterEquipe() {
		int a_aj= this.dataeq.size()+1;
		for ( int i=1;i <= this.dataeq.size(); i++){
			if(!ideqs.contains(i)){
				a_aj=i;
				break;
			}
		}
		try {
			dialogEquipe.addEquipe(null, a_aj, this.id_tournoi, "Joueur 1", "Joueur 2");
		    majEquipes();
		} catch (Exception e) {
			Fenetre.afficherErreur("Erreur lors de l'ajout de l'équipe.");
			System.out.println(e.getMessage()); // Message développeur
		}
	}

	/**
	 * Cette méthode permet de mettre à jour les noms des joueurs d'une équipe.
	 * @param index : l'indice de l'équipe à mettre à jour.
	 */
	public void majEquipe(int index) {
		try {
			dialogEquipe.setNomsJoueursDUneEquipe(getEquipe(index).getId(),
					Tools.mysql_real_escape_string(getEquipe(index).getEquipe1()),
					Tools.mysql_real_escape_string(getEquipe(index).getEquipe2()));
		    majEquipes();
		} catch (Exception e) {
			Fenetre.afficherErreur("Erreur lors de la mise à jour de l'équipe.");
			System.out.println(e.getMessage()); // Message développeur
		}
	}

	/**
	 * Cette méthode permet de mettre à jour le score d'un match.
	 * @param index L'indice du match à mettre à jour.
	 */
	public void majMatch(int index) {
		String termine = (getMatch(index).getScore1() > 0 || getMatch(index).getScore2() > 0) ? "oui":"non";
		try {
			dialogMatch.setScoresMatch(getMatch(index).getIdMatch(), getMatch(index).getEquipe1(), getMatch(index).getEquipe2(), getMatch(index).getScore1(), getMatch(index).getScore2(), termine);
		} catch (Exception e) {
			Fenetre.afficherErreur("Erreur lors de la mise à jour du match.");
			System.out.println(e.getMessage()); // Message développeur
		}
		majMatch();
	}

	/**
	 * Supprime une équipe de ce tournoi.
	 * @param ideq : l'identifiant de l'équipe à supprimer
	 */
	public void supprimerEquipe(int ideq) {
		try {
			int numeq;
			ResultSet rs = dialogEquipe.getNumDUneEquipe(ideq);
			rs.next();
			numeq = rs.getInt(TableAttributType.NUM_EQUIPE.getColumnName());
			rs.close();
			dialogEquipe.removeUneEquipe(this.id_tournoi, ideq);
			dialogEquipe.setNumEquipesDUnTournoi(this.id_tournoi, numeq);
		    majEquipes();
		} catch (Exception e) {
			Fenetre.afficherErreur("Erreur lors de la suppression de l'équipe.");
			System.out.println(e.getMessage()); // Message développeur
		}
	}

	/**
	 * Récupère les matchs à faire pour un tournoi avec un nombre de joueurs et de tours donnés.
	 * @param nbJoueurs : le nombre de joueurs ou d'équipes dans le tournoi
	 * @param nbTours : le nombre de tours que le tournoi doit avoir
	 * @return un vecteur de vecteurs de matchs à jouer, chaque vecteur de matchs correspondant à un tour
	 */
	public static Vector<Vector<Match>> getMatchsToDo(int nbJoueurs, int nbTours) {
		if (nbTours >= nbJoueurs) {
			Fenetre.afficherErreur("Erreur lors de la récupération des matchs à faire, le nombre de tours est supérieur ou égal au nombre d'équipes.");
			return null;
		}
		int[] tabJoueurs;
		if ((nbJoueurs % 2) == 1) {
			tabJoueurs = new int[nbJoueurs+1];
			tabJoueurs[nbJoueurs] = -1;
			for (int z = 0; z < nbJoueurs;z++) {
				tabJoueurs[z] = z+1;
			}
			nbJoueurs++;
		} else {
			tabJoueurs = new int[nbJoueurs];
			for (int z = 0; z < nbJoueurs;z++) {
				tabJoueurs[z] = z+1;
			}
		}
		boolean quitter;
		int i, increment = 1, temp;
		Vector<Vector<Match>> retour = new Vector<>();
		Vector<Match> vm;
		for (int r = 1; r <= nbTours;r++) {
			if (r > 1) {
				temp = tabJoueurs[nbJoueurs - 2];
				for (i = (nbJoueurs - 2) ; i > 0; i--) {
					tabJoueurs[i] = tabJoueurs[i-1];
				}
				tabJoueurs[0] = temp;
			}
			i = 0;
			quitter = false;
			vm = new Vector<>();
			while (!quitter) {
				if (!(tabJoueurs[i] == -1 || tabJoueurs[nbJoueurs - 1  - i] == -1)) {
					vm.add(new Match(tabJoueurs[i], tabJoueurs[nbJoueurs - 1  - i]));
				}
		        i+= increment;
				if (i >= nbJoueurs / 2) {
					quitter = true;
				}
			}
			retour.add(vm);
		}
		return retour;
	}

}
