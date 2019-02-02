package com.korotaev.r.ms.hor.WebServices;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.korotaev.r.ms.hor.AppHelpers.FileHelper.FILE_SERVER_IP;

public class WebServiceMainService {
    
    public static String NAMESPACE ="http://Service.ru/";
    public static String url = FILE_SERVER_IP + ":8080/samplejpa-46/ws?wsdl";
    public String PREFIX_SERVICE  = "";
    public static int timeOut = 5000;
    public IWsdl2CodeEvents eventHandler;
    public WS_Enums.SoapProtocolVersion soapVersion;
    private static SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
    private static HttpTransportSE httpTransport = new HttpTransportSE(url,timeOut);

    public WebServiceMainService(){
        soapEnvelope.implicitTypes = true;
    }
    
    public WebServiceMainService(IWsdl2CodeEvents eventHandler)
    {
        this.eventHandler = eventHandler;
    }
    public WebServiceMainService(IWsdl2CodeEvents eventHandler,String url)
    {
        this.eventHandler = eventHandler;
        this.url = url;
    }
    public WebServiceMainService(IWsdl2CodeEvents eventHandler,String url,int timeOutInSeconds)
    {
        this.eventHandler = eventHandler;
        this.url = url;
        this.setTimeOut(timeOutInSeconds);
    }
    public void setTimeOut(int seconds){
        this.timeOut = seconds * 1000;
    }
    public void setUrl(String url){
        this.url = url;
    }

    public serviceResult getSessionToken(String name,String password){
        return getSessionToken(name, password, null);
    }

    public serviceResult soapMethodExecutor(List<HeaderProperty> headers, String methodRequestName)
    {
        ArrayList<HeaderProperty> headerProperty = new ArrayList<HeaderProperty>();

        headerProperty.add(new HeaderProperty("Content-Type", "text/xml"));
        headerProperty.add(new HeaderProperty("charset", "UTF-8"));
        soapEnvelope.encodingStyle = "UTF-8";
        try{
            if (headers!=null){
                //http://Service.ru/WebServiceMain/
                httpTransport.call(methodRequestName, soapEnvelope,headers);
            }else{
                httpTransport.call(PREFIX_SERVICE + methodRequestName, soapEnvelope);
            }
            Object retObj = soapEnvelope.bodyIn;
            if (retObj instanceof SoapFault){
                SoapFault fault = (SoapFault)retObj;
                Exception ex = new Exception(fault.faultstring);
                if (eventHandler != null)
                    eventHandler.Wsdl2CodeFinishedWithException(ex);
            }else{
                SoapObject result=(SoapObject)retObj;
                if (result.getPropertyCount() > 0){
                    Object obj = result.getProperty(0);
                    SoapObject j = (SoapObject)obj;
                    serviceResult resultVariable =  new serviceResult (j);
                    return resultVariable;

                }
            }
        }catch (Exception e) {
            if (eventHandler != null)
                eventHandler.Wsdl2CodeFinishedWithException(e);
            e.printStackTrace();
        }
        return null;
    }

    public serviceResult getSessionToken(String name,String password,List<HeaderProperty> headers){

        SoapObject soapReq = new SoapObject("http://Service.ru/","getSessionToken");
        soapReq.addProperty("name",name);
        soapReq.addProperty("password",password);
        soapEnvelope.setOutputSoapObject(soapReq);
        return soapMethodExecutor(headers, "getSessionTokenRequest");

    }



    public serviceResult insertMessage(
            String sessionToken,
            String text,
            long requestId,boolean requestIdSpecified,
            long regionId,boolean regionIdSpecified,
            long userRx,boolean userRxSpecified,
            long typeId,boolean typeIdSpecified,
            long fileId,
            String fileName,
            VectorByte fileImage,
            List<HeaderProperty> headers){

        SoapObject soapReq = new SoapObject("http://Service.ru/","insertMessage");
        soapReq.addProperty("sessionToken",sessionToken);
        soapReq.addProperty("text",text);
        soapReq.addProperty("requestId",requestIdSpecified?requestId:"");
        soapReq.addProperty("regionId",regionIdSpecified?regionId:"");
        soapReq.addProperty("userRx",userRxSpecified?userRx:"");
        soapReq.addProperty("typeId",typeIdSpecified?typeId:"");
        soapReq.addProperty("fileId",fileId);
        soapReq.addProperty("fileName",fileName!=null ? fileName : "");
        soapReq.addProperty("fileImage",fileImage!=null ? fileImage.toString() : "");
        soapEnvelope.setOutputSoapObject(soapReq);

        return soapMethodExecutor(headers, "insertMessageRequest");

    }


    public serviceResult getMessageByRegionAndIdGreater(String sessionToken,long regionId,boolean regionIdSpecified,long lastId,boolean lastIdSpecified,int pageSize,List<HeaderProperty> headers){

        SoapObject soapReq = new SoapObject("http://Service.ru/","getMessageByRegionAndIdGreater");
        soapReq.addProperty("sessionToken",sessionToken);
        soapReq.addProperty("regionId",regionId);
        soapReq.addProperty("regionIdSpecified",regionIdSpecified);
        soapReq.addProperty("lastId",lastId);
        soapReq.addProperty("lastIdSpecified",lastIdSpecified);
        soapReq.addProperty("pageSize",pageSize);
        soapEnvelope.setOutputSoapObject(soapReq);
        return soapMethodExecutor(headers, "getMessageByRegionAndIdGreaterRequest");
    }



    public serviceResult findMessageByRegionAndCreationDateBetweenOffset(
            String sessionToken,
            long regionId,boolean regionIdSpecified,
            int offset,
            int page,
            int pageSize,
            List<HeaderProperty> headers){

        SoapObject soapReq = new SoapObject("http://Service.ru/","findMessageByRegionAndCreationDateBetweenOffset");
        soapReq.addProperty("sessionToken",sessionToken);
        soapReq.addProperty("regionId",regionId);
        soapReq.addProperty("regionIdSpecified",regionIdSpecified);
        soapReq.addProperty("offset",offset);
        soapReq.addProperty("page",page);
        soapReq.addProperty("pageSize",pageSize);
        soapEnvelope.setOutputSoapObject(soapReq);
        return soapMethodExecutor(headers, "findMessageByRegionAndCreationDateBetweenOffsetRequest");
    }



    public serviceResult findMessageByRegionAndCreationDateBetween(
            String sessionToken,
            long regionId, boolean regionIdSpecified,
            Date startDate, boolean startDateSpecified,
            Date endDate, boolean endDateSpecified,
            int page, int pageSize,
            List<HeaderProperty> headers){

        SoapObject soapReq = new SoapObject("http://Service.ru/","findMessageByRegionAndCreationDateBetweenOrderByIdAsc");
        soapReq.addProperty("sessionToken",sessionToken);
        soapReq.addProperty("regionId",regionId);
        soapReq.addProperty("regionIdSpecified",regionIdSpecified);
        soapReq.addProperty("startDate",startDate);
        soapReq.addProperty("startDateSpecified",startDateSpecified);
        soapReq.addProperty("endDate",endDate);
        soapReq.addProperty("endDateSpecified",endDateSpecified);
        soapReq.addProperty("page",page);
        soapReq.addProperty("pageSize",pageSize);
        soapEnvelope.setOutputSoapObject(soapReq);
        return soapMethodExecutor(headers, "findMessageByRegionAndCreationDateBetweenOrderByIdAscRequest");
    }

    

    
    public serviceResult getAllMessageByRequest(String sessionToken,long request,boolean requestSpecified,int startRow, int pageSize,List<HeaderProperty> headers){

        SoapObject soapReq = new SoapObject("http://Service.ru/","getAllMessageByRequest");
        soapReq.addProperty("sessionToken",sessionToken);
        soapReq.addProperty("request",request);
        soapReq.addProperty("requestSpecified",requestSpecified);
        soapReq.addProperty("startRow",startRow);
        soapReq.addProperty("pageSize",pageSize);
        soapEnvelope.setOutputSoapObject(soapReq);
        return soapMethodExecutor(headers, "getAllMessageByRequestRequest");
    }


    public serviceResult getAllRequestByCreationUser(String sessionToken,long userId,boolean userIdSpecified,List<HeaderProperty> headers){

        SoapObject soapReq = new SoapObject("http://Service.ru/","getAllRequestByCreationUser");
        soapReq.addProperty("sessionToken",sessionToken);
        soapReq.addProperty("userId",userId);
        soapReq.addProperty("userIdSpecified",userIdSpecified);
        soapEnvelope.setOutputSoapObject(soapReq);

        return soapMethodExecutor(headers, "getAllRequestByCreationUserRequest");
    }
    

    public serviceResult getActiveRequestByCreationUser(String sessionToken,long userId,boolean userIdSpecified,List<HeaderProperty> headers){

        SoapObject soapReq = new SoapObject("http://Service.ru/","getActiveRequestByCreationUser");
        soapReq.addProperty("sessionToken",sessionToken);
        soapReq.addProperty("userId",userId);
        soapReq.addProperty("userIdSpecified",userIdSpecified);
        soapEnvelope.setOutputSoapObject(soapReq);

        return soapMethodExecutor(headers, "getActiveRequestByCreationUserRequest");
    }

    
    public serviceResult getAllOpenRequestByRegion(String sessionToken,long regionId,boolean regionIdSpecified,String typeIds,List<HeaderProperty> headers){

        SoapObject soapReq = new SoapObject("http://Service.ru/","getAllOpenRequestByRegion");
        soapReq.addProperty("sessionToken",sessionToken);
        soapReq.addProperty("regionId",regionId);
        soapReq.addProperty("regionIdSpecified",regionIdSpecified);
        soapReq.addProperty("typeIds",typeIds);
        soapEnvelope.setOutputSoapObject(soapReq);

        return soapMethodExecutor(headers, "getAllOpenRequestByRegionRequest");
    }
    

    
    public serviceResult findRequestResolvedByCurrentUserWithTypeFilter(String sessionToken,String typeIds,List<HeaderProperty> headers){

        SoapObject soapReq = new SoapObject("http://Service.ru/","findRequestResolvedByCurrentUserWithTypeFilter");
        soapReq.addProperty("sessionToken",sessionToken);
        soapReq.addProperty("typeIds",typeIds);
        soapEnvelope.setOutputSoapObject(soapReq);

        return soapMethodExecutor(headers, "findRequestResolvedByCurrentUserWithTypeFilterRequest");
    }
    

    public serviceResult getAllMessageTypes(String sessionToken,List<HeaderProperty> headers){

        SoapObject soapReq = new SoapObject("http://Service.ru/","getAllMessageTypes");
        soapReq.addProperty("sessionToken",sessionToken);
        soapEnvelope.setOutputSoapObject(soapReq);

        return soapMethodExecutor(headers, "getAllMessageTypesRequest");
    }
    

    
    public serviceResult getAllRequestType(String sessionToken,List<HeaderProperty> headers){

        SoapObject soapReq = new SoapObject("http://Service.ru/","getAllRequestType");
        soapReq.addProperty("sessionToken",sessionToken);
        soapEnvelope.setOutputSoapObject(soapReq);

        return soapMethodExecutor(headers, "getAllRequestTypeRequest");
    }
    

    public serviceResult getAllTransmissionType(String sessionToken,List<HeaderProperty> headers){

        SoapObject soapReq = new SoapObject("http://Service.ru/","getAllTransmissionType");
        soapReq.addProperty("sessionToken",sessionToken);
        soapEnvelope.setOutputSoapObject(soapReq);

        return soapMethodExecutor(headers, "getAllTransmissionTypeRequest");
    }
    

    
    public serviceResult getAllToolType(String sessionToken,List<HeaderProperty> headers){

        SoapObject soapReq = new SoapObject("http://Service.ru/","getAllToolType");
        soapReq.addProperty("sessionToken",sessionToken);
        soapEnvelope.setOutputSoapObject(soapReq);

        return soapMethodExecutor(headers, "getAllToolTypeRequest");
    }


    public serviceResult getAllAchievmenttype(String sessionToken,List<HeaderProperty> headers){

        SoapObject soapReq = new SoapObject("http://Service.ru/","getAllAchievmenttype");
        soapReq.addProperty("sessionToken",sessionToken);
        soapEnvelope.setOutputSoapObject(soapReq);

        return soapMethodExecutor(headers, "getAllAchievmenttypeRequest");
    }

    
    public serviceResult getAllAchievmentByUser(String sessionToken,long user,boolean userSpecified,List<HeaderProperty> headers){

        SoapObject soapReq = new SoapObject("http://Service.ru/","getAllAchievmentByUser");
        soapReq.addProperty("sessionToken",sessionToken);
        soapReq.addProperty("user",user);
        soapReq.addProperty("userSpecified",userSpecified);
        soapEnvelope.setOutputSoapObject(soapReq);

        return soapMethodExecutor(headers, "getAllAchievmentByUserRequest");
    }

    
    public serviceResult getAllToolByUser(String sessionToken,long user,boolean userSpecified,List<HeaderProperty> headers){

        SoapObject soapReq = new SoapObject("http://Service.ru/","getAllToolByUser");
        soapReq.addProperty("sessionToken",sessionToken);
        soapReq.addProperty("user",user);
        soapReq.addProperty("userSpecified",userSpecified);
        soapEnvelope.setOutputSoapObject(soapReq);

        return soapMethodExecutor(headers, "getAllToolByUserRequest");
    }

    
    public serviceResult getAllAutoByUser(String sessionToken,long user,boolean userSpecified,List<HeaderProperty> headers){

        SoapObject soapReq = new SoapObject("http://Service.ru/","getAllAutoByUser");
        soapReq.addProperty("sessionToken",sessionToken);
        soapReq.addProperty("user",user);
        soapReq.addProperty("userSpecified",userSpecified);
        soapEnvelope.setOutputSoapObject(soapReq);

        return soapMethodExecutor(headers, "getAllAutoByUserRequest");
    }

    
    public serviceResult getAllRegions(String sessionToken,List<HeaderProperty> headers){

        SoapObject soapReq = new SoapObject("http://Service.ru/","getAllRegions");
        soapReq.addProperty("sessionToken",sessionToken);
        soapEnvelope.setOutputSoapObject(soapReq);

        return soapMethodExecutor(headers, "getAllRegionsRequest");
    }

    
    public serviceResult getUserInfo(String sessionToken,List<HeaderProperty> headers){

        SoapObject soapReq = new SoapObject("http://Service.ru/","getUserInfo");
        soapReq.addProperty("sessionToken",sessionToken);
        soapEnvelope.setOutputSoapObject(soapReq);

        return soapMethodExecutor(headers, "getUserInfoRequest");
    }

    
    public serviceResult insertRequest(String sessionToken,String description,double latitude,boolean latitudeSpecified,double longitude,boolean longitudeSpecified,long isResolvedByUserId,boolean isResolvedByUserIdSpecified,long typeId,boolean typeIdSpecified,long regionId,boolean regionIdSpecified,String fileName,VectorByte fileImage,List<HeaderProperty> headers){

        SoapObject soapReq = new SoapObject("http://Service.ru/","insertRequest");
        MarshalFloat marshalFloat = new MarshalFloat();
        marshalFloat.register(soapEnvelope);
        soapReq.addProperty("sessionToken",sessionToken);
        soapReq.addProperty("description",description);
        soapReq.addProperty("latitude",latitude);
        soapReq.addProperty("latitudeSpecified",latitudeSpecified);
        soapReq.addProperty("longitude",longitude);
        soapReq.addProperty("longitudeSpecified",longitudeSpecified);
        soapReq.addProperty("isResolvedByUserId",isResolvedByUserId);
        soapReq.addProperty("isResolvedByUserIdSpecified",isResolvedByUserIdSpecified);
        soapReq.addProperty("typeId",typeId);
        soapReq.addProperty("typeIdSpecified",typeIdSpecified);
        soapReq.addProperty("regionId",regionId);
        soapReq.addProperty("regionIdSpecified",regionIdSpecified);
        soapReq.addProperty("fileName",fileName);
        soapReq.addProperty("fileImage",fileImage.toString());
        soapEnvelope.setOutputSoapObject(soapReq);

        return soapMethodExecutor(headers, "insertRequestRequest");
    }

    
    public serviceResult updateRequest(long id,String sessionToken,String description,double latitude,boolean latitudeSpecified,double longitude,boolean longitudeSpecified,long statusId,boolean statusIdSpecified,long isResolvedByUser,boolean isResolvedByUserSpecified,long typeId,boolean typeIdSpecified,long regionId,boolean regionIdSpecified,String fileName,VectorByte fileImage,List<HeaderProperty> headers){

        SoapObject soapReq = new SoapObject("http://Service.ru/","updateRequest");
        MarshalFloat marshalFloat = new MarshalFloat();
        marshalFloat.register(soapEnvelope);
        soapReq.addProperty("Id",id);
        soapReq.addProperty("sessionToken",sessionToken);
        soapReq.addProperty("description",description);
        soapReq.addProperty("latitude",latitude);
        soapReq.addProperty("latitudeSpecified",latitudeSpecified);
        soapReq.addProperty("longitude",longitude);
        soapReq.addProperty("longitudeSpecified",longitudeSpecified);
        soapReq.addProperty("statusId",statusId);
        soapReq.addProperty("statusIdSpecified",statusIdSpecified);
        soapReq.addProperty("isResolvedByUser",isResolvedByUser);
        soapReq.addProperty("isResolvedByUserSpecified",isResolvedByUserSpecified);
        soapReq.addProperty("typeId",typeId);
        soapReq.addProperty("typeIdSpecified",typeIdSpecified);
        soapReq.addProperty("regionId",regionId);
        soapReq.addProperty("regionIdSpecified",regionIdSpecified);
        soapReq.addProperty("fileName",fileName);
        soapReq.addProperty("fileImage",fileImage.toString());
        soapEnvelope.setOutputSoapObject(soapReq);

        return soapMethodExecutor(headers, "updateRequestRequest");
    }

    
    public serviceResult insertUser(String name,long region,boolean regionSpecified,String password,String email,String phone,List<HeaderProperty> headers){

        SoapObject soapReq = new SoapObject("http://Service.ru/","insertUser");
        soapReq.addProperty("name",name);
        soapReq.addProperty("region",region);
        //soapReq.addProperty("regionSpecified",regionSpecified);
        soapReq.addProperty("password",password);
        soapReq.addProperty("email",email);
        soapReq.addProperty("phone",phone);
        soapEnvelope.setOutputSoapObject(soapReq);

        return soapMethodExecutor(headers, "insertUserRequest");
    }


    
    public serviceResult updateUser(String sessionToken,long region,boolean regionSpecified,String password,String fileName,VectorByte fileImage,List<HeaderProperty> headers){

        SoapObject soapReq = new SoapObject("http://Service.ru/","updateUser");
        soapReq.addProperty("sessionToken",sessionToken);
        soapReq.addProperty("region",region);
        soapReq.addProperty("regionSpecified",regionSpecified);
        soapReq.addProperty("password",password);
        soapReq.addProperty("fileName",fileName);
        soapReq.addProperty("fileImage",fileImage!=null ? fileImage.toString(): "");
        soapEnvelope.setOutputSoapObject(soapReq);

        return soapMethodExecutor(headers, "updateUserRequest");
    }


    public void updateUserPasswordAsync(String sessionToken,String password) throws Exception{
        if (this.eventHandler == null)
            throw new Exception("Async Methods Requires IWsdl2CodeEvents");
        updateUserPasswordAsync(sessionToken, password);
    }



    public serviceResult updateUserPassword(String sessionToken,String password,List<HeaderProperty> headers){

        SoapObject soapReq = new SoapObject("http://Service.ru/","updateUserPassword");
        soapReq.addProperty("sessionToken",sessionToken);
        soapReq.addProperty("password",password);

        soapEnvelope.setOutputSoapObject(soapReq);

        return soapMethodExecutor(headers, "updateUserPasswordRequest");
    }

    

    
    public serviceResult insertUpdateUserAuto(String sessionToken,String name,long haveCable,boolean haveCableSpecified,long transmissionType,boolean transmissionTypeSpecified){
        return insertUpdateUserAuto(sessionToken, name, haveCable, haveCableSpecified, transmissionType, transmissionTypeSpecified, null);
    }
    
    public serviceResult insertUpdateUserAuto(String sessionToken,String name,long haveCable,boolean haveCableSpecified,long transmissionType,boolean transmissionTypeSpecified,List<HeaderProperty> headers){

        SoapObject soapReq = new SoapObject("http://Service.ru/","insertUpdateUserAuto");
        soapReq.addProperty("sessionToken",sessionToken);
        soapReq.addProperty("name",name);
        soapReq.addProperty("haveCable",haveCable);
        soapReq.addProperty("haveCableSpecified",haveCableSpecified);
        soapReq.addProperty("transmissionType",transmissionType);
        soapReq.addProperty("transmissionTypeSpecified",transmissionTypeSpecified);
        soapEnvelope.setOutputSoapObject(soapReq);

        return soapMethodExecutor(headers, "insertUpdateUserAutoRequest");
    }

    
    public serviceResult insertUpdateUserTools(String sessionToken,String toolTypeIds,List<HeaderProperty> headers){

        SoapObject soapReq = new SoapObject("http://Service.ru/","insertUpdateUserTools");
        soapReq.addProperty("sessionToken",sessionToken);
        soapReq.addProperty("toolTypeIds",toolTypeIds);
        soapEnvelope.setOutputSoapObject(soapReq);

        return soapMethodExecutor(headers, "insertUpdateUserToolsRequest");
    }


    public serviceResult closeCurrentActiveRequestByCustomUser(String sessionToken,long userId,boolean userIdSpecified,List<HeaderProperty> headers){

        SoapObject soapReq = new SoapObject("http://Service.ru/","closeCurrentActiveRequestByCustomUser");
        soapReq.addProperty("sessionToken",sessionToken);
        soapReq.addProperty("UserId",userId);
        soapReq.addProperty("UserIdSpecified",userIdSpecified);
        soapEnvelope.setOutputSoapObject(soapReq);

        return soapMethodExecutor(headers, "closeCurrentActiveRequestByCustomUserRequest");
    }

    
    public serviceResult closeAllActiveRequestByAuthor(String sessionToken,List<HeaderProperty> headers){

        SoapObject soapReq = new SoapObject("http://Service.ru/","closeAllActiveRequestByAuthor");
        soapReq.addProperty("sessionToken",sessionToken);
        soapEnvelope.setOutputSoapObject(soapReq);
        return soapMethodExecutor(headers, "closeAllActiveRequestByAuthorRequest");
    }

    public serviceResult getFileById(String sessionToken,long id, List<HeaderProperty> headers){

        SoapObject soapReq = new SoapObject("http://Service.ru/","getFileById");
        soapReq.addProperty("sessionToken",sessionToken);
        soapReq.addProperty("id",id);
        soapEnvelope.setOutputSoapObject(soapReq);

        return soapMethodExecutor(headers, "getFileByIdRequest");
    }

    public serviceResult insertFile(
            String sessionToken,
            String fileName,
            String description,
            String fileType,
            Long createUser,
            VectorByte fileImage,
            List<HeaderProperty> headers){

        SoapObject soapReq = new SoapObject("http://Service.ru/","insertFile");
        soapReq.addProperty("sessionToken",sessionToken);
        soapReq.addProperty("fileName",fileName);
        soapReq.addProperty("description",description);
        soapReq.addProperty("fileType",fileType);
        soapReq.addProperty("createUser",createUser);
        soapReq.addProperty("fileImage",fileImage!=null ? fileImage.toString() : "");

        soapEnvelope.setOutputSoapObject(soapReq);

        return soapMethodExecutor(headers, "insertFileRequest");
    }
    
}
