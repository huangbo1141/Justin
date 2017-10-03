package au.com.myphysioapp.myphysio.adapter;

import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Pager adapter that doesn't require fragments
 * Use it with simple layouts
 */

public class ImagePagerAdapter extends WidgetPagerAdapter<ImageView, Bitmap> {
    public ImagePagerAdapter(int layoutId, int viewId, Bitmap... data) {
        super(layoutId, viewId, data);
    }

    public ImagePagerAdapter(int layoutId, int viewId, List<Bitmap> data) {
        super(layoutId, viewId, data);
    }

    public ImagePagerAdapter(int layoutId, Bitmap[] data) {
        super(layoutId, data);
    }

    @Override
    public void bindData(ImageView view, Bitmap item) {
        view.setImageBitmap(item);
    }
}
