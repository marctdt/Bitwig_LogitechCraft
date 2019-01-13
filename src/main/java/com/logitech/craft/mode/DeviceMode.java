package com.logitech.craft.mode;

import com.logitech.craft.Craft;
import com.logitech.craft.dataobjects.CrownRootObject;
import com.logitech.craft.handlers.TurnEventHandler;

public class DeviceMode extends Mode {

	public DeviceMode(Craft craft) {
		super(Mode.ModeType.DEVICEMODE, craft);
//		super(null, craft); //STOMB
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doAction(CrownRootObject co) throws IllegalAccessException {

		if (co.message_type.equals(TurnEventHandler.TurnEventMessageType))
			switch (co.task_options.current_tool_option) {
			case "DeviceOption":

				if (co.ratchet_delta > 0)
					craft.selectNextDevice(co.ratchet_delta);
				else if (co.ratchet_delta < 0)
					craft.selectPreviousDevice(co.ratchet_delta);
				break;
			case "ParameterOption":
				craft.incSelectedDeviceParametr(co.delta);
				break;
			case "DeviceParameterOption":
				if (co.ratchet_delta > 0)
					craft.selectNextDeviceParameter(co.ratchet_delta);
				else if (co.ratchet_delta < 0)
					craft.selectPreviousDeviceParameter(co.ratchet_delta);
				break;
			case "DeviceBankOption":
				if (co.ratchet_delta > 0)
					craft.selectNextDeviceParameterBank(co.ratchet_delta);
				else if (co.ratchet_delta < 0)
					craft.selectPreviousDeviceParameterBank(co.ratchet_delta);
				break;

			default:
				break;
			}
	}

}
