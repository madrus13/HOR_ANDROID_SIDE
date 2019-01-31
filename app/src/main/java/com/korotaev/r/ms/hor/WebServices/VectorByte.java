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
import org.ksoap2.serialization.SoapPrimitive;

import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Vector;
import java.util.Base64;
public class VectorByte extends Vector<Byte> implements KvmSerializable {
    
    
    public VectorByte(){}
    
    public VectorByte(SoapPrimitive soapPrimitive) {
        if (soapPrimitive != null) {
            String result = soapPrimitive.toString();
            if (result != "") {
                byte[] bytes = new byte[0];
                try {
                    bytes = Base64.getDecoder().decode(result.getBytes("UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                for (final byte b : bytes) {
                    add(b);
                }
            }
        }
    }
    public VectorByte(byte[] bytes){
        for (byte b : bytes) {
            add(b);
        }
    }
    @Override
    public Object getProperty(int arg0) {
        return this.get(arg0);
    }
    
    @Override
    public int getPropertyCount() {
        return this.size();
    }
    
    @Override
    public void getPropertyInfo(int index, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo info) {
        info.name = "Byte";
        info.type = Byte.class;
    }
    

    
    
    @Override
    public void setProperty(int arg0, Object arg1) {
    }
    
    @Override
    public String toString() {
        byte[] byteToString = toBytes();
        String str =  Base64.getEncoder().encodeToString(byteToString);

        return str;
    }
    public byte[] toBytes(){
        byte[] bytes = new byte[this.size()];
        int i = 0;
        for (Byte b : this) {
            bytes[i++] = b;
        }
        return bytes;
    }
//    @Deprecated
//    public static void saveFile(String pathNameTo, ByteArrayOutputStream sourceStream)
//    {
//        pathNameTo = pathNameTo.replace(" ", "_").replace(":","_");
//        FileOutputStream fos = null;
//        try {
//            File f = new File(pathNameTo);
//            f.createNewFile();
//            fos = new FileOutputStream(f);
//            sourceStream.writeTo(fos);
//        } catch(IOException ioe) {
//            ioe.printStackTrace();
//        } finally {
//            try {
//                fos.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }



}
