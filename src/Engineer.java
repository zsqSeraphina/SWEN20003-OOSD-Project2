
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * The Engineer class, construct engineers, an engineer can move and mine resources
 * some part of the update method is get from the project1 sample and will be indicted in the comment
 * @author Siqi Zhou 903274
 *
 */
public class Engineer extends Sprite{

	/**the moving speed of engineer*/
	private static final float ENG_SPEED = (float) 0.1;
	/**the time require to mine once*/
	private static final int MINE_TIME = 5000;
	/**the quantity each time can mine*/
	private static final int MINE_QUANT = 2;
	/**the path of the engineer image*/
	private static final String ENG_IMA = "assets/units/engineer.png";
	/**the size of a image, 
	 * used to check the distance between the engineer and another object*/
	private int imaSize = 64;
	/**the type of this engineer*/
	private String type = "engineer";
	private double curr_x;
	private double curr_y;
	private double targetX, targetY;
	private Camera camera;
	/**the x and y coordinate of a command centre*/
	private double comX, comY;
	/**the x and y coordinate of a resource*/
	private double resX, resY;
	/**to check if the engineer is mining*/
	private boolean isRunning = false;
	/**the resource going to mine*/
	private Resource res = null;
	private Resource mineRes = null;
	/**to check if the engineer is carrying resource*/
	private boolean isCarrying = false;
	/**to check if the comand centre gets the resource*/
	private boolean get = false;
	/**to calculate the mine time*/
	private int timer;
	
	/**
	 * the engineer class construct
	 * @param camera the camera that will follow this engineer
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @throws SlickException
	 */
	public Engineer(Camera camera, double x, double y) throws SlickException {
		super(camera, x, y);
		this.camera = camera;
		this.curr_x = x;
		this.curr_y = y;
		this.targetX = curr_x;
		this.targetY = curr_y;
		/**set the type and image of the engineer*/
		setImage(new Image(ENG_IMA));
		setType(type);
	}
	
	/**
	 * the update method allows the engineer to move
	 * with the mouse click and mine the resources
	 * @param world to get the input and delta
	 */
	public void update(World world) throws SlickException{
		Input input = world.getInput();
		Sprite comCent = world.findNearComCent(this);
		int pylonQ = world.checkPassedPylon();
		if(res != null&& !isRunning) {
			if(timer <  MINE_TIME) {
				timer+=world.getDelta();
			}else{
				isRunning = true;
				mineRes = res;
				timer = 0;
			}
		}
		if(res == null && !isRunning) {timer = 0;}
		if(!isCarrying && isRunning && mineRes != null) {
			if(get) {
				if(mineRes.getType().equals("Metal")) {world.setMetQuant(MINE_QUANT+pylonQ);}
				if(mineRes.getType().equals("Unobtainium")) {world.setUnobQuant(MINE_QUANT+pylonQ);}
				mineRes.setQuantity(mineRes.getQuantity()-(MINE_QUANT+pylonQ));
				get = false;
				isRunning = false;
			}
		}
		if(isRunning) {
			mine(mineRes, comCent);
		}
		if(getSelectFlag()) {
			if(input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)){
				targetX = camera.screenXToGlobalX(input.getMouseX());
				targetY = camera.screenYToGlobalY(input.getMouseY());
			}
			camera.followSprite(this);
		}
			/**this part is get from the project1 sample*/
		if (World.distance(curr_x, curr_y, targetX, targetY) <= ENG_SPEED) {
			resetTarget();
		} else {
			// Calculate the appropriate x and y distances
			double theta = Math.atan2(targetY - curr_y, targetX - curr_x);
			double dx = (double)Math.cos(theta) * world.getDelta() * ENG_SPEED;
			double dy = (double)Math.sin(theta) * world.getDelta() * ENG_SPEED;
			// Check the tile is free before moving; otherwise, we stop moving
				if (world.isPositionFree(curr_x + dx, curr_y + dy)) {
					curr_x += dx;
					curr_y += dy;
					setX(curr_x);
					setY(curr_y);
				} else {
					resetTarget();
				}
		}
	}
	
	/**
	 * To let the engineer run between the resource and the nearest command centre
	 * @param res the resource to mine
	 * @param comCent the command centre to come back and store the resource
	 */
	public void mine(Resource res, Sprite comCent) {
		comX = comCent.getX();
		comY = comCent.getY();
		resX = res.getX();
		resY = res.getY();
		if(World.distance(curr_x, curr_y, resX, resY) <= imaSize/2) {
			isCarrying = true;
			targetX = comX;
			targetY = comY;
			resetTarget();
		}else if((World.distance(curr_x, curr_y, comX, comY) <= imaSize/2) && !res.isEmpty()) {
			get = true;
			isCarrying = false;
			targetX = resX;
			targetY = resY;
			resetTarget();
		}
	}
	/**
	 * get the current x coordinate of the engineer
	 */
	public double getX() {
		return curr_x;
	}
	/**
	 * get the current y coordinate of the  engineer
	 */
	public double getY() {
		return curr_y;
	}
	/**
	 * set the found nearest resource to this engineer
	 */
	public void setRes(Resource res) {
		this.res = res;
	}
}