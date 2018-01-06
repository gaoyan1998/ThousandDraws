package com.ml.thousandsdraw.Views;

import android.content.*;
import android.graphics.*;
import android.support.design.widget.Snackbar;
import android.util.*;
import android.view.*;
import android.widget.*;
import java.io.*;
import java.util.ArrayList;

import com.ml.thousandsdraw.R;
import com.ml.thousandsdraw.model.PathModel;
import com.ml.thousandsdraw.saveActivity;
import com.ml.thousandsdraw.sql.ListSqlHelp;
import com.ml.thousandsdraw.util.*;
import android.os.*;

public class MySurfaceView extends SurfaceView implements
		SurfaceHolder.Callback {
	public static final int paintType1 = 0,
			                PaintType2 = 1,
			                PaintType3 = 2;
	public static int FRAME_WIDTH = 10;
	public static int FRAME_HEIGHT = 10;
	// SurfaceHolder实例
	private SurfaceHolder mSurfaceHolder;
	// Canvas对象
	private Canvas mCanvas;
	// Path实例
	private int count = 20;
	private Path paths[] = new Path[21];
	// Paint实例
	private Paint mpaint = new Paint();
   //背景图片
	public Bitmap backBitmap = null;
	//缓冲图片
	private Bitmap cacheBitmap = null;
	//缓冲画布
	private Canvas cacheCanvas;
	//画笔颜色大小画布颜色
	private int color = Color.BLACK;
	private int size = 13;
	private int backColor = Color.WHITE;
	//上下文
	private Context c;

	public MySurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(); // 初始化
		this.c = context;
	}

	/**
	 * 初始化VIEW
	 * */

	public void initView() {
		mSurfaceHolder = getHolder();
		mSurfaceHolder.addCallback(this);
		// 设置可获得焦点
		setFocusable(true);
		setFocusableInTouchMode(true);
		// 设置常亮
		this.setKeepScreenOn(true);
		mpaint.setStyle(Paint.Style.STROKE);
		//反锯齿
		mpaint.setAntiAlias(true);
		mpaint.setDither(true);
		mpaint.setStrokeWidth(DensityUtil.px2dip(getContext(), size));
		for(int i=0;i<paths.length;i++){
			paths[i] = new Path();
		}
	}

	/**
	 * 创建
	 */

	@Override
	public void surfaceCreated(SurfaceHolder holder) {

		if(cacheBitmap == null){
		    cacheBitmap = Bitmap.createBitmap(FRAME_WIDTH,FRAME_HEIGHT,Bitmap.Config.ARGB_8888);
		    cacheCanvas = new Canvas(cacheBitmap);
		}
		if (backBitmap != null){
			backBitmap = ImageDeal.zoomImg(backBitmap,FRAME_WIDTH,FRAME_HEIGHT);
		}
		drawToScreen();
	}

	/**
	 * 改变
	 */

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	/**
	 * 销毁
	 */

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.i("m","des");
	}

	/**
	 *
	 * 将画面提交到屏幕
	 *
	 * */

	public void drawToScreen() {
		try {
			mCanvas = mSurfaceHolder.lockCanvas();
			drawBack(mCanvas);
	 	} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 对画布内容进行提交
			if (mCanvas != null) {
				mSurfaceHolder.unlockCanvasAndPost(mCanvas);
			}
		}
	}

	/**
	 * 绘制背景
	 * */

	public void drawBack(Canvas canvas){
		canvas.drawColor(backColor);
		if(backBitmap != null){
			canvas.drawBitmap(backBitmap,0,0,mpaint);
		}
		//画缓冲图片
		canvas.drawBitmap(cacheBitmap,0,0,mpaint);
		//画线
		drawLine(canvas);
	}
	
	/**
	 * 绘制线段
	 * */

	public void drawLine(Canvas canvas){
		mpaint.setColor(color);
		for(int i=0;i<count;i++){
			canvas.drawPath(paths[i], mpaint);
		}
	}

	/**
	 * 绘制缓冲图片
	 * */

	private void drawCache(){
		drawLine(cacheCanvas);
		resetPaint();
	}

	/**
	 * 监听触摸事件
	 * 刷新path
	 * */

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();    //获取手指移动的x坐标
		int y = (int) event.getY();    //获取手指移动的y坐标
		drawToScreen();
		return RefereshPath(x,y,event.getAction());
	}
	public boolean RefereshPath(float x, float y, int action){
		int cx = FRAME_WIDTH / 2,cy = FRAME_HEIGHT / 2;
		float px = x - cx;
		float py = y - cy;
		float rotation = (float) (360 / (count) * Math.PI / 180);
		float r = (float)Math.sqrt(px*px + py*py);
		float rot_center = (float) Math.atan2(py, px);
		switch (action) {
			case MotionEvent.ACTION_DOWN:
				drawCache();
				for(int i=0;i<paths.length;i++){
					int tpx = (int) (r * Math.cos(rotation*i + rot_center));
					int tpy = (int) (r * Math.sin(rotation*i + rot_center));
					paths[i].moveTo(tpx+cx, tpy+cy);
				}
				return true;
			case MotionEvent.ACTION_MOVE:
				//mPath.lineTo(x, y);
				for(int i=0;i<count;i++){
					int tpx = (int) (r * Math.cos(rotation*i + rot_center));
					int tpy = (int) (r * Math.sin(rotation*i + rot_center));
					paths[i].lineTo(tpx+cx, tpy+cy);
				}
				break;
			case MotionEvent.ACTION_UP:

				break;
		}
		return false;
	}

	public void setPaintColor(int c){
		drawCache();
		drawToScreen();
		this.color = c;
	}
	public void setPaintStyle(int style){
		drawCache();
		switch (style){
			case paintType1:
				mpaint.setStyle(Paint.Style.STROKE);break;
			case PaintType2:
				mpaint.setStyle(Paint.Style.FILL);break;
			case PaintType3:
				mpaint.setStyle(Paint.Style.FILL_AND_STROKE);break;
		}
	}
	public void setSize(int c){
		drawCache();
		this.size = c;
		mpaint.setStrokeWidth(DensityUtil.px2dip(getContext(), size));
		drawToScreen();
	}
	
	public void setCount(int count){
		drawCache();
		if(count == 0){count = 1;}
		this.count = count;
		drawToScreen();
	}
	
	public void setBackColor(int color){
		drawCache();
		backColor = color;
		drawToScreen();
	}
	
	public void setBackImg(Bitmap bm){
		drawCache();
		this.backBitmap = bm;
		drawToScreen();
	}
	public void drawRevoke(){
		resetPaint();
		drawToScreen();
	}
	public void reset() {
		cacheBitmap = Bitmap.createBitmap(FRAME_WIDTH,FRAME_HEIGHT,Bitmap.Config.ARGB_8888);
		cacheCanvas = new Canvas(cacheBitmap);
		resetPaint();
		drawToScreen();
	}
	public void restBackPic(){
		backBitmap = null;
		drawToScreen();
	}
	public void restBackColor(){
		backColor = Color.WHITE;
		drawToScreen();
	}
	private void resetPaint(){
		for(int i=0;i<count;i++){
			paths[i].reset();
		}
	}

	public void loadData(String bg,String draw,int paint,int bg_color){
		this.backBitmap =  BitmapFactory.decodeFile(bg);
		this.cacheBitmap = BitmapFactory.decodeFile(draw);
		this.color = paint;
		this.backColor = bg_color;
		this.cacheBitmap = cacheBitmap.copy(Bitmap.Config.ARGB_8888,true);
		cacheCanvas = new Canvas(cacheBitmap);
	}
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		super.onSizeChanged(w, h, oldw, oldh);
		FRAME_WIDTH = w;
		FRAME_HEIGHT = h;
	}


	public void save(Boolean saveTosd,saveTask.OnSaved Isave){
		Bitmap MergeBitmap = Bitmap.createBitmap(FRAME_WIDTH,FRAME_HEIGHT, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(MergeBitmap);
		drawToScreen();
		drawCache();
		drawBack(canvas);

		ArrayList<PathModel> pathList = new ArrayList<PathModel>();
		String BgPath = c.getFilesDir() + "/bg_/tdw/";
		String DrawPath = c.getFilesDir() + "/draw_/tdw/";
		String MergePath = c.getFilesDir() + "/cache_/tdw/";

		pathList.add(new PathModel(BgPath,
				System.currentTimeMillis()+".png",backBitmap));
		pathList.add(new PathModel(DrawPath,
				System.currentTimeMillis()+".png",cacheBitmap));
		pathList.add(new PathModel(MergePath,
				System.currentTimeMillis()+".png",MergeBitmap));
		if(saveTosd){
			pathList.add(new PathModel( Environment.getExternalStorageDirectory() + "/tdw/",
					System.currentTimeMillis()+".png",MergeBitmap));
		}

		ListSqlHelp listSqlHelp = new ListSqlHelp(c);
		listSqlHelp.insertData(BgPath+pathList.get(0).getName(),
				DrawPath+pathList.get(1).getName(),
				MergePath+pathList.get(2).getName(),
				backColor,color,FRAME_WIDTH,FRAME_HEIGHT);

		new saveTask(c,pathList,Isave).execute();
	}
}
