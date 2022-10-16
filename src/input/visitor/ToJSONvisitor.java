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
		
		
		
		
		PointNodeDatabase pointDB = ((FigureNode) o).getPointsDatabase();
		JSONArray pointsArray = (JSONArray) visitPointNodeDatabase(pointDB, figure);
		figure.put("Points", pointsArray);
		
		figure.put("Description", node.getDescription());
		
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
				
				
				
				// Put point two from the currentSegment into the array
				segArray.put(next.getPoint2().getName());
				
				// Update currPoint 
				next = itr.next();
				
				// Add the array and the first point to the segment JSONObject as a key value pair
				// only if the next segment has a different first point
				if (!next.getPoint1().equals(currSegment)) segment.put(currSegment.getName(), segArray);
				
			}
			
			//put the segment into the segments array
			segments.put(segment);
			
			// Get the next segment
			currSegment = next.getPoint1();
			
		}
		
		
		return null;
	}

	@Override
	public Object visitSegmentNode(SegmentNode node, Object o) {
		// TODO Auto-generated method stub
		
		
		JSONObject segment = new JSONObject(); 
		
		// Get the point names from the SegmentNode 
		String point1 = node.getPoint1().getName();
		String point2 = node.getPoint2().getName();
		// Add the second point to the array
		
		// Put both the first point and the array into the segment Object
		
		return segment;
	}

	@Override
	public Object visitPointNode(PointNode node, Object o) {
		// TODO Auto-generated method stub
		JSONObject point = new JSONObject(); 
		point.put("name", node.getName());
		point.put("x", node.getX());
		point.put("y", node.getY());
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
			JSONObject point = (JSONObject) visitPointNode(next, null);
			pointsArray.put(point);
		}
		return pointsArray;
	}

}
