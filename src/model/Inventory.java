package model;

import java.util.ArrayList;
import java.util.List;

import model.exceptions.BadInventoryPositionException;

// TODO: Auto-generated Javadoc
/**
 * Inventory Class.
 *
 * @author sebastianpasker
 */
public class Inventory {
	
	/**
	 * The in hand.
	 *
	 */
	private ItemStack inHand;
	
	/**
	 * The items.
	 *
	 */
	private List<ItemStack> items;
	
	/**
	 * Constructor
	 * Start items.
	 */
	public Inventory() {
		items = new ArrayList<ItemStack>();
	}
	
	/**
	 * Instantiates a new inventory.
	 *
	 * @param passedInv the passed inv
	 */
	public Inventory(Inventory passedInv) {
		if(passedInv.items == null) {
			items = null;
		} else {
			items = new ArrayList<ItemStack>(passedInv.items);
		}
		
		if(passedInv.inHand == null) {
			inHand = null;
		} else {
			inHand = new ItemStack(passedInv.inHand);
		}
	}
	
	/**
	 * Add passed item in function in list.
	 * @param items It's item we add to ItemStack List.
	 * @return position of items in list.
	 */
	public int addItem(ItemStack items) {
		this.items.add(items);
		return items.getAmount();
	}
	
	/**
	 * Clear function. Delete all values of items and inHand.
	 */
	public void clear() {
		items.clear();
		inHand = null;
	}
	
	/**
	 * Clear function. Delete position we pass as param
	 * @param slot Position in list we wan't to delete.
	 * @throws BadInventoryPositionException Case position don't exist in list
	 */
	public void clear(int slot) throws BadInventoryPositionException {
		if(slot < 0 || slot >= items.size()) {
			throw new BadInventoryPositionException(slot);
		}
		items.remove(slot);
	}

	/**
	 *  Function to know first object in list items with
	 *  Material type.
	 *
	 * @param type Material we want to find
	 * @return position Return -1 in case object not found.
	 */
	public int first(Material type) {
		int typePosition = -1;
		for(int i=0; i<items.size(); i++) {
			if(items.get(i).getType().equals(type)) {
				typePosition = i;
				break;
			}
		}
		
		return typePosition;
	}
	
	/**
	 * Getter.
	 * @return InHand object
	 */
	public ItemStack getItemInHand() {
		if(inHand == null) {
			return null;
		}
		return inHand;
	}
	
	/**
	 * Getter.
	 * @return size of items list regardless inHand.
	 */
	public int getSize() {
		if(inHand != null && items.size() != 0) {
			for(ItemStack item: items) {
				if(inHand == item) {
					return items.size()-1;
				}
			}
		}
		
		return items.size();
	}

	/**
	 * Setter.
	 * @param items Put items object in inHand. Can be null.
	 */
	public void setItemInHand(ItemStack items) {
		this.inHand = items;
	}

	/**
	 * Getter.
	 * @param slot Position we want to get of items list.
	 * @return ItemStack object in position we wan't to get. Can return null.
	 */
	public ItemStack getItem(int slot) {
		if(slot < 0 || slot >= items.size()) {
			return null;
		} else {
			return items.get(slot);
		}
	}

	/**
	 * Setter. Remove object that was in pos position.
	 * @param pos Position we want to set items object.
	 * @param items ItemStack object.
	 * @throws BadInventoryPositionException Exception
	 */
	public void setItem(int pos, ItemStack items) throws BadInventoryPositionException {
		if(pos >= this.items.size() || pos < 0) {
			throw new BadInventoryPositionException(pos);
		} else {
			this.items.remove(pos);
			this.items.add(pos, items);
		}
	}
	
	/**
	 * ToString Function.
	 * @return [(inHand=TYPE, AMOUNT), [(TYPE1, AMOUNT1), (TYPE2, AMOUNT2)]) format.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(inHand=");
		if(inHand != null) {
			sb.append("(" + inHand.getType() + "," + inHand.getAmount() + ")");
		} else {
			sb.append("null");
		}
		
		
		sb.append(",[");
		
		
		for(int i=0; i<items.size(); i++) {
			sb.append("(" + items.get(i).getType() + "," + items.get(i).getAmount() + ")");
			if(i != items.size()-1) {
				sb.append(", ");
			}
		}
	
		sb.append("]");
		sb.append(")");
		
		return sb.toString();
	}

	/**
	 * Hash Code Function.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((inHand == null) ? 0 : inHand.hashCode());
		result = prime * result + ((items == null) ? 0 : items.hashCode());
		return result;
	}

	/**
	 * Equals Function.
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
		Inventory other = (Inventory) obj;
		if (inHand == null) {
			if (other.inHand != null)
				return false;
		} else if (!inHand.equals(other.inHand))
			return false;
		if (items == null) {
			if (other.items != null)
				return false;
		} else if (!items.equals(other.items))
			return false;
		return true;
	}
	
}


