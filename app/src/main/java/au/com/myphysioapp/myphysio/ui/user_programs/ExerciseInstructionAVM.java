package au.com.myphysioapp.myphysio.ui.user_programs;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import au.com.myphysioapp.myphysio.R;
import au.com.myphysioapp.myphysio.adapter.WidgetPagerAdapter;
import au.com.myphysioapp.myphysio.databinding.ActivityExerciseStepsBinding;

public class ExerciseInstructionAVM extends ExerciseBasicAVM {
    private String[] steps;
    public final ObservableInt stepNumber = new ObservableInt(0);
    private WidgetPagerAdapter<TextView, String> instructionAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityExerciseStepsBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_exercise_steps);

        steps = new String[4];
        steps[0] = "Begin on your hands and knees with your hands underneath " +
                "your shoulders but slightly wider than your shoulders.";

        steps[1] = "Come onto the balls of your feet and the heels of your hands, " +
                "and then walk the feet back until you're in the plank position. " +
                "Keep your hips lifted to avoid the lower back bowing so the " +
                "belly sags towards the ground.";

        steps[2] = "Begin to bend your elbows, lowering your body in " +
                "one solid piece down towards the floor. " +
                "Your elbows will bend out to the side, not behind you. " +
                "Keep your abdominal and leg muscles engaged throughout the entire movement. " +
                "Your head should stay in line with your spine; not droop.";

        steps[3] = "Lower yourself down until your chest is about an inch or two " +
                "from the ground and then slowly push yourself back up to the starting position. " +
                "Push through the heels of your hands in order to return to the starting position.";

        instructionAdapter = new WidgetPagerAdapter<TextView, String>(R.layout.item_exercise_step,
                        numberSteps(steps)) {

            @Override
            public void bindData(TextView view, String item) {
                view.setText(item);
            }
        };

        binding.setVm(this);
    }

    private String[] numberSteps(String... steps){
        String[] result = new String[steps.length];
        for (int i = 0; i < steps.length; i++)
            result[i] = String.format(getString(R.string.exercise_steps_formatter), i + 1, steps[i]);

        return result;
    }

    public void onClickPreviousStep(View v){
        if (stepNumber.get() > 0)
            stepNumber.set(stepNumber.get() - 1);
    }

    public void onClickNextStep(View v){
        if (stepNumber.get() < steps.length)
            stepNumber.set(stepNumber.get() + 1);
    }

    public WidgetPagerAdapter<TextView, String> getInstructionAdapter() {
        return instructionAdapter;
    }

}
