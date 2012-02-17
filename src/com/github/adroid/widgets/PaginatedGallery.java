/*******************************************************************************
 * Copyright 2012 Saad Farooq
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.github.adroid.widgets;

import com.viewpagerindicator.CirclePageIndicator;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class PaginatedGallery extends ViewGroup {

	private final static String TAG = "PaginatedGallery";
	ViewPager mPager;
	CirclePageIndicator mPagerIndicator;
	
	public PaginatedGallery(Context context) {
		super(context);
		mPager = new ViewPager(context);
		mPagerIndicator = new CirclePageIndicator(context);
		addView(mPager);
		addView(mPagerIndicator);
	}

	public void setAdapter(PaginatedGalleryAdapter adapter) {
		mPager.setAdapter(adapter);
		mPagerIndicator.setViewPager(mPager);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		View child = getChildAt(0);

		Log.i(TAG, "Pager top : "+ t + " Padding top : " +getPaddingTop());
		
		int pagerBottom = t + ((PaginatedGalleryAdapter) mPager.getAdapter()).getLayoutHeight();
		child.layout(l, t, r, pagerBottom);
		
		child = getChildAt(1);
		Log.i(TAG, " Pager bottom: "+pagerBottom + ", indicator bottom : "+ pagerBottom+child.getMeasuredHeight());
		child.layout(l, pagerBottom, r, pagerBottom+child.getMeasuredHeight());
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		View child = getChildAt(0);
		measureChild(child, widthMeasureSpec, MeasureSpec.makeMeasureSpec(((PaginatedGalleryAdapter) mPager.getAdapter()).getLayoutHeight(), MeasureSpec.EXACTLY));
		
		child = getChildAt(1);
		measureChild(child, widthMeasureSpec, heightMeasureSpec);

		Log.i(TAG, "Setting measured dimensions: " + MeasureSpec.getSize(widthMeasureSpec) + ", " + (getChildAt(0).getMeasuredHeight() + child.getMeasuredHeight()));
		setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), getChildAt(0).getMeasuredHeight() + child.getMeasuredHeight());
	}

}
