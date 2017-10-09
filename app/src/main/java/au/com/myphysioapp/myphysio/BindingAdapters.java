package au.com.myphysioapp.myphysio;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.bumptech.glide.Glide;

import au.com.myphysioapp.myphysio.widget.WrapContentViewPager;

/**
 * Created by Wooden on 07.02.2017.
 */

public class BindingAdapters {
    @BindingAdapter({"uncheckedDrawableTop", "checkedDrawableTop"})
    public static void setRadioButtonDrawables(RadioButton radioButton,
                                               Drawable unchecked,
                                               Drawable checked){

        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{android.R.attr.state_checked}, checked);
        states.addState(new int[]{}, unchecked);
        radioButton.setCompoundDrawablesWithIntrinsicBounds(null, states, null, null);
    }

    @BindingConversion
    public static int boolToVisibility(boolean visibility){
        return visibility ? View.VISIBLE : View.INVISIBLE;
    }

    @BindingAdapter(value = {"adapter", "measureChildHeight"}, requireAll = false)
    public static void setAdapter(final WrapContentViewPager viewPager,
                                  FragmentPagerAdapter adapter,
                                  boolean measureChildHeight) {
        viewPager.setAdapter(adapter);
        if (measureChildHeight){
            viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
                @Override
                public void onPageSelected(int position) {
                    viewPager.reMeasureCurrentPage(position);
                }
            });
        }
    }

    @BindingAdapter("layout_marginTop")
    public static void setMargin(View view, float topMargin) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        layoutParams.setMargins(layoutParams.leftMargin, Math.round(topMargin),
                layoutParams.rightMargin, layoutParams.bottomMargin);
        view.setLayoutParams(layoutParams);
    }

    @BindingAdapter(value = {"pageTransformer", "reverseDrawingOrder"}, requireAll = false)
    public static void setPageTransformer(ViewPager viewPager,
                                          ViewPager.PageTransformer transformer,
                                          Boolean reverseDrawingOrder) {

        if (reverseDrawingOrder == null)
            viewPager.setPageTransformer(true, transformer);
        else
            viewPager.setPageTransformer(reverseDrawingOrder, transformer);
    }

    @BindingAdapter("viewpager")
    public static void setViewPager(TabLayout tabLayout, ViewPager viewPager) {
        tabLayout.setupWithViewPager(viewPager);
    }

    @BindingAdapter("android:src")
    public static void setImageUrl(ImageView view, String url) {
        Glide.with(view.getContext()).load(url).into(view);
    }

}
