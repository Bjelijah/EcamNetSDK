package com.howell.ecamnetsdk;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.Toast;

import com.howell.bean.httpbean.VehicleDetectionResult;
import com.howell.bean.httpbean.VehiclePlatePicture;
import com.howell.camera.CameraInterface;
import com.howell.camera.ICameraInterface;
import com.howell.protocol.http.HttpManager;
import com.howell.protocol.utils.SDKDebugLog;
import com.howell.utils.DemoDebugLog;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/12.
 */

public class TakePhotoActivity extends Activity implements ICameraInterface {

    private static final String TAG = TakePhotoActivity.class.getName();

    private static final int MSG_PIC_GET = 0x00;
    private static final int MSG_PIC_SEND_OK = 0x01;
    private static final int MSG_PIC_SEND_ERROR = 0x02;
    private byte [] mPic = null;
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_PIC_GET:
                    sendPic();
                    break;
            }
        }
    };

    @BindView(R.id.player_yuv_photo) Button TakePhotoBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_take_photo);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        CameraInterface.getInstance().setCameraInterfaceCallback(this);
    }

    @Override
    protected void onDestroy() {
        CameraInterface.getInstance().setCameraInterfaceCallback(null);
        super.onDestroy();
    }

    @OnClick(R.id.player_yuv_photo) void clickTakePhoto(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                CameraInterface.getInstance().doTakePicture();
            }
        }.start();


    }

    private void sendPic(){
        if (mPic == null)return;
        new AsyncTask<Void,Void,Void>(){
            VehiclePlatePicture pic;
            VehicleDetectionResult result ;
            @Override
            protected Void doInBackground(Void... params) {
                HttpManager mgr = HttpManager.getInstance();
                try {
                    //发送照片
                    pic = mgr.updataVehiclePicture(mPic);
                    String picID = pic.getId();
                    //查询比对结果
                    result = mgr.queryVehicleDetectionResult(picID);
                    SDKDebugLog.logI(TAG,"result:"+result.toString());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (NullPointerException e){
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (pic==null||result==null){
                    Toast.makeText(TakePhotoActivity.this,"照片发送失败",Toast.LENGTH_LONG).show();
                    return;
                }
                DemoDebugLog.logI(TAG+":sendPic",pic.toString());
                DemoDebugLog.logI(TAG+":sendPic",result.toString());
                Toast.makeText(TakePhotoActivity.this,"照片发送成功 result="+result.getResult(),Toast.LENGTH_LONG).show();
                super.onPostExecute(aVoid);
            }
        }.execute();
    }

    @Override
    public void takePhotoFinish(final Bitmap bitmap) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
                mPic = stream.toByteArray();
//        mPic = FileUtil.loadPic();
                mHandler.sendEmptyMessage(MSG_PIC_GET);
                bitmap.recycle();
            }
        }.start();

    }
}
