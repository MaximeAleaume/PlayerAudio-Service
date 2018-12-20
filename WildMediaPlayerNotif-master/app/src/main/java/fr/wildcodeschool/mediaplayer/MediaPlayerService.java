package fr.wildcodeschool.mediaplayer;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class MediaPlayerService extends Service {

    private WildPlayer myWildPlayer;


    @Override
    public void onCreate() {
        myWildPlayer = new WildPlayer(MainActivity.getAppContext());
        //myWildPlayer.setPrepared(true);

        // Initialization of the wild audio player
        myWildPlayer.init(R.string.song, new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                myWildPlayer.setPrepared(true);
            }


        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent.getAction() != null) {
            switch (intent.getAction()) {
                case "PLAY":
                    myWildPlayer.play();
                    break;

                case "PAUSE":
                    myWildPlayer.pause();
                    break;

                case "RESET":
                    myWildPlayer.reset();
                    break;
            }
        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new Binder();
    }
}
