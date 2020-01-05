package model.score;

import model.exceptions.score.ScoreException;

// TODO: Auto-generated Javadoc
/**
 * Abstract Score class.
 * 
 * @author sebastianpasker
 *
 * @param <T> Generic Type
 */
public abstract class Score<T> implements Comparable<Score<T>> {
	
	/** Players Name. */
	private String PlayerName;
	
	/** Score double. */
	protected double score;
	
	/**
	 * Constructor. 
	 * @param name Players name
	 */
	public Score(String name) {
		this.PlayerName = name;
		score = 0.0;
	}

	/**
	 * Getter.
	 * @return the PlayerName.
	 */
	public String getName() {
		return PlayerName;
	}

	/**
	 * Getter.
	 * @return the score
	 */
	public double getScoring() {
		return score;
	}

	/**
	 * To String
	 * "<playername>:<score>" format.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return PlayerName + ":" + score;
	}
	
	/**
	 * Abstract score class.
	 *
	 * @param t the t
	 * @throws ScoreException the score exception
	 */
	public abstract void score(T t);
	
}
