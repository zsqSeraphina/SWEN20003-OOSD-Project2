import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * The builder should be able to move with the mouse click and build factory to create trucks;
 * @author Siqi Zhou 903274
 *
 */
public class Builder extends Sprite{

	/**the path of the builder image*/
	private static final String BUILD_IMA = "assets/units/builder.png";
	/**the moving speed of builder*/
	private static final float BUILD_SPEED = (float) 0.1;
	/**the time require to build a factory*/
	private static final int CREATE_TIME = 10000;
	/**the number required to create one factory*/
	private static final int METAL_FACT = 100;
	/**camera to follow the builder*/
	private Camera camera;
	private String type = "builder";
	private double x, y;
	private double targetX, targetY;
	/**check if the builder is creating a factory*/
	private boolean isCreating = false;
	public Builder (Camera camera, double x, double y) throws SlickException{
		super(camera, x, y);
		this.camera = camera;
		this.x = x;
		this.y = y;
		this.targetX = x;
		this.targetY = y;
		/**set the type and image of builder*/
		setType(type);
		setImage(new Image(BUILD_IMA));
	}
	/**
	 * this part is get from the project1 sample
	 * let the builder can move correctly
	 * @param world get input to get information of the mouse click
	 * and get delta to calculateb for move
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
		if (World.distance(x, y, targetX, targetY) <= BUILD_SPEED) {
			resetTarget();
		} else {
			// Calculate the appropriate x and y distances
			double theta = Math.atan2(targetY - y, targetX - x);
			double dx = (double)Math.cos(theta) * world.getDelta() * BUILD_SPEED;
			double dy = (double)Math.sin(theta) * world.getDelta() * BUILD_SPEED;
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
	
	/**to create a new factory and add it to the totallist and factlist in world
	 *@param world get the input and delta, so that the builder will start to build 
	 *when key 1 is pressed and whis will last for 10s; 
	 */
	public void create(World world) throws SlickException {
		Input input = world.getInput();
		int delta = world.getDelta();
		int metalQuant = world.getMetQuant();
		if(input.isKeyPressed(Input.KEY_1) && getSelectFlag() && (metalQuant >= METAL_FACT)) {
			isCreating = true;
		}if(isCreating && getSelectFlag()) {
			if(getTimer() < CREATE_TIME) {
				Timer(delta);
			}else {
			setSelectFlag(false);
			isCreating = false;
			metalQuant -= METAL_FACT;
			Sprite factory = new Factory(camera, x, y);
			world.updateList(factory);
			resetTimer();
			}
		}
	}
	
	/**
	 * get the x coordinate of the builder
	 */
	public double getX() {
		return x;
	}
	/**
	 * get the y coordinate of the builder
	 */
	public double getY() {
		return y;
	}
	/**
	 * @param isCreating the status of the builder(is creating or not)
	 */
	public void setCreate(boolean isCreating) {this.isCreating = isCreating;}
	/**
	 * @return the status of the buider(is creating or not)
	 */
	public boolean getCreate() {return isCreating;}
}
