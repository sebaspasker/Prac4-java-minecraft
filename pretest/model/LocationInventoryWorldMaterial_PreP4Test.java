package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LocationInventoryWorldMaterial_PreP4Test {
	World w;
	static ItemStack isApple, isGrass;
	
	@BeforeClass
	public static void  setUpBeforeClass() throws Exception {
		isApple= new ItemStack(Material.APPLE, ItemStack.MAX_STACK_SIZE);
		isGrass = new ItemStack(Material.GRASS, 5);
	}

	@Before
	public void setUp() throws Exception {
		w =  new World(5, 20, "World 10x10","Raul");
	}

	//TESTS P4 PARA Location
	/* Comprueba el método location1.compareTo(location2) en el que location1.x y location2.x son
	 * distintos. Analiza que cuando location1.x>location2.x devuelve un valor >0 y si es
	 * location1.x<location2.x devuelve un valor <0
	 */
	@Test
	public void testCompareToDifferentX() {
		Location loc1 = new Location(w, 7, 5, -3);
		Location loc2 = new Location(w, 6.9, 7, -2);
		assertTrue (loc1.compareTo(loc2)>0);
		assertTrue (loc2.compareTo(loc1)<0);
	}

	/* Comprueba el método location1.compareTo(location2) en el que las 'x' son
	 * iguales pero las 'y' distintas . Analiza que cuando location1.y>location2.y 
	 * devuelve un valor >0 y si es  location1.y<location2.y devuelve un valor <0
	 */
	@Test
	public void testCompareToSameXDifferentY() {
		Location l1 = new Location(w,0,3,1);
		Location l2 = new Location(w,0,1,1);
		assertTrue(l1.compareTo(l2) > 0);
		l2.setY(5);
		assertTrue(l1.compareTo(l2)<0);
	}
	
	/* Comprueba el método location1.compareTo(location2) en el que las 'x' y las 'y' son
	 * iguales pero las 'z' distintas . Analiza que cuando location1.z>location2.z 
	 * devuelve un valor >0 y si es  location1.z<location2.z devuelve un valor <0
	 */
	@Test
	public void testCompareToSameXYDifferentZ() {
		Location l1 = new Location(w,0,1,3);
		Location l2 = new Location(w,0,1,1);
		assertTrue(l1.compareTo(l2) > 0);
		l2.setZ(5);
		assertTrue(l1.compareTo(l2) < 0);
	}
	
	/* Comprueba el método location1.compareTo(location2) en el que las 'x', 'y' 
	 * y 'z' son iguales. Analiza que en tal caso se devuelve siempre un valor 0
	 */
	@Test
	public void testCompareToSameXYZ() {
		Location l1 = new Location(w,0,1,1);
		Location l2 = new Location(new World(5, 20, "World 2","PEPE"), 0,1,1);
		assertEquals(l1.compareTo(l2),0,0.01);
	}
	
	//TEST P4 PARA Inventory
	/* Comprueba que el constructor copia de un inventario vacío crea otro igual vacío */
	@Test
	public void testConstructorCopiaInventory1() {
		Inventory inv1 = new Inventory();
		Inventory inv2 = new Inventory(inv1);
		assertEquals(inv1,inv2);
		inv1.addItem(isApple);
		assertNotEquals(inv1,inv2);
	}
	
	/* Crea un inventario y añadele algunos items y pon uno en su mano. Crea un inventario 
	 * nuevo copia del anterior. Comprueba que ambos son iguales
	 */
	@Test
	public void testConstructorCopiaInventory2() {
		Inventory inv1 = new Inventory();
		inv1.addItem(isApple);
		inv1.addItem(isGrass);
		inv1.setItemInHand(isApple);
		Inventory inv2 = new Inventory(inv1);
		assertEquals(inv1,inv2);
	}
		
	//TEST P4 PARA World
	//Comprueba que el nombre del jugador del mundo w es "Raul".
	@Test
	public void testConstructorWorld() {
		assertEquals("Raul",w.getPlayer().getName());
	}
	
	//TEST P4 PARA Material
	/* Comprueba que el material AIR está en la posición 18 de los materiales. Que es un bloque 
	 * y es líquido nada más. Que su valor es 0 y su símbolo ' ' */
	@Test
	public void testAIR() {
		Material air = Material.AIR;
		assertEquals(18,air.ordinal());
		assertEquals(18,air.ordinal());
		assertTrue(air.isBlock());
		assertTrue(air.isLiquid());
		assertFalse(air.isEdible());
		assertFalse(air.isTool());
		assertFalse(air.isWeapon());
		assertEquals(0,air.getValue(),0.01);
		assertEquals(' ',air.getSymbol());
	}
}
