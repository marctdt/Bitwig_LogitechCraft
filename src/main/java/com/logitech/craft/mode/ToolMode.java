package com.logitech.craft.mode;

import java.util.HashMap;
import java.util.Map;

import com.logitech.craft.Craft;
import com.logitech.craft.dataobjects.CrownRootObject;

public class ToolMode extends Mode {


	private Map<String, ModeType> mappingOptionsMode;

	public ToolMode(Craft craft ) {
		super(Mode.ModeType.TOOLMODE,craft);
		mappingOptionsMode = new HashMap<String, Mode.ModeType>();
	}

	@Override
	public void initOptions() {
		this.addOption("TrackOption");
		mappingOptionsMode.put("TrackOption",ModeType.TRACKMODE);
		this.addOption("TransportOption");
		mappingOptionsMode.put("TransportOption",ModeType.TRANSPORTMODE);
		
		currentOption = options.get(0);
	}
	
	private ModeType getModeFromToolName(String toolname) {
		return mappingOptionsMode.get(toolname);
	}
	public ModeType getSelectedMode() {
	return getModeFromToolName(currentOption);
	}

	@Override
	public void doAction(CrownRootObject co) {
		
		if(co.ratchet_delta>0)
			nextOption();
		else
			previousOption();
	}
}
