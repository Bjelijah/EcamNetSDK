package com.howell.jni;


import android.content.Context;
import android.opengl.GLSurfaceView;



/**
 * jniUtil:jni interface<br/>
 * all jni method is packed in libplayer_jni.so
 *
 * @author howell
 */
public class JniUtil {
	static{
		System.loadLibrary("hwtrans");
		System.loadLibrary("hwplay");
		System.loadLibrary("player_jni");
	}
    public static native void logEnable(boolean enable);
	//yuv

    /**
     * yuv interface ,not to change it<br/>
     * yuv init <br/>
     *
     */
	public static native void YUVInit();			//初始化

    /**
     * yuv release memory
     */
    public static native void YUVDeinit();			//释放内存

    /**
     * yuv set callback
     * @param callbackObject the object which callback method belongs to
     * @param flag should be 0
     */
    public static native void YUVSetCallbackObject(Object callbackObject,int flag);

    /**
     * yuv set callback method
     * @param methodStr the name of callback method in application layer
     * @param flag {@link com.howell.player.YV12Renderer#YV12Renderer(Context, GLSurfaceView)}<br/>
     *             0:callback set played time (type long)(it maybe a timesteamp get from hwplay.so)<br/>
     *             1:callback to tell surfaceview request Render {@link YV12Renderer#requestRender()};
     */
	public static native void YUVSetCallbackMethodName(String methodStr,int flag);

    /**
     * yuv lock; when application layer draw a frame
     */
    public static native void YUVLock();

    /**
     * yuv unlock; after application layer draw a frame and let jni get next
     */
    public static native void YUVUnlock();

    /**
     * yuv set enable; after application layer onSurfaceCreated is finished;
     */
    public static native void YUVSetEnable();//开始显示YUV数据

    /**
     * render Y
     */
    public static native void YUVRenderY();			//渲染Y数据

    /**
     * render U
     */
	public static native void YUVRenderU();			//渲染U数据

    /**
     * render V
     */
    public static native void YUVRenderV();			//渲染V数据

	//sound

    /**
     * sound yuv interface<br/>
     * audio init<br/>
     * more information {@link com.howell.player.AudioAction}
     */
    public static native void nativeAudioInit();

    /**
     * audio release memory
     */
    public static native void nativeAudioDeinit();


    /**
     * set audio callback object ,
     * @param o the object which callback method belongs to;
     * @param flag should be 0;
     */
    public static native void nativeAudioSetCallbackObject(Object o,int flag);

    /**
     * set audio callback method(field)
     * @param str the method(field) name which you want to be callback in application layer
     * @param flag {@link AudioAction#initAudio()},(in this example we set field name)<br/>
     *       0: get audio data length (type int)<br/>
     *       1: get audio data (type byte[]) <br/>
     */
    public static native void nativeAudioSetCallbackMethodName(String str,int flag);


    /**
     * set audio ready to play;<br/>
     * when the audio data is coming it will call the callback method(field) you once set,you may play sound in application layer
     */
    public static native void nativeAudioBPlayable();

    /**
     * set audio stop and it will not call callback even if data coming;
     */
    public static native void nativeAudioStop();

    /**
     * yuv to show on screen
     * @param data yuv 420 data
     * @param len yuv len
     * @param w  yuv width
     * @param h yuv height
     */
    public static native void YUVsetData(byte [] data,int len,int w,int h);

    /**
     * input h264Data to decoder
     * @param data h264 data
     * @param len h264 len
     * @param w h264 width
     * @param h h264 height
     * @param isI if this h264 frame is I frame
     */
    public static native void setH264Data(byte [] data,int len,int w,int h,int isI);

    /**
     * input howell stream to decoder
     * @param data howell stream data
     * @param len stream len
     */
    public static native void setHWData(byte [] data,int len);

    /**
     * transfer h264 data to howell stream data
     * @param inH264 h264 data
     * @param inLen h264 len
     * @param isI if this frame is I Frame
     * @return howell stream data
     */
    public static native byte [] H264toHWStream(byte [] inH264,int inLen,int isI);

	//net and play

    /**
     * net and player jni interface
     * net and player lib init;malloc memory
     */
	public static native void netInit();

    /**
     * release net and player memory
     */
    public static native void netDeinit();

    /**
     * client login to camera direct with 5198 protocol
     * @deprecated we not used it in this example
     * @param ip  the ip of ip camera
     * @return true:login ok;false:login error(maybe ip is wrong);
     */
	public static native boolean login(String ip);//no using

    /**
     * client logout from camera direct with 5198 protocol
     * @deprecated we not used it in this example
     * @return true:logout success;false:logout error
     */
	public static native boolean loginOut();//no using

    /**
     * set net or player callback object
     * @deprecated we not used it in this example
     * @param o callback object
     */
	public static native void setCallBackObj(Object o);

    /**
     * ready play live view
     * @deprecated not use in this example {@link JniUtil#readyPlayTurnLive(Object)}
     * @return true:ok;false:error
     */
	public static native boolean readyPlayLive();

    /**
     * ready play live view
     * @param bean {@link com.howell.bean.TurnSubScribeAckBean} this param is from a callback after transSubscribe<br/>
     * @return true:ok;false:error
     */
	public static native boolean readyPlayTurnLive(Object bean,int isPlayback);

    /**
     * ready play playback
     * @deprecated not use in this example
     * @return true:ok;false:error
     */
	public static native boolean readyPlayPlayback();

    /**
     * play view
     */
    public static native void playView();

    /**
     * play or pause
     */
    public static native void pauseAndPlayView();

    /**
     * if the status is pause now
     * @return if is pause
     */
    public static native int isPause();


    /**
     * stop view
     */
    public static native void stopView();

    public static native long getFirstTimeStamp();
    public static native long getTimeStamp();


    /**
     * get 265 version
     * @deprecated not use in this example
     */
	public static native void getHI265Version();

	//transmission
    /**
     * trans jni interface<br/>
     * turn protocol packed in libhwtrans.so<br/>
     * more information {@see "皓维流转发服务协议.doc"}

     */
	public static native void transInit();

    /**
     * set callback object
     * @param o the object that callback method belongs to
     * @param flag it should be 0
     */
	public static native void transSetCallBackObj(Object o,int flag);

    /**
     * set callback method
     * @param methodName the callback method name
     * @param flag 0:on connect ok callback  (param type String : session id)  <br/>
     *             1:on disconnect callback  <br/>
     *             2:on recordFile list callback  (not use in this example) <br/>
     *             3:on disconnect unexpect callback <br/>
     *             4:on subscribe callback  (param type json string:information about subscribe result)<br/>
     *             5:no use in this example <br/>
     *             6:on unsubscribe callback <br/>
     *             <br/>
     */
	public static native void transSetCallbackMethodName(String methodName,int flag);

    /**
     * release trans memory
     */
    public static native void transDeinit();

    /**
     * send turn protocol to connect to turn server
     * @param ip turn server ip
     * @param port turn server port,in default is use ssl it's 8862 else it's 8812
     * @param isUseSSL if use ssl
     * @param type it should be 100 for pc, 101 for android phone,102 for ios
     * @param id   device id get from
     * @param name account name that you login
     * @param pwd  password that you login
     */
	public static native void transConnect(String ip,int port,boolean isUseSSL,int type,String id,String name,String pwd);

    /**
     *  send turn protocol to disconnect to turn server
     */
    public static native void transDisconnect();

    /**
     * send turn protocol to subscribe the stream
     * @param jsonStr protocol (type json String){@link com.howell.bean.Subscribe}
     * @param jsonLen json string length
     */
	public static native void transSubscribe(String jsonStr,int jsonLen);

    /**
     * send turn protocol to unsubscribe stream;
     */
    public static native void transUnsubscribe();

    /**
     * catch the picture (not send protocol just catch from libhwplay.so)
     * @param path the path where picture should be saved;
     */
	public static native void catchPic(String path);

    /**
     * get stream length since last call this method (if you call this method ,the length will be set to zero)
     * @return stream data length
     */
	public static native int transGetStreamLenSomeTime();

    /**
     * get camera information
     * @deprecated not supported in this example
     * @param jsonStr
     * @param jsonLen
     */
	public static native void transGetCam(String jsonStr,int jsonLen);

    /**
     * get recordfiles list
     * @deprecated not supported int this example
     * @param jsonStr
     * @param jsonLen
     */
	public static native void transGetRecordFiles(String jsonStr,int jsonLen);

    /**
     * set crt file<br/>
     * save the crt files to memory
     * @deprecated {@link JniUtil#transSetCrtPaht(String, String, String)}
     * @param ca ca.crt files
     * @param client client.crt
     * @param key client.key
     */
	public static native void transSetCrt(String ca,String client,String key);

    /**
     * set crt file path so that they can be read in libhwtrans.so <br/>
     * @param caPath path that you saved ca.crt in application layer
     * @param clientPath path that you saved client.crt in application layer
     * @param keyPath path that you saved client.key in application layer
     */
	public static native void transSetCrtPaht(String caPath,String clientPath,String keyPath);
	//turn
    /**
     * send camera data to turn server by turn protocol
     * @deprecated not used in this example
     * @param data camera data
     * @param len data length
     */
	public static native void turnInputViewData(byte [] data,int len);
	
	
}
