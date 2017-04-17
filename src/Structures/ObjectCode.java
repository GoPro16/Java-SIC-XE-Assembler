package Structures;

/*
 * Container for all object code for each instruction
 */
public class ObjectCode {
	
	private int optCode;
	private int flags;
	private int address;
	private boolean extended;
	private boolean immediate;
	private boolean pc;
	private boolean resistor;
	
	public ObjectCode(){
		extended = false;
		immediate = false;
		pc = false;
		resistor = false;
		optCode=0;
		flags=0;
	}	
	
	public String toString(){
		if(optCode == flags){
			String temp = Integer.toHexString(address);
			while(temp.length()!=6){
				temp = ("0"+temp);
			}
			return temp;
		}else if(resistor){
			return(Integer.toHexString(optCode)+Integer.toHexString(address));
		}else{
			String tempObj = Integer.toHexString(optCode);
			while(tempObj.length() != 2){
				tempObj = ("0"+tempObj);
			}
			String tempAddy;
			if(address < 0){
				tempAddy = Integer.toHexString(address).substring(Integer.toHexString(address).length()-3).toUpperCase();	
			}else{
				tempAddy = Integer.toHexString(address);
			}
			if(extended){
				while(tempAddy.length() != 5){
					tempAddy = "0"+tempAddy;
				}
			}else if(!resistor){
				while(tempAddy.length() != 3){
					tempAddy = "0"+tempAddy;
				}
			}
			return (tempObj+Integer.toHexString(flags)+tempAddy);	
		}
	}
	
	public void setOptCode(int optCode){
		this.optCode = optCode;
	}
	
	public void calcNI(boolean indirect,boolean immediate){
		if(indirect == true && immediate == true){
			optCode+= 3;
		}else if(indirect && !immediate){
			optCode+= 2;
		}else if(immediate && !indirect){
			this.immediate = true;
			optCode+= 1;
		}
	}
	
	public void setFlags(int num){
		flags = num;
	}
	
	public void setAddress(int address){
		this.address = address;
	}
	public boolean isImmediate(){
		return immediate;
	}
	
	public void setExtended(boolean e){
		extended = e;
	}
	
	public boolean isExtended(){
		return extended;
	}
	
	public void setPc(boolean p){
		pc = p;
	}
	
	public void modifyPC(){
		flags= flags+2;
	}
	
	public boolean isResistor(){
		return resistor;
	}
	
	public void setResistor(boolean is){
		resistor = is;
	}
	
	public boolean isPC(){
		return pc;
	}
	
}
