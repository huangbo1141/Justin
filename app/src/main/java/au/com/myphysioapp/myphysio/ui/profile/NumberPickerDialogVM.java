package au.com.myphysioapp.myphysio.ui.profile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.widget.NumberPicker;

import au.com.myphysioapp.myphysio.R;
import au.com.myphysioapp.myphysio.databinding.DialogNumberPickerBinding;

/**
 * Created by Wooden on 06.03.2017.
 */

public class NumberPickerDialogVM extends DialogFragment{
    public static final String NUMBER_DIALOG_TITLE =
            "au.com.myphysioapp.myphysio.ui.profile.NUMBER_DIALOG_TITLE";

    public static final String PICKER_MIN_VALUE =
            "au.com.myphysioapp.myphysio.ui.profile.PICKER_MIN_VALUE";

    public static final String PICKER_MAX_VALUE =
            "au.com.myphysioapp.myphysio.ui.profile.PICKER_MAX_VALUE";

    public static final String PICKER_VALUE =
            "au.com.myphysioapp.myphysio.ui.profile.PICKER_VALUE";


    private onNumberPickedListener buttonClickListener;
    private String title;
    private int maxValue;
    private int minValue;
    private int value;

    public int getMaxValue() {
        return maxValue;
    }

    public int getMinValue() {
        return minValue;
    }

    public int getValue() {
        return value;
    }

    public static NumberPickerDialogVM newInstance(String title, int minValue, int maxValue, int value){
        Bundle args = new Bundle();
        args.putString(NUMBER_DIALOG_TITLE, title);
        args.putInt(PICKER_MIN_VALUE, minValue);
        args.putInt(PICKER_MAX_VALUE, maxValue);
        args.putInt(PICKER_VALUE, value);
        NumberPickerDialogVM result = new NumberPickerDialogVM();
        result.setArguments(args);
        return result;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            buttonClickListener = (onNumberPickedListener)getTargetFragment();

            //Fragment haven't been passed, using Activity
            if (buttonClickListener == null)
                buttonClickListener = (onNumberPickedListener)getActivity();

        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() +
                    " should implement onNumberPickedListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString(NUMBER_DIALOG_TITLE);
        minValue = getArguments().getInt(PICKER_MIN_VALUE);
        maxValue = getArguments().getInt(PICKER_MAX_VALUE);
        value = getArguments().getInt(PICKER_VALUE);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title);

        DialogNumberPickerBinding pickerBinding =
                DialogNumberPickerBinding.inflate(LayoutInflater.from(getContext()));
        pickerBinding.setVm(this);

        builder.setView(pickerBinding.getRoot());
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                buttonClickListener.onNumberPicked(NumberPickerDialogVM.this, value);
                NumberPickerDialogVM.this.dismiss();
            }
        });
        return builder.create();
    }

    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
        value = i1;
    }


    /* The activity or the fragment that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Use setTargetFragment method for fragments */

    interface onNumberPickedListener {
        void onNumberPicked(NumberPickerDialogVM dialog, int value);
    }
}
