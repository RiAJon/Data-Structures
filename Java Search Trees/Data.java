package search_tree;

//Data class =========================================================================================>
//this class holds the data structure present in ea ch BST node which will be loaded with external data

public class Data implements Comparable<Data>{
	
	// for now the data structure will hold student id's, names, and test scores
	String id, name, score;
	
	// constructor to initiate with data
	public Data(String id, String name, String score){
		this.id = id;
		this.name = name;
		this.score = score; 
	}
	

	@Override
	public int compareTo(Data other) {
		return this.id.compareTo(other.id);
	}

}
