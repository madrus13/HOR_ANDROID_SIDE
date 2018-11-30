package com.korotaev.r.ms.hor.WebServices;

//------------------------------------------------------------------------------
// <wsdl2code-generated>
//    This code was generated by http://www.wsdl2code.com version  2.6
//
// Date Of Creation: 9/8/2018 2:04:47 PM
//    Please dont change this code, regeneration will override your changes
//</wsdl2code-generated>
//
//------------------------------------------------------------------------------
//
//This source code was auto-generated by Wsdl2Code  Version
//
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import java.util.Hashtable;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

public class serviceResult implements KvmSerializable {
    
    public boolean isSuccess;
    public String resultObjectJSON;
    public long errorCode;
    public boolean errorCodeSpecified;
    public String errorMessage;
    public String timingMessage;
    
    public serviceResult(){}
    
    public serviceResult(SoapObject soapObject)
    {
        if (soapObject == null)
            return;
        if (soapObject.hasProperty("IsSuccess"))
        {
            Object obj = soapObject.getProperty("IsSuccess");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) obj;
                isSuccess = Boolean.parseBoolean(j.toString());
            }else if (obj!= null && obj instanceof Boolean){
                isSuccess = (Boolean) obj;
            }
        }
        if (soapObject.hasProperty("ResultObjectJSON"))
        {
            Object obj = soapObject.getProperty("ResultObjectJSON");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) obj;
                resultObjectJSON = j.toString();
            }else if (obj!= null && obj instanceof String){
                resultObjectJSON = (String) obj;
            }
        }
        if (soapObject.hasProperty("errorCode"))
        {
            Object obj = soapObject.getProperty("errorCode");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) obj;
                errorCode = Integer.parseInt(j.toString());
            }else if (obj!= null && obj instanceof Number){
                errorCode = (Integer) obj;
            }
        }
        if (soapObject.hasProperty("errorCodeSpecified"))
        {
            Object obj = soapObject.getProperty("errorCodeSpecified");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) obj;
                errorCodeSpecified = Boolean.parseBoolean(j.toString());
            }else if (obj!= null && obj instanceof Boolean){
                errorCodeSpecified = (Boolean) obj;
            }
        }
        if (soapObject.hasProperty("errorMessage"))
        {
            Object obj = soapObject.getProperty("errorMessage");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) obj;
                errorMessage = j.toString();
            }else if (obj!= null && obj instanceof String){
                errorMessage = (String) obj;
            }
        }
        if (soapObject.hasProperty("timingMessage"))
        {
            Object obj = soapObject.getProperty("timingMessage");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) obj;
                timingMessage = j.toString();
            }else if (obj!= null && obj instanceof String){
                timingMessage = (String) obj;
            }
        }
    }
    @Override
    public Object getProperty(int arg0) {
        switch(arg0){
            case 0:
                return isSuccess;
            case 1:
                return resultObjectJSON;
            case 2:
                return errorCode;
            case 3:
                return errorCodeSpecified;
            case 4:
                return errorMessage;
            case 5:
                return timingMessage;
        }
        return null;
    }
    
    @Override
    public int getPropertyCount() {
        return 5;
    }
    
    @Override
    public void getPropertyInfo(int index, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo info) {
        switch(index){
            case 0:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "IsSuccess";
                break;
            case 1:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ResultObjectJSON";
                break;
            case 2:
                info.type = Long.class;
                info.name = "errorCode";
                break;
            case 3:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "errorCodeSpecified";
                break;
            case 4:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "errorMessage";
                break;
            case 5:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "timingMessage";
                break;

        }
    }
    

    
    
    @Override
    public void setProperty(int arg0, Object arg1) {
    }
    
}
