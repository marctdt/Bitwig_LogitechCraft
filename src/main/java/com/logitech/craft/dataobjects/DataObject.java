package com.logitech.craft.dataobjects;

import com.google.gson.Gson;
import com.logitech.craft.handlers.MessageTypes;

public abstract class DataObject {

	public String message_type;
	
	public MessageTypes getMessageType()
	{
		return MessageTypes.forName(message_type);
	}
	
	public String toJSON() {
		return new Gson().toJson(this);
	}
}
