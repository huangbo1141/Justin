package au.com.myphysioapp.myphysio.widget;
import android.content.Context;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.StyleRes;
import android.support.v4.widget.TextViewCompat;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by Wooden on 16.02.2017.
 */

public class ValueLabelsSeekBar {

    int maxCount, textAppearance, spacing = 10;
    SeekBar.OnSeekBarChangeListener changeListener;
    Context mContext;
    LinearLayout mSeekLin;
    SeekBar mSeekBar;

    public ValueLabelsSeekBar(Context context, int maxCount, @StyleRes int textAppearance) {
        this.mContext = context;
        this.maxCount = maxCount;
        this.textAppearance = textAppearance;
    }

    /*
     * Sets space between a line of values and a seekbar
     * Call it before addSeekBarMethod
     */
    public void setSeekBarSpace(int space){
        this.spacing = space;
    }

    public void setOnSeekBarChangeListener(SeekBar.OnSeekBarChangeListener listener){
        this.changeListener = listener;
    }

    public void addSeekBar(LinearLayout parent) {
        if (parent != null) {

            parent.setOrientation(LinearLayout.VERTICAL);
            mSeekBar = new SeekBar(mContext);
            mSeekBar.setMax(maxCount);
            mSeekBar.setOnSeekBarChangeListener(changeListener);

            // Add LinearLayout for labels above SeekBar
            mSeekLin = new LinearLayout(mContext);
            mSeekLin.setOrientation(LinearLayout.HORIZONTAL);
            mSeekLin.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(35, 0, 35, this.spacing);
            mSeekLin.setLayoutParams(params);

            addSeekBarLabels();
            parent.addView(mSeekLin);
            parent.addView(mSeekBar);

        } else {

            Log.e("ValueLabelsSeekBar", " Parent is not a LinearLayout");

        }

    }

    private void addSeekBarLabels() {
        for (int count = 0; count <= maxCount; count++) {
            TextView textView = new TextView(mContext);
            textView.setText(String.valueOf(count));
            if (this.textAppearance > 0)
                TextViewCompat.setTextAppearance(textView, textAppearance);
            textView.setGravity(Gravity.START);
            mSeekLin.addView(textView);
            textView.setLayoutParams((count == maxCount) ? getLayoutParams(0.0f) : getLayoutParams(1.0f));
        }
    }

    LinearLayout.LayoutParams getLayoutParams(float weight) {
        return new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, weight);
    }

}
