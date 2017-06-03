package se.farm;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import static se.farm.R.array.food;

public class FoodActivity extends AppCompatActivity implements View.OnClickListener, LoadJson.OnFinishLoadJSonListener, AdapterView.OnItemSelectedListener {
    private RelativeLayout foodbuy;
    private RelativeLayout foodview;
    private LoadJson loadJson;
    private Context context = this;
    private TextView tvBalance;
    private Var var;
    private String food_name;
    private Boolean isBuyFood = false, isFoodView = false;
    private LinearLayout btnAnimal, btnFinance, btnMedical, btnFood;

//    String arr[]={
//            "Rice",
//            "Grass",
//            "General",
//            "Meat"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        var = new Var();
        tvBalance = (TextView) findViewById(R.id.tvBalance);
        tvBalance.setText("$"+var.getBalance(context));
        loadJson = new LoadJson();
        loadJson.setOnFinishLoadJSonListener(this);
        foodbuy = (RelativeLayout) findViewById(R.id.btnfood_buy);
        foodview = (RelativeLayout) findViewById(R.id.btnfood_view);
        btnAnimal = (LinearLayout) findViewById(R.id.btnAnimal);
        btnFinance = (LinearLayout) findViewById(R.id.btnFinace);
        btnFood = (LinearLayout) findViewById(R.id.btnFood);
        btnMedical = (LinearLayout) findViewById(R.id.btnMedical);
        foodbuy.setOnClickListener(this);
        foodview.setOnClickListener(this);
        btnAnimal.setOnClickListener(this);
        btnMedical.setOnClickListener(this);
        btnFood.setOnClickListener(this);
        btnFinance.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btnfood_buy){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Import food ");
            final EditText input = new EditText(this);
            final Spinner spinner = new Spinner(this);
            final TextView textView = new TextView(this);
            textView.setText("(Kg)");
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.food, R.layout.support_simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(this);
            int type = InputType.TYPE_CLASS_NUMBER |  InputType.TYPE_NUMBER_FLAG_DECIMAL;
            input.setRawInputType(Configuration.KEYBOARD_12KEY);
            input.setInputType(type);
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.addView(spinner);
            linearLayout.addView(input);
            linearLayout.addView(textView);
            alert.setView(linearLayout);
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int whichButton) {
                    double amount = Double.parseDouble(input.getText().toString().trim());
                    HashMap map = new HashMap<String, String>();
                    map.put(Var.KEY_ACCOUNT, Var.account);
                    map.put(Var.KEY_FOOD_AMOUNT, String.valueOf(amount));
                    if(food_name.equals("Rice")){
                        map.put(Var.KEY_FOOD_ID, String.valueOf(1));
                    }else if(food_name.equals("Grass")){
                        map.put(Var.KEY_FOOD_ID, String.valueOf(2));
                    }else if(food_name.equals("General")){
                        map.put(Var.KEY_FOOD_ID, String.valueOf(3));
                    }else if(food_name.equals("Meat")){
                        map.put(Var.KEY_FOOD_ID, String.valueOf(4));
                    }
                    try {
                        isBuyFood = true;
                        loadJson.sendDataToServer(context, Var.METHOD_BUY_FOOD, map);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }


                    //Put actions for OK button here
                }
            });
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    //Put actions for CANCEL button here, or leave in blank
                }
            });
            alert.show();
        }
        if (id == R.id.btnfood_view){
            Intent intent = new Intent(this, FoodViewActivity.class);
            startActivity(intent);
            // custom dialog
//            isFoodView = true;
//            HashMap<String,String> map = new HashMap<>();
//            map.put(Var.KEY_ACCOUNT, Var.account);
//            try {
//                loadJson.sendDataToServer(context, Var.METHOD_GET_FOOD_INFO, map);
//                System.out.println("check");
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
        }
        if (id == R.id.btnAnimal){
            Intent intent = new Intent(this, PetsActivity.class);
            intent.putExtra(Var.KEY_ACCOUNT, Var.account);
            startActivity(intent);
        }
        if (id == R.id.btnFinace){
            Intent intent = new Intent(this, FinanceActivity.class);
            startActivity(intent);
        }
        if (id == R.id.btnFood){
            Intent intent = new Intent(this, FoodActivity.class);
            startActivity(intent);
        }
        if (id == R.id.btnMedical){
            Intent intent = new Intent(this, MedicalActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void finishLoadJSon(String error, String json) {
        if (json!=null){
            JSONObject jsonObject = null;
            if(isBuyFood) {
                try {
                    jsonObject = new JSONObject(json);
                    String mess = jsonObject.getString("mess");
                    Var.showToast(this, mess);
                    var.updateBalance(this);
                    tvBalance.setText("$" + var.balance);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                isBuyFood = false;
            }
            if(isFoodView){
                isFoodView = false;
                try {
                    LayoutInflater li = LayoutInflater.from(context);
                    View promptsView = li.inflate(R.layout.foodview_layout, null);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            context);
                    alertDialogBuilder.setView(promptsView);
                    System.out.println("checkpoint 1");

//                    TextView tvRice = (TextView) promptsView.findViewById(R.id.tvRice);
//                    TextView tvGrass = (TextView) promptsView.findViewById(R.id.tvGrass);
//                    TextView tvGF = (TextView) promptsView.findViewById(R.id.tvGeneral);
//                    TextView tvMeat = (TextView) promptsView.findViewById(R.id.tvMeat);
//                    Button btnOK = (Button) promptsView.findViewById(R.id.btnOK);
                    alertDialogBuilder
                            .setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    System.out.println("checkpoint2");
                    JSONArray arrayJson = new JSONArray(json);
                    for (int i = 0; i < arrayJson.length(); i++) {
                        JSONObject Object = arrayJson.getJSONObject(i);
//                        if (i == 0){
//                            tvRice.setText("Rice: " + Object.getDouble("Quantity") +" Kg");
//                        }
//                        if (i == 1){
//                            tvGrass.setText("Grass: " + Object.getDouble("Quantity") +" Kg");
//                        }
//                        if (i == 2){
//                            tvGF.setText("GF: " + Object.getDouble("Quantity") +" Kg");
//                        }
//                        if (i == 3){
//                            tvMeat.setText("Rice: " + Object.getDouble("Quantity") +" Kg");
//                        }
                    }
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String[] arr = getResources().getStringArray(food);
        food_name = arr[position].toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
