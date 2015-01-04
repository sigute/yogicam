package com.github.sigute.yogicam;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.commonsware.cwac.camera.CameraFragment;

public class MainActivity extends Activity {

    private final String CAMERA_FRAGMENT_TAG = "camera_fragment_tag";

    Handler mHandler;
    MediaPlayer mp;
    Button takePictureButton;
    volatile boolean takingPictures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        takePictureButton = (Button) findViewById(R.id.take_pictures_button);

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

        takingPictures = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHandler.removeCallbacks(mRunnable);
    }

    public void takePictureButtonClicked(View v) {
        if (v == takePictureButton) {
            if (!takingPictures) {
                startTakingPictures();
            } else {
                stopTakingPictures();
            }
        }
    }

    public void startTakingPictures() {
        mHandler = new Handler();
        mHandler.postDelayed(mRunnable, 5000);
        takePictureButton.setText(R.string.button_stop_taking_pictures);
        takingPictures = true;
    }

    public void stopTakingPictures() {
        //stops the sounds and taking of pictures
        mHandler.removeCallbacksAndMessages(null);
        takePictureButton.setText(R.string.button_start_taking_pictures);
        takingPictures = false;
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

    @Override
    protected void onPause() {
        super.onPause();
        stopTakingPictures();
    }
}