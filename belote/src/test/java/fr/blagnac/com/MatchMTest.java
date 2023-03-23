package fr.blagnac.com;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MatchMTest {
    @Test
    public void constructorId() {
        MatchM match = new MatchM(1, 2, 3, 4, 5, 6, true);
        assertTrue(match.idmatch == 1);
    }

    @Test
    public void constructorEq1() {
        MatchM match = new MatchM(1, 2, 3, 4, 5, 6, true);
        assertTrue(match.eq1 == 2);
    }

    @Test
    public void constructorEq2() {
        MatchM match = new MatchM(1, 2, 3, 4, 5, 6, true);
        assertTrue(match.eq2 == 3);
    }

    @Test
    public void constructorScore1() {
        MatchM match = new MatchM(1, 2, 3, 4, 5, 6, true);
        assertTrue(match.score1 == 4);
    }

    @Test
    public void constructorScore2() {
        MatchM match = new MatchM(1, 2, 3, 4, 5, 6, true);
        assertTrue(match.score2 == 5);
    }

    @Test
    public void constructorNumTour() {
        MatchM match = new MatchM(1, 2, 3, 4, 5, 6, true);
        assertTrue(match.num_tour == 6);
    }

    @Test
    public void constructorTermine() {
        MatchM match = new MatchM(1, 2, 3, 4, 5, 6, true);
        assertTrue(match.termine == true);
    }

    @Test
    public void toStringEq1Eq2() {
        MatchM match = new MatchM(1, 2, 3, 4, 5, 6, true);
        assertTrue(match.toString().equals("  2 contre 3"));
    }
}
