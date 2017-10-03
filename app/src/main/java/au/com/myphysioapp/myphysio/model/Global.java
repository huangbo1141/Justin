package au.com.myphysioapp.myphysio.model;

import org.json.JSONObject;

/**
 * Created by Administrator on 8/8/2017.
 */

public class Global {
    public static String BASE_API_URL = "http://mpa.jsoft.com.au/api";

    public static String LOGIN_API_URL = BASE_API_URL + "/user/login";
    public static String SIGNUP_API_URL = BASE_API_URL + "/user/register";
    public static String GET_PROGRAM_API_URL = BASE_API_URL + "/user/getClientProgramList";


    public static UserModel g_loginedUser;

    public static String optString(JSONObject obj, String param){
        if( obj.isNull(param) == true  )
            return null;
        return obj.optString(param);
    }
}
