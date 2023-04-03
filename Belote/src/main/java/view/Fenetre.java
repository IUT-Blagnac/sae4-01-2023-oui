package view;


import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import control.dialogs.DialogMatch;
import control.dialogs.DialogTournoi;
import model.Equipe;
import model.Match;
import model.Tournoi;
import types.StatutTournoi;
import types.TableAttributType;


public class Fenetre extends JFrame {

	private static final long serialVersionUID = 1L;

	public JPanel c;

	private JList<String> list;
	private JButton selectTournoi;
	private JButton deleteTournoi;
	private final JButton btournois;
	private final JButton bequipes;
	private final JButton btours;
	private final JButton bmatchs;
	private final JButton bresultats;
	private final JButton bparams;

	// Détails tournois
	JLabel detailt_nom;
	JLabel detailt_statut;
	JLabel detailt_nbtours;

	// Équipes
	private AbstractTableModel eq_modele;
	private JButton eq_ajouter;
	private JButton eq_supprimer;
	private JButton eq_valider;
	JTable eq_jt;
	JPanel eq_p;
	BoxLayout eq_layout;
	JLabel eq_desc;

	// Tours tournois
	JTable tours_t;
	JScrollPane tours_js;
	JPanel tours_p;
	BoxLayout tours_layout;
	JLabel tours_desc;
	JButton tours_ajouter;
	JButton tours_supprimer;

	// Match tournois
	private AbstractTableModel match_modele;
	JTable match_jt;
	JPanel match_p;
	BoxLayout match_layout;
	JLabel match_desc;
	JPanel match_bas;
	JLabel match_statut;
	JButton match_valider;

	// Résultats tournois
	private JScrollPane resultats_js;
	JTable resultats_jt;
	JPanel resultats_p;
	BoxLayout resultats_layout;
	JLabel resultats_desc;
	JPanel resultats_bas;
	JLabel resultats_statut;

	// Traces
	private boolean tournois_trace = false;
	private boolean equipes_trace = false;
	private boolean tours_trace = false;
	private boolean match_trace = false;
	private boolean resultats_trace = false;

	private final CardLayout fen;
	final static String TOURNOIS = "Tournois";
    final static String DETAIL = "Paramètres du tournoi";
    final static String EQUIPES = "Equipes";
    final static String TOURS = "Tours";
    final static String MATCHS = "Matchs";
    final static String RESULTATS = "Resultats";
    public Tournoi tournoi = null;

    private final JLabel statut_select;

	// Dialogs
	private final DialogTournoi dialogTournoi = new DialogTournoi();
	private final DialogMatch dialogMatch = new DialogMatch();

	/**
	 * 
	 * Constructeur de la fenêtre principale de l'application de gestion de tournoi
	 * de Belote.
	 * 
	 * Initialise les différents éléments de l'interface graphique tels que les
	 * boutons et les panels.
	 * 
	 * La fenêtre est également affichée à l'écran.
	 */
	public Fenetre() {
		this.setTitle("Gestion de tournoi de Belote");
		setSize(800,400);
		this.setVisible(true);
		this.setLocationRelativeTo(this.getParent());
		JPanel contenu = new JPanel();
		contenu.setLayout(new BorderLayout());
		this.setContentPane(contenu);
		JPanel phaut = new JPanel();
		contenu.add(phaut,BorderLayout.NORTH);
		phaut.add(statut_select = new JLabel());
		this.setStatutSelect("Pas de tournoi sélectionné");
		JPanel pgauche = new JPanel();
		pgauche.setBackground(Color.RED);
		pgauche.setPreferredSize(new Dimension(130,0));
		contenu.add(pgauche,BorderLayout.WEST);
		btournois = new JButton("Tournois");
		bparams = new JButton("Paramètres");
		bequipes = new JButton("Equipes");
		btours = new JButton("Tours");
		bmatchs = new JButton("Matchs");
		bresultats = new JButton("Résultats");
		int taille_boutons = 100;
		int hauteur_boutons = 30;
		btournois.setPreferredSize(new Dimension(taille_boutons,hauteur_boutons));
		bparams.setPreferredSize(new Dimension(taille_boutons,hauteur_boutons));
		bequipes.setPreferredSize(new Dimension(taille_boutons,hauteur_boutons));
		btours.setPreferredSize(new Dimension(taille_boutons,hauteur_boutons));
		bmatchs.setPreferredSize(new Dimension(taille_boutons,hauteur_boutons));
		bresultats.setPreferredSize(new Dimension(taille_boutons,hauteur_boutons));
		pgauche.add(btournois);
		pgauche.add(bparams);
		pgauche.add(bequipes);
		pgauche.add(btours);
		pgauche.add(bmatchs);
		pgauche.add(bresultats);
		fen = new CardLayout();
		c = new JPanel(fen);
		contenu.add(c,BorderLayout.CENTER);
		btournois.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tracer_select_tournoi();
			}
		});
		btours.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tracer_tours_tournoi();
			}
		});
		bparams.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tracer_details_tournoi();
			}
		});
		bequipes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tracer_tournoi_equipes();
			}
		});
		bmatchs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tracer_tournoi_matchs();
			}
		});
		bresultats.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tracer_tournoi_resultats();
			}
		});
		tracer_select_tournoi();
	}

	/**
	 * Met à jour le statut affiché en haut de la fenêtre avec le texte donné en
	 * paramètre.
	 * Le statut affiché par défaut est "Gestion de tournois de Belote v1.0 - Pas de
	 * tournoi sélectionné".
	 *
	 * @param t le texte à afficher après le statut par défaut.
	 */
	public void setStatutSelect(String t) {
		String statut_deft = "Gestion de tournois de Belote v1.0 - ";
		statut_select.setText(statut_deft + "" + t);
	}

	/**
	 * Met à jour l'état d'activation des boutons en fonction de l'état du tournoi.
	 * Si aucun tournoi n'est sélectionné, seuls les boutons "Tournois" et
	 * "Paramètres" sont activés.
	 * Si un tournoi est sélectionné, les boutons sont activés en fonction de l'état
	 * du tournoi.
	 * 
	 * Si le tournoi est à l'état "Inscription", les boutons "Tournois", "Equipes"
	 * et "Paramètres" sont activés.
	 * Les boutons "Matchs", "Tours" et "Résultats" sont désactivés.
	 * 
	 * Si le tournoi est à l'état "En cours", les boutons "Tournois", "Equipes",
	 * "Matchs", "Tours" et "Paramètres" sont activés.
	 * Le bouton "Résultats" est activé uniquement si tous les matchs du tournoi
	 * sont terminés.
	 * 
	 * @see Tournoi
	 */
	public void majboutons() {
		if (tournoi == null){
			btournois.setEnabled(true);
			bequipes.setEnabled(false);
			bmatchs.setEnabled(false);
			btours.setEnabled(false);
			bresultats.setEnabled(false);
			bparams.setEnabled(false);
		} else {
			switch (tournoi.getStatut()) {
				case INSCRIPTION:
					btournois.setEnabled(true);
					bequipes.setEnabled(true);
					bmatchs.setEnabled(false);
					btours.setEnabled(false);
					bresultats.setEnabled(false);
					bparams.setEnabled(true);
					break;
				case EN_COURS:
					btournois.setEnabled(true);
					bequipes.setEnabled(true);
					bmatchs.setEnabled(tournoi.getNbTours() > 0);
					btours.setEnabled(true);
					int total = -1, termines = -1;
					try {
						ResultSet rs = dialogMatch.getNbMatchsTermines(this.tournoi.getIdTournoi());
						rs.next();
						total = rs.getInt(TableAttributType.TOTAL.getColumnName());
						termines = rs.getInt(TableAttributType.TERMINE.getColumnName());
					} catch (Exception e) {
						Fenetre.afficherErreur("Une erreur est survenue lors de la récupération du nombre de matchs terminés pour ce tournoi.");
						System.out.println(e.getMessage()); // Message développeur
						return;
					}
					bresultats.setEnabled(total == termines && total > 0);
					bparams.setEnabled(true);
					break;
			}
		}
	}

	/**
	 * 
	 * Cette fonction permet de tracer l'interface graphique de la sélection d'un
	 * tournoi.
	 * Elle initialise la liste des tournois en récupérant les noms de tous les
	 * tournois stockés dans la base de données.
	 * Si la liste est vide, les boutons "Sélectionner le tournoi" et "Supprimer le
	 * tournoi" sont désactivés.
	 * Sinon, le premier tournoi de la liste est sélectionné par défaut.
	 * Si l'interface graphique de la sélection des tournois est déjà tracée, alors
	 * elle la montre.
	 * Sinon, elle crée l'interface graphique et l'ajoute à la fenêtre principale.
	 * 
	 */
	public void tracer_select_tournoi() {
		tournoi = null;
		majboutons();
		int nbdeLignes = 0;
		Vector<String> noms_tournois = new Vector<>();
        this.setStatutSelect("sélection d'un tournoi");
		ResultSet rs;
		try {
			rs = dialogTournoi.getTousLesTournois();
			while( rs.next() ){
				nbdeLignes++;
				noms_tournois.add(rs.getString(TableAttributType.NOM_TOURNOI.getColumnName()));
			}
			rs.close();
		} catch (Exception e) {
			Fenetre.afficherErreur("Une erreur est survenue lors de la récupération des tournois.");
			System.out.println(e.getMessage()); // Message développeur
		}
		if (tournois_trace) {
			list.setListData(noms_tournois);
	        if (nbdeLignes == 0) {
	        	selectTournoi.setEnabled(false);
	        	deleteTournoi.setEnabled(false);
	        } else {
	        	selectTournoi.setEnabled(true);
	        	deleteTournoi.setEnabled(true);
	        	list.setSelectedIndex(0);
	        }
			fen.show(c, TOURNOIS);
		} else {
		    tournois_trace = true;
			JPanel t = new JPanel();
			t.setLayout(new BoxLayout(t, BoxLayout.Y_AXIS));
			c.add(t,TOURNOIS);
			JTextArea gt = new JTextArea("Gestion des tournois\nXXXXX XXXXXXXX, juillet 2012");
			gt.setAlignmentX(Component.CENTER_ALIGNMENT);
			gt.setEditable(false);
			t.add(gt);
			JPanel listeTournois = new JPanel();
			t.add(listeTournois);
			list = new JList<>(noms_tournois);
			list.setAlignmentX(Component.LEFT_ALIGNMENT);
			list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		    list.setVisibleRowCount(-1);
		    JScrollPane listScroller = new JScrollPane(list);
	        listScroller.setPreferredSize(new Dimension(250, 180));
			JLabel label = new JLabel("Liste des tournois");
	        label.setLabelFor(list);
	        label.setAlignmentX(Component.LEFT_ALIGNMENT);
	        t.add(label);
	        t.add(listScroller);
	        t.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
	        Box bh = Box.createHorizontalBox();
	        t.add(bh);
			JButton creerTournoi = new JButton("Créer un nouveau tournoi");
			selectTournoi = new JButton("Sélectionner le tournoi");
			deleteTournoi = new JButton("Supprimer le tournoi");
			bh.add(creerTournoi);
			bh.add(selectTournoi);
			bh.add(deleteTournoi);
			t.updateUI();
	        if (nbdeLignes == 0) {
	        	selectTournoi.setEnabled(false);
	        	deleteTournoi.setEnabled(false);
	        } else {
	        	list.setSelectedIndex(0);
	        }
	        creerTournoi.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
				dialogTournoi.creerTournoi();
				Fenetre.this.tracer_select_tournoi();
				}
			});
	        deleteTournoi.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
				dialogTournoi.supprimerTournoi(Fenetre.this.list.getSelectedValue());
				Fenetre.this.tracer_select_tournoi();
				}
			});
	        selectTournoi.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
				String nt = Fenetre.this.list.getSelectedValue();
				Fenetre.this.tournoi = new Tournoi(nt);
				Fenetre.this.tracer_details_tournoi();
				Fenetre.this.setStatutSelect("Tournoi \" " + nt + " \"");
				}
			});
	        fen.show(c, TOURNOIS);
		}
	}

	/**
	 * 
	 * Affiche les détails du tournoi sélectionné.
	 * Si aucun tournoi n'est sélectionné, ne fait rien.
	 * Crée un nouveau panel et y ajoute un tableau de 4 lignes et 2 colonnes
	 * contenant les informations du tournoi : nom, statut et nombre de tours.
	 * Affiche le tout dans la fenêtre principale.
	 */
	public void tracer_details_tournoi() {
		if (tournoi == null) {
			return ;
		}
		majboutons();
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		p.add(new JLabel("Détail du tournoi"));
		c.add(p, DETAIL);
		JPanel tab = new JPanel( new GridLayout(4,2));
		detailt_nom = new JLabel(tournoi.getNom());
		tab.add(new JLabel("Nom du tournoi"));
		tab.add(detailt_nom);
		detailt_statut = new JLabel(tournoi.getStatut().getLibelle());
		tab.add(new JLabel("Statut"));
		tab.add(detailt_statut);
		detailt_nbtours = new JLabel(Integer.toString(tournoi.getNbTours()));
		tab.add(new JLabel("Nombre de tours"));
		tab.add(detailt_nbtours);
		p.add(tab);
		p.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
		fen.show(c, DETAIL);
	}

	
	/**
	 * 
	 * Cette fonction permet de tracer la fenêtre affichant les équipes du tournoi
	 * en cours.
	 * Si le tournoi est null, elle ne fait rien.
	 * Sinon, elle met à jour les boutons, vérifie si les équipes ont déjà été
	 * tracées. Si c'est le cas, elle met à jour les données
	 * de l'interface correspondant aux équipes. Sinon, elle crée une nouvelle
	 * fenêtre avec les informations sur les équipes du tournoi.
	 * Cette fenêtre contient un tableau avec les numéros d'équipes, les noms des
	 * joueurs 1 et 2 de chaque équipe, ainsi que trois boutons: "Ajouter une
	 * équipe",
	 * "Supprimer une équipe" et "Valider les équipes". Lorsqu'on appuie sur le
	 * bouton "Ajouter une équipe", elle ajoute une nouvelle équipe au tournoi.
	 * Lorsqu'on appuie sur le bouton "Supprimer une équipe", elle supprime l'équipe
	 * sélectionnée dans le tableau. Lorsqu'on appuie sur le bouton "Valider les
	 * équipes",
	 * elle génère les matchs du tournoi et met à jour les boutons et la fenêtre des
	 * matchs du tournoi.
	 */
	public void tracer_tournoi_equipes() {
		if (tournoi == null) {
			return ;
		}
		majboutons();
		if (equipes_trace) {
			tournoi.majEquipes();
			eq_modele.fireTableDataChanged();
		} else {
			equipes_trace = true;
			eq_p = new JPanel();
			eq_layout = new BoxLayout(eq_p, BoxLayout.Y_AXIS);
			eq_p.setLayout(eq_layout);
			eq_desc = new JLabel("Equipes du tournoi");
			eq_p.add(eq_desc);
			eq_p.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
			c.add(eq_p, EQUIPES);
			eq_modele = new AbstractTableModel() {

				private static final long serialVersionUID = 1L;

				@Override
				public Object getValueAt(int arg0, int arg1) {
					Object r=null;
					switch (arg1) {
						case 0:
							r= tournoi.getEquipe(arg0).getNumero();
							break;
						case 1:
							r= tournoi.getEquipe(arg0).getEquipe1();
							break;
						case 2:
							r= tournoi.getEquipe(arg0).getEquipe2();
							break;
					}
					return r;
				}

				public String getColumnName(int col) {
					switch (col) {
						case 0:
							return "Numéro d'équipe";
						case 1:
							return "Joueur 1";
						case 2:
							return "Joueur 2";
						default:
							return "??";
					}
				 }

				@Override
				public int getRowCount() {
					if (tournoi == null) return 0;
					return tournoi.getNbEquipes();
				}

				@Override
				public int getColumnCount() {
					return 3;
				}
				public boolean isCellEditable(int x, int y) {
					if (tournoi.getStatut() != StatutTournoi.INSCRIPTION) return false;
					return y > 0;
				}

				public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
					Equipe e = tournoi.getEquipe(rowIndex);
					if (columnIndex == 1) {
						e.setEquipe1((String)aValue);
					} else if ( columnIndex == 2) {
						e.setEquipe2((String)aValue);
					}
					tournoi.majEquipe(rowIndex);
					fireTableDataChanged();
				}
			};
			eq_jt = new JTable(eq_modele);
			JScrollPane eq_js = new JScrollPane(eq_jt);
			eq_p.add(eq_js);
			JPanel bt = new JPanel();
			eq_ajouter = new JButton("Ajouter une équipe");
			eq_supprimer = new JButton("Supprimer une équipe");
			eq_valider = new JButton("Valider les équipes");
			eq_ajouter.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
				tournoi.ajouterEquipe();
				eq_valider.setEnabled(tournoi.getNbEquipes() > 0 && tournoi.getNbEquipes() % 2 == 0) ;
				eq_modele.fireTableDataChanged();
				if (tournoi.getNbEquipes() > 0) {
					eq_jt.getSelectionModel().setSelectionInterval(0, 0);
				}
				}
			});
			eq_supprimer.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
				if (Fenetre.this.eq_jt.getSelectedRow() != -1) {
					tournoi.supprimerEquipe(tournoi.getEquipe(Fenetre.this.eq_jt.getSelectedRow()).getId());
				}
				eq_valider.setEnabled(tournoi.getNbEquipes() > 0 && tournoi.getNbEquipes() % 2 == 0) ;
				eq_modele.fireTableDataChanged();
				if (tournoi.getNbEquipes() > 0) {
					eq_jt.getSelectionModel().setSelectionInterval(0, 0);
				}
				}
			});
			eq_valider.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
				tournoi.genererMatchs();
				Fenetre.this.majboutons();
				Fenetre.this.tracer_tournoi_matchs();
				}
			});
			if (tournoi.getNbEquipes() > 0) {
				eq_jt.getSelectionModel().setSelectionInterval(0, 0);
			}
			bt.add(eq_ajouter);
			bt.add(eq_supprimer);
			bt.add(eq_valider);
			eq_p.add(bt);
			eq_p.add(new JLabel("Dans le cas de nombre d'équipes impair, créer une équipe virtuelle"));
		}
		if (tournoi.getStatut() != StatutTournoi.INSCRIPTION) {
			eq_ajouter.setEnabled(false);
			eq_supprimer.setEnabled(false);
			eq_valider.setEnabled(tournoi.getStatut() == StatutTournoi.GENERATION);
		} else {
			eq_ajouter.setEnabled(true);
			eq_supprimer.setEnabled(true);
			eq_valider.setEnabled(tournoi.getNbEquipes() > 0) ;
		}
		fen.show(c, EQUIPES);
	}

	/**
	 * 
	 * Cette méthode permet de tracer les tours d'un tournoi en affichant un tableau
	 * qui contient
	 * le numéro du tour, le nombre de matchs et le nombre de matchs terminés pour
	 * chaque tour.
	 * Si le tournoi n'existe pas, cette méthode ne fait rien.
	 */
	public void tracer_tours_tournoi() {
		if (tournoi == null) {
			return ;
		}
		majboutons();
		Vector< Vector<Object>> to = new Vector<>();
		Vector<Object> v;
		boolean peutajouter = true;
		try {
			ResultSet rs = dialogMatch.getNbToursParMatch(this.tournoi.getIdTournoi());
			while(rs.next()){
				v = new Vector<>();
				v.add(rs.getInt(TableAttributType.NUM_TOUR.getColumnName()));
				v.add(rs.getInt(TableAttributType.NB_MATCHS.getColumnName()));
				v.add(rs.getString(TableAttributType.TERMINE.getColumnName()));
				to.add(v);
				peutajouter = peutajouter && rs.getInt(TableAttributType.NB_MATCHS.getColumnName()) == rs.getInt(TableAttributType.TERMINE.getColumnName());
			}
		} catch (Exception e) {
			Fenetre.afficherErreur("Une erreur est survenue lors de la récupération des tours du match du tournoi.");
			System.out.println(e.getMessage()); // Message développeur
		}
		Vector<String> columnNames = new Vector<>();
		columnNames.add("Numéro du tour");
		columnNames.add("Nombre de matchs");
		columnNames.add("Matchs joués");
		tours_t = new JTable(to,columnNames );
		if (tours_trace) {
			tours_js.setViewportView(tours_t);
		} else {
			tours_trace = true;
			tours_p = new JPanel();
			tours_layout = new BoxLayout( tours_p, BoxLayout.Y_AXIS);
			tours_p.setLayout( tours_layout);
			tours_desc = new JLabel("Tours");
			tours_p.add(tours_desc);
			tours_p.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
			c.add(tours_p, TOURS);
			tours_js = new JScrollPane();
			tours_js.setViewportView(tours_t);
			tours_p.add(tours_js);
			JPanel bt = new JPanel();
			tours_ajouter = new JButton("Ajouter un tour");
			tours_supprimer = new JButton("Supprimer le dernier tour");
			bt.add(tours_ajouter);
			bt.add(tours_supprimer);
			tours_p.add(bt);
			tours_p.add(new JLabel("Pour pouvoir ajouter un tour, terminez tous les matchs du précédent."));
			tours_p.add(new JLabel("Le nombre maximum de tours est \"le nombre total d'équipes - 1\""));
			tours_ajouter.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
				tournoi.ajouterTour();
				Fenetre.this.tracer_tours_tournoi();
				}
			});
			tours_supprimer.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
				tournoi.supprimerTour();
				Fenetre.this.tracer_tours_tournoi();
				}
			});
		}
		if (to.size() == 0) {
			tours_supprimer.setEnabled(false);
			tours_ajouter.setEnabled(true);
		} else {
			tours_supprimer.setEnabled( tournoi.getNbTours() > 1);
			tours_ajouter.setEnabled(peutajouter && tournoi.getNbTours() < tournoi.getNbEquipes() - 1);
		}
		fen.show(c, TOURS);
	}

	/**
	 * 
	 * Cette fonction affiche les matchs d'un tournoi dans un tableau.
	 * Si le tournoi n'est pas initialisé, la fonction ne fait rien.
	 * Si le tableau de matchs est déjà affiché, la fonction met à jour les données
	 * affichées.
	 * Sinon, la fonction crée le tableau et l'ajoute à la fenêtre.
	 * Les colonnes du tableau sont : Tour, Équipe 1, Équipe 2, Score équipe 1,
	 * Score équipe 2.
	 * Les scores des deux équipes sont éditables si le match est en cours et est le
	 * dernier match du tournoi.
	 * Les statistiques sur le nombre de matchs joués et le bouton "Afficher les
	 * résultats" sont également affichés.
	 * Cette fonction met à jour le statut de la fenêtre et les boutons de
	 * navigation.
	 */
	public void tracer_tournoi_matchs() {
		if (tournoi == null) {
			return ;
		}
		majboutons();
		if (match_trace) {
			tournoi.majMatch();
			match_modele.fireTableDataChanged();
			majStatutM();
		} else {
			match_trace = true;
			match_p = new JPanel();
			match_layout = new BoxLayout(match_p, BoxLayout.Y_AXIS);
			match_p.setLayout(match_layout);
			match_desc = new JLabel("Matchs du tournoi");
			match_p.add(match_desc);
			match_p.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
			c.add(match_p, MATCHS);
			match_modele = new AbstractTableModel() {

				private static final long serialVersionUID = 1L;

				@Override
				public Object getValueAt(int arg0, int arg1) {
					Object r=null;
					switch (arg1) {
						case 0:
							r= tournoi.getMatch(arg0).getNumeroTour();
							break;
						case 1:
							r= tournoi.getMatch(arg0).getEquipe1();
							break;
						case 2:
							r= tournoi.getMatch(arg0).getEquipe2();
							break;
						case 3:
							r= tournoi.getMatch(arg0).getScore1();
							break;
						case 4:
							r= tournoi.getMatch(arg0).getScore2();
							break;
					}
					return r;
				}

				public String getColumnName(int col) {
					switch (col) {
						case 0:
							return "Tour";
						case 1:
							return "Équipe 1";
						case 2:
							return "Équipe 2";
						case 3:
							return "Score équipe 1";
						case 4:
							return "Score équipe 2";
						default:
							return "??";
					}
				}

				@Override
				public int getRowCount() {
					if (tournoi == null) return 0;
					return tournoi.getNbMatchs();
				}

				@Override
				public int getColumnCount() {
					return 5;
				}

				public boolean isCellEditable(int x, int y){
					return y > 2 && tournoi.getMatch(x).getNumeroTour() == tournoi.getNbTours();
				}

				public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
					Match m = tournoi.getMatch(rowIndex);
					if (columnIndex == 3) {
						try {
							int sco = Integer.parseInt((String)aValue);
							m.setScore1(sco);
							tournoi.majMatch(rowIndex);
						} catch(Exception e) {
							Fenetre.afficherErreur("Erreur lors de la saisie des scores, les scores doivent être des nombres entiers.");
							System.out.println(e.getMessage()); // Message développeur
						}
					} else if ( columnIndex == 4) {
						try {
							int sco = Integer.parseInt((String)aValue);
							m.setScore2(sco);
							tournoi.majMatch(rowIndex);
						} catch(Exception e) {
							Fenetre.afficherErreur("Erreur lors de la saisie des scores, les scores doivent être des nombres entiers.");
							System.out.println(e.getMessage()); // Message développeur
						}
					}
					fireTableDataChanged();
					Fenetre.this.majStatutM();
					Fenetre.this.majboutons();
				}
			};
			match_jt = new JTable(match_modele);
			JScrollPane match_js = new JScrollPane(match_jt);
			match_p.add(match_js);
			match_bas = new JPanel();
			match_bas.add(match_statut = new JLabel("?? Matchs joués"));
			match_bas.add(match_valider = new JButton("Afficher les résultats"));
			match_valider.setEnabled(false);
			match_p.add(match_bas);
			majStatutM();
		}
		fen.show(c, MATCHS);
	}

	/**
	 * 
	 * Cette fonction permet de tracer les résultats d'un tournoi dans une JTable et
	 * de les afficher dans une fenêtre.
	 * Si aucun tournoi n'a été créé, la fonction se termine sans rien faire.
	 * Les résultats sont récupérés depuis la base de données à l'aide d'un
	 * ResultSet et stockés dans un Vector de Vector d'objets.
	 * Les noms des colonnes sont stockés dans un Vector de chaînes de caractères.
	 * Ensuite, une JTable est créée à partir de ces deux vecteurs et affichée dans
	 * une JScrollPane.
	 * Si la JTable a déjà été affichée auparavant, elle est simplement mise à jour
	 * avec les nouveaux résultats.
	 * Si c'est la première fois que la JTable est affichée, elle est ajoutée à un
	 * JPanel avec un titre et un JLabel pour afficher le gagnant.
	 * Enfin, le JPanel est affiché dans une fenêtre.
	 */
	public void tracer_tournoi_resultats() {
		if (tournoi == null) {
			return ;
		}
		Vector< Vector<Object>> to = new Vector<>();
		Vector<Object> v;
		try {
			ResultSet rs = dialogMatch.getResultatsMatch(this.tournoi.getIdTournoi());
			while (rs.next()) {
				v = new Vector<>();
				v.add(rs.getInt(TableAttributType.EQUIPE.getColumnName()));
				v.add(rs.getString(TableAttributType.NOM_J1.getColumnName()));
				v.add(rs.getString(TableAttributType.NOM_J2.getColumnName()));
				v.add(rs.getInt(TableAttributType.SCORE.getColumnName()));
				v.add(rs.getInt(TableAttributType.MATCHS_GAGNES.getColumnName()));
				v.add(rs.getInt(TableAttributType.MATCHS_JOUES.getColumnName()));
				to.add(v);
			}
		} catch (Exception e) {
			Fenetre.afficherErreur("Une erreur est survenue lors de la récupération des résultats du match pour ce tournoi.");
			System.out.println(e.getMessage()); // Message développeur
		}
		Vector<String> columnNames = new Vector<>();
		columnNames.add("Numéro d'équipe");
		columnNames.add("Nom joueur 1");
		columnNames.add("Nom joueur 2");
		columnNames.add("Score");
		columnNames.add("Matchs gagnés");
		columnNames.add("Matchs joués");
		resultats_jt = new JTable(to,columnNames );
		resultats_jt.setAutoCreateRowSorter(true);
		if (resultats_trace) {
			resultats_js.setViewportView(resultats_jt);
		} else {
			resultats_trace = true;
			resultats_p = new JPanel();
			resultats_layout = new BoxLayout(resultats_p, BoxLayout.Y_AXIS);
			resultats_p.setLayout(resultats_layout);
			resultats_desc = new JLabel("Résultats du tournoi");
			resultats_p.add(resultats_desc);
			resultats_p.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
			c.add(resultats_p, RESULTATS );
			resultats_js = new JScrollPane(resultats_jt);
			resultats_p.add(resultats_js);
			resultats_bas = new JPanel();
			resultats_bas.add(resultats_statut = new JLabel("Gagnant:"));
			resultats_p.add(resultats_bas);
		}
		fen.show(c, RESULTATS);
	}

	/**
	 * 
	 * Met à jour le statut des matchs pour le tournoi en cours.
	 * Récupère le nombre de matchs terminés et le nombre total de matchs
	 * et affiche le statut correspondant.
	 * Si tous les matchs sont terminés, active le bouton de validation.
	 * Si une erreur survient lors de la récupération des données, affiche une
	 * erreur et ne fait rien.
	 */
	private void majStatutM() {
		int total = -1, termines = -1;
		try {
			ResultSet rs = dialogMatch.getNbMatchsTermines(this.tournoi.getIdTournoi());
			rs.next();
			total = rs.getInt(TableAttributType.TOTAL.getColumnName());
			termines = rs.getInt(TableAttributType.TERMINE.getColumnName());
		} catch (Exception e) {
			Fenetre.afficherErreur("Une erreur est survenue lors de la récupération du nombre de matchs terminés pour ce tournoi.");
			System.out.println(e.getMessage()); // Message développeur
			return ;
		}
		match_statut.setText(termines + "/" + total + " matchs terminés");
		match_valider.setEnabled(total == termines);
	}

	/**
	 * 
	 * Affiche une boîte de dialogue d'erreur avec le message spécifié.
	 * 
	 * @param message le message d'erreur à afficher
	 */
	public static void afficherErreur(String message) {
		JOptionPane.showMessageDialog(null, message, "ERREUR", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * 
	 * Affiche une boîte de dialogue demandant à l'utilisateur d'entrer le nom du
	 * tournoi.
	 * 
	 * @return le nom du tournoi saisi par l'utilisateur
	 */
	public static String saisieNomTournoi() {
		return JOptionPane.showInputDialog(null, "Entrez le nom du tournoi", "Nom du tournoi", JOptionPane.PLAIN_MESSAGE);
	}

}
