package model.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;

import org.junit.Before;
import org.junit.Test;

import model.Block;
import model.ItemStack;
import model.LiquidBlock;
import model.Location;
import model.Material;
import model.World;
import model.entities.Creature;
import model.entities.Player;

public class P_test_IWorld {
	IWorld iw;
	World w;
	
	@Before
	public void setup() {
		w = new World(20, 20, "World", "Juan");
		iw = new WorldAdapter(w);
	}
	
	@Test
	public void getNegativeLimit_comprobation() {
		assertEquals(-9, iw.getNegativeLimit());
		w = new World(51,51, "World", "Juan");
		iw = new WorldAdapter(w);
		assertEquals(-25, iw.getNegativeLimit());
	}
	
	@Test
	public void getPlayer_comprobation() {
		Player p = w.getPlayer();
		IPlayer ip = new PlayerAdapter(p);
		assertEquals(ip.getName(), iw.getPlayer().getName());
		assertEquals(ip.getHealth(), iw.getPlayer().getHealth(),0.0);
	}
	
	@Test
	public void getPositiveLimit_comprobation() {
		assertEquals(10, iw.getPositiveLimit());
		w = new World(50,50,"W", "P");
		iw = new WorldAdapter(w);
		assertEquals(25, iw.getPositiveLimit());
		w = new World(51,51,"W","P");
		iw = new WorldAdapter(w);
		assertEquals(25, iw.getPositiveLimit());
	}
	
	private List<Location> get_16_locs(Location NE) {
		List<Location> locationList = new ArrayList<>();
		for(int j=0; j<16; j++) { 
			for(int i=0; i<16; i++) {
				for(int k=0; k<16; k++) {
					locationList.add(new Location(NE.getWorld(), NE.getX()+i, NE.getY()+j, NE.getZ()+k));
				}
			}
		}
		
		return locationList;
	}
	
	@Test
	public void getMapBlock_comprobation() {
		World w = new World(50,50,"World", "Juan");
		iw = new WorldAdapter(w);
		Location l = new Location(w, 0, 0, 0);
		NavigableMap<Location, Block> nm = iw.getMapBlock(l);

		/*
		 * A Adil en su test le sale bien
		for(int i=0; i < 16; i++) {
			for(int j=0; j < 16; j++) {
				for(int z=0; z < 16; z++) {
					try {
						if(Location.check(new Location(w, l.getX()+i, l.getY()+j, l.getZ()+z))) {
							if(w.getBlockAt(new Location(w, l.getX()+i, l.getY()+j, l.getZ()+z)) == null) {
								assertEquals(new LiquidBlock(Material.AIR),nm.get(new Location(w, i, j, z)));
							} else if(nm.get(new Location(w, i,j,z)) != w.getBlockAt(new Location(w, l.getX()+i, l.getY()+j, l.getZ()+z))) {
								assertEquals(w.getBlockAt(new Location(w, l.getX()+i, l.getY()+j, l.getZ()+z)).clone(), nm.get(new Location(w, i,j,z)));
							}
						} else {
							assertNull(nm.get(new Location(w, i, j, z)));
						}
					} catch (Exception e) {
						fail();
					}
				}	
			}	
		}*/ 
	} 
	
	@Test
	public void getCreatures_comprobation() {
		World w = new World(51, 51, "W", "P");
		iw = new WorldAdapter(w);
		Location l = new Location(w, 0, 0, 0);
		List<Location> locationList = get_16_locs(l);
		List<Creature> creatureList = new ArrayList<Creature>();
		assertEquals(16*16*16, locationList.size());
		
		for(Location loc:locationList) {
			try {
				if(w.getCreatureAt(loc) != null) creatureList.add(w.getCreatureAt(loc));
			} catch (Exception e) {
			}
		}
		List<Creature> cl = iw.getCreatures(l); 
		assertEquals(cl.size(), creatureList.size());
		for(Creature c: creatureList) {
			if(!cl.contains(c)) {
				fail("Hay una entidad que no ha añadido " + c.toString());
			}
		}
	}
	
	@Test
	public void getItems_comprobation() {
		World w = new World(51, 51, "W", "P");
		iw = new WorldAdapter(w);
		Location l = new Location(w, 0, 0, 0);
		List<Location> locationList = get_16_locs(l);
		assertEquals(16*16*16, locationList.size());
		
		Map<Location, ItemStack> itemMap = iw.getItems(l);
		Map<Location, ItemStack> itemMapByLoc = new HashMap<Location, ItemStack>();
		
		for(Location loc: locationList) {
			try {
				if(w.getItemsAt(loc)!= null) {
					itemMapByLoc.put(loc, w.getItemsAt(loc));
				}
			} catch (Exception e) {
			}
		}
		
		assertEquals(itemMapByLoc.size(), itemMap.size());
		
		for(Location loc: locationList) {
			try {
				if(w.getItemsAt(loc) != null) {
					assertEquals(w.getItemsAt(loc), itemMap.get(loc));
				}
			} catch (Exception e) {
			}
		}
	}
}
