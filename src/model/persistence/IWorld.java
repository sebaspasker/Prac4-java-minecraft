package model.persistence;

import java.util.List;
import java.util.Map;
import java.util.NavigableMap;

import model.Block;
import model.Location;
import model.entities.*;
import model.ItemStack;

// TODO: Auto-generated Javadoc
/**
 * IWorld world interface.
 * @author sebastianpasker
 *
 */
public interface IWorld {
	/**
	 * Obtains ordered map of blocks. Represents 16x16x16 blocks area.
	 * MapBlock, loc corresponds to N-E block from this area.
	 * @param l Location.
	 * @return NavigableMap<Location, Block> class.
	 */
	public NavigableMap<Location, Block> getMapBlock(Location l);
	
	/**
	 * Getter.
	 * @return negative values from world limits.
	 */
	public int getNegativeLimit();
	
	/**
	 * Getter.
	 * @return player from world.
	 */
	public IPlayer getPlayer();
	
	/**
	 * Getter.
	 * @return  positive values from world limits.
	 */
	public int getPositiveLimit();
	
	/**
	 * Getter. List from creatures in a 16x16x16 world.
	 * l corresponds to inferior N-E location.
	 * @param l Location where we wan't to search creatures.
	 * @return List of creatures instance.
	 */
	public List<Creature> getCreatures(Location l);
	
	/**
	 * Map of each ItemStack in 16x16x16 world.
	 * l corresponds to inferior N-E location.
	 * @param l Location instance
	 * @return Map of location and ItemStack relation.
	 */
	public Map<Location, ItemStack> getItems(Location l);
}
