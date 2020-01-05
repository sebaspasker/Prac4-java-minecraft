package model.entities;

import model.*;

// TODO: Auto-generated Javadoc
/**
 * The Class Monster.
 */
public class Monster extends Creature {
	
	/** The symbol. */
	private static char symbol = 'M';
	
	/**
	 * Instantiates a new monster.
	 *
	 * @param loc the loc
	 * @param health the health
	 */
	public Monster(Location loc, double health) {
		super(loc, health);
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
		return "Monster [location=" + getLocation() + ", health=" + getHealth() + "]";
	}
}
