package model.score;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import model.Location;
import model.World;

public class P_test_PlayerMovementScore {
	PlayerMovementScore ps;
	
	@Before
	public void before_settings() {
		ps = new PlayerMovementScore("Sebastian");
	} 
	
	@Test
	public void PlayerMovementScore_constructor() {
		assertEquals("Name should be", "Sebastian", ps.getName());
		assertEquals("Score should be", 0, ps.getScoring(), 0.0);
	}
	
	@Test
	public void compareTo_comprobation_higher() {
		PlayerMovementScore ps1 = new PlayerMovementScore("Juan");
		ps.score = 2;
		assertEquals("Number should be", 2, ps.compareTo(ps1));
		ps1.score = 1;
		assertEquals("Number should be", 1, ps.compareTo(ps1));
		ps.score = 100;
		ps1.score = -1.5;
		assertEquals("Number should be", 101, ps.compareTo(ps1));
	}
	
	@Test
	public void compareTo_comprobation_equal() {
		PlayerMovementScore ps1 = new PlayerMovementScore("Juan");
		assertEquals("Number should be", 0, ps.compareTo(ps1));
		ps.score = 30.5;
		ps1.score = 30.5;
		assertEquals("Number should be", 0, ps.compareTo(ps1));
		ps.score = -22.4;
		ps1.score = -22.4;
		assertEquals("Number should be", 0, ps.compareTo(ps1));
	}
	
	@Test
	public void compareTo_comprobation_lower() {
		PlayerMovementScore ps1 = new PlayerMovementScore("Juan");
		ps.score = -1;
		assertEquals("Number should be",-1, ps.compareTo(ps1));
		ps.score = 2.4;
		ps1.score = 5;
		assertEquals(-2, ps.compareTo(ps1));
		ps.score = -1;
		assertEquals(-6, ps.compareTo(ps1));
	}
	
	@Test 
	public void score_comprobation() {
		World w = new World(20, 20, "default_world", "default_name");
		Location l = new Location(w, 1,0,0);
		ps.score(l);
		assertEquals("Score should be", 0.0, ps.getScoring(), 0.0);
		l = new Location(w, 1,1,0);
		ps.score(l);
		assertEquals("Score should be", 1.0, ps.getScoring(), 0.0);
		l = new Location(w, 1,1,1);
		ps.score(l);
		assertEquals(2.0, ps.getScoring(), 0.0);
		l = new Location(w,-1,0,0);
		ps.score(l);
		assertEquals(3.0, ps.getScoring(), 0.0);
	}
	
}
