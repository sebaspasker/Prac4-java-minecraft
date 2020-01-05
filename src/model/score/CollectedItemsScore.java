package model.score;

import model.ItemStack;

// TODO: Auto-generated Javadoc
/**
 * CollectedItemsScore extends Score.
 * @author sebastianpasker
 *
 */
public class CollectedItemsScore extends Score<ItemStack>{

	/**
	 * Instantiates a new collected items score.
	 *
	 * @param playerName the player name
	 */
	public CollectedItemsScore(String playerName) {
		super(playerName);
	}

	/**
	 * CompareTo function.
	 *
	 * @param score2 value to compare with instance
	 * @return -1/0/1 if score is higher/equal/lower tan score2
	 */
	@Override
	public int compareTo(Score<ItemStack> score2) {
		int compareNumber = (int)((this.score-score2.score)*(-1)); // Default case lower
		return compareNumber;
	}

	/**
	 * Score function.
	 *
	 * @param itemScore Passed item to sum score
	 */
	@Override
	public void score(ItemStack itemScore) {
		score += itemScore.getType().getValue() * itemScore.getAmount();
	}
	
	
}
