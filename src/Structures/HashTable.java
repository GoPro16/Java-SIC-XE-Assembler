package Structures;

/**
 * Table for placing data items in
 * an array 
 * @author Kyle
 */
public class HashTable {
	
	private DataItem[] arr; // the array of items
	private int size;	//The size of the array

	/**
	 * Constructor for the HashTable
	 * @param size - the size of the table
	 * @param type - the type of probing to be used
	 */
	public HashTable(int size){
		this.size = size;
		arr = new DataItem[size];
	}//end HashTable
	
	/**
	 * Hashes the given string
	 * @param s - the string to hash
	 * @return - the value of the hashed string
	 */
	public int hash(String s){
		int hash = 0;
		char[] arr = s.toCharArray();
		hash=arr[0];
		for(int x=1;x<arr.length;x++){
			hash=(hash*26+arr[x])%size;
		}//hash the string
		hash%=size;//Just in case its one character(will not affect if more)
		return hash;
	}//end hash
	
	/**
	 * Displays the insertions of the data
	 * @return - the formated string of info
	 */
	public String displayTable(){
		String s = "";
		for(int x=0;x<arr.length;x++){
			if(arr[x] != null){
				//s+=(String.format("Memory Locatiom:%-10d%-10s\n",x,arr[x].getContent()));
			}
		}
		return s;
	}//end displayInsertions
	
	
	/**
	 * Insertion method for hash table
	 * @param item - the item to be inserted
	 */
	public void insert(DataItem item){
		//Hash
		String s = item.getContent();
		int hash = hash(s);
		//insert
		if(arr[hash] == null){
			arr[hash] = item;
		}else{//if the index is null or there is a deleted item
			probeInsert(hash,item);
		}//else probe until valid insertion
	}//end insert
	
	/**
	 * probeInsertion
	 * @param hash - the hash value of the string
	 * @param item - the item to be inserted
	 */
	public void probeInsert(int hash,DataItem item){
		boolean isFound = false;//boolean to know when found
		int walker = hash;//walker to walk on array
		int count = 1;//counter for probing
		int  newHash = hash;//New hash value
		
		while(!isFound){
			walker = hash;
			newHash = quadP(count++);
			for(int x=newHash;x>0;x--){
				if(++walker == arr.length){
					walker = 0;
				}
			}
			if(arr[walker]== null){
				arr[walker] = item; 
				isFound = true;
			}//if the array item is null or
		}//while the item position isn't found
	}//end probeInsert
	
	
	
	//Used if using quadratic probing
	public int quadP(int x){
		return x*x;
	}//end quadP

}//end HashTable