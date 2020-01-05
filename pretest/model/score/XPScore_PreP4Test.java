package model.score;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.BlockFactory;
import model.ItemStack;
import model.Location;
import model.Material;
import model.SolidBlock;
import model.World;
import model.entities.Player;
import model.exceptions.StackSizeException;
import model.exceptions.score.ScoreException;

public class XPScore_PreP4Test {

	XPScore xpJulia, xpCharles;
	Player pJulia, pCharles;
	World world;
	CollectedItemsScore cis;
	MiningScore ms;
	PlayerMovementScore pms;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@Before
	public void setUp() throws Exception {
		world = new World(3,10,"A Little World", "Joan");
		pJulia = new Player("Julia",world);
		pCharles = new Player("Charles",world);
		xpJulia = new XPScore(pJulia);
		xpCharles = new XPScore(pCharles);
	}

	@Test
	public void testXPScoreAndGetName() {
		Score<Player> score = xpJulia; //Implementas la herencia ???
		assertEquals("Julia",score.getName());	
		
		assertEquals("Julia",xpJulia.getName());
		assertEquals("Charles", xpCharles.getName());
	}

	/* Comparar las puntuaciones de xpJulia y de xpCharles modificando solo
	 * el health y el foodLevel
	 */	
	@Test
	public void testCompareTo1() {
			//Inicialmente ambas puntuaciones son iguales 
			assertTrue(xpJulia.compareTo(xpCharles)==0);
			
			xpCharles.score(pCharles);
			assertTrue(xpJulia.compareTo(xpCharles)==0);
			
			/* Modifica el health de Julia y comprueba que
			 * si xpJulia invoca a compareTo, devuelve un valor >0
			 * al ser el score de xpJulia menor
			 */	 
			pJulia.damage(3);
			assertTrue(xpJulia.compareTo(xpCharles)>0);
			
			/* Modifica el foodLevel de Charles al mismo valor que
			 * el health de Julia y comprueba que compareTo devuelve 0
			 */
			pCharles.setFoodLevel(pJulia.getHealth());
			assertTrue(xpJulia.compareTo(xpCharles)==0);
	}
	
	/* Comparar los XPScore de xpJulia y de xpCharles sin modificar
	 * health y foodLevel pero sí añadiendo a la lista de XPScore de xpJulia 
	 * y xpCharles: un CollectedItemsScore, un MiningScore y un PlayerMovementScore 
	 * sucesivamente.
	 */
	@Test
	public void testCompareTo2() {
		try {
			//Añadimos un ItemScore al marcador de Julia
			cis = new CollectedItemsScore("Julia");
			cis.score(new ItemStack(Material.BREAD,5));
			xpJulia.addScore(cis);
			assertTrue(xpJulia.compareTo(xpCharles)<0);	
			//Añadimos el mismo ItemScore al marcador de Charles
			xpCharles.addScore(cis);
			assertTrue(xpCharles.compareTo(xpJulia)==0);
			//mining score para los dos
			ms = new MiningScore("Julia");
			ms.score(new SolidBlock(Material.GRASS));
			xpJulia.addScore(ms);
			xpCharles.addScore(ms);
			assertTrue(xpCharles.compareTo(xpJulia)==0);
			//Player Movement score para los dos
			pms = new PlayerMovementScore("Julia");
			pms.score(new Location(pJulia.getLocation().getWorld(),pJulia.getLocation().getX()+1,pJulia.getLocation().getY()+1,pJulia.getLocation().getZ()+1));
			xpJulia.addScore(pms);
			pms = new PlayerMovementScore("Charles");
			pms.score(new Location(pCharles.getLocation().getWorld(),pCharles.getLocation().getX()+1,pCharles.getLocation().getY()+1,pCharles.getLocation().getZ()+1));
			xpCharles.addScore(pms);
			assertTrue(xpCharles.compareTo(xpJulia)==0);
		} catch (Exception e) {
			fail ("Error, no debió lanzar la excepcion "+e.getClass().getName());
		}
	}

	/* Comprobar que inicialmente, sin calcular, xpJulia tiene el marcador a 0. 	 
	 * Calcular el Score inicial de xpJulia, y comprobar que es 40. Modificar
	 * el health y calcular y comprobar el nuevo resultado. Hacer lo mismo con
	 * foodLevel.
	 */
	@Test
	public void testScorePlayer() {
		assertEquals(0, xpJulia.score,0.01);
		
		xpJulia.score(pJulia);
		assertEquals(40, xpJulia.score,0.01);
		
		pJulia.damage(3);
		xpJulia.score(pJulia);
		assertEquals(37, xpJulia.score,0.01);
		
		pJulia.setFoodLevel(17);
		xpJulia.score(pJulia);
		assertEquals(34, xpJulia.score,0.01);
	}	
	
	//Se comprueba la excepción ScoreException en el método score
	@Test(expected=ScoreException.class)
	public void testScorePlayerException() {
		Player p = new Player("Marta",world);
		xpJulia.score(p);
	}
	
	
	/* Añadir un CollectedIntemsScore a xpJulia, y comprobar
	 * que lo que obteneis es lo esperado.
	 * Hacer lo mismo con un MininScore y un PlayerMovementScore
	 */
	@Test
	public void testAddScorePlayer() {
		cis = new CollectedItemsScore("Julia");
		try {
			cis.score(new ItemStack(Material.BREAD,5));
		} catch (StackSizeException e) {
			fail();
		}
		xpJulia.addScore(cis);
		assertEquals(65, xpJulia.score,0.01);
	}

	/* Comprobar lo mismo que en el testScorePlayer1 pero con 
	 * getScore()
	 */
	@Test
	public void testGetScoring() {
	    
	}
	
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

	/* Repite lo mismo que en testScorePlayer() y testAddScorePlayer pero usando
	 * toString() en los asseretEquals
	 */
	@Test
	public void testToString() {
		
		compareScores("Julia:0.0", xpJulia.toString());
		
		xpJulia.score(pJulia);
		assertEquals("Julia:40.0", xpJulia.toString());
		
		pJulia.damage(3);
		xpJulia.score(pJulia);
		assertEquals("Julia:37.0", xpJulia.toString());
		
		pJulia.setFoodLevel(17);
		xpJulia.score(pJulia);
		assertEquals("Julia:34.0", xpJulia.toString());
		
		pJulia.setFoodLevel(Player.MAX_FOODLEVEL);
		pJulia.setHealth(Player.MAX_HEALTH);
		
		try {
			cis = new CollectedItemsScore("Julia");
			cis.score(new ItemStack(Material.BREAD,5));
			xpJulia.addScore(cis);
			assertEquals("Julia:65.0", xpJulia.toString());
			
			ms = new MiningScore("Julia");
			ms.score(new SolidBlock(Material.GRASS));
			xpJulia.addScore(ms);
			assertEquals("Julia:52.8", xpJulia.toString());
			
			pms = new PlayerMovementScore("Julia");
			Location l = new Location(pJulia.getLocation().getWorld(),pJulia.getLocation().getX()+1,pJulia.getLocation().getY()+1,pJulia.getLocation().getZ()+1);
			pms.score(l);
			xpJulia.addScore(pms);
			double media = (25+0.6)/3;
			media = media + 40;
			assertEquals("Julia:"+media, xpJulia.toString());
			
			
		} catch (Exception e) {
			fail ("Error, no debió lanzar la excepcion "+e.getClass().getName());
		}
		
	}	
}
