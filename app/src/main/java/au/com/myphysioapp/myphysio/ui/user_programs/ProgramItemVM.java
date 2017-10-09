package au.com.myphysioapp.myphysio.ui.user_programs;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.io.Serializable;

import au.com.myphysioapp.myphysio.ViewVM;
import au.com.myphysioapp.myphysio.model.ProgramModel;

/**
 * Created by Wooden on 09.02.2017.
 */

public class ProgramItemVM extends ViewVM {
    public Drawable programPicture;
    public final String programName;
    public final String programDescription;
    public String url;
    public ProgramModel model;

    public ProgramItemVM(Context context, Drawable programPicture, String programName, String programDescription) {
        super(context);
        this.programPicture = programPicture;
        this.programName = programName;
        this.programDescription = programDescription;
    }

    public ProgramItemVM(Context context, ProgramModel model) {
        super(context);
        this.programName = model.fullname;
        this.url = model.image;

        this.programDescription = model.fullname;
        this.model = model;
    }
}
