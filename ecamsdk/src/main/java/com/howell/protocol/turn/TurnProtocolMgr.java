package com.howell.protocol.turn;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.howell.bean.turnbean.HWTransmissonHead;
import com.howell.bean.turnbean.HWTurnPushHead;
import com.howell.bean.turnbean.PTZ_CMD;
import com.howell.bean.turnbean.TurnConnectAckBean;
import com.howell.bean.turnbean.TurnConnectBean;
import com.howell.bean.turnbean.TurnDisConnectAckBean;
import com.howell.bean.turnbean.TurnDisConnectBean;
import com.howell.bean.turnbean.TurnDisSubscribeAckBean;
import com.howell.bean.turnbean.TurnDisSubscribeBean;
import com.howell.bean.turnbean.TurnGetCamAckBean;
import com.howell.bean.turnbean.TurnGetCamBean;
import com.howell.bean.turnbean.TurnGetRecordedFileAckBean;
import com.howell.bean.turnbean.TurnGetRecordedFilesBean;
import com.howell.bean.turnbean.TurnPtzCtrlAckBean;
import com.howell.bean.turnbean.TurnPtzCtrlBean;
import com.howell.bean.turnbean.TurnSubScribe;
import com.howell.bean.turnbean.TurnSubScribeAckBean;
import com.howell.jni.JniUtil;

import com.howell.protocol.utils.SSLSocketUtil;


import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;


import struct.JavaStruct;
import struct.StructException;

/**
 * Turn protocol manager
 * @deprecated because low effect in java,and {@link TurnManager} in jni
 */
@Deprecated
public class TurnProtocolMgr implements SSLSocketUtil.IProcessMsg {
	private static TurnProtocolMgr mInstance = null;
	public static TurnProtocolMgr getInstance(){
		if (mInstance==null){
			mInstance = new TurnProtocolMgr();
		}
		return mInstance;
	}
	private TurnProtocolMgr(){}
	int m_seq = 0;
	int headLen = 0;
	int pushHeadLen = 0;
	Context context;
	String sessionID = null;
	String subscribeID = null;
	int dialogId = 0;
	boolean bSubscribe = false;
	PushThread pushThread = null;
	String mAccount;
	String mPassword;
    String mDeviceId;
    boolean mIsSSL;
    boolean mIsSub;

	ArrayList<IProtocolResult> mCbs;

	private void sendSocketCb(boolean bOK){
		if (mCbs==null)return;
		for (IProtocolResult r:mCbs){
			r.socketRes(bOK);
		}
	}


	private void sendConnectCb(boolean bOK,@Nullable String str){
		if (mCbs==null)return;
		for (IProtocolResult r:mCbs){
			r.connectRes(bOK,str);
		}
	}

	private void sendDisconnectCb(boolean bOK,@Nullable String str){
		if (mCbs==null)return;
		for (IProtocolResult r:mCbs){
			r.disconnectRes(bOK,str);
		}
	}

	private void sendSubscribeCb(boolean bOK,@Nullable String str,@Nullable TurnSubScribeAckBean bean){
		if (mCbs==null)return;
		for (IProtocolResult r:mCbs){
			r.subscribeRes(bOK,str,bean);
		}
	}

	private void sendUnsubscribeCb(boolean bOK,@Nullable String str){
		if (mCbs == null)return;
		for (IProtocolResult r:mCbs){
			r.unsubscribeRes(bOK,str);
		}
	}

	private void sendRecordedFileCb(boolean bOK,@Nullable String str,@Nullable TurnGetRecordedFileAckBean bean){
		if (mCbs == null)return;
		for (IProtocolResult r:mCbs){
			r.recordedFileRes(bOK,str,bean);
		}
	}

	private void sendPTZCb(boolean bOK,@Nullable String str){
		if (mCbs == null)return;
		for (IProtocolResult r:mCbs){
			r.ptzRes(bOK,str);
		}
	}

	private void sendCameraCb(boolean bOK,@Nullable String str,@Nullable TurnGetCamAckBean res){
		if (mCbs == null)return;
		for (IProtocolResult r:mCbs){
			r.getCameraRes(bOK,str,res);
		}
	}



	private int getNewDialogId(){
		return dialogId++;
	}


	public TurnProtocolMgr turnInit(Context context, String ip, int port , boolean isSSL, IProtocolResult res){
		this.context = context;
		registProtocolResultCallback(res);
		SSLSocketUtil.getInstance().init(context,ip,port,this);
		SSLSocketUtil.getInstance().connectSocket(isSSL);
		return this;
	}

	public void turnDeInit(){
		mCbs.clear();
		mCbs = null;
		SSLSocketUtil.getInstance().disConnectSocket();
	}

	public TurnProtocolMgr registProtocolResultCallback(IProtocolResult result){
		if (mCbs==null){
			mCbs = new ArrayList<>();
		}
		for (IProtocolResult r:mCbs){
			if (r.equals(result))return this;
		}
		mCbs.add(result);
		return this;
	}

	public TurnProtocolMgr unregistProtocolResultCallback(IProtocolResult result){
		mCbs.remove(result);
		if (mCbs.size()==0)mCbs=null;
		return this;
	}




	private ByteBuffer readbuf = ByteBuffer.allocate(2*1024*1024);

	public void connect2TurnService(String imei,String account,String password){
		readbuf.clear();
		int type = 101;
		mAccount = account;
		mPassword = password;
		TurnConnectBean bean = new TurnConnectBean(type, imei, account, password);
		String jsonStr = TurnJsonUtil.getTurnConnectJsonStr(bean);
		byte flag = 0;
		short kmd =  KMD.kCmdConnect.getVal();
		send(kmd,flag,jsonStr,jsonStr.length());
	}
	
	public void disConnect2TurnService(){
		if (sessionID==null) {
			throw new NullPointerException("dis connect sessionId = null");
		}
//		readbuf.clear();
		String jsonStr = TurnJsonUtil.getTurnDisconnectJsonStr(new TurnDisConnectBean(sessionID));
		byte flag = 0;
		short kmd =  KMD.kCmdDisconnect.getVal();
		send(kmd, flag, jsonStr, jsonStr.length());
	}
	
	
	public void subScribeCamLiveStream(String deviceID,int channel,boolean isSubStream ){
//		readbuf.clear();
		int isSub = isSubStream?1:0;
		String deviceId = deviceID;
		TurnSubScribe.meta meta = new TurnSubScribe.meta(deviceId, "live", channel, isSub);
		TurnSubScribe.media media = new TurnSubScribe.media(getNewDialogId(), meta);
		TurnSubScribe bean = new TurnSubScribe(sessionID, "media", media);
		String jsonStr = TurnJsonUtil.getTurnSubScribe(bean);
		byte flag = 0;
		short kmd = KMD.kCmdSubscribe.getVal();
		send(kmd, flag, jsonStr, jsonStr.length());

	}
	
	public void subScribeCamPlaybackStream(String deviceID,int channel,boolean isSubStream,String beginTime,String endTime){
		int isSub = isSubStream?1:0;
		TurnSubScribe.meta meta = new TurnSubScribe.meta(deviceID, "playback", channel, isSub, beginTime, endTime);
		TurnSubScribe.media media = new TurnSubScribe.media(getNewDialogId(), meta);
		TurnSubScribe bean = new TurnSubScribe(sessionID, "media", media);
		String jsonStr = TurnJsonUtil.getTurnSubScribe(bean);
		byte flag = 0;
		short kmd = KMD.kCmdSubscribe.getVal();
		send(kmd, flag, jsonStr, jsonStr.length());
	}
	
	
	public void disSubscribeStream(){
//		readbuf.clear();
		TurnDisSubscribeBean bar = new TurnDisSubscribeBean(sessionID, subscribeID);
		String jsonStr = TurnJsonUtil.getTurnDisSubscribeJsonStr(bar);
		byte flag = 0;
		short kmd = KMD.kCmdUnsubscribe.getVal();
		send(kmd, flag, jsonStr, jsonStr.length());

	}
	
	public void getCamreaInfo(){
		TurnGetCamBean bar = new TurnGetCamBean(mAccount, mPassword);
		String jsonStr = TurnJsonUtil.getTurnCamJsonStr(bar);
		byte flag = 0;
		short kmd = KMD.kCmdGetCamrea.getVal();
		send(kmd, flag, jsonStr, jsonStr.length());
	}
	
	public void getRecordFiles(String deviceID,int channel,String beginTime,String endTime){
		TurnGetRecordedFilesBean bar = new TurnGetRecordedFilesBean(deviceID, channel, beginTime, endTime);
		String jsonStr = TurnJsonUtil.getTurnRecordFilesJsonStr(bar);
		byte flag = 0;
		short kmd = KMD.kCmdGetRecordedFiles.getVal();
		send(kmd, flag, jsonStr, jsonStr.length());
	}
	
	public void ptzCtrl(String deviceID, PTZ_CMD cmd, int channel, int speed, int preset){
		if (sessionID==null) throw new NullPointerException();
		TurnPtzCtrlBean bar = new TurnPtzCtrlBean(sessionID, deviceID, channel, cmd.getVal(), speed, preset);
		String jsonStr = TurnJsonUtil.getTurnPtzJsonStr(bar);
		byte flag = 0;
		short kmd = KMD.kCmdPtzCtrl.getVal();
		send(kmd, flag, jsonStr, jsonStr.length());
	}
	
	
	private byte [] buildHead(HWTransmissonHead head, short cmd, byte flag, int len) throws StructException {
		if(head == null) return null;
		Log.i("123", "sync:"+String.format("0x%x", head.getSync()));
		Log.i("123", "cmd="+cmd);
		head.setVersion((byte)1);
		head.setFlag(flag);
		try {
			head.setCommand(cmd);
			head.setSeq((short)m_seq++);
			head.setPayload_len(len);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return JavaStruct.pack(head);
	}
	
	
	private void send(short cmd,byte flag,String json,int jsonLen){
		HWTransmissonHead head = new HWTransmissonHead();
		byte[] headBuf = null;
		try {
			headBuf = buildHead(head, cmd, flag, jsonLen);
		} catch (StructException e) {
			e.printStackTrace();
		}
		headLen = headBuf.length;
		sendMsg(headBuf, json.getBytes());
	}
	
	private synchronized void sendMsg(byte[] head,byte [] body){
		if(head==null || body == null){
			Log.e("123", "null");
			return;
		}
		String string = new String(body);
		Log.i("123", "string="+string);
		Log.i("123", "head.len="+head.length+" body.len="+body.length);
		byte [] buf = new byte[head.length+body.length];
		System.arraycopy(head, 0, buf, 0,head.length);
		System.arraycopy(body, 0, buf, head.length, body.length);
		SSLSocketUtil.getInstance().write(buf);
//		buf = null;
	}

	@Override
	public void socketState(boolean isLink) {
		sendSocketCb(isLink);
	}

	@Override
	public void processMsg(byte[] data) {
		processMsssage(data);
	}

	public synchronized boolean processMsssage(byte [] buf){
		if(buf==null){
			Log.e("123", "buf = null");
			return false;
		}
		Log.i("123","before pos="+readbuf.position()+"    bufsize="+buf.length);
		readbuf.put(buf);
		Log.i("123", "after  pos="+readbuf.position()+"  limit="+readbuf.limit());//limit == readbuf.size();
		if(readbuf.position()<headLen){
			Log.e("123","  pos < headlen  pos="+readbuf.position()+"  headlen="+headLen);
			return false;
		}
		
		byte [] readBufArray = readbuf.array();
		Log.i("123", "readbufArray len="+readBufArray.length+"  now pos="+readbuf.position());
		int dataLen = 0;
		byte [] head = new byte[headLen];
		System.arraycopy(readBufArray, 0, head, 0, headLen);

		HWTransmissonHead headObj = new HWTransmissonHead();
		try {
			JavaStruct.unpack(headObj, head);
			dataLen = headObj.getPayload_len();
		} catch (StructException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(headObj.getSync()!=  (byte) (0xa5) ){
			Log.e("123", "sync!=0xa5 all the readbuf is been clear   sync="+headObj.getSync()+"  pos="+readbuf.position());
			readbuf.clear();
			return false;
		}

		if (readbuf.position()-headLen<dataLen){
			return false;
		}

		short cmd = 0;
		try {
			cmd =  headObj.getCommand();
			Log.i("123", "sync="+String.format("0x%x", headObj.getSync())+"  dataLen="+dataLen+" command="+String.format("0x%x", headObj.getCommand()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		byte [] body = new byte[dataLen];
		System.arraycopy(readBufArray, headLen, body, 0, dataLen);
		if (KMD.valueOf(cmd)== KMD.kCmdPush) {
			doPush(body,dataLen);
		}else{
			String jsonStr = new String(body);
			Log.i("123", "jsonStr="+jsonStr);
			phaseMsg(cmd, jsonStr);
		}
		readbuf.clear();
		return true;
	}
	
	
	private void phaseMsg(final short cmd,final String jsonStr){
		new Thread(){
			public void run() {
				switch (KMD.valueOf(cmd)) {
				case kCmdConnectAck:
					doConnectAck(TurnJsonUtil.getTurnConnectAckFromJsonStr(jsonStr));
					break;
				case kCmdDisconnectAck:
					doDisConnectAck(TurnJsonUtil.getTurnDisconnectAckFromJsonStr(jsonStr));
					break;
				case kCmdSubscribeAck:
					doSubscribeAck(TurnJsonUtil.getTurnSubscribeAckAllFromJsonStr(jsonStr));
					break;
				case kCmdUnsubscribeAck:
					doDisSubscribeAck(TurnJsonUtil.getTurnDisSubscribeAckFromJsonStr(jsonStr));
					break;
				case kCmdGetCamreaAck:
					doGetCameraAck(TurnJsonUtil.getTurnCamFromJsonStr(jsonStr));
					break;
				case kCmdGetRecordedFilesAck:
					doRecordedFileAck(TurnJsonUtil.getTurnRecordAckFromJsonStr(jsonStr));
					break;
				case kCmdPtzCtrlAck:
					doPtzAck(TurnJsonUtil.getTurnPtzAckFromJsonStr(jsonStr));
					break;
				default:
					break;
				}
			};
		}.start();
	}
	
	private void doConnectAck(TurnConnectAckBean bean){
		if (bean==null) throw new NullPointerException();
		Log.i("123", "code ="+bean.getCode()+" sessionid="+bean.getSessionId());
		if(bean.getCode()!=200){
			//fail
			sendConnectCb(false,bean.getDetail());
		}else{
			//success
			sessionID = bean.getSessionId();
			sendConnectCb(true,null);
		}
	}
	
	private void doDisConnectAck(TurnDisConnectAckBean bean){
		if (bean==null) throw new NullPointerException();
		if (bean.getCode()!=200) {
			sendDisconnectCb(false,bean.getDetail());
		}else{
			sendDisconnectCb(true,null);
		}
	}
	
	private void doSubscribeAck(TurnSubScribeAckBean bean){
		if (bean==null) throw new NullPointerException();
		if (bean.getCode()!=200) {
			sendSubscribeCb(false,bean.getDetail(),null);
		}else{
			subscribeID = bean.getSubscribeId();
			bSubscribe = true;
			if (pushThread==null) {
				pushThread = new PushThread();
				pushThread.start();
			}
			sendSubscribeCb(true,null,bean);
		}
	}
	
	private void doDisSubscribeAck(TurnDisSubscribeAckBean bean){
		if (bean==null) throw new NullPointerException();
		if (bean.getCode()!=200) {
			sendUnsubscribeCb(false,bean.getDetail());
		}else{
			bSubscribe = false;
			if (pushThread!=null) {
				try {
					pushThread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				pushThread = null;
			}
			sendUnsubscribeCb(true,null);
		}
	}
	
	private void doRecordedFileAck(TurnGetRecordedFileAckBean bean){
		if (bean==null) throw new NullPointerException();
		if (bean.getCode()!=200) {
			sendRecordedFileCb(false,bean.getDetail(),null);
		}else{
			sendRecordedFileCb(true,null,bean);
		}
		
	}
	
	private void doPtzAck(TurnPtzCtrlAckBean bean){
		if (bean==null) throw new NullPointerException();
		if (bean.getCode()!=200) {
			sendPTZCb(false,bean.getDetail());
		}else{
			sendPTZCb(true,null);
		}
		
	}
	
	private void doPush(final byte[]data,final int len){
		if (pushThread==null) {
			throw new NullPointerException("doPush but pushThread=null");
		}
		pushThread.pushData(data, len);
	}
	
	private void doGetCameraAck(TurnGetCamAckBean bean){
		if (bean==null) throw new NullPointerException();
		if (bean.getCode() != 200) {
			sendCameraCb(false,bean.getDetail(),null);
		}else{
			sendCameraCb(true,null,bean);
		}
		
	}
	
	
	
	
	
	private void pushFun(final byte [] data,final int datalen){
		HWTurnPushHead pushHead = new HWTurnPushHead();
		if (pushHeadLen==0) {
			byte [] bar = null;
			try {
				bar = JavaStruct.pack(pushHead);
			} catch (StructException e) {
				e.printStackTrace();
			}
			pushHeadLen = bar.length;
		}
		byte [] pushHeadBuf = new byte[pushHeadLen];
		System.arraycopy(data, 0, pushHeadBuf, 0, pushHeadLen);
		try {
			JavaStruct.unpack(pushHead, pushHeadBuf);
		} catch (StructException e) {
			e.printStackTrace();
		}
		byte [] pushBuf = new byte[datalen - pushHeadLen];
		//TODO get pushbuf
		System.arraycopy(data, pushHeadLen, pushBuf, 0, datalen-pushHeadLen);
		JniUtil.turnInputViewData(pushBuf, pushBuf.length);
	}
	
	
	private class PushThread extends Thread{
		private Queue<PushData> pushDataQueue =  new ArrayBlockingQueue<PushData>(25);
		public void pushData(byte [] data,int len){
			pushDataQueue.offer(new PushData(data, len));
		}
		private PushData popData(){
			return pushDataQueue.poll();
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(bSubscribe){
				PushData pushData = popData();
				if (pushData!=null) {
					byte [] data = pushData.getData();
					int len = pushData.getLen();
					pushFun(data,len);
				}else{
					try {
						sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}	
			}
			super.run();
		}
	}
	
	private class PushData{
		byte [] data;
		int len;
		public byte[] getData() {
			return data;
		}
		public void setData(byte[] data) {
			this.data = data;
		}
		public int getLen() {
			return len;
		}
		public void setLen(int len) {
			this.len = len;
		}
		public PushData(byte[] data, int len) {
			super();
			this.data = data;
			this.len = len;
		}
	}
	
	public enum KMD{
		kCmdConnect((short)0x10),
		kCmdConnectAck((short)0x11),
		kCmdDisconnect((short)0x12),
		kCmdDisconnectAck((short)0x13),
		kCmdSubscribe((short)0x14),
		kCmdSubscribeAck((short)0x15),
		kCmdUnsubscribe((short)0x16),
		kCmdUnsubscribeAck((short)0x17),
		kCmdPushReq((short)0x18),
		kCmdPushReqAck((short)0x19),
		kCmdPush((short)0x20),
		kCmdGetCamrea((short)0x101),
		kCmdGetCamreaAck((short)0x102),
		kCmdGetRecordedFiles((short)0x103),
		kCmdGetRecordedFilesAck((short)0x104),
		kCmdPtzCtrl((short)0x105),
		kCmdPtzCtrlAck((short)0x106),
		kCmdNone((short)0xffff)
		;
		private short val;		
		private KMD(short val) {
			this.val = val;
		}		
		public final short getVal(){
			return val;
		}
		public static KMD valueOf(short v){
			switch (v) {
			case 0x10:
				return kCmdConnect;
			case 0x11:
				return kCmdConnectAck;
			case 0x12:
				return kCmdDisconnect;
			case 0x13:
				return kCmdDisconnectAck;
			case 0x14:
				return kCmdSubscribe;
			case 0x15:
				return kCmdSubscribeAck;
			case 0x16:
				return kCmdUnsubscribe;
			case 0x17:
				return kCmdUnsubscribeAck;
			case 0x18:
				return kCmdPushReq;
			case 0x19:
				return kCmdPushReqAck;
			case 0x20:
				return kCmdPush;
			case 0x101:
				return kCmdGetCamrea;
			case 0x102:
				return kCmdGetCamreaAck;
			case 0x103:
				return kCmdGetRecordedFiles;
			case 0x104:
				return kCmdGetRecordedFilesAck;
			case 0x105:
				return kCmdPtzCtrl;
			case 0x106:
				return kCmdPtzCtrlAck;
			default:
				break;
			}
			return kCmdNone;
		}
	}


	public interface IProtocolResult{
		void socketRes(boolean isOK);
		void connectRes(boolean isOK,@Nullable String failDetail);
		void disconnectRes(boolean isOK,@Nullable  String failDetail);
		void subscribeRes(boolean isOK,@Nullable String failDetail,TurnSubScribeAckBean res);
		void unsubscribeRes(boolean isOK,@Nullable String failDetail);
		void recordedFileRes(boolean isOk,@Nullable String failDetail,@Nullable TurnGetRecordedFileAckBean res);
		void ptzRes(boolean isOK,@Nullable String failDetail);
		void getCameraRes(boolean isOK,@Nullable String failDetail,@Nullable TurnGetCamAckBean res);
	}

}
