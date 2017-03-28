package Main;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import Structures.DataItem;
import Structures.HashTable;

public class SymbolTable {

	public static HashTable table;
	//Variables for hashing
	private static String[] inputArr;	//The string array of the inputs
	private static int size;	//Size of the HashTables
	
		
	public static void setupTable() throws IOException{
		table = new HashTable(size); 
		createTable();
	}
	
	/**
	 * Reads the files for finding and deleting
	 * @throws IOException 
	 */
	public static String readInput() throws IOException{
		FileReader reader;
		BufferedReader bReader;
		String input;
		String content;
		
		reader = new FileReader(new File("SICOPS.txt"));
		bReader = new BufferedReader(reader);
		input = "";
		content = "";
		while((input = bReader.readLine()) != null){
			content+=input+";";
		}
		return content;
	}
	
	/**
	 * Inserts the data into the tables
	 * @throws IOException
	 */
	public static void createTable() throws IOException{
		String s = readInput();//read input into string
		inputArr = s.split(";");	//Split string into array
		size=(2*inputArr.length);	//Create odd size array
		
		int arrSize = findNextPrime(size);
		table = new HashTable(arrSize);
		
		//For each element inert into the table
		for(String element : inputArr){
			table.insert(new DataItem(element));
		}
		
	}//end insertions
	
	/**
	 * Finds the next prime greater than a min value
	 * @param min - the value to find the next prime 
	 * @return - the next prime number
	 */
	public static int findNextPrime(int min){
		boolean isPrime = true;
		boolean isFound = false;
		int num = min+1;
		while(!isFound){
			isPrime = true;
			for(int x=2;x<Math.sqrt(num) && isPrime;x++){
				if(num%x==0){
					isPrime = false;
				}//if the number is divisible by x
			}//for every number up to the square root(test it's primeness)
			if(!isPrime){
				num+=1;
			}else{
				isFound = true;
			}
		}//while the number isn't prime
		return num;
	}//end findNextPrime
	
	public static HashTable getTable(){
		return table;
	}
}
