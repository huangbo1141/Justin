package au.com.myphysioapp.myphysio.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

/**
 * Created by Wooden on 25.11.2016.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private Fragment[] items;
    private String[] titles;

    public ViewPagerAdapter(FragmentManager fm, String[] titles, Fragment... fragments) {
        super(fm);
        if (fragments.length != titles.length)
            throw new IllegalArgumentException("Titles count should be equal to fragments count");
        this.items = fragments;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return items[position];
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }
}
