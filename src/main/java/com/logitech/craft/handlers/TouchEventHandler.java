package com.logitech.craft.handlers;

import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import com.logitech.craft.Craft;
import com.logitech.craft.dataobjects.CrownRootObject;
import com.logitech.craft.mode.Mode;
import com.logitech.craft.mode.Mode.ModeType;

public class TouchEventHandler extends Handler implements Observer {


	public static final String TouchEventMessageType = "crown_touch_event";
	private static Timer timerToSwitchToSelectToolMode;

	private static TimerTask timerTaskToSwitchToSelectToolMode;

	public TouchEventHandler(Craft craft) {
		super(craft);

	}

	@Override
	public void handle(CrownRootObject co) throws IllegalAccessException {

		if (co.touch_state == 1) {
			activateTimer();
			craft.getCurrentMode().doAction(co);
			
		}

		if (co.touch_state == 0) {
			deactivateTimer();
			craft.setToolMode(false);
//			craft.setCurrentMode(craft.getSelectedToolMode());
//			craft.switchTool(craft.getCurrentMode().getModeType().name());

		}
	}

	@Override
	public MessageTypes getMessageTypeHandler() {
		return MessageTypes.CROWNTOUCHEVENT_MESSAGETYPE;
	}

	private void activateTimer() {

		timerToSwitchToSelectToolMode = new Timer();
		timerTaskToSwitchToSelectToolMode = new TimerTask() {

			@Override
			public void run() {
				craft.setToolMode(true);
			}
		};
		timerToSwitchToSelectToolMode.schedule(timerTaskToSwitchToSelectToolMode, 800);
	}

	private void deactivateTimer() {
		if (timerTaskToSwitchToSelectToolMode != null || timerToSwitchToSelectToolMode != null) {
			timerTaskToSwitchToSelectToolMode.cancel();
			timerToSwitchToSelectToolMode.cancel();
		}
		timerTaskToSwitchToSelectToolMode = null;
		timerToSwitchToSelectToolMode = null;
	}

	@Override
	public void update(Observable arg0, Object arg1) {

		deactivateTimer();
	}

}
