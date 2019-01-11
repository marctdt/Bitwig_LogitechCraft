package com.logitech.craft.mode;

import com.logitech.craft.Craft;
import com.logitech.craft.dataobjects.CrownRootObject;
import com.logitech.craft.handlers.TouchEventHandler;
import com.logitech.craft.handlers.TurnEventHandler;

public class TempoMode extends Mode {

	public TempoMode(Craft craft) {
		super(Mode.ModeType.TEMPOMODE, craft);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doAction(CrownRootObject co) throws IllegalAccessException {

		if (co.message_type.equals(TurnEventHandler.TurnEventMessageType))
		switch (co.task_options.current_tool_option) {
		case "TempoOption":
			
			craft.incTempo(co.delta);
			break;
		default:
			break;
		}
		else if(co.message_type.equals(TouchEventHandler.TouchEventMessageType))
			craft.tapTempo();
	}

}
