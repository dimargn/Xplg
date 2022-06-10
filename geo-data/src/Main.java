import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import data.DailyData;
import data.Parser;

public class Main {

	public static void main(String[] args)  {
		//Test();
		
		Parser geoJsonParser = new Parser();
		try {
			geoJsonParser.ParseGeoJson(GetData.getData(), "C:\\test.txt");
		} catch (FileNotFoundException e) {
			System.out.println("Could not print to file");
		} catch (UnsupportedEncodingException e) {
			System.out.println("Unsopported encoding UTF-8");
		} catch (JSONException e) {
			System.out.println("Could not parse Json data");
		} catch (IOException e) {
			System.out.println("Could not print to file");
		} catch (URISyntaxException e) {
			System.out.println("Wrong URI of geo Json");
		}
	}
	
	
	public static void Test() throws JSONException, IOException, URISyntaxException, ParseException
	{
		JSONObject obj = new JSONObject(GetData.getData());
		
		JSONArray features= (JSONArray)obj.get("features");
		SimpleDateFormat formatter = new SimpleDateFormat(
      	      "dd/MM/yyyy");
		
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
		
        ArrayList<String> keys = new ArrayList<String>(data.keySet());
		for (int i=data.size()-1; i>=0; i--) 
		{
			String date = keys.get(i);
			DailyData dailyData = data.get(date);
			
			System.out.println(String.format("%s %.2f,location: %s", date, dailyData.average, dailyData.maxLocation));
			//System.out.println(date + " " + dailyData.average + ", location: "  + dailyData.maxLocation);
		}
	}
	
/*
	public static void Test1() throws JSONException, IOException, URISyntaxException, ParseException
	{
		JSONObject jsonObject  = new JSONObject(GetData.getData());
		
		JSONArray features= (JSONArray)jsonObject.getJSONArray("features");
		

		features.stream().
		SimpleDateFormat formatter = new SimpleDateFormat(
      	      "dd/MM/yyyy");
		
		features.stream();
		
		Map<String, DailyData> data = new LinkedHashMap<String, DailyData>(); 
	}*/

}
