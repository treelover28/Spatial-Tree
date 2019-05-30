package treelover28.SpatialTree;
/*
 * Khai Lai
 * COMP 2673 
 * Assignment 5- Spatial Tree
 * Professor Mohammed Albow 
 * T.A Dalton Crutchfield and T.A Lombe Chileshe
 */
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import edu.princeton.cs.introcs.StdDraw;

public class SpatialTree
{
	private SpatialTreeNode root;
	private int size;
	private boolean queryActive = false;
	private SearchParameters param;
	private ArrayList<Point2D> locationSet = new ArrayList<>();
	static int maxScale = 800;
	
	/*
	 * Constructor that creates an empty tree
	 */
	public SpatialTree()
	{
		this.root = null;
		this.size = 0;
	}
	
	/*
	 * Adds a point to the tree
	 */
	public void add(Point2D point)
	{
		// check whether point already exist.
		// else n is parent of the point we would like to add
		SpatialTreeNode n = findNode(point,root);
		
		if (n == null) // when tree is still empty, we add first point
		{
			this.root = new SpatialTreeNode(null, point, true); 
			// I want the root to be an x-node.
			// Root has no parent
			this.size++;
			return;
		}
		
		// if node already exist
		if (n.equals(point)) // uses the overridden equals() method in SpatialTreeNode class
		{
			// if point already exist, don't add
			// does nothing
			return;
		}
		
		// if n is parent of new point
		if (n.isX()) // if n is an X-node, we compare X-value and add accordingly
		{
			if (point.getX() < n.point().getX())
			{
				n.setLeft(new SpatialTreeNode(n,point,false));
				this.size++;	
				// children of X-node are Y-nodes
			}
			
			if (point.getX() >= n.point().getX())
			{
				n.setRight(new SpatialTreeNode(n,point,false));
				this.size++;	
			}
		}
		
		if (!n.isX()) // if n is a y-node, we compare y-value and add accordingly
		{
			if (point.getY() < n.point().getY())
			{
				n.setLeft(new SpatialTreeNode(n,point,true));
				this.size++;	
				
				// children of Y-node are X-nodes
			}
			if (point.getY() >= n.point().getY())
			{
				n.setRight(new SpatialTreeNode(n,point,true));
				this.size++;	
			}
		}
		
	}
	
	/* 
	 * Adds a point to tree but takes in two double argument instead
	 * Helps speed up coding process so coder don't have to create a
	 * Point2D object every time!
	 */
	public void add(double x, double y)
	{
		add(new Point2D.Double(x,y));
	}
	
	/*
	 * Private method to help print the tree using preorder traversal
	 */
	private String toStringRecursive(SpatialTreeNode r, int level, StringBuilder sb)
	{
		for(int i=0; i < level; i++)
		{
			sb.append("  ");
		}
		
		if (r.isX())
		{
			sb.append("|(X) " + r.toString() + "\n");
		}
		else
		{
			sb.append("|(Y) " + r.toString() + "\n");
		}
		
		SpatialTreeNode leftChild = r.getLeft();
		SpatialTreeNode rightChild = r.getRight();
		
		if(leftChild != null)
		{
			toStringRecursive(leftChild, level+1, sb);
		}
		if(rightChild != null)
		{
			toStringRecursive(rightChild, level+1, sb);
		}
		return sb.toString();
	}
	
	/*
	 * Overridden toString() method to print the spatial tree
	 */
	public String toString()
	{
		return toStringRecursive(root, 0, new StringBuilder());
	}
	
	/**
	 * @param p 2DPoint to search for 
	 * @param r root of the subtree
	 * @return either node containing value or node that would be parent 
	 */
	private SpatialTreeNode findNode(Point2D p, SpatialTreeNode r)
	{
		//Can only be true if the entire tree is empty
		if(r == null)
		{
			return null;
		}

		//The value is at the root or it's a leaf
		if ((r.point().getX() == p.getX() && r.point().getY() == p.getY()) || r.isLeaf())
		{
			return r;
		}

		SpatialTreeNode leftChild = r.getLeft();
		SpatialTreeNode rightChild = r.getRight();
		
		if (r.isX()) // children of x-node gets compare by x-values
		{
			
			if (leftChild != null) // only check left subtree of leftChild exists
			{
				if (p.getX() < r.point().getX()) // if x-coordinate of p < than current node's x-coordinate
				{
					return findNode(p, leftChild); // recursively search in left subtree
				}
			}
			
			if (rightChild != null) // only check right subtree of rightChild exist
			{
				if (p.getX() >= r.point().getX()) // if x-coordinate of p >= current node's x-coordinate
				{
					return findNode(p, rightChild); // recursively search in right subtree
				}
			}
		}
		
		if (!r.isX()) // children of y-node gets compare by y-values
		{
			if (leftChild != null) 
			{
				if (p.getY() < r.point().getY()) 
					// recursively search in left subtree if p's y-coordinate < current node's y-values
				{
					return findNode(p, leftChild);
				}
			}
			if (rightChild != null)
			{
				if (p.getY() >= r.point().getY()) 
					// recursively search in right subtree if p's y-coordinate >= current node's y-values
				{
					return findNode(p, rightChild);
				}
			}
		}
		return r;
	}
	
	/*
	 * Return size of tree
	 */
	public int size()
	{
		return size;
	}
	
	/*
	 * Draw out the map of points using StdDraw
	 */
	
	public void draw()
	{
		StdDraw.setCanvasSize(maxScale,maxScale);
		
		StdDraw.setXscale(0,maxScale);
		StdDraw.setYscale(0,maxScale);
		Rectangle2D bound = new Rectangle2D.Double(0, 0, maxScale, maxScale);
		draw(root,bound);
	}
	
	private void draw(SpatialTreeNode n, Rectangle2D boundary)
	{
		if (n == root)
		{
			StdDraw.setPenColor(StdDraw.RED);
			double x = n.point().getX();
			double y =  n.point().getY();
			StdDraw.filledCircle(x, y, maxScale/200);
//			StdDraw.text(x, y, n.toString());
			
			// Draw first vertical X-line
			StdDraw.line(x, 0, x, maxScale);
			
			
			SpatialTreeNode leftChild = n.getLeft();
			SpatialTreeNode rightChild = n.getRight();
			
			if (leftChild != null) // recursively draw left children
			{
				// left bound of point
				Rectangle2D bound = new Rectangle2D.Double(0,0, x ,maxScale);
				draw(leftChild, bound);
			}
			
			if (rightChild != null)
			{
				// right bound of point
				Rectangle2D bound = new Rectangle2D.Double(x, 0, (maxScale - x), maxScale);
				draw(rightChild, bound);
			}
		}
		else if (n != null) // code block to recursively draw children
		{
			if (n.isX()) // draw boundaries of X-node
			{
				// draw dot first
				StdDraw.setPenColor(StdDraw.RED);
				double x = n.point().getX();
				double y =  n.point().getY();
				StdDraw.filledCircle(x, y, maxScale/200);
//				StdDraw.text(x, y, n.toString());
				StdDraw.line(x, boundary.getMinY(), x, boundary.getMaxY());
				
				SpatialTreeNode leftChild = n.getLeft();
				SpatialTreeNode rightChild = n.getRight();
				if (leftChild != null) // bound of x-line on the LEFT side
				{
					double botLeftX = boundary.getMinX();
					double botLeftY = boundary.getMinY();
					double width = (x - boundary.getMinX());
					double height = (boundary.getMaxY() - boundary.getMinY()); // the height is restricted by previous bound's height
					
					Rectangle2D childBound = new Rectangle2D.Double(botLeftX, botLeftY, width, height);
					draw(leftChild, childBound);
				}
				
				if (rightChild != null) // bound of x-line on the RIGHT side
				{
					double botLeftX = x;
					double botLeftY = boundary.getMinY();
					double width =  (boundary.getMaxX() - x);
					double height = (boundary.getMaxY() - boundary.getMinY() ); // the height is restricted by previous bound's height
					
					Rectangle2D childBound = new Rectangle2D.Double(botLeftX, botLeftY, width, height);
					draw(rightChild, childBound);
				}	
			}
			else if (!n.isX()) // draw boundaries of y-node
			{
				// draw dot first
				StdDraw.setPenColor(StdDraw.BLUE);
				double x = n.point().getX();
				double y =  n.point().getY();
				StdDraw.filledCircle(x, y, maxScale/200);
//				StdDraw.text(x, y, n.toString());
				
				// Draw horizontal line separating y-boundaries
				StdDraw.line(boundary.getMinX(), y, boundary.getMaxX(), y);
				
				
				SpatialTreeNode leftChild = n.getLeft();
				SpatialTreeNode rightChild = n.getRight();
				
				if (leftChild != null) // bound of left subtree for y-line is BELOW
				{
					double botLeftX = boundary.getMinX();
					double botLeftY = boundary.getMinY();
					double width = (boundary.getMaxX() - boundary.getMinX()); // the width is restricted by previous bound's width
					double height = (y - boundary.getMinY()); 
					
					Rectangle2D childBound = new Rectangle2D.Double(botLeftX, botLeftY, width, height);
					draw(leftChild, childBound);
				}
				
				if (rightChild != null) // bound of right subtree for y-line is ABOVE
				{
					double botLeftX = boundary.getMinX();
					double botLeftY = y;
					double width = (boundary.getMaxX() - boundary.getMinX()); // the width is restricted by previous bound's width
					double height = (boundary.getMaxY() - y); 
					
					Rectangle2D childBound = new Rectangle2D.Double(botLeftX, botLeftY, width, height);
					draw(rightChild, childBound);
				}
			}
		}
	}
	
	
	/*
	 * This method resets the search query
	 */
	public void resetQuery()
	{
		queryActive = false;
		locationSet = null;
	}
	/*
	 * Returns an ArrayList of all points contained inside the query circle.
	 */
	public ArrayList<Point2D> query(Point2D center, double radius)
	{
		ArrayList<Point2D> result = new ArrayList<>();
		queryActive = true;
		// initialize param SearchParameters object
		// to be use to draw circle on graph and highlight point inside
		param = new SearchParameters(center, radius);
		locationSet = pointsInQuery(center, radius, root, result);
		drawQueryPoints();
		return locationSet;
	}
	
	/*
	 * Private recursive method to find points in query circle
	 */
	private ArrayList<Point2D> pointsInQuery(Point2D center, double radius, SpatialTreeNode n, ArrayList<Point2D> result)
	{
		
		double minX = center.getX() - radius;
		double maxX = center.getX() + radius;
		
		double minY = center.getY() - radius;
		double maxY = center.getY() + radius;
		
		if (n!= null) // only check if n is not null
		{
			SpatialTreeNode leftChild = n.getLeft();
			SpatialTreeNode rightChild = n.getRight();
			
			if (n.isX())
			{
				// if n is X-node, we compare x values to the min and max
				double xVal = n.point().getX();
				if (xVal > maxX) // if current node's X is outside circle's range to the right
				{
					// we recursively search in left subtree where x values would be lower, only if left subtree exits
					if (leftChild != null) 
					{
						pointsInQuery(center, radius, leftChild, result);
					}
					
				}
				else if(xVal < minX) // if current node's X is outside circle's range to the left
				{
					// we recursively search in right subtree where x values would be greater, only if right subtree exits
					if (rightChild != null)
					{
						pointsInQuery(center, radius, rightChild, result);
					}
				}
				else // else if current node's X is in circle's range
				{
					double distance = n.point().distance(center);
					// we compare current node's distance to center to the radius
					// if the distance is <= radius, it means the point is perfectly in the circle!
					if (distance <= radius)
					{
						// if in range, add to result ArrayList
						result.add(n.point());
					}
					
					// recursively search in left subtree for other potential point in circle if left subtree exists
					if (leftChild != null)
					{
						pointsInQuery(center,radius, n.getLeft(), result);	
					}
					// recursively search in right subtree for other potential point in circle if right subtree exists
					if (rightChild != null)
					{
						pointsInQuery(center, radius, rightChild, result);
					}
				}
			}
			
			if (!n.isX()) 
			{
				// if node is a Y-node, we compare Y-values to determine how to proceed
				double yVal = n.point().getY();
				if (yVal > maxY) // if current node's y is outside circle's range ABOVE
				{
					// we recursively search in left subtree BELOW where y values would be lower, only if left subtree exits
					if (leftChild != null)
					{
						pointsInQuery(center, radius, leftChild, result);
					}
					
				}
				else if(yVal < minY) // if current node's y is outside circle's range BELOW
				{
					// we recursively search in right subtree ABOVE where y values would be higher, only if right subtree exits
					if (rightChild != null) 
					{
						pointsInQuery(center, radius, rightChild, result);
					}
				}
				else
				{
					// similarly, if y-values are in range, do distance-check
					double distance = n.point().distance(center);
					if (distance <= radius)
					{
						result.add(n.point());
					}
					
					if (leftChild != null)
					{
						pointsInQuery(center,radius, n.getLeft(), result);	
					}
					if (rightChild != null)
					{
						pointsInQuery(center, radius, rightChild, result);
					}
				}
			}
			return result;
		}
		return null; // if n is null
	}
	
	/*
	 * This private method draws the query circle and highlight all points inside
	 */
	private void drawQueryPoints()
	{
		if (queryActive)
		{
			StdDraw.setPenColor(StdDraw.GREEN);
			StdDraw.setPenRadius(0.01);
			Point2D center = param.getCenter();
			StdDraw.circle(center.getX(), center.getY(), param.getRadius());
			
			StdDraw.setPenRadius();
			for (Point2D p: locationSet)
			{
				StdDraw.setPenColor(StdDraw.BLACK);
				StdDraw.circle(p.getX(), p.getY(), maxScale/200);
				StdDraw.setPenColor(StdDraw.YELLOW);
				StdDraw.filledCircle(p.getX(), p.getY(), maxScale/200);
			}
		}
	}
	
	
	// private that contains searching parameters for querying************************************************************************************
	private class SearchParameters
	{
		public Point2D center;
		public double radius;
		
		public SearchParameters( Point2D center, double radius)
		{
			this.center = center;
			this.radius = radius;
			
		}
		
		public Point2D getCenter()
		{
			return this.center;
		}
		
		public double getRadius()
		{
			return this.radius;
		}
	}
	// ********************************************************************************************************************************************
}




