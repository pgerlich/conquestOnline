package conquest.online.gameAssets.Structures;


public class AbstractStructure  {

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
	private int x;
	private int y;

	//public abstract void use();
	
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
	 * used to set values of items read from the shop
	 */
	public void create(String name, int cost, String pic, String description, int id) {
    	setCost(cost);
    	picPath = pic;
    	setDes(description);
    	setName(name);
    	this.id = id;
    }
	
	/**
	 * Return the ID
	 * @return
	 */
	public int getId() {
		return id;
	}
    
//	//deep copy of floorplan provided
//	//floor plan must include (0,0) to represent the starting grid block
//	private void setFloorPlan(ArrayList<Coordinate> floor) {
//		for(int i = 0; i < floor.size();  i++){
//			this.floorPlan.add(new Coordinate(floor.get(i).getX(), floor.get(i).getY()));
//		}
//		
//	}
//	
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
	 * Get x
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Get y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Set the x
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Set the y
	 */
	public void setY(int y) {
		this.y = y;
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
