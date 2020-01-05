package model;

import model.score.*;

import java.util.ArrayList;  
import java.util.List;
import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;

import model.entities.Animal;
import model.entities.Creature;
import model.entities.Player;
import model.entities.Monster;
import model.exceptions.BadInventoryPositionException;
import model.exceptions.BadLocationException;
import model.exceptions.EntityIsDeadException;
import model.exceptions.StackSizeException;
import model.exceptions.WrongMaterialException;

// TODO: Auto-generated Javadoc
// TODO Actualize pick up item Score CollectedItemsScore
// TODO Actualize block mine Score MiningScore
// TODO Actualize player move Score PlayerMovementScore

/**
 * BlockWorld class.
 *
 * @author sebastianpasker
 */
public class BlockWorld {
	
	/**
	 * The instance.
	 *
	 */
	private static BlockWorld instance;
	
	/**
	 * The world.
	 *
	 */
	private World world;
	
	/** itemScore CollectedItemsScore instance. */
	private CollectedItemsScore itemScore;
	
	/** miningScore MiningScore instance. */
	private MiningScore miningScore;
	
	/** movementScore PlayerMovementScore instance. */
	private PlayerMovementScore movementScore;
	
	/**
	 * Getter.
	 * @return instance In case null create new one
	 */
	public static BlockWorld getInstance() {
		if(instance == null) {
			instance = new BlockWorld();
		}
		return instance;
	}
	 
	/**
	 * Constructor.
	 * Set world and scores at null
	 */
	private BlockWorld() {
		world = null;
		this.miningScore = null;
		this.itemScore = null;
		this.movementScore = null;
	}
	
	/**
	 * Start world variable.
	 * @param seed Id with special terrain constructor 
	 * @param size World variable for equality 
	 * @param name World name
	 * @return new world
	 */
	@Deprecated
	public World createWorld(long seed, int size, String name) {
		world = new World(seed, size, name);
		return world;
	}
	
	/**
	 * Start world variable.
	 * @param seed Id with special terrain constructor 
	 * @param size World variable for equality 
	 * @param name World name
	 * @param playerName name of player
	 * @return new world
	 */
	public World createWorld(long seed, int size, String name, String playerName) {
		world = new World(seed, size, name, playerName);
		miningScore = new MiningScore(playerName);
		itemScore = new CollectedItemsScore(playerName);
		movementScore = new PlayerMovementScore(playerName);
		return world;
	}
	
	/**
	 * Return string with all player information.
	 *
	 * @param p Player you want information
	 * @return Sb.toString Player information
	 */
	public String showPlayerInfo(Player p) {
		StringBuilder sb = new StringBuilder();
		sb.append(world.getPlayer().toString());
		try {
			sb.append(world.getNeighbourhoodString(world.getPlayer().getLocation()));
		} catch(Exception e) {
			System.out.println("Exception BadLocationException " + e.getMessage());
		}
		sb.append("Scores: [items: " + (double)this.itemScore.getScoring());
		sb.append(", blocks: " + (double)this.miningScore.getScoring());
		sb.append(", movements: " + (double)this.movementScore.getScoring()+"]\n");
		return sb.toString();
		
	}
	
	/**
	 * Move passed player to passed Location.
	 * @param p Player
	 * @param x Three-dimensional value
	 * @param y Three-dimensional value
	 * @param z Three-dimensional value
	 * @throws EntityIsDeadException Exception
	 * @throws BadLocationException Exception
	 */
	public void movePlayer(Player p, int x, int y, int z) throws EntityIsDeadException, BadLocationException {
		Location playerLocation = p.move(x, y, z);
		movementScore.score(playerLocation);
		if(world.getItemsAt(playerLocation) != null) {
			ItemStack item = world.getItemsAt(playerLocation);
			world.removeItemsAt(playerLocation);
			p.addItemsToInventory(item);
			itemScore.score(item);
		}
		
		if(p.getLocation().getWorld().getBlockAt(p.getLocation()) instanceof LiquidBlock) {
			LiquidBlock l = (LiquidBlock)p.getLocation().getWorld().getBlockAt(p.getLocation());
			if(l.getType() == Material.LAVA) {
				p.damage(l.getType().getValue());
			}
		}
	}
	
	/**
	 * It's for player select a item to pass to hand.
	 *
	 * @param p Passed player
	 * @param pos Position
	 * @throws BadInventoryPositionException Exception
	 */
	public void selectItem(Player p, int pos) throws BadInventoryPositionException {
		p.selectItem(pos);
	}
	
	/**
	 * Returns object correspondent damage. In case it's a block it's 0.1 
	 * else if it's a weapon or a tool it's Material value.
	 * @param i Passed ItemStack
	 * @return damage
	 */
	private double damageCalculator(ItemStack i) {
		double d = 0;
		if(i.getType().isBlock()) {
			d = 0.1;
		} else if(i.getType().isWeapon() || i.getType().isTool()) {
			d = i.getType().getValue();
		}
		
		return d;
	}
	
	/**
	 * Use item at block. In case damage is superior to Material value in world
	 * it destroys block and left a same Material type ItemStack
	 * @param orientation Player orientation 
	 * @param damage object in players hand
	 * @throws BadLocationException Launched by getBlockAt, destroyBlockAt and addItems 
	 * @throws StackSizeException Launched by ItemStack
	 */
	private void useItemAtBlock(Location orientation, double damage) throws BadLocationException, StackSizeException{
		Block b = world.getBlockAt(orientation);
		if (!b.getType().isLiquid() && b.getType().getValue() <= damage) { 
			world.destroyBlockAt(orientation);
			world.addItems(orientation, new ItemStack(b.getType(), 1));
			miningScore.score(b);
		}
		
	}
	
	/**
	 * Attack a creature. In case it's an animal and dies it returns a beef item, if it's a 
	 * monster and it don't dies they attack back (times*0.5).
	 * @param orientation Player orientation.
	 * @param damage object in player hand.
	 * @param times it's used in case monster attack back.
	 * @param p Player.
	 * @throws BadLocationException launched by getCreatureAt and killCreature.
	 */
	private void useItemAtCreature(Location orientation, double damage, int times, Player p) throws BadLocationException {
		Creature c = world.getCreatureAt(orientation);
		c.damage(damage);
		if(c.isDead()) {
			world.killCreature(orientation);
			try {
				if(c instanceof Animal) { // Case entity is dead and an animal
					world.addItems(orientation, new ItemStack(Material.BEEF, 1));
				}
			} catch (Exception e) {
				System.err.println("Already object at position");
			}
		} else {
			if(c instanceof Monster) { // Case entity is not dead and is a Monster
				p.damage(times*0.5);
			}
		}
	
	}
	
	/**
	 * Add new item at world in case itemInHand is a Block and position in
	 * orientation is empty. 
	 * @param orientation Player location orientation.
	 * @param i Item in hand. Must be a SolidBlock.
	 * @throws BadLocationException launched by addBlock.
	 * @throws WrongMaterialException launched by createBlock. 
	 */
	private void addItemBlock(Location orientation, ItemStack i) throws BadLocationException, WrongMaterialException {
		Block blockAddition = BlockFactory.createBlock(i.getType());
		world.addBlock(orientation, blockAddition);
	}

	/**
	 * Firstly, use item in hand x times. In case it's an apple add food-level/health
	 * else it rest it. Secondly, in case it's an weapon, block or tool it can
	 * damage a creature-block or in case it's a solid block and place is empty it add block
	 * to world.
	 * @param p Passed player.
	 * @param times Times we use item in hand.
	 * @throws IllegalArgumentException launched by useItemInHand and in case it's launched 
	 * by useItemAtBlock, UseItemAtCreature or addItemBlock.
	 * @throws EntityIsDeadException launched by useItemInHand.
	 */
	public void useItem(Player p, int times) throws IllegalArgumentException, EntityIsDeadException {
		ItemStack i = p.useItemInHand(times);

		if(i != null && Location.check(p.getOrientation()) && !i.getType().isEdible() && !p.isDead()) {
		// 	Item weapon - block - tool
			double damage = damageCalculator(i) * times; // Block --> 0.1, else Value
			Location orientation = p.getOrientation();
			try {
				if (world.getBlockAt(orientation) != null) { // Case there is a block at position
					useItemAtBlock(orientation, damage);
				} else if (world.getCreatureAt(orientation) != null && !p.isDead())  { // Case there is a creature at position
					useItemAtCreature(orientation, damage, times, p);
				} else if (world.getCreatureAt(orientation) == null && world.getBlockAt(orientation) == null && !p.isDead()) {
					if(i.getType().isBlock() && !i.getType().isLiquid()) { // Case no object in position and item in hand is a solibBlock
						addItemBlock(orientation, i);
					} 
				}
				
			} catch (WrongMaterialException e) {
			} catch (Exception e) {
				throw new IllegalArgumentException();
			}
		
		}
		
	}
	
	/**
	 * Orientate passed player to xyz adjacent position.
	 * @param p Player
	 * @param x Three dimensional value
	 * @param y Three dimensional value
	 * @param z Three dimensional value
	 * @throws BadLocationException launched by orientate
	 * @throws EntityIsDeadException launched by orientate
	 */
	public void orientatePlayer(Player p, int x, int y, int z) throws BadLocationException, EntityIsDeadException {
		p.orientate(x, y, z);
	}
	
	/**
	 * Testing of correct string and int arguments.
	 *
	 * @param arg String argument // possibilities --> move || orientate || useItem || show || selectItem
	 * @param intArgs // int args list (size 0-3)
	 * @param p the p
	 * @return exe Boolean value
	 * @throws Exception can be EntityIsDeadException, BadLocationException, IllegalArgumentException, BadInventoryException
	 */
	private boolean exeCommand(String arg, List<Integer> intArgs, Player p) throws Exception {
		boolean exe = false;
		
		if(intArgs.size() >= 0 && intArgs.size() <= 3) {
			if(arg.contentEquals("move") && intArgs.size() == 3) {
				this.movePlayer(p, intArgs.get(0), intArgs.get(1), intArgs.get(2));
				exe = true;
			} else if(arg.contentEquals("orientate") && intArgs.size() == 3) {
				this.orientatePlayer(p, intArgs.get(0), intArgs.get(1), intArgs.get(2));
				exe = true;
			} else if(arg.contentEquals("useItem") && intArgs.size() == 1) {
				this.useItem(p, intArgs.get(0));
				exe =true;
			} else if(arg.contentEquals("show") && intArgs.size() == 0) {
				exe = true;
			} else if(arg.contentEquals("selectItem") && intArgs.size() == 1) {
				this.selectItem(p, intArgs.get(0));
				exe = true;
			}
		}
		
		return exe;
	}
	
	/**
	 * Interpret Scanner arguments.
	 * Possibilities:
	 * move dx dy dz
	 * orientation dx dy dz
	 * useItem times
	 * show
	 * selectItem pos 
	 * @param sc Scanner passed object
	 */ // TODO El player no se inicializa correctamente porque al empezar el objeto inHand = null
	private void play(Scanner sc) {
		String line = sc.nextLine();
		String stringArray[] = line.split(" ");
		String s = new String();
		for(int i=3; i<stringArray.length; i++) {
			if(stringArray.length != i+1) {
				s = new String(s + stringArray[i] + " ");
			} else {
				s = new String(s + stringArray[i]);
			}
		}
		
		createWorld(Long.parseLong(stringArray[0]), Integer.parseInt(stringArray[1]), s, stringArray[2]);
		List<Integer> argsList = new ArrayList<Integer>();
		boolean correctValue = false;
		Player p = world.getPlayer();
		while(sc.hasNextLine()) {
			line = new String(sc.nextLine());
			stringArray = line.split(" ");
			for(int i=1;i<stringArray.length; i++) {
				argsList.add(Integer.parseInt(stringArray[i]));
			}
			try {
				correctValue = exeCommand(stringArray[0], argsList, p);
			} catch (Exception e) {
				System.err.println(e.getMessage());
				correctValue = true;
			}
			 
			if(!correctValue) {
				System.err.println("Error, el formato no es correcto");
			} else if(correctValue && stringArray[0].contentEquals("show")) {
				System.out.println(this.showPlayerInfo(p));
			}
			argsList.clear();
		}
	}
	
	/**
	 * File check and execute command input.
	 * @param path file.
	 * @throws FileNotFoundException in case can't find file.
	 */
	public void playFile(String path) throws FileNotFoundException {
		
		File f = new File(path);
		
		Scanner sc = new Scanner(f);
		this.play(sc);
		
		
	}
	
	/**
	 * Plays all command by console.
	 */
	public void playFromConsole() {
		Scanner input = new Scanner(System.in);
		
		while(input.hasNext()) {
			input.nextLine();
		}
		
		input.close();
		
		play(input);
	}
	
	/**
	 * Getter.
	 *
	 * @return CollectedItemsScore instance
	 */
	public CollectedItemsScore getItemsScore() {
		return itemScore;
	}

	/**
	 * Getter.
	 *
	 * @return MiningScore instance
	 */
	public MiningScore getMiningScore() {
		return miningScore;
	}

	/**
	 * Getter.
	 *
	 * @return PlayerMovementScore instance
	 */
	public PlayerMovementScore getMovementScore() {
		return movementScore;
	}
	
	
}




