package com.wangjiong.ershisishi;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Administrator on 2018/12/8.
 */
public class MusicService extends Service {

    public static MusicService msMusicService;
    public static final String TAG = "MusicService==";

    MediaPlayer mMediaPlayer = new MediaPlayer();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        msMusicService = this;
        playMusic("music/main.mp3");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void playMusic(String path){
        AssetFileDescriptor fileDescriptor;
        try {
            fileDescriptor = getAssets().openFd(path);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(),
                    fileDescriptor.getStartOffset(),
                    fileDescriptor.getLength());
            mMediaPlayer.prepare();
            mMediaPlayer.start();
            Log.w(TAG, "mMediaPlayer.start");
        } catch (IOException e) {
            e.printStackTrace();
            Log.w(TAG, "IOException");
        }
    }
}
