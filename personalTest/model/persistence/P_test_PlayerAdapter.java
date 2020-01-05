package model.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import model.Inventory;
import model.ItemStack;
import model.Material;
import model.World;
import model.entities.Player;

public class P_test_PlayerAdapter {
	PlayerAdapter pa;
	
	@Test
	public void constructor_comprobation() {
		World w = new World(20, 20, "World", "Juan");
		Player p = w.getPlayer();
		try {
			p.addItemsToInventory(new ItemStack(Material.BEEF, 20));
			p.addItemsToInventory(new ItemStack(Material.SAND, 11));
		} catch (Exception e) {
			fail("");
		}
		Inventory i = p.getInventory();
		IInventory ii = new InventoryAdapter(i);
		pa = new PlayerAdapter(p);
		assertEquals(ii.getSize(), pa.getInventory().getSize());	
	}
}
