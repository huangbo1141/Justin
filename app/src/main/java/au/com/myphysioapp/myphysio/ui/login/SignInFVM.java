package au.com.myphysioapp.myphysio.ui.login;

import android.content.Intent;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import au.com.myphysioapp.myphysio.FragmentVM;
import au.com.myphysioapp.myphysio.databinding.FrSignInBinding;
import au.com.myphysioapp.myphysio.model.Global;
import au.com.myphysioapp.myphysio.model.UserModel;
import au.com.myphysioapp.myphysio.netutil.CommonAsyncTask;
import au.com.myphysioapp.myphysio.netutil.WebServiceClient;
import au.com.myphysioapp.myphysio.ui.BottomBarActivity;
import au.com.myphysioapp.myphysio.widget.OnClickSocialButtonListener;

/**
 * Created by Wooden on 07.02.2017.
 */

public class SignInFVM extends FragmentVM implements OnClickSocialButtonListener {
    public final ObservableField<String> email = new ObservableField<String>();
    public final ObservableField<String> password = new ObservableField<String>();

    private CommonAsyncTask mAsyncTask = null;
    private JSONArray objResArray;
    private JSONObject objRes;
    private String strRes;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FrSignInBinding binding = FrSignInBinding.inflate(inflater);
        this.email.set("triton_client@gmail.com");
        this.password.set("12345");
        binding.setVm(this);
        return binding.getRoot();
    }

    public void onClickSignIn(View view) {
        final String szEmail = email.get();
        final String szPass = password.get();
        if( szEmail == null || szEmail.length() == 0 ){
            Toast.makeText(getActivity(), "Please Input Email", Toast.LENGTH_SHORT).show();
            return;
        }

        if( szPass == null || szPass.toString().length() == 0 ){
            Toast.makeText(getActivity(), "Please Input Password", Toast.LENGTH_SHORT).show();
            return;
        }

        mAsyncTask = new CommonAsyncTask(getActivity(), true, new CommonAsyncTask.asyncTaskListener() {
            @Override
            public Boolean onTaskExecuting() {
                try{
                    JSONObject objParams = new JSONObject();
                    objParams.put("username", szEmail);
                    objParams.put("password", szPass);

                    ArrayList<NameValuePair> postParameters = null;
                    postParameters = new ArrayList<NameValuePair>();
                    postParameters.add(new BasicNameValuePair("username", szEmail));
                    postParameters.add(new BasicNameValuePair("password", szPass));
                    WebServiceClient wsClient = new WebServiceClient(getActivity());
                    objRes = new JSONObject(wsClient.sendDataToServer(Global.LOGIN_API_URL, objParams));
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
                            JSONObject objUser = objRes.getJSONObject("user_data");
                            Global.g_loginedUser = new UserModel();

                            boolean bFirstname = objUser.isNull("first_name");

                            Global.g_loginedUser.user_id = Global.optString(objUser, "id");
                            Global.g_loginedUser.email = Global.optString(objUser,"email");
                            Global.g_loginedUser.username = Global.optString(objUser,"username");
                            Global.g_loginedUser.password = Global.optString(objUser,"password");
                            Global.g_loginedUser.api_key = Global.optString(objUser,"api_key");
                            Global.g_loginedUser.avatar = Global.optString(objUser,"avatar");
                            Global.g_loginedUser.fullname = Global.optString(objUser,"fullname");
                            Global.g_loginedUser.first_name = Global.optString(objUser,"first_name");
                            Global.g_loginedUser.last_name = Global.optString(objUser,"last_name");
                            Global.g_loginedUser.created_on = Global.optString(objUser,"created_on");
                            Global.g_loginedUser.modified_on = Global.optString(objUser,"modified_on");
                            Global.g_loginedUser.percent = Global.optString(objUser,"percent");
                            Global.g_loginedUser.company_id = Global.optString(objUser,"company_id");
                            Global.g_loginedUser.role_id = Global.optString(objUser,"role_id");
                            Global.g_loginedUser.invited = Global.optString(objUser,"invited");
                            Global.g_loginedUser.active = Global.optString(objUser,"active");
//                            Global.g_loginedUser.hash_active = Global.optString(objUser,"hash_active");
//                            Global.g_loginedUser.hash_forgetpassword = Global.optString(objUser,"hash_forgetpassword");
//                            Global.g_loginedUser.is_deleted = Global.optString(objUser,"is_deleted");
                            Global.g_loginedUser.tokbox_session_id = Global.optString(objUser,"tokbox_session_id");
//                            Global.g_loginedUser.active_program = Global.optString(objUser,"active_program");
//                            Global.g_loginedUser.phone = Global.optString(objUser,"phone");
//                            Global.g_loginedUser.cliniko_id = Global.optString(objUser,"cliniko_id");
                            Global.g_loginedUser.company_tokbox_session_id = Global.optString(objUser,"company_tokbox_session_id");
                            Global.g_loginedUser.tokbox_api_key = Global.optString(objUser,"tokbox_api_key");

                            Intent i = new Intent(getActivity(), BottomBarActivity.class);
                            startActivity(i);
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

    public void onClickForgotPassword(View view) {
    }

    @Override
    public void onTwitterEntry(View view) {
    }

    @Override
    public void onFacebookEntry(View view) {
    }

    @Override
    public void onGooglePlusEntry(View view) {
    }
}
