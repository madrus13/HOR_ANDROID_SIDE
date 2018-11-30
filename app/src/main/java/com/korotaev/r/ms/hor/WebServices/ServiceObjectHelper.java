package com.korotaev.r.ms.hor.WebServices;

import android.content.Context;

import com.korotaev.r.ms.hor.Preferences.Preferences;
import com.korotaev.r.ms.testormlite.data.Entity.Achievmenttype;
import com.korotaev.r.ms.testormlite.data.Entity.Messagetype;
import com.korotaev.r.ms.testormlite.data.Entity.Requesttype;
import com.korotaev.r.ms.testormlite.data.Entity.Tooltypes;
import com.korotaev.r.ms.testormlite.data.Entity.TransmissionType;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ServiceObjectHelper {

    private  static WebServiceMainService service = new WebServiceMainService();
    private String AllRequestType = "";
    private static List<Requesttype> requesttypeList;

    private String AllMessageType = "";
    private static List<Messagetype> messagetypeList;

    private String AllAchievmenttype = "";
    private static List<Achievmenttype> achievmenttypeList;

    private String AllToolType = "";
    private static List<Tooltypes> tooltypesList;

    private String AllTransmissionType = "";
    private static List<TransmissionType> transmissionTypeList;


    public static List<Requesttype> getRequestTypes(Context context, String currentToken)
    {
        if (!currentToken.isEmpty()) {
            serviceResult result = service.getAllRequestType(currentToken);
            ObjectMapper mapper = new ObjectMapper();
            try {

                if (result!=null && !result.resultObjectJSON.isEmpty()) {
                    requesttypeList = Arrays.asList(mapper.readValue(result.resultObjectJSON, Requesttype[].class));
                    Preferences.saveObjInPrefs(context, Preferences.SAVED_RequestType,result.resultObjectJSON);
                    return requesttypeList;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }



}
