package com.howell.baidumap;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.baidu.mapapi.map.DotOptions;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.howell.bean.httpbean.GPSDeviceList;
import com.howell.bean.httpbean.RMC;
import com.howell.bean.httpbean.RMCList;
import com.howell.utils.DemoDebugLog;
import com.howell.utils.MyUtil;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/5/16.
 */

public class RMCMapActivity extends BaseMapActivity {
    public static final String RMC_NAME = "rmc";
    private static final String TAG = RMCMapActivity.class.getName();
    ArrayList<RMC> mRMCs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getRMC();
    }



    @Override
    protected void overLay() {
        super.overLay();
        if (mRMCs == null || mRMCs.size()==0){
            Toast.makeText(this,"没有轨迹记录",Toast.LENGTH_SHORT).show();
            return;
        }
//        Log.i("123","mRmcs="+mRMCs+"  size="+mRMCs.size());
        List<LatLng> list = new ArrayList<>();
        for (RMC c:mRMCs){
            LatLng latLng = WCG84ToBD09(new LatLng(c.getLatitude(),c.getLongitude()));
            list.add(latLng);
            drawPoint(latLng);
        }
        drawLine(list);
        drawMark(list.get(0),"终点");
        if (list.size()-1!=0) {
            drawMark(list.get(list.size() - 1), "起点");
        }


        if (isFirst){
            isFirst = false;
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(list.get(0)).zoom(18.0f);
            mMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        }


    }

    private void drawLine(List<LatLng> list){
        if (list.size()<2){
            DemoDebugLog.logE(TAG+":drawLine","list size <2");
            Toast.makeText(this,"list size <2",Toast.LENGTH_LONG).show();
            return;
        }
        OverlayOptions ooPolyLine  = new PolylineOptions().width(5).points(list).color(0xaaff00ff);
        mMap.addOverlay(ooPolyLine);

    }

    private void drawPoint(LatLng p){
        OverlayOptions options = new DotOptions().center(p).color(0xaaff00ff).radius(15);

        mMap.addOverlay(options);
    }

    private void drawMark(LatLng p,String str){
//        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher);
//        OverlayOptions options = new MarkerOptions().position(p).title(str).icon(bitmap).alpha(0);

        OverlayOptions options = new TextOptions().bgColor(0xaaff00ff).fontSize(24).position(p).text(str);
        mMap.addOverlay(options);
    }

    private void getRMC(){

        new Thread(){
            @Override
            public void run() {
                super.run();
                Date now = new Date();
                Calendar c = Calendar.getInstance();
                c.setTime(now);
                c.add(Calendar.HOUR_OF_DAY,-12);
                Date before = c.getTime();
                String endStr = MyUtil.Date2ISODate(now);
                String begStr= MyUtil.Date2ISODate(before);
                DemoDebugLog.logI(TAG+":getRMC","begStr="+begStr+"  endStr="+endStr);
                GPSDeviceList list = null;
                try {
                    list = mgr.queryGPSDeviceList();

                    String deviceId = list.getDevices().get(0).getId();
                    DemoDebugLog.logI(TAG+":getRMC","deviceID="+deviceId);
                    RMCList rmclist = mgr.queryGPSRMCList(deviceId,begStr,endStr,null,60,0,0);
                    mRMCs = rmclist.getRMCs();
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
    public boolean onPolylineClick(Polyline polyline) {
        return true;
    }


    @Override
    public boolean onMyLocationClick() {
        return super.onMyLocationClick();
    }

    @Override
    public void onMapClick(LatLng latLng) {
        boolean isfind = false;
        for (RMC c:mRMCs){
            LatLng clatLng = WCG84ToBD09(new LatLng(c.getLatitude(),c.getLongitude()));
            if (Math.abs(clatLng.latitude - latLng.latitude)<0.0002   &&   Math.abs(clatLng.longitude - latLng.longitude)<0.0002  ){
                isfind = true;
                break;
            }
        }
        if (!isfind)

        super.onMapClick(latLng);
    }

    @Override
    public boolean onMapPoiClick(MapPoi mapPoi) {
        return super.onMapPoiClick(mapPoi);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return super.onMarkerClick(marker);
    }
}
