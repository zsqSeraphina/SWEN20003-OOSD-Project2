import org.newdawn.slick.SlickException;

/**
 * the most general unit class, to be extended
 *
 */
public abstract class Unit {
	private double x, y;
	/**
	 * 
	 * @param x
	 * @param y
	 * @throws SlickException
	 */
	public Unit(Camera camera, double x, double y) throws SlickException{
		this.x = x;
		this.y = y;
	}

	/**
	 * 
	 * @return the x coordinate
	 */
	public double getX() {
		return x;
	}

	/**
	 * 
	 * @return the y coordinate
	 */
	public double getY() {
		return y;
	}
}
