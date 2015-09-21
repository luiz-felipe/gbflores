package br.ufrgs.enq.gbflores.termo;

//A simple Java example 
//javasci v2
//Filename: BasicExample.java

import org.scilab.modules.javasci.Scilab;

class BasicExample {

 public static void main(String[] args) {
     try {
         Scilab sci = new Scilab();
         sci.open();
         sci.exec("a=cos(%pi)*sin(%pi^2);");
     } catch (org.scilab.modules.javasci.JavasciException e) {
         System.err.println("Could not find variable type: " + e.getLocalizedMessage());
     }
}
}
