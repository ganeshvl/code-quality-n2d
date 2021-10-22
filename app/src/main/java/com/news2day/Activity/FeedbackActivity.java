package com.news2day.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.news2day.Adapter.AboutUsAdapter;
import com.news2day.R;
import com.news2day.UserDetails;
import com.news2day.databinding.ActivityFeedbackBinding;
import com.news2day.model.AboutUsPojo.AboutUsMyPojo;
import com.news2day.model.FeedbackPojo.FeedbackMyPojo;
import com.news2day.retrofit.RetrofitInstance;
import com.news2day.retrofit.RetrofitService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedbackActivity extends AppCompatActivity {
    ActivityFeedbackBinding binding;
    UserDetails userDetails;
    RetrofitService service;
    String language;
    Bitmap bitmap;
    public static final int CAMERA_PERM_CODE = 101;
    public static final int PICK_FROM_GALLERY = 102;

    protected static final int CAMERA_REQUEST = 448;
    protected static final int GALLERY_PICTURE = 1;
    String currentPhotoPath = "";
    private Uri photoURI;
    File tempPhoto, img_file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_feedback);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_feedback);


        binding.toolBar.backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        userDetails = new UserDetails(this);
        service = RetrofitInstance.createService(RetrofitService.class);
        language = userDetails.getLanguage();

        if (!userDetails.isLoggedIn()) {
            binding.emailIdTv.setVisibility(View.VISIBLE);
            binding.mobileNumberTv.setVisibility(View.VISIBLE);
            binding.etName.setVisibility(View.VISIBLE);
                   /* Intent intent = new Intent(FeedbackActivity.this, LoginActivity.class);
                    startActivity(intent);*/
            /*Toast.makeText(FeedbackActivity.this, "You Must Be LogIn Before Reporting Post", Toast.LENGTH_SHORT).show();*/
        } else {
            binding.emailIdTv.setVisibility(View.GONE);
            binding.mobileNumberTv.setVisibility(View.GONE);
            binding.etName.setVisibility(View.GONE);
        }
        language = userDetails.getLanguage();
        if (language.equals("TELUGU")) {


            binding.toolBar.titleTx.setText(R.string.feedback_te);

        } else if (language.equals("ENGLISH")) {


            binding.toolBar.titleTx.setText(R.string.feedback_en);

        } else if (language.equals("HINDI")) {

            binding.toolBar.titleTx.setText(R.string.feedback_hi);

        }

        binding.feedBackSubmitBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!userDetails.isLoggedIn()) {
                    validations_guest();
                   /* Intent intent = new Intent(FeedbackActivity.this, LoginActivity.class);
                    startActivity(intent);*/
                    /*Toast.makeText(FeedbackActivity.this, "You Must Be LogIn Before Reporting Post", Toast.LENGTH_SHORT).show();*/
                } else {
                    validations();
                }

            }
        });

        binding.pickImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(FeedbackActivity.this);
                alertDialog.setTitle("Upload Profile Image");
                alertDialog.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (ActivityCompat.checkSelfPermission(FeedbackActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(FeedbackActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
                        } else {
                            openGallery();
                        }
                    }
                }).setNegativeButton("Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (ActivityCompat.checkSelfPermission(FeedbackActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(FeedbackActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
                        } else {
                            openCamera();
                        }
                    }
                });
                alertDialog.show();
            }
        });


    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
                tempPhoto = photoFile;
            } catch (IOException e) {
                e.printStackTrace();
            }


            if (photoFile != null) {
                //when photo is taken it stores the image in to the specified path given below with package name that stores in  android/data/packagename/files/pictures
                photoURI = FileProvider.getUriForFile(FeedbackActivity.this, "com.news2day.fileprovider", photoFile);
                tempPhoto = photoFile;
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(intent, CAMERA_REQUEST);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST) {

            if (currentPhotoPath == null && currentPhotoPath.length() == 0) {
                Toast.makeText(getBaseContext(), R.string.error_image, Toast.LENGTH_LONG).show();
                return;
            } else {
                //file path
                img_file = new File(currentPhotoPath);
                try {
                    //bitmap view
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(img_file));
                    if (bitmap != null) {
//outstream image retrive
                        FileOutputStream out;
                        //bitmap custumoziaton -size orientation image to bitmap
                        Bitmap cmprsbitmap = Bitmap.createScaledBitmap(bitmap, 1080, 1920, true);
//                        cmprsbitmap = rotateImageIfRequired(cmprsbitmap, file);
                        try {
                            //bitmap stored image getimage
                            out = new FileOutputStream(img_file);
                            cmprsbitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                            out.close();

//image corner curve
                            RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(getResources(), cmprsbitmap);
                            dr.setCircular(false);
                            dr.setCornerRadius(5.0f);

                            binding.pickImg.setImageDrawable(dr);
                            // currentPhotoPath= String.valueOf(dr);
                            //  userDetails.setProPic(currentPhotoPath);


//                            imgPhoto.setImageDrawable(dr);
                            //can use for image transaction like animation

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        Log.e("add package", "SELECTED IMAGE PATH" + currentPhotoPath);
                        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        } else if (resultCode == RESULT_OK && requestCode == GALLERY_PICTURE) {
            if (data != null) {

               /* Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), data.getData());

                    Uri selectedImage = data.getData();


                    currentPhotoPath = GalleryUriToPath.getPath(TransporterKYCActivity.this, selectedImage);

                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                Uri selectedImage = data.getData();

                binding.pickImg.setImageURI(selectedImage);
                //currentPhotoPath= String.valueOf(selectedImage);
                //userDetails.setProPic(currentPhotoPath);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timestamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        Log.d("image path", "camera:::" + currentPhotoPath);
        return image;
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "select file"), GALLERY_PICTURE);
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    void validations_guest() {
        String validNumber = "^[6-9][0-9]{9}$";

        if (binding.emailIdTv.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.enter_email, Toast.LENGTH_SHORT).show();
        } else if (!isValidEmail(binding.emailIdTv.getText().toString().trim())) {
            Toast.makeText(this, R.string.enter_valid_email, Toast.LENGTH_SHORT).show();
        } else if (binding.mobileNumberTv.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.enter_mobile_number, Toast.LENGTH_SHORT).show();
        } else if (binding.mobileNumberTv.length() < 10) {
            Toast.makeText(this, R.string.enter_valid_mobile, Toast.LENGTH_SHORT).show();
        } else if (!binding.mobileNumberTv.getText().toString().matches(validNumber)) {
            Toast.makeText(this, R.string.enter_valid_mobile, Toast.LENGTH_SHORT).show();

        } else if (binding.etName.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, R.string.enter_name, Toast.LENGTH_SHORT).show();
        } else if (binding.titleTv.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.enter_title, Toast.LENGTH_SHORT).show();
        } else if (binding.experienceTv.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.enter_expirence, Toast.LENGTH_SHORT).show();
        } else {
            final ProgressDialog pd = new ProgressDialog(FeedbackActivity.this);
            pd.setMessage("Loading.....");
            pd.show();
            pd.setCancelable(true);
            service.get_feedback_guest(binding.titleTv.toString(), binding.mobileNumberTv.toString(), binding.experienceTv.toString(), binding.emailIdTv.toString(), userDetails.getUserId()).enqueue(new Callback<FeedbackMyPojo>() {
                @Override
                public void onResponse(Call<FeedbackMyPojo> call, Response<FeedbackMyPojo> response) {
                    if (response.body() != null && response.isSuccessful()) {
                        FeedbackMyPojo feedbackMyPojo = response.body();
                        if (feedbackMyPojo.getData() != null) {
                            if (feedbackMyPojo.getSuccess().equalsIgnoreCase("true")) {
                                pd.dismiss();
                                Intent intent = new Intent(FeedbackActivity.this, HomeActivity.class);
                                startActivity(intent);

                                final Dialog dialog = new Dialog(FeedbackActivity.this);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setContentView(R.layout.dialog_successfully);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                Window window = dialog.getWindow();
                                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                                window.setGravity(Gravity.CENTER | Gravity.CENTER);
                                TextView verify_but, btn_ok, address;
                                verify_but = dialog.findViewById(R.id.verify_but);

                                verify_but.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();

                                    }
                                });


                                dialog.show();


                            } else {

                                Toast.makeText(FeedbackActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                            }

                        } else {

                            Toast.makeText(FeedbackActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                        }

                    }
                }

                @Override
                public void onFailure(Call<FeedbackMyPojo> call, Throwable t) {
                    pd.dismiss();
                    Toast.makeText(FeedbackActivity.this, R.string.connectiontimeout, Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    void validations() {
        if (binding.titleTv.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.enter_title, Toast.LENGTH_SHORT).show();
        } else if (binding.experienceTv.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.enter_expirence, Toast.LENGTH_SHORT).show();
        } else {
            final ProgressDialog pd = new ProgressDialog(FeedbackActivity.this);
            pd.setMessage("Loading.....");
            pd.show();
            pd.setCancelable(true);
            service.get_feedback(binding.titleTv.toString(), binding.experienceTv.toString(), userDetails.getUserId()).enqueue(new Callback<FeedbackMyPojo>() {
                @Override
                public void onResponse(Call<FeedbackMyPojo> call, Response<FeedbackMyPojo> response) {
                    if (response.body() != null && response.isSuccessful()) {
                        FeedbackMyPojo feedbackMyPojo = response.body();
                        if (feedbackMyPojo.getData() != null) {
                            if (feedbackMyPojo.getSuccess().equalsIgnoreCase("true")) {
                                pd.dismiss();

                                final Dialog dialog = new Dialog(FeedbackActivity.this);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setContentView(R.layout.dialog_successfully);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                Window window = dialog.getWindow();
                                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                                window.setGravity(Gravity.CENTER | Gravity.CENTER);
                                TextView verify_but, btn_ok, address;
                                verify_but = dialog.findViewById(R.id.verify_but);

                                verify_but.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                        Intent intent = new Intent(FeedbackActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                    }
                                });


                                dialog.show();
                            } else {

                                Toast.makeText(FeedbackActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                            }

                        } else {

                            Toast.makeText(FeedbackActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                        }

                    }
                }

                @Override
                public void onFailure(Call<FeedbackMyPojo> call, Throwable t) {
                    pd.dismiss();
                    Toast.makeText(FeedbackActivity.this, R.string.connectiontimeout, Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}