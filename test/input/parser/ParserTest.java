package input.parser;
import java.beans.Transient;
import java.util.ArrayList;
​
import static org.junit.jupiter.api.Assertions.*;
​
import org.junit.jupiter.api.Test;
​
import input.components.FigureNode;
import input.components.point.PointNode;
import input.components.point.PointNodeDatabase;
import input.components.segment.SegmentNode;
import input.components.segment.SegmentNodeDatabase;
import input.parser.JSONParser;
​
class ParserTest {
    /**
     * checks that all parsed data matches the data in the file collinear_line_segments.JSON
     */
    @Test
    void test_collinear_line_segments_Parse() {
        JSONParser parser = new JSONParser();
        FigureNode figNode = (FigureNode) parser.parse("/Users/milesdame/eclipse-workspace/CSC-223-Lab4/collinear_line_segments.json");
        PointNodeDatabase pointDB = figNode.getPointsDatabase();
        SegmentNodeDatabase segmentDB = figNode.getSegments();
        String description = figNode.getDescription();
​
        // checking so that the description matches
        assertEquals(description, "A seqeunce of collinear line segments mimicking one line with 6 points.");
        // checking so that all the points are in pointDB
        assertTrue(pointDB.contains(new PointNode("A", 0,0)));
        assertTrue(pointDB.contains(new PointNode("B", 4, 0)));
        assertTrue(pointDB.contains(new PointNode("C", 9, 0)));
        assertTrue(pointDB.contains(new PointNode("D", 11, 0)));
        assertTrue(pointDB.contains(new PointNode("E", 16, 0)));
        assertTrue(pointDB.contains(new PointNode("F", 26, 0)));
​
        // checking all segments
        ArrayList<SegmentNode> segmentList = (ArrayList<SegmentNode>) segmentDB.asUniqueSegmentList();
        assertTrue(segmentList.contains(new SegmentNode(new PointNode("A", 0, 0), new PointNode("B", 4, 0))));
        assertTrue(segmentList.contains(new SegmentNode(new PointNode("B", 4, 0), new PointNode("C", 9, 0))));
        assertTrue(segmentList.contains(new SegmentNode(new PointNode("C", 9, 0), new PointNode("D", 11, 0))));
        assertTrue(segmentList.contains(new SegmentNode(new PointNode("E", 16, 0), new PointNode("D", 11, 0))));
        assertTrue(segmentList.contains(new SegmentNode(new PointNode("E", 16, 0), new PointNode("F", 26, 0))));
    }
​
    /**
     * checks that all data was parsed accordingly from the file 
     */
    @Test
    void test_fully_connected_irregular_polygon() {
        JSONParser parser = new JSONParser();
        FigureNode figNode = (FigureNode) parser.parse("/Users/milesdame/eclipse-workspace/CSC-223-Lab4/fully_connected_irregular_polygon.json");
        PointNodeDatabase pointDB = figNode.getPointsDatabase();
        SegmentNodeDatabase segmentDB = figNode.getSegments();
        String description = figNode.getDescription();
​
        // stores all the points
        PointNode a = new PointNode("A", 0,0);
        PointNode b = new PointNode("B", 4, 0);
        PointNode c = new PointNode("C", 6, 3);
        PointNode d = new PointNode("D", 3, 7);
        PointNode e = new PointNode("E", -2, 4);
        PointNode f = new PointNode("F", 26, 0);
​
​
        // check so that the description matches
        assertEquals(description, "Irregular pentagon in which each vertex is connected to each other.");
​
        // check so that all the points are in pointDB
        assertTrue(pointDB.contains(a));
        assertTrue(pointDB.contains(b));
        assertTrue(pointDB.contains(c));
        assertTrue(pointDB.contains(d));
        assertTrue(pointDB.contains(e));
        assertTrue(pointDB.contains(f));
​
        // checking all segments
        ArrayList<SegmentNode> segmentList = (ArrayList<SegmentNode>) segmentDB.asUniqueSegmentList();
        assertTrue(segmentList.contains(a, b));
        assertTrue(segmentList.contains(a, c));
        assertTrue(segmentList.contains(a,d));
        assertTrue(segmentList.contains(a,e));
        assertTrue(segmentList.contains(b, c));
        assertTrue(segmentList.contains(b,d));
        assertTrue(segmentList.contains(b,e));
        assertTrue(segmentList.contains(c, d));
        assertTrue(segmentList.contains(c, e));
        assertTrue(segmentList.contains(d, e));
    }
}