package com.demo.receive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Base64;
import android.util.Log;

import com.demo.db.DBDao;
import com.demo.utils.DemoDebugLog;
import com.demo.utils.Util;
import com.howell.bean.httpbean.GISMap;
import com.howell.bean.httpbean.GISMapItem;
import com.howell.bean.httpbean.GISMapItemList;
import com.howell.bean.httpbean.GISMapList;
import com.howell.bean.httpbean.Vehicle;
import com.howell.bean.httpbean.VehicleList;
import com.howell.bean.httpbean.VehiclePlateRecord;
import com.howell.bean.httpbean.VehiclePlateRecordList;
import com.howell.bean.httpbean.WSRes;
import com.howell.protocol.http.HttpManager;
import com.howell.protocol.utils.SDKDebugLog;
import com.howell.service.PushService;


import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * 推送接收器<br></>
 * AndroidManifest.xml<br></>
 * 注册 com.howell.sdk.alarmEvent 接收事件推送<br></>
 * 注册 com.howell.sdk.alarmNotice 接收消息推送<br></>
 * Created by Administrator on 2017/7/7.
 * @author howell
 */

public class MyReceive extends BroadcastReceiver {
    private static final String TAG = MyReceive.class.getName();
    DBDao mDB=null;
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (mDB==null)mDB = new DBDao(context);
        if (action.equals("com.howell.sdk.alarmEvent")){
            //todo 事件
            doFun((WSRes.AlarmEvent) intent.getSerializableExtra(PushService.BROADCAST_NAME_EVENT));
        }else if(action.equals("com.howell.sdk.alarmNotice")){
            //todo 消息
            doFun((WSRes.AlarmNotice) intent.getSerializableExtra(PushService.BROADCAST_NAME_NOTICE));
        }
    }

    private void doFun(WSRes.AlarmEvent event){
        if (event==null)return;
        mDB.insert(event);//保存到数据库
        String type = event.getEventType();//触发事件
        //TODO：触发事件根据协议具体判断 事件类型参见<readme.doc> 附录1

        String devId = event.getId();//触发的报警器id
        String time = event.getTime();//触发时间
        String info = event.getExtendInformation();
        DemoDebugLog.logI(TAG+":MyReceiver","eventType="+type+"   devId="+devId+"  time="+time);

        //查询车辆信息
        if (devId!=null && "VehiclePlate".equals(type)){
            byte [] b = Base64.decode(info,0);
            Log.e("123","info="+new String(b));//显示推送过来的事件信息
            queryDeviceRecords(devId,time);//或者根据 deviceID 和 time 查询
            return;
        }
        //TODO
    }

    private void doFun(WSRes.AlarmNotice notice){
        if (notice==null)return;
        mDB.insert(notice);//保存到数据库
        DemoDebugLog.logI(TAG,"notice:"+notice.toString());
    }

    /**
     * example<br/>
     * 查询车辆探测器记录<br/>
     * 根据推送获得的设备ID和时间查询车辆探测器历史纪录
     *
     * @param deviceId 触发事件的设备id
     * @param time 设备触发时间
     */
    private void queryDeviceRecords(final String deviceId, String time){
        final HttpManager hMgr = HttpManager.getInstance();
        DemoDebugLog.logI(TAG+":queryDeviceRecords","time="+time);
        String [] str =time.split("\\.");
        time = str[0]+"Z";
        DemoDebugLog.logI(TAG+":queryDeviceRecords","time after split ="+time);
        Date datePush = Util.ISODateString2ISODate(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datePush);
        // calendar.add(Calendar.YEAR,-1);
        calendar.add(Calendar.MINUTE,-1);
        Date dateBefore = calendar.getTime();
        calendar.add(Calendar.MINUTE,+2);
        Date dateAfter = calendar.getTime();
        final String begTime = Util.Date2ISODate(dateBefore);//fixme 触发事件的前一分钟
        final String endTime = Util.Date2ISODate(dateAfter);//fixme 触发事件的后一分钟

        DemoDebugLog.logI(TAG+":queryDeviceRecords","begTime ="+begTime+"   endTime="+endTime);
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    VehiclePlateRecordList list = hMgr.queryVehiclePlateRecords(deviceId,begTime,endTime,0,0);
                    //fixme list size==0  查询失败
                    if (list==null){DemoDebugLog.logE(TAG+":queryDeviceRecords","list=null");return;}


                    //根据 车牌查车
                    ArrayList<VehiclePlateRecord> records = list.getRecords();
                    if (records.size()==0){DemoDebugLog.logE(TAG+":queryDeviceRecords","list size=0");return;}
                    VehiclePlateRecord r = records.get(0);//fixme test:只查第一辆
                    VehicleList vehicleList = hMgr.queryVehicleList(r.getPlate(),null,null,0,0);
                    if (vehicleList==null||vehicleList.getVehicles().size()==0){DemoDebugLog.logE(TAG+":queryDeviceRecords","vehicleList==null or size =0");return;}
                    Vehicle vehicle = vehicleList.getVehicles().get(0);
                    //根据车辆id  遍历查 绑定的 gps设备

                    GISMapList gisMapList = hMgr.queryBusinessGISMaps();
                    ArrayList<GISMap> maps = gisMapList.getGisMaps();
                    GISMap map = maps.get(0);//fixme test:假设配置在第一张地图里
                    GISMapItemList items = hMgr.queryBusinessGISMapsItems(map.getId());
                    ArrayList<GISMapItem> itemsArray = items.getItems();
                    GISMapItem item=null;
                    for (GISMapItem i:itemsArray){
                        if (i.getItemId().equals(vehicle.getId())){
                            item = i;
                            break;
                        }
                    }
                    SDKDebugLog.logI(TAG,"queryDeviceRecords="+item.toString());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }catch (IndexOutOfBoundsException e){
                    e.printStackTrace();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();


    }



}
