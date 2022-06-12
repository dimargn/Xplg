package data;

public class EarthQuakeDailyData {
	
	public EarthQuakeDailyData(String date, double magnitude, String location)
	{
		this.averageMagnitude = magnitude;
		this.maxMagnitude = magnitude;
		this.count = 1;		
		this.locationOfMaxMagnitude = location;
		this.date = date;
	}
	
	private double averageMagnitude;
	
	private int count;
	
	private double maxMagnitude;
	
	private String locationOfMaxMagnitude;
	
	private String date;
	
	
	public double getMaxMagnitude() {
		return maxMagnitude;
	}
	
	
	public String getDate() {
		return date;
	}
	
	public double getAverageMagnitude() {
		return averageMagnitude;
	}
	
	public String getLocationOfMaxMagnitude() {
		return locationOfMaxMagnitude;
	}
	
	
	public void AddEarthquake(double magnitude, String location)
	{
		if(this.maxMagnitude<magnitude)
        {
        	//updating maximum magnitude of that day;
        	this.maxMagnitude = magnitude;
        	this.locationOfMaxMagnitude = location;
        }
		this.count++;
		this.averageMagnitude = this.averageMagnitude + (magnitude-this.averageMagnitude)/this.count;
	}

	
}
