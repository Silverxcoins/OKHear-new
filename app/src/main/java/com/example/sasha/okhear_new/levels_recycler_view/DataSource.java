package com.example.sasha.okhear_new.levels_recycler_view;

public abstract class DataSource {

    public abstract LevelItem getItem(int position);

    public abstract int getCount();
}
