package com.news2day.Activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.news2day.Fragment.DiscoverFragment;
import com.news2day.R;
import com.news2day.UserDetails;
import com.news2day.databinding.ActivityNewSettingsBinding;


public class SettingsActivity extends AppCompatActivity {
    ActivityNewSettingsBinding activitySettingsBinding;
    CheckBox cb_english,
            cb_telugu,
            cb_hindi;
    UserDetails userDetails;
    String language;
    ImageView cancel_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySettingsBinding = DataBindingUtil.setContentView(this, R.layout.activity_new_settings);

        userDetails = new UserDetails(this);

        language = userDetails.getLanguage();

        activitySettingsBinding.langTv.setText(language);


        activitySettingsBinding.toolbar.backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // onBackPressed();
                Intent intent = new Intent(SettingsActivity.this, OptionActivity.class);
                intent.putExtra("language", language);
                startActivity(intent);
            }
        });


        activitySettingsBinding.dayModeLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activitySettingsBinding.dayModeLl.setVisibility(View.GONE);
                activitySettingsBinding.nightModeLl.setVisibility(View.VISIBLE);
            }
        });

        activitySettingsBinding.dayModeLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activitySettingsBinding.dayModeLl.setVisibility(View.VISIBLE);
                activitySettingsBinding.nightModeLl.setVisibility(View.GONE);
            }
        });
        activitySettingsBinding.llLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String language = userDetails.getLanguage();
                Intent intent = new Intent(SettingsActivity.this, StatesActivity.class);
                intent.putExtra("language", language);
                intent.putExtra("id", "id");
                startActivity(intent);
            }
        });
        activitySettingsBinding.onLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activitySettingsBinding.onLl.setVisibility(View.GONE);
                activitySettingsBinding.offLl.setVisibility(View.VISIBLE);
            }
        });

        activitySettingsBinding.offLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activitySettingsBinding.onLl.setVisibility(View.VISIBLE);
                activitySettingsBinding.offLl.setVisibility(View.GONE);
            }
        });

        activitySettingsBinding.llLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               logout();
            }
        });
        activitySettingsBinding.nightModeLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                activitySettingsBinding.nightModeLl.setVisibility(View.GONE);
                activitySettingsBinding.dayModeLl.setVisibility(View.VISIBLE);
            }
        });

        activitySettingsBinding.dayModeLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                activitySettingsBinding.nightModeLl.setVisibility(View.VISIBLE);
                activitySettingsBinding.dayModeLl.setVisibility(View.GONE);
            }
        });
      /*  int currentNightMode = configuration.uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                // Night mode is not active, we're using the light theme
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                // Night mode is active, we're using dark theme
                break;
        }*/





        language = userDetails.getLanguage();
        if (language.equals("ENGLISH")) {
            activitySettingsBinding.toolbar.titleTx.setText(R.string.settings_en);
            activitySettingsBinding.changeLangTv.setText(R.string.change_lang_en);
            activitySettingsBinding.changeLocationTv.setText(R.string.update_location_en);
            activitySettingsBinding.selectThemeTv.setText(R.string.select_theme_en);
            activitySettingsBinding.notificationTv.setText(R.string.notification_en);
            activitySettingsBinding.deleteAccountTv.setText(R.string.delete_my_account_en);
            activitySettingsBinding.logoutTv.setText(R.string.logout_en);


        } else if (language.equals("TELUGU")) {
            activitySettingsBinding.toolbar.titleTx.setText(R.string.settings_te);
            activitySettingsBinding.changeLangTv.setText(R.string.change_lang_te);
            activitySettingsBinding.changeLocationTv.setText(R.string.update_location_te);
            activitySettingsBinding.selectThemeTv.setText(R.string.select_theme_te);
            activitySettingsBinding.notificationTv.setText(R.string.notification_te);
            activitySettingsBinding.deleteAccountTv.setText(R.string.delete_my_account_te);
            activitySettingsBinding.logoutTv.setText(R.string.logout_te);


        } else if (language.equals("HINDI")) {
            activitySettingsBinding.toolbar.titleTx.setText(R.string.settings_hi);
            activitySettingsBinding.changeLangTv.setText(R.string.change_lang_hi);
            activitySettingsBinding.changeLocationTv.setText(R.string.update_location_hi);
            activitySettingsBinding.selectThemeTv.setText(R.string.select_theme_hi);
            activitySettingsBinding.notificationTv.setText(R.string.notification_hi);
            activitySettingsBinding.deleteAccountTv.setText(R.string.delete_my_account_hi);
            activitySettingsBinding.logoutTv.setText(R.string.logout_hi);

        }


        activitySettingsBinding.rlLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(SettingsActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_language);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Window window = dialog.getWindow();
//                window.setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                window.setGravity(Gravity.CENTER | Gravity.CENTER);
                Button save = dialog.findViewById(R.id.save);
                cb_english = dialog.findViewById(R.id.cb_english);
                cb_telugu = dialog.findViewById(R.id.cb_telugu);
                cb_hindi = dialog.findViewById(R.id.cb_hindi);
                cancel_img = dialog.findViewById(R.id.cancel_img);

                cancel_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                if (language.equals("ENGLISH")) {

                    cb_english.setChecked(true);
                } else if (language.equals("TELUGU")) {

                    cb_telugu.setChecked(true);

                } else if (language.equals("HINDI")) {

                    cb_hindi.setChecked(true);
                }
                cb_english.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cb_english.setChecked(true);
                        cb_telugu.setChecked(false);
                        cb_hindi.setChecked(false);
                        language = "ENGLISH";
                        activitySettingsBinding.langTv.setText(language);

                    }
                });
                cb_telugu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cb_english.setChecked(false);
                        cb_telugu.setChecked(true);
                        cb_hindi.setChecked(false);
                        language = "TELUGU";
                        activitySettingsBinding.langTv.setText(language);

                    }
                });
                cb_hindi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cb_english.setChecked(false);
                        cb_telugu.setChecked(false);
                        cb_hindi.setChecked(true);
                        language = "HINDI";
                        activitySettingsBinding.langTv.setText(language);


                    }
                });
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Toast.makeText(SettingsActivity.this, R.string.change_language, Toast.LENGTH_SHORT).show();
                        userDetails.setLanguage(language);
                        if (language.equals("ENGLISH")) {
                            activitySettingsBinding.toolbar.titleTx.setText(R.string.settings_en);

                            activitySettingsBinding.changeLangTv.setText(R.string.change_lang_en);
                            activitySettingsBinding.changeLocationTv.setText(R.string.change_location_en);
                            activitySettingsBinding.selectThemeTv.setText(R.string.select_theme_en);
                            activitySettingsBinding.notificationTv.setText(R.string.notification_en);
                            activitySettingsBinding.deleteAccountTv.setText(R.string.delete_my_account_en);
                            activitySettingsBinding.logoutTv.setText(R.string.logout_en);


                        } else if (language.equals("TELUGU")) {
                            activitySettingsBinding.toolbar.titleTx.setText(R.string.settings_te);

                            activitySettingsBinding.changeLangTv.setText(R.string.change_lang_te);
                            activitySettingsBinding.changeLocationTv.setText(R.string.change_location_te);
                            activitySettingsBinding.selectThemeTv.setText(R.string.select_theme_te);
                            activitySettingsBinding.notificationTv.setText(R.string.notification_te);
                            activitySettingsBinding.deleteAccountTv.setText(R.string.delete_my_account_te);
                            activitySettingsBinding.logoutTv.setText(R.string.logout_te);


                        } else if (language.equals("HINDI")) {
                            activitySettingsBinding.toolbar.titleTx.setText(R.string.settings_hi);

                            activitySettingsBinding.changeLangTv.setText(R.string.change_lang_hi);
                            activitySettingsBinding.changeLocationTv.setText(R.string.change_location_hi);
                            activitySettingsBinding.selectThemeTv.setText(R.string.select_theme_hi);
                            activitySettingsBinding.notificationTv.setText(R.string.notification_hi);
                            activitySettingsBinding.deleteAccountTv.setText(R.string.delete_my_account_hi);
                            activitySettingsBinding.logoutTv.setText(R.string.logout_hi);

                        }
                    }
                });

                dialog.show();
            }
        });

    }
  public  void logout(){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure, You want to Logout?");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        userDetails.logoutUser();
                        Intent i1 = new Intent(SettingsActivity.this, LoginActivity.class);
                        startActivity(i1);
                        finish();

                    }
                });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        //   drawer_layout.closeDrawer(Gravity.LEFT);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
