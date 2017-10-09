package au.com.myphysioapp.myphysio.model;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by Wooden on 11.02.2017.
 */

public class Exercise implements Serializable{
    public enum Side {
        LEFT("L"), RIGHT("R"), BOTH("L|R");

        private String stringValue;

        Side(String stringValue) {
            this.stringValue = stringValue;
        }

        @Override
        public String toString() {
            return stringValue;
        }
    }

    private String name;
    private String description;
    private int duration;
    public String time;
    private int sets;
    private Side side;
    private Bitmap exercisePreview;
    public String photo;
    public String video;

    public Exercise(){}

    public Exercise(String name, String description, int duration, int sets, Side side, Bitmap exercisePreview, String exerciseURI) {
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.sets = sets;
        this.side = side;
        this.exercisePreview = exercisePreview;
        this.exerciseURI = exerciseURI;
    }

    private String exerciseURI;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }
    public String getDurationString() {
        if (duration == 0){
            return "~";
        }else{
            return String.valueOf(duration);
        }
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public Side getSide() {
        return side;
    }

    public void setSide(Side side) {
        this.side = side;
    }

    public Bitmap getExercisePreview() {
        return exercisePreview;
    }

    public void setExercisePreview(Bitmap exercisePreview) {
        this.exercisePreview = exercisePreview;
    }

    public String getExerciseURI() {
        return exerciseURI;
    }

    public void setExerciseURI(String exerciseURI) {
        this.exerciseURI = exerciseURI;
    }
}
