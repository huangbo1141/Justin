package au.com.myphysioapp.myphysio.ui.login;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.EnumSet;

import au.com.myphysioapp.myphysio.BR;
import au.com.myphysioapp.myphysio.R;
import au.com.myphysioapp.myphysio.adapter.OnItemClickListener;
import au.com.myphysioapp.myphysio.adapter.RVBindingAdapter;
import au.com.myphysioapp.myphysio.databinding.ActivitySignUpWithEmailBinding;
import au.com.myphysioapp.myphysio.model.Global;
import au.com.myphysioapp.myphysio.model.Hobby;
import au.com.myphysioapp.myphysio.netutil.CommonAsyncTask;
import au.com.myphysioapp.myphysio.netutil.WebServiceClient;
import au.com.myphysioapp.myphysio.widget.MarginDecoration;
import au.com.myphysioapp.myphysio.widget.RVConf;

public class SignUpWithEmailAVM extends AppCompatActivity implements
        OnItemClickListener<ItemHobbyVM> {

    private CommonAsyncTask mAsyncTask = null;
    private JSONArray objResArray;
    private JSONObject objRes;
    private String strRes;
    public ObservableField<String> email = new ObservableField<>(),
            password = new ObservableField<>(),
            postCode = new ObservableField<>(),
            firstname = new ObservableField<>(),
            lastname = new ObservableField<>();

    //Hobbies part
    private EnumSet<Hobby> checkedHobbies = EnumSet.allOf(Hobby.class);
    public final RVConf listConf = new RVConf();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivitySignUpWithEmailBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_sign_up_with_email);

        RVBindingAdapter<ItemHobbyVM> hobbyAdapter;
        hobbyAdapter = new RVBindingAdapter<>(R.layout.item_hobby, BR.vm,
                ItemHobbyVM.defaultHobbies(this));

        hobbyAdapter.setOnItemClickListener(this);
        listConf.setAdapter(hobbyAdapter);
        listConf.setItemDecoration(new MarginDecoration(this));
        listConf.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));

        binding.setVm(this);
    }

    public void onClickSignUp(View view){
        final String str_fname = firstname.get();
        final String str_lname = lastname.get();
        final String str_email = email.get();
        final String str_password = password.get();
        final Activity activity = this;
        if (str_fname.isEmpty()){
            String msg = "Please Input Firstname";
            Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
            return;
        }
        if (str_lname.isEmpty()){
            String msg = "Please Input Lastname";
            Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
            return;
        }
        if (str_email.isEmpty()){
            String msg = "Please Input Email";
            Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
            return;
        }
        if (str_password.isEmpty()){
            String msg = "Please Input Password";
            Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
            return;
        }

        Log.d("xxx","ddd");

        mAsyncTask = new CommonAsyncTask(activity, true, new CommonAsyncTask.asyncTaskListener() {
            @Override
            public Boolean onTaskExecuting() {
                try{
                    ArrayList<NameValuePair> postParamsValue = new ArrayList<>();
                    // first_name, last_name, email,password
                    if (Global.isTest){
                        postParamsValue.add(new BasicNameValuePair("first_name", "aaa"));
                        postParamsValue.add(new BasicNameValuePair("last_name", "qqq"));
                        postParamsValue.add(new BasicNameValuePair("email", "bo_test1@gmail.com"));    // bo_test1@gmail.com bo_test2@gmail.com
                        postParamsValue.add(new BasicNameValuePair("password", "12345"));
                    }else{
                        postParamsValue.add(new BasicNameValuePair("first_name", str_fname));
                        postParamsValue.add(new BasicNameValuePair("last_name", str_lname));
                        postParamsValue.add(new BasicNameValuePair("email", str_email));
                        postParamsValue.add(new BasicNameValuePair("password", str_password));
                    }



                    WebServiceClient wsClient = new WebServiceClient(activity);
                    String ret = wsClient.sendDataToServerForm(Global.SIGNUP_API_URL, postParamsValue);
                    objRes = new JSONObject(ret);
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
//                            Intent i = new Intent(activity, BottomBarActivity.class);
//                            startActivity(i);
                            Toast.makeText(activity, "Register Success", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            String msg = objRes.getString("msg");
                            Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
                            return;
                        }
                    } catch (JSONException e) {
                        Toast.makeText(activity, "JSON Parse Error!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }else{

                    Toast.makeText(activity, "Server Connection Timeout!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mAsyncTask.execute();
    }

    public void onActivityLevelChecked(RadioGroup group, int checkedId){

    }

    public void onGenderChecked(RadioGroup group, int checkedId) {

    }

    //Click on hobby item callback
    @Override
    public void onItemClick(int position, ItemHobbyVM item) {
        //Inform VM about the click event
        item.onClickItem(null);
        checkedHobbies.add(item.hobby);
    }
}
