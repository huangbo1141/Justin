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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import au.com.myphysioapp.myphysio.R;
import au.com.myphysioapp.myphysio.adapter.OnItemClickListener;
import au.com.myphysioapp.myphysio.adapter.WidgetPagerAdapter;
import au.com.myphysioapp.myphysio.databinding.ActivityProgramContentBinding;
import au.com.myphysioapp.myphysio.model.Exercise;

public class ProgramContentAVM extends AppCompatActivity implements OnItemClickListener<Bitmap> {

    private WidgetPagerAdapter<ImageView, Bitmap> videoAdapter;
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
                    exerDuration.set(String.valueOf(exercises.get(position).getDuration()));
                    exerSets.set(String.valueOf(exercises.get(position).getSets()));
                    exerSide.set(exercises.get(position).getSide().toString());
                }
            };

    @Override
    public void onItemClick(int position, Bitmap item) {
        Intent showExercise = new Intent(this, ExerciseVideoAVM.class);
        startActivity(showExercise);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityProgramContentBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_program_content);

        setTitle(programName);

        //Creating test data
        Exercise e1 = new Exercise();
        e1.setName("Exercise 1");
        e1.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Ut iaculis vulputate tellus, at convallis felis consequat vel. " +
                "Donec venenatis non ipsum in mollis.");
        e1.setDuration(180);
        e1.setSide(Exercise.Side.LEFT);
        e1.setExercisePreview(BitmapFactory.decodeResource(getResources(), R.drawable.exercise_preview));
        e1.setSets(2);

        Exercise e2 = new Exercise();
        e2.setName("Exercise 2");
        e2.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Ut iaculis vulputate tellus, at convallis felis consequat vel. " +
                "Donec venenatis non ipsum in mollis." +
                "Fusce placerat eros quis augue porttitor iaculis. Sed nec nunc velit. " +
                "Duis dictum erat sit amet porta scelerisque. " +
                "In tincidunt tellus quis lorem euismod, non sagittis magna imperdiet.");
        e2.setDuration(80);
        e2.setSide(Exercise.Side.RIGHT);
        e2.setExercisePreview(BitmapFactory.decodeResource(getResources(), R.drawable.exercise_preview));
        e2.setSets(10);

        Exercise e3 = new Exercise();
        e3.setName("Exercise 3");
        e3.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
        e3.setDuration(30);
        e3.setSide(Exercise.Side.RIGHT);
        e3.setExercisePreview(BitmapFactory.decodeResource(getResources(), R.drawable.exercise_preview));
        e3.setSets(5);

        exercises.addAll(Arrays.asList(e1, e2, e3));

        //Filling with test data
        Bitmap[] images = new Bitmap[]{e1.getExercisePreview(),
                e2.getExercisePreview(),
                e3.getExercisePreview()};

        videoAdapter =
                new WidgetPagerAdapter<ImageView, Bitmap>(R.layout.view_exercise_preview,
                        R.id.video_preview, images) {

                    //Configure width of each preview page
                    @Override
                    public float getPageWidth(int position) {
                        return 0.93f;
                    }

                    @Override
                    public void bindData(ImageView view, Bitmap item) {
                        view.setImageBitmap(item);
                    }
                };


        videoAdapter.setOnItemClickListener(this);
        //TODO: remove code with explicit accessing to views
        binding.exercisePreview.setAdapter(videoAdapter);

        //Fake listener invocation to fill views with initial data
        pageChangeListener.onPageSelected(binding.exercisePreview.getCurrentItem());
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
