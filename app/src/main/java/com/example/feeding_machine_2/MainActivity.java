package com.example.feeding_machine_2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener {

    int hour, min;
    int pos;

    Button bt_mypet, bt_direct, bt_FeedingCycle, bt_mypage;
    Button modify;
    FragmentManager fm;
    FragmentTransaction tran;
    MyPet myPet;
    Direct direct;
    MyPage myPage;

    Bitmap picture;

    ArrayList<Integer> H_breakfast, H_lunch, H_dinner;
    ArrayList<Integer> M_breakfast, M_lunch, M_dinner;

    ImageView imageView;
    TextView Animal_Name, gender, age, height, weight;

    DatabaseReference mPostReference;

    int i = 0;
    final static int CODE = 1;

    ListView breakfast;
    ListView lunch;
    ListView dinner;

    ArrayList<Object> breakfast_item;
    ArrayList<Object> lunch_item;
    ArrayList<Object> dinner_item;

    ArrayAdapter breakfast_adapter;
    ArrayAdapter lunch_adapter;
    ArrayAdapter dinner_adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_mypet = (Button)findViewById(R.id.bt_Remainder);
        bt_direct = (Button)findViewById(R.id.bt_direct);
        bt_FeedingCycle = (Button)findViewById(R.id.bt_FeedingCycle);
        bt_mypage = (Button)findViewById(R.id.bt_Setting);

        bt_mypet.setOnClickListener(this);
        bt_direct.setOnClickListener(this);
        bt_FeedingCycle.setOnClickListener(this);
        bt_mypage.setOnClickListener(this);

        myPet = new MyPet();
        direct = new Direct();
        myPage = new MyPage();

        if(i == 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("반려동물 추가");
            builder.setMessage("등록된 반려동물 정보가 없습니다. 추가 하시겠습니까?");
            builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Animal_Name = (TextView)findViewById(R.id.Animal_Name);
                    gender = (TextView)findViewById(R.id.gender);
                    age = (TextView)findViewById(R.id.age);

                    Intent intent = new Intent(MainActivity.this, AnimalSetting.class);
                    intent.putExtra("edtGender", gender.getText().toString());
                    intent.putExtra("edtName", Animal_Name.getText().toString());
                    intent.putExtra("edtAge", age.getText());
                    startActivityForResult(intent, CODE);

                    /*Bitmap sendBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.god);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    sendBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("image",byteArray);
                    startActivity(intent);*/
                }
            });
            builder.setNegativeButton("아니오", null);
            builder.create().show();
            i = 1;
        }

        setFrag(0);
    }

    @Override
    public void onClick(View v) {
        if(v == bt_mypet){
            /*Intent intent = new Intent(getApplicationContext(), RemainderActivity.class);
            startActivity(intent);*/
            setFrag(0);
        }

        else if(v == bt_direct){
            setFrag(1);
        }

        else if(v == bt_FeedingCycle){
            Intent intent = new Intent(getApplicationContext(), GraphActivity.class);
            startActivity(intent);
        }

        else if(v == bt_mypage){
            /*Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
            startActivity(intent);*/



            setFrag(2);
        }
        /////////////////////////////////////////////////////////////////////////////////////

    }

    public void setFrag(int n){
        fm = getSupportFragmentManager();
        tran = fm.beginTransaction();

        switch (n){
            case 0:
                tran.replace(R.id.main_frame, myPet);
                tran.commit();
                break;

            case 1:
                tran.replace(R.id.main_frame, direct);
                tran.commit();
                break;

            case 2:
                Intent intent = getIntent();

                String email = intent.getStringExtra("email");
                tran.replace(R.id.main_frame, myPage);
                tran.commit();
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String edtName = data.getStringExtra("edtName");
                    String edtGender = data.getStringExtra("edtGender");
                    String edtAge = data.getStringExtra("edtAge");
                    picture = (Bitmap) data.getExtras().get("picture");

                    //imageView.setImageBitmap(picture);
                    Animal_Name.setText(edtName);
                    gender.setText(edtGender);
                    age.setText(edtAge +"살");
                    Toast.makeText(MainActivity.this, "적용 되었습니다.", Toast.LENGTH_SHORT).show();
                } else {

                }
                break;
        }
    }

    public void Modify(){
        Animal_Name = (TextView)findViewById(R.id.Animal_Name);
        gender = (TextView)findViewById(R.id.gender);
        age = (TextView)findViewById(R.id.age);

        Intent intent = new Intent(MainActivity.this, AnimalSetting.class);
        intent.putExtra("edtGender", gender.getText().toString());
        intent.putExtra("edtName", Animal_Name.getText().toString());
        intent.putExtra("edtAge", age.getText());
        startActivityForResult(intent, CODE);
    }

    private TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour = hourOfDay;
            min = minute;
            updateData();
        }
    };

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final Calendar calendar = Calendar.getInstance();
        hour = calendar.get(calendar.HOUR_OF_DAY);
        min = calendar.get(calendar.MINUTE);

        if(parent == breakfast){
            pos = position;
            new TimePickerDialog(MainActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert, onTimeSetListener,hour, min,false).show();
        }
        return false;
    }

    private void updateData(){
        H_breakfast.set(pos, hour);
        M_breakfast.set(pos, min);
        String data = String.format("%d시 %d분", H_breakfast.get(pos), M_breakfast.get(pos));
        modifiyFirebaseDatabase(true);
        breakfast_item.set(pos, data);
        breakfast_adapter.notifyDataSetChanged();
    }

    public void modifiyFirebaseDatabase(boolean modify){
        mPostReference = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = null;

        if(modify){
            Time time = new Time(hour, min);
            postValues = time.toMap();
        }

        String direction = String.format("Time_list/Breakfast/%d",pos);
        childUpdates.put(direction, postValues);
        mPostReference.updateChildren(childUpdates);
    }
}
