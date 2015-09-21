package br.ufrgs.enq.gbflores.termo;

//A simple Java example with javasci v2
//Filename: DisplayPI.java

import org.scilab.modules.javasci.Scilab;
import org.scilab.modules.types.ScilabType;
import org.scilab.modules.types.ScilabDouble;

class Example1 {

 public static void main(String[] args) {

   try {
         Scilab sci = new Scilab();
         sci.open();
         sci.exec("disp(%pi);");

         ScilabDouble a = new ScilabDouble(3.14);
         sci.put("a",a);
         sci.exec("b=sin(a);");
         ScilabType b = sci.get("b");
         System.out.println("b = " + b);

         sci.close();

   } catch (org.scilab.modules.javasci.JavasciException e) {
         System.err.println("An exception occurred: " + e.getLocalizedMessage());
   }

 }
}