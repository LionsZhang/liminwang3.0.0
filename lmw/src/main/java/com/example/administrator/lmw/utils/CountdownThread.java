package com.example.administrator.lmw.utils;

import android.os.Handler;
import android.os.Looper;

public class CountdownThread extends Thread {
	private static final int DEFAULT_COUNT = 60;
	private Handler mHandler = new Handler(Looper.getMainLooper());
	private int count = DEFAULT_COUNT;
	private OnCountdownListener mListener;
	public interface OnCountdownListener {
		void OnCountdown(int count);
		void OnCountdownFinish();
	}


	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public void run() {
		while(count > 0) {
			count--;
			mHandler.post(new Runnable() {
				
				@Override
				public void run() {
					if(mListener != null) {
						mListener.OnCountdown(count);
					}
				}
			});
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		mHandler.post(new Runnable() {
			
			@Override
			public void run() {
				if(mListener != null) {
					count = DEFAULT_COUNT;
					mListener.OnCountdownFinish();
				}
				
			}
		});
		
	}


	public void setOnCountdownListener(OnCountdownListener mListener) {
		this.mListener = mListener;
	}
	
	

}
