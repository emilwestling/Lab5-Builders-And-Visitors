package input.components;

import input.visitor.ComponentNodeVisitor;

// Test
public interface ComponentNode
{
	Object unparse(StringBuilder sb, int level);
	
	Object accept(ComponentNodeVisitor visitor, Object o);
}
