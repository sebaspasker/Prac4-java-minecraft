package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.junit.Test;

import model.entities.*;
import model.exceptions.StackSizeException;

public class P_test_Player {
	@Test
	public void getInventory_test() throws StackSizeException {
		@SuppressWarnings("deprecation")
		World w = new World("WORLD");
		Player p = new Player("Juan", w);
		p.addItemsToInventory(new ItemStack(Material.APPLE, 20));
		p.addItemsToInventory(new ItemStack(Material.BEDROCK, 11));
		p.addItemsToInventory(new ItemStack(Material.APPLE, 20));
		p.addItemsToInventory(new ItemStack(Material.BEDROCK, 11));
		
		Inventory i = p.getInventory();
		assertNotSame(i, p.getInventory());
		assertEquals(4, i.getSize());
	}
}
