package Structures;

public class ObjectCode {
	
	private int optCode;
	private int flags;
	private int address;
	
	public ObjectCode(){
		optCode=-1;
		flags=-1;

	}	
	
	public String toString(){
		if(flags == optCode){
			return Integer.toHexString(address);
		}else{
			return (Integer.toHexString(optCode)+Integer.toHexString(flags)+Integer.toHexString(address));	
		}
	}
	
	public void setOptCode(int optCode){
		this.optCode = optCode;
	}
	
	public void calcNI(boolean indirect,boolean immediate){
		System.out.println("Optcode: "+optCode);
		if(indirect){
			System.out.print("indirect");
		}
		if(immediate){
			System.out.print("immediate\n");
		}
		if(indirect == true && immediate == true){
			optCode+= 3;
			System.out.println("OptCode: " +optCode);
		}else if(indirect && !immediate){
			optCode+= 2;
		}else if(immediate && !indirect){
			optCode+= 1;
		}
	}
	
	public void setFlags(int flags){
		this.flags = flags;
	}
	
	public void setAddress(int address){
		this.address = address;
	}
}
