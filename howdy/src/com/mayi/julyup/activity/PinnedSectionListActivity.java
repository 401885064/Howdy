/*
 * Copyright (C) 2013 Sergej Shafarenka, halfbit.de
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mayi.julyup.activity;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mayi.julyup.R;
import com.mayi.julyup.adapter.MyFastScrollAdapter;
import com.mayi.julyup.adapter.PinnedSectionlistBaseAdapter;
import com.mayi.julyup.listviewItem.PinnedSectionList_Item;

public class PinnedSectionListActivity extends ListActivity implements OnClickListener {
	
	private boolean hasHeaderAndFooter;
	private boolean isFastScroll;
	private boolean addPadding;
	private boolean isShadowVisible = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.upload_history_listview);
		if (savedInstanceState != null) {
		    isFastScroll = savedInstanceState.getBoolean("isFastScroll");
		    addPadding = savedInstanceState.getBoolean("addPadding");
		    isShadowVisible = savedInstanceState.getBoolean("isShadowVisible");
		    hasHeaderAndFooter = savedInstanceState.getBoolean("hasHeaderAndFooter");
		}
		
		initializeHeaderAndFooter();
		initializeAdapter();
		initializePadding();
	}

    @Override
	protected void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    outState.putBoolean("isFastScroll", isFastScroll);
	    outState.putBoolean("addPadding", addPadding);
	    outState.putBoolean("isShadowVisible", isShadowVisible);
	    outState.putBoolean("hasHeaderAndFooter", hasHeaderAndFooter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		PinnedSectionList_Item item = (PinnedSectionList_Item) getListView().getAdapter().getItem(position);
	    if (item != null) {
	        Toast.makeText(this, "Item " + position + ": " + item.text, Toast.LENGTH_SHORT).show();
	    } else {
	        Toast.makeText(this, "Item " + position, Toast.LENGTH_SHORT).show();
	    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		menu.getItem(0).setChecked(isFastScroll);
		menu.getItem(1).setChecked(addPadding);
		menu.getItem(2).setChecked(isShadowVisible);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
//	    switch (item.getItemId()) {
//    	    case R.id.action_fastscroll:
//    	        isFastScroll = !isFastScroll;
//    	        item.setChecked(isFastScroll);
//    	        initializeAdapter();
//    	        break;
//    	    case R.id.action_addpadding:
//    	        addPadding = !addPadding;
//    	        item.setChecked(addPadding);
//    	        initializePadding();
//    	        break;
//    	    case R.id.action_showShadow:
//    	        isShadowVisible = !isShadowVisible;
//    	        item.setChecked(isShadowVisible);
//    	        ((PinnedSectionListView)getListView()).setShadowVisible(isShadowVisible);
//    	        break;
//    	    case R.id.action_showHeaderAndFooter:
//    	        hasHeaderAndFooter = !hasHeaderAndFooter;
//    	        item.setChecked(hasHeaderAndFooter);
//    	        initializeHeaderAndFooter();
//    	        break;
//	    }
	    return true;
	}

	private void initializePadding() {
	    float density = getResources().getDisplayMetrics().density;
	    int padding = addPadding ? (int) (16 * density) : 0;
	    getListView().setPadding(padding, padding, padding, padding);
	}

    private void initializeHeaderAndFooter() {
        setListAdapter(null);
        if (hasHeaderAndFooter) {
            ListView list = getListView();

            LayoutInflater inflater = LayoutInflater.from(this);
            TextView header1 = (TextView) inflater.inflate(android.R.layout.simple_list_item_1, list, false);
            header1.setText("First header");
            list.addHeaderView(header1);

            TextView header2 = (TextView) inflater.inflate(android.R.layout.simple_list_item_1, list, false);
            header2.setText("Second header");
            list.addHeaderView(header2);

            TextView footer = (TextView) inflater.inflate(android.R.layout.simple_list_item_1, list, false);
            footer.setText("Single footer");
            list.addFooterView(footer);
        }
        initializeAdapter();
    }

    @SuppressLint("NewApi")
    public void initializeAdapter() {
    	isFastScroll=true;
        getListView().setFastScrollEnabled(isFastScroll);
        if (isFastScroll) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                getListView().setFastScrollAlwaysVisible(true);
            }
            setListAdapter(new MyFastScrollAdapter(PinnedSectionListActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1));
        } else {
            setListAdapter(new PinnedSectionlistBaseAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1));
        }
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "Item: " + v.getTag() , Toast.LENGTH_SHORT).show();
    }

}