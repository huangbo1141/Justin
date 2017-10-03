package au.com.myphysioapp.myphysio.ui.profile;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RadioGroup;

import java.util.Calendar;
import java.util.Date;
import java.util.EnumSet;

import au.com.myphysioapp.myphysio.BR;
import au.com.myphysioapp.myphysio.FragmentVM;
import au.com.myphysioapp.myphysio.R;
import au.com.myphysioapp.myphysio.adapter.OnItemClickListener;
import au.com.myphysioapp.myphysio.adapter.RVBindingAdapter;
import au.com.myphysioapp.myphysio.databinding.FrProfileBinding;
import au.com.myphysioapp.myphysio.model.Hobby;
import au.com.myphysioapp.myphysio.ui.login.ItemHobbyVM;
import au.com.myphysioapp.myphysio.widget.MarginDecoration;
import au.com.myphysioapp.myphysio.widget.RVConf;

/**
 * Created by Wooden on 14.02.2017.
 */

public class ProfileFVM extends FragmentVM implements NumberPickerDialogVM.onNumberPickedListener,
        DialogInterface.OnClickListener,
        DatePickerDialog.OnDateSetListener,
        OnItemClickListener<ItemHobbyVM>{

    //Bounds constants
    public static final int MAX_HEIGHT = 250;
    public static final int MIN_HEIGHT = 100;

    public static final int MAX_WEIGHT = 200;
    public static final int MIN_WEIGHT = 30;

    //Dialogs tags
    public static final String HEIGHT_PICKER =
            "au.com.myphysioapp.myphysio.ui.profile.HEIGHT_PICKER";

    public static final String WEIGHT_PICKER =
            "au.com.myphysioapp.myphysio.ui.profile.WEIGHT_PICKER";


    /*
    * Array that maps int value to specific element id in radiogroup
    * Used to set and parse value from form_activity_level fast and without "switch macaroni"
    */
    private final int[] activityLevelRadioButtons = new int[]{-1, //to avoid additional operations
                                                             R.id.level_1,
                                                             R.id.level_2,
                                                             R.id.level_3,
                                                             R.id.level_4,
                                                             R.id.level_5};

    private Bitmap userPicture;
    public final ObservableBoolean isMale = new ObservableBoolean();
    public final ObservableField<Date> birthDay = new ObservableField<>();
    public final ObservableInt userHeight = new ObservableInt();
    public final ObservableInt userWeight = new ObservableInt();

    //Value varies in [1..5] interval
    private int userActivityLevel;
    {
        isMale.set(false);
        Date d1 = new Date();
        d1.setYear(92);
        birthDay.set(d1);
        userHeight.set(182);
        userWeight.set(76);
        userActivityLevel = 3;
    }

    //Editable fields
    public final ObservableField<String> userName = new ObservableField<>();
    public final ObservableField<String> userPhone = new ObservableField<>();
    public final ObservableField<String> userEmail = new ObservableField<>();
    public final ObservableField<String> userPostCode = new ObservableField<>();

    private EnumSet<Hobby> checkedHobbies = EnumSet.allOf(Hobby.class);
    public final RVConf listConf = new RVConf();


    public Bitmap getUserPicture() {
        return userPicture;
    }

    public Integer getUserActivityLevel(){
        return activityLevelRadioButtons[userActivityLevel];
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FrProfileBinding binding = FrProfileBinding.inflate(inflater);
        getActivity().setTitle(R.string.profile);
        userPicture = BitmapFactory.decodeResource(getResources(), R.drawable.test_girl_picture);

        RVBindingAdapter<ItemHobbyVM> hobbyAdapter;
        hobbyAdapter = new RVBindingAdapter<>(R.layout.item_hobby, BR.vm,
                ItemHobbyVM.defaultHobbies(getContext()));

        hobbyAdapter.setOnItemClickListener(this);
        listConf.setAdapter(hobbyAdapter);
        listConf.setItemDecoration(new MarginDecoration(getContext()));
        listConf.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        binding.setVm(this);
        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.profile_settings, menu);
    }

    public void onClickGender(View v){
        AlertDialog.Builder sexDialogBuilder;
        sexDialogBuilder = new AlertDialog.Builder(getContext());
        String[] items = new String[]{getString(R.string.male), getString(R.string.female)};
        sexDialogBuilder.setItems(items, this);
        sexDialogBuilder.create().show();
    }

    public void onClickBirthday(View v){
        //Dialog initialization
        Calendar initialDate = Calendar.getInstance();
        initialDate.setTimeInMillis(birthDay.get().getTime());

        DatePickerDialog birthDayDialog = new DatePickerDialog(
                getContext(),
                this,
                initialDate.get(Calendar.YEAR),
                initialDate.get(Calendar.MONTH),
                initialDate.get(Calendar.DAY_OF_MONTH));

        birthDayDialog.show();
    }

    public void onClickHeight(View v){
        NumberPickerDialogVM heightPicker =
                NumberPickerDialogVM.newInstance(getString(R.string.choose_your_height),
                        MIN_HEIGHT,
                        MAX_HEIGHT,
                        userHeight.get());

        heightPicker.setTargetFragment(this, 0);
        heightPicker.show(getFragmentManager(), HEIGHT_PICKER);
    }

    public void onClickWeight(View v){
        NumberPickerDialogVM heightPicker =
                NumberPickerDialogVM.newInstance(getString(R.string.choose_your_weight),
                        MIN_WEIGHT,
                        MAX_WEIGHT,
                        userWeight.get());

        heightPicker.setTargetFragment(this, 0);
        heightPicker.show(getFragmentManager(), WEIGHT_PICKER);
    }


    //Height and weight dialogs callback
    @Override
    public void onNumberPicked(NumberPickerDialogVM dialog, int value) {
        String tag = dialog.getTag();
        switch (tag){
            case HEIGHT_PICKER:
                userHeight.set(value);
                break;

            case WEIGHT_PICKER:
                userWeight.set(value);
                break;
        }
    }

    //Gender dialog callback
    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        isMale.set(i == 0);
    }

    //Birthday dialog callback
    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth){
        Calendar setDate = Calendar.getInstance();
        setDate.set(Calendar.YEAR, year);
        setDate.set(Calendar.MONTH, monthOfYear);
        setDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        birthDay.set(setDate.getTime());
    }

    //Click on hobby item callback
    @Override
    public void onItemClick(int position, ItemHobbyVM item) {
        //Inform VM about the click event
        item.onClickItem(null);
        checkedHobbies.add(item.hobby);
    }

    public void onActivityLevelChecked(RadioGroup radioGroup, int radioButtonId) {
        for(int index = 1; index < activityLevelRadioButtons.length; index++)
            if (activityLevelRadioButtons[index] == radioButtonId)
                userActivityLevel = index;
    }

}
