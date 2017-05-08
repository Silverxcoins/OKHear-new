package com.example.sasha.okhear_new.levels_recycler_view;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.sasha.okhear_new.R;

public class LevelsAdapter extends RecyclerView.Adapter {

    private static final int EMPTY_ITEM_VIEW = 0;
    private static final int LEVEL_ITEM_VIEW = 1;

    private DataSource dataSource;

    public LevelsAdapter(DataSource dataSource) {
        super();
        this.dataSource = dataSource;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == LEVEL_ITEM_VIEW) {
            return new LevelItemViewHolder(
                    ((Activity) parent.getContext()).getLayoutInflater().inflate(R.layout.level_item_view, parent, false)
            );
        } else {
            return new LevelItemViewHolder(
                    ((Activity) parent.getContext()).getLayoutInflater().inflate(R.layout.empty_level_item, parent, false)
            );
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        LevelItem item = dataSource.getItem(position);
        ((LevelItemViewHolder) holder).bind(item);
    }

    @Override
    public int getItemCount() {
        return dataSource.getCount();
    }

        @Override
        public int getItemViewType(int position) {
            if (position == dataSource.getCount() - 1) {
                return EMPTY_ITEM_VIEW;
            } else {
                return LEVEL_ITEM_VIEW;
            }
        }

}
