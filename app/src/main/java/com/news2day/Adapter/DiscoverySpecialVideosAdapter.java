package com.news2day.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.news2day.Activity.StartUpActivity;
import com.news2day.Activity.VideosActvity;
import com.news2day.R;
import com.news2day.UserDetails;
import com.news2day.model.DiscoveryPojos.SubCategoryDetailsPojo;
import com.news2day.model.VerticalViewPojos.PostsPojoTwo;
import com.news2day.retrofit.RetrofitInstance;
import com.news2day.retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.List;

public class DiscoverySpecialVideosAdapter extends RecyclerView.Adapter<DiscoverySpecialVideosAdapter.ViewHolder> {
    Context context;
    ArrayList<String> name = new ArrayList<>();
    ArrayList<Integer> image = new ArrayList<>();
    private static int checkedPosition = -1;
    List<SubCategoryDetailsPojo> subcat_data;
    UserDetails userDetails;
    RetrofitService service;
    String language;
    List<PostsPojoTwo> id;
    String[] video_url;
    int page_count = 1, per_page_count = 10;

    public DiscoverySpecialVideosAdapter(Context context, List<PostsPojoTwo> id, String language) {
        this.context = context;
        this.id = id;
        this.language = language;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.special_videos_recycler_adapter_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        service = RetrofitInstance.createService(RetrofitService.class);
        userDetails = new UserDetails(context);
        video_url = id.get(position).getVideo_url();
        MediaController mediaController = new MediaController(context);
        mediaController.setVisibility(View.GONE);
        mediaController.setAnchorView(holder.video_view);

        //specify the location of media file
        // Uri uri = Uri.parse("https://www.youtube.com/watch?v=Q_EwuVHDb5U");
        // Uri uri = Uri.parse("https://www.istockphoto.com/video/harvesting-a-wheat-field-during-a-very-dry-summer-season-aerial-view-gm1002097560-270815174?utm_source=pixabay&utm_medium=affiliate&utm_campaign=SRP_video_sponsored&referrer_url=http%3A%2F%2Fpixabay.com%2Fvideos%2Fsearch%2Fscenery%2F&utm_term=scenery.mp4");

        for (int i = 0; i < video_url.length; i++) {

            Uri uri = Uri.parse("http://18.118.90.146:5001/"+video_url[i]);
            //Setting nViewCelMediaController and URI, then starting the videoView
            holder.video_view.setMediaController(mediaController);
            holder.video_view.setVideoURI(uri);
            holder.video_view.requestFocus();
            Glide.with(context).load("http://18.118.90.146:5001/"+video_url[i]).into(holder.iv_view);

        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, VideosActvity.class);
                intent.putExtra("video_url",video_url);
                intent.putExtra("text",id.get(position).getName());
                context.startActivity(intent);

            }
        });

    }


    @Override
    public int getItemCount() {
        return id.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        VideoView video_view;

        ImageView iv_view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            video_view = itemView.findViewById(R.id.video_view);
            iv_view = itemView.findViewById(R.id.iv_view);

        }
    }


}
