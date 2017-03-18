import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import Structures.HashTable;
import Structures.LinkedList;

public class Program {
	
	private LinkedList instructions;
	/**
	 * Constructor for program
	 * @param f - the input file containing source code
	 * @throws IOException
	 */
	public Program(File f) throws IOException{
		instructions = new LinkedList();
		readInput(f);
	}
	
	/**
	 * Reads the files for finding and deleting
	 * @throws IOException 
	 */
	public String readInput(File f) throws IOException{
		FileReader reader;
		BufferedReader bReader;
		String input;
		String content;
		
		reader = new FileReader(f);
		bReader = new BufferedReader(reader);
		input = "";
		content = "";
		while((input = bReader.readLine()) != null){
			instructions.insert(input);
		}
		return content;
	}//end readInput
	
	public void display(){
		instructions.display();
	}
	
	
	
	
}//end program
