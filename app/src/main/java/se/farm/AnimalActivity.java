package se.farm;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

public class AnimalActivity extends AppCompatActivity implements LoadJson.OnFinishLoadJSonListener, View.OnClickListener {
//    private Toolbar menuToolbar;
    private ArrayList<Animal> arraylist_animal = new ArrayList<Animal>();
    private ListView animalList;
    private TextView tvNote;
    public static int Animal_ID;
    private String name;
    private LoadJson loadJson;
    private LoadJson loadJson1;
    private int isDelete, isSell;
    private TextView tvAdd, tvDelete, tvSell;
    private TextView tvBalance;
    private Var var;
    private LinearLayout btnAnimal, btnFinance, btnMedical, btnFood;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pig);
        var = new Var();
        animalList = (ListView) findViewById(R.id.lv_pigs);
        tvNote = (TextView) findViewById(R.id.tv_note);
//        menuToolbar = (Toolbar) findViewById(R.id.tlb_actionbar);
        loadJson = new LoadJson();
        loadJson.setOnFinishLoadJSonListener(this);
        loadJson1 = new LoadJson();
        loadJson1.setOnFinishLoadJSonListener(this);

        //get data from previous activity
        Bundle extras = getIntent().getExtras();
        Animal_ID = extras.getInt(Var.KEY_ANIMAL_ID);
        name = extras.getString(Var.KEY_ACCOUNT);

        isDelete = 0;
        isSell = 0;
        btnAnimal = (LinearLayout) findViewById(R.id.btnAnimal);
        btnFinance = (LinearLayout) findViewById(R.id.btnFinace);
        btnFood = (LinearLayout) findViewById(R.id.btnFood);
        btnMedical = (LinearLayout) findViewById(R.id.btnMedical);
        tvDelete = (TextView) findViewById(R.id.tv_delete);
        tvAdd = (TextView) findViewById(R.id.tv_add);
        tvSell = (TextView) findViewById(R.id.tv_sell);
        tvBalance = (TextView) findViewById(R.id.tvBalance);
        tvBalance.setText("$" + var.getBalance(this));
        tvDelete.setOnClickListener(this);
        tvAdd.setOnClickListener(this);
        tvSell.setOnClickListener(this);
        btnAnimal.setOnClickListener(this);
        btnMedical.setOnClickListener(this);
        btnFood.setOnClickListener(this);
        btnFinance.setOnClickListener(this);
        try {
            refresh();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
//
    private void refresh() throws UnsupportedEncodingException {
        String temp = String.valueOf(Animal_ID);
        HashMap<String, String> map = new HashMap<>();
        map.put(Var.KEY_ACCOUNT, name);
        map.put(Var.KEY_ANIMAL_ID, temp);
        tvBalance.setText("$" + var.getBalance(this));
        loadJson.sendDataToServer(this,Var.METHOD_GET_ANIMAL, map);
    }
    private void showNotify(String notify) {
        Var.showToast(this, notify);
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void finishLoadJSon(String error, String json) {

            if (json != null ) {
                    if(isDelete == 0 && isSell == 0)
                        updateList(json);
                    else{
                        try {
                            JSONObject jsonObject = new JSONObject(json);
                            if(isDelete == 1) {
                                if (jsonObject.getBoolean(Var.KEY_DELETE)) {
                                    Var.showToast(this, "Deleted");
                                    isDelete = 0;
                                    try {
                                        refresh();
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                                } else
                                    isDelete = 0;
                            }
                            if(isSell == 1){
                                if(jsonObject.getBoolean(Var.KEY_DELETE)){
                                    Var.showToast(this, "Selled, Total money: "+jsonObject.getDouble("total_money")+" $");
                                    isSell = 0;
                                    try {
                                        refresh();
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                                }else
                                    isSell = 0;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

            }
            else {
                showNotify(error);
            }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void updateList(String json) {
        ArrayList<Animal> newList = LoadJson.jsonToListAnimal(json);
        arraylist_animal.clear();
        arraylist_animal.addAll(newList);

        if (arraylist_animal.size() == 0) {
            animalList.setVisibility(View.GONE);
            tvNote.setVisibility(View.VISIBLE);
        }
        else {
            animalList.setVisibility(View.VISIBLE);
            tvNote.setVisibility(View.GONE);
            AnimalAdapter animalAdapter = new AnimalAdapter(this, arraylist_animal);
            animalList.setAdapter(animalAdapter);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void deleteAnimal() throws UnsupportedEncodingException {
        HashMap<String, String> map = new HashMap<>();
        String json = AnimalAdapter.jsonArray.toString();
        if(AnimalAdapter.jsonArray.length() == 0)
        {
            Var.showToast(this,"Please select some pets first!");
        }
        else {
            AnimalAdapter.flush_array();
            map.put("array_id", json);
            isDelete = 1;
            loadJson1.sendDataToServer(this, Var.METHOD_DELETE_ANIMAL, map);

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void sellAnimals() throws UnsupportedEncodingException {
        HashMap<String, String> map = new HashMap<>();
        String json = AnimalAdapter.jsonArray.toString();
        if(AnimalAdapter.jsonArray.length() == 0)
        {
            Var.showToast(this,"Please select some pets first!");
        }
        else {
            AnimalAdapter.flush_array();
            map.put("array_id", json);
            isSell = 1;
            loadJson1.sendDataToServer(this, Var.METHOD_SELL_ANIMALS, map);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.tv_delete){
            try {
                deleteAnimal();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        if(id == R.id.tv_add){
            Intent intent = new Intent(this, AddAnimalActivity.class);
            intent.putExtra("animal_id", Animal_ID);
            intent.putExtra(Var.KEY_ACCOUNT, name);
            startActivity(intent);
        }
        if(id == R.id.tv_sell){
            try {
                sellAnimals();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
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
}
