package model;
import model.exceptions.WrongMaterialException;

// TODO: Auto-generated Javadoc
/**
 * The Class Block.
 */
public abstract class Block {
	
	/** The type. */
	private Material type;
	
	/**
	 * Instantiates a new block.
	 *
	 * @param type Material type
	 * @throws WrongMaterialException the wrong material exception
	 */
	public Block(Material type) throws WrongMaterialException {
		if(type.isBlock()) {
			this.type = type;
		} else {
			throw new WrongMaterialException(type);
		}
	}
	
	/**
	 * Instantiates a new block.
	 *
	 * @param b the b
	 * @throws WrongMaterialException the wrong material exception
	 */
	protected Block(Block b) throws WrongMaterialException {
		type = b.getType();
	}
	
	
	
	/**
	 * Gets the type.
	 *
	 * @return type value
	 */
	public Material getType() {
		return type;
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
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Block other = (Block) obj;
		if (type != other.type)
			return false;
		return true;
	}


	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	/**
	 * @return [type] format
	 */
	public String toString() {
		return "[" + type + "]";
	}
	
	/**
	 * Clone.
	 *
	 * @return block value for each son.
	 */
	@Override
	public abstract Block clone(); 
}
