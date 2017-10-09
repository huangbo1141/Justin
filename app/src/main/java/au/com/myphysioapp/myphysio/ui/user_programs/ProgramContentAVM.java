package au.com.myphysioapp.myphysio.ui.user_programs;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import au.com.myphysioapp.myphysio.R;
import au.com.myphysioapp.myphysio.adapter.OnItemClickListener;
import au.com.myphysioapp.myphysio.adapter.WidgetPagerAdapter;
import au.com.myphysioapp.myphysio.databinding.ActivityProgramContentBinding;
import au.com.myphysioapp.myphysio.model.Exercise;
import au.com.myphysioapp.myphysio.model.ExerciseModel;
import au.com.myphysioapp.myphysio.model.ProgramModel;

public class ProgramContentAVM extends AppCompatActivity implements OnItemClickListener<Exercise> {

    private WidgetPagerAdapter<ImageView, Exercise> videoAdapter;
    private String programName = "Program 1";
    private List<Exercise> exercises = new ArrayList<>();

    public final ObservableField<String> exerDescription = new ObservableField<>(""),
            exerDuration = new ObservableField<>(""),
            exerSets = new ObservableField<>(""),
            exerSide = new ObservableField<>("");


    public final ViewPager.OnPageChangeListener pageChangeListener =
            new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    exerDescription.set(exercises.get(position).getDescription());
                    exerDuration.set(exercises.get(position).getDurationString());
                    exerSets.set(String.valueOf(exercises.get(position).getSets()));
                    exerSide.set(exercises.get(position).getSide().toString());
                }
            };

    @Override
    public void onItemClick(int position, Exercise exercise) {
        Intent showExercise = new Intent(this, ExerciseVideoAVM.class);
        showExercise.putExtra("exercise",exercise);
        startActivity(showExercise);
    }

    ProgramModel programModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityProgramContentBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_program_content);

        setTitle(programName);

        Intent intent = getIntent();

        try{
            programModel = (ProgramModel) intent.getSerializableExtra("programModel");
            for (int i=0; i<programModel.exerciseList.size(); i++) {
                ExerciseModel exerciseModel = programModel.exerciseList.get(i);
                Exercise exercise = exerciseModel.getExercise();
                exercises.add(exercise);
            }

            videoAdapter =
                    new WidgetPagerAdapter<ImageView, Exercise>(R.layout.view_exercise_preview,
                            R.id.video_preview, exercises) {

                        //Configure width of each preview page
                        @Override
                        public float getPageWidth(int position) {
                            return 0.93f;
                        }

                        @Override
                        public void bindData(ImageView view, Exercise exercise) {
                            Glide.with(view.getContext()).load(exercise.photo).into(view);
//                            view.setImageBitmap(item);
                        }
                    };


            videoAdapter.setOnItemClickListener(this);
            //TODO: remove code with explicit accessing to views
            binding.exercisePreview.setAdapter(videoAdapter);

            //Fake listener invocation to fill views with initial data
            pageChangeListener.onPageSelected(binding.exercisePreview.getCurrentItem());
        }catch (Exception ex){

        }

        binding.setVm(this);
    }

    public PagerAdapter getVideoAdapter() {
        return videoAdapter;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.remind_menu, menu);
        return true;
    }
}
