package com.logitech.craft.mode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.logitech.craft.Craft;
import com.logitech.craft.dataobjects.CrownRootObject;
import com.logitech.craft.dataobjects.ToolChangeObject;

public abstract class Mode {

	public enum ModeType {
		TRACKMODE, TRANSPORTMODE
		//, DEVICEMODE //remove it as not functionnal
		,BROWSERMODE
		,TEMPOMODE
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
