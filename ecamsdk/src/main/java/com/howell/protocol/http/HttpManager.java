package com.howell.protocol.http;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;


import com.howell.bean.enumerations.ProtocolEnum;
import com.howell.bean.httpbean.*;
import com.howell.protocol.utils.MD5;
import com.howell.protocol.utils.SDKDebugLog;
import com.howell.protocol.utils.SSLConection;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;



/**
 * Created by Administrator on 2017/4/5.
 * Http protocol manager {@link <皓维数据中心平台协议>},{@link <车牌识别服务协议>} {@link <GPS定位服务协议>}<br></>
 * this HTTP protocol contain AUTHENTICATION,USER,SERVER,DEPARTMENT,PERMISSION,SYSTEM,BUSINESS,VEHICLE,GPS<br></>
 * all protocol communcated to same server but different service by http+json,but just AUTHENTICATION,BUSINESS,VEHICLE and GPS are used in this example<br></>
 *  <dl>
 *      <dt><dt/>
 *      <dd>AUTHENTICATION,BUSINESS  {@see <皓维数据中心平台协议>}   <dd/>
 *      <dd>VEHICLE   {@see <车牌识别服务协议>}   <dd/>
 *      <dd>GPS   {@see <GPS定位服务协议>}  <dd/>
 *  <dl/><br/>
 * these protocol is different in permission and some protocols are not allowed to visit <br></>
 *
 * @author howell
 */
public class HttpManager {
    private static final String TAG = HttpManager.class.getName();
    private static HttpManager mInstance = null;
    public static HttpManager getInstance(){
        if (mInstance==null){
            mInstance=new HttpManager();
        }
        return mInstance;
    }
    private HttpManager(){}

    private static final int POST    = 1;
    private static final int GET     = 2;
    private static final int PUT     = 3;
    private static final int DEL     = 4;

    private HttpPost post;
    private HttpGet get;
    private HttpPut put;
    private HttpDelete delete;
    private HttpClient client;
    private HttpResponse response;
    private String retSrc = "";
    private Context mContext;
    private boolean mIsSSL;

    private String mServerIP;
    private int mPort;
    private String mHttp;

    private String cookieHalf = null;
    private String session = null;
    private String verify = null;

    /**
     * init url
     * @param c context
     * @param serverIP HTTP server IP
     * @param serverPort server port in default, if use ssl 8850 nor 8800
     * @param isUseSSL if use ssl
     * @return http manager
     */
    public HttpManager initURL(Context c, String serverIP,int serverPort,boolean isUseSSL){
        this.mContext = c;
        this.mServerIP = serverIP;
        this.mPort = serverPort;
        this.mIsSSL = isUseSSL;
        this.mHttp = isUseSSL?"https":"http";
        return this;
    }

    /**
     * get session after authenticate <br/>
     * in most case we not use it
     * @return sessionID
     */
    public String getSession(){
        return session;
    }

    private String handleHttpProtocol(String url,byte [] data,String cookie){
        post = new HttpPost(url);

        post.addHeader("Cookie",cookie);
        client = mIsSSL?SSLConection.getHttpsClient(mContext):new DefaultHttpClient();
        client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 8000);
        client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 8000  );
        post.addHeader("Content-Type", "application/octet-stream");
        post.setEntity(new ByteArrayEntity(data));

        try {
            response = client.execute(post);
            if(response.getStatusLine().getStatusCode() == 200) {
                //获得请求的Entity
                retSrc = EntityUtils.toString(response.getEntity());
                SDKDebugLog.logI(TAG+":handleHttpProtocol","http response="+retSrc);
                return retSrc;
            }else{
                SDKDebugLog.logE(TAG+":handleHttpProtocol","response code="+response.getStatusLine().getStatusCode());
                return null;
            }
        } catch (ConnectTimeoutException e){
            e.printStackTrace();
        } catch (ClientProtocolException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            client.getConnectionManager().shutdown();
        }
        return null;
    }

    private byte[] handleHttpProtocol(String url,JSONObject param,String cookie){
        get = new HttpGet(url);
        get.addHeader("Cookie",cookie);
        client = mIsSSL?SSLConection.getHttpsClient(mContext):new DefaultHttpClient();
        client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 8000);
        client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 8000  );

        try {
            response = client.execute(get);
            if(response.getStatusLine().getStatusCode() == 200) {
                //获得请求的Entity
                return EntityUtils.toByteArray(response.getEntity());
            }else{
                return null;
            }
        } catch (ConnectTimeoutException e){
            e.printStackTrace();
        } catch (ClientProtocolException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String handleHttpProtocol(int mode, String url, JSONObject param,String cookie){
        SDKDebugLog.logI(TAG+":handleHttpProtocol","url="+url+(param==null?"":"   param="+param.toString())+"  cookie="+cookie);
        if (POST == mode){
            post = new HttpPost(url);
            if (cookie!=null){
                post.addHeader("Cookie",cookie);
            }
        }else if(GET == mode){
            get = new HttpGet(url);
            if(cookie != null){
                get.setHeader("Cookie", cookie);
            }
        }else if(PUT == mode){
            put = new HttpPut(url);
            if (cookie!=null){
                put.setHeader("Cookie",cookie);
            }
        }else if(DEL == mode){
            delete = new HttpDelete(url);
            if (cookie!=null){
                delete.setHeader("Cookie",cookie);
            }
        }

        client = mIsSSL?SSLConection.getHttpsClient(mContext):new DefaultHttpClient();
        client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 8000);
        client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 8000  );
        try {
            if(param != null){
                if (mode == POST) {
                    post.addHeader("Content-Type", "application/json;charset=utf-8");

                    StringEntity se = new StringEntity(param.toString(), HTTP.UTF_8);
                    post.setEntity(se);
                }else if(mode == PUT){
                    put.addHeader("Content-Type", "application/json;charset=utf-8");
                    StringEntity se = new StringEntity(param.toString(), HTTP.UTF_8);
                    put.setEntity(se);
                }
            }


            //客户端开始向指定的网址发送请求
            if(mode == POST){
                response = client.execute(post);
            }else if(mode == GET){
                response = client.execute(get);
            }else if(mode == PUT){
                response = client.execute(put);
            }else if(mode == DEL){
                response = client.execute(delete);
            }
            //若状态码为200 ok
            if(response.getStatusLine().getStatusCode() == 200) {
                //获得请求的Entity
                retSrc = EntityUtils.toString(response.getEntity());
                SDKDebugLog.logI(TAG+":handleHttpProtocol","http response:"+retSrc);
                return retSrc;
            }else{
                SDKDebugLog.logE(TAG+":handleHttpProtocol","http code="+response.getStatusLine().getStatusCode());
                return "";
            }

        } catch (ConnectTimeoutException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            client.getConnectionManager().shutdown();

        }

        return "";//失败会返回上次的
    }

    private boolean isfirst(ArrayList<String> s,int index){
        if (s==null)return true;
        if (index>=s.size() || index<0)return true;
        for(int i=0;i<index;i++){
            if (s.get(i)!=null){
                return false;
            }
        }
        return true;
    }



    /*************用户认证协议   user_authentication ***********************/
    /**
     * AUTHENTICATION <br/>
     * get nonce from server<br/>
     * @param userName userName
     * @return userNonce
     * @throws JSONException
     */
    public UserNonce userNonce(String userName) throws JSONException {
        return JsonUtil.parseNonceJsonObject(new JSONObject(handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/user_authentication/Authentication/Nonce?UserName="+userName,null,null))) ;
    }

    /**
     * AUTHENTICATION <br/>
     * get authentication sverice version
     * @return version {@link Version}
     * @throws JSONException
     */
    public Version userVersion() throws JSONException {
        return JsonUtil.parseVersionJsonObject(new JSONObject(handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/user_authentication/System/Version",null,null))) ;
    }

    /**
     * AUTHENTICATION <br></>
     * authenticate to server just like login
     * @param req {@link UserClientCredential}<br></> more information see Demo<br></>
     * @return result of authenticate {@link Fault}
     * @throws JSONException
     */
    public Fault doUserAuthenticate(UserClientCredential req) throws JSONException {
        JSONObject jsonObj = JsonUtil.createAuthenticateJsonObject(req);
        Fault res =  JsonUtil.parseFaultJsonObject(new JSONObject(handleHttpProtocol(POST,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/user_authentication/Authentication/Authenticate",jsonObj,null)));
        if (res.getFaultCode()==0){
            cookieHalf = "username="+req.getUserName()+";sid="+res.getId()+";domain="+req.getDomain()+";";
            session = res.getId();
            verify = req.getVerifySession();
        }
        return res;
    }

    /**
     * AUTHENTICATION <br></>
     * query if the method is accessed
     * @param req {@link UserMethodValidation}
     * @return result {@link Fault}
     * @throws JSONException
     */
    public Fault doUserMethodValidation(UserMethodValidation req) throws JSONException {
        JSONObject jsonObj = JsonUtil.createMethodValidationJsonObject(req);
        return JsonUtil.parseFaultJsonObject(new JSONObject(handleHttpProtocol(POST,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/user_authentication/Authentication/MethodValidate",jsonObj,null)));
    }

    /**
     * AUTHENTICATION <br></>
     * user teardown from server just like logout
     * @param req {@link UserTeardownCredential}
     * @return result {@link Fault}
     * @throws JSONException
     */
    public Fault doUserTeardown(UserTeardownCredential req) throws JSONException {
        JSONObject jsonObj = JsonUtil.createTeardownCredentialJsonObject(req);
        return JsonUtil.parseFaultJsonObject(new JSONObject(handleHttpProtocol(POST,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/user_authentication/Authentication/Teardown",jsonObj,null)));
    }

    /*******************中心平台协议   data_service************************/

    private String getCookie(String action) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return cookieHalf+"verifysession="+MD5.getMD5(action+verify);
    }


    public Version serverVersion() throws JSONException, UnsupportedEncodingException, NoSuchAlgorithmException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/System/Version:");
        return JsonUtil.parseVersionJsonObject(new JSONObject(handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/System/Version",null,cookie)));
    }

    //查询所有用户列表
    public UserList queryServerUsers(int pageIndex, int pageSize, String permission) throws JSONException, UnsupportedEncodingException, NoSuchAlgorithmException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Users:");
        return pageSize==0?JsonUtil.parseUserListJsonObject(new JSONObject(handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Users"+(permission==null?"":"?Permission="+permission),null,cookie)))
                :JsonUtil.parseUserListJsonObject(new JSONObject(handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Users?PageIndex="+pageIndex+"&PageSize="+pageSize+(permission==null?"":"&Permission="+permission),null,cookie)));
    }

    //查询该用户id下的设备
    public DevicePermissionList queryUserDevice(String userId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Users/"+userId+"/Devices:");
        return JsonUtil.parseDevicePermissionList(new JSONObject(
           handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Users/"+userId+"/Devices",null,cookie)
        ));
    }

    public DepartmentList queryUserDepartment(String userId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Users/"+userId+"/Departments:");
        return JsonUtil.parseDepartmentListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Users/"+userId+"/Departments",null,cookie)
        ));
    }

    /********************users************************/
    // users   查询该用户id下的视频输入
    public VideoInputChannelPermissionList queryUserInputChannel(String userId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Users/"+userId+"/Video/Inputs/Channels:");
        return JsonUtil.parseVideoInputChannelPermissionListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Users/"+userId+"/Video/Inputs/Channels",null,cookie)
        ));
    }

    public VideoOutputChannelPermissionList queryUserOunputChannel(String userId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Users/"+userId+"/Video/Outputs/Channels:");
        return JsonUtil.parseVideoOutputChannelPermissionListJsonObject(new JSONObject(
           handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Users/"+userId+"/Video/Outputs/Channels",null,cookie)
        ));
    }

    public IOInputChannelPermissionList queryUserIOInputChannel(String userId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Users/"+userId+"/IO/Inputs/Channels:");
        return JsonUtil.parseIOInputChannelPermissionListJsonObject(new JSONObject(
           handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Users/"+userId+"/IO/Inputs/Channels",null,cookie)
        ));
    }

    public IOOutputChannelPermissionList queryUserIOOutChannel(String userId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Users/"+userId+"/IO/Outputs/Channels:");
        return JsonUtil.parseIOOutChannelPermisssionListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Users/"+userId+"/IO/Outputs/Channels",null,cookie)
        ));
    }

    public LinkageList queryUserLinkage(String userId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Users/"+userId+"/Linkages:");
        return JsonUtil.parseLinkageListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Users/"+userId+"/Linkages",null,cookie)
        ));
    }

    public EventLinkageList queryUserEventLinkage(String userId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Users/"+userId+"/Events/Linkages:");
        return JsonUtil.parseEventLinkageListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Users/"+userId+"/Events/Linkages",null,cookie)
        ));
    }

    /******************department******************************/
    public DepartmentList queryDepartment() throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Departments:");
        return JsonUtil.parseDepartmentListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Departments",null,cookie)
        ));
    }

    public Department queryDepartment(String departmentId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Departments/"+departmentId+":");
        return JsonUtil.parseDepartmentJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Departments/"+departmentId,null,cookie)
        ));
    }

    public UserList queryDepartmentUsers(String departmentId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Departments/"+departmentId+"/Users:");
        return JsonUtil.parseUserListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Departments/"+departmentId+"/Users",null,cookie)
        ));
    }

    public User queryDepartmentUsers(String departmentId, String userId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Departments/"+departmentId+"/Users/"+userId+":");
        return JsonUtil.parseUserJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Departments/"+departmentId+"/Users/"+userId,null,cookie)
        ));
    }

    public DevicePermissionList queryDepartmentDevices(String departmentId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Departments/"+departmentId+"/Devices:");
        return JsonUtil.parseDevicePermissionList(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Departments/"+departmentId+"/Devices",null,cookie)
        ));
    }

    public DevicePermission queryDepartmentDevices(String departmentId, String deviceId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Departments/"+departmentId+"/Devices/"+deviceId+":");
        return JsonUtil.parseDevicePermission(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Departments/"+departmentId+"/Devices/"+deviceId,null,cookie)
        ));
    }

    public MapList queryDepartmentMaps(String departmentId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Departments/"+departmentId+"/Maps:");
        return JsonUtil.parseMapListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Departments/"+departmentId+"/Maps",null,cookie)
        ));
    }

    public Map queryDepartmentMaps(String departmentId, String mapId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Departments/"+departmentId+"/Maps/"+mapId+":");
        return JsonUtil.parseMapJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Departments/"+departmentId+"/Maps/"+mapId,null,cookie)
        ));
    }

    public MapGroupList queryDepartmentMapsGroup(String departmentId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Departments/"+departmentId+"/Maps/Groups:");
        return JsonUtil.parseMapgroupListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Departments/"+departmentId+"/Maps/Groups",null,cookie)
        ));
    }

    public MapGroup queryDepartmentMapGroup(String departmentId, String groupId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Departments/"+departmentId+"/Maps/Groups/"+groupId+":");
        return JsonUtil.parseMapGroupJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Departments/"+departmentId+"/Maps/Groups/"+groupId,null,cookie)
        ));
    }

    public MapGroupList queryDepartmentMapGroupChildGroups(String departmentId,String groupId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Departments/"+departmentId+"/Maps/Groups/"+groupId+"/ChildGroups:");
        return JsonUtil.parseMapgroupListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Departments/"+departmentId+"/Maps/Groups/"+groupId+"/ChildGroups",null,cookie)
        ));
    }

    public MapList queryDepartmentMapGroupsMaps(String departmentId,String groupId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Departments/"+departmentId+"/Maps/Groups/"+groupId+"/Maps:");
        return JsonUtil.parseMapListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Departments/"+departmentId+"/Maps/Groups/"+groupId+"/Maps",null,cookie)
        ));
    }

    public Map queryDepartmentMapGroupsMaps(String departmentId,String groupId,String mapId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Departments/"+departmentId+"/Maps/Groups/"+groupId+"/Maps/"+mapId+":");
        return JsonUtil.parseMapJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Departments/"+departmentId+"/Maps/Groups/"+groupId+"/Maps/"+mapId,null,cookie)
        ));
    }

    public VideoInputChannelPermissionList queryDepartmentVideoInputsChannels(String departmentId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Departments/"+departmentId+"/Video/Inputs/Channels:");
        return JsonUtil.parseVideoInputChannelPermissionListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Departments/"+departmentId+"/Video/Inputs/Channels",null,cookie)
        ));
    }

    public VideoInputChannelPermission queryDepartmentVideoInputsChannels(String departmentId,String channelId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Departments/"+departmentId+"/Video/Inputs/Channels/"+channelId+":");
        return JsonUtil.parseVideoInputChannelPermissionJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Departments/"+departmentId+"/Video/Inputs/Channels/"+channelId,null,cookie)
        ));
    }

    public VideoInputChannelGroupList queryDepartmentVideoInputChannelGroups(String departmentId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Departments/"+departmentId+"/Video/Inputs/Channels/Groups:");
        return JsonUtil.parseVideoInputChannelGroupListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Departments/"+departmentId+"/Video/Inputs/Channels/Groups",null,cookie)
        ));
    }

    public VideoInputChannelGroup queryDepartmentVideoInputChannelGroups(String departmentId, String groupId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Departments/"+departmentId+"/Video/Inputs/Channels/Groups/"+groupId+":");
        return JsonUtil.parseVideoInputChannelGroupJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Departments/"+departmentId+"/Video/Inputs/Channels/Groups/"+groupId,null,cookie)
        ));
    }

    public VideoInputChannelGroupList queryDepartmentInputChannelsGroupChildGroups(String departmentId,String groupId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Departments/"+departmentId+"/Video/Inputs/Channels/Groups/"+groupId+"/ChildGroups:");
        return JsonUtil.parseVideoInputChannelGroupListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Departments/"+departmentId+"/Video/Inputs/Channels/Groups/"+groupId+"/ChildGroups",null,cookie)
        ));
    }

    public VideoInputChannelPermissionList queryDepartmentInputChannelGroupChannels(String departmentId,String groupId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Departments/"+departmentId+"/Video/Inputs/Channels/Groups/"+groupId+"/Channels:");
        return JsonUtil.parseVideoInputChannelPermissionListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Departments/"+departmentId+"/Video/Inputs/Channels/Groups/"+groupId+"/Channels",null,cookie)
        ));
    }

    public VideoInputChannelPermission queryDepartmentInputChannelGroupChannels(String departmentId,String groupId,String channelId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Departments/"+departmentId+"/Video/Inputs/Channels/Groups/"+groupId+"/Channels/"+channelId+":");
        return JsonUtil.parseVideoInputChannelPermissionJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Departments/"+departmentId+"/Video/Inputs/Channels/Groups/"+groupId+"/Channels/"+channelId,null,cookie)
        ));
    }

    public VideoOutputChannelPermissionList queryDepartmentVideoOutputChannels(String departmentId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Departments/"+departmentId+"/Video/Outputs/Channels:");
        return JsonUtil.parseVideoOutputChannelPermissionListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Departments/"+departmentId+"/Video/Outputs/Channels",null,cookie)
        ));
    }

    public VideoOutputChannelPermission queryDepartmentVideoOutputChannels(String departmentId,String channelId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Departments/"+departmentId+"/Video/Outputs/Channels/"+channelId+":");
        return JsonUtil.parseVideoOutputChannelPermissionJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Departments/"+departmentId+"/Video/Outputs/Channels/"+channelId,null,cookie)
        ));
    }

    public LinkageList queryDepartmentLinkages(String departmentId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Departments/"+departmentId+"/Linkages:");
        return JsonUtil.parseLinkageListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Departments/"+departmentId+"/Linkages",null,cookie)
        ));
    }

    public Linkage queryDepartmentLinkages(String departmentId, String linkageId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Departments/"+departmentId+"/Linkages/"+linkageId+":");
        return JsonUtil.parseLinkageJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Departments/"+departmentId+"/Linkages/"+linkageId,null,cookie)
        ));
    }

    public IOInputChannelPermissionList queryDepartmentIOInputChannels(String departmentId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Departments/"+departmentId+"/IO/Inputs/Channels:");
        return JsonUtil.parseIOInputChannelPermissionListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Departments/"+departmentId+"/IO/Inputs/Channels",null,cookie)
        ));
    }

    public IOInputChannelPermission queryDepartmentIOInputChannels(String departmentId, String channleId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Departments/"+departmentId+"/IO/Inputs/Channels/"+channleId);
        return JsonUtil.parseIOInputChannelPermissionJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Departments/"+departmentId+"/IO/Inputs/Channels/"+channleId,null,cookie)
        ));
    }

    public IOOutputChannelPermissionList queryDepartmentIOOutputChannels(String departmentId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Departments/"+departmentId+"/IO/Outputs/Channels:");
        return JsonUtil.parseIOOutChannelPermisssionListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Departments/"+departmentId+"/IO/Outputs/Channels",null,cookie)
        ));
    }

    public IOOutputChannelPermission queryDepartmentIOOutputChannels(String departmentId, String channelId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Departments/"+departmentId+"/IO/Outputs/Channels/"+channelId+":");
        return JsonUtil.parseIOOutChannelPermissionJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Departments/"+departmentId+"/IO/Outputs/Channels/"+channelId,null,cookie)
        ));
    }

    public EventLinkageList queryDepartmentEventsLinkages(String departmentId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Departments/"+departmentId+"/Events/Linkages:");
        return JsonUtil.parseEventLinkageListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Departments/"+departmentId+"/Events/Linkages",null,cookie)
        ));
    }

    public EventLinkage queryDepartmentEventLinkages(String departmentId, String componetId, String eventType, String eventState) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Departments/"+departmentId+"/Events/Linkages/"+componetId+"/"+eventType+"/"+eventState+":");
        return JsonUtil.parseEventLinkageJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Departments/"+departmentId+"/Events/Linkages/"+componetId+"/"+eventType+"/"+eventState,null,cookie)
        ));
    }

    /*******************permissions********************************/
    public UserList queryPermissionsVideoInputChannelUsers(String inputChannelId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Permissions/Video/Inputs/Channels/"+inputChannelId+"/Users:");
        return JsonUtil.parseUserListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Permissions/Video/Inputs/Channels/"+inputChannelId+"/Users",null,cookie)
        ));
    }

    public User queryPermissionsVideoInputChannelUsers(String inputChannelId,String userId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Permissions/Video/Inputs/Channels/"+inputChannelId+"/Users/"+userId+":");
        return JsonUtil.parseUserJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Permissions/Video/Inputs/Channels/"+inputChannelId+"/Users/"+userId,null,cookie)
        ));
    }

    public DepartmentList queryPermissionsVideoInputsChannelDepartment(String inputChannelId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Permissions/Video/Inputs/Channels/"+inputChannelId+"/Departments:");
        return JsonUtil.parseDepartmentListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Permissions/Video/Inputs/Channels/"+inputChannelId+"/Departments",null,cookie)
        ));
    }

    public Department queryPermissionVideoInputsChannelDepartment(String inputChannelId,String departmentId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Permissions/Video/Inputs/Channels/"+inputChannelId+"/Departments/"+departmentId+":");
        return JsonUtil.parseDepartmentJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Permissions/Video/Inputs/Channels/"+inputChannelId+"/Departments/"+departmentId,null,cookie)
        ));
    }



    public UserList queryPermissionVideoOutputsChannelsUsers(String outputChannelId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Permissions/Video/Outputs/Channels/"+outputChannelId+"/Users:");
        return JsonUtil.parseUserListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Permissions/Video/Outputs/Channels/"+outputChannelId+"/Users",null,cookie)
        ));
    }

    public User queryPermissionVideoOutputsChannelsUsers(String outputChannelId,String userId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Permissions/Video/Outputs/Channels/"+outputChannelId+"/Users/"+userId+":");
        return JsonUtil.parseUserJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Permissions/Video/Outputs/Channels/"+outputChannelId+"/Users/"+userId,null,cookie)
        ));
    }

    public DepartmentList queryPermissionVideoOutputsChannelsDepartments(String outputChannelId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Permissions/Video/Outputs/Channels/"+outputChannelId+"/Departments:");
        return JsonUtil.parseDepartmentListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Permissions/Video/Outputs/Channels/"+outputChannelId+"/Departments",null,cookie)
        ));
    }

    public Department queryPermissionVideoOutputsChannelsDepartments(String outputChannelId,String departmentId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Permissions/Video/Outputs/Channels/"+outputChannelId+"/Departments/"+departmentId+":");
        return JsonUtil.parseDepartmentJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Permissions/Video/Outputs/Channels/"+outputChannelId+"/Departments/"+departmentId,null,cookie)
        ));
    }

    public UserList queryPermissionDevicesUsers(String deviceId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Permissions/Devices/"+deviceId+"/Users:");
        return JsonUtil.parseUserListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Permissions/Devices/"+deviceId+"/Users",null,cookie)
        ));
    }

    public User queryPermissionDevicesUsers(String deviceId,String userId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Permissions/Devices/"+deviceId+"/Users/"+userId+":");
        return JsonUtil.parseUserJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Permissions/Devices/"+deviceId+"/Users/"+userId,null,cookie)
        ));
    }

    public DepartmentList queryPermissionDevicesDepartments(String deviceId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Permissions/Devices/"+deviceId+"/Departments:");
        return JsonUtil.parseDepartmentListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Permissions/Devices/"+deviceId+"/Departments",null,cookie)
        ));
    }

    public Department queryPermissionDevicesDepartments(String deviceId,String departmentId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Permissions/Devices/"+deviceId+"/Departments/"+departmentId+":");
        return JsonUtil.parseDepartmentJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Permissions/Devices/"+deviceId+"/Departments/"+departmentId,null,cookie)
        ));
    }

    public UserList queryPermissionMapsUser(String mapId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Permissions/Maps/"+mapId+"/Users:");
        return JsonUtil.parseUserListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Permissions/Maps/"+mapId+"/Users",null,cookie)
        ));
    }

    public User queryPermissionMapsUser(String mapId,String userId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Permissions/Maps/"+mapId+"/Users/"+userId+":");
        return JsonUtil.parseUserJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Permissions/Maps/"+mapId+"/Users/"+userId,null,cookie)
        ));
    }

    public DepartmentList queryPermissionMapsDepartments(String mapId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Permissions/Maps/"+mapId+"/Departments:");
        return JsonUtil.parseDepartmentListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Permissions/Maps/"+mapId+"/Departments",null,cookie)
        ));
    }

    public Department queryPermissionMapsDepartments(String mapId,String departmentId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Permissions/Maps/"+mapId+"/Departments/"+departmentId+":");
        return JsonUtil.parseDepartmentJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Permissions/Maps/"+mapId+"/Departments/"+departmentId,null,cookie)
        ));
    }

    public UserList queryPermissionIOInputChannelUsers(String ioInputChannelId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Permissions/IO/Inputs/Channels/"+ioInputChannelId+"/Users:");
        return JsonUtil.parseUserListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Permissions/IO/Inputs/Channels/"+ioInputChannelId+"/Users",null,cookie)
        ));
    }

    public User queryPermissionIOInputChannelUsers(String ioInputChannelId,String userId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Permissions/IO/Inputs/Channels/"+ioInputChannelId+"/Users/"+userId);
        return JsonUtil.parseUserJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Permissions/IO/Inputs/Channels/"+ioInputChannelId+"/Users/"+userId,null,cookie)
        ));
    }

    public DepartmentList queryPermissionIOInputChannelDepartment(String ioInputChannelId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Permissions/IO/Inputs/Channels/"+ioInputChannelId+"/Departments:");
        return JsonUtil.parseDepartmentListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Permissions/IO/Inputs/Channels/"+ioInputChannelId+"/Departments",null,cookie)
        ));
    }

    public Department queryPermissionIOInputChannelDepartment(String ioInputChannelId,String departmentId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/security/Permissions/IO/Inputs/Channels/"+ioInputChannelId+"/Departments/"+departmentId+":");
        return JsonUtil.parseDepartmentJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/security/Permissions/IO/Inputs/Channels/"+ioInputChannelId+"/Departments/"+departmentId,null,cookie)
        ));
    }


    /******************health**********************/
    //health
    public SystemHealthStatistics queryHealth() throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/statistics/Health:");
        return JsonUtil.parseSystemHealthStatisticeJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/statistics/Health",null,cookie)
        ));
    }

    public SystemFaultReportList queryFault() throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/statistics/Health/Faults/Reports:");
        return JsonUtil.parseSystemFaultReportListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/statistics/Health/Faults/Reports",null,cookie)
        ));
    }

    //FIXME 500
    public SystemWarningReportList queryWarning() throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/statistics/Health/Warnings/Reports:");
        return JsonUtil.parseSystemWarningReprotListdJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/statistics/Health/Warnings/Reports",null,cookie)
        ));
    }


    /*********************business************************/
    // business

    /**
     * BUSINESS<br></>
     * query all device list in this level permission <br></>
     * @return device list {@link DevicePermissionList}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public DevicePermissionList queryBusinessDevice() throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/Devices:");
        return JsonUtil.parseDevicePermissionList(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Devices",null,cookie)
        ));
    }

    /**
     * BUSINESS<br></>
     * query device list in this level permission by pages <br></>
     * @param pageIndex page index [1-n]
     * @param pageSize how many devices u want in one page ;if zero return all device list
     * @param classification query device by classification {@link ProtocolEnum.DeviceClassification}
     * @return device list {@link DevicePermissionList}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public DevicePermissionList queryBusinessDevice(int pageIndex,int pageSize,@Nullable ProtocolEnum.DeviceClassification classification) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        if (pageSize==0&&classification==null)return queryBusinessDevice();
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/Devices:");
        return JsonUtil.parseDevicePermissionList(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Devices"+(pageSize==0?"":"?PageIndex="+pageIndex+"&PageSize="+pageSize)+(classification==null?"":"&Classification="+classification.getVal()),null,cookie)
        ));
    }

    /**
     * BUSINESS<br></>
     * query device by device id in business permission
     * @param deviceId device id
     * @return device {@link DevicePermission}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public DevicePermission queryBusinessDevice(String deviceId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/Devices/"+deviceId);
        return JsonUtil.parseDevicePermission(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Devices/"+deviceId,null,cookie)
        ));
    }

    /**
     * BUSINESS<br></>
     * query device by device id
     * @param deviceId
     * @param pageIndex
     * @param pageSize
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public DevicePermission queryBusinessDevice(String deviceId,int pageIndex,int pageSize) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        if (pageSize==0)return queryBusinessDevice(deviceId);
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/Devices/"+deviceId);
        return JsonUtil.parseDevicePermission(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Devices/"+deviceId+"?PageIndex="+pageIndex+"&PageSize="+pageSize,null,cookie)
        ));
    }

    /**
     * BUSINESS<br></>
     * query video input channel <br></>
     * cameras or some other video devices are linked to server,we visit these ID and play it <br></>
     * @return video input channel list {@link VideoInputChannelPermissionList} <></>
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public  VideoInputChannelPermissionList queryBusinessVideoInputChannel() throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/Video/Inputs/Channels:");
        return JsonUtil.parseVideoInputChannelPermissionListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Video/Inputs/Channels",null,cookie)
        ));
    }

    /**
     * BUSINESS<br></>
     * query video input channel and list it by pages <br></>
     * @param pageIndex page index [0-n]
     * @param pageSize a page size; if zero query all
     * @return list {@link VideoInputChannelPermissionList}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public VideoInputChannelPermissionList queryBusinessVideoInputChannel(int pageIndex,int pageSize) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        if (pageSize==0)return queryBusinessVideoInputChannel();
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/Video/Inputs/Channels:");
        return JsonUtil.parseVideoInputChannelPermissionListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Video/Inputs/Channels"+"?PageIndex="+pageIndex+"&PageSize="+pageSize,null,cookie)
        ));
    }


    /**
     * BUSINESS<br></>
     * query video input channel by its id <br></>
     * @param channelId id
     * @return channel {@link VideoInputChannelPermission}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public VideoInputChannelPermission queryBusinessVideoInputChannel(String channelId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/Video/Inputs/Channels/"+channelId+":");
        return JsonUtil.parseVideoInputChannelPermissionJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Video/Inputs/Channels/"+channelId,null,cookie)
        ));
    }

    /**
     * BUSINESS<br></>
     * query groups which u have created contain some video input channels<br></>
     * @return group list {@link VideoInputChannelGroupList}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public VideoInputChannelGroupList queryBusinessVideoInputChannelGroup() throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/Video/Inputs/Channels/Groups:");
        return JsonUtil.parseVideoInputChannelGroupListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Video/Inputs/Channels/Groups",null,cookie)
        ));
    }

    /**
     * BUSINESS<br></>
     * query groups which u have created contain some video input channels and list by pages<br></>
     * @param pageIndex page index [0-n]
     * @param pageSize page size if zero query all
     * @return group list {@link VideoInputChannelGroupList}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public VideoInputChannelGroupList queryBusinessVideoInputChannelGroup(int pageIndex,int pageSize) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        if (pageSize == 0)return queryBusinessVideoInputChannelGroup();
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/Video/Inputs/Channels/Groups:");
        return JsonUtil.parseVideoInputChannelGroupListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Video/Inputs/Channels/Groups"+"?PageIndex="+pageIndex+"&PageSize="+pageSize,null,cookie)
        ));
    }

    /**
     * BUSINESS<br></>
     * create video input channel group<br></>
     * @param group group {@link VideoInputChannelGroup}
     * @return result {@link Fault}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public Fault createBusinessVideoInputChannelGroup(VideoInputChannelGroup group) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("POST:/howell/ver10/data_service/Business/Informations/Video/Inputs/Channels/Groups:");
        return JsonUtil.parseFaultJsonObject(new JSONObject(
                handleHttpProtocol(POST,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Video/Inputs/Channels/Groups",JsonUtil.createInputChannelGroup(group),cookie)
        ));
    }


    /**
     * BUSINESS<br></>
     * query video input channel group by group id<br/>
     * @param groupId group id
     * @return group {@link VideoInputChannelGroup}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public VideoInputChannelGroup queryBusinessVideoInputChannelGroup(String groupId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/Video/Inputs/Channels/Groups/"+groupId+":");
        return JsonUtil.parseVideoInputChannelGroupJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Video/Inputs/Channels/Groups/"+groupId,null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * updata video input channel group <br/>
     * @param groupId group id
     * @param group group {@link VideoInputChannelGroup}
     * @return resutl {@link Fault}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public Fault updataBusinessVideoInputChannelGroup(String groupId,VideoInputChannelGroup group) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie =  getCookie("PUT:/howell/ver10/data_service/Business/Informations/Video/Inputs/Channels/Groups/"+groupId+":");
        return JsonUtil.parseFaultJsonObject(new JSONObject(
                handleHttpProtocol(PUT,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Video/Inputs/Channels/Groups/"+groupId,JsonUtil.createInputChannelGroup(group),cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * delete video input channel group <br/>
     * @param groupId group id
     * @return result {@link Fault}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public Fault deleteBusinessVideoInputChannelGroup(String groupId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("DELETE:/howell/ver10/data_service/Business/Informations/Video/Inputs/Channels/Groups/"+groupId+":");
        return JsonUtil.parseFaultJsonObject(new JSONObject(
                handleHttpProtocol(DEL,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Video/Inputs/Channels/Groups/"+groupId,null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * query child group list if video input group has child
     * @param groupId group id
     * @return child group list {@link VideoInputChannelGroupList}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public VideoInputChannelGroupList queryBusinessVideoInputChildGroup(String groupId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/Video/Inputs/Channels/Groups/"+groupId+"/ChildGroups:");
        return JsonUtil.parseVideoInputChannelGroupListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Video/Inputs/Channels/Groups/"+groupId+"/ChildGroups",null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * query child group list if video input group has child and list it by pages <br/>
     * @param groupId group id
     * @param pageIndex page index [0-n]
     * @param pageSize size in one page
     * @return child group list {@link VideoInputChannelGroupList}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public VideoInputChannelGroupList queryBusinessVideoInputChildGroup(String groupId,int pageIndex,int pageSize) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        if (pageSize == 0)return queryBusinessVideoInputChildGroup(groupId);
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/Video/Inputs/Channels/Groups/"+groupId+"/ChildGroups:");
        return JsonUtil.parseVideoInputChannelGroupListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Video/Inputs/Channels/Groups/"+groupId+"/ChildGroups"+"?PageIndex="+pageIndex+"&PageSize="+pageSize,null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * query all channels in group <br/>
     * @param groupId group id
     * @return video input channel list {@link VideoInputChannelPermissionList}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public VideoInputChannelPermissionList queryBusinessVideoInputChannelGroupChannels(String groupId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/Video/Inputs/Channels/Groups/"+groupId+"/Channels:");
        return JsonUtil.parseVideoInputChannelPermissionListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Video/Inputs/Channels/Groups/"+groupId+"/Channels",null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * query all channels in group list by pages <br/>
     * @param groupId group id
     * @param pageIndex index [0-n]
     * @param pageSize size
     * @return video input channel list {@link VideoInputChannelPermissionList}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public VideoInputChannelPermissionList queryBusinessVideoInputChannelGroupChannels(String groupId,int pageIndex,int pageSize) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        if (pageSize==0)return queryBusinessVideoInputChannelGroupChannels(groupId);
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/Video/Inputs/Channels/Groups/"+groupId+"/Channels:");
        return JsonUtil.parseVideoInputChannelPermissionListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Video/Inputs/Channels/Groups/"+groupId+"/Channels"+"?PageIndex="+pageIndex+"&PageSize="+pageSize,null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * query channel in group <br></>
     * @param groupId group id
     * @param channelId channel
     * @return video input channel {@link VideoInputChannelPermission}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public VideoInputChannelPermission queryBusinessVideoInputChannelGroupChannels(String groupId,String channelId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/Video/Inputs/Channels/Groups/"+groupId+"/Channels/"+channelId+":");
        return JsonUtil.parseVideoInputChannelPermissionJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Video/Inputs/Channels/Groups/"+groupId+"/Channels/"+channelId,null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * create video input channels in group <br/>
     * @param groupId group id
     * @param channelId channel id
     * @return result {@link Fault}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public Fault createBusinessVideoInputChannelGroupChannels(String groupId,String channelId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("POST:/howell/ver10/data_service/Business/Informations/Video/Inputs/Channels/Groups/"+groupId+"/Channels/"+channelId+":");
        return JsonUtil.parseFaultJsonObject(new JSONObject(
                handleHttpProtocol(POST,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Video/Inputs/Channels/Groups/"+groupId+"/Channels/"+channelId,null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * delete input video channel in group <br/>
     * @param groupId group id
     * @param channelId channel
     * @return result {@link Fault}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public Fault deleteBusinessVideoInputChannelGroupChannels(String groupId,String channelId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("DELETE:/howell/ver10/data_service/Business/Informations/Video/Inputs/Channels/Groups/"+groupId+"Channels"+channelId+":");
        return JsonUtil.parseFaultJsonObject(new JSONObject(
                handleHttpProtocol(DEL,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Video/Inputs/Channels/Groups/"+groupId+"Channels"+channelId,null,cookie)
        ));
    }

    /**
     *  BUSINESS<br/>
     *  query video output channel <br/>
     *  output channel example: video screen,TV <br/>
     * @return video output channel list {@link VideoOutputChannelList}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public VideoOutputChannelPermissionList queryBusinessOutputChannel() throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/Video/OutPuts/Channels:");
        return JsonUtil.parseVideoOutputChannelPermissionListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Video/OutPuts/Channels",null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * query video output channel list by pages<br/>
     * @param pageIndex page index [0-n]
     * @param pageSize page size if zero query all
     * @return out put channel list {@link VideoOutputChannelPermissionList}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public VideoOutputChannelPermissionList queryBusinessOutputChannel(int pageIndex,int pageSize) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        if (pageSize==0)return queryBusinessOutputChannel();
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/Video/OutPuts/Channels:");
        return JsonUtil.parseVideoOutputChannelPermissionListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Video/OutPuts/Channels"+"?PageIndex="+pageIndex+"&PageSize="+pageSize,null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * query output channel by id <br/>
     * @param channleId channel id
     * @return video output channel {@link VideoOutputChannelPermission}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public VideoOutputChannelPermission queryBusinessOutputChannel(String channleId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/Video/OutPuts/Channels/"+channleId+":");
        return JsonUtil.parseVideoOutputChannelPermissionJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Video/OutPuts/Channels/"+channleId,null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * query io input channel<br/>
     * io input channel example: alarm device <br/>
     * @return IO input channel list {@link IOInputChannelList}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public IOInputChannelList queryBusinessIOInputChannel() throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/IO/Inputs/Channels:");
        return JsonUtil.parseIOInputChannelListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/IO/Inputs/Channels",null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * query io input channel list by pages <br/>
     * @param pageIndex page index [0-n]
     * @param pageSize  page size
     * @return io input channel list {@link IOInputChannelList}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public IOInputChannelList queryBusinessIOInputChannel(int pageIndex,int pageSize) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        if (pageSize==0)return queryBusinessIOInputChannel();
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/IO/Inputs/Channels:");
        return JsonUtil.parseIOInputChannelListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/IO/Inputs/Channels"+"?PageIndex="+pageIndex+"&PageSize="+pageSize,null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * query io input channel by channel id
     * @param channelId channel id
     * @return io input channel {@link IOInputChannel}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public IOInputChannel queryBusinessIOInputChannel(String channelId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/IO/Inputs/Channels/"+channelId+":");
        return JsonUtil.parseIOInputChannelJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/IO/Inputs/Channels/"+channelId,null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * query io input channel by id and list it by pages
     * @param channelId channelId
     * @param pageIndex page index [0-n]
     * @param pageSize page size if zero query all
     * @return io input channle {@link IOInputChannel}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public IOInputChannel queryBusinessIOInputChannel(String channelId,int pageIndex,int pageSize) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        if (pageSize==0)return queryBusinessIOInputChannel(channelId);
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/IO/Inputs/Channels/"+channelId+":");
        return JsonUtil.parseIOInputChannelJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/IO/Inputs/Channels/"+channelId+"?PageIndex="+pageIndex+"&PageSize="+pageSize,null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * query io output channel <br/>
     * io output channel example:sound alarm <br/>
     * @return io output channel list {@link IOOutputChannelList}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public IOOutputChannelList queryBusinessIOOutputChannel() throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/IO/Outputs/Channels:");
        return JsonUtil.parseIOOutputChannelListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/IO/Outputs/Channels",null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * query io output channel and list by pages
     * @param pageIndex page index [0-n]
     * @param pageSize page size if zero query all
     * @return io output channel list {@link IOOutputChannelList}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public IOOutputChannelList queryBusinessIOOutputChannel(int pageIndex,int pageSize) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        if (pageSize==0)return queryBusinessIOOutputChannel();
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/IO/Outputs/Channels:");
        return JsonUtil.parseIOOutputChannelListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/IO/Outputs/Channels"+"?PageIndex="+pageIndex+"&PageSize="+pageSize,null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * query IO output channel by channel id <br/>
     * @param channelId channel id
     * @return io output channel {@link IOOutputChannel}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public IOOutputChannel queryBusinessIOoutChannel(String channelId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/IO/Outputs/Channels/"+channelId+":");
        return JsonUtil.parseIOOutPutChannelJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/IO/Outputs/Channels/"+channelId,null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * query maps <br/>
     * map item contain linkage ,alarm ,video input channel <br/>
     * @return map list {@link MapList}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public MapList queryBusinessMaps() throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/Maps:");
        return JsonUtil.parseMapListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Maps",null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * query maps and list pages
     * @param pageIndex page index [0-n]
     * @param pageSize page size if zero query all
     * @return map list {@link MapList}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public MapList queryBusinessMaps(int pageIndex,int pageSize) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        if (pageSize==0)return queryBusinessMaps();
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/Maps:");
        return JsonUtil.parseMapListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Maps"+"?PageIndex="+pageIndex+"&PageSize="+pageSize,null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * query map by map id <br/>
     * @param mapId map id
     * @return map {@link Map}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public Map queryBusinessMaps(String mapId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/Maps/"+mapId+":");
        return JsonUtil.parseMapJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Maps/"+mapId,null,cookie)
        ));
    }


    /**
     * BUSINESS<br/>
     * query map group if created
     * @return map group list {@link MapGroupList}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public MapGroupList queryBusinessMapGroup() throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/Maps/Groups:");
        return JsonUtil.parseMapgroupListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Maps/Groups",null,cookie)
        ));
    }



    /**
     * BUSINESS<br/>
     * query map group and list by pages <br/>
     * @param pageIndex page index [0-n]
     * @param pageSize page size if zero query all
     * @return map group list {@link MapGroupList}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public MapGroupList queryBusinessMapGroup(int pageIndex,int pageSize) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        if (pageSize == 0)return queryBusinessMapGroup();
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/Maps/Groups:");
        return JsonUtil.parseMapgroupListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Maps/Groups"+"?PageIndex="+pageIndex+"&PageSize="+pageSize,null,cookie)
        ));
    }



    /**
     * BUSINESS<br/>
     * create map group <br/>
     * @param group group {@link MapGroup}
     * @return result {@link Fault}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public Fault createBusinessMapGroup(MapGroup group) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("POST:/howell/ver10/data_service/Business/Informations/Maps/Groups:");
        return JsonUtil.parseFaultJsonObject(new JSONObject(
                handleHttpProtocol(POST,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Maps/Groups",JsonUtil.createMapGroup(group),cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * query map group by group id <br/>
     * @param groupId group id
     * @return map group {@link MapGroup}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public MapGroup queryBusinessMapGroup(String groupId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/Maps/Groups/"+groupId+":");
        return JsonUtil.parseMapGroupJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Maps/Groups/"+groupId,null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * @param groupId
     * @param group
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public Fault updataBusinessMapGroup(String groupId,MapGroup group) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("PUT:/howell/ver10/data_service/Business/Informations/Maps/Groups/"+groupId+":");
        return JsonUtil.parseFaultJsonObject(new JSONObject(
                handleHttpProtocol(PUT,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Maps/Groups/"+groupId,JsonUtil.createMapGroup(group),cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * delete map group  <br/>
     * @param groupId group id
     * @return result {@link Fault}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public Fault deleteBusinessMapGroup(String groupId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("DELETE:/howell/ver10/data_service/Business/Informations/Maps/Groups/"+groupId+":");
        return JsonUtil.parseFaultJsonObject(new JSONObject(
                handleHttpProtocol(DEL,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Maps/Groups/"+groupId,null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * query map child group in map group <br/>
     * @param groupId group id
     * @return map group list {@link MapGroupList}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public MapGroupList queryBusinessMapGroupChildGroup(String groupId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/Maps/Groups/"+groupId+"/ChildGroups:");
        return JsonUtil.parseMapgroupListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Maps/Groups/"+groupId+"/ChildGroups",null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * query map child group in map group and list by pages <br/>
     * @param groupId group id
     * @param pageIndex page index [0-n]
     * @param pageSize page size if zero query all
     * @return map group list {@link MapGroupList}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public MapGroupList queryBusinessMapGroupChildGroup(String groupId,int pageIndex,int pageSize) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        if (pageSize==0)queryBusinessMapGroupChildGroup(groupId);
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/Maps/Groups/"+groupId+"/ChildGroups:");
        return JsonUtil.parseMapgroupListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Maps/Groups/"+groupId+"/ChildGroups"+"?PageIndex="+pageIndex+"&PageSize="+pageSize,null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * query maps in map group <br/>
     * @param groupId group id
     * @return map list {@link MapList}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public MapList queryBusinessMapGroupMaps(String groupId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/Maps/Groups/"+groupId+"/Maps:");
        return JsonUtil.parseMapListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Maps/Groups/"+groupId+"/Maps",null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * query maps in map group and list by pages <br/>
     * @param groupId group id
     * @param pageIndex page index [0-n]
     * @param pageSize page size if zero query all
     * @return map list {@link MapList}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public MapList queryBusinessMapGroupMaps(String groupId,int pageIndex,int pageSize) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        if (pageSize==0)return queryBusinessMapGroupMaps(groupId);
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/Maps/Groups/"+groupId+"/Maps:");
        return JsonUtil.parseMapListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Maps/Groups/"+groupId+"/Maps"+"?PageIndex="+pageIndex+"&PageSize="+pageSize,null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * query map in group <br/>
     * @param groupId group id
     * @param mapId map id
     * @return map {@link Map}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public Map queryBusinessMapGroupMaps(String groupId,String mapId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/Maps/Groups/"+groupId+"/Maps/"+mapId+":");
        return JsonUtil.parseMapJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Maps/Groups/"+groupId+"/Maps/"+mapId,null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * create map in group <br/>
     * @param groupId group id
     * @param mapId map id
     * @return result {@link Fault}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public Fault createBusinessMapGroupMaps(String groupId,String mapId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("POST:/howell/ver10/data_service/Business/Informations/Maps/Groups/"+groupId+"/Maps/"+mapId+":");
        return JsonUtil.parseFaultJsonObject(new JSONObject(
                handleHttpProtocol(POST,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Maps/Groups/"+groupId+"/Maps/"+mapId,null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * delete map in group <br/>
     * @param groupId group id
     * @param mapId map id
     * @return result {@link Fault}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public Fault deleteBusinessMapGroupMaps(String groupId,String mapId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("DELETE:/howell/ver10/data_service/Business/Informations/Maps/Groups/"+groupId+"/Maps/"+mapId+":");
        return JsonUtil.parseFaultJsonObject(new JSONObject(
                handleHttpProtocol(DEL,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Maps/Groups/"+groupId+"/Maps/"+mapId,null,cookie)
        ));
    }


    /**
     * BUSINESS<br/>
     * query linkages <br/>
     * @return linkage list {@link LinkageList}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public LinkageList queryBusinessLinkages() throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/Linkages:");
        return JsonUtil.parseLinkageListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Linkages",null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * query linkages and list by pages  <br/>
     * @param pageIndex page index [0-n]
     * @param pageSize page size if zero query all
     * @return linkage list {@link LinkageList}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public LinkageList queryBusinessLinkages(int pageIndex,int pageSize) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        if (pageSize==0)return queryBusinessLinkages();
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/Linkages:");
        return JsonUtil.parseLinkageListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Linkages"+"?PageIndex="+pageIndex+"&PageSize="+pageSize,null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * query linkage by id <br/>
     * @param linkageId linkage id
     * @return Linkage {@link Linkage}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public Linkage queryBusinessLinkages(String linkageId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/Linkages/"+linkageId+":");
        return JsonUtil.parseLinkageJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Linkages/"+linkageId,null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * query user information in this level permission<br/>
     * @return User {@link User}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public User queryBusinessUser() throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/User:");
        return JsonUtil.parseUserJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/User",null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * query department information in this level permission <br/>
     * @return department list {@link DepartmentList}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public DepartmentList queryBusinessDepartments() throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/Departments:");
        return JsonUtil.parseDepartmentListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Departments",null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * query department information in this level permission and list by pages <br/>
     * @param pageIndex index [0-n]
     * @param pageSize size 0:query all
     * @return department list {@link DepartmentList}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public DepartmentList queryBusinessDepartments(int pageIndex,int pageSize) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        if (pageSize==0)return queryBusinessDepartments();
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/Departments:");
        return JsonUtil.parseDepartmentListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Departments"+"?PageIndex="+pageIndex+"&PageSize="+pageSize,null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * query department information by id
     * @param departmentId id
     * @return department {@link Department}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public Department queryBusinessDepartments(String departmentId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/Departments/"+departmentId+":");
        return JsonUtil.parseDepartmentJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Departments/"+departmentId,null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * query io input channel status <br/>
     * @return status list {@link IOInputChannelStatusList}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public IOInputChannelStatusList queryBusinessIOInputChannelStatus() throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/IO/Inputs/Channels/Status:");
        return JsonUtil.parseIOInputChannelStatusListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/IO/Inputs/Channels/Status",null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * query io input channel status and list by pages
     * @param pageIndex index [0-n]
     * @param pageSize size 0 query all
     * @return status list {@link IOInputChannelStatusList}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public IOInputChannelStatusList queryBusinessIOInputChannelStatus(int pageIndex,int pageSize) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        if (pageSize==0)return queryBusinessIOInputChannelStatus();
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/IO/Inputs/Channels/Status:");
        return JsonUtil.parseIOInputChannelStatusListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/IO/Inputs/Channels/Status"+"?PageIndex="+pageIndex+"&PageSize="+pageSize,null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * query io input status by id
     * @param channelId id
     * @return status {@link IOInputChannelStatus}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public IOInputChannelStatus queryBusinessIOInputChannelStatus(String channelId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/IO/Inputs/Channels/"+channelId+"/Status:");
        return JsonUtil.parseIOInputChannelStatusJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/IO/Inputs/Channels/"+channelId+"/Status",null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * updata io input status <br/>
     * @param channelId id
     * @param status status {@link IOInputChannelStatus}
     * @return result {@link Fault}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public Fault updataBusinessIOInputChannelStatus(String channelId,IOInputChannelStatus status) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("PUT:/howell/ver10/data_service/Business/Informations/IO/Inputs/Channels/"+channelId+"/Status:");
        return JsonUtil.parseFaultJsonObject(new JSONObject(
                handleHttpProtocol(PUT,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/IO/Inputs/Channels/"+channelId+"/Status",JsonUtil.createIOInputChannelStatus(status),cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * eliminate io input status
     * @param channelId id
     * @return result {@link Fault}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public Fault doBusinessIOInputChannelStatusEliminate(String channelId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("POST:/howell/ver10/data_service/Business/Informations/IO/Inputs/Channels/"+channelId+"/Status/Eliminate:");
        return JsonUtil.parseFaultJsonObject(new JSONObject(
                handleHttpProtocol(POST,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/IO/Inputs/Channels/"+channelId+"/Status/Eliminate",null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * process io input status <br/>
     * @param channelId id
     * @param result needed processing result {@link ProcessingResult}
     * @return result {@link Fault}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public Fault doBusinessIOInputChannelStatusProcess(String channelId, ProcessingResult result) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("POST:/howell/ver10/data_service/Business/Informations/IO/Inputs/Channels/"+channelId+"/Status/Process:");
        return JsonUtil.parseFaultJsonObject(new JSONObject(
                handleHttpProtocol(POST,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/IO/Inputs/Channels/"+channelId+"/Status/Process",JsonUtil.createProcessingResult(result),cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * query io output status
     * @return status list {@link IOOutputChannelStatusList}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public IOOutputChannelStatusList queryBusinessIOOutputChannelStatus() throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/IO/Outputs/Channels/Status:");
        return JsonUtil.parseIOOutputChannelStatusListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/IO/Outputs/Channels/Status",null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * query io output status by id <br/>
     * @param channelId id
     * @return status {@link IOOutputChannelStatus}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public IOOutputChannelStatus queryBusinessIOOutputChannelStatus(String channelId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/IO/Outputs/Channels/"+channelId+"/Status:");
        return JsonUtil.parseIOOutputChannelStatusJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/IO/Outputs/Channels/"+channelId+"/Status",null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * updata io output status
     * @param channelId id
     * @param status status {@link IOOutputChannelStatus}
     * @return result {@link Fault}
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public Fault updataBusinessIOOutputChannelStatus(String channelId,IOOutputChannelStatus status) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("PUT:/howell/ver10/data_service/Business/Informations/IO/Outputs/Channels/"+channelId+"/Status:");
        return JsonUtil.parseFaultJsonObject(new JSONObject(
                handleHttpProtocol(PUT,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/IO/Outputs/Channels/"+channelId+"/Status",JsonUtil.createIOOutputChannelStatus(status),cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * query map data
     * @param mapId id
     * @return data
     * @deprecated
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public String queryBusinessMapData(String mapId) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/Maps/"+mapId+"/Data:");
        return handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Maps/"+mapId+"/Data",null,cookie);
    }

    public byte [] queryBusinessMapDataEx(String mapId) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/Maps/"+mapId+"/Data:");
        return handleHttpProtocol(mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Maps/"+mapId+"/Data",(JSONObject) null,cookie);
    }

    /**
     * BUSINESS<br/>
     * query map item list
     * @param mapId id
     * @return map item list
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public MapItemList queryBusinessMapItem(String mapId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/Maps/"+mapId+"/Items:");
        return JsonUtil.parseMapItemListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Maps/"+mapId+"/Items",null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * query map item
     * @param mapId map id
     * @param itemId item id
     * @return item
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public MapItem queryBusinessMapItem(String mapId, String itemId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/Maps/"+mapId+"/Items/"+itemId+":");
        return JsonUtil.parseMapItemJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Maps/"+mapId+"/Items/"+itemId,null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * query event linkages
     * @return linkage list
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public EventLinkageList queryBusinessEventLinkages() throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/Event/Linkages:");
        return JsonUtil.parseEventLinkageListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Event/Linkages",null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * query event linkages with linkages id
     * @param linkagesId linkages id
     * @return linkage
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public EventLinkage queryBusinessEventLinkages(String linkagesId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/Event/Linkages/"+linkagesId+":");
        return JsonUtil.parseEventLinkageJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Event/Linkages/"+linkagesId,null,cookie)
        ));
    }



    /**
     * BUSINESS<br/>
     * query event records
     * @param begTime records begin time
     * @param endTime records end time
     * @return record list
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public EventRecordedList queryBusinessEventRecords(String begTime, String endTime) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/Event/Records?BeginTime="+begTime+"&EndTime="+endTime+":");
        return JsonUtil.parseEventRecordListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/Event/Records?BeginTime="+begTime+"&EndTime="+endTime,null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * query all gis map
     * @return gis map list
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public GISMapList queryBusinessGISMaps() throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/GIS/Maps:");
        return JsonUtil.parseGISMapList(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/GIS/Maps",null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * query gis map by page
     * @param pageIndex page index
     * @param pageSize page size ;if size=0 query all gis map
     * @return gis map list;
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public GISMapList queryBusinessGISMaps(int pageIndex,int pageSize) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        if (pageSize==0) return queryBusinessGISMaps();
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/GIS/Maps:");
        return JsonUtil.parseGISMapList(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/GIS/Maps?"+"PageIndex="+pageIndex+"&PageSize="+pageSize,null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * query gis maps layers
     * @param gisMapId gis map id
     * @return layer list
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public GISMapLayerList queryBusinessGISMapsLayers(String gisMapId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/GIS/Maps/"+gisMapId+"/Layers:");
        return JsonUtil.parseGISMapLayerList(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/GIS/Maps/"+gisMapId+"/Layers",null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * query map layer by id
     * @param gisMapId gis map id
     * @param parnetLayerId layer id
     * @param pageIndex page index
     * @param pageSize page size ;if size =0 ,query all layers
     * @return layer list
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public GISMapLayerList queryBusinessGISMapsLayers(String gisMapId,@Nullable String parnetLayerId,int pageIndex,int pageSize) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        if (parnetLayerId==null&&pageSize==0)return queryBusinessGISMapsLayers(gisMapId);
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/GIS/Maps/"+gisMapId+"/Layers:");
        ArrayList<String> array = new ArrayList<>();
        array.add(parnetLayerId);
        array.add(pageSize==0?null:""+pageSize);
        return JsonUtil.parseGISMapLayerList(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/GIS/Maps/"+gisMapId+"/Layers?"+
                                (parnetLayerId==null?"":"ParnetLayerId="+parnetLayerId)+
                                (pageSize==0?"":(isfirst(array,1)?"":"&")+"PageIndex="+pageIndex+"&PageSize="+pageSize)
                ,null,cookie)
        ));
    }

        //2.5.1.43

    /**
     * BUSINESS<br/>
     * query gis map item
     * @param gisMapId gis map id
     * @return item list
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public GISMapItemList queryBusinessGISMapsItems(String gisMapId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/GIS/Maps/"+gisMapId+"/Items:");
        return JsonUtil.parseGISMapItemList(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/GIS/Maps/"+gisMapId+"/Items",null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * query gis map item with some information
     * @param gisMapId gis map id
     * @param itemId item id
     * @param parentLayerId layer id
     * @param GPSId gps id
     * @param hasGPSId if  {@param GPSId} is used
     * @param vehiclePlateId vehicle plate id
     * @param hasVehiclePlateId if {@param vehiclePlateId} is used
     * @param faceRecognitionId face recognition id
     * @param hasFaceRecognitionId if {@param faceRecognitionId} is used
     * @param pageIndex page index
     * @param pageSize page size; size=0 query all item ;
     * @return item list
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public GISMapItemList queryBusinessGISMapsItems(String gisMapId,@Nullable String itemId,@Nullable String parentLayerId,@Nullable String GPSId,@Nullable Boolean hasGPSId,@Nullable String vehiclePlateId,@Nullable Boolean hasVehiclePlateId,@Nullable String faceRecognitionId,@Nullable Boolean hasFaceRecognitionId,int pageIndex,int pageSize) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        if (itemId==null&&parentLayerId==null&&GPSId==null&&hasGPSId==null&&vehiclePlateId==null&&hasVehiclePlateId==null&&faceRecognitionId==null&&hasFaceRecognitionId==null&&pageSize==0){
           return queryBusinessGISMapsItems(gisMapId);
        }
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/GIS/Maps/"+gisMapId+"/Items:");
        ArrayList<String> array = new ArrayList<>();
        array.add(itemId);
        array.add(parentLayerId);
        array.add(GPSId);
        array.add(hasGPSId==null?null:hasGPSId+"");
        array.add(vehiclePlateId);
        array.add(hasVehiclePlateId==null?null:hasVehiclePlateId+"");
        array.add(faceRecognitionId);
        array.add(hasFaceRecognitionId==null?null:hasFaceRecognitionId+"");
        array.add(pageSize==0?null:pageSize+"");
        return JsonUtil.parseGISMapItemList(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/GIS/Maps/"+gisMapId+"/Items?"+
                                (itemId==null?"":"ItemId="+itemId)+
                                (parentLayerId==null?"":(isfirst(array,1)?"":"&")+"ParentLayerId="+parentLayerId)+
                                (GPSId==null?"":(isfirst(array,2)?"":"&")+"GPSId="+GPSId)+
                                (hasGPSId==null?"":(isfirst(array,3)?"":"&")+"HasGPSId="+hasGPSId)+
                                (vehiclePlateId==null?"":(isfirst(array,4)?"":"&")+"VehiclePlateId="+vehiclePlateId)+
                                (hasVehiclePlateId==null?"":(isfirst(array,5)?"":"&")+"HasVehiclePlateId="+hasVehiclePlateId)+
                                (faceRecognitionId==null?"":(isfirst(array,6)?"":"&")+"FaceRecognition"+faceRecognitionId)+
                                (hasFaceRecognitionId==null?"":(isfirst(array,7)?"":"&")+"HasFaceRecognitionId"+hasFaceRecognitionId)+
                                (pageSize==0?"":(isfirst(array,9)?"":"&")+"PageIndex="+pageIndex+"&PageSize="+pageSize)
                ,null,cookie)
        ));
    }

    /**
     * BUSINESS<br/>
     * query gis map item with item id
     * @param gisMapId gis map id
     * @param itemId item id
     * @return gis map item
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public GISMapItem queryBusinessGISMapsItems(String gisMapId,String itemId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Informations/GIS/Maps/"+gisMapId+"/Items/"+itemId+":");
        return JsonUtil.parseGISMapItem(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Informations/GIS/Maps/"+gisMapId+"/Items/"+itemId,null,cookie)
        ));
    }



    /**
     * BUSINESS<br/>
     * preview
     * @deprecated no used in this example
     * @param videoId video id
     * @param streamNo main or sub stream
     * @return task
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public PreviewTask doBusinessTaskPreview(String videoId, int streamNo) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("POST:/howell/ver10/data_service/Business/Clients/Tasks/Preview:");
        return JsonUtil.parsePreviewTaskJsonObject(new JSONObject(
                handleHttpProtocol(POST,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Clients/Tasks/Preview", JsonUtil.createPreviewTask(videoId, streamNo),cookie)
        ));
    }

    //FIXME 404

    /**
     * BUSINESS<br/>
     * playback
     * @deprecated no used in this example
     * @param videoId video id
     * @param streamNo main or sub stream
     * @param begTime playback begin time
     * @param endTime playback end time
     * @return task
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public PlaybackTask doBusinessPlayback(String videoId, int streamNo, String begTime, String endTime) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("POST:/howell/ver10/data_service/Business/Clients/Tasks/Playback:");
        return JsonUtil.parsePlaybackTaskJsonObject(new JSONObject(
                handleHttpProtocol(POST,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Clients/Tasks/Playback",JsonUtil.createPlaybackTask(videoId,streamNo,begTime,endTime),cookie)
        ));
    }



    /**
     * BUSINESS<br/>
     * download task
     * @deprecated no used in this example
     * @param videoId video id
     * @param streamNo main or sub stream
     * @param begTime begin time which u want download
     * @param endTime end time which u want download
     * @return task
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public DownloadTask doBusinessDownload(String videoId, int streamNo, String begTime, String endTime) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("POST:/howell/ver10/data_service/Business/Clients/Tasks/Download:");
        return JsonUtil.parseDownloadTaskJsonObject(new JSONObject(
                handleHttpProtocol(POST,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Clients/Tasks/Download",JsonUtil.createDownloadTask(videoId,streamNo,begTime,endTime),cookie)
        ));
    }



    /**
     * BUSINESS<br/>
     * query visitor
     * @deprecated no used in this example
     * @return visitor list
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public VisitorList queryBusinessVisitor() throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Visitors:");
        return JsonUtil.parseVistorListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Visitors",null,cookie)
        ));
    }


    /**
     * BUSINESS<br/>
     * query visitor record list
     * @deprecated no used in this example
     * @return visitor record list
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public VisitorRecordList queryBusinessVisitorRecord() throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Visitors/Records:");
        return JsonUtil.parseVisitorRecordListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Visitors/Records",null,cookie)
        ));
    }



    /**
     * BUSINESS<br/>
     * query visitor record in current
     * @deprecated  no used in this example
     * @return visitor record list
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public VisitorRecordList queryBusinessVisitorRecordCurrent() throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/Business/Visitors/Records/Current:");
        return JsonUtil.parseVisitorRecordListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/Business/Visitors/Records/Current",null,cookie)
        ));
    }

    /********SYSTEM*************/

    public DeviceList querySystemDevice() throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices:");
        return JsonUtil.parseDeviceListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices",null,cookie)
        ));
    }

    public DeviceDetails querySystemDeviceDetails(String deviceId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Details:");
        return JsonUtil.parseDeviceDetailsJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Details",null,cookie)
        ));
    }

    public DeviceStatus querySystemDeviceStatus(String deviceId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Status:");
        return JsonUtil.parseDeviceStatusJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Status",null,cookie)
        ));
    }

    public DeviceClassificationCapabilities querySystemDeviceCapabilities(String classification) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/Classifications/"+classification+"/Capabilities:");
        return JsonUtil.parseDeviceClassificationCapabilitiesJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/Classifications/"+classification+"/Capabilities",null,cookie)
        ));
    }

    //fIXME 500
    public DeviceInformation querySystemDeviceInformation(String deviceId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Information:");
        return JsonUtil.parseDeviceInformationJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Information",null,cookie)
        ));
    }

    //FIXME 400
    public SystemTime querySystemDeviceTime(String deviceId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Time:");
        return JsonUtil.parseSystemTimeJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Time",null,cookie)
        ));
    }

    //FIXME 400
    public String querySystemDeviceLocalTime(String deviceId) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Time/LocalTime:");
        return handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Time/LocalTime",null,cookie);
    }

    //FIXME 404
    public NTPServerList querySystemDeviceNTPServer(String deviceId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Time/NTPServers:");
        return JsonUtil.parseNTPServerListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Time/NTPServers",null,cookie)
        ));
    }

    //FIXME 500
    public WifiInformation querySystemDeviceWifiInformation(String deviceId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Network/WiFi:");
        return JsonUtil.parseDeviceWifiInformationJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Network/WiFi",null,cookie)
        ));
    }

    public VideoInputChannelList querySystemDeviceVideoInputChannel(String deviceId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Inputs/Channels:");
        return JsonUtil.parseVideoInputChannelListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Inputs/Channels",null,cookie)
        ));
    }


    public VideoInputChannel querySystemDeviceVideoInputChannel(String deviceId, String channelId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Inputs/Channels/"+channelId+":");
        return JsonUtil.parseVideoInputChannelJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Inputs/Channels/"+channelId,null,cookie)
        ));
    }

    //FIXME 400
    public VideoInputChannelStatus querySystemDeviceVideoInputChannelStatus(String deviceId,String channelId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie =getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Inputs/Channels/"+channelId+"/Status:");
        return JsonUtil.parseVideoInputChannelStatusJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Inputs/Channels/"+channelId+"/Status",null,cookie)
        ));
    }

    //FIXME 200   返回是 空
    public VideoInputAssociation querySystemDeviceVideoInputChannelAssociation(String deviceId, String channelId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Inputs/Channels/"+channelId+"/Association:");
//        String str = handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Inputs/Channels/"+channelId+"/Association",null,cookie);
//        Log.e("123","str="+str.toString());
        return JsonUtil.parseVideoInputAssociationJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Inputs/Channels/"+channelId+"/Association",null,cookie)
        ));
    }

    //FIXME 400
    public ArrayList<StreamingChannel> querySystemDeviceVideoInputChannelStreaming(String deviceId, String channelId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Inputs/Channels/"+channelId+"/Streaming:");
        return JsonUtil.parseVideoInputChannelStreamingJsonObject(new JSONArray(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Inputs/Channels/"+channelId+"/Streaming",null,cookie)
        ));
    }

    public PreviewSourceList querySystemDeviceInputChannelPreview(String deviceId, String channelId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Inputs/Channels/"+channelId+"/Previews:");
        return JsonUtil.parsePreviewSourceListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Inputs/Channels/"+channelId+"/Previews",null,cookie)
        ));
    }

    public PreviewSource querySystemDeviceInputChannelPreview(String deviceId, String channelId, String previewId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Inputs/Channels/"+channelId+"/Previews/"+previewId+":");
        return JsonUtil.parsePreviewSourceJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Inputs/Channels/"+channelId+"/Previews/"+previewId,null,cookie)
        ));
    }

    public PlaybackSourceList querySystemDeviceInputChannelPlayback(String deviceId, String channelId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Inputs/Channels/"+channelId+"/Playbacks:");
        return JsonUtil.parsePlaybackSourceListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Inputs/Channels/"+channelId+"/Playbacks",null,cookie)
        ));
    }

    public PlaybackSource queryStstemDeviceInputChannelPlayback(String deviceId, String channelId, String playbackId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Inputs/Channels/"+channelId+"/Playbacks/"+playbackId+":");
        return JsonUtil.parsePlaybackSourceJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Inputs/Channels/"+channelId+"/Playbacks/"+playbackId,null,cookie)
        ));
    }

    //FIXME 400
    public VideoEffect querySystemDeviceInputChannelEffect(String deviceId, String channelId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Inputs/Channels/"+channelId+"/Effect:");
        return JsonUtil.parseVideoInputChannelEffectJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Inputs/Channels/"+channelId+"/Effect",null,cookie)
        ));
    }

    public String querySystemDeviceInputChannelStreamingPicture(String deviceId,String channelId,String streamId) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String cookie=getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Inputs/Channels/"+channelId+"/Streaming/"+streamId+"/Picture:");
        return handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Inputs/Channels/"+channelId+"/Streaming/"+streamId+"/Picture",null,cookie);
    }

    //FIXME 400
    public PTZDecoder querySystemDeviceInputChannelDecoder(String deviceId, String channelId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Inputs/Channels/"+channelId+"/Decoder:");
        return JsonUtil.parseVideoInputChannelDecoderJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Inputs/Channels/"+channelId+"/Decoder",null,cookie)
        ));
    }

    //FIXME 400
    public PTZProtocolList querySystemDeviceInputChannelDecoderProtocol(String deviceId, String channelId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Inputs/Channels/"+channelId+"/Decoder/Protocols:");
        return JsonUtil.parsePTZProtocolListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Inputs/Channels/"+channelId+"/Decoder/Protocols",null,cookie)
        ));
    }

    //FIXME 400
    public Fault updataSystemDeviceInputChannelPTZDirection(String deviceId, String channelId, PTZControl c) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("PUT:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Inputs/Channels/"+channelId+"/PTZ/Direction:");
        return JsonUtil.parseFaultJsonObject(new JSONObject(
                handleHttpProtocol(PUT,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Inputs/Channels/"+channelId+"/PTZ/Direction",JsonUtil.createPTZDirection(c),cookie)
        ));
    }

    //FIXME 401
    public Fault updataSystemDeviceInputChannelPTZLens(String deviceId, String channelId, PTZLens ptz) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("PUT:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Inputs/Channels/"+channelId+"/PTZ/Lens:");
        return JsonUtil.parseFaultJsonObject(new JSONObject(
                handleHttpProtocol(PUT,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Inputs/Channels/"+channelId+"/PTZ/Lens",JsonUtil.createPTZLens(ptz),cookie)
        ));
    }

    //fIXME 400
    public Fault updataSystemDeviceInputChannelPTZPreset(String deviceId, String channelId, PTZPreset ptz) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("PUT:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Inputs/Channels/"+channelId+"/PTZ/Preset:");
        return JsonUtil.parseFaultJsonObject(new JSONObject(
                handleHttpProtocol(PUT,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Inputs/Channels/"+channelId+"/PTZ/Preset",JsonUtil.createPTZPreset(ptz),cookie)
        ));
    }

    public BitrateStatusList querySystemDeviceVideoInputChannelsBitrateStatus() throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/Video/Inputs/Channels/Diagnostics/Bitr5ate/Status:");
        return JsonUtil.parseBitrateStatusListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/Video/Inputs/Channels/Diagnostics/Bitr5ate/Status",null,cookie)
        ));
    }

    public VideoInputExtendedDataList querySystemDeviceVidoInputExtendedData(String deviceId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Inputs/ExtendedData:");
        return JsonUtil.parseVideoInputExtendedDataListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Inputs/ExtendedData",null,cookie)
        ));
    }

    public VideoInputExtendedData querySystemDeviceVidoInputExtendedData(String deviceId,String extendedId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Inputs/ExtendedData/"+extendedId+":");
        return JsonUtil.parseVideoInputExtendedDataJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Inputs/ExtendedData/"+extendedId,null,cookie)
        ));
    }

    public VideoOutputChannelList querySystemDeviceVideoOutput(String deviceId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Outputs/Channels:");
        return JsonUtil.parseVideoOutputChannelListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Outputs/Channels",null,cookie)
        ));
    }

    public VideoOutputChannel querySystemDeviceVideoOutput(String deviceId,String channelId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Outputs/Channels/"+channelId+":");
        return JsonUtil.parseVideoOutputChannelJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Outputs/Channels/"+channelId,null,cookie)
        ));
    }

    public VideoOutputChannelStatus querySystemDeviceVideoOutputChannelStatus(String deviceId,String channelId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Outputs/Channels/"+channelId+"/Status:");
        return JsonUtil.parseVideoOutputChannelSatusJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Outputs/Channels/"+channelId+"/Status",null,cookie)
        ));
    }

    public DecodingChannelList querySystemDeviceVideoOutputChannelDecoding(String deviceId, String channelId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Outputs/Channels/"+channelId+"/Decoding:");
        return JsonUtil.parseDecodingChannelListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Outputs/Channels/"+channelId+"/Decoding",null,cookie)
        ));
    }

    public DecodingChannel querySystemDeviceVideoOutputChannelDecoding(String deviceId, String channelId, String decodingId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Outputs/Channels/"+channelId+"/Decoding/"+decodingId+":");
        return JsonUtil.parseDecodingChannelJsonobject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Outputs/Channels/"+channelId+"/Decoding/"+decodingId,null,cookie)
        ));
    }

    public DecodingChannelStatus querySystemDeviceVideoOutputChannelDecodingStatus(String deviceId, String channelId, String decodingId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Outputs/Channels/"+channelId+"/Decoding/"+decodingId+"/Status:");
        return JsonUtil.parseDecodingChannelStatus(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Outputs/Channels/"+channelId+"/Decoding/"+decodingId+"/Status",null,cookie)
        ));
    }

    public DecodingChannelList querySystemDeviceVideoDecodingChannels(String deviceId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Decoding/Channels:");
        return JsonUtil.parseDecodingChannelListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Decoding/Channels",null,cookie)
        ));
    }

    public DecodingChannel querySystemDeviceVideoDecodingChannels(String deviceId,String channelId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Decoding/Channels/"+channelId+":");
        return JsonUtil.parseDecodingChannelJsonobject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Decoding/Channels/"+channelId,null,cookie)
        ));
    }

    public DecodingChannelStatus querySystemDeviceVideoDecodingChannelStatus(String deviceId,String channelId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Decoding/Channels/"+channelId+"/Status:");
        return JsonUtil.parseDecodingChannelStatus(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Video/Decoding/Channels/"+channelId+"/Status",null,cookie)
        ));
    }

    public IOInputChannelList querySystemDeviceIOInputChannels(String deviceId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/IO/Inputs/Channels:");
        return JsonUtil.parseIOInputChannelListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/IO/Inputs/Channels",null,cookie)
        ));
    }

    public IOInputChannel querySystemDeviceIOInputChannels(String deviceId,String channelId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/IO/Inputs/Channels/"+channelId+":");
        return JsonUtil.parseIOInputChannelJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/IO/Inputs/Channels/"+channelId,null,cookie)
        ));
    }

    //FIXME 400
    public IOInputChannelStatus querySystemDeviceIOInputChannelStatus(String deviceId,String channelId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/IO/Inputs/Channels/"+channelId+"/Status:");
        return JsonUtil.parseIOInputChannelStatusJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/IO/Inputs/Channels/"+channelId+"/Status",null,cookie)
        ));
    }

    //FIXME 400
    public TimeBlockList querySystemDeviceIOInputAlertSchedule(String deviceId, String channelId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/IO/Inputs/Channels/"+channelId+"/Alert/Schedule:");
        return JsonUtil.parseTimeBlockListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/IO/Inputs/Channels/"+channelId+"/Alert/Schedule",null,cookie)
        ));
    }

    public Fault updataSystemDeviceIOInputAlertSchedule(String deviceId,String channelId,TimeBlockList time) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("PUT:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/IO/Inputs/Channels/"+channelId+"/Alert/Schedule:");
        return JsonUtil.parseFaultJsonObject(new JSONObject(
                handleHttpProtocol(PUT,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/IO/Inputs/Channels/"+channelId+"/Alert/Schedule",JsonUtil.createTimeBlockList(time),cookie)
        ));
    }

    public AlertHandle querySystemDeviceIOInputAlertHandle(String deviceId, String channelId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/IO/Inputs/Channels/"+channelId+"/Alert/Handle:");
        return JsonUtil.parseAlertHandleJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/IO/Inputs/Channels/"+channelId+"/Alert/Handle",null,cookie)
        ));
    }

    public Fault updataSystemDeviceIOInputAlertHandle(String deviceId,String channelId,AlertHandle handle) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("PUT:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/IO/Inputs/Channels/"+channelId+"/Alert/Handle:");
        return JsonUtil.parseFaultJsonObject(new JSONObject(
                handleHttpProtocol(PUT,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/IO/Inputs/Channels/"+channelId+"/Alert/Handle",JsonUtil.createAlertHandle(handle),cookie)
        ));
    }

    public UltrasonicInformation querySystemDeviceIOInputUltrasonic(String deviceId, String channelId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/IO/Inputs/Channels/"+channelId+"/Ultrasonic:");
        return JsonUtil.parseUltrasonicInformationJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/IO/Inputs/Channels/"+channelId+"/Ultrasonic",null,cookie)
        ));
    }

    public Fault updataSystemDeviceIOInputUltrasonic(String deviceId,String channelId,UltrasonicInformation info) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("PUT:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/IO/Inputs/Channels/"+channelId+"/Ultrasonic:");
        return JsonUtil.parseFaultJsonObject(new JSONObject(
                handleHttpProtocol(PUT,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/IO/Inputs/Channels/"+channelId+"/Ultrasonic",JsonUtil.createUltrasonicInformation(info),cookie)
        ));
    }

    public IOOutputChannelList querySystemDeviceIOOutputChannels(String deviceId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/IO/Outputs/Channels:");
        return JsonUtil.parseIOOutputChannelListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/IO/Outputs/Channels",null,cookie)
        ));
    }

    public IOOutputChannel querySystemDeviceIOOutputChannels(String deviceId,String channelId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/IO/Outputs/Channels/"+channelId+":");
        return JsonUtil.parseIOOutPutChannelJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/IO/Outputs/Channels/"+channelId,null,cookie)
        ));
    }

    //// FIXME: 2017/4/18 400
    public IOOutputChannelStatus querySystemDeviceIOOutputChannelStatus(String deviceId,String channelId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/IO/Outputs/Channels/"+channelId+"/Status:");
        return JsonUtil.parseIOOutputChannelStatusJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/IO/Outputs/Channels/"+channelId+"/Status",null,cookie)
        ));
    }

    public IOOutputChannelStatus updataSystemDeviceIOOutputChannelTrigger(String deviceId, String channelId, IOOutputPortData data) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("PUT:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/IO/Outputs/Channels/"+channelId+"/Trigger:");
        return JsonUtil.parseIOOutputChannelStatusJsonObject(new JSONObject(
                handleHttpProtocol(PUT,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/IO/Outputs/Channels/"+channelId+"/Trigger",JsonUtil.createIOOutputPortData(data),cookie)
        ));
    }

    //// FIXME: 2017/4/18 400
    public TimeBlockList querySystemDeviceIOOutputChannelTriggerSchedule(String deviceId,String channelId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/IO/Outputs/Channels/"+channelId+"/Trigger/Schedule:");
        return JsonUtil.parseTimeBlockListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/IO/Outputs/Channels/"+channelId+"/Trigger/Schedule",null,cookie)
        ));
    }

    public Fault updataSystemDeviceIOOutputChannelTriggerSchedule(String deviceId,String channelId,TimeBlockList list) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("PUT:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/IO/Outputs/Channels/"+channelId+"/Trigger/Schedule:");
        return JsonUtil.parseFaultJsonObject(new JSONObject(
                handleHttpProtocol(PUT,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/IO/Outputs/Channels/"+channelId+"/Trigger/Schedule",JsonUtil.createTimeBlockList(list),cookie)
        ));
    }

    public NetworkInterfaceList querySystemDeviceNetworkInterfaces(String deviceId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Network/Interfaces:");
        return JsonUtil.parseNetworkInterfaceListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Network/Interfaces",null,cookie)
        ));
    }

    public Fault updataSystemDeviceNetworkInterfaces(String deviceId, NetworkInterface networkInterface) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("POST:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Network/Interfaces:");
        return JsonUtil.parseFaultJsonObject(new JSONObject(
                handleHttpProtocol(POST,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Network/Interfaces",JsonUtil.createNetworkInterface(networkInterface),cookie)
        ));
    }

    public NetworkInterface querySystemDeviceNetworkInterfaces(String deviceId,String interfaceId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Network/Interfaces/"+interfaceId+":");
        return JsonUtil.parseNetworkInterfaceJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Network/Interfaces/"+interfaceId,null,cookie)
        ));
    }

    //// FIXME: 2017/4/18  400
    public NetworkInterfaceStatus querySystemDeviceNetworkInterfaceStatus(String deviceId, String interfaceId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Network/Interfaces/"+interfaceId+"/Status:");
        return JsonUtil.parseNetworkInterfaceStatusJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Network/Interfaces/"+interfaceId+"/Status",null,cookie)
        ));
    }

    public BitrateStatus querySystemDeviceNetworkDiagnosticsStatus(String deviceId, String interfaceId, String sessionId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Network/Interfaces/"+interfaceId+"/Diagnostics/Bitrate/Status/"+sessionId+":");
        return JsonUtil.parseBitrateStatusJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Network/Interfaces/"+interfaceId+"/Diagnostics/Bitrate/Status/"+sessionId,null,cookie)
        ));
    }

    public Fault createSystemDeviceNetworkDiagnosticsStatus(String deviceId,String interfaceId,String sessionId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("POST:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Network/Interfaces/"+interfaceId+"/Diagnostics/Bitrate/Status/"+sessionId+":");
        return JsonUtil.parseFaultJsonObject(new JSONObject(
                handleHttpProtocol(POST,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Network/Interfaces/"+interfaceId+"/Diagnostics/Bitrate/Status/"+sessionId,null,cookie)
        ));
    }

    public Fault deleteSystemDeviceNetworkDiagnosticsStatus(String deviceId,String interfaceId,String sessionId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("DELETE:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Network/Interfaces/"+interfaceId+"/Diagnostics/Bitrate/Status/"+sessionId+":");
        return JsonUtil.parseFaultJsonObject(new JSONObject(
                handleHttpProtocol(DEL,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Network/Interfaces/"+interfaceId+"/Diagnostics/Bitrate/Status/"+sessionId,null,cookie)
        ));
    }

    //// FIXME: 2017/4/18 400
    public BitrateStatusList querySystemDeviceNetworkInterfaceDiagnosticsBitrateStatus() throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/Network/Interfaces/Diagnostics/Bitrate/Status:");
        return JsonUtil.parseBitrateStatusListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/Network/Interfaces/Diagnostics/Bitrate/Status",null,cookie)
        ));
    }

    public StoragemediumList querySystemDeviceStorageMedium(String deviceId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Storage/Medium:");
        return JsonUtil.parseStorageMediumListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Storage/Medium",null,cookie)
        ));
    }

    public StorageMedium querySystemDeviceStorageMedium(String deviceId, String mediumId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Storage/Medium/"+mediumId+":");
        return JsonUtil.parseStorageMediumJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Storage/Medium/"+mediumId,null,cookie)
        ));
    }

    public StorageMediumStatus querySystemDeviceStorageMediumStatus(String deviceId, String mediumId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Storage/Medium/"+mediumId+"/Status:");
        return JsonUtil.parseStorageMediumStatusJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Storage/Medium/"+mediumId+"/Status",null,cookie)
        ));
    }

    public MapList querySystemMaps() throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Maps:");
        return JsonUtil.parseMapListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Maps",null,cookie)
        ));
    }

    public Map querySystemMaps(String mapId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Maps/"+mapId+":");
        return JsonUtil.parseMapJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Maps/"+mapId,null,cookie)
        ));
    }

    public String querySystemMapData(String mapId) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Maps/"+mapId+"/Data:");
        return handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Maps/"+mapId+"/Data",null,cookie);
    }

    public MapItemList querySystemMapItems(String mapId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Maps/"+mapId+"/Items:");
        return JsonUtil.parseMapItemListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Maps/"+mapId+"/Items",null,cookie)
        ));
    }

    public MapItem querySystemMapItems(String mapId,String itemId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Maps/"+mapId+"/Items/"+itemId+":");
        return JsonUtil.parseMapItemJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Maps/"+mapId+"/Items/"+itemId,null,cookie)
        ));
    }

    public LinkageTemplateList querySystemLinkageTemplates() throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Linkage/Templates:");
        return JsonUtil.parseLinkageTemplateListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Linkage/Templates",null,cookie)
        ));
    }

    public LinkageTemplate querySystemLinkageTemplates(String templatesId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Linkage/Templates/"+templatesId+":");
        return JsonUtil.parseLinkageTemplateJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Linkage/Templates/"+templatesId,null,cookie)
        ));
    }

    public EventRecordedList querySystemEventsRecords() throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Events/Records:");
        return JsonUtil.parseEventRecordListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Events/Records",null,cookie)
        ));
    }

    public EventLinkageTemplateList querySystemEventsLinkagesTemplates() throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Events/Linkages/Templates:");
        return JsonUtil.parseEventLinkageTemplateListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Events/Linkages/Templates",null,cookie)
        ));
    }

    public EventLinkageTemplate querySystemEventsLinkagesTemplates(String templatesId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Events/Linkages/Templates/"+templatesId+":");
        return JsonUtil.parseEventLinkageTemplateJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Events/Linkages/Templates/"+templatesId,null,cookie)
        ));
    }

    public EventLinkageList querySystemEventsLinkages() throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Events/Linkages:");
        return JsonUtil.parseEventLinkageListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Events/Linkages",null,cookie)
        ));
    }

    public EventLinkage querySystemEventsLinkages(String componentsId,String eventType,String eventState) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Events/Linkages/Components/"+componentsId+"/"+eventType+"/"+eventState+":");
        return JsonUtil.parseEventLinkageJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Events/Linkages/Components/"+componentsId+"/"+eventType+"/"+eventState,null,cookie)
        ));
    }

    public VideoNetworkStateLogList querySystemLogsVideoInputNetworkState(String deviceId,String channelId,String begTime,String endTime) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Logs/Devices/"+deviceId+"/Video/Inputs/Channels/"+channelId+"/NetworkState:");
        return JsonUtil.parseVideoNetworkStateLogList(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Logs/Devices/"+deviceId+"/Video/Inputs/Channels/"+channelId+"/NetworkState"+"?BeginTime="+begTime+"&EndTime="+endTime,null,cookie)
        ));
    }

    public VideoConnectionLogList querySystemLogsVideoInputConnection(String deviceId, String channelId, String begTime, String endTime) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Logs/Devices/"+deviceId+"/Video/Inputs/Channels/"+channelId+"/Connection:");
        return JsonUtil.parseVideoConnectLogList(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Logs/Devices/"+deviceId+"/Video/Inputs/Channels/"+channelId+"/Connection"+"?BeginTime="+begTime+"&EndTime="+endTime,null,cookie)
        ));
    }

    public TeardownLogList querySystemLogsTeardown(String deviceId, String begTime, String endTime) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Logs/Devices/"+deviceId+"/Teardown:");
        return JsonUtil.parseTeardownLogListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Logs/Devices/"+deviceId+"/Teardown",null,cookie)
        ));
    }

    public StorageMediumAbnormalLogList querySystemLogsStorageMedium(String deviceId, String mediumId, String begTime, String endTime) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Logs/Devices/"+deviceId+"/Storage/Medium/"+mediumId+"/Abnormal:");
        return JsonUtil.parseStorageMediumAbnormalLogList(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Logs/Devices/"+deviceId+"/Storage/Medium/"+mediumId+"/Abnormal"+"?BeginTime="+begTime+"&EndTime="+endTime,null,cookie)
        ));
    }

    public HeartbeatLogList querySystemLogsHeartbeats(String deviceId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Logs/Devices/"+deviceId+"/Heartbeats:");
        return JsonUtil.parseHeartbeatLogList(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Logs/Devices/"+deviceId+"/Heartbeats",null,cookie)
        ));
    }

    //// FIXME: 2017/4/19 404
    public RFIDAntennaList querySystemDeviceRfidAntennas(String deviceId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/RFID/Antennas:");
        return JsonUtil.parseRfidAntennaList(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/RFID/Antennas",null,cookie)
        ));
    }

    public RFIDAntenna querySystemDeviceRfidAntennas(String deviceId, String antennasId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/RFID/Antennas/"+antennasId+":");
        return JsonUtil.parseRfidAntenna(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/RFID/Antennas/"+antennasId,null,cookie)
        ));
    }

    //// FIXME: 2017/4/19  404
    public RFIDCardList querySystemRfidCards() throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/RFID/Cards:");
        return JsonUtil.parseRfidCardList(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/RFID/Cards",null,cookie)
        ));
    }

    public RFIDCard querySystemRfidCards(String cardId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/RFID/Cards/"+cardId+":");
        return JsonUtil.parseRfidCard(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/RFID/Cards/"+cardId,null,cookie)
        ));
    }

    public RFIDGroupList querySystemRfidGroups() throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/RFID/Groups:");
        return JsonUtil.parseRfidGroupList(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/RFID/Groups",null,cookie)
        ));
    }

    public RFIDGroup querySystemRfidGroups(String groupId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/RFID/Groups/"+groupId+":");
        return JsonUtil.parseRfidGroup(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/RFID/Groups/"+groupId,null,cookie)
        ));
    }

    public RFIDCardList querySystemRfidGroupsCards(String groupId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/RFID/Groups/"+groupId+"/Cards:");
        return JsonUtil.parseRfidCardList(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/RFID/Groups/"+groupId+"/Cards",null,cookie)
        ));
    }

    public RFIDCard querySystemRfidGroupsCards(String groupId,String cardId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/RFID/Groups/"+groupId+"/Cards/"+cardId+":");
        return JsonUtil.parseRfidCard(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/RFID/Groups/"+groupId+"/Cards/"+cardId,null,cookie)
        ));
    }


    public RFIDGroupPriorityList querySystemRfidGroupPrioritesAntennas(String groupId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/RFID/Groups/"+groupId+"/Priorites/Antennas:");
        return JsonUtil.parseRfidGroupPriorityList(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/RFID/Groups/"+groupId+"/Priorites/Antennas",null,cookie)
        ));
    }

    public RFIDGroupPriority querySystemRfidGroupPrioritesAntennas(String groupId, String antennasId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/RFID/Groups/"+groupId+"/Priorites/Antennas/"+antennasId+":");
        return JsonUtil.parseRfidGroupPriority(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/RFID/Groups/"+groupId+"/Priorites/Antennas/"+antennasId,null,cookie)
        ));
    }

    public RFIDAntennaList querySystemRfidAntennas() throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/RFID/Antennas:");
        return JsonUtil.parseRfidAntennaList(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/RFID/Antennas",null,cookie)
        ));
    }

    public RFIDAntenna querySystemRfidAntennas(String antennasId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/RFID/Antennas/"+antennasId+":");
        return JsonUtil.parseRfidAntenna(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/RFID/Antennas/"+antennasId,null,cookie)
        ));
    }

    public RFIDGroupPriorityList querySystemRfidAntennasPrioritesGroup(String antennasId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/RFID/Antennas/"+antennasId+"/Priorites/Groups:");
        return JsonUtil.parseRfidGroupPriorityList(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/RFID/Antennas/"+antennasId+"/Priorites/Groups",null,cookie)
        ));
    }

    public RFIDGroupPriority querySystemRfidAntennasPrioritesGroup(String antennasId,String groupId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/RFID/Antennas/"+antennasId+"/Priorites/Groups/"+groupId+":");
        return JsonUtil.parseRfidGroupPriority(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/RFID/Antennas/"+antennasId+"/Priorites/Groups/"+groupId,null,cookie)
        ));
    }

    public NoticeList querySystemNotices() throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Notices:");
        return JsonUtil.parseNoticeListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Notices",null,cookie)
        ));
    }



    public NoticeList querySystemNotices(@Nullable String classification,@Nullable String status,@Nullable String noticeType,@Nullable String sender,@Nullable String userId,int pageIndex,int pageSize) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        if (classification==null&&status==null&&noticeType==null&&sender==null&&userId==null&&pageSize==0)return querySystemNotices();
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Notices:");
        ArrayList<String> array = new ArrayList<>();
        array.add(classification);
        array.add(status);
        array.add(noticeType);
        array.add(sender);
        array.add(userId);
        array.add(pageSize==0?null:""+pageSize);
        return JsonUtil.parseNoticeListJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Notices?"+
                        (classification==null?"":"Classification="+classification)+
                        (status==null?"":(isfirst(array,1)?"":"&")+"Status="+status)+
                        (noticeType==null?"":(isfirst(array,2)?"":"&")+"NoticeType="+noticeType)+
                        (sender==null?"":(isfirst(array,3)?"":"&")+"Sender="+sender)+
                        (userId==null?"":(isfirst(array,4)?"":"&")+"UserId="+userId)+
                        (pageSize==0?"":(isfirst(array,5)?"":"&")+"PageIndex="+pageIndex+"&PageSize="+pageSize)
                ,null,cookie)
        ));
    }

    public Notice querySystemNotices(String noticeId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Notices/"+noticeId+":");
        return JsonUtil.parseNoticeJsonObject(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Notices/"+noticeId,null,cookie)
        ));
    }

    public Fault setSystemNoticesRead(String noticeId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("POST:/howell/ver10/data_service/management/System/Notices/"+noticeId+"/Read:");
        return JsonUtil.parseFaultJsonObject(new JSONObject(
                handleHttpProtocol(POST,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Notices/"+noticeId+"/Read",null,cookie)
        ));
    }

    public ServerInformation querySystemDeviceAcquisition(String deviceId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Acquisition:");
        return JsonUtil.parseServerInformation(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/data_service/management/System/Devices/"+deviceId+"/Acquisition",null,cookie)
        ));
    }







    /************************GPS  service*************************/
    /**
     * GPS <br/>
     * query gps server soft version
     * @return version
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public ServiceVersion queryGPSServerVersion() throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/gps_service/System/Version:");
        return JsonUtil.parseServiceVersion(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/gps_service/System/Version",null,cookie)
        ));
    }

    /**
     * GPS <br/>
     * query gps device list
     * @return gps list
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public GPSDeviceList queryGPSDeviceList() throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/gps_service/System/Devices:");
        return JsonUtil.parseGPSDeviceList(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/gps_service/System/Devices",null,cookie)
        ));
    }

    /**
     * GPS <br/>
     * query gps device with device id
     * @param deviceId device id
     * @param pageIndex page index
     * @param pageSize page size;size=0 query all
     * @return gps device list
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public GPSDeviceList queryGPSDeviceList(@Nullable String deviceId,int pageIndex,int pageSize) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        if (deviceId==null&&pageSize==0)return queryGPSDeviceList();
        String cookie = getCookie("GET:/howell/ver10/gps_service/System/Devices:");
        ArrayList<String> array = new ArrayList<>();
        array.add(deviceId);
        array.add(pageSize==0?null:pageSize+"");
        return JsonUtil.parseGPSDeviceList(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/gps_service/System/Devices?"+
                        (deviceId==null?"":"DeviceId="+deviceId)+
                        (pageSize==0?"":(isfirst(array,1)?"":"&")+"PageIndex="+pageIndex+"&PageSize="+pageSize)
                ,null,cookie)
        ));
    }

    /**
     * GPS <br/>
     * query gps device with device id
     * @param deviceId device id
     * @return gps device
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public GPSDevice queryGPSDevice(String deviceId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/gps_service/System/Devices/"+deviceId+":");
        return JsonUtil.parseGPSDevice(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/gps_service/System/Devices/"+deviceId,null,cookie)
        ));
    }

    /**
     * GPS <br/>
     * query gps RMC list (gps history)
     * @param deviceId device id
     * @param begTime begin time
     * @param endTime end time
     * @param status status
     * @param interval interval
     * @param pageIndex page index
     * @param pageSize page size
     * @return list
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public RMCList queryGPSRMCList(String deviceId,String begTime,String endTime,@Nullable Integer status,@Nullable  Integer interval,int pageIndex,int pageSize) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/gps_service/System/Devices/"+deviceId+"/RMC:");
        return JsonUtil.parseRMCList(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/gps_service/System/Devices/"+deviceId+"/RMC?"+"BeginTime="+begTime+"&EndTime="+endTime+
                                (status==null?"":"&Status="+status)+
                                (interval==null?"":"&Interval="+interval)+
                                (pageSize==0?"":"&PageIndex="+pageIndex+"&PageSize="+pageSize)
                ,null,cookie)
        ));
    }

    /**
     * GPS <br/>
     * query device
     * @return device list
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public GPSDeviceList queryGPSDeviceAccess() throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/gps_service/System/Devices/Access:");
        return JsonUtil.parseGPSDeviceList(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/gps_service/System/Devices/Access",null,cookie)
        ));
    }

    /**
     * GPS<br/>
     * query device with access id (serial id)
     * @param accessId id
     * @param pageIndex index
     * @param pageSize size
     * @return device list
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public GPSDeviceList queryGPSDeviceAccess(@Nullable String accessId,int pageIndex,int pageSize) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        if (accessId==null&&pageSize==0)return queryGPSDeviceAccess();
        String cookie = getCookie("GET:/howell/ver10/gps_service/System/Devices/Access:");
        ArrayList<String>array = new ArrayList<>();
        array.add(accessId);
        array.add(pageSize==0?null:pageSize+"");
        return JsonUtil.parseGPSDeviceList(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/gps_service/System/Devices/Access?"+
                                (accessId==null?"":"AccessId="+accessId)+
                                (pageSize==0?"":(isfirst(array,1)?"":"&")+"PageIndex="+pageIndex+"&PageSize="+pageSize)
                ,null,cookie)
        ));
    }

    /**
     * GPS<br/>
     * query device
     * @param deviceId device id
     * @return gps device
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public GPSDevice queryGPSDeviceAccess(String deviceId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/gps_service/System/Devices/Access/"+deviceId+":");
        return JsonUtil.parseGPSDevice(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/gps_service/System/Devices/Access/"+deviceId,null,cookie)
        ));
    }

    /**
     * GPS<br/>
     * query device access RMC list (gps history)
     * @param deviceId device id
     * @param begTime query begin time
     * @param endTime query end time
     * @param status status 1:0 ;
     * @param interval interval per sec
     * @param pageIndex page index
     * @param pageSize size ;size=0 query all
     * @return rmc list
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public RMCList queryGPSDeviceAccessRMCList(String deviceId,String begTime,String endTime,@Nullable Integer status,@Nullable Integer interval,int pageIndex,int pageSize) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/gps_service/System/Devices/Access/"+deviceId+"/RMC:");
        return JsonUtil.parseRMCList(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/gps_service/System/Devices/Access/"+deviceId+"/RMC?"+
                                ("BeginTime="+begTime+"&EndTime="+endTime)+
                                (status==null?"":"&Status="+status)+
                                (interval==null?"":"&Interval="+interval)+
                                (pageSize==0?"":"&PageIndex="+pageIndex+"&PageSize="+pageSize)
                ,null,cookie)
        ));
    }

    /******************vehicle service*******************************/

    /**
     * VEHICLE <br/>
     * query vehicle server soft version
     * @return version
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public ServiceVersion queryVehicleServiceVersion() throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/vehicle_service/System/Version:");
        return JsonUtil.parseServiceVersion(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/vehicle_service/System/Version",null,cookie)
        ));
    }

    /**
     * VEHICLE <br/>
     * query vehicle plate device list
     * @return plate list
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public VehiclePlateDeviceList queryVehiclePlateDeviceList() throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/vehicle_service/System/Devices:");
        return JsonUtil.parseVehiclePlateDeviceList(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/vehicle_service/System/Devices",null,cookie)
        ));
    }

    /**
     * VEHICLE <br/>
     * query vehicle plate device list with device id
     * @param deviceId device id
     * @param pageIndex index
     * @param pageSize size
     * @return device list
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public VehiclePlateDeviceList queryVehiclePlateDeviceList(@Nullable String deviceId,int pageIndex,int pageSize) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        if (deviceId==null&& pageSize==0) return queryVehiclePlateDeviceList();
        String cookie = getCookie("GET:/howell/ver10/vehicle_service/System/Devices:");
        ArrayList<String> array = new ArrayList<>();
        array.add(deviceId);
        array.add(pageSize==0?null:pageSize+"");
        return JsonUtil.parseVehiclePlateDeviceList(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/vehicle_service/System/Devices?"+
                                (deviceId==null?"":"DeviceId="+deviceId)+
                                (pageSize==0?"":(isfirst(array,1)?"":"&")+"PageIndex="+pageIndex+"&PageSize="+pageSize)
                ,null,cookie)
        ));
    }

    /**
     * VEHICLE <br/>
     * query vehicle plate device list with device id
     * @param deviceId device id
     * @return plate device
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public VehiclePlateDevice queryVehiclePlateDevice(String deviceId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/vehicle_service/System/Devices/"+deviceId+":");
        return JsonUtil.parseVehiclePlateDevice(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/vehicle_service/System/Devices/"+deviceId,null,cookie)
        ));
    }

    /**
     * VEHICLE <br/>
     * query plate record
     * @param deviceId device id
     * @param begTime record begin time
     * @param endTime record end time
     * @param pageIndex page index
     * @param pageSize page size ;size=0 query all
     * @return plate record list
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public VehiclePlateRecordList queryVehiclePlateRecords(String deviceId,String begTime,String endTime,int pageIndex,int pageSize) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/vehicle_service/System/Devices/"+deviceId+"/Records:");
        return JsonUtil.parseVehiclePlateRecordList(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/vehicle_service/System/Devices/"+deviceId+"/Records?"+
                                ("BeginTime="+begTime+"&EndTime="+endTime)+
                                (pageSize==0?"":"&PageIndex="+pageIndex+"&PageSize="+pageSize)
                        ,null,cookie)
        ));
    }

    /**
     * VEHICLE <br/>
     * query plate device access (serial id ) with has bind to server
     * @return plate device list
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public VehiclePlateDeviceList queryVehiclePlateDeviceAccess() throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/vehicle_service/System/Devices/Access:");
        return JsonUtil.parseVehiclePlateDeviceList(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/vehicle_service/System/Devices/Access",null,cookie)
        ));
    }

    /**
     * VEHICLE <br/>
     * query plate device list with id
     * @param accessId access id (serial id)
     * @param pageIndex page index
     * @param pageSize page size
     * @return plate device list
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public VehiclePlateDeviceList queryVehiclePlateDeviceAccess(String accessId,int pageIndex,int pageSize) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        if (accessId==null&&pageSize==0) return queryVehiclePlateDeviceAccess();
        String cookie = getCookie("GET:/howell/ver10/vehicle_service/System/Devices/Access:");
        ArrayList<String> array = new ArrayList<>();
        array.add(accessId);
        array.add(pageSize==0?null:pageSize+"");
        return JsonUtil.parseVehiclePlateDeviceList(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/vehicle_service/System/Devices/Access?"+
                                (accessId==null?"":"AccessId="+accessId)+
                                (pageSize==0?"":(isfirst(array,1)?"":"&")+"PageIndex="+pageIndex+"&PageSize="+pageSize)
                        ,null,cookie)
        ));
    }

    /**
     * VEHICLE <br/>
     * query plate device with access id (serial id)
     * @param accessId access id
     * @return plate device
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public VehiclePlateDevice queryVehiclePlateDeviceAccess(String accessId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/vehicle_service/System/Devices/Access/"+accessId+":");
        return JsonUtil.parseVehiclePlateDevice(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/vehicle_service/System/Devices/Access/"+accessId,null,cookie)
        ));
    }

    /**
     * VEHICLE <br/>
     * query plate record with access id
     * @param accessId access id (serial id)
     * @param begTime record begin time
     * @param endTime record end time
     * @param pageIndex page index
     * @param pageSize page size ;size=0 query all
     * @return record list
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public VehiclePlateRecordList queryVehiclePlateDeviceAccessRecords(@NonNull String accessId,@NonNull String begTime,@NonNull String endTime, int pageIndex, int pageSize) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/vehicle_service/System/Devices/Access/"+accessId+"/Records:");
        return JsonUtil.parseVehiclePlateRecordList(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/vehicle_service/System/Devices/Access/"+accessId+"/Records?"+
                                ("BeginTime="+begTime+"&EndTime="+endTime)+
                                (pageSize==0?"":"&PageIndex="+pageIndex+"&PageSize="+pageSize)
                        ,null,cookie)
        ));
    }

    /**
     * VEHICLE <br/>
     * query record
     * @param begTime record begin time
     * @param endTime record end time
     * @param plate vehicle plate
     * @param brand vehicle brand
     * @param name  vehicle name
     * @param deviceId plate record id
     * @param accessId access id
     * @param pageIndex page index
     * @param pageSize page size; size=0 query all
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public VehiclePlateRecordList queryVehiclePlateDeviceRecord(String begTime,String endTime,@Nullable String plate,@Nullable String brand,@Nullable String name,@Nullable String deviceId,@Nullable String accessId,int pageIndex,int pageSize) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/vehicle_service/System/Devices/Records:");
        return JsonUtil.parseVehiclePlateRecordList(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/vehicle_service/System/Devices/Records?"+
                                ("BeginTime="+begTime+"&EndTime="+endTime)+
                                (plate==null?"":"&Plate="+plate)+
                                (brand==null?"":"&Brand="+brand)+
                                (name==null?"":"&Name="+name)+
                                (deviceId==null?"":"&DeviceId="+deviceId)+
                                (accessId==null?"":"&AccessId="+accessId)+
                                (pageSize==0?"":"&PageIndex="+pageIndex+"&PageSize="+pageSize)
                        ,null,cookie)
        ));
    }

    /**
     * VEHICLE <br/>
     * query send picture to server
     * @param imageData picture
     * @return picture information from server
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public  VehiclePlatePicture updataVehiclePicture(byte [] imageData) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("POST:/howell/ver10/vehicle_service/System/Pictures:");
        return JsonUtil.parseVehiclePlatePicture(new JSONObject(
                handleHttpProtocol(mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/vehicle_service/System/Pictures",imageData,cookie)
        ));
    }

    /**
     * VEHICLE <br/>
     * query picture with picture id
     * @param pictureId picture id
     * @return picture information from server
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public VehiclePlatePicture queryVehiclePicture(String pictureId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/vehicle_service/System/Pictures/"+pictureId+":");
        return JsonUtil.parseVehiclePlatePicture(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/vehicle_service/System/Pictures/"+pictureId,null,cookie)
        ));
    }

    /**
     * VEHICLE <br/>
     * query picture data with picture id
     * @param pictureId picture id
     * @return data
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public byte [] queryVehiclePictureData(String pictureId) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String cookie = getCookie("GET:/howell/ver10/vehicle_service/System/Pictures/"+pictureId+"/Data:");
        return handleHttpProtocol(mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/vehicle_service/System/Pictures/"+pictureId+"/Data",(JSONObject) null,cookie);
    }

    /**
     * VEHICLE <br/>
     * query picture detect result
     * @param pictureId picture id
     * @return detect result
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public VehicleDetectionResult queryVehicleDetectionResult(String pictureId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/vehicle_service/System/Pictures/"+pictureId+"/Detection:");
        return JsonUtil.parseVehicleDetectionResult(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/vehicle_service/System/Pictures/"+pictureId+"/Detection",null,cookie)
        ));
    }

    /**
     * VEHICLE <br/>
     * query vehicle list
     * @return vehicle list
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public VehicleList queryVehicleList() throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/vehicle_service/System/Vehicles:");
        return JsonUtil.parseVehicleList(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/vehicle_service/System/Vehicles",null,cookie)
        ));
    }

    /**
     * VEHICLE <br/>
     * query vehicle list
     * @param plate vehicle plate
     * @param brand vehicle brand
     * @param existedInBlackList if vehicle in blacklist
     * @param pageIndex page index
     * @param pageSize page size ; size =0 query all
     * @return vehicle list
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public VehicleList queryVehicleList(@Nullable String plate,@Nullable String brand,@Nullable Boolean existedInBlackList,int pageIndex,int pageSize) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        if (plate==null&&brand==null&&existedInBlackList==null&&pageSize==0)return queryVehicleList();
        String cookie = getCookie("GET:/howell/ver10/vehicle_service/System/Vehicles:");
        ArrayList<String> array = new ArrayList<>();
        array.add(plate);
        array.add(brand);
        array.add(existedInBlackList==null?null:existedInBlackList+"");
        array.add(pageSize==0?null:pageSize+"");
        return JsonUtil.parseVehicleList(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/vehicle_service/System/Vehicles?"+
                                (plate==null?"":"Plate="+plate)+
                                (brand==null?"":(isfirst(array,1)?"":"&")+"Brand="+brand)+
                                (existedInBlackList==null?"":(isfirst(array,2)?"":"&")+"ExistedInBlackList="+existedInBlackList)+
                                (pageSize==0?"":(isfirst(array,3)?"":"&")+"PageIndex="+pageIndex+"&PageSize="+pageSize)
                        ,null,cookie)
        ));
    }

    /**
     * VEHICLE <br/>
     * query vehicle with vehicle id
     * @param vehicleId vehicle id
     * @return vehicle
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public Vehicle queryVehicle(String vehicleId) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        String cookie = getCookie("GET:/howell/ver10/vehicle_service/System/Vehicles/"+vehicleId+":");
        return JsonUtil.parseVehicle(new JSONObject(
                handleHttpProtocol(GET,mHttp+"://"+mServerIP+":"+mPort+"/howell/ver10/vehicle_service/System/Vehicles/"+vehicleId,null,cookie)
        ));
    }

}
