package model.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.junit.Before;
import org.junit.Test;

import model.Inventory;
import model.ItemStack;
import model.Location;
import model.Material;
import model.World;
import model.entities.*;
import model.exceptions.BadLocationException;
import model.exceptions.EntityIsDeadException;
import model.exceptions.StackSizeException;

public class P_test_IPlayer {
	IPlayer player;
	boolean option1;
	Player p;
	
	@Before
	public void before_setup() throws EntityIsDeadException, BadLocationException {
		World w = new World(20,20,"World", "Juan");
		p = w.getPlayer();
		p.damage(10);
		if(w.isFree(new Location(w, p.getLocation().getX(), p.getLocation().getY()+1, p.getLocation().getZ()))) {
			p.move(0, 1, 0);
			option1 = true;
		} else {
			p.move(1, 0, 0);
			option1 = false;
		}
		player = new PlayerAdapter(p);
	}
	
	@Test
	public void getHealth_comprobation_start() {
		@SuppressWarnings("deprecation")
		Player p1 = new Player("Juan", new World("World"));
		player = new PlayerAdapter(p1);
		assertEquals(p1.getHealth(), player.getHealth(), 0.0);
	}
	
	@Test
	public void getHealth_comprobation_normal() {
		player = new PlayerAdapter(p);
		assertEquals(p.getHealth(), player.getHealth(), 0.0);
	}
	
	@Test
	public void getInventory_equality_comprobation() throws StackSizeException {
		p.addItemsToInventory(new ItemStack(Material.APPLE, 22));
		p.addItemsToInventory(new ItemStack(Material.BEDROCK, 33));
		player = new PlayerAdapter(p);
		Inventory i = p.getInventory();
		IInventory ii = new InventoryAdapter(i);
		assertEquals(ii.getSize(), player.getInventory().getSize());
	}
	
	@Test
	public void getLocation_comprobation() {
		World w = new World(20,20,"World", "Juan");
		Player p = w.getPlayer();
		IPlayer ip = new PlayerAdapter(p);
		assertEquals(p.getLocation(), ip.getLocation());
	}
	
	@Test
	public void getName_comprobation() {
		World w = new World(20, 20, "World", "Juan");
		Player p = w.getPlayer();
		IPlayer ip = new PlayerAdapter(p);
		assertEquals(p.getName(), ip.getName());
	}
}
