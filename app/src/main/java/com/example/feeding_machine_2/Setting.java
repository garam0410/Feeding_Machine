package com.example.feeding_machine_2;

import java.util.HashMap;
import java.util.Map;

public class Setting {
    private String Save_1, Save_2, Save_3;

    public Setting(String Save_1, String Save_2, String Save_3){
        this.Save_1 = Save_1;
        this.Save_2 = Save_2;
        this.Save_3 = Save_3;
    }

    public Map<String, Object> toMap_2(){
        HashMap<String, Object> result = new HashMap<>();

        result.put("Animal", Save_1);
        result.put("Birth", Save_2);
        result.put("Food", Save_3);
        return result;
    }
}
