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

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class PaginatedGalleryAdapter extends PagerAdapter {
	private static final String TAG = "PaginatedGalleryAdapter";

	private int viewPages;
	private int viewsPerPage;
	private static List<Drawable> images;
	private Context context;
	private int screenWidth;
	private OnItemClickListener mItemClickListener;
    
	/**
	 * Creates a PaginatedGallery using the List of Drawables with the specified views per page
	 * @param context the Context for the PaginatedGallery
	 * @param list the List of Drawables to display
	 * @param viewsPerPage the number of views on one page
	 */
    public PaginatedGalleryAdapter ( final Context context, final List<Drawable> list, final int viewsPerPage ) {
    	this.context = context;
    	this.viewsPerPage = viewsPerPage;
    	viewPages = (int) Math.ceil((double) list.size()/viewsPerPage );
    	images = list;
    	screenWidth = ( (WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
    }
    
    public interface OnItemClickListener {
		void onItemClick(View view, int position);
	}
	
	public void setOnItemClickListener(OnItemClickListener listener) {
		mItemClickListener = listener;
	}    
    
    @Override
    public int getCount() {
        return viewPages;
    }

	@Override
	public Object instantiateItem(View collection, int position) {
		LinearLayout layout = new LinearLayout(context);
		layout.setOrientation(LinearLayout.HORIZONTAL);
		
		layout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		int size = images.size();
		for (int i = 0 ; i < viewsPerPage; i++ ) {
			ImageView imageView = new ImageView(context);
			final int index = position + (position * (viewsPerPage - 1))+ i;
			if (index < size ) {
				imageView.setImageDrawable(images.get(index));
				imageView.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						if (mItemClickListener != null ) {
							mItemClickListener.onItemClick(v, index);
						}
					}
				});
			}
			imageView.setLayoutParams(new LayoutParams(screenWidth/viewsPerPage, screenWidth/viewsPerPage));
			imageView.setPadding(10, 10, 10, 10);
			layout.addView(imageView);
		}
		
		((ViewPager) collection).addView(layout);
		return layout;
	}
	
	@Override
	public void destroyItem(View collection, int position, Object view) {
		((ViewPager) collection).removeView((LinearLayout) view);
	}
	
	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view==((LinearLayout)object);
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {}

	@Override
	public Parcelable saveState() { 
		return null;
	}

	@Override
	public void startUpdate(View arg0) { }
	
	@Override
	public void finishUpdate(View arg0) {}

	public int getLayoutHeight() {
		Log.i(TAG, "Pager height : " + screenWidth/viewsPerPage);
		return screenWidth/viewsPerPage;
	}
	
	
	
}
