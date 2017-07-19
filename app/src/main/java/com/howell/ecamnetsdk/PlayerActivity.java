package com.howell.ecamnetsdk;

import android.app.Activity;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.howell.jni.JniUtil;
import com.howell.player.BasePlayer;
import com.howell.player.YV12Renderer;
import com.howell.protocol.turn.TurnManager;
import com.howell.utils.DemoDebugLog;
import com.howell.utils.MyUtil;

import java.util.Date;

/**
 *
 * Created by Administrator on 2017/4/21.
 * 播放界面
 * @author howell
 */
public class PlayerActivity extends Activity implements SurfaceHolder.Callback,IConfig, SeekBar.OnSeekBarChangeListener, PlayManager.TimeCallback {
    private static final String TAG = PlayerActivity.class.getName();
    private final int MSG_DRAW = 0x01;
    private GLSurfaceView mGlView;
    private BasePlayer mgr;
    private String mDeviceId;
    private int mChannel;
    private boolean isPlayback;
    private String begTime;
    private String endTime;
    private Button mPauseBtn;
    private SeekBar mSeekBar;
    private TextView mTv;
    private long mAllSec;
    private Date mBegDate,mEndDate;
    private long mNewBeg=0;
    private boolean isUserControl=false;

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_DRAW:
                    drawInfo(msg.arg1,msg.arg2);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        mPauseBtn = (Button) findViewById(R.id.btn_pause_play);
        mSeekBar = (SeekBar) findViewById(R.id.seekBar);
        mSeekBar.setOnSeekBarChangeListener(this);
        mTv = (TextView) findViewById(R.id.tv_play_speed);
        mPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (JniUtil.isPause()==1){
                    mPauseBtn.setText("暂停");
                }else{
                    mPauseBtn.setText("播放");
                }
                JniUtil.pauseAndPlayView();
            }
        });
        boolean bar = false;
        if(bar){
            mDeviceId ="00310101011111111000031000000000";
        }else{
            mDeviceId = getIntent().getStringExtra("id");//"00310101011111111000031000000000";
        }

//        mDeviceId = getIntent().getStringExtra("id");//"00310101011111111000031000000000";
        mChannel = getIntent().getIntExtra("channel",0);
        isPlayback = getIntent().getBooleanExtra("isPlayback",false);
        if (isPlayback){
            begTime = getIntent().getStringExtra("beg");
            endTime = getIntent().getStringExtra("end");
            mPauseBtn.setVisibility(View.VISIBLE);
            mSeekBar.setVisibility(View.VISIBLE);
        }else{
            mPauseBtn.setVisibility(View.GONE);
            mSeekBar.setVisibility(View.GONE);
        }
//        mDeviceId = "00310101031111111000001000000000";
        DemoDebugLog.logI(TAG+"","mDeviceId="+mDeviceId);
        initUI();
        initFun();
    }

    @Override
    protected void onDestroy() {
        ((PlayManager)mgr).stopTimeTask();
        ((PlayManager)mgr).setTimeCallBack(null);
        mgr.stopView();

        mgr.deInit();

        super.onDestroy();
    }

    private void initUI(){
        mGlView = (GLSurfaceView) findViewById(R.id.glsurface_view);
        mGlView.setEGLContextClientVersion(2);
        mGlView.setRenderer(new YV12Renderer(this,mGlView));
        mGlView.getHolder().addCallback(this);
        mGlView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

    }

    /**
     * 初始化 playManager<br/>
     * 播放 <br/>
     * 需要设备id及设备的通道号，若是回放还需要回放开始时间和回放结束时间
     */
    private void initFun(){
        mgr = new PlayManager().init(TurnManager.getInstance(),mDeviceId,mChannel,true);

        new Thread(){
            @Override
            public void run() {
                super.run();
                if (isPlayback){
                    mgr.playback(begTime,endTime);
                }else{
                    mgr.playView();
                }
                if (isPlayback){
                    mBegDate = MyUtil.ISODateString2ISODate(begTime);
                    mEndDate = MyUtil.ISODateString2ISODate(endTime);
                    mAllSec = mEndDate.getTime() - mBegDate.getTime();
                    DemoDebugLog.logI(TAG+":initFun","mEndDate="+mEndDate+"   mBegDate="+mBegDate+"   allSec="+mAllSec);
                    mSeekBar.setMax((int) mAllSec);
                }
                ((PlayManager)mgr).setTimeCallBack(PlayerActivity.this);
                ((PlayManager)mgr).startTimeTask();



            }
        }.start();

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }



    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//        Log.i("123","progress="+progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        isUserControl = true;
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        isUserControl = false;
        mNewBeg = mBegDate.getTime()+seekBar.getProgress();
        Date date = new Date(mNewBeg);
        String str = MyUtil.Date2ISODate(date);
        mgr.rePlayback(str,endTime);

    }

    private void drawInfo(int speed,int timeOffset){
        mTv.setText(String.format("%d kbit/s",speed));
        mTv.setTextColor(0xaaff0000);
        if(isPlayback && !isUserControl) {

            long o =  (mNewBeg-mBegDate.getTime());
            int progress = (int) ((o>0?o:0) + timeOffset);
            mSeekBar.setProgress(progress);
        }
    }

    @Override
    public void onTime(final int speed, long time, long first) {
        Message msg = new Message();
        msg.what = MSG_DRAW;
        msg.arg1 = speed;
        msg.arg2 = (int) (time-first);
        mHandler.sendMessage(msg);
    }
}
