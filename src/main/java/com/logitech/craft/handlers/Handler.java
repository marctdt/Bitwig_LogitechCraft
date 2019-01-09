package com.logitech.craft.handlers;

import com.logitech.craft.dataobjects.CrownRootObject;

public interface Handler {


	public void handle(CrownRootObject co);
	public MessageTypes getMessageTypeHandler(); 

}
