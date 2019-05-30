package treelover28.SpatialTree;
/*
 * Khai Lai
 * COMP 2673 
 * Assignment 5- Spatial Tree
 * Professor Mohammed Albow 
 * T.A Dalton Crutchfield and T.A Lombe Chileshe
 */
import java.awt.geom.Point2D;
import java.util.Scanner;

public class Driver {

	public static void main(String[] args) 
	{
		SpatialTree t = new SpatialTree();

		for (int i = 0; i < 100; i++)
		{
			t.add(Math.random() * SpatialTree.maxScale , Math.random() * SpatialTree.maxScale );
		}
		t.draw();
		
		//Allow user to type in queries
		Scanner keyboard = new Scanner(System.in);
		
		System.out.println("\t\tWelcome to SearchQuery");
		System.out.println("\t\tMAP DIMENSION: " + SpatialTree.maxScale + "x" + SpatialTree.maxScale + "\n");
		System.out.println("Here are the available command:");
		System.out.println("search xCoordinate yCoordinate radius\n\tThis command will draw a query circle on the map and highlight all points inside.");
		System.out.println("\tFor example: search 500.0 257.25 200\n\twhere the parameters are seperated by a space.\n");
		System.out.println("searchList xCoordinate yCoordinate radius\n\tThis command prints all results found to console and highlight on the map");
		System.out.println("\tFor example: searchList 500.0 257.25 200\n\twhere the parameters are seperated by a space.\n");
		System.out.println("searchList xCoordinate yCoordinate radius\n\tThis command prints all results found to console and highlight on the map");
		System.out.println("\tFor example: searchList 500.0 257.25 200\n\twhere the parameters are seperated by a space.\n");
		System.out.println("reset\n\tThis command reset the query and redraw the map\n\tIf you don't reset, all searching methods will continue drawing on top of map");
		System.out.println("\tFor example: reset\n");
		System.out.println("randomizeMap n \n\tThis command draw a new map of n random values\n\tIf no n argument is typed, map just randomize using the 100 points");
		System.out.println("\tFor example: randomizeMap 200 or randomizeMap");
		System.out.println("help\n\tThis command prints all commands and their instructions to the console");
		System.out.println("\tFor example: help\n");
		System.out.println("quit\n\tThis command terminates the program");
		System.out.println("\tFor example: quit\n");
		
		while (true)
		{
			
			System.out.println("Type your command here:");
			String[] command = (keyboard.nextLine()).split(" ");
			
			switch (command[0])
			{
				case "searchList":
				{	
					try 
					{
						double xCoord = Double.parseDouble(command[1]);
						double yCoord = Double.parseDouble(command[2]);
						double radius = Double.parseDouble(command[3]);
						Point2D center = new Point2D.Double(xCoord,yCoord);
						for (Point2D p : t.query(center, radius))
						{
							System.out.println(p);
						}	
						break; // will only get to break statement if inputs are valid
					}
					catch (NumberFormatException e)
					{
						System.out.println("Invalid coordinates");
						break;
					}
				}
				case "search":
				{	
					try 
					{
						double xCoord = Double.parseDouble(command[1]);
						double yCoord = Double.parseDouble(command[2]);
						double radius = Double.parseDouble(command[3]);
						Point2D center = new Point2D.Double(xCoord,yCoord);
						t.query(center, radius);
						break; // will only get to break statement if inputs are valid
					}
					catch (NumberFormatException e)
					{
						System.out.println("Invalid coordinates");
						break;
					}
				}
				case "help":
				{
					System.out.println("Here are the available command:");
					System.out.println("search xCoordinate yCoordinate radius\n\tThis command will draw a query circle on the map and highlight all points inside.");
					System.out.println("\tFor example: search 500.0 257.25 200\n\twhere the parameters are seperated by a space.\n");
					System.out.println("searchList xCoordinate yCoordinate radius\n\tThis command prints all results found to console and highlight on the map");
					System.out.println("\tFor example: searchList 500.0 257.25 200\n\twhere the parameters are seperated by a space.\n");
					System.out.println("searchList xCoordinate yCoordinate radius\n\tThis command prints all results found to console and highlight on the map");
					System.out.println("\tFor example: searchList 500.0 257.25 200\n\twhere the parameters are seperated by a space.\n");
					System.out.println("reset\n\tThis command reset the query and redraw the map\n\tIf you don't reset, all searching methods will continue drawing on top of map");
					System.out.println("\tFor example: reset\n");
					System.out.println("randomizeMap n \n\tThis command draw a new map of n random values\n\tIf no n argument is typed, map just randomize using the 100 points");
					System.out.println("\tFor example: randomizeMap 200 or randomizeMap");
					System.out.println("help\n\tThis command prints all commands and their instructions to the console");
					System.out.println("\tFor example: help\n");
					System.out.println("quit\n\tThis command terminates the program");
					System.out.println("\tFor example: quit\n");
					break;
				}
				
				case "quit":
				{
					keyboard.close();
					System.exit(0);
					break;
				}
				
				case "reset":
				{
					t.resetQuery();
					t.draw();
					break;
				}
				
				case "randomizeMap":
				{
					try
					{
						int value = Integer.parseInt(command[1]);
				
						t = new SpatialTree();
						for (int i = 0; i < value; i++)
						{
							t.add(Math.random() * SpatialTree.maxScale , Math.random() * SpatialTree.maxScale );
						}
						t.draw();
						break;
					}
					catch (NumberFormatException e)
					{
						System.out.println("Invalid number of points");
						break;
					}
					catch(ArrayIndexOutOfBoundsException e) // if no argument is entered
					{
						System.out.println("No argument entered. Will default to n = 100");
						t = new SpatialTree();
						for (int i = 0; i < 100; i++)
						{
							t.add(Math.random() * SpatialTree.maxScale , Math.random() * SpatialTree.maxScale );
						}
						t.draw();
						break;
					}
				}
				default:
				{
					continue; // if user types nothing or command is not recognized, do nothing.
				}
			}	
		}
	}
}

