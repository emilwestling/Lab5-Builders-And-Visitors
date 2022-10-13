package input.builder;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import input.components.*;
import input.components.point.*;
import input.components.segment.SegmentNode;
import input.components.segment.SegmentNodeDatabase;

/*
 * A Builder design pattern for constructing a geometry hierarchy.
 * 
 * The default builder allows JSON file parsing without constructing
 * the corresponding hierarchy.
 */
public class DefaultBuilder
{
	public DefaultBuilder() { }

    public FigureNode buildFigureNode(String description,
    		                          PointNodeDatabase points,
    		                          SegmentNodeDatabase segments)
    {
        return null;
    }
    
    public SegmentNodeDatabase buildSegmentNodeDatabase(LinkedHashMap<PointNode, Set<PointNode>> map)
    {
        return null;
    }
    
    public void addSegmentToDatabase(SegmentNodeDatabase segments, PointNode from, PointNode to)
    {
    	if (segments != null) segments.addUndirectedEdge(from, to);
    }
    
    public SegmentNode buildSegmentNode(PointNode pt1, PointNode pt2)
    {
        return null;
    }
    
    public PointNodeDatabase buildPointDatabaseNode(List<PointNode> points)
    {
        return null;
    }
    
    public PointNode buildPointNode(String name, double x, double y)
    {
        return null;
    }
}
