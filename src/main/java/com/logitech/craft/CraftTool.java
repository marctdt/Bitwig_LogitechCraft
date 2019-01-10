package com.logitech.craft;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public enum CraftTool {
	
	TRANSPORT,
	TRACK,
	VOLUME,
	PAN,
	TEMPO,
	DEVICESELECTION,
	DEVICEPARAMETERSELECTION,
	DEVICEPARAMETER;
	
	private static final List<CraftTool> nameToValueMap =
	        new ArrayList<CraftTool>();

	    static {
	        for (CraftTool value : EnumSet.allOf(CraftTool.class)) {
	            nameToValueMap.add(value);
	        }
	    }

	    public static int getLength() {
	    	return nameToValueMap.size();
	    }
	    
	    public CraftTool getNext()
	    {
	    	
	    	return nameToValueMap.get((this.ordinal() + 1 ) % getLength());
	    }
	
	    public CraftTool getPrevious()
	    {
	    	int val = this.ordinal() - 1;
	    	return nameToValueMap.get(val < 0 ? getLength():val);
	    }

}
