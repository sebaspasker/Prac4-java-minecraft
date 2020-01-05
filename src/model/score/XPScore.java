package model.score;

import java.util.ArrayList;
import java.util.List;

import model.entities.Player;
import model.exceptions.score.ScoreException;

// TODO: Auto-generated Javadoc
/**
 * XPScore class. Extends Score<Player>
 * @author sebastianpasker
 *
 */ 
public class XPScore extends Score<Player>{
	/**
	 * Player instance. 
	 */
	private Player player;
	
	/**
	 * Score<?> instance.
	 */
	private List<Score<?>> scores;
	
	/**
	 * Overloaded Constructor. Super score and pass player name.
	 * @param player Player instance.
	 */
	public XPScore(Player player) {
		super(player.getName());
		this.player = player;
		super.score = 0;
		scores = new ArrayList<Score<?>>();
	}

	/**
	 * compareTo function. Case number is higher 
	 * compareToNumber is lower and reverse. Case equals
	 * return 0.
	 *
	 * @param p2score Second player score.
	 * @return the int
	 */
	@Override
	public int compareTo(Score<Player> p2score) {
		int compareNumber = (int)((this.getScoring()-p2score.getScoring())*(-1)); // Default case lower
		return compareNumber;
	}
	
	// TODO Falla.
	/**
	 * Score function. Calculate average of all scores and
	 * sum player food and player health.
	 *
	 * @param p Player instance.
	 * @throws ScoreException the score exception
	 */
	@Override
	public void score(Player p) {
		if(!this.player.equals(p) || p == null) {
			throw new ScoreException();
		}
	
		if(scores.size() == 0) {
			score = p.getHealth() + p.getFoodLevel();
		} else {
			double allScore = 0;
			for(int i=0; i<scores.size(); i++) {
				allScore += scores.get(i).getScoring();
			}
			
			double average = allScore/scores.size();
			
			double totalScore = average + p.getHealth() + p.getFoodLevel();
			super.score = totalScore;
		}
	}
	
	/**
	 * Score Getter. Recalculate score.
	 * @return score value.
	 */
	@Override
	public double getScoring() {
		try {
			score(this.player);
		} catch (Exception e) {
			System.err.println("Player is null or not same");
		}
		
		return super.getScoring();
	} 
	
	/**
	 * Add new score ? class and recalculates score value.
	 *
	 * @param score the score
	 * @throws ScoreException the score exception
	 */
	public void addScore(Score<?> score) {
		scores.add(score);
		try {
			score(this.player);
		} catch (Exception e) {
			System.err.println("Player is null or not same");
		}
	}
	
}
