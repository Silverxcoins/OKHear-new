package com.example.sasha.okhear_new.levels_recycler_view;

import java.util.ArrayList;
import java.util.List;

public class WordsDataSource extends DataSource {

    private final List<LevelItem> items = new ArrayList<>();
    private String[] words = {"Еда", "Ваза", "Жиза", ""};

    {
        for (String word : words) {
            items.add(new LevelItem(word));
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
