
import org.newdawn.slick.Input;
/**
 * This class should be used to restrict the game's view to a subset of the entire world.
 * 
 * Part of this class is get from the project1 sample and will be indicated in the comment
 *@author Siqi Zhou 903274
 */

public class Camera {
	/**the moving speed of this camera*/
	private static final float CAM_SPEED = (float) 0.4;
	/**the x and y used to transfer the coordinate*/
	private double x = App.WINDOW_WIDTH/2;
	private double y = App.WINDOW_HEIGHT/2;
	/**the x and y coordinate of the target to be followed*/
	private double targetX = App.WINDOW_WIDTH/2;
	private double targetY = App.WINDOW_HEIGHT/2;
	/**the target to be followed*/
	private Sprite target = null;
	/**the select status of the target*/
	private boolean selectFlag = false;
	
	/**
	 * if this method is been invoked,
	 * the camera will follow this sprite
	 * @param target the sprite to be followed
	 */
	public void followSprite(Sprite target) {
		this.target = target;
	}
	/**
	 * transfer the given x into screen x coordinate
	 * @param x the x coordinate to be transfer
	 * @return the transfered result
	 */
	public double globalXToScreenX(double x) {
		return x - this.x;
	}
	/**
	 * transfer the given y into screen y coordinate
	 * @param y the y coordinate to be transfer
	 * @return the transfered result
	 */
	public double globalYToScreenY(double y) {
		return y - this.y;
	}

	/**
	 * transfer the given x into x coordinate on the map
	 * @param x the x coordinate to be transfer
	 * @return the transfered result
	 */
	public double screenXToGlobalX(double x) {
		return x + this.x;
	}
	/**
	 * transfer the given y into y coordinate on the map
	 * @param y the y coordinate to be transfer
	 * @return the transfered result
	 */
	public double screenYToGlobalY(double y) {
		return y + this.y;
	}
	
	/**set the status of selection accordingto the
	 * selection status of the followed sprite
	 * @param selectFlag the selection status to be set here
	 */
	public void setSelectFlag(boolean selectFlag) {
		this.selectFlag  = selectFlag;
	}
	/**
	 * Used to update the camera,
	 * depends on if it is following a sprite,
	 * or it is moving by 'wasd' keys
	 * @param world to get the input from world
	 */
	public void update(World world) {
		if(selectFlag && (target != null)){
			/**this part is get from the project1 sample
			 * to follow a moving sprite if it has been selected
			 * and let the camera stops while it has reached the
			 * edge of the map
			 */
			targetX = target.getX() - App.WINDOW_WIDTH / 2;
			targetY = target.getY() - App.WINDOW_HEIGHT / 2;
			x = Math.min(targetX, world.getMapWidth() -	 App.WINDOW_WIDTH);
			x = Math.max(x, 0);
			y = Math.min(targetY, world.getMapHeight() - App.WINDOW_HEIGHT);
			y = Math.max(y, 0);
		}
		/**this part written by myself
		 * to move the camera by 'wasd'keys
		 * and let the camera stops while it has reached the
		 * edge of the map
		 */
		if(world.getInput().isKeyDown(Input.KEY_W)) {
			y = y - CAM_SPEED * world.getDelta();
			if(y < 0) {
				y = y + CAM_SPEED*world.getDelta();
			}
		}
		else if(world.getInput().isKeyDown(Input.KEY_S)) {
			y = y + CAM_SPEED * world.getDelta();
			if(y > world.getMapHeight() - App.WINDOW_HEIGHT) {
				y = y - CAM_SPEED * world.getDelta();
			}
		}
		else if(world.getInput().isKeyDown(Input.KEY_A)) {
			x = x - CAM_SPEED * world.getDelta();
			if(x < 0) {
				x = x + CAM_SPEED * world.getDelta();
			}
		}
		else if(world.getInput().isKeyDown(Input.KEY_D)) {
			x = x + CAM_SPEED * world.getDelta();
			if(x > world.getMapWidth() - App.WINDOW_WIDTH) {
				x = x - CAM_SPEED * world.getDelta();
			}
		}
		
	}
}