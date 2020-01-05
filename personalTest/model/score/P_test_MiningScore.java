package model.score;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import model.BlockFactory;
import model.Material;
import model.exceptions.WrongMaterialException;

public class P_test_MiningScore {
	MiningScore m;
	
	@Before
	public void before_settings() {
		m = new MiningScore("Juan");
	}
	
	@Test
	public void constructor_comprobation() {
		assertEquals("Juan", m.getName());
		assertEquals(0, m.getScoring(), 0.0);
	}
	
	@Test 
	public void compareTo_comprobation_higher() throws WrongMaterialException {
		MiningScore m1 = new MiningScore("Jose");
		m1.score = 1.5;
		assertEquals("Number should be", 1, m.compareTo(m1), 0.0);
		m1.score = 3;
		assertEquals("Number should be", 3, m.compareTo(m1), 0.0);
		m1.score = 4.5;
		assertEquals("Number should be", 4, m.compareTo(m1), 0.0);
		m1.score = 0;
		m.score = -2;
		assertEquals("Number should be", 2, m.compareTo(m1), 0.0);
	}
	
	@Test
	public void compareTo_comprobation_lower() throws WrongMaterialException {
		MiningScore m1 = new MiningScore("Jose");
		m.score = 1;
		assertEquals("Number should be", -1, m.compareTo(m1));
		m.score = 2.5;
		assertEquals("Number should be", -2, m.compareTo(m1));
		m1.score = -2.5;
		m.score = -1;
		assertEquals("Number should be", -1, m.compareTo(m1));
	}
	
	@Test
	public void compareTo_comprobation_equals() {
		MiningScore m1 = new MiningScore("Jose");
		assertEquals("Number should be",0, m.compareTo(m1));
		m.score = 2;
		m1.score = 2;
		assertEquals("Number should be", 0, m.compareTo(m1));
		m.score = -2.5;
		m1.score = -2.5;
		assertEquals("Number should be", 0, m.compareTo(m1));
	}
	
	@Test 
	public void score_comprobation() throws WrongMaterialException {
		m.score(BlockFactory.createBlock(Material.BEDROCK));
		assertEquals("Score should be", -1, m.getScoring(), 0.0);
		m.score(BlockFactory.createBlock(Material.GRANITE));
		assertEquals("Score should be", 0.5, m.getScoring(), 0.0);
		m.score(BlockFactory.createBlock(Material.STONE));
		assertEquals("Score should be", 2, m.getScoring(), 0.0);	
	}
	
}
