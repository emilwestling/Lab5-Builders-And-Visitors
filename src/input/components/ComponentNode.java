package input.components;

import input.visitor.ComponentNodeVisitor;

// Test
public interface ComponentNode
{
	void unparse(StringBuilder sb, int level);
	
	Object accept(ComponentNodeVisitor visitor, Object o);
}
