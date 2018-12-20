package fr.wildcodeschool.mediaplayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.StringRes;
import android.util.Log;

import java.io.IOException;

class WildPlayer implements WildAudioManagerListener {
  // Activity context
  private Context mContext;
  // Android media player
  private MediaPlayer mPlayer;
  // media player prepared state
  private boolean isPrepared = false;

  WildPlayer(Context ctx) {
    mContext = ctx;
    mPlayer  = new MediaPlayer();

    // Register to the audioManager events
    //WildAudioManager.getInstance().setAudioManagerListener(this);
  }

  /**
   * Initialize the media to play
   * @param song URI of media to play
   * @param listener onPrepared event listener
   */
  void init(@StringRes int song, final MediaPlayer.OnPreparedListener listener) {

    try {
      // Set source and init the player engine
      mPlayer.setDataSource(mContext.getString(song));
      mPlayer.prepareAsync();
    } catch (IOException e) {
      Log.e(this.toString(), e.getMessage());
    }

    this.mPlayer.setOnPreparedListener((MediaPlayer mp) -> {
      mp.seekTo(0);
      // Send the audio engine state to the listener
      if (null != listener) listener.onPrepared(mp);
      // Update state
      isPrepared = true;
    });
/*
    this.mPlayer.setOnCompletionListener((MediaPlayer mp) -> {
        // Send the audio engine state to the listener
        if (null != listener) listener.onCompletion(mp);
    });*/
  }

  /**
   * Check the validity of player and call play command
   * @return The validity of the call
   */
  boolean play() {
    if (null != mPlayer && isPrepared && !mPlayer.isPlaying()) {
      mPlayer.start();
//      if (WildAudioManager.getInstance().requestAudioFocus()) {
//        return true;
//      }
    }
    return false;
  }

  public void setPrepared(boolean prepared) {
    isPrepared = prepared;
  }

  /**
   * Check the validity of player and call pause command
   * @return The validity of the call
   */
  boolean pause() {
    mPlayer.pause();
//    if (null != mPlayer && isPrepared && mPlayer.isPlaying()) {
//      return true;
//    }
    return false;
  }

  /**
   * Check the validity of player and call stop command
   * @return The validity of the call
   */
  boolean reset() {
    mPlayer.seekTo(0);
//    if (null != mPlayer && isPrepared) {
//      return true;
//    }
    return false;
  }

  /**
   * Check the playing state of media
   * @return Media playing state
   */
  boolean isPlaying() {
    if (null != mPlayer && isPrepared) {
      return mPlayer.isPlaying();
    }
    return false;
  }

  /**
   * Check the validity of player and return media current position
   * @return Media current position
   */
  int getCurrentPosition() {
    if (null != mPlayer && isPrepared) {
      return mPlayer.getCurrentPosition();
    }
    return 0;
  }

  /**
   * Seek in the timeline
   * @param position Value in ms
   */
  void seekTo(int position) {
    if (null != mPlayer && isPrepared) {
      mPlayer.seekTo(position);
    }
  }

  /**
   * WildAudioManagerListener
   * @param isGain inform that application has audio focus or not
   */
  @Override
  public void audioFocusGain(boolean isGain) {
    if (!isGain) pause();
  }
}
