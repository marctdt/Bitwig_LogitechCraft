package com.logitech.craft;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import com.bitwig.extension.callback.DoubleValueChangedCallback;
import com.bitwig.extension.controller.api.ControllerHost;
import com.bitwig.extension.controller.api.CursorTrack;
import com.bitwig.extension.controller.api.Transport;
import com.google.gson.Gson;
import com.logitech.connectivity.CraftSocketConnection;
import com.logitech.craft.dataobjects.CrownRegisterRootObject;
import com.logitech.craft.dataobjects.CrownRootObject;
import com.logitech.craft.dataobjects.ToolChangeObject;
import com.logitech.craft.dataobjects.ToolOption;
import com.logitech.craft.dataobjects.ToolUpdateRootObject;
import com.logitech.craft.handlers.CommandHandler;
import com.logitech.craft.mode.Mode;
import com.logitech.craft.mode.Mode.ModeType;
import com.logitech.craft.mode.ToolMode;
import com.logitech.craft.mode.TrackMode;

public class Craft implements Observer {

	private ControllerHost host;
	private CraftSocketConnection craftWSClient;

	private CursorTrack cursor;
	private Transport transport;

	private CommandHandler commandHandler;

	public final static String PLUGIN_GUID = "5379c471-be4e-4685-b197-d146728368a0";
	public final static String PLUGIN_EXECNAME = "Bitwig Studio.exe";

	private Map<Mode.ModeType, Mode> modes;

	public Craft(ControllerHost host) {
		this.host = host;

		commandHandler = new CommandHandler(this);

		modes = new HashMap<Mode.ModeType, Mode>();
		modes.put(Mode.ModeType.TOOLMODE, new ToolMode(this));
		modes.put(ModeType.TRACKMODE, new TrackMode(this));
		initViews();
	}

	public void initViews() {

		transport = host.createTransport();

		cursor = host.createCursorTrack(0, 0);

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
				ReportToolOptionDataValueChange(ModeType.TRANSPORTMODE, ModeType.TRANSPORTMODE.name(),
						transport.getPosition().getFormatted());

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

		commandHandler.execute(co);
	}

	public CraftSocketConnection getCraftWSCLient() {
		return craftWSClient;
	}

	public void sendMessagetoCraft(String JSONMessage) throws IOException {
		craftWSClient.sendToDevice(JSONMessage);
	}

	public void incTransportPosition(double value) {

		transport.incPosition(value, false);

	}

	public void transportForward() {
		transport.fastForward();
	}

	public void transportrewind() {
		transport.rewind();
	}

	public void selectNextTrack() {
		cursor.selectNext();
	}

	public void selectPreviousTrack() {

		cursor.selectPrevious();
	}

	public void ReportToolOptionDataValueChange(Mode.ModeType tool, String toolOption, String value) {
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

		try {
			craftWSClient.sendToDevice(toolUpdateRootObject.toJSON());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		host.println("MyWebSocket.ReportToolOptionDataValueChange - Tool:" + tool.toString() + ", Tool option:"
				+ toolOption + ", Value:" + value);
	}

	public String getSessionId() {
		return commandHandler.getSessionId();
	}

	public void showPopupNotification(String string) {
		host.showPopupNotification(string);
	}

	public Mode getMode(Mode.ModeType mode) {
		return modes.get(mode);
	}

	public Mode getCurrentMode() {
		if (isToolModeEnabled)
			return getMode(ModeType.TOOLMODE);

		ToolMode toolmode = (ToolMode) modes.get(ModeType.TOOLMODE);
		ModeType selectedMode = toolmode.getSelectedMode();
		return getMode(selectedMode);
	}

	private boolean isToolModeEnabled;

	public void setToolMode(boolean enable) {
		isToolModeEnabled = enable;
	}

	public void switchTool(String option) {
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
		transport.tempo().inc(d);
	}

	public void initCraft() {

		
	}

	public void incVolumeCurrentTrack(double delta) {
		
		cursor.volume().inc(delta);
	}

}
