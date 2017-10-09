package au.com.myphysioapp.myphysio.model;

import org.json.JSONObject;

/**
 * Created by Administrator on 8/8/2017.
 */

public class Global {

    public static String LOGIN_API_URL          = "/api/user/login";
    public static String SIGNUP_API_URL         = "/api/user/register";
    public static String GET_PROGRAM_API_URL    = "/api/user/getClientProgramList";
    public static String GENERATE_TOKBOX_TOKEN    = "/api/user/generateTokboxToken";
    public static boolean isTest = false;
    public static UserModel g_loginedUser;

    public static String optString(JSONObject obj, String param){
        if( obj.isNull(param) == true  )
            return null;
        return obj.optString(param);
    }
}
