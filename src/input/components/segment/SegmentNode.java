package input.components.segment;

import input.components.ComponentNode;
import input.components.point.PointNode;
import input.visitor.ComponentNodeVisitor;

/**
 * A utility class only for representing ONE segment
 */
public class SegmentNode implements ComponentNode
{
	protected PointNode _point1;
	protected PointNode _point2;
	
	public PointNode getPoint1() { return _point1; }
	public PointNode getPoint2() { return _point2; }
	
	/**
	 * constructor
	 * @param pt1 The first PointNode of the segment
	 * @param pt2 The second PointNode of the segment
	 */
	public SegmentNode(PointNode pt1, PointNode pt2)
	{
		_point1 = pt1;
		_point2 = pt2;
	}

	/**
	 * Compares two SegmentNodes to see if they share the same points 
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null) return false;
		if (!(obj instanceof SegmentNode)) return false;
		
		SegmentNode that = (SegmentNode)obj;
		
		return (_point1.equals(that.getPoint1()) && _point2.equals(that.getPoint2())) || 
			   (_point1.equals(that.getPoint2()) && _point2.equals(that.getPoint1())); 
		
		
		
	}

	@Override
	public Object accept(ComponentNodeVisitor visitor, Object o) {
		// TODO Auto-generated method stub
		return visitor.visitSegmentNode(this, o);
	}
	@Override
	public Object unparse(StringBuilder sb, int level) {
		// TODO Auto-generated method stub
		return null;
	}
}