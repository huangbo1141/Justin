package au.com.myphysioapp.myphysio.ui.statistics;

import android.content.Intent;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import au.com.myphysioapp.myphysio.FragmentVM;
import au.com.myphysioapp.myphysio.R;
import au.com.myphysioapp.myphysio.databinding.FrProgressChartBinding;
import au.com.myphysioapp.myphysio.model.PainLevel;
import au.com.myphysioapp.myphysio.model.ProgressItem;
import au.com.myphysioapp.myphysio.ui.chat.ChatAVM;

/**
 * Created by Wooden on 17.02.2017.
 */

public class ProgressChartFVM extends FragmentVM
        implements IValueFormatter, IAxisValueFormatter, OnChartValueSelectedListener{

    private static final float VISIBLE_BARS_NUMBER = 8;

    //Used to avoid a bar label overflowing
    private static final float MAX_Y_COEFFICIENT = 5;

    private FrProgressChartBinding binding;
    private BarChart chart;

    public final ObservableInt currentProgressValue = new ObservableInt(5);
    public final ObservableInt maxProgressValue = new ObservableInt(10);
    public final ObservableField<Date> progressDate = new ObservableField<>(new Date());
    public final ObservableField<String> programName = new ObservableField<>("Magic program");

    //Location
    public final ObservableInt atHomePercentage = new ObservableInt(25);
    public final ObservableInt atWorkPercentage = new ObservableInt(35);
    public final ObservableInt otherPercentage = new ObservableInt(40);

    //Pain feelings
    public final ObservableInt noHurtPercentage = new ObservableInt(25);
    public final ObservableInt littleBitPercentage = new ObservableInt(35);
    public final ObservableInt littleMorePercentage = new ObservableInt(40);

    //Used to format X-asix value
    private Calendar dateFormatter = Calendar.getInstance();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FrProgressChartBinding.inflate(inflater);

        chart = binding.progressChart;

        final float textSize = getResources().getDimension(R.dimen.chart_text_size) /
                getResources().getDisplayMetrics().density;

        final float xLabelMarginTop = getResources().getDimension(R.dimen.x_axis_label_marginTop) /
                getResources().getDisplayMetrics().density;

        BarDataSet dataSet = new BarDataSet(getChartEntries(), "Complete exercises");
        dataSet.setColor(ContextCompat.getColor(getContext(), R.color.colorChartBar));
        dataSet.setHighLightColor(ContextCompat.getColor(getContext(), R.color.colorChartBarHighlight));
        dataSet.setValueTextSize(textSize);
        dataSet.setValueTextColor(ContextCompat.getColor(getContext(), R.color.colorChartValueText));

        BarData data = new BarData(dataSet);
        data.setValueFormatter(this);
        data.setHighlightEnabled(true);
        data.setBarWidth(.3f); // set custom bar width

        chart.setOnChartValueSelectedListener(this);

        YAxis lestAxis = chart.getAxisLeft(), rightAxis = chart.getAxisRight();
        lestAxis.setDrawLabels(false);
        lestAxis.setDrawAxisLine(false);
        lestAxis.setGridColor(ContextCompat.getColor(getContext(), R.color.colorChartGrid));
        rightAxis.setDrawLabels(false);
        rightAxis.setDrawAxisLine(false);
        rightAxis.setGridColor(ContextCompat.getColor(getContext(), R.color.colorChartGrid));

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextSize(textSize);
        xAxis.setTextColor(ContextCompat.getColor(getContext(), R.color.colorChartAxisText));
        //Make the X-axis thicker
        xAxis.setYOffset(xLabelMarginTop);

        chart.setPinchZoom(false);
        chart.setDoubleTapToZoomEnabled(false);
        chart.setHighlightPerDragEnabled(false);
        Description d = new Description();
        d.setText("");
        chart.setDescription(d);
        chart.setData(data);
        chart.setVisibleXRangeMaximum(VISIBLE_BARS_NUMBER);
        xAxis.setValueFormatter(this);

        chart.getLegend().setEnabled(false);

        chart.setDrawBorders(false);
        chart.setDrawGridBackground(true);
        chart.setGridBackgroundColor(ContextCompat.getColor(getContext(),
                R.color.colorChartGridBackground));
        chart.setBackgroundColor(ContextCompat.getColor(getContext(),
                R.color.colorChartBackground));
        chart.setFitBars(true); // make the x-axis fit exactly all bars

        //Remove useless Y-axis labels space
        ViewPortHandler viewPortHandler = chart.getViewPortHandler();
        chart.setViewPortOffsets(0, 0, 0,
                viewPortHandler.offsetBottom());

        //Increase max Y-axis values to avoid bar overflowing
        float maxLeft = lestAxis.getAxisMaximum();
        float maxRight = rightAxis.getAxisMaximum();
        lestAxis.setAxisMaximum(maxLeft + MAX_Y_COEFFICIENT);
        rightAxis.setAxisMaximum(maxRight + MAX_Y_COEFFICIENT);

        //Choose first
        chart.highlightValue(dataSet.getEntryForIndex(0).getX(), 0);

        //Apply previously set viewPortOffsets and avoid a tricky bug
        chart.moveViewToX(0);
        binding.setVm(this);
        binding.setHandlers(this);
        return binding.getRoot();
    }

    private List<BarEntry> getChartEntries(){
        //TODO: Change a way of getting data from the Activity to Parcelable
        final List<ProgressItem> graphData = ((StatisticsAVM)getActivity()).getProgressItems();
        if (graphData == null || graphData.size() == 0) return new ArrayList<>();

        float date;
        List<BarEntry> entries = new ArrayList<>(graphData.size());
        for(ProgressItem item : graphData){
            date = TimeUnit.MILLISECONDS.toDays(item.getDate().getTime());
            entries.add(new BarEntry(date, item.getCurrentProgress(), item));
        }
        return entries;
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return String.valueOf((int)value);
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        dateFormatter.setTimeInMillis(TimeUnit.DAYS.toMillis((long)value));
        return String.valueOf(dateFormatter.get(Calendar.DAY_OF_MONTH));
    }


    private Highlight lastHighlight;
    @Override
    public void onNothingSelected() {
        if (lastHighlight != null && chart != null)
            chart.highlightValue(lastHighlight, false);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        //Disable reselecting a value
        if (lastHighlight != null && lastHighlight.equalTo(h)) return;

        ProgressItem item = (ProgressItem)e.getData();

        progressDate.set(item.getDate());
        programName.set(item.getName());

        currentProgressValue.set(item.getCurrentProgress());
        maxProgressValue.set(item.getUpperBound());

        atHomePercentage.set(item.getLocationPercentage(ProgressItem.Location.AT_HOME));
        atWorkPercentage.set(item.getLocationPercentage(ProgressItem.Location.AT_WORK));
        otherPercentage.set(item.getLocationPercentage(ProgressItem.Location.OTHER));

        noHurtPercentage.set(item.getPainPercentage(PainLevel.NO_HURT));
        littleBitPercentage.set(item.getPainPercentage(PainLevel.HURTS_A_LITTLE_BIT));
        littleMorePercentage.set(item.getPainPercentage(PainLevel.HURTS_A_LITTLE_MORE));

        lastHighlight = h;
    }

    public int getExerciseCountNorm(){
        return 12;
    }
    public void onClickQuickNote(View view) {
        Intent i = new Intent(getActivity(), ChatAVM.class);
        startActivity(i);
    }
}
