package com.mayi.julyup.listviewItem;

public class PinnedSectionList_Item {
	
	public static final int ITEM = 0;
	public static final int SECTION = 1;

	public final int type;
	public final String text;

	public int sectionPosition;
	public int listPosition;

	public PinnedSectionList_Item(int type, String text) {
		this.type = type;
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}
