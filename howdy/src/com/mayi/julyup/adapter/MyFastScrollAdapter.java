package com.mayi.julyup.adapter;

import android.content.Context;
import android.widget.SectionIndexer;

import com.mayi.julyup.listviewItem.PinnedSectionList_Item;

public class MyFastScrollAdapter extends PinnedSectionlistBaseAdapter implements SectionIndexer {

	private PinnedSectionList_Item[] sections;

	public MyFastScrollAdapter(Context context, int resource,int textViewResourceId) {
		super(context, resource, textViewResourceId);
	}

	@Override
	protected void prepareSections(int sectionsNumber) {
		sections = new PinnedSectionList_Item[sectionsNumber];
	}

	@Override
	protected void onSectionAdded(PinnedSectionList_Item section,int sectionPosition) {
		sections[sectionPosition] = section;
	}

	@Override
	public PinnedSectionList_Item[] getSections() {
		return sections;
	}

	@Override
	public int getPositionForSection(int section) {
		if (section >= sections.length) {
			section = sections.length - 1;
		}
		return sections[section].listPosition;
	}

	@Override
	public int getSectionForPosition(int position) {
		if (position >= getCount()) {
			position = getCount() - 1;
		}
		return getItem(position).sectionPosition;
	}

}