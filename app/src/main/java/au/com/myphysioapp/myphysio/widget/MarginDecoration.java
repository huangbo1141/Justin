package au.com.myphysioapp.myphysio.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import au.com.myphysioapp.myphysio.R;


public class MarginDecoration extends RecyclerView.ItemDecoration {
    private int marginH;
    private int marginV;

    public MarginDecoration(Context context) {
        marginH = context.getResources().getDimensionPixelSize(R.dimen.cell_horizontal_margin);
        marginV = context.getResources().getDimensionPixelSize(R.dimen.cell_vertical_margin);
    }

    @Override
    public void getItemOffsets(
            Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(marginH, marginV, marginH, marginV);
    }
}
