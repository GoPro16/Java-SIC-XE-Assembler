package Main;

public class ErrorTable {
	
	public static String report(int status){
		String s = "";
		switch(status){
		case 100:
			s = "Invalid Mneumonic";
			break;
		case 200:
			s = "Invalid Instruction Format";
			break;
		default:
			s= "Some error occured";
			break;
		}
		
		return s;
	}
}
