package conquest.client.classes;

public class UpdateLatLongRequest {
	public String username;
	public String token;
	
	//Where we are now
	public double curLat;
	public double curLng;
	
	//Where we are going
	public double destLat;
	public double destLng;
}
