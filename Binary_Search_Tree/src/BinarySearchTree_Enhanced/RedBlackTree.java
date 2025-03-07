package search_tree;

import java.util.ArrayList;

//Red-Black Tree implementation
class RedBlackTree {
	
	//Node Class ======================================================>

	private class Node {
		
		 Data data;
		 Node left, right, parent;
		 boolean isRed;
		
		 public Node(Data data) {
		     this.data = data;
		     this.isRed = true; // New nodes are red by default
		 }
	}
	
	private Node root;
 

	 // Insert------------------------------------------------------>
	 // Inserts a new Data object into the tree
	 public void insert(Data data) {
		 
	     Node newNode = new Node(data);
	     
	     if (root == null) {
	         root = newNode;
	         root.isRed = false;
	     } else {
	         insertRec(root, newNode);
	         fixInsert(newNode);
	     }
	 }

	// Recursive helper function for insertion
	 private void insertRec(Node root, Node newNode) {
		 
	     if (newNode.data.compareTo(root.data) < 0) {
	         if (root.left == null) {
	             root.left = newNode;
	             newNode.parent = root;
	         } else {
	             insertRec(root.left, newNode);
	         }
	     } else {
	         if (root.right == null) {
	             root.right = newNode;
	             newNode.parent = root;
	         } else {
	             insertRec(root.right, newNode);
	         }
	     }
	 }

	 // Fixes Red-Black tree properties after insertion
	 private void fixInsert(Node node) {
		 
	     while (node.parent != null && node.parent.isRed) {
	    	 
	         Node grandparent = node.parent.parent;
	         
	         if (node.parent == grandparent.left) {
	       
	             Node uncle = grandparent.right;
	             
	             if (uncle != null && uncle.isRed) {
	            	 
	                 node.parent.isRed = false;
	                 uncle.isRed = false;
	                 grandparent.isRed = true;
	                 node = grandparent;
	                 
	             } else {
	            	 
	                 if (node == node.parent.right) {
	                     node = node.parent;
	                     leftRotate(node);
	                 }
	                 
	                 node.parent.isRed = false;
	                 grandparent.isRed = true;
	                 rightRotate(grandparent);
	             }
	         } else {
	        	 
	             Node uncle = grandparent.left;
	             
	             if (uncle != null && uncle.isRed) {
	            	 
	                 node.parent.isRed = false;
	                 uncle.isRed = false;
	                 grandparent.isRed = true;
	                 node = grandparent;
	                 
	             } else {
	                 if (node == node.parent.left) {
	                	 
	                     node = node.parent;
	                     rightRotate(node);
	                 }
	                 
	                 node.parent.isRed = false;
	                 grandparent.isRed = true;
	                 leftRotate(grandparent);
	             }
	         }
	     }
	     
     root.isRed = false;
     
	 }
	 
	 // Load Data ------------------------------------------------------------------->
	 
	 void loadData(ArrayList<Data> dataList) {
		 
		 for (Data data : dataList) {
			 insert(data);
		 }
		 
	 }

	 // Balance ---------------------------------------------------------->
	 
	 private void leftRotate(Node node) {
		 
	     Node rightChild = node.right;
	     node.right = rightChild.left;
	     
	     if (rightChild.left != null) {
	    	 rightChild.left.parent = node;
	     }
	     
	     rightChild.parent = node.parent;
	     
	     if (node.parent == null) {
	    	 root = rightChild;
	     } 
	     else if (node == node.parent.left) {
	    	 node.parent.left = rightChild;
	     } 
	     else {
	    	 node.parent.right = rightChild;
	     }
	     
	     rightChild.left = node;
	     node.parent = rightChild;
	 }

	 private void rightRotate(Node node) {
	     Node leftChild = node.left;
	     node.left = leftChild.right;
	     
	     if (leftChild.right != null) {
	    	 leftChild.right.parent = node;
	     }
	     leftChild.parent = node.parent;
	     if (node.parent == null) {
	    	 root = leftChild;
	     }
	     else if (node == node.parent.right) {
	    	 node.parent.right = leftChild;
	     }
	     else {
	    	 node.parent.left = leftChild;
	     }
	     
	     leftChild.right = node;
	     node.parent = leftChild;
	     
	 	}

	// Search ------------------------------------------------------------>
	public Data search(String id) {
		
	    Node current = root;
	    
	    while (current != null) {
	    	
	    	int comparisonResult = id.compareTo(current.data.id);
	    	
	        if (comparisonResult == 0) { // Found the node
	            return current.data;
	            
	        } else if (comparisonResult < 0) { // Search left
	            current = current.left;
	            
	        } else { // Search right
	            current = current.right;
	            
	        }
	    }
	    
	    return null; // Not found
	}
	 
	 // Sorting ------------------------------------------------------------>
	
	 // In order traversal 
	 public void inorderTraversal(Node node) {
		 
	     if (node != null) {
	    	 
	         inorderTraversal(node.left);
	         System.out.println(node.data.id + " - " + node.data.name + " - " + node.data.score);
	         inorderTraversal(node.right);
	     }
	 }
	
	 public void printSorted() {
	     inorderTraversal(root);
	 }
	 
	 // Removal -------------------------------------------------------------->
	 public void remove(String id) {
	    root = removeRec(root, id);
	 }
	
	 private Node removeRec(Node root, String id) {
		 
	    if (root == null) {
	    	return null;
	    }
	    
	    int comparisonResult = id.compareTo(root.data.id);
	
	    if (comparisonResult < 0) {
	        root.left = removeRec(root.left, id);
	    } else if (comparisonResult > 0) {
	        root.right = removeRec(root.right, id);
	    } else {
	    	
	        if (root.left == null) return root.right;
	        else if (root.right == null) return root.left;
	        
	        Node minLargerNode = findMin(root.right);
	        root.data = minLargerNode.data;
	        root.right = removeRec(root.right, minLargerNode.data.id);
	    }
	    
	    return root;
	}
	 
	 // runs a while loop to find min value 
	 private Node findMin(Node node) {
	        while (node.left != null) {
	            node = node.left;
	        }
	        return node;
	    }
	 
}

