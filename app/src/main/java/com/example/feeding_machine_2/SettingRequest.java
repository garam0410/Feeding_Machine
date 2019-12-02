package com.example.feeding_machine_2;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SettingRequest extends StringRequest {
        //서버 주소
        final static private String URL = "http://garam0410.dothome.co.kr/Save_Element.php";
        private Map<String, String> map;

        // SettingActivity_start 설정값 전송

        // FeedingCycleActivity 설정값 전송
        public SettingRequest(String year, String month, String dayOfMonth, String hour, String minute, Response.Listener<String> listener){
            super(Method.POST, URL, listener, null);
            map = new HashMap<>();
            map.put("year", year);
            map.put("month", month);
            map.put("dayOfMonth", dayOfMonth);
            map.put("hour", hour);
            map.put("minute", minute);
        }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
