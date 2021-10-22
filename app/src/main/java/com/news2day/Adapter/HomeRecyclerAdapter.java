package com.news2day.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.news2day.BuildConfig;
import com.news2day.R;


public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder> {

    Context context;
    Animation bottomAnim;
    CheckBox cb_other;
    TextView others_ed_tx;

    public HomeRecyclerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_news_slide, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        bottomAnim = AnimationUtils.loadAnimation(context, R.anim.bottom_animation);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ic_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey check out my app at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                sendIntent.setType("text/plain");
                context.startActivity(sendIntent);
            }
        });
        holder.ic_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.ic_like.setBackgroundResource(R.drawable.ic_like_click);
            }
        });
        holder.ic_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.ic_bookmark.setBackgroundResource(R.drawable.ic_bookmark_click);
                Toast.makeText(context, "Added to Bookmark", Toast.LENGTH_SHORT).show();
            }
        });
        holder.ic_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.ic_report.setBackgroundResource(R.drawable.ic_spam_click);
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_report);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Window window = dialog.getWindow();
//                window.setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                window.setGravity(Gravity.CENTER | Gravity.CENTER);
                CheckBox edit_tx = dialog.findViewById(R.id.cb_mistakes);
                LinearLayout out_side_lay_out = dialog.findViewById(R.id.out_side_lay_out);
                TextView submit_tx = (TextView) dialog.findViewById(R.id.submit_tx);
               // cb_other =dialog.findViewById(R.id.cb_other);
                others_ed_tx = (TextView) dialog.findViewById(R.id.others_ed_tx);

                cb_other.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        others_ed_tx.setVisibility(View.VISIBLE);
                    }
                });

                out_side_lay_out.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                submit_tx.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        final Dialog dialog = new Dialog(context);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog_report_sucess);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        Window window = dialog.getWindow();
//                window.setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
                        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                        window.setGravity(Gravity.CENTER | Gravity.CENTER);
                        LinearLayout ll_dialog = dialog.findViewById(R.id.ll_dialog);

                        ll_dialog.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();

                    }
                });

                dialog.show();

            }
        });
        holder.ic_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.bottom_download.requestFocus();
                holder.bottom_download.setVisibility(View.VISIBLE);
                holder.bottom_lay_out.setVisibility(View.GONE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        holder.bottom_download.setVisibility(View.GONE);
                        holder.bottom_lay_out.setVisibility(View.VISIBLE);
                        Toast.makeText(context, "Download Sucessfull", Toast.LENGTH_SHORT).show();
                    }
                }, 3000);

            }
        });

        holder.iv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_image);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Window window = dialog.getWindow();
//                window.setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                window.setGravity(Gravity.CENTER);
                LinearLayout ll_dialog = dialog.findViewById(R.id.ll_dialog);
                ImageView ic_share = dialog.findViewById(R.id.ic_share);

                ImageView ic_download = dialog.findViewById(R.id.ic_download);
                ic_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT,
                                "Hey check out my app at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                        sendIntent.setType("text/plain");
                        context.startActivity(sendIntent);
                    }
                });

                ic_download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "Download Sucessful", Toast.LENGTH_SHORT).show();
                    }
                });

                ll_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }

        });


    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ic_share, ic_bookmark, ic_download, ic_report, ic_like;
        RelativeLayout bottom_lay_out, bottom_download;
        ImageView iv_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ic_share = itemView.findViewById(R.id.ic_share);
            ic_bookmark = itemView.findViewById(R.id.ic_bookmark);
            ic_download = itemView.findViewById(R.id.ic_download);
            ic_report = itemView.findViewById(R.id.ic_report);
            ic_like = itemView.findViewById(R.id.ic_like);
            bottom_lay_out = itemView.findViewById(R.id.bottom_lay_out);
            bottom_download = itemView.findViewById(R.id.bottom_download);
            iv_image = itemView.findViewById(R.id.iv_image);


        }
    }
}
