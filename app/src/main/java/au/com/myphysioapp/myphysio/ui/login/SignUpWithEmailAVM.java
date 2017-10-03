package au.com.myphysioapp.myphysio.ui.login;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RadioGroup;

import java.util.EnumSet;

import au.com.myphysioapp.myphysio.BR;
import au.com.myphysioapp.myphysio.R;
import au.com.myphysioapp.myphysio.adapter.OnItemClickListener;
import au.com.myphysioapp.myphysio.adapter.RVBindingAdapter;
import au.com.myphysioapp.myphysio.databinding.ActivitySignUpWithEmailBinding;
import au.com.myphysioapp.myphysio.model.Hobby;
import au.com.myphysioapp.myphysio.widget.MarginDecoration;
import au.com.myphysioapp.myphysio.widget.RVConf;

public class SignUpWithEmailAVM extends AppCompatActivity implements
        OnItemClickListener<ItemHobbyVM> {

    public final ObservableField<String> email = new ObservableField<>(),
            password = new ObservableField<>(),
            postCode = new ObservableField<>();

    //Hobbies part
    private EnumSet<Hobby> checkedHobbies = EnumSet.allOf(Hobby.class);
    public final RVConf listConf = new RVConf();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivitySignUpWithEmailBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_sign_up_with_email);

        RVBindingAdapter<ItemHobbyVM> hobbyAdapter;
        hobbyAdapter = new RVBindingAdapter<>(R.layout.item_hobby, BR.vm,
                ItemHobbyVM.defaultHobbies(this));

        hobbyAdapter.setOnItemClickListener(this);
        listConf.setAdapter(hobbyAdapter);
        listConf.setItemDecoration(new MarginDecoration(this));
        listConf.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));

        binding.setVm(this);
    }

    public void onClickSignUp(View view){
        finish();
    }

    public void onActivityLevelChecked(RadioGroup group, int checkedId){

    }

    public void onGenderChecked(RadioGroup group, int checkedId) {

    }

    //Click on hobby item callback
    @Override
    public void onItemClick(int position, ItemHobbyVM item) {
        //Inform VM about the click event
        item.onClickItem(null);
        checkedHobbies.add(item.hobby);
    }
}
