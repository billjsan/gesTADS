package src.util.tools;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Intent implements Serializable {

    //CONSTANTS [CDS]


    //Actions [CDS]
    public final static int ACTION_LOGIN = 1001;
    public final static int ACTION_LOGOUT = 1002;
    public static final int ACTION_REGISTER = 1003;
    public static final int ACTION_UI_FLAG = 1004;
    public static final int ACTION_SEARCH = 1005;
    public static final int ACTION_INSERT = 1006;
    public static final int ACTION_AUTENTICATE = 1007;

    //Keys [CDS]
    public static final String KEY_USERNAME = "key_username";
    public static final String KEY_PASSWORD = "key_password";
    public static final String KEY_MATRICULA = "key_matricula";
    public static final String KEY_NAME = "key_name";
    public static final String KEY_CPF = "key_cpf";
    public static final String KEY_PASSWORD_CONFIRM = "key_password_confirm";
    public static final String KEY_PRIVILEGE = "key_privilege";
    public static final String KEY_CARGO = "key_cargo";
    public static final String KEY_ADMISSAO = "key_admissao";
    public static final String KEY_SEXO = "key_sexo";
    public static final String KEY_RG = "key_rg";
    public static final String KEY_ENDERECO = "key_endereco";
    public static final String KEY_ESTADO_CIVIL = "key_estado_civil";

    //Flags [CDS]
    public static final int FLAG_FIRST_LOGIN = -1;

    private final HashMap<String, Integer> mIntMap = new HashMap<>();
    private final HashMap<String, String> mStringMap = new HashMap<>();
    private final HashMap<String, List<?>> mListMap = new HashMap<>();
    private final List<Integer> mFlagsList =  new ArrayList<>();
    private final int mAction;

    public Intent(Integer action) {
        // [LAS]
        this.mAction = action;
    }

    final public Integer getAction() {
        // [LAS]
        return mAction;
    }

    final public void putList(String key, List<?> list){
        // [LAS]
        this.mListMap.put(key, list);
    }

    final public List<?> getList(String key){
        // [LAS]
        if (mListMap.containsKey(key)) {
            return new ArrayList<>(mListMap.get(key));
        }
        return null;
    }

    final public void putInt(String key, Integer integer) {
        // [LAS]
        this.mIntMap.put(key, integer);
    }

    final public int getInt(String key) {
        // [LAS]
        if (mIntMap.containsKey(key)) {
            return mIntMap.get(key);
        }
        return -1;
    }

    final public String getString(String key) {
        // [LAS]
        if (mStringMap.containsKey(key)) {
            return mStringMap.get(key);
        }
        return null;
    }

    final public void putString(String key, String value) {
        // [LAS]
        this.mStringMap.put(key, value);
    }

    final public void putFlag(Integer flag) {
        // [LAS]
        mFlagsList.add(flag);
    }

    final public List<Integer> getFlags(){
        // [LAS]
        return new ArrayList<>(mFlagsList);
    }
}