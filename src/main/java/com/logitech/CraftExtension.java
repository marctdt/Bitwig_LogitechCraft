package com.logitech;

import com.bitwig.extension.controller.ControllerExtension;
import com.bitwig.extension.controller.api.ControllerHost;
import com.logitech.Device.Craft;

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
    
      Craft device=new Craft(host);
      device.ConnectionToCraftDevice();
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
