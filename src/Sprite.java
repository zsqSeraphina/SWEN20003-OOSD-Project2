
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * The general Sprite class, will be extended to 
 * buildings and characters
 *@author Siqi Zhou 903274
 */
public abstract class Sprite extends Unit{
	/**The path of the highlight images*/
	private static final String UNIT_HIGHLIGHT = "assets/highlight.png";
	private static final String LARGE_HIGHLIGHT= "assets/highlight_large.png";
	
	private Image highLight;
	private Image largeHighLight;
	private Image image;
	private String type;
	private double targetX, targetY;
	private double x, y;
	private Camera camera;
	/**the timer to calculate time*/
	private int timer = 0;
	/**To check if an object (e.g. builder) is creating an objeect*/
	private boolean isCreating = false;
	/**To check if an object is been slected*/
	private boolean isSelected = false;
	
	/**
	 * 
	 * @param camera the camera to follow the sprite
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @throws SlickException
	 */
	public Sprite(Camera camera, double x, double y) throws SlickException{
		super(camera, x, y);
		highLight = new Image(UNIT_HIGHLIGHT);
		largeHighLight = new Image(LARGE_HIGHLIGHT);
		this.camera = camera;
		this.x = x;
		this.y = y;
		this.targetX = x;
		this.targetY = y;
	}
	/**
	 * The update method, to be overriden in subclasses
	 * @param world
	 * @throws SlickException
	 */
	public void update(World world) throws SlickException {}
	
	/**
	 * 
	 * @return the x coordinate
	 */
	public double getX() {
		return x;
	}
	/**
	 * set the x coordinate
	 * @param x the x coordinate to be set to here
	 */
	public void setX(double x) {
		this.x = x;
	}
	/**
	 * 
	 * @return the y coordinate
	 */
	public double getY() {
		return y;
	}
	/**
	 * set the y coordinate
	 * @param y the y coordinate to be set to here
	 */
	public void setY(double y) {
		this.y = y;
	}
	/**
	 * set the x coordinate that is intend to go
	 * @param x the x coordiate to be set here
	 */
	public void setTargetX(double x) {
		this.targetX = x;
	}
	/**
	 * set the y coordinate that is intended to go
	 * @param y the y coordinate to be set here
	 */
	public void setTargetY(double y) {
		this.targetY = y;
	}
	/**
	 * the method get from project1 sample
	 * to reset the target
	 */
	public void resetTarget() {
		targetX = x;
		targetY = y;		
	}
	
	/**
	 * To draw the Sprites onto the screen according to the type,
	 * if the sprite is been selected also draw the highlight
	 * @throws SlickException
	 */
	public void render() throws SlickException {
		if(type.equals("scout")||type.equals("builder")||type.equals("engineer")||type.equals("truck")) {
			if(isSelected) {
				highLight.drawCentered((int)camera.globalXToScreenX(x),
						   (int)camera.globalYToScreenY(y));
			}
			image.drawCentered((int)camera.globalXToScreenX(x),
					   (int)camera.globalYToScreenY(y));
		}
		else if(type.equals("CommandCentre")||type.equals("Pylon")||type.equals("Factory")) {
			if(isSelected) {
				largeHighLight.drawCentered((int)camera.globalXToScreenX(x),
						   (int)camera.globalYToScreenY(y));
			}
			image.drawCentered((int)camera.globalXToScreenX(x),
					(int)camera.globalYToScreenY(y));
		}
	}

	/**
	 * Let the subclasses set the image to here
	 * @param image
	 */
	public void setImage(Image image) {
		this.image = image;
	}
	/**
	 * Let the subclassed set the type here
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * Let the other classes to get the type of this sprite
	 * @return the type of this sprite
	 */
	public String getType() {
		return this.type;
	}
	/**
	 * To set if the sprite has been selected or deselected
	 * @param flag the status of selection to be set
	 */
	public void setSelectFlag(boolean flag) {
		this.isSelected = flag;
	}
	/**
	 * Get the current selection status of this sprite
	 * @return the status of selection
	 */
	public boolean getSelectFlag() {
		return isSelected;
	}

	/**
	 * the methods to be overriden,
	 * the comments will be written in the subclasses
	 */
	public void mine(World world) {}
	public void setPassed() throws SlickException {}
	public boolean getPassed() {return false;}
	public boolean getCreate() {return isCreating;}
	public void setCreate(boolean running) {}
	public void setRes(Resource res) {}
	public void Timer(int delta) {this.timer += delta;}
	public int getTimer() {return this.timer;}
	public void resetTimer() {this.timer = 0;}
	public void create(World world) throws SlickException {}
	public int getPylonQuant() {return 0;}
	public void setChecked(boolean checked) {}
	public boolean getChecked() {return false;}
}