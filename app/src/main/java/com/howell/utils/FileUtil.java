package com.howell.utils;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {
	private static final  String TAG = "FileUtil";  
	private static final File parentPath = Environment.getExternalStorageDirectory();  
	private static   String storagePath = "";  
	private static final String DST_FOLDER_NAME = "EcamNetSDK";
	private static final String PIC_NAME = "photo_1";

	/**初始化保存路径 
	 * @return 
	 */
	private static String initPath(){  
		if(storagePath.equals("")){  
			storagePath = parentPath.getAbsolutePath()+"/" + DST_FOLDER_NAME;  
			File f = new File(storagePath);  
			if(!f.exists()){  
				f.mkdir();  
			}  
		}  
		return storagePath;  
	}  

	/**保存Bitmap到sdcard 
	 * @param b 
	 */  
	public static void saveBitmap(Bitmap b){  

		String path = initPath();  
		long dataTake = System.currentTimeMillis();  
		String jpegName = path + "/" + PIC_NAME +".jpg";
		Log.i(TAG, "saveBitmap:jpegName = " + jpegName);  
		try {  
			FileOutputStream fout = new FileOutputStream(jpegName);  
			BufferedOutputStream bos = new BufferedOutputStream(fout);  
			b.compress(Bitmap.CompressFormat.JPEG, 100, bos);  
			bos.flush();  
			bos.close();
			fout.close();
			Log.i(TAG, "saveBitmap成功");  
		} catch (IOException e) {  
			// TODO Auto-generated catch block  
			Log.i(TAG, "saveBitmap:失败");  
			e.printStackTrace();  
		}
	}  

	public static byte [] loadPic(){

		String jpegName = initPath()+"/"+PIC_NAME+".jpg";
		byte [] picBuf = new byte[1024*1024];
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		int len = 0;
		try {
			fis = new FileInputStream(jpegName);
			bis = new BufferedInputStream(fis);
			len = bis.read(picBuf);
			if (len==0)return null;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}finally {
			try {
				bis.close();
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		byte [] b = new byte[len];
		System.arraycopy(picBuf,0,b,0,len);
		picBuf = null;
		Log.i("123","load pic finish");
		return b;
	}





}
