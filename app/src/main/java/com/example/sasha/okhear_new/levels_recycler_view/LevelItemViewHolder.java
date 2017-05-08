package com.example.sasha.okhear_new.levels_recycler_view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.sasha.okhear_new.R;

public class LevelItemViewHolder extends RecyclerView.ViewHolder {

    TextView text;

    public LevelItemViewHolder(View itemView) {
        super(itemView);
        text = (TextView) itemView.findViewById(R.id.text);
    }

    public void bind(LevelItem item) {
        if (text != null) {
            text.setText(item.getText());
        }
    }
}
