package au.com.myphysioapp.myphysio.model;

import java.util.ArrayList;

/**
 * Created by Administrator on 8/8/2017.
 */

public class ProgramModel {
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
}
