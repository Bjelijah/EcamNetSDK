package com.howell.protocol.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.SocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class SSLSocketUtil {
	private static final String TAG = SSLSocketUtil.class.getName();
	private static SSLSocketUtil mInstance = null;
	public static SSLSocketUtil getInstance(){
		if(mInstance == null){
			mInstance = new SSLSocketUtil();
		}
		return mInstance;
	}
	private SSLSocketUtil(){}

	Context mContext;
	String mIp;
	int mPort;

//	SSLSocket mSocket;
	Socket mSocket;
	OutputStream mOutputStream;
	InputStream mInputStream;

	Thread mReadThread;
	boolean mbConnected;
	boolean mIsSSL;
	IProcessMsg mCb;
	
	public void init(Context context,String ip,int port,IProcessMsg cb){
		this.mIp = ip;
		this.mPort = port;
		this.mContext = context;
		this.mCb = cb;
	}
	
	public void connectSocket(final boolean isSSL){
		mIsSSL = isSSL;
		new AsyncTask<Void, Void, Boolean>() {

			@Override
			protected Boolean doInBackground(Void... params) {
				try {
					SSLContext context; 
					KeyStore ks = KeyStore.getInstance("BKS");
					ks.load(mContext.getResources().getAssets().open("client.bks"),"123456".toCharArray());  
					KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
					kmf.init(ks,"123456".toCharArray());
//					context = SSLContext.getInstance("SSL");  
					context = SSLContext.getInstance("TLS");
					context.init(kmf.getKeyManagers(), new TrustManager[]{new MyX509TrustManager()}, null);  
					SocketFactory factory = context.getSocketFactory(); 
					if (isSSL) {
						mSocket = (SSLSocket) factory.createSocket(mIp, mPort);
					}else {
						mSocket = SocketFactory.getDefault().createSocket(mIp,mPort);
					}


//					mSocket.setSoLinger(true, 30);
//					mSocket.setSendBufferSize(4096);
//					mSocket.setReceiveBufferSize(2*1024*1024);
					
					mOutputStream = mSocket.getOutputStream();
					mInputStream  = mSocket.getInputStream();

					if(mOutputStream==null){
						SDKDebugLog.logE(TAG,"[connectSocket]: get out put stream = null");
					}else{

					}
					
					
					mbConnected = true;
					
					read();
					
					
				} catch (UnrecoverableKeyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					mbConnected = false;
				} catch (KeyManagementException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					mbConnected = false;
				} catch (KeyStoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					mbConnected = false;
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					mbConnected = false;
				} catch (CertificateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					mbConnected = false;
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					mbConnected = false;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					mbConnected = false;
				}catch(Exception e){
					e.printStackTrace();
					mbConnected = false;
				}
				return mbConnected;
			}
			
			protected void onPostExecute(Boolean result) {
//				handler.sendEmptyMessage(MSG_SOCK_CONNECT);
				if (result) mCb.socketState(true);
			}
			
		}.execute();
	}
	
	public void disConnectSocket(){
		mbConnected = false;
		new AsyncTask<Void,Void,Void>(){

			@Override
			protected Void doInBackground(Void... params) {
				try {
					mInputStream.close();
					mOutputStream.close();
					mSocket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		}.execute();

		mCb.socketState(false);
	}
	
	public void write(final byte [] buffer){
		if(!mbConnected){
			SDKDebugLog.logE(TAG,"[write :] socket not connect");
			return;
		}

		new Thread(){
			public void run() {
				try {
					mOutputStream.write(buffer);
					mOutputStream.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}.start();
	}
	
	public void read(){
		mReadThread = new ReadThread();
		mReadThread.start();
	}
	
	class ReadThread extends Thread{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			int ss=0;
			byte [] buffer = new byte[2*1024*1024];
			
			while(mbConnected){
				try {
					if(!mSocket.isClosed()){
						if(mSocket.isConnected()){
							
							ss = mInputStream.read(buffer);
							byte [] bar = new byte [ss];
							System.arraycopy(buffer, 0, bar, 0, ss);
//							ss = mIs.read(buffer);
							
//							TurnProtocolMgr.getInstance().processMsg(bar);
							if (mCb!=null){
								mCb.processMsg(bar);
							}
							
						}else{
							SDKDebugLog.logE(TAG,"[ReadThread] : read mSocket is not connect");
						}
						
						
						
						
					}else{
						SDKDebugLog.logE(TAG,"[ReadThread] : read socket is close");
					}
					
					
				
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					break;
				} catch (Exception e){
					e.printStackTrace();
					break;
				}
			}
			super.run();
		}
	}
	
	
	
	
	
	
	
	
	
	static class MyX509TrustManager implements X509TrustManager{
		private static  X509Certificate[] _AcceptedIssuers = new X509Certificate[]{};
		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
			if(null != chain){
				for(int k=0; k < chain.length; k++){
					X509Certificate cer = chain[k];
					print(cer);
				}
			}

		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
			if(null != chain){
				for(int k=0; k < chain.length; k++){
					X509Certificate cer = chain[k];
					print(cer);
				}
			}

		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {//返回受信任的证书组


			return _AcceptedIssuers;
		}


		private void print(X509Certificate cer){

			int version = cer.getVersion();
			String sinname = cer.getSigAlgName();
			String type = cer.getType();
			String algorname = cer.getPublicKey().getAlgorithm();
			BigInteger serialnum = cer.getSerialNumber();
			Principal principal = cer.getIssuerDN();
			String principalname = principal.getName();
			
			SDKDebugLog.logI(TAG,"[print] : "+"version="+version+", sinname="+sinname+", type="+type+", algorname="+algorname+", serialnum="+serialnum+", principalname="+principalname);
		}

	}
	
	
	public interface IProcessMsg{
		void socketState(boolean isLink);
		void processMsg(byte [] data);
	}
	
	
	
}
