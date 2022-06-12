
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.List;

import org.json.JSONException;

import data.EarthQuakeDailyData;
import data.JSONEarthQuakeParser;




public class Main {

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException, JSONException, IOException, URISyntaxException  {
		
		JSONEarthQuakeParser jsonEarthQuakeParser = new JSONEarthQuakeParser();
		List<EarthQuakeDailyData> weeklyEarthquakes = jsonEarthQuakeParser.parse(GetData.getData());
		PrintWriter writer = new PrintWriter("C:\\test.txt", "UTF-8");
		for(EarthQuakeDailyData earthquake : weeklyEarthquakes )
		{
			String line = String.format("%s %.2f,location: %s", earthquake.getDate(), earthquake.getAverageMagnitude(), earthquake.getLocationOfMaxMagnitude());
			System.out.println(line);
			writer.println(line);
		}
		writer.close();
	}

}
