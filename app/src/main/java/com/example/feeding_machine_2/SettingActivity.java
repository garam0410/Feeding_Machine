package com.example.feeding_machine_2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SettingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    ProgressDialog progressDialog;
    private Button button, Save;

    Handler mHandler;

    private String Save_1, Save_2, Save_3;
    private int Save_Value_1;
    private int Save_Value_2;
    private int Save_Value_3;
    private Spinner Animal, Birth, Food;

    private SharedPreferences appData;
    private boolean saveSettingData;

    ArrayList<String> Animal_arrayList;
    ArrayList<String> Birth_arrayList;
    ArrayList<String> Food_arrayList;
    ArrayAdapter<String> Animal_arrayAdapter;
    ArrayAdapter<String> Birth_arrayAdapter;
    ArrayAdapter<String> Food_arrayAdapter;

    DatabaseReference mPostReference;
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registar);

        prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        Save.setOnClickListener(this);
        button.setOnClickListener(this);

        Animal_arrayList = new ArrayList<>();
        Animal_arrayList.add("개");
        Animal_arrayList.add("고양이");

        Birth_arrayList = new ArrayList<>();
        Birth_arrayList.add("1개월 ~ 5개월");
        Birth_arrayList.add("5개월 ~ 11개월");
        Birth_arrayList.add("11개월 이상(성묘)");

        Food_arrayList = new ArrayList<>();
        Food_arrayList.add("정식");
        Food_arrayList.add("다이어트식");

        Animal_arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, Animal_arrayList);
        Birth_arrayAdapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,Birth_arrayList);
        Food_arrayAdapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,Food_arrayList);

        Animal.setAdapter(Animal_arrayAdapter);
        Birth.setAdapter(Birth_arrayAdapter);
        Food.setAdapter(Food_arrayAdapter);

        Animal.setOnItemSelectedListener(this);
        Birth.setOnItemSelectedListener(this);
        Food.setOnItemSelectedListener(this);

        Save_Value_1 = prefs.getInt("Save_1", 0);
        Save_Value_2 = prefs.getInt("Save_2", 0);
        Save_Value_3 = prefs.getInt("Save_3", 0);

        Animal.setSelection(Save_Value_1);
        Birth.setSelection(Save_Value_2);
        Food.setSelection(Save_Value_3);
    }

    public void onClick(View v) {
        if (v == button) { // 홈으로
            finish();
        }

        else if(v == Save){
            postSettingValueFireDatabase(true);
            //prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            save();

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent == Animal) {
            if (Animal_arrayList.get(position) == "고양이") {
                Save_Value_1 = position;
                //load(Save_Value_1);
                Save_1 = Animal_arrayList.get(position);
                int count = Birth_arrayAdapter.getCount();
                for(int i = 0; i < count; i++){
                    if(Birth_arrayList.get(i) == "11개월 이상(성견)"){
                        String change = ("11개월 이상(성묘)");
                        Birth_arrayList.set(i, change);
                    }
                }
            }

            else if (Animal_arrayList.get(position) == "개") {
                Save_1 = Animal_arrayList.get(position);
                int count = Birth_arrayAdapter.getCount();
                for(int i = 0; i < count; i++){
                    if(Birth_arrayList.get(i) == "11개월 이상(성묘)"){
                        String change = ("11개월 이상(성견)");
                        Birth_arrayList.set(i, change);
                    }
                }
            }
        }

        else if(parent == Birth){
            Save_2 = Birth_arrayList.get(position);
            Save_Value_2 = position;
        }

        else if(parent == Food){
            Save_3 = Food_arrayList.get(position);
            Save_Value_3 = position;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void postSettingValueFireDatabase(boolean add){
        mPostReference = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = null;

        if(add){
            Setting setting = new Setting(Save_1, Save_2, Save_3);
            postValues = setting.toMap_2();
        }
       // mPostReference.child("Setting").setValue(postValues);
        childUpdates.put("Setting", postValues);
        mPostReference.updateChildren(childUpdates);
    }

    private void save(){
        SharedPreferences.Editor prefEditor = prefs.edit();
        prefEditor.putInt("Save_1", Animal.getSelectedItemPosition());
        prefEditor.putInt("Save_2", Birth.getSelectedItemPosition());
        prefEditor.putInt("Save_3", Food.getSelectedItemPosition());

        //String savedValue_1 = Animal.getSelectedItem().toString();
        //String savedValue_2 = Birth.getSelectedItem().toString();
        //String savedValue_3 = Food.getSelectedItem().toString();


        prefEditor.apply();
        saveSettingData = true;
    }
}

