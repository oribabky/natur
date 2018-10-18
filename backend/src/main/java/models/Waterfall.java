package models;

public class Waterfall {
	private String name;
	private String country;
	private int height;
	private double latitude;
	private double longitude;
	private String imageURL;
	private String imageContrib;
	private String wikiURL;
	
	public Waterfall(String name, 
			String country, 
			int height, 
			double latitude, 
			double longitude, 
			String imageURL, 
			String imageContrib,
			String wikiURL)
	{
		this.country = country;
		this.height = height;
		this.latitude = latitude;
		this.longitude = longitude;
		this.imageURL = imageURL;
		this.imageContrib = imageContrib;
		this.wikiURL = wikiURL;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getCountry() {
		return this.country;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public double getLatitude() {
		return this.latitude;
	}
	
	public double getLongitude() {
		return this.longitude;
	}
	
	public String getImageURL() {
		return this.imageURL;
	}
	
	public String getImageContrib() {
		return this.imageContrib;
	}
	
	public String getWikiURL() {
		return this.wikiURL;
	}
}
