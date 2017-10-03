package au.com.myphysioapp.myphysio.ui.user_programs;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;

import au.com.myphysioapp.myphysio.BR;
import au.com.myphysioapp.myphysio.FragmentVM;
import au.com.myphysioapp.myphysio.R;
import au.com.myphysioapp.myphysio.adapter.OnItemClickListener;
import au.com.myphysioapp.myphysio.adapter.RVBindingAdapter;
import au.com.myphysioapp.myphysio.databinding.FrMyProgramsBinding;
import au.com.myphysioapp.myphysio.widget.MarginDecoration;
import au.com.myphysioapp.myphysio.widget.RVConf;

/**
 * Created by Wooden on 10.02.2017.
 */

public class ProgramListFVM extends FragmentVM {
    public final RVConf listConf = new RVConf();

    private OnItemClickListener<ProgramItemVM> onItemClick = new OnItemClickListener<ProgramItemVM>() {
        @Override
        public void onItemClick(int position, ProgramItemVM item) {
            Intent i = new Intent(getActivity(), ProgramContentAVM.class);
            startActivity(i);
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FrMyProgramsBinding binding = FrMyProgramsBinding.inflate(inflater);
        getActivity().setTitle(R.string.my_programs);

        //Filling with test data
        Drawable picture = ContextCompat.getDrawable(getContext(), R.drawable.exercise_preview);
        ProgramItemVM itemVM = new ProgramItemVM(getContext(), picture, "Program 1", "Sweet program");
        RVBindingAdapter<ProgramItemVM> programListAdapter =
                new RVBindingAdapter<>(R.layout.item_program,
                                             BR.vm,
                                             Collections.nCopies(4, itemVM));

        programListAdapter.setOnItemClickListener(onItemClick);

        listConf.setAdapter(programListAdapter);
        listConf.setItemDecoration(new MarginDecoration(getContext()));
        listConf.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.setVm(this);
        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);
    }
}
