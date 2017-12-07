package com.ml.thousandsdraw;
import android.widget.*;
import java.util.*;
import android.app.*;
import android.os.*;
import com.ml.thousandsdraw.util.*;
import android.view.*;
import android.view.View.*;
import android.content.*;


public class ViewPager extends Activity
{

	ArrayList<ImageView> ViewList;
	ImageView im1,im2,im3,im4;
	android.support.v4.view.ViewPager vp;
	Button toGame;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_flow);
		
		vp =( android.support.v4.view. ViewPager) findViewById(R.id.viewPager);
		toGame = (Button) findViewById(R.id.viewflow_toGame);
		im1 = new ImageView(this);
		im1.setBackgroundResource(R.mipmap.bg2);
		im2 = new ImageView(this);
		im2.setBackgroundResource(R.mipmap.bg3);
		im3 = new ImageView(this);
		im3.setBackgroundResource(R.mipmap.bg4);
		im4 = new ImageView(this);
		im4.setBackgroundResource(R.mipmap.bg1);
		ViewList = new ArrayList<ImageView>();
		ViewList.add(im1);
		ViewList.add(im2);
	    ViewList.add(im3);
		ViewList.add(im4);
		
		vp.setAdapter(new ViewPagerAdapter(ViewList));
		vp.setCurrentItem(0);
		vp.setOnPageChangeListener(new android.support.v4.view.ViewPager.OnPageChangeListener(){

				@Override
				public void onPageScrolled(int p1, float p2, int p3)
				{
					if(p1 >= 3){
						toGame.setVisibility(View.VISIBLE);
					}
				}

				@Override
				public void onPageSelected(int p1)
				{
					// TODO: Implement this method
				}

				@Override
				public void onPageScrollStateChanged(int p1)
				{
					// TODO: Implement this method
				}
			});
			
		toGame.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					Intent i = new Intent(ViewPager.this,logoList_.class);
					startActivity(i);
					finish();
				}
			});
		
	}
	
	
}
