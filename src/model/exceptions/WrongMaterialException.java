package model.exceptions;
import model.Material;

// TODO: Auto-generated Javadoc
/**
 * Exception class.
 * @author sebastianpasker
 *
 */
public class WrongMaterialException extends Exception {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The m.
	 *
	 */
	Material m;
	
	/**
	 * Constructor.
	 *
	 * @param m Material type to this pass and super
	 */
	public WrongMaterialException(Material m) {
		super("Error en el material" + m.toString());
		this.m = m;
	}
}
