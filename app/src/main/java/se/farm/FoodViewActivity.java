package se.farm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public class FoodViewActivity extends AppCompatActivity implements LoadJson.OnFinishLoadJSonListener {
    TextView tvRice, tvGF, tvMeat, tvGrass;
    LoadJson loadJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodview_layout);
        loadJson = new LoadJson();
        loadJson.setOnFinishLoadJSonListener(this);
        tvRice = (TextView) findViewById(R.id.tvRice);
        tvGrass = (TextView) findViewById(R.id.tvGrass);
        tvMeat = (TextView) findViewById(R.id.tvMeat);
        tvGF = (TextView) findViewById(R.id.tvGF);
        HashMap<String, String> map = new HashMap<>();
        map.put(Var.KEY_ACCOUNT, Var.account);
        try {
            loadJson.sendDataToServer(this, Var.METHOD_GET_FOOD_INFO, map);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void finishLoadJSon(String error, String json) {
        System.out.println(json);
        if (json != null){
            JSONArray arraySMSJson = null;
            try {
                arraySMSJson = new JSONArray(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (int i = 0; i <= 4 ; i++) {
                try {
                    JSONObject jsonObject = arraySMSJson.getJSONObject(i);
                    if (i == 0)
                        tvRice.setText("Rice: " + jsonObject.getDouble("Quantity") +"Kgs" );
                    else if (i == 1)
                        tvGrass.setText("Grass: " + jsonObject.getDouble("Quantity")+"Kgs");
                    else if (i == 2)
                        tvGF.setText("General Food: " + jsonObject.getDouble("Quantity")+"Kgs");
                    else if (i == 0)
                        tvMeat.setText("Meat: " + jsonObject.getDouble("Quantity")+"Kgs");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
