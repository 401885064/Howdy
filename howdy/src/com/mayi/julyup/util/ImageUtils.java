package com.mayi.julyup.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;

/**
 * ImageUtils
 * 
 * @author Trinea 2012-6-27
 */
public class ImageUtils {
    /** 水平方向模糊度 */
    private static float hRadius = 5;
    /** 竖直方向模糊度 */
    private static float vRadius = 5;
    /** 模糊迭代度 */
    private static int iterations = 5;
    
    
    
    /**
     * convert Bitmap to byte array
     * 
     * @param b
     * @return
     */
    public static byte[] bitmapToByte(Bitmap b) {
	if (b == null) {
	    return null;
	}

	ByteArrayOutputStream o = new ByteArrayOutputStream();
	b.compress(Bitmap.CompressFormat.PNG, 100, o);
	return o.toByteArray();
    }

    /**
     * convert byte array to Bitmap
     * 
     * @param b
     * @return
     */
    public static Bitmap byteToBitmap(byte[] b) {
	return (b == null || b.length == 0) ? null : BitmapFactory
		.decodeByteArray(b, 0, b.length);
    }

    /**
     * convert Drawable to Bitmap
     * 
     * @param d
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable d) {
	return d == null ? null : ((BitmapDrawable) d).getBitmap();
    }

    /**
     * convert Bitmap to Drawable
     * 
     * @param b
     * @return
     */
    public static Drawable bitmapToDrawable(Bitmap b) {
	return b == null ? null : new BitmapDrawable(b);
    }

    /**
     * convert Drawable to byte array
     * 
     * @param d
     * @return
     */
    public static byte[] drawableToByte(Drawable d) {
	return bitmapToByte(drawableToBitmap(d));
    }

    /**
     * convert byte array to Drawable
     * 
     * @param b
     * @return
     */
    public static Drawable byteToDrawable(byte[] b) {
	return bitmapToDrawable(byteToBitmap(b));
    }

    /**
     * scale image
     * 
     * @param org
     * @param newWidth
     * @param newHeight
     * @return
     */
    public static Bitmap scaleImageTo(Bitmap org, int newWidth, int newHeight) {
	return scaleImage(org, (float) newWidth / org.getWidth(),
		(float) newHeight / org.getHeight());
    }

    /**
     * scale image
     * 
     * @param org
     * @param scaleWidth
     *            sacle of width
     * @param scaleHeight
     *            scale of height
     * @return
     */
    public static Bitmap scaleImage(Bitmap org, float scaleWidth,
	    float scaleHeight) {
	if (org == null) {
	    return null;
	}

	Matrix matrix = new Matrix();
	matrix.postScale(scaleWidth, scaleHeight);
	return Bitmap.createBitmap(org, 0, 0, org.getWidth(), org.getHeight(),
		matrix, true);
    }

    /**
     * 获取图片缩小的图片
     * 
     * @param src
     * @param max
     * @return
     */
    public static Bitmap scaleBitmap(String src, int max) {
	// 获取图片的高和宽
	BitmapFactory.Options options = new BitmapFactory.Options();
	// 这一个设置使 BitmapFactory.decodeFile获得的图片是空的,但是会将图片信息写到options中
	options.inJustDecodeBounds = true;
	BitmapFactory.decodeFile(src, options);
	// 计算比例 为了提高精度,本来是要640 这里缩为64
	max = max / 10;
	int be = options.outWidth / max;
	if (be % 10 != 0)
	    be += 10;
	be = be / 10;
	if (be <= 0)
	    be = 1;
	options.inSampleSize = be;
	// 设置可以获取数据
	options.inJustDecodeBounds = false;
	// 获取图片
	return BitmapFactory.decodeFile(src, options);
    }

    /**
     * 从给定的路径加载图片，并指定是否自动旋转方向
     * 
     * @param imgpath
     * @param adjustOritation
     * @return
     */
    public static Bitmap RotateBitmapByExif(String imgpath,
	    boolean adjustOritation) {
	if (!adjustOritation) {
	    return LoadBitmapFromPath(imgpath);
	} else {
	    Bitmap bm = LoadBitmapFromPath(imgpath);
	    int digree = 0;
	    ExifInterface exif = null;
	    try {
		exif = new ExifInterface(imgpath);
	    } catch (IOException e) {
		e.printStackTrace();
		exif = null;
	    }
	    if (exif != null) {
		// 读取图片中相机方向信息
		int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
			ExifInterface.ORIENTATION_UNDEFINED);
		// 计算旋转角度
		switch (ori) {
		case ExifInterface.ORIENTATION_ROTATE_90:
		    digree = 90;
		    break;
		case ExifInterface.ORIENTATION_ROTATE_180:
		    digree = 180;
		    break;
		case ExifInterface.ORIENTATION_ROTATE_270:
		    digree = 270;
		    break;
		default:
		    digree = 0;
		    break;
		}
	    }
	    if (digree != 0) {
		// 旋转图片
		Matrix m = new Matrix();
		m.postRotate(digree);
		bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
			bm.getHeight(), m, true);
	    }
	    return bm;
	}
    }

    /**
     * loadBitmap
     * 
     * @param bm
     * @param exif
     * @return
     */
    public static Bitmap RotateBitmapByExif(Bitmap bm, ExifInterface exif) {
	int digree = 0;
	if (exif != null) {
	    // 读取图片中相机方向信息
	    int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
		    ExifInterface.ORIENTATION_UNDEFINED);
	    // 计算旋转角度
	    switch (ori) {
	    case ExifInterface.ORIENTATION_ROTATE_90:
		digree = 90;
		break;
	    case ExifInterface.ORIENTATION_ROTATE_180:
		digree = 180;
		break;
	    case ExifInterface.ORIENTATION_ROTATE_270:
		digree = 270;
		break;
	    default:
		digree = 0;
		break;
	    }
	}
	if (digree != 0) {
	    // 旋转图片
	    Matrix m = new Matrix();
	    m.postRotate(digree);
	    bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(),
		    m, true);
	}
	return bm;
    }

    private static Bitmap LoadBitmapFromPath(String imgpath) {
	// TODO Auto-generated method stub
	Bitmap bitmap = BitmapFactory.decodeFile(imgpath);
	return bitmap;
    }

    // 加水印 也可以加文字
    public static Bitmap WatermarkBitmap(Bitmap src, Bitmap watermark,
	    String title) {
	if (src == null) {
	    return null;
	}
	int w = src.getWidth();
	int h = src.getHeight();

	System.out.println("w  :" + w + "   h:" + h);

	// 需要处理图片太大造成的内存超过的问题,这里我的图片很小所以不写相应代码了
	Bitmap newb = Bitmap.createBitmap(w, h, Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
	Canvas cv = new Canvas(newb);
	cv.drawBitmap(src, 0, 0, null);// 在 0，0坐标开始画入src
	Paint paint = new Paint();
	// 加入图片
	if (watermark != null) {
	    int ww = watermark.getWidth();
	    int wh = watermark.getHeight();
	    paint.setAlpha(50);
	    cv.drawBitmap(watermark, w - ww + 5, h - wh + 5, paint);// 在src的右下角画入水印
	}
	// 加入文字
	if (title != null) {
	    String familyName = "宋体";
	    Typeface font = Typeface.create(familyName, Typeface.BOLD);
	    TextPaint textPaint = new TextPaint();
	    textPaint.setColor(Color.RED);
	    textPaint.setTypeface(font);
	    textPaint.setTextSize(128);

	    cv.translate(w / 2, h / 2);

	    // 这里是自动换行的
	    StaticLayout layout = new StaticLayout(title, textPaint, w,
		    Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
	    layout.draw(cv);
	    // //文字就加左上角算了
	    cv.drawText(title, 0, 40, paint);
	}
	cv.save(Canvas.ALL_SAVE_FLAG);// 保存
	cv.restore();// 存储
	return newb;
    }

    /**
     * 添加文字到图片，类似水印文字。
     * 
     * @param gContext
     * @param gResId
     * @param gText
     * @return
     */
    public static Bitmap drawTextToBitmap(Context gContext, int gResId,
	    String gText) {
	Resources resources = gContext.getResources();
	float scale = resources.getDisplayMetrics().density;
	Bitmap bitmap = BitmapFactory.decodeResource(resources, gResId);

	Config bitmapConfig = bitmap.getConfig();
	// set default bitmap config if none
	if (bitmapConfig == null) {
	    bitmapConfig = Config.ARGB_8888;
	}
	// resource bitmaps are imutable,
	// so we need to convert it to mutable one
	bitmap = bitmap.copy(bitmapConfig, true);

	Canvas canvas = new Canvas(bitmap);
	// new antialised Paint
	Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	// text color - #3D3D3D
	paint.setColor(Color.rgb(61, 61, 61));
	// text size in pixels
	paint.setTextSize((int) (14 * scale * 5));
	// text shadow
	paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);

	// draw text to the Canvas center
	Rect bounds = new Rect();
	paint.getTextBounds(gText, 0, gText.length(), bounds);
	// int x = (bitmap.getWidth() - bounds.width()) / 2;
	// int y = (bitmap.getHeight() + bounds.height()) / 2;
	// draw text to the bottom
	int x = (bitmap.getWidth() - bounds.width()) / 10 * 9;
	int y = (bitmap.getHeight() + bounds.height()) / 10 * 9;
	canvas.drawText(gText, x, y, paint);

	return bitmap;
    }

    /***
     * 按质量压缩bitmap
     */
    public static Bitmap compressImage(Bitmap image) {

	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
	int options = 100;
	while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
	    baos.reset();// 重置baos即清空baos
	    image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
	    options -= 10;// 每次都减少10
	}
	ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
	Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
	System.out.println("当前图像大小为: " + (baos.toByteArray().length / 1024)
		+ "kb");
	return bitmap;
    }

    /***
     * 按照指定的长宽值压缩
     */
    public static Bitmap getimage(String srcPath) {
	BitmapFactory.Options newOpts = new BitmapFactory.Options();
	// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
	newOpts.inJustDecodeBounds = true;
	// 此时返回bm为空
	Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
	int w = newOpts.outWidth;
	int h = newOpts.outHeight;
	// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
	float hh = 800f;// 这里设置高度为800f
	float ww = 480f;// 这里设置宽度为480f
	// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
	int be = 1;// be=1表示不缩放
	if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
	    be = (int) (newOpts.outWidth / ww);
	} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
	    be = (int) (newOpts.outHeight / hh);
	}
	if (be <= 0)
	    be = 1;
	newOpts.inSampleSize = be;// 设置缩放比例
	newOpts.inJustDecodeBounds = false;
	// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
	bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
	return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
    }

    /**
     * 
     高斯模糊
     */
    public static Bitmap BoxBlurFilter(Bitmap bmp) {
	int width = bmp.getWidth();
	int height = bmp.getHeight();
	int[] inPixels = new int[width * height];
	int[] outPixels = new int[width * height];
	Bitmap bitmap = Bitmap.createBitmap(width, height,
		Bitmap.Config.ARGB_8888);
	bmp.getPixels(inPixels, 0, width, 0, 0, width, height);
	
	for (int i = 0; i < iterations; i++) {
	    blur(inPixels, outPixels, width, height, hRadius);
	    blur(outPixels, inPixels, height, width, vRadius);
	}
	blurFractional(inPixels, outPixels, width, height, hRadius);
	blurFractional(outPixels, inPixels, height, width, vRadius);
	bitmap.setPixels(inPixels, 0, width, 0, 0, width, height);
	return bitmap;
    }

    public static void blur(int[] in, int[] out, int width, int height,
	    float radius) {
	int widthMinus1 = width - 1;
	int r = (int) radius;
	int tableSize = 2 * r + 1;
	int divide[] = new int[256 * tableSize];

	for (int i = 0; i < 256 * tableSize; i++)
	    divide[i] = i / tableSize;

	int inIndex = 0;

	for (int y = 0; y < height; y++) {
	    int outIndex = y;
	    int ta = 0, tr = 0, tg = 0, tb = 0;

	    for (int i = -r; i <= r; i++) {
		int rgb = in[inIndex + clamp(i, 0, width - 1)];
		ta += (rgb >> 24) & 0xff;
		tr += (rgb >> 16) & 0xff;
		tg += (rgb >> 8) & 0xff;
		tb += rgb & 0xff;
	    }

	    for (int x = 0; x < width; x++) {
		out[outIndex] = (divide[ta] << 24) | (divide[tr] << 16)
			| (divide[tg] << 8) | divide[tb];

		int i1 = x + r + 1;
		if (i1 > widthMinus1)
		    i1 = widthMinus1;
		int i2 = x - r;
		if (i2 < 0)
		    i2 = 0;
		int rgb1 = in[inIndex + i1];
		int rgb2 = in[inIndex + i2];

		ta += ((rgb1 >> 24) & 0xff) - ((rgb2 >> 24) & 0xff);
		tr += ((rgb1 & 0xff0000) - (rgb2 & 0xff0000)) >> 16;
		tg += ((rgb1 & 0xff00) - (rgb2 & 0xff00)) >> 8;
		tb += (rgb1 & 0xff) - (rgb2 & 0xff);
		outIndex += height;
	    }
	    inIndex += width;
	}
    }

    public static void blurFractional(int[] in, int[] out, int width,
	    int height, float radius) {
	radius -= (int) radius;
	float f = 1.0f / (1 + 2 * radius);
	int inIndex = 0;

	for (int y = 0; y < height; y++) {
	    int outIndex = y;

	    out[outIndex] = in[0];
	    outIndex += height;
	    for (int x = 1; x < width - 1; x++) {
		int i = inIndex + x;
		int rgb1 = in[i - 1];
		int rgb2 = in[i];
		int rgb3 = in[i + 1];

		int a1 = (rgb1 >> 24) & 0xff;
		int r1 = (rgb1 >> 16) & 0xff;
		int g1 = (rgb1 >> 8) & 0xff;
		int b1 = rgb1 & 0xff;
		int a2 = (rgb2 >> 24) & 0xff;
		int r2 = (rgb2 >> 16) & 0xff;
		int g2 = (rgb2 >> 8) & 0xff;
		int b2 = rgb2 & 0xff;
		int a3 = (rgb3 >> 24) & 0xff;
		int r3 = (rgb3 >> 16) & 0xff;
		int g3 = (rgb3 >> 8) & 0xff;
		int b3 = rgb3 & 0xff;
		a1 = a2 + (int) ((a1 + a3) * radius);
		r1 = r2 + (int) ((r1 + r3) * radius);
		g1 = g2 + (int) ((g1 + g3) * radius);
		b1 = b2 + (int) ((b1 + b3) * radius);
		a1 *= f;
		r1 *= f;
		g1 *= f;
		b1 *= f;
		out[outIndex] = (a1 << 24) | (r1 << 16) | (g1 << 8) | b1;
		outIndex += height;
	    }
	    out[outIndex] = in[width - 1];
	    inIndex += width;
	}
    }

    public static int clamp(int x, int a, int b) {
	return (x < a) ? a : (x > b) ? b : x;
    }
}
