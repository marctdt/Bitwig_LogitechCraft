package com.logitech.Device;

import java.io.IOException;
import java.net.URI;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import com.bitwig.extension.callback.BooleanValueChangedCallback;
import com.bitwig.extension.controller.api.ControllerHost;
import com.bitwig.extension.controller.api.CursorTrack;
import com.bitwig.extension.controller.api.Transport;
import com.logitech.connectivity.CraftSocketConnection;

public class Craft implements Observer{

private ControllerHost host;
private final String connectionURL = "ws://localhost:10134";	
private CraftSocketConnection craftWSClient;

private CursorTrack cursor;
private Transport transport;

	
	public Craft(ControllerHost host) {
		// TODO Auto-generated constructor stub
		this.host = host;

		initViews();
	}
	
	public void initViews()
	{
		transport=host.createTransport();
		transport.isMetronomeEnabled().addValueObserver(new BooleanValueChangedCallback() {
			@Override
			public void valueChanged(boolean newValue) {
				host.showPopupNotification("tick is now "+ newValue);
			}
		});
		
	cursor = host.createCursorTrack(0, 0);
	cursor.addIsSelectedInEditorObserver(new BooleanValueChangedCallback() {
		
		@Override
		public void valueChanged(boolean newValue) {
		
			
		}
	});
		
		
		
	}
	
	
	public void ConnectionToCraftDevice()
	{
		  try {
				craftWSClient = new CraftSocketConnection(host);
				WebSocketClient wsClient = new WebSocketClient();
				wsClient.start();
				wsClient.connect(craftWSClient,new URI(connectionURL),new ClientUpgradeRequest() );
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
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		

	}

}
