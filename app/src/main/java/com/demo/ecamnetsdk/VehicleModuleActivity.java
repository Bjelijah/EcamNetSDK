package com.demo.ecamnetsdk;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.demo.baidumap.GPSMapActivity;
import com.demo.utils.Util;
import com.howell.action.PushAction;
import com.demo.baidumap.RMCMapActivity;
import com.howell.bean.httpbean.GISMap;
import com.howell.bean.httpbean.GISMapItem;
import com.howell.bean.httpbean.GISMapItemList;
import com.howell.bean.httpbean.GISMapList;
import com.howell.bean.httpbean.GPSDevice;
import com.howell.bean.httpbean.VehicleList;
import com.howell.bean.httpbean.VehiclePlateRecordList;
import com.howell.bean.httpbean.WSRes;
import com.demo.db.DBDao;
import com.howell.ecamnetsdk.R;
import com.howell.protocol.http.HttpManager;
import com.howell.protocol.utils.SDKDebugLog;
import com.demo.utils.DemoDebugLog;


import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/10.<br/>
 * 车辆模块<br/>
 *
 * @author howell
 */

public class VehicleModuleActivity extends AppCompatActivity implements IConfig{
    private static final String TAG = VehicleModuleActivity.class.getName();
    HttpManager hMgr = HttpManager.getInstance();
    @BindView(R.id.btn_vehicle_start_service) Button mStartService;
    @BindView(R.id.btn_vehicle_stop_service) Button mStopService;
    @BindView(R.id.btn_vehicle_query_event) Button mQueryEvent;
    @BindView(R.id.btn_vehicle_query_vehicle) Button mQueryVehicle;
    @BindView(R.id.btn_vehicle_photo_query) Button mPhotoQuery;
    @BindView(R.id.btn_vehicle_gis_query) Button mGisQuery;
    @BindView(R.id.btn_vehicle_map) Button mMap;
    @BindView(R.id.btn_vehicle_rmc) Button mRMC;
    @BindView(R.id.btn_record) Button mRecord;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_module);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    private void init(){
        hMgr.initURL(getApplicationContext(),TEST_IP,IS_SSL?8850:8800,IS_SSL);
    }

    /**
     * 绑定车辆推送服务
     */
    @OnClick(R.id.btn_vehicle_start_service) void clickStartService(){
        PushAction.getInstance().startPushServer(this,TEST_IP,TEST_ACCOUTN);
    }

    /**
     * 解绑推送服务
     */
    @OnClick(R.id.btn_vehicle_stop_service) void clickStopService(){
        PushAction.getInstance().stopPushServer(this);
    }

    /**
     * 查询车辆历史事件<br/>
     * 收到推送，保存在本地数据库，从数据库查询历史事件
     */
    @OnClick(R.id.btn_vehicle_query_event) void clickQueryEvent(){
        DBDao dao = new DBDao(this);
//        ArrayList<WSRes.AlarmAliveRes> history = dao.queryAlive();
//        ArrayList<WSRes.AlarmNotice> history = dao.queryNotice();
        ArrayList<WSRes.AlarmEvent> history = dao.queryEvent();
        DemoDebugLog.logI(TAG+":clickQueryEvent","history="+history.toString());
    }



    /**
     * 查询车辆信息<br/>
     * 根据车牌模糊查询<br/>
     * 根据品牌查询<br/>
     */
    @OnClick(R.id.btn_vehicle_query_vehicle) void clickQueryVehicle(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    VehicleList list1 = hMgr.queryVehicleList("*1*",null,true,0,0);//// FIXME:  通过 关键字（车牌）查车辆  *表示缺损查询
                    VehicleList list2 = hMgr.queryVehicleList(null,"null",true,0,0);// // FIXME: 2017/6/12 通过关键字 （品牌） 查车辆
                    VehicleList list3 = hMgr.queryVehicleList();//FIXME 查询所有
                    DemoDebugLog.logI(TAG+":clickQueryVehicle","list1="+list1);
                    DemoDebugLog.logI(TAG+":clickQueryVehicle","list2="+list2);
                    DemoDebugLog.logI(TAG+":clickQueryVehicle","list2="+list3);
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

    /**
     * 拍照查车辆信息
     */
    @OnClick(R.id.btn_vehicle_photo_query) void clickPhotoVehicle(){
        startActivity(new Intent(this,TakePhotoActivity.class));
    }

    /**
     * 查询配置在gis地图中的车辆信息例子<br/>
     * 查询gis地图下的项 如果是车 继续查询车绑定的gps设备
     * 最终返回gps
     *
     */
    @OnClick(R.id.btn_vehicle_gis_query) void clickGis(){
        new Thread(){
            @Override
            public void run() {
                try {
                    GISMapList list = hMgr.queryBusinessGISMaps();
                    ArrayList<GISMap> maps = list.getGisMaps();
                    String mapId = maps.get(0).getId();//FIXME  查询第一张地图

                    GISMapItemList itemList = hMgr.queryBusinessGISMapsItems(mapId);//FIXME 查第一张图中的项
                    ArrayList<GISMapItem> items = itemList.getItems();
                    double longitude = items.get(0).getLongitude();
                    double latitude = items.get(0).getLatitude();

                    SDKDebugLog.logI(TAG,"longitude="+longitude+"  Latitude="+latitude);//TODO show in map

                    ////FIXME 或者继续查询该设备绑定的gps设备
                    ArrayList<GISMapItem> vehicleWithGPS = new ArrayList<GISMapItem>();
                    for (GISMapItem i:items){
                        if (com.howell.protocol.http.Util.isTypeId(68,i.getItemId())){//fixme 68 车
                            vehicleWithGPS.add(i);
                        }
                    }
                    DemoDebugLog.logI(TAG+":clickGis","vehicle="+vehicleWithGPS.toString());//fixme 选出这些项中是车的

                    for (GISMapItem i:vehicleWithGPS){//fixme 车绑定的gps
                        if (null!=i.getGpsId()){
                            GPSDevice gps = hMgr.queryGPSDevice(i.getGpsId());//fixme 查gps
                            DemoDebugLog.logI(TAG+":clickGis","gps="+gps.toString());
                            DemoDebugLog.logI(TAG+":clickGis","status="+gps.getGpsStatus().toString());
                        }
                    }

                    //TODO show gps in Baidu map @see {@link GPSMapActivity}

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (NullPointerException e){
                    e.printStackTrace();
                } catch (Exception e){
                    e.printStackTrace();
                }
                super.run();
            }
        }.start();
    }

    /**
     * 根据gps在地图中显示
     */
    @OnClick(R.id.btn_vehicle_map) void clickMap(){
        startActivity(new Intent(this,GPSMapActivity.class));
    }

    /**
     * 根据gps列表在地图中显示
     */
    @OnClick(R.id.btn_vehicle_rmc) void clickRMC(){
        startActivity(new Intent(this, RMCMapActivity.class));
    }

    /**
     * 根据 车牌探测器序列号 获取 探测器中的记录
     */
    @OnClick(R.id.btn_record) void clickRecord(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                String accessId = "test_access_id";
                Date now = new Date();
                String end = Util.Date2ISODate(now);
                Calendar c = Calendar.getInstance();
                c.setTime(now);
                c.add(Calendar.DAY_OF_MONTH,-1);
                Date yesterday =  c.getTime();
                String beg = Util.Date2ISODate(yesterday);
                try {
                    VehiclePlateRecordList list = hMgr.queryVehiclePlateDeviceAccessRecords(accessId,beg,end,0,0);
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

}
