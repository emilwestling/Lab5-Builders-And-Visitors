package input.visitor;

import input.components.FigureNode;
import input.components.point.PointNode;
import input.components.point.PointNodeDatabase;
import input.components.segment.SegmentNode;
import input.components.segment.SegmentNodeDatabase;

public class ToJSONvisitor implements ComponentNodeVisitor {

	@Override
	public Object visitFigureNode(FigureNode node, Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitSegmentDatabaseNode(SegmentNodeDatabase node, Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitSegmentNode(SegmentNode node, Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitPointNode(PointNode node, Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitPointNodeDatabase(PointNodeDatabase node, Object o) {
		// TODO Auto-generated method stub
		return null;
	}

}
