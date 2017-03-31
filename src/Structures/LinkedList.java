package Structures;

import Main.ErrorTable;
import Main.SymbolTable;

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
			first = new Link(data,-1);
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
	private int address;
	private String label;
	private String comment;
	private int status;
	
	public Link(String data,int add){
		status = -1;
		if(add == -1){
			this.address = (int)(Long.parseLong(data.substring(19,28).trim(),16));
		}else{
			this.address = add;
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
		mneumonic = data.substring(10,16).trim();
		
		//Operand
		try{
			operand = data.substring(19,28).trim();
		}catch(StringIndexOutOfBoundsException e){
			operand = data.substring(19).trim();	
		}
		
		//Comments
		try{
			comment = data.substring(31);
		}catch(StringIndexOutOfBoundsException e){
			comment = "";
		}
	}
	
	public int nextAddress(){
		int inc;
		if(mneumonic.equals("RESW")){
			inc = 3*Integer.parseInt(operand);
		}else if(this.mneumonic == null || this.mneumonic.equals("")){
			status = 200;
			inc = 0;
		}else{
			inc = SymbolTable.getTable().find(this.mneumonic);
		}
		
		if(inc == -1){
			status = 100;
			inc = 0;
		}
		return incrementAddress(inc);
	}
	
	public int incrementAddress(int count){
		return (int)(Long.parseLong(Integer.toString(address))+Long.parseLong(Integer.toString(count)));
	}
	
	public String getDisplayFormat(){
		if(status == -1){
			return String.format("%-10s%-10s%-10s%-10s%-10s",Integer.toHexString(address).toUpperCase(),label,mneumonic,operand,comment);
		}else{
			return ErrorTable.report(status);
		}
	}
}
