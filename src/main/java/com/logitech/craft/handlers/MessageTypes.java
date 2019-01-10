package com.logitech.craft.handlers;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum MessageTypes {
	 REGISTRATIONACK_MESSAGETYPE ( "register_ack"),
	 REGISTRATION_MESSAGETYPE ( "register"),
	 TOOLCHANGE_MESSAGETYPE ( "tool_change"),
	 ENABLEPLUGIN_MESSAGETYPE ( "enable_plugin"),
	 ACTIVATEPLUGIN_MESSAGETYPE ( "activate_plugin"),
	 DEACTIVATEPLUGIN_MESSAGETYPE ( "deactivate_plugin"),
	 CROWNTOUCHEVENT_MESSAGETYPE ( "crown_touch_event"),
	 CROWNTURNEVENT_MESSAGETYPE ( "crown_turn_event");

	private final String text;

	private MessageTypes(final String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		return text;
} 
	
	
	
	private static final Map<String, MessageTypes> nameToTextMap =
	        new HashMap<String, MessageTypes>();

	    static {
	        for (MessageTypes value : EnumSet.allOf(MessageTypes.class)) {
	            nameToTextMap.put(value.text, value);
	        }
	    }

	    public static MessageTypes forText(String text) {
	        return nameToTextMap.get(text);
	    }
	
}