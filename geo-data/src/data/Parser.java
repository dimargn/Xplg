package data;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Parser {
	
	public void ParseGeoJson(String json, String file) throws JSONException, FileNotFoundException, UnsupportedEncodingException
	{
		//loading json data into object
		JSONObject obj = new JSONObject(json);
		
		JSONArray features= (JSONArray)obj.get("features");
		SimpleDateFormat formatter = new SimpleDateFormat(
      	      "dd/MM/yyyy");
		
		
		//map between date and relevant earthquakes info
		Map<String, DailyData> data = new LinkedHashMap<String, DailyData>(); 
		
		for( int i=0; i<features.length(); i++)
		{
			JSONObject properties = (JSONObject)((JSONObject)features.get(i)).get("properties");
			Long time = (Long)properties.get("time");
			Double mag =  Double.valueOf(properties.get("mag").toString());
			String place = properties.get("place").toString();
			String date = formatter.format(new Date(time));
	        DailyData dailyData = data.get(date);
	        if(dailyData==null)
	        {
	        	data.put(date, new DailyData(mag, mag, place));
	        }
	        else
	        {
	        	
	        	if(dailyData.max<mag)
	        	{
	        		//updating maximum magnitude of that day;
	        		dailyData.max = mag;
	        		dailyData.maxLocation = place;
	        	}
	        	
	        	//updating the average magnitude of that day
	        	dailyData.length++;
	        	dailyData.average = dailyData.average + (mag-dailyData.average)/dailyData.length;
	        }			
		}
		
		PrintWriter writer = new PrintWriter(file, "UTF-8");
        ArrayList<String> keys = new ArrayList<String>(data.keySet());
        //printing the relevant data
		for (int i=data.size()-1; i>=0; i--) 
		{
			String date = keys.get(i);
			DailyData dailyData = data.get(date);
			
			String line = String.format("%s %.2f,location: %s", date, dailyData.average, dailyData.maxLocation);
			System.out.println(line);
			writer.println(line);
		}
		writer.close();

	}

}
