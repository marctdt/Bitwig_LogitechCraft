package com.logitech.connectivity;

import com.google.gson.Gson;

public class CrownRegisterRootObject {
	   public String message_type; 
       public String plugin_guid ;
       public String session_id ;
       public int PID ;
       public String execName ;
       
       
       public String ToJson() {
    	   return new Gson().toJson(this);
       }
}
