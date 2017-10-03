package au.com.myphysioapp.myphysio.ui.statistics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import au.com.myphysioapp.myphysio.BR;
import au.com.myphysioapp.myphysio.FragmentVM;
import au.com.myphysioapp.myphysio.R;
import au.com.myphysioapp.myphysio.adapter.OnItemClickListener;
import au.com.myphysioapp.myphysio.adapter.RVBindingAdapter;
import au.com.myphysioapp.myphysio.databinding.FrMyProgressBinding;
import au.com.myphysioapp.myphysio.model.ProgressItem;
import au.com.myphysioapp.myphysio.widget.MarginDecoration;
import au.com.myphysioapp.myphysio.widget.RVConf;

/**
 * Created by Wooden on 10.02.2017.
 */

public class ProgressListFVM extends FragmentVM {
    public final RVConf listConf = new RVConf();

    private OnItemClickListener<ProgressItemVM> onItemClick = new OnItemClickListener<ProgressItemVM>() {
        @Override
        public void onItemClick(int position, ProgressItemVM item) {
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FrMyProgressBinding binding = FrMyProgressBinding.inflate(inflater);

        RVBindingAdapter<ProgressItemVM> programListAdapter =
                new RVBindingAdapter<>(R.layout.item_progress,
                                             BR.vm,
                                             getListEntries());

        programListAdapter.setOnItemClickListener(onItemClick);

        listConf.setAdapter(programListAdapter);
        listConf.setItemDecoration(new MarginDecoration(getContext()));
        listConf.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.setVm(this);
        return binding.getRoot();
    }

    private List<ProgressItemVM> getListEntries(){
        //TODO: Change a way of getting data from the Activity to Parcelable
        final List<ProgressItem> data = ((StatisticsAVM)getActivity()).getProgressItems();
        if (data == null || data.size() == 0) return new ArrayList<>();

        List<ProgressItemVM> entries = new ArrayList<>(data.size());
        for (int i = data.size() - 1; i>=0; i--){
            entries.add(ProgressItemVM.wrapItem(getContext(), data.get(i)));
        }

        return  entries;
    }

}
