package com.logitech.craft.dataobjects;

import com.google.gson.Gson;
import com.logitech.craft.handlers.MessageTypes;

public abstract class DataObject {

	public String message_type;
	
	public MessageTypes getMessageType()
	{
		MessageTypes mt = MessageTypes.forText(message_type);
		if (mt != null)
			return mt;
		throw new NullPointerException("Cannot find the handler");
	}
	
	public String toJSON() {
		return new Gson().toJson(this);
	}
}
