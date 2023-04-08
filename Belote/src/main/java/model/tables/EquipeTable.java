package model.tables;


import javax.swing.table.AbstractTableModel;
import model.Equipe;
import model.Tournoi;
import types.StatutTournoi;


/**
 * Cette classe représente un modèle de tableau pour afficher les équipes d'un tournoi.
 * Elle hérite de la classe AbstractTableModel de Java.
 */
public class EquipeTable extends AbstractTableModel {

    private static final long serialVersionUID = 1L;
    private final Tournoi tournoi;

    /**
     * Constructeur de la classe EquipeTable.
     * @param tournoi : le tournoi pour lequel les équipes doivent être affichées.
     */
    public EquipeTable(Tournoi tournoi) {
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
                r = tournoi.getEquipe(arg0).getNumero();
                break;
            case 1:
                r = tournoi.getEquipe(arg0).getEquipe1();
                break;
            case 2:
                r = tournoi.getEquipe(arg0).getEquipe2();
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
                return "Numéro d'équipe";
            case 1:
                return "Joueur 1";
            case 2:
                return "Joueur 2";
            default:
                return "??";
        }
    }

    /**
     * Cette méthode retourne le nombre d'équipes d'un tournoi, soit le nombre de lignes.
     * @return le nombre d'équipes d'un tournoi, soit le nombre de lignes du tableau
     */
    @Override
    public int getRowCount() {
        if (tournoi == null)
            return 0;
        return tournoi.getNbEquipes();
    }

    /**
     * Cette méthode retourne le nombre de colonnes dans le tableau (toujours 3).
     * @return le nombre de colonnes dans le tableau (toujours 3).
     */
    @Override
    public int getColumnCount() {
        return 3;
    }

    /**
     * Cette méthode retourne vrai si la cellule située à la position x et y est éditable, faux sinon.
     * @param x : l'index de la ligne de la cellule.
     * @param y : l'index de la colonne de la cellule.
     * @return vrai si la cellule est éditable, faux sinon.
     */
    public boolean isCellEditable(int x, int y) {
        if (tournoi.getStatut() != StatutTournoi.INSCRIPTION)
            return false;
        return y > 0;
    }

    /**
     * Met à jour la valeur de la cellule située à l'intersection de la ligne spécifiée par rowIndex et
     *   de la colonne spécifiée par columnIndex avec la valeur spécifiée aValue.
     * @param aValue : la nouvelle valeur pour la cellule
     * @param rowIndex : l'index de la ligne où se trouve la cellule à mettre à jour
     * @param columnIndex : l'index de la colonne où se trouve la cellule à mettre à jour
     */
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Equipe e = tournoi.getEquipe(rowIndex);
        if (columnIndex == 1) {
            e.setEquipe1((String) aValue);
        } else if (columnIndex == 2) {
            e.setEquipe2((String) aValue);
        }
        tournoi.majEquipe(rowIndex);
        fireTableDataChanged();
    }

}
