package com.howell.camera;



import android.content.Context;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.util.Log;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;

public class CameraTextureView extends TextureView implements SurfaceTextureListener,CameraInterface.CamOpenOverCallback {

	private static final String TAG = "CameraTextureView";  
	Context mContext;  
	SurfaceTexture mSurface;  

	public CameraTextureView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mContext = context;
		this.setSurfaceTextureListener(this);
	}

	@Override
	public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onSurfaceTextureAvailable...");  
		mSurface = surface;
		
		new Thread(){
			public void run() {
				CameraInterface.getInstance().doOpenCamera(CameraTextureView.this);
			};
		}.start();
		
	}

	@Override
	public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onSurfaceTextureSizeChanged...");  
	}

	@Override
	public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onSurfaceTextureDestroyed...");  
		CameraInterface.getInstance().doStopCamera();
		return true;
	}

	@Override
	public void onSurfaceTextureUpdated(SurfaceTexture surface) {
		// TODO Auto-generated method stub
		//Log.i(TAG, "onSurfaceTextureUpdated...");
	}

	
	
	
	public SurfaceTexture getSurfaceTexture(){
		return mSurface;
	}

	@Override
	public void cameraHasOpened() {
		// TODO Auto-generated method stub
		float previewRate = 1.33f;
		CameraInterface.getInstance().doStartPreview(mSurface, previewRate);
	}
}
