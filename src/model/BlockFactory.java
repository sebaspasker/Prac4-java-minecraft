package model;

import model.exceptions.WrongMaterialException;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating Block objects.
 */
public class BlockFactory {
	
	/**
	 * Creates a new Block object.
	 *
	 * @param type the type
	 * @return the block
	 * @throws WrongMaterialException the wrong material exception
	 */
	public static Block createBlock(Material type) throws WrongMaterialException {
		Block b;
		if(type.isLiquid()) {
			b = new LiquidBlock(type);
		} else if(!type.isLiquid()) {
			b = new SolidBlock(type);
		} else {
			throw new WrongMaterialException(type);
		}
		
		return b;
	}
	
	
}
