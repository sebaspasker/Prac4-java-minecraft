package model.exceptions;

// TODO: Auto-generated Javadoc
/**
 * Exception class.
 *
 * @author sebastianpasker
 */
public class EntityIsDeadException extends Exception{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public EntityIsDeadException() {
		super("La entidad esta muerta");
	}
}
