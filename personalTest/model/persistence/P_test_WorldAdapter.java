package model.persistence;

import org.junit.Test;

import model.World;

public class P_test_WorldAdapter {
	@Test
	public void WorldAdapter_constructor() {
		World w = new World(20, 20, "World", "Juan");
		@SuppressWarnings("unused")
		WorldAdapter wa = new WorldAdapter(w);
	}
}
