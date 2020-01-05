package model.persistence;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import model.Inventory;
import model.ItemStack;
import model.Material;
import model.exceptions.StackSizeException;

public class P_test_InventoryAdapter {
	InventoryAdapter inventory;
	
	@Test
	public void constructor_comprobation() throws StackSizeException {
		Inventory i = new Inventory();
		i.addItem(new ItemStack(Material.APPLE, 22));
		i.addItem(new ItemStack(Material.BEDROCK, 33));
		i.addItem(new ItemStack(Material.CHEST, 1));
		i.addItem(new ItemStack(Material.GRANITE, 11));
		i.addItem(new ItemStack(Material.GRASS, 21));
		i.addItem(new ItemStack(Material.IRON_PICKAXE, 1));
		i.addItem(new ItemStack(Material.IRON_SHOVEL, 1));
		i.addItem(new ItemStack(Material.IRON_SWORD, 1));
		i.addItem(new ItemStack(Material.WATER_BUCKET, 1));
		i.addItem(new ItemStack(Material.OBSIDIAN, 33));
		i.addItem(new ItemStack(Material.STONE, 5));
		i.addItem(new ItemStack(Material.BEEF, 6));
		
		inventory = new InventoryAdapter(i);
		assertEquals(new ItemStack(Material.APPLE, 22), inventory.getItem(0));
		assertEquals(new ItemStack(Material.BEDROCK, 33), inventory.getItem(1));
		assertEquals(new ItemStack(Material.CHEST, 1), inventory.getItem(2));
		assertEquals(new ItemStack(Material.GRANITE, 11), inventory.getItem(3));
		assertEquals(new ItemStack(Material.GRASS, 21), inventory.getItem(4));
		assertEquals(new ItemStack(Material.IRON_PICKAXE, 1), inventory.getItem(5));
		assertEquals(new ItemStack(Material.IRON_SHOVEL, 1), inventory.getItem(6));
		assertEquals(new ItemStack(Material.IRON_SWORD, 1), inventory.getItem(7));
		assertEquals(new ItemStack(Material.WATER_BUCKET, 1), inventory.getItem(8));
		assertEquals(new ItemStack(Material.OBSIDIAN, 33), inventory.getItem(9));
		assertEquals(new ItemStack(Material.STONE, 5), inventory.getItem(10));
		assertEquals(new ItemStack(Material.BEEF, 6), inventory.getItem(11));
	}
}
