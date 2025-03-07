package search_tree;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		//define search trees
		BinaryTree basicTree = new BinaryTree();
		RedBlackTree redBlackTree = new RedBlackTree();
		
		int choice = 0;
		
		while (choice != 9) {
			
			System.out.println("\nMenu:");
			System.out.println("1. Load Data");
			System.out.println("2. Display all Data");
			System.out.println("3. Find Data by ID");
			System.out.println("4. Remove Data by ID");
			System.out.println("9. Exit");
			System.out.println("Enter Choice: ");

			choice = scanner.nextInt();
			
			switch (choice) {
			
			case 1: 
				System.out.println("Enter file name: ");
				String fileName = scanner.next();
					
				ArrayList<Data> dataList = CSVParser.parse(fileName);
				
				if (dataList.size() != 0) {
				
					// load bids into basic tree 
					long startTimeLoad = System.nanoTime();				
					basicTree.loadData(dataList);
					long endTimeLoad = System.nanoTime();
					System.out.println("Data loaded into basic tree in " + (endTimeLoad-startTimeLoad)/ 1_000_000.0 + " milliseconds.");
					
					// load bids into red and black tree
					long startTimeLoadRB = System.nanoTime();	
					redBlackTree.loadData(dataList);
					long endTimeLoadRB = System.nanoTime();
					System.out.println("Data loaded into red and black tree in " + (endTimeLoadRB-startTimeLoadRB)/ 1_000_000.0 + " milliseconds."); 
				}
					
				break;
				
			case 2: 
				// display basic tree
				System.out.println("***** Basic Tree *****");
				long startTimeDisplay = System.nanoTime();
				basicTree.inOrderSort();
				long endTimeDisplay = System.nanoTime();
				System.out.println("Data displayed from basic tree in " + (endTimeDisplay-startTimeDisplay)/ 1_000_000.0 + " milliseconds.");
				
				// display RB tree 
				System.out.println("\n***** Red and Black Tree *****");
				long startTimeDisplayRB = System.nanoTime();
				redBlackTree.printSorted();
				long endTimeDisplayRB = System.nanoTime();
				System.out.println("Data displayed from red and black tree in " + (endTimeDisplayRB-startTimeDisplayRB)/ 1_000_000.0 + " milliseconds.");
				
				break;
				
			case 3: 
				System.out.println("Enter id to be found: ");
				String findId = scanner.next();
				
				try {
				
					// search basic tree
					long startTimeSearch = System.nanoTime();
					Data findData = basicTree.search(findId);
					long endTimeSearch = System.nanoTime();
					System.out.println("ID: " + findData.id + "\nName: " + findData.name + "\nScore: " + findData.score);		
					System.out.println("Data found in " + (endTimeSearch-startTimeSearch)/ 1_000_000.0 + " milliseconds.");	
					
					// search RB tree 
					long startTimeSearchRB = System.nanoTime();
					Data findDataRB = redBlackTree.search(findId);
					long endTimeSearchRB = System.nanoTime();
					System.out.println("ID: " + findDataRB.id + "\nName: " + findDataRB.name + "\nScore: " + findDataRB.score);		
					System.out.println("Data found in " + (endTimeSearchRB-startTimeSearchRB)/ 1_000_000.0 + " milliseconds.");
				
				}
				catch(Exception e){
					System.out.println("Sorry, no record exists with that id.");
				}
				
				break;
				
			case 4: 
				System.out.println("Enter id to be deleted: ");
				String removeId = scanner.next();
				
				try {

					// remove from basic tree
					long startTimeRemove = System.nanoTime();
					basicTree.remove(removeId);
					long endTimeRemove = System.nanoTime();
					System.out.println("Data removed from basic tree in " + (endTimeRemove-startTimeRemove)/ 1_000_000.0 + " milliseconds.");	
					
					// remove from RB tree
					long startTimeRemoveRB = System.nanoTime();
					redBlackTree.remove(removeId);
					long endTimeRemoveRB = System.nanoTime();
					System.out.println("Data removed from red and black tree in " + (endTimeRemoveRB-startTimeRemoveRB)/ 1_000_000.0 + " milliseconds.");
				
				}
				catch(Exception e){
					System.out.println("Sorry, no record exists with that id.");
				}
				
				break;
			}
			
		}
		
		scanner.close();
		
	}
	
}
