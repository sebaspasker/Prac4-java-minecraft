package model.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import model.Inventory;
import model.ItemStack;
import model.Material;
import model.exceptions.StackSizeException;

public class P_test_IInventory {
	IInventory inventory;
	
	@Before
	public void before_setup() throws StackSizeException {
		Inventory i = new Inventory();
		try {
			i.addItem(new ItemStack(Material.BEDROCK, 30));
			i.addItem(new ItemStack(Material.IRON_PICKAXE, 1));
			i.addItem(new ItemStack(Material.APPLE, 22));
			i.addItem(new ItemStack(Material.SAND, 11));
			i.addItem(new ItemStack(Material.WATER_BUCKET, 1));
		} catch (Exception e) {
			fail("No deberia de haber saltado " + e.getMessage() + " "+ e.getClass().getName());
		}
		inventory = new InventoryAdapter(i);
	}
	
	@Test
	public void getItem_normal_comprobation() {
		ItemStack item1 = inventory.getItem(0);
		ItemStack item2 = inventory.getItem(1);
		ItemStack item3 = inventory.getItem(2);
		ItemStack item4 = inventory.getItem(3);
		ItemStack item5 = inventory.getItem(4);
		try {
			assertEquals(new ItemStack(Material.BEDROCK, 30), item1);
			assertEquals(new ItemStack(Material.IRON_PICKAXE, 1), item2);
			assertEquals(new ItemStack(Material.APPLE, 22), item3);
			assertEquals(new ItemStack(Material.SAND, 11), item4);
			assertEquals(new ItemStack(Material.WATER_BUCKET, 1), item5);
		} catch (Exception e) {
			fail("No deberia de haber saltado " +  e.getClass().getName());
		}
	}
	
	@Test
	public void getItem_null_comprobation() {
		ItemStack itemHull1 = inventory.getItem(-1);
		ItemStack itemNull2 = inventory.getItem(5);
		assertNull(itemHull1);
		assertNull(itemNull2);
	}
	
	@Test
	public void getSize_comprobation() {
		assertEquals(5, inventory.getSize());
		Inventory i = new Inventory();
		inventory = new InventoryAdapter(i);
		assertEquals(0,inventory.getSize());
		try {
			i.addItem(new ItemStack(Material.GRASS, 40));
		} catch (Exception e) {
			fail("No deberia de haber saltado" + e.getClass().getName());
		}
		inventory = new InventoryAdapter(i);
		assertEquals(inventory.getSize(), 1);
	}
	
	@Test
	public void inHandItem_comprobation_null() {
		Inventory i = new Inventory();
		i.setItemInHand(null);
		inventory = new InventoryAdapter(i);
		assertNull(inventory.inHandItem());
	}
	
	@Test
	public void inHandItem_comprobation_normal() throws StackSizeException {
		Inventory i = new Inventory();
		i.setItemInHand(new ItemStack(Material.IRON_SWORD, 1));
		inventory = new InventoryAdapter(i);
		assertEquals(new ItemStack(Material.IRON_SWORD, 1), inventory.inHandItem());
		i.setItemInHand(new ItemStack(Material.APPLE, 20));
		inventory = new InventoryAdapter(i);
		assertEquals(new ItemStack(Material.APPLE, 20), inventory.inHandItem());
	}
}
