package com.korotaev.r.ms.hor.WebServices;

import android.content.Context;

import com.korotaev.r.ms.hor.Preferences.Preferences;
import com.korotaev.r.ms.testormlite.data.Entity.Achievement;
import com.korotaev.r.ms.testormlite.data.Entity.Achievmenttype;
import com.korotaev.r.ms.testormlite.data.Entity.Auto;
import com.korotaev.r.ms.testormlite.data.Entity.Messagetype;
import com.korotaev.r.ms.testormlite.data.Entity.Requesttype;
import com.korotaev.r.ms.testormlite.data.Entity.Tool;
import com.korotaev.r.ms.testormlite.data.Entity.Tooltypes;
import com.korotaev.r.ms.testormlite.data.Entity.TransmissionType;
import com.korotaev.r.ms.testormlite.data.Entity.User;

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

    public static User getCurrentUserInfo(Context context, String currentToken)
    {
        User currentUser = null;
        serviceResult result = new serviceResult();
        if (!currentToken.isEmpty()) {

            try {
                result = service.getUserInfo(currentToken);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ObjectMapper mapper = new ObjectMapper();
            try {

                if (result!=null && !result.resultObjectJSON.isEmpty()) {
                    currentUser = mapper.readValue(result.resultObjectJSON, User.class);
                    Preferences.saveObjInPrefs(context, Preferences.SAVED_CurrentUserInfo,result.resultObjectJSON);
                    return currentUser;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static List<Achievement>  getAllAchievmentByUser(Context context, String currentToken, long userId, boolean userSpecified)
    {
        List<Achievement> achievements = null;
        serviceResult result = new serviceResult();
        if (!currentToken.isEmpty()) {

            try {
                result = service.getAllAchievmentByUser(currentToken, userId, userSpecified);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ObjectMapper mapper = new ObjectMapper();
            try {

                if (result!=null && !result.resultObjectJSON.isEmpty()) {
                    achievements =  Arrays.asList(mapper.readValue(result.resultObjectJSON, Achievement[].class));
                    Preferences.saveObjInPrefs(context, Preferences.SAVED_CurrentUserAchievs,result.resultObjectJSON);
                    return achievements;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static List<Tool>  getAllToolByUser(Context context, String currentToken, long userId, boolean userSpecified)
    {
        List<Tool> tools = null;
        serviceResult result = new serviceResult();
        if (!currentToken.isEmpty()) {

            try {
                result = service.getAllToolByUser(currentToken, userId, userSpecified);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ObjectMapper mapper = new ObjectMapper();
            try {

                if (result!=null && !result.resultObjectJSON.isEmpty()) {
                    tools =  Arrays.asList(mapper.readValue(result.resultObjectJSON, Tool[].class));
                    Preferences.saveObjInPrefs(context, Preferences.SAVED_CurrentUserTools,result.resultObjectJSON);
                    return tools;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static List<Auto>  getAllAutoByUser(Context context, String currentToken, long userId, boolean userSpecified)
    {
        List<Auto> autos = null;
        serviceResult result = new serviceResult();
        if (!currentToken.isEmpty()) {

            try {
                result = service.getAllAutoByUser(currentToken, userId, userSpecified);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ObjectMapper mapper = new ObjectMapper();
            try {

                if (result!=null && !result.resultObjectJSON.isEmpty()) {
                    autos =  Arrays.asList(mapper.readValue(result.resultObjectJSON, Auto[].class));
                    Preferences.saveObjInPrefs(context, Preferences.SAVED_CurrentUserAutos,result.resultObjectJSON);
                    return autos;
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
