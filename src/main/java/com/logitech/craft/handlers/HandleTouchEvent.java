package com.logitech.craft.handlers;

import com.bitwig.extension.controller.api.ControllerHost;
import com.logitech.craft.Craft;
import com.logitech.craft.dataobjects.CrownRootObject;

public class HandleTouchEvent implements Handler{

	Craft craft;
	
	public HandleTouchEvent(Craft craft) {
		this.craft = craft;
    }

	@Override
	public void handle(CrownRootObject co) {
		craft.selectNextTool();
	}

	@Override
	public MessageTypes getMessageTypeHandler() {
		return MessageTypes.CROWNTOUCHEVENT_MESSAGETYPE;
	}


}
