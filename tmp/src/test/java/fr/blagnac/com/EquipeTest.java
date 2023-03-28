package fr.blagnac.com;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import fr.blagnac.com.model.Equipe;

public class EquipeTest {
    @Test
    public void constructorId() {
        Equipe equipe = new Equipe(1, 1, "eq1", "eq2");
        assertTrue(equipe.getId() == 1);
    }

    @Test
    public void constructorNum() {
        Equipe equipe = new Equipe(1, 1, "eq1", "eq2");
        assertTrue(equipe.getNumero() == 1);
    }

    @Test
    public void constructorEq1() {
        Equipe equipe = new Equipe(1, 1, "eq1", "eq2");
        assertTrue(equipe.getEquipe1() == "eq1");
    }

    @Test
    public void constructorEq2() {
        Equipe equipe = new Equipe(1, 1, "eq1", "eq2");
        assertTrue(equipe.getEquipe2() == "eq2");
    }
}
