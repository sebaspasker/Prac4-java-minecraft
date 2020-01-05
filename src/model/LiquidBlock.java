package model;

import model.exceptions.WrongMaterialException;

// TODO: Auto-generated Javadoc
/**
 * The Class LiquidBlock.
 */
public class LiquidBlock extends Block {
	
	/** The damage. */
	private double damage;
	
	/**
	 * Instantiates a new liquid block.
	 *
	 * @param type the type
	 * @throws WrongMaterialException the wrong material exception
	 */
	public LiquidBlock(Material type) throws WrongMaterialException {
		super(type);
		if(type.isLiquid() == false) {
			throw new WrongMaterialException(type);
		}
		
		damage = type.getValue();
	}
	
	/**
	 * Instantiates a new liquid block.
	 *
	 * @param l the l
	 * @throws WrongMaterialException the wrong material exception
	 */
	protected LiquidBlock(LiquidBlock l) throws WrongMaterialException {
		this(l.getType());
	}

	/**
	 * Gets the damage.
	 *
	 * @return the damage
	 */
	public double getDamage() {
		return damage;
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
		temp = Double.doubleToLongBits(damage);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	
	/**
	 * Clone.
	 *
	 * @return This LiquidBlock copy
	 */
	public Block clone() {
		LiquidBlock s = null;
		try {
			 s = new LiquidBlock(this);
		} catch (WrongMaterialException e) {
			e.printStackTrace();
		}
		
		return s;
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
		LiquidBlock other = (LiquidBlock) obj;
		if (Double.doubleToLongBits(damage) != Double.doubleToLongBits(other.damage))
			return false;
		return true;
	}
}
