package conquest.online.gameAssets;

import java.util.ArrayList;

import map.Coordinate;

import com.google.android.gms.maps.model.LatLng;

import conquest.online.gameAssets.Structures.AbstractStructure;

public class Property {
	
	//The property's ID
	private int id;
	
	//Latitude/longitude of this object
	private LatLng location;
	
	//2d array representing the property. 
	protected String[][] land;
	
	//arrayList of structure objects the user owns
	protected ArrayList<AbstractStructure> structureList; 

	/**
	 * makes a new property with the given ID/Location
	 * @param id
	 * @param location
	 */
	public Property(int id, LatLng location, ArrayList<AbstractStructure> structures) {
		this.id = id;
		this.location = location;
		this.structureList = structures;
		this.land = new String[7][7];
		cleanProperty();
		populateLand();
	}
	/**
	 * set all of the land values to NULL
	 */
	private void cleanProperty() {
		for(int x = 0; x < 7; x++){
			for(int y = 0; y < 7; y++){
				land[x][y] = null;
			}
		}	
	}

	/**
	 * use the structureList to populate land array with image strings
	 */
	protected void populateLand() {
		Coordinate start, set;
		String imagePath;
		int x,y;
		
		for(int i = 0; i < this.structureList.size(); i ++){
			start = this.structureList.get(i).getCoordinate();
			imagePath = this.structureList.get(i).getPic();
			if(start.x > 0 && start.y > 0){
				for(int j = 0;  j < this.structureList.get(i).floorPlan.size(); j++){
					set = this.structureList.get(i).floorPlan.get(j);
					x = start.x + set.x;
					y = start.y + set.y;
					this.land[x][y] = imagePath + set.x + set.y + ".png";
				}
			}
		}	
	}
	
	/**
	 * check each tile of the structure s
	 * @return
	 * true if placement doesn't overlap or go out of bounds
	 */
	public boolean checkStructurePacement(int structureIndex, Coordinate start){
		
		AbstractStructure s = structureList.get(structureIndex);
		Coordinate c;
		int x, y;
		
		for(int i = 0; i < s.floorPlan.size(); i++){
			c = s.floorPlan.get(i);
			x = c.x + start.x;
			y = c.y + start.y;
			if(!this.land[x][y].equals(null)){
				return false;
			}	
		}
		return true;
	}
	
	/**
	 * remove a structrue form the grid not Property
	 * @return
	 */
	public void pickUpStructure(int structureIndex){
		AbstractStructure s = structureList.get(structureIndex);
		Coordinate start, c;
		int x,y;
		
		start = s.getCoordinate();
		for(int i = 0; i < s.floorPlan.size(); i++){
			c = s.floorPlan.get(i);
			x = c.x + start.x;
			y = c.y + start.y;
			land[x][y] = null;
		}
		s.setCoordinate(new Coordinate(-1,-1));
	}
	
	
	/**
	 * place a structure on the grid that is currently not on it.
	 * 
	 * @return
	 */
	public void placeStructure(int structureIndex, Coordinate start){
		AbstractStructure s = structureList.get(structureIndex);
		if(s.getCoordinate().x > 0 && s.getCoordinate().y > 0){
			Coordinate c;
			int x,y;
			s.setCoordinate(start);
		
			for(int i = 0; i < s.floorPlan.size(); i++){
				c = s.floorPlan.get(i);
				x = c.x + start.x;
				y = c.y + start.y;
				land[x][y] = s.getPic()+ c.x + c.y + ".png";
			}
		}
	}
	
	
	/**
	 * Return this propety's ID
	 * @return
	 */
	public int getId(){
		return id;
	}
	
	/**
	 * Return the Latitude/Longitude location
	 * @return
	 */
	public LatLng getLocation(){
		return location;
	}
	
	/**
	 * Set the location for this property
	 * @param location
	 */
	public void setLocation(LatLng location){
		this.location = location;
	}
	
	/**
	 * Return the longitude
	 * @return
	 */
	public double getLongitude(){
		return location.longitude;
	}
	
	/**
	 * Return the latitude
	 * @return
	 */
	public double getLatitude(){
		return location.latitude;
	}
}
