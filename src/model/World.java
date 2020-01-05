package model;

import java.util.ArrayList;  
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.bukkit.util.noise.CombinedNoiseGenerator;
import org.bukkit.util.noise.OctaveGenerator;
import org.bukkit.util.noise.PerlinOctaveGenerator;

import model.entities.*;
import model.exceptions.BadLocationException;
import model.exceptions.StackSizeException;
import model.exceptions.WrongMaterialException;


// TODO: Auto-generated Javadoc
/**
 * The Class World.
 *
 * @author Sebastian Pasker Y0025435B
 * @version 1.0
 */
public class World {
	
	/**
	 * The name.
	 *
	 */
	private String name;
	
	/**
	 * The player.
	 *
	 */
	private Player player;
	
	/**
	 * The world size.
	 *
	 */
	private int worldSize;
	
	/**
	 * The seed.
	 *
	 */
	private long seed;
	
	/**
	 * The blocks.
	 *
	 */
	private Map<Location, Block> blocks;
	
	/**
	 * The items.
	 *
	 */
	private Map<Location, ItemStack> items;

	/**
	 * The creatures.
	 *
	 */
	private Map<Location, Creature> creatures;
	
	 /**
 	 * Constructor with name introduction.
 	 *
 	 * @param name the name
 	 */ 
	@Deprecated
    public World(String name) {
        this.name = name;
    }
    
	/**
	 * Constructor with name introduction.
	 *
	 * @param seed Id for world creation
	 * @param size of world.
	 * @param name World name entrance
	 * @throws IllegalArgumentException  In case size is < 0
	 */
	@Deprecated
	public World(long seed, int size, String name) throws IllegalArgumentException {
		if(size < 0) {
			throw new IllegalArgumentException();
		}
		
		this.name = name;
		worldSize = size;
		this.seed = seed; 
		blocks = new HashMap<Location, Block>();
		items = new HashMap<Location, ItemStack>();
		creatures = new HashMap<Location, Creature>();
		generate(seed, size);
	} 
	
	/**
	 * Constructor with name introduction.
	 *
	 * @param seed Id for world creation
	 * @param size of world.
	 * @param name World name entrance
	 * @param playerName name of a player in world
	 * @throws IllegalArgumentException  In case size is < 0
	 */
	public World(long seed, int size, String name,String playerName) throws IllegalArgumentException {
		if(size < 0) {
			throw new IllegalArgumentException();
		}
		
		this.name = name;
		worldSize = size;
		this.seed = seed; 
		blocks = new HashMap<Location, Block>();
		items = new HashMap<Location, ItemStack>();
		creatures = new HashMap<Location, Creature>();
		generate(seed, size);
		player = new Player(playerName, this);
	} 
	
	/**
	 * Getter.
	 * @return worldSize this value
	 */
	public int getSize() {
		return worldSize;
	}
	
	/**
	 * Getter.
	 * @return seed this value
	 */
	public long getSeed() {
		return seed;
	} 

	/**
	 * Getter.
	 * @return name World String name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Getter.
	 * @return player Player Object
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * GetBlockAt return a block in case there is one in passed Location.
	 *
	 * @param loc Location object
	 * @return block in case there is one related.
	 * @throws BadLocationException Exception in case not same world or not in correct location.
	 */
	/*public Block getBlockAt(Location loc) throws BadLocationException {
		if(!this.equals(loc.getWorld()) || loc.getWorld() == null) {
			throw new BadLocationException(this.name);
		}
		
		if(blocks.containsKey(loc) && Location.check(loc)) {
			return blocks.get(loc);
		} else {	
			return null;
		}
	}*/
	
	public Block getBlockAt(Location loc) throws BadLocationException {
		if(loc.getWorld() == null || !loc.getWorld().equals(this)) {
			throw new BadLocationException("La posicion no se encuentra en este mundo.");
		}
		
		Block block = null;
		
		if(blocks.get(loc) != null) {
			block = blocks.get(loc).clone();
		}
		
    	return block;
    }
	
	/**
	 * Return a itemstack in case there is one in passed Location.
	 *
	 * @param loc Location object
	 * @return item of position in case it's sane world and correct position.
	 * @throws BadLocationException In case not same world or not correct location
	 */
	public ItemStack getItemsAt(Location loc) throws BadLocationException {
		if(loc == null || loc.getWorld() == null) {
			throw new BadLocationException("NULL");
		} else if(!this.equals(loc.getWorld()) || !Location.check(loc)) {
			throw new BadLocationException(this.name);
		}
		
		if(items.containsKey(loc)) {
			return items.get(loc);
		} else {
			return null;
		}
	}
	
	/**
	 * Return Neighbourhood in string format.
	 *
	 * @param loc Location object
	 * @return String of neighbourhood
	 * @throws BadLocationException In case world is null
	 */
	public String getNeighbourhoodString(Location loc) throws BadLocationException {
		StringBuilder sb = new StringBuilder();
		
		if (loc.getWorld() == null|| this == null) {
			throw new BadLocationException("NULL");
		} else if(this.equals(loc.getWorld()) == false) {
			throw new BadLocationException(loc.getWorld().getName());
		}
		
		int cont = 0;
		
		for(int k=-1; k<2; k++) {
			for(int j=1; j>-2; j--) {
				for(int i=-1; i<2; i++) {
					Location l = new Location(loc.getWorld(), loc.getX()+i, loc.getY()+j, loc.getZ()+k);
					if(Location.check(l)) {
						if(player.getLocation().equals(l)) { // Case Player is in position
							sb.append('P');
						} else if(items.containsKey(l)) { // Case ItemStack is in position
							try {
								char letter = items.get(l).getType().getSymbol(); 
								if((letter >= 'a' || letter <= 'z') || (letter >='A' || letter <= 'Z')) { 
									sb.append(Character.toUpperCase(items.get(l).getType().getSymbol()));
								} else {
									sb.append(letter);
								}
							} catch(Exception e) {
							}
						} else if(creatures.containsKey(l)) { // Case Creatures is in position
							sb.append(creatures.get(l).getSymbol());
						} else if(blocks.containsKey(l)) {
							char letter = Character.toLowerCase(blocks.get(l).getType().getSymbol());
							try {
								if((letter >= 'a' || letter <= 'z') || (letter >='A' || letter <= 'Z')) { 
									sb.append(letter);
								} else {
									sb.append(letter);
								}
							} catch(Exception e) {
							}
						} else if(this.isFree(l)) { // Case position is free
							sb.append('.');
						} 
					} else {
						sb.append("X");
					}
					
					if((cont+1)%9 == 0) {
						sb.append("\n");
					} else if((cont+1)%3 == 0) {
						sb.append(" ");
					}
					
					cont++;
				}
			}
		}
		
		return sb.toString();

	}
	
	/**
	 * Remove Item at passed position.
	 * @param loc Location object
	 * @throws BadLocationException In case no position at passed location
	 */
	public void removeItemsAt(Location loc) throws BadLocationException {
		if(items.containsKey(loc) && this.equals(loc.getWorld()) && Location.check(loc)) {
			items.remove(loc);
		} else {
			throw new BadLocationException(loc.getWorld().name);
		}
	}
	
	/**
	 * Hash Code.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (int) (seed ^ (seed >>> 32));
		result = prime * result + worldSize;
		return result;
	}
	
	/**
	 * Equal function.
	 *
	 * @param obj the obj
	 * @return true, if successful
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		World other = (World) obj;
		if (name == null) {
			if (other.name != null)
				return false; 
		} else if (!name.equals(other.name))
			return false;
		if (seed != other.seed)
			return false;
		if (worldSize != other.worldSize)
			return false;
		return true;
	}

	/**
	 * toString function.
	 *
	 * @return string name
	 */
	@Override
	public String toString() {
		return name;
	}
	
	/**
	 * Get highest location at passed Location.
	 *
	 * @param ground Location object where we see max Y pos
	 * @return l Location object with new y position
	 * @throws BadLocationException Exception
	 */
	public Location getHighestLocationAt(Location ground) throws BadLocationException {
		double yMAXPOSITION;
		
		if(ground.getWorld() == null) {
			throw new BadLocationException("null");
		}

		
		if(ground.getWorld().equals(this) == false  || Location.check(ground) == false) {
			throw new BadLocationException(ground.getWorld().getName());
		} else {
			yMAXPOSITION = this.heightMap.get(ground.getX(), ground.getZ());
		}
		Location l = new Location(ground.getWorld(),ground.getX(), yMAXPOSITION, ground.getZ());
		if(items.containsKey(l)) {
			l.setY(l.getY()+1);
		}
		return l;
	}
	
	/**
	 * See actual location is free.
	 *
	 * @param l Location object
	 * @return false in case no block or item else true
	 * @throws BadLocationException the bad location exception
	 */
	public boolean isFree(Location l) throws BadLocationException {
		boolean isFree = true;
		
		if(this.equals(l.getWorld()) == false) {
			throw new BadLocationException(l.getWorld().getName()); 
		}
		
		if(blocks.containsKey(l)) {
			if(!blocks.get(l).getType().isLiquid()) {
				isFree = false;
			}
		} else if(player.getLocation().equals(l)) {
			isFree = false;
		} else if(creatures.containsKey(l)) {
			isFree = false;
		}
		
		return isFree;
	}
	
	/**
	 * Adds the block.
	 *
	 * @param l the l
	 * @param b the b
	 * @throws BadLocationException the bad location exception
	 */
	public void addBlock(Location l, Block b) throws BadLocationException {
		if(l.getWorld() == null || l == null) {
			throw new BadLocationException("Null");
		} else if(!Location.check(l) || !this.equals(l.getWorld()) || player.getLocation().equals(l)) { // Case location not in world limits, not same world or same location of Player
			throw new BadLocationException(l.getWorld().name);
		} else {
			if(blocks.containsKey(l)) { // Remove block in position 
				blocks.remove(l);
			}
			if(creatures.containsKey(l)) { // Remove creature in position l
				creatures.remove(l);
			}
			if(items.containsKey(l)) { // Remove item in position l
				items.remove(l);
			}
			
			blocks.put(l,b.clone());
			if(heightMap.get(l.getX(), l.getZ()) < l.getY()) {
				heightMap.set(l.getX(), l.getZ(), l.getY());
			}
		}
	}
	
	/**
	 * Adds the creature.
	 *
	 * @param c the c
	 * @throws BadLocationException the bad location exception
	 */
	public void addCreature(Creature c) throws BadLocationException {
		if (c.getLocation().getWorld() == null|| this == null || c.getLocation() == null || c == null) {
			throw new BadLocationException("NULL");
		} else if(!c.getLocation().getWorld().equals(this) || !Location.check(c.getLocation()) || !this.isFree(c.getLocation())) { // Case location not in world limits, not same world or same location of Player 
			throw new BadLocationException(c.getLocation().getWorld().getName());
		} else {
			if(items.containsKey(c.getLocation())) { // Case items exists in creature position --> remove it
				items.remove(c.getLocation());
			}
			
			creatures.put(c.getLocation(), c); 
		}
	}
	
	/**
	 * Adds the items.
	 *
	 * @param l the location where we want to add item
	 * @param item the item we want to add
	 * @throws BadLocationException in case no equality or the position is not free
	 * 
	 */
	public void addItems(Location l, ItemStack item) throws BadLocationException {
		if(!l.getWorld().equals(this) || !Location.check(l) || !this.isFree(l)) {
			throw new BadLocationException(l.getWorld().getName());
		}
		
		if(items.containsKey(l)) { // In case there is already a item removes it
			items.remove(l);
		}
		
		items.put(l,item); // New item addition
	}
	
	/**
	 * Destroy block at.
	 *
	 * @param l the l
	 * @throws BadLocationException the bad location exception
	 */
	public void destroyBlockAt(Location l) throws BadLocationException {
		if(l.getWorld() == null || l == null) {
			throw new BadLocationException("NULL");
		} else if (!this.equals(l.getWorld())|| l.getY()== 0 ) { 
			throw new BadLocationException(l.getWorld().getName());
		} else if(!blocks.containsKey(l) ) {
			throw new BadLocationException(l.getWorld().getName());
		} else {
			Location NewLocationForItem = new Location(this, l.getX(), l.getY()-1, l.getZ());
			
			if(items.containsKey(l) && blocks.containsKey(NewLocationForItem)) { // In case down side there is a block and there is a item --> y = y-1 from item
				ItemStack itemInBlock = items.get(l);
				items.remove(l);
				items.put(NewLocationForItem, itemInBlock);
			}
			
			if(blocks.get(l).getType() == Material.CHEST) { // Case block is a CHEST
				SolidBlock sb = (SolidBlock)blocks.get(l);
				ItemStack i = sb.getDrops();
				items.put(l, i);
			}
			
			blocks.remove(l); // Remove block in location
			if(heightMap.get(l.getX(), l.getZ()) == l.getY()) {
				double nextLouderY=0;
				
				for(double i=l.getY(); i>0; i--) {
					if(blocks.containsKey(new Location(this, l.getX(), i, l.getZ()))) {
						nextLouderY = i;
						break;
					}
				}
				
				heightMap.set(l.getX(), l.getZ(), nextLouderY);
				
			}
		}
	}
	
	/**
	 * Gets the creature at.
	 *
	 * @param l the l
	 * @return the creature at position, case no creature return null
	 * @throws BadLocationException the bad location exception
	 */
	public Creature getCreatureAt(Location l) throws BadLocationException {
		if(l == null || l.getWorld() == null) {
			throw new BadLocationException("NULL");
		}else if(!l.getWorld().equals(this)) {
			throw new BadLocationException(l.getWorld().getName());
		}
		
		if(creatures.containsKey(l)) {
			return creatures.get(l);
		} else {
			return null; // Case no creature in creature map
		}
	}
	
	/**
	 * Gets the nearby creatures.
	 *
	 * @param l the l
	 * @return the nearby creatures
	 * @throws BadLocationException the bad location exception
	 */
	public Collection<Creature> getNearbyCreatures(Location l) throws BadLocationException {
		if(l == null || l.getWorld() == null) {
			throw new BadLocationException("NULL");
		} else if(!l.getWorld().equals(this)) {
			throw new BadLocationException(l.getWorld().getName());
		}
		
		Collection<Creature> creaturesAdj = new ArrayList<Creature>();
		Set<Location> locationsAdj = l.getNeighborhood(); // Save all adjacent position from passed location
		
		for(Location lA:locationsAdj) {
			if(creatures.containsKey(lA)) {
				creaturesAdj.add(creatures.get(lA)); // In case creature in position save creature
			}
		}
		return creaturesAdj;
	}
	
	/**
	 * Kill creature.
	 *
	 * @param l the l
	 * @throws BadLocationException the bad location exception
	 */
	public void killCreature(Location l) throws BadLocationException {
		if(l.getWorld() == null || l == null) {
			throw new BadLocationException("NULL");
		} else if(!l.getWorld().equals(this) || !creatures.containsKey(l) || this == null) {
			throw new BadLocationException(l.getWorld().getName());
		}
		
		creatures.remove(l);
	}
	
	
	/** Esta clase interna representa un mapa de alturas bidimiensional
	 * que nos servirá para guardar la altura del terreno (coordenada 'y')
	 * en un array bidimensional, e indexarlo con valores 'x' y 'z' positivos o negativos.
	 * 
	 * la localización x=0,z=0 queda en el centro del mundo. 
	 * Por ejemplo, un mundo de tamaño 51 tiene su extremo noroeste a nivel del mar en la posición (-25,63,-25) 
	 * y su extremo sureste, también a nivel del mar, en la posición (25,63,25). 
	 * Para un mundo de tamaño 50, estos extremos serán (-24,63,-24) y (25,63,25), respectivamente.
	 * 
	 * Por ejemplo, para obtener la altura del terreno en estas posiciones, invocaríamos al método get() de esta  clase:
	 *   get(-24,24) y get(25,25)
	 * 
	 * de forma análoga, si queremos modificar el valor 'y' almacenado, haremos
	 *   set(-24,24,70)
	 *
	 */
	class HeightMap {
		
		/**
		 * The height map.
		 *
		 */
		double[][] heightMap;
		
		/**
		 * The positive world limit.
		 *
		 */
    	int positiveWorldLimit;
    	
    	/**
	     * The negative world limit.
	     *
	     */
    	int negativeWorldLimit;

    	
    	/**
	     *  Constructor.
	     *
	     * @param worldsize It's world size
	     */
		HeightMap(int worldsize) {
			heightMap = new double[worldsize][worldsize];
			positiveWorldLimit  = worldsize/2;
			negativeWorldLimit = (worldsize % 2 == 0) ? -(positiveWorldLimit-1) : -positiveWorldLimit;
		}
		
		/**
		 * obtiene la atura del  terreno en la posición (x,z).
		 *
		 * @param x coordenada 'x' entre 'positiveWorldLimit' y 'negativeWorldLimit'
		 * @param z coordenada 'z' entre 'positiveWorldLimit' y 'negativeWorldLimit'
		 * @return y value with max height
		 */
		double get(double x, double z) {
			return heightMap[(int)x - negativeWorldLimit][(int)z - negativeWorldLimit];
		}
		
		/**
		 * Setter.
		 *
		 * @param x Three-dimensional value
		 * @param z Three-dimensional value
		 * @param y Three-dimensional value
		 */
		void set(double x, double z, double y) {
			heightMap[(int)x - negativeWorldLimit][(int)z - negativeWorldLimit] = y;
		}
	}
	
	
	/**
	 * Coordenadas 'y' de la superficie del mundo. Se inicializa en generate() y debe actualizarse
	 * cada vez que el jugador coloca un nuevo bloque en una posición vacía
	 * Puedes usarlo para localizar el bloque de la superficie de tu mundo.
	 */
	private HeightMap heightMap;
	  
	  /**
     * Genera un mundo nuevo del tamaño size*size en el plano (x,z). Si existían elementos anteriores en el mundo,  
     * serán eliminados. Usando la misma semilla y el mismo tamaño podemos generar mundos iguales
     * @param seed semilla para el algoritmo de generación. 
     * @param size tamaño del mundo para las dimensiones x y z
     */
    private  void generate(long seed, int size) {
    	
    	Random rng = new Random(getSeed());

    	blocks.clear();
    	creatures.clear();
    	items.clear();
    	
    	// Paso 1: generar nuevo mapa de alturas del terreno
    	heightMap = new HeightMap(size);
    	CombinedNoiseGenerator noise1 = new CombinedNoiseGenerator(this);
    	CombinedNoiseGenerator noise2 = new CombinedNoiseGenerator(this);
    	OctaveGenerator noise3 = new PerlinOctaveGenerator(this, 6);
    	
    	System.out.println("Generando superficie del mundo...");
    	for (int x=0; x<size; x++) {
    		for (int z=0; z<size; z++) {
    	    	double heightLow = noise1.noise(x*1.3, z*1.3) / 6.0 - 4.0;
    	    	double heightHigh = noise2.noise(x*1.3, z*1.3) / 5.0 + 6.0;
    	    	double heightResult = 0.0;
    	    	if (noise3.noise(x, z, 0.5, 2) / 8.0 > 0.0)
    	    		heightResult = heightLow;
    	    	else
    	    		heightResult = Math.max(heightHigh, heightLow);
    	    	heightResult /= 2.0;
    	    	if (heightResult < 0.0)
    	    		heightResult = heightResult * 8.0 / 10.0;
    	    	heightMap.heightMap[x][z] = Math.floor(heightResult + Location.SEA_LEVEL);
    		}
    	}
    	
    	// Paso 2: generar estratos
    	SolidBlock block = null;
    	Location location = null;
    	Material material = null;
    	OctaveGenerator noise = new PerlinOctaveGenerator(this, 8);
    	System.out.println("Generando terreno...");
    	for (int x=0; x<size; x++) {
    		for (int z=0; z<size; z++) {
    	    	double dirtThickness = noise.noise(x, z, 0.5, 2.0) / 24 - 4;
    	    	double dirtTransition = heightMap.heightMap[x][z];
    	    	double stoneTransition = dirtTransition + dirtThickness;
    	    	for (int y=0; y<= dirtTransition; y++) {
    	    		if (y==0) material = Material.BEDROCK;
    	    		else if (y <= stoneTransition) 
    	    			material = Material.STONE;
    	    		else // if (y <= dirtTransition)
    	    			material = Material.DIRT;
					try {
						location = new Location(this,x+heightMap.negativeWorldLimit,y,z+heightMap.negativeWorldLimit);
						block = new SolidBlock(material);
						if (rng.nextDouble() < 0.5) // los bloques contendrán item con un 50% de probabilidad
							block.setDrops(block.getType(), 1);
						blocks.put(location, block);
					} catch (WrongMaterialException | StackSizeException e) {
						// Should never happen
						e.printStackTrace();
					}
    	    	}

    		}
    	}
    	
    	// Paso 3: Crear cuevas
    	int numCuevas = size * size * 256 / 8192;
		double theta = 0.0;
		double deltaTheta = 0.0;
		double phi = 0.0;
		double deltaPhi = 0.0;

		System.out.print("Generando cuevas");
    	for (int cueva=0; cueva<numCuevas; cueva++) {
    		System.out.print("."); System.out.flush();
    		Location cavePos = new Location(this,rng.nextInt(size),rng.nextInt((int)Location.UPPER_Y_VALUE), rng.nextInt(size));
    		double caveLength = rng.nextDouble() * rng.nextDouble() * 200;
    		//cave direction is given by two angles and corresponding rate of change in those angles,
    		//spherical coordinates perhaps?
    		theta = rng.nextDouble() * Math.PI * 2;
    		deltaTheta = 0.0;
    		phi = rng.nextDouble() * Math.PI * 2;
    		deltaPhi = 0.0;
    		double caveRadius = rng.nextDouble() * rng.nextDouble();

    		for (int i=1; i <= (int)caveLength ; i++) {
    			cavePos.setX(cavePos.getX()+ Math.sin(theta)*Math.cos(phi));
    			cavePos.setY(cavePos.getY()+ Math.cos(theta)*Math.cos(phi));
    			cavePos.setZ(cavePos.getZ()+ Math.sin(phi));
    			theta += deltaTheta*0.2;
    			deltaTheta *= 0.9;
    			deltaTheta += rng.nextDouble();
    			deltaTheta -= rng.nextDouble();
    			phi /= 2.0;
    			phi += deltaPhi/4.0; 
    			deltaPhi *= 0.75;
    			deltaPhi += rng.nextDouble();
    			deltaPhi -= rng.nextDouble();
    			if (rng.nextDouble() >= 0.25) {
    				Location centerPos = new Location(cavePos);
    				centerPos.setX(centerPos.getX() + (rng.nextDouble()*4.0-2.0)*0.2);
    				centerPos.setY(centerPos.getY() + (rng.nextDouble()*4.0-2.0)*0.2);
    				centerPos.setZ(centerPos.getZ() + (rng.nextDouble()*4.0-2.0)*0.2);
    				double radius = (Location.UPPER_Y_VALUE - centerPos.getY()) / Location.UPPER_Y_VALUE;
    				radius = 1.2 + (radius * 3.5 + 1) * caveRadius;
    				radius *= Math.sin(i * Math.PI / caveLength);
    				try {
    					fillOblateSpheroid( centerPos, radius, null);
    				} catch (WrongMaterialException e) {
    					// Should not occur
    					e.printStackTrace();
    				}
    			}

    		}
    	}
    	System.out.println();
    	
    	// Paso 4: crear vetas de minerales
    	// Abundancia de cada mineral
    	double abundance[] = new double[2];
    	abundance[0] = 0.5; // GRANITE
    	abundance[1] =  0.3; // OBSIDIAN
    	int numVeins[] = new int[2];
    	numVeins[0] = (int) (size * size * 256 * abundance[0]) / 16384; // GRANITE
    	numVeins[1] =  (int) (size * size * 256 * abundance[1]) / 16384; // OBSIDIAN

    	Material vein = Material.GRANITE;
    	for (int numVein=0 ; numVein<2 ; numVein++, vein = Material.OBSIDIAN) { 
    		System.out.print("Generando vetas de "+vein);
    		for (int v=0; v<numVeins[numVein]; v++) {
    			System.out.print(vein.getSymbol());
    			Location veinPos = new Location(this,rng.nextInt(size),rng.nextInt((int)Location.UPPER_Y_VALUE), rng.nextInt(size));
    			double veinLength = rng.nextDouble() * rng.nextDouble() * 75 * abundance[numVein];
    			//cave direction is given by two angles and corresponding rate of change in those angles,
    			//spherical coordinates perhaps?
    			theta = rng.nextDouble() * Math.PI * 2;
    			deltaTheta = 0.0;
    			phi = rng.nextDouble() * Math.PI * 2;
    			deltaPhi = 0.0;
    			//double caveRadius = rng.nextDouble() * rng.nextDouble();
    			for (int len=0; len<(int)veinLength; len++) {
    				veinPos.setX(veinPos.getX()+ Math.sin(theta)*Math.cos(phi));
    				veinPos.setY(veinPos.getY()+ Math.cos(theta)*Math.cos(phi));
    				veinPos.setZ(veinPos.getZ()+ Math.sin(phi));
    				theta += deltaTheta*0.2;
    				deltaTheta *= 0.9;
    				deltaTheta += rng.nextDouble();
    				deltaTheta -= rng.nextDouble();
    				phi /= 2.0;
    				phi += deltaPhi/4.0;
    				deltaPhi *= 0.9; // 0.9 for veins
    				deltaPhi += rng.nextDouble();
    				deltaPhi -= rng.nextDouble();
    				double radius = abundance[numVein] * Math.sin(len * Math.PI / veinLength) + 1;

    				try {
    					fillOblateSpheroid(veinPos, radius, vein);
    				} catch (WrongMaterialException ex) {
    					// should not ocuur
    					ex.printStackTrace();
    				}
    			}
    		}
    		System.out.println();
    	}
    	
    	System.out.println();

    	// flood-fill water     	
    	char water= Material.WATER.getSymbol();

    	int numWaterSources = size*size/800;
    	
    	System.out.print("Creando fuentes de agua subterráneas");
    	int x = 0;
    	int z = 0;
    	int y = 0;
    	for (int w=0; w<numWaterSources; w++) {
    		System.out.print(water);
    		x = rng.nextInt(size)+heightMap.negativeWorldLimit;
    		z = rng.nextInt(size)+heightMap.negativeWorldLimit;
    		y = (int)Location.SEA_LEVEL - 1 - rng.nextInt(2);
    		try {
				floodFill(Material.WATER, new Location(this,x,y,z));
			} catch (WrongMaterialException | BadLocationException e) {
				// no debe suceder
				throw new RuntimeException(e);
			}
    	}
    	System.out.println();
   
    	System.out.print("Creando erupciones de lava");
    	char lava = Material.LAVA.getSymbol();
    	// flood-fill lava
    	int numLavaSources = size*size/2000;
    	for (int w=0; w<numLavaSources; w++) {
    		System.out.print(lava);
    		x = rng.nextInt(size)+heightMap.negativeWorldLimit;
    		z = rng.nextInt(size)+heightMap.negativeWorldLimit;
    		y = (int)((Location.SEA_LEVEL - 3) * rng.nextDouble()* rng.nextDouble());
    		try {
				floodFill(Material.LAVA, new Location(this,x,y,z));
			} catch (WrongMaterialException  | BadLocationException e) {
				// no debe suceder
				throw new RuntimeException(e);			
			}
    	}
    	System.out.println();

    	// Paso 5. crear superficie, criaturas e items
    	// Las entidades aparecen sólo en superficie (no en cuevas, por ejemplo)

    	OctaveGenerator onoise1 = new PerlinOctaveGenerator(this, 8);
    	@SuppressWarnings("unused")
		OctaveGenerator onoise2 = new PerlinOctaveGenerator(this, 8);
    	boolean sandChance = false;
    	double entitySpawnChance = 0.05;
    	double itemsSpawnChance = 0.10;
    	double foodChance = 0.8;
    	double toolChance = 0.1;
    	@SuppressWarnings("unused")
		double weaponChance = 0.1;
    	
    	System.out.println("Generando superficie del terreno, entidades e items...");
    	for (x=0; x<size; x++) {    		
    		for (z=0; z<size; z++) {
    			sandChance = onoise1.noise(x, z, 0.5, 2.0) > 8.0;
    			y = (int)heightMap.heightMap[(int)x][(int)z];
    			Location surface = new Location(this,x+heightMap.negativeWorldLimit,y,z+heightMap.negativeWorldLimit); // la posición (x,y+1,z) no está ocupada (es AIR)
    			try {
	    			if (sandChance) {
	    				SolidBlock sand = new SolidBlock(Material.SAND);
	    				if (rng.nextDouble() < 0.5)
	    					sand.setDrops(Material.SAND, 1);
	    				blocks.put(surface, sand);
	    			}
	    			else {
	    				SolidBlock grass = new SolidBlock(Material.GRASS);
	    				if (rng.nextDouble() < 0.5)
	    					grass.setDrops(Material.GRASS, 1);
	    				blocks.put(surface, grass);
	    			}
    			} catch (WrongMaterialException | StackSizeException ex) {
    				// will never happen
    				ex.printStackTrace();
    			}
    			// intenta crear una entidad en superficie
    			try {
    				Location aboveSurface = surface.above();
    				
    				if (rng.nextDouble() < entitySpawnChance) {
    					Creature entity =null;
    					double entityHealth = rng.nextInt((int)LivingEntity.MAX_HEALTH)+1;
    					if (rng.nextDouble() < 0.75) // generamos Monster (75%) o Animal (25%) de las veces
    						entity = new Monster(aboveSurface, entityHealth);
    					else 
    						entity = new Animal(aboveSurface, entityHealth);
    					creatures.put(aboveSurface, entity);
    				} else { 
    					// si no, intentamos crear unos items de varios tipos (comida, armas, herramientas)
    					// dentro de cofres
    					Material itemMaterial = null;
    					int amount = 1; // p. def. para herramientas y armas
    					if (rng.nextDouble() < itemsSpawnChance) {
    						double rand = rng.nextDouble();
    						if (rand < foodChance) { // crear comida
    							// hay cuatro tipos de item de comida, en las posiciones 8 a 11 del array 'materiales'
    							itemMaterial = Material.getRandomItem(8, 11);
    							amount = rng.nextInt(5)+1;
    						}
    						else if (rand < foodChance+toolChance)
    							// hay dos tipos de item herramienta, en las posiciones 12 a 13 del array 'materiales'
    							itemMaterial = Material.getRandomItem(12, 13);
    						else
    							// hay dos tipos de item arma, en las posiciones 14 a 15 del array 'materiales'
    							itemMaterial = Material.getRandomItem(14, 15);
    						
    						items.put(aboveSurface, new ItemStack(itemMaterial, amount));
    					}
    				}
    			} catch (BadLocationException | StackSizeException e) {
    				// BadLocationException : no hay posiciones más arriba, ignoramos creación de entidad/item sin hacer nada 
    				// StackSizeException : no se producirá
    				throw new RuntimeException(e);    			}

    		}
    	}
    	    	
    	// Generar jugador
    	player = new Player("Steve",this);
    	// El jugador se crea en la superficie (posición (0,*,0)). Asegurémonos de que no hay nada más ahí
    	Location playerLocation = player.getLocation();
    	creatures.remove(playerLocation);
    	items.remove(playerLocation);
    	
    }
    /**
     * Where fillOblateSpheroid() is a method which takes a central point, a radius and a material to fill to use on the block array.
     * @param centerPos central point
     * @param radius radius around central point
     * @param material material to fill with
     * @throws WrongMaterialException if 'material' is not a block material
     */
    private void fillOblateSpheroid(Location centerPos, double radius, Material material) throws WrongMaterialException {
    	
				for (double x=centerPos.getX() - radius; x< centerPos.getX() + radius; x += 1.0) {					
					for (double y=centerPos.getY() - radius; y< centerPos.getY() + radius; y += 1.0) {
						for (double z=centerPos.getZ() - radius; z< centerPos.getZ() + radius; z += 1.0) {
							double dx = x - centerPos.getX();
							double dy = y - centerPos.getY();
							double dz = z - centerPos.getZ();
							
							if ((dx*dx + 2*dy*dy + dz*dz) < radius*radius) {
								// point (x,y,z) falls within level bounds ?
								// we don't need to check it, just remove or replace that location from the blocks map.
								Location loc = new Location(this,Math.floor(x+heightMap.negativeWorldLimit),Math.floor(y),Math.floor(z+heightMap.negativeWorldLimit));
								if (material==null)
									blocks.remove(loc);
								else try { //if ((Math.abs(x) < worldSize/2.0-1.0) && (Math.abs(z) < worldSize/2.0-1.0) && y>0.0 && y<=Location.UPPER_Y_VALUE)
									SolidBlock veinBlock = new SolidBlock(material);
									// los bloques de veta siempre contienen material
									veinBlock.setDrops(material, 1);
									blocks.replace(loc, veinBlock);
								} catch  (StackSizeException ex) {
									// will never happen
									ex.printStackTrace();
								}
							}
						}
					}
				}
	}

    /**
     * Flood fill.
     *
     * @param liquid the liquid
     * @param from the from
     * @throws WrongMaterialException the wrong material exception
     * @throws BadLocationException the bad location exception
     */
    private void floodFill(Material liquid, Location from) throws WrongMaterialException, BadLocationException {
    	if (!liquid.isLiquid())
    		throw new WrongMaterialException(liquid);
    	if (!blocks.containsKey(from))
    	{
    		blocks.put(from, BlockFactory.createBlock(liquid));
    		items.remove(from);
    		Set<Location> floodArea = getFloodNeighborhood(from);
    		for (Location loc : floodArea) 
    			floodFill(liquid, loc);
    	}
    }
    
	/**
	 * Obtiene las posiciones adyacentes a esta que no están por encima y están libres .
	 *
	 * @param location the location
	 * @return si esta posición pertenece a un mundo, devuelve sólo aquellas posiciones adyacentes válidas para ese mundo,  si no, devuelve todas las posiciones adyacentes
	 * @throws BadLocationException cuando la posición es de otro mundo
	 */
	private Set<Location> getFloodNeighborhood(Location location) throws BadLocationException {
		if (location.getWorld() !=null && location.getWorld() != this)
			throw new BadLocationException("Esta posición no es de este mundo");
		Set<Location> neighborhood = location.getNeighborhood();
		Iterator<Location> iter = neighborhood.iterator();
		while (iter.hasNext()) {
			Location loc = iter.next();
			try {
				if ((loc.getY() > location.getY()) || getBlockAt(loc)!=null)
					iter.remove();
			} catch (BadLocationException e) {
				throw new RuntimeException(e);
				// no sucederá
			}
		}
		return neighborhood;
	}

	
}
