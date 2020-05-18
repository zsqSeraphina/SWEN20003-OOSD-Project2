import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * The unobtainium class, construct the unobtainiums
 * @author Siqi Zhou 903274
 *
 */
public class Unobtainium extends Resource{

	/**the image to be rend on the map*/
	private static final String UNOB_IMA = "assets/resources/unobtainium_mine.png";
	/**the x and y coordinate*/
	private float unobX, unobY;
	/**the type to distinguish it from other resouorce*/
	private String type = "Unobtainium";
	/**the maximum quantity of unobtainium*/
	private int quantity = 50;
	/**
	 * The unobtainium class struct
	 * @param camera the camera from parent class
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @throws SlickException
	 */
	public Unobtainium(Camera camera, float x, float y) throws SlickException {
		super(camera, x, y);
		this.unobX = x;
		this.unobY = y;
		/**set the image, type and quantity of the unobtainium*/
		setImage(new Image(UNOB_IMA));
		setType(type);
		setQuantity(quantity);
	}
	
	/**
	 * get the x coordinate of this unobtainium
	 */
	public double getX() {
		return unobX;
	}
	/**
	 * get the y coordinate of this unobtainium
	 */
	public double getY() {
		return unobY;
	}
}
