package conquest.online.gameAssets;

import com.google.android.gms.maps.model.LatLng;

public class Property {
	
	//The properties ID
	private int id;
	
	//Latitude/longitude of this object
	private LatLng location;

	/**
	 * makes a new property with the given ID/Location
	 * @param id
	 * @param location
	 */
	public Property(int id, LatLng location) {
		this.id = id;
		this.location = location;
	}
	
	/**
	 * Return this propeties ID
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
