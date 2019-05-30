package treelover28.SpatialTree;
/*
 * Khai Lai
 * COMP 2673 
 * Assignment 5- Spatial Tree
 * Professor Mohammed Albow 
 * T.A Dalton Crutchfield and T.A Lombe Chileshe
 */
import java.awt.geom.Point2D;
import java.text.DecimalFormat;


public class SpatialTreeNode 
{
	private SpatialTreeNode parent;
	private SpatialTreeNode left;
	private SpatialTreeNode right;
	private Point2D point;
	private boolean xNode;
	
	public SpatialTreeNode(SpatialTreeNode parent, Point2D point, boolean xNode)
	{
		this.setParent(parent);
		this.point = point;
		this.setxNode(xNode);
		this.setLeft(null);
		this.setRight(null);
		
	}
	
	public String toString()
	{
		DecimalFormat df = new DecimalFormat("#.##");
		// only print coordinates to 2 decimal places
		return "(" + df.format(this.point.getX()) + "," + df.format(this.point.getY()) + ")";
	}
	
	public boolean isLeaf()
	{
		return this.left == null && this.right == null;
	}
	
	public boolean equals(Point2D point) // check if node's value equals point in argument
	{
		if (point != null && this.point != null)
		{
			return this.point.getX() == point.getX() && this.point.getY() == point.getY();
		}
		return false;
	}
	
	/*
	 * Generic getters and setters to access private variables
	 */

	public SpatialTreeNode getParent() 
	{
		return parent;
	}

	public void setParent(SpatialTreeNode parent) 
	{
		this.parent = parent;
	}

	public SpatialTreeNode getLeft() 
	{
		return left;
	}

	public void setLeft(SpatialTreeNode left) 
	{
		this.left = left;
	}

	public SpatialTreeNode getRight() 
	{
		return right;
	}

	public void setRight(SpatialTreeNode right) 
	{
		this.right = right;
	}

	public boolean isX() 
	{
		return xNode;
	}

	public void setxNode(boolean xNode) 
	{
		this.xNode = xNode;
	}
	
	public Point2D point()
	{
		return this.point;
	}
	
}
