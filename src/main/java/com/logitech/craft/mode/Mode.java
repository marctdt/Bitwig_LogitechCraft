package com.logitech.craft.mode;

import com.logitech.craft.Craft;
import com.logitech.craft.dataobjects.CrownRootObject;

public abstract class Mode {

	public enum ModeType {
		TRANSPORTMODE,
		TRACKMODE
		, DEVICEMODE //remove it as not functionnal
		,TEMPOMODE
		,BROWSERMODE
	}
	

	protected Craft craft;

	public Mode(ModeType mode, Craft craft) {
		this.craft = craft;
		this.modeName = mode;
		
	}

	private final ModeType modeName;

	public ModeType getModeType() {
		return modeName;
	}
	
	public abstract void doAction(CrownRootObject co) throws IllegalAccessException;
	
}
