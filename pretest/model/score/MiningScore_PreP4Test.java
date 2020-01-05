package model.score;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.Block;
import model.BlockFactory;
import model.Material;

public class MiningScore_PreP4Test {

	MiningScore scLaura;
	MiningScore scPeter;
	static Block chest,sand,dirt,grass,stone,granite,obsidian,lava,air,water;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		chest = BlockFactory.createBlock(Material.CHEST);
		sand = BlockFactory.createBlock(Material.SAND);
		dirt = BlockFactory.createBlock(Material.DIRT);
		grass = BlockFactory.createBlock(Material.GRASS);
		stone = BlockFactory.createBlock(Material.STONE);
		granite = BlockFactory.createBlock(Material.GRANITE);
		obsidian = BlockFactory.createBlock(Material.OBSIDIAN);
		lava =  BlockFactory.createBlock(Material.OBSIDIAN);
		air =  BlockFactory.createBlock(Material.AIR);
		water =  BlockFactory.createBlock(Material.WATER);
	}
	
	@Before
	public void setUp() throws Exception {
		scLaura = new MiningScore("Laura");
		scPeter = new MiningScore("Peter");
	}

	/* Comprueba con getName() que los nombres de los jugadores de los dos
	 * MiningScore son correctos
	 */
	@Test
	public void testMiningScoreAndGetName() {
		assertEquals("Laura", scLaura.getName());
		assertEquals("Peter", scPeter.getName());
	}

	/* Inicialmente aplica score sobre los Scores scLaura y 
	 * scPeter con los mismos bloques. Comprueba que compareTo da 0.
	 * Aplica sobre uno de ellos con score, un bloque que incremente su puntuación.
	 * Comprueba ahora que el que ha aumentado, si es el que invoca a compareTo da un
	 * valor negativo y si es el menor el que lo invoca, da un valor positivo.
	 */
	@Test
	public void testCompareTo() {
		assertTrue(scPeter.compareTo(scLaura)==0);
		scPeter.score(granite);
		scLaura.score(granite);
		assertTrue(scPeter.compareTo(scLaura)==0);
		scPeter.score(granite);
		assertTrue(scPeter.compareTo(scLaura)==-1);
	}

	/* Toma alguno de los bloques estáticos creados al inicio. Con cada uno, 
	 * crea las puntuaciones (score) sobre el MiningScore scLaura y comprueba 
	 * con getScoring() que  los valores que se van obteniendo se van 
	 * acumulando sucesivamente. Por ejemplo:
	 * chest --> 0.1
	 * sand -->0.6
	 * dirt -->1.1
	 * ...
	 * Haz también algo parecido con scPeter
	 */
	@Test
	public void testScoreBlock() {
		assertEquals(0,scLaura.getScoring(),0.01);
		scLaura.score(chest);
		assertEquals(0.1,scLaura.getScoring(),0.01);
		scLaura.score(sand);
		assertEquals(0.6,scLaura.getScoring(),0.01);
		scLaura.score(dirt);
		assertEquals(1.1,scLaura.getScoring(),0.01);
	}

	/* Comprueba toString sobre scLaura y comprueba que inicialmente es: "Laura:0.0"
	 * Toma algunos bloques del principio y aplica el método score sobre scLaura. 
	 * Comprueba que la salida va cambiando de valor.
	 */
	@Test
	public void testToString() {
		assertEquals("Laura:0.0",scLaura.toString());
		scLaura.score(chest);
		assertEquals("Laura:0.1",scLaura.toString());
		scLaura.score(sand);
		assertEquals("Laura:0.6",scLaura.toString());
		scLaura.score(dirt);
		assertEquals("Laura:1.1",scLaura.toString());
	}

	
	/**********************************************/
	/**********************************************/
	//FUNCIONES DE APOYO
	/* Para las salidas Score.toString() compara los valores impresos
	 * de los Scores hasta una precisión de 0.01
	 * 
	 */
	void compareScores(String expected, String result ) {
		String ex[]= expected.split(":");
		String re[]= result.split(":");
		if (ex.length!=re.length) fail("Lineas distintas");
		if (ex.length==2) {
			if (ex[0].trim().equals(re[0].trim())) {
				double ed = Double.parseDouble(ex[1]);
				double rd = Double.parseDouble(re[1]);
		
				assertEquals(ex[0],ed,rd,0.01);
			}
			else fail("Nombres jugadores distintos: esperado=<"+ex[0].trim()+"> obtenido=<"+re[0].trim()+">");
		}
		else
			assertEquals(expected.trim(),result.trim());		
	}	

}
