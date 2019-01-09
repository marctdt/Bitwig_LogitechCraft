package com.logitech.craft.handlers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.logitech.craft.Craft;
import com.logitech.craft.dataobjects.CrownRootObject;
import com.logitech.craft.dataobjects.ToolChangeObject;

public class CommandHandler {
	
	private Map<MessageTypes,Handler> handlers;
	private Craft craft;
	
	public CommandHandler(Craft craft) {

		handlers = new HashMap<MessageTypes, Handler>();
		handlers.put(MessageTypes.REGISTRATIONACK_MESSAGETYPE, new HandleRegistration());
		this.craft =craft;
	}

	public void execute(CrownRootObject co)
	{
		getHandler(co).handle(co);
	}
	
	private Handler getHandler(CrownRootObject co)
	{
		return handlers.get(co.getMessageType());
	}
	
	public String getSessionId() {
		String v = ((HandleRegistration)handlers.get(MessageTypes.REGISTRATIONACK_MESSAGETYPE)).session_id;
		if (!v.isEmpty())
			return v;
		throw new IllegalAccessError("No session ID has been registered");
	}
	
	
	
}
