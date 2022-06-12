package data;

import java.util.List;

import org.json.JSONException;

public interface IEarthQuakeParser {
	public List<EarthQuakeDailyData>  parse(String data) throws JSONException;

}
