/**
 * A database containing PointNode objects.
 * @author Sophie Ngo
 */

package input.components.point;

import java.util.Iterator;

import utilities.io.StringUtilities;
import utilities.math.MathUtilities;
import java.util.LinkedHashSet;
import java.util.List;

public class PointNodeDatabase {
	
	/**
	 * The set that underlies the database.
	 */
	protected LinkedHashSet<PointNode> _set;
	
	/**
	 * Creates an empty PointNodeDatabase.
	 */
	public PointNodeDatabase() {
		_set = new LinkedHashSet<PointNode>();
		
	}
	
	/**
	 * Creates a PointNodeDatabase that contains all elements contained in the given list, in order.
	 * @param list - the list to initialize the database with
	 */
	public PointNodeDatabase(List<PointNode> list) {
		_set = new LinkedHashSet<PointNode>(list);
	}
	
	/**
	 * Adds the given PointNode to the end of this database.
	 * @param point - the point to add
	 */
	public void put(PointNode point) {
		_set.add(point); 
	}
	
	/**
	 * Checks whether the given point is an element of this database.
	 * @param point
	 * @return
	 */
	public boolean contains(PointNode point) {
		return _set.contains(point);
	}
	
	/**
	 * Checks whether the database contains a PointNode that has the indicated x and y values.
	 * @param x, the value of x that should match with the point to search for
	 * @param y, the value of y that should match with the point to search for
	 * @return whether a PointNode exists in the database that matches the given x and y
	 */
	public boolean contains(double x, double y) {
		Iterator<PointNode> itr = _set.iterator(); 
		while (itr.hasNext()) {
			
			PointNode next = (PointNode)itr.next();
			
			if (next.getX() == x && next.getY() == y) {
				return true;
			}
		}	
		return false;
	}
	
	/**
	 * Gets the name of a given point in the database.
	 * @param point - the point to get the name of 
	 * @return the name of the point
	 */
	public String getName(PointNode point) {
		return point.getName();
	}
	
	/**
	 * Gets the name of a point in the database with the given x and y values. 
	 * 
	 * @param x, the value of x that should match with the point to search for
	 * @param y, the value of y that should match with the point to search for
	 * @return the name of the point, if contained in the database. null if it is not contained
	 */
	public String getName(double x, double y) {
		Iterator<PointNode> itr = _set.iterator(); 
		while (itr.hasNext()) {
			
			PointNode next = (PointNode)itr.next();
			
			if (next.getX() == x && next.getY() == y) {
				return next.getName();
			}
		}	
		return null;
	}
	
	/**
	 * A method for getting the point based on the name
	 * @param point - a string name of a given point
	 * @return PointNode
	 */
	public PointNode getPoint(String point) {
		Iterator<PointNode> itr = _set.iterator(); 
		while (itr.hasNext()) {
			
			PointNode next = (PointNode)itr.next();
			
			if (next.getName().equals(point)) {
				return next;
			}
		}	
		return null;
	}
	
	/**
	 * Gets the given point.
	 * @param point - the point to get
	 * @return the point
	 */
	public PointNode getPoint(PointNode point) {
		return getPoint(point.getX(), point.getY()); 
	}
	
	/**
	 * Gets the point with the given x and y values.
	 * @param x, the value of x that should match with the point to search for
	 * @param y, the value of y that should match with the point to search for
	 * @return the point
	 */
	public PointNode getPoint(double x, double y) {
		Iterator<PointNode> itr = _set.iterator(); 
		while (itr.hasNext()) {
			
			PointNode next = (PointNode)itr.next();
			
			if (next.getX() == x && next.getY() == y) {
				return next;
			}
		}	
		return null;
	}
	
public StringBuilder unparse( StringBuilder sb, int level) {
		
		sb.append(StringUtilities.indent(level) + "Points");
		sb.append("\n" + "{");
		for(PointNode p: _set) {
			sb.append(StringUtilities.indent(level + 1));
			sb.append("Point(" + p.getName() + ")" + "(" + String.valueOf(p.getX()) + ")"
			+ "(" + String.valueOf(p.getY()) + ") \n");
		}
		sb.append("}" + "\n");
		return sb;
	}
}