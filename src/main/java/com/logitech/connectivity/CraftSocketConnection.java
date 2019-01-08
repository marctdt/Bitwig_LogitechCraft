package com.logitech.connectivity;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.Observable;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketFrame;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.api.extensions.Frame;

import com.bitwig.extension.controller.api.ControllerHost;

@WebSocket
public class CraftSocketConnection extends Observable{
	
	private Session session;
	public BufferedOutputStream bos;
		
	
	public final static String  CRAFT_IP = "127.0.0.1";
	public final static int CRAFT_PORT = 10134;
	public final static String PLUGIN_GUID ="5379c471-be4e-4685-b197-d146728368a0";
	public final static String PLUGIN_EXECNAME = "Bitwig Studio.exe";
	
	
	public String value;
	
	public ControllerHost host;
	
	public CraftSocketConnection(ControllerHost host) throws IOException
	{
		this.host=host;
	}
	
	@OnWebSocketConnect
	public void initConnection(Session session) throws IOException
	{
		this.session =session;

		CrownRegisterRootObject registerRequest=new CrownRegisterRootObject();
		registerRequest.message_type = "register";
		registerRequest.plugin_guid = PLUGIN_GUID;
		registerRequest.execName = PLUGIN_EXECNAME;
		registerRequest.PID = Integer.parseInt(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
		
		String request = registerRequest.ToJson();
		
		session.getRemote().sendString(request);
host.println("send connection Request: " + request);

	}
	
	public boolean isConnected() {
		return session.isOpen();
	}
	
	@OnWebSocketMessage
	public void onMessage(Session session,String message) {
		setChanged();
		notifyObservers(message);
		host.println(message);
	}

@OnWebSocketFrame
public void onFrame(Frame test)
{
	
	host.println("Frame "+test);

}

@OnWebSocketMessage
public void onWebSocketBinary(byte[] payload,
                                                  int offset,
                                                  int length) {
	host.println("Byte: "+ payload);
}

	@OnWebSocketError
    public void onError(Throwable t) {
        System.out.println("Error: " + t.getMessage());
        host.println(t.getMessage());
    }
	public void SendMessageToCraft(String JsonMessage) {
	}
	
	
	@OnWebSocketClose
    public void onClose(int statusCode, String reason)
    {
        System.out.printf("onClose(%d, %s)%n",statusCode,reason);
        host.println("onClose() " + statusCode+","+reason);
    }
}