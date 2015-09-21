package br.ufrgs.enq.gbflores;

@SuppressWarnings("serial")
public class MissingCompoundException extends Exception {
	
	public MissingCompoundException(String message){
		super (message);
	}
	public MissingCompoundException(){
		super ();
	}
}
