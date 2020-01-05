package model.persistence;

import model.ItemStack;

// TODO: Auto-generated Javadoc
/**
 * IInventory interface.
 *
 * @author sebastianpasker
 */
public interface IInventory {
	/**
	 * Getter. Returns pos object of inventory. If position
	 * don't exist or there is no ItemStack returns null.
	 * @param pos Position.
	 * @return ItemStack instance.
	 */
	public ItemStack getItem(int pos);
	
	/**
	 * Getter.
	 * @return Inventory size
	 */
	public int getSize();
	
	/**
	 * InHandItem from inventory. Return null in case there is 
	 * no ItemInHand.
	 * @return ItemStack instance.
	 */
	public ItemStack inHandItem();
}
