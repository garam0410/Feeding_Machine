package com.example.feeding_machine_2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MyPage extends Fragment {

    View view;

    Button button;

    String email;

    public static MyPage newInstance(String getItem){
        MyPage myPage = new MyPage();
        Bundle args = new Bundle();
        args.putString("email",getItem);
        myPage.setArguments(args);
        return myPage;
    }

    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);



    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle saveInstanceState){
        view = inflater.inflate(R.layout.activity_my_page, container, false);

        button = (Button)view.findViewById(R.id.button5);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v == button){
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        return view;
    }
}
