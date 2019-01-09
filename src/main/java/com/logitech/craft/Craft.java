package com.logitech.craft;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.Observable;
import java.util.Observer;

import com.bitwig.extension.callback.BooleanValueChangedCallback;
import com.bitwig.extension.controller.api.ControllerHost;
import com.bitwig.extension.controller.api.CursorTrack;
import com.bitwig.extension.controller.api.Transport;
import com.google.gson.Gson;
import com.logitech.connectivity.CraftSocketConnection;
import com.logitech.craft.dataobjects.CrownRegisterRootObject;
import com.logitech.craft.dataobjects.CrownRootObject;
import com.logitech.craft.dataobjects.ToolChangeObject;
import com.logitech.craft.handlers.CommandHandler;

public class Craft implements Observer {

	private ControllerHost host;
	private CraftSocketConnection craftWSClient;

	private CursorTrack cursor;
	private Transport transport;

	private CommandHandler commandHandler;

	public final static String PLUGIN_GUID = "5379c471-be4e-4685-b197-d146728368a0";
	public final static String PLUGIN_EXECNAME = "Bitwig Studio.exe";
	
	private CraftTool currentTool;

	public Craft(ControllerHost host) {
		// TODO Auto-generated constructor stub
		this.host = host;

		commandHandler = new CommandHandler(this);

		initViews();
	}

	public void initViews() {
		transport = host.createTransport();

		cursor = host.createCursorTrack(0, 0);
		cursor.addIsSelectedInEditorObserver(new BooleanValueChangedCallback() {

			@Override
			public void valueChanged(boolean newValue) {
					toolChange(CraftTool.TRACK);
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
		host.println(co.message_type);

		commandHandler.execute(co);
	}

	public CraftSocketConnection getCraftWSCLient() {
		return craftWSClient;
	}

	public void sendMessagetoCraft(String JSONMessage) throws IOException {
		craftWSClient.sendToDevice(JSONMessage);
	}
	
	 public void toolChange(CraftTool context) throws IOException
     {
             ToolChangeObject toolChangeObject = new ToolChangeObject();
             toolChangeObject.message_type = "tool_change";
             toolChangeObject.session_id = commandHandler.getSessionId();
             toolChangeObject.tool_id = context.name();
             
             currentTool = context;

             sendMessagetoCraft(toolChangeObject.toJSON());
     }

	 public void selectNextTool()
	 {
		 try {
			toolChange(currentTool.getNext());
		} catch (IOException e) {
			host.showPopupNotification("Cannot change Tool");
		}
	 }
}
