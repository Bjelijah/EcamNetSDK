package com.howell.protocol.soap;

import android.content.Context;
import android.util.Log;

import com.howell.bean.soap.*;

import com.howell.protocol.utils.AnalyzingDoNetOutput;
import com.howell.protocol.utils.SDKDebugLog;
import com.howell.protocol.utils.SSLConection;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/16.<br/>
 * SoapManager:the example of soap protocol,which is the main communication with server<br/>
 * more information {@see "移动终端通讯协议.doc"}
 * @author howell
 */
public class SoapManager {
    private static final String TAG = SoapManager.class.getName();
    private String mNameSpace = "http://www.haoweis.com/HomeServices/MCU/";// what ever
    private String mEndPoint;//soap url
    private Context mContext;
    private boolean mIsSSL;

    /**
     * init
     * @param c  context
     * @param serverIP ip of server, it supports domain name
     * @param serverPort port of server
     * @param isUseSSL true if use ssl
     * @return object of Soapmanager
     */
    public SoapManager initURL(Context c, String serverIP, int serverPort, boolean isUseSSL){
        mContext = c;
        mIsSSL = isUseSSL;
        mEndPoint = isUseSSL?"https://" + serverIP + ":" + serverPort + "/HomeService/HomeMCUService.svc?wsdl"
                :"http://" + serverIP + ":" + serverPort + "/HomeService/HomeMCUService.svc?wsdl";
        return this;
    }

    private SoapObject initEnvelopAndTransport(SoapObject rpc, String sSoapAction) throws IOException, XmlPullParserException {
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.bodyOut = rpc;
        envelope.dotNet = true;
        envelope.encodingStyle = "UTF-8";
        envelope.setOutputSoapObject(rpc);

        if (mIsSSL) {
            SSLConection.allowAllSSL(mContext);
        }
        HttpTransportSE transport;

        transport = new HttpTransportSE(mEndPoint);
        transport.debug = true;
        SDKDebugLog.logI("soap",sSoapAction);
        try{
            transport.call(sSoapAction, envelope);
        }catch(Exception e){
            e.printStackTrace();
        }


        SoapObject soapObject = (SoapObject) envelope.bodyIn;
        SDKDebugLog.logI(TAG+":initEnvelopAndTransport","res="+soapObject.toString());
        return soapObject;
    }

    /**
     * check if this phone has be Authenticated
     * @param req {@link QueryDeviceAuthenticatedReq}
     * @return  {@link QueryDeviceAuthenticatedRes} <br/>
     * {@link QueryDeviceAuthenticatedRes#getResult()} the result <br/>
     * "OK":success; <br/>
     * {@link QueryDeviceAuthenticatedRes#Authenticated} <br/>
     * true:already Authenticated;<br/>
     * @throws IOException
     * @throws XmlPullParserException
     */
    public QueryDeviceAuthenticatedRes getDeviceAuthenticatedRes(QueryDeviceAuthenticatedReq req) throws IOException, XmlPullParserException {
        QueryDeviceAuthenticatedRes res = new QueryDeviceAuthenticatedRes();
        SoapObject rpc = new SoapObject(mNameSpace, "queryMCUDeviceAuthenticatedReq");
        rpc.addProperty("UUID", req.getUUID());
        SoapObject object = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/queryMCUDeviceAuthenticated");
        Object result = object.getProperty("result");
        res.setResult(result.toString());
        Object Authenticated = object.getProperty("Authenticated");
        res.setAuthenticated(Authenticated.toString().equalsIgnoreCase("True")?true:false);
        return res;
    }

    /**
     * authenticate to server with phone unique id (in this example we use imei)
     * @param req {@link UpdataDeviceAuthenticatedReq}
     * @return {@link UpdataDeviceAuthenticatedRes} <br/>
     * {@link UpdataDeviceAuthenticatedRes#getResult()} the result
     * "OK":authenticate to server is success;<br/>
     * @throws IOException
     * @throws XmlPullParserException
     */
    public UpdataDeviceAuthenticatedRes getUpdataDeviceAuthenticatedRes(UpdataDeviceAuthenticatedReq req) throws IOException, XmlPullParserException {
        UpdataDeviceAuthenticatedRes res = new UpdataDeviceAuthenticatedRes();
        SoapObject rpc = new SoapObject(mNameSpace, "uploadMCUDeviceReq");
        rpc.addProperty("UUID",req.getUUID());
        rpc.addProperty("Model",req.getModel());
        rpc.addProperty("Type",req.getType());
        rpc.addProperty("OSType",req.getOSType());
        rpc.addProperty("OSVersion", req.getOSVersion());
        rpc.addProperty("Manufactory",req.getManufactory());
        rpc.addProperty("IEMI",req.getIMEI());
        SoapObject object = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/uploadMCUDevice");
        Object result = object.getProperty("result") ;
        res.setResult(result.toString());
        return res;
    }

    /**
     * login to server
     * @param loginRequest {@link LoginRequest}
     * @return {@link LoginResponse}<br/>
     * {@link LoginResponse#getResult()}the result<br/>
     * "OK":login sussess <br/>
     * "SessionExpired:login session is expired <br/>
     * @throws IOException
     * @throws XmlPullParserException
     */
    public LoginResponse getUserLoginRes(LoginRequest loginRequest) throws IOException, XmlPullParserException {
        LoginResponse res = new LoginResponse();
        SoapObject rpc = new SoapObject(mNameSpace, "userLoginReq");
        rpc.addProperty("Account", loginRequest.getAccount());
        rpc.addProperty("PwdType", loginRequest.getPwdType());
        rpc.addProperty("Password", loginRequest.getPassword());
        rpc.addProperty("Version", loginRequest.getVersion());
        SoapObject rpcChild = new SoapObject(mNameSpace, "MCUDev");
        rpcChild.addProperty("UUID",loginRequest.getIEMI());
        rpcChild.addProperty("Model",loginRequest.getPhoneModel());
        rpcChild.addProperty("NetworkOperator",loginRequest.getNetworkOperator());
        rpcChild.addProperty("NetType","Wifi");
        rpcChild.addProperty("Type", "CellPhone");
        rpcChild.addProperty("OSType", "Android");
        rpcChild.addProperty("OSVersion",loginRequest.getOsVersion());
        rpcChild.addProperty("Manufactory",loginRequest.getManuFactory());
        rpcChild.addProperty("IEMI",loginRequest.getIEMI());
        rpc.addProperty("MCUDev",rpcChild);
        SoapObject object = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/userLogin");
        res.setResult(object.getProperty("result").toString());
        if (object.getProperty("result").toString().equalsIgnoreCase("OK")){
            res.setLoginSession(object.getProperty("LoginSession").toString());
            res.setNodeList(AnalyzingDoNetOutput.analyzing(object.getProperty("NodeList").toString()));
            res.setUsername(object.getProperty("Username").toString());
            res.setAccount(object.getProperty("Account").toString());
        }
        return res;
    }

    /**
     * change device name
     * @param req
     * @return
     * @throws IOException
     * @throws XmlPullParserException
     */
    public UpdateChannelNameRes getUpdateChannelNameRes(UpdateChannelNameReq req) throws IOException, XmlPullParserException {
        UpdateChannelNameRes res = new UpdateChannelNameRes();
        SoapObject rpc = new SoapObject(mNameSpace, "updateChannelNameReq");
        rpc.addProperty("Account", req.getAccount());
        rpc.addProperty("LoginSession", req.getLoginSession());
        rpc.addProperty("DevID", req.getDevID());
        rpc.addProperty("ChannelNo", 0);
        rpc.addProperty("ChannelName", req.getChannelName());
        SoapObject object = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/updateChannelName");
        Object result = object.getProperty("result");
        res.setResult(result.toString());
        return res;
    }


    public Result getUserLogoutRes(LogoutRequest logoutRequest) throws IOException, XmlPullParserException {
        Result res = new Result();
        SoapObject rpc = new SoapObject(mNameSpace, "userLogoutReq");
        rpc.addProperty("Account", logoutRequest.getAccount());
        rpc.addProperty("LoginSession",logoutRequest.getSession());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/userLogout");
        res.setResult(obj.getProperty("Result").toString());
        return res;
    }

    public Result getAddDeviceResult(AddDeviceReq req) throws IOException, XmlPullParserException {
        Result res = new Result();
        SoapObject rpc = new SoapObject(mNameSpace,"addDeviceReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("DevID",req.getDevId())
                .addProperty("DevKey",req.getDevKey())
                .addProperty("DevName",req.getDevName())
                .addProperty("Forcible",req.isForcible());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/addDevice");
        res.setResult(obj.getProperty("Result").toString());
        return res;
    }

    /**
     * delete device
     * @param req
     * @return
     * @throws IOException
     * @throws XmlPullParserException
     */
    public NullifyDeviceRes getNullifyDeviceRes(NullifyDeviceReq req) throws IOException, XmlPullParserException {
        NullifyDeviceRes res = new NullifyDeviceRes();
        SoapObject rpc = new SoapObject(mNameSpace, "nullifyDeviceReq");
        rpc.addProperty("Account", req.getAccount());
        rpc.addProperty("LoginSession", req.getLoginSession());
        rpc.addProperty("DevID", req.getDevID());
        rpc.addProperty("DevKey", req.getDevKey());
        SoapObject object = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/nullifyDevice");
        Object result = object.getProperty("result");
        res.setResult(result.toString());
        return res;
    }

    /**
     * ptz control
     * @param req {@link PtzControlReq}
     * @return
     * @throws IOException
     * @throws XmlPullParserException
     */
    public PtzControlRes GetPtzControlRes(PtzControlReq req) throws IOException, XmlPullParserException {
        PtzControlRes res = new PtzControlRes();
        SoapObject rpc = new SoapObject(mNameSpace, "ptzControlReq");
        rpc.addProperty("Account", req.getAccount());
        rpc.addProperty("LoginSession", req.getLoginSession());
        rpc.addProperty("DevID", req.getDevID());
        rpc.addProperty("ChannelNo", req.getChannelNo());
        rpc.addProperty("PtzDirection", req.getPtzDirection());
        SoapObject object = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/ptzControl");
        Object result = object.getProperty("result");
        res.setResult(result.toString());
        return res;
    }

    public Result getLensControl(GetLensControlReq req) throws IOException, XmlPullParserException {
        Result res = new Result();
        SoapObject rpc = new SoapObject(mNameSpace,"lensControlReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("DevID",req.getDevID())
                .addProperty("ChannelNo",req.getChannelNo())
                .addProperty("PtzLens",req.getPtzLens());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/lensControl");
        res.setResult(obj.getProperty("Result").toString());
        return res;
    }

    public Result getPresetControl(PresetControlReq req) throws IOException, XmlPullParserException {
        SoapObject rpc = new SoapObject(mNameSpace,"presetControlReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("DevID",req.getDevId())
                .addProperty("ChannelNo",req.getChannelNo())
                .addProperty("PtzPreset",req.getPtzPreset())
                .addProperty("PresetNo",req.getPresetNo());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/lensControl");
        return new Result(obj.getProperty("Result").toString());
    }

    public GetCodingParamRes getCodingParam(GetCodingParamReq req) throws IOException, XmlPullParserException {
        GetCodingParamRes res = new GetCodingParamRes();
        SoapObject rpc = new SoapObject(mNameSpace,"getCodingParamReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("DevID",req.getDevId())
                .addProperty("ChannelNo",req.getChannelNo())
                .addProperty("StreamType",req.getStreamType());

        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/getCodingParam");
        String result = obj.getProperty("Result").toString();
        if (result.equalsIgnoreCase("OK")){
            try{res.setFrameSize( obj.getProperty("FrameSize").toString());}catch (Exception e){}
            try{res.setFrameRate(Integer.valueOf(obj.getProperty("FrameRate").toString()));}catch (Exception e){}
            try{res.setRateType(obj.getProperty("RateType").toString());}catch (Exception e){}
            try{res.setBitRate(Integer.valueOf(obj.getProperty("BitRate").toString()));}catch (Exception e){}
            try{res.setImageQuality(Integer.valueOf(obj.getProperty("ImageQuality").toString()));}catch (Exception e){}
            try{res.setAudioInput(Boolean.valueOf(obj.getProperty("AudioInput").toString()));}catch (Exception e){}
        }
        res.setResult(result);
        return res;
    }

    public Result setCodingParam(SetCodingParamReq req) throws IOException, XmlPullParserException {
        SoapObject rpc = new SoapObject(mNameSpace,"setCodingParamReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("DevID",req.getDevId())
                .addProperty("ChannelNo",req.getChannelNo())
                .addProperty("StreamType",req.getStreamType());
        if (req.getFrameSize()!=null)rpc.addProperty("FrameSize",req.getFrameSize());
        if (req.getFrameRate()>0)rpc.addProperty("FrameRate",req.getFrameRate());
        if (req.getRateType()!=null)rpc.addProperty("RateType",req.getRateType());
        if (req.getBitRate()>0)rpc.addProperty("BitRate",req.getBitRate());
        if (req.getImageQuality()>0)rpc.addProperty("ImageQuality",req.getImageQuality());
        if (req.isAudioInput()!=null)rpc.addProperty("AudioInput",req.isAudioInput());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/setCodingParam");
        return new Result(obj.getProperty("Result").toString());
    }

    public Result reboot(RebootReq req) throws IOException, XmlPullParserException {
        SoapObject rpc = new SoapObject(mNameSpace,"rebootReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("DevID",req.getDevID());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/reboot");
        return new Result(obj.getProperty("Result").toString());
    }

    /**
     * get device version
     * @param req
     * @return
     * @throws IOException
     * @throws XmlPullParserException
     */
    public GetDevVerRes getGetDevVerRes(GetDevVerReq req) throws IOException, XmlPullParserException {
        GetDevVerRes res = new GetDevVerRes();
        SoapObject rpc = new SoapObject(mNameSpace, "getDevVerReq");
        rpc.addProperty("Account", req.getAccount());
        rpc.addProperty("LoginSession", req.getLoginSession());
        rpc.addProperty("DevID", req.getDevID());
        SoapObject object = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/getDevVer");
        Object result = object.getProperty("result");
        res.setResult(result.toString());
        Object CurDevVer = object.getProperty("CurDevVer");
        res.setCurDevVer(CurDevVer.toString());
        Log.e("getGetDevVerRes", "CurDevVer = " + CurDevVer.toString());
        Object NewDevVer = object.getProperty("NewDevVer");
        res.setNewDevVer(NewDevVer.toString());
        return res;
    }

    public Result upgradeDevVer(UpgradeDevVerReq req) throws IOException, XmlPullParserException {
        SoapObject rpc = new SoapObject(mNameSpace,"upgradeDevVerReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("DevID",req.getDevID());
        if (req.getNewDevVer()!=null)rpc.addProperty("NewDevVer",req.getNewDevVer());
        if (req.getFtpAddress()!=null)rpc.addProperty("FTPAddress",req.getFtpAddress());
        if (req.getFtpPort()>0)rpc.addProperty("FTPPort",req.getFtpPort());
        if (req.getAccount()!=null)rpc.addProperty("FTPAccount",req.getAccount());
        if (req.getFtpPassword()!=null)rpc.addProperty("FTPPassword",req.getFtpPassword());
        if (req.getFtpFileName()!=null)rpc.addProperty("FTPFileName",req.getFtpFileName());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/upgradeDevVer");
        return new Result(obj.getProperty("Result").toString());
    }

    public GetVideoParamRes getVideoParam(Request req) throws IOException, XmlPullParserException {
        GetVideoParamRes res = new GetVideoParamRes();
        SoapObject rpc = new SoapObject(mNameSpace,"getVideoParamReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("DevID",req.getDevID())
                .addProperty("ChannelNo",req.getChannelNo());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/getVideoParam");
        if (obj.getProperty("Result").toString().equalsIgnoreCase("ok")){
            try{res.setVideoStandard(obj.getProperty("VideoStandard").toString());}catch (Exception e){}
            try{res.setRotationDegree(Integer.valueOf(obj.getProperty("RotationDegree").toString()));}catch (Exception e){}
            try{res.setBrightness(Integer.valueOf(obj.getProperty("Brightness").toString()));}catch (Exception e){}
            try{res.setContrast(Integer.valueOf(obj.getProperty("Contrast").toString()));}catch (Exception e){}
            try{res.setSaturation(Integer.valueOf(obj.getProperty("Saturation").toString()));}catch (Exception e){}
            try{res.setHue(Integer.valueOf(obj.getProperty("Hue").toString()));}catch (Exception e){}
            try{res.setInfrared(Boolean.valueOf(obj.getProperty("Infrared").toString()) );}catch (Exception e){}
        }
        res.setResult(obj.getProperty("Result").toString());
        return res;
    }

    public Result setVideoParam(SetVideoParamReq req) throws IOException, XmlPullParserException {
        SoapObject rpc = new SoapObject(mNameSpace,"setVideoParamReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("DevID",req.getDevId())
                .addProperty("ChannelNo",req.getChannelNo());
        if (req.getVideoStandard()!=null)rpc.addProperty("VideoStandard",req.getVideoStandard());
        if (req.getRotationDegree()>0)rpc.addProperty("RotationDegree",req.getRotationDegree());
        if (req.getBrightness()>0)rpc.addProperty("Brightness",req.getBrightness());
        if (req.getContrast()>0)rpc.addProperty("Contrast",req.getContrast());
        if (req.getSaturation()>0)rpc.addProperty("Saturation",req.getSaturation());
        if (req.getHue()>0)rpc.addProperty("Hue",req.getHue());
        if (req.getInfrared()!=null)rpc.addProperty("Infrared",req.getInfrared());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/setVideoParam");
        return new Result(obj.getProperty("Result").toString());
    }

    public GetOSDParamRes getOSDParam(Request req) throws IOException, XmlPullParserException {
        GetOSDParamRes res = new GetOSDParamRes();
        SoapObject rpc = new SoapObject(mNameSpace,"getOSDParamReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("DevID",req.getDevID())
                .addProperty("ChannelNo",req.getChannelNo());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/getOSDParam");
        if (obj.getProperty("Result").toString().equalsIgnoreCase("OK")){
            try{res.setEnable(Boolean.valueOf(obj.getProperty("Enable").toString()));}catch (Exception e){}
            try{res.setTimestampEnabled(Boolean.valueOf(obj.getProperty("TimestampEnabled").toString()));}catch (Exception e){}
            try{res.setDateTimeFormat(obj.getProperty("DateTimeFormat").toString());}catch (Exception e){}
            try{res.setDisplayText(obj.getProperty("DisplayText").toString());}catch (Exception e){}
            try{res.setFontSize(Integer.valueOf(obj.getProperty("FontSize").toString()));}catch (Exception e){}
            try{res.setFontColor(Integer.valueOf(obj.getProperty("FontColor").toString()));}catch (Exception e){}
            try{res.setTextPositionX(Integer.valueOf(obj.getProperty("TextPositionX").toString()));}catch (Exception e){}
            try{res.setTextPositionY(Integer.valueOf(obj.getProperty("TextPositionY").toString()));}catch (Exception e){}
            try{res.setTimestampPositionX(Integer.valueOf(obj.getProperty("TimestampPositionX").toString()));}catch (Exception e){}
            try{res.setTimestampPositionY(Integer.valueOf(obj.getProperty("TimestampPositionY").toString()));}catch (Exception e){}
        }
        res.setResult(obj.getProperty("Result").toString());
        return res;
    }

    public Result SetOSDParam(SetOSDParamReq req) throws IOException, XmlPullParserException {
        SoapObject rpc = new SoapObject(mNameSpace,"setOSDParamReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("DevID",req.getDevId())
                .addProperty("ChannelNo",req.getChannelNo());
        if (req.getEnable()!=null)rpc.addProperty("Enabled",req.getEnable());
        if (req.getTimestanpEnable()!=null)rpc.addProperty("TimestampEnabled",req.getTimestanpEnable());
        if (req.getDateTimeFormat()!=null)rpc.addProperty("DateTimeFormat",req.getDateTimeFormat());
        if (req.getDisplayText()!=null)rpc.addProperty("DisplayText",req.getDisplayText());
        if (req.getFontSize()>0)rpc.addProperty("FontSize",req.getFontSize());
        if (req.getFontColor()>0)rpc.addProperty("FontColor",req.getFontColor());
        if (req.getTextPositionX()>0)rpc.addProperty("TextPositionX",req.getTextPositionX());
        if (req.getTextPositionY()>0)rpc.addProperty("TextPositionY",req.getTextPositionY());
        if (req.getTimestampPositionX()>0)rpc.addProperty("TimestampPositionX",req.getTimestampPositionX());
        if (req.getTimestampPositionY()>0)rpc.addProperty("TimestampPositionY",req.getTimestampPositionY());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/setOSDParam");
        return new Result(obj.getProperty("Result").toString());
    }

    public GetTimeRes getTime(GetTimeReq req) throws IOException, XmlPullParserException {
        GetTimeRes res = new GetTimeRes();
        SoapObject rpc = new SoapObject(mNameSpace,"getTimeReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("DevID",req.getDevId());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/getTime");
        if (obj.getProperty("Result").toString().equalsIgnoreCase("ok")){
            try{res.setTime(obj.getProperty("Time").toString());}catch (Exception e){}
            try{res.setTimeZone(obj.getProperty("TimeZone").toString());}catch (Exception e){}
            try{res.setPOSIXTimeZone(obj.getProperty("POSIXTimeZone").toString());}catch (Exception e){}
        }
        res.setResult(obj.getProperty("Result").toString());
        return res;
    }

    public Result setTime(SetTimeReq req) throws IOException, XmlPullParserException {
        SoapObject rpc = new SoapObject(mNameSpace,"setTimeReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("DevID",req.getDevId());
        if (req.getTime()!=null)rpc.addProperty("Time",req.getTime());
        if (req.getTimeZone()!=null)rpc.addProperty("TimeZone",req.getTimeZone());
        if (req.getPOSIXTimeZone()!=null)rpc.addProperty("POSIXTimeZone",req.getPOSIXTimeZone());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/setTime");
        return new Result(obj.getProperty("Result").toString());
    }

    public GetVMDParamRes getVMDParam(Request req) throws IOException, XmlPullParserException {
        GetVMDParamRes res = new GetVMDParamRes();
        SoapObject rpc = new SoapObject(mNameSpace,"getVMDParamReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("DevID",req.getDevID())
                .addProperty("ChannelNo",req.getChannelNo());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/getVMDParam");
        if (obj.getProperty("Result").toString().equalsIgnoreCase("OK")){
            try{res.setEnable(Boolean.valueOf(obj.getProperty("Enabled").toString()));}catch (Exception e){}
            try{res.setSensitivity(Integer.valueOf(obj.getProperty("Sensitivity").toString()));}catch (Exception e){}
            try{res.setStartTriggerTime(Integer.valueOf(obj.getProperty("StartTriggerTime").toString()));}catch (Exception e){}
            try{res.setEndTriggerTime(Integer.valueOf(obj.getProperty("EndTriggerTime").toString()));}catch (Exception e){}
            try{res.setRowGranularity(Integer.valueOf(obj.getProperty("RowGranularity").toString()));}catch (Exception e){}
            try{res.setColumnGranularity(Integer.valueOf(obj.getProperty("ColumnGranularity").toString()));}catch (Exception e){}
            try{

                SoapObject childGrid = (SoapObject) obj.getProperty("VMDGrid");
                String row = childGrid.getProperty("Row").toString();
                res.setVmdGrid(new GetVMDParamRes.VMDGrid(row));
            }catch (Exception e){}
            try{
                SoapObject childWorkSheet = (SoapObject) obj.getProperty("WorkSheet");
                Boolean enable = Boolean.valueOf(childWorkSheet.getProperty("Enabled").toString());
                String bitString = childWorkSheet.getProperty("BitString").toString();
                res.setWorkSheet(new GetVMDParamRes.WorkSheet(enable,bitString));
            }catch (Exception e){}
        }
        res.setResult(obj.getProperty("Result").toString());
        return res;
    }

    public Result setVMDParam(SetVMDParamReq req) throws IOException, XmlPullParserException {
        SoapObject rpc = new SoapObject(mNameSpace,"setVMDParamReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("DevID",req.getDevId())
                .addProperty("ChannelNo",req.getChannelId());
        if (req.getEnable()!=null)rpc.addProperty("Enabled",req.getEnable());
        if (req.getSensitivity()>0)rpc.addProperty("Sensitivity",req.getSensitivity());
        if (req.getStartTriggerTime()>0)rpc.addProperty("StartTriggerTime",req.getStartTriggerTime());
        if (req.getEndTriggerTime()>0)rpc.addProperty("EndTriggerTime",req.getEndTriggerTime());
        if (req.getVmdGrid()!=null){
            SoapObject vmdGrid = new SoapObject(mNameSpace,"VMDGrid");
            vmdGrid.addProperty("Row",req.getVmdGrid().getRow());
            rpc.addProperty("VMDGrid",vmdGrid);
        }
        if (req.getWorkSheet()!=null){
            SoapObject workSheet = new SoapObject(mNameSpace,"WorkSheet");
            workSheet.addProperty("Enabled",req.getWorkSheet().getEnable())
                .addProperty("BitString",req.getWorkSheet().getBitString());
            rpc.addProperty("WorkSheet",workSheet);
        }
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/setVMDParam");
        return new Result(obj.getProperty("Result").toString());
    }

    public GetPrivacyMaskParamRes getPrivacyMaskParam(Request req) throws IOException, XmlPullParserException {
        GetPrivacyMaskParamRes res = new GetPrivacyMaskParamRes();
        SoapObject rpc = new SoapObject(mNameSpace,"getPrivacyMaskParamReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("DevID",req.getDevID())
                .addProperty("ChannelNo",req.getChannelNo());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/getPrivacyMaskParam");
        if (obj.getProperty("Result").toString().equalsIgnoreCase("ok")){
            try{res.setEnable(Boolean.valueOf(obj.getProperty("Enabled").toString()));}catch (Exception e){}
            try{res.setHorizontalResolution(Integer.valueOf(obj.getProperty("HorizontalResolution").toString()));}catch (Exception e){}
            try{res.setVerticalResolution(Integer.valueOf(obj.getProperty("VerticalResolution").toString()));}catch (Exception e){}
            try{
                int left = Integer.valueOf(obj.getProperty("Left").toString());
                int top =  Integer.valueOf(obj.getProperty("Top").toString());
                int right = Integer.valueOf(obj.getProperty("Right").toString());
                int bottom = Integer.valueOf(obj.getProperty("Bottom").toString());
                res.setPrivacyMaskRegion(new GetPrivacyMaskParamRes.PrivacyMaskRegion(left,top,right,bottom));
            }catch (Exception e){}
        }
        res.setResult(obj.getProperty("Result").toString());
        return res;
    }

    public Result setPrivacymaskParam(SetPrivacyMaskParamReq req) throws IOException, XmlPullParserException {
        SoapObject rpc = new SoapObject(mNameSpace,"setPrivacyMaskParamReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("DevID",req.getDevID())
                .addProperty("ChannelNo",req.getChannelNo());
        if (req.getEnable()!=null)rpc.addProperty("Enabled",req.getEnable());
        if (req.getPrivacyMaskRegion()!=null){
            SoapObject child = new SoapObject(mNameSpace,"PrivacyMaskRegion");
            child.addProperty("Left",req.getPrivacyMaskRegion().getLeft())
                    .addProperty("Top",req.getPrivacyMaskRegion().getTop())
                    .addProperty("Right",req.getPrivacyMaskRegion().getRight())
                    .addProperty("Bottom",req.getPrivacyMaskRegion().getBottom());
            rpc.addProperty("PrivacyMaskRegion",child);
        }
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/setPrivacyMaskParam");
        return new Result(obj.getProperty("Result").toString());
    }

    public VodSearchRes vodSearch(VodSearchReq req) throws IOException, XmlPullParserException {
        VodSearchRes res = new VodSearchRes();
        SoapObject rpc = new SoapObject(mNameSpace,"vodSearchReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("DevID",req.getDevID())
                .addProperty("ChannelNo",req.getChannelNo())
                .addProperty("StreamType",req.getStreamType())
                .addProperty("StartTime",req.getStartTime())
                .addProperty("EndTime",req.getEndTime())
                .addProperty("PageNo",req.getPageNo())
                .addProperty("PageSize",req.getPageSize());
        if (req.getSearchID()!=null)rpc.addProperty("SearchID",req.getSearchID());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/vodSearch");
        if (obj.getProperty("Result").toString().equalsIgnoreCase("ok")){
            res.setPageNo(Integer.valueOf(obj.getProperty("PageNo").toString()));
            res.setPageCount(Integer.valueOf(obj.getProperty("PageCount").toString()));
            res.setRecordCount(Integer.valueOf(obj.getProperty("RecordCount").toString()));

            SoapObject record = (SoapObject) obj.getProperty("Record");

            int conunt = record.getPropertyCount();

            ArrayList<VodSearchRes.Record> records = new ArrayList<>();
            for (int i=0;i<conunt;i++){
                SoapObject o = (SoapObject) obj.getProperty(i);
                records.add(new VodSearchRes.Record(
                        o.getProperty("StartTime").toString(),
                        o.getProperty("EndTime").toString(),
                        Long.valueOf( o.getProperty("FileSize").toString()),
                        o.getProperty("ContentType").toString(),
                        o.getProperty("Desc").toString()
                ));
            }
            res.setRecord(records);
        }
        res.setResult(obj.getProperty("Result").toString());
        return res;
    }


    /**
     * create account
     * @param req
     * @return the result from server
     * @throws IOException
     * @throws XmlPullParserException
     */
    public CreateAccountRes getCreateAccountRes(CreateAccountReq req) throws IOException, XmlPullParserException {
        CreateAccountRes res = new CreateAccountRes();
        SoapObject rpc = new SoapObject(mNameSpace, "createAccountReq");
        rpc.addProperty("Account", req.getAccount());
        rpc.addProperty("Username", req.getUsername());
        rpc.addProperty("Password", req.getPassword());
        rpc.addProperty("Email", req.getEmail());
        if (req.getMobileTel()!=null)rpc.addProperty("MobileTel",req.getMobileTel());
        rpc.addProperty("SecurityQuestion",req.getSecurityQuestion());
        rpc.addProperty("SecurityAnswer",req.getSecurityAnswer());
        if (req.getCountry()!=null)rpc.addProperty("Country",req.getCountry());
        if (req.getCountryTelCode()!=null)rpc.addProperty("CountryTelCode",req.getCountryTelCode());
        if (req.getiDCard()!=null)rpc.addProperty("IDCard",req.getiDCard());
        if (req.getApplicationID()!=null)rpc.addProperty("ApplicationID",req.getApplicationID());
        //rpc.addProperty("MobileTel", " ");
        SoapObject object = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/createAccount");
        Object result = object.getProperty("result");
        res.setResult(result.toString());
        return res;
    }

    /**
     * change account information
     * @param req
     * @return
     * @throws IOException
     * @throws XmlPullParserException
     */
    public UpdateAccountRes getUpdateAccountRes(UpdateAccountReq req) throws IOException, XmlPullParserException {
        UpdateAccountRes res = new UpdateAccountRes();
        SoapObject rpc = new SoapObject(mNameSpace, "updateAccountReq");
        rpc.addProperty("Account", req.getAccount());
        rpc.addProperty("LoginSession", req.getLoginSession());
        //rpc.addProperty("Username", "");
        rpc.addProperty("MobileTel", req.getMobileTel());
        SoapObject object = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/updateAccount");
        Object result = object.getProperty("result");
        res.setResult(result.toString());
        return res;
    }


    /**
     * changer login password
     * @param req {@link UpdatePasswordReq}
     * @return {@link UpdatePasswordRes} <br/>
     * {@link UpdatePasswordRes#getResult()}<br/>
     * "OK":success;<br/>
     * @throws IOException
     * @throws XmlPullParserException
     */
    public UpdatePasswordRes getUpdatePasswordRes(UpdatePasswordReq req) throws IOException, XmlPullParserException {
        UpdatePasswordRes res = new UpdatePasswordRes();
        SoapObject rpc = new SoapObject(mNameSpace, "updatePasswordReq");
        rpc.addProperty("Account", req.getAccount());
        rpc.addProperty("LoginSession", req.getLoginSession());
        rpc.addProperty("Password", req.getPassword());
        rpc.addProperty("NewPassword", req.getNewPassword());
        SoapObject object = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/updatePassword");
        Object result = object.getProperty("result");
        res.setResult(result.toString());
        return res;
    }

    public QueryDeviceRes queryDevice(Request req) throws IOException, XmlPullParserException {
        QueryDeviceRes res = new QueryDeviceRes();
        SoapObject rpc = new SoapObject(mNameSpace,"queryDeviceReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession());
        if (req.getDevID()!=null)rpc.addProperty("DevID",req.getDevID());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/queryDevice");
        if (obj.getProperty("result").toString().equalsIgnoreCase("ok")){
            SoapObject nodeList = (SoapObject) obj.getProperty("NodeList");
            int count = nodeList.getPropertyCount();
            ArrayList<QueryDeviceRes.NodeList> nodeLists = new ArrayList<>();
            for (int i=0;i<count;i++){
                SoapObject node = (SoapObject) nodeList.getProperty(i);
                nodeLists.add(new QueryDeviceRes.NodeList(
                        node.getProperty("DevID").toString(),
                        Integer.valueOf(node.getProperty("ChannelNo").toString()),
                        node.getProperty("Name").toString(),
                        Boolean.valueOf(node.getProperty("OnLine").toString()),
                        Boolean.valueOf(node.getProperty("PtzFlag").toString()),
                        Integer.valueOf(node.getProperty("SecurityArea").toString()),
                        Integer.valueOf(node.getProperty("EStoreFlag").toString()),
                        node.getProperty("UpnpIP").toString(),
                        Integer.valueOf(node.getProperty("UpnpPort").toString()),
                        node.getProperty("DevVer").toString(),
                        Integer.valueOf(node.getProperty("CurVideoNum").toString()),
                        node.getProperty("LastUpdated").toString(),
                        Integer.valueOf(node.getProperty("SMSSubscribedFlag").toString()),
                        Integer.valueOf(node.getProperty("EMailSubscribedFlag").toString()),
                        Integer.valueOf(node.getProperty("SharingFlag").toString()),
                        Integer.valueOf(node.getProperty("ApplePushSubscribedFlag").toString()),
                        Integer.valueOf(node.getProperty("AndroidPushSubscribedFlag").toString()),
                        Integer.valueOf(node.getProperty("InfraredFlag").toString()),
                        Integer.valueOf(node.getProperty("WirelessFlag").toString())
                ));
            }
            res.setNodeLists(nodeLists);
            try{
                SoapObject network = (SoapObject) obj.getProperty("WirelessNetwork");
                res.setWirelessNetwork(new QueryDeviceRes.WirelessNetwork(
                        network.getProperty("WirelessType").toString(),
                        network.getProperty("SSID").toString(),
                        Integer.valueOf(network.getProperty("Intensity").toString())
                ));
            }catch (Exception e){}
        }
        res.setResult(obj.getProperty("Result").toString());
        return res;
    }

    public GetAccountRes getAccount(Request req) throws IOException, XmlPullParserException {
        GetAccountRes res = new GetAccountRes();
        SoapObject rpc = new SoapObject(mNameSpace,"getAccountReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/getAccount");
        if (obj.getProperty("result").toString().equalsIgnoreCase("Ok")){
            res.setUserName(obj.getProperty("Username").toString());
            res.setEmail(obj.getProperty("Email").toString());
            res.setMobileTel(obj.getProperty("MobileTel").toString());
            res.setCountry(obj.getProperty("MobileTel").toString());
            res.setCountryTelCode(obj.getProperty("CountryTelCode").toString());
            try{res.setIDCard(obj.getProperty("IDCard").toString());}catch (Exception e){}
        }
        res.setResult(obj.getProperty("result").toString());
        return res;
    }

    public GetBackPasswordRes getBackPassword(GetBackPasswordReq req) throws IOException, XmlPullParserException {
        GetBackPasswordRes res = new GetBackPasswordRes();
        SoapObject rpc = new SoapObject(mNameSpace,"getBackPasswordReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("GetBackType",req.getGetBackType());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/getBackPassword");
        if (obj.getProperty("Result").toString().equalsIgnoreCase("ok")){
            res.setAddress(obj.getProperty("Address").toString());
        }
        res.setResult(obj.getProperty("Result").toString());
        return res;
    }

    public GetAuxiliaryRes getAuxiliary(GetAuxiliaryReq req) throws IOException, XmlPullParserException {
        GetAuxiliaryRes res = new GetAuxiliaryRes();
        SoapObject rpc = new SoapObject(mNameSpace,"getAuxiliaryReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("DevID",req.getDevID())
                .addProperty("Auxiliary",req.getAuxiliary());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/getAuxiliary");
        if (obj.getProperty("Result").toString().equalsIgnoreCase("ok")){
            res.setAuxiliaryState(obj.getProperty("AuxiliaryState").toString());
        }
        res.setResult(obj.getProperty("Result").toString());
        return res;
    }

    public Result setAuxiliary(SetAuxiliaryReq req) throws IOException, XmlPullParserException {
        SoapObject rpc = new SoapObject(mNameSpace,"setAuxiliaryReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("DevID",req.getDevID())
                .addProperty("Auxiliary",req.getAuxiliary())
                .addProperty("AuxiliaryState",req.getAuxiliaryState());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/setAuxiliary");
        return new Result(obj.getProperty("Result").toString());
    }

    public InviteRes invite(InviteReq req) throws IOException, XmlPullParserException {
        InviteRes res = new InviteRes();
        SoapObject rpc = new SoapObject(mNameSpace,"inviteReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("DevID",req.getDevID())
                .addProperty("ChannelNo",req.getChannelNo())
                .addProperty("StreamType",req.getStreamType())
                .addProperty("DialogID",req.getDialogId())
                .addProperty("SDPMessage",req.getSdpMessage());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/invite");
        if (obj.getProperty("Result").toString().equalsIgnoreCase("ok")){
            res.setDialogId(obj.getProperty("DialogID").toString());
            res.setSdpMessage(obj.getProperty("SDPMessage").toString());
        }
        res.setResult(obj.getProperty("Result").toString());
        return res;
    }

    public Result bye(ByeReq req) throws IOException, XmlPullParserException {
        SoapObject rpc = new SoapObject(mNameSpace,"byeReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("DevID",req.getDevID())
                .addProperty("ChannelNo",req.getChannelNo())
                .addProperty("StreamType",req.getStreamType())
                .addProperty("DialogID",req.getDialogID());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/bye");
        return new Result(obj.getProperty("Result").toString());
    }

    /**
     * get net and turn server address;the net or turn server is belong to the server that we login
     * @param req {@link GetNATServerReq}
     * @return {@link GetNATServerRes}<br/>
     *
     * @throws IOException
     * @throws XmlPullParserException
     */
    public GetNATServerRes getGetNATServerRes(GetNATServerReq req) throws IOException, XmlPullParserException {
        GetNATServerRes res = new GetNATServerRes();
        SoapObject rpc = new SoapObject(mNameSpace, "getNATServerReq");
        rpc.addProperty("Account", req.getAccount());
        rpc.addProperty("LoginSession", req.getLoginSession());
        SoapObject object = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/getNATServer");
        Object result = object.getProperty("result");
        res.setResult(result.toString());
        SoapObject STUNServerList = (SoapObject) object.getProperty("STUNServerList");
        SoapObject STUNServer = (SoapObject) STUNServerList.getProperty("STUNServer");
        Object STUNIPv4Address = STUNServer.getProperty("IPv4Address");
        res.setSTUNServerAddress(STUNIPv4Address.toString());
        Object STUNPort = STUNServer.getProperty("Port");
        res.setSTUNServerPort(Integer.valueOf(STUNPort.toString()));

        SoapObject TURNServerList = (SoapObject)object.getProperty("TURNServerList");
        SoapObject TURNServer = (SoapObject) TURNServerList.getProperty("TURNServer");
        Object TURNIPv4Address = TURNServer.getProperty("IPv4Address");
        res.setTURNServerAddress(TURNIPv4Address.toString());
        Object TURNPort = TURNServer.getProperty("Port");
        res.setTURNServerPort(Integer.valueOf(TURNPort.toString()));
        Object userName = TURNServer.getProperty("Username");
        res.setTURNServerUserName(userName.toString());
        Object password = TURNServer.getProperty("Password");
        res.setTURNServerPassword(password.toString());
        return res;
    }

    public GetNATRes getGetNATServerRes(Request req) throws IOException, XmlPullParserException {
        GetNATRes res = new GetNATRes();
        SoapObject rpc = new SoapObject(mNameSpace,"getNATServerReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/getNATServer");
        if (obj.getProperty("Result").toString().equalsIgnoreCase("ok")){
            SoapObject stunList = (SoapObject) obj.getProperty("STUNServerList");
            int countS = stunList.getPropertyCount();
            ArrayList<GetNATRes.STUNServer> stunServers = new ArrayList<>();
            for (int i=0;i<countS;i++){
                SoapObject stun = (SoapObject) stunList.getProperty(i);
                GetNATRes.STUNServer server = new GetNATRes.STUNServer();
                server.setIPv4Address(stun.getProperty("IPv4Address").toString());
                try{server.setIPv6Address(stun.getProperty("IPv6Address").toString());}catch (Exception e){}
                server.setPort(Integer.valueOf(stun.getProperty("Port").toString()));
                stunServers.add(server);
            }
            res.setStunServers(stunServers);

            SoapObject turnList = (SoapObject) obj.getProperty("TURNServerList");
            int countT = turnList.getPropertyCount();
            ArrayList<GetNATRes.TURNServer> turnServers = new ArrayList<>();
            for (int i=0;i<countT;i++){
                SoapObject turn = (SoapObject) turnList.getProperty(i);
                GetNATRes.TURNServer server = new GetNATRes.TURNServer();
                server.setIPv4Address(turn.getProperty("IPv4Address").toString());
                try{server.setIPv6Address(turn.getProperty("IPv6Address").toString());}catch (Exception e){}
                server.setPort(Integer.valueOf(turn.getProperty("Port").toString()));
                try{server.setUsername(turn.getProperty("Username").toString());}catch (Exception e){}
                try{server.setPassword(turn.getProperty("Password").toString());}catch (Exception e){}
            }
            res.setTurnServers(turnServers);
        }
        res.setResult(obj.getProperty("Result").toString());
        return res;
    }

    public Result subscribeEmail(SubscribeBaseReq req) throws IOException, XmlPullParserException {
        SoapObject rpc = new SoapObject(mNameSpace,"subscribeEMailReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("SubscribedFlag",req.getSubscribedFlag())
                .addProperty("DevID",req.getDevID())
                .addProperty("ChannelNo",req.getChannelNo());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/subscribeEMail");
        return new Result(obj.getProperty("Result").toString());
    }

    public Result subscribeSMS(SubscribeBaseReq req) throws IOException, XmlPullParserException {
        SoapObject rpc = new SoapObject(mNameSpace,"subscribeSMSReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("SubscribedFlag",req.getSubscribedFlag())
                .addProperty("DevID",req.getDevID())
                .addProperty("ChannelNo",req.getChannelNo());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/subscribeSMS");
        return new Result(obj.getProperty("Result").toString());
    }

    public Result notifyNATResult(NotifyNATResultReq req) throws IOException, XmlPullParserException {
        SoapObject rpc = new SoapObject(mNameSpace,"notifyNATResultReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("DialogID",req.getDialogID())
                .addProperty("NATType",req.getNATType());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/notifyNATResult");
        return new Result(obj.getProperty("Result").toString());
    }

    public Result inviteKeepAlive(InviteKeepAliveReq req) throws IOException, XmlPullParserException {
        SoapObject rpc = new SoapObject(mNameSpace,"inviteKeepAliveReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("DialogID",req.getDialogID());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/inviteKeepAlive");
        return new Result(obj.getProperty("Result").toString());
    }

    public QueryPUOnOffLogRes queryPUOnOffLog(QueryPUOnOffLogReq req) throws IOException, XmlPullParserException {
        QueryPUOnOffLogRes res = new QueryPUOnOffLogRes();
        SoapObject rpc = new SoapObject(mNameSpace,"queryPUOnOffLogReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession());
        if (req.getDevID()!=null) rpc.addProperty("DevID",req.getDevID());
        rpc.addProperty("StartTime",req.getStartTime());
        rpc.addProperty("EndTime",req.getEndTime());
        if (req.getPageNo()>0)rpc.addProperty("PageNo",req.getPageNo());
        if (req.getSearchID()!=null)rpc.addProperty("SearchID",req.getSearchID());
        if (req.getPageSize()>0)rpc.addProperty("PageSize",req.getPageSize());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/queryPUOnOffLog");
        if (obj.getProperty("Result").toString().equalsIgnoreCase("ok")){
            res.setPageNo(Integer.valueOf(obj.getProperty("PageNo").toString()));
            res.setPageCount(Integer.valueOf(obj.getProperty("PageCount").toString()));
            res.setRecordCount(Integer.valueOf(obj.getProperty("RecordCount").toString()));
            SoapObject o = (SoapObject) obj.getProperty("Log");
            QueryPUOnOffLogRes.Log log = new QueryPUOnOffLogRes.Log();
            log.setDevID(o.getProperty("DevID").toString());
            log.setTime(o.getProperty("Time").toString());
            log.setOffTime(o.getProperty("OffTime").toString());
            try{log.setOffReason(o.getProperty("OffReason").toString());}catch (Exception e){}
            res.setLog(log);
        }
        res.setResult(obj.getProperty("Result").toString());
        return res;
    }

    public QueryPUEventLogRes queryPUEventLog(QueryPUEventLogReq req) throws IOException, XmlPullParserException {
        QueryPUEventLogRes res = new QueryPUEventLogRes();
        SoapObject rpc = new SoapObject(mNameSpace,"queryPUEventLogReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession());
        if (req.getDevID()!=null)rpc.addProperty("DevID",req.getDevID());
        if (req.getEventType()!=null)rpc.addProperty("EventType",req.getEventType());
        if (req.getEventState()!=null)rpc.addProperty("EventState",req.getEventState());
        rpc.addProperty("StartTime",req.getStartTime());
        rpc.addProperty("EndTime",req.getEndTime());
        if (req.getPageNo()>0)rpc.addProperty("PageNo",req.getPageNo());
        if (req.getSearchID()!=null)rpc.addProperty("SearchID",req.getSearchID());
        if (req.getPageSize()>0)rpc.addProperty("PageSize",req.getPageSize());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/queryPUEventLog");
        if (obj.getProperty("Result").toString().equalsIgnoreCase("ok")){
            res.setPageNo(Integer.valueOf(obj.getProperty("PageNo").toString()));
            res.setPageCount(Integer.valueOf(obj.getProperty("PageCount").toString()));
            res.setRecordCount(Integer.valueOf(obj.getProperty("RecordCount").toString()));
            QueryPUEventLogRes.Log log = new QueryPUEventLogRes.Log();
            SoapObject o = (SoapObject) obj.getProperty("Log");
            log.setDevID(o.getProperty("DevID").toString());
            log.setChannelNo(Integer.valueOf(o.getProperty("ChannelNo").toString()));
            log.setEventType(o.getProperty("EventType").toString());
            log.setEventState(o.getProperty("EventState").toString());
            try{log.setEventDesc(o.getProperty("EventDesc").toString());}catch (Exception e){}
            log.setTime(o.getProperty("Time").toString());
            res.setLog(log);
        }
        res.setResult(obj.getProperty("Result").toString());
        return res;
    }

    public Result updateAndroidToken(UpdateAndroidTokenReq req) throws IOException, XmlPullParserException {
        SoapObject rpc = new SoapObject(mNameSpace,"updateAndroidTokenReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("UDID",req.getUDID())
                .addProperty("APNs",req.getAPNs())
                .addProperty("DeviceToken",req.getDeviceToken())
                .addProperty("AndroidOS",req.getAndroidOS());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/updateAndroidToken");
        return new Result(obj.getProperty("Result").toString());
    }

    public QueryDeviceStatusRes queryDeviceStatus(QueryDeviceStatusReq req) throws IOException, XmlPullParserException {
        QueryDeviceStatusRes res = new QueryDeviceStatusRes();
        SoapObject rpc = new SoapObject(mNameSpace,"queryDeviceStatusReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession());
        if (req.getPageNo()>0)rpc.addProperty("PageNo",req.getPageNo());
        if (req.getSearchID()!=null)rpc.addProperty("SearchID",req.getSearchID());
        if (req.getPageSize()>0)rpc.addProperty("PageSize",req.getPageSize());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/queryDeviceStatus");
        if (obj.getProperty("Result").toString().equalsIgnoreCase("ok")){
            res.setPageNo(Integer.valueOf(obj.getProperty("PageNo").toString()));
            res.setPageCount(Integer.valueOf(obj.getProperty("PageCount").toString()));
            res.setRecordCount(Integer.valueOf(obj.getProperty("RecordCount").toString()));
            SoapObject nodeList = (SoapObject) obj.getProperty("NodeList");
            int count = nodeList.getPropertyCount();
            ArrayList<QueryDeviceStatusRes.NodeList> nodeLists = new ArrayList<>();
            for (int i=0;i<count;i++){
                SoapObject o = (SoapObject) nodeList.getProperty(i);
                QueryDeviceStatusRes.NodeList node = new QueryDeviceStatusRes.NodeList();
                node.setDevID(o.getProperty("DevID").toString());
                node.setChannelNo(Integer.valueOf(o.getProperty("ChannelNo").toString()));
                node.setName(o.getProperty("Name").toString());
                node.setOnLine(Boolean.valueOf(o.getProperty("OnLine").toString()));
                node.setPtzFlag(Boolean.valueOf(o.getProperty("PtzFlag").toString()));
                node.setSecurityArea(Integer.valueOf(o.getProperty("SecurityArea").toString()));
                node.seteStoreFlag(Integer.valueOf(o.getProperty("EStoreFlag").toString()));
                node.setUpnpIP(o.getProperty("UpnpIP").toString());
                node.setUpnpPort(Integer.valueOf(o.getProperty("UpnpPort").toString()));
                node.setDevVer(o.getProperty("DevVer").toString());
                node.setCurVideoNum(Integer.valueOf(o.getProperty("CurVideoNum").toString()));
                node.setLastUpdated(o.getProperty("LastUpdated").toString());
                node.setSmsSubscribedFlag(Integer.valueOf(o.getProperty("SMSSubscribedFlag").toString()));
                node.setEmailSubscribedFlag(Integer.valueOf(o.getProperty("EMailSubscribedFlag").toString()));
                node.setSharingFlag(Integer.valueOf(o.getProperty("SharingFlag").toString()));
                node.setApplePushSubscribedFlag(Integer.valueOf(o.getProperty("ApplePushSubscribedFlag").toString()));
                node.setAndroidPushSubscribedFlag(Integer.valueOf(o.getProperty("AndroidPushSubscribedFlag").toString()));
                node.setInfraredFlag(Integer.valueOf(o.getProperty("InfraredFlag").toString()));
                node.setWirelessFlag(Integer.valueOf(o.getProperty("WirelessFlag").toString()));
                nodeLists.add(node);
            }
            res.setNodeLists(nodeLists);

            try{
                QueryDeviceStatusRes.WirelessNetwork network = new QueryDeviceStatusRes.WirelessNetwork();
                SoapObject o = (SoapObject) obj.getProperty("WirelessNetwork");
                network.setWirelessType(o.getProperty("WirelessType").toString());
                network.setSsid(o.getProperty("SSID").toString());
                network.setIntensity(Integer.valueOf(o.getProperty("Intensity").toString()));
                res.setWirelessNetwork(network);
            }catch (Exception e){}

            try{res.setDevType(Integer.valueOf(obj.getProperty("DevType").toString()));}catch (Exception e){}
            try{res.setModel(obj.getProperty("Model").toString());}catch (Exception e){}
            try{res.setSerialID(obj.getProperty("SerialID").toString());}catch (Exception e){}

        }
        res.setResult(obj.getProperty("Result").toString());
        return res;
    }

    public Result addDeviceSharer(AddDeviceSharerReq req) throws IOException, XmlPullParserException {
        SoapObject rpc = new SoapObject(mNameSpace,"addDeviceSharerReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("DevID",req.getDevID())
                .addProperty("ChannelNo",req.getChannelNo())
                .addProperty("SharerAccount",req.getSharerAccount())
                .addProperty("SharingPriority",req.getSharingPriority())
                .addProperty("SharingName",req.getSharingName());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/addDeviceSharer");
        return new Result(obj.getProperty("Result").toString());
    }

    public Result nullifyDeviceSharer(NullifyDeviceSharerReq req) throws IOException, XmlPullParserException {
        SoapObject rpc = new SoapObject(mNameSpace,"nullifyDeviceSharerReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("DevID",req.getDevID())
                .addProperty("ChannelNo",req.getChannelNo())
                .addProperty("SharerAccount",req.getSharerASccount());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/nullifyDeviceSharer");
        return new Result(obj.getProperty("Result").toString());
    }

    public QueryDeviceSharerRes queryDeviceSharer(Request req) throws IOException, XmlPullParserException {
        QueryDeviceSharerRes res = new QueryDeviceSharerRes();
        SoapObject rpc = new SoapObject(mNameSpace,"queryDeviceSharerReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("DevID",req.getDevID())
                .addProperty("ChannelNo",req.getChannelNo());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/queryDeviceSharer");
        if (obj.getProperty("Result").toString().equalsIgnoreCase("ok")){
            ArrayList<QueryDeviceSharerRes.Sharer> sharers = new ArrayList<>();
            SoapObject sharer = (SoapObject) obj.getProperty("Sharer");
            int count = sharer.getPropertyCount();
            for (int i=0;i<count;i++){
                SoapObject o = (SoapObject) sharer.getProperty(i);
                sharers.add(new QueryDeviceSharerRes.Sharer(
                        o.getProperty("SharerAccount").toString(),
                        Integer.valueOf(o.getProperty("SharingPriority").toString())
                ));
            }
        }
        res.setResult(obj.getProperty("Result").toString());
        return res;
    }

    public QueryDeviceSharingSourceRes queryDeviceSharingSource(Request req) throws IOException, XmlPullParserException {
        QueryDeviceSharingSourceRes res = new QueryDeviceSharingSourceRes();
        SoapObject rpc = new SoapObject(mNameSpace,"queryDeviceSharingSourceReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("DevID",req.getDevID())
                .addProperty("ChannelNo",req.getChannelNo());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/queryDeviceSharingSource");
        if (obj.getProperty("Result").toString().equalsIgnoreCase("ok")){
            res.setSharingSourceAccount(obj.getProperty("SharingSourceAccount").toString());
        }
        res.setResult(obj.getProperty("Result").toString());
        return res;
    }

    public QueryAndroidTokenRes queryAndroidToken(QueryAndroidTokenReq req) throws IOException, XmlPullParserException {
        QueryAndroidTokenRes res = new QueryAndroidTokenRes();
        SoapObject rpc = new SoapObject(mNameSpace,"queryAndroidTokenReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("UDID",req.getUDID());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/queryAndroidToken");
        if (obj.getProperty("Result").toString().equalsIgnoreCase("ok")){
            res.setUDID(obj.getProperty("UDID").toString());
            res.setAPNs(Boolean.valueOf(obj.getProperty("APNs").toString()));
            res.setDeviceToken(obj.getProperty("DeviceToken").toString());
        }
        res.setResult(obj.getProperty("Result").toString());
        return res;
    }

    public QueryClientVersionRes queryClientVersion(QueryClientVersionReq req) throws IOException, XmlPullParserException {
        QueryClientVersionRes res = new QueryClientVersionRes();
        SoapObject rpc = new SoapObject(mNameSpace,"queryClientVersionReq");
        rpc.addProperty("ClientType",req.getClientType());
        if (req.getApplicationID()!=null)rpc.addProperty("ApplicationID",req.getApplicationID());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/queryClientVersion");
        if (obj.getProperty("Result").toString().equalsIgnoreCase("ok")){
            res.setVersion(obj.getProperty("Version").toString());
            res.setDownloadAddress(obj.getProperty("DownloadAddress").toString());
        }
        res.setResult(obj.getProperty("Result").toString());
        return res;
    }

    public QueryDeviceBondedRes queryDeviceBonded(Request req) throws IOException, XmlPullParserException {
        QueryDeviceBondedRes res = new  QueryDeviceBondedRes();
        SoapObject rpc = new SoapObject(mNameSpace,"queryDeviceBondedReq");
        rpc.addProperty("DevID",req.getDevID());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/queryDeviceBonded");
        if (obj.getProperty("Result").toString().equalsIgnoreCase("ok")){
            res.setHasBonded(Boolean.valueOf(obj.getProperty("HasBonded").toString()));
        }
        res.setResult(obj.getProperty("Result").toString());
        return res;
    }

    public Result subscribeAndroidPush(SubscribeAndroidPushReq req) throws IOException, XmlPullParserException {
        SoapObject rpc = new SoapObject(mNameSpace,"subscribeAndroidPushReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("SubscribedFlag",req.getSubscribedFlag());
        if (req.getDevID()!=null)rpc.addProperty("DevID",req.getDevID());
        if (req.getChannelNo()>-1)rpc.addProperty("ChannelNo",req.getChannelNo());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/subscribeAndroidPush");
        return new Result(obj.getProperty("Result").toString());
    }

    public GetPushWorkSheetRes getpushworkSheet(Request req) throws IOException, XmlPullParserException {
        GetPushWorkSheetRes res = new GetPushWorkSheetRes();
        SoapObject rpc = new SoapObject(mNameSpace,"getPushWorkSheetReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("DevID",req.getDevID())
                .addProperty("ChannelNo",req.getChannelNo());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/getPushWorkSheet");
        if (obj.getProperty("Result").toString().equalsIgnoreCase("ok")){
            res.setWorkSheet(new GetPushWorkSheetRes.WorkSheet(((SoapObject)obj.getProperty("WorkSheet")).getProperty("BitString").toString()));
        }
        res.setResult(obj.getProperty("Result").toString());
        return res;
    }

    public Result setPushWorkSheet(SetPushWorkSheetReq req) throws IOException, XmlPullParserException {
        SoapObject rpc = new SoapObject(mNameSpace,"setPushWorkSheetReq");
        SoapObject workSheet = new SoapObject(mNameSpace,"WorkSheet").addProperty("BitString",req.getWorkSheet().getBitString());
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("DevID",req.getDevID())
                .addProperty("ChannelNo",req.getChannelNo())
                .addProperty("WorkSheet",workSheet);
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/setPushWorkSheet");
        return new Result(obj.getProperty("Result").toString());
    }

    public GetRecordParamRes  getRecordParam(Request req) throws IOException, XmlPullParserException {
        GetRecordParamRes res = new GetRecordParamRes();
        SoapObject rpc = new SoapObject(mNameSpace,"getRecordParamReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("DevID",req.getDevID())
                .addProperty("ChannelNo",req.getChannelNo());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/getRecordParam");
        if (obj.getProperty("Result").toString().equalsIgnoreCase("ok")){
            res.setEnabled(Boolean.valueOf(obj.getProperty("Enabled").toString()));
            SoapObject workSheet = (SoapObject) obj.getProperty("WorkSheet");
            res.setWorkSheet(new GetRecordParamRes.WorkSheet(
                    Boolean.valueOf(workSheet.getProperty("Enabled").toString()),
                    workSheet.getProperty("BitString").toString()
                    ));
        }
        res.setResult(obj.getProperty("Result").toString());
        return res;
    }

    public Result setRecordParam(SetRecordParamReq req) throws IOException, XmlPullParserException {
        SoapObject rpc = new SoapObject(mNameSpace,"setRecordParamReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("DevID",req.getDevID())
                .addProperty("ChannelNo",req.getChannelNo())
                .addProperty("Enabled",req.getEnabled());
        if (req.getWorkSheet()!=null){
            SoapObject worksheet = new SoapObject(mNameSpace,"WorkSheet");
            worksheet.addProperty("Enabled",req.getWorkSheet().getEnabled())
                    .addProperty("BitString",req.getWorkSheet().getBitString());
            rpc.addProperty("WorkSheet",worksheet);
        }
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/setRecordParam");
        return new Result(obj.getProperty("Result").toString());
    }

    public GetRelayRes getRelay(Request req) throws IOException, XmlPullParserException {
        GetRelayRes res = new GetRelayRes();
        SoapObject rpc = new SoapObject(mNameSpace,"getRelayReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("DevID",req.getDevID());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/getRelay");
        if (obj.getProperty("Result").toString().equalsIgnoreCase("ok")){
           ArrayList<GetRelayRes.Relay> relays = new ArrayList<>();
            SoapObject relay = (SoapObject) obj.getProperty("Relay");
            int count = relay.getPropertyCount();
            for (int i=0;i<count;i++){
                SoapObject o = (SoapObject) relay.getProperty(i);
                relays.add(new GetRelayRes.Relay(
                        Integer.valueOf(o.getProperty("No").toString()),
                        o.getProperty("Name").toString(),
                        o.getProperty("State").toString()
                ));
            }
            res.setRelays(relays);
        }
        res.setResult(obj.getProperty("Result").toString());
        return res;
    }

    public Result setRelay(SetRelayReq req) throws IOException, XmlPullParserException {
        SoapObject rpc = new SoapObject(mNameSpace,"setRelayReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("DevID",req.getDevID());
        if (req.getRelays()!=null){
            for (SetRelayReq.Relay r:req.getRelays()){
                SoapObject relay = new SoapObject(mNameSpace,"Relay");
                relay.addProperty("No",r.getNo());
                if (r.getName()!=null)relay.addProperty("Name",r.getNo());
                if (r.getState()!=null)relay.addProperty("State",r.getState());
                rpc.addProperty("Relay",relay);
            }
        }
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/setRelay");
        return new Result(obj.getProperty("Result").toString());
    }

    /**
     * get device network state
     * @param req
     * @return
     * @throws IOException
     * @throws XmlPullParserException
     */
    public GetWirelessNetworkRes getGetWirelessNetworkRes(GetWirelessNetworkReq req) throws IOException, XmlPullParserException {
        GetWirelessNetworkRes res = new GetWirelessNetworkRes();
        SoapObject rpc = new SoapObject(mNameSpace, "getWirelessNetworkReq");
        rpc.addProperty("Account", req.getAccount());
        rpc.addProperty("LoginSession", req.getLoginSession());
        rpc.addProperty("DevID", req.getDevID());
        SoapObject object = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/getWirelessNetwork");
        Object result = object.getProperty("result");
        res.setResult(result.toString());
        Object wirelessType = object.getProperty("WirelessType");
        res.setWirelessType(wirelessType.toString());
        Object sSID = object.getProperty("SSID");
        res.setsSID(sSID.toString());
        Object intensity = object.getProperty("Intensity");
        res.setIntensity(Integer.valueOf(intensity.toString()));
        return res;
    }

    public Result getDynamicPassword(Request req) throws IOException, XmlPullParserException {
        SoapObject rpc = new SoapObject(mNameSpace,"getDynamicPasswordReq");
        rpc.addProperty("Account",req.getAccount());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/getDynamicPassword");
        return new Result(obj.getProperty("Result").toString());
    }

    public GetDeviceMatchingCodeRes getDeviceMatchingCode(Request req) throws IOException, XmlPullParserException {
        GetDeviceMatchingCodeRes res = new GetDeviceMatchingCodeRes();
        SoapObject rpc = new SoapObject(mNameSpace,"getDeviceMatchingCodeReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/getDeviceMatchingCode");
        if (obj.getProperty("Result").toString().equalsIgnoreCase("ok")){
            res.setMatchCode(obj.getProperty("MatchingCode").toString());
        }
        res.setResult(obj.getProperty("Result").toString());
        return res;
    }

    public GetDeviceMatchingResultRes getDeviceMatchingResult(GetDeviceMatchingResultReq req) throws IOException, XmlPullParserException {
        GetDeviceMatchingResultRes res = new GetDeviceMatchingResultRes();
        SoapObject rpc = new SoapObject(mNameSpace,"getDeviceMatchingResultReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("MatchingCode",req.getMatchingCode());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/getDeviceMatchingResult");
        if (obj.getProperty("Result").toString().equalsIgnoreCase("ok")){
            res.setDevID(obj.getProperty("DevID").toString());
        }
        res.setResult(obj.getProperty("Result").toString());
        return res;
    }

    public TrustedAuthorityLoginRes trustedAuthorityLogin(TrustedAuthorityLoginReq req) throws IOException, XmlPullParserException {
        TrustedAuthorityLoginRes res = new TrustedAuthorityLoginRes();
        SoapObject rpc = new SoapObject(mNameSpace,"trustedAuthorityLoginReq");
        rpc.addProperty("TrustedAuthority",req.getTrustedAuthority())
                .addProperty("TrustedCode",req.getTrustedCode())
                .addProperty("AuthorizationCode",req.getAuthorizationCode())
                .addProperty("Version",req.getVersion())
                .addProperty("NetworkOperator",req.getNetworkOperator())
                .addProperty("NetType",req.getNetType());
        SoapObject mcuDev = new SoapObject(mNameSpace,"MCUDev");
        mcuDev.addProperty("UUID",req.getMcuDev().getUUID())
                .addProperty("Model",req.getMcuDev().getModel())
                .addProperty("Type",req.getMcuDev().getType())
                .addProperty("OSType",req.getMcuDev().getOsType());
        if (req.getMcuDev().getOsVersion()!=null)mcuDev.addProperty("OSVersion",req.getMcuDev().getOsVersion());
        if (req.getMcuDev().getManufactory()!=null)mcuDev.addProperty("Manufactory",req.getMcuDev().getManufactory());
        if (req.getMcuDev().getIEMI()!=null)mcuDev.addProperty("IEMI",req.getMcuDev().getIEMI());
        rpc.addProperty("MCUDev",mcuDev);
        rpc.addProperty("ApplicationID",req.getApplicationID());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/trustedAuthorityLogin");
        if (obj.getProperty("Result").toString().equalsIgnoreCase("ok")){
            res.setSession(obj.getProperty("LoginSession").toString());
            res.setUserName(obj.getProperty("Username").toString());
            res.setAccount(obj.getProperty("Account").toString());
            //TODO info
        }
        res.setResult(obj.getProperty("Result").toString());
        return res;
    }

    /**
     * get notices list,the notices list maybe a history of alarm
     * @param req
     * @return result
     * @throws IOException
     * @throws XmlPullParserException
     */
    public QueryNoticesRes getQueryNoticesRes(QueryNoticesReq req) throws IOException, XmlPullParserException {
        System.out.println("QueryNotices");
        QueryNoticesRes res = new QueryNoticesRes();
        SoapObject rpc = new SoapObject(mNameSpace, "queryNoticesReq");
        rpc.addProperty("Account", req.getAccount());
        rpc.addProperty("LoginSession", req.getLoginSession());
        if(req.getPageNo() != 0)
            rpc.addProperty("PageNo", req.getPageNo());
        if(req.getPageSize() != 0)
            rpc.addProperty("PageSize", req.getPageSize());
        SoapObject object = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/queryNotices");
        Object result = object.getProperty("result");
        res.setResult(result.toString());
        Object pageNo = object.getProperty("PageNo");
        res.setPageNo(Integer.valueOf(pageNo.toString()));
        Object pageCount = object.getProperty("PageCount");
        res.setPageCount(Integer.valueOf(pageCount.toString()));
        Object recordCount = object.getProperty("RecordCount");
        res.setRecordCount(Integer.valueOf(recordCount.toString()));
        SoapObject noticeList = (SoapObject)object.getProperty("Notice");
        ArrayList<NoticeList> list = new ArrayList<NoticeList>();
        for(int i = 0 ;i<noticeList.getPropertyCount();i++){
            NoticeList n = new NoticeList();
            SoapObject notice = (SoapObject)noticeList.getProperty(i);
            Object id = notice.getProperty("ID");
            n.setiD(id.toString());
            System.out.println("id:"+id);
            Object message = notice.getProperty("Message");
            n.setMessage(message.toString());
            System.out.println("message:"+message);
            Object classification = notice.getProperty("Classification");
            n.setClassification(classification.toString());
            System.out.println("classification:"+classification);
            Object time = notice.getProperty("Time");
            n.setTime(time.toString());
            System.out.println("time:"+time);
            Object status = notice.getProperty("Status");
            n.setStatus(status.toString());
            System.out.println("status:"+status);
            Object devID = notice.getProperty("DevID");
            n.setDevID(devID.toString());
            System.out.println("devID:"+devID);
            Object channelNo = notice.getProperty("ChannelNo");
            n.setChannelNo(Integer.valueOf(channelNo.toString()));
            System.out.println("channelNo:"+channelNo);
            try{
                Object name = notice.getProperty("Name");
                n.setName(name.toString());
            }catch(Exception e){
                n.setName("");
            }
            try{
                SoapObject pictureIDList = (SoapObject)notice.getProperty("PictureID");
                ArrayList<String> pictureIdList = new ArrayList<String>();
                for(int j = 0 ; j < pictureIDList.getPropertyCount() ; j++){
                    Object pictureID = pictureIDList.getProperty(j);
                    pictureIdList.add(pictureID.toString());
                }
                n.setPictureID(pictureIdList);
            }catch(Exception e){
                n.setPictureID(new ArrayList<String>());
            }
            list.add(n);
        }
        res.setNoticeList(list);
        return res;
    }

    /**
     * flag notice status
     * @param req {@link FlaggedNoticeStatusReq} <br/>
     * {@link FlaggedNoticeStatusReq#status}<br/>
     * "Unread":has not read;<br/>
     * "Read":has read;<br/>
     * @return result
     * @throws IOException
     * @throws XmlPullParserException
     */
    public FlaggedNoticeStatusRes getFlaggedNoticeStatusRes(FlaggedNoticeStatusReq req) throws IOException, XmlPullParserException {
        FlaggedNoticeStatusRes res = new FlaggedNoticeStatusRes();
        SoapObject rpc = new SoapObject(mNameSpace, "FlaggedNoticeStatusReq");
        rpc.addProperty("Account", req.getAccount());
        rpc.addProperty("LoginSession", req.getLoginSession());
        rpc.addProperty("Status", req.getStatus());
        rpc.addProperty("NoticeID", req.getNoticeID());
        SoapObject object = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/flaggedNoticeStatus");
        Object result = object.getProperty("result");
        res.setResult(result.toString());
        System.out.println(result);
        return res;
    }

    /**
     * get picture information
     * @param req {@link GetPictureReq}<br/>
     * {@link GetPictureReq#pictureID} picture ID which you get it from the result of getQueryNoticesRes<br/>
     * @return {@link GetPictureRes}<br/>
     * {@link GetPictureRes#getPicture()} the date of picture with is type base64
     * @throws IOException
     * @throws XmlPullParserException
     */
    public GetPictureRes getGetPictureRes(GetPictureReq req) throws IOException, XmlPullParserException {
        System.out.println("GetPictureRes");
        GetPictureRes res = new GetPictureRes();
        SoapObject rpc = new SoapObject(mNameSpace, "getPictureReq");
        rpc.addProperty("Account", req.getAccount());
        rpc.addProperty("LoginSession", req.getLoginSession());
        rpc.addProperty("PictureID", req.getPictureID());
        SoapObject object = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/getPicture");
        Object result = object.getProperty("result");
        res.setResult(result.toString());
        Object pictureID = object.getProperty("PictureID");
        res.setPictureID(pictureID.toString());
        Object picture = object.getProperty("Picture");
        res.setPicture(picture.toString());
        return res;
    }

    public GetScheduledTaskRes getScheduledTask(Request req) throws IOException, XmlPullParserException {
        GetScheduledTaskRes res = new GetScheduledTaskRes();
        SoapObject rpc = new SoapObject(mNameSpace,"getScheduledTaskReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("DevID",req.getDevID());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/getScheduledTask");
        if (obj.getProperty("Result").toString().equalsIgnoreCase("ok")){
            SoapObject task = (SoapObject) obj.getProperty("Task");
            int count = task.getPropertyCount();
            ArrayList<GetScheduledTaskRes.Task> tasks = new ArrayList<>();
            for (int i=0;i<count;i++){
                SoapObject o = (SoapObject) task.getProperty(i);
                tasks.add(new GetScheduledTaskRes.Task(
                        o.getProperty("TaskID").toString(),
                        o.getProperty("Name").toString(),
                        Boolean.valueOf(o.getProperty("Enabled").toString()),
                        o.getProperty("TimeType").toString(),
                        o.getProperty("Time").toString(),
                        o.getProperty("ActionType").toString()
                ));
            }
            res.setTasks(tasks);
            SoapObject auxiliary = (SoapObject) obj.getProperty("Auxiliary");
            res.setAuxiliary(new GetScheduledTaskRes.Auxiliary(
                    auxiliary.getProperty("AuxiliaryType").toString(),
                    auxiliary.getProperty("AuxiliaryState").toString()
            ));
        }
        res.setResult(obj.getProperty("Result").toString());
        return res;
    }

    public CreateScheduledTaskRes createScheduledTask(CreateScheduledTaskReq req) throws IOException, XmlPullParserException {
        CreateScheduledTaskRes res = new CreateScheduledTaskRes();
        SoapObject task = new SoapObject(mNameSpace,"Task");
        task.addProperty("TaskID",req.getTask().getTaskID())
                .addProperty("Name",req.getTask().getName())
                .addProperty("Enabled",req.getTask().getEnabled())
                .addProperty("TimeType",req.getTask().getTimeType())
                .addProperty("TIme",req.getTask().getTime())
                .addProperty("ActionType",req.getTask().getActionType());
        SoapObject auxiliary = new SoapObject(mNameSpace,"Auxiliary");
        auxiliary.addProperty("AuxiliaryType",req.getAuxiliary().getAuxiliaryType())
                .addProperty("AuxiliaryState",req.getAuxiliary().getAuxiliaryState());

        SoapObject rpc = new SoapObject(mNameSpace,"createScheduledTaskReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("DevID",req.getDevID())
                .addProperty("Task",task)
                .addProperty("Auxiliary",auxiliary);
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/createScheduledTask");
        if (obj.getProperty("Result").toString().equalsIgnoreCase("ok")){
            res.setTaskID(obj.getProperty("TaskID").toString());
        }
        res.setResult(obj.getProperty("Result").toString());
        return res;
    }

    public Result setScheduledTask(SetScheduledTaskReq req) throws IOException, XmlPullParserException {
        SoapObject task = new SoapObject(mNameSpace,"Task");
        task.addProperty("TaskID",req.getTask().getTaskID())
                .addProperty("Name",req.getTask().getName())
                .addProperty("Enabled",req.getTask().getEnabled())
                .addProperty("TimeType",req.getTask().getTimeType())
                .addProperty("Time",req.getTask().getTime())
                .addProperty("ActionType",req.getTask().getActionType());

        SoapObject auxiliary =  new SoapObject(mNameSpace,"Auxiliary");
        auxiliary.addProperty("AuxiliaryType",req.getAuxiliary().getAuxiliType())
                .addProperty("AuxiliaryState",req.getAuxiliary().getAuxiliState());
        SoapObject rpc = new SoapObject(mNameSpace,"setScheduledTaskReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("DevID",req.getDevID())
                .addProperty("Task",task)
                .addProperty("Auxiliary",auxiliary);
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/setScheduledTask");
        return new Result(obj.getProperty("Result").toString());
    }

    public Result deleteScheduledTask(DeleteScheduledTaskReq req) throws IOException, XmlPullParserException {
        SoapObject rpc = new SoapObject(mNameSpace,"deleteScheduledTaskReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("DevID",req.getDevID())
                .addProperty("TaskID",req.getTaskID());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/deleteScheduledTask");
        return new Result(obj.getProperty("Result").toString());
    }

    public GetExtendedParamRes getExtendedParam(Request req) throws IOException, XmlPullParserException {
        GetExtendedParamRes res = new GetExtendedParamRes();
        SoapObject rpc = new SoapObject(mNameSpace,"getExtendedParamReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("DevID",req.getDevID());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/getExtendedParam");
        if (obj.getProperty("Result").toString().equalsIgnoreCase("ok")){
            res.setLightingDuration(Integer.valueOf(obj.getProperty("LightingDuration").toString()));
        }
        res.setResult(obj.getProperty("Result").toString());
        return res;
    }

    public Result setExtentedParam(SetExtendedParamReq req) throws IOException, XmlPullParserException {
        SoapObject rpc = new SoapObject(mNameSpace,"setExtendedParamReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("DevID",req.getDevID())
                .addProperty("LightingDuration",req.getLightingDuration());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/setExtendedParam");
        return new Result(obj.getProperty("Result").toString());
    }

    public Result uploadMCUDevice(UploadMCUDeviceReq req) throws IOException, XmlPullParserException {
        SoapObject rpc = new SoapObject(mNameSpace,"uploadMCUDeviceReq");
        rpc.addProperty("UUID",req.getUUID())
                .addProperty("Model",req.getModel())
                .addProperty("Type",req.getType())
                .addProperty("OSType",req.getOsType());
        if (req.getOsVersion()!=null)rpc.addProperty("OSVersion",req.getOsVersion());
        if (req.getManufactory()!=null)rpc.addProperty("Manufactory",req.getManufactory());
        if (req.getIEMI()!=null)rpc.addProperty("IEMI",req.getIEMI());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/uploadMCUDevice");
        return new Result(obj.getProperty("Result").toString());
    }

    public QueryMCUDeviceAuthenticatedRes queryMCUDeviceAuthenticated(QueryMCUDeviceAuthenticatedReq req) throws IOException, XmlPullParserException {
        QueryMCUDeviceAuthenticatedRes res = new QueryMCUDeviceAuthenticatedRes();
        SoapObject rpc = new SoapObject(mNameSpace,"queryMCUDeviceAuthenticatedReq");
        rpc.addProperty("UUID",req.getUUID());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/queryMCUDeviceAuthenticated");
        if (obj.getProperty("Result").toString().equalsIgnoreCase("ok")){
            res.setAuthenticated(Boolean.valueOf(obj.getProperty("Authenticated").toString()));
        }
        res.setResult(obj.getProperty("Result").toString());
        return res;
    }

    public Result addDeviceForcible(AddDeviceForceibleReq req) throws IOException, XmlPullParserException {
        SoapObject rpc = new SoapObject(mNameSpace,"addDeviceForcibleReq");
        rpc.addProperty("Account",req.getAccount());
        rpc.addProperty("LoginSession",req.getSession());

        for (AddDeviceForceibleReq.Device d:req.getDevices()){
            SoapObject dev = new SoapObject(mNameSpace,"Device");
            dev.addProperty("DevID",d.getDevID());
            dev.addProperty("DevKey",d.getDevKey());
            if (d.getChannelNo()>-1)dev.addProperty("ChannelNo",d.getChannelNo());
            dev.addProperty("DevName",d.getDevName());
            rpc.addProperty("Device",dev);
        }
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/addDeviceForcible");
        return new Result(obj.getProperty("Result").toString());
    }

    public Result nullifyDeviceForcible(nullifyDeviceForcibleReq req) throws IOException, XmlPullParserException {
        SoapObject rpc = new SoapObject(mNameSpace,"nullifyDeviceForcibleReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("DevID",req.getDevID());
                if (req.getDevKey()!=null)rpc.addProperty("DevKey",req.getDevKey());
                if (req.getChannelNo()>-1)rpc.addProperty("ChannelNo",req.getChannelNo());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/nullifyDeviceForcible");
        return new Result(obj.getProperty("Result").toString());
    }

    public GetVideoBasicRes getVideoBasic(Request req) throws IOException, XmlPullParserException {
        GetVideoBasicRes res = new GetVideoBasicRes();
        SoapObject rpc = new SoapObject(mNameSpace,"getVideoBasicReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("DevID",req.getDevID())
                .addProperty("ChannelNo",req.getChannelNo());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/getVideoBasic");
        if (obj.getProperty("Result").toString().equalsIgnoreCase("ok")){
                try{res.setDNMode(obj.getProperty("DNMode").toString());}catch (Exception e){}
                try{
                    SoapObject o = (SoapObject) obj.getProperty("DNSensitivity");
                    res.setDnSensitivity(new GetVideoBasicRes.DNSensitivity(
                            Integer.valueOf(o.getProperty("Minimum").toString()),
                            Integer.valueOf(o.getProperty("Maximum").toString()),
                            Integer.valueOf(o.getProperty("Value").toString())
                    ));
                }catch (Exception e){}
                try{res.setAGCMode(obj.getProperty("AGCMode").toString());}catch (Exception e){}
                try{
                    SoapObject o = (SoapObject) obj.getProperty("AGC");
                    res.setAgc(new GetVideoBasicRes.AGC(
                            Integer.valueOf(o.getProperty("Minimum").toString()),
                            Integer.valueOf(o.getProperty("Maximum").toString()),
                            Integer.valueOf(o.getProperty("Value").toString())
                    ));
                }catch (Exception e){}
                try{res.seteShutterMode(obj.getProperty("eShutterMode").toString());}catch (Exception e){}
                try{
                    SoapObject o = (SoapObject) obj.getProperty("eShutter");
                    res.seteShutter(new GetVideoBasicRes.EShutter(
                            o.getProperty("Value").toString(),
                            o.getProperty("Options").toString()
                    ));
                }catch (Exception e){}
                try{res.setInfraredMode(obj.getProperty("InfraredMode").toString());}catch (Exception e){}

        }
        res.setResult(obj.getProperty("Result").toString());
        return res;
    }

    public Result setVideoBasic(SetVideoBasicReq req) throws IOException, XmlPullParserException {
        SoapObject rpc = new SoapObject(mNameSpace,"setVideoBasicReq");
        rpc.addProperty("Account",req.getAccount())
                .addProperty("LoginSession",req.getSession())
                .addProperty("DevID",req.getDevID())
                .addProperty("ChannelNo",req.getChannelNo());
        if (req.getDNMode()!=null)rpc.addProperty("DNMode",req.getDNMode());
        if (req.getDNSensitivity()>0)rpc.addProperty("DNSensitivity",req.getDNSensitivity());
        if (req.getAGCMode()!=null)rpc.addProperty("AGCMode",req.getAGCMode());
        if (req.getAGC()>0)rpc.addProperty("AGC",req.getAGC());
        if (req.geteShutterMode()!=null)rpc.addProperty("eShutterMode",req.geteShutterMode());
        if (req.geteShutter()!=null)rpc.addProperty("eShutter",req.geteShutter());
        if (req.getInfraredMode()!=null)rpc.addProperty("InfraredMode",req.getInfraredMode());
        SoapObject obj = initEnvelopAndTransport(rpc,"http://www.haoweis.com/HomeServices/MCU/setVideoBasicReq");
        return new Result(obj.getProperty("Result").toString());
    }











}
