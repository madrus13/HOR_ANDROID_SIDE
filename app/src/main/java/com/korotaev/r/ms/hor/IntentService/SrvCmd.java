package com.korotaev.r.ms.hor.IntentService;

public class SrvCmd {
    public  static final int    CMD_RegisterIntentServiceClientReq = 1;
    public  static final int    CMD_RegisterIntentServiceClientResp = 2;
    public  static final int	CMD_UnregisterIntentServiceClientReq  = 3;
    public  static final int	CMD_UnregisterIntentServiceClientResp = 4;
    public  static final int	CMD_EntitySyncReq              = 5;
    public  static final int	CMD_EntitySyncResp             = 6;

    public  static String APP_TAG_CODE = "HOD_APP";
}
