//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.0.1.
//


package com.mayi.julyup.listviewItem;

import android.content.Context;
import android.widget.TextView;
import com.mayi.julyup.R.id;
import com.mayi.julyup.R.layout;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;


/**
 * We use @SuppressWarning here because our java code
 * generator doesn't know that there is no need
 * to import OnXXXListeners from View as we already
 * are in a View.
 * 
 */
@SuppressWarnings("unused")
public final class UploadFinishListItem_
    extends UploadFinishListItem
    implements HasViews, OnViewChangedListener
{

    private boolean alreadyInflated_ = false;
    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();

    public UploadFinishListItem_(Context context) {
        super(context);
        init_();
    }

    public static UploadFinishListItem build(Context context) {
        UploadFinishListItem_ instance = new UploadFinishListItem_(context);
        instance.onFinishInflate();
        return instance;
    }

    /**
     * The mAlreadyInflated_ hack is needed because of an Android bug
     * which leads to infinite calls of onFinishInflate()
     * when inflating a layout with a parent and using
     * the <merge /> tag.
     * 
     */
    @Override
    public void onFinishInflate() {
        if (!alreadyInflated_) {
            alreadyInflated_ = true;
            inflate(getContext(), layout.upload_finished_list_item, this);
            onViewChangedNotifier_.notifyViewChanged(this);
        }
        super.onFinishInflate();
    }

    private void init_() {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        OnViewChangedNotifier.registerOnViewChangedListener(this);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        upload_unfinished_list_item_statue = ((TextView) hasViews.findViewById(id.upload_unfinished_list_item_statue));
        upload_unfinished_list_creat_time = ((TextView) hasViews.findViewById(id.upload_unfinished_list_creat_time));
        upload_unfinished_list_last_modify_time = ((TextView) hasViews.findViewById(id.upload_unfinished_list_last_modify_time));
        upload_unfinished_list_item_remark = ((TextView) hasViews.findViewById(id.upload_unfinished_list_item_remark));
        upload_unfinished_list_item_name = ((TextView) hasViews.findViewById(id.upload_unfinished_list_item_name));
    }

}
