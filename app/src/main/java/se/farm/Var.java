package se.farm;

/**
 * Created by BuiAnhVu on 5/6/2017.
 */

import android.content.Context;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public class Var implements LoadJson.OnFinishLoadJSonListener {
    //
    public static double balance;
    public static String account;
    private boolean isProcessingDone = false;

    // const for load and put data with server
    public static final String KEY_METHOD = "method";

    public static final String KEY_ACCOUNT = "Account";
    public static final String KEY_PASS = "Password";

    public static final String KEY_ID = "Id";
    public static final String KEY_HEALTH_INDEX = "Health_Index";
    public static final String KEY_WEIGHT = "Weight";
    public static final String KEY_SEX = "Sex";
    public static final String KEY_SOURCE = "Source";
    public static final String KEY_ANIMAL_ID = "Animal_ID";
    public static final String KEY_DATE = "Date_Import";
    public static final String KEY_NUMBER = "Number_Animals";
    public static final String KEY_AMOUNT = "Amount";
    public static final String KEY_BALANCE = "Balance";
    public static final String KEY_FOOD_ID = "Food_ID";
    public static final String KEY_FOOD_AMOUNT = "Food_Amount";


    public static final String KEY_DELETE = "delete";
    public static final String KEY_LOGIN = "login";
    public static final String KEY_REGISTER = "register";
    public static final String KEY_ADD = "add";
    public static final String KEY_ADD_LIST = "add_list";

    public static final int METHOD_LOGIN = 1;
    public static final int METHOD_REGISTER = 2;
    public static final int METHOD_GET_ANIMAL = 3;
    public static final int METHOD_DELETE_ANIMAL = 4;
    public static final int METHOD_ADD_ANIMAL = 5;
    public static final int METHOD_ADD_MONEY = 6;
    public static final int METHOD_WITHDRAW_MONEY = 7;
    public static final int METHOD_ADD_LIST = 8;
    public static final int METHOD_CURRENT_BALANCE = 9;
    public static final int METHOD_SELL_ANIMALS = 10;
    public static final int METHOD_GET_TRANSACTION = 11;
    public static final int METHOD_GET_SICK_ANIMALS = 13;
    public static final int METHOD_BUY_FOOD = 14;
    public static final int METHOD_GET_FOOD_INFO = 15;



    public static final int PIG_ID = 1;
    public static final int BUFFALO_ID = 2;
    public static final int COW_ID = 3;
    public static final int CHICKEN_ID = 4;


    public static void showToast(Context context, String sms) {
        Toast.makeText(context, sms, Toast.LENGTH_SHORT).show();
    }

    public boolean updateBalance(Context context) {
        LoadJson loadJson = new LoadJson();
        loadJson.setOnFinishLoadJSonListener(this);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(KEY_ACCOUNT, account);
        try {
            loadJson.sendDataToServer(context, METHOD_CURRENT_BALANCE, map);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if(isProcessingDone == true){
            isProcessingDone = false;
            return true;
        }
        return false;
    }
    public double getBalance(Context context){
        updateBalance(context);
        return balance;
    }

    @Override
    public void finishLoadJSon(String error, String json) {
        if(json != null){
            try {
                JSONObject jsonObject = new JSONObject(json);
                this.balance = jsonObject.getDouble(KEY_BALANCE);
                System.out.println("NEW BALANCE: " + balance);
                isProcessingDone = true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    // method for save and get nick and pass user

//    public static void save(Context context, String key, String value) {
//        SharedPreferences.Editor editor = PreferenceManager
//                .getDefaultSharedPreferences(context.getApplicationContext())
//                .edit();
//        editor.putString(key, value);
//        editor.apply();
//    }
//
//    public static String get(Context context, String key) {
//        SharedPreferences settings = PreferenceManager
//                .getDefaultSharedPreferences(context.getApplicationContext());
//        return settings.getString(key, null);
//    }
}
