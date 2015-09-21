package br.ufrgs.enq.gbflores;

///////////////////////////////////////////////////////////////////////////
////
//Program file name: Integral.java                                      //
////
//© Tao Pang 2006                                                       //
////
//Last modified: January 18, 2006                                       //
////
//(1) This Java program is part of the book, "An Introduction to        //
//Computational Physics, 2nd Edition," written by Tao Pang and      //
//published by Cambridge University Press on January 19, 2006.      //
////
//(2) No warranties, express or implied, are made for this program.     //
////
///////////////////////////////////////////////////////////////////////////

public class Integral {
	double h;
	int n;
	double y[];
	
	public Integral(double y[], double h){
		this.y = new double [y.length];
		this.y = y;
		this.h = h;
		n = y.length-1;
	}
	
	//Method to achieve the evenly spaced Simpson rule.
//	public double simpson(double y[], double h) {
	public double simpson() {
		double s0 = 0, s1 = 0, s2 = 0;
		for (int i=1; i<n; i+=2) {
			s0 += y[i-1];
			s1 += y[i];
			s2 += y[i+1];
		}
		double s = (s0+4*s1+s2)/3;

		// Add the last slice separately for an even n+1
		if ((n+1)%2 == 0)
			return h*(s+(5*y[n]+8*y[n-1]-y[n-2])/12);
		else
			return h*s;
	}
}