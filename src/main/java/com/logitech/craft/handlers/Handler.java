package com.logitech.craft.handlers;

import java.util.Observable;

import com.logitech.craft.Craft;
import com.logitech.craft.dataobjects.CrownRootObject;

public abstract class Handler extends Observable{

	public Craft craft;
	
	public Handler(Craft craft) {
		this.craft = craft;
	}

	public abstract void handle(CrownRootObject co);

	public abstract MessageTypes getMessageTypeHandler();

}
