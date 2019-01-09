package com.logitech;

import com.google.gson.Gson;

public class CrownObject {
	   public String message_type; 
       public String device_id ;
       public String task_id; 
       public int delta;
       public int ratchet_delta;
       public int time_stamp ;
       
       
       public String ToJson() {
    	   return new Gson().toJson(this);
       }
}
