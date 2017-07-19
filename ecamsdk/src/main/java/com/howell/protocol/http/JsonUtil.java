package com.howell.protocol.http;


import android.util.Log;

import com.howell.bean.httpbean.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/5.
 */

public class JsonUtil {
    public static UserNonce parseNonceJsonObject(JSONObject obj) throws JSONException{
        if (obj==null)return null;
        return new UserNonce(obj.getString("Nonce"),obj.getString("Domain"));
    }

    public static Version parseVersionJsonObject(JSONObject obj) throws JSONException {
        if (obj == null)return null;
        return new Version(obj.getString("Version"),obj.getString("BuildDate"),obj.getString("Company"));
    }

    public static Fault parseFaultJsonObject(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        int code = obj.getInt("FaultCode");
        String reason = obj.getString("FaultReason");
        String message = null;
        String type = null;
        String id = null;
        try{
            JSONObject exceptionData = obj.getJSONObject("Exception");
            message = exceptionData.getString("Message");
            type = exceptionData.getString("ExceptionType");
        }catch (JSONException e){}
        try{
            id = obj.getString("Id");
        }catch (JSONException e){}
        return new Fault(code,reason,new Fault.Exception(message,type),id);
    }

    public static JSONObject createAuthenticateJsonObject(UserClientCredential req) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("UserName",req.getUserName());
        obj.put("Nonce",req.getNonce());
        obj.put("Domain",req.getDomain());
        obj.put("ClientNonce",req.getClientNonce());
        obj.put("VerifySession",req.getVerifySession());
        if (req.getPhysicalAddress()!=null){
            obj.put("PhysicalAddress",req.getPhysicalAddress());
        }
        return obj;
    }

    public static JSONObject createMethodValidationJsonObject(UserMethodValidation req) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("UserName",req.getUserName());
        obj.put("SessionId",req.getSessionID());
        obj.put("Method",req.getMethod());
        obj.put("URL",req.getUrl());
        obj.put("MethodVerifySession",req.getMethodVerifySession());
        return obj;
    }

    public static JSONObject createTeardownCredentialJsonObject(UserTeardownCredential req) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("UserName",req.getUserName());
        obj.put("SessionId",req.getSessionID());
        if (req.getTeardownReason()!=null){
            obj.put("TeardownReason",req.getTeardownReason());
        }
        return obj;
    }

    public static User parseUserJsonObject(JSONObject userObj) throws JSONException {
        if (userObj==null)return null;
        User user = new User();
        user.setId(userObj.getString("Id"));
        user.setUserName(userObj.getString("Username"));
        try{user.setNickName(userObj.getString("Nickname"));}catch (JSONException e){}
        user.setPassword(userObj.getString("Password"));
        try{user.setMobile(userObj.getString("Mobile"));}catch(JSONException e){}
        try{user.setPhone(userObj.getString("Phone"));}catch (JSONException e){}
        try{user.setUniformedId(userObj.getString("UniformedId"));}catch (JSONException e){}
        try{user.setDuty(userObj.getString("Duty"));}catch (JSONException e){}
        try{user.setInformation(userObj.getString("Information"));}catch (JSONException e){}
        try{user.setSex(userObj.getString("Sex"));}catch (JSONException e){}
        user.setPermission(userObj.getString("Permission"));
        try{user.setDetailPermission(userObj.getString("DetailPermission"));}catch (JSONException e){}
        try{user.setDeviceCount(userObj.getInt("DeviceCount"));}catch (JSONException e){}
        try{user.setVideoInputChannelCount(userObj.getInt("VideoInputChannelCount"));}catch (JSONException e){}
        try{user.setVideoOutputChannelCount(userObj.getInt("VideoOutputChannelCount"));}catch (JSONException e){}
        try{user.setIOInputChannelCount(userObj.getInt("IOInputChannelCount"));}catch (JSONException e){}
        try{user.setIOOutputChannelCount(userObj.getInt("IOOutputChannelCount"));}catch (JSONException e){}
        try{user.setDepartmentCount(userObj.getInt("DepartmentCount"));}catch (JSONException e){}
        return user;
    }

    public static UserList parseUserListJsonObject(JSONObject obj){
        if (obj == null)return null;
        UserList res = new UserList();
        JSONObject pageObj = null;
        try {
            pageObj = obj.getJSONObject("Page");
            res.setPage(new Page(
                    pageObj.getInt("PageIndex"),
                    pageObj.getInt("PageSize"),
                    pageObj.getInt("PageCount"),
                    pageObj.getInt("RecordCount"),
                    pageObj.getInt("TotalRecordCount")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray userArry = null;
        ArrayList<User> userArrayList = new ArrayList<>();
        try {
            userArry = obj.getJSONArray("User");

            for (int i=0;i<userArry.length();i++){
                userArrayList.add(parseUserJsonObject(userArry.getJSONObject(i)));
            }
            res.setUsers(userArrayList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static Page parsePageJsonObject(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        return new Page(obj.getInt("PageIndex"),obj.getInt("PageSize"),obj.getInt("PageCount"),obj.getInt("RecordCount"),obj.getInt("TotalRecordCount"));
    }

    public static Device parseDeviceJsonObject(JSONObject obj) throws JSONException {
        if (obj == null)return null;
        Device device = new Device();
        device.setId(obj.getString("Id"));
        device.setAuthenticationCode(obj.getString("AuthenticationCode"));
        device.setName(obj.getString("Name"));
        try{device.setManufacturer(obj.getString("Manufacturer"));}catch (JSONException e){}
        try{device.setModel(obj.getString("Model"));}catch (JSONException e){}
        try{device.setFirmware(obj.getString("Firmware"));}catch (JSONException e){}
        try{device.setSerialNumber(obj.getString("SerialNumber"));}catch (JSONException e){}
        try{device.setPointOfSale(obj.getString("PointOfSale"));}catch (JSONException e){}
        try{device.setInformation(obj.getString("Information"));}catch (JSONException e){}
        try{device.setUserName(obj.getString("Username"));}catch (JSONException e){}
        try{device.setPassword(obj.getString("Password"));}catch (JSONException e){}
        device.setUri(obj.getString("Uri"));
        device.setClassification(obj.getString("Classification"));
        device.setProtocolType(obj.getString("ProtocolType"));
        try{device.setParentDeviceId(obj.getString("ParentDeviceId"));}catch (JSONException e){}
        device.setBusAddress(obj.getInt("BusAddress"));
        device.setHasSubDevice(obj.getBoolean("HasSubDevice"));
        try{device.setAbilities(obj.getString("Abilities"));}catch (JSONException e){}
        try{device.setRatedVoltage(obj.getDouble("RatedVoltage"));}catch (JSONException e){}
        try{device.setMaximumUserConnectionsNumber(obj.getInt("MaximumUserConnectionsNumber"));}catch (JSONException e){}
        try{device.setMaximumVideoConnectionsNumber(obj.getInt("MaximumVideoConnectionsNumber"));}catch (JSONException e){}
        try{device.setExistedInDatabase(obj.getBoolean("ExistedInDatabase"));}catch (JSONException e){}
        try{device.setDeviceStatus(obj.getString("DeviceStatus"));}catch (JSONException e){}
        try{device.setVideoInputChannelConunt(obj.getInt("VideoInputChannelCount"));}catch (JSONException e){}
        try{device.setVideoOutputChannelCount(obj.getInt("VideoOutputChannelCount"));}catch (JSONException e){}
        try{device.setIOInputChannelCount(obj.getInt("IOInputChannelCount"));}catch (JSONException e){}
        try{device.setIOOutputChannelCount(obj.getInt("IOOutputChannelCount"));}catch (JSONException e){}
        try{device.setNetworkInterfaceCount(obj.getInt("NetworkInterfaceCount"));}catch (JSONException e){}
        try{device.setStorageMediumCount(obj.getInt("StorageMediumCount"));}catch (JSONException e){}
        try{device.setDecodingChannelCount(obj.getInt("DecodingChannelCount"));}catch (JSONException e){}
        try{device.setRelationUserCount(obj.getInt("RelationUserCount"));}catch (JSONException e){}
        try{device.setRelationDepartmentCount(obj.getInt("RelationDepartmentCount"));}catch (JSONException e){}
        return device;
    }

    public static DevicePermission parseDevicePermission(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        DevicePermission dp = new DevicePermission();
        dp.setId(obj.getString("Id"));
        dp.setName(obj.getString("Name"));
        dp.setPermission(obj.getString("Permission"));
        dp.setDevice(parseDeviceJsonObject(obj.getJSONObject("Device")));
        try{dp.setFromDepartment(obj.getBoolean("IsFromDepartment"));}catch (JSONException e){}
        return dp;
    }

    public static DevicePermissionList parseDevicePermissionList(JSONObject obj) throws JSONException {
        if (obj==null) return null;
        DevicePermissionList list = new DevicePermissionList();
        list.setPage(parsePageJsonObject(obj.getJSONObject("Page")));
        JSONArray arry = obj.getJSONArray("DevicePermission");
        ArrayList<DevicePermission> dlist = new ArrayList<>();
        for (int i=0;i<arry.length();i++){
            dlist.add(parseDevicePermission(arry.getJSONObject(i)));
        }
        list.setDevicePermissionses(dlist);
        return list;
    }

    public static VideoInputAssociation parseVideoInputAssociationJsonObject(JSONObject obj) throws JSONException {
        if (obj == null)return null;
        VideoInputAssociation association = new VideoInputAssociation();
        association.setFromId(obj.getString("FromId"));
        try{association.setUrl(obj.getString("Url"));}catch (JSONException e){}
        try{association.setUserName(obj.getString("Username"));}catch (JSONException e){}
        try{association.setPassword(obj.getString("Password"));}catch (JSONException e){}
        try{association.setTCPTransport(obj.getBoolean("TCPTransport"));}catch (JSONException e){}
        return association;
    }

    public static NetworkStreamingAssociation parseNetworkStreamingAssociationJsonObject(JSONObject obj) throws JSONException {
        if (obj == null)return null;
        NetworkStreamingAssociation association = new NetworkStreamingAssociation();
        association.setNo(obj.getInt("No"));
        association.setUrl(obj.getString("Url"));
        try{association.setUserName(obj.getString("Username"));}catch (JSONException e){}
        try{association.setPassword(obj.getString("Password"));}catch (JSONException e){}
        try{association.setTCPTransport(obj.getBoolean("TCPTransport"));}catch (JSONException e){}
        return association;
    }



    public static StreamingChannel parseStreamingChannelJsonObject(JSONObject obj) throws JSONException {
        if (obj == null) return null;
        StreamingChannel stream = new StreamingChannel();
        stream.setNo(obj.getInt("No"));
        stream.setVideoCodecType(obj.getString("VideoCodecType"));
        stream.setVideoResolution(obj.getString("VideoResolution"));
        stream.setVideoQualityControlType(obj.getString("VideoQualityControlType"));
        stream.setVideoBitrateupperCap(obj.getDouble("VideoBitrateUpperCap"));
        stream.setFrameRate(obj.getDouble("FrameRate"));
        stream.setFixedQuality(obj.getDouble("FixedQuality"));
        try{stream.setUrl(obj.getString("Url"));}catch (JSONException e){}
        try{stream.setNetworkStreamingAssociation(parseNetworkStreamingAssociationJsonObject(obj.getJSONObject("NetworkStreamingAssociation")));}catch (JSONException e){}
        return stream;
    }

    public static VideoInputChannel parseVideoInputChannelJsonObject(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        VideoInputChannel channel = new VideoInputChannel();
        channel.setId(obj.getString("Id"));
        channel.setName(obj.getString("Name"));
        try{channel.setPtz(obj.getBoolean("PTZ"));}catch (JSONException e){}
        try{channel.setInfrared(obj.getBoolean("Infrared"));}catch (JSONException e){}
        try{channel.setCameraType(obj.getString("CameraType"));}catch (JSONException e){}
        try{channel.setTerminal(obj.getBoolean("Terminal"));}catch (JSONException e){}
        try{channel.setNetworked(obj.getBoolean("Networked"));}catch (JSONException e){}
        try{channel.setVideoInterfaceType(obj.getString("VideoInterfaceType"));}catch (JSONException e){}
        try{channel.setPseudoCode(obj.getInt("PseudoCode"));}catch (JSONException e){}
        try{
            ArrayList<StreamingChannel> list = new ArrayList<>();
            JSONArray arry = obj.getJSONArray("StreamingChannel");
            for (int i=0;i<arry.length();i++){
                list.add(parseStreamingChannelJsonObject(arry.getJSONObject(i)));
            }
            channel.setStreamingChannels(list);
        }catch (JSONException e){}
        try{channel.setVideoInputAssociation(parseVideoInputAssociationJsonObject(obj.getJSONObject("Association")));}catch (JSONException e){}
        return channel;
    }


    public static VideoInputChannelPermission parseVideoInputChannelPermissionJsonObject(JSONObject obj) throws JSONException {
        if (obj == null)return null;
        VideoInputChannelPermission permission = new VideoInputChannelPermission();
        permission.setId(obj.getString("Id"));
        try{permission.setName(obj.getString("Name"));}catch (JSONException e){}
        permission.setPermission(obj.getString("Permission"));
        permission.setVideoInputChannel(parseVideoInputChannelJsonObject(obj.getJSONObject("VideoInputChannel")));
        try{permission.setFromDepartment(obj.getBoolean("IsFromDepartment"));}catch (JSONException e){}
        return permission;
    }

    public static VideoInputChannelPermissionList parseVideoInputChannelPermissionListJsonObject(JSONObject obj){
        if (obj == null)return null;
        VideoInputChannelPermissionList permissionList = new VideoInputChannelPermissionList();
        try{permissionList.setPage(parsePageJsonObject(obj.getJSONObject("Page")));}catch (JSONException e){}
        try{
            ArrayList<VideoInputChannelPermission> list = new ArrayList<>();
            JSONArray arr = obj.getJSONArray("VideoInputChannelPermission");

            for (int i=0;i<arr.length();i++){

                list.add(parseVideoInputChannelPermissionJsonObject(arr.getJSONObject(i)));
            }
            permissionList.setVideoInputChannelPermissiones(list);
        }catch (JSONException e){}
        return permissionList;
    }

    public static Department parseDepartmentJsonObject(JSONObject obj) throws JSONException {
        if (obj == null)return null;
        Department department = new Department();
        department.setId(obj.getString("Id"));
        department.setName(obj.getString("Name"));
        try{department.setInformation(obj.getString("Information"));}catch (JSONException e){}
        department.setPermission(obj.getString("Permission"));
        try{department.setDetailPermission(obj.getString("DetailPermission"));}catch (JSONException e){}
        try{department.setDeviceCount(obj.getInt("DeviceCount"));}catch (JSONException e){}
        try{department.setVideoInputChannelCount(obj.getInt("VideoInputChannelCount"));}catch (JSONException e){}
        try{department.setVideoOutputChannelCount(obj.getInt("VideoOutputChannelCount"));}catch (JSONException e){}
        try{department.setIOInputChannelCount(obj.getInt("IOInputChannelCount"));}catch (JSONException e){}
        try{department.setIOOutputChannelCount(obj.getInt("IOOutputChannelCount"));}catch (JSONException e){}
        try{department.setUserCount(obj.getInt("UserCount"));}catch (JSONException e){}
        return department;
    }

    public static DepartmentList parseDepartmentListJsonObject(JSONObject obj){
        if (obj == null)return null;
        DepartmentList departmentList = new DepartmentList();
        try {departmentList.setPage(parsePageJsonObject(obj.getJSONObject("Page")));} catch (JSONException e) {}
        try{
            JSONArray arry = obj.getJSONArray("Department");
            ArrayList<Department> list = new ArrayList<>();
            for (int i=0;i<arry.length();i++){
                list.add(parseDepartmentJsonObject(arry.getJSONObject(i)));
            }
            departmentList.setList(list);
        }catch (JSONException e){}
        return departmentList;
    }

    public static DecodingChannel parseDecodingChannelJsonobject(JSONObject obj) throws JSONException {
        if (obj == null) return null;
        DecodingChannel channel = new DecodingChannel();
        channel.setId(obj.getString("Id"));
        channel.setEnable(obj.getBoolean("Enabled"));
        try{channel.setSupportedCodecFormats(obj.getString("SupportedCodecFormats"));}catch (JSONException e){}
        try{channel.setPseudoCode(obj.getInt("PseudoCode"));}catch (JSONException e){}
        return channel;
    }

    public static VideoOutputChannel parseVideoOutputChannelJsonObject(JSONObject obj) throws JSONException {
        if (obj == null) return null;
        VideoOutputChannel channel = new VideoOutputChannel();
        channel.setId(obj.getString("Id"));
        channel.setName(obj.getString("Name"));
        try{channel.setTerminal(obj.getBoolean("Terminal"));}catch (JSONException e){}
        try{channel.setNetworked(obj.getBoolean("Networked"));}catch (JSONException e){}
        try{channel.setInterfaceEquipped(obj.getBoolean("InterfaceEquipped"));}catch (JSONException e){}
        try{channel.setResolution(obj.getString("Resolution"));}catch (JSONException e){}
        try{channel.setFrequency(obj.getInt("Frequency"));}catch (JSONException e){}
        try{channel.setVideoInterfaceType(obj.getString("VideoInterfaceType"));}catch (JSONException e){}
        try{channel.setPseudoCode(obj.getInt("PseudoCode"));}catch (JSONException e){}
        try{
            JSONArray arry = obj.getJSONArray("DecodingChannel");
            ArrayList<DecodingChannel> decodingChannels = new ArrayList<>();
            for (int i=0;i<arry.length();i++){
                decodingChannels.add(parseDecodingChannelJsonobject(arry.getJSONObject(i)));
            }
            channel.setDecodingChannels(decodingChannels);
        }catch (JSONException e){}
        return channel;
    }

    public static VideoOutputChannelPermission parseVideoOutputChannelPermissionJsonObject(JSONObject obj) throws JSONException {
        if (obj == null) return null;
        VideoOutputChannelPermission permission = new VideoOutputChannelPermission();
        permission.setId(obj.getString("Id"));
        try{permission.setName(obj.getString("Name"));}catch (JSONException e){}
        permission.setPermission(obj.getString("Permission"));
        permission.setVideoOutputChannel(parseVideoOutputChannelJsonObject(obj.getJSONObject("VideoOutputChannel")));
        try{permission.setFromDepartment(obj.getBoolean("IsFromDepartment"));}catch (JSONException e){}
        return permission;
    }

    public static VideoOutputChannelPermissionList parseVideoOutputChannelPermissionListJsonObject(JSONObject obj){
        if (obj==null)return null;
        VideoOutputChannelPermissionList list = new VideoOutputChannelPermissionList();
        try{list.setPage(parsePageJsonObject(obj.getJSONObject("Page")));} catch (JSONException e) {}
        try{
            JSONArray arry = obj.getJSONArray("VideoOutputChannelPermission");
            ArrayList<VideoOutputChannelPermission> lists = new ArrayList<>();
            for (int i=0;i<arry.length();i++){
                lists.add(parseVideoOutputChannelPermissionJsonObject(arry.getJSONObject(i)));
            }
            list.setVideoOutputChannelPermissions(lists);
        }catch (JSONException e){}
        return list;
    }

    public static Linkage parseLinkageJsonObject(JSONObject obj) throws JSONException {
        if (obj == null) return null;
        Linkage linkage = new Linkage();
        linkage.setId(obj.getString("Id"));
        linkage.setName(obj.getString("Name"));
        linkage.setScriptType(obj.getString("ScriptType"));
        linkage.setScript(obj.getString("Script"));
        linkage.setDeviceId(obj.getString("DeviceId"));
        return linkage;
    }

    public static LinkageList parseLinkageListJsonObject(JSONObject obj){
        if (obj == null) return null;
        LinkageList list = new LinkageList();
        try{list.setPage(parsePageJsonObject(obj.getJSONObject("Page")));} catch (JSONException e) {}
        try{
            JSONArray arry = obj.getJSONArray("Linkage");
            ArrayList<Linkage> lists = new ArrayList<>();
            for(int i=0;i<arry.length();i++){
                lists.add(parseLinkageJsonObject(arry.getJSONObject(i)));
            }
            list.setLinkages(lists);
        }catch (JSONException e){}
        return list;
    }

    public static IOInputChannel parseIOInputChannelJsonObject(JSONObject obj) throws JSONException {
        if (obj == null)return null;
        IOInputChannel channel = new IOInputChannel();
        channel.setId(obj.getString("Id"));
        channel.setName(obj.getString("Name"));
        try{channel.setProbeType(obj.getString("ProbeType"));}catch (JSONException e){}
        try{channel.setTriggeringType(obj.getString("TriggeringType"));}catch (JSONException e){}
        try{channel.setDefenceZoneId(obj.getString("DefenceZoneId"));}catch (JSONException e){}
        return channel;
    }

    public static IOInputChannelPermission parseIOInputChannelPermissionJsonObject(JSONObject obj) throws JSONException {
        if (obj == null)return null;
        IOInputChannelPermission permission = new IOInputChannelPermission();
        permission.setId(obj.getString("Id"));
        try{permission.setName(obj.getString("Name"));}catch (JSONException e){}
        permission.setPermission(obj.getString("Permission"));
        permission.setIoInputChannel(parseIOInputChannelJsonObject(obj.getJSONObject("IOInputChannel")));
        try{  permission.setFromDepartment(obj.getBoolean("IsFromDepartment"));}catch (JSONException e){}
        return permission;
    }

    public static IOInputChannelPermissionList parseIOInputChannelPermissionListJsonObject(JSONObject obj){
        if (obj==null)return null;
        IOInputChannelPermissionList list = new IOInputChannelPermissionList();
        try {list.setPage(parsePageJsonObject(obj.getJSONObject("Page")));}catch (JSONException e){}
        try{
            JSONArray arry = obj.getJSONArray("IOInputChannelPermission");
            ArrayList<IOInputChannelPermission> lists = new ArrayList<>();
            for(int i=0;i<arry.length();i++){
                lists.add(parseIOInputChannelPermissionJsonObject(arry.getJSONObject(i)));
            }
            list.setIoInputChannelPermissions(lists);
        }catch (JSONException e){}
        return list;
    }

    public static IOOutputChannel parseIOOutPutChannelJsonObject(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        IOOutputChannel channel = new IOOutputChannel();
        channel.setId(obj.getString("Id"));
        channel.setName(obj.getString("Name"));
        try{channel.setTriggeringType(obj.getString("TriggeringType"));}catch (JSONException e){}
        return channel;
    }

    public static IOOutputChannelPermission parseIOOutChannelPermissionJsonObject(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        IOOutputChannelPermission permission = new IOOutputChannelPermission();
        permission.setId(obj.getString("Id"));
        try{ permission.setName(obj.getString("Name"));}catch (JSONException e){}
        permission.setPermission(obj.getString("Permission"));
        permission.setIoOutputChannel(parseIOOutPutChannelJsonObject(obj.getJSONObject("IOOutputChannel")));
        try{permission.setFromDepartment(obj.getBoolean("IsFromDepartment"));}catch (JSONException e){}
        return permission;
    }

    public static IOOutputChannelPermissionList parseIOOutChannelPermisssionListJsonObject(JSONObject obj){
        if (obj==null)return null;
        IOOutputChannelPermissionList list = new IOOutputChannelPermissionList();
        try{list.setPage(parsePageJsonObject(obj.getJSONObject("Page")));} catch (JSONException e) {}
        try{
            JSONArray arry = obj.getJSONArray("IOOutputChannelPermission");
            ArrayList<IOOutputChannelPermission> lists = new ArrayList<>();
            for (int i=0;i<arry.length();i++){
                lists.add(parseIOOutChannelPermissionJsonObject(arry.getJSONObject(i)));
            }
            list.setIoOutputChannelPermissions(lists);
        }catch (JSONException e){}
        return list;
    }

    public static DecoderIdentifier parseDecoderIdentifierJsonObject(JSONObject obj) throws JSONException {
        if (obj == null)return null;
        return new DecoderIdentifier(obj.getString("DeviceId"));
    }

    public static VideoSnapIdentifier parseVideoSnapIdentifierJsonObject(JSONObject obj) throws JSONException {
        if (obj == null) return null;
        VideoSnapIdentifier identifier = new VideoSnapIdentifier();
        identifier.setVideoInputChannelId(obj.getString("VideoInputChannelId"));
        identifier.setStreamNo(obj.getInt("StreamNo"));
        try{identifier.setPictureFormat(obj.getString("PictureFormat"));}catch (JSONException e){}
        return identifier;
    }

    public static AudioPlayerIdentifier parseAudioPlayerIdentifierJsonObject(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        AudioPlayerIdentifier identifier = new AudioPlayerIdentifier();
        identifier.setAudioUrl(obj.getString("AudioUrl"));
        try{identifier.setRepeatTimes(obj.getInt("RepeatTimes"));}catch (JSONException e){}
        try {identifier.setDuration(obj.getInt("Duration"));}catch (JSONException e){}
        return identifier;
    }

    public static VideoPlaybackIdentifier parseVideoPlaybackIdentifierJsonObject(JSONObject obj) throws JSONException {
        if (obj == null)return null;
        VideoPlaybackIdentifier identifier = new VideoPlaybackIdentifier();
        identifier.setVideoInputChannelId(obj.getString("VideoInputChannelId"));
        identifier.setStreamNo(obj.getInt("StreamNo"));
        try{identifier.setProtocol(obj.getString("Protocol"));}catch (JSONException e){}
        try{identifier.setBeginTime(obj.getInt("BeginTime"));}catch (JSONException e){}
        try{identifier.setEndTime(obj.getInt("EndTime"));}catch (JSONException e){}
        return identifier;
    }

    public static VideoPreviewIdentifier parseVideoPreviewIdentifierJsonObject(JSONObject obj) throws JSONException {
        if (obj == null)return null;
        VideoPreviewIdentifier identifier = new VideoPreviewIdentifier();
        identifier.setVideoInputChannelId(obj.getString("VideoInputChannelId"));
        identifier.setStreamNo(obj.getInt("StreamNo"));
        try {identifier.setProtocol(obj.getString("Protocol"));}catch (JSONException e){}
        return identifier;
    }

    public static EventLinkage parseEventLinkageJsonObject(JSONObject obj) throws JSONException {
        if (obj == null)return null;
        EventLinkage eventLinkage = new EventLinkage();
        eventLinkage.setComponentId(obj.getString("ComponentId"));
        eventLinkage.setEventType(obj.getString("EventType"));
        eventLinkage.setEventState(obj.getString("EventState"));
        try{
            JSONArray arry = obj.getJSONArray("VideoPreviewIdentifier");
            ArrayList<VideoPreviewIdentifier> identifiers = new ArrayList<>();
            for(int i=0;i<arry.length();i++){
                identifiers.add(parseVideoPreviewIdentifierJsonObject(arry.getJSONObject(i)));
            }
            eventLinkage.setVideoPreviewIdentifiers(identifiers);
        }catch (JSONException e){}
        try{
            JSONArray arry = obj.getJSONArray("VideoPlaybackIdentifier");
            ArrayList<VideoPlaybackIdentifier> identifiers = new ArrayList<>();
            for (int i=0;i<arry.length();i++){
                identifiers.add(parseVideoPlaybackIdentifierJsonObject(arry.getJSONObject(i)));
            }
            eventLinkage.setVideoPlaybackIdentifiers(identifiers);
        }catch (JSONException e){}
        try{eventLinkage.setTextIdentifier(obj.getString("TextIdentifier"));}catch (JSONException e){}
        try{eventLinkage.setAudioPlayerIdentifier(parseAudioPlayerIdentifierJsonObject(obj.getJSONObject("AudioPlayerIdentifier")));}catch (JSONException e){}
        try{
            JSONArray arry = obj.getJSONArray("VideoSnapIdentifier");
            ArrayList<VideoSnapIdentifier> identifiers = new ArrayList<>();
            for (int i=0;i<arry.length();i++){
                identifiers.add(parseVideoSnapIdentifierJsonObject(arry.getJSONObject(i)));
            }
            eventLinkage.setVideoSnapIdentifiers(identifiers);
        }catch (JSONException e){}
        try{
            JSONArray arry = obj.getJSONArray("Executor");
            ArrayList<String> executors = new ArrayList<>();
            for(int i=0;i<arry.length();i++){
                executors.add(arry.getString(i));
            }
            eventLinkage.setExecutors(executors);
        }catch (JSONException e){}
        try{
            JSONArray arry = obj.getJSONArray("DecoderIdentifier");
            ArrayList<DecoderIdentifier> identifiers = new ArrayList<>();
            for (int i=0;i<arry.length();i++){
                identifiers.add(parseDecoderIdentifierJsonObject(arry.getJSONObject(i)));
            }
            eventLinkage.setDecoderIdentifiers(identifiers);
        }catch (JSONException e){}
        return eventLinkage;
    }

    public static EventLinkageList parseEventLinkageListJsonObject(JSONObject obj){
        if (obj==null)return null;
        EventLinkageList list = new EventLinkageList();
        try{list.setPage(parsePageJsonObject(obj.getJSONObject("Page")));}catch (JSONException e){}
        try{
            JSONArray array = obj.getJSONArray("EventLinkage");
            ArrayList<EventLinkage> eventLinkages = new ArrayList<>();
            for (int i=0;i<array.length();i++){
                eventLinkages.add(parseEventLinkageJsonObject(array.getJSONObject(i)));
            }
            list.setEventLinkages(eventLinkages);
        }catch (JSONException e){}
        return list;
    }

    public static SystemWarningStatistics parseSystemWarningJsonObject(JSONObject obj) throws JSONException {
        if (obj == null) return null;
        SystemWarningStatistics statistics = new SystemWarningStatistics();
        statistics.setWarnimgNumber(obj.getInt("WarningNumber"));
        statistics.setCPUUsageNumber(obj.getInt("CPUUsageNumber"));
        statistics.setMemoryUsageNumber(obj.getInt("MemoryUsageNumber"));
        statistics.setNetWrokusageNumber(obj.getInt("NetworkUsageNumber"));
        try{statistics.setSuperheatNumber(obj.getInt("SuperHeatNumber"));}catch (JSONException e){}
        try{statistics.setVoltageInstabilityNumber(obj.getInt("VoltageInstabilityNumber"));}catch (JSONException e){}
        try{statistics.setVideoHighLoadNumber(obj.getInt("VideoHighLoadNumber"));}catch (JSONException e){}
        try{statistics.setVideoNetworkinstabilityNumber(obj.getInt("VideoNetworkInstabilityNumber"));}catch (JSONException e){}
        try{statistics.setTeardownNumber(obj.getInt("TeardownNumber"));}catch (JSONException e){}
        try{statistics.setVideoConnectionFailureNumber(obj.getInt("VideoConnectionFailureNumber"));}catch (JSONException e){}
        return statistics;
    }

    public static SystemFaultStatistics parseSystemFaultStatisticsJsonObject(JSONObject obj) throws JSONException {
        if (obj == null)return null;//fixme StorageMediumNumber
        return new SystemFaultStatistics(obj.getInt("FaultNumber"),obj.getInt("OfflineNumber"),obj.getInt("StorageMediumNumber"),obj.getInt("VideolossNumber"));
    }

    public static SystemHealthStatistics parseSystemHealthStatisticeJsonObject(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        return new SystemHealthStatistics(obj.getString("Id"),
                obj.getDouble("Percentage"),parseSystemFaultStatisticsJsonObject(obj.getJSONObject("Faults")),
                parseSystemWarningJsonObject(obj.getJSONObject("Warnings")));
    }

    public static SystemFaultReport parseSystemFaultReportJsonObject(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        SystemFaultReport report = new SystemFaultReport();
        report.setId(obj.getString("Id"));
        report.setCreationTime(obj.getString("CreationTime"));
        report.setFaultType(obj.getString("FaultType"));
        report.setComponentId(obj.getString("ComponentId"));
        report.setRecovered(obj.getBoolean("Recovered"));
        try{report.setRecoveryTime(obj.getString("RecoveryTime"));}catch (JSONException e){}
        try{report.setDescription(obj.getString("Description"));}catch (JSONException e){}
        try{report.setComponentName(obj.getString("ComponentName"));}catch (JSONException e){}
        return report;
    }

    public static SystemFaultReportList parseSystemFaultReportListJsonObject(JSONObject obj){
        if (obj==null)return null;
        SystemFaultReportList list = new SystemFaultReportList();
        try{list.setPage(parsePageJsonObject(obj.getJSONObject("Page")));}catch (JSONException e){}
        try{
            JSONArray arry = obj.getJSONArray("SystemFaultReport");
            ArrayList<SystemFaultReport> reports = new ArrayList<>();
            for (int i=0;i<arry.length();i++){
                reports.add(parseSystemFaultReportJsonObject(arry.getJSONObject(i)));
            }
            list.setReports(reports);
        }catch (JSONException e){}
        return list;
    }

    public static SystemWarningReport parseSystemWarningReportJsonObject(JSONObject obj) throws JSONException {
        if (obj == null)return null;
        SystemWarningReport report = new SystemWarningReport();
        report.setId(obj.getString("Id"));
        report.setCreationTime(obj.getString("CreationTime"));
        report.setWarningType(obj.getString("WarningType"));
        report.setComponentId(obj.getString("ComponentId"));
        try{report.setDescription(obj.getString("Description"));}catch (JSONException e){}
        try{report.setComponentName(obj.getString("ComponentName"));}catch (JSONException e){}
        return report;
    }

    public static SystemWarningReportList parseSystemWarningReprotListdJsonObject(JSONObject obj){
        if (obj == null)return null;
        SystemWarningReportList list = new SystemWarningReportList();
        try{list.setPage(parsePageJsonObject(obj.getJSONObject("Page")));} catch (JSONException e) {}
        try{
            JSONArray arry = obj.getJSONArray("SystemWarningReport");
            ArrayList<SystemWarningReport> reports = new ArrayList<>();
            for (int i=0;i<arry.length();i++){
                reports.add(parseSystemWarningReportJsonObject(arry.getJSONObject(i)));
            }
            list.setReports(reports);
        }catch (JSONException e){}
        return list;
    }

    public static VideoInputChannelGroup parseVideoInputChannelGroupJsonObject(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        VideoInputChannelGroup group = new VideoInputChannelGroup();
        group.setId(obj.getString("Id"));
        group.setName(obj.getString("Name"));
        try{group.setComment(obj.getString("Comment"));}catch (JSONException e){}
        try{group.setParentId(obj.getString("ParentId"));}catch (JSONException e){}
        return group;
    }

    public static VideoInputChannelGroupList parseVideoInputChannelGroupListJsonObject(JSONObject obj){
        if (obj==null)return null;
        VideoInputChannelGroupList ls = new VideoInputChannelGroupList();
        try{ls.setPage(parsePageJsonObject(obj.getJSONObject("Page")));}catch (JSONException e){}
        try{
            JSONArray arry = obj.getJSONArray("VideoInputChannelGroup");
            ArrayList<VideoInputChannelGroup> groups = new ArrayList<>();
            for (int i=0;i<arry.length();i++){
                groups.add(parseVideoInputChannelGroupJsonObject(arry.getJSONObject(i)));
            }
            ls.setGroups(groups);
        }catch (JSONException e){}
        return ls;
    }

    public static IOInputChannelList parseIOInputChannelListJsonObject(JSONObject obj){
        if (obj==null)return null;
        IOInputChannelList ls = new IOInputChannelList();
        try{ls.setPage(parsePageJsonObject(obj.getJSONObject("Page")));} catch (JSONException e) {}
        try{
            JSONArray array = obj.getJSONArray("IOInputChannel");
            ArrayList<IOInputChannel> channels = new ArrayList<>();
            for(int i=0;i<array.length();i++){
                channels.add(parseIOInputChannelJsonObject(array.getJSONObject(i)));
            }
            ls.setInputChannels(channels);
        }catch (JSONException e){}
        return ls;
    }

    public static IOOutputChannelList parseIOOutputChannelListJsonObject(JSONObject obj){
        if (obj==null)return null;
        IOOutputChannelList ls = new IOOutputChannelList();
        try{ls.setPage(parsePageJsonObject(obj.getJSONObject("Page")));}catch (JSONException e){}
        try{
            JSONArray array = obj.getJSONArray("IOOutputChannel");
            ArrayList<IOOutputChannel> channels = new ArrayList<>();
            for(int i=0;i<array.length();i++){
                channels.add(parseIOOutPutChannelJsonObject(array.getJSONObject(i)));
            }
            ls.setChannels(channels);
        }catch (JSONException e){}
        return ls;
    }

    public static Map parseMapJsonObject(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        Map map = new Map();
        map.setId(obj.getString("Id"));
        map.setName(obj.getString("Name"));
        try{map.setComment(obj.getString("Comment"));}catch (JSONException e){}
        map.setMapFormat(obj.getString("MapFormat"));
        try{map.setMd5Code(obj.getString("MD5Code"));}catch (JSONException e){}
        try{map.setLastModificationTime(obj.getString("LastModificationTime"));}catch (JSONException e){}
        return map;
    }

    public static MapList parseMapListJsonObject(JSONObject obj){
        if (obj ==null) return null;
        MapList list = new MapList();
        try{list.setPage(parsePageJsonObject(obj.getJSONObject("Page")));} catch (JSONException e) {}
        try{
            JSONArray array = obj.getJSONArray("Map");
            ArrayList<Map> maps = new ArrayList<>();
            for (int i=0;i<array.length();i++){
                maps.add(parseMapJsonObject(array.getJSONObject(i)));
            }
            list.setMaps(maps);
        }catch (JSONException e){}
        return list;
    }

    public static MapGroup parseMapGroupJsonObject(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        MapGroup group = new MapGroup();
        group.setId(obj.getString("Id"));
        group.setName(obj.getString("Name"));
        try{group.setComment(obj.getString("Comment"));}catch (JSONException e){}
        try{group.setParentId(obj.getString("ParentId"));}catch (JSONException e){}
        return group;
    }

    public static MapGroupList parseMapgroupListJsonObject(JSONObject obj){
        if (obj == null) return null;
        MapGroupList list = new MapGroupList();
        try{list.setPage(parsePageJsonObject(obj.getJSONObject("Page")));} catch (JSONException e) {}
        try{
            JSONArray array = obj.getJSONArray("MapGroup");
            ArrayList<MapGroup> groups = new ArrayList<>();
            for (int i=0;i<array.length();i++){
                groups.add(parseMapGroupJsonObject(array.getJSONObject(i)));
            }
            list.setMapGroups(groups);
        }catch (JSONException e){}
        return list;
    }

    public static IOInputChannelStatus parseIOInputChannelStatusJsonObject(JSONObject obj) throws JSONException {
        if (obj == null)return null;
        return new IOInputChannelStatus(obj.getString("Id"),obj.getString("State"),obj.getString("ArmType"));
    }

    public static IOInputChannelStatusList parseIOInputChannelStatusListJsonObject(JSONObject obj){
        if (obj==null)return null;
        IOInputChannelStatusList list = new IOInputChannelStatusList();
        try{list.setPage(parsePageJsonObject(obj.getJSONObject("Page")));}catch (JSONException e){}
        try{
            JSONArray array = obj.getJSONArray("IOInputChannelStatus");
            ArrayList<IOInputChannelStatus> statuses = new ArrayList<>();
            for(int i=0;i<array.length();i++){
                statuses.add(parseIOInputChannelStatusJsonObject(array.getJSONObject(i)));
            }
            list.setStatuses(statuses);
        }catch (JSONException e){}
        return list;
    }

    public static IOOutputChannelStatus parseIOOutputChannelStatusJsonObject(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        return new IOOutputChannelStatus(obj.getString("Id"),obj.getString("State"));
    }

    public static IOOutputChannelStatusList parseIOOutputChannelStatusListJsonObject(JSONObject obj){
        if (obj==null)return null;
        IOOutputChannelStatusList list = new IOOutputChannelStatusList();
        try{list.setPage(parsePageJsonObject(obj.getJSONObject("Page")));}catch (JSONException e){}
        try{
            JSONArray array = obj.getJSONArray("IOOutputChannelStatus");
            ArrayList<IOOutputChannelStatus> statuses = new ArrayList<>();
            for (int i=0;i<array.length();i++){
                statuses.add(parseIOOutputChannelStatusJsonObject(array.getJSONObject(i)));
            }
            list.setStatuses(statuses);
        }catch (JSONException e){}
        return list;
    }

    public static Coordinate parseCoordinateJsonObject(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        return new Coordinate(obj.getDouble("X"),obj.getDouble("Y"));
    }

    public static MapItem parseMapItemJsonObject(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        return new MapItem(obj.getString("Id"),
                obj.getString("ItemType"),
                obj.getString("ComponentId"),
                parseCoordinateJsonObject(obj.getJSONObject("Coordinate")));
    }

    public static MapItemList parseMapItemListJsonObject(JSONObject obj){
        if (obj==null)return null;
        MapItemList list = new MapItemList();
        try{list.setPage(parsePageJsonObject(obj.getJSONObject("Page")));}catch (JSONException e){}
        try{
            JSONArray array = obj.getJSONArray("MapItem");
            ArrayList<MapItem> mapItems = new ArrayList<>();
            for (int i=0;i<array.length();i++){
                mapItems.add(parseMapItemJsonObject(array.getJSONObject(i)));
            }
            list.setMapItems(mapItems);
        }catch (JSONException e){}
        return list;
    }

    public static EventRecordedFile parseEventRecordedFileJsonObject(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        EventRecordedFile file = new EventRecordedFile();
        file.setRecordedFileId(obj.getString("RecordedFileId"));
        try{file.setRecordedFileTimestamp(obj.getLong("RecordedFileTimestamp"));}catch (JSONException e){}
        return file;
    }

    public static EventRecord parseEventRecordedJsonObject(JSONObject obj) throws JSONException {
        if (obj == null)return null;
        EventRecord record = new EventRecord();
        record.setId(obj.getString("Id"));
        record.setComponentId(obj.getString("ComponentId"));
        record.setName(obj.getString("Name"));
        record.setEventType(obj.getString("EventType"));
        record.setAlarmTime(obj.getString("AlarmTime"));
        record.setSeverity(obj.getInt("Severity"));
        try{record.setDisalarmTime(obj.getString("DisalarmTime"));}catch (JSONException e){}
        try{record.setProcessTime(obj.getString("ProcessTime"));}catch (JSONException e){}
        try{record.setProcessDescription(obj.getString("ProcessDescription"));}catch (JSONException e){}
        try{record.setDescription(obj.getString("Description"));}catch (JSONException e){}
        try{record.setObjectType(obj.getString("ObjectType"));}catch (JSONException e){}
        try{record.setTriggerValue(obj.getDouble("TriggerValue"));}catch (JSONException e){}
        try{
            JSONArray array = obj.getJSONArray("PictureId");
            ArrayList<String> ids = new ArrayList<>();
            for(int i=0;i<array.length();i++){
                ids.add(array.getString(i));
            }
            record.setPictureId(ids);
        }catch (JSONException e){}
        try{
            JSONArray array = obj.getJSONArray("RecordedFile");
            ArrayList<EventRecordedFile> files = new ArrayList<>();
            for(int i=0;i<array.length();i++){
                files.add(parseEventRecordedFileJsonObject(array.getJSONObject(i)));
            }
            record.setEventRecordedFiles(files);
        }catch (JSONException e){}
        return record;
    }

    public static EventRecordedList parseEventRecordListJsonObject(JSONObject obj){
        if (obj==null)return null;
        EventRecordedList list = new EventRecordedList();
        try{list.setPage(parsePageJsonObject(obj.getJSONObject("Page")));}catch (JSONException e){}
        try{
            JSONArray array = obj.getJSONArray("EventRecord");
            ArrayList<EventRecord> records = new ArrayList<>();
            for(int i=0;i<array.length();i++){
                records.add(parseEventRecordedJsonObject(array.getJSONObject(i)));
            }
            list.setRecords(records);
        }catch (JSONException e){}
        return list;
    }

    public static PreviewTask parsePreviewTaskJsonObject(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        PreviewTask task = new PreviewTask();
        task.setTaskId(obj.getString("TaskId"));
        task.setVideoInputChannelId(obj.getString("VideoInputChannelId"));
        task.setUrl(obj.getString("Url"));
        task.setProtocol(obj.getString("Protocol"));
        try{task.setSDP(obj.getString("SDP"));}catch (JSONException e){}
        return task;
    }

    public static JSONObject createPreviewTask(String videoInputChannelId,int streamNo) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("VideoInputChannelId",videoInputChannelId);
        obj.put("StreamNo",streamNo);
        return obj;
    }

    public static PlaybackTask parsePlaybackTaskJsonObject(JSONObject obj) throws JSONException {
        if (obj == null)return null;
        PlaybackTask task = new PlaybackTask();
        task.setTaskId(obj.getString("TaskId"));
        task.setVideoInputChannelId(obj.getString("VideoInputChannelId"));
        task.setUrl(obj.getString("Url"));
        task.setProtocol(obj.getString("Protocol"));
        try{task.setSDP(obj.getString("SDP"));}catch (JSONException e){}
        return task;
    }

    public static JSONObject createPlaybackTask(String videoInputChannelId,int streamNo,String beginTime,String endTime) throws JSONException {
        return new JSONObject().put("VideoInputChannelId",videoInputChannelId).put("StreamNo",streamNo).put("BeginTime",beginTime).put("EndTime",endTime);
    }

    public static DownloadTask parseDownloadTaskJsonObject(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        DownloadTask task = new DownloadTask();
        task.setTaskId(obj.getString("TaskId"));
        task.setVideoInputChannelId(obj.getString("VideoInputChannelId"));
        task.setUrl(obj.getString("Url"));
        task.setProtocol(obj.getString("Protocol"));
        try{task.setSDP(obj.getString("SDP"));}catch (JSONException e){}
        return task;
    }

    public static JSONObject createDownloadTask(String videoId,int streamNo,String begTime,String endTime) throws JSONException {
        return new JSONObject().put("VideoInputChannelId",videoId).put("StreamNo",streamNo).put("BeginTime",begTime).put("EndTime",endTime);
    }

    public static Visitor parseVistorJsonObject(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        Visitor visitor = new Visitor();
        visitor.setId(obj.getString("Id"));
        visitor.setName(obj.getString("Name"));
        visitor.setIdNumberType(obj.getString("IdNumberType"));
        visitor.setIdNumber(obj.getString("IdNumber"));
        visitor.setSex(obj.getString("Sex"));
        visitor.setAge(obj.getInt("Age"));
        visitor.setNationality(obj.getString("Nationality"));
        visitor.setNation(obj.getString("Nation"));
        try{visitor.setDescription(obj.getString("Description"));}catch (JSONException e){}
        try{visitor.setMobile(obj.getString("Mobile"));}catch (JSONException e){}
        try{visitor.setVisitedCount(obj.getInt("VisitedCount"));}catch (JSONException e){}
        return visitor;
    }

    public static VisitorList parseVistorListJsonObject(JSONObject obj){
        if (obj==null)return null;
        VisitorList list = new VisitorList();
        try{list.setPage(parsePageJsonObject(obj.getJSONObject("Page")));}catch (JSONException e){}
        try{
            JSONArray array = obj.getJSONArray("Visitor");
            ArrayList<Visitor> visitors = new ArrayList<>();
            for(int i=0;i<array.length();i++){
                visitors.add(parseVistorJsonObject(array.getJSONObject(i)));
            }
            list.setVisitors(visitors);
        }catch (JSONException e){}
        return list;
    }

    public static VisitorRecord parseVistorRecordJsonObject(JSONObject obj) throws JSONException {
        if (obj == null)return null;
        VisitorRecord record = new VisitorRecord();
        record.setId(obj.getString("Id"));
        record.setEntryTime(obj.getString("EntryTime"));
        record.setDepartureTime(obj.getString("DepartureTime"));
        try{record.setVisitedUnit(obj.getString("VisitedUnit"));}catch (JSONException e){}
        try{record.setVisitedStaff(obj.getString("VisitedStaff"));}catch (JSONException e){}
        try{record.setVisitedDepartment(obj.getString("VisitedDepartment"));}catch (JSONException e){}
        record.setPermissionLevel(obj.getInt("PermissionLevel"));
        try{record.setDescription(obj.getString("Description"));}catch (JSONException e){}
        record.setVisitorRecordNumber(obj.getString("VisitorRecordNumber"));
        record.setVisitorCount(obj.getInt("VisitorCount"));
        record.setVisitor(parseVistorJsonObject(obj.getJSONObject("Visitor")));
        return record;
    }

    public static VisitorRecordList parseVisitorRecordListJsonObject(JSONObject obj){
        if (obj==null)return null;
        VisitorRecordList list = new VisitorRecordList();
        try{list.setPage(parsePageJsonObject(obj.getJSONObject("Page")));}catch (JSONException e){}
        try{
            JSONArray array = obj.getJSONArray("VisitorRecord");
            ArrayList<VisitorRecord> records = new ArrayList<>();
            for (int i=0;i<array.length();i++){
                records.add(parseVistorRecordJsonObject(array.getJSONObject(i)));
            }
            list.setVisitorRecords(records);
        }catch (JSONException e){}
        return list;
    }

    public static VideoInputChannelList parseVideoInputChannelListJsonObject(JSONObject obj){
        if (obj==null)return null;
        VideoInputChannelList list = new VideoInputChannelList();
        try{list.setPage(parsePageJsonObject(obj.getJSONObject("Page")));}catch (JSONException e){}
        try{
            JSONArray array = obj.getJSONArray("VideoInputChannel");
            ArrayList<VideoInputChannel> channels = new ArrayList<>();
            for (int i=0;i<array.length();i++){
                channels.add(parseVideoInputChannelJsonObject(array.getJSONObject(i)));
            }
            list.setChannels(channels);
        } catch (JSONException e){}
        return list;
    }

    public static VideoOutputChannelList parseVideoOutputChannelListJsonObject(JSONObject obj){
        if (obj == null)return null;
        VideoOutputChannelList list = new VideoOutputChannelList();
        try{list.setPage(parsePageJsonObject(obj.getJSONObject("Page")));}catch (JSONException e){}
        try{
            JSONArray array = obj.getJSONArray("VideoOutputChannel");
            ArrayList<VideoOutputChannel> channels = new ArrayList<>();
            Log.e("123","array.len="+array.length());
            for(int i=0;i<array.length();i++){
                channels.add(parseVideoOutputChannelJsonObject(array.getJSONObject(i)));
            }
            list.setChannels(channels);
        }catch (JSONException e){}
        return list;
    }




    public static DeviceList parseDeviceListJsonObject(JSONObject obj){
        if (obj==null)return null;
        DeviceList list = new DeviceList();
        try{list.setPage(parsePageJsonObject(obj.getJSONObject("Page")));}catch (JSONException e){}
        try{
            JSONArray array = obj.getJSONArray("Device");
            ArrayList<Device> devices = new ArrayList<>();
            for (int i=0;i<array.length();i++){
                devices.add(parseDeviceJsonObject(array.getJSONObject(i)));
            }
            list.setDevices(devices);
        }catch (JSONException e){}
        return list;
    }

    public static NetworkInterface parseNetworkInterfaceJsonObject(JSONObject obj) throws JSONException {
        if (obj == null)return null;
        NetworkInterface networkInterface = new NetworkInterface();
        networkInterface.setId(obj.getString("Id"));
        networkInterface.setInterfacePort(obj.getInt("InterfacePort"));
        networkInterface.setIpVersion(obj.getString("IPVersion"));
        networkInterface.setAddressType(obj.getString("AddressingType"));
        networkInterface.setIpAddress(obj.getString("IPAddress"));
        networkInterface.setPhyscialAddress(obj.getString("PhyscialAddress"));
        try{networkInterface.setCableType(obj.getString("CableType"));}catch (JSONException e){}
        try{networkInterface.setSpeedDuplex(obj.getString("SpeedDuplex"));}catch (JSONException e){}
        try{networkInterface.setWorkMode(obj.getString("WorkMode"));}catch (JSONException e){}
        try{networkInterface.setWireless(obj.getString("Wireless"));}catch (JSONException e){}
        try{networkInterface.setMTU(obj.getInt("MTU"));}catch (JSONException e){}
        return networkInterface;
    }

    public static NetworkInterfaceList parseNetworkInterfaceListJsonObject(JSONObject obj){
        if (obj == null) return null;
        NetworkInterfaceList list = new NetworkInterfaceList();
        try{list.setPage(parsePageJsonObject(obj.getJSONObject("Page")));}catch (JSONException e){}
        try{
            JSONArray array = obj.getJSONArray("NetworkInterface");
            ArrayList<NetworkInterface> interfaces = new ArrayList<>();
            for (int i=0;i<array.length();i++){
                interfaces.add(parseNetworkInterfaceJsonObject(array.getJSONObject(i)));
            }
            list.setInterfaces(interfaces);
        }catch (JSONException e){}
        return list;
    }

    public static StorageMedium parseStorageMediumJsonObject(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        StorageMedium storageMedium = new StorageMedium();
        storageMedium.setId(obj.getString("Id"));
        storageMedium.setStoragePort(obj.getInt("StoragePort"));
        storageMedium.setMediumType(obj.getString("MediumType"));
        try{storageMedium.setManufacturer(obj.getString("Manufacturer"));}catch (JSONException e){}
        try{storageMedium.setModel(obj.getString("Model"));}catch (JSONException e){}
        storageMedium.setCapacity(obj.getLong("Capacity"));
        storageMedium.setFreespace(obj.getLong("Freespace"));
        try{storageMedium.setStorageType(obj.getString("StorageType"));}catch (JSONException e){}
        return storageMedium;
    }

    public static StoragemediumList parseStorageMediumListJsonObject(JSONObject obj){
        if (obj==null)return null;
        StoragemediumList list = new StoragemediumList();
        try{list.setPage(parsePageJsonObject(obj.getJSONObject("Page")));}catch (JSONException e){}
        try{
            JSONArray array = obj.getJSONArray("StorageMedium");
            ArrayList<StorageMedium> storageMedia = new ArrayList<>();
            for(int i=0;i<array.length();i++){
                storageMedia.add(parseStorageMediumJsonObject(array.getJSONObject(i)));
            }
            list.setStorageMedias(storageMedia);
        }catch (JSONException e){}
        return list;
    }

    public static DecodingChannelList parseDecodingChannelListJsonObject(JSONObject obj){
        if (obj==null)return null;
        DecodingChannelList list = new DecodingChannelList();
        try{list.setPage(parsePageJsonObject(obj.getJSONObject("Page")));}catch (JSONException e){}
        try{
            JSONArray array = obj.getJSONArray("DecodingChannel");
            ArrayList<DecodingChannel> channels = new ArrayList<>();
            for (int i=0;i<array.length();i++){
                channels.add(parseDecodingChannelJsonobject(array.getJSONObject(i)));
            }
            list.setChannels(channels);
        }catch (JSONException e){}
        return list;
    }

    public static IOOutputChannelList parseIOOutputChannelListJsonObjcet(JSONObject obj){
        if (obj==null)return null;
        IOOutputChannelList list = new IOOutputChannelList();
        try{list.setPage(parsePageJsonObject(obj.getJSONObject("Page")));}catch (JSONException e){}
        try{
            JSONArray array = obj.getJSONArray("IOOutputChannel");
            ArrayList<IOOutputChannel> channels = new ArrayList<>();
            for(int i=0;i<array.length();i++){
                channels.add(parseIOOutPutChannelJsonObject(array.getJSONObject(i)));
            }
            list.setChannels(channels);
        }catch (JSONException e){}
        return list;
    }


    public static DeviceDetails parseDeviceDetailsJsonObject(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        DeviceDetails details = new DeviceDetails();
        details.setId(obj.getString("Id"));
        details.setName(obj.getString("Name"));
        try{details.setManufacturer(obj.getString("Manufacturer"));}catch (JSONException e){}
        try{details.setModel(obj.getString("Model"));}catch (JSONException e){}
        try{details.setFirmware(obj.getString("Firmware"));}catch (JSONException e){}
        try{details.setSerialNumber(obj.getString("SerialNumber"));}catch (JSONException e){}
        try{details.setPointOfSale(obj.getString("PointOfSale"));}catch (JSONException e){}
        try{details.setInformation(obj.getString("Information"));}catch (JSONException e){}
        try{details.setUsername(obj.getString("Username"));}catch (JSONException e){}
        try{details.setPasswork(obj.getString("Password"));}catch (JSONException e){}
        details.setUri(obj.getString("Uri"));
        details.setClassification(obj.getString("Classification"));
        details.setProtocolType(obj.getString("ProtocolType"));
        try{details.setParentDeviceId(obj.getString("ParentDeviceId"));}catch (JSONException e){}
        try{details.setBusAddress(obj.getString("BusAddress"));}catch (JSONException e){}
        details.setHasSubDevice(obj.getBoolean("HasSubDevice"));
        try{details.setVideoInputChannelList(parseVideoInputChannelListJsonObject(obj.getJSONObject("VideoInputChannelList")));}catch (JSONException e){}
        try{details.setVideoOutputChannelList(parseVideoOutputChannelListJsonObject(obj.getJSONObject("VideoOutputChannelList")));}catch (JSONException e){}
        try{details.setIoInputChannelList(parseIOInputChannelListJsonObject(obj.getJSONObject("IOInputChannelList")));}catch (JSONException e){}
        try{details.setIoOutputChannelList(parseIOOutputChannelListJsonObjcet(obj.getJSONObject("IOOutputChannelList")));}catch (JSONException e){}
        try{details.setNetworkInterfaceList(parseNetworkInterfaceListJsonObject(obj.getJSONObject("NetworkInterfaceList")));}catch (JSONException e){}
        try{details.setStoragemediumList(parseStorageMediumListJsonObject(obj.getJSONObject("StorageMediumList")));}catch (JSONException e){}
        try{details.setDecodingChannelList(parseDecodingChannelListJsonObject(obj.getJSONObject("DecodingChannelList")));}catch (JSONException e){}
        try{details.setAbilities(obj.getString("Abilities"));}catch (JSONException e){}
        try{details.setRatedVoltage(obj.getDouble("RatedVoltage"));}catch (JSONException e){}
        try{details.setMaximumUserConnectionsNumber(obj.getInt("MaximumUserConnectionsNumber"));}catch (JSONException e){}
        try{details.setMaximumVideoConnectionsNumber(obj.getInt("MaximumVideoConnectionsNumber"));}catch (JSONException e){}
        return details;
    }

    public static DeviceStatus parseDeviceStatusJsonObject(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        DeviceStatus deviceStatus = new DeviceStatus();
        deviceStatus.setId(obj.getString("Id"));
        deviceStatus.setCpuUsage(obj.getDouble("CpuUsage"));
        deviceStatus.setMemoryUsage(obj.getDouble("MemoryUsage"));
        deviceStatus.setWorkingSeconds(obj.getLong("WorkingSeconds"));
        try{deviceStatus.setTemperature(obj.getDouble("Temperature"));}catch (JSONException e){}
        try{deviceStatus.setPressure(obj.getDouble("Pressure"));}catch (JSONException e){}
        try{deviceStatus.setVoltage(obj.getDouble("Voltage"));}catch (JSONException e){}
        try{deviceStatus.setNetworkSpeedRate(obj.getDouble("NetworkSpeedRate"));}catch (JSONException e){}
        try{deviceStatus.setNetworkUsage(obj.getDouble("NetworkUsage"));}catch (JSONException e){}
        try{deviceStatus.setVideoConnectionNumber(obj.getInt("VideoConnectionNumber"));}catch (JSONException e){}
        try{deviceStatus.setStorageMediumAbnormalNumber(obj.getInt("StorageMediumAbnormalNumber"));}catch (JSONException e){}
        deviceStatus.setOnline(obj.getBoolean("Online"));
        deviceStatus.setLastOnlineTime(obj.getString("LastOnlineTime"));
        try{deviceStatus.setWifiIntensity(obj.getInt("WiFiIntensity"));}catch (JSONException e){}
        try{deviceStatus.setBatteryRemaining(obj.getInt("BatteryRemaining"));}catch (JSONException e){}
        return deviceStatus;
    }

    public static DeviceProtocolCapabilities parseDeviceProtocolCapabilitiesJsonObject(JSONObject obj) throws JSONException {
        if (obj == null)return null;
        DeviceProtocolCapabilities capabilities = new DeviceProtocolCapabilities();
        capabilities.setProtocolType(obj.getString("ProtocolType"));
        try{capabilities.setComment(obj.getString("Comment"));}catch (JSONException e){}
        capabilities.setUriFormat(obj.getString("UriFormat"));
        capabilities.setHasVideoInputChannel(obj.getBoolean("HasVideoInputChannel"));
        capabilities.setHasVideoOutputChannel(obj.getBoolean("HasVideoOutputChannel"));
        capabilities.setHasIOInputChannel(obj.getBoolean("HasIOInputChannel"));
        capabilities.setHasIOOutputchannel(obj.getBoolean("HasIOOutputChannel"));
        capabilities.setHasNetworkInterface(obj.getBoolean("HasNetworkInterface"));
        capabilities.setHasStorageMedium(obj.getBoolean("HasStorageMedium"));
        capabilities.setSupportedSearch(obj.getBoolean("SupportedSearch"));
        return capabilities;
    }

    public static DeviceClassificationCapabilities parseDeviceClassificationCapabilitiesJsonObject(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        DeviceClassificationCapabilities capabilities = new DeviceClassificationCapabilities();
        capabilities.setClassification(obj.getString("Classification"));
        try{
            JSONArray array = obj.getJSONArray("DeviceProtocolCapabilities");
            ArrayList<DeviceProtocolCapabilities> protocolCapabilities = new ArrayList<>();
            for (int i=0;i<array.length();i++){
                protocolCapabilities.add(parseDeviceProtocolCapabilitiesJsonObject(array.getJSONObject(i)));
            }
            capabilities.setCapabilities(protocolCapabilities);
        }catch (JSONException e){}
        return capabilities;
    }

    public static JSONObject createProcessingResult(ProcessingResult result) throws JSONException {
        return new JSONObject().put("Description",result.getDescription());
    }

    public static JSONObject createIOOutputChannelStatus(IOOutputChannelStatus status) throws JSONException {
        return new JSONObject().put("Id",status.getId()).put("State",status.getState());
    }

    public static DeviceInformation parseDeviceInformationJsonObject(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        DeviceInformation information = new DeviceInformation();
        information.setId(obj.getString("Id"));
        information.setAuthenticationCode(obj.getString("AuthenticationCode"));
        information.setSystemTime(obj.getString("SystemTime"));
        information.setClassification(obj.getString("Classification"));
        information.setManufacturer(obj.getString("Manufacturer"));
        information.setModel(obj.getString("Model"));
        information.setFirmware(obj.getString("Firmware"));
        information.setSoftware(obj.getString("Software"));
        information.setSerialNumber(obj.getString("SerialNumber"));
        try{information.setInfrared(obj.getBoolean("Infrared")); }catch (JSONException e){}
        try{information.setWireless(obj.getBoolean("Wireless"));}catch (JSONException e){}
        try{information.setTemperatureSensor(obj.getBoolean("TemperatureSensor"));}catch (JSONException e){}
        try{information.setPressureSensor(obj.getBoolean("PressureSensor"));}catch (JSONException e){}
        try{information.setStorable(obj.getBoolean("Storable"));}catch (JSONException e){}
        try{information.setRatedVoltage(obj.getDouble("RatedVoltage"));}catch (JSONException e){}
        try{information.setMaximumUserConnectionsNumber(obj.getInt("MaximumUserConnectionsNumber"));}catch (JSONException e){}
        try{information.setMaximumVideoConnectionsNumber(obj.getInt("MaximumVideoConnectionsNumber"));}catch (JSONException e){}
        try{information.setServiceUrl(obj.getString("ServiceUrl"));}catch (JSONException e){}
        return information;
    }

    public static SystemTime parseSystemTimeJsonObject(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        SystemTime systemTime = new SystemTime();
        systemTime.setTimeMode(obj.getString("TimeMode"));
        systemTime.setLocalTime(obj.getString("LocalTime"));
        try{systemTime.setTimeZone(obj.getString("TimeZone"));}catch (JSONException e){}
        return systemTime;
    }

    public static NTPServer parseNTPServerJsonObject(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        NTPServer server = new NTPServer();
        server.setId(obj.getString("Id"));
        try{server.setHostName(obj.getString("HostName"));}catch (JSONException e){}
        try{server.setIpAddress(obj.getString("IPAddress"));}catch (JSONException e){}
        try{server.setIpv6Address(obj.getString("IPv6Address"));}catch (JSONException e){}
        server.setPort(obj.getInt("Port"));
        return server;
    }

    public static NTPServerList parseNTPServerListJsonObject(JSONObject obj){
        if (obj==null)return null;
        NTPServerList list = new NTPServerList();
        try{list.setPage(parsePageJsonObject(obj.getJSONObject("Page")));}catch (JSONException e){}
        try{
            JSONArray array = obj.getJSONArray("NTPServer");
            ArrayList<NTPServer> servers = new ArrayList<>();
            for(int i=0;i<array.length();i++){
                servers.add(parseNTPServerJsonObject(array.getJSONObject(i)));
            }
            list.setServers(servers);
        }catch (JSONException e){}
        return list;
    }

    public static WifiInformation parseDeviceWifiInformationJsonObject(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        return new WifiInformation(obj.getString("Name"),obj.getString("Password"),obj.getInt("Channel"),obj.getInt("Intensity"));
    }

    public static ClientAddress parseClientAddressJsonObject(JSONObject obj){
        if (obj==null)return null;
        ClientAddress clientAddress = new ClientAddress();
        try{clientAddress.setIpv4(obj.getString("IPv4Address"));}catch (JSONException e){}
        try{clientAddress.setIpv6(obj.getString("IPv6Address"));}catch (JSONException e){}
        return clientAddress;
    }

    public static StreamingSessionStatus parseStreamingSessionStatusJsonObject(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        StreamingSessionStatus status = new StreamingSessionStatus();
        status.setClientAddress(parseClientAddressJsonObject(obj.getJSONObject("ClientAddress")));
        try{status.setClientUserName(obj.getString("ClientUserName"));}catch (JSONException e){}
        try{status.setStartDateTime(obj.getString("StartDateTime"));}catch (JSONException e){}
        try{status.setElapsedSeconds(obj.getInt("ElapsedSeconds"));}catch (JSONException e){}
        try{status.setBandwidth(obj.getDouble("Bandwidth"));}catch (JSONException e){}
        return status;
    }

    public static StreamingStatus parseStreamingStatusJsonObject(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        StreamingStatus status = new StreamingStatus();
        status.setNo(obj.getInt("No"));
        status.setBitrate(obj.getDouble("Bitrate"));
        try{status.setTotalpacket(obj.getLong("TotalPacket"));}catch (JSONException e){}
        try{status.setLostPacket(obj.getLong("LostPacket"));}catch (JSONException e){}
        try{status.setErrorPacket(obj.getLong("ErrorPacket"));}catch (JSONException e){}
        try{status.setReceivePacket(obj.getLong("ReceivedPacket"));}catch (JSONException e){}
        try{
            JSONArray array = obj.getJSONArray("StreamingSessionStatus");
            ArrayList<StreamingSessionStatus> statuses = new ArrayList<>();
            for (int i=0;i<array.length();i++){
                statuses.add(parseStreamingSessionStatusJsonObject(array.getJSONObject(i)));
            }
            status.setSessionStatuses(statuses);
        }catch (JSONException e){}
        return status;
    }

    public static VideoInputChannelStatus parseVideoInputChannelStatusJsonObject(JSONObject obj) throws JSONException {
        if (obj == null)return null;
        VideoInputChannelStatus status = new VideoInputChannelStatus();
        status.setId(obj.getString("Id"));
        try{status.setIrCutState(obj.getString("IRCutState"));}catch (JSONException e){}
        try{status.setDayNightState(obj.getString("DayNightState"));}catch (JSONException e){}
        try{status.setSignalState(obj.getString("SignalState"));}catch (JSONException e){}
        try{status.setRecordState(obj.getString("RecordState"));}catch (JSONException e){}
        try{status.setShutter(obj.getInt("Shutter"));}catch (JSONException e){}
        try{status.setLuminance(obj.getDouble("Luminance"));}catch (JSONException e){}
        try{status.setNetworkState(obj.getString("NetworkState"));}catch (JSONException e){}
        try{
            JSONArray array = obj.getJSONArray("StreamingStatus");
            ArrayList<StreamingStatus> statuses = new ArrayList<>();
            for (int i=0;i<array.length();i++){
                statuses.add(parseStreamingStatusJsonObject(array.getJSONObject(i)));
            }
            status.setStreamingStatuses(statuses);
        }catch (JSONException e){}
        return status;
    }

    public static ArrayList<StreamingChannel> parseVideoInputChannelStreamingJsonObject(JSONArray arry) throws JSONException {
        if (arry==null)return null;
        ArrayList<StreamingChannel> channels = new ArrayList<>();
//        JSONArray array = obj.getJSONArray("")
        for (int i=0;i<arry.length();i++){
            channels.add(parseStreamingChannelJsonObject(arry.getJSONObject(i)));
        }
        return channels;
    }

    public static PreviewSource parsePreviewSourceJsonObject(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        PreviewSource source = new PreviewSource();
        source.setId(obj.getString("Id"));
        try{source.setPreviewFromId(obj.getString("PreviewFromId"));}catch (JSONException e){}
        try{source.setPreviewFromUrl(obj.getString("PreviewFromUrl"));}catch (JSONException e){}
        return source;
    }
    public static PreviewSourceList parsePreviewSourceListJsonObject(JSONObject obj){
        if (obj==null)return null;
        PreviewSourceList list = new PreviewSourceList();
        try{list.setPage(parsePageJsonObject(obj.getJSONObject("Id")));}catch (JSONException e){}
        try{
            JSONArray array = obj.getJSONArray("PreviewSource");
            ArrayList<PreviewSource> sources = new ArrayList<>();
            for (int i=0;i<array.length();i++){
                sources.add(parsePreviewSourceJsonObject(array.getJSONObject(i)));
            }
            list.setSources(sources);
        }catch (JSONException e){}
        return list;
    }

    public static PlaybackSource parsePlaybackSourceJsonObject(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        PlaybackSource source = new PlaybackSource();
        source.setId(obj.getString("Id"));
        try{source.setPlaybackFromId(obj.getString("PlaybackFromId"));}catch (JSONException e){}
        try{source.setPlaybakcFromUrl(obj.getString("PlaybackFromUrl"));}catch (JSONException e){}
        return source;
    }

    public static PlaybackSourceList parsePlaybackSourceListJsonObject(JSONObject obj){
        if (obj == null)return null;
        PlaybackSourceList list = new PlaybackSourceList();
        try{list.setPage(parsePageJsonObject(obj.getJSONObject("Page")));}catch (JSONException e){}
        try{
            JSONArray array = obj.getJSONArray("PlaybackSource");
            ArrayList<PlaybackSource> sources = new ArrayList<>();
            for(int i=0;i<array.length();i++){
                sources.add(parsePlaybackSourceJsonObject(array.getJSONObject(i)));
            }
            list.setSources(sources);
        }catch (JSONException e){}
        return list;
    }

    public static VideoEffect parseVideoInputChannelEffectJsonObject(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        return new VideoEffect(obj.getDouble("Brightness"),obj.getDouble("Contrast"),obj.getDouble("Saturation"),obj.getDouble("Hue"));
    }

    public static PTZDecoder parseVideoInputChannelDecoderJsonObject(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        PTZDecoder decoder = new PTZDecoder();
        decoder.setBaudRate(obj.getInt("BaudRate"));
        decoder.setDataBit(obj.getInt("DataBit"));
        decoder.setStopBit(obj.getString("StopBit"));
        decoder.setParity(obj.getString("Parity"));
        try{decoder.setDtrEanble(obj.getBoolean("DtrEnable"));}catch (JSONException e){}
        try{decoder.setRtsEnable(obj.getBoolean("RtsEnable"));}catch (JSONException e){}
        decoder.setProtocol(obj.getString("Protocol"));
        decoder.setAddress(obj.getInt("Address"));
        return decoder;
    }

    public static PTZProtocol parsePTZProtocolJsonObject(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        PTZProtocol protocol = new PTZProtocol();
        protocol.setProtocol(obj.getString("Protocol"));
        try{protocol.setDescription(obj.getString("Description"));}catch (JSONException e){}
        return protocol;
    }

    public static PTZProtocolList parsePTZProtocolListJsonObject(JSONObject obj){
        if (obj==null)return null;
        PTZProtocolList list = new PTZProtocolList();
        try {list.setPage(parsePageJsonObject(obj.getJSONObject("Page")));}catch (JSONException e){}
        try{
            JSONArray array = obj.getJSONArray("PTZProtocol");
            ArrayList<PTZProtocol> protocols = new ArrayList<>();
            for (int i=0;i<array.length();i++){
                protocols.add(parsePTZProtocolJsonObject(array.getJSONObject(i)));
            }
            list.setPtzProtocols(protocols);
        }catch (JSONException e){}
        return list;
    }

    public static JSONObject createPTZDirection(PTZControl control) throws JSONException {
        return new JSONObject().put("Control",control.getControlString()).put("Speed",control.getSpeed());
    }

    public static JSONObject createInputChannelGroup(VideoInputChannelGroup group) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("Id",group.getId());
        obj.put("Name",group.getName());
        if (group.getComment()!=null)obj.put("Comment",group.getComment());
        if (group.getParentId()!=null)obj.put("ParentId",group.getParentId());
        return obj;
    }

    public static JSONObject createMapGroup(MapGroup group) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("Id",group.getId());
        obj.put("Name",group.getName());
        if (group.getComment()!=null)obj.put("Comment",group.getComment());
        if (group.getParentId()!=null)obj.put("ParentId",group.getParentId());
        return obj;
    }

    public static JSONObject createIOInputChannelStatus(IOInputChannelStatus status) throws JSONException {
        return new JSONObject().put("Id",status.getId()).put("State",status.getState()).put("ArmType",status.getArmType());
    }

    public static JSONObject createPTZLens(PTZLens lens) throws JSONException {
        return new JSONObject().put("Control",lens.getPtzLen().getVal()).put("Speed",lens.getSpeed());
    }

    public static JSONObject createPTZPreset(PTZPreset preset) throws JSONException {
        return new JSONObject().put("Control",preset.getPreset().getVal()).put("PresetNo",preset.getPresetNo()).put("Speed",preset.getSpeed());
    }

    public static BitrateStatus parseBitrateStatusJsonObject(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        BitrateStatus status = new BitrateStatus();
        status.setId(obj.getString("Id"));
        status.setBitrate(obj.getDouble("Bitrate"));
        try{status.setTotalPacket(obj.getLong("TotalPacket"));}catch (JSONException e){}
        try{status.setLostPacket(obj.getLong("LostPacket"));}catch (JSONException e){}
        try{status.setErrorPacket(obj.getLong("ErrorPacket"));}catch (JSONException e){}
        try{status.setReceivedPacket(obj.getLong("ReceivedPacket"));}catch (JSONException e){}
        try{status.setSessionID(obj.getString("SessionID"));}catch (JSONException e){}
        return status;
    }

    public static BitrateStatusList parseBitrateStatusListJsonObject(JSONObject obj){
        if (obj==null)return null;
        BitrateStatusList list = new BitrateStatusList();
        try{list.setPage(parsePageJsonObject(obj.getJSONObject("Page")));}catch (JSONException e){}
        try{
            JSONArray array = obj.getJSONArray("BitrateStatus");
            ArrayList<BitrateStatus> statuses = new ArrayList<>();
            for (int i=0;i<array.length();i++){
                statuses.add(parseBitrateStatusJsonObject(array.getJSONObject(i)));
            }
            list.setStatus(statuses);
        }catch (JSONException e){}
        return list;
    }

    public static VideoInputExtendedData parseVideoInputExtendedDataJsonObject(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        VideoInputExtendedData data = new VideoInputExtendedData();
        data.setId(obj.getString("Id"));
        data.setName(obj.getString("Name"));
        data.setExtendedType(obj.getInt("ExtendedType"));
        try{
            JSONArray array = obj.getJSONArray("VideoInputChannel");
            ArrayList<VideoInputChannel> channels = new ArrayList<>();
            for (int i=0;i<array.length();i++){
                channels.add(parseVideoInputChannelJsonObject(array.getJSONObject(i)));
            }
            data.setChannels(channels);
        }catch (JSONException e){}
        return data;
    }

    public static VideoInputExtendedDataList parseVideoInputExtendedDataListJsonObject(JSONObject obj) {
        if (obj == null) return null;
        VideoInputExtendedDataList list = new VideoInputExtendedDataList();
        try {
            list.setPage(parsePageJsonObject(obj.getJSONObject("Page")));
        } catch (JSONException e) {
        }
        try {
            JSONArray array = obj.getJSONArray("VideoInputExtendedData");
            ArrayList<VideoInputExtendedData> datas = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                datas.add(parseVideoInputExtendedDataJsonObject(array.getJSONObject(i)));
            }
            list.setExtendedDatas(datas);
        } catch (JSONException e) {}
        return list;
    }

    public static DecodingChannelStatus parseDecodingChannelStatus(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        DecodingChannelStatus status = new DecodingChannelStatus();
        status.setId(obj.getString("Id"));
        status.setState(obj.getString("State"));
        try{status.setUrl(obj.getString("Url"));}catch (JSONException e){}
        try{status.setFps(obj.getDouble("Fps"));}catch (JSONException e){}
        try{status.setBirate(obj.getDouble("Bitrate"));}catch (JSONException e){}
        return status;
    }


    public static VideoOutputChannelStatus parseVideoOutputChannelSatusJsonObject(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        VideoOutputChannelStatus status = new VideoOutputChannelStatus();
        status.setId(obj.getString("Id"));
        status.setState(obj.getString("State"));
        try{
            JSONArray array = obj.getJSONArray("DecodingChannelStatus");
            ArrayList<DecodingChannelStatus> statuses = new ArrayList<>();
            for (int i=0;i<array.length();i++){
                statuses.add(parseDecodingChannelStatus(array.getJSONObject(i)));
            }
            status.setStatus(statuses);
        }catch (JSONException e){}
        return status;
    }

    public static TimeRange parseTimeRangeJsonObject(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        return new TimeRange(obj.getString("BeginTime"),obj.getString("EndTime"));
    }

    public static TimeBlock parseTimeBlockJsonObject(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        return new TimeBlock(obj.getString("DayOfWeek"),parseTimeRangeJsonObject(obj.getJSONObject("TimeRange")));
    }

    public static TimeBlockList parseTimeBlockListJsonObject(JSONObject obj){
        if (obj==null)return null;
        TimeBlockList list = new TimeBlockList();
        try{
            JSONArray array = obj.getJSONArray("TimeBlock");
            ArrayList<TimeBlock> timeBlocks = new ArrayList<>();
            for (int i=0;i<array.length();i++){
                timeBlocks.add(parseTimeBlockJsonObject(array.getJSONObject(i)));
            }
            list.setTimeBlocks(timeBlocks);
        }catch (JSONException e){}
        return list;
    }

    public static JSONObject createTimeRange(TimeRange range) throws JSONException {
        return new JSONObject().put("BeginTime",range.getBeginTime()).put("EndTime",range.getEndTime());
    }

    public static JSONObject createTimeBlock(TimeBlock time) throws JSONException {
        return new JSONObject().put("DayOfWeek",time.getDayOfWeek()).put("TimeRange",createTimeRange(time.getTimeRange()));
    }

    public static JSONObject createTimeBlockList(TimeBlockList list) throws JSONException {
        JSONArray array = new JSONArray();
        for (int i=0;i<list.getTimeBlocks().size();i++){
            array.put(i,createTimeBlock(list.getTimeBlocks().get(i)));
        }
        return new JSONObject().put("TimeBlock",array);
    }

    public static AlertHandle parseAlertHandleJsonObject(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        AlertHandle handle = new AlertHandle();
        handle.setHandleType(obj.getString("HandleType "));
        try{
            JSONArray array = obj.getJSONArray("IOOutput");
            ArrayList<String> outputs = new ArrayList<>();
            for (int i=0;i<array.length();i++){
                outputs.add(array.getString(i));
            }
            handle.setIOOutput(outputs);
        }catch (JSONException e){}
        try{
            JSONArray array = obj.getJSONArray("CapturePicture");
            ArrayList<String> capturePictures = new ArrayList<>();
            for (int i=0;i<array.length();i++){
                capturePictures.add(array.getString(i));
            }
            handle.setCapturePicture(capturePictures);
        }catch (JSONException e){}
        try{
            JSONArray array = obj.getJSONArray("Record");
            ArrayList<String> records = new ArrayList<>();
            for (int i=0;i<array.length();i++){
                records.add(array.getString(i));
            }
            handle.setRecord(records);
        }catch (JSONException e){}
        return handle;
    }

    public static JSONObject createAlertHandle(AlertHandle handle) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("HandleType",handle.getHandleType());
        if (handle.getIOOutput()!=null){
            JSONArray arry = new JSONArray();
            for (int i=0;i<handle.getIOOutput().size();i++){
                arry.put(i,handle.getIOOutput().get(i));
            }
            obj.put("IOOutput",arry);
        }
        if (handle.getCapturePicture()!=null){
            JSONArray array = new JSONArray();
            for (int i=0;i<handle.getCapturePicture().size();i++){
                array.put(i,handle.getCapturePicture().get(i));
            }
            obj.put("CapturePicture",array);
        }
        if (handle.getRecord()!=null){
            JSONArray array = new JSONArray();
            for (int i=0;i<handle.getRecord().size();i++){
                array.put(i,handle.getRecord().get(i));
            }
            obj.put("Record",array);
        }
        return obj;
    }

    public static UltrasonicInformation parseUltrasonicInformationJsonObject(JSONObject obj) throws JSONException {
        if (obj == null)return null;
        return new UltrasonicInformation(obj.getInt("Interval"),obj.getInt("Range"),obj.getInt("MaxRange"));
    }

    public static JSONObject createUltrasonicInformation(UltrasonicInformation info) throws JSONException {
        return new JSONObject().put("Interval",info.getInterval()).put("Range",info.getRange()).put("MaxRange",info.getMaxRange());
    }

    public static JSONObject createIOOutputPortData(IOOutputPortData data) throws JSONException {
        return new JSONObject().put("State",data.getIOState());
    }

    public static JSONObject createNetworkInterface(NetworkInterface networkInterface) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("Id",networkInterface.getId());
        obj.put("InterfacePort",networkInterface.getInterfacePort());
        obj.put("IPVersion",networkInterface.getIpVersion());
        obj.put("AddressingType",networkInterface.getAddressType());
        obj.put("IPAddress",networkInterface.getIpAddress());
        obj.put("PhyscialAddress",networkInterface.getPhyscialAddress());
        if (networkInterface.getCableType()!=null){
            obj.put("CableType",networkInterface.getCableType());
        }
        if (networkInterface.getSpeedDuplex()!=null){
            obj.put("SpeedDuplex",networkInterface.getSpeedDuplex());
        }
        return obj;
    }

    public static NetworkInterfaceStatus parseNetworkInterfaceStatusJsonObject(JSONObject obj) throws JSONException {
        if (obj ==null) return null;
        NetworkInterfaceStatus status = new NetworkInterfaceStatus();
        status.setId(obj.getString("Id"));
        try{status.setInputRate(obj.getDouble("InputRate"));}catch (JSONException e){}
        try{status.setOutputRate(obj.getDouble("OutputRate"));}catch (JSONException e){}
        return status;
    }

    public static StorageMediumStatus parseStorageMediumStatusJsonObject(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        StorageMediumStatus status = new StorageMediumStatus();
        status.setId(obj.getString("Id"));
        status.setMediumState(obj.getString("MediumState"));
        try{status.setWritingBps(obj.getLong("WritingBps"));}catch (JSONException e){}
        try{status.setReadingBps(obj.getLong("ReadingBps"));}catch (JSONException e){}
        try{status.setBadBlockNumber(obj.getLong("BadBlockNumber"));}catch (JSONException e){}
        try{status.setTotalBlockNumber(obj.getLong("TotalBlockNumber"));}catch (JSONException e){}
        return status;
    }

    public static LinkageTemplate parseLinkageTemplateJsonObject(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        LinkageTemplate link = new LinkageTemplate();
        link.setId(obj.getString("Id"));
        link.setName(obj.getString("Name"));
        link.setScriptType(obj.getString("ScriptType"));
        link.setScript(obj.getString("Script"));
        try{link.setComment(obj.getString("Comment"));}catch (JSONException e){}
        return link;
    }

    public static LinkageTemplateList parseLinkageTemplateListJsonObject(JSONObject obj){
        if (obj==null)return null;
        LinkageTemplateList list = new LinkageTemplateList();
        try{list.setPage(parsePageJsonObject(obj.getJSONObject("Page")));}catch (JSONException e){}
        try{
            JSONArray array = obj.getJSONArray("LinkageTemplate");
            ArrayList<LinkageTemplate> links = new ArrayList<>();
            for (int i=0;i<array.length();i++){
                links.add(parseLinkageTemplateJsonObject(array.getJSONObject(i)));
            }
            list.setLinkageTemplates(links);
        }catch (JSONException e){}
        return list;
    }

    public static EventLinkageParameter parseEventLinkageParameterJsonObject(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        return new EventLinkageParameter(obj.getString("Name"),obj.getString("Type"),obj.getString("DefaultValue"));
    }

    public static EventLinkageTemplate parseEventLinkageTemplateJsonObject(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        EventLinkageTemplate template = new EventLinkageTemplate();
        template.setId(obj.getString("Id"));
        template.setName(obj.getString("Name"));
        try{
            JSONArray array = obj.getJSONArray("Parameter");
            ArrayList<EventLinkageParameter> parameters = new ArrayList<>();
            for (int i=0;i<array.length();i++){
                parameters.add(parseEventLinkageParameterJsonObject(array.getJSONObject(i)));
            }
            template.setParameters(parameters);
        }catch (JSONException e){}
        template.setEventLinkage(parseEventLinkageJsonObject(obj.getJSONObject("EventLinkage")));
        return template;
    }

    public static EventLinkageTemplateList parseEventLinkageTemplateListJsonObject(JSONObject obj){
        if (obj == null)return null;
        EventLinkageTemplateList list = new EventLinkageTemplateList();
        try{list.setPage(parsePageJsonObject(obj.getJSONObject("Page")));}catch (JSONException e){}
        try{
            JSONArray array = obj.getJSONArray("EventLinkageTemplate");
            ArrayList<EventLinkageTemplate> templates = new ArrayList<>();
            for (int i=0;i<array.length();i++){
                templates.add(parseEventLinkageTemplateJsonObject(array.getJSONObject(i)));
            }
            list.setTemplates(templates);
        }catch (JSONException e){}
        return list;
    }

    public static VideoNetworkStateLog parseVideoNetworkStateLogJsonObject(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        VideoNetworkStateLog log = new VideoNetworkStateLog();
        log.setNetworkState(obj.getString("NetworkState"));
        log.setDataTime(obj.getString("Time"));
        try{log.setDescription(obj.getString("Description"));}catch (JSONException e){}
        return log;
    }

    public static VideoNetworkStateLogList parseVideoNetworkStateLogList(JSONObject obj){
        if (obj==null)return null;
        VideoNetworkStateLogList list =  new VideoNetworkStateLogList();
        try{list.setPage(parsePageJsonObject(obj.getJSONObject("Page")));}catch (JSONException e){}
        try{
            JSONArray array = obj.getJSONArray("VideoNetworkStateLog");
            ArrayList<VideoNetworkStateLog> logs = new ArrayList<>();
            for(int i=0;i<array.length();i++){
                logs.add(parseVideoNetworkStateLogJsonObject(array.getJSONObject(i)));
            }
            list.setLogs(logs);
        }catch (JSONException e){}
        return list;
    }

    public static VideoConnectionLog parseVideoConnectionLog(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        VideoConnectionLog log = new VideoConnectionLog();
        log.setClientIP(obj.getString("ClientIP"));
        log.setConnectTime(obj.getString("ConnectTime"));
        log.setDisconnectTime(obj.getString("DisconnectTime"));
        log.setSuccessed(obj.getBoolean("Succeed"));
        try{log.setFailedReason(obj.getString("FailedReason"));}catch (JSONException e){}
        try{log.setDisconnectReason(obj.getString("DisconnectReason"));}catch (JSONException e){}
        try{log.setDescription(obj.getString("Description"));}catch (JSONException e){}
        return log;
    }

    public static VideoConnectionLogList parseVideoConnectLogList(JSONObject obj){
        if (obj == null)return null;
        VideoConnectionLogList list = new VideoConnectionLogList();
        try{list.setPage(parsePageJsonObject(obj.getJSONObject("Page")));}catch (JSONException e){}
        try{
            JSONArray array = obj.getJSONArray("VideoConnectionLog");
            ArrayList<VideoConnectionLog> logs = new ArrayList<>();
            for(int i=0;i<array.length();i++){
                logs.add(parseVideoConnectionLog(array.getJSONObject(i)));
            }
            list.setLogs(logs);
        }catch (JSONException e){}
        return list;
    }

    public static TeardownLog parseTeardownLogJsonObject(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        TeardownLog log = new TeardownLog();
        log.setTime(obj.getString("Time"));
        try{log.setDescription(obj.getString("Description"));}catch (JSONException e){}
        return log;
    }

    public static TeardownLogList parseTeardownLogListJsonObject(JSONObject obj){
        if (obj==null)return null;
        TeardownLogList list = new TeardownLogList();
        try{list.setPage(parsePageJsonObject(obj.getJSONObject("Page")));}catch (JSONException e){}
        try{
            JSONArray array = obj.getJSONArray("TeardownLog");
            ArrayList<TeardownLog> logs = new ArrayList<>();
            for (int i=0;i<array.length();i++){
                logs.add(parseTeardownLogJsonObject(array.getJSONObject(i)));
            }
            list.setLogs(logs);
        }catch (JSONException e){}
        return list;
    }

    public static StorageMediumAbnormalLog parseStorageMediumAbnormalLog(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        StorageMediumAbnormalLog log = new StorageMediumAbnormalLog();
        log.setAbnormalType(obj.getString("AbnormalType"));
        log.setTime(obj.getString("Time"));
        try{log.setReport(obj.getString("Report"));}catch (JSONException e){}
        try{log.setDescription(obj.getString("Description"));}catch (JSONException e){}
        return log;
    }

    public static StorageMediumAbnormalLogList parseStorageMediumAbnormalLogList(JSONObject obj){
        if (obj==null)return null;
        StorageMediumAbnormalLogList list = new StorageMediumAbnormalLogList();
        try{list.setPage(parsePageJsonObject(obj.getJSONObject("Page")));}catch (JSONException e){}
        try{
            JSONArray array = obj.getJSONArray("StorageMediumAbnormalLog");
            ArrayList<StorageMediumAbnormalLog> logs = new ArrayList<>();
            for (int i=0;i<array.length();i++){
                logs.add(parseStorageMediumAbnormalLog(array.getJSONObject(i)));
            }
            list.setLogs(logs);
        }catch (JSONException e){}
        return list;
    }

    public static HeartbeatLog parseHeartbeatLogJsonObject(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        HeartbeatLog log = new HeartbeatLog();
        log.setId(obj.getString("Id"));
        log.setTime(obj.getString("Time"));
        log.setCpuUsage(obj.getDouble("CpuUsage"));
        log.setMemoryUsage(obj.getDouble("MemoryUsage"));
        log.setWorkingSeconds(obj.getLong("WorkingSeconds"));
        try{log.setTemperature(obj.getDouble("Temperature"));}catch (JSONException e){}
        try{log.setPressure(obj.getDouble("Pressure"));}catch (JSONException e){}
        try{log.setVoltage(obj.getDouble("Voltage"));}catch (JSONException e){}
        try{log.setNetworkSpeedRate(obj.getDouble("NetworkSpeedRate"));}catch (JSONException e){}
        try{log.setNetworkUsage(obj.getDouble("NetworkUsage"));}catch (JSONException e){}
        try{log.setVideoConnectionNumber(obj.getInt("VideoConnectionNumber"));}catch (JSONException e){}
        try{log.setStorageMediumAbnormalNumber(obj.getInt("StorageMediumAbnormalNumber"));}catch (JSONException e){}
        return log;
    }

    public static HeartbeatLogList parseHeartbeatLogList(JSONObject obj){
        if (obj==null)return null;
        HeartbeatLogList list = new HeartbeatLogList();
        try{list.setPage(parsePageJsonObject(obj.getJSONObject("Page")));}catch (JSONException e){}
        try{
            JSONArray array = obj.getJSONArray("HeartbeatLog");
            ArrayList<HeartbeatLog> logs = new ArrayList<>();
            for (int i=0;i<array.length();i++){
                logs.add(parseHeartbeatLogJsonObject(array.getJSONObject(i)));
            }
            list.setLogs(logs);
        }catch (JSONException e){}
        return list;
    }

    public static RFIDAntenna parseRfidAntenna(JSONObject obj) throws JSONException {
        if(obj==null)return null;
        RFIDAntenna rfid = new RFIDAntenna();
        rfid.setId(obj.getString("Id"));
        rfid.setMaxDistance(obj.getInt("MaxDistance"));
        try{rfid.setManufacturer(obj.getString("Manufacturer"));}catch (JSONException e){}
        try{rfid.setModel(obj.getString("Model"));}catch (JSONException e){}
        try{rfid.setDescription(obj.getString("Description"));}catch (JSONException e){}
        return rfid;
    }

    public static RFIDAntennaList parseRfidAntennaList(JSONObject obj){
       if (obj==null)return null;
        RFIDAntennaList list = new RFIDAntennaList();
        try{list.setPage(parsePageJsonObject(obj.getJSONObject("Page")));}catch (JSONException e){}
        try{
            JSONArray array = obj.getJSONArray("RFIDAntenna");
            ArrayList<RFIDAntenna> rfidAntennas = new ArrayList<>();
            for (int i=0;i<array.length();i++){
                rfidAntennas.add(parseRfidAntenna(array.getJSONObject(i)));
            }
            list.setRfidAntennas(rfidAntennas);
        }catch (JSONException e){}
        return list;
    }

    public static RFIDCard parseRfidCard(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        RFIDCard card = new RFIDCard();
        card.setId(obj.getString("Id"));
        try{card.setManufacturer(obj.getString("Manufacturer"));}catch (JSONException e){}
        try{card.setModel(obj.getString("Model"));}catch (JSONException e){}
        try{card.setDescription(obj.getString("Description"));}catch (JSONException e){}
        return card;
    }

    public static RFIDCardList parseRfidCardList(JSONObject obj){
        if (obj == null) return null;
        RFIDCardList list = new RFIDCardList();
        try{list.setPage(parsePageJsonObject(obj.getJSONObject("Page")));}catch (JSONException e){}
        try{
            JSONArray array = obj.getJSONArray("RFIDCard");
            ArrayList<RFIDCard> cards = new ArrayList<>();
            for (int i=0;i<array.length();i++){
                cards.add(parseRfidCard(array.getJSONObject(i)));
            }
            list.setCards(cards);
        }catch (JSONException e){}
        return list;
    }

    public static RFIDGroup parseRfidGroup(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        RFIDGroup group = new RFIDGroup();
        group.setId(obj.getString("Id"));
        group.setName(obj.getString("Name"));
        try{group.setDescription(obj.getString("Description"));}catch (JSONException e){}
        return group;
    }

    public static RFIDGroupList parseRfidGroupList(JSONObject obj){
        if (obj == null)return null;
        RFIDGroupList list = new RFIDGroupList();
        try{
            JSONArray array = obj.getJSONArray("RFIDGroup");
            ArrayList<RFIDGroup> groups = new ArrayList<>();
            for (int i=0;i<array.length();i++){
                groups.add(parseRfidGroup(array.getJSONObject(i)));
            }
            list.setGroups(groups);
        }catch (JSONException e){}
        return list;
    }

    public static RFIDGroupPriority parseRfidGroupPriority(JSONObject obj) throws JSONException {
        if (obj == null)return null;
        return new RFIDGroupPriority(obj.getString("RFIDGroupId"),obj.getString("RFIDAntennaId"),obj.getInt("MaxDuration"));
    }

    public static RFIDGroupPriorityList parseRfidGroupPriorityList(JSONObject obj){
        if(obj==null)return null;
        RFIDGroupPriorityList list = new RFIDGroupPriorityList();
        try {list.setPage(parsePageJsonObject(obj.getJSONObject("Page")));}catch (JSONException e){}
        try{
            JSONArray array = obj.getJSONArray("RFIDGroupPriority");
            ArrayList<RFIDGroupPriority> prioritys = new ArrayList<>();
            for (int i=0;i<array.length();i++){
                prioritys.add(parseRfidGroupPriority(array.getJSONObject(i)));
            }
            list.setPrioritys(prioritys);
        }catch (JSONException e){}
        return list;
    }

    public static Notice parseNoticeJsonObject(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        Notice notice = new Notice();
        notice.setId(obj.getString("Id"));
        notice.setCreationTime(obj.getString("CreationTime"));
        notice.setMessage(obj.getString("Message"));
        notice.setClassification(obj.getString("Classification"));
        notice.setStatus(obj.getString("Status"));
        notice.setSender(obj.getString("Sender"));
        try{notice.setComponentId(obj.getString("ComponentId"));}catch (JSONException e){}
        try{notice.setComponentName(obj.getString("Name"));}catch (JSONException e){}
        try{notice.setPictureIds(obj.getString("PictureIds"));}catch (JSONException e){}
        try{notice.setNoticeType(obj.getString("NoticeType"));}catch (JSONException e){}
        try{notice.setUserId(obj.getString("UserId"));}catch (JSONException e){}
        return notice;
    }

    public static NoticeList parseNoticeListJsonObject(JSONObject obj){
        if (obj==null)return null;
        NoticeList list = new NoticeList();
        try{list.setPage(parsePageJsonObject(obj.getJSONObject("Page")));}catch (JSONException e){}
        try{
            JSONArray array = obj.getJSONArray("Notice");
            ArrayList<Notice> notices = new ArrayList<>();
            for (int i=0;i<array.length();i++){
                notices.add(parseNoticeJsonObject(array.getJSONObject(i)));
            }
            list.setNotices(notices);
        }catch (JSONException e){}
        return list;
    }

    public static ServerInformation parseServerInformation(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        ServerInformation info = new ServerInformation();
        info.setAddress(obj.getString("Address"));
        info.setPort(obj.getInt("Port"));
        try{info.setTimeSynchronization(obj.getBoolean("TimeSynchronization"));}catch (JSONException e){}
        try{info.setProtocolVersion(obj.getString("ProtocolVersion"));}catch (JSONException e){}
        return info;
    }

    public static GISMap parseGISMap(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        GISMap map = new GISMap();
        map.setId(obj.getString("Id"));
        map.setName(obj.getString("Name"));
        map.setLongitude(obj.getDouble("Longitude"));
        map.setLatitude(obj.getDouble("Latitude"));
        try{map.setDescription(obj.getString("Description"));}catch (JSONException e){}
        return map;
    }

    public static GISMapList parseGISMapList(JSONObject obj){
        if (obj==null)return null;
        GISMapList list = new GISMapList();
        try{list.setPage(parsePageJsonObject(obj.getJSONObject("Page")));}catch (JSONException e){}
        try{
            JSONArray array = obj.getJSONArray("GISMap");
            ArrayList<GISMap> maps = new ArrayList<>();
            for (int i=0;i<array.length();i++){
                maps.add(parseGISMap(array.getJSONObject(i)));
            }
            list.setGisMaps(maps);
        }catch (JSONException e){}
        return list;
    }

    public static GISMapLayer parseGISMapLayer(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        GISMapLayer layer = new GISMapLayer();
        layer.setId(obj.getString("Id"));
        try{layer.setParentLayerId(obj.getString("ParentLayerId"));}catch (JSONException e){}
        layer.setName(obj.getString("Name"));
        try{layer.setIconType(obj.getInt("IconType"));}catch (JSONException e){}
        try{layer.setDescription(obj.getString("Description"));}catch (JSONException e){}
        return layer;
    }

    public static GISMapLayerList parseGISMapLayerList(JSONObject obj){
        if (obj==null)return null;
        GISMapLayerList list = new GISMapLayerList();
        try{list.setPage(parsePageJsonObject(obj.getJSONObject("Page")));}catch (JSONException e){}
        try{
            JSONArray array = obj.getJSONArray("GISMapLayer");
            ArrayList<GISMapLayer> layers = new ArrayList<>();
            for (int i=0;i<array.length();i++){
                layers.add(parseGISMapLayer(array.getJSONObject(i)));
            }
            list.setGisMapLayers(layers);
        }catch (JSONException e){}
        return list;
    }

    public static GISMapItem parseGISMapItem(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        GISMapItem item = new GISMapItem();
        item.setId(obj.getString("Id"));
        item.setParentLayerId(obj.getString("ParentLayerId"));
        item.setItemId(obj.getString("ComponentId"));
        item.setName(obj.getString("Name"));
        try{item.setIconType(obj.getInt("IconType"));}catch (JSONException e){}
        try{item.setOnline(obj.getBoolean("Online"));}catch (JSONException e){}
        item.setLongitude(obj.getDouble("Longitude"));
        item.setLatitude(obj.getDouble("Latitude"));
        try{item.setCourse(obj.getDouble("Course"));}catch (JSONException e){}
        try{item.setStatus(obj.getString("Status"));}catch (JSONException e){}
        try{item.setGpsId(obj.getString("GPSId"));}catch (JSONException e){}
        try{item.setVehiclePlateId(obj.getString("VehiclePlateId"));}catch (JSONException e){}
        try{item.setFaceRecognitionId(obj.getString("FaceRecognitionId"));}catch (JSONException e){}
        try{item.setDescription(obj.getString("Description"));}catch (JSONException e){}
        item.setUpdatadTime(obj.getString("UpdatedTime"));
        return item;
    }

    public static GISMapItemList parseGISMapItemList(JSONObject obj){
        if (obj==null)return null;
        GISMapItemList list = new GISMapItemList();
        try{list.setPage(parsePageJsonObject(obj.getJSONObject("Page")));}catch (JSONException e){}
        try{
            JSONArray array = obj.getJSONArray("GISMapItem");
            ArrayList<GISMapItem> items = new ArrayList<>();
            for (int i=0;i<array.length();i++){
                items.add(parseGISMapItem(array.getJSONObject(i)));
            }
            list.setItems(items);
        }catch (JSONException e){e.printStackTrace();}
        return list;
    }

    public static Capabilities parseCapabilities(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        Capabilities capabilities = new Capabilities();
        capabilities.setVideo(obj.getBoolean("Video"));
        capabilities.setHowell5198(obj.getBoolean("Howell5198"));
        capabilities.setGIS(obj.getBoolean("GIS"));
        capabilities.setGPS(obj.getBoolean("GPS"));
        capabilities.setVehiclePlate(obj.getBoolean("VehiclePlate"));
        capabilities.setFaceRecognitiion(obj.getBoolean("FaceRecognition"));
        capabilities.setRFID(obj.getBoolean("RFID"));
        capabilities.setUltrasonic(obj.getBoolean("Ultrasonic"));
        return capabilities;
    }

    public static ServiceVersion parseServiceVersion(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        return new ServiceVersion(obj.getString("Version"),obj.getString("BuildDate"),obj.getString("Company"));
    }

    public static GPSStatus parseGPSStatus(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        GPSStatus status = new GPSStatus();
        status.setTime(obj.getString("Time"));
        try{status.setOnline(obj.getBoolean("IsOnline"));}catch (JSONException e){}
        try{status.setStatus(obj.getInt("Status"));}catch (JSONException e){}
        try{status.setLatitude(obj.getDouble("Latitude"));}catch (JSONException e){}
        try{status.setLongitude(obj.getDouble("Longitude"));}catch (JSONException e){}
        try{status.setSpeed(obj.getDouble("Speed"));}catch (JSONException e){}
        try{status.setCourse(obj.getDouble("Course"));}catch (JSONException e){}
        try{status.setMagneticVariation(obj.getDouble("MagneticVariation"));}catch (JSONException e){}
        try{status.setAltitude(obj.getDouble("Altitude"));}catch (JSONException e){}
        try{status.setSystemUpTime(obj.getLong("SystemUpTime"));}catch (JSONException e){}
        try{status.setBattery(obj.getInt("Battery"));}catch (JSONException e){}
        try{status.setSignalIntensity(obj.getInt("SignalIntensity"));}catch (JSONException e){}
        return status;
    }

    public static GPSDevice parseGPSDevice(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        GPSDevice device = new GPSDevice();
        device.setId(obj.getString("Id"));
        device.setCreateTime(obj.getString("CreationTime"));
        try{device.setName(obj.getString("Name"));}catch (JSONException e){}
        try{device.setUserName(obj.getString("Username"));}catch (JSONException e){}
        try{device.setPassword(obj.getString("Password"));}catch (JSONException e){}
        try{device.setModel(obj.getString("Model"));}catch (JSONException e){}
        try{device.setDescription(obj.getString("Description"));}catch (JSONException e){}
        device.setAccessId(obj.getString("AccessId"));
        try{device.setGpsStatus(parseGPSStatus(obj.getJSONObject("GPSStatus")));}catch (JSONException e){}
        return device;
    }

    public static GPSDeviceList parseGPSDeviceList(JSONObject obj){
        if (obj==null)return null;
        GPSDeviceList list = new GPSDeviceList();
        try{list.setPage(parsePageJsonObject(obj.getJSONObject("Page")));}catch (JSONException e){}
        try{
            JSONArray array = obj.getJSONArray("GPSDevice");
            ArrayList<GPSDevice> devices = new ArrayList<>();
            for (int i=0;i<array.length();i++){
                devices.add(parseGPSDevice(array.getJSONObject(i)));
            }
            list.setDevices(devices);
        }catch (JSONException e){e.printStackTrace();}
        return list;
    }

    public static RMC parseRMC(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        RMC rmc = new RMC();
        rmc.setDeviceId(obj.getString("DeviceId"));
        rmc.setTime(obj.getString("Time"));
        rmc.setStatus(obj.getInt("Status"));
        rmc.setLatitude(obj.getDouble("Latitude"));
        rmc.setLongitude(obj.getDouble("Longitude"));
        try{rmc.setSpeed(obj.getDouble("Speed"));}catch (JSONException e){}
        try{rmc.setCourse(obj.getDouble("Course"));}catch (JSONException e){}
        try{rmc.setMagneticVariation(obj.getDouble("MagneticVariation"));}catch (JSONException e){}
        try{rmc.setAltitude(obj.getDouble("Altitude"));}catch (JSONException e){}
        try{rmc.setDescription(obj.getString("Description"));}catch (JSONException e){}
        rmc.setAccessId(obj.getString("AccessId"));
        return rmc;
    }

    public static RMCList parseRMCList(JSONObject obj){
        if (obj==null)return null;
        RMCList list = new RMCList();
        try{list.setPage(parsePageJsonObject(obj.getJSONObject("Page")));}catch (JSONException e){}
        try{
            JSONArray array = obj.getJSONArray("RMC");
            ArrayList<RMC> RMCs = new ArrayList<>();
            for (int i=0;i<array.length();i++){
                RMCs.add(parseRMC(array.getJSONObject(i)));
            }
            list.setRMCs(RMCs);
        }catch (JSONException e){}
        return list;
    }

    public static VehiclePlateDeviceStatus parseVehiclePlateDeviceStatus(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        VehiclePlateDeviceStatus status = new VehiclePlateDeviceStatus();
        status.setTime(obj.getString("Time"));
        try{status.setOnline(obj.getBoolean("IsOnline"));}catch (JSONException e){}
        try{status.setLatitude(obj.getDouble("Latitude"));}catch (JSONException e){}
        try{status.setLongitude(obj.getDouble("Longitude"));}catch (JSONException e){}
        try{status.setSystemUpTime(obj.getLong("SystemUpTime"));}catch (JSONException e){}
        try{status.setBattery(obj.getInt("Battery"));}catch (JSONException e){}
        try{status.setSignalIntensity(obj.getInt("SignalIntensity"));}catch (JSONException e){}
        return status;
    }

    public static VehiclePlateDevice parseVehiclePlateDevice(JSONObject obj) throws JSONException {
        if (obj == null)return null;
        VehiclePlateDevice device = new VehiclePlateDevice();
        device.setId(obj.getString("Id"));
        device.setCreateTime(obj.getString("CreationTime"));
        try{device.setName(obj.getString("Name"));}catch (JSONException e){}
        try{device.setUsername(obj.getString("Username"));}catch (JSONException e){}
        try{device.setPassword(obj.getString("Password"));}catch (JSONException e){}
        try{device.setModel(obj.getString("Model"));}catch (JSONException e){}
        try{device.setDescription(obj.getString("Description"));}catch (JSONException e){}
        device.setAccessId(obj.getString("AccessId"));
        try{device.setStatus(parseVehiclePlateDeviceStatus(obj.getJSONObject("VehiclePlateDeviceStatus")));}catch (JSONException e){}
        return device;
    }

    public static VehiclePlateDeviceList parseVehiclePlateDeviceList(JSONObject obj){
        if (obj==null)return null;
        VehiclePlateDeviceList list = new VehiclePlateDeviceList();
        try{list.setPage(parsePageJsonObject(obj.getJSONObject("Page")));}catch (JSONException e){}
        try{
            JSONArray array = obj.getJSONArray("VehiclePlateDevice");
            ArrayList<VehiclePlateDevice> devices = new ArrayList<>();
            for (int i=0;i<array.length();i++){
                devices.add(parseVehiclePlateDevice(array.getJSONObject(i)));
            }
            list.setDevices(devices);
        }catch (JSONException e){}
        return list;
    }

    public static Rectangle parseRectangle(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        return new Rectangle(obj.getInt("X"),obj.getInt("Y"),obj.getInt("Width"),obj.getInt("Height"));
    }

    public static VehiclePlateRecord parseVehiclePlateRecord(JSONObject obj) throws JSONException {
        if (obj == null)return null;
        VehiclePlateRecord record = new VehiclePlateRecord();
        record.setDeviceId(obj.getString("DeviceId"));
        record.setCreateTime(obj.getString("CreationTime"));
        record.setAccessId(obj.getString("AccessId"));
        try{record.setName(obj.getString("Name"));}catch (JSONException e){}
        record.setPlate(obj.getString("Plate"));
        try{record.setPlateColor(obj.getString("PlateColor"));}catch (JSONException e){}
        try{record.setVehicleColor(obj.getString("VehicleColor"));}catch (JSONException e){}
        try{record.setBrand(obj.getString("Brand"));}catch (JSONException e){}
        try{record.setChildBrand(obj.getString("ChildBrand"));}catch (JSONException e){}
        try{record.setCredibility(obj.getInt("Credibility"));}catch (JSONException e){}
        try{record.setSpeed(obj.getDouble("Speed"));}catch (JSONException e){}
        try{record.setPlatePosition(parseRectangle(obj.getJSONObject("PlatePosition")));}catch (JSONException e){}
        try{record.setLaneId(obj.getString("LaneId"));}catch (JSONException e){}
        try{record.setLatitude(obj.getDouble("Latitude"));}catch (JSONException e){}
        try{record.setLongitude(obj.getDouble("Longitude"));}catch (JSONException e){}
        try{record.setDescription(obj.getString("Description"));}catch (JSONException e){}
        try{
            JSONArray array = obj.getJSONArray("ImageId");
            ArrayList<String> imageIds = new ArrayList<>();
            for (int i=0;i<array.length();i++){
                imageIds.add(array.getString(i));
            }
            record.setImageIds(imageIds);
        }catch (JSONException e){}
        return record;
    }

    public static VehiclePlateRecordList parseVehiclePlateRecordList(JSONObject obj){
        if (obj == null)return null;
        VehiclePlateRecordList list = new VehiclePlateRecordList();
        try{list.setPage(parsePageJsonObject(obj.getJSONObject("Page")));}catch (JSONException e){}
        try{
            JSONArray arry = obj.getJSONArray("VehiclePlateRecord");
            ArrayList<VehiclePlateRecord> records = new ArrayList<>();
            for (int i=0;i<arry.length();i++){
                records.add(parseVehiclePlateRecord(arry.getJSONObject(i)));
            }
            list.setRecords(records);
        }catch (JSONException e){}
        return list;
    }

    public static Size parseVehiclePictureSize(JSONObject obj) throws JSONException {
        if (obj == null)return null;
        return new Size(obj.getInt("Width"),obj.getInt("Height"));
    }


    public static VehiclePlatePicture parseVehiclePlatePicture(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        VehiclePlatePicture picture = new VehiclePlatePicture();
        picture.setId(obj.getString("Id"));
        picture.setCreateTime(obj.getString("CreationTime"));
        picture.setMd5String(obj.getString("MD5String"));
        picture.setPictureType(obj.getString("PictureType"));
        try{picture.setPictureSize(parseVehiclePictureSize(obj.getJSONObject("PictureSize")));}catch (JSONException e){}
        try{picture.setPictureLength(obj.getInt("PictureLength"));}catch (JSONException e){}
        picture.setPcitureFormat(obj.getString("PictureFormat"));
        try{picture.setDeviceId(obj.getString("DeviceId"));}catch (JSONException e){}
        picture.setBase64data(obj.getString("Data"));
        try{picture.setDescription(obj.getString("Description"));}catch (JSONException e){}
        return picture;
    }

    public static VehicleDetectionResult parseVehicleDetectionResult(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        VehicleDetectionResult result = new VehicleDetectionResult();
        result.setResult(obj.getInt("Result"));
        try{result.setExistedInDataBase(obj.getBoolean("ExistedInBlackList"));}catch (JSONException e){}
        try{result.setExistedInBlackList(obj.getBoolean("ExistedInBlackList"));}catch (JSONException e){}
        try{result.setPlate(obj.getString("Plate"));}catch (JSONException e){}
        try{result.setId(obj.getString("Id"));}catch (JSONException e){}
        try{result.setPlateColor(obj.getString("PlateColor"));}catch (JSONException e){}
        try{result.setVehicleColor(obj.getString("VehicleColor"));}catch (JSONException e){}
        try{result.setBrand(obj.getString("Brand"));}catch (JSONException e){}
        try{result.setChildBrand(obj.getString("ChildBrand"));}catch (JSONException e){}
        try{result.setDescription(obj.getString("Description"));}catch (JSONException e){}
        return result;
    }

    public static Vehicle parseVehicle(JSONObject obj) throws JSONException {
        if (obj==null)return null;
        Vehicle vehicle = new Vehicle();
        vehicle.setId(obj.getString("Id"));
        vehicle.setCreateTime(obj.getString("CreationTime"));
        try{vehicle.setName(obj.getString("Name"));}catch (JSONException e){}
        vehicle.setPlate(obj.getString("Plate"));
        try{vehicle.setPlateColor(obj.getString("PlateColor"));}catch (JSONException e){}
        try{vehicle.setVehicleColor(obj.getString("VehicleColor"));}catch (JSONException e){}
        try{vehicle.setBrand(obj.getString("Brand"));}catch (JSONException e){}
        try{vehicle.setChildBrand(obj.getString("ChildBrand"));}catch (JSONException e){}
        vehicle.setExistedInBlackList(obj.getBoolean("ExistedInBlackList"));
        vehicle.setMatchingPercentage(obj.getInt("MatchingPercentage"));
        try{vehicle.setDescription(obj.getString("Description"));}catch (JSONException e){}
        return vehicle;
    }

    public static VehicleList parseVehicleList(JSONObject obj){
        if (obj==null)return null;
        VehicleList list = new VehicleList();
        try{list.setPage(parsePageJsonObject(obj.getJSONObject("Page")));}catch (JSONException e){}
        try{
            JSONArray array = obj.getJSONArray("Vehicle");
            ArrayList<Vehicle> vehicles = new ArrayList<>();
            for (int i=0;i<array.length();i++){
                vehicles.add(parseVehicle(array.getJSONObject(i)));
            }
            list.setVehicles(vehicles);
        }catch (JSONException e){}
        return list;
    }


}
