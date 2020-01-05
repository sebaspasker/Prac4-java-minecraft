package model.score;

import model.Location;

// TODO: Auto-generated Javadoc
/**
 * PlayerMovementScore class. Extends Score<Location>.
 * @author sebastianpasker
 *
 */
public class PlayerMovementScore extends Score<Location>{

	/** PreviousLocation is a Location instance we  use for score function. */
	private Location previousLocation;
	
	/**
	 * Constructor.
	 * @param name super value.
	 */
	public PlayerMovementScore(String name) {
		super(name);
		previousLocation = null;
	}

	/**
	 * CompareTo function.
	 * @param score2 Score<Location> instance, class we gonna compare
	 * @return compareNumber 1/-1/0 condition
	 */
	@Override
	public int compareTo(Score<Location> score2) {		
		return (int)(this.getScoring() - score2.getScoring());
	}

	/**
	 * Score function, calculate distance between actual and 
	 * previous location and sum it to score.
	 *
	 * @param loc Passed Location
	 */
	@Override
	public void score(Location loc) {
		if(previousLocation != null) {
			double elevatedSum = Math.pow(loc.getX()-previousLocation.getX(),2)
					+Math.pow(loc.getY()-previousLocation.getY(), 2)
					+Math.pow(loc.getZ()-previousLocation.getZ(), 2);
			double comparationNumber = Math.sqrt(elevatedSum);
			if(comparationNumber < 0.0) {
				comparationNumber *= -1.0;
			}
			
			score += comparationNumber;
		}
		
		previousLocation = new Location(loc);
	}

}
