package model.exceptions;

// TODO: Auto-generated Javadoc
/**
 * Exception.
 * @author sebastianpasker
 *
 */
public class BadLocationException extends Exception {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The message.
	 *
	 */
	String message;
	/**
	 * Constructor Exception.
	 * @param message to this.message
	 */
	public BadLocationException(String message) {
		super(message);
		this.message = message;
	}
}
