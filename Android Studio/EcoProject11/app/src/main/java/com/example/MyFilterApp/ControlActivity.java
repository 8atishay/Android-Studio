package com.example.MyFilterApp;

import android.Manifest;
import android.app.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;


import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.MyFilterApp.utility.ApplyFilter;
import com.example.MyFilterApp.utility.Helper;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
//import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import 	android.provider.MediaStore;
import android.widget.SeekBar;

import java.io.IOException;

public class ControlActivity extends AppCompatActivity {
    static
    {
        System.loadLibrary("NativeImageProcessor");
    }

    final static String TAG=ControlActivity.class.getSimpleName();
    Toolbar mControlToolbar;
    ImageView mCenterImageView;
    ImageView mFirstFilterView, mSecondFilterView, mThirdFilterView, mFourthFilterView;

    final static int PICK_IMAGE=2;
    final static int MY_PERMISSIONS_REQUEST_STORAGE_PERMISSION=3;

    ApplyFilter selectedImage;
    float screenHeight;
    float screenWidth;

    SeekBar mSeekBar;
    ImageView cancelImageView;
    ImageView mTickImageView;

    Uri selectImageUri;
    int mCurrentFilter;

    Target mApplySingleFilter =new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            int currentFilterValue = mSeekBar.getProgress();
            if(mCurrentFilter == ApplyFilter.FILTER_BRIGHTNESS){
                Bitmap mBitmap = selectedImage.applyBrightnessFilter(currentFilterValue);
                String filename = selectedImage.getmFilename(ApplyFilter.FILTER_BRIGHTNESS);
                Helper.WriteDataIntoExternalStorage(ControlActivity.this,filename,mBitmap);
                Picasso.get().invalidate(Helper.getFileFromExternalStorage(ControlActivity.this,filename));
                Picasso.get().load(Helper.getFileFromExternalStorage(ControlActivity.this,filename)).resize(0, (int) (screenHeight*0.75)).into(mCenterImageView);

            }else if(mCurrentFilter == ApplyFilter.FILTER_CONTRAST){
                selectedImage.applyContrastFilter(currentFilterValue);
                Bitmap mBitmap = selectedImage.applyContrastFilter(currentFilterValue);
                String filename = selectedImage.getmFilename(ApplyFilter.FILTER_CONTRAST) ;
                Helper.WriteDataIntoExternalStorage(ControlActivity.this,filename,mBitmap);
                Picasso.get().invalidate(Helper.getFileFromExternalStorage(ControlActivity.this,filename));
                Picasso.get().load(Helper.getFileFromExternalStorage(ControlActivity.this,filename)).resize(0, (int) (screenHeight*0.75)).into(mCenterImageView);

            }else if(mCurrentFilter == ApplyFilter.FILTER_SATURATION){
                selectedImage.applySaturationFilter(currentFilterValue);
                Bitmap mBitmap = selectedImage.applySaturationFilter(currentFilterValue);
                String filename = selectedImage.getmFilename(ApplyFilter.FILTER_SATURATION);
                Helper.WriteDataIntoExternalStorage(ControlActivity.this,filename,mBitmap);
                Picasso.get().invalidate(Helper.getFileFromExternalStorage(ControlActivity.this,filename));
                Picasso.get().load(Helper.getFileFromExternalStorage(ControlActivity.this,filename)).resize(0, (int) (screenHeight*0.75)).into(mCenterImageView);

            }else if(mCurrentFilter == ApplyFilter.FILTER_VIGNETTE){
                selectedImage.applyVignetteFilter(currentFilterValue);
                Bitmap mBitmap = selectedImage.applyVignetteFilter(currentFilterValue);
                String filename = selectedImage.getmFilename(ApplyFilter.FILTER_VIGNETTE);
                Helper.WriteDataIntoExternalStorage(ControlActivity.this,filename,mBitmap);
                Picasso.get().invalidate(Helper.getFileFromExternalStorage(ControlActivity.this,filename));
                Picasso.get().load(Helper.getFileFromExternalStorage(ControlActivity.this,filename)).resize(0, (int) (screenHeight*0.75)).into(mCenterImageView);

            }

        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        mControlToolbar =(Toolbar) findViewById(R.id.toolbar);
        mCenterImageView = (ImageView) findViewById(R.id.centerimage);
        mFirstFilterView =(ImageView)findViewById(R.id.imageView5);
        mSecondFilterView=(ImageView) findViewById(R.id.imageView6);
        mThirdFilterView =(ImageView)findViewById(R.id.imageView7);
        mFourthFilterView =(ImageView)findViewById(R.id.imageView8);
        mTickImageView =(ImageView) findViewById(R.id.TickImage);
        mSeekBar = findViewById(R.id.seekBar);
        //mControlToolbar.setTitle(getString(R.string.app_name));
        mControlToolbar.setTitle("Filter App");
        mControlToolbar.setNavigationIcon(R.drawable.ic_launcher_foreground);
        mControlToolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));


        /*mTickImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ControlActivity.this,ImagePreview.class);
                startActivity(intent);

            }
        });
         */

        mCenterImageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                RequestStoragePermission();
                if (ContextCompat.checkSelfPermission(ControlActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                Intent intent =new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE);
            }
        });
        mFirstFilterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Brightness
                mCurrentFilter = ApplyFilter.FILTER_BRIGHTNESS;
                mSeekBar.setMax(ApplyFilter.MAX_BRIGHTNESS);
                mSeekBar.setProgress(ApplyFilter.DEFAULT_BRIGHTNESS);

                Picasso.get().load(Helper.getFileFromExternalStorage(ControlActivity.this,selectedImage.getmFilename(ApplyFilter.FILTER_BRIGHTNESS)))
                        .resize(0, (int) (screenHeight*0.75)).into(mCenterImageView);
            }
        });
        mSecondFilterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Contrast
                mCurrentFilter = ApplyFilter.FILTER_CONTRAST;
                mSeekBar.setMax(ApplyFilter.MAX_CONTRAST);
                mSeekBar.setProgress(ApplyFilter.DEFAULT_CONTRAST);

                Picasso.get().load(Helper.getFileFromExternalStorage(ControlActivity.this,selectedImage.getmFilename(ApplyFilter.FILTER_CONTRAST)))
                        .resize(0, (int) (screenHeight*0.75)).into(mCenterImageView);
            }
        });
        mThirdFilterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Saturation
                mCurrentFilter = ApplyFilter.FILTER_SATURATION;
                mSeekBar.setMax(ApplyFilter.MAX_SATURATION);
                mSeekBar.setProgress(ApplyFilter.DEFAULT_SATURATION);

                Picasso.get().load(Helper.getFileFromExternalStorage(ControlActivity.this,selectedImage.getmFilename(ApplyFilter.FILTER_SATURATION)))
                        .resize(0, (int) (screenHeight*0.75)).into(mCenterImageView);
            }
        });
        mFourthFilterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Vignette
                mCurrentFilter = ApplyFilter.FILTER_VIGNETTE;
                mSeekBar.setMax(ApplyFilter.MAX_VIGNETTE);
                mSeekBar.setProgress(ApplyFilter.DEFAULT_VIGNETTE);

                Picasso.get().load(Helper.getFileFromExternalStorage(ControlActivity.this,selectedImage.getmFilename(ApplyFilter.FILTER_VIGNETTE)))
                        .resize(0, (int) (screenHeight*0.75)).into(mCenterImageView);
            }
        });
        mTickImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.get().load(selectImageUri).into(mApplySingleFilter);
            }
        });

        DisplayMetrics displayMetrics =new DisplayMetrics() ;
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;

    }

    public void onRequestPermissionsResult(int requestcode, String[] Permissions, int[] grantResults){
        switch (requestcode){
            case MY_PERMISSIONS_REQUEST_STORAGE_PERMISSION:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                }else{
                    Log.d(TAG,"Permission denied");
                }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {

            selectImageUri = data.getData();
            Picasso.get().load(selectImageUri).fit().centerInside().into(mCenterImageView);//Picasso
            //Glide  UIL  Picasso

            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectImageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            selectedImage = new ApplyFilter(ControlActivity.this,bitmap);
            allPreview(selectedImage);
        }
    }

    public void RequestStoragePermission(){
        if (ContextCompat.checkSelfPermission(ControlActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(ControlActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                new MaterialDialog.Builder(ControlActivity.this)
                        .title(R.string.title).content(R.string.supporting_text)
                        .positiveText(R.string.positive)
                        .negativeText(R.string.negative)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                startActivityForResult(new Intent(Settings.ACTION_SETTINGS),0);
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                            }
                        })
                        .canceledOnTouchOutside(true)
                        .show();
            } else {
                ActivityCompat.requestPermissions(ControlActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_STORAGE_PERMISSION);
            }
        }
    }
    public void allPreview(ApplyFilter selectedImage){
        Bitmap mBitmap = selectedImage.applyBrightnessFilter(ApplyFilter.DEFAULT_BRIGHTNESS);
        String filename = selectedImage.getmFilename(ApplyFilter.FILTER_BRIGHTNESS);
        Helper.WriteDataIntoExternalStorage(ControlActivity.this,filename,mBitmap);
        Picasso.get().load(Helper.getFileFromExternalStorage(ControlActivity.this,filename)).fit().centerInside().into(mFirstFilterView);

        Bitmap mbitmap1 = selectedImage.applyContrastFilter(ApplyFilter.DEFAULT_CONTRAST);
        String filename1 = selectedImage.getmFilename(ApplyFilter.FILTER_CONTRAST);
        Helper.WriteDataIntoExternalStorage(ControlActivity.this,filename1,mbitmap1);
        Picasso.get().load(Helper.getFileFromExternalStorage(ControlActivity.this,filename1)).fit().centerInside().into(mSecondFilterView);

        Bitmap mbitmap2 = selectedImage.applySaturationFilter(ApplyFilter.DEFAULT_SATURATION);
        String filename2 = selectedImage.getmFilename(ApplyFilter.FILTER_SATURATION);
        Helper.WriteDataIntoExternalStorage(ControlActivity.this,filename2,mbitmap2);
        Picasso.get().load(Helper.getFileFromExternalStorage(ControlActivity.this,filename2)).fit().centerInside().into(mThirdFilterView);

        Bitmap mbitmap3 = selectedImage.applyVignetteFilter(ApplyFilter.DEFAULT_VIGNETTE);
        String filename3 = selectedImage.getmFilename(ApplyFilter.FILTER_VIGNETTE);
        Helper.WriteDataIntoExternalStorage(ControlActivity.this,filename3,mbitmap3);
        Picasso.get().load(Helper.getFileFromExternalStorage(ControlActivity.this,filename3)).fit().centerInside().into(mFourthFilterView);
    }
}
