package au.com.myphysioapp.myphysio.ui;

import android.databinding.DataBindingUtil;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.roughike.bottombar.OnTabSelectListener;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import au.com.myphysioapp.myphysio.R;
import au.com.myphysioapp.myphysio.databinding.ActivityBottomBarBinding;
import au.com.myphysioapp.myphysio.model.Global;
import au.com.myphysioapp.myphysio.netutil.CommonAsyncTask;
import au.com.myphysioapp.myphysio.netutil.WebServiceClient;
import au.com.myphysioapp.myphysio.ui.chat.ChatAVM;
import au.com.myphysioapp.myphysio.ui.chat.OpenTokConfig;
import au.com.myphysioapp.myphysio.ui.contacts.ContactsFVM;
import au.com.myphysioapp.myphysio.ui.home.HomeFVM;
import au.com.myphysioapp.myphysio.ui.profile.ProfileFVM;
import au.com.myphysioapp.myphysio.ui.user_programs.ProgramListFVM;

public class BottomBarActivity extends BaseActivity implements OnTabSelectListener{
    private CommonAsyncTask mAsyncTask = null;
    private JSONArray objResArray;
    private JSONObject objRes;
    private String strRes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityBottomBarBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_bottom_bar);

        binding.setVm(this);

        mAsyncTask = new CommonAsyncTask(this, true, new CommonAsyncTask.asyncTaskListener() {
            @Override
            public Boolean onTaskExecuting() {
                try{
                    ArrayList<NameValuePair> objParams= new ArrayList<>();
                    objParams.add(new BasicNameValuePair("tokbox_session_id", Global.g_loginedUser.tokbox_session_id));
                    objParams.add(new BasicNameValuePair("company_tokbox_session_id", Global.g_loginedUser.company_tokbox_session_id));
                    WebServiceClient wsClient = new WebServiceClient(BottomBarActivity.this);
                    objRes = new JSONObject(wsClient.sendDataToServerForm(Global.GENERATE_TOKBOX_TOKEN, objParams));
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
                            String tokbox_client_token = objRes.getString("tokbox_client_token");
                            String tokbox_company_token = objRes.getString("tokbox_company_token");
                            Global.g_loginedUser.tokbox_client_token = tokbox_client_token;
                            Global.g_loginedUser.tokbox_company_token = tokbox_company_token;
                            OpenTokConfig.API_KEY = Global.g_loginedUser.tokbox_api_key;
                            OpenTokConfig.SESSION_ID = Global.g_loginedUser.tokbox_session_id;
                            OpenTokConfig.TOKEN = tokbox_client_token;
                        }else{
//                            Toast.makeText(ChatAVM.this, "Invalid Account", Toast.LENGTH_LONG).show();
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(BottomBarActivity.this, "Fail to Get Token", Toast.LENGTH_LONG).show();

                }
            }
        });
        mAsyncTask.execute();
    }

    @Override
    public int getFragmentArea() {
        return R.id.main_content;
    }

    @Override
    public void onTabSelected(@IdRes int tabId) {
        switch (tabId){
            case R.id.tab_home:
                changeFragment(new HomeFVM(), false);
                break;

            case R.id.tab_programs:
                changeFragment(new ProgramListFVM(), false);
                break;

            case R.id.tab_profile:
                changeFragment(new ProfileFVM(), false);
                break;

            case R.id.tab_myphysio:
                changeFragment(new ContactsFVM(), false);
                break;
        }
    }
}
