package br.ufrgs.enq.gbflores;

import java.util.Comparator;
public class Sort implements Comparator<Component> {
	public int compare(Component comp1,Component comp2)
	{
		if(comp1.getBoilingPoint()==comp2.getBoilingPoint())
			return 0;
		else if(comp1.getBoilingPoint()<comp2.getBoilingPoint())
			return -1;
		else
			return 1;
	}    		
}
