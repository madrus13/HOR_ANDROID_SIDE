package com.korotaev.r.ms.hor.AppHelpers;

import android.content.Context;

import java.io.FileOutputStream;
import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;

public class FileHelper {


    public void createIntStoragePrivatePicture(Context context, String fileName, byte[] sourceStream) {

        String path = "";
        //File file = new File(path, fileName);
        FileOutputStream file = null;
        try {
            file = context.openFileOutput(fileName, MODE_PRIVATE);
            file.write(sourceStream);
//
//            // Tell the media scanner about the new file so that it is
//            // immediately available to the user.
//            MediaScannerConnection.scanFile(SettingsFragment.this.getContext(),
//                    new String[] { file.toString() }, null,
//                    (path1, uri) -> {
//                        myDBHelper.getHelper().addLog("ExtStr", "Scanned " + path1 + ":");
//                        myDBHelper.getHelper().addLog("ExtStr", "-> uri=" + uri);
//                    });
        } catch (IOException e) {
            // Unable to create file, likely because external storage is
            // not currently mounted.
           // myDBHelper.getHelper().addLog("ExtStr", "Error writing " + file+ " " + e);
        }
    }
}
