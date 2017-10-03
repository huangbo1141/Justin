package au.com.myphysioapp.myphysio.ui.user_programs;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import au.com.myphysioapp.myphysio.R;
import au.com.myphysioapp.myphysio.databinding.ActivityExerciseVideoBinding;

public class ExerciseVideoAVM extends ExerciseBasicAVM {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityExerciseVideoBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_exercise_video);
        binding.setVm(this);

    }
}
