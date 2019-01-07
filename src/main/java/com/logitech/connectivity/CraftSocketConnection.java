package com.logitech.connectivity;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.Socket;
import java.net.UnknownHostException;

public class CraftSocketConnection {
	
	public Socket connection;
	public BufferedOutputStream bos;
	public Stream
		
	
	public final static String  CRAFT_IP = "127.0.0.1";
	public final static int CRAFT_PORT = 10134;
	public final static String PLUGIN_GUID ="5379c471-be4e-4685-b197-d146728368a0";
	public final static String PLUGIN_EXECNAME = "Bitwig Studio.exe";
	
	
	public String value;
	public CraftSocketConnection() throws IOException {
			initConnection();
	}
	
	public void initConnection() throws UnknownHostException, IOException
	{
		connection = new Socket(CRAFT_IP,CRAFT_PORT);
		//SocketAddress sa = new InetSocketAddress(CRAFT_IP, CRAFT_PORT);
		//connection.connect(sa);
				
		CrownRegisterRootObject registerRequest=new CrownRegisterRootObject();
		registerRequest.message_type = "register";
		registerRequest.plugin_guid = PLUGIN_GUID;
		registerRequest.execName = PLUGIN_EXECNAME;
		registerRequest.PID = Integer.parseInt(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
		
		String request = registerRequest.ToJson();
		value = request;
		
		BufferedOutputStream bos = new BufferedOutputStream(connection.getOutputStream());
		bos.write(request.getBytes());
		bos.flush();

	}
	
	public boolean isConnected() {
		return connection.isConnected();
	}
	
	public void SendMessageToCraft(string JsonMessage) {
		connection.
	}
}