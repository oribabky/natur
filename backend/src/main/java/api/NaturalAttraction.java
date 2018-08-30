package api;

/**
 * this class defines a nature attraction 
 * @author tobias
 *
 */
public class NaturalAttraction {
	private String name;
	private String country;
	private double latitude;
	private double longitude;
	private String attractionType;
	
	public NaturalAttraction(String name, String country, double lat, double lon, String type) {
		this.name = name;
		this.country = country;
		latitude = lat;
		longitude = lon;
		attractionType = type;
	}
	
	public String getName () {
		return name;
	}
	
	public String getCountry () {
		return country;
	}

	public double getLatitude() {
		return latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public String getAttractionType () {
		return attractionType;
	}
}
