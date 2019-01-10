package com.logitech.craft.handlers;

import com.logitech.craft.Craft;
import com.logitech.craft.dataobjects.CrownRootObject;

public class RegistrationHandler extends Handler {

	public RegistrationHandler(Craft craft) {
		super(craft);
	}

	public String session_id;
	
	
	@Override
	public void handle(CrownRootObject co) {
		session_id = co.session_id;
		craft.initCraft();

	}

	@Override
	public MessageTypes getMessageTypeHandler() {
		return MessageTypes.REGISTRATIONACK_MESSAGETYPE;
	}
}
