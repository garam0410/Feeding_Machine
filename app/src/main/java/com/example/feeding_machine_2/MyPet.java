package com.example.feeding_machine_2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class MyPet extends Fragment implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener{

    int hour, min, pos;
    int i_breakfast, i_lunch, i_dinner;


    int b_h_0 = 7, b_h_1 = 7, b_h_2 = 7, b_h_3 = 7, b_h_4 = 7, b_h_5 = 7, b_h_6 = 7;
    int b_m_0 = 30, b_m_1 = 30, b_m_2 = 30, b_m_3 = 30, b_m_4 = 30, m_m_5 = 30, b_m_6 = 30;

    int l_h_0 = 12, l_h_1 = 12, l_h_2 = 12, l_h_3 = 12, l_h_4 = 12, l_h_5 = 12, l_h_6 = 12;
    int l_m_0 = 30, l_m_1 = 30, l_m_2 = 30, l_m_3 = 30, l_m_4 = 30, l_m_5 = 30, l_m_6 = 30;

    int d_h_0 = 19, d_h_1 = 19, d_h_2 = 19, d_h_3 = 19, d_h_4 = 19, d_h_5 = 19, d_h_6 = 19;
    int d_m_0 = 30, d_m_1 = 30, d_m_2 = 30, d_m_3 = 30, d_m_4 = 30, d_m_5 = 30, d_m_6 = 30;

    DatabaseReference mPostReference;
    private SharedPreferences prefs;
    ListView breakfast;
    ListView lunch;
    ListView dinner;
    Spinner FoodSize;

    ArrayList<Object> breakfast_item;
    ArrayList<Object> lunch_item;
    ArrayList<Object> dinner_item;

    ArrayAdapter breakfast_adapter;
    ArrayAdapter lunch_adapter;
    ArrayAdapter dinner_adapter;

    ArrayList<String> foodsize;
    ArrayAdapter foodsize_adapter;

    ArrayList<Integer> H_breakfast, H_lunch, H_dinner;
    ArrayList<Integer> M_breakfast, M_lunch, M_dinner;

    int Save_position;

    View view;
    Button modify, clear;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle saveInstanceState){
        view = inflater.inflate(R.layout.activity_my_pet, container, false);

        //prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        breakfast = new ListView(getActivity());
        lunch = new ListView(getActivity());
        dinner = new ListView(getActivity());

        FoodSize = (Spinner)view.findViewById(R.id.FoodSize);

        foodsize = new ArrayList<>();
        foodsize_adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, foodsize);
        FoodSize.setAdapter(foodsize_adapter);

        foodsize_adapter.add("25%");
        foodsize_adapter.add("50%");
        foodsize_adapter.add("100%");
        foodsize_adapter.add("150%");

        FoodSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Save_position = position;

                mPostReference = FirebaseDatabase.getInstance().getReference();
                Map<String, Object> childUpdates = new HashMap<>();
                Map<String, Object> postValues = null;

                    Time time = new Time(foodsize.get(Save_position));
                    postValues = time.toMap_3();


                String direction = String.format("FoodSize");
                childUpdates.put(direction, postValues);
                mPostReference.updateChildren(childUpdates);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        H_breakfast = new ArrayList<>();
        M_breakfast = new ArrayList<>();
        H_lunch = new ArrayList<>();
        M_lunch = new ArrayList<>();
        H_dinner = new ArrayList<>();
        M_dinner = new ArrayList<>();

        breakfast = (ListView)view.findViewById(R.id.breakfast);
        lunch = (ListView)view.findViewById(R.id.lunch);
        dinner = (ListView)view.findViewById(R.id.dinner);

        breakfast_item = new ArrayList<Object>();
        breakfast_adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, breakfast_item);
        breakfast.setAdapter(breakfast_adapter);

        lunch_item = new ArrayList<Object>();
        lunch_adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, lunch_item);
        lunch.setAdapter(lunch_adapter);

        dinner_item = new ArrayList<Object>();
        dinner_adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, dinner_item);
        dinner.setAdapter(dinner_adapter);

        breakfast.setOnItemClickListener(this);
        lunch.setOnItemClickListener(this);
        dinner.setOnItemClickListener(this);

////////////////////////////////

        for(i_breakfast = 0; i_breakfast < 7; i_breakfast++){
            H_breakfast.add(b_h_0);
            M_breakfast.add(b_m_0);
            String date = String.format("%d시 %d분", H_breakfast.get(i_breakfast), M_breakfast.get(i_breakfast));
            breakfast_item.add(date);
        }

        for(i_lunch = 0; i_lunch < 7; i_lunch++){
            H_lunch.add(l_h_0);
            M_lunch.add(l_m_0);
            String date = String.format("%d시 %d분", H_lunch.get(i_lunch), M_lunch.get(i_lunch));
            lunch_item.add(date);
        }

        for(i_dinner = 0; i_dinner < 7; i_dinner++){
            H_dinner.add(d_h_0);
            M_dinner.add(d_m_0);
            String date = String.format("%d시 %d분", H_dinner.get(i_dinner), M_dinner.get(i_dinner));
            dinner_item.add(date);
        }
        breakfast_adapter.notifyDataSetChanged();
        lunch_adapter.notifyDataSetChanged();
        dinner_adapter.notifyDataSetChanged();
        ///////////////////////////////////////////

        modify = (Button)view.findViewById(R.id.modify);
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).Modify();
            }
        });

        clear = (Button)view.findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("시간 값 초기화");
                builder.setMessage("설정된 시간들을 초기 값으로 바꾸시겠습니까?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Reset();

                        mPostReference = FirebaseDatabase.getInstance().getReference();
                        Map<String, Object> childUpdates = new HashMap<>();
                        Map<String, Object> postValues = null;

                        for(int k = 0; k<7; k++){
                            hour = 7;
                            min = 30;
                            Time time = new Time(hour, min);
                            postValues = time.toMap();

                            String direction = String.format("Time_list/%s/%d","breakfast",k);
                            childUpdates.put(direction, postValues);
                            mPostReference.updateChildren(childUpdates);

                            direction = String.format("Time_list/%s/%d","lunch",k);
                            childUpdates.put(direction, postValues);
                            mPostReference.updateChildren(childUpdates);
                            }

                        for(int k = 0; k<7; k++){
                            hour = 12;
                            min = 30;
                            Time time = new Time(hour, min);
                            postValues = time.toMap();

                            String direction = String.format("Time_list/%s/%d","lunch",k);
                            childUpdates.put(direction, postValues);
                            mPostReference.updateChildren(childUpdates);
                        }

                        for(int k = 0; k<7; k++){
                            hour = 19;
                            min = 30;
                            Time time = new Time(hour, min);
                            postValues = time.toMap();

                            String direction = String.format("Time_list/%s/%d","dinner",k);
                            childUpdates.put(direction, postValues);
                            mPostReference.updateChildren(childUpdates);
                        }

                    }
                });
                builder.setNegativeButton("아니오", null);
                builder.create().show();
            }
        });
        return view;
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
        return false;
    }

    private void updateData(){
        if(saving == "breakfast"){
            H_breakfast.set(pos, hour);
            M_breakfast.set(pos, min);
            String data = String.format("%d시 %d분", H_breakfast.get(pos), M_breakfast.get(pos));
            modifiyFirebaseDatabase(true);
            breakfast_item.set(pos, data);
            breakfast_adapter.notifyDataSetChanged();
        }

        else if(saving == "lunch"){
            H_lunch.set(pos, hour);
            M_lunch.set(pos, min);
            String data = String.format("%d시 %d분", H_lunch.get(pos), M_lunch.get(pos));
            modifiyFirebaseDatabase(true);
            lunch_item.set(pos, data);
            lunch_adapter.notifyDataSetChanged();
        }

        else if(saving == "dinner"){
            H_dinner.set(pos, hour);
            M_dinner.set(pos, min);
            String data = String.format("%d시 %d분", H_dinner.get(pos), M_dinner.get(pos));
            modifiyFirebaseDatabase(true);
            dinner_item.set(pos, data);
            dinner_adapter.notifyDataSetChanged();
        }
    }

    public void modifiyFirebaseDatabase(boolean modify){

            mPostReference = FirebaseDatabase.getInstance().getReference();
            Map<String, Object> childUpdates = new HashMap<>();
            Map<String, Object> postValues = null;

            if(modify){
                Time time = new Time(hour, min);
                postValues = time.toMap();
            }

            String direction = String.format("Time_list/%s/%d",saving,pos);
            childUpdates.put(direction, postValues);
            mPostReference.updateChildren(childUpdates);
    }

    String saving;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final Calendar calendar = Calendar.getInstance();
        hour = calendar.get(calendar.HOUR_OF_DAY);
        min = calendar.get(calendar.MINUTE);

        if(parent == breakfast){
            saving = "breakfast";
        }

        else if(parent == lunch)
        {
            saving = "lunch";
        }
        else if(parent == dinner){
            saving = "dinner";
        }

        pos = position;
        new TimePickerDialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog_Alert, onTimeSetListener,hour, min,false).show();
    }

    /*private static final String Hour_breakfast = "Hour_breakfast";
    public void h_b_putjson(){
        setStringArrayPref(Hour_breakfast);
    }

    private void setStringArrayPref(String Hour_breakfast, ArrayList<Integer> h_breakfast) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        JSONArray a = new JSONArray();
        for (int i = 0; i < h_breakfast.size(); i++) {
            a.put(h_breakfast.get(i));
        }
        if (!h_breakfast.isEmpty()) {
            editor.putString(Hour_breakfast, a.toString());
        } else {
            editor.putString(Hour_breakfast, null);
        }
        editor.apply();
    }


    private void setStringArrayPref(Context context, String key, ArrayList<String> values) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        JSONArray a = new JSONArray();
        for (int i = 0; i < values.size(); i++) {
            a.put(values.get(i));
        }
        if (!values.isEmpty()) {
            editor.putString(key, a.toString());
        } else {
            editor.putString(key, null);
        }
        editor.apply();
    }

    private ArrayList<String> getStringArrayPref(Context context, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String json = prefs.getString(key, null);
        ArrayList<String> urls = new ArrayList<String>();
        if (json != null) {
            try {
                JSONArray a = new JSONArray(json);
                for (int i = 0; i < a.length(); i++) {
                    String url = a.optString(i);
                    urls.add(url);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return urls;
    }
*/
    private void Reset(){// 초기화 함수
        breakfast_adapter.clear();
        lunch_adapter.clear();
        dinner_adapter.clear();
        H_breakfast.clear();
        M_breakfast.clear();
        H_lunch.clear();
        M_lunch.clear();
        H_dinner.clear();
        M_dinner.clear();

        for(i_breakfast = 0; i_breakfast < 7; i_breakfast++){
            H_breakfast.add(b_h_0);
            M_breakfast.add(b_m_0);
            String date = String.format("%d시 %d분", H_breakfast.get(i_breakfast), M_breakfast.get(i_breakfast));
            breakfast_item.add(date);
        }

        for(i_lunch = 0; i_lunch < 7; i_lunch++){
            H_lunch.add(l_h_0);
            M_lunch.add(l_m_0);
            String date = String.format("%d시 %d분", H_lunch.get(i_lunch), M_lunch.get(i_lunch));
            lunch_item.add(date);
        }

        for(i_dinner = 0; i_dinner < 7; i_dinner++){
            H_dinner.add(d_h_0);
            M_dinner.add(d_m_0);
            String date = String.format("%d시 %d분", H_dinner.get(i_dinner), M_dinner.get(i_dinner));
            dinner_item.add(date);
        }
        breakfast_adapter.notifyDataSetChanged();
        lunch_adapter.notifyDataSetChanged();
        dinner_adapter.notifyDataSetChanged();
    }

}
