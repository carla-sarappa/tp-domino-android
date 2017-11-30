package ar.edu.unq.uis.domino.screens;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;

import ar.edu.unq.uis.domino.R;

/**
 * Created by Carla on 27/11/2017.
 */

public class SettingsActivity extends PreferenceActivity {
    // Sacado de los docs https://developer.android.com/guide/topics/ui/settings.html
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
