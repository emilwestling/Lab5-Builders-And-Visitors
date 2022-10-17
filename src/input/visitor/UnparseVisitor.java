package input.visitor;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;

import input.components.*;
import input.components.point.*;
import input.components.segment.SegmentNode;
import input.components.segment.SegmentNodeDatabase;
import utilities.io.StringUtilities;

//
// A JSON file may contain:
//
//     Figure:
//       Points
//       Segments
//
public class UnparseVisitor implements ComponentNodeVisitor
{
	@Override
	public Object visitFigureNode(FigureNode node, Object o)
	{
		// Unpack the input object containing a Stringbuilder and an indentation level
		@SuppressWarnings("unchecked")
		AbstractMap.SimpleEntry<StringBuilder, Integer> pair = (AbstractMap.SimpleEntry<StringBuilder, Integer>)(o);
		StringBuilder sb = pair.getKey();
		int level = pair.getValue();

		//build StringBuilder
		sb.append(StringUtilities.indent(level) + "{ \n");
		sb.append(StringUtilities.indent(level) + "figure: \n");
		
		//ask during lab if this is a bad way to represent, I think it makes the level change very clear
		//-------------------need to update to current visitation method (ie: vistSegmentNodeDatabase
			sb = unparseD(sb, level + 1, node);
			
			AbstractMap.SimpleEntry<StringBuilder, Integer> PNPair = 
					new AbstractMap.SimpleEntry<StringBuilder, Integer>(sb, level + 1);
			
			sb = (StringBuilder) visitPointNodeDatabase(node.getPointsDatabase(), PNPair);
			sb.append("\n");
			
			AbstractMap.SimpleEntry<StringBuilder, Integer> SNPair = 
					new AbstractMap.SimpleEntry<StringBuilder, Integer>(sb, level + 1);
			
			sb = (StringBuilder) visitSegmentDatabaseNode(node.getSegments(), SNPair);
		
		sb.append(StringUtilities.indent(level) + "}");

        return sb;
	}
	
	public StringBuilder unparseD(StringBuilder sb, int level, FigureNode node) {
		sb.append(StringUtilities.indent(level));
		sb.append(node.getDescription());
		return sb;
	}

	@Override
	public Object visitSegmentDatabaseNode(SegmentNodeDatabase node, Object o)
	{
		//unparse from segment node database
		//needs refactoring 
		@SuppressWarnings("unchecked")
		AbstractMap.SimpleEntry<StringBuilder, Integer> pair = (AbstractMap.SimpleEntry<StringBuilder, Integer>)(o);
		StringBuilder sb = pair.getKey();
		int level = pair.getValue();
		
		sb.append(StringUtilities.indent(level) + "Points \n");
		sb.append(StringUtilities.indent(level) + "{");
		for(Map.Entry<PointNode, Set<PointNode>> p: node.getAdjLists().entrySet()) {
			sb.append(StringUtilities.indent(level + 1));
			sb.append("(" + p.getKey() + ")");
			for(PointNode point: p.getValue()) {
				sb.append("(" + point + ")");
			}
			sb.append("\n");
		}
		sb.append(StringUtilities.indent(level) + "} \n");
		return sb;
	}

	/**
	 * This method should NOT be called since the segment database
	 * uses the Adjacency list representation
	 */
	@Override
	public Object visitSegmentNode(SegmentNode node, Object o)
	{
		// will not be called in this implementation
		
		return null;
	}

	@Override
	public Object visitPointNodeDatabase(PointNodeDatabase node, Object o)
	{
		//unparse from pointNodeDatabase
		//needs refactoring
		
		@SuppressWarnings("unchecked")
		AbstractMap.SimpleEntry<StringBuilder, Integer> pair = (AbstractMap.SimpleEntry<StringBuilder, Integer>)(o);
		StringBuilder sb = pair.getKey();
		int level = pair.getValue();
		
		
		sb.append(StringUtilities.indent(level) + "Points");
		sb.append("\n" + "{");
		
		for(PointNode p: node.getSet()) {
			AbstractMap.SimpleEntry<StringBuilder, Integer> newPair = new AbstractMap.SimpleEntry<StringBuilder, Integer>(sb, level);
			visitPointNode(p, (Object)newPair);
		}
		sb.append("}" + "\n");
		
		return sb;
	}
	
	@Override
	public Object visitPointNode(PointNode node, Object o)
	{
			@SuppressWarnings("unchecked")
			AbstractMap.SimpleEntry<StringBuilder, Integer> pair = (AbstractMap.SimpleEntry<StringBuilder, Integer>)(o);
			StringBuilder sb = pair.getKey();
			int level = pair.getValue();
			
			sb.append(StringUtilities.indent(level + 1));
			sb.append("Point(" + node.getName() + ")" + "(" + String.valueOf(node.getX()) + ")"
			+ "(" + String.valueOf(node.getY()) + ") \n");
			return sb;
		
	}
}