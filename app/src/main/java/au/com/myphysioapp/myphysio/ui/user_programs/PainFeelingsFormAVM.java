package au.com.myphysioapp.myphysio.ui.user_programs;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import au.com.myphysioapp.myphysio.R;
import au.com.myphysioapp.myphysio.databinding.ActivityPainFeelingsFormBinding;
import au.com.myphysioapp.myphysio.widget.ValueLabelsSeekBar;

public class PainFeelingsFormAVM extends AppCompatActivity
        implements SeekBar.OnSeekBarChangeListener{

    final static int PAIN_LEVELS = 6;
    private int hurtLevel;

    private Drawable[] painPictures;

    private String[] painLevelDescriptions;
    public final ObservableField<Drawable> currentLevelPicture = new ObservableField<>();
    public final ObservableField<String> currentLevelDescription = new ObservableField<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityPainFeelingsFormBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_pain_feelings_form);

        //Get pain levels illustrations
        painPictures = new Drawable[]{
                ContextCompat.getDrawable(this, R.drawable.ic_pain_0),
                ContextCompat.getDrawable(this, R.drawable.ic_pain_1),
                ContextCompat.getDrawable(this, R.drawable.ic_pain_2),
                ContextCompat.getDrawable(this, R.drawable.ic_pain_3),
                ContextCompat.getDrawable(this, R.drawable.ic_pain_4),
                ContextCompat.getDrawable(this, R.drawable.ic_pain_5)
        };

        //Get descriptions from resources
        painLevelDescriptions = new String[]{
            getString(R.string.no_hurt),
            getString(R.string.hurts_little_bit),
            getString(R.string.hurts_little_more),
            getString(R.string.hurts_even_more),
            getString(R.string.hurts_whole_lot),
            getString(R.string.hurts_worst)
        };

        /*
         * Initialize custom seekbar with a value block
         * The seekbar requires a LinearLayout as a parent
         */
        ValueLabelsSeekBar seekBar = new ValueLabelsSeekBar(this, 5, R.style.Text_Common_Bold_Green);
        seekBar.setSeekBarSpace((int)getResources().getDimension(R.dimen.seekBar_valueLine_spacing));
        seekBar.setOnSeekBarChangeListener(this);
        seekBar.addSeekBar(binding.seekbarSection);

        setHurtLevel(0);

        binding.setVm(this);
    }

    public void setHurtLevel(int hurt){
        this.hurtLevel = hurt;
        currentLevelPicture.set(painPictures[hurtLevel]);
        currentLevelDescription.set(painLevelDescriptions[hurtLevel]);
    }

    public void onClickStart(View view){

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        setHurtLevel(i);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
