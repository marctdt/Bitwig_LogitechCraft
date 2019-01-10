package com.logitech.craft.handlers;

import com.logitech.craft.Craft;
import com.logitech.craft.CraftTool;
import com.logitech.craft.dataobjects.CrownRootObject;

public class TurnEventHandler extends Handler {

	public TurnEventHandler(Craft craft) {
		super(craft);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(CrownRootObject co) {

		craft.setIsChangingToolMode(false);
		switch (craft.getCurrentTool()) {
		case TRACK:
			if (co.ratchet_delta > 0)
				craft.selectNextTrack();
			else
				craft.selectPreviousTrack();

			break;
		case TRANSPORT:
			craft.incTransportPosition((double) co.delta / 4);
			break;

		default:
			break;
		}

//		if(co.delta>0)
//			craft.transportForward();
//		else
//			craft.transportrewind();
	}

	@Override
	public MessageTypes getMessageTypeHandler() {
		return MessageTypes.CROWNTURNEVENT_MESSAGETYPE;
	}

}
