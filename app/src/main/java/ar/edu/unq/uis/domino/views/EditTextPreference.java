package ar.edu.unq.uis.domino.views;

/**
 * Created by Carla Sarappa on 29/11/2017.
 */

import android.content.Context;
import android.util.AttributeSet;

// https://stackoverflow.com/questions/34288925/display-the-value-of-the-edittextpreference-in-summary
public class EditTextPreference extends android.preference.EditTextPreference{
    public EditTextPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public EditTextPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditTextPreference(Context context) {
        super(context);
    }

    @Override
    public CharSequence getSummary() {
        String summary = super.getSummary().toString();
        return String.format(summary, getText());
    }
}