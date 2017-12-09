package com.ml.thousandsdraw.Adapter;
import android.support.v4.view.*;
import android.view.*;
import java.util.*;
import android.widget.*;

public class ViewPagerAdapter extends PagerAdapter
{

	private ArrayList<ImageView> mViewList;
	
	public ViewPagerAdapter(ArrayList<ImageView> list){
		mViewList = list;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object)
	{
		// TODO: Implement this method
		//super.destroyItem(container, position, object);
		container.removeView(mViewList.get(position));
	}
	
	//加载

	@Override
	public Object instantiateItem(ViewGroup container, int position){
        //这个方法用来实例化页卡       
        container.addView(mViewList.get(position), 0);//添加页卡
        return mViewList.get(position);
	}
	
	@Override
	public int getCount()
	{
		// TODO: Implement this method
		return mViewList.size();
	}

	@Override
	public boolean isViewFromObject(View p1, Object p2)
	{
		// TODO: Implement this method
		return p1 ==  p2;
	}
	
}
