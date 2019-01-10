package com.logitech.craft.mode;

import org.eclipse.jetty.util.statistic.RateStatistic;

import com.logitech.craft.Craft;
import com.logitech.craft.dataobjects.CrownRootObject;

public class TrackMode extends Mode{

	public TrackMode(Craft craft) {
		super(ModeType.TRACKMODE, craft);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doAction(CrownRootObject co) {
		
		switch (co.task_options.current_tool_option) {
		case "TrackOption":
			if(co.ratchet_delta>0)
			craft.selectNextTrack();
			else if(co.ratchet_delta < 0)
				craft.selectPreviousTrack();
			break;
		case "VolumeOption":
			craft.incVolumeCurrentTrack(co.delta);

			break;
		case "PanOption":
			craft.incPanCurrentTrack(co.delta);
			break;
		default:
			break;
		}
	}

}
