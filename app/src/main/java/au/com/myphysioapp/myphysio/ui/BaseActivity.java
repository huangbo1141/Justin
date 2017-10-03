package au.com.myphysioapp.myphysio.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public abstract class BaseActivity extends AppCompatActivity {
    //Without recording to the back stack
    public void changeActivity(Class<? extends Activity> destinationActivity){
        if (!destinationActivity.isInstance(this)) {
            Intent i = new Intent(this, destinationActivity);
            //i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(i);
            finish();
        }
    }

    //Fragment management part
    public void changeFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(getFragmentArea(), fragment)
                .addToBackStack(null)
                .commit();
    }

    public void changeFragment(Fragment fragment, boolean addToBackStack){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction()
                .replace(getFragmentArea(), fragment);
        if (addToBackStack) fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void changeFragment(Fragment fragment, boolean addToBackStack, boolean hideToolBar){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            if (hideToolBar) actionBar.hide();
            else actionBar.show();
        changeFragment(fragment, addToBackStack);
    }

    public abstract int getFragmentArea();
}
