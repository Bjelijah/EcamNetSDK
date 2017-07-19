package com.howell.protocol.utils;

import android.content.Context;
import android.util.Log;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class SSLConection {
	private static final String TAG = SSLConection.class.getName();
	private static TrustManager [] trustManagers;
	private static HttpClient httpClient = null;
	private static SSLContext mSSLContext;
	public static class _FakeX509TrustManager implements X509TrustManager{
		private static final X509Certificate[] _AcceptedIssuers = new X509Certificate[]{};
		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			
			if(null != chain){
				for(int k=0; k < chain.length; k++){
					X509Certificate cer = chain[k];
					print(cer);
				}
			}
		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			// TODO Auto-generated method stub
			if(null != chain){
				for(int k=0; k < chain.length; k++){
					X509Certificate cer = chain[k];
					print(cer);
				}
			}
		}

		 public boolean isClientTrusted(X509Certificate[] chain) { 
	            return true; 
	    } 

	    public boolean isServerTrusted(X509Certificate[] chain) { 
	            return true; 
	    } 
		
		@Override
		public X509Certificate[] getAcceptedIssuers() {
			// TODO Auto-generated method stub
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
			SDKDebugLog.logI(TAG,"[print]: "+"version="+version+", sinname="+sinname+", type="+type+", algorname="+algorname+", serialnum="+serialnum+", principalname="+principalname);
		}

	}
	public static void allowAllSSL(Context mContext){
		if(mContext==null){
			SDKDebugLog.logE(TAG,"[allowAllSSL]: allow all ssl context = null");
		}

		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
			
			@Override
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		});
		

		if(trustManagers == null){
			trustManagers = new TrustManager[]{new _FakeX509TrustManager()};
		}
		
		
		InputStream ksIn = null;
		
		try {
//			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			KeyStore keyStore = KeyStore.getInstance("BKS");
//			 ksIn = mContext.getResources().getAssets().open("client.p12");
			ksIn = mContext.getResources().getAssets().open("client.bks");

		
			keyStore.load(ksIn, "123456".toCharArray());
			
			
			String kmfAlgorithm = KeyManagerFactory.getDefaultAlgorithm();
			KeyManagerFactory kmf = KeyManagerFactory.getInstance(kmfAlgorithm);
			kmf.init(keyStore, "123456".toCharArray());

			mSSLContext = SSLContext.getInstance("TLS");
//			context = SSLContext.getInstance("SSL");
//			Log.i("123", "using ssl");
			mSSLContext.init(kmf.getKeyManagers(), trustManagers, new SecureRandom());
//			context.init(null, trustManagers, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(mSSLContext.getSocketFactory());

			//if (null == httpClient){
				SSLSocketFactory sf = new SSLSocketFactory(keyStore){
					@Override
					public Socket createSocket() throws IOException {
						return mSSLContext.getSocketFactory().createSocket();
					}
					@Override
					public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
						return mSSLContext.getSocketFactory().createSocket(socket,host,port,autoClose);
					}
				};
				sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
				HttpParams params = new BasicHttpParams();
				HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
				HttpProtocolParams.setContentCharset(params, HTTP.DEFAULT_CONTENT_CHARSET);
				HttpProtocolParams.setUseExpectContinue(params,true);

				ConnManagerParams.setTimeout(params,8000);
				HttpConnectionParams.setConnectionTimeout(params,8000);
				HttpConnectionParams.setSoTimeout(params,8000);
				SchemeRegistry schReg = new SchemeRegistry();
				schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(),80));
				schReg.register(new Scheme("https",sf,443));
				ClientConnectionManager conMgr = new ThreadSafeClientConnManager(params,schReg);
				httpClient = new DefaultHttpClient(conMgr,params);
			//}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}
		//Log.i("123", "allow all ssl");	
		catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				ksIn.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public static HttpClient getHttpsClient(Context context) {
		allowAllSSL(context);
		return httpClient;
	}
}
