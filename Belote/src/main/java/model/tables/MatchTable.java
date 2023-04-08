package model.tables;


import javax.swing.table.AbstractTableModel;
import model.Match;
import model.Tournoi;
import view.Fenetre;


/**
 * Cette classe représente un modèle de tableau pour afficher les matchs d'un tournoi.
 * Elle hérite de la classe AbstractTableModel de Java.
 */
public class MatchTable extends AbstractTableModel {

    private static final long serialVersionUID = 1L;
    private final Tournoi tournoi;

    /**
     * Constructeur de la classe MatchTable.
     * @param tournoi : le tournoi pour lequel les équipes doivent être affichées.
     */
    public MatchTable(Tournoi tournoi) {
        this.tournoi = tournoi;
    }

    /**
     * Cette méthode retourne la valeur située à la ligne arg0 et à la colonne arg1 du tableau.
     * @param arg0 : la ligne de la valeur à retourner.
     * @param arg1 : la colonne de la valeur à retourner.
     * @return la valeur située à la ligne arg0 et à la colonne arg1 du tableau.
     */
    @Override
    public Object getValueAt(int arg0, int arg1) {
        Object r = null;
        switch (arg1) {
            case 0:
                r = tournoi.getMatch(arg0).getNumeroTour();
                break;
            case 1:
                r = tournoi.getMatch(arg0).getEquipe1();
                break;
            case 2:
                r = tournoi.getMatch(arg0).getEquipe2();
                break;
            case 3:
                r = tournoi.getMatch(arg0).getScore1();
                break;
            case 4:
                r = tournoi.getMatch(arg0).getScore2();
                break;
        }
        return r;
    }

    /**
     * Cette méthode retourne le string par défaut d'une colonne.
     * @param col : la colonne de la valeur à retourner.
     * @return la valeur String par défaut d'une colonne.
     */
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

    /**
     * Cette méthode retourne le nombre de matchs d'un tournoi, soit le nombre de lignes.
     * @return le nombre de matchs d'un tournoi, soit le nombre de lignes du tableau
     */
    @Override
    public int getRowCount() {
        if (tournoi == null)
            return 0;
        return tournoi.getNbMatchs();
    }

    /**
     * Cette méthode retourne le nombre de colonnes dans le tableau (toujours 5).
     * @return le nombre de colonnes dans le tableau (toujours 5).
     */
    @Override
    public int getColumnCount() {
        return 5;
    }

    /**
     * Détermine si les cellules de saisie des scores d'un match sont modifiables
     *   en fonction du numéro du tour correspondant.
     * @param x : numéro de numéro de ligne
     * @param y : numéro de colonne
     * @return
     */
    public boolean isCellEditable(int x, int y) {
        return y > 2 && tournoi.getMatch(x).getNumeroTour() == tournoi.getNbTours();
    }

    /**
     * Met à jour la valeur de la cellule située à l'intersection de la ligne spécifiée par rowIndex et
     *   de la colonne spécifiée par columnIndex avec la valeur spécifiée aValue.
     * @param aValue : la nouvelle valeur pour la cellule
     * @param rowIndex : l'index de la ligne où se trouve la cellule à mettre à jour
     * @param columnIndex : l'index de la colonne où se trouve la cellule à mettre à jour
     */
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Match m = tournoi.getMatch(rowIndex);
        if (columnIndex == 3) {
            try {
                int sco = Integer.parseInt((String) aValue);
                m.setScore1(sco);
                tournoi.majMatch(rowIndex);
            } catch (Exception e) {
                Fenetre.afficherErreur("Erreur lors de la saisie des scores, les scores doivent être des nombres entiers.");
                System.out.println(e.getMessage()); // Message développeur
            }
        } else if (columnIndex == 4) {
            try {
                int sco = Integer.parseInt((String) aValue);
                m.setScore2(sco);
                tournoi.majMatch(rowIndex);
            } catch (Exception e) {
                Fenetre.afficherErreur("Erreur lors de la saisie des scores, les scores doivent être des nombres entiers.");
                System.out.println(e.getMessage()); // Message développeur
            }
        }
        fireTableDataChanged();
    }
    
}
