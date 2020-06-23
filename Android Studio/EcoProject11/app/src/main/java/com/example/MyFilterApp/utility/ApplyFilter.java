package com.example.MyFilterApp.utility;

import android.content.Context;
import android.graphics.Bitmap;

import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.VignetteSubFilter;

public class ApplyFilter {
    private String mFilename;
    private Bitmap mBitmap;
    private Context mContext;

    private Bitmap brightnessBitmap;
    private Bitmap contrastBitmap;
    private Bitmap saturationBitmap;
    private Bitmap vignetteBitmap;

    public static int FILTER_BRIGHTNESS=0;
    public static int FILTER_CONTRAST=1;
    public static int FILTER_SATURATION=2;
    public static int FILTER_VIGNETTE=3;

    public static final int DEFAULT_BRIGHTNESS=50;
    public static final int DEFAULT_CONTRAST=2;
    public static final int DEFAULT_SATURATION=2;
    public static final int DEFAULT_VIGNETTE=100;

    public static final int MAX_BRIGHTNESS=100;
    public static final int MAX_CONTRAST=10;
    public static final int MAX_SATURATION=5;
    public static final int MAX_VIGNETTE=255;

    public String getmFilename(int filter){
        if(filter == FILTER_BRIGHTNESS){
            return mFilename+"_brightness";
        }else if(filter == FILTER_CONTRAST){
            return mFilename+"_contrast";
        }else if(filter == FILTER_SATURATION){
            return mFilename+"_saturation";
        }else if(filter == FILTER_VIGNETTE){
            return mFilename+"_vignette";
        }
        return mFilename;
    }

    /*public Bitmap getmBitmap(int filter){
        if(filter == FILTER_BRIGHTNESS){
            return brightnessBitmap;
        }else if(filter == FILTER_CONTRAST){
            return contrastBitmap;
        }else if(filter == FILTER_SATURATION){
            return saturationBitmap;
        }else if(filter == FILTER_VIGNETTE){
            return vignetteBitmap;
        }
        return mBitmap;
    }

     */

    public ApplyFilter(Context context,Bitmap bitmap){
        mContext = context;
        mBitmap = bitmap;
        mFilename = System.currentTimeMillis()+"";
    }
    public Bitmap applyBrightnessFilter(int amount){
        Bitmap workingbitmap = Bitmap.createBitmap(mBitmap);
        Bitmap mutablebitmap = workingbitmap.copy(Bitmap.Config.ARGB_8888,true);

        Filter myFilter = new Filter();
        myFilter.addSubFilter(new BrightnessSubFilter(amount));
        Bitmap outputImage = myFilter.processFilter(mutablebitmap);

        brightnessBitmap = outputImage;
        return brightnessBitmap;
    }
    public Bitmap applyContrastFilter(int amount){
        Bitmap workingbitmap = Bitmap.createBitmap(mBitmap);
        Bitmap mutablebitmap = workingbitmap.copy(Bitmap.Config.ARGB_8888,true);

        Filter myFilter3 = new Filter();
        myFilter3.addSubFilter(new ContrastSubFilter(amount));
        Bitmap outputImage = myFilter3.processFilter(mutablebitmap);

        contrastBitmap = outputImage;
        return contrastBitmap;
    }
    public Bitmap applySaturationFilter(int amount){
        Bitmap workingbitmap = Bitmap.createBitmap(mBitmap);
        Bitmap mutablebitmap = workingbitmap.copy(Bitmap.Config.ARGB_8888,true);

        Filter myFilter2 = new Filter();
        myFilter2.addSubFilter(new SaturationSubFilter(amount));
        Bitmap outputImage = myFilter2.processFilter(mutablebitmap);

        saturationBitmap = outputImage;
        return saturationBitmap;
    }
    public Bitmap applyVignetteFilter(int amount){
        Bitmap workingbitmap = Bitmap.createBitmap(mBitmap);
        Bitmap mutablebitmap = workingbitmap.copy(Bitmap.Config.ARGB_8888,true);

        Filter myFilter4 = new Filter();
        myFilter4.addSubFilter(new VignetteSubFilter(mContext,amount));
        Bitmap outputImage = myFilter4.processFilter(mutablebitmap);

        vignetteBitmap = outputImage;
        return vignetteBitmap;
    }

}
