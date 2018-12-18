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

    String mCurrentpath;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        msMusicService = this;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        MusicService.msMusicService.playMusic("music/main.mp3");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    public void playMusic(String path) {
        Log.w(TAG, "playMusic : " + path);
        if (mCurrentpath == path || (mCurrentpath!=null && mCurrentpath.endsWith(path))) {
            return;
        }
        mCurrentpath = path;
        AssetFileDescriptor fileDescriptor;
        try {
            fileDescriptor = getAssets().openFd(path);
            mMediaPlayer.reset();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(),
                    fileDescriptor.getStartOffset(),
                    fileDescriptor.getLength());
            mMediaPlayer.setLooping(true);
            mMediaPlayer.prepare();
            mMediaPlayer.start();

        } catch (IOException e) {
            e.printStackTrace();
            Log.w(TAG, "IOException");
        }
    }
}
