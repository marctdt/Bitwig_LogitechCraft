package com.logitech.craft.handlers;

import com.logitech.craft.Craft;
import com.logitech.craft.dataobjects.CrownRootObject;
import com.logitech.craft.mode.Mode;
import com.logitech.craft.mode.ToolMode;
import com.logitech.craft.mode.Mode.ModeType;

public class TurnEventHandler extends Handler{

	public static final String TurnEventMessageType = "crown_turn_event";
	
	public TurnEventHandler(Craft craft) {
		super(craft);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(CrownRootObject co) throws IllegalAccessException {
		setChanged();
		this.notifyObservers();
		
		Mode selectedMode = craft.isToolModeEnabled()?craft.getToolMode() : craft.getMode(ModeType.valueOf(co.task_options.current_tool));
		selectedMode.doAction(co);
	}

	@Override
	public MessageTypes getMessageTypeHandler() {
		return MessageTypes.CROWNTURNEVENT_MESSAGETYPE;
	}

}
