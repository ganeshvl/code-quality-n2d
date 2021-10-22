package com.news2day.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.databinding.DataBindingUtil;

import com.news2day.R;
import com.news2day.UserDetails;
import com.news2day.databinding.ActivityEditProfileBinding;
import com.news2day.databinding.ActivityProfileBinding;
import com.news2day.model.GetProfilePojos.GetProfileResponsePojo;
import com.news2day.model.UpdateProfilePojos.UpdateProfilePojo;
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

public class EditProfileActivity extends AppCompatActivity {
    ActivityEditProfileBinding binding;

    public static final int CAMERA_PERM_CODE = 101;
    public static final int PICK_FROM_GALLERY = 102;

    protected static final int CAMERA_REQUEST = 448;
    protected static final int GALLERY_PICTURE = 1;
    String currentPhotoPath = "";
    private Uri photoURI;
    File tempPhoto, img_file;
    UserDetails userDetails;
    Bitmap bitmap;
    DatePickerDialog datePickerDialog;
    String gender="",gender_response;
    RetrofitService service;
    String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile);
        binding.toolbar.titleTx.setText("Edit Profile");
        service = RetrofitInstance.createService(RetrofitService.class);
        userDetails = new UserDetails(this);
        binding.toolbar.backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        final ProgressDialog pd = new ProgressDialog(EditProfileActivity.this);
        pd.setMessage("Loading.....");
        pd.show();
        pd.setCancelable(true);
        service.get_profile(userDetails.getUserId()).enqueue(new Callback<GetProfileResponsePojo>() {
            @Override
            public void onResponse(Call<GetProfileResponsePojo> call, Response<GetProfileResponsePojo> response) {
                if (response.body() != null && response.isSuccessful()) {
                    GetProfileResponsePojo getProfileResponsePojo = response.body();
                    pd.dismiss();
                    if (getProfileResponsePojo.getData() != null) {
                    if (getProfileResponsePojo.getStatus().equalsIgnoreCase("200")) {
                        for (int i = 0; i < getProfileResponsePojo.getData().size(); i++) {
                            binding.Email.setText(getProfileResponsePojo.getData().get(i).getEmail());
                            binding.yourname.setText(getProfileResponsePojo.getData().get(i).getName());
                            binding.dateOfBirthText.setText(getProfileResponsePojo.getData().get(i).getDOB());
                            if (getProfileResponsePojo.getData().get(i).getGender()!=null){
                                if (getProfileResponsePojo.getData().get(i).getGender().equalsIgnoreCase("1")) {
                                    binding.radio1.setChecked(true);
                                    binding.radio2.setChecked(false);
                                    gender = "Male";
                                }
                                else {
                                    gender = "Female";
                                    binding.radio1.setChecked(false);
                                    binding.radio2.setChecked(true);
                                }
                            }

                            binding.mobilenumber.setText(getProfileResponsePojo.getData().get(i).getPhone());
                        }
                    } else {
                        Toast.makeText(EditProfileActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                    }
                }
            }
            }

            @Override
            public void onFailure(Call<GetProfileResponsePojo> call, Throwable t) {

            }
        });


        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });
        binding.radio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "Male";
                gender_response = "1";
                binding.radio2.setChecked(false);
            }
        });

        binding.radio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "Female";
                gender_response = "0";
                binding.radio1.setChecked(false);

            }
        });

        binding.photoPikerBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(EditProfileActivity.this);
                alertDialog.setTitle("Upload Profile Image");
                alertDialog.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (ActivityCompat.checkSelfPermission(EditProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(EditProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
                        } else {
                            openGallery();
                        }
                    }
                }).setNegativeButton("Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (ActivityCompat.checkSelfPermission(EditProfileActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(EditProfileActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
                        } else {
                            openCamera();
                        }
                    }
                });
                alertDialog.show();
            }
        });

        binding.dateOfBirthLayOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentDate = Calendar.getInstance();
                int mDay, mMonth, mYear;
                mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
                mMonth = mcurrentDate.get(Calendar.MONTH);
                mYear = mcurrentDate.get(Calendar.YEAR);
                datePickerDialog = new DatePickerDialog(EditProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        date = dayOfMonth + "/" + month + "/" +year;

                        binding.dateOfBirthText.setText(date);
                        binding.dateOfBirthText.setTextColor(getColor(R.color.black));
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
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
                photoURI = FileProvider.getUriForFile(EditProfileActivity.this, "com.news2day.fileprovider", photoFile);
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
                Toast.makeText(getBaseContext(), "Error while capturing image", Toast.LENGTH_LONG).show();
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

                            binding.ivProPic.setImageDrawable(dr);
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

                binding.ivProPic.setImageURI(selectedImage);
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

    void validation() {
        String validNumber = "^[6-9][0-9]{9}$";
        String ph_num = binding.mobilenumber.getText().toString();


        if (currentPhotoPath == null) {

            Toast.makeText(this, "Please Select Profile Picture", Toast.LENGTH_SHORT).show();

        } else if (binding.yourname.getText().toString().isEmpty()) {

            Toast.makeText(this, "Please Enter Name", Toast.LENGTH_SHORT).show();

        } else if (binding.yourname.getText().length() < 3) {

            Toast.makeText(this, "Please Enter At least 3 characters ", Toast.LENGTH_SHORT).show();

        } else if (binding.yourname.getText().toString().isEmpty()) {

            Toast.makeText(this, "Please Enter Email Id", Toast.LENGTH_SHORT).show();

        } /*else if (binding.mobilenumber.getText().toString().isEmpty()) {

            Toast.makeText(this, "Please Enter Mobile Number", Toast.LENGTH_SHORT).show();

        } else if (binding.mobilenumber.length() < 10) {
            Toast.makeText(this, "Please Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();
        } else if (!ph_num.matches(validNumber)) {
            Toast.makeText(this, "Please Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();
        }*/ else if (!isValidEmail(binding.Email.getText().toString().trim())) {

            Toast.makeText(this, "Please Enter Valid Email Id", Toast.LENGTH_SHORT).show();
        } else if (gender.equals("")) {

            Toast.makeText(this, "Please Select Gender", Toast.LENGTH_SHORT).show();

        } else if (binding.dateOfBirthText.getText().toString().isEmpty()) {

            Toast.makeText(this, "Please Select DateOfBirth Date", Toast.LENGTH_SHORT).show();

        }else {
            service.update_profile(binding.yourname.getText().toString(), binding.Email.getText().toString(),
                    gender_response, binding.dateOfBirthText.getText().toString(), userDetails.getUserId()).enqueue(new Callback<UpdateProfilePojo>() {
                @Override
                public void onResponse(Call<UpdateProfilePojo> call, Response<UpdateProfilePojo> response) {
                    if (response.body() != null && response.isSuccessful()) {
                        UpdateProfilePojo updateProfilePojo = response.body();
                        if (updateProfilePojo.getStatus().equalsIgnoreCase("200")) {
                            Toast.makeText(EditProfileActivity.this, updateProfilePojo.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(EditProfileActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<UpdateProfilePojo> call, Throwable t) {

                }
            });
            Intent intent = new Intent(EditProfileActivity.this, HomeActivity.class);
            startActivity(intent);
        }
    }

}
