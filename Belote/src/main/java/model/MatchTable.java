package model;

import javax.swing.table.AbstractTableModel;

import view.Fenetre;

public class MatchTable extends AbstractTableModel {

    private static final long serialVersionUID = 1L;

    private Tournoi tournoi;

    public MatchTable(Tournoi tournoi) {
        this.tournoi = tournoi;
    }

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
        if (tournoi == null)
            return 0;
        return tournoi.getNbMatchs();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    public boolean isCellEditable(int x, int y) {
        return y > 2 && tournoi.getMatch(x).getNumeroTour() == tournoi.getNbTours();
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Match m = tournoi.getMatch(rowIndex);
        if (columnIndex == 3) {
            try {
                int sco = Integer.parseInt((String) aValue);
                m.setScore1(sco);
                tournoi.majMatch(rowIndex);
            } catch (Exception e) {
                Fenetre.afficherErreur(
                        "Erreur lors de la saisie des scores, les scores doivent être des nombres entiers.");
                System.out.println(e.getMessage()); // Message développeur
            }
        } else if (columnIndex == 4) {
            try {
                int sco = Integer.parseInt((String) aValue);
                m.setScore2(sco);
                tournoi.majMatch(rowIndex);
            } catch (Exception e) {
                Fenetre.afficherErreur(
                        "Erreur lors de la saisie des scores, les scores doivent être des nombres entiers.");
                System.out.println(e.getMessage()); // Message développeur
            }
        }
        fireTableDataChanged();
    }
    
}
