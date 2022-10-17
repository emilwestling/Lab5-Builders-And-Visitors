package input.parser;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import input.builder.DefaultBuilder;
import input.builder.GeometryBuilder;
import input.components.*;
import input.components.point.PointNode;
import input.components.point.PointNodeDatabase;
import input.components.segment.SegmentNodeDatabase;
import input.exception.ParseException;

public class JSONParser
{
	protected ComponentNode  _astRoot;
	protected DefaultBuilder _builder;

	public JSONParser()
	{
		_astRoot = null;
		_builder = new GeometryBuilder();
	}

	private void error(String message)
	{
		throw new ParseException("Parse error: " + message);
	}
	
	/**
	 * 
	 * @param str
	 * @return FigureNode
	 * @throws ParseException
	 */
	public ComponentNode parse(String str) throws ParseException
	{
		try {
			// Parsing is accomplished via the JSONTokenizer class.
			JSONTokener tokenizer = new JSONTokener(str);
			JSONObject  JSONroot = (JSONObject)tokenizer.nextValue();
			JSONObject object = (JSONObject) JSONroot.get("Figure");
			
			// Set the Description by getting it from the JSON file			
			String description = object.getString("Description");
		
			
			// Construct the PointNodeDatabase from the "Points" values in the JSON file
			PointNodeDatabase pointDB = parsePoints(object);
			
			// Construct the PointNodeDatabase from the "Points" values in the JSON file
			SegmentNodeDatabase segmentDB = parseSegments(object, pointDB);
			
			// Construct the figure by creating a GeometryBuilder object and creating a FigureNode object
			_astRoot = new GeometryBuilder().buildFigureNode(description, pointDB, segmentDB);
		} catch (JSONException je) {
			this.error("JSON file empty");
		}
		return _astRoot;
	}
	
	/**
	 * parses the points one by one and 
	 * @param obj -- JSONObject
	 * @return PointNodeDatabase
	 */
	private PointNodeDatabase parsePoints(JSONObject obj) {
		JSONArray points = obj.getJSONArray("Points");
		List<PointNode> list = new ArrayList<PointNode>();
		
		// loop through the points and parse them out one by one
		for(Object o : points) {
			String name = ((JSONObject) o).getString("name");
			Double x = ((JSONObject) o).getDouble("x");
			Double y = ((JSONObject) o).getDouble("y");
			list.add(_builder.buildPointNode(name, x, y));
		}
		return _builder.buildPointNodeDatabase(list);
	}
	
	/**
	 * parses all segments and creates a segment
	 * 
	 * @param obj -- JSONObject
	 * @param pointDB -- PointNodeDatabase
	 * @return segmentDB -- SegmentNodeDatabase
	 */
	private SegmentNodeDatabase parseSegments(JSONObject obj, PointNodeDatabase pointDB) {
		// Get the array of segments that is assigned to the "Segments" key in JSON
		JSONArray segmentsArray = obj.getJSONArray("Segments");
		// Create an empty SegmentNodeDatabase
		SegmentNodeDatabase segmentDB = _builder.buildSegmentNodeDatabase();
		
		// Loop through the segments in the array
		for(Object o : segmentsArray) {
			// For each segment in the array extract the key
			Iterator<String> keyArray = ((JSONObject) o).keys();
			String key = keyArray.next();
			PointNode keyPoint = pointDB.getPoint(key);
			
			// Get the array of values assigned to that key
			JSONArray points = ((JSONObject)o).getJSONArray(key);
			// Loop through that array and get each value 
			for(Object str : points) {
				// Get the point corresponding to that value from the PointNodeDatabase 
				String pointStr = str.toString();
				PointNode point = pointDB.getPoint(pointStr);
				// Add that point and the key to a the SegmentNodeDatabase
				_builder.addSegmentToDatabase(segmentDB, point, keyPoint);
			}
		}
		return segmentDB;
	}
}

