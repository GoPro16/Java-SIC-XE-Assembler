package Structures;

public class LinkedList{
	private Link first;
	private Link last;
	public LinkedList(){
		first = null;
		last = null; 
	}
	
	public String delete(){
		String data = first.getDisplayFormat();
		first = first.next;
		return data;
	}
	
	public void reset(){
		first = null;
		last = null;
	}
	
	public String getPeek(){
		return first.getDisplayFormat();
	}
	
	public boolean isEmpty(){
		return (first == null);
	}
	
	public void insert(String data){
		if(first == null){
			first = new Link(data,null);
			last = first;
		}else{
			Link newLink = new Link(data,last.nextAddress());
			last.next = newLink;
			last = newLink;	
		}
	}
	
	public void display(){
		Link temp = first;
		while(temp != null){
			System.out.println(temp.getDisplayFormat());
			temp = temp.next;
		}
	}
}//end List

class Link{
	
	public Link next;
	private String mneumonic;
	private String operand;
	private boolean format4 = false;
	private boolean sic = false;
	private String address;
	private String label;
	
	public Link(String data,String address){
		if(address == null){
			address = data.substring(10,16);
		}else{
			this.address = address;
		}
		
		//Checks label
		label = data.substring(0,7);
		
		//Checks format
		if(data.charAt(9) == '+'){
			format4 = true;
		}else if(data.charAt(9) == '*'){
			sic = true;
		}
		
		//Instruction
		mneumonic = data.substring(10,16);
		
		//Operand
		operand = data.substring(19);
	}
	
	public String nextAddress(){
		int inc = 3;
		if(format4){
			inc = 4;
		}else if(sic){
			inc = 3;
		}else if(mneumonic.equals("RESW")){
			inc = Integer.parseInt(operand);
		}//Still working
		return incrementAddress(inc);
	}
	
	public String incrementAddress(int count){
		return null;
	}
	
	public String getDisplayFormat(){
		return String.format("%-10s%-10s%-10s%-10s",address,label,mneumonic,operand);
	}
}
