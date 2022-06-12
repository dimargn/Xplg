package data;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONEarthQuakeParser  implements IEarthQuakeParser{
	
	public List<EarthQuakeDailyData> parse(String data) throws JSONException
	{
		//loading json data into object
		JSONObject jsonObj = new JSONObject(data);
		
		JSONArray features= (JSONArray)jsonObj.get("features");
		SimpleDateFormat formatter = new SimpleDateFormat(
      	      "dd/MM/yyyy");
		
		
		//map between date and relevant earthquakes info
		Map<String, EarthQuakeDailyData> earthQuakesData = new LinkedHashMap<String, EarthQuakeDailyData>(); 
		
		for( int i=0; i<features.length(); i++)
		{
			JSONObject properties = (JSONObject)((JSONObject)features.get(i)).get("properties");
			Long time = (Long)properties.get("time");
			Double magnitude =  Double.valueOf(properties.get("mag").toString());
			String location = properties.get("place").toString();
			String date = formatter.format(new Date(time));
			EarthQuakeDailyData earthQuakeDailyData = earthQuakesData.get(date);
			if(earthQuakeDailyData==null)
			{
				earthQuakesData.put(date, new EarthQuakeDailyData(date, magnitude, location));
			}
	        
			else
			{
				earthQuakeDailyData.AddEarthquake(magnitude, location);
				earthQuakesData.put(date, earthQuakeDailyData);
			}
		}
		
		
		List<EarthQuakeDailyData> earthQuakesDataList = new ArrayList<EarthQuakeDailyData>(earthQuakesData.values());
		Collections.reverse(earthQuakesDataList);
		return earthQuakesDataList;
		
	}

}
