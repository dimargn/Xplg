package data;

public class DailyData {
	
	public DailyData(double average, double max, String maxLocation)
	{
		this.average = average;
		this.max = max;
		this.length = 1;		
		this.maxLocation = maxLocation;
	}
	
	public double average;
	
	public int length;
	
	public double max;
	
	public String maxLocation;
	
	
}
