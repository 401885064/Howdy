package com.mayi.julyup.adapter;

import java.util.Locale;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mayi.julyup.R;
import com.mayi.julyup.listviewItem.PinnedSectionList_Item;
import com.mayi.julyup.view.PinnedSectionListView.PinnedSectionListAdapter;

public class PinnedSectionlistBaseAdapter extends ArrayAdapter<PinnedSectionList_Item> implements PinnedSectionListAdapter {

    private static final int[] COLORS = new int[] {
        R.color.gold, R.color.aquamarine,
        R.color.whitesmoke, R.color.turquoise };

    public PinnedSectionlistBaseAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);

        final int sectionsNumber = 'Z' - 'A' + 1;
        prepareSections(sectionsNumber);

        int sectionPosition = 0, listPosition = 0;
        for (char i=0; i<sectionsNumber; i++) {
            PinnedSectionList_Item section = new PinnedSectionList_Item(PinnedSectionList_Item.SECTION, String.valueOf((char)('A' + i)));
            section.sectionPosition = sectionPosition;
            section.listPosition = listPosition++;
            onSectionAdded(section, sectionPosition);
            add(section);

            final int itemsNumber = (int) Math.abs((Math.cos(2f*Math.PI/3f * sectionsNumber / (i+1f)) * 25f));
            for (int j=0;j<itemsNumber;j++) {
                PinnedSectionList_Item item = new PinnedSectionList_Item(PinnedSectionList_Item.ITEM, section.text.toUpperCase(Locale.ENGLISH) + " - " + j);
                item.sectionPosition = sectionPosition;
                item.listPosition = listPosition++;
                add(item);
            }

            sectionPosition++;
        }
    }

    protected void prepareSections(int sectionsNumber) { }
    protected void onSectionAdded(PinnedSectionList_Item section, int sectionPosition) { }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getView(position, convertView, parent);
        view.setTextColor(Color.DKGRAY);
        view.setTag("" + position);
        PinnedSectionList_Item item = getItem(position);
        if (item.type == PinnedSectionList_Item.SECTION) {
            //view.setOnClickListener(PinnedSectionListActivity.this);
            view.setBackgroundColor(parent.getResources().getColor(COLORS[item.sectionPosition % COLORS.length]));
        }
        return view;
    }

    @Override public int getViewTypeCount() {
        return 2;
    }

    @Override public int getItemViewType(int position) {
        return getItem(position).type;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == PinnedSectionList_Item.SECTION;
    }

}
