package com.logitech.craft.handlers;

import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import com.logitech.craft.Craft;
import com.logitech.craft.dataobjects.CrownRootObject;
import com.logitech.craft.mode.Mode;

public class TouchEventHandler extends Handler implements Observer {

	private static long touchedTime;
	private static Timer timerToSwitchToSelectToolMode;

	private static TimerTask timerTaskToSwitchToSelectToolMode;
	
	public TouchEventHandler(Craft craft) {
		super(craft);
		timerToSwitchToSelectToolMode = new Timer();
		timerTaskToSwitchToSelectToolMode = new TimerTask() {
			
			@Override
			public void run() {
				craft.setToolMode(true);
			}
		};
		
	}

	@Override
	public void handle(CrownRootObject co) {
		double elapesedTime = (System.currentTimeMillis() - touchedTime);

		if (co.touch_state == 1)
		{
			touchedTime = System.currentTimeMillis();
			timerToSwitchToSelectToolMode.schedule(timerTaskToSwitchToSelectToolMode, 1500);
		}
		else if (co.touch_state == 0 && elapesedTime < 200)
		{
			craft.getCurrentMode().nextOption();
		}

		
		
		if(co.touch_state == 0)
		{
			deactivateTimer();
			craft.setToolMode(false);
//			craft.setCurrentMode(craft.getSelectedToolMode());
			craft.switchTool(craft.getCurrentMode().getModeType().name());

		}
	}

	@Override
	public MessageTypes getMessageTypeHandler() {
		return MessageTypes.CROWNTOUCHEVENT_MESSAGETYPE;
	}

	private void deactivateTimer()
	{
		timerTaskToSwitchToSelectToolMode.cancel();
		timerToSwitchToSelectToolMode.cancel();	
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		
		deactivateTimer();
	}
	
}
