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
	public void initOptions() {
		this.addOption("TrackOption");
		this.addOption("VolumeOption");
		this.addOption("PanOption");
		
		this.currentOption = this.options.get(0);
	}

	@Override
	public void doAction(CrownRootObject co) {
		
		switch (currentOption) {
		case "TrackOption":
			if(co.ratchet_delta>0)
			craft.selectNextTrack();
			else
				craft.selectPreviousTrack();
			break;
		case "VolumeOption":
			craft.incVolumeCurrentTrack(co.delta);

			break;
		default:
			break;
		}
	}

}
