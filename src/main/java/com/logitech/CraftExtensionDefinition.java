package com.logitech;
import java.util.UUID;

import com.bitwig.extension.api.PlatformType;
import com.bitwig.extension.controller.AutoDetectionMidiPortNamesList;
import com.bitwig.extension.controller.ControllerExtensionDefinition;
import com.bitwig.extension.controller.api.ControllerHost;

public class CraftExtensionDefinition extends ControllerExtensionDefinition
{
   private static final UUID DRIVER_ID = UUID.fromString("84ca98ff-6ffe-45d0-8162-659f9b3252a8");
   
   public CraftExtensionDefinition()
   {
   }

   @Override
   public String getName()
   {
      return "Craft";
   }
   
   @Override
   public String getAuthor()
   {
      return "marctdt";
   }

   @Override
   public String getVersion()
   {
      return "0.1";
   }

   @Override
   public UUID getId()
   {
      return DRIVER_ID;
   }
   
   @Override
   public String getHardwareVendor()
   {
      return "Logitech";
   }
   
   @Override
   public String getHardwareModel()
   {
      return "Craft";
   }

   @Override
   public int getRequiredAPIVersion()
   {
      return 7;
   }

   @Override
   public int getNumMidiInPorts()
   {
      return 0;
   }

   @Override
   public int getNumMidiOutPorts()
   {
      return 0;
   }

   @Override
   public void listAutoDetectionMidiPortNames(final AutoDetectionMidiPortNamesList list, final PlatformType platformType)
   {
   }

   @Override
   public CraftExtension createInstance(final ControllerHost host)
   {
      return new CraftExtension(this, host);
   }
}
