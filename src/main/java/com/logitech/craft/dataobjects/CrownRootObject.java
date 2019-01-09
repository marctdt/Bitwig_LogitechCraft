package com.logitech.craft.dataobjects;

import java.math.BigInteger;

import com.logitech.craft.handlers.MessageTypes;


public class CrownRootObject extends DataObject {
      public int device_id ;
      public int unit_id ;
      public int feature_id ;
      public String task_id ;
      public String session_id ;
      public int touch_state ;
      public TaskOptions task_options ;
      public int delta ;
      public int ratchet_delta ;
      public BigInteger time_stamp ;
      public String state ;
}
