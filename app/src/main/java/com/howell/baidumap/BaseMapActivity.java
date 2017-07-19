package com.howell.baidumap;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.howell.bean.httpbean.GPSDevice;
import com.howell.bean.httpbean.GPSDeviceList;
import com.howell.bean.httpbean.GPSStatus;
import com.howell.ecamnetsdk.R;
import com.howell.protocol.http.HttpManager;
import com.howell.utils.DemoDebugLog;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import static com.baidu.location.d.j.O;
import static com.baidu.location.d.j.f;

/**
 * Created by Administrator on 2017/5/15.
 * 百度地图基类
 * @author howell
 */

public class BaseMapActivity extends AppCompatActivity implements BaiduMap.OnMyLocationClickListener, BaiduMap.OnPolylineClickListener, BaiduMap.OnMapClickListener, BaiduMap.OnMarkerClickListener {
    private static final String TAG = BaseMapActivity.class.getName();
    protected static final int MSG_1 = 0x01;
    protected HttpManager mgr = HttpManager.getInstance();

    MapView mMapView;
    BaiduMap mMap;
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    boolean isFirst = true;

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_1:
                    overLay();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map);
        initView();
        initMyLocation();
    }

    @Override
    protected void onDestroy() {
        mLocClient.stop();
        mMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    private void initView(){
        mMapView = (MapView) findViewById(R.id.map_view);
        mMap = mMapView.getMap();
        mMap.setOnMyLocationClickListener(this);
       // mMap.setOnPolylineClickListener(this);
        mMap.setOnMapClickListener(this);
        mMap.setOnMarkerClickListener(this);
    }

    private void initMyLocation(){
        mMap.setMyLocationEnabled(true);
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(60000);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }


    protected void overLay(){

    }

    protected LatLng WCG84ToBD09(LatLng src){
// 将GPS设备采集的原始GPS坐标转换成百度坐标
        CoordinateConverter converter  = new CoordinateConverter();
        converter.from(CoordinateConverter.CoordType.GPS);
// sourceLatLng待转换坐标
        converter.coord(src);
        return converter.convert();
    }

    protected LatLng GCJ02ToBD09(LatLng src){
        CoordinateConverter converter  = new CoordinateConverter();
        converter.from(CoordinateConverter.CoordType.COMMON);
// sourceLatLng待转换坐标
        converter.coord(src);
        return converter.convert();
    }

    @Override
    public boolean onMyLocationClick() {
        return false;
    }

    @Override
    public boolean onPolylineClick(Polyline polyline) {
        return false;
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public boolean onMapPoiClick(MapPoi mapPoi) {
        return false;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }


    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置

            if (location == null || mMapView == null) {
                DemoDebugLog.logE(TAG+":onReceiveLocation","receive location =null");
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();

            mMap.setMyLocationData(locData);


//            if (isFirst) {
//                isFirst = false;
//                LatLng ll = new LatLng(location.getLatitude(),
//                        location.getLongitude());
//                MapStatus.Builder builder = new MapStatus.Builder();
//                builder.target(ll).zoom(18.0f);
//                mMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
//            }



        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

}
