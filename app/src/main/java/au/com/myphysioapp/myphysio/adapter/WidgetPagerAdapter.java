package au.com.myphysioapp.myphysio.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Wooden on 08.03.2017.
 */

public abstract class WidgetPagerAdapter<V extends View, T> extends PagerAdapter {
    private OnItemClickListener<T> itemClickListener;
    private int layoutId, viewId;
    {layoutId = viewId = 0;}

    private List<T> data;

    public WidgetPagerAdapter(int layoutId, int viewId, T... data) {
        this.layoutId = layoutId;
        this.viewId = viewId;
        this.data = Arrays.asList(data);
    }

    public WidgetPagerAdapter(int layoutId, int viewId, List<T> data) {
        this.layoutId = layoutId;
        this.viewId = viewId;
        this.data = data;
    }

    //The root view should be an ImageView
    public WidgetPagerAdapter(int layoutId, T[] data) {
        this.layoutId = layoutId;
        this.data = Arrays.asList(data);
    }

    //Call it before passing the adapter to a ViewPager
    public void setOnItemClickListener(OnItemClickListener<T> listener){
        itemClickListener = listener;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final View rootView = LayoutInflater.from(container.getContext())
                .inflate(layoutId, container, false);

        if (itemClickListener != null){
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onItemClick(position, data.get(position));
                }
            });
        }

        View targetView;

        try {
            targetView = viewId != 0 ? rootView.findViewById(viewId)
                    : rootView;
            bindData((V) targetView, data.get(position));
        }
        catch (ClassCastException e){
            throw new IllegalStateException("Wrong targetView type is indicated with viewId " +
                    "property or root targetView has wrong type");
        }
        catch (NullPointerException e){
            throw new IllegalStateException("There is no View with viewId");
        }

        container.addView(rootView);
        return rootView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public int getCount() {
        return data.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public abstract void bindData(V view, T item);
}
