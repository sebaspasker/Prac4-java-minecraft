package model.entities;

import java.util.Set;

import model.Inventory;
import model.ItemStack;
import model.Location;
import model.Material;
import model.World;
import model.exceptions.BadInventoryPositionException;
import model.exceptions.BadLocationException;
import model.exceptions.EntityIsDeadException;
 
// TODO: Auto-generated Javadoc
/**
 * The Class Player.
 */
public class Player extends LivingEntity {
	
	/**
	 * The name.
	 *
	 */
	private String name;
	
	/**
	 * The food level.
	 *
	 */
	private double foodLevel;
	
	/**
	 * The orientation.
	 *
	 */
	private Location orientation;
	
	/**
	 * The Constant MAX_FOODLEVEL.
	 *
	 */
	static public final double MAX_FOODLEVEL = 20;
	
	
	/**
	 * The inventory.
	 *
	 */
	private Inventory inventory;
	
	/** The symbol. */
	private static char symbol = 'P';
	
	/**
	 * Constructor.
	 * @param name to save in player variable this.name.
	 * @param world to save in location of player.
	 */
	public Player(String name, World world) {
		super(new Location(world,0,0,0), MAX_HEALTH);
		Location highLocation;
		try {
			highLocation = world.getHighestLocationAt(new Location(world, 0,0,0));
			location = new Location(world, highLocation.getX(), highLocation.getY()+1, highLocation.getZ()); // Save upper value than highest location value.
			orientation = new Location(world, 0, 0, 1); // Relative position
		} catch(Exception e) {
			System.out.println("Exception BadLocationException: in location " + e.getMessage());
		}
		this.name = name;
		foodLevel = MAX_FOODLEVEL;
		inventory = new Inventory();
		try {
			inventory.addItem(new ItemStack(Material.WOOD_SWORD, 1));
			this.selectItem(0);
		} catch(Exception e) {
			System.out.println("Exception StackSizeException" + e.getMessage());
		}
	}

	/**
	 * Gets the symbol.
	 *
	 * @return the symbol
	 */
	@Override
	public char getSymbol() {
		return symbol;
	}

	/**
	 * Gets the orientation.
	 *
	 * @return the orientation
	 */
	public Location getOrientation() {
		return new Location(location.getWorld(), location.getX()+ orientation.getX(), location.getY()+orientation.getY(), location.getZ()+orientation.getZ());
	}

	/**
	 * Getter.
	 * @return name String variable.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Getter.
	 * @return foodLevel Double variable.
	 */
	public double getFoodLevel() {
		return foodLevel;
	}
	
	/**
	 * Getter.
	 * @return Inventory copy
	 */
	public Inventory getInventory() {
		return new Inventory(this.inventory);
	}
	
	/**
	 * Setter.
	 * @param foodLevel In case it's over MAX_FOODLEVEL this value stays with MAX_FOODLEVEL.
	 */
	public void setFoodLevel(double foodLevel) {
		if(foodLevel > MAX_FOODLEVEL) {
			this.foodLevel = MAX_FOODLEVEL;
		} else {
			this.foodLevel = foodLevel;
		}
	}
	
	/**
	 * Move function. Check position passed is adjacent to actual position. And in case position is with no object,
	 * it moves player to passed position. In case player is dead or position is not adjacent it launch a exception.
	 * @param dx Three dimensional x value.
	 * @param dy Three dimensional y value.
	 * @param dz Three dimensional z value.
	 * @return Actual location
	 * @throws EntityIsDeadException In case player is dead.
	 * @throws BadLocationException  In case passed position is not adjacent.
	 */
	public Location move(int dx, int dy, int dz) throws EntityIsDeadException, BadLocationException {
		Location l = new Location(location.getWorld(), location.getX()+dx, location.getY()+dy, location.getZ()+dz);
		if(this.isDead()) {
			throw new EntityIsDeadException(); // Case player is dead.
		} 
		
		Set<Location> locationArray = location.getNeighborhood();
		boolean isAdjacent = false;
		
		for(Location locationPosition:locationArray) { // Search adjacent position
			if(l.equals(locationPosition)) {
				isAdjacent = true;
			}
		}
		 
		if(isAdjacent == false || Location.check(l) == false || (l.isFree() == false)) {
			throw new BadLocationException(location.getWorld().getName()); // Case not adjacent
		}
		
		if((location.getWorld()).isFree(l) == true && location.equals(l) == false) {
			location = l;
			this.decreaseFoodLevel(0.05);
			return location;
		} else {
			return location;
		}
	}
	
	/**
	 * Orientate.
	 *
	 * @param dx the dx
	 * @param dy the dy
	 * @param dz the dz
	 * @return the location
	 * @throws BadLocationException the bad location exception
	 * @throws EntityIsDeadException the entity is dead exception
	 */
	public Location orientate(int dx, int dy, int dz) throws BadLocationException, EntityIsDeadException {
		Location o;
		if(!this.isDead()) {
			if((dx!=0 || dy!=0 || dz != 0) && (dx > -2 && dx < 2 && dy > -2 && dy < 2 && dz > -2 && dz < 2)) {
				o = new Location(orientation.getWorld(), dx, dy, dz); // New orientation value
				orientation = new Location(o);
			} else {
				throw new BadLocationException(name);
			}
		} else {
			throw new EntityIsDeadException();
		} 
		// return_loc --> Orientation in base of player position
		return getOrientation();
	}
	
	/**
	 * Use item in hand. It's a function that in case ItemStack in hand is edible it increase Food Level(Material value*times) and if
	 * material is not edible it decrease food level(0.01*times). 
	 *
	 * @param times Times value
	 * @return the item stack
	 * @throws IllegalArgumentException Case times is <= 0.
	 * @throws EntityIsDeadException Case player is dead.
	 */
	public ItemStack useItemInHand(int times) throws IllegalArgumentException, EntityIsDeadException {
		if(times <= 0) {
			throw new IllegalArgumentException();
		}
		
		if(this.isDead()) {
			throw new EntityIsDeadException();
		}
		
		if(inventory.getItemInHand() != null) {
			if(inventory.getItemInHand().getType().isEdible()) {
				if(inventory.getItemInHand().getAmount() > times) { // Case itemInHand amount is bigger than times
					double valueItemInHand = inventory.getItemInHand().getType().getValue(); // Value from itemInHand
					this.increaseFoodLevel(valueItemInHand*times); 
					try {
						inventory.getItemInHand().setAmount(inventory.getItemInHand().getAmount()-times); // Rest amount-times
					} catch(Exception e) {
						System.out.println(e.getMessage());
					}
					
				} else { 											// Case times is bigger than itemInHand amount
					double valueItemInHand = inventory.getItemInHand().getType().getValue();
					double amountItemInHand = inventory.getItemInHand().getAmount();
					increaseFoodLevel(valueItemInHand*amountItemInHand);  // Value * amount
					inventory.setItemInHand(null);
					
				}
			} else {
				this.decreaseFoodLevel(0.1*times); // Case item not null and not food it decrease food level or health.
				
			}
		}
		
		if(inventory.getItemInHand() == null) {
			return null;
		} else {
			return new ItemStack(inventory.getItemInHand());
		}
	}
	
	/**
	 * Put item in position of inventory to itemInHand. In case no object in Hand it remove item from inventory list, in case there is a 
	 * object in hand it change it in inventory list.
	 * @param pos Position we wan't to pickup item for pass it to itemInHand.
	 * @throws BadInventoryPositionException It's from setItem. It throw in case position don't exist in list.
	 */
	public void selectItem(int pos) throws BadInventoryPositionException {
		if(inventory.getItemInHand() != null) { // Case there is a item in Hand.
			ItemStack oldItemInHand = inventory.getItemInHand(); // Save item in hand
			ItemStack newItemInHand = inventory.getItem(pos); // Save item in list
			inventory.setItemInHand(newItemInHand); // Set new item in Hand
			inventory.setItem(pos, oldItemInHand); // Set old item in list.
			
		} else {
			ItemStack newItemInHand = inventory.getItem(pos);
			inventory.setItemInHand(newItemInHand);
			inventory.clear(pos);
		}
	}
	
	/**
	 * Add item to inventory.
	 *
	 * @param items Item we wan't to add.
	 */
	public void addItemsToInventory(ItemStack items) {
		inventory.addItem(items);
	}
	
	/**
	 * Getter.
	 * @return inventory size.
	 */
	public int getInventorySize() {
		return inventory.getSize();
	}
	
	/**
	 * Increaser of food/health level. When food is equal to maximum value it increase health.
	 * @param increase Value we want to add to food/health .
	 */
	private void increaseFoodLevel(double increase) {
		if(getFoodLevel()+increase > MAX_FOODLEVEL) {
			double extra = getFoodLevel()+increase-MAX_FOODLEVEL;
			setFoodLevel(MAX_FOODLEVEL);
			setHealth(getHealth() + extra);
		} else {
			setFoodLevel(getFoodLevel()+increase);
		}
	}
	
	/**
	 * Decrement of food/health level. When food is equal to 0 it decrement health.
	 * @param decrement Value we want to decrease to food/health.
	 */
	private void decreaseFoodLevel(double decrement) {
		if((getFoodLevel()-decrement) < 0.0) {
			double extra = getFoodLevel()-decrement;
			extra *= -1;
			setFoodLevel(0);
			setHealth(getHealth() - extra);
		} else {
			setFoodLevel(getFoodLevel()-decrement);
		}
	}
	

	/**
	 * Hash code.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(foodLevel);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((inventory == null) ? 0 : inventory.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((orientation == null) ? 0 : orientation.hashCode());
		return result;
	}

	/**
	 * Equals.
	 *
	 * @param obj the obj
	 * @return true, if successful
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (Double.doubleToLongBits(foodLevel) != Double.doubleToLongBits(other.foodLevel))
			return false;
		if (inventory == null) {
			if (other.inventory != null)
				return false;
		} else if (!inventory.equals(other.inventory))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (orientation == null) {
			if (other.orientation != null)
				return false;
		} else if (!orientation.equals(other.orientation))
			return false;
		return true;
	}

	/**
	 * To String function.
	 * @return  Name=name \n location \n Health=health \n Food level=foodLevel \n Inventory=inventory \n format.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Name=" + name +"\n");
		sb.append(location.toString() + "\n");
		sb.append("Orientation=" + orientation.toString()+ "\n");
		sb.append("Health=" + getHealth() + "\n"); 
		sb.append("Food level=" + getFoodLevel() + "\n");
		sb.append("Inventory=" + inventory.toString() + "\n");
		return sb.toString();
	}
}
