
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * This is an abstract class to create a general resource
 *@author Siqi Zhou 903274
 */
public abstract class Resource extends Unit{
	
	private float x, y;
	private Image image;
	private Camera camera;
	private String type;
	private int quantity;
	/**
	 * The resource struct
	 * @param camera used to transfer x and y coordinate
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @throws SlickException
	 */
	public Resource(Camera camera, float x, float y) throws SlickException{
		super(camera, x, y);
		this.x = x;
		this.y = y;
		this.camera = camera;
	}
	/**
	 * The render method, to draw the image
	 */
	public void render() {
		image.drawCentered((int)camera.globalXToScreenX(x),
				   (int)camera.globalYToScreenY(y));
	}
	
	/**
	 * get the x coordinate of the resource
	 */
	public double getX() {
		return x;
	}
	/**
	 * set the x coordinate to this resource
	 * @param resX the x coordinate to be set here
	 */
	public void setX(float resX) {
		this.x = resX;
	}
	/**
	 * get the y coordinate of the resource
	 */
	public double getY() {
		return y;
	}
	/**
	 * set the y coordinate to this resource
	 * @param resY the y coordinate to be set here
	 */
	public void setY(float resY) {
		this.y = resY;
	}
	/**
	 * Set the image according to the type to rend
	 * @param image te image to be set to here
	 */
	public void setImage(Image image) {
		this.image = image;
	}
	/**
	 * set the type of this resource, 
	 * to distinguish it in later use
	 * @param type the type to be set here
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * get the type of this resource
	 * @return the type of this resource
	 */
	public String getType() {
		return type;
	}
	/**
	 * set the quantity of this resource,
	 * for later use such as mining,
	 * to change the quantity of this resource
	 * @param quantity
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	/**
	 * get the current quantity of this resource
	 * @return the current quantity of this resource
	 */
	public int getQuantity() {
		return this.quantity;
	}
	/**
	 * to check if this resource is empty,
	 * it will be removed if it is empty
	 * @return whether it is empty(true/false) 
	 */
	public boolean isEmpty() {
		if(quantity <= 0) {
			return true;
		}
		return false;
	}
}
