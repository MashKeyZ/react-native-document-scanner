package com.documentscanner;

import com.documentscanner.views.MainView;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;

/**
 * Created by andre on 28/11/2017.
 */

public class DocumentScannerModule extends ReactContextBaseJavaModule{

    public DocumentScannerModule(ReactApplicationContext reactContext){
        super(reactContext);
    }


    @Override
    public String getName() {
        return "RNPdfScannerManager";
    }

    @ReactMethod
    public void capture(){
        MainView view = MainView.getInstance();
        view.capture();
    }
    
    @ReactMethod
    public void procImage(String path, Callback callBack,Callback myFailureCallback) {
 
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getCurrentActivity().getContentResolver(), path);
            mat = new Mat();
            Utils.bitmapToMat(bitmap,mat);
            Imgproc.adaptiveThreshold(mat ,mat, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C,Imgproc.THRESH_BINARY, 77, 10);
            Imgcodecs.imwrite(path,mat);
            callBack.invoke(path);
        } catch (IOException e) {
            e.printStackTrace();
            myFailureCallback.invoke("An error occurred when converting image to bitmap : ",e);
        }

    }
}
