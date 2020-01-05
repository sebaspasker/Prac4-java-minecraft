package model.persistence;

import model.Inventory;
import model.ItemStack;

// TODO: Auto-generated Javadoc
/**
 * InventoryAdapter. Implements IInventory. 
 * Utility Object to pass Inventory from world to MinetestSerializer.
 * @author sebastianpasker
 *
 */
public class InventoryAdapter implements IInventory {
	
	/**
	 * Inventory instance.
	 */
	private Inventory inventory;
	
	/**
	 * InventoryAdapter function.
	 * @param i Inventory instance.
	 */
	public InventoryAdapter(Inventory i) {
		inventory = i;
	}
	
	/**
	 * Getter.
	 * @param pos Position in inventory.
	 * @return ItemStack instance for pos position.
	 */
	@Override
	public ItemStack getItem(int pos) {
		if(inventory == null) {
			return null;
		}
		return inventory.getItem(pos);
	}

	/**
	 * Getter.
	 * @return Inventory size.
	 */
	@Override
	public int getSize() {
		return inventory.getSize();
	}	
	
	/**
	 * Getter. 
	 * @return inventory Hand Item. 
	 */
	@Override
	public ItemStack inHandItem() {
		
		return inventory.getItemInHand();
	}

}
