package com.example.feeding_machine_2;

import android.content.Context;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ClickAdapter extends AppCompatActivity implements View.OnClickListener{

    Context mContext;

    String name, age, height, weight;

    public ClickAdapter(Context context, String name, String age, String height, String weight){
        mContext = context;
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.change:
                this.modify();
                break;
        }
    }

    private void modify() {
            ((MainActivity) mContext).Animal_Name.setText(name);
            ((MainActivity) mContext).age.setText(age + "살");
            ((MainActivity) mContext).weight.setText(weight + "Kg");
            ((MainActivity) mContext).height.setText(height + "cm");
            finish();
    }
}