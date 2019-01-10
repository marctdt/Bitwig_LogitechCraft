package com.logitech.craft.handlers;

import com.logitech.craft.Craft;
import com.logitech.craft.dataobjects.CrownRootObject;

public class TouchEventHandler extends Handler {

	private static long touchedTime;
	public TouchEventHandler(Craft craft) {
		super(craft);
	}

	@Override
	public void handle(CrownRootObject co) {
		double seconds = (System.nanoTime() - touchedTime) / 1_000_000_000.0;
		
		if (co.touch_state == 1)
			touchedTime = System.nanoTime();
		
		
		else if (co.touch_state == 0 && seconds < 1)
		craft.selectNextTool();
	}

	@Override
	public MessageTypes getMessageTypeHandler() {
		return MessageTypes.CROWNTOUCHEVENT_MESSAGETYPE;
	}

}
