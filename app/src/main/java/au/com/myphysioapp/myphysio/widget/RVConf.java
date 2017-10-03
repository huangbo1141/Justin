package au.com.myphysioapp.myphysio.widget;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import au.com.myphysioapp.myphysio.BR;

/**
 * Created by Wooden on 23.11.2016.
 */

public class RVConf extends BaseObservable {
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.ItemDecoration itemDecoration;
    private RecyclerView.Adapter adapter;
    private RecyclerView.OnScrollListener onScrollListener;


    public RVConf() {
    }

    public RVConf(RecyclerView.LayoutManager layoutManager, RecyclerView.ItemDecoration itemDecoration, RecyclerView.Adapter adapter) {
        this.layoutManager = layoutManager;
        this.itemDecoration = itemDecoration;
        this.adapter = adapter;
    }

    @Bindable
    public RecyclerView.LayoutManager getLayoutManager() {
        return layoutManager;
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        notifyPropertyChanged(BR.layoutManager);
    }

    @Bindable
    public RecyclerView.ItemDecoration getItemDecoration() {
        return itemDecoration;
    }

    public void setItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        this.itemDecoration = itemDecoration;
        notifyPropertyChanged(BR.itemDecoration);
    }

    @Bindable
    public RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
        notifyPropertyChanged(BR.adapter);
    }

    @Bindable
    public RecyclerView.OnScrollListener getOnScrollListener() {
        return onScrollListener;
    }

    public void setOnScrollListener(RecyclerView.OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
        notifyPropertyChanged(BR.onScrollListener);
    }

    @BindingAdapter("configuration")
    public static void configureRecyclerView(RecyclerView recyclerView, RVConf conf) {
        if (conf == null) return;
        if (conf.getLayoutManager() != null)
            recyclerView.setLayoutManager(conf.getLayoutManager());

        if (conf.getItemDecoration() != null)
            recyclerView.addItemDecoration(conf.getItemDecoration());

        if (conf.getAdapter() != null) {
            recyclerView.setAdapter(conf.getAdapter());
        }

        if (conf.getOnScrollListener() != null){
            recyclerView.addOnScrollListener(conf.getOnScrollListener());
        }
        recyclerView.invalidate();
    }
}
