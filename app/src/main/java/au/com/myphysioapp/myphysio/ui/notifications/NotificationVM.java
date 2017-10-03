package au.com.myphysioapp.myphysio.ui.notifications;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import au.com.myphysioapp.myphysio.ViewVM;

/**
 * Created by Wooden on 13.02.2017.
 */

public class NotificationVM extends ViewVM {
    private static final SimpleDateFormat datePattern =
            new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    public final String dateString;
    public final Date date;
    public final String sender;

    public NotificationVM(Context context, Date date, String sender) {
        super(context);
        this.date = date;
        this.dateString = datePattern.format(date);
        this.sender = sender;
    }
}
