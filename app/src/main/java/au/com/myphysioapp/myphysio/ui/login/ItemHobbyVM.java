package au.com.myphysioapp.myphysio.ui.login;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import au.com.myphysioapp.myphysio.BR;
import au.com.myphysioapp.myphysio.R;
import au.com.myphysioapp.myphysio.ViewVM;
import au.com.myphysioapp.myphysio.adapter.OnItemClickListener;
import au.com.myphysioapp.myphysio.adapter.RVBindingAdapter;
import au.com.myphysioapp.myphysio.model.Hobby;

/**
 * Created by Wooden on 06.03.2017.
 */

public class ItemHobbyVM extends ViewVM {
    public final Hobby hobby;
    public final Drawable picture;
    public final String pictureTitle;
    public final ObservableBoolean isChecked = new ObservableBoolean(false);


    public ItemHobbyVM(Context context, Hobby hobby, Drawable picture) {
        super(context);
        this.hobby = hobby;
        this.picture = picture;
        this.pictureTitle = hobby.toString();
    }

    public ItemHobbyVM(Context context, Hobby hobby, Drawable picture, boolean checked) {
        this(context, hobby, picture);
        isChecked.set(checked);
    }

    public void onClickItem(View v){
        isChecked.set(!isChecked.get());
    }

    public static List<ItemHobbyVM> defaultHobbies(Context c){
        List<ItemHobbyVM> result = new ArrayList<>(4);
        result.add(new ItemHobbyVM(c, Hobby.ELITE_SPORTS,
                ContextCompat.getDrawable(c, R.drawable.hobby_sport)));

        result.add(new ItemHobbyVM(c, Hobby.RECREATIONAL_SPORTS,
                ContextCompat.getDrawable(c, R.drawable.hobby_education)));

        result.add(new ItemHobbyVM(c, Hobby.NUTRITION,
                ContextCompat.getDrawable(c, R.drawable.hobby_health)));

        result.add(new ItemHobbyVM(c, Hobby.GYM_TRAINING_AND_FITNESS,
                ContextCompat.getDrawable(c, R.drawable.hobby_sport)));

        return result;
    }
}
