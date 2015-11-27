package com.warmtel.custom.slider;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class SliderMainActivity extends Activity {
	private ViewPager mViewPager;
	public final static int[] imageThumbRes = new int[] { R.drawable.pager_one,
			R.drawable.pager_two, R.drawable.pager_three };
	private static final int TITME = 2000;/** 滑页间隔时间 */
	private static final int MSG_NEXT_IMAGE = 1;/** 显示下一张图片 */
	private static final int MSG_PAGE_CHANGED = 2;/** 执行手动滑动页动作*/

	private Handler mPagerHandler = new Handler() {
		int currentPagerNo = 0; // 页号
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MSG_NEXT_IMAGE:
				currentPagerNo++;
				mViewPager.setCurrentItem(currentPagerNo);
				mPagerHandler.sendEmptyMessageDelayed(MSG_NEXT_IMAGE, TITME);
				break;
			case MSG_PAGE_CHANGED:
				currentPagerNo = msg.arg1;// 记录滑动后页号，避免自动滑动时页面显示不正确。
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.slider_viktor_layout);

		mViewPager = (ViewPager) findViewById(R.id.slider_viewpager);

		LayoutInflater inflater = LayoutInflater.from(this);
		View v1 = inflater.inflate(R.layout.slider_viktor_item_layout, null);
		ImageView v1Img = (ImageView) v1.findViewById(R.id.slider_item_img);
		v1Img.setImageResource(imageThumbRes[0]);
		v1Img.setScaleType(ScaleType.CENTER_CROP);

		View v2 = inflater.inflate(R.layout.slider_viktor_item_layout, null);
		ImageView v2Img = (ImageView) v2.findViewById(R.id.slider_item_img);
		v2Img.setImageResource(imageThumbRes[1]);
		v2Img.setScaleType(ScaleType.CENTER_CROP);

		View v3 = inflater.inflate(R.layout.slider_viktor_item_layout, null);
		ImageView v3Img = (ImageView) v3.findViewById(R.id.slider_item_img);
		v3Img.setImageResource(imageThumbRes[2]);
		v3Img.setScaleType(ScaleType.CENTER_CROP);

		ArrayList<View> data = new ArrayList<View>();
		data.add(v1);
		data.add(v2);
		data.add(v3);

		MyPagerAdapter viewPagerAdapter = new MyPagerAdapter();
		mViewPager.setAdapter(viewPagerAdapter);

		viewPagerAdapter.setData(data);

		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int postion) {
				mPagerHandler.sendMessage(Message.obtain(mPagerHandler,MSG_PAGE_CHANGED, postion, 0));
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

		/**
		 * 2147483647 / 2 = 1073741820 - 1
		 * 设置ViewPager的当前项为一个比较大的数，以便一开始就可以左右循环滑动
		 */
		int n = Integer.MAX_VALUE / 2 % data.size();
		int currentPager = Integer.MAX_VALUE / 2 - n;

		mViewPager.setCurrentItem(currentPager);

		/** 停止自动滑动 */
		mPagerHandler.sendEmptyMessageDelayed(MSG_NEXT_IMAGE, TITME);
	}

	/**
	 * 定义循环滑动适配器
	 * 
	 */
	public class MyPagerAdapter extends PagerAdapter {
		private ArrayList<View> data = new ArrayList<View>();

		public void setData(ArrayList<View> data) {
			this.data = data;
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {

		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			position = position % data.size(); // 0%3=0,1%3=1,2%3=2,3%3=0,4%3=1,5%3=2,6%3=0,........

			View v = data.get(position);
			if (v.getParent() != null) {
				container.removeView(v);
			}

			container.addView(v);

			return v;
		}

	}
}
