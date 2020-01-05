package model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class P_test_Location {
	Location loc1, loc2;
	World w;
	
	@SuppressWarnings("deprecation")
	@Before
	public void before_settings() {
		w = new World("WORLD1");
	}
	

	@Test
	public void compareTo_Location_lower() {
		loc1 = new Location(w, 0, 0, 0);
		loc2 = new Location(w, 1, 0, 0);
		assertEquals(loc1.compareTo(loc2), -1, 0.0);
		loc1 = new Location(w, 0, 0, 0);
		loc2 = new Location(w, 0, 1, 0);
		assertEquals(loc1.compareTo(loc2), -1, 0.0);
		loc1 = new Location(w, 0, 0, 0);
		loc2 = new Location(w, 0, 0, 1);
		assertEquals(loc1.compareTo(loc2), -1, 0.0);
	}
	
	@Test
	public void compareTo_Location_lower_big_values() {
		loc1 = new Location(w, 500, 500, 500);
		loc2 = new Location(w, 1000, 0, 0);
		assertEquals(loc1.compareTo(loc2), -500, 0.0);
		loc1 = new Location(w, 500, 500, 500);
		loc2 = new Location(w, 500, 1000, 0);
		assertEquals(loc1.compareTo(loc2), -500, 0.0);
		loc1 = new Location(w, 500, 500, 500);
		loc2 = new Location(w, 500, 500, 1000);
		assertEquals(loc1.compareTo(loc2), -500, 0.0);
	}
	
	@Test
	public void compareTo_Location_same() {
		loc1 = new Location(w, 0, 0, 0);
		loc2 = new Location(w, 0, 0, 0);
		assertEquals(loc1.compareTo(loc2), 0, 0.0);
		loc1 = new Location(w, 500, 500, 500);
		loc2 = new Location(w, 500, 500, 500);
		assertEquals(loc1.compareTo(loc2), 0, 0.0);
		loc1 = new Location(w, -500, -500, -500);
		loc2 = new Location(w, -500, -500, -500);
		assertEquals(loc1.compareTo(loc2), 0, 0.0);
	}
	
	@Test
	public void compareTo_Location_higher() {
		loc1 = new Location(w, 1, 0, 0);
		loc2 = new Location(w, 0, 0, 0);
		assertEquals(loc1.compareTo(loc2), 1, 0.0);
		loc1 = new Location(w, 0, 1, 0);
		loc2 = new Location(w, 0, 0, 0);
		assertEquals(loc1.compareTo(loc2), 1, 0.0);
		loc1 = new Location(w, 0, 0, 1);
		loc2 = new Location(w, 0, 0, 0);
		assertEquals(loc1.compareTo(loc2), 1, 0.0);
	}
	
	@Test
	public void compareTo_Location_higher_bignumber() {
		loc1 = new Location(w, 5000, 0, 0);
		loc2 = new Location(w, 4000, 4000, 4000);
		assertEquals(loc1.compareTo(loc2), 1000, 0.0);
		loc1 = new Location(w, 4000, 5000, 0);
		loc2 = new Location(w, 4000, 4000, 4000);
		assertEquals(loc1.compareTo(loc2), 1000, 0.0);
		loc1 = new Location(w, 4000, 4000, 5000);
		loc2 = new Location(w, 4000, 4000, 4000);
		assertEquals(loc1.compareTo(loc2), 1000, 0.0);
	}
	
	@Test(expected = NullPointerException.class)
	public void compareTo_Location_Null() {
		loc1 = new Location(w, 0, 0, 0);
		loc1.compareTo(loc2);
	}
}
