package model.score;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import model.World;
import model.entities.Player;
import model.exceptions.WrongMaterialException;
import model.exceptions.score.ScoreException;

public class P_test_XPScore {
	Player p;
	World w;
	XPScore xps;
	XPScore xps1;
	
	@Before
	public void before_setUp() {
		w = new World(20, 20, "default_world", "Juan");
		p = w.getPlayer();
		xps = new XPScore(p);
		xps1 = new XPScore(p);
	}
	
	@Test
	public void XPScore_constructor_comprobation() {
		assertEquals("Juan", xps.getName());
			assertEquals(0, xps.score, 0.0);
	}
	

	@Test 
	public void compareTo_comprobation_higher() throws WrongMaterialException {
		xps1.score = 1.5;
		assertEquals("Number should be", 1, xps.compareTo(xps1), 0.0);
		xps1.score = 3;
		assertEquals("Number should be", 3, xps.compareTo(xps1), 0.0);
		xps1.score = 4.5;
		assertEquals("Number should be", 4, xps.compareTo(xps1), 0.0);
		xps1.score = 0;
		xps.score = -2;
		assertEquals("Number should be", 2, xps.compareTo(xps1), 0.0);
	}
	
	@Test
	public void compareTo_comprobation_lower() throws WrongMaterialException {
		xps.score = 1;
		assertEquals("Number should be", -1, xps.compareTo(xps1));
		xps.score = 2.5;
		assertEquals("Number should be", -2, xps.compareTo(xps1));
		xps1.score = -2.5;
		xps.score = -1;
		assertEquals("Number should be", -1, xps.compareTo(xps1));
	}
	
	@Test
	public void compareTo_comprobation_equals() {
		assertEquals("Number should be",0, xps.compareTo(xps1));
		xps.score = 2;
		xps1.score = 2;
		assertEquals("Number should be", 0, xps.compareTo(xps1));
		xps.score = -2.5;
		xps1.score = -2.5;
		assertEquals("Number should be", 0, xps.compareTo(xps1));
	}
	
	@Test
	public void score_comprobation() {
		CollectedItemsScore c = new CollectedItemsScore("Juan");
		MiningScore m = new MiningScore("Juan");
		PlayerMovementScore ps = new PlayerMovementScore("Juan");
		c.score = 1.0;
		m.score = 1.0;
		ps.score = 1.0;
		try {
			xps.score(w.getPlayer());
		} catch (Exception e) {
			fail("No deberia de haber saltado");
		}
		assertEquals("",40, xps.score, 0.0);
		xps.addScore(c);
		xps.addScore(m);
		xps.addScore(ps);
		try {
			xps.score(w.getPlayer());
		}catch (Exception e) {
			fail("No deberia de haber saltado");
		}
		assertEquals(41, xps.getScoring(), 0.0);
		
		c.score = 3;
		m.score = 3;
		ps.score = 3;
		xps.addScore(c);
		xps.addScore(m);
		xps.addScore(ps);
		try {
			xps.score(w.getPlayer());
		} catch (Exception e) {
			fail("No deberia de haber saltado");
		}
		
		assertEquals(p.getHealth()+p.getFoodLevel()+ (3), xps.getScoring(), 0.0);
	}
	
	@Test(expected= ScoreException.class)
	public void score_exception() throws ScoreException {
		Player p1 = new Player("Jose", new World(100,100,"WORLD2", "Jose"));
		xps.score(p1);
	}
	
	@Test(expected= ScoreException.class)
	public void score_exception_null() throws ScoreException {
		xps.score(null);
	}
	
	@Test
	public void score_addScore() {
		CollectedItemsScore c = new CollectedItemsScore("Juan");
		c.score = 1;
		MiningScore m = new MiningScore("Juan");
		m.score = 2;
		PlayerMovementScore p = new PlayerMovementScore("Juan");
		p.score = 3;
		xps.addScore(c);
		assertEquals(xps.score, 41, 0.0);
		xps.addScore(m);
		assertEquals(xps.score, 40 +(3/2.0), 0.0);
		xps.addScore(p);
		assertEquals(xps.score, 40 + (6/3), 0.0);
	}
	
	public void getScoring_comprobation() {
		xps.score = 0;
		assertEquals(xps.getScoring(), 40, 0.0);
		xps.score = 1;
		assertEquals(xps.getScoring(), 40, 0.0);
		CollectedItemsScore c = new CollectedItemsScore("Juan");
		c.score = 1;
		xps.addScore(c);
		assertEquals(xps.getScoring(), 41, 0.0);
	}
}
