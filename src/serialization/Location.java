package serialization;

public class Location {

	// As the values contains -ve and decimals we are using return type as double here
	private double lat;
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	private double lng;

	
}
