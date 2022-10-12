package input.parser;
// Comment

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import utilities.io.FileUtilities;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import input.components.ComponentNode;
import input.components.FigureNode;
import input.components.point.PointNode;
import input.components.point.PointNodeDatabase;
import input.components.segment.SegmentNode;
import input.components.segment.SegmentNodeDatabase;
import input.exception.ParseException;

class JSONParserTest
{
	public static ComponentNode runFigureParseTest(String filename)
	{
		JSONParser parser = new JSONParser();

		String figureStr = utilities.io.FileUtilities.readFileFilterComments(filename);
		
		return parser.parse(figureStr);
	}
	
	@Test
	void empty_json_string_test()
	{
		JSONParser parser = new JSONParser();

		assertThrows(ParseException.class, () -> { parser.parse("{}"); });
	}

	@Test
	void single_triangle_test()
	{
		ComponentNode node = JSONParserTest.runFigureParseTest("single_triangle.json");

		assertTrue(node instanceof FigureNode);
		
		StringBuilder sb = new StringBuilder();
		node.unparse(sb, 0);
		System.out.println(sb.toString());
	}
	
	@Test
    void test_collinear_line_segments_Parse() {
        JSONParser parser = new JSONParser();
        String file = FileUtilities.readFileFilterComments("collinear_line_segments.json");
        FigureNode figNode = (FigureNode) parser.parse(file);
        PointNodeDatabase pointDB = figNode.getPointsDatabase();
        SegmentNodeDatabase segmentDB = figNode.getSegments();
        String description = figNode.getDescription();

        // checking so that the description matches
        assertEquals(description, "A seqeunce of collinear line segments mimicking one line with 6 points.");
        // checking so that all the points are in pointDB
        assertTrue(pointDB.contains(new PointNode("A", 0,0)));
        assertTrue(pointDB.contains(new PointNode("B", 4, 0)));
        assertTrue(pointDB.contains(new PointNode("C", 9, 0)));
        assertTrue(pointDB.contains(new PointNode("D", 11, 0)));
        assertTrue(pointDB.contains(new PointNode("E", 16, 0)));
        assertTrue(pointDB.contains(new PointNode("F", 26, 0)));

        // checking all segments
        ArrayList<SegmentNode> segmentList = (ArrayList<SegmentNode>) segmentDB.asUniqueSegmentList();
        assertTrue(segmentList.contains(new SegmentNode(new PointNode("A", 0, 0), new PointNode("B", 4, 0))));
        assertTrue(segmentList.contains(new SegmentNode(new PointNode("B", 4, 0), new PointNode("C", 9, 0))));
        assertTrue(segmentList.contains(new SegmentNode(new PointNode("C", 9, 0), new PointNode("D", 11, 0))));
        assertTrue(segmentList.contains(new SegmentNode(new PointNode("E", 16, 0), new PointNode("D", 11, 0))));
        assertTrue(segmentList.contains(new SegmentNode(new PointNode("E", 16, 0), new PointNode("F", 26, 0))));
    }
	
	 @Test
	    void test_fully_connected_irregular_polygon() {
	        JSONParser parser = new JSONParser();
	        FigureNode figNode = (FigureNode) parser.parse(FileUtilities.readFileFilterComments("fully_connected_irregular_polygon.json"));
	        PointNodeDatabase pointDB = figNode.getPointsDatabase();
	        SegmentNodeDatabase segmentDB = figNode.getSegments();
	        String description = figNode.getDescription();

	        // stores all the points
	        PointNode a = new PointNode("A", 0,0);
	        PointNode b = new PointNode("B", 4, 0);
	        PointNode c = new PointNode("C", 6, 3);
	        PointNode d = new PointNode("D", 3, 7);
	        PointNode e = new PointNode("E", -2, 4);
	        PointNode f = new PointNode("F", 26, 0);
	        // check so that the description matches
	        assertEquals(description, "Irregular pentagon in which each vertex is connected to each other.");

	        // check so that all the points are in pointDB
	        assertTrue(pointDB.contains(a));
	        assertTrue(pointDB.contains(b));
	        assertTrue(pointDB.contains(c));
	        assertTrue(pointDB.contains(d));
	        assertTrue(pointDB.contains(e));
	        assertTrue(pointDB.contains(f));

	        // checking all segments
	        ArrayList<SegmentNode> segmentList = (ArrayList<SegmentNode>) segmentDB.asUniqueSegmentList();
	        assertTrue(segmentList.contains(new SegmentNode(a, b)));
	        assertTrue(segmentList.contains(new SegmentNode(a, c)));
	        assertTrue(segmentList.contains(new SegmentNode(a,d)));
	        assertTrue(segmentList.contains(new SegmentNode(a,e)));
	        assertTrue(segmentList.contains(new SegmentNode(b, c)));
	        assertTrue(segmentList.contains(new SegmentNode(b,d)));
	        assertTrue(segmentList.contains(new SegmentNode(b,e)));
	        assertTrue(segmentList.contains(new SegmentNode(c, d)));
	        assertTrue(segmentList.contains(new SegmentNode(c, e)));
	        assertTrue(segmentList.contains(new SegmentNode(d, e)));
	    }
	
	
}
