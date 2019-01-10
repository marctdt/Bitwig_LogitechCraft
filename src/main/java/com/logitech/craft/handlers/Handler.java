package com.logitech.craft.handlers;

import com.logitech.craft.Craft;
import com.logitech.craft.dataobjects.CrownRootObject;

public abstract class Handler {

	public Craft craft;
	
	public Handler(Craft craft) {
		this.craft = craft;
	}

	public abstract void handle(CrownRootObject co);

	public abstract MessageTypes getMessageTypeHandler();

}
