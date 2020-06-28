package com.example.MyFilterApp.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Helper {
    final static String TAG="Helper";

    public static void WriteDataIntoExternalStorage(Context context, String filename, Bitmap bitmap){
        //File directory =new File(Environment.getExternalStorageDirectory().getAbsolutePath());

        File directory = new File(context.getExternalFilesDir(null).getAbsolutePath() + "/Filter");

        if(!directory.exists()){
            Log.d(TAG,"Helper1");
            directory.mkdirs();
        }
        File file = new File(directory.getAbsolutePath()+"/"+filename);

        try{
            FileOutputStream fileOutputStream =new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
        }catch(FileNotFoundException e){
            e.printStackTrace();
            Log.d(TAG,"Helper3");
        }
    }

    public static File getFileFromExternalStorage(Context context, String filename){
        File directory = new File(context.getExternalFilesDir(null).getAbsolutePath() + "/Filter");
        File file = new File(directory.getAbsolutePath()+"/"+filename);

        if(!file.exists() || !file.canRead()){
            Log.d(TAG,"Helper2");
            return null;
        }
        return file;
    }

}
