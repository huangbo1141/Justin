package au.com.myphysioapp.myphysio.model;

import android.graphics.BitmapFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import au.com.myphysioapp.myphysio.R;

/**
 * Created by Administrator on 8/8/2017.
 */

public class ExerciseModel implements Serializable{
    public String exercise_id;
    public String name;
    public String reps;
    public String time;
    public String bodyside;
    public String set;
    public String rest;

    public ArrayList<MediaModel> mediaList = new ArrayList<>();

    public Exercise getExercise(){
        Exercise e1 = new Exercise();
        e1.setName(name);
        e1.setDescription(name);
        try{
            e1.setDuration(Integer.parseInt(time));
        }catch (Exception ex){

        }
        e1.time = time;
        try{
            if (bodyside.equals("1")){

            }
        }catch (Exception ex){

        }

        e1.setSide(Exercise.Side.LEFT);
//        e1.setExercisePreview(BitmapFactory.decodeResource(getResources(), R.drawable.exercise_preview));
        try{
            e1.setSets(Integer.parseInt(set));
        }catch (Exception ex){

        }
        try{
            for (int i=0; i<mediaList.size(); i++){
                MediaModel model = mediaList.get(i);
                if (model.type.equals("photo")){
                    e1.photo = model.path;
                    break;
                }
            }
        }catch (Exception ex){

        }

        try{
            for (int i=0; i<mediaList.size(); i++){
                MediaModel model = mediaList.get(i);
                if (model.type.equals("video")){
                    e1.video = model.path;
                    break;
                }
            }
        }catch (Exception ex){

        }
        return e1;
    }

    public static ExerciseModel parseModel(JSONObject jsonObject){
        ExerciseModel model = new ExerciseModel();
        try{
            model.exercise_id = jsonObject.getString("id");
            model.name = jsonObject.getString("name");
            model.reps = jsonObject.getString("reps");
            model.time = jsonObject.getString("time");
            model.bodyside = jsonObject.getString("bodyside");
            model.set = jsonObject.getString("set");
            model.rest = jsonObject.getString("rest");

            if (jsonObject.has("mediaList")){
                model.mediaList = MediaModel.parseArray(jsonObject.getJSONArray("mediaList"));
            }
        }catch (Exception ex){

        }
        return model;
    }
    public static ArrayList<ExerciseModel> parseArray(JSONArray jsonArray){
        ArrayList<ExerciseModel> list = new ArrayList<>();
        try{
            for (int i=0; i<jsonArray.length(); i++){
                JSONObject iobj = jsonArray.getJSONObject(i);
                ExerciseModel model =  ExerciseModel.parseModel(iobj);
                list.add(model);
            }
        }catch (Exception e){

        }

        return list;
    }
}
