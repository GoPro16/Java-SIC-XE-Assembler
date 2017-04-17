package Structures;

import Main.ErrorTable;
import Main.SymbolTable;

public class LinkedList{
	private Link first;
	private Link last;
	private int baseAdd;
	
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
		System.out.println("****************************\nKyle Gray: SIC/XE Assembler\nVersion Date 04/14/17\n****************************");
		System.out.println(String.format("ASSEMBLER REPORT\n-----------------\n%8s %13s %15s","Loc","Object Code","Source Code"));
		System.out.println(String.format("%8s %13s %16s","---","-----------","-----------"));
		int line = 1;
		while(temp != null){
			System.out.println(String.format("%03d- %s",line++,temp.getDisplayFormat()));
			temp = temp.next;
		}
	}
	
	public String toLST(){
		Link temp = first;
		String output = "";
		output+="****************************\nKyle Gray: SIC/XE Assembler\nVersion Date 04/14/17\n****************************\n";
		output+=String.format("ASSEMBLER REPORT\n-----------------\n%8s %13s %15s\n","Loc","Object Code","Source Code");
		output+=String.format("%8s %13s %16s\n","---","-----------","-----------");
		int line = 1;
		while(temp != null){
			output+=String.format("%03d- %s\n",line++,temp.getDisplayFormat());
			temp = temp.next;
		}
		return output;
	}
	
	public String toOBJ(){
		Link temp = first;
		String output = "";
		while(temp != null){
			output+=String.format("%s\n",temp.getObjectCode().toString());
			temp = temp.next;
		}
		return output+"!";
	}
	
	public void findBase(){
		//Base Find
		Link temp = first;
		while(true && temp.next != null){
			if(temp.getMneumonic().equals("BASE")){
						break;
			}
			temp = temp.next;
		}
		String base = temp.getOperand();
		temp = temp.next;
		while(true && temp.next != null){
			if(temp.getOperand() == base){
				baseAdd = temp.getAddress();
				break;
			}
			temp = temp.next;
		}
	}//end findBase
	
	public void calculateAddresses(){
		findBase();
		Link temp = first;
		while(temp.next != null){
			switch(temp.getMneumonic()){
			case "START":
			case "END":
			case "RESW":
			case "WORD":
			case "BASE":
				break;
			default:
				boolean address = false;
				if(temp.getObjectCode().isExtended()){//if extended
					String key = temp.getOperand();
					if(key.charAt(0) == '#' || key.charAt(0) == '@'){
						key = key.substring(1);
					}
					Link link = first;
					while(true && link.next != null){
						if(link.getLabel().equals(key) && !link.compareTo(temp)){
							temp.getObjectCode().setAddress(link.getAddress());
							break;
						}else{
							link = link.next;
						}
					}
				}else if(SymbolTable.table.find(temp.getMneumonic()).getBytes() == 2){//if Resistor operation
					String[] registers = temp.getOperand().split(",");
					for(int i=0;i<2;i++){
						switch(registers[i]){
						case "A":
							registers[i] = "0";
							break;
						case "T":
							registers[i] = "5";
							break;
						}
					}//end for
					
					temp.getObjectCode().setAddress(Integer.parseInt((registers[0]+registers[1]),16));
				}else{//if its a 6 byte instruction
					String key = temp.getOperand();
					if(key.charAt(0) == '#' || key.charAt(0) == '@'){
						key = key.substring(1);
					}
					Link link = first;
					while(true && link.next != null){
						if(link.getLabel().equals(key) && !link.compareTo(temp)){//if we found the label
							if(link.getAddress() - temp.next.getAddress() > 2048){
								//Base
								temp.getObjectCode().setAddress(link.getAddress()-baseAdd);
								temp.getObjectCode().modifyPC();
							}else{
								temp.getObjectCode().setAddress(link.getAddress()-temp.nextAddress());
								break;
							}
							break;
						}else{
							link = link.next;
						}
					}
				}//end if 	
				break;
			}//end swtich
			temp = temp.next;
		}
	}
	
	
}//end List

class Link{
	public ObjectCode objectCode;//ObjectCode String
	public Link next;
	private String mneumonic;
	private String operand;
	private int address;
	private String label;
	private String comment;
	private int status;
	
	public Link(String data,int add){
		objectCode = new ObjectCode();
		status = -1;
		if(add == -1){
			this.address = (int)(Long.parseLong(data.substring(19,28).trim(),16));
		}else{
			this.address = add;
		}
		
		//Checks label
		label = data.substring(0,7).trim();
		
		//Checks format
		if(data.charAt(9) == '+'){
			objectCode.setExtended(true);
		}
		
		//Instruction
		mneumonic = data.substring(10,16).trim();
		try{
			if(SymbolTable.table.find(this.mneumonic).getBytes() == 2){
				objectCode.setResistor(true);
			}	
		}catch(NullPointerException e){
			
		}
		
		//Operand
		try{
			operand = data.substring(18,28).trim();
		}catch(StringIndexOutOfBoundsException e){
			operand = data.substring(18).trim();	
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
		case "END":
			break;
		case "WORD":
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
				if(objectCode.isResistor()){
					objectCode.calcNI(false, false);
				}else{
					objectCode.calcNI(true, true);	
				}
				break;
			}
			}catch(NullPointerException e){
			}
			
			//Calculate Flags
			int temp = 0;
			if(objectCode.isExtended()){
				temp+=1;
			}else if(!objectCode.isImmediate() && SymbolTable.table.find(this.mneumonic).getBytes() != 2){
				temp+=2;
			}
			if(operand.contains(",X") && operand.length() != 3){
				temp+=8;
			}
			objectCode.setFlags(temp);
		
		}//end switch
	}//end Constructor
	
	public ObjectCode getObjectCode(){
		return objectCode;
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

	public String getMneumonic(){
		return mneumonic;
	}
	
	public String getOperand(){
		return operand;
	}
	
	public int getAddress(){
		return address;
	}
	
	public String getLabel(){
		return label;
	}
	public String getDisplayFormat(){
		if(status == -1){
			String addy = Integer.toHexString(address);
			while(addy.length() != 5){
				addy=("0"+addy);
			}
			String mnu = mneumonic;
			if(objectCode.isExtended()){
				mnu = "+"+mnu;
			}
			return String.format("%-6s%-15s%-8s%-7s%-7s%-10s",addy.toUpperCase(),objectCode.toString().toUpperCase(),label,mnu,operand,comment);
		}else{
			return ErrorTable.report(status);
		}
	}
	
	public boolean compareTo(Link link){
		return (this.address == link.address);
	}
}
