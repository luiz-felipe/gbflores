package br.ufrgs.enq.gbflores.termo;

import org.scilab.modules.javasci.JavasciException;
import org.scilab.modules.javasci.Scilab;
import org.scilab.modules.types.ScilabDouble;
import org.scilab.modules.types.ScilabType;

public class TestScilab {

	public TestScilab() throws JavasciException {
		Scilab sci = new Scilab();
		sci.open();
		sci.exec("disp(%pi);");
		ScilabDouble a = new ScilabDouble(3.14);
		sci.put("a",a);
		sci.exec("b=sin(a);");
		ScilabType b = sci.get("b");
		System.out.println("b = " + b);
		}
	
	
	public static void main(String[] args) throws JavasciException {
		new TestScilab();
	}
}

