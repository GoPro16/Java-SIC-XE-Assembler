package Structures;

/**
 * Object for storing data
 * @author Kyle
 *
 */
public class DataItem {

	private String instruction;//Used for any type of data
	private boolean format4;
	private boolean sic;
	private String optCode;
	private int bytes;
	
	public DataItem(String s){
		assignValues(s);
	}
	
	public void assignValues(String s){
		String[] temp = s.split("\\s+");
		instruction = temp[0];
		optCode = temp[1];
		bytes = Integer.parseInt(temp[2]);
		if(temp[0].charAt(0) == '+'){
			format4 = true;
		}else if(temp[0].charAt(0) == '*'){
			sic = true;
		}else{
			sic=false;
			format4=false;
		}
	}
	
	//gets the content of the item
	public String getContent(){
		return String.format("%8s %8d",instruction,bytes); 
	}
	
	public int getBytes(){
		return bytes;
	}
	public String getInstruction(){
		return instruction;
	}
	
	public boolean compareTo(String s){
		return (s == instruction);
	}
	
}//end DataItem