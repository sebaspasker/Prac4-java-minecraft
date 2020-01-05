package model.entities;

import model.*;
// TODO: Auto-generated Javadoc
/**
 * The Class LivingEntity.
 */
public abstract class LivingEntity  {
	
	/** The location. */
	protected Location location;
	
	/** The health. */
	private double health;
	
	/** The Constant MAX_HEALTH. */
	public static final double MAX_HEALTH = 20;
	
	/**
	 * Instantiates a new living entity.
	 *
	 * @param loc the loc
	 * @param health the health
	 */
	public LivingEntity(Location loc, double health) {
		location = new Location(loc);
		setHealth(health);
	}
	
	/**
	 * Damage.
	 *
	 * @param amount the amount
	 */
	public void damage(double amount) {
		setHealth(getHealth()-amount);
	}
	
	/**
	 * Setter.
	 * @param health In case it's over MAX_HEALTH this value stays with MAX_HEALTH
	 */
	public void setHealth(double health) {
		if(health > MAX_HEALTH) {
			this.health = MAX_HEALTH;
		} else {
			this.health = health;
		}
	}

	/**
	 * Getter.
	 * @return health Double variable.
	 */
	public double getHealth() {
		return health;
	}
	
	/**
	 * Getter.
	 * @return location Location Object.
	 */
	public Location getLocation() {
		return new Location(location);
	}

	/**
	 * Function that check that actual player life is <= 0. 
	 * @return true/false in case player is dead or not.
	 */
	public boolean isDead() {
		if(health <= 0) {
			return true;
		} else {
			return false;
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
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(health);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((location == null) ? 0 : location.hashCode());
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
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LivingEntity other = (LivingEntity) obj;
		if (Double.doubleToLongBits(health) != Double.doubleToLongBits(other.health))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		return true;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "LivingEntity [location=" + location + ", health=" + health + "]";
	}

	/**
	 * Gets the symbol.
	 *
	 * @return the symbol
	 */
	public abstract char getSymbol();
}
