package au.com.myphysioapp.myphysio.ui.user_programs;

import android.content.Context;
import android.graphics.drawable.Drawable;

import au.com.myphysioapp.myphysio.ViewVM;

/**
 * Created by Wooden on 09.02.2017.
 */

public class ProgramItemVM extends ViewVM {
    public final Drawable programPicture;
    public final String programName;
    public final String programDescription;


    public ProgramItemVM(Context context, Drawable programPicture, String programName, String programDescription) {
        super(context);
        this.programPicture = programPicture;
        this.programName = programName;
        this.programDescription = programDescription;
    }
}
