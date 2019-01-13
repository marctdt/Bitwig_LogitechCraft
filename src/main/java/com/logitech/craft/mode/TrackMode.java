package com.logitech.craft.mode;

import org.eclipse.jetty.util.statistic.RateStatistic;

import com.logitech.craft.Craft;
import com.logitech.craft.dataobjects.CrownRootObject;
import com.logitech.craft.handlers.TurnEventHandler;

public class TrackMode extends Mode{

	public TrackMode(Craft craft) {
		super(ModeType.TRACKMODE, craft);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doAction(CrownRootObject co) {
		
		if (co.message_type.equals(TurnEventHandler.TurnEventMessageType))
		switch (co.task_options.current_tool_option) {
		case "TrackOption":
			if(co.ratchet_delta>0)
			craft.selectNextTrack(co.ratchet_delta);
			else if(co.ratchet_delta < 0)
				craft.selectPreviousTrack(co.ratchet_delta);
			break;
		case "VolumeOption":
			craft.incVolumeCurrentTrack(co.delta);

			break;
		case "PanOption":
			craft.incPanCurrentTrack(co.delta);
			break;
		case "SendOption":
			craft.incSelectedSend(co.delta);
			break;
		case "SendSelectOption":
			craft.selectSend(co.ratchet_delta);
			break;
		default:
			break;
		}
	}

}
