package au.com.myphysioapp.myphysio.ui.user_programs;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import au.com.myphysioapp.myphysio.model.Exercise;

public class ExerciseBasicAVM extends AppCompatActivity {
    public final ObservableInt timeElapsed = new ObservableInt(41);
    public final ObservableField<String> timeRemaining = new ObservableField<>(String.valueOf(59));
    public final ObservableField<String> exerSets = new ObservableField<>(String.valueOf(2));
    public final ObservableField<String> exerSide =
            new ObservableField<>(Exercise.Side.LEFT.toString());
    private int exerciseDuration = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public int getExerciseDuration(){
        return exerciseDuration;
    }

    public void setExerciseDuration(int a){
        exerciseDuration = a;
    }
}
