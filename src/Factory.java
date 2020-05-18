
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * the factory should be able to create trucks to build command centres
 * @author Siqi Zhou 903274
 *
 */
public class Factory extends Sprite{

	/**the path of the factory image*/
	private static final String FACT_IMA = "assets/buildings/factory.png";
	/**the quantity required to create one truck*/
	private static final int METAL_TRUCK = 150;
	private String type = "Factory";
	private double x;
	private double y;
	private Camera camera;
	
	public Factory(Camera camera, double x, double y) throws SlickException{
		super(camera, x, y);
		/**camera will follow the factory when it is been selected*/
		this.camera = camera;
		this.x = x;
		this.y = y;
		/**set the type and the image of the factory*/
		setType(type);
		setImage(new Image(FACT_IMA));
	}
	
	/**
	 * if the factory is been selected, the camera will follow it
	 */
	public void update(World world) throws SlickException {
		if(getSelectFlag()) {
			camera.followSprite(this);
			setX(x);
			setY(y);
		}
	}
	/**
	 * @param world to get the input so that the factory will start to create when key 1 is pressed
	 * and can update the new truck into totallist and trucklist in world
	 */
	public void create(World world) throws SlickException {
		Input input = world.getInput();
		if(getSelectFlag()) {
			if(input.isKeyPressed(Input.KEY_1) && (world.getMetQuant() >= METAL_TRUCK)) {
				world.setMetQuant(-METAL_TRUCK);
				Sprite truck = new Truck(camera, x, y);
				world.updateList(truck);
			}
		}
	}
	/**
	 * @return x the x coordinate of the factory
	 */
	public double getX() {
		return x;
	}
	/**
	 * @return y the y coordinate of the factory
	 */
	public double getY() {
		return y;
	}
}
