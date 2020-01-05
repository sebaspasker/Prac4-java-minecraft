package model;

import org.junit.Test;

import model.exceptions.BadLocationException;
import model.exceptions.EntityIsDeadException;
import model.exceptions.StackSizeException;
import model.exceptions.WrongMaterialException;
import model.score.CollectedItemsScore;
import model.score.MiningScore;
import model.score.PlayerMovementScore;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Before;

public class P_test_BlockWorld {
	
	public final String preString = "Name=Juan\n" +
									"Location{world=World,x=0.0,y=67.0,z=0.0}\n" + 
									"Orientation=Location{world=World,x=0.0,y=0.0,z=1.0}\n" +
									"Health=20.0\n" +
									"Food level=20.0\n" +
									"Inventory=(inHand=(WOOD_SWORD,1),[])\n" +
									"... ... nnn\n" +
									"... MP. nn.\n" +
									"... nn. dd.\n";
	
	BlockWorld bw;
	World w;
	
	@Before
	public void before_setUp() {
		bw = BlockWorld.getInstance();
	}
	
	@Test
	public void constructor_Score_Initiation_Comprobation() {
		assertNull(bw.getItemsScore());
		assertNull(bw.getMiningScore());
		assertNull(bw.getMovementScore());
	}
	
	@Test
	public void createWorld_playerName() {
		w = bw.createWorld(20, 20, "World", "Juan");
		assertEquals("Expected player name","Juan", w.getPlayer().getName());
	}
	
	@Test
	public void createWorld_Score() {
		w = bw.createWorld(20, 20, "World", "Juan");
		assertNotNull(bw.getItemsScore());
		assertNotNull(bw.getMiningScore());
		assertNotNull(bw.getMovementScore());
	}
	
	@Test
	public void showPlayerInfo_Scores_initiation() {
		w = bw.createWorld(20, 20, "World", "Juan");
		String s = bw.showPlayerInfo(w.getPlayer());
		String comprobation1 = preString + "Scores: [items: 0.0, blocks: 0.0, movements: 0.0]\n";
		assertEquals(comprobation1, s); // TODO Bad implementation
	}
	
	public void changeValues1() throws StackSizeException, WrongMaterialException {
		w = bw.createWorld(20, 20, "World", "Juan");
		CollectedItemsScore i = bw.getItemsScore();
		i.score(new ItemStack(Material.BEDROCK, 3)); // SCORE = -3
		MiningScore m = bw.getMiningScore();
		m.score(new SolidBlock(Material.SAND)); 
		m.score(new SolidBlock(Material.SAND)); // SCORE = 1  
		PlayerMovementScore p = bw.getMovementScore();
		p.score(new Location(w, w.getPlayer().getLocation().getX(), w.getPlayer().getLocation().getY()+2, w.getPlayer().getLocation().getZ())); // SCORE = 2
		
	}
	
	@Test
	public void showPlayerInfo_Scores_diffScores() throws StackSizeException, WrongMaterialException {
		w = bw.createWorld(20, 20, "World", "Juan");
		changeValues1();
		String s = bw.showPlayerInfo(w.getPlayer());
		String comprobation2 = preString + "Scores: [items: -3.0, blocks: 1.0, movements: 0.0]\n";
		assertEquals(s, comprobation2);
	}
	
	@Test
	public void play_playerName() {
		w = bw.createWorld(20, 20, "World", "Juan");
		try {
			bw.playFile("personalTest/files/play_playerName.txt");
			// TODO finish comprobation
			fail("");
		} catch (Exception e) {
			fail("No deberia de haber saltado " + e.getClass() + " " + e.getMessage());
		}
	}
	
	@Test
	public void movePlayer_Score() {
		w = bw.createWorld(20, 20, "World", "Juan");
		Location l = w.getPlayer().getLocation();
		CollectedItemsScore c = bw.getItemsScore();
		PlayerMovementScore p = bw.getMovementScore();
		try {
			w.addItems(new Location(w, l.getX(), l.getY()+1, l.getZ()), new ItemStack(Material.SAND, 20));
			bw.movePlayer(w.getPlayer(), 0, 1, 0);
		} catch (Exception e) {
			fail("Incorrect exception " + e.getMessage() + " " + e.getClass().getName());
		}
		

		try {
			c.score(new ItemStack(Material.SAND, 20));
		} catch (StackSizeException e) {
			fail("Incorrect exception " + e.getMessage() + " " + e.getClass().getName());
		}
		p.score(new Location(w, l.getX(), l.getY()+1, l.getZ()));
		assertEquals(c, bw.getItemsScore());
		assertEquals(p, bw.getMovementScore());
	}
	
	@Test
	public void useItem_Score() throws IllegalArgumentException, EntityIsDeadException  {
		w = bw.createWorld(20, 20, "World", "Juan");
		MiningScore m = bw.getMiningScore();
		bw.useItem(w.getPlayer(), 50);
		
		try {
			m.score(new SolidBlock(Material.SAND));
		} catch (WrongMaterialException e) {
			fail("Incorrect exception " + e.getMessage() + " " + e.getClass().getName());
		}
		
		assertEquals(m, bw.getMiningScore());
	}
}
