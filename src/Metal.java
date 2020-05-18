import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * The metal class, constructs the metals
 * @author Siqi Zhou 903274
 *
 */
public class Metal extends Resource{

	/**the image to be rend on the map*/
	private static final String METAL_IMA = "assets/resources/metal_mine.png";
	/**the x and y coordinate*/
	private float metalX, metalY;
	/**the type to distinguish it from other resouorce*/
	private String type = "Metal";
	/**the maximum quantity of metal*/
	private int quantity = 500;
	
	/**
	 * the metal class struct
	 * @param  the camera from parent class
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @throws SlickException
	 */
	public Metal(Camera camera, float x, float y) throws SlickException {
		super(camera, x, y);
		this.metalX = x;
		this.metalY = y;
		/**set the image, type and quantity of the unobtainium*/
		setImage(new Image(METAL_IMA));
		setType(type);
		setQuantity(quantity);
	}
	/**
	 * get the x coordinate of this metal
	 */
	public double getX() {
		return metalX;
	}
	/**
	 * get the y coordinate of this metal
	 */
	public double getY() {
		return metalY;
	}
}
