package com.warmtel.android.slider;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class SliderActivity extends FragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.reference_slider_activity_layout);

		getSupportFragmentManager().beginTransaction().add(
				R.id.slider_fragment_layout, SliderFragment.newInstance()).commit();
	}
}
