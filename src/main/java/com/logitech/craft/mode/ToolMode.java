package com.logitech.craft.mode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.logitech.craft.Craft;
import com.logitech.craft.dataobjects.CrownRootObject;

public class ToolMode extends Mode {



	private List<ModeType> modes;

	public ToolMode(Craft craft ) {
		super(null,craft);
		modes = Arrays.asList(ModeType.values());

	}

	@Override
	public void doAction(CrownRootObject co) throws IllegalAccessException {
		
		String currentMode = co.task_options.current_tool;
		if(co.ratchet_delta>0)
			nextMode(currentMode);
		else if(co.ratchet_delta<0)
			previousMode(currentMode);
	}

	public void nextMode(String currentMode) throws IllegalAccessException {
		int i = modes.indexOf(ModeType.valueOf(currentMode));
		currentMode = modes.get((i + 1) % modes.size()).name();
		craft.switchTool(currentMode);
	}

	public void previousMode(String currentMode) throws IllegalAccessException {
		int newi = modes.indexOf(ModeType.valueOf(currentMode)) - 1;
		currentMode = modes.get(newi < 0 ? modes.size() -1: newi).name();
		craft.switchTool(currentMode);
	}
	
}
