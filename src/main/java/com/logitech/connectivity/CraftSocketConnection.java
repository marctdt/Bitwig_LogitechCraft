package com.logitech.connectivity;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.URI;
import java.util.Observable;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import com.bitwig.extension.controller.api.ControllerHost;

@WebSocket
public class CraftSocketConnection extends Observable{
	
	private Session session;
	public BufferedOutputStream bos;
		
	
	public final static String  CRAFT_IP = "127.0.0.1";
	public final static int CRAFT_PORT = 10134;
	private final String connectionURL = "ws://localhost:10134";	
	private WebSocketClient wsClient;
	
	
	
	public ControllerHost host;
	public String RegistrationRequest;
	
	public CraftSocketConnection(ControllerHost host,String registrationRequest) throws Exception
	{
		this.host=host;
		this.RegistrationRequest = registrationRequest;
		openConnection();
	}
	
	
	public void openConnection() throws Exception {
		wsClient = new WebSocketClient();
		wsClient.start();
		wsClient.connect(this,new URI(connectionURL),new ClientUpgradeRequest());
	}
	
	@OnWebSocketConnect
	public void initConnection(Session session) throws IOException
	{
		this.session =session;
		sendToDevice(RegistrationRequest);
host.println("send connection Request: " + RegistrationRequest);

	}
	
	public boolean isConnected() {
		return session.isOpen();
	}
	
	@OnWebSocketMessage
	public void onMessage(Session session,String message) {
		setChanged();
		notifyObservers(message);
	}

//@OnWebSocketFrame
//public void onFrame(Frame test)
//{
//	
//	host.println("Frame "+test);
//
//}

	public void sendToDevice(String jsonMessage) throws IOException {
		session.getRemote().sendString(jsonMessage);
	}
	
	@OnWebSocketError
    public void onError(Throwable t) {
        System.out.println("Error: " + t.getMessage());
        host.println(t.getMessage());
    }
	
	
	@OnWebSocketClose
    public void onClose(int statusCode, String reason)
    {
        System.out.printf("onClose(%d, %s)%n",statusCode,reason);
        host.println("onClose() " + statusCode+","+reason);
    }
}