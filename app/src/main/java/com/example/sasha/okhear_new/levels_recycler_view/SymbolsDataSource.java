package com.example.sasha.okhear_new.levels_recycler_view;

import java.util.ArrayList;
import java.util.List;

public class SymbolsDataSource extends DataSource {

    private final List<LevelItem> items = new ArrayList<>();
    private String symbolsString = "0123456789АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЫЬЭЮЯ ";

    {
        for (char c : symbolsString.toCharArray()) {
            items.add(new LevelItem(String.valueOf(c)));
        }
    }

    @Override
    public LevelItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }
}
