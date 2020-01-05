package model.score;

import model.Block;

// TODO: Auto-generated Javadoc
/**
 * MiningScore class extends Score<Block>.
 *
 * @author sebastianpasker
 */
public class MiningScore extends Score<Block>{

	/**
	 * Constructor, calls super Constructor.
	 *
	 * @param playerName the player name
	 */
	public MiningScore(String playerName) {
		super(playerName);
	}
	
	/**
	 * CompareTo function.
	 *
	 * @param score2 Score<Block> type
	 * @return -1/0/1
	 */
	@Override
	public int compareTo(Score<Block> score2) {
		int compareNumber = (int)((this.score-score2.score)*(-1)); // Default case lower
		return compareNumber;
	}

	/**
	 * Score function.
	 *
	 * @param t Block value
	 */
	@Override
	public void score(Block t) {
		this.score += t.getType().getValue();
	}
	
}
