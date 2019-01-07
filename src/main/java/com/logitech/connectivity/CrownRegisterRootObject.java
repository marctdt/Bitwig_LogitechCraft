package com.logitech.connectivity;

import com.eclipsesource.json.JsonObject;

public class CrownRegisterRootObject {
	   public String message_type; 
       public String plugin_guid ;
       public String session_id ;
       public int PID ;
       public String execName ;
       
       
       public String ToJson() {
    	   JsonObject jo=new JsonObject();
    	   jo.add("message_type", message_type);
    	   jo.add("plugin_guid", plugin_guid);
    	   jo.add("PID", PID);
    	   if (session_id != null) jo.add("session_id", session_id);
    	   jo.add("execName", execName);
    	   return jo.toString();
       }
}
