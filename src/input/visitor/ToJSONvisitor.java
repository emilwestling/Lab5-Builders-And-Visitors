package input.visitor;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import input.components.FigureNode;
import input.components.point.PointNode;
import input.components.point.PointNodeDatabase;
import input.components.segment.SegmentNode;
import input.components.segment.SegmentNodeDatabase;

public class ToJSONvisitor implements ComponentNodeVisitor {

	@Override
	public Object visitFigureNode(FigureNode node, Object o) {
		// TODO Auto-generated method stub
		JSONObject figure = new JSONObject();
		JSONObject geometryJSON = new JSONObject();
		
		// Add the description to the figure
		String description = node.getDescription();
		figure.put("Description", description);
		
		// Get the SegmentNodeDatabase from the FigureNode
		SegmentNodeDatabase segmentDB = node.getSegments();
		
		// Visit the SegmentNodeDatabase and get an array of segments
		JSONArray segmentsArray = (JSONArray) segmentDB.accept(this, null);
		
		// Add the segments to the figure 
		figure.put("Segments", segmentsArray);
		
		// Get the PointNodeDatabase from the FigureNode
		PointNodeDatabase pointDB = node.getPointsDatabase();
		
		// Visit the PointNodeDatabase and get an array of points 
		JSONArray pointsArray = (JSONArray) pointDB.accept(this, pointDB);
		
		// Add the points array to the figure
		figure.put("Points", pointsArray);
		
		// Add the entire figure to the geometryJSON object and return 
		geometryJSON.put("Figure", figure);
		
		
		return geometryJSON;
	}

	@Override
	public Object visitSegmentDatabaseNode(SegmentNodeDatabase node, Object o) {
		// TODO Auto-generated method stub
		
		// Array of all the segments
		JSONArray segments = new JSONArray(); 
		
		

		
		// Get a list of all segments
		char name = 'A';
		List<SegmentNode> list = node.asUniqueSegmentList();
		//System.out.println("LIST: " + list.toString());
		Iterator<SegmentNode> itr = list.iterator();
		SegmentNode next = itr.next();
		
		// Get the first point from the segment
		PointNode currSegment = next.getPoint1(); 
		
		while(itr.hasNext()) {
			// A segment
			JSONObject segment = new JSONObject();
			
			// Array of the points for a single segment
			JSONArray segArray = new JSONArray();
			
			
			PointNode keyPoint = next.getPoint1();
			
			// Loop through all of the segments that have the same first point
			while (currSegment.equals(keyPoint)) {
				
				// Visit segment 
				segArray.put(next.getPoint2().getName());
				
				//System.out.println("Point: " + currSegment.getName() + " : " + segArray);
				// Update currPoint 
				next = itr.next();
				
				// Check to see if the next segment has a different first point than the one when just visited
				// If so then put the currSegment's name in as the key and the segArray as the value to the segments object
				if (!next.getPoint1().equals(currSegment)) segment.put(currSegment.getName(), segArray);
				if (!next.getPoint1().equals(currSegment)) segArray = new JSONArray();
				
				// Get the next segment
				currSegment = next.getPoint1();
			}
			
			//put the segment we just completed into the segments array
			segments.put(segment);
			
			
			
		}
		
		
		return segments;
	}

	@Override
	public Object visitSegmentNode(SegmentNode node, Object o) {
		// TODO Auto-generated method stub
		
		// Get the name of point two from the SegmentNode 
		String point2 = node.getPoint2().getName();
		
		// Add the second point to the array
		((JSONArray) o).put(point2);
		
		return o;
	}

	@Override
	public Object visitPointNode(PointNode node, Object o) {
		// TODO Auto-generated method stub
		JSONObject point = new JSONObject(); 
		point.put("name", node.getName());
		point.put("x", node.getX());
		point.put("y", node.getY());
		//System.out.println("visiting segment: " + node.toString());
		return point;
	}
	
	

	@Override
	public Object visitPointNodeDatabase(PointNodeDatabase node, Object o) {
		// TODO Auto-generated method stub
		LinkedHashSet<PointNode> points = node.getPoints();
		JSONArray pointsArray = new JSONArray();
		Iterator<PointNode> itr = points.iterator();
		while(itr.hasNext()) {
			PointNode next = itr.next();
			JSONObject point = (JSONObject) next.accept(this, null);
			pointsArray.put(point); 
		}
		return pointsArray;
	}

}
