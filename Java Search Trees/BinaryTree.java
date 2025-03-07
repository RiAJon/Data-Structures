package search_tree;

import java.util.ArrayList;

// Tree ==================================================================>

public class BinaryTree {
	
	// Node Class ===================================================>
	// this class defines the nodes which will be sorted in the BST
	private class Node {
		
		Node left;
		Node right;
		Data data; // this is the data held in each node 
		
		// constructor to initialize with data
		Node(Data data)	{
			
			left = null;
			right = null;
			this.data = data;
			
		}
	}

	// SearchTree =======================================================>

	 private Node root; 
	  
	 // constructor 
	 BinaryTree() {
		 root = null; // root starts null
	 }
	 
	 // Sorting ------------------------------------------------------------>
	 
	 void inOrderSort()	{
		 inOrder(root); 
	 }
	 
	 void inOrder(Node node) {
		 
		  if (node != null)	{ // if current node is not null 
			  
			 inOrder(node.left); //traverse left 
			 System.out.println(node.data.id + " - " + node.data.name + " - " + node.data.score);
			 inOrder(node.right); //traverse right
		 }
		  
		  return;
	 }
	 
	 // Search------------------------------------------------------------------>
	 Data search(String id) {
		 
		 Node currentNode = root; // set current node as root
		 
		 // while current node is not null
		 while (currentNode != null) { 
			 
			 int comparisonResult = id.compareTo(currentNode.data.id);
			 
			 if (comparisonResult == 0) { // if matching data is found 
				 return currentNode.data; // return data
			 }
			 else if (comparisonResult < 0) { // if passed id is lexicographically less than current node data's id
				 currentNode = currentNode.left; // traverse left
			 }
			 else {
				 currentNode = currentNode.right; // traverse right
			 }
		 }
		 
		 // otherwise return null data
		 return null;
	 }
	 
	 // Insert------------------------------------------------------------------>
	 void insert(Data data) {
	 		
	 	// if tree root is null
	 	if (root == null){
	 		
		 	Node newNode = new Node(data); // instanciate new node
	 		root = newNode;
	 	}
	 	else {
	 		addNode(root,data); // add new node to tree
	 	}
	 		
	 }
	 	
	 void addNode(Node node, Data data) {
	 		
	 	Node newNode = new Node(data); // instanciate new Node
	 	int comparisonResult = data.id.compareTo(node.data.id);
	 		
	 	// if data to be entered has an id less than data in current node
	 	if (comparisonResult < 0) {
	 			
	 		if (node.left == null) { // if current node has no left child
	 			node.left = newNode; // new node becomes left child 
	 		}
	 		else {
	 			addNode(node.left, data); // recursively transverse left 
	 		}
	 	}
	 	else { // if data to be entered has an id greater than data in current node 
	 			
	 		if (node.right == null) { // if current node has no right child 
	 			node.right = newNode; // new node becomes right child
	 		}
	 		else {
	 			addNode(node.right, data); // recursively transverse right 
	 		}
	 			
	 	}
	 		
	 }
	 
	 // Load Data ------------------------------------------------------------------->
	 
	 void loadData(ArrayList<Data> dataList) {
		 
		 for (Data data : dataList) {
			 insert(data);
		 }
		 
	 }
	 	
	 // Remove ---------------------------------------------------------------------->
	 void remove(String id) {
		 removeNode(root, id);
 	}
 	
	 Node removeNode(Node node, String id) {
	 
		 if (node == null) {
			 return null;
		 }
	 
		 int comparisonResult = id.compareTo(node.data.id);
		 
		 if (comparisonResult < 0) {
		        node.left = removeNode(node.left, id);
		 } else if (comparisonResult > 0) {
		        node.right = removeNode(node.right, id);
		        
		 } else { // this is the node you are looking for 
		    	
		 	// Case 1: Node is a leaf (no children)
	        if (node.left == null && node.right == null) {
	        	return null;
	        }
	        
	        // Case 2: Node has only one child (left or right)
	        else if (node.left == null) {
	        	return node.right;
	        } else if (node.right == null) {
	        	return node.left;
	        }
	        
	        // Case 3: Node has two children
	        else {
	            // Find in-order successor (smallest in right subtree)
	            Node successor = findMin(node.right);
	            node.data = successor.data; // Replace node's data with successor's data
	            node.right = removeNode(node.right, successor.data.id); // Remove successor
	        }
	    }
		 
		    return node;
	 }
	 
	// Helper function to find the smallest node in a subtree
	 Node findMin(Node node) {
	     while (node.left != null) {
	         node = node.left;
	     }
	     return node;
	 }
}
