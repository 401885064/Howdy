package com.mayi.julyup.pictures;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

/**
 * 图片缓存 BitmapCache.java
 * 
 * @author mayi 2014-8-7 下午9:09:57
 * 
 * put(String path, Bitmap bmp)
 * displayBmp(final ImageView iv, final String thumbPath,final String sourcePath, final ImageCallback callback)
 * revitionImageSize(String path)
 * interface ImageCallback
 */
public class BitmapCache extends Activity {

	public Handler myhandler = new Handler();
	public final String TAG = getClass().getSimpleName();
	//imageCache
	private HashMap<String, SoftReference<Bitmap>> imageCache = new HashMap<String, SoftReference<Bitmap>>();
	
	//存进imageCache中
	public void put(String path, Bitmap bmp) {
		if (!TextUtils.isEmpty(path) && bmp != null) {
			imageCache.put(path, new SoftReference<Bitmap>(bmp));
		}
	}

	/**
	 * displayBmp 两种途径展示图片
	 * 线查找缓存中是否存在该图片，如果不存在就从path加载，如果都不存在则设置默认图片
	 * @param iv  imageview控件
	 * @param thumbPath 缩略图路径
	 * @param sourcePath 原图路径
	 * @param callback  缓存监听
	 */
	public void displayBmp(final ImageView iv, final String thumbPath,
			final String sourcePath, final ImageCallback callback) {
		if (TextUtils.isEmpty(thumbPath) && TextUtils.isEmpty(sourcePath)) {
			Log.e(TAG, "no paths pass in");
			return;
		}

		final String path;
		final boolean isThumbPath;
		if (!TextUtils.isEmpty(thumbPath)) {
			path = thumbPath;
			isThumbPath = true;
		} else if (!TextUtils.isEmpty(sourcePath)) {
			path = sourcePath;
			isThumbPath = false;
		} else {
			// iv.setImageBitmap(null);
			return;
		}
		// 缓存中是否有该图片
		if (imageCache.containsKey(path)) {
			SoftReference<Bitmap> reference = imageCache.get(path);
			Bitmap bmp = reference.get();
			if (bmp != null) {
				if (callback != null) {
					callback.imageLoad(iv, bmp, sourcePath);
				}
				iv.setImageBitmap(bmp);
				Log.d(TAG, "hit cache");
				return;
			}
		}

		// 缓存中没有该图片，iv设为NULL
		iv.setImageBitmap(null);

		// 开启一个线程去加载该图片
		new Thread() {
			Bitmap thumb;

			public void run() {
				try {
					if (isThumbPath) {
						thumb = BitmapFactory.decodeFile(thumbPath);
						if (thumb == null) {
							//压缩图片
							thumb = revitionImageSize(sourcePath);
						}
					} else {
						//压缩图片
						thumb = revitionImageSize(sourcePath);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				// 如果为null，设置默认图片
				if (thumb == null) {
					thumb = PictureActivity.bimap;
				}
				Log.e(TAG, "-------thumb------" + thumb);
				// 存进缓存
				put(path, thumb);
				// 主线程回调缓存图片接口
				if (callback != null) {
					myhandler.post(new Runnable() {
						@Override
						public void run() {
							callback.imageLoad(iv, thumb, sourcePath);
						}
					});
				}
			}
		}.start();

	}

	/**
	 * 压缩图片
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public Bitmap revitionImageSize(String path) throws IOException {
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(
				new File(path)));
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(in, null, options);
		in.close();
		int i = 0;
		Bitmap bitmap = null;
		while (true) {
			if ((options.outWidth >> i <= 300)&& (options.outHeight >> i <= 500)) {
				in = new BufferedInputStream(
						new FileInputStream(new File(path)));
				options.inSampleSize = (int) Math.pow(2.0D, i);
				options.inJustDecodeBounds = false;
				bitmap = BitmapFactory.decodeStream(in, null, options);
				break;
			}
			i += 1;
		}
		return bitmap;
	}

	// 图片缓存回调接口
	public interface ImageCallback {
		public void imageLoad(ImageView imageView, Bitmap bitmap,
				Object... params);
	}
}
