import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import Structures.*;
/**
 * @author - Kyle Gray
 * @Date - 3/15/17
 */
public class MainClass{
	
	//Variables for hashing
	private String[] inputArr;	//The string array of the inputs
	private int size;	//Size of the HashTables
	private HashTable table;
	
	//Necessary variables for file IO
	private static File f_input;
	private FileReader reader;
	private BufferedReader bReader;
	private String input;
	private String content;
	
	/**
	 * Reads the files for finding and deleting
	 * @throws IOException 
	 */
	public String readInput(File f) throws IOException{
		reader = new FileReader(f);
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
	public void insertions() throws IOException{
		String s = readInput(f_input);//read input into string
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
	public int findNextPrime(int min){
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
	
	//Main
	public static void main(String[] args) throws IOException{
		f_input = new File("SICOPS.txt");
		
		//Create instance of the mainClass
		MainClass main = new MainClass();
		main.insertions();
		
		//Display the insertions
		System.out.println(main.table.displayTable());
	}//end main
	
}//end MainClass
