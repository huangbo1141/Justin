package au.com.myphysioapp.myphysio.ui.notifications;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.Date;

/**
 * Created by Wooden on 13.02.2017.
 */

public class NewNotificationVM extends NotificationVM {
    public final Bitmap senderPicture;
    public NewNotificationVM(Context context,
                             Date date,
                             String sender,
                             Bitmap senderPicture) {

        super(context, date, sender);
        this.senderPicture = senderPicture;
    }
}
