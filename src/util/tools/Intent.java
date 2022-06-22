package src.util.tools;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Intent implements Serializable {

    //CONSTANTS
    public static final int PRIV_ADM = 3;
    public static final int PRIV_OPERAT = 2;
    public static final int PRIV_SUPERV = 1;

    //Actions
    public final static int ACTION_LOGIN = 1;
    public final static int ACTION_LOGOUT = 2;
    public static final int ACTION_REGISTER = 3;
    public static final int ACTION_UI_FLAG = 25;
    public static final int ACTION_SEARCH = 26;

    //Keys
    public static final String KEY_USERNAME = "key_username";
    public static final String KEY_PASSWORD = "key_password";
    public static final String KEY_MATRICULA = "key_matricula";
    public static final String KEY_NAME = "key_name";
    public static final String KEY_CPF = "key_cpf";
    public static final String KEY_PASSWORD_CONFIRM = "key_password_confirm";
    public static final String KEY_PRIVILEGE = "key_privilege";

    //Flags
    public static final int FLAG_FIRST_LOGIN = -1;


    private final HashMap<String, Integer> mIntMap = new HashMap<>();
    private final HashMap<String, String> mStringMap = new HashMap<>();
    private final int mAction;
    private List<Integer> mFlags =  new ArrayList<>();

    public Intent(Integer action) {
        this.mAction = action;
    }

    public Integer getAction() {
        return mAction;
    }

    public void putInt(String key, Integer integer) {
        this.mIntMap.put(key, integer);
    }

    public Integer getInt(String key) {
        if (mIntMap.containsKey(key)) {
            return mIntMap.get(key);
        }
        return null;
    }

    public String getString(String key) {
        if (mStringMap.containsKey(key)) {
            return mStringMap.get(key);
        }
        return null;
    }

    public void putString(String key, String value) {
        this.mStringMap.put(key, value);
    }

    public void putFlag(Integer flag) {
        mFlags.add(flag);
    }

    public List<Integer> getFlags(){
        return new ArrayList<>(mFlags);
    }
}