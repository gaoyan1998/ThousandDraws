package com.ml.thousandsdraw.util;
import com.ml.thousandsdraw.Views.*;
import android.app.*;
import android.content.*;
import android.view.*;


public class preinsteall
{
	
	private static final int PRE_LINE = 0;

	private static final int PRE_HEAET = 1;
	
	
	MySurfaceView msv ;

	private Context context;
	public preinsteall(MySurfaceView msv,Context context){
		this.msv = msv;
		this.context = context;
	}
	
	public void pre_line(){
		msv.addPath(0,0,MotionEvent.ACTION_DOWN);
		int cx = MySurfaceView.FRAME_WIDTH / 2,cy = MySurfaceView.FRAME_HEIGHT / 2;
		msv.addPath(cx,cy,MotionEvent.ACTION_MOVE);
		msv.drawToScreen();
		msv.addPath(0,0,MotionEvent.ACTION_UP);
	}
	//画心
	public void drawLove(float rate,int offset){
		// 路径的起始点
		int cx = MySurfaceView.FRAME_WIDTH / 2,cy = MySurfaceView.FRAME_HEIGHT / 2;
		cx -= offset; cy -= offset;
		msv.addPath(cx,cy,MotionEvent.ACTION_DOWN);
		// 根据心形函数画图
		for(int z = 0; z < 30; z ++){
			msv.addPath(cx,cy,MotionEvent.ACTION_DOWN);
		for (double i = 0; i <= 2 * Math.PI; i += 0.01)
		{
			float x = (float) (16 * Math.sin(i) * Math.sin(i) * Math.sin(i));
			float y = (float) (13 * Math.cos(i) - 5 * Math.cos(2 * i) - 2 * Math.cos(3 * i) - Math.cos(4 * i));
			x *= rate;
			y *= rate;
			x = cx - x;
			y = cy - y;
			msv.addPath(x,y,MotionEvent.ACTION_MOVE);
			}
			cx -= rate*offset; cy -= rate*offset;
			//提交画布
			msv.drawToScreen();
			//完成绘图 模仿抬起动作完成绘图
			msv.addPath(0,0,MotionEvent.ACTION_UP);
		}
			
	}
	public void show(){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		
		builder.setTitle("选择一个预设版本");
		//    指定下拉列表的显示数据
		final String[] color_name = {"直线","爱心"};
		//final int[] color_value = {Color.BLACK,Color.BLUE,Color.RED,Color.GREEN,Color.YELLOW,Color.GRAY,Color.WHITE};
		//    设置一个下拉的列表选择项
		builder.setItems(color_name, new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					switch(which){
						case PRE_LINE:
							pre_line();
							break;
						case PRE_HEAET:
							drawLove(1,30);
							break;
					}
					
				}
			});
		builder.show();
	}
}
