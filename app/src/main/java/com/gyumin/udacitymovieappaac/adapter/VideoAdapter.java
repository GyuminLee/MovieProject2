package com.gyumin.udacitymovieappaac.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gyumin.udacitymovieappaac.R;
import com.gyumin.udacitymovieappaac.data.Video;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private ArrayList<Video> videos;
    private Context context;
    private VideoClickListener videoClickListener;

    public VideoAdapter(Context context, ArrayList<Video> videos) {
        this.videos = videos;
        this.context = context;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.video_view, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        Video video = videos.get(position);
        holder.nameTv.setText(video.getName());
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nameTv;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.tv_video_name);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (videoClickListener != null) {
                videoClickListener.onVideoClick(v, getAdapterPosition());
            }
        }
    }

    public void setVideoClickListener(VideoClickListener videoClickListener) {
        this.videoClickListener = videoClickListener;
    }
    public interface VideoClickListener {
        void onVideoClick(View view, int position);
    }
}
