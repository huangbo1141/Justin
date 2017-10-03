package au.com.myphysioapp.myphysio;

import android.support.v4.app.Fragment;

import au.com.myphysioapp.myphysio.ui.BaseActivity;

/**
 * Basic Viewmodel class
 */

public class FragmentVM extends Fragment {
    public BaseActivity getBaseActivity(){
        return (BaseActivity) getActivity();
    }
}
