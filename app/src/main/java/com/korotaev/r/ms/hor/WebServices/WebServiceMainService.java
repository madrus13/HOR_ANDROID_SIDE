package com.korotaev.r.ms.hor.WebServices;

import android.os.AsyncTask;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class WebServiceMainService {
    
    public static String NAMESPACE ="http://Service.ru/";
    public static String url="http://185.246.154.49:8080/samplejpa-29/ws?wsdl"; //?wsdl
    public String PREFIX_SERVICE  = "";
    public static int timeOut = 5000;
    public IWsdl2CodeEvents eventHandler;
    public WS_Enums.SoapProtocolVersion soapVersion;
    private static SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
    private static HttpTransportSE httpTransport = new HttpTransportSE(url,timeOut);

    public WebServiceMainService(){}
    
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
    public void getSessionTokenAsync(String name,String password) throws Exception{
        if (this.eventHandler == null)
            throw new Exception("Async Methods Requires IWsdl2CodeEvents");
        getSessionTokenAsync(name, password, null);
    }
    
    public void getSessionTokenAsync(final String name,final String password,final List<HeaderProperty> headers) throws Exception{
        
        new AsyncTask<Void, Void, serviceResult>(){
            @Override
            protected void onPreExecute() {
                eventHandler.Wsdl2CodeStartedRequest();
            };
            @Override
            protected serviceResult doInBackground(Void... params) {
                return getSessionToken(name, password, headers);
            }
            @Override
            protected void onPostExecute(serviceResult result)
            {
                eventHandler.Wsdl2CodeEndedRequest();
                if (result != null){
                    eventHandler.Wsdl2CodeFinished("getSessionToken", result);
                }
            }
        }.execute();
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

        soapEnvelope.implicitTypes = true;
        SoapObject soapReq = new SoapObject("http://Service.ru/","getSessionToken");
        soapReq.addProperty("name",name);
        soapReq.addProperty("password",password);
        soapEnvelope.setOutputSoapObject(soapReq);
        return soapMethodExecutor(headers, "getSessionTokenRequest");

    }
    
    public void insertMessageAsync(String sessionToken,String text,long requestId,boolean requestIdSpecified,long regionId,boolean regionIdSpecified,long userRx,boolean userRxSpecified,long typeId,boolean typeIdSpecified,String fileName,VectorByte fileImage) throws Exception{
        if (this.eventHandler == null)
            throw new Exception("Async Methods Requires IWsdl2CodeEvents");
        insertMessageAsync(sessionToken, text, requestId, requestIdSpecified, regionId, regionIdSpecified, userRx, userRxSpecified, typeId, typeIdSpecified, fileName, fileImage, null);
    }
    
    public void insertMessageAsync(final String sessionToken,final String text,final long requestId,final boolean requestIdSpecified,final long regionId,final boolean regionIdSpecified,final long userRx,final boolean userRxSpecified,final long typeId,final boolean typeIdSpecified,final String fileName,final VectorByte fileImage,final List<HeaderProperty> headers) throws Exception{
        
        new AsyncTask<Void, Void, serviceResult>(){
            @Override
            protected void onPreExecute() {
                eventHandler.Wsdl2CodeStartedRequest();
            };
            @Override
            protected serviceResult doInBackground(Void... params) {
                return insertMessage(sessionToken, text, requestId, requestIdSpecified, regionId, regionIdSpecified, userRx, userRxSpecified, typeId, typeIdSpecified, fileName, fileImage, headers);
            }
            @Override
            protected void onPostExecute(serviceResult result)
            {
                eventHandler.Wsdl2CodeEndedRequest();
                if (result != null){
                    eventHandler.Wsdl2CodeFinished("insertMessage", result);
                }
            }
        }.execute();
    }
    
    public serviceResult insertMessage(String sessionToken,String text,long requestId,boolean requestIdSpecified,long regionId,boolean regionIdSpecified,long userRx,boolean userRxSpecified,long typeId,boolean typeIdSpecified,String fileName,VectorByte fileImage){
        return insertMessage(sessionToken, text, requestId, requestIdSpecified, regionId, regionIdSpecified, userRx, userRxSpecified, typeId, typeIdSpecified, fileName, fileImage, null);
    }
    
    public serviceResult insertMessage(String sessionToken,String text,long requestId,boolean requestIdSpecified,long regionId,boolean regionIdSpecified,long userRx,boolean userRxSpecified,long typeId,boolean typeIdSpecified,String fileName,VectorByte fileImage,List<HeaderProperty> headers){

        soapEnvelope.implicitTypes = true;

        SoapObject soapReq = new SoapObject("http://Service.ru/","insertMessage");
        soapReq.addProperty("sessionToken",sessionToken);
        soapReq.addProperty("text",text);


        soapReq.addProperty("requestId",requestIdSpecified?requestId:"");
        //soapReq.addProperty("requestIdSpecified",requestIdSpecified);
        soapReq.addProperty("regionId",regionIdSpecified?regionId:"");
        //soapReq.addProperty("regionIdSpecified",regionIdSpecified);
        soapReq.addProperty("userRx",userRxSpecified?userRx:"");
        //soapReq.addProperty("userRxSpecified",userRxSpecified);
        soapReq.addProperty("typeId",typeIdSpecified?typeId:"");
        //soapReq.addProperty("typeIdSpecified",typeIdSpecified);

        soapReq.addProperty("fileName",fileName!=null ? fileName : "");
        soapReq.addProperty("fileImage",fileImage!=null ? fileImage.toString() : "");
        soapEnvelope.setOutputSoapObject(soapReq);

        return soapMethodExecutor(headers, "insertMessageRequest");

    }
    
    public void getMessageByRegionAndIdGreaterAsync(String sessionToken,
                                                    long regionId, boolean regionIdSpecified,
                                                    long lastId, boolean lastIdSpecified,
                                                    int pageSize
    ) throws Exception{
        if (this.eventHandler == null)
            throw new Exception("Async Methods Requires IWsdl2CodeEvents");
        getMessageByRegionAndIdGreaterAsync(sessionToken, regionId, regionIdSpecified, lastId, lastIdSpecified, pageSize, null);
    }
    
    public void getMessageByRegionAndIdGreaterAsync(final String sessionToken,final long regionId,final boolean regionIdSpecified,final long lastId,final boolean lastIdSpecified,final int pageSize,final List<HeaderProperty> headers) throws Exception{
        
        new AsyncTask<Void, Void, serviceResult>(){
            @Override
            protected void onPreExecute() {
                eventHandler.Wsdl2CodeStartedRequest();
            };
            @Override
            protected serviceResult doInBackground(Void... params) {
                return getMessageByRegionAndIdGreater(sessionToken, regionId, regionIdSpecified, lastId, lastIdSpecified, pageSize, headers);
            }
            @Override
            protected void onPostExecute(serviceResult result)
            {
                eventHandler.Wsdl2CodeEndedRequest();
                if (result != null){
                    eventHandler.Wsdl2CodeFinished("getMessageByRegionAndIdGreater", result);
                }
            }
        }.execute();
    }
    
    public serviceResult getMessageByRegionAndIdGreater(String sessionToken,long regionId,boolean regionIdSpecified,long lastId,boolean lastIdSpecified,int pageSize){
        return getMessageByRegionAndIdGreater(sessionToken, regionId, regionIdSpecified, lastId, lastIdSpecified, pageSize, null);
    }
    
    public serviceResult getMessageByRegionAndIdGreater(String sessionToken,long regionId,boolean regionIdSpecified,long lastId,boolean lastIdSpecified,int pageSize,List<HeaderProperty> headers){

        soapEnvelope.implicitTypes = true;
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
    
    public void getAllMessageByRequestAsync(String sessionToken,long request,boolean requestSpecified,int pageSize) throws Exception{
        if (this.eventHandler == null)
            throw new Exception("Async Methods Requires IWsdl2CodeEvents");
        getAllMessageByRequestAsync(sessionToken, request, requestSpecified, pageSize, null);
    }
    
    public void getAllMessageByRequestAsync(final String sessionToken,final long request,final boolean requestSpecified,final int pageSize,final List<HeaderProperty> headers) throws Exception{
        
        new AsyncTask<Void, Void, serviceResult>(){
            @Override
            protected void onPreExecute() {
                eventHandler.Wsdl2CodeStartedRequest();
            };
            @Override
            protected serviceResult doInBackground(Void... params) {
                return getAllMessageByRequest(sessionToken, request, requestSpecified, pageSize, headers);
            }
            @Override
            protected void onPostExecute(serviceResult result)
            {
                eventHandler.Wsdl2CodeEndedRequest();
                if (result != null){
                    eventHandler.Wsdl2CodeFinished("getAllMessageByRequest", result);
                }
            }
        }.execute();
    }
    
    public serviceResult getAllMessageByRequest(String sessionToken,long request,boolean requestSpecified,int pageSize){
        return getAllMessageByRequest(sessionToken, request, requestSpecified, pageSize, null);
    }
    
    public serviceResult getAllMessageByRequest(String sessionToken,long request,boolean requestSpecified,int pageSize,List<HeaderProperty> headers){

        soapEnvelope.implicitTypes = true;

        SoapObject soapReq = new SoapObject("http://Service.ru/","getAllMessageByRequest");
        soapReq.addProperty("sessionToken",sessionToken);
        soapReq.addProperty("request",request);
        soapReq.addProperty("requestSpecified",requestSpecified);
        soapReq.addProperty("pageSize",pageSize);
        soapEnvelope.setOutputSoapObject(soapReq);
        return soapMethodExecutor(headers, "getAllMessageByRequestRequest");
    }
    
    public void getAllRequestByCreationUserAsync(String sessionToken,long userId,boolean userIdSpecified) throws Exception{
        if (this.eventHandler == null)
            throw new Exception("Async Methods Requires IWsdl2CodeEvents");
        getAllRequestByCreationUserAsync(sessionToken, userId, userIdSpecified, null);
    }
    
    public void getAllRequestByCreationUserAsync(final String sessionToken,final long userId,final boolean userIdSpecified,final List<HeaderProperty> headers) throws Exception{
        
        new AsyncTask<Void, Void, serviceResult>(){
            @Override
            protected void onPreExecute() {
                eventHandler.Wsdl2CodeStartedRequest();
            };
            @Override
            protected serviceResult doInBackground(Void... params) {
                return getAllRequestByCreationUser(sessionToken, userId, userIdSpecified, headers);
            }
            @Override
            protected void onPostExecute(serviceResult result)
            {
                eventHandler.Wsdl2CodeEndedRequest();
                if (result != null){
                    eventHandler.Wsdl2CodeFinished("getAllRequestByCreationUser", result);
                }
            }
        }.execute();
    }
    
    public serviceResult getAllRequestByCreationUser(String sessionToken,long userId,boolean userIdSpecified){
        return getAllRequestByCreationUser(sessionToken, userId, userIdSpecified, null);
    }
    
    public serviceResult getAllRequestByCreationUser(String sessionToken,long userId,boolean userIdSpecified,List<HeaderProperty> headers){

        soapEnvelope.implicitTypes = true;

        SoapObject soapReq = new SoapObject("http://Service.ru/","getAllRequestByCreationUser");
        soapReq.addProperty("sessionToken",sessionToken);
        soapReq.addProperty("userId",userId);
        soapReq.addProperty("userIdSpecified",userIdSpecified);
        soapEnvelope.setOutputSoapObject(soapReq);

        return soapMethodExecutor(headers, "getAllRequestByCreationUserRequest");
    }
    
    public void getActiveRequestByCreationUserAsync(String sessionToken,long userId,boolean userIdSpecified) throws Exception{
        if (this.eventHandler == null)
            throw new Exception("Async Methods Requires IWsdl2CodeEvents");
        getActiveRequestByCreationUserAsync(sessionToken, userId, userIdSpecified, null);
    }
    
    public void getActiveRequestByCreationUserAsync(final String sessionToken,final long userId,final boolean userIdSpecified,final List<HeaderProperty> headers) throws Exception{
        
        new AsyncTask<Void, Void, serviceResult>(){
            @Override
            protected void onPreExecute() {
                eventHandler.Wsdl2CodeStartedRequest();
            };
            @Override
            protected serviceResult doInBackground(Void... params) {
                return getActiveRequestByCreationUser(sessionToken, userId, userIdSpecified, headers);
            }
            @Override
            protected void onPostExecute(serviceResult result)
            {
                eventHandler.Wsdl2CodeEndedRequest();
                if (result != null){
                    eventHandler.Wsdl2CodeFinished("getActiveRequestByCreationUser", result);
                }
            }
        }.execute();
    }
    
    public serviceResult getActiveRequestByCreationUser(String sessionToken,long userId,boolean userIdSpecified){
        return getActiveRequestByCreationUser(sessionToken, userId, userIdSpecified, null);
    }
    
    public serviceResult getActiveRequestByCreationUser(String sessionToken,long userId,boolean userIdSpecified,List<HeaderProperty> headers){

        soapEnvelope.implicitTypes = true;

        SoapObject soapReq = new SoapObject("http://Service.ru/","getActiveRequestByCreationUser");
        soapReq.addProperty("sessionToken",sessionToken);
        soapReq.addProperty("userId",userId);
        soapReq.addProperty("userIdSpecified",userIdSpecified);
        soapEnvelope.setOutputSoapObject(soapReq);

        return soapMethodExecutor(headers, "getActiveRequestByCreationUserRequest");
    }
    
    public void getAllOpenRequestByRegionAsync(String sessionToken,long regionId,boolean regionIdSpecified,String typeIds) throws Exception{
        if (this.eventHandler == null)
            throw new Exception("Async Methods Requires IWsdl2CodeEvents");
        getAllOpenRequestByRegionAsync(sessionToken, regionId, regionIdSpecified, typeIds, null);
    }
    
    public void getAllOpenRequestByRegionAsync(final String sessionToken,final long regionId,final boolean regionIdSpecified,final String typeIds,final List<HeaderProperty> headers) throws Exception{
        
        new AsyncTask<Void, Void, serviceResult>(){
            @Override
            protected void onPreExecute() {
                eventHandler.Wsdl2CodeStartedRequest();
            };
            @Override
            protected serviceResult doInBackground(Void... params) {
                return getAllOpenRequestByRegion(sessionToken, regionId, regionIdSpecified, typeIds, headers);
            }
            @Override
            protected void onPostExecute(serviceResult result)
            {
                eventHandler.Wsdl2CodeEndedRequest();
                if (result != null){
                    eventHandler.Wsdl2CodeFinished("getAllOpenRequestByRegion", result);
                }
            }
        }.execute();
    }
    
    public serviceResult getAllOpenRequestByRegion(String sessionToken,long regionId,boolean regionIdSpecified,String typeIds){
        return getAllOpenRequestByRegion(sessionToken, regionId, regionIdSpecified, typeIds, null);
    }
    
    public serviceResult getAllOpenRequestByRegion(String sessionToken,long regionId,boolean regionIdSpecified,String typeIds,List<HeaderProperty> headers){

        soapEnvelope.implicitTypes = true;

        SoapObject soapReq = new SoapObject("http://Service.ru/","getAllOpenRequestByRegion");
        soapReq.addProperty("sessionToken",sessionToken);
        soapReq.addProperty("regionId",regionId);
        soapReq.addProperty("regionIdSpecified",regionIdSpecified);
        soapReq.addProperty("typeIds",typeIds);
        soapEnvelope.setOutputSoapObject(soapReq);

        return soapMethodExecutor(headers, "getAllOpenRequestByRegionRequest");
    }
    
    public void findRequestResolvedByCurrentUserWithTypeFilterAsync(String sessionToken,String typeIds) throws Exception{
        if (this.eventHandler == null)
            throw new Exception("Async Methods Requires IWsdl2CodeEvents");
        findRequestResolvedByCurrentUserWithTypeFilterAsync(sessionToken, typeIds, null);
    }
    
    public void findRequestResolvedByCurrentUserWithTypeFilterAsync(final String sessionToken,final String typeIds,final List<HeaderProperty> headers) throws Exception{
        
        new AsyncTask<Void, Void, serviceResult>(){
            @Override
            protected void onPreExecute() {
                eventHandler.Wsdl2CodeStartedRequest();
            };
            @Override
            protected serviceResult doInBackground(Void... params) {
                return findRequestResolvedByCurrentUserWithTypeFilter(sessionToken, typeIds, headers);
            }
            @Override
            protected void onPostExecute(serviceResult result)
            {
                eventHandler.Wsdl2CodeEndedRequest();
                if (result != null){
                    eventHandler.Wsdl2CodeFinished("findRequestResolvedByCurrentUserWithTypeFilter", result);
                }
            }
        }.execute();
    }
    
    public serviceResult findRequestResolvedByCurrentUserWithTypeFilter(String sessionToken,String typeIds){
        return findRequestResolvedByCurrentUserWithTypeFilter(sessionToken, typeIds, null);
    }
    
    public serviceResult findRequestResolvedByCurrentUserWithTypeFilter(String sessionToken,String typeIds,List<HeaderProperty> headers){

        soapEnvelope.implicitTypes = true;

        SoapObject soapReq = new SoapObject("http://Service.ru/","findRequestResolvedByCurrentUserWithTypeFilter");
        soapReq.addProperty("sessionToken",sessionToken);
        soapReq.addProperty("typeIds",typeIds);
        soapEnvelope.setOutputSoapObject(soapReq);

        return soapMethodExecutor(headers, "findRequestResolvedByCurrentUserWithTypeFilterRequest");
    }
    
    public void getAllMessageTypesAsync(String sessionToken) throws Exception{
        if (this.eventHandler == null)
            throw new Exception("Async Methods Requires IWsdl2CodeEvents");
        getAllMessageTypesAsync(sessionToken, null);
    }
    
    public void getAllMessageTypesAsync(final String sessionToken,final List<HeaderProperty> headers) throws Exception{
        
        new AsyncTask<Void, Void, serviceResult>(){
            @Override
            protected void onPreExecute() {
                eventHandler.Wsdl2CodeStartedRequest();
            };
            @Override
            protected serviceResult doInBackground(Void... params) {
                return getAllMessageTypes(sessionToken, headers);
            }
            @Override
            protected void onPostExecute(serviceResult result)
            {
                eventHandler.Wsdl2CodeEndedRequest();
                if (result != null){
                    eventHandler.Wsdl2CodeFinished("getAllMessageTypes", result);
                }
            }
        }.execute();
    }
    
    public serviceResult getAllMessageTypes(String sessionToken){
        return getAllMessageTypes(sessionToken, null);
    }
    
    public serviceResult getAllMessageTypes(String sessionToken,List<HeaderProperty> headers){

        soapEnvelope.implicitTypes = true;

        SoapObject soapReq = new SoapObject("http://Service.ru/","getAllMessageTypes");
        soapReq.addProperty("sessionToken",sessionToken);
        soapEnvelope.setOutputSoapObject(soapReq);

        return soapMethodExecutor(headers, "getAllMessageTypesRequest");
    }
    
    public void getAllRequestTypeAsync(String sessionToken) throws Exception{
        if (this.eventHandler == null)
            throw new Exception("Async Methods Requires IWsdl2CodeEvents");
        getAllRequestTypeAsync(sessionToken, null);
    }
    
    public void getAllRequestTypeAsync(final String sessionToken,final List<HeaderProperty> headers) throws Exception{
        
        new AsyncTask<Void, Void, serviceResult>(){
            @Override
            protected void onPreExecute() {
                eventHandler.Wsdl2CodeStartedRequest();
            };
            @Override
            protected serviceResult doInBackground(Void... params) {
                return getAllRequestType(sessionToken, headers);
            }
            @Override
            protected void onPostExecute(serviceResult result)
            {
                eventHandler.Wsdl2CodeEndedRequest();
                if (result != null){
                    eventHandler.Wsdl2CodeFinished("getAllRequestType", result);
                }
            }
        }.execute();
    }
    
    public serviceResult getAllRequestType(String sessionToken){
        return getAllRequestType(sessionToken, null);
    }
    
    public serviceResult getAllRequestType(String sessionToken,List<HeaderProperty> headers){

        soapEnvelope.implicitTypes = true;
        //soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject("http://Service.ru/","getAllRequestType");
        soapReq.addProperty("sessionToken",sessionToken);
        soapEnvelope.setOutputSoapObject(soapReq);

        return soapMethodExecutor(headers, "getAllRequestTypeRequest");
    }
    
    public void getAllTransmissionTypeAsync(String sessionToken) throws Exception{
        if (this.eventHandler == null)
            throw new Exception("Async Methods Requires IWsdl2CodeEvents");
        getAllTransmissionTypeAsync(sessionToken, null);
    }
    
    public void getAllTransmissionTypeAsync(final String sessionToken,final List<HeaderProperty> headers) throws Exception{
        
        new AsyncTask<Void, Void, serviceResult>(){
            @Override
            protected void onPreExecute() {
                eventHandler.Wsdl2CodeStartedRequest();
            };
            @Override
            protected serviceResult doInBackground(Void... params) {
                return getAllTransmissionType(sessionToken, headers);
            }
            @Override
            protected void onPostExecute(serviceResult result)
            {
                eventHandler.Wsdl2CodeEndedRequest();
                if (result != null){
                    eventHandler.Wsdl2CodeFinished("getAllTransmissionType", result);
                }
            }
        }.execute();
    }
    
    public serviceResult getAllTransmissionType(String sessionToken){
        return getAllTransmissionType(sessionToken, null);
    }
    
    public serviceResult getAllTransmissionType(String sessionToken,List<HeaderProperty> headers){
        ;
        soapEnvelope.implicitTypes = true;

        SoapObject soapReq = new SoapObject("http://Service.ru/","getAllTransmissionType");
        soapReq.addProperty("sessionToken",sessionToken);
        soapEnvelope.setOutputSoapObject(soapReq);

        return soapMethodExecutor(headers, "getAllTransmissionTypeRequest");
    }
    
    public void getAllToolTypeAsync(String sessionToken) throws Exception{
        if (this.eventHandler == null)
            throw new Exception("Async Methods Requires IWsdl2CodeEvents");
        getAllToolTypeAsync(sessionToken, null);
    }
    
    public void getAllToolTypeAsync(final String sessionToken,final List<HeaderProperty> headers) throws Exception{
        
        new AsyncTask<Void, Void, serviceResult>(){
            @Override
            protected void onPreExecute() {
                eventHandler.Wsdl2CodeStartedRequest();
            };
            @Override
            protected serviceResult doInBackground(Void... params) {
                return getAllToolType(sessionToken, headers);
            }
            @Override
            protected void onPostExecute(serviceResult result)
            {
                eventHandler.Wsdl2CodeEndedRequest();
                if (result != null){
                    eventHandler.Wsdl2CodeFinished("getAllToolType", result);
                }
            }
        }.execute();
    }
    
    public serviceResult getAllToolType(String sessionToken){
        return getAllToolType(sessionToken, null);
    }
    
    public serviceResult getAllToolType(String sessionToken,List<HeaderProperty> headers){

        soapEnvelope.implicitTypes = true;

        SoapObject soapReq = new SoapObject("http://Service.ru/","getAllToolType");
        soapReq.addProperty("sessionToken",sessionToken);
        soapEnvelope.setOutputSoapObject(soapReq);

        return soapMethodExecutor(headers, "getAllToolTypeRequest");
    }
    
    public void getAllAchievmenttypeAsync(String sessionToken) throws Exception{
        if (this.eventHandler == null)
            throw new Exception("Async Methods Requires IWsdl2CodeEvents");
        getAllAchievmenttypeAsync(sessionToken, null);
    }
    
    public void getAllAchievmenttypeAsync(final String sessionToken,final List<HeaderProperty> headers) throws Exception{
        
        new AsyncTask<Void, Void, serviceResult>(){
            @Override
            protected void onPreExecute() {
                eventHandler.Wsdl2CodeStartedRequest();
            };
            @Override
            protected serviceResult doInBackground(Void... params) {
                return getAllAchievmenttype(sessionToken, headers);
            }
            @Override
            protected void onPostExecute(serviceResult result)
            {
                eventHandler.Wsdl2CodeEndedRequest();
                if (result != null){
                    eventHandler.Wsdl2CodeFinished("getAllAchievmenttype", result);
                }
            }
        }.execute();
    }
    
    public serviceResult getAllAchievmenttype(String sessionToken){
        return getAllAchievmenttype(sessionToken, null);
    }
    
    public serviceResult getAllAchievmenttype(String sessionToken,List<HeaderProperty> headers){

        soapEnvelope.implicitTypes = true;
        //soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject("http://Service.ru/","getAllAchievmenttype");
        soapReq.addProperty("sessionToken",sessionToken);
        soapEnvelope.setOutputSoapObject(soapReq);

        return soapMethodExecutor(headers, "getAllAchievmenttypeRequest");
    }
    
    public void getAllAchievmentByUserAsync(String sessionToken,long user,boolean userSpecified) throws Exception{
        if (this.eventHandler == null)
            throw new Exception("Async Methods Requires IWsdl2CodeEvents");
        getAllAchievmentByUserAsync(sessionToken, user, userSpecified, null);
    }
    
    public void getAllAchievmentByUserAsync(final String sessionToken,final long user,final boolean userSpecified,final List<HeaderProperty> headers) throws Exception{
        
        new AsyncTask<Void, Void, serviceResult>(){
            @Override
            protected void onPreExecute() {
                eventHandler.Wsdl2CodeStartedRequest();
            };
            @Override
            protected serviceResult doInBackground(Void... params) {
                return getAllAchievmentByUser(sessionToken, user, userSpecified, headers);
            }
            @Override
            protected void onPostExecute(serviceResult result)
            {
                eventHandler.Wsdl2CodeEndedRequest();
                if (result != null){
                    eventHandler.Wsdl2CodeFinished("getAllAchievmentByUser", result);
                }
            }
        }.execute();
    }
    
    public serviceResult getAllAchievmentByUser(String sessionToken,long user,boolean userSpecified){
        return getAllAchievmentByUser(sessionToken, user, userSpecified, null);
    }
    
    public serviceResult getAllAchievmentByUser(String sessionToken,long user,boolean userSpecified,List<HeaderProperty> headers){

        soapEnvelope.implicitTypes = true;
        //soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject("http://Service.ru/","getAllAchievmentByUser");
        soapReq.addProperty("sessionToken",sessionToken);
        soapReq.addProperty("user",user);
        soapReq.addProperty("userSpecified",userSpecified);
        soapEnvelope.setOutputSoapObject(soapReq);

        return soapMethodExecutor(headers, "getAllAchievmentByUserRequest");
    }
    
    public void getAllToolByUserAsync(String sessionToken,long user,boolean userSpecified) throws Exception{
        if (this.eventHandler == null)
            throw new Exception("Async Methods Requires IWsdl2CodeEvents");
        getAllToolByUserAsync(sessionToken, user, userSpecified, null);
    }
    
    public void getAllToolByUserAsync(final String sessionToken,final long user,final boolean userSpecified,final List<HeaderProperty> headers) throws Exception{
        
        new AsyncTask<Void, Void, serviceResult>(){
            @Override
            protected void onPreExecute() {
                eventHandler.Wsdl2CodeStartedRequest();
            };
            @Override
            protected serviceResult doInBackground(Void... params) {
                return getAllToolByUser(sessionToken, user, userSpecified, headers);
            }
            @Override
            protected void onPostExecute(serviceResult result)
            {
                eventHandler.Wsdl2CodeEndedRequest();
                if (result != null){
                    eventHandler.Wsdl2CodeFinished("getAllToolByUser", result);
                }
            }
        }.execute();
    }
    
    public serviceResult getAllToolByUser(String sessionToken,long user,boolean userSpecified){
        return getAllToolByUser(sessionToken, user, userSpecified, null);
    }
    
    public serviceResult getAllToolByUser(String sessionToken,long user,boolean userSpecified,List<HeaderProperty> headers){

        soapEnvelope.implicitTypes = true;
        //soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject("http://Service.ru/","getAllToolByUser");
        soapReq.addProperty("sessionToken",sessionToken);
        soapReq.addProperty("user",user);
        soapReq.addProperty("userSpecified",userSpecified);
        soapEnvelope.setOutputSoapObject(soapReq);

        return soapMethodExecutor(headers, "getAllToolByUserRequest");
    }
    
    public void getAllAutoByUserAsync(String sessionToken,long user,boolean userSpecified) throws Exception{
        if (this.eventHandler == null)
            throw new Exception("Async Methods Requires IWsdl2CodeEvents");
        getAllAutoByUserAsync(sessionToken, user, userSpecified, null);
    }
    
    public void getAllAutoByUserAsync(final String sessionToken,final long user,final boolean userSpecified,final List<HeaderProperty> headers) throws Exception{
        
        new AsyncTask<Void, Void, serviceResult>(){
            @Override
            protected void onPreExecute() {
                eventHandler.Wsdl2CodeStartedRequest();
            };
            @Override
            protected serviceResult doInBackground(Void... params) {
                return getAllAutoByUser(sessionToken, user, userSpecified, headers);
            }
            @Override
            protected void onPostExecute(serviceResult result)
            {
                eventHandler.Wsdl2CodeEndedRequest();
                if (result != null){
                    eventHandler.Wsdl2CodeFinished("getAllAutoByUser", result);
                }
            }
        }.execute();
    }
    
    public serviceResult getAllAutoByUser(String sessionToken,long user,boolean userSpecified){
        return getAllAutoByUser(sessionToken, user, userSpecified, null);
    }
    
    public serviceResult getAllAutoByUser(String sessionToken,long user,boolean userSpecified,List<HeaderProperty> headers){

        soapEnvelope.implicitTypes = true;

        SoapObject soapReq = new SoapObject("http://Service.ru/","getAllAutoByUser");
        soapReq.addProperty("sessionToken",sessionToken);
        soapReq.addProperty("user",user);
        soapReq.addProperty("userSpecified",userSpecified);
        soapEnvelope.setOutputSoapObject(soapReq);

        return soapMethodExecutor(headers, "getAllAutoByUserRequest");
    }
    
    public void getAllRegionsAsync(String sessionToken) throws Exception{
        if (this.eventHandler == null)
            throw new Exception("Async Methods Requires IWsdl2CodeEvents");
        getAllRegionsAsync(sessionToken, null);
    }
    
    public void getAllRegionsAsync(final String sessionToken,final List<HeaderProperty> headers) throws Exception{
        
        new AsyncTask<Void, Void, serviceResult>(){
            @Override
            protected void onPreExecute() {
                eventHandler.Wsdl2CodeStartedRequest();
            };
            @Override
            protected serviceResult doInBackground(Void... params) {
                return getAllRegions(sessionToken, headers);
            }
            @Override
            protected void onPostExecute(serviceResult result)
            {
                eventHandler.Wsdl2CodeEndedRequest();
                if (result != null){
                    eventHandler.Wsdl2CodeFinished("getAllRegions", result);
                }
            }
        }.execute();
    }
    
    public serviceResult getAllRegions(String sessionToken){
        return getAllRegions(sessionToken, null);
    }
    
    public serviceResult getAllRegions(String sessionToken,List<HeaderProperty> headers){

        soapEnvelope.implicitTypes = true;

        SoapObject soapReq = new SoapObject("http://Service.ru/","getAllRegions");
        soapReq.addProperty("sessionToken",sessionToken);
        soapEnvelope.setOutputSoapObject(soapReq);

        return soapMethodExecutor(headers, "getAllRegionsRequest");
    }
    
    public void getUserInfoAsync(String sessionToken) throws Exception{
        if (this.eventHandler == null)
            throw new Exception("Async Methods Requires IWsdl2CodeEvents");
        getUserInfoAsync(sessionToken, null);
    }
    
    public void getUserInfoAsync(final String sessionToken,final List<HeaderProperty> headers) throws Exception{
        
        new AsyncTask<Void, Void, serviceResult>(){
            @Override
            protected void onPreExecute() {
                eventHandler.Wsdl2CodeStartedRequest();
            };
            @Override
            protected serviceResult doInBackground(Void... params) {
                return getUserInfo(sessionToken, headers);
            }
            @Override
            protected void onPostExecute(serviceResult result)
            {
                eventHandler.Wsdl2CodeEndedRequest();
                if (result != null){
                    eventHandler.Wsdl2CodeFinished("getUserInfo", result);
                }
            }
        }.execute();
    }
    
    public serviceResult getUserInfo(String sessionToken){
        return getUserInfo(sessionToken, null);
    }
    
    public serviceResult getUserInfo(String sessionToken,List<HeaderProperty> headers){

        soapEnvelope.implicitTypes = true;

        SoapObject soapReq = new SoapObject("http://Service.ru/","getUserInfo");
        soapReq.addProperty("sessionToken",sessionToken);
        soapEnvelope.setOutputSoapObject(soapReq);

        return soapMethodExecutor(headers, "getUserInfoRequest");
    }
    
    public void insertRequestAsync(String sessionToken,String description,double latitude,boolean latitudeSpecified,double longitude,boolean longitudeSpecified,long isResolvedByUserId,boolean isResolvedByUserIdSpecified,long typeId,boolean typeIdSpecified,long regionId,boolean regionIdSpecified,String fileName,VectorByte fileImage) throws Exception{
        if (this.eventHandler == null)
            throw new Exception("Async Methods Requires IWsdl2CodeEvents");
        insertRequestAsync(sessionToken, description, latitude, latitudeSpecified, longitude, longitudeSpecified, isResolvedByUserId, isResolvedByUserIdSpecified, typeId, typeIdSpecified, regionId, regionIdSpecified, fileName, fileImage, null);
    }
    
    public void insertRequestAsync(final String sessionToken,final String description,final double latitude,final boolean latitudeSpecified,final double longitude,final boolean longitudeSpecified,final long isResolvedByUserId,final boolean isResolvedByUserIdSpecified,final long typeId,final boolean typeIdSpecified,final long regionId,final boolean regionIdSpecified,final String fileName,final VectorByte fileImage,final List<HeaderProperty> headers) throws Exception{
        
        new AsyncTask<Void, Void, serviceResult>(){
            @Override
            protected void onPreExecute() {
                eventHandler.Wsdl2CodeStartedRequest();
            };
            @Override
            protected serviceResult doInBackground(Void... params) {
                return insertRequest(sessionToken, description, latitude, latitudeSpecified, longitude, longitudeSpecified, isResolvedByUserId, isResolvedByUserIdSpecified, typeId, typeIdSpecified, regionId, regionIdSpecified, fileName, fileImage, headers);
            }
            @Override
            protected void onPostExecute(serviceResult result)
            {
                eventHandler.Wsdl2CodeEndedRequest();
                if (result != null){
                    eventHandler.Wsdl2CodeFinished("insertRequest", result);
                }
            }
        }.execute();
    }
    
    public serviceResult insertRequest(String sessionToken,String description,double latitude,boolean latitudeSpecified,double longitude,boolean longitudeSpecified,long isResolvedByUserId,boolean isResolvedByUserIdSpecified,long typeId,boolean typeIdSpecified,long regionId,boolean regionIdSpecified,String fileName,VectorByte fileImage){
        return insertRequest(sessionToken, description, latitude, latitudeSpecified, longitude, longitudeSpecified, isResolvedByUserId, isResolvedByUserIdSpecified, typeId, typeIdSpecified, regionId, regionIdSpecified, fileName, fileImage, null);
    }
    
    public serviceResult insertRequest(String sessionToken,String description,double latitude,boolean latitudeSpecified,double longitude,boolean longitudeSpecified,long isResolvedByUserId,boolean isResolvedByUserIdSpecified,long typeId,boolean typeIdSpecified,long regionId,boolean regionIdSpecified,String fileName,VectorByte fileImage,List<HeaderProperty> headers){

        soapEnvelope.implicitTypes = true;

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
    
    public void updateRequestAsync(long id,String sessionToken,String description,double latitude,boolean latitudeSpecified,double longitude,boolean longitudeSpecified,long statusId,boolean statusIdSpecified,long isResolvedByUser,boolean isResolvedByUserSpecified,long typeId,boolean typeIdSpecified,long regionId,boolean regionIdSpecified,String fileName,VectorByte fileImage) throws Exception{
        if (this.eventHandler == null)
            throw new Exception("Async Methods Requires IWsdl2CodeEvents");
        updateRequestAsync(id, sessionToken, description, latitude, latitudeSpecified, longitude, longitudeSpecified, statusId, statusIdSpecified, isResolvedByUser, isResolvedByUserSpecified, typeId, typeIdSpecified, regionId, regionIdSpecified, fileName, fileImage, null);
    }
    
    public void updateRequestAsync(final long id,final String sessionToken,final String description,final double latitude,final boolean latitudeSpecified,final double longitude,final boolean longitudeSpecified,final long statusId,final boolean statusIdSpecified,final long isResolvedByUser,final boolean isResolvedByUserSpecified,final long typeId,final boolean typeIdSpecified,final long regionId,final boolean regionIdSpecified,final String fileName,final VectorByte fileImage,final List<HeaderProperty> headers) throws Exception{
        
        new AsyncTask<Void, Void, serviceResult>(){
            @Override
            protected void onPreExecute() {
                eventHandler.Wsdl2CodeStartedRequest();
            };
            @Override
            protected serviceResult doInBackground(Void... params) {
                return updateRequest(id, sessionToken, description, latitude, latitudeSpecified, longitude, longitudeSpecified, statusId, statusIdSpecified, isResolvedByUser, isResolvedByUserSpecified, typeId, typeIdSpecified, regionId, regionIdSpecified, fileName, fileImage, headers);
            }
            @Override
            protected void onPostExecute(serviceResult result)
            {
                eventHandler.Wsdl2CodeEndedRequest();
                if (result != null){
                    eventHandler.Wsdl2CodeFinished("updateRequest", result);
                }
            }
        }.execute();
    }
    
    public serviceResult updateRequest(long id,String sessionToken,String description,double latitude,boolean latitudeSpecified,double longitude,boolean longitudeSpecified,long statusId,boolean statusIdSpecified,long isResolvedByUser,boolean isResolvedByUserSpecified,long typeId,boolean typeIdSpecified,long regionId,boolean regionIdSpecified,String fileName,VectorByte fileImage){
        return updateRequest(id, sessionToken, description, latitude, latitudeSpecified, longitude, longitudeSpecified, statusId, statusIdSpecified, isResolvedByUser, isResolvedByUserSpecified, typeId, typeIdSpecified, regionId, regionIdSpecified, fileName, fileImage, null);
    }
    
    public serviceResult updateRequest(long id,String sessionToken,String description,double latitude,boolean latitudeSpecified,double longitude,boolean longitudeSpecified,long statusId,boolean statusIdSpecified,long isResolvedByUser,boolean isResolvedByUserSpecified,long typeId,boolean typeIdSpecified,long regionId,boolean regionIdSpecified,String fileName,VectorByte fileImage,List<HeaderProperty> headers){

        soapEnvelope.implicitTypes = true;

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
    
    public void insertUserAsync(String name,long region,boolean regionSpecified,String password,String email,String phone) throws Exception{
        if (this.eventHandler == null)
            throw new Exception("Async Methods Requires IWsdl2CodeEvents");
        insertUserAsync(name, region, regionSpecified, password, email, phone, null);
    }
    
    public void insertUserAsync(final String name,final long region,final boolean regionSpecified,final String password,final String email,final String phone,final List<HeaderProperty> headers) throws Exception{
        
        new AsyncTask<Void, Void, serviceResult>(){
            @Override
            protected void onPreExecute() {
                eventHandler.Wsdl2CodeStartedRequest();
            };
            @Override
            protected serviceResult doInBackground(Void... params) {
                return insertUser(name, region, regionSpecified, password, email, phone, headers);
            }
            @Override
            protected void onPostExecute(serviceResult result)
            {
                eventHandler.Wsdl2CodeEndedRequest();
                if (result != null){
                    eventHandler.Wsdl2CodeFinished("insertUser", result);
                }
            }
        }.execute();
    }
    
    public serviceResult insertUser(String name,long region,boolean regionSpecified,String password,String email,String phone){
        return insertUser(name, region, regionSpecified, password, email, phone, null);
    }
    
    public serviceResult insertUser(String name,long region,boolean regionSpecified,String password,String email,String phone,List<HeaderProperty> headers){

        soapEnvelope.implicitTypes = true;

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
    
    public void updateUserAsync(String sessionToken,long region,boolean regionSpecified,String password,String fileName,VectorByte fileImage) throws Exception{
        if (this.eventHandler == null)
            throw new Exception("Async Methods Requires IWsdl2CodeEvents");
        updateUserAsync(sessionToken, region, regionSpecified, password, fileName, fileImage, null);
    }
    
    public void updateUserAsync(final String sessionToken,final long region,final boolean regionSpecified,final String password,final String fileName,final VectorByte fileImage,final List<HeaderProperty> headers) throws Exception{
        
        new AsyncTask<Void, Void, serviceResult>(){
            @Override
            protected void onPreExecute() {
                eventHandler.Wsdl2CodeStartedRequest();
            };
            @Override
            protected serviceResult doInBackground(Void... params) {
                return updateUser(sessionToken, region, regionSpecified, password, fileName, fileImage, headers);
            }
            @Override
            protected void onPostExecute(serviceResult result)
            {
                eventHandler.Wsdl2CodeEndedRequest();
                if (result != null){
                    eventHandler.Wsdl2CodeFinished("updateUser", result);
                }
            }
        }.execute();
    }
    
    public serviceResult updateUser(String sessionToken,long region,boolean regionSpecified,String password,String fileName,VectorByte fileImage){
        return updateUser(sessionToken, region, regionSpecified, password, fileName, fileImage, null);
    }
    
    public serviceResult updateUser(String sessionToken,long region,boolean regionSpecified,String password,String fileName,VectorByte fileImage,List<HeaderProperty> headers){

        soapEnvelope.implicitTypes = true;

        SoapObject soapReq = new SoapObject("http://Service.ru/","updateUser");
        soapReq.addProperty("sessionToken",sessionToken);
        soapReq.addProperty("region",region);
        soapReq.addProperty("regionSpecified",regionSpecified);
        soapReq.addProperty("password",password);
        soapReq.addProperty("fileName",fileName);
        soapReq.addProperty("fileImage",fileImage!=null ? fileImage.toString() : "");
        soapEnvelope.setOutputSoapObject(soapReq);

        return soapMethodExecutor(headers, "updateUserRequest");
    }


    public void updateUserPasswordAsync(String sessionToken,String password) throws Exception{
        if (this.eventHandler == null)
            throw new Exception("Async Methods Requires IWsdl2CodeEvents");
        updateUserPasswordAsync(sessionToken, password);
    }

    public void updateUserPasswordAsync(final String sessionToken,final String password,final List<HeaderProperty> headers) throws Exception{

        new AsyncTask<Void, Void, serviceResult>(){
            @Override
            protected void onPreExecute() {
                eventHandler.Wsdl2CodeStartedRequest();
            };
            @Override
            protected serviceResult doInBackground(Void... params) {
                return updateUserPassword(sessionToken,  password, headers);
            }
            @Override
            protected void onPostExecute(serviceResult result)
            {
                eventHandler.Wsdl2CodeEndedRequest();
                if (result != null){
                    eventHandler.Wsdl2CodeFinished("updateUserPassword", result);
                }
            }
        }.execute();
    }

    public serviceResult updateUserPassword(String sessionToken, String password){
        return updateUserPassword(sessionToken, password);
    }

    public serviceResult updateUserPassword(String sessionToken,String password,List<HeaderProperty> headers){

        soapEnvelope.implicitTypes = true;

        SoapObject soapReq = new SoapObject("http://Service.ru/","updateUserPassword");
        soapReq.addProperty("sessionToken",sessionToken);
        soapReq.addProperty("password",password);

        soapEnvelope.setOutputSoapObject(soapReq);

        return soapMethodExecutor(headers, "updateUserPasswordRequest");
    }

    
    public void insertUpdateUserAutoAsync(String sessionToken,String name,int haveCable,boolean haveCableSpecified,long transmissionType,boolean transmissionTypeSpecified) throws Exception{
        if (this.eventHandler == null)
            throw new Exception("Async Methods Requires IWsdl2CodeEvents");
        insertUpdateUserAutoAsync(sessionToken, name, haveCable, haveCableSpecified, transmissionType, transmissionTypeSpecified, null);
    }
    
    public void insertUpdateUserAutoAsync(final String sessionToken,final String name,final int haveCable,final boolean haveCableSpecified,final long transmissionType,final boolean transmissionTypeSpecified,final List<HeaderProperty> headers) throws Exception{
        
        new AsyncTask<Void, Void, serviceResult>(){
            @Override
            protected void onPreExecute() {
                eventHandler.Wsdl2CodeStartedRequest();
            };
            @Override
            protected serviceResult doInBackground(Void... params) {
                return insertUpdateUserAuto(sessionToken, name, haveCable, haveCableSpecified, transmissionType, transmissionTypeSpecified, headers);
            }
            @Override
            protected void onPostExecute(serviceResult result)
            {
                eventHandler.Wsdl2CodeEndedRequest();
                if (result != null){
                    eventHandler.Wsdl2CodeFinished("insertUpdateUserAuto", result);
                }
            }
        }.execute();
    }
    
    public serviceResult insertUpdateUserAuto(String sessionToken,String name,long haveCable,boolean haveCableSpecified,long transmissionType,boolean transmissionTypeSpecified){
        return insertUpdateUserAuto(sessionToken, name, haveCable, haveCableSpecified, transmissionType, transmissionTypeSpecified, null);
    }
    
    public serviceResult insertUpdateUserAuto(String sessionToken,String name,long haveCable,boolean haveCableSpecified,long transmissionType,boolean transmissionTypeSpecified,List<HeaderProperty> headers){

        soapEnvelope.implicitTypes = true;

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
    
    public void insertUpdateUserToolsAsync(String sessionToken,String toolTypeIds) throws Exception{
        if (this.eventHandler == null)
            throw new Exception("Async Methods Requires IWsdl2CodeEvents");
        insertUpdateUserToolsAsync(sessionToken, toolTypeIds, null);
    }
    
    public  void insertUpdateUserToolsAsync(final String sessionToken,final String toolTypeIds,final List<HeaderProperty> headers) throws Exception{
        
        new  AsyncTask<Void, Void, serviceResult>(){
            @Override
            protected void onPreExecute() {
                eventHandler.Wsdl2CodeStartedRequest();
            };
            @Override
            protected serviceResult doInBackground(Void... params) {
                return insertUpdateUserTools(sessionToken, toolTypeIds, headers);
            }
            @Override
            protected void onPostExecute(serviceResult result)
            {
                eventHandler.Wsdl2CodeEndedRequest();
                if (result != null){
                    eventHandler.Wsdl2CodeFinished("insertUpdateUserTools", result);
                }
            }
        }.execute();
    }
    
    public serviceResult insertUpdateUserTools(String sessionToken,String toolTypeIds){
        return insertUpdateUserTools(sessionToken, toolTypeIds, null);
    }
    
    public serviceResult insertUpdateUserTools(String sessionToken,String toolTypeIds,List<HeaderProperty> headers){

        soapEnvelope.implicitTypes = true;

        SoapObject soapReq = new SoapObject("http://Service.ru/","insertUpdateUserTools");
        soapReq.addProperty("sessionToken",sessionToken);
        soapReq.addProperty("toolTypeIds",toolTypeIds);
        soapEnvelope.setOutputSoapObject(soapReq);

        return soapMethodExecutor(headers, "insertUpdateUserToolsRequest");
    }
    
    public void closeCurrentActiveRequestByCustomUserAsync(String sessionToken,long userId,boolean userIdSpecified) throws Exception{
        if (this.eventHandler == null)
            throw new Exception("Async Methods Requires IWsdl2CodeEvents");
        closeCurrentActiveRequestByCustomUserAsync(sessionToken, userId, userIdSpecified, null);
    }
    
    public void closeCurrentActiveRequestByCustomUserAsync(final String sessionToken,final long userId,final boolean userIdSpecified,final List<HeaderProperty> headers) throws Exception{
        
        new AsyncTask<Void, Void, serviceResult>(){
            @Override
            protected void onPreExecute() {
                eventHandler.Wsdl2CodeStartedRequest();
            };
            @Override
            protected serviceResult doInBackground(Void... params) {
                return closeCurrentActiveRequestByCustomUser(sessionToken, userId, userIdSpecified, headers);
            }
            @Override
            protected void onPostExecute(serviceResult result)
            {
                eventHandler.Wsdl2CodeEndedRequest();
                if (result != null){
                    eventHandler.Wsdl2CodeFinished("closeCurrentActiveRequestByCustomUser", result);
                }
            }
        }.execute();
    }
    
    public serviceResult closeCurrentActiveRequestByCustomUser(String sessionToken,long userId,boolean userIdSpecified){
        return closeCurrentActiveRequestByCustomUser(sessionToken, userId, userIdSpecified, null);
    }
    
    public serviceResult closeCurrentActiveRequestByCustomUser(String sessionToken,long userId,boolean userIdSpecified,List<HeaderProperty> headers){

        soapEnvelope.implicitTypes = true;

        SoapObject soapReq = new SoapObject("http://Service.ru/","closeCurrentActiveRequestByCustomUser");
        soapReq.addProperty("sessionToken",sessionToken);
        soapReq.addProperty("UserId",userId);
        soapReq.addProperty("UserIdSpecified",userIdSpecified);
        soapEnvelope.setOutputSoapObject(soapReq);

        return soapMethodExecutor(headers, "closeCurrentActiveRequestByCustomUserRequest");
    }
    
    public void closeAllActiveRequestByAuthorAsync(String sessionToken) throws Exception{
        if (this.eventHandler == null)
            throw new Exception("Async Methods Requires IWsdl2CodeEvents");
        closeAllActiveRequestByAuthorAsync(sessionToken, null);
    }
    
    public void closeAllActiveRequestByAuthorAsync(final String sessionToken,final List<HeaderProperty> headers) throws Exception{
        
        new AsyncTask<Void, Void, serviceResult>(){
            @Override
            protected void onPreExecute() {
                eventHandler.Wsdl2CodeStartedRequest();
            };
            @Override
            protected serviceResult doInBackground(Void... params) {
                return closeAllActiveRequestByAuthor(sessionToken, headers);
            }
            @Override
            protected void onPostExecute(serviceResult result)
            {
                eventHandler.Wsdl2CodeEndedRequest();
                if (result != null){
                    eventHandler.Wsdl2CodeFinished("closeAllActiveRequestByAuthor", result);
                }
            }
        }.execute();
    }
    
    public serviceResult closeAllActiveRequestByAuthor(String sessionToken){
        return closeAllActiveRequestByAuthor(sessionToken, null);
    }
    
    public serviceResult closeAllActiveRequestByAuthor(String sessionToken,List<HeaderProperty> headers){

        soapEnvelope.implicitTypes = true;

        SoapObject soapReq = new SoapObject("http://Service.ru/","closeAllActiveRequestByAuthor");
        soapReq.addProperty("sessionToken",sessionToken);
        soapEnvelope.setOutputSoapObject(soapReq);
        return soapMethodExecutor(headers, "closeAllActiveRequestByAuthorRequest");
    }
    
}
