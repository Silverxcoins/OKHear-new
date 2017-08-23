package com.example.sasha.okhear_new;

import org.androidannotations.annotations.sharedpreferences.DefaultInt;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref
public interface ScorePref {

    @DefaultInt(0)
    int score();
}
