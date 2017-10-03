package au.com.myphysioapp.myphysio.ui.login;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import au.com.myphysioapp.myphysio.R;
import au.com.myphysioapp.myphysio.adapter.ViewPagerAdapter;
import au.com.myphysioapp.myphysio.databinding.ActivityLoginBinding;

/**
 * A email screen that offers email via email/password.
 */
public class LoginAVM extends AppCompatActivity{
    private ViewPagerAdapter pagerAdapter;

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

            getTheme().applyStyle(R.style.MyPhysioAppTheme_NoActionBar_WhiteStatusBar, false);
        }

        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        this.pagerAdapter =
                new ViewPagerAdapter(getSupportFragmentManager(),
                        new String[]{getString(R.string.sign_in), getString(R.string.sign_up)},
                        new SignInFVM(),
                        new SignUpFVM());

        binding.setVm(this);

    }

    public ViewPagerAdapter getPagerAdapter(){
        return pagerAdapter;
    }
}

