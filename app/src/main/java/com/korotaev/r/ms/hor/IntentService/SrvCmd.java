package com.korotaev.r.ms.hor.IntentService;

public class SrvCmd {
    public  static final int    CMD_RegisterIntentServiceClientReq = 1;
    public  static final int    CMD_RegisterIntentServiceClientResp = 2;
    public  static final int	CMD_UnregisterIntentServiceClientReq  = 3;
    public  static final int	CMD_UnregisterIntentServiceClientResp = 4;
    public  static final int	CMD_EntitySyncReq              = 5;
    public  static final int	CMD_EntitySyncResp             = 6;
    public  static final int	CMD_EntityGetUserInfoReq       = 7;
    public  static final int	CMD_EntityGetUserInfoResp      = 8;
    public  static final int	CMD_EntitySetUserInfoReq       = 9;
    public  static final int	CMD_EntitySetUserInfoResp      = 10;
    public  static final int	CMD_InsertMessageReq           = 11;
    public  static final int	CMD_InsertMessageResp          = 12;

    public  static String CODE_INFO = "INF";
    public  static String CODE_ERR  = "ERR";
    public  static String CODE_WRN  = "WRN";
}
