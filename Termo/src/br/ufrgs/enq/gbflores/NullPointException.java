package br.ufrgs.enq.gbflores;

@SuppressWarnings("serial")
public class NullPointException extends Exception {
	
	public NullPointException(String message){
		super (message);
	}
	public NullPointException(){
		super ();
	}
}
