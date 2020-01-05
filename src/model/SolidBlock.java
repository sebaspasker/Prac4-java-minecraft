package model;

import model.exceptions.StackSizeException;
import model.exceptions.WrongMaterialException;

// TODO: Auto-generated Javadoc
/**
 * The Class SolidBlock.
 */
public class SolidBlock extends Block {
	
	/** The drops. */
	private ItemStack drops;
	
	/**
	 * Instantiates a new solid block.
	 *
	 * @param type Material type
	 * @throws WrongMaterialException the wrong material exception
	 */
	public SolidBlock(Material type) throws WrongMaterialException {
		super(type);
		if(type.isLiquid()) {
			throw new WrongMaterialException(type);
		}
  	}

	/**
	 * Instantiates a new solid block.
	 *
	 * @param s SolidBlock Class
	 * @throws WrongMaterialException the wrong material exception
	 */
	protected SolidBlock(SolidBlock s) throws WrongMaterialException {
		this(s.getType());
		try {
			setDrops(s.getDrops().getType(), s.getDrops().getAmount());
		} catch (Exception e) {
		}
	}
	
	/**
	 * Breaks.
	 *
	 * @param damage the damage
	 * @return true, if material type <= damage
	 */
	public boolean breaks(double damage) {
		double c = getType().getValue();
		if(damage >= c) {
			return true;
		} else {
			return false;
		}
		 
	}
	
	/**
	 * Clone.
	 *
	 * @return This SolidBlock constructor
	 */
	public Block clone() {
		SolidBlock s = null;
		try {
			s = new SolidBlock(this.getType());
			s.setDrops(getDrops().getType(), getDrops().getAmount());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return s;
		
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
		result = prime * result + ((drops == null) ? 0 : drops.hashCode());
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
		SolidBlock other = (SolidBlock) obj;
		if (drops == null) {
			if (other.drops != null)
				return false;
		} else if (!drops.equals(other.drops))
			return false;
		return true;
	}

	/**
	 * Getter.
	 *
	 * @return return drops ItemStack copy.
	 */
	public ItemStack getDrops() {
		return drops;
	}
	
	/**
	 * Setter.
	 * @param type Material class to add to ItemStack object drops
	 * @param amount Amount integer
	 * @throws StackSizeException Exception
	 */
	public void setDrops(Material type, int amount) throws StackSizeException {
		if(this.getType() != Material.CHEST && amount == 1 ) {
			drops = new ItemStack(type, amount);
		} else if(this.getType() == Material.CHEST && amount > 0){
			drops = new ItemStack(type, amount);
		} else {
			throw new StackSizeException();
		}
	}
	
	
}
