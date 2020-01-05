package model.exceptions;

// TODO: Auto-generated Javadoc
/**
 * Exception.
 *
 * @author sebastianpasker
 */
public class BadInventoryPositionException extends Exception {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The pos.
	 *
	 */
	int pos;
	
	/**
	 * Exception Constructor.
	 *
	 * @param pos super param integer.
	 */
	public BadInventoryPositionException(int pos) {
		super("Posición incorrecta" + pos);
		this.pos = pos;
	}
}
