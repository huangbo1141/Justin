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
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import au.com.myphysioapp.myphysio.BR;
import au.com.myphysioapp.myphysio.FragmentVM;
import au.com.myphysioapp.myphysio.R;
import au.com.myphysioapp.myphysio.adapter.OnItemClickListener;
import au.com.myphysioapp.myphysio.adapter.RVBindingAdapter;
import au.com.myphysioapp.myphysio.databinding.FrHomeBinding;
import au.com.myphysioapp.myphysio.model.Exercise;
import au.com.myphysioapp.myphysio.model.Global;
import au.com.myphysioapp.myphysio.model.ProgramModel;
import au.com.myphysioapp.myphysio.netutil.CommonAsyncTask;
import au.com.myphysioapp.myphysio.netutil.WebServiceClient;
import au.com.myphysioapp.myphysio.ui.notifications.MessagingAVM;
import au.com.myphysioapp.myphysio.ui.statistics.StatisticsAVM;
import au.com.myphysioapp.myphysio.ui.user_programs.ExerciseInstructionAVM;
import au.com.myphysioapp.myphysio.ui.user_programs.ProgramContentAVM;
import au.com.myphysioapp.myphysio.ui.user_programs.ProgramItemVM;
import au.com.myphysioapp.myphysio.widget.MarginDecoration;
import au.com.myphysioapp.myphysio.widget.RVConf;

/**
 * Created by Wooden on 14.02.2017.
 */

public class HomeFVM extends FragmentVM implements
        OnItemClickListener<ExerciseListItemVM> {
    public final RVConf listConf = new RVConf();
    public final ObservableBoolean scrolledToEnd = new ObservableBoolean(false);

    private CommonAsyncTask mAsyncTask;
    private JSONObject objRes;

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

    FrHomeBinding binding;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FrHomeBinding.inflate(inflater);
        getActivity().setTitle(R.string.home);
        List<Integer> list = Arrays.asList(1, 2, 11, 3);
        binding.setVm(this);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAsyncTask = new CommonAsyncTask(getActivity(), true, new CommonAsyncTask.asyncTaskListener() {
            @Override
            public Boolean onTaskExecuting() {
                try{
                    Map<String,Object> map = new HashMap<>();
                    map.put("authorization_header","authorization_header");

                    WebServiceClient wsClient = new WebServiceClient(getActivity());
                    objRes = new JSONObject(wsClient.sendDataToServer(Global.GET_PROGRAM_API_URL, null,map));
                    if( objRes == null )    return false;
                }catch(Exception e){
                    return false;
                }
                return true;
            }

            @Override
            public void onTaskFinish(Boolean result) {
                if (result == true) {
                    try {
                        if( objRes.getBoolean("success")  ){
                            ArrayList<ProgramModel> list = ProgramModel.parseProgramFromResponse(objRes);
                            if (list.size()>0){
                                List<ProgramItemVM> vmList = new ArrayList<>();
                                for (int i=0; i<list.size(); i++){
                                    ProgramItemVM itemVM = new ProgramItemVM(getActivity(),list.get(i));
                                    vmList.add(itemVM);
                                }

                                //RV tuning
                                RVBindingAdapter<ProgramItemVM> adapter =
                                        new RVBindingAdapter<>(R.layout.item_program, BR.vm, vmList);

                                adapter.setOnItemClickListener(listener);

                                listConf.setAdapter(adapter);
                                listConf.setItemDecoration(new MarginDecoration(getContext()));

                                listConf.setLayoutManager(new LinearLayoutManager(getContext(),
                                        LinearLayoutManager.HORIZONTAL, false));
                                listConf.setOnScrollListener(scrollListener);

                                binding.setVm(HomeFVM.this);
                            }

                        }else{
                            Toast.makeText(getActivity(), "Invalid Account", Toast.LENGTH_LONG).show();
                            return;
                        }
                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), "JSON Parse Error!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(getActivity(), "Server Connection Timeout!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mAsyncTask.execute();
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

    OnItemClickListener<ProgramItemVM> listener = new OnItemClickListener<ProgramItemVM>() {
        @Override
        public void onItemClick(int position, ProgramItemVM item) {
            Intent i = new Intent(getActivity(), ProgramContentAVM.class);
            i.putExtra("programModel",item.model);
            startActivity(i);
        }
    } ;
}
