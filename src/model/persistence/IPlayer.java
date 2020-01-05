package model.persistence;

import model.Location;

// TODO: Auto-generated Javadoc
/**
 * IPlayer interface.
 * @author sebastianpasker
 *
 */
public interface IPlayer {
	/**
	 * Getter.
	 * @return health
	 */
	public double getHealth();
	
	/**
	 * Getter. Returns a inventory copy.
	 * @return inventory
	 */
	public IInventory getInventory();
	
	/**
	 * Getter.
	 * @return location
	 */
	public Location getLocation();
	
	/**
	 * Getter. Returns name from player.
	 * @return name
	 */
	public String getName();
}
