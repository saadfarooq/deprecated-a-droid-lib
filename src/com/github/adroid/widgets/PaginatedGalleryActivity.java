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

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.github.adroid.R;

public class PaginatedGalleryActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		layout.setBackgroundColor(android.R.color.white);	
		
		List<Drawable> list = new ArrayList<Drawable>();
		
		for ( int i = 0 ; i < 8 ; i ++ ) {
			list.add(getResources().getDrawable(R.drawable.ic_launcher));
		}

		PaginatedGalleryAdapter adapter = new PaginatedGalleryAdapter(this, list, 4);
        
        PaginatedGallery gallery = new PaginatedGallery(this);
        gallery.setAdapter(adapter);
        layout.addView(gallery);
        
        setContentView(layout);
        
    }
}
