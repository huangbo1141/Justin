package au.com.myphysioapp.myphysio.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 8/8/2017.
 */

public class ProgramModel implements Serializable{
    public String program_id;
    public String fullname;
    public String painscale;
    public String rate;
    public String percent;
    public String notes;
    public String interval;
    public String intervaltype;
    public String interval_duration;
    public String interval_start_date;
    public String image;

    public ArrayList<ExerciseModel> exerciseList = new ArrayList<>();

    public static ProgramModel parseModel(JSONObject jsonObject){
        ProgramModel model = new ProgramModel();
        try{
            model.program_id = jsonObject.getString("id");
            model.fullname = jsonObject.getString("fullname");
            model.painscale = jsonObject.getString("painscale");
            model.rate = jsonObject.getString("rate");
            model.percent = jsonObject.getString("percent");
            model.notes = jsonObject.getString("notes");
            model.interval = jsonObject.getString("interval");
            model.intervaltype = jsonObject.getString("intervaltype");
            model.interval_duration = jsonObject.getString("interval_duration");
            model.interval_start_date = jsonObject.getString("interval_start_date");
            model.image = jsonObject.getString("image");

            if (jsonObject.has("exerciseList")){
                model.exerciseList = ExerciseModel.parseArray(jsonObject.getJSONArray("exerciseList"));
            }
        }catch (Exception ex){

        }
        return model;
    }
    public static ArrayList<ProgramModel> parseProgramFromResponse(JSONObject jsonObject){
        ArrayList<ProgramModel> list = new ArrayList<>();
        try{
            if (jsonObject.getBoolean("success")){
                JSONArray jsonArray = jsonObject.getJSONArray("list_program");
                for (int i=0; i<jsonArray.length(); i++){
                    JSONObject iobj = jsonArray.getJSONObject(i);
                    ProgramModel model = ProgramModel.parseModel(iobj);
                    list.add(model);
                }
            }
        }catch (Exception e){

        }

        return list;
    }
}
