package com.logitech;

import com.bitwig.extension.controller.api.ControllerHost;
import com.bitwig.extension.controller.api.Transport;
import com.logitech.connectivity.CraftSocketConnection;

import java.io.IOException;
import java.lang.management.ManagementFactory;

import com.bitwig.extension.controller.ControllerExtension;

public class CraftExtension extends ControllerExtension
{
   protected CraftExtension(final CraftExtensionDefinition definition, final ControllerHost host)
   {
      super(definition, host);
   }

   @Override
   public void init()
   {
      final ControllerHost host = getHost();      

      // TODO: Perform your driver initialization here.
      // For now just show a popup notification for verification that it is running.
      host.showPopupNotification("Craft Initialized");
      try {
		CraftSocketConnection connection = new CraftSocketConnection();
		host.println(connection.value);
		host.println(connection.isConnected()?"Connected" : "Not Connected");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		host.println("Cannot connect to Craft! Make sure it is connected");
	}
   }

   @Override
   public void exit()
   {
      // TODO: Perform any cleanup once the driver exits
      // For now just show a popup notification for verification that it is no longer running.
      getHost().showPopupNotification("Craft Exited");
   }

   @Override
   public void flush()
   {
      // TODO Send any updates you need here.
   }


}
