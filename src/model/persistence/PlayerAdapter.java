package model.persistence;

import model.Location;
import model.entities.*;

// TODO: Auto-generated Javadoc
/**
 * PlayerAdapter class. Implements IPlayer inteface.
 * @author sebastianpasker
 *
 */
public class PlayerAdapter implements IPlayer {
	/**
	 * Player instance.
	 */
	private Player player;
	
	/**
	 * IInventory instance.
	 */
	private IInventory inventory;
	
	/**
	 * Constructor.
	 * @param p Player instance.
	 */
	public PlayerAdapter(Player p) {
		player = p;
		inventory = new InventoryAdapter(p.getInventory());
	}
	
	/**
	 * Getter.
	 * @return Player health.
	 */
	@Override
	public double getHealth() {
		return player.getHealth();
	}

	/**
	 * Getter.
	 * @return Inventory from player.
	 */
	@Override
	public IInventory getInventory() {
		return inventory;
	}

	/**
	 * Getter.
	 * @return Location from player.
	 */
	@Override
	public Location getLocation() {
		return player.getLocation();
	}

	/**
	 * Getter.
	 * @return players name.
	 */
	@Override
	public String getName() {
		return player.getName();
	}

}
