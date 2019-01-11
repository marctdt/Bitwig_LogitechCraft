package com.logitech.craft;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import com.bitwig.extension.callback.DoubleValueChangedCallback;
import com.bitwig.extension.controller.api.Browser;
import com.bitwig.extension.controller.api.ControllerHost;
import com.bitwig.extension.controller.api.CursorDevice;
import com.bitwig.extension.controller.api.CursorTrack;
import com.bitwig.extension.controller.api.DeviceBank;
import com.bitwig.extension.controller.api.PinnableCursorDevice;
import com.bitwig.extension.controller.api.PopupBrowser;
import com.bitwig.extension.controller.api.Transport;
import com.google.gson.Gson;
import com.logitech.connectivity.CraftSocketConnection;
import com.logitech.craft.dataobjects.CrownRegisterRootObject;
import com.logitech.craft.dataobjects.CrownRootObject;
import com.logitech.craft.dataobjects.ToolChangeObject;
import com.logitech.craft.dataobjects.ToolOption;
import com.logitech.craft.dataobjects.ToolUpdateRootObject;
import com.logitech.craft.handlers.CommandHandler;
import com.logitech.craft.mode.BrowserMode;
import com.logitech.craft.mode.DeviceMode;
import com.logitech.craft.mode.Mode;
import com.logitech.craft.mode.Mode.ModeType;
import com.logitech.craft.mode.ToolMode;
import com.logitech.craft.mode.TrackMode;
import com.logitech.craft.mode.TransportMode;

public class Craft implements Observer {

	private ControllerHost host;
	private CraftSocketConnection craftWSClient;

	private CursorTrack cursorTrack;
	private Transport transport;
	private PinnableCursorDevice cursorDevice;
//	private DeviceBank deviceBank;
	private PopupBrowser deviceBrowser;

	private CommandHandler commandHandler;

	public final static String PLUGIN_GUID = "5379c471-be4e-4685-b197-d146728368a0";
	public final static String PLUGIN_EXECNAME = "Bitwig Studio.exe";

	private Map<Mode.ModeType, Mode> modes;
	private ToolMode toolMode;

	public Craft(ControllerHost host) {
		this.host = host;

		commandHandler = new CommandHandler(this);

		modes = new HashMap<Mode.ModeType, Mode>();
		modes.put(ModeType.TRACKMODE, new TrackMode(this));
		modes.put(Mode.ModeType.TRANSPORTMODE, new TransportMode(this));
		// modes.put(ModeType.DEVICEMODE, new DeviceMode(this));
		modes.put(ModeType.BROWSERMODE, new BrowserMode(this));
		toolMode = new ToolMode(this);
		initViews();
	}

	public ToolMode getToolMode() {
		return toolMode;
	}

	public void initViews() {

		transport = host.createTransport();

		cursorTrack = host.createCursorTrack(0, 0);

		cursorDevice = cursorTrack.createCursorDevice();

//		deviceBank = cursorTrack.createDeviceBank(0);

		deviceBrowser = host.createPopupBrowser();

//		cursor.addIsSelectedInEditorObserver(new BooleanValueChangedCallback() {
//			@Override
//			public void valueChanged(boolean newValue) {
//					try {
//						toolChange(CraftTool.TRACK);
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						host.showPopupNotification("Cannot change the tool");
//					}
//					catch (IllegalAccessError e)
//					{
//						
//					}
//			}
//		});

		transport.getPosition().addValueObserver(new DoubleValueChangedCallback() {

			@Override
			public void valueChanged(double newValue) {
				try {
					ReportToolOptionDataValueChange(ModeType.TRANSPORTMODE, ModeType.TRANSPORTMODE.name(),
							transport.getPosition().getFormatted());
				} catch (IllegalAccessException | IOException e) {
				}

			}
		});

	}

	public void ConnectionToCraftDevice() {
		try {
			CrownRegisterRootObject registerRequest = new CrownRegisterRootObject();
			registerRequest.message_type = "register";
			registerRequest.plugin_guid = PLUGIN_GUID;
			registerRequest.execName = PLUGIN_EXECNAME;
			registerRequest.PID = Integer.parseInt(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);

			craftWSClient = new CraftSocketConnection(host, registerRequest.toJSON());

			craftWSClient.addObserver(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			host.println("Cannot connect to Craft! Make sure it is connected");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			host.println("Cannot connect to Craft! Make sure it is connected");
			host.println(e.getMessage());
		}
	}

	@Override
	public void update(Observable arg0, Object message) {
		// TODO Auto-generated method stub
		CrownRootObject co = new Gson().fromJson(message.toString(), CrownRootObject.class);
		host.println("execute: " + co.message_type);

		try {
			commandHandler.execute(co);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			host.println("Couldn't execute the task");
		}
	}

	public CraftSocketConnection getCraftWSCLient() {
		return craftWSClient;
	}

	public void sendMessagetoCraft(String JSONMessage) throws IOException {
		craftWSClient.sendToDevice(JSONMessage);
	}

	public void incTransportPosition(int value) {

//		transport.incPosition(calculateCenteredValue(value)*256, true);
		transport.incPosition(value, true);

	}

	public void transportForward() {
		transport.fastForward();
	}

	public void transportrewind() {
		transport.rewind();
	}

	public void selectNextTrack(int repeatTime) {
		for (int i = 0; i < Math.abs(repeatTime); i++)
			cursorTrack.selectNext();
	}

	public void selectPreviousTrack(int repeatTime) {
		for (int i = 0; i < Math.abs(repeatTime); i++)
			cursorTrack.selectPrevious();
	}

	public void ReportToolOptionDataValueChange(Mode.ModeType tool, String toolOption, String value)
			throws IOException, IllegalAccessException {
		ToolUpdateRootObject toolUpdateRootObject = new ToolUpdateRootObject();
		toolUpdateRootObject.tool_id = tool.name();
		toolUpdateRootObject.message_type = "tool_update";
		toolUpdateRootObject.session_id = commandHandler.getSessionId();
		toolUpdateRootObject.show_overlay = "true";
		toolUpdateRootObject.tool_options = new ArrayList<ToolOption>();
		ToolOption tool_option = new ToolOption();
		tool_option.name = toolOption;
		tool_option.value = value;
		toolUpdateRootObject.tool_options.add(tool_option);

		craftWSClient.sendToDevice(toolUpdateRootObject.toJSON());
		host.println("MyWebSocket.ReportToolOptionDataValueChange - Tool:" + tool.toString() + ", Tool option:"
				+ toolOption + ", Value:" + value);
	}

	public String getSessionId() throws IllegalAccessException {
		return commandHandler.getSessionId();
	}

	public void showPopupNotification(String string) {
		host.showPopupNotification(string);
	}

	public Mode getMode(Mode.ModeType mode) {
		return modes.get(mode);
	}

	private boolean isToolModeEnabled;

	public void setToolMode(boolean enable) {
		isToolModeEnabled = enable;
	}

	public boolean isToolModeEnabled() {
		return isToolModeEnabled;
	}

	public void switchTool(String option) throws IllegalAccessException {
		ToolChangeObject toolChangeObject = new ToolChangeObject();
		toolChangeObject.message_type = "tool_change";
		toolChangeObject.session_id = getSessionId();
		toolChangeObject.tool_id = option;
		try {
			sendMessagetoCraft(toolChangeObject.toJSON());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			showPopupNotification("Couldn't change the Tool");
		}
	}

	public void incTempo(double d) {
		transport.tempo().inc(calculateCenteredValue(d));
	}

	public void initCraft() {

		try {
			switchTool(ModeType.TRACKMODE.name());
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void incVolumeCurrentTrack(double delta) {

		cursorTrack.volume().inc(calculateCenteredValue(delta));
	}

	public void incPanCurrentTrack(double delta) {
		cursorTrack.pan().inc(calculateCenteredValue(delta));

	}

	public double calculateCenteredValue(double delta) {
		if (delta < 0)
			return -calculateCenteredValue(-delta);
		return 1 - (1 / (delta / 1024 + 1));
	}

	public void selectNextDevice(int repeatTime) {
		for (int i = 0; i < Math.abs(repeatTime); i++)
			cursorDevice.selectNext();
	}

	public void selectPreviousDevice(int repeatTime) {
		for (int i = 0; i < Math.abs(repeatTime); i++)
			cursorDevice.selectPrevious();
	}

	public void incSelectedDeviceParametr(int delta) {

		cursorTrack.createDeviceBank(0).getDevice(0).createCursorRemoteControlsPage(2).getParameter(0)
				.inc(calculateCenteredValue(delta));
	}

	public void selectNextDeviceParameterBank(int repeatTime) {
		for (int i = 0; i < Math.abs(repeatTime); i++)
			cursorDevice.nextParameterPage();

	}

	public void selectPreviousDeviceParameterBank(int repeatTime) {
		for (int i = 0; i < Math.abs(repeatTime); i++)
			cursorDevice.previousParameterPage();
	}

	public void selectNextDeviceParameter(int repeatTime) {
		for (int i = 0; i < Math.abs(repeatTime); i++)
			cursorTrack.createDeviceBank(0).getDevice(0).createCursorRemoteControlsPage(0).selectNext();
	}

	public void selectPreviousDeviceParameter(int repeatTime) {

		for (int i = 0; i < Math.abs(repeatTime); i++)
			cursorTrack.createDeviceBank(0).getDevice(0).createCursorRemoteControlsPage(0).selectPrevious();
	}

	public void selectNextDeviceInBrowser(int repeatTime) {

		for (int i = 0; i < Math.abs(repeatTime); i++)
			deviceBrowser.selectNextFile();

	}

	public void selectPreviousDeviceInBrowser(int repeatTime) {
		for (int i = 0; i < Math.abs(repeatTime); i++) {
			deviceBrowser.selectPreviousFile();
		}

	}
}
