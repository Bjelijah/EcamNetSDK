package com.howell.baidumap;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.howell.bean.httpbean.GPSDevice;
import com.howell.bean.httpbean.GPSDeviceList;
import com.howell.bean.httpbean.GPSStatus;
import com.howell.ecamnetsdk.R;
import com.howell.ecamnetsdk.VehicleModuleActivity;
import com.howell.protocol.http.HttpManager;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2017/5/16.
 */

public class GPSMapActivity extends BaseMapActivity {
    public static final String STATUS_NAME = "status";
    HttpManager mgr = HttpManager.getInstance();
    double mlatitudel,mlongitude,mSpeed,mCourse;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getGPS();
    }

    @Override
    protected void overLay() {
        super.overLay();
        LatLng point = WCG84ToBD09(new LatLng(mlatitudel,mlongitude));
//        LatLng point = GCJ02ToBD09(new LatLng(mlatitudel,mlongitude));

        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher);
        OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
        mMap.addOverlay(option);
        if (isFirst){
            isFirst = false;

            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(point).zoom(18.0f);
            mMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        }
    }

    private void getGPS(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    GPSDeviceList list = mgr.queryGPSDeviceList();

                    GPSDevice device = list.getDevices().get(0);////fixme 查询第一个gps设备
                    GPSStatus status = device.getGpsStatus();
                    mlatitudel = status.getLatitude();
                    mlongitude = status.getLongitude();
                    mSpeed = status.getSpeed();
                    mCourse = status.getCourse();
                    mHandler.sendEmptyMessage(MSG_1);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();






    }

    @Override
    public boolean onMarkerClick(Marker marker) {


        return super.onMarkerClick(marker);
    }
}
