package au.com.myphysioapp.myphysio.ui.contacts;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import au.com.myphysioapp.myphysio.FragmentVM;
import au.com.myphysioapp.myphysio.R;
import au.com.myphysioapp.myphysio.databinding.FrContactsBinding;

/**
 * Created by Wooden on 14.02.2017.
 */

public class ContactsFVM extends FragmentVM{
    private Bitmap header;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(R.string.my_physio);
        FrContactsBinding binding = FrContactsBinding.inflate(inflater);
        header = BitmapFactory.decodeResource(getResources(), R.drawable.test_doctor);
        binding.setVm(this);
        return binding.getRoot();
    }

    public Bitmap getHeaderImage() {
        return header;
    }

    public void onUrgentAppointmentClick(View view){

    }

    public void onVisitWebsiteClick(View view){

    }

    public void onEmailClick(View view){

    }

    public void onRequestAppointmentClick(View view){

    }

    public void onCallClick(View view){

    }

    public void onShareClick(View view){

    }

}
