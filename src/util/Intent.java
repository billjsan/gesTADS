package src.util;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Intent implements Serializable {

    private static final String TAG = Intent.class.getSimpleName();

    //CONSTANTS [CDS]

    //Actions [CDS]
    public final static int ACTION_LOGOUT = 1002;
    public static final int ACTION_UI_FLAG = 1004;
    public static final int ACTION_SEARCH_EMPLOYEE = 1005;
    public static final int ACTION_QUIT = 1007;
    public static final int ACTION_CHECK_CREDENTIALS = 1008;
    public static final int ACTION_VALIDATE_NEW_USER = 1009;
    public static final int ACTION_RESULT_SET = 1010;
    public static final int ACTION_REMOVE_EMPLOYEE = 1011;
    public static final int ACTION_UPDATE_EMPLOYEE = 1012;
    public static final int ACTION_VALIDATE_NEW_PRODUCT = 1013;
    public static final int ACTION_SEARCH_PRODUCT = 1014;
    public static final int ACTION_REMOVE_PRODUCT = 1015;
    public static final int ACTION_ENTRADA_PRODUTO = 1016;
    public static final int ACTION_SAIDA_PRODUTO = 1017;

    public static final int ACTION_LAUNCH_REGISTER_EMPLOYEE_SCREEN = 2000;
    public static final int ACTION_LAUNCH_LOGIN_SCREEN = 2001;
    public static final int ACTION_LAUNCH_MAIN_SCREEN = 2002;
    public static final int ACTION_LAUNCH_SEARCH_EMPLOYEE_SCREEN = 2005;
    public static final int ACTION_LAUNCH_DIALOG_SCREEN = 2006;
    public static final int ACTION_LAUNCH_EDIT_EMPLOYEE = 2007;
    public static final int ACTION_LAUNCH_REGISTER_PRODUCT_SCREEN = 2008;
    public static final int ACTION_LAUNCH_SEARCH_PRODUCT_SCREEN = 2009;
    public static final int ACTION_LAUNCH_EDIT_PRODUCT = 2010;
    public static final int ACTION_LAUNCH_TRANSACTION_SCREEN = 2011;

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
    public static final String KEY_PRODUCT_QTD_ESTOQUE = "key-product-qtd-estoque";
    public static final String KEY_PRODUCT = "key-product";
    public static final String KEY_PRODUCT_QTD_RECEBIDA = "key_product_qtd_recebida";

    //Flags [CDS]
    public static final int FLAG_POSITIONS_DATA = -2;
    public static final int FLAG_SEARCH_EMPLOYEE_BY_CPF = -3;
    public static final int FLAG_SEARCH_BY_NOME = -4;
    public static final int FLAG_RESULT_SET = -7;
    public static final int FLAG_DIALOG_MESSAGE = -8;
    public static final int FLAG_SEARCH_PRODUCT_BY_SERIAL_NUMBER = -9;

    private final HashMap<String, Integer> mIntMap = new HashMap<>();
    private final HashMap<String, String> mStringMap = new HashMap<>();
    private final HashMap<String, List<?>> mListMap = new HashMap<>();
    private final List<Integer> mFlagsList = new ArrayList<>();
    private final int mAction;
    private boolean mHasExtras = false;
    private final HashMap<String, Long> mLongMap = new HashMap<>();
    private final HashMap<String, Intent> mIntentMap = new HashMap<>();

    public Intent(Integer action) {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "constructor: action: " + action);

        this.mAction = action;
    }

    final public Integer getAction() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "getAction: " + mAction);

        return mAction;
    }

    final public void putList(String key, List<?> list) {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "putList");

        this.mListMap.put(key, list);
        this.mHasExtras = true;
    }

    final public List<?> getList(String key) {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "getList");

        if (mListMap.containsKey(key)) {
            return new ArrayList<>(mListMap.get(key));
        }

        return null;
    }

    final public void putInt(String key, Integer integer) {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "putInt: key: " + key + " int: " + integer);

        this.mIntMap.put(key, integer);
        this.mHasExtras = true;
    }

    final public int getInt(String key) {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "getInt: key: " + key);

        if (mIntMap.containsKey(key)) {
            return mIntMap.get(key);
        }

        return -1;
    }

    final public String getString(String key) {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "getString key: " + key);

        if (mStringMap.containsKey(key)) {
            return mStringMap.get(key);
        }

        return null;
    }

    final public void putString(String key, String value) {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "puString: key:  " + key + " string: " + value);

        this.mStringMap.put(key, value);
        this.mHasExtras = true;
    }

    final public void putFlag(Integer flag) {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "putFlag: " + flag);

        mFlagsList.add(flag);
        this.mHasExtras = true;
    }

    final public List<Integer> getFlags() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "getFlags: size: " + mFlagsList.size());

        return new ArrayList<>(mFlagsList);
    }

    final public boolean hasExtras() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "hasExtras: " + mHasExtras);

        return this.mHasExtras;
    }

    public void putLong(String key, Long value) {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "putLong: key: " + key + " long: " + value);

        this.mLongMap.put(key, value);
        this.mHasExtras = true;
    }

    public Long getLong(String key) {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "getLong: key: " + key);

        if (mLongMap.containsKey(key))
            return mLongMap.get(key);

        return null;
    }

    public void putIntent(String key, Intent inent) {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "putIntent: key: " + key);

        this.mIntentMap.put(key, inent);
        this.mHasExtras = true;
    }

    public Intent getIntent(String key) {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "getIntent:");

        return this.mIntentMap.get(key);
    }
}