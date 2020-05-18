import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * The scout should move correctly,
 * some part of this class is get from the project1 sample
 * @author Siqi Zhou 903274
 *
 */
public class Scout extends Sprite{

	/**the path of the scout image*/
	private static final String SCOUT_IMA = "assets/units/scout.png";
	/**the moving speed of the scout*/
	private static final float SCOUT_SPEED = (float) 0.3;
	
	private Camera camera;
	private String type = "scout";
	private double x, y;
	private double targetX, targetY;
	public Scout (Camera camera, double x, double y) throws SlickException{
		super(camera, x, y);
		this.camera = camera;
		this.x = x;
		this.y = y;
		this.targetX = x;
		this.targetY = y;
		/**set the type and image of the scout*/
		setImage(new Image(SCOUT_IMA));
		setType(type);
	}
	/**
	 * this part is get from the project1 sample
	 * @param world get the input to let the scout move with mouse click
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
		if (World.distance(x, y, targetX, targetY) <= SCOUT_SPEED) {
			resetTarget();
		} else {
			// Calculate the appropriate x and y distances
			double theta = Math.atan2(targetY - y, targetX - x);
			double dx = (double)Math.cos(theta) * world.getDelta() * SCOUT_SPEED;
			double dy = (double)Math.sin(theta) * world.getDelta() * SCOUT_SPEED;
			// Check the tile is free before moving; otherwise, we stop moving
				if (world.isPositionFree(x + dx, y + dy)) {
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
	 * @return the x coordinate of the scout
	 */
	public double getX() {
		return x;
	}
	/**
	 * @return the y coordinate of the scout
	 */
	public double getY() {
		return y;
	}
	
}
