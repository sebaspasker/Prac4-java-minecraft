package model.entities;

import model.ItemStack;
import model.Location; 
import model.Material;

// TODO: Auto-generated Javadoc
/**
 * The Class Animal.
 */
public class Animal extends Creature {
	
	/** The symbol. */
	private static char symbol = 'L';
	
	/**
	 * Instantiates a new animal.
	 *
	 * @param loc the loc
	 * @param health the health
	 */
	public Animal(Location loc, double health) {
		super(loc, health);
	}
	
	/**
	 * Gets the drops.
	 *
	 * @return the drops
	 */
	public ItemStack getDrops() {
		ItemStack beef = null; 
		try {
			return new ItemStack(Material.BEEF, 1);
		} catch(Exception e) {
			System.err.println("ITEMSTACK CONSTRUCTOR ERROR");
		}
		
		return beef;
	}
	
	/**
	 * Gets the symbol.
	 *
	 * @return the symbol
	 */
	public char getSymbol() {
		char i = symbol;
		return i;
	}
	
	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Animal [location=" + getLocation() + ", health=" + getHealth() + "]";
	}
}
