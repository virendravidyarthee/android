package com.virendra.practiceassignment.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.virendra.practiceassignment.Model.DisplayData;
import com.virendra.practiceassignment.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<DisplayData> mDataList;

    public RecyclerAdapter(List<DisplayData> mDataList) {
        this.mDataList = mDataList;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        holder.titleTextView.setText(mDataList.get(position).getTitle());
        holder.imageIdTextView.setText("ID: " + String.valueOf(mDataList.get(position).getId()));
        holder.albumIdTextView.setText("Album ID: " + String.valueOf(mDataList.get(position).getAlbumId()));
        Picasso.get()
                .load(mDataList.get(position).getUrl())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_imageID) TextView imageIdTextView;
        @BindView(R.id.tv_albumID) TextView albumIdTextView;
        @BindView(R.id.tv_title) TextView titleTextView;
        @BindView(R.id.iv_imageContent) ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
