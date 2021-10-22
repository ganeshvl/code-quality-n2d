package com.news2day.Adapter;

import android.app.Dialog;
import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.news2day.Activity.HomeActivity;
import com.news2day.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;
import static com.news2day.Adapter.VerticalSliderAdapter.dots_indicator;

public class SliderViewPagerAdapter extends PagerAdapter {
    String title;
    Context context;
    ImageView iv_image;
    String[] img;
    FileOutputStream outputStream;
    File file;
    NotificationManager mNotificationManager;
    Uri uri;
    TextView heading_tx,
            descripte_tx;
  /*  int[] img = {R.drawable.onboard_one, R.drawable.onboard_two, R.drawable.onboard_three};
    //int[] heading = {R.string.heading_one, R.string.heading_two, R.string.heading_three};*/

    public SliderViewPagerAdapter(Context context, String[] img, String title) {
        this.context = context;
        this.img = img;
        this.title = title;
    }

    @Override
    public int getCount() {
        return img.length;
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (LinearLayout) object);
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_view_pager_adapter_lay_out, container, false);
        container.addView(view);
        iv_image = view.findViewById(R.id.iv_image);

    /*    if(img.length<2){

            dots_indicator.setVisibility(View.GONE);

        }
        else {

            dots_indicator.setVisibility(View.VISIBLE);

        }*/


        //  Glide.with(context).load(img).into(iv_image);

        List<String> url_img = new ArrayList<>();
        Collections.addAll(url_img, img);
        if (url_img.size() == 0) {
            dots_indicator.setVisibility(View.GONE);
            Glide.with(context).load("").error(R.mipmap.ic_launcher).into(iv_image);
        } else {
            if (url_img.get(position) == null) {
                dots_indicator.setVisibility(View.GONE);
                Glide.with(context).load("").error(R.mipmap.ic_launcher).into(iv_image);
            } else {
                Glide.with(context).load("http://18.118.90.146:5001/" + url_img.get(position)).error(R.mipmap.ic_launcher).into(iv_image);
            }
        }


      /*  if (img.length==0){
                Glide.with(context).load("").error(R.mipmap.ic_launcher).into(iv_image);
            }else{
                for (int i = 0; i < img.length; i++) {
                 if (img[i]==null){
                     dots_indicator.setVisibility(View.GONE);
                    */
        /* VerticalSliderAdapter.trending_tv.setTextColor(context.getResources().getColor(R.color.app_color));
                     VerticalSliderAdapter.trending_tv.setBackgroundColor(context.getResources().getColor(R.color.white));*//*
                        Glide.with(context).load("").error(R.mipmap.ic_launcher).into(iv_image);
                    }
                 else {
                     Glide.with(context).load("http://18.118.90.146:5001/" + url_img).error(R.mipmap.ic_launcher).into(iv_image);
                     Log.d(TAG, "instantiateItem:"+img[i]);
                 }
            }
        }*/

        /* Glide.with(context).load(img).error(R.mipmap.ic_launcher).into(iv_image);*/

        iv_image.setOnClickListener(new View.OnClickListener() {
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
                ImageView ivimage = dialog.findViewById(R.id.iv_image);
                TextView tv_news_title = dialog.findViewById(R.id.tv_news_title);
                ImageView ic_download = dialog.findViewById(R.id.ic_download);
                ImageView ic_download_all = dialog.findViewById(R.id.ic_download_all);
                List<String> url_img = new ArrayList<>();
                Collections.addAll(url_img, img);
                tv_news_title.setText(title);
                if (url_img.size() == 0) {
                    dots_indicator.setVisibility(View.GONE);
                    Glide.with(context).load("").error(R.mipmap.ic_launcher).into(ivimage);
                } else {
                    if (url_img.get(position) == null) {
                        dots_indicator.setVisibility(View.GONE);
                        Glide.with(context).load("").error(R.mipmap.ic_launcher).into(ivimage);
                    } else {
                        Glide.with(context).load("http://18.118.90.146:5001/" + url_img.get(position)).error(R.mipmap.ic_launcher).into(ivimage);
                    }
                }
                ic_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                        intent.putExtra(Intent.EXTRA_TEXT, uri + "  " + "Hi,Friends This Post is Useful For all");
                        context.startActivity(Intent.createChooser(intent, "share via"));

                    }
                });
                ic_download_all.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i = 0; i < img.length; i++) {
                            downloadFile("http://18.118.90.146:5001/" + img[i], context);
                        }
                    }
                });

                ic_download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BitmapDrawable bitmapDrawable = (BitmapDrawable) ivimage.getDrawable();
                        Bitmap bitmap = bitmapDrawable.getBitmap();
                        // Bitmap cmprsbitmap = Bitmap.createScaledBitmap(bitmap, 1080, 1920, true);


                        File filepath = Environment.getExternalStorageDirectory();
                        File dir = new File(filepath.getAbsoluteFile() + "/Download/");
                        dir.mkdir();
                        file = new File(dir, System.currentTimeMillis() + ".jpg");
                        try {
                            outputStream = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                            showDownloadNotification();
                            Toast.makeText(context, "Download Successfully", Toast.LENGTH_SHORT).show();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        try {
                            outputStream.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            outputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

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


        return view;
    }

    public static void downloadFile(String uRl, Context context) {
        File myDir = new File(Environment.getExternalStorageDirectory(), "/Download/");
        if (!myDir.exists()) {
            myDir.mkdirs();
        }

        DownloadManager mgr = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        Uri downloadUri = Uri.parse(uRl);
        DownloadManager.Request request = new DownloadManager.Request(
                downloadUri);

        request.setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI
                        | DownloadManager.Request.NETWORK_MOBILE).setAllowedOverMetered(true)
                .setAllowedOverRoaming(true).setTitle("Myapp - " + "Downloading " + uRl).
                setVisibleInDownloadsUi(true)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, uRl);

        mgr.enqueue(request);
        Toast.makeText(context, "All Images Downloaded", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void displayNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.down_arrow) //set icon for notification
                        .setContentTitle("Notifications Example") //set title of notification
                        .setContentText("This is a notification message")//this is notification message
                        .setAutoCancel(true) // makes auto cancel of notification
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT); //set priority of notification


        Intent notificationIntent = new Intent(context, HomeActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //notification message will get at NotificationView
        notificationIntent.putExtra("message", "This is a notification message");

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());

    }

    void showDownloadNotification() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            {
                NotificationChannel notificationChannel = new NotificationChannel("newstwoday",
                        "Channel 1", NotificationManager.IMPORTANCE_HIGH);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

                notificationChannel.setDescription("This is BNT");
                notificationChannel.setLightColor(Color.RED);
                notificationChannel.enableVibration(true);
                notificationChannel.setShowBadge(true);
                Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                notificationChannel.setSound(uri, null);
                notificationManager.createNotificationChannel(notificationChannel);

                NotificationChannel notificationChannel2 = null;

                notificationChannel2 = new NotificationChannel("news",
                        "Channel 2", NotificationManager.IMPORTANCE_MIN);


                notificationChannel.setDescription("This is bTV");
                notificationChannel.setLightColor(Color.RED);
                notificationChannel.enableVibration(true);
                notificationChannel.setShowBadge(true);
                Uri uri1 = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                notificationChannel.setSound(uri1, null);
                notificationManager.createNotificationChannel(notificationChannel2);

            }
            try {
                Uri selectedUri = Uri.parse(String.valueOf(file));
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(selectedUri, "resource/folder");
                //            startActivity(intent);

                //mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.down_arrow)
                        .setContentTitle("Invoice downloaded")
                        .setContentText("")
                        .setContentIntent(pendingIntent)
                        .setPriority(NotificationCompat.PRIORITY_LOW);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

                // notificationId is a unique int for each notification that you must define
                mNotificationManager.notify(1, builder.build());


            } catch (Exception e) {
                Log.e(TAG, "Notification " + e.toString());
            }
        }
    }
}
