package model.score;

import java.util.SortedSet;
import java.util.TreeSet;

import model.exceptions.score.EmptyRankingException;

// TODO: Auto-generated Javadoc
/**
 * Ranking class.
 * @author sebastianpasker
 *
 * @param <ScoreType> extends Score(?)
 */
public class Ranking<ScoreType extends Score<?>> {
	/**
	 * Scores is a SortedSet ScoreType.
	 */
	private SortedSet<ScoreType> scores;
	
	/**
	 * Constructor.
	 */
	public Ranking() {
		scores = new TreeSet<ScoreType>();
	}
	
	/**
	 * addScore function, add score to scores array.
	 *
	 * @param score ScoreType instance.
	 */
	public void addScore(ScoreType score) {
		scores.add(score);
	}
	
	/**
	 * Getter.
	 * @return Defensive copy of scores
	 */
	public SortedSet<ScoreType> getSortedRanking() {
		return scores;
	}
	
	/**
	 * Getter, get first score in ranking.
	 * @return winner, the winner of ranking
	 * @throws EmptyRankingException In case size of scores is 0
	 */
	public ScoreType getWinner() throws EmptyRankingException {
		if(scores.size() == 0) {
			throw new EmptyRankingException();
		}
		return scores.first();
	}
}

