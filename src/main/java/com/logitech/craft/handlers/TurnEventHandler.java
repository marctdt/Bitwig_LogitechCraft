package com.logitech.craft.handlers;

import com.logitech.craft.Craft;
import com.logitech.craft.dataobjects.CrownRootObject;
import com.logitech.craft.mode.Mode;
import com.logitech.craft.mode.ToolMode;

public class TurnEventHandler extends Handler{

	public TurnEventHandler(Craft craft) {
		super(craft);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(CrownRootObject co) {
		setChanged();
		this.notifyObservers();
		
		Mode selectedMode = craft.getCurrentMode();
		selectedMode.doAction(co);
	}

	@Override
	public MessageTypes getMessageTypeHandler() {
		return MessageTypes.CROWNTURNEVENT_MESSAGETYPE;
	}

}
