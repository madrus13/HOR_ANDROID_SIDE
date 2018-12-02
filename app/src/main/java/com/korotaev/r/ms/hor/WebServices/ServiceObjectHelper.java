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


    public static List<Messagetype> getMessageType(Context context, String currentToken)
    {
        serviceResult result = new serviceResult();
        if (!currentToken.isEmpty()) {

            try {
                result = service.getAllMessageTypes(currentToken);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ObjectMapper mapper = new ObjectMapper();
            try {

                if (result!=null && !result.resultObjectJSON.isEmpty()) {
                    messagetypeList = Arrays.asList(mapper.readValue(result.resultObjectJSON, Messagetype[].class));
                    Preferences.saveObjInPrefs(context, Preferences.SAVED_MessageType,result.resultObjectJSON);
                    return messagetypeList;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static List<Achievmenttype> getAchievmenttype(Context context, String currentToken)
    {
        serviceResult result = new serviceResult();
        if (!currentToken.isEmpty()) {

            try {
                result = service.getAllAchievmenttype(currentToken);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ObjectMapper mapper = new ObjectMapper();
            try {

                if (result!=null && !result.resultObjectJSON.isEmpty()) {
                    achievmenttypeList = Arrays.asList(mapper.readValue(result.resultObjectJSON, Achievmenttype[].class));
                    Preferences.saveObjInPrefs(context, Preferences.SAVED_Achievmenttype,result.resultObjectJSON);
                    return achievmenttypeList;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static List<Tooltypes> getTooltypes(Context context, String currentToken)
    {
        serviceResult result = new serviceResult();
        if (!currentToken.isEmpty()) {

            try {
                result = service.getAllToolType(currentToken);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ObjectMapper mapper = new ObjectMapper();
            try {

                if (result!=null && !result.resultObjectJSON.isEmpty()) {
                    tooltypesList = Arrays.asList(mapper.readValue(result.resultObjectJSON, Tooltypes[].class));
                    Preferences.saveObjInPrefs(context, Preferences.SAVED_ToolType,result.resultObjectJSON);
                    return tooltypesList;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static List<TransmissionType> getTransmissionType(Context context, String currentToken)
    {
        serviceResult result = new serviceResult();
        if (!currentToken.isEmpty()) {

            try {
                result = service.getAllTransmissionType(currentToken);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ObjectMapper mapper = new ObjectMapper();
            try {

                if (result!=null && !result.resultObjectJSON.isEmpty()) {
                    transmissionTypeList = Arrays.asList(mapper.readValue(result.resultObjectJSON, TransmissionType[].class));
                    Preferences.saveObjInPrefs(context, Preferences.SAVED_TransmissionType,result.resultObjectJSON);
                    return transmissionTypeList;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static List<Requesttype> getRequestTypes(Context context, String currentToken)
    {
        serviceResult result = new serviceResult();
        if (!currentToken.isEmpty()) {

            try {
                result = service.getAllRequestType(currentToken);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
