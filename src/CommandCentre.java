
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * the command centre should be able to create new engineer, builder and scout
 * @author Siqi Zhou 903274
 *
 */
public class CommandCentre extends Sprite{

	/**the path of the command centre image*/
	private static final String COM_CENT = "assets/buildings/command_centre.png";
	/**the number required to create one scout*/
	private static final int METAL_SCOUT = 5;
	/**the number required to create one builder*/
	private static final int METAL_BUILDER = 10;
	/**the number required to create one engineer*/
	private static final int METAL_ENG = 20;
	private String type = "CommandCentre";
	private double comX;
	private double comY;
	private Camera camera;
	
	public CommandCentre(Camera camera, double x, double y) throws SlickException{
		super(camera, x, y);
		/** the camera will follow the command centre when it is been selected*/
		this.camera = camera;
		this.comX = x;
		this.comY = y;
		/**set the type and image of the command centre*/
		setType(type);
		setImage(new Image(COM_CENT));
	}
	
	/**if the command centre is been selected, the camera will follow it*/
	public void update(World world) throws SlickException {
		if(getSelectFlag()) {
			camera.followSprite(this);
		}
	}
	
	/** create a new scout/builder/engineer and add it to the corresponding list
	 * according to its type, when key 1/2/3 is pressed*/
	public void create(World world) throws SlickException {
		Input input = world.getInput();
		if(getSelectFlag()) {
			if(input.isKeyPressed(Input.KEY_1) && (world.getMetQuant() >= METAL_SCOUT)) {
				world.setMetQuant(-METAL_SCOUT);
				Sprite scout = new Scout(camera, comX, comY);
				world.updateList(scout);
			}else if(input.isKeyPressed(Input.KEY_2) && (world.getMetQuant() >= METAL_BUILDER)) {
				world.setMetQuant(-METAL_BUILDER);
				Sprite builder = new Builder(camera, comX, comY);
				world.updateList(builder);
			}else if(input.isKeyPressed(Input.KEY_3) && (world.getMetQuant() >= METAL_ENG)) {
				world.setMetQuant(-METAL_ENG);
				Sprite eng = new Engineer(camera, comX, comY);
				world.updateList(eng);
			}
		}
	}
	/**
	 * @return comX the x coordinate of the command centre
	 */
	public double getX() {
		return comX;
	}
	/**
	 * @return comY the y coordinate of the command centre
	 */
	public double getY() {
		return comY;
	}
}
