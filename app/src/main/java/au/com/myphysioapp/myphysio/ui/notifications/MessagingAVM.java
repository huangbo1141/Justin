package au.com.myphysioapp.myphysio.ui.notifications;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import au.com.myphysioapp.myphysio.BR;
import au.com.myphysioapp.myphysio.R;
import au.com.myphysioapp.myphysio.adapter.NewNotificationRVAdapter;
import au.com.myphysioapp.myphysio.adapter.RVBindingAdapter;
import au.com.myphysioapp.myphysio.databinding.ActivityNotificationsBinding;
import au.com.myphysioapp.myphysio.widget.MarginDecoration;
import au.com.myphysioapp.myphysio.widget.RVConf;

public class MessagingAVM extends AppCompatActivity {
    public final RVConf readListConf = new RVConf();
    public final RVConf newListConf = new RVConf();

    private NewNotificationRVAdapter newListAdapter;
    private RVBindingAdapter<ReadNotificationVM> readListAdapter;

    //Forced to juggle with viewmodels cause there're no models
    private final NewNotificationRVAdapter.OnItemButtonClickListener<NewNotificationVM> onAccept =
            new NewNotificationRVAdapter.OnItemButtonClickListener<NewNotificationVM>() {
                @Override
                public void onButtonClick(int position, NewNotificationVM item, boolean isAccepted) {
                    newListAdapter.removeItem(position);
                    readListAdapter.add(0, new ReadNotificationVM(MessagingAVM.this, item.date, item.sender, isAccepted));
                }
            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityNotificationsBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_notifications);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //Filling with test data
        Bitmap senderPicture = BitmapFactory.decodeResource(getResources(),
                R.drawable.test_girl_picture);

        NewNotificationVM itemNew = new NewNotificationVM(this, new Date(), "Olga Polit", senderPicture);

        newListAdapter = new NewNotificationRVAdapter(new ArrayList<>(Collections.nCopies(4, itemNew)), onAccept);

        newListConf.setAdapter(newListAdapter);
        newListConf.setItemDecoration(new MarginDecoration(this));
        newListConf.setLayoutManager(new LinearLayoutManager(this));

        ReadNotificationVM itemVM = new ReadNotificationVM(this, new Date(), "Jennifer Goodgirl", true);
        ReadNotificationVM itemVM2 = new ReadNotificationVM(this, new Date(), "Erik Badboy", false);
        ArrayList<ReadNotificationVM> listdata = new ArrayList<>();
        listdata.addAll(Collections.nCopies(2, itemVM));
        listdata.addAll(Collections.nCopies(2, itemVM2));

        readListAdapter = new RVBindingAdapter<>(R.layout.item_notification_read,
                BR.vm, listdata);

        readListConf.setAdapter(readListAdapter);
        readListConf.setItemDecoration(new MarginDecoration(this));
        readListConf.setLayoutManager(new LinearLayoutManager(this));

        binding.setVm(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return false;
        }
    }
}
