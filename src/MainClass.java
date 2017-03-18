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
	
	private Program program;
	
	
	public void readProgram() throws IOException{
		try {
			program = new Program(new File("testFile.txt"));
			program.display();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//Main
	public static void main(String[] args) throws IOException{
		
		//Create instance of the mainClass
		MainClass main = new MainClass();
		SymbolTable.createTable();
		main.readProgram();
		//Display the insertions
		System.out.println(SymbolTable.table.displayTable());
	}//end main
	
}//end MainClass
