package au.com.myphysioapp.myphysio.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 8/8/2017.
 */

public class MediaModel implements Serializable{
    public String type;
    public String path;
    public static MediaModel parseModel(JSONObject jsonObject){
        MediaModel model = new MediaModel();
        try{
            model.type = jsonObject.getString("type");
            model.path = jsonObject.getString("path");

        }catch (Exception ex){

        }
        return model;
    }
    public static ArrayList<MediaModel> parseArray(JSONArray jsonArray){
        ArrayList<MediaModel> list = new ArrayList<>();
        try{
            for (int i=0; i<jsonArray.length(); i++){
                JSONObject iobj = jsonArray.getJSONObject(i);
                MediaModel model = MediaModel.parseModel(iobj);
                list.add(model);
            }
        }catch (Exception e){

        }

        return list;
    }
}
