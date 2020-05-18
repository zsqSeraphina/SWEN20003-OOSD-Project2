import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * The pylon class, construct pylons
 * @author Siqi Zhou 903274
 *
 */
public class Pylon extends Sprite{
	/**
	 * The path of pylon images, original and activated
	 */
	private static final String PYLON_IMA = "assets/buildings/pylon.png";
	private static final String ACTIVE_IMA = "assets/buildings/pylon_active.png";
	/**To check if this pylon has been  passed*/
	private boolean isPassed = false;
	private Camera camera;
	private double x, y;
	private boolean checked = false;
	/**
	 * The pylon struct
	 * @param camera can let the camera follow the pylon while it is been selected
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @throws SlickException
	 */
	public Pylon(Camera camera, float x, float y) throws SlickException{
		super(camera, x, y);
		this.x = x;
		this.y = y;
		this.camera = camera;
		/**set the image and type of this pylon*/
		setImage(new Image(PYLON_IMA));
		setType("Pylon");
	}
	/**
	 * update the pylon,
	 * if it is been selected, let the camera follow it
	 * and set its coordinate
	 */
	public void update(World world) throws SlickException {
		if(getSelectFlag()) {
			camera.followSprite(this);
			setX(x);
			setY(y);
		}
	}
	/**
	 * get the x coordinate
	 */
	public double getX() {
		return this.x;
	}
	/**
	 * get the y coordinate
	 */
	public double getY() {
		return this.y;
	}
	/**
	 * If this pylon has been passed by a unit,
	 * change the image into the activated inage,
	 * set its status into passed
	 */
	public void setPassed() throws SlickException {
		setImage(new Image(ACTIVE_IMA));
		isPassed = true;
	}
	/**
	 * get the current status of this pylon(passed or not)
	 */
	public boolean getPassed() {
		return this.isPassed;
	}
	/**
	 * @param the status of the pylon(been checked or not)
	 */
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	/**
	 * @return the status of the pylon(been checked or not)
	 */
	public boolean getChecked() {
		return this.checked;
	}
}
