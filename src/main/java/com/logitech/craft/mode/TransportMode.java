package com.logitech.craft.mode;

import com.logitech.craft.Craft;
import com.logitech.craft.dataobjects.CrownRootObject;

public class TransportMode extends Mode{

	public TransportMode(Craft craft) {
		super(ModeType.TRANSPORTMODE, craft);
	}


	@Override
	public void doAction(CrownRootObject co) {
		
		switch (co.task_options.current_tool_option) {
		case "PositionOption":
			craft.incTransportPosition( co.ratchet_delta);
			break;
		case "TempoOption":
			craft.incTempo((double)co.delta);
			break;

		default:
			break;
		}
	}

}
