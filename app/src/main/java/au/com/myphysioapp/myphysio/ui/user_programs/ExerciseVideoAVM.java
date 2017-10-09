package au.com.myphysioapp.myphysio.ui.user_programs;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import au.com.myphysioapp.myphysio.R;
import au.com.myphysioapp.myphysio.databinding.ActivityExerciseVideoBinding;
import au.com.myphysioapp.myphysio.model.Exercise;

public class ExerciseVideoAVM extends ExerciseBasicAVM {

    Exercise exercise;
    ActivityExerciseVideoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =
                DataBindingUtil.setContentView(this, R.layout.activity_exercise_video);

        try{
            Intent intent = getIntent();
            exercise = (Exercise) intent.getSerializableExtra("exercise");
            timeElapsed.set(exercise.getDuration());
            timeRemaining.set(String.valueOf(exercise.getDuration()));
            exerSets.set(String.valueOf(exercise.getSets()));
            exerSide.set(Exercise.Side.LEFT.toString());
            setExerciseDuration(exercise.getDuration());
        }catch (Exception ex){

        }


        binding.setVm(this);
        try{
            binding.exerciseView.setVideoPath(exercise.video);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        playVideo();
    }

    public void StartButton(View v) {
        playVideo();
    }

    public void StopButton(View v) {
        stopVideo();
    }
    private void playVideo() {
        binding.exerciseView.seekTo(0);
        binding.exerciseView.start();
    }
    private void stopVideo() {
        binding.exerciseView.pause();
    }
}
