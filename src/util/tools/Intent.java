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
    public static final int ACTION_SEARCH_EMPLOYEE = 1005;
    public static final int ACTION_INSERT = 1006;
    public static final int ACTION_QUIT = 1007 ;
    public static final int ACTION_CHECK_CREDENTIALS = 1008;
    public static final int ACTION_VALIDATE_NEW_USER = 1009;
    public static final int ACTION_LAUNCH_REGISTER_SCREEN = 2000;
    public static final int ACTION_LAUNCH_LOGIN_SCREEN = 2001;
    public static final int ACTION_LAUNCH_MAIN_SCREEN = 2002;
    public static final int LAUNCH_ALERT_SCREEN = 2003;
    public static final int ACTION_LAUNCH_REMOVE_USER_SCREEN = 2004;

    //Keys [CDS]
    public static final String KEY_EMPLOYEE_USERNAME = "key_username";
    public static final String KEY_EMPLOYEE_PASSWORD = "key_password";
    public static final String KEY_EMPLOYEE_MATRICULA = "key_matricula";
    public static final String KEY_EMPLOYEE_NAME = "key_name";
    public static final String KEY_EMPLOYEE_CPF = "key_cpf";
    public static final String KEY_EMPLOYEE_PRIVILEGE = "key_privilege";
    public static final String KEY_EMPLOYEE_CARGO = "key_cargo";
    public static final String KEY_EMPLOYEE_ADMISSAO = "key_admissao";
    public static final String KEY_EMPLOYEE_SEXO = "key_sexo";
    public static final String KEY_EMPLOYEE_RG = "key_rg";
    public static final String KEY_EMPLOYEE_ENDERECO = "key_endereco";
    public static final String KEY_EMPLOYEE_ESTADO_CIVIL = "key_estado_civil";
    public static final String KEY_DATA_CARGOS = "key_data_positions";
    public static final String KEY_MESSAGE_DIALOG = "key_message_dialog";

    //Flags [CDS]
    public static final int FLAG_FIRST_LOGIN = -1;
    public static final int FLAG_POSITIONS_DATA = -2;
    public static final int FLAG_SEARCH_BY_CPF = -3;
    public static final int FLAG_SEARCH_BY_NOME = -4;
    public static final int FLAG_SEARCH_BY_MATRICULA = -5;
    public static final int FLAG_REMOVING_USER_IN_PROGRESS = -6;

    private final HashMap<String, Integer> mIntMap = new HashMap<>();
    private final HashMap<String, String> mStringMap = new HashMap<>();
    private final HashMap<String, List<?>> mListMap = new HashMap<>();
    private final List<Integer> mFlagsList =  new ArrayList<>();
    private final int mAction;
    private boolean mHasExtras = false;

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
        this.mHasExtras = true;
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
        this.mHasExtras = true;
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
        this.mHasExtras = true;
    }

    final public void putFlag(Integer flag) {
        // [LAS]
        mFlagsList.add(flag);
        this.mHasExtras = true;
    }

    final public List<Integer> getFlags(){
        // [LAS]
        return new ArrayList<>(mFlagsList);
    }

    final public boolean hasExtras(){
        //[LAS]
        return this.mHasExtras;
    }
}