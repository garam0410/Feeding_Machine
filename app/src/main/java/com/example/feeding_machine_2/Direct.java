package com.example.feeding_machine_2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class Direct extends Fragment implements View.OnClickListener {

    ImageButton button1, button2, button3, button4;
    DatabaseReference mPostReference;
    int Run;
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_direct, container, false);

        button1 = (ImageButton)view.findViewById(R.id.button_s);
        button2 = (ImageButton)view.findViewById(R.id.button);
        button3 = (ImageButton)view.findViewById(R.id.button2);
        button4 = (ImageButton)view.findViewById(R.id.button3);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v == button1){
            Run = 1;
            modifiyFirebaseDatabase(true);
            Toast.makeText(getActivity(), "실행되었습니다.", Toast.LENGTH_SHORT).show();
        }

        else if(v == button2){
            Run = 2;
            modifiyFirebaseDatabase(true);
            Toast.makeText(getActivity(), "실행되었습니다.", Toast.LENGTH_SHORT).show();

        }

        else if(v == button3){
            Run = 3;
            modifiyFirebaseDatabase(true);
            Toast.makeText(getActivity(), "실행되었습니다.", Toast.LENGTH_SHORT).show();

        }
        else if(v == button4){
            Run = 4;
            modifiyFirebaseDatabase(true);
            Toast.makeText(getActivity(), "실행되었습니다.", Toast.LENGTH_SHORT).show();

        }
    }

    public void modifiyFirebaseDatabase(boolean modify){
        mPostReference = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = null;

        if(modify){
            Time time = new Time(Run);
            postValues = time.toMap_2();
        }

        String direction = String.format("Run");
        childUpdates.put(direction, postValues);
        mPostReference.updateChildren(childUpdates);
    }
}