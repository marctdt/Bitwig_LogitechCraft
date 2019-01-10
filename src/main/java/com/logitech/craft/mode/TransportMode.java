package com.logitech.craft.mode;

import com.logitech.craft.Craft;
import com.logitech.craft.dataobjects.CrownRootObject;

public class TransportMode extends Mode{

	public TransportMode(Craft craft) {
		super(ModeType.TRANSPORTMODE, craft);
	}

	@Override
	public void initOptions() {
		this.options.add("PositionOption");
		this.options.add("tempoOption");
		this.currentOption=options.get(0);
	}

	@Override
	public void doAction(CrownRootObject co) {
		
		switch (currentOption) {
		case "PositionOption":
			craft.incTransportPosition((double) co.delta / 4);
			break;
		case "tempoOption":
			craft.incTempo((double)co.delta/4);
			break;

		default:
			break;
		}
	}

}
