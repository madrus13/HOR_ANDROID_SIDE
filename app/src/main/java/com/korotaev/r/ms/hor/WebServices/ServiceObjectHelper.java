package com.korotaev.r.ms.hor.WebServices;

import android.content.Context;

import com.korotaev.r.ms.hor.Preferences.Preferences;
import com.korotaev.r.ms.testormlite.data.Entity.Achievement;
import com.korotaev.r.ms.testormlite.data.Entity.Achievmenttype;
import com.korotaev.r.ms.testormlite.data.Entity.Auto;
import com.korotaev.r.ms.testormlite.data.Entity.Files;
import com.korotaev.r.ms.testormlite.data.Entity.Message;
import com.korotaev.r.ms.testormlite.data.Entity.Messagetype;
import com.korotaev.r.ms.testormlite.data.Entity.Region;
import com.korotaev.r.ms.testormlite.data.Entity.Requesttype;
import com.korotaev.r.ms.testormlite.data.Entity.Tool;
import com.korotaev.r.ms.testormlite.data.Entity.Tooltypes;
import com.korotaev.r.ms.testormlite.data.Entity.TransmissionType;
import com.korotaev.r.ms.testormlite.data.Entity.User;

import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

    private String AllRegions = "";
    private static List<Region> regionsList;


    private String AllTransmissionType = "";
    private static List<TransmissionType> transmissionTypeList;

    private static ObjectMapper mapper = new ObjectMapper();

    public static boolean isValidResult(serviceResult result)
    {
        return (result!=null && result.resultObjectJSON!=null && !result.resultObjectJSON.isEmpty() &&
                !result.resultObjectJSON.toUpperCase().equals("NULL"));
    }

    public static boolean isValidResultWithNoObject(serviceResult result)
    {
        return (result!=null );
    }

    public static List<Messagetype> getMessageType(Context context, String currentToken)
    {
        serviceResult result = new serviceResult();
        if (!currentToken.isEmpty()) {

            try {
                result = service.getAllMessageTypes(currentToken, null);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {

                if (isValidResult(result)) {
                    messagetypeList = Arrays.asList(mapper.readValue(result.resultObjectJSON, Messagetype[].class));
                    Preferences.saveObjInPrefs(context, Preferences.SAVED_MessageType,result.resultObjectJSON);
                    return messagetypeList;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static List<Message> getAllMessageByRegion(Context context,
                                                      String currentToken,
                                                      Long regionId,
                                                      Long startRow,
                                                      int pageSize
                                                      )
    {
        ArrayList<Message> messageList = new ArrayList<Message>();

        serviceResult result = new serviceResult();
        if (!currentToken.isEmpty()) {

            try {
                result = service.getMessageByRegionAndIdGreater(
                        currentToken,
                        regionId, regionId > 0 ? true : false,
                        startRow, true,
                        pageSize, null);

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {

                if (isValidResult(result)) {

                    messageList.addAll(Arrays.asList(mapper.readValue(result.resultObjectJSON, Message[].class)));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return messageList;
    }


    public static List<Message> findMessageByRegionAndCreationDateBetween(Context context,
                                                      String currentToken,
                                                      long regionId,
                                                      Date startDate,
                                                      Date endDate,
                                                      int page, int pageSize
    )
    {
        ArrayList<Message> messageList = new ArrayList<Message>();

        serviceResult result = new serviceResult();
        if (!currentToken.isEmpty()) {

            try {
                result = service.findMessageByRegionAndCreationDateBetween(
                        currentToken,
                        regionId, regionId > 0 ? true : false,
                        startDate, true,
                        endDate, true,
                        page,  pageSize, null);

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {

                if (isValidResult(result)) {

                    messageList.addAll(Arrays.asList(mapper.readValue(result.resultObjectJSON, Message[].class)));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return messageList;
    }


    public static List<Message> findMessageByRegionAndCreationDateBetweenOffset(Context context,
                                                                          String currentToken,
                                                                          long regionId,
                                                                          int offset,
                                                                          int page, int pageSize
    )
    {
        ArrayList<Message> messageList = new ArrayList<Message>();

        serviceResult result = new serviceResult();
        if (!currentToken.isEmpty()) {

            try {
                result = service.findMessageByRegionAndCreationDateBetweenOffset(
                        currentToken,
                        regionId, regionId > 0 ? true : false,
                        offset,
                        page,  pageSize, null);

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {

                if (isValidResult(result)) {

                    messageList.addAll(Arrays.asList(mapper.readValue(result.resultObjectJSON, Message[].class)));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return messageList;
    }


    public static List<Region> getAllRegions(Context context, String currentToken)
    {
        serviceResult result = new serviceResult();
         try {
                result = service.getAllRegions(currentToken, null);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {

                if (isValidResult(result)) {
                    regionsList = Arrays.asList(mapper.readValue(result.resultObjectJSON, Region[].class));
                    Preferences.saveObjInPrefs(context, Preferences.SAVED_Region,result.resultObjectJSON);
                    return regionsList;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        return null;
    }


    public static List<Achievmenttype> getAchievmenttype(Context context, String currentToken)
    {
        serviceResult result = new serviceResult();
        if (!currentToken.isEmpty()) {

            try {
                result = service.getAllAchievmenttype(currentToken, null);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {

                if (isValidResult(result)) {
                    achievmenttypeList = Arrays.asList(mapper.readValue(result.resultObjectJSON, Achievmenttype[].class));
                    Preferences.saveObjInPrefs(context, Preferences.SAVED_Achievmenttype,result.resultObjectJSON);
                    return achievmenttypeList;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static User getCurrentUserInfo(Context context, String currentToken)
    {
        User currentUser;
        serviceResult result = new serviceResult();
        if (!currentToken.isEmpty()) {

            try {
                result = service.getUserInfo(currentToken, null);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {

                if (isValidResult(result))
                {
                    currentUser = mapper.readValue(result.resultObjectJSON, User.class);
                    Preferences.saveObjInPrefs(context, Preferences.SAVED_CurrentUserInfo,result.resultObjectJSON);
                    return currentUser;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static User setCurrentUserInfo(Context context,
                                          String currentToken,
                                          long region,
                                          boolean regionSpecified,
                                          String password,
                                          String fileName,
                                          VectorByte fileImage)
    {
        User currentUser;
        serviceResult result = new serviceResult();
        if (!currentToken.isEmpty()) {

            try {
                result = service.updateUser( currentToken,
                                             region,
                                             regionSpecified,
                                             "EMPTY",
                                             fileName,
                                              fileImage, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {

                if (isValidResult(result))
                {
                    currentUser = getCurrentUserInfo(context,currentToken);
                    if (currentUser!=null) {
                        currentUser = mapper.readValue(result.resultObjectJSON, User.class);
                        Preferences.saveObjInPrefs(context, Preferences.SAVED_CurrentUserInfo,result.resultObjectJSON);
                        Preferences.saveObjInPrefs(context,Preferences.SAVED_LAST_MSG_ROW_IN_REGION,String.valueOf(Preferences.INITIAL_MSG_ROW_IN_REGION));

                        return currentUser;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static List<Tool> setCurrentUserTools(Context context,
                                          String currentToken,
                                          String toolTypeIds)
    {
        List<Tool> currentUserTools;
        serviceResult result = new serviceResult();
        if (!currentToken.isEmpty()) {

            try {
                result = service.insertUpdateUserTools( currentToken, toolTypeIds, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {

                if (isValidResult(result))
                {
                    currentUserTools = Arrays.asList(mapper.readValue(result.resultObjectJSON, Tool[].class));
                    Preferences.saveObjInPrefs(context, Preferences.SAVED_CurrentUserTools,result.resultObjectJSON);
                    return currentUserTools;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static serviceResult insertMessage(Context context,
                                              String currentToken,
                                              String text,
                                              Long requestId, boolean requestIdSpecified,
                                              Long regionId,  boolean regionIdSpecified,
                                              Long userRx,    boolean userRxSpecified,
                                              Long typeId,    boolean typeIdSpecified,
                                              Long fileId,
                                              String fileName,
                                              VectorByte fileImage)
    {
        serviceResult result = new serviceResult();

        if (!currentToken.isEmpty()) {

            try {
                result = service.insertMessage(
                        currentToken,
                        text,
                        requestId, requestIdSpecified,
                        regionId, regionIdSpecified,
                        userRx, userRxSpecified,
                        typeId, typeIdSpecified,
                        fileId,
                        fileName,
                        fileImage, null);

            } catch (Exception e) {
                result.isSuccess = false;
                result.errorMessage = e.toString();
                e.printStackTrace();
            }
        }
        return result;
    }


    public static List<Auto> setCurrentUserAuto(Context context,
                                                String currentToken,
                                                String name,
                                                long haveCable,
                                                boolean haveCableSpecified,
                                                long transmissionType,
                                                boolean transmissionTypeSpecified)
    {
        List<Auto> currentUserAutos;
        serviceResult result = new serviceResult();
        if (!currentToken.isEmpty()) {

            try {
                result = service.insertUpdateUserAuto(  currentToken,
                                                        name,
                                                        haveCable,
                                                        haveCableSpecified,
                                                        transmissionType,
                                                        transmissionTypeSpecified,
                                                        null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {

                if (isValidResult(result))
                {
                    currentUserAutos = Arrays.asList(mapper.readValue(result.resultObjectJSON, Auto[].class));
                    Preferences.saveObjInPrefs(context, Preferences.SAVED_CurrentUserAutos,result.resultObjectJSON);
                    return currentUserAutos;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }



    public static List<Achievement>  getAllAchievmentByUser(Context context, String currentToken, long userId, boolean userSpecified)
    {
        List<Achievement> achievements;
        serviceResult result = new serviceResult();
        if (!currentToken.isEmpty()) {

            try {
                result = service.getAllAchievmentByUser(currentToken, userId, userSpecified, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {

                if (isValidResult(result))
                {
                    achievements =  Arrays.asList(mapper.readValue(result.resultObjectJSON, Achievement[].class));
                    Preferences.saveObjInPrefs(context, Preferences.SAVED_CurrentUserAchievs,result.resultObjectJSON);
                    return achievements;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }




    public static List<Tool>  getAllToolByUser(Context context, String currentToken, long userId, boolean userSpecified)
    {
        List<Tool> tools;
        serviceResult result = new serviceResult();
        if (!currentToken.isEmpty()) {

            try {
                result = service.getAllToolByUser(currentToken, userId, userSpecified, null);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {

                if (isValidResult(result))
                {
                    tools =  Arrays.asList(mapper.readValue(result.resultObjectJSON, Tool[].class));
                    Preferences.saveObjInPrefs(context, Preferences.SAVED_CurrentUserTools,result.resultObjectJSON);
                    return tools;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Auto  getAllAutoByUser(Context context, String currentToken, long userId, boolean userSpecified)
    {
        Auto autos;
        serviceResult result = new serviceResult();
        if (!currentToken.isEmpty()) {

            try {
                result = service.getAllAutoByUser(currentToken, userId, userSpecified, null);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {

                if (isValidResult(result)) {
                    autos =  mapper.readValue(result.resultObjectJSON, Auto.class);
                    Preferences.saveObjInPrefs(context, Preferences.SAVED_CurrentUserAutos,result.resultObjectJSON);
                    return autos;
                }

            } catch (Exception e) {
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
                result = service.getAllToolType(currentToken, null);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {

                if (isValidResult(result))
                {
                    tooltypesList = Arrays.asList(mapper.readValue(result.resultObjectJSON, Tooltypes[].class));
                    Preferences.saveObjInPrefs(context, Preferences.SAVED_ToolType,result.resultObjectJSON);
                    return tooltypesList;
                }

            } catch (Exception e) {
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
                result = service.getAllTransmissionType(currentToken, null);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {

                if (isValidResult(result)) {
                    transmissionTypeList = Arrays.asList(mapper.readValue(result.resultObjectJSON, TransmissionType[].class));
                    Preferences.saveObjInPrefs(context, Preferences.SAVED_TransmissionType,result.resultObjectJSON);
                    return transmissionTypeList;
                }

            } catch (Exception e) {
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
                result = service.getAllRequestType(currentToken, null);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {

                if (isValidResult(result))
                {
                    requesttypeList = Arrays.asList(mapper.readValue(result.resultObjectJSON, Requesttype[].class));
                    Preferences.saveObjInPrefs(context, Preferences.SAVED_RequestType,result.resultObjectJSON);
                    return requesttypeList;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static Files  getFileById(Context context, String currentToken, long fileId)
    {
        Files object;
        serviceResult result = new serviceResult();
        if (!currentToken.isEmpty()) {

            try {
                result = service.getFileById(currentToken, fileId,  null);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {

                if (isValidResult(result)) {
                    object =  mapper.readValue(result.resultObjectJSON, Files.class);
                    return object;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static serviceResult insertFile(Context context,
                                              String currentToken,
                                              String fileName,
                                              String description,
                                              String fileType,
                                              Long createUser,
                                              VectorByte fileImage)
    {
        serviceResult result = new serviceResult();

        if (!currentToken.isEmpty()) {

            try {
                result = service.insertFile(
                       currentToken,
                        fileName,
                        description,
                        fileType,
                        createUser,
                        fileImage, null);

            } catch (Exception e) {
                result.isSuccess = false;
                result.errorMessage = e.toString();
                e.printStackTrace();
            }
        }
        return result;
    }

}
