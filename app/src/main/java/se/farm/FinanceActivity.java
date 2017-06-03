package se.farm;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public class FinanceActivity extends AppCompatActivity implements View.OnClickListener, LoadJson.OnFinishLoadJSonListener {
    private RelativeLayout money_adding;
    private RelativeLayout money_withdrawing;
    private RelativeLayout transaction_view;
    private TextView tvBalance;
    private EditText money_input;
    private Context context = this;
    private LoadJson loadJson;
    private boolean isAdd = false, isWithdraw = false, isTransaction = false;
    private Var var;
    private LinearLayout btnAnimal, btnFinance, btnMedical, btnFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance);
        var = new Var();
        money_adding = (RelativeLayout) findViewById(R.id.money_add_item);
        money_withdrawing = (RelativeLayout) findViewById(R.id.money_withdraw_item);
        transaction_view = (RelativeLayout) findViewById(R.id.transaction);
        btnAnimal = (LinearLayout) findViewById(R.id.btnAnimal);
        btnFinance = (LinearLayout) findViewById(R.id.btnFinace);
        btnFood = (LinearLayout) findViewById(R.id.btnFood);
        btnMedical = (LinearLayout) findViewById(R.id.btnMedical);
        money_adding.setOnClickListener(this);
        money_withdrawing.setOnClickListener(this);
        transaction_view.setOnClickListener(this);
        btnAnimal.setOnClickListener(this);
        btnMedical.setOnClickListener(this);
        btnFood.setOnClickListener(this);
        btnFinance.setOnClickListener(this);
        tvBalance = (TextView) findViewById(R.id.tvBalance);
        tvBalance.setText("$" + var.getBalance(this));
        loadJson = new LoadJson();
        loadJson.setOnFinishLoadJSonListener(this);

//        money_input.setInputType(InputType.TYPE_CLASS_NUMBER);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Amount of money: ");
        final EditText input = new EditText(this);
        int type = InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL;
        input.setRawInputType(Configuration.KEYBOARD_12KEY);
        input.setInputType(type);
        alert.setView(input);
        if (id == R.id.money_add_item) {
            alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    double amount = Double.parseDouble(input.getText().toString().trim());
                    HashMap map = new HashMap<String, String>();
                    map.put(Var.KEY_ACCOUNT, Var.account);
                    map.put(Var.KEY_AMOUNT, String.valueOf(amount));
                    try {
                        isAdd = true;
                        loadJson.sendDataToServer(context, Var.METHOD_ADD_MONEY, map);
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
        if (id == R.id.money_withdraw_item) {
            alert.setPositiveButton("Withdraw", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    double amount = Double.parseDouble(input.getText().toString().trim());
                    HashMap map = new HashMap<String, String>();
                    map.put(Var.KEY_ACCOUNT, Var.account);
                    map.put(Var.KEY_AMOUNT, String.valueOf(amount));
                    if (amount > Var.balance) {
                        Var.showToast(context, "Your account is not enough");
                    } else {
                        try {
                            isWithdraw = true;
                            loadJson.sendDataToServer(context, Var.METHOD_WITHDRAW_MONEY, map);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    //Put actions for CANCEL button here, or leave in blank
                }
            });
            alert.show();
        }
        if (id == R.id.transaction) {
            Intent intent = new Intent(this, TransactionActivity.class);
            startActivity(intent);
        }

        if (id == R.id.btnAnimal) {
            Intent intent = new Intent(this, PetsActivity.class);
            intent.putExtra(Var.KEY_ACCOUNT, Var.account);
            startActivity(intent);
        }

        if (id == R.id.btnFinace) {
            Intent intent = new Intent(this, FinanceActivity.class);
            startActivity(intent);
        }
        if (id == R.id.btnFood) {
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
        if(json != null){
            if(isAdd){
            try {
                JSONObject jsonObject = new JSONObject(json);
                if (jsonObject.getBoolean("add_money")) {
                    Var.showToast(context, "Add money successfully");
                }else{
                    Var.showToast(context, "Add failed");
                }

                if(var.updateBalance(this) )
                    tvBalance.setText("$" + Var.balance);
                else
                    tvBalance.setText("$" + 0.0);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            isAdd = false;
            }
            if(isWithdraw){
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    if (jsonObject.getBoolean("withdraw")){
                        Var.showToast(context, "Withdraw successfully");
                    }else{
                        Var.showToast(context, "Withdraw failed");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                isWithdraw = false;
            }

        }

    }
}
