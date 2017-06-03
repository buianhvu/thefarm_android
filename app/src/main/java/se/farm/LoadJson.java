package se.farm;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

public class LoadJson {

    public static final String LINK = "http://thefarmteam.esy.es/process.php";

    public void sendDataToServer(Context context,int method, HashMap<String, String> map) throws UnsupportedEncodingException {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        map.put(Var.KEY_METHOD, String.valueOf(method));
        JSONObject jsonObject = new JSONObject(map);
        // put data to server
        StringEntity se = new StringEntity(jsonObject.toString());
//        httpPost.setEntity(se);

//        params.put("",jsonObject);
//        if (map != null) {
//            for (String key : map.keySet()) {
//                params.put(key, map.get(key));
//            }
//        }

        System.out.println("Post...");
        System.out.println(jsonObject.toString());
        System.out.println(se.toString());
        client.post(context, LINK, se, "application/json", new AsyncHttpResponseHandler() {
            @SuppressWarnings("deprecation")
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json = new String(responseBody);
                System.out.println("onSuccess:" + json);
                onFinishLoadJSonListener.finishLoadJSon(null, json);
            }

            @SuppressWarnings("deprecation")
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("onFailure:" + statusCode);

                String e;

                if (statusCode == 404) {
                    e = "Requested resource not found";
                } else if (statusCode == 500) {
                    e = "Something went wrong at server end";
                } else {
                    e = "Device might not be connected to Internet";
                }
                onFinishLoadJSonListener.finishLoadJSon(e, null);
            }
        });
    }

    public static Animal jsonToAnimal(JSONObject jsonObject) {
        try {
            int id = jsonObject.getInt(Var.KEY_ID);
            int sex = jsonObject.getInt(Var.KEY_SEX);
            int animal_id = jsonObject.getInt(Var.KEY_ANIMAL_ID);
            int health_index = jsonObject.getInt(Var.KEY_HEALTH_INDEX);
            int weight = jsonObject.getInt(Var.KEY_WEIGHT);
            String date = jsonObject.getString(Var.KEY_DATE);
            String source = jsonObject.getString(Var.KEY_SOURCE);
            String account = jsonObject.getString(Var.KEY_ACCOUNT);
            return new Animal(id, sex, animal_id, health_index, weight, source, date, account);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static Transaction jsonToTransaction(JSONObject jsonObject) {
        try {
            int id = jsonObject.getInt("Transaction_ID");
            String type = jsonObject.getString("Type");
            String action = jsonObject.getString("Action");
            double money = jsonObject.getDouble("Money");
            String trans_date = jsonObject.getString("Trans_Date");
            return new Transaction(id, type, action, money, trans_date);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Animal> jsonToListAnimal(String json) {
        ArrayList<Animal> list = new ArrayList<>();

        try {
            JSONArray arraySMSJson = new JSONArray(json);
            for (int i = 0; i < arraySMSJson.length(); i++) {
                JSONObject jsonObject = arraySMSJson.getJSONObject(i);
                Animal animal = jsonToAnimal(jsonObject);
                if ( animal != null) {
                    list.add(animal);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static ArrayList<Transaction> jsonToListTransaction(String json) {
        ArrayList<Transaction> list = new ArrayList<>();

        try {
            JSONArray arraySMSJson = new JSONArray(json);
            for (int i = 0; i < arraySMSJson.length(); i++) {
                JSONObject jsonObject = arraySMSJson.getJSONObject(i);
                Transaction transaction = jsonToTransaction(jsonObject);
                if ( transaction != null) {
                    list.add(transaction);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }


    public interface OnFinishLoadJSonListener {
        void finishLoadJSon(String error, String json);
    }

    public OnFinishLoadJSonListener onFinishLoadJSonListener;

    public void setOnFinishLoadJSonListener(OnFinishLoadJSonListener onFinishLoadJSonListener) {
        this.onFinishLoadJSonListener = onFinishLoadJSonListener;
    }
}