package au.com.myphysioapp.myphysio.model;

/**
 * Created by Wooden on 06.03.2017.
 */

public enum Hobby {
    ELITE_SPORTS("Elite Sports"),
    RECREATIONAL_SPORTS("Recreational Sports"),
    NUTRITION("Nutrition"),
    GYM_TRAINING_AND_FITNESS("Gym Training & Fitness");

    private String label;

    Hobby(String label) {
        this.label = label;
    }


    @Override
    public String toString() {
        return label;
    }
}
