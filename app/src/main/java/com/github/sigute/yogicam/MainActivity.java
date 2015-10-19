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
import android.widget.TextView;

import com.commonsware.cwac.camera.CameraFragment;

/**
 * Main activity.
 *
 * @author sigute
 */
public class MainActivity extends Activity {

    private final String CAMERA_FRAGMENT_TAG = "CAMERA_FRAGMENT_TAG";

    private Handler handler;
    private MediaPlayer mediaPlayer;
    private Button takePicturesButton;
    private volatile boolean takingPictures;

    private TextView picturesTakenCounter;
    private int picturesTaken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        takePicturesButton = (Button) findViewById(R.id.take_pictures_button);
        picturesTakenCounter = (TextView) findViewById(R.id.pictures_taken_counter);

        //Create the CameraFragment and add it to the layout
        CameraFragment f = new CameraFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.camera_fragment, f, CAMERA_FRAGMENT_TAG)
                .commit();

        //Set the CameraHost
        YogiCamCameraHost cameraHost = new YogiCamCameraHost(this);
        f.setCameraHost(cameraHost);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bip);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        takingPictures = false;

        handler = new Handler();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopTakingPictures();
    }

    public void takePictureButtonClicked(View v) {
        if (v == takePicturesButton) {
            if (!takingPictures) {
                startTakingPictures();
            } else {
                stopTakingPictures();
            }
        }
    }

    private  void startTakingPictures() {
        handler.postDelayed(mRunnable, 5000);
        takePicturesButton.setText(R.string.button_stop_taking_pictures);
        picturesTaken = 0;
        picturesTakenCounter.setText(String.valueOf(picturesTaken));
        takingPictures = true;
    }

    private  void stopTakingPictures() {
        //stops the sounds and taking of pictures
        handler.removeCallbacksAndMessages(null);
        takePicturesButton.setText(R.string.button_start_taking_pictures);
        picturesTakenCounter.setText(String.valueOf(picturesTaken));
        takingPictures = false;
    }

    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    playSound();
                }
            }, 0);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    playSound();
                }
            }, 1000);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    playSound();
                }
            }, 2000);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    takePicture();
                }
            }, 3000);

            handler.postDelayed(mRunnable, 10000);
        }
    };

    private void playSound() {
        mediaPlayer.start();
    }

    private void takePicture() {
        CameraFragment f = (CameraFragment) getFragmentManager().findFragmentByTag(CAMERA_FRAGMENT_TAG);
        f.takePicture();
        picturesTaken++;
        picturesTakenCounter.setText(String.valueOf(picturesTaken));
    }
}
