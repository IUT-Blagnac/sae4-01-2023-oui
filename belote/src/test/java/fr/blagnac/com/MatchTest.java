package fr.blagnac.com;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import fr.blagnac.com.model.Match;

public class MatchTest {
    @Test
    public void constructorId() {
        Match match = new Match(1, 2, 3, 4, 5, 6, true);
        assertTrue(match.getIdMatch() == 1);
    }

    @Test
    public void constructorEq1() {
        Match match = new Match(1, 2, 3, 4, 5, 6, true);
        assertTrue(match.getEquipe1() == 2);
    }

    @Test
    public void constructorEq2() {
        Match match = new Match(1, 2, 3, 4, 5, 6, true);
        assertTrue(match.getEquipe2() == 3);
    }

    @Test
    public void constructorScore1() {
        Match match = new Match(1, 2, 3, 4, 5, 6, true);
        assertTrue(match.getScore1() == 4);
    }

    @Test
    public void constructorScore2() {
        Match match = new Match(1, 2, 3, 4, 5, 6, true);
        assertTrue(match.getScore2() == 5);
    }

    @Test
    public void constructorNumTour() {
        Match match = new Match(1, 2, 3, 4, 5, 6, true);
        assertTrue(match.getNumeroTour() == 6);
    }

    @Test
    public void constructorTermine() {
        Match match = new Match(1, 2, 3, 4, 5, 6, true);
        assertTrue(match.isTermine() == true);
    }

    @Test
    public void toStringEq1Eq2() {
        Match match = new Match(1, 2, 3, 4, 5, 6, true);
        assertTrue(match.toString().equals("  2 contre 3"));
    }
}
