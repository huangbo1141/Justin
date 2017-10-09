package au.com.myphysioapp.myphysio.ui.statistics;

import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import au.com.myphysioapp.myphysio.R;
import au.com.myphysioapp.myphysio.adapter.ViewPagerAdapter;
import au.com.myphysioapp.myphysio.databinding.ActivityStatisticsBinding;
import au.com.myphysioapp.myphysio.model.ProgressItem;

public class StatisticsAVM extends AppCompatActivity{
    private List<ProgressItem> progressItems;
    private FragmentPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStatisticsBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_statistics);

        setSupportActionBar(binding.toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
            upArrow.setColorFilter(ContextCompat.getColor(this, R.color.colorStatToolbarElements),
                    PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);

        }

        /*
            Fill with test data
            data SHOULD be sorted by ProgressItem.date property in ASCENT order
            to avoid bugs with a chart
        */

        final int itemsCount = 17;
        progressItems = new ArrayList<>(itemsCount);

        Random rand = new Random(47);
        //for (int i = itemsCount - 1; i >= 0; i--) {
        for (int i = 0; i < itemsCount; i++) {
            Calendar day = Calendar.getInstance();
            day.set(Calendar.DAY_OF_MONTH, i + 8);
            int highValue = (int)(rand.nextFloat() * 10 + 5);
            int curValue = rand.nextInt(highValue);
            progressItems.add(new ProgressItem("Test Program " + i, day.getTime(), highValue, curValue));
        }

        String listTitle = getString(R.string.list);
        String chartTitle = getString(R.string.chart);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),
                new String[]{chartTitle,listTitle},
                new ProgressChartFVM(),
                new ProgressListFVM()
                );

        binding.setVm(this);
    }

    public List<ProgressItem> getProgressItems() {
        return progressItems;
    }

    public PagerAdapter getAdapter(){
        return viewPagerAdapter;
    }

    public void onClickBackButton(View view){
        finish();
    }
}
