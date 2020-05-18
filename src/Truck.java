import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * The truck should move correctly and be able to build command centre to create scout/builder/engineer;
 * part of this class is get from the project1 sample
 * @author Siqi Zhou 903274
 *
 */
public class Truck extends Sprite{

	/**the path of the truck image*/
	private static final String TRUCK_IMA = "assets/units/truck.png";
	/** the moving speed of the truck*/
	private static final float TRUCK_SPEED = (float) 0.25;
	/** the time require to build a command centre(15s)*/
	private static final int CREATE_TIME = 15000;
	
	private Camera camera;
	private String type = "truck";
	private double x, y;
	private double targetX, targetY;
	private boolean isCreating = false;
	private boolean created = false;
	
	public Truck (Camera camera, double x, double y) throws SlickException{
		super(camera, x, y);
		this.camera = camera;
		this.x = x;
		this.y = y;
		this.targetX = x;
		this.targetY = y;
		/**set the type and image of the truck*/
		setType(type);
		setImage(new Image(TRUCK_IMA));
	}
	/**
	 * @param world to get input and delta from the world class
	 * to let the truck move correctly
	 */
	public void update(World world) throws SlickException{
		Input input = world.getInput();
		if(getSelectFlag()) {
			if(input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)){
				targetX = camera.screenXToGlobalX(input.getMouseX());
				targetY = camera.screenYToGlobalY(input.getMouseY());
			}
			camera.followSprite(this);
		}
			/*this part is get from the project1 sample*/
		if (World.distance(x, y, targetX, targetY) <= TRUCK_SPEED) {
			resetTarget();
		} else {
			// Calculate the appropriate x and y distances
			double theta = Math.atan2(targetY - y, targetX - x);
			double dx = (double)Math.cos(theta) * world.getDelta() * TRUCK_SPEED;
			double dy = (double)Math.sin(theta) * world.getDelta() * TRUCK_SPEED;
			// Check the tile is free before moving; otherwise, we stop moving
				if (world.isPositionFree(x + dx, y + dy) && !isCreating) {
					x += dx;
					y += dy;
					setX(x);
					setY(y);
				} else {
					resetTarget();
				}
		}
	}
	
	/**
	 * @param world to get input so that the truck will start to create when key 1 is pressed
	 * and will be able to add the built command centre into its corresponding list in the world
	 */
	public void create(World world) throws SlickException {
		Input input = world.getInput();
		int delta = world.getDelta();
		if(input.isKeyPressed(Input.KEY_1) && getSelectFlag()) {
			isCreating = true;
		}if(isCreating && getSelectFlag()) {
			if(getTimer() < CREATE_TIME) {
				Timer(delta);
			}else {
				setSelectFlag(false);
				isCreating = false;
				created = true;
				Sprite comCent = new CommandCentre(camera, x, y);
				world.updateList(comCent);
				resetTimer();
			
			}
		}
	}
	/**
	 * @return the x coordinate of the truck
	 */
	public double getX() {
		return x;
	}
	/**
	 * @return the y coordinate of the truck
	 */
	public double getY() {
		return y;
	}
	/**
	 * get the status of the truck, when it created a command centre it will be destroyed
	 * @return the status of the truck(created or not)
	 */
	public boolean getCreate() {
		return this.created;
	}
	
}
