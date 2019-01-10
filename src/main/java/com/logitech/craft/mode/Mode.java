package com.logitech.craft.mode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.logitech.craft.Craft;
import com.logitech.craft.dataobjects.CrownRootObject;
import com.logitech.craft.dataobjects.ToolChangeObject;

public abstract class Mode {

	protected List<String> options;
	protected String currentOption;
	public enum ModeType {
		TOOLMODE, TRACKMODE, TRANSPORTMODE
	}
	

	protected Craft craft;

	public Mode(ModeType mode, Craft craft) {
		this.craft = craft;
		this.modeName = mode;
		
		options=new ArrayList<String>();
		initOptions();
	}

	private final ModeType modeName;

	public ModeType getModeType() {
		return modeName;
	}
	
	public abstract void initOptions();
	protected void addOption(String option) {
		options.add(option);
	}
	
	
	public void nextOption() {
		int i = options.indexOf(currentOption);
		currentOption = options.get((i + 1) % options.size());
		craft.switchTool(currentOption);
	}

	public void previousOption() {
		int newi = options.indexOf(currentOption) - 1;
		currentOption = options.get(newi < 0 ? options.size() : newi);
		craft.switchTool(currentOption);
	}
	
	public String getCurrentOption()
	{
		return currentOption;
	}

	public abstract void doAction(CrownRootObject co);
	
}
