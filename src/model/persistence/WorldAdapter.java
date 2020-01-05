package model.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import model.Block;
import model.ItemStack;
import model.LiquidBlock;
import model.Location;
import model.Material;
import model.World;
import model.entities.Creature;

// TODO: Auto-generated Javadoc
/**
 * WorldAdapter class.
 *
 * @author sebastianpasker
 */
public class WorldAdapter implements IWorld {
	
	/** World instance. */
	private World world;
	
	/** IPlayer instance. */
	private IPlayer player;
	
	/**
	 * Constructor.
	 * @param w World instance
	 */
	public WorldAdapter(World w) {
		player = new PlayerAdapter(w.getPlayer());
		world = w;
	}
	
	/**
	 * Getter of 16*16*16 Area.
	 *
	 * @param NELocation N-E Location inferior plant
	 * @return Location List with all locations.
	 */
	private List<Location> get16AreaLocations(Location NELocation) {
		List<Location> locationList = new ArrayList<Location>();
		Location l;
		for(int j=0; j<16; j++) {
			for(int i=0; i<16; i++) {
				for(int k=0; k<16; k++) {
					l = new Location(world, NELocation.getX()+i, NELocation.getY()+j, NELocation.getZ()+k);
					locationList.add(l); 
				}
			}
		}
		
		return locationList;
	}
	
	/**
	 * NavigableMap function. Returns a NavigableMap instance with
	 * 16 local area blocks.
	 * @param l Location instance
	 * @return NavigableMap instance
	 */
	@Override 
	public NavigableMap<Location, Block> getMapBlock(Location l) {
		NavigableMap<Location, Block> nm = new TreeMap<>();
		
		for(int i=0; i < 16; i++) {
			for(int j=0; j < 16; j++) {
				for(int k=0; k < 16; k++) {
					Location relativeValue = new Location(this.world, i, j, k);
					Location absolutValue = new Location(this.world, l.getX()+i, l.getY()+j, l.getZ()+k);
					try {
						if(!Location.check(absolutValue)) {
							nm.put(relativeValue, null);
						} else {
							if (world.getBlockAt(absolutValue) != null) {
								nm.put(relativeValue, world.getBlockAt(relativeValue));
							} else {
								nm.put(relativeValue, new LiquidBlock(Material.AIR));
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}	
			}	
		}
		
		return nm;
	}
	
	/**
	 * Getter.
	 * @return Negative Limit from world.
	 */
	@Override
	public int getNegativeLimit() {
		return (world.getSize()%2==1) ? (-1)*(world.getSize()/2): (world.getSize()/2-1)*(-1); 
	}

	/**
	 * Getter.
	 * @return Player from world in IPlayer format.
	 */
	@Override
	public IPlayer getPlayer() {
		return player;
	}

	/**
	 * Getter.
	 * @return positive Limit from World
	 */
	@Override
	public int getPositiveLimit() {
		return world.getSize()/2;
	}

	/**
	 * Getter. 
	 * @param l NE Location
	 * @return Creature list with all creatures there are in 16x16x16 area. 
	 */
	@Override
	public List<Creature> getCreatures(Location l) {
		List<Location> locationList = get16AreaLocations(l);
		List<Creature> creatureList = new ArrayList<Creature>();
		
		for(Location loc : locationList) {
			try {
				if(world.getCreatureAt(loc) != null) {
					creatureList.add(world.getCreatureAt(loc));
				}
			} catch (Exception e) {
				System.err.println("Incorrect location");
			}
		}
		return creatureList;
	}

	/**
	 * Getter.
	 * @param l NE location.
	 * @return item Map with all items in 16x16x16 area. 
	 */
	@Override
	public Map<Location, ItemStack> getItems(Location l) {
		List<Location> locationList = get16AreaLocations(l);
		Map<Location, ItemStack> itemMap = new HashMap<Location, ItemStack>();
		
		for(Location loc : locationList) {
			try {
				if(world.getItemsAt(loc) != null) {
					itemMap.put(loc, world.getItemsAt(loc));
				}
			} catch (Exception e) {
				System.err.println("Incorrect location");
			}
		}
		
		return itemMap;
	}

}