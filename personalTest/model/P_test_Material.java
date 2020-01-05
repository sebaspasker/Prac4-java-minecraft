package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class P_test_Material {
	@Test
	public void Air_Material_Comprobation() {
		Material m = Material.AIR;
		assertTrue(m.isLiquid());
		assertTrue(m.isBlock());
		assertEquals("El valor deberia de ser",0,m.getValue(), 0.0);
		assertEquals("El simbolo deberia de ser" , ' ', m.getSymbol());
	}
}
