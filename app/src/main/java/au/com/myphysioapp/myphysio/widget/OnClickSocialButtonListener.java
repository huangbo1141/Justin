package au.com.myphysioapp.myphysio.widget;

import android.view.View;

/**
 * Allows you to receive events when user clicks on one of the buttons from the login_with_widget.xml.
 * Just put an instance of the interface via databinding while including the layout or set it after created binding class/
 */

public interface OnClickSocialButtonListener {
    void onTwitterEntry(View view);

    void onFacebookEntry(View view);

    void onGooglePlusEntry(View view);
}
