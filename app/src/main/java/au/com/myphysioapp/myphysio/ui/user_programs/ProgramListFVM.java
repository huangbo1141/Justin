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
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import au.com.myphysioapp.myphysio.BR;
import au.com.myphysioapp.myphysio.FragmentVM;
import au.com.myphysioapp.myphysio.R;
import au.com.myphysioapp.myphysio.adapter.OnItemClickListener;
import au.com.myphysioapp.myphysio.adapter.RVBindingAdapter;
import au.com.myphysioapp.myphysio.databinding.FrMyProgramsBinding;
import au.com.myphysioapp.myphysio.model.Global;
import au.com.myphysioapp.myphysio.model.ProgramModel;
import au.com.myphysioapp.myphysio.netutil.CommonAsyncTask;
import au.com.myphysioapp.myphysio.netutil.WebServiceClient;
import au.com.myphysioapp.myphysio.ui.home.HomeFVM;
import au.com.myphysioapp.myphysio.widget.MarginDecoration;
import au.com.myphysioapp.myphysio.widget.RVConf;

/**
 * Created by Wooden on 10.02.2017.
 */

public class ProgramListFVM extends FragmentVM {
    public final RVConf listConf = new RVConf();
    FrMyProgramsBinding binding;
    private OnItemClickListener<ProgramItemVM> onItemClick = new OnItemClickListener<ProgramItemVM>() {
        @Override
        public void onItemClick(int position, ProgramItemVM item) {
            Intent i = new Intent(getActivity(), ProgramContentAVM.class);
            i.putExtra("programModel",item.model);
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
        binding = FrMyProgramsBinding.inflate(inflater);
        getActivity().setTitle(R.string.my_programs);

        //Filling with test data
//        Drawable picture = ContextCompat.getDrawable(getContext(), R.drawable.exercise_preview);
//        ProgramItemVM itemVM = new ProgramItemVM(getContext(), picture, "Program 1", "Sweet program");
//        RVBindingAdapter<ProgramItemVM> programListAdapter =
//                new RVBindingAdapter<>(R.layout.item_program,
//                                             BR.vm,
//                                             Collections.nCopies(4, itemVM));
//
//        programListAdapter.setOnItemClickListener(onItemClick);
//
//        listConf.setAdapter(programListAdapter);
//        listConf.setItemDecoration(new MarginDecoration(getContext()));
//        listConf.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.setVm(this);
        return binding.getRoot();
    }
    private CommonAsyncTask mAsyncTask;
    private JSONObject objRes;
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

                                adapter.setOnItemClickListener(onItemClick);

                                listConf.setAdapter(adapter);
                                listConf.setItemDecoration(new MarginDecoration(getContext()));
                                listConf.setLayoutManager(new LinearLayoutManager(getContext()));

                                binding.setVm(ProgramListFVM.this);
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);
    }
}
