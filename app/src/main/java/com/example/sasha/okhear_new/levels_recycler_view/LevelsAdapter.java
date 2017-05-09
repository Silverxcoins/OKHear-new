package com.example.sasha.okhear_new.levels_recycler_view;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.sasha.okhear_new.camera.CameraScreen;
import com.example.sasha.okhear_new.R;

public class LevelsAdapter extends RecyclerView.Adapter {

    private static final int EMPTY_ITEM_VIEW = 0;
    private static final int LEVEL_ITEM_VIEW = 1;

    private RecyclerView recyclerView;
    private DataSource dataSource;
    private CameraScreen cameraScreen;
    private boolean isTraining;

    public LevelsAdapter(RecyclerView recyclerView, DataSource dataSource, CameraScreen cameraScreen, boolean isTraining) {
        super();
        this.recyclerView = recyclerView;
        this.dataSource = dataSource;
        this.cameraScreen = cameraScreen;
        this.isTraining = isTraining;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == LEVEL_ITEM_VIEW) {
            final View view = ((Activity) parent.getContext()).getLayoutInflater().inflate(R.layout.level_item_view, parent, false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = recyclerView.getChildLayoutPosition(view);
                    LevelItem item = dataSource.getItem(position);
                    cameraScreen.showCamera(item.getText(), isTraining);
                }
            });
            return new LevelItemViewHolder(view);
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
