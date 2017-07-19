package com.howell.player;

import android.content.Context;

import com.howell.bean.turnbean.TurnSubScribeAckBean;
import com.howell.jni.JniUtil;
import com.howell.protocol.turn.TurnJsonUtil;
import com.howell.protocol.turn.TurnManager;
import com.howell.protocol.utils.SSLSocketUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/24.
 */

public abstract class BasePlayer {

    private ArrayList<IPlayResult> mCbs;
    protected TurnManager mTurnMgr;
    protected String mDeviceId;
    protected int mChannel;
    protected boolean mIsSub;
    protected String mBeg,mEnd;
    public BasePlayer init(TurnManager mgr,String deviceId,int channel,boolean isSub){
        this.mTurnMgr = mgr;
        this.mDeviceId = deviceId;
        this.mChannel = channel;
        this.mIsSub = isSub;
        JniUtil.netInit();
        AudioAction.getInstance().initAudio();
        return this;
    }

    public void deInit(){
        AudioAction.getInstance().deInitAudio();
        JniUtil.netDeinit();
        if (mCbs!=null) {
            mCbs.clear();
            mCbs = null;
        }
    }



    public void playView(){
        AudioAction.getInstance().playAudio();
    }

    public void playback(String beg,String end){
        mBeg = beg;
        mEnd = end;
        AudioAction.getInstance().playAudio();
    }

    public void rePlayback(String beg,String end){
        mBeg = beg;
        mEnd = end;
        AudioAction.getInstance().playAudio();
    }


    public void getRecoredeFile(String beg,String end){
        mBeg = beg;
        mEnd = end;
    }

    public void readyAndPlay(TurnSubScribeAckBean bean,boolean isPlayback){
        JniUtil.readyPlayTurnLive(bean,isPlayback?1:0);
        JniUtil.playView();
    }


    public void stopView(){
        AudioAction.getInstance().stopAudio();
        JniUtil.stopView();
    }

    public void registPlayResult(IPlayResult cb){
        if (mCbs==null){
            mCbs = new ArrayList<>();
        }
        for (IPlayResult r:mCbs){
            if (cb.equals(r))return;
        }
        mCbs.add(cb);
    }

    public void unregistPlayResult(IPlayResult cb){
        if (mCbs==null)return;
        mCbs.remove(cb);
        if (mCbs.size()==0)mCbs=null;
    }





    protected void sendPlayResult(){



    }

    public interface IPlayResult{


    }


}
