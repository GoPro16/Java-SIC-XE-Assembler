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
		System.out.println(String.format("%-10s%-10s%-10s%-10s%-10s","Address","Label","Instruct","Operand","Comments"));
		System.out.println("-------------------------------------------------");
		while(temp != null){
			System.out.println(temp.getDisplayFormat());
			temp = temp.next;
		}
	}
}//end List

class Link{
	public ObjectCode objectCode;//ObjectCode String
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
		System.out.println(data);
		objectCode = new ObjectCode();
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
		
		
		if(mneumonic.equals("RESW") && !label.isEmpty()){
			SymbolTable.insertWord(String.format("%s %s %d",label,address,Integer.parseInt(operand)*3));
		}else if(mneumonic.equals("WORD") && !label.isEmpty()){
			SymbolTable.insertWord(String.format("%s %s %d",label,address,3));
		}
		
		//Append the first part of object code
		switch(this.mneumonic){
		case "START":
		case "BASE":
			break;
		case "WORD":
			String temp = operand;
			while(operand.length() != 6){
				operand = "0"+objectCode;
			}
			objectCode.setAddress(Integer.parseInt(operand,16));
			break;
		case "RESW":
			objectCode.setAddress(Integer.parseInt("FFFFFF",16));
			break;
		default:
			try{
			objectCode.setOptCode(Integer.parseInt(SymbolTable.table.find(this.mneumonic).getOptCode(),16));
			switch(data.charAt(18)){
			case '#':
				System.out.println("THIS SHOULD FUCKING WORD");
				try{
					if(Integer.parseInt(operand) == Integer.parseInt(operand,10)){
						objectCode.calcNI(false,true);
					}
				}catch(NumberFormatException e){
					objectCode.calcNI(true, true);	
				}
				break;
			case '@':
				objectCode.calcNI(true, false);
				break;
			default:
				objectCode.calcNI(true, true);
				break;
			}
			}catch(NullPointerException e){
			}
		}
		
	
			//(Long.parseLong(Integer.toString(address))+Long.parseLong(Integer.toString(count)));
		
		System.out.println(objectCode.toString());
	}
	
	public int nextAddress(){
		int inc;
		if(mneumonic.equals("RESW")){
			inc = 3*Integer.parseInt(operand);
		}else if(this.mneumonic == null || this.mneumonic.equals("")){
			status = 200;
			inc = 0;
		}else{
			inc = SymbolTable.getTable().find(this.mneumonic).getBytes();
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
