package com.example.britalians;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class RowAdapter extends RecyclerView.Adapter<RowAdapter.RowViewHolder> {
    SerieList serieList;
    List<Row> rowList;
    Context context;
    MainFragment fragment;
    int count;
    VideoAdapter.OnVideoSelectedListener onVideoSelectedListener;
    VideoAdapter.OnVideoFocusedListener onVideoFocusedListener;


    public static final int TYPE_VIDEO_ROW = 0;
    public static final int TYPE_SERIE_LIST_ROW = 1;

    public RowAdapter(MainFragment fragment, List<Row> rowList, Context context, VideoAdapter.OnVideoSelectedListener onVideoSelectedListener, VideoAdapter.OnVideoFocusedListener onVideoFocusedListener, SerieList serieList, int count) {
        this.rowList = rowList;
        this.context = context;
        this.fragment = fragment;
        this.onVideoSelectedListener = onVideoSelectedListener;
        this.onVideoFocusedListener = onVideoFocusedListener;
        this.serieList = serieList;
        this.count = count;
    }


    @Override
    public int getItemViewType(int position) {
        if (position < count) {
            return TYPE_VIDEO_ROW;
        } else if (position == count) {
            return TYPE_SERIE_LIST_ROW;
        } else {
            return TYPE_VIDEO_ROW;
        }
    }

    @Override
    public RowViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new RowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RowViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_SERIE_LIST_ROW) {
            holder.text.setText("Series");
            LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            holder.recyclerView.setLayoutManager(layoutManager);
            holder.recyclerView.setAdapter(new SerieAdapter(fragment, serieList.series, onVideoSelectedListener, onVideoFocusedListener));
        } else {
            int rowIndex;
            if (position < count) {
                rowIndex = position;
            } else {
                rowIndex = position - 1;
            }
            Row row = rowList.get(rowIndex);
            holder.text.setText(row.title);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            holder.recyclerView.setLayoutManager(layoutManager);
            holder.recyclerView.setAdapter(new VideoAdapter(row.items, onVideoSelectedListener, onVideoFocusedListener));
            holder.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled( RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    if (dy > 0) { // Scrolling down
                        Log.d("checka","called");
                        View nextFocusedView = recyclerView.focusSearch(holder.recyclerView, View.FOCUS_DOWN);
                        if (nextFocusedView != null && nextFocusedView != holder.recyclerView) {
                            nextFocusedView.requestFocus();
                        }
                    }
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return rowList.size() + 1;
    }

    static class RowViewHolder extends RecyclerView.ViewHolder {

        RecyclerView recyclerView;
        TextView text;

        public RowViewHolder( View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.row_title);
            recyclerView = itemView.findViewById(R.id.row_recycler_view);
        }
    }
}
