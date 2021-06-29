package com.example.vacationstation;

import android.os.Bundle;

import androidx.preference.CheckBoxPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

public class Settings extends PreferenceFragmentCompat {

    private CheckBoxPreference tick;
    private SwitchPreference cardSet;
    public static Boolean Dot = false;
    public static Boolean Cardmode = false;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        tick = findPreference("redDot");
        cardSet = findPreference("card_pref");
        Cardmode = cardSet.isChecked();
        Dot = tick.isChecked();
        //bindPreferenceSummaryToValue(findPreference("ringtone"));
    }
}