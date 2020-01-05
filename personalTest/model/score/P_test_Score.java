package model.score;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class P_test_Score {
	CollectedItemsScore c;
	
	@Before
	public void before_set() {
		c = new CollectedItemsScore("Juan");
	}
	
	@Test
	public void constructor_test() {
		assertEquals("Juan", c.getName());
	}
	
	@Test
	public void toString_test() {
		assertEquals("Score should be",0.0, c.getScoring(), 0.0);
		assertEquals("String should be", "Juan:0.0", c.toString());
	}
}
