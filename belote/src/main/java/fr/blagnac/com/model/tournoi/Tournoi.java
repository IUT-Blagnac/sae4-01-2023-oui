package fr.blagnac.com.model.tournoi;


import fr.blagnac.com.control.DialogEquipe;
import fr.blagnac.com.control.DialogMatch;
import fr.blagnac.com.control.DialogTournoi;
import fr.blagnac.com.model.Equipe;
import fr.blagnac.com.model.Match;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;


public class Tournoi {

	String nt;
	Statut statut;
	int id_tournoi;
	//int nbtours;
	private Vector<Equipe> dataeq = null;
	private Vector<Match> datam  = null;
	private Vector<Integer>ideqs  = null; 
	//Statement st;

	// Dialogs
	private DialogEquipe dialogEquipe = new DialogEquipe();
	private DialogMatch dialogMatch = new DialogMatch();
	private DialogTournoi dialogTournoi = new DialogTournoi();


/* =====================================================================================================================
	##### Partie HUGO ##################################################################################################
   ===================================================================================================================== */

	public Tournoi(String nt){
		try {
			ResultSet rs = dialogTournoi.getTournoisParNom(Tournoi.mysql_real_escape_string(nt)); // TODO : mettre dans classe Tool
			if(!rs.next()){
				return ;
			}
			this.statut = Statut.getStatut(rs.getInt("statut"));
			this.id_tournoi = rs.getInt("id_tournoi");
			rs.close();
		} catch (SQLException e) { // TODO : popup
			System.out.println("Erreur SQL: " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (this.statut == null) {
			this.statut = Statut.INCONNU;
		}
		this.nt = nt;
	}

	public void majEquipes(){
		dataeq = new Vector<Equipe>();
		ideqs = new Vector<Integer>();
		try {
			ResultSet rs = dialogTournoi.getEquipesParTournoi(this.id_tournoi);
			while(rs.next()){
				dataeq.add(new Equipe(rs.getInt("id_equipe"),rs.getInt("num_equipe"), rs.getString("nom_j1"), rs.getString("nom_j2")));
				ideqs.add(rs.getInt("num_equipe"));
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage()); // TODO : popup
		}
	}

	public void majMatch(){
		datam = new Vector<Match>();
		try {
			ResultSet rs= dialogTournoi.getMatchsParTournoi(id_tournoi);
			while(rs.next()) datam.add(new Match(rs.getInt("id_match"),rs.getInt("equipe1"),rs.getInt("equipe2"), rs.getInt("score1"),rs.getInt("score2"),rs.getInt("num_tour"),rs.getString("termine").equals("oui")));
			//public MatchM(int _idmatch,int _e1,int _e2,int _score1, int _score2, int _num_tour, boolean _termine)
			rs.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	// Getters

	public int getIdTournoi() {
		return this.id_tournoi;
	}

	public Match getMatch(int index){
		if(datam == null) majMatch();
		return datam.get(index);
	}

	public int getNbMatchs(){
		if(datam == null) majMatch();
		return datam.size();
	}

	public Equipe getEquipe(int index){
		if(dataeq == null) 
			majEquipes();
		return dataeq.get(index);
	}

	public int getNbEquipes(){
		if(dataeq == null) 
			majEquipes();
		return dataeq.size();
	}
	
	public Statut getStatut(){
		return statut;
	}

	public String getNom() {
		return nt;
	}

	public int getNbTours(){
		try {
			ResultSet rs = dialogTournoi.getNbToursMaxMatchParTournoi(id_tournoi);
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return -1;
		}
	}

	public void genererMatchs(){
		int nbt = 1;
		System.out.println("Nombre d'�quipes : " + getNbEquipes());
		System.out.println("Nombre de tours  : " + nbt);
		Vector<Vector<Match>> ms;
		ms = Tournoi.getMatchsToDo(getNbEquipes(), nbt);
		int z = 1;
		try {
			for(Vector<Match> t :ms){
				for(Match m:t){
					dialogMatch.insertMatch(null, this.id_tournoi, z, m.getEquipe1(), m.getEquipe2(), "non");
				}
				z++;
			}
			dialogTournoi.setStatutTournoi(2, this.id_tournoi);
			this.statut = Statut.getStatut(2);
		}catch(SQLException e){
			System.out.println("Erreur validation �quipes : " + e.getMessage()); // TODO : popup
		}
	}


/* =====================================================================================================================
	##### Partie TILIAN ################################################################################################
   ===================================================================================================================== */

	public boolean ajouterTour(){
		// Recherche du nombre de tours actuel
		int nbtoursav;
		if(getNbTours() >=  (getNbEquipes() -1) ) return false;
		System.out.println("Eq:" + getNbEquipes() + "  tours" + getNbTours());
		try {
			ResultSet rs = dialogTournoi.getNbToursMaxMatchParTournoi(this.id_tournoi);
			rs.next();
			nbtoursav = rs.getInt(1);
			rs.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage()); // TODO : popup
			return false;
		}
		System.out.println("Nombre de tours avant:" + nbtoursav);
		
		if(nbtoursav == 0){
			Vector<Match> ms;
			ms = Tournoi.getMatchsToDo(getNbEquipes(), nbtoursav+1).lastElement();
			try{
				for(Match m:ms){
					dialogMatch.insertMatch(null, this.id_tournoi, (nbtoursav + 1), m.getEquipe1(), m.getEquipe2(), "non");
				}
			}catch(SQLException e){
				System.out.println("Erreur ajout tour : " + e.getMessage()); // TODO : popup
			}
		}else{
			try {
				ResultSet rs;
				//rs = dialogTournoi.getStatement().executeQuery("SELECT equipe, (SELECT count(*) FROM matchs m WHERE (m.equipe1 = equipe AND m.score1 > m.score2 AND m.id_tournoi = id_tournoi) OR (m.equipe2 = equipe AND m.score2 > m.score1 AND m.id_tournoi = id_tournoi )) as matchs_gagnes FROM  (select equipe1 as equipe,score1 as score from matchs where id_tournoi=" + this.id_tournoi + " UNION select equipe2 as equipe,score2 as score from matchs where id_tournoi=" + this.id_tournoi + ") GROUP BY equipe ORDER BY matchs_gagnes DESC;");
				rs = dialogMatch.getMatchsDataCount(this.id_tournoi);
				ArrayList<Integer> ordreeq= new ArrayList<Integer>();
				while(rs.next()){
					ordreeq.add(rs.getInt("equipe"));
					System.out.println(rs.getInt(1) +" _ " + rs.getString(2));
				}
				System.out.println("Taille"+ordreeq.size());
				int i;
				boolean fini;
				while(ordreeq.size() > 1){
					System.out.println("Taille " + ordreeq.size());
					int j=0;
					while(j<ordreeq.size()) {
						System.out.println(ordreeq.get(j));
						j++;
					}
					i=1;
					do{
						rs = dialogMatch.getNbMatchsParEquipes(ordreeq.get(0), ordreeq.get(i));
						rs.next();
						if(rs.getInt(1) > 0){
							// Le match est d�j� jou�
							i++;
							fini = false;
						}else{ 
							fini = true;
							dialogMatch.insertMatch(null, this.id_tournoi, (nbtoursav + 1), ordreeq.get(0), ordreeq.get(i), "non");
							ordreeq.remove(0);
							ordreeq.remove(i-1);
						}
					}while(!fini);
				}
			} catch (SQLException e) {
				e.printStackTrace(); // TODO : popup
			}
		}
		return true;
	}

	public void supprimerTour(){
		int nbtoursav;
		try {
			ResultSet rs = dialogTournoi.getNbToursMaxMatchParTournoi(this.id_tournoi);
			rs.next();
			nbtoursav = rs.getInt(1);
			rs.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage()); // TODO : popup
			return ;
		}
		//if(tour != nbtoursav) return ;
		try {
			dialogMatch.deleteMatch(this.id_tournoi, nbtoursav);
		} catch (SQLException e) {
			System.out.println("Erreur del tour : " + e.getMessage()); // TODO : popup
		}
	}
	
	public void ajouterEquipe(){
		int a_aj= this.dataeq.size()+1;
		for ( int i=1;i <= this.dataeq.size(); i++){
			if(!ideqs.contains(i)){
				a_aj=i;
				break;
			}
		}
		try {
			dialogEquipe.insertEquipe(null, a_aj, this.id_tournoi, "Joueur 1", "Joueur 2");
		    majEquipes();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void majEquipe(int index){
		try {
			dialogEquipe.setNomsJoueursEquipe(getEquipe(index).getId(), mysql_real_escape_string(getEquipe(index).getEquipe1()), mysql_real_escape_string(getEquipe(index).getEquipe2()));
		    majEquipes();
		} catch (SQLException e) { // TODO : popup
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void majMatch(int index){
		String termine = (getMatch(index).getScore1() > 0 || getMatch(index).getScore2() > 0) ? "oui":"non";
		System.out.println(termine);
		try {
			dialogMatch.updateMatch(getMatch(index).getIdMatch(), getMatch(index).getEquipe1(), getMatch(index).getEquipe2(), getMatch(index).getScore1(), getMatch(index).getScore2(), termine);
		} catch (SQLException e) {
			e.printStackTrace(); // TODO popup
		}
		majMatch();
	}

	public void supprimerEquipe(int ideq){
		try {
			int numeq;
			ResultSet rs = dialogEquipe.getEquipe(ideq);
			rs.next();
			numeq = rs.getInt("num_equipe");
			rs.close();
			dialogEquipe.deleteEquipe(this.id_tournoi, ideq);
			dialogEquipe.setNumEquipe(this.id_tournoi, numeq);
		    majEquipes();
		} catch (SQLException e) {
			e.printStackTrace(); // TODO : popup
		}		
	}

	// TODO : mettre dans une classe Tool
    public static String mysql_real_escape_string( String str) throws Exception {
          if (str == null) {
              return null;
          }
          if (str.replaceAll("[a-zA-Z0-9_!@#$%^&*()-=+~.;:,\\Q[\\E\\Q]\\E<>{}\\/? ]","").length() < 1) {
              return str;
          }
          String clean_string = str;
          clean_string = clean_string.replaceAll("\\n","\\\\n");
          clean_string = clean_string.replaceAll("\\r", "\\\\r");
          clean_string = clean_string.replaceAll("\\t", "\\\\t");
          clean_string = clean_string.replaceAll("\\00", "\\\\0");
          clean_string = clean_string.replaceAll("'", "''");
          return clean_string;
	}
    
	public static Vector<Vector<Match>> getMatchsToDo(int nbJoueurs, int nbTours){
		if( nbTours >= nbJoueurs){
			System.out.println("Erreur tours < equipes"); // TODO : popup ?
			return null;
		}
		
		int[] tabJoueurs;
		if((nbJoueurs % 2) == 1){
			// Nombre impair de joueurs, on rajoute une �quipe fictive
			tabJoueurs   = new int[nbJoueurs+1];
			tabJoueurs[nbJoueurs] = -1;
			for(int z = 0; z < nbJoueurs;z++){
				tabJoueurs[z] = z+1;
			}
			nbJoueurs++;
		}else{
			tabJoueurs = new int[nbJoueurs];
			for(int z = 0; z < nbJoueurs;z++){
				tabJoueurs[z] = z+1;
			}
		}
		boolean quitter;
		int i, increment = 1, temp;
		Vector<Vector<Match>> retour = new Vector<Vector<Match>>();
		Vector<Match> vm;
		for( int r = 1; r <= nbTours;r++){
			if(r > 1){
				temp = tabJoueurs[nbJoueurs - 2];
				for(i = (nbJoueurs - 2) ; i > 0; i--){
					tabJoueurs[i] = tabJoueurs[i-1];
				}
				tabJoueurs[0] = temp;
			}
			i = 0;
			quitter = false;
			vm = new Vector<Match>();
			while(!quitter){
				if (tabJoueurs[i] == -1 || tabJoueurs[nbJoueurs - 1  - i] == -1){
					// Nombre impair de joueur, le joueur n'a pas d'adversaire
				}else{
					vm.add(new Match(tabJoueurs[i], tabJoueurs[nbJoueurs - 1  - i]));
				}
		        i+= increment;
				if(i >= nbJoueurs / 2){
					if(increment == 1){
						quitter = true;
						break;
					}else{
						increment = -2;
						if( i > nbJoueurs / 2){
							i = ((i > nbJoueurs / 2) ? i - 3 : --i) ;
						}
						if ((i < 1) && (increment == -2)){
							quitter = true;
							break;
						}
					}
				}
			}
			retour.add(vm);
		}
		return retour;
	}

}
