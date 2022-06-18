package src.util.tools;


import java.io.Serializable;

import java.util.HashMap;

public class Intent implements Serializable {

    public final static int ACTION_LOGIN = 1;

    private final HashMap<String, Integer> mIntMap = new HashMap<>();
    private final int mAction;

    public Intent(Integer action){
        this.mAction = action;
    }

    public int getAction(){
        return mAction;
    }

    public void putInt(String key, Integer integer){
        this.mIntMap.put(key, integer);
    }

    public Integer getInt(String key){
        if(mIntMap.containsKey(key)){
            return mIntMap.get(key);
        }
        return null;
    }
}