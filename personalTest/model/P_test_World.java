package model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class P_test_World {
	@Test
	public void Constructor_playerName() {
		World w = new World(50, 50, "World", "Juan");
		assertEquals("El nombre deberia de ser:","Juan", w.getPlayer().getName());
		w = new World(50, 50, "World", "Jose");
		assertEquals("El nombre deberia de ser:","Jose", w.getPlayer().getName());
		w = new World(50, 50, "World", "Eduardo");
		assertEquals("El nombre deberia de ser:","Eduardo", w.getPlayer().getName());
		
	}
}
