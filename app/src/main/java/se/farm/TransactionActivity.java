package se.farm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

public class TransactionActivity extends AppCompatActivity implements LoadJson.OnFinishLoadJSonListener, View.OnClickListener {
    private LoadJson loadJson;
    private ArrayList<Transaction> arraylist_transaction = new ArrayList<Transaction>();
    private ListView transactionlListView;
    private String account = Var.account;
    private TextView tvNote;
    private Var var;
    private LinearLayout btnAnimal, btnFinance, btnMedical, btnFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        var = new Var();
        loadJson = new LoadJson();
        transactionlListView = (ListView) findViewById(R.id.lv_transaction);
        tvNote = (TextView) findViewById(R.id.tv_note);
        loadJson.setOnFinishLoadJSonListener(this);
        HashMap<String,String> map = new HashMap<>();
        map.put(Var.KEY_ACCOUNT, account);
        btnAnimal = (LinearLayout) findViewById(R.id.btnAnimal);
        btnFinance = (LinearLayout) findViewById(R.id.btnFinace);
        btnFood = (LinearLayout) findViewById(R.id.btnFood);
        btnMedical = (LinearLayout) findViewById(R.id.btnMedical);
        btnAnimal.setOnClickListener(this);
        btnMedical.setOnClickListener(this);
        btnFood.setOnClickListener(this);
        btnFinance.setOnClickListener(this);

        try {
            loadJson.sendDataToServer(this,Var.METHOD_GET_TRANSACTION, map);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void finishLoadJSon(String error, String json) {
        ArrayList<Transaction> newList = LoadJson.jsonToListTransaction(json);
        arraylist_transaction.clear();
        arraylist_transaction.addAll(newList);
        if (arraylist_transaction.size() == 0) {
            transactionlListView.setVisibility(View.GONE);
            tvNote.setVisibility(View.VISIBLE);
        } else {
            transactionlListView.setVisibility(View.VISIBLE);
            tvNote.setVisibility(View.GONE);
            TransactionAdapter transactionAdapter = new TransactionAdapter(this, arraylist_transaction);
            transactionlListView.setAdapter(transactionAdapter);
     }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
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
}
