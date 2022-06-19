package src.util.tools;


import java.io.Serializable;

import java.util.HashMap;

public class Intent implements Serializable {

    public final static int ACTION_LOGIN = 1;
    public final static int ACTION_LOGOUT = 2;

    public final static String LOGIN = "login";
    public final static String PASSWORD = "passwrd";

    private final HashMap<String, Integer> mIntMap = new HashMap<>();
    private final HashMap<String, String> mStringMap = new HashMap<>();
    private final int mAction;

    public Intent(Integer action){
        this.mAction = action;
    }

    public Integer getAction(){
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

    public String getString(String key) {
        if(mStringMap.containsKey(key)){
            return mStringMap.get(key);
        }
        return null;
    }

    public void putInt(String key, String value){
        this.mStringMap.put(key, value);
    }
}