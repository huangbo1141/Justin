package au.com.myphysioapp.myphysio.model;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by Wooden on 17.02.2017.
 */

public class ProgressItem {
    private static Random random = new Random(47);

    public enum Location{AT_HOME, AT_WORK, OTHER}

    private String name;
    private int currentProgress;
    private int upperBound;
    private Date date;


    public ProgressItem(String name, Date date, int upperBound, int currentProgress) {
        this.name = name;
        this.date = date;
        this.upperBound = upperBound;
        this.currentProgress = currentProgress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCurrentProgress() {
        return currentProgress;
    }

    public int getUpperBound() {
        return upperBound;
    }

    public Date getDate() {
        return date;
    }

    public int getLocationPercentage(Location location){
        return (int)(random.nextFloat() * 100);
    }

    public int getPainPercentage(PainLevel pain){
        return (int)(random.nextFloat() * 100);
    }
}
