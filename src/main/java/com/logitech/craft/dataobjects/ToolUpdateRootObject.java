package com.logitech.craft.dataobjects;

import java.util.List;

public class ToolUpdateRootObject extends DataObject{
    public String session_id ;
    public String show_overlay ;
    public String tool_id ;
    public List<ToolOption> tool_options ;
    public String play_task ;
}
