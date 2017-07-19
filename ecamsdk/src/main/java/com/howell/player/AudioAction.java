package com.howell.player;


import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

import com.howell.jni.JniUtil;


/**
 * audio manager
 * @author howell
 */
public class AudioAction {
	private static AudioAction mInstance = null;
	private AudioAction(){}
	public static AudioAction getInstance(){
		if(mInstance==null){
			mInstance = new AudioAction();
		}
		return mInstance;
	}

	private AudioTrack mAudioTrack;//FIXME
	private byte[] mAudioData;
	private int mAudioDataLength;
	
	private AudioManager mAudioManager = null;


	/**
	 * initAudio,set jni callback field
	 * {@link JniUtil}
	 */
	public void initAudio(){
		int buffer_size = AudioTrack.getMinBufferSize(8000, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);
		mAudioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 8000, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, buffer_size*8, AudioTrack.MODE_STREAM);
		mAudioData = new byte[buffer_size*8];
	
		
		JniUtil.nativeAudioInit();
		JniUtil.nativeAudioSetCallbackObject(this, 0);
		JniUtil.nativeAudioSetCallbackMethodName("mAudioDataLength", 0);
		JniUtil.nativeAudioSetCallbackMethodName("mAudioData", 1);
		JniUtil.nativeAudioBPlayable();
	}

	/**
	 * play audio
	 */
	public void playAudio(){
		if(mAudioTrack==null)return;		
		if(mAudioTrack.getPlayState() != AudioTrack.PLAYSTATE_PLAYING){	
			mAudioTrack.play();
		}
		JniUtil.nativeAudioBPlayable();
	}

	/**
	 * stop audio
	 */
	public void stopAudio(){
		JniUtil.nativeAudioStop();
		if(mAudioTrack!=null){
			mAudioTrack.stop();	
		}
	}

	/**
	 * stop audio track,release audio memory
	 */
	public void deInitAudio(){
	
		if(mAudioTrack != null){
			mAudioTrack.stop();
			mAudioTrack.release();
			mAudioTrack = null;
		}
		JniUtil.nativeAudioDeinit();
	}

	/**
	 * write sound data to audio track<br/>
	 * attention:this method is call by jni,if you set {@link JniUtil#nativeAudioSetCallbackObject(Object, int)} <br/>
	 * The Object should contain a method named as "audioWrite()" and should not be changed<br/>
	 */
	public void audioWrite() {
		mAudioTrack.write(mAudioData,0,mAudioDataLength);
	}

	/**
	 * mute sound
	 * @param context
	 * @param bMute
	 */
	public void audioSoundMute(Context context,boolean bMute){
		if (mAudioManager == null) {//should just do once otherwise it will never be Unmuted
			mAudioManager =  (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		}
		mAudioManager.setStreamMute(AudioManager.STREAM_MUSIC, bMute);
	}
	
	
	
	
}
