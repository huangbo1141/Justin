package au.com.myphysioapp.myphysio.ui.statistics;

import android.content.Context;

import java.util.Date;

import au.com.myphysioapp.myphysio.ViewVM;
import au.com.myphysioapp.myphysio.model.ProgressItem;

/**
 * Created by Wooden on 09.02.2017.
 */

public class ProgressItemVM extends ViewVM {
    public final Date progressDate;
    public final String progressDescription;
    public final int currentProgressValue;
    public final int maxProgressValue;

    public ProgressItemVM(Context context,
                          Date progressDate,
                          String progressDescription,
                          int currentProgressValue,
                          int maxProgressValue) {

        super(context);
        this.progressDate = progressDate;
        this.progressDescription = progressDescription;
        this.currentProgressValue = currentProgressValue;
        this.maxProgressValue = maxProgressValue;
    }

    public static ProgressItemVM wrapItem(Context c, ProgressItem item){
        return new ProgressItemVM(c,
                item.getDate(),
                item.getName(),
                item.getCurrentProgress(),
                item.getUpperBound());
    }
}
