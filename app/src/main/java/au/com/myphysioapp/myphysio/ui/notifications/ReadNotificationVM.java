package au.com.myphysioapp.myphysio.ui.notifications;

import android.content.Context;

import java.util.Date;

/**
 * Created by Wooden on 13.02.2017.
 */

public class ReadNotificationVM extends NotificationVM {
    public final boolean isAccepted;

    public ReadNotificationVM(Context context, Date date, String sender, boolean isAccepted) {
        super(context, date, sender);
        this.isAccepted = isAccepted;
    }
}
