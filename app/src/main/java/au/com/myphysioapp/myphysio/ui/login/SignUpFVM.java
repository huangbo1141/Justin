package au.com.myphysioapp.myphysio.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import au.com.myphysioapp.myphysio.FragmentVM;
import au.com.myphysioapp.myphysio.databinding.FrSignUpBinding;
import au.com.myphysioapp.myphysio.ui.BottomBarActivity;

/**
 * Created by Wooden on 09.03.2017.
 */

public class SignUpFVM extends FragmentVM {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final FrSignUpBinding binding = FrSignUpBinding.inflate(inflater);
        binding.setVm(this);
        return binding.getRoot();
    }

    public void onFacebookSignUp(View view) {
    }

    public void onGooglePlusSignUp(View view) {
    }

    public void onEmailSignUp(View view) {
        Intent signUpWithEmail = new Intent(getActivity(), SignUpWithEmailAVM.class);
        startActivityForResult(signUpWithEmail, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent i = new Intent(getActivity(), BottomBarActivity.class);
        startActivity(i);
    }
}
