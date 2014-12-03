package conquest.online.gameAssets.Structures;
import conquest.online.gameAssets.Attackable;
import conquest.online.gameAssets.Visible;
import map.Coordinate;
import java.util.ArrayList;
public abstract class AbstractStructure implements Visible, Attackable {

	//Stats/Info
	private String name;
	private int id;
	private int imageID;
	private int level;
	private int cost;
	private String description;
	private int curHealth;
	private int maxHealth;
	private boolean enabled;
	private int defense;
	private int viewRadius;
	private String picPath;
	
	//Images/location/ect.
	private Coordinate coordinates;

	public abstract void use();
	
	/**
	 * Instantiate an object
	 * @param id
	 * @param floor
	 */
	public AbstractStructure(int id, String picPath) {
		this.picPath = picPath;
		this.enabled = true;
	}
	
	/**
	 * Set all the stats
	 * @param name
	 * @param level
	 * @param cost
	 * @param curHealth
	 * @param maxHealth
	 * @param defense
	 * @param viewRadius
	 * @param enabled
	 */
	public void setStats(String name, int imageID, int level, int cost, int curHealth, int maxHealth, int defense, int viewRadius, boolean enabled){
		this.name = name;
		this.imageID = imageID;
		this.level = level;
		this.cost = cost;
		this.curHealth = curHealth;
		this.maxHealth = maxHealth;
		this.defense = defense;
		this.viewRadius = viewRadius;
		this.enabled = enabled;
	}
	
	/**
	 * Return the ID
	 * @return
	 */
	public int getId() {
		return id;
	}
    
	/**
	 * Set cost
	 * @param cost
	 */
	public void setCost(int cost) {
		this.cost = cost;
	}
	
	/**
	 * Set the description
	 * @param d
	 */
	public void setDes(String d) {
		description = d;
	}
	
	/**
	 * get description
	 * @return
	 */
	public String getDes() {
		return description;
	}
	
	/**
	 * Set the name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get the name
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Adjust current health
	 */
	public void adjustCurrentHealth(int change) {
		curHealth += change;
	}
	
	/**
	 * Get the current health
	 */
	public int getCurrentHealth() {
		return curHealth;
	}
	
	/**
	 * Set max health
	 */
	public void adjustMaxHealth(int change) {
		maxHealth += change;
	}
	
	/**
	 * Get max health
	 */
	public int getMaxHealth() {
		return maxHealth;
	}
	
	/**
	 * Draw on the screen??
	 */
	public void drawSelf(Coordinate c) {
		// TODO Auto-generated method stub
	}

	/**
	 * Get coordinates of top left point
	 */
	public Coordinate getCoordinate() {
		return coordinates;
	}

	/**
	 * Set the coordinates
	 */
	public void setCoordinate(Coordinate c) {
		coordinates = c;
	}
	
	/**
	 * Enable/disable the structure
	 */
	public void enableDisable(){
		enabled = !enabled;
	}
	
	/**
	 * get enabled/disabled
	 */
	public boolean isEnabled(){
		return enabled;
	}
	
	/**
	 * Return the defense
	 * @return
	 */
	public int getDefense(){
		return defense;
	}
	
	/**
	 * adjust the defense
	 * @param change
	 */
	public void adjustDefense(int change){
		defense += change;
	}
	
	/**
	 * Get the view radius
	 * @return
	 */
	public int getViewRadius(){
		return viewRadius;
	}
	
	/**
	 * adjust the view radius
	 * @param change
	 */
	public void adjustViewRadius(int change){
		viewRadius += change;
	}
	
	/**
	 * Adjust the level
	 * @param change
	 */
	public void adjustLevel(int change){
		level += change;
	}
	
	/**
	 * get the level
	 * @return
	 */
	public int getLevel(){
		return level;
	}
	
	/**
	 * get the cost
	 * @return
	 */
	public int getCost(){
		return cost;
	}

	/**
	 * Return the picture path as a string
	 */
	public String getPic(){
		return picPath;
	}
	
	/**
	 * Set the image id
	 * @param id
	 */
	public void setImageID(int id){
		imageID = id;
	}
	
	/**
	 * Return the image ID
	 * @return
	 */
	public int getImageID(){
		return imageID;
	}
}
