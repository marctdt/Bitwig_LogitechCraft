package com.logitech.craft.mode;

import com.logitech.craft.Craft;
import com.logitech.craft.dataobjects.CrownRootObject;

public class TempoMode extends Mode {

	public TempoMode(Craft craft) {
		super(Mode.ModeType.TEMPOMODE, craft);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doAction(CrownRootObject co) throws IllegalAccessException {

		switch (co.task_options.current_tool_option) {
		case "TempoOption":
			
			craft.incTempo(co.delta);
			break;
		default:
			break;
		}
	}

}
