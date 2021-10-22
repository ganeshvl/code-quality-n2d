package com.news2day.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.news2day.Activity.LocationActivity;
import com.news2day.Activity.StartUpActivity;
import com.news2day.Activity.VideosActvity;
import com.news2day.R;
import com.news2day.model.VerticalViewPojos.PostsPojoTwo;

import java.util.ArrayList;
import java.util.List;

public class SpecialVideosAdapter extends RecyclerView.Adapter<SpecialVideosAdapter.ViewHolder> {
    Context context;

    List<PostsPojoTwo> data;
    String[] video_url;
    Uri uri;
    String video_string;
    ArrayList<Uri> imageUris = new ArrayList<Uri>();
    public SpecialVideosAdapter(Context context, List<PostsPojoTwo> data) {
        this.context = context;

        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_videos, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        video_url = data.get(position).getVideo_url();
        holder.title_tx_special.setText(data.get(position).getName());
        MediaController mediaController = new MediaController(context);
        mediaController.setAnchorView(holder.video_view);

        //specify the location of media file
        // Uri uri = Uri.parse("https://www.youtube.com/watch?v=Q_EwuVHDb5U");
        // Uri uri = Uri.parse("https://www.istockphoto.com/video/harvesting-a-wheat-field-during-a-very-dry-summer-season-aerial-view-gm1002097560-270815174?utm_source=pixabay&utm_medium=affiliate&utm_campaign=SRP_video_sponsored&referrer_url=http%3A%2F%2Fpixabay.com%2Fvideos%2Fsearch%2Fscenery%2F&utm_term=scenery.mp4");

        for (int i = 0; i < video_url.length; i++) {

             uri = Uri.parse("http://18.118.90.146:5001/" + video_url[i]);
              video_string="http://18.118.90.146:5001/" + video_url[i];
              imageUris.add(uri);
            //Setting nViewCelMediaController and URI, then starting the videoView
            holder.video_view.setMediaController(mediaController);
            holder.video_view.setVideoURI(uri);
            holder.video_view.requestFocus();
            holder.video_view.start();
            Glide.with(context).load("http://18.118.90.146:5001/"+video_url[i]);
        }
        holder.share_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.video_view.stopPlayback();
                Intent share = new Intent();
                share.setAction(Intent.ACTION_SEND);
                share.putExtra(Intent.EXTRA_STREAM, video_string);
                share.putExtra(Intent.EXTRA_TEXT, video_string + "  " + "Hi,Friends This Video is Useful For all" + " ");
                share.setType("image/*");
                share.setType("text/*");
                context.startActivity(Intent.createChooser(share,null));

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, VideosActvity.class);
                intent.putExtra("video_url",video_url);
                intent.putExtra("text",data.get(position).getName());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_language,share_iv;
        TextView title_tx_special;
        VideoView video_view;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_language = itemView.findViewById(R.id.iv_image);

            title_tx_special = itemView.findViewById(R.id.title_tx_special);

            video_view = itemView.findViewById(R.id.video_view);
            share_iv = itemView.findViewById(R.id.share);
        }
    }
}
