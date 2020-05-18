

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 * The basic world class, contains all the lists that need to be
 * update and rend.
 * @author Siqi Zhou 903274
 */
public class World {
	
	private static final String SOLID_PROPERTY = "solid";
	private static final String MAP_PATH = "assets/main.tmx";
	private static final String FILE = "assets/objects.csv";
	private static final int TEXT_X = 32;
	private static final int TEXT_Y = 100;
	private static final int INFO_Y = 32;
	
	/**
	 * The list to hold all the objects in the map
	 * that need to be able for select and deselect
	 */
	private ArrayList<Sprite> totallist = new ArrayList<>();
	/**
	 * The lists to store the objects separately according to their type
	 * to be able for later update and rend
	 */
	private ArrayList<Sprite> englist = new ArrayList<>();
	private ArrayList<Sprite> scoutlist = new ArrayList<>();
	private ArrayList<Sprite> builderlist = new ArrayList<>();
	private ArrayList<Sprite> trucklist = new ArrayList<>();
	private ArrayList<Sprite> comcentlist = new ArrayList<>();
	private ArrayList<Sprite> factlist = new ArrayList<>();
	private ArrayList<Sprite> pylonlist = new ArrayList<>();
	private ArrayList<Resource> reslist = new ArrayList<>();
	
	/**set the initial map and player*/
	private static TiledMap map;
	//set up the camera
	private Camera camera = new Camera();
	private int mapHeight;
	private int mapWidth;
	/**Some numbers for later calculation*/
	private int imaSize = 64;
	private int metalQuant = 0;
	private int unobQuant = 0;
	private int pylonQ = 0;
	
	private Input lastInput;
	private int lastDelta;
	
	
	/**
	 * Create a new world 
	 * @throws SlickException
	 */
	public World() throws SlickException{
		map = new TiledMap(MAP_PATH);
		mapHeight = map.getHeight()*map.getTileHeight();
		mapWidth = map.getWidth()*map.getTileWidth();
		readFile(FILE);
	}
	/**
	 * function to get input, from the class World in project1 sample
	 * @return the input for later use in other classes
	 */
	public Input getInput() {
		return lastInput;
	}
	/**
	 * function to get delta, from the class World in project1 sample
	 * @return the delta for later use in other classes
	 */
	public int getDelta() {
		return lastDelta;
	}
	/**
	 * function  to check the property of a position, from the class World in project1 sample
	 * @param x the x coordinate to be checked
	 * @param y the y coordinate to be checked
	 * @return whether the coordinate is free to go
	 */
	
	public boolean isPositionFree(double x, double y) {
		int tileId = map.getTileId(worldXToTileX(x), worldYToTileY(y), 0);
		return !Boolean.parseBoolean(map.getTileProperty(tileId, SOLID_PROPERTY, "false"));
	}
	
	/**
	 * Read through the file and store the initial objects in the lists
	 * so that they can be initialised in the original world
	 * @param filename the file to be read
	 */
	public void readFile(String filename){
		try(BufferedReader br = new BufferedReader(new FileReader(filename))){
			String line = null;
			String [] position;
			String type = null;
			while((line = br.readLine()) != null) {
				position = line.split(",");
				type = position[0];
				if(type.equals("command_centre")) {
					Sprite s = new CommandCentre(camera, Float.parseFloat(position[1]),
							Float.parseFloat(position[2]));
					comcentlist.add(s);
					totallist.add(s);
				}else if(type.equals("pylon")) {
					Sprite s = new Pylon(camera, Float.parseFloat(position[1]),
							Float.parseFloat(position[2]));
					pylonlist.add(s);
					totallist.add(s);
				}else if(type.equals("metal_mine")) {
					Resource r = new Metal(camera, Float.parseFloat(position[1]),
							Float.parseFloat(position[2]));
					reslist.add(r);
				}else if(type.equals("unobtainium_mine")) {
					Resource r = new Unobtainium(camera, Float.parseFloat(position[1]),
							Float.parseFloat(position[2]));
					reslist.add(r);
				}else if(type.equals("engineer")) {
					Sprite s = new Engineer(camera, Float.parseFloat(position[1]),
							Float.parseFloat(position[2]));
					englist.add(s);
					totallist.add(s);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Loop through the lists and update the objects by their own properties
	 * @param input the input from player
	 * @param delta to count the time (0.001s)
	 * @throws SlickException
	 */
	public void update(Input input, int delta) throws SlickException {
		lastInput = input;
		lastDelta = delta;
		camera.update(this);
		/**update the totallist, let all the items in this list 
		 * able to be selected and deselected corrctly,
		 * including deselect one object while pressed on another,
		 * or deselect one while moving the camera by 'wasd'*/
		for(Sprite s : totallist) {
			passPylon(s);
			if(input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON) && !s.getSelectFlag()) {
				double targetX = camera.screenXToGlobalX(input.getMouseX());
				double targetY = camera.screenYToGlobalY(input.getMouseY());
				if ((targetX < s.getX() + imaSize/2 && targetX > s.getX() - imaSize/2) 
						&& (targetY < s.getY() + imaSize/2 && targetY > s.getY() - imaSize/2)) {
						input.clearKeyPressedRecord();
						s.setSelectFlag(true);
						camera.setSelectFlag(true);
					for(Sprite c : totallist) {
						if(c.getSelectFlag() && !c.equals(s)) {
							c.setSelectFlag(false);
						}
					}
				}
			}
			else if(input.isKeyDown(Input.KEY_W)||input.isKeyDown(Input.KEY_A)
					||input.isKeyDown(Input.KEY_S)||input.isKeyDown(Input.KEY_D)) {
				s.setSelectFlag(false);
				camera.setSelectFlag(false);
			}
		}
		/**Use an iterator so that the truck can be
		 * self destryed after creating a new command centre*/
		Iterator<Sprite> iter = trucklist.iterator();
		while(iter.hasNext()) {
			Sprite s = iter.next();
			if(!s.getCreate()) {
				s.create(this);
				s.update(this);
			}else {
				iter.remove();
			}
		}
		
		for(Sprite s: englist) {
			s.setRes(findResource(s));
			s.update(this);
		}
		
		for(Sprite s: scoutlist) {s.update(this);}
		for(Sprite s: comcentlist) {
			s.create(this);
			s.update(this);
		}
		
		for(Sprite s: factlist) {
			s.create(this);
			s.update(this);
		}
		for(Sprite s: builderlist) {
			s.create(this);
			s.update(this);
		}
		for(Sprite s: pylonlist) {s.update(this);}
		
		/**Use an iterator so that a resource can be 
		 * self destroyed when it is empty*/
		Iterator<Resource> iter1 = reslist.iterator();
		while(iter1.hasNext()) {
			Resource r = iter1.next();
			if(r.isEmpty()) {
				iter1.remove();
			}
		}
		
	}
	
	/**
	 * Read through the lists and rend the objects
	 * @param g used to display the text on the screen
	 * @throws SlickException
	 */
	public void render(Graphics g) throws SlickException{
		/*initialise the map*/
		map.render((int)camera.globalXToScreenX(0),
				   (int)camera.globalYToScreenY(0));
		
		for(Resource r: reslist) {r.render();}
		for(Sprite s: comcentlist) {
			if(s.getSelectFlag()) {
				g.drawString("1- Create Scout\n2- Create Builder\n3- Create Engineer\n", TEXT_X, TEXT_Y);
			}
			s.render();
		}
		for(Sprite s: factlist) {
			if(s.getSelectFlag()) {
				g.drawString("1- Create Truck\n", TEXT_X, TEXT_Y);
			}
			s.render();
		}
		for(Sprite s: trucklist) {
			if(s.getSelectFlag()) {
				g.drawString("1- Create Command Center\n", TEXT_X, TEXT_Y);
			}
			s.render();
		}
		for(Sprite s: pylonlist) {s.render();}
		for(Sprite s: englist) {s.render();}
		for(Sprite s: scoutlist) {s.render();}
		for(Sprite s: builderlist) {
			if(s.getSelectFlag()) {
				g.drawString("1- Create Factory\n", TEXT_X, TEXT_Y);
			}
			s.render();
		}
		g.drawString(String.format("Metal: %d\nUnobtainium: %d", metalQuant, unobQuant), TEXT_X, INFO_Y);
	}
	
	/**
	 * Find a nearest command centre of the engineer
	 * @param s the engineer 
	 * @return the found nearest command centre
	 */
	public Sprite findNearComCent(Sprite s) {
		double dis = -1;
		double near = Integer.MAX_VALUE;
		Sprite nearCom = null;
		for(Sprite com : comcentlist) {
			dis = distance(s.getX(), s.getY(), com.getX(), com.getY());
			if(dis < near) {
				near = dis;
				nearCom = com;
			}
		}
		return nearCom;
	}
	/**
	 * Find a resource near the engineer
	 * @param s the engineer
	 * @return the found nearest resource
	 */
	public Resource findResource(Sprite s) {
		Resource nearRes = null;
		for(Resource r: reslist) {
			if(distance(s.getX(), s.getY(), r.getX(), r.getY()) < imaSize/2) {
				nearRes = r;
			}
		}
		return nearRes;
	}
	/**
	 * Check if a sprite has  passed through a pylon
	 * @param s the Sprite that passes the pylon
	 * @throws SlickException
	 */
	public void passPylon(Sprite s) throws SlickException {
		for(Sprite p: pylonlist) {
			if((distance(s.getX(), s.getY(), p.getX(), p.getY()) < imaSize/2) && !p.equals(s)) {
				p.setPassed();
			}
		}
	}
	
	/**
	 * @return the width of the map
	 */
	public int getMapWidth() {
		return mapWidth;
	}
	/**
	 * 
	 * @return the height of the map
	 */
	public int getMapHeight() {
		return mapHeight;
	}
	/**
	 * get from the  project1 sample,
	 * to convert an x coordinate from 
	 * x into tile x
	 * @param x the x to be converte
	 * @return the converted x
	 */
	public int worldXToTileX(double x) {
		return (int)(x / map.getTileWidth());
	}
	/**
	 * get from the  project1 sample,
	 * to convert a y coordinate from 
	 * y into tile y
	 * @param y the y to be converte
	 * @return the converted y
	 */
	public int worldYToTileY(double y) {
		return (int)(y / map.getTileHeight());
	}
	
	/**
	 * get from the project1 sample
	 * calculate the distance between 2 coordinates
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return the calculated result
	 */
	public static double distance(double x1, double y1, double x2, double y2) {
		return (double)Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
	}
	/**
	 * Add the mined quantity of metal
	 * @param metal the quantity to be add
	 */
	public void setMetQuant(int metal) {
		this.metalQuant += (metal);
	}
	/**
	 * Add the mined quantity of unobtainium
	 * @param unob the quantity to be add
	 */
	public void setUnobQuant(int unob) {
		this.unobQuant += (unob);
	}
	/**
	 * 
	 * @return the total metal quantity
	 */
	public int getMetQuant() {
		return this.metalQuant;
	}
	/**
	 * 
	 * @return the total unobtainium quantity
	 */
	public int getUnobQuant() {
		return this.unobQuant;
	}
	/**
	 * Add a new sprite to the lists 
	 * according to its type
	 * @param s the sprite to be added
	 */
	public void updateList(Sprite s) {
		if(s.getType().equals("Factory")) {
			factlist.add(s);
			totallist.add(s);
		}
		if(s.getType().equals("scout")) {
			scoutlist.add(s);
			totallist.add(s);
		}
		if(s.getType().equals("builder")) {
			builderlist.add(s);
			totallist.add(s);
		}
		if(s.getType().equals("engineer")) {
			englist.add(s);
			totallist.add(s);
		}
		if(s.getType().equals("truck")) {
			trucklist.add(s);
			totallist.add(s);
		}
		if(s.getType().equals("CommandCentre")) {
			comcentlist.add(s);
			totallist.add(s);
		}
	}
	/**
	 * change the pylonQ to show the number 
	 * of activated pylon in world for later use
	 * @param pylonQ number of activated pylons
	 */
	public void setPylonQ(int pylonQ) {this.pylonQ = pylonQ;}
	/**
	 * get the pylon quantity from the world class
	 * @return the current quantity of pylon
	 */
	public int getPylonQ() {return this.pylonQ;}
	/**
	 * check the number of pylons been passed by sprite and whether this pylon has been checked
	 * in case it repeatedly adding since it is been invoked in an update mmethod.
	 * @return the quantity of passed pylons
	 */
	public int checkPassedPylon() {
		for(Sprite p: pylonlist) {
			if(p.getPassed() && !p.getChecked()) {
				this.pylonQ += 1;
				p.setChecked(true);
			}
		}
		return this.pylonQ;
	}
}
