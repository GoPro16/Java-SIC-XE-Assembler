package Main;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import Structures.HashTable;
import Structures.LinkedList;

public class Program {
	
	private LinkedList instructions;
	private PrintWriter writer;
	/**
	 * Constructor for program
	 * @param f - the input file containing source code
	 * @throws IOException
	 */
	public Program(File f) throws IOException{
		instructions = new LinkedList();
		readInput(f);
		instructions.calculateAddresses();
		createLST();
		createOBJ();
	}
	
	/**
	 * Reads the files for finding and deleting
	 * @throws IOException 
	 */
	public void readInput(File f) throws IOException{
		FileReader reader;
		BufferedReader bReader;
		String input;
		String content;
		
		reader = new FileReader(f);
		bReader = new BufferedReader(reader);
		input = "";
		content = "";
		while((input = bReader.readLine()) != null){
			if(input.split("\\s+").length > 0){
				instructions.insert(input);	
			}//checks if there is an empty line
		}
		bReader.close();
		reader.close();
	
	}//end readInput
	
	public void display(){
		instructions.display();
	}
	
	public void createLST() throws FileNotFoundException, UnsupportedEncodingException{
		writer = new PrintWriter("pgm.lst","UTF-8");
		writer.println(instructions.toLST());
		writer.close();
	}
	
	public void createOBJ() throws FileNotFoundException, UnsupportedEncodingException{
		writer = new PrintWriter("pgm.obj","UTF-8");
		writer.println(instructions.toOBJ());
		writer.close();
	}
	
	
}//end program
