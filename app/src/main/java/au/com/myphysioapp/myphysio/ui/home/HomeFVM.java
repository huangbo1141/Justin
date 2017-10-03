package au.com.myphysioapp.myphysio.ui.home;

import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import au.com.myphysioapp.myphysio.BR;
import au.com.myphysioapp.myphysio.FragmentVM;
import au.com.myphysioapp.myphysio.R;
import au.com.myphysioapp.myphysio.adapter.OnItemClickListener;
import au.com.myphysioapp.myphysio.adapter.RVBindingAdapter;
import au.com.myphysioapp.myphysio.databinding.FrHomeBinding;
import au.com.myphysioapp.myphysio.model.Exercise;
import au.com.myphysioapp.myphysio.ui.notifications.MessagingAVM;
import au.com.myphysioapp.myphysio.ui.statistics.StatisticsAVM;
import au.com.myphysioapp.myphysio.ui.user_programs.ExerciseInstructionAVM;
import au.com.myphysioapp.myphysio.widget.MarginDecoration;
import au.com.myphysioapp.myphysio.widget.RVConf;

/**
 * Created by Wooden on 14.02.2017.
 */

public class HomeFVM extends FragmentVM implements
        OnItemClickListener<ExerciseListItemVM> {
    public final RVConf listConf = new RVConf();
    public final ObservableBoolean scrolledToEnd = new ObservableBoolean(false);

    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        private int lastItem = -1;
        private int lastVisible;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (lastItem == -1)
                lastItem = listConf.getAdapter().getItemCount() - 1;

            lastVisible = ((LinearLayoutManager)recyclerView.getLayoutManager())
                    .findLastCompletelyVisibleItemPosition();

            if (lastItem == lastVisible)
                scrolledToEnd.set(true);
            else
                scrolledToEnd.set(false);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FrHomeBinding binding = FrHomeBinding.inflate(inflater);
        getActivity().setTitle(R.string.home);

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

        List<ExerciseListItemVM> vmList = new ArrayList<ExerciseListItemVM>(3);
        vmList.add(new ExerciseListItemVM(getContext(), e1));
        vmList.add(new ExerciseListItemVM(getContext(), e2));
        vmList.add(new ExerciseListItemVM(getContext(), e3));

        //RV tuning
        RVBindingAdapter<ExerciseListItemVM> adapter =
            new RVBindingAdapter<>(R.layout.item_rv_exercise, BR.vm, vmList);

        adapter.setOnItemClickListener(this);

        listConf.setAdapter(adapter);
        listConf.setItemDecoration(new MarginDecoration(getContext()));

        listConf.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        listConf.setOnScrollListener(scrollListener);

        List<Integer> list = Arrays.asList(1, 2, 11, 3);
        binding.setVm(this);
        return binding.getRoot();
    }

    @Override
    public void onItemClick(int position, ExerciseListItemVM item) {
        Intent showExercise = new Intent(getActivity(), ExerciseInstructionAVM.class);
        startActivity(showExercise);
    }

    public void onClickReminders(View view){

    }

    public void onClickNotifications(View view){
        Intent i = new Intent(getActivity(), MessagingAVM.class);
        startActivity(i);
    }


    public void onClickStatistics(View view){
        Intent i = new Intent(getActivity(), StatisticsAVM.class);
        startActivity(i);
    }

    public void onClickResource(View view){

    }
}
