package com.sigute.yogicam.yogicam;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.commonsware.cwac.camera.CameraFragment;

public class MainActivity extends Activity {

    private final String CAMERA_FRAGMENT_TAG = "camera_fragment_tag";

    Handler mHandler;
    MediaPlayer mp;

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

        mp = MediaPlayer.create(getApplicationContext(), R.raw.bip);
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHandler.removeCallbacks(mRunnable);
    }

    public void takePictureButtonClicked(View v) {
        mHandler = new Handler();
        mHandler.postDelayed(mRunnable, 5000);
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    playSound();
                }
            }, 0);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    playSound();
                }
            }, 1000);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    playSound();
                }
            }, 2000);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    takePicture();
                }
            }, 3000);

            mHandler.postDelayed(mRunnable, 10000);
        }
    };

    private void playSound() {
        mp.start();
    }

    private void takePicture() {
        CameraFragment f = (CameraFragment) getFragmentManager().findFragmentByTag(CAMERA_FRAGMENT_TAG);
        f.takePicture();
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
