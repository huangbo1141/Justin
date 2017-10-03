package au.com.myphysioapp.myphysio.ui.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import au.com.myphysioapp.myphysio.R;
import au.com.myphysioapp.myphysio.adapter.ImagePagerAdapter;
import au.com.myphysioapp.myphysio.databinding.ActivityAppOverviewAvmBinding;

public class AppOverviewAVM extends AppCompatActivity implements ViewPager.PageTransformer {
    private List<String> statements;
    public ObservableField<String> currentStatement = new ObservableField<>();

    public final ViewPager.OnPageChangeListener pageChangeListener =
            new ViewPager.SimpleOnPageChangeListener(){
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    currentStatement.set(statements.get(position));
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Trying to paint status bar in white
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            Window window = getWindow();

            // clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            // finally change the color
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.background));

            setTheme(R.style.MyPhysioAppTheme_NoActionBar_WhiteStatusBar);
        } else {
            setTheme(R.style.MyPhysioAppTheme_NoActionBar);
        }


        ActivityAppOverviewAvmBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_app_overview_avm);

        //Filling with test data
        final int items_count = 3;
        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.app_overview_image);

        ImagePagerAdapter adapter = new ImagePagerAdapter(R.layout.item_overview_picture,
                R.id.overview_image,
                Collections.nCopies(items_count, image));

        statements = new ArrayList<>(items_count);
        statements.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Ut iaculis vulputate tellus, at convallis felis consequat vel. " +
                "Donec venenatis non ipsum in mollis.");

        statements.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Ut iaculis vulputate tellus, at convallis felis consequat vel. " +
                "Donec venenatis non ipsum in mollis." +
                "Fusce placerat eros quis augue porttitor iaculis. Sed nec nunc velit. " +
                "Duis dictum erat sit amet porta scelerisque. " +
                "In tincidunt tellus quis lorem euismod, non sagittis magna imperdiet.");

        statements.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");

        binding.overviewImages.setAdapter(adapter);
        pageChangeListener.onPageSelected(binding.overviewImages.getCurrentItem());
        binding.setVm(this);
    }

    public void onClickLogin(View view){
        Intent i = new Intent(this, LoginAVM.class);
        startActivity(i);
    }

    //TODO:Resolve the problem with page left alignment
    //Custom transition animation for the Viewpager
    private static final float MIN_SCALE = 0.75f;
    @Override
    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(0);

        } else if (position <= 0) { // [-1,0]
            // Use the default slide transition when moving to the left page
            view.setAlpha(1);
            view.setTranslationX(0);
            view.setScaleX(1);
            view.setScaleY(1);

        } else if (position <= 1) { // (0,1]
            // Fade the page out.
            view.setAlpha(1 - position);

            // Counteract the default slide transition
            view.setTranslationX(pageWidth * -position);

            // Scale the page down (between MIN_SCALE and 1)
            float scaleFactor = MIN_SCALE
                    + (1 - MIN_SCALE) * (1 - Math.abs(position));
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(0);
        }
    }
}
