package au.com.myphysioapp.myphysio.ui;

import android.databinding.DataBindingUtil;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.roughike.bottombar.OnTabSelectListener;

import au.com.myphysioapp.myphysio.R;
import au.com.myphysioapp.myphysio.databinding.ActivityBottomBarBinding;
import au.com.myphysioapp.myphysio.ui.contacts.ContactsFVM;
import au.com.myphysioapp.myphysio.ui.home.HomeFVM;
import au.com.myphysioapp.myphysio.ui.profile.ProfileFVM;
import au.com.myphysioapp.myphysio.ui.user_programs.ProgramListFVM;

public class BottomBarActivity extends BaseActivity implements OnTabSelectListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityBottomBarBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_bottom_bar);

        binding.setVm(this);
    }

    @Override
    public int getFragmentArea() {
        return R.id.main_content;
    }

    @Override
    public void onTabSelected(@IdRes int tabId) {
        switch (tabId){
            case R.id.tab_home:
                changeFragment(new HomeFVM(), false);
                break;

            case R.id.tab_programs:
                changeFragment(new ProgramListFVM(), false);
                break;

            case R.id.tab_profile:
                changeFragment(new ProfileFVM(), false);
                break;

            case R.id.tab_myphysio:
                changeFragment(new ContactsFVM(), false);
                break;
        }
    }
}
