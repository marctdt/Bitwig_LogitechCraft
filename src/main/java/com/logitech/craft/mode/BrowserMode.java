package com.logitech.craft.mode;

import com.logitech.craft.Craft;
import com.logitech.craft.dataobjects.CrownRootObject;

public class BrowserMode extends Mode {

	public BrowserMode(Craft craft) {
		super(Mode.ModeType.BROWSERMODE, craft);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doAction(CrownRootObject co) throws IllegalAccessException {

		switch (co.task_options.current_tool_option) {
		case "SelectInBrowserOption":
			if (co.ratchet_delta > 0)
				craft.selectNextDeviceInBrowser(co.ratchet_delta);
			else if (co.ratchet_delta < 0)
				craft.selectPreviousDeviceInBrowser(co.ratchet_delta);
			
			break;
		default:
			break;
		}
	}

}
