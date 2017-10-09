package au.com.myphysioapp.myphysio.ui.chat;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import au.com.myphysioapp.myphysio.R;

/**
 * Created by Administrator on 10/4/2017.
 */

public class SignalMessage {
    public String message;
    public boolean remote = false;

    public SignalMessage(String message){
        this.message = message;
    }

    public SignalMessage(String message,boolean remote){
        this.message = message;
        this.remote = remote;
    }
    public String getMessageText(){
        return this.message;
    }

    public SignalMessageVM signalMessageVM(Activity activity){
        SignalMessageVM signalMessageVM = new SignalMessageVM();
        signalMessageVM.message = message;
        signalMessageVM.isRemote = remote;
        signalMessageVM.isMe = !remote;
        return signalMessageVM;
    }
}
