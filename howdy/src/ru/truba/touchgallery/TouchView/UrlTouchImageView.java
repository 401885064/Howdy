/*
 Copyright (c) 2012 Roman Truba

 Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 permit persons to whom the Software is furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all copies or substantial
 portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package ru.truba.touchgallery.TouchView;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import ru.truba.touchgallery.TouchView.InputStreamWrapper.InputStreamProgressListener;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView.ScaleType;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.mayi.julyup.R;
import com.mayi.julyup.util.AlertDialogUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sondon.util.Options;

public class UrlTouchImageView extends RelativeLayout {
//	public ProgressBar mProgressBar;
    public TouchImageView mImageView;
    public Bitmap mybitmap;
    protected Context mContext;
    // ImageLoader
    ImageLoader imageload = ImageLoader.getInstance();
    DisplayImageOptions options = Options.getListOptions();
    Dialog dialog=null;
    
    public UrlTouchImageView(Context ctx)
    {
        super(ctx);
        mContext = ctx;
        init();

    }
    public UrlTouchImageView(Context ctx, AttributeSet attrs)
    {
        super(ctx, attrs);
        mContext = ctx;
        init();
    }
    public TouchImageView getImageView() { return mImageView; }

    @SuppressWarnings("deprecation")
    protected void init() {
    	dialog=AlertDialogUtil.createLoadingDialog(mContext, "正在加载...");
        mImageView = new TouchImageView(mContext);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mImageView.setLayoutParams(params);
        this.addView(mImageView);
//        mImageView.setVisibility(GONE);

//        mProgressBar = new ProgressBar(mContext, null, android.R.attr.progressBarStyleHorizontal);
//        params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//        params.addRule(RelativeLayout.CENTER_VERTICAL);
//        params.setMargins(30, 0, 30, 0);
//        mProgressBar.setLayoutParams(params);
//        mProgressBar.setIndeterminate(false);
//        mProgressBar.setMax(100);
//        this.addView(mProgressBar);
    }

    public void setUrl(String imageUrl)
    {
//    	 mImageView.setVisibility(VISIBLE);
    	imageload.displayImage(imageUrl, mImageView,options
    			
//    			,new ImageLoadingListener() {
//			
//			@Override
//			public void onLoadingStarted(String arg0, View arg1) {
//				// TODO Auto-generated method stub
//				if(!dialog.isShowing()){
//					dialog.show();
//				}
//			}
//			
//			@Override
//			public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
//				// TODO Auto-generated method stub
//				if(dialog.isShowing()){
//					dialog.dismiss();
//				}
//			}
//			
//			@Override
//			public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
//				// TODO Auto-generated method stub
//				if(dialog.isShowing()){
//					dialog.dismiss();
//				}
//			}
//			
//			@Override
//			public void onLoadingCancelled(String arg0, View arg1) {
//				// TODO Auto-generated method stub
//				if(dialog.isShowing()){
//					dialog.dismiss();
//				}
//			}
//		
//		}
//    	
    			);
//        new ImageLoadTask().execute(imageUrl);
    }
    
    public void setScaleType(ScaleType scaleType) {
        mImageView.setScaleType(scaleType);
    }
    
    public Bitmap getImageBitmap(){
    	return mybitmap;
    }
    
    
    //No caching load
    public class ImageLoadTask extends AsyncTask<String, Integer, Bitmap>
    {
        @Override
        protected Bitmap doInBackground(String... strings) {
            String url = strings[0];
            Bitmap bm = null;
            try {
                URL aURL = new URL(url);
                URLConnection conn = aURL.openConnection();
                conn.connect();
                InputStream is = conn.getInputStream();
                int totalLen = conn.getContentLength();
                InputStreamWrapper bis = new InputStreamWrapper(is, 8192, totalLen);
                bis.setProgressListener(new InputStreamProgressListener()
				{					
					@Override
					public void onProgress(float progressValue, long bytesLoaded,
							long bytesTotal)
					{
						publishProgress((int)(progressValue * 100));
					}
				});
                bm = BitmapFactory.decodeStream(bis);
                bis.close();
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bm;
        }
        
        @Override
        protected void onPostExecute(Bitmap bit) {
        	if (bit == null) 
        	{
        		mImageView.setScaleType(ScaleType.CENTER);
        		bit = BitmapFactory.decodeResource(getResources(), R.drawable.no_photo);
        		mImageView.setImageBitmap(bit);
        	}
        	else 
        	{
        		mImageView.setScaleType(ScaleType.MATRIX);
	            mImageView.setImageBitmap(bit);
        	}
        	mybitmap=bit;
            mImageView.setVisibility(VISIBLE);
//            mProgressBar.setVisibility(GONE);
        }

		@Override
		protected void onProgressUpdate(Integer... values)
		{
//			mProgressBar.setProgress(values[0]);
		}
    }
}
