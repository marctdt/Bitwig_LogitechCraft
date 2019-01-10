package com.logitech.craft.handlers;

import java.util.HashMap;
import java.util.Map;

import com.logitech.craft.Craft;
import com.logitech.craft.dataobjects.CrownRootObject;

public class CommandHandler {
	
	private Map<MessageTypes,Handler> handlers;
	private Craft craft;
	
	public CommandHandler(Craft craft) {

		this.craft =craft;
		handlers = new HashMap<MessageTypes, Handler>();
		handlers.put(MessageTypes.REGISTRATIONACK_MESSAGETYPE, new RegistrationHandler(craft));
		handlers.put(MessageTypes.CROWNTOUCHEVENT_MESSAGETYPE, new TouchEventHandler(craft));
		handlers.put(MessageTypes.CROWNTURNEVENT_MESSAGETYPE, new TurnEventHandler(craft));
		
		handlers.get(MessageTypes.CROWNTURNEVENT_MESSAGETYPE).addObserver((TouchEventHandler)handlers.get(MessageTypes.CROWNTOUCHEVENT_MESSAGETYPE));
	}

	public void execute(CrownRootObject co) throws IllegalAccessException
	{
		Handler handler = getHandler(co);
		if (handler!=null)
			handler.handle(co);
	}
	
	private Handler getHandler(CrownRootObject co)
	{
		return handlers.get(co.getMessageType());
	}
	
	public String getSessionId() throws IllegalAccessException{
		String v = ((RegistrationHandler)handlers.get(MessageTypes.REGISTRATIONACK_MESSAGETYPE)).session_id;
		if (v != null && !v.isEmpty())
			return v;
		throw new IllegalAccessException("No session ID has been registered");
	}
	
	
	
}
