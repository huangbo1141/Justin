package au.com.myphysioapp.myphysio.model;

import java.util.ArrayList;

/**
 * Created by Administrator on 8/8/2017.
 */

public class ExerciseModel {
    public String exercise_id;
    public String name;
    public String reps;
    public String time;
    public String bodyside;
    public String set;
    public String rest;

    public ArrayList<MediaModel> mediaList = new ArrayList<>();
}
