package model.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.Block;
import model.BlockFactory;
import model.ItemStack;
import model.Location;
import model.Material;
import model.World;
import model.entities.Animal;
import model.entities.Creature;
import model.entities.LivingEntity;
import model.exceptions.BadLocationException;

public class WorldAdapter_PreP4Test {
	static final int TOTAL_ELEMENTS = 16*16*16;
	static World world75;
    static IWorld iworld75; 
    static Block blockAIR;
    static Location[] lcr;
    World world10;
    IWorld iworld10;
   
    
    @BeforeClass
	public static void setUpBeforeClass() throws Exception {
    	world75 = new World(5,75,"Dangerous World","Paul");
    	iworld75 = new WorldAdapter(world75);
    	blockAIR = BlockFactory.createBlock(Material.AIR);
	}
    
	@Before
	public void setUp() throws Exception {
		world10 =  new World(5, 10,"World 10x10","Joel");
		iworld10 = new WorldAdapter(world10);
	}

	/* Creamos un Wordl de BlockWorld y un IWorld a partir de aquel. Comprobamos
	 * que la localización del Player,y el nombre, son iguales
	 */
	@Test
	public void testWorldAdapter() {
		//World world10x10 =  new World(5, 10,"Joel","World 10x10");
		iworld10 = new WorldAdapter(world10);
		Location loc = new Location(world10,0.0,65.0,0.0);
		assertEquals(LivingEntity.MAX_HEALTH,iworld10.getPlayer().getHealth(),0.01 );
		assertEquals(0,iworld10.getPlayer().getInventory().getSize());
		assertEquals(loc,iworld10.getPlayer().getLocation());
		assertEquals("Joel",iworld10.getPlayer().getName());
	}
	
	/* A partir de una localización dentro de iworld75 (-10,0,-5) comprueba que iworld75.getMapBlock
	 * genera todos los valores de forma correcta .Para ello examina que cada elemento del NavigableMap
	 * obtenido coincide con el mismo elemento en el mapa de world75.
	 * Además comprueba también que:
	 * - las coordenadas del NavigableMap están dentro de los límites [0,15] para x,y,z con el
	 *   método auxiliar okLocation(Location,Location)
	 * - El número total de elementos de NavigableMap es 16³
	 *
	 * Observación: Todos las posiciones están dentro de los límites de world75
	 */
	@Test
	public void testGetMapBlock1() {
		Location base = new Location(world75,-10,0,-5);
		NavigableMap<Location, Block> mapBlock = iworld75.getMapBlock(base);
		Set<Location> setLoc = mapBlock.keySet();
		Location absolute;
		
		Block block;
		int count=0;
		for (Location relative : setLoc) {
			
			absolute = new Location(relative);
			absolute.add(base);
			assertTrue(okLocation(absolute,base)); //Comprobamos que la coordenada es correcta
			try {
				Location l = new Location(world75,absolute.getX(),absolute.getY(),absolute.getZ());
				assertEquals(l,absolute);
			} catch (Exception e) {
					fail("Error: No debió lanzar la excepción "+e.getClass().getName());
			}
			count++;	
		}
		assertEquals("mapBlock tiene 16x16x16 elementos?",TOTAL_ELEMENTS,count);	
	}
	
	/* A partir del extremo noroeste inferior del mundo (-4,0,-5) de world10 comprueba 
	 * que iworld.getMapBlock(Location) genera el mapa de bloques de forma correcta. 
	 * Para ello examina que cada elemento del NavigableMap obtenido coincide con el 
	 * mismo elemento en el mapa de world10.
	 * Además comprueba también que:
	 * - las coordenadas del NavigableMap están dentro de los límites [0,15] para x,y,z con el
	 *   método auxiliar okLocation(Location absolute,Location base)
	 * - El número total de elementos de NavigableMap es 16³
	 * 
	 * Date cuenta que NO todas las posiciones están dentro de los límites de world10
	 */
	@Test
	public void testGetMapBlock2() {
		Location base = new Location(world10,-4,0,-5);
		NavigableMap<Location, Block> mapBlock = iworld10.getMapBlock(base);
		Set<Location> setLoc = mapBlock.keySet();
		Location absolute;
		
		Block block;
		int count=0;
		for (Location relative : setLoc) {
			
			absolute = new Location(relative);
			absolute.add(base);
			assertTrue(okLocation(absolute,base)); //Comprobamos que la coordenada es correcta
			try {
				Location l = new Location(world10,absolute.getX(),absolute.getY(),absolute.getZ());
				assertEquals(l,absolute);
			} catch (Exception e) {
					fail("Error: No debió lanzar la excepción "+e.getClass().getName());
			}
			count++;	
		}
		assertEquals("mapBlock tiene 16x16x16 elementos?",TOTAL_ELEMENTS,count);
		
	}
	
	/* A partir de una localización fuera de world10 (6,256,6) comprueba que getMapBlock
	 * genera todos sus valores a null.
	 *  Además comprueba también que:
	 * - las coordenadas del NavigableMap están dentro de los límites [0,15] para x,y,z con el
	 *   método auxiliar okLocation(Location,Location)
	 * - El número total de elementos de NavigableMap es 16³
	 * 
	 */
	@Test
	public void testGetMapBlock3() {
		Location base = new Location(world10,6,256,6);
		NavigableMap<Location, Block> mapBlock = iworld10.getMapBlock(base);
		Set<Location> setLoc = mapBlock.keySet();
		Location absolute;
		
		int count=0;
		for (Location relative : setLoc) {
			absolute = new Location(relative);
			absolute.add(base);
			assertTrue(okLocation(absolute, base)); //Comprobamos que las coordenadas son correctas
			assertEquals("Location "+relative.toString(),null, mapBlock.get(relative));
		
			count++;	
		}
		assertEquals("mapBlock tiene 16x16x16 elementos?",TOTAL_ELEMENTS,count);
	}
	
	

	@Test
	public void testGetPlayer() {
		World world10x10 =  new World(5, 10,"World 10x10","Joel");
		iworld10 = new WorldAdapter(world10x10);
		Location loc = new Location(world10x10,0.0,65.0,0.0);
		assertEquals(LivingEntity.MAX_HEALTH,iworld10.getPlayer().getHealth(),0.01 );
		assertEquals(0,iworld10.getPlayer().getInventory().getSize());
		assertEquals(loc,iworld10.getPlayer().getLocation());
		assertEquals("Joel",iworld10.getPlayer().getName());
	}

	@Test
	public void testGetPositiveLimit() {
		assertEquals(5,iworld10.getPositiveLimit());
		assertEquals(37,iworld75.getPositiveLimit());
	}

	@Test
	public void testGetNegativeLimit() {
		assertEquals(-4,iworld10.getNegativeLimit());
		assertEquals(-37,iworld75.getNegativeLimit());
	}
	
	
	/* A partir de una localización dentro de iworld75 (0,55,0) comprueba que getCreatures
	 * genera una lista con todas las Creatures que existen de forma correcta. Para ello
	 * analiza que para cada Creature de la lista:
	 *  - sus coordenadas están dentro de los límites [0,15] para x,y,z con el
	 *    método auxiliar okLocation(Location absolute,Location base)
	 *  - que esa Creature está efectivamente en esa posición en world75
	 *  - que hay 3 Animals y 12 Monsters
	 */
	@Test
	public void testGetCreatures1() {
		Location base = new Location(world75,0,55,0);
		List<Creature> listCreature = iworld75.getCreatures(base);

		int nAnimals = 0;
		int nMonsters = 0;
		Location posCreature;
		
		for(int i = 0;i<listCreature.size();i++) {
			posCreature = listCreature.get(i).getLocation();
			if(listCreature.get(i) instanceof Animal) {
				nAnimals++;
			}else {
				nMonsters++;
			}
		}
	    if(nAnimals != 3 || nMonsters != 12) {
	    	fail();
	    }
	}

	
	/* A partir de una localización dentro de iworld10 base=(-4,55,-5) comprueba que getCreatures
	 * genera una lista con todas las Creatures que existen de forma correcta. Para ello
	 * analiza que para cada Creature de la lista:
	 *  - sus coordenadas están dentro de los límites [base,base+15] para x,y,z con el
	 *    método auxiliar okLocation(Location absolute,Location base)
	 *  - que esa Creature está efectivamente en esa posición en world10
	 *  - que hay 4 Animals y 3 Monsters
	 *
	 */
	@Test
	public void testGetCreatures2() {
		Location base = new Location(world10,-4,55,-5);
		List<Creature> listCreature = iworld10.getCreatures(base);

		int nAnimals = 0;
		int nMonsters = 0;
		Location posCreature;
		for(int i = 0;i<listCreature.size();i++) {
			posCreature = listCreature.get(i).getLocation();
			if(listCreature.get(i) instanceof Animal) {
				nAnimals++;
			}else {
				nMonsters++;
			}
		}
	    if(nAnimals != 4 || nMonsters != 3) {
	    	fail();
	    }
	}
	
	/* A partir de una localización dentro de iworld10 (6,256,6) comprueba que getCreatures
	 * genera una lista sin elementos.
	 */
	@Test
	public void testGetCreatures3() {
		Location base = new Location(world10,6,256,6);
		List<Creature> listCreature = iworld10.getCreatures(base);
		assertEquals(0, listCreature.size());
	}
	
/* A partir de una localización dentro de iworld75 (0,55,0) comprueba que iworld75.getItems
 * genera todos los valores de forma correcta .Para ello examina que cada item del Map
 * obtenido coincide con el mismo elemento en dentro del mapa de world75.
 * Además comprueba también que:
 * - las coordenadas del Map obtenido están dentro de los límites [base,base+15] para x,y,z con el
 *   método auxiliar okLocation(Location,Location)
 * - El número total de elementos del mapa obtenido es 24
 *
 *  Todas las posiciones están dentro de los límites de world75
 */
@Test
public void testGetItems1() {
		Location base = new Location(world75,0,55,0);
		Map<Location, ItemStack> mapItems = iworld75.getItems(base);
		Set<Location> setLoc = mapItems.keySet();
		for(Location l:setLoc) {
			assertTrue(okLocation(l,base));
			try {
				assertEquals(world75.getItemsAt(l),mapItems.get(l));
			} catch (BadLocationException e) {
				fail();
			}
		}
		assertEquals(24,mapItems.size(),0.01);
}
  
/* A partir de una localización dentro de iworld10 base=(-4,55,-5) comprueba que 
 * iworld10.getItems genera todos los valores de forma correcta. 
 * Para ello examina que cada item del Map obtenido coincide con el mismo elemento
 * dentro del mapa de world10.
 * Además comprueba también que:
 * - las coordenadas del Map obtenido están dentro de los límites [base,base+15] para x,y,z con el
 *   método auxiliar okLocation(Location,Location)
 * - El número total de elementos del mapa obtenido es el número total de items
 *
 *  Observació: Todas las posiciones NO están dentro de los límites de world10
 */
@Test
public void testGetItems2() {
		Location base = new Location(world10,-4,55,-5);
		Map<Location, ItemStack> mapItems = iworld10.getItems(base);
		Set<Location> setLoc = mapItems.keySet();
		for(Location l:setLoc) {
			assertTrue(okLocation(l,base));
			try {
				assertEquals(world10.getItemsAt(l),mapItems.get(l));
			} catch (BadLocationException e) {
				fail();
			}
		}
		assertEquals("Map tiene 8 elementos?",8,setLoc.size());
		
			
}

/* A partir de una localización fuera de iworld10 base=(6,256,6) comprueba que 
 * iworld10.getItems genera un Map vacío.
 */
@Test
public void testGetItems3() {
		Location base = new Location(world10,6,256,6);
		Map<Location, ItemStack> mapItems = iworld10.getItems(base);
		Set<Location> setLoc = mapItems.keySet();
		
		assertEquals("Map tiene 0 elementos?",0,setLoc.size());			
}	 
	 
 //FUNCIONES AUXILIARES
/* Comprueba que las coordenadas absolutas [absolute.x,absolute.y,absolute.z] están entre 
 * las coordenadas [base.x,base.y,base.z] y [base.x+15, base.y+15, base.z+15] donde
 * base es la posición noroeste más inferior
 */
 boolean okLocation(Location absolute, Location base) {
	 double x = absolute.getX();
	 double y = absolute.getY();
	 double z = absolute.getZ();
	 double bx = base.getX();
	 double by = base.getY();
	 double bz = base.getZ();
	return ((x>=bx)&&(x<bx+16)&&(y>=by)&&(y<by+16)&&(z>=bz)&&(z<bz+16) );
 }
	
}
