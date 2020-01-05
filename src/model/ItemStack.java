package model;

import model.exceptions.StackSizeException;

// TODO: Auto-generated Javadoc
/**
 * ItemStack class.
 *
 * @author sebastianpasker
 */
public class ItemStack {
	
	/**
	 * The type.
	 *
	 */
	private Material type;
	
	/**
	 * The amount.
	 *
	 */
	private int amount;
	
	/**
	 * The Constant MAX_STACK_SIZE.
	 *
	 */
	public static final int MAX_STACK_SIZE = 64;

	/**
	 * Getter.
	 *
	 * @return type of class.
	 */
	public Material getType() {
		return type;
	}

	/**
	 * Constructor.
	 * @param type It's Material type for this.type
	 * @param amount It's value for this.amount, maxim 64
	 * @throws StackSizeException Exception we launch when it's not in range.
	 */
	public ItemStack(Material type, int amount) throws StackSizeException{
		if(amount != 1 && (type.isWeapon() || type.isTool())) {
			throw new StackSizeException();
		} else if(amount < 1 || amount > MAX_STACK_SIZE) {
			throw new StackSizeException();
		} else {
			this.type = type;
			this.setAmount(amount); 

		}
	}
	
	/**
	 * Copy constructor.
	 * @param item Item Stack object with different values.
	 */
	public ItemStack(ItemStack item) {
		this.amount = item.amount;
		this.type = item.type;
	}

	/**
	 * Getter.
	 * @return MAX_STACK_SIZE Const 64 value.
	 */
	public static int getMaxStackSize() {
		return MAX_STACK_SIZE;
	}

	/**
	 * Getter. 
	 * @return amount Quantity of material variable.
	 */
	public int getAmount() {
		return amount;
	}
	
	/**
	 * Setter.
	 * @param amount Quantity of material variable.
	 * @throws StackSizeException Exception we launch when it's not in range.
	 */
	public void setAmount(int amount) throws StackSizeException {
		if(amount < 1 || amount > MAX_STACK_SIZE) {
			throw new StackSizeException();
		} else {
			if((type.isWeapon()|| type.isTool()) && amount != 1) {
				throw new StackSizeException();
			}
			
			this.amount = amount;
		}
	}
	
	/**
	 * HashCode.
	 *
	 * @return hash code created
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + amount;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	/**
	 * Equality function.
	 *
	 * @param obj Object Same object comprobation.
	 * @return true/false in each case
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemStack other = (ItemStack) obj;
		if (amount != other.amount)
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	/**
	 * To string function.
	 *
	 * @return ItemStack format passed to string (type, amount)
	 */
	public String toString() { 
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append(type);
		sb.append(",");
		sb.append(amount);
		sb.append(")");
		return sb.toString();
	}
}
