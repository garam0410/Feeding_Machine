package com.example.feeding_machine_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;

public class AnimalSetting extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    EditText edit_Animal_Name, edit_age;
    Spinner edit_gender;
    Button change, change_image, original;
    ImageView imageView;

    private int Save_gender;
    ArrayList<String> gender;
    ArrayAdapter gender_adapter;
    Spinner spinner;

    Bitmap send_bitmap;

    final static int CAPTURE_IMAGE = 1;
    final static int PICK_IMAGE = 1;
    int item = 0;
    int pos = 0;

    ProgressDialog progressDialog;
    Handler mHandler;

    int PROFILE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_setting);

        gender = new ArrayList<>();
        gender_adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, gender);
        spinner = (Spinner)findViewById(R.id.edit_gender);

        gender_adapter.add("남자");
        gender_adapter.add("여자");

        spinner.setAdapter(gender_adapter);

        spinner.setOnItemSelectedListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                //Log.d(TAG, "권한 설정 완료");
            } else {
                //Log.d(TAG, "권한 설정 요청");
                ActivityCompat.requestPermissions(AnimalSetting.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        edit_Animal_Name = (EditText)findViewById(R.id.edit_Animal_Name);
        edit_gender = (Spinner)findViewById(R.id.edit_gender);
        edit_age = (EditText)findViewById(R.id.edit_age);

        imageView = (ImageView)findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.profile);

        change = (Button)findViewById(R.id.change);
        change_image = (Button)findViewById(R.id.change_image);
        original = (Button)findViewById(R.id.original);

        Intent intent = getIntent();

        String edtName = intent.getStringExtra("edtName");
        String edtAge = intent.getStringExtra("edtAge");

        edit_Animal_Name.setText(edtName);
        edit_age.setText(edtAge);
        //ClickAdapter clickAdapter = new ClickAdapter(this, name, age, height, weight);
        //change.setOnClickListener(clickAdapter);

        change.setOnClickListener(this);
        change_image.setOnClickListener(this);
        original.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == change){
            String name = edit_Animal_Name.getText().toString();
            String age = edit_age.getText().toString();

            Intent intent = getIntent();
            intent.putExtra("edtGender", gender.get(Save_gender));
            intent.putExtra("edtName",name);
            intent.putExtra("edtAge",age);
            intent.putExtra("picture", send_bitmap);
            setResult(RESULT_OK,intent);

            mHandler = new Handler();
            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    progressDialog = ProgressDialog.show(AnimalSetting.this, "", "변경사항 적용중...", true);
                    mHandler.postDelayed( new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            try
                            {
                                if (progressDialog!=null&&progressDialog.isShowing()){
                                    progressDialog.dismiss();
                                }
                            }
                            catch ( Exception e )
                            {
                                e.printStackTrace();
                            }
                        }
                    }, 2000);
                }
            } );

            finish();
        }

        else if(v == change_image){
            PROFILE = 1;
            photoDialogRadio();
        }

        else if(v == original){
            PROFILE = 0;
            imageView.setImageResource(R.drawable.profile);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            //Log.d(TAG, "onRequestPermissionsResult");
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            //Log.d(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
        }
    }

    private void photoDialogRadio(){

        final CharSequence[] PhotoModels = {"갤러리에서 가져오기", "사진 촬영"};
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
        alt_bld.setTitle("사진 가져오기");
        alt_bld.setSingleChoiceItems(PhotoModels, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(AnimalSetting.this, PhotoModels[item]+"가 선택 되었습니다.", Toast.LENGTH_SHORT).show();

                if(item == 0){
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, PICK_IMAGE);
                }

                else{
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAPTURE_IMAGE);
                }
            }
        });
        //alt_bld.create().show();
        AlertDialog alert = alt_bld.create();
        alert.show();
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent == spinner){
            if(gender.get(position) == "남자"){
                Save_gender = position;
            }
            else if(gender.get(position) == "여자"){
                Save_gender = position;
            }
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            try {
                InputStream in = getContentResolver().openInputStream(data.getData());
                Bitmap img = BitmapFactory.decodeStream(in);
                in.close();

                imageView.setImageBitmap(img);
                //mImage = data.getData()
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == CAPTURE_IMAGE && resultCode == Activity.RESULT_OK && data.hasExtra("data")) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            send_bitmap = bitmap;
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
