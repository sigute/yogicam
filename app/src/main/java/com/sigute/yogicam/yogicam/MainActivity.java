package com.sigute.yogicam.yogicam;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.commonsware.cwac.camera.CameraFragment;
import com.commonsware.cwac.camera.SimpleCameraHost;

public class MainActivity extends Activity {

    private final String CAMERA_FRAGMENT_TAG = "camera_fragment_tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create the CameraFragment and add it to the layout
        CameraFragment f = new CameraFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.camera_fragment, f, CAMERA_FRAGMENT_TAG)
                .commit();

        //Set the CameraHost
        YogiCamCameraHost cameraHost = new YogiCamCameraHost(this);
        f.setHost(cameraHost);
    }

    public void takePicture(View v) {
        if (v == findViewById(R.id.take_pictures_button)) {
            CameraFragment f = (CameraFragment) getFragmentManager().findFragmentByTag(CAMERA_FRAGMENT_TAG);
            f.takePicture();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
