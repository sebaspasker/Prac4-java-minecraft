package model.score;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import model.ItemStack;
import model.Material;
import model.exceptions.StackSizeException;
import model.exceptions.WrongMaterialException;

public class P_test_CollectedItemScore {
	CollectedItemsScore c;
	CollectedItemsScore c1;
	@Before
	public void before_settings() throws StackSizeException {
		c = new CollectedItemsScore("Jose");
		c1 = new CollectedItemsScore("Juan");
	}
	
	@Test
	public void CollectedItemsScore_constructor() {
		assertEquals("Name should be","Jose",c.getName());
		assertEquals("Puntuation should be", 0, c.getScoring(), 0.0);
	}
	
	@Test 
	public void compareTo_comprobation_higher() throws WrongMaterialException {
		c1.score = 1.5;
		assertEquals("Number should be", 1, c.compareTo(c1), 0.0);
		c1.score = 3;
		assertEquals("Number should be", 3, c.compareTo(c1), 0.0);
		c1.score = 4.5;
		assertEquals("Number should be", 4, c.compareTo(c1), 0.0);
		c1.score = 0;
		c.score = -2;
		assertEquals("Number should be", 2, c.compareTo(c1), 0.0);
	}
	
	@Test
	public void compareTo_comprobation_lower() throws WrongMaterialException {
		c.score = 1;
		assertEquals("Number should be", -1, c.compareTo(c1));
		c.score = 2.5;
		assertEquals("Number should be", -2, c.compareTo(c1));
		c1.score = -2.5;
		c.score = -1;
		assertEquals("Number should be", -1, c.compareTo(c1));
	}
	
	@Test
	public void compareTo_comprobation_equals() {
		assertEquals("Number should be",0, c.compareTo(c1));
		c.score = 2;
		c1.score = 2;
		assertEquals("Number should be", 0, c.compareTo(c1));
		c.score = -2.5;
		c1.score = -2.5;
		assertEquals("Number should be", 0, c.compareTo(c1));
	}
	
	@Test 
	public void score_comprobation() throws StackSizeException {
		ItemStack i = new ItemStack(Material.BEDROCK, 20);
		c.score(i);
		assertEquals("Score should be",-1*20, c.getScoring(), 0.0);
		c.score(i);
		assertEquals("Score should be",  -1*20*2, c.getScoring(), 0.0);
		i = new ItemStack(Material.STONE, 20);
		c.score(i);
		assertEquals("Score should be", -1*20*2+1.5*20, c.getScoring(), 0.0);
		c.score(i);
		assertEquals("Score should be", -1*20*2+1.5*20*2, c.getScoring(), 0.0);
	}
}
