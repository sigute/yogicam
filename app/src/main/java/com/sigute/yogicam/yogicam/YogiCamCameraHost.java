package com.sigute.yogicam.yogicam;

import android.content.Context;
import android.os.Environment;

import com.commonsware.cwac.camera.SimpleCameraHost;

import java.io.File;

/**
 * Created by sigute on 28/09/2014.
 */
public class YogiCamCameraHost extends SimpleCameraHost {
    public YogiCamCameraHost(Context context) {
        super(context);
    }

    @Override
    protected String getPhotoFilename()
    {
        return "YogiCam-" + super.getPhotoFilename();
    }

    @Override
    protected java.io.File getPhotoDirectory()
    {
        return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "/YogiCam");
    }

    @Override
    public boolean scanSavedImage()
    {
        return true;
    }
}
