## Problem Statement 
2-d trees order its points by a mix of the two coordinates.  They have two types of nodes -- x-nodes and y-nodes -- and maintain the following two properties:

* The children of an x-node are y-nodes
* The children of a y-node are x-nodes
Each type of node uses a different comparison to order points.  
This causes different levels of the tree to compare points differently, using the following rules:

For every x-node:
* All descendants in the left subtree have a smaller x-coordinate than the point stored at the node.  Visually, all descendant points are left of this point.
* All descendants in the right subtree have a larger x-coordinate than the points stored at the node.  Visually, all descendant points are right of this point.

For every y-node:
* All descendants in the left subtree have a smaller y-coordinate than the point stored at the node.  Visually, all descendant points are below this point.
* All descendants in the right subtree have a larger y-coordinate than the point stored at the node.  Visually, all descendant points are above this point.
As it turns out, this property allows us to efficiently perform queries like **"which points are within a given radius of a target?"**

## Result 
The following image show querying result in a circle centered at (500,500) with radius 200 from a map of 2000 location-points.
![spatialTreeExamples](https://user-images.githubusercontent.com/50902696/58613137-131c1180-8272-11e9-874e-19f9ad001ff1.PNG)
