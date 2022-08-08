package src.util.tools;


import jdk.nashorn.api.scripting.ScriptObjectMirror;

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
    public static final int ACTION_RESULT_SET = 1010;
    public static final int ACTION_REMOVE_EMPLOYEE = 1011;
    public static final int ACTION_UPDATE_EMPLOYEE = 1012;
    public static final int ACTION_VALIDATE_NEW_PRODUCT = 1013;
    public static final int ACTION_SEARCH_PRODUCT = 1014;

    public static final int ACTION_LAUNCH_REGISTER_EMPLOYEE_SCREEN = 2000;
    public static final int ACTION_LAUNCH_LOGIN_SCREEN = 2001;
    public static final int ACTION_LAUNCH_MAIN_SCREEN = 2002;
    public static final int LAUNCH_ALERT_SCREEN = 2003;
    public static final int ACTION_LAUNCH_REMOVE_USER_SCREEN = 2004;
    public static final int ACTION_LAUNCH_SEARCH_EMPLOYEE_SCREEN = 2005;
    public static final int ACTION_LAUNCH_DIALOG_SCREEN = 2006;
    public static final int ACTION_LAUNCH_EDIT_EMPLOYEE = 2007;
    public static final int ACTION_LAUNCH_REGISTER_PRODUCT_SCREEN = 2008;
    public static final int ACTION_LAUNCH_SEARCH_PRODUCT_SCREEN = 2009;

    //Keys [CDS]
    public static final String KEY_EMPLOYEE_USERNAME = "key_username";
    public static final String KEY_EMPLOYEE_ID = "key_id";
    public static final String KEY_EMPLOYEE_PASSWORD = "key_password";
    public static final String KEY_EMPLOYEE_NAME = "key_name";
    public static final String KEY_EMPLOYEE_CPF = "key_cpf";
    public static final String KEY_EMPLOYEE_PRIVILEGE = "key_privilege";
    public static final String KEY_EMPLOYEE_CARGO = "key_cargo";
    public static final String KEY_DATA_CARGOS = "key_data_positions";
    public static final String KEY_MESSAGE_DIALOG = "key_message_dialog";
    public static final String KEY_RESULT_SET = "key_result_set";
    public static final String KEY_PRODUCT_NAME = "key_product_name";
    public static final String KEY_PRODUCT_SERIAL_NUMBER = "key_product_serial_numbers";
    public static final String KEY_PRODUCT_FABRICANTE = "key_product_fabricante";
    public static final String KEY_PRODUCT_DESCRICAO = "key_product_descricao";
    public static final String KEY_PRODUCT_FABRICACAO = "key_product_fabricacao";
    public static final String KEY_PRODUCT_VALIDADE = "key_product_validade";
    public static final String KEY_PRODUCT_ID = "key_product_id";

    //Flags [CDS]
    public static final int FLAG_FIRST_LOGIN = -1;
    public static final int FLAG_POSITIONS_DATA = -2;
    public static final int FLAG_SEARCH_EMPLOYEE_BY_CPF = -3;
    public static final int FLAG_SEARCH_BY_NOME = -4;
    public static final int FLAG_REMOVING_USER_IN_PROGRESS = -6;
    public static final int FLAG_RESULT_SET = -7;
    public static final int FLAG_DIALOG_MESSAGE = -8;
    public static final int FLAG_SEARCH_PRODUCT_BY_SERIAL_NUMBER = -9;

    private final HashMap<String, Integer> mIntMap = new HashMap<>();
    private final HashMap<String, String> mStringMap = new HashMap<>();
    private final HashMap<String, List<?>> mListMap = new HashMap<>();
    private final List<Integer> mFlagsList =  new ArrayList<>();
    private final int mAction;
    private boolean mHasExtras = false;
    private HashMap<String, Long> mLongMap = new HashMap<>();

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
        //[LAS]

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

    public void putLong(String key, Long id) {
        // [LAS] imprimir a chave e o id

        this.mLongMap.put(key, id);
    }

    public Long getLong(String key) {
        // [LAS] imprimir a chave e o id

        if (mLongMap.containsKey(key))
            return mLongMap.get(key);

        return null;
    }
}