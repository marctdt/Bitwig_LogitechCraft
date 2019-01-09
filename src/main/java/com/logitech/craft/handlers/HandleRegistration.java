package com.logitech.craft.handlers;

import com.logitech.craft.dataobjects.CrownRootObject;

public class HandleRegistration implements Handler {

	public String session_id;
	
	@Override
	public void handle(CrownRootObject co) {
		session_id = co.session_id;
	}

	@Override
	public MessageTypes getMessageTypeHandler() {
		return MessageTypes.REGISTRATIONACK_MESSAGETYPE;
	}
}
