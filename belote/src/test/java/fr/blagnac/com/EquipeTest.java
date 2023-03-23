package fr.blagnac.com;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EquipeTest {
    @Test
    public void constructorId() {
        Equipe equipe = new Equipe(1, 1, "eq1", "eq2");
        assertTrue(equipe.id == 1);
    }

    @Test
    public void constructorNum() {
        Equipe equipe = new Equipe(1, 1, "eq1", "eq2");
        assertTrue(equipe.num == 1);
    }

    @Test
    public void constructorEq1() {
        Equipe equipe = new Equipe(1, 1, "eq1", "eq2");
        assertTrue(equipe.eq1 == "eq1");
    }

    @Test
    public void constructorEq2() {
        Equipe equipe = new Equipe(1, 1, "eq1", "eq2");
        assertTrue(equipe.eq2 == "eq2");
    }
}
