package au.com.myphysioapp.myphysio.ui.home;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.Locale;

import au.com.myphysioapp.myphysio.R;
import au.com.myphysioapp.myphysio.ViewVM;
import au.com.myphysioapp.myphysio.model.Exercise;

/**
 * Created by Wooden on 14.02.2017.
 */

public class ExerciseListItemVM extends ViewVM {
    private Exercise exercise;

    public ExerciseListItemVM(Context context, Exercise e) {
        super(context);
        exercise = e;
    }

    public Bitmap getPreview(){
        return exercise.getExercisePreview();
    }

    public String getName(){
        return exercise.getName();
    }

    public String getDuration(){
        return String.format(Locale.getDefault(),
                context.getString(R.string.seconds_formatter), exercise.getDuration());
    }

    public String getSetsCount(){
        return String.format(Locale.getDefault(),
                context.getString(R.string.sets_formatter), exercise.getSets());
    }
}
