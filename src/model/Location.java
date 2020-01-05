package model;


import java.util.HashSet;
import java.util.Set;

import model.exceptions.BadLocationException;
// TODO: Auto-generated Javadoc
/**
 * This class can return distance, length and hash code. Have getters and
 * setters and variable manipulation methods.
 * 
 * @author Sebastian Pasker Y0025435
 * @version 1.0
 */

public class Location implements Comparable<Location> {

	/**
	 * The x.
	 *
	 */
	private double x;
	
	/**
	 * The y.
	 *
	 */
	private double y;
	
	/**
	 * The z.
	 *
	 */
	private double z;
	
	/**
	 * The world.
	 *
	 */
	private World world;
	
	/**
	 * The Constant UPPER_Y_VALUE.
	 *
	 */
	public static final double UPPER_Y_VALUE = 255.0;
	
	/**
	 * The Constant SEA_LEVEL.
	 *
	 */
	public static final double SEA_LEVEL = 63.0;

	/**
	 * Constructor.
	 * 
	 * @param w World type parameter entrance
	 * @param x Thrdimensional x value entrance
	 * @param y Threeee--dimensional y value entrance , special condition setting (see
	 *          SetY)
	 * @param z Three-dimensional z value entrance
	 * 
	 */
	public Location(World w, double x, double y, double z) {
		if(w != null) {
			this.world = w;
		} else {
			w = null;
		}
		
		this.x = x;
		setY(y);
		this.z = z;

	}

	/**
	 * Copy Constructor.
	 *
	 * @param l Passed Location object
	 */
	public Location(Location l) {
		if(l.getWorld() != null) {
			world = l.getWorld();
		}
		x = l.getX();
		y = l.getY();
		z = l.getZ();
	}

	/**
	 * Create unique hash code for every Object Location.
	 *
	 * @return result. Is equal to object hash code
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((world == null) ? 0 : world.hashCode());
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(z);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/**
	 * Equal method for Location Object Case Location is null or transmited Objected
	 * is null return false.
	 *
	 * @param obj the obj
	 * @return true, if successful
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (world == null) {
			if (other.world != null)
				return false;
		} else if (!world.equals(other.world))
			return false;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
			return false;
		return true;
	}

	/**
	 * Add l values to Object values World can't be different to Object world.
	 *
	 * @param l passed Location by param
	 * @return this New this Location object with new values
	 */
	public Location add(Location l) {
		if (world != l.world) {
			System.err.println("Cannot add Locations of differing worlds.");
		} else {
			x += l.x;
			setY(y + l.y);
			z += l.z;
		}
		return this;
	}

	/**
	 * Rest l values to Object values World can't be different to Object world.
	 *
	 * @param l Location with variables to rest to actual Object
	 * @return this Object with new changed values
	 */
	public Location substract(Location l) {
		if (world != l.world) {
			System.err.println("Cannot substract Locations of differing worlds.");
		} else {
			x -= l.x;
			setY(y - l.y);
			z -= l.z;
		}
		return this;
	}

	/**
	 * Getter function.
	 * 
	 * @return x
	 */
	public double getX() {
		return x;
	}

	/**
	 * Getter function.
	 * 
	 * @return y
	 */
	public double getY() {
		return y;
	}

	/**
	 * Getter function.
	 * 
	 * @return z
	 */
	public double getZ() {
		return z;
	}

	/**
	 * Getter function.
	 * 
	 * @return world
	 */
	public World getWorld() {
		if(world == null) {
			return null;
		} else {
			return world;
		}
	}

	/**
	 * Setter function.
	 * 
	 * @param x Three-dimensional x entrance value
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * Setter function. 
	 * 
	 * @param y Three-dimensional y entrance value (conditioned)
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * Setter function.
	 * 
	 * @param z Three-dimensional z entrance value.
	 */
	public void setZ(double z) {
		this.z = z;
	}

	/**
	 * Setter function.
	 * 
	 * @param world World object type entrance.
	 */
	public void setWorld(World world) {
		this.world = world;
	}

	/**
	 * Calculate distance of 2 locations resting x, y and z parameters , squaring
	 * and adding the result values. At least sqrt the final result.
	 * 
	 * @param l Passed Location object
	 * @return distance It's result of dx,dz and dy elevated 2 and sqrt the result
	 */
	public double distance(Location l) {
		if (l.getWorld() == null || getWorld() == null) {
			System.err.println("Cannnot measure distance to a null world");
			return -1.0;
		} else if (l.getWorld() != getWorld()) {
			System.err.println("Cannot measure distance between " + world.getName() + " and " + l.world.getName());
			return -1.0;
		} else {
			double dx = x - l.getX();
			double dy = y - l.getY();
			double dz = z - l.getZ();
			return Math.sqrt((dx * dx) + (dy * dy) + (dz * dz));
		}
	}

	/**
	 * Calculate length of Location multiplying each xyz value by them self, adding
	 * the numbers and sqrt the sum.
	 *
	 * @return length It's the result of calculations mentioned over this
	 */
	public double length() {

		return Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * Multiply xyz value *factor.
	 *
	 * @param factor It's the number x, y, z multiplies separatly
	 * @return self New values of actual object in Location format with
	 *         multiplications
	 */
	public Location multiply(double factor) {
		x *= factor;
		setY(y * factor);
		z *= factor;
		return this;
	}

	/**
	 * Reset all xyz values to 0.0
	 * 
	 * @return self Return actual object with new 0.0 xyz values
	 */
	public Location zero() {
		x = y = z = 0.0;
		return this;
	}

	/**
	 * Pass all values of Location to a common string.
	 *
	 * @return sb.toString() It's Location string with "Location{world=this.world,
	 *         x=this.x, y=this.y, z=this.z}" format
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Location{world=");
		if (world == null) {
			sb.append("NULL");
		} else {
			sb.append(world);
		}
		sb.append(",x=" + x + ",y=" + y + ",z=" + z + "}");
		return sb.toString();
	}
	
	/**
	 * Function that returns down y Location copy.
	 * @return new Location with y-1 value
	 * @throws BadLocationException Exception launched when y is zero.
	 */
	public Location below() throws BadLocationException {
		if(y == 0 && world != null) { 
			throw new BadLocationException(world.getName());
		}
		
		return new Location(world, x, y-1,z);
	}
	
	/**
	 * Function that returns up y Location copy.
	 * @return new Location with y+1 value
	 * @throws BadLocationException Exception launched when y is 255 (MAX value).
	 */
	public Location above() throws BadLocationException {
		if(y == Location.UPPER_Y_VALUE && world != null) {
			throw new BadLocationException(world.getName());
		}
		
		return new Location(world, x, y+1, z);
	}
	
	/**
	 * Is free function.
	 *
	 * @return world.isFree with this value
	 */ 
	public boolean isFree() {
		boolean bool = false;
		if(world == null) {
			return false;
		}
		try {
			bool = world.isFree(this);
		} catch(Exception e) {
			System.err.println("Exception "+ e.getMessage());
		}
		
		return bool;
	}
	
	
	/**
	 * Function that create a ArrayList with all three dimensional positions that all around of Location Object.
	 * It don't include same x,y,z position.
	 * @return locationList. ArrayList Location type with all 9x9(8)x9 values around Location.
	 */
	public Set<Location> getNeighborhood() {
		Set<Location> locationList = new HashSet<Location>();
		
		for(int k=-1; k<2; k++) {
			for(int j=-1; j<2; j++) {
				for(int i=-1; i<2; i++) {
					Location l = new Location(world, x+i, y+j, z+k);
					if(Location.check(l)==true && this.equals(l) == false) {
						locationList.add(l);
					}
				}
			}
		}
		
		return locationList;
	}
	
	/**
	 * Check if passed xyz values are inside of World range.
	 * @param w World we use to know if Location is inside, pickup size as reference.
	 * @param x Three dimensional value
	 * @param y Three dimensional value
	 * @param z Three dimensional value
	 * @return True/false in case it's inside or out of world range
	 */
	public static boolean check(World w, double x, double y, double z) {
		int negativeSize;
		int positiveSize;
		if((w.getSize()%2) == 1) {
			negativeSize = (w.getSize()/2)*(-1);
		} else {
			negativeSize = (w.getSize()/2-1)*(-1);
		} 
		
		positiveSize = w.getSize()/2;
		if(x < negativeSize || z < negativeSize) {
			return false;
		} else if (x > positiveSize || z > positiveSize) {
			return false;
		} else if(y < 0 || y > Location.UPPER_Y_VALUE) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Copy check function, it's to pass location method.
	 *
	 * @param l Location Object, pickup as reference location.world and location xyz values.
	 * @return True/False case it's inside or out of range.
	 */
	public static boolean check(Location l) {
		if( l.getWorld() == null) {
			return true;
		}
		return Location.check(l.getWorld(), l.getX(), l.getY(), l.getZ());
	}
	
	/**
	 * Compare to function, loc1 < loc2, loc1 = loc2, loc1 > loc2 proof.
	 * @param loc2 transmitted Location
	 * @return integer (-X, 0, +X) in each case
	 */
	public int compareTo(Location loc2) {
		Location loc1 = this;
		double returnable = 1;
		
		if(loc1.getX() != loc2.getX()) {
			returnable = loc1.getX()-loc2.getX();
			
		} else if(loc1.getY() != loc2.getY()) {
			returnable = loc1.getY()-loc2.getY();
		} else if(loc1.getZ() != loc2.getZ()) {
			returnable = loc1.getZ()-loc2.getZ();
		} else {
			returnable = 0;
		}
		
		if(returnable > 0.0 && returnable < 1.0) {
			returnable = 1;
		} else if(returnable < 0.0 && returnable > -1.0) {
			returnable = -1.0;
		}
			
		return (int)returnable;
	}
}
