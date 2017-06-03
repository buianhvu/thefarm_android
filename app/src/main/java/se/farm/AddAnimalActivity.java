package se.farm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;

public class AddAnimalActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, LoadJson.OnFinishLoadJSonListener, View.OnClickListener{
    private EditText editWeight;
    private EditText editHealthIndex;
    private EditText editNumber;
    private TextView tvSave;
    private TextView tvBalance;
    private Spinner spinner;
    private Spinner source_spinner;
    private int weight;
    private int healthindex;
    private String source;
    private int number;
    private int Animal_ID;
    private String Account;
    private LoadJson loadJson;
    private Var var;
    private LinearLayout btnAnimal, btnFinance, btnMedical, btnFood;



    String sex;
    String arr[]={
            "Male",
            "Female"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_animal);
        //get info
        var = new Var();
        Bundle extras = getIntent().getExtras();
        Animal_ID = extras.getInt("animal_id");
        Account = extras.getString(Var.KEY_ACCOUNT);

        loadJson = new LoadJson();
        loadJson.setOnFinishLoadJSonListener(this);
        //find view
        editNumber = (EditText) findViewById(R.id.editNumber);
        editWeight = (EditText) findViewById(R.id.editWeight);
        editHealthIndex = (EditText) findViewById(R.id.editHealthIndex);
        spinner = (Spinner) findViewById(R.id.spinner_sex);
        source_spinner = (Spinner) findViewById(R.id.spinner_source);
        btnAnimal = (LinearLayout) findViewById(R.id.btnAnimal);
        btnFinance = (LinearLayout) findViewById(R.id.btnFinace);
        btnFood = (LinearLayout) findViewById(R.id.btnFood);
        btnMedical = (LinearLayout) findViewById(R.id.btnMedical);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sex, R.layout.support_simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.source, R.layout.support_simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        source_spinner.setAdapter(adapter1);
        source_spinner.setOnItemSelectedListener(this);
        tvSave = (TextView) findViewById(R.id.tv_save);
        tvBalance = (TextView) findViewById(R.id.tvBalance);
        tvBalance.setText("$"+var.getBalance(this));
        tvSave.setOnClickListener(this);
        btnAnimal.setOnClickListener(this);
        btnMedical.setOnClickListener(this);
        btnFood.setOnClickListener(this);
        btnFinance.setOnClickListener(this);


    }
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.actionbar1, menu);
//        return true;
//    }
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int sex_id = 0;
//        id = item.getItemId();
//        weight = Integer.parseInt(editWeight.getText().toString().trim());
//        healthindex = Integer.parseInt(editHealthIndex.getText().toString().trim());
//        source = editSource.getText().toString();
//        number = Integer.parseInt(editNumber.getText().toString().trim());
//        Date date = new Date();
//
//        if(sex.equals("MALE")){
//            sex_id = 1;
//        }
//        switch(id) {
//            case R.id.save_animal:
//                if(editWeight.getText().toString().trim().equals("") || editSource.getText().toString().trim().equals("") || editHealthIndex.getText().toString().equals(""))
//                    Toast.makeText(this,"Please fulfill all the fields", Toast.LENGTH_SHORT).show();
//                else {
//
//                    HashMap<String, String> map = new HashMap<>();
//                    map.put(Var.KEY_NUMBER, String.valueOf(number));
//                    map.put(Var.KEY_ANIMAL_ID, String.valueOf(Animal_ID));
//                    map.put(Var.KEY_SEX, String.valueOf(sex_id));
//                    map.put(Var.KEY_HEALTH_INDEX, String.valueOf(healthindex));
//                    map.put(Var.KEY_WEIGHT, String.valueOf(weight));
//                    map.put(Var.KEY_SOURCE, source);
//                    map.put(Var.KEY_ACCOUNT, Account);
//                    map.put(Var.KEY_DATE, DateFormat.getDateInstance().format(date));
//                    try {
//                        loadJson.sendDataToServer(this ,Var.METHOD_ADD_LIST, map);
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }
//                    editWeight.setText("");
//                    editHealthIndex.setText("");
//                    editSource.setText("");
//                    break;
//                }
//        }
//        return true;
//    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Spinner spinner = (Spinner) parent;
        if(spinner.getId() == R.id.spinner_sex)
        {
            //do this
            String[] arr = getResources().getStringArray(R.array.sex);
            sex = arr[position].toString();
        }
        else if(spinner.getId() == R.id.spinner_source)
        {
            String[] arr = getResources().getStringArray(R.array.source);
            source = arr[position].toString();
            //do this
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void finishLoadJSon(String error, String json) {
        if (json != null ) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                if(jsonObject.getBoolean(Var.KEY_ADD_LIST)){
                    Var.showToast(this, "ADDED");
                    Intent intent = new Intent(this, AnimalActivity.class);
                    intent.putExtra(Var.KEY_ANIMAL_ID, Animal_ID);
                    intent.putExtra(Var.KEY_ACCOUNT, Account);
                    startActivity(intent);
                }
                else {
                    Var.showToast(this, "ADD FAILED");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else {
            Var.showToast(this,error);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.tv_save){
            prepareData();
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
    public void prepareData(){
        int sex_id = 0;
        weight = Integer.parseInt(editWeight.getText().toString().trim());
        healthindex = Integer.parseInt(editHealthIndex.getText().toString().trim());
        number = Integer.parseInt(editNumber.getText().toString().trim());
        Date date = new Date();
        if(sex.equals("MALE")){
            sex_id = 1;
        }
        else if(editWeight.getText().toString().trim().length() == 0){
            editWeight.requestFocus();
            Var.showToast(this, "Please enter a minimum weight");
        }
        else if(source.length() == 0) {
            Var.showToast(this, "Please enter source");
        }
        else if (editHealthIndex.getText().toString().trim().length() == 0) {
            editHealthIndex.requestFocus();
            Var.showToast(this, "Please enter a minimum health-index");
        }
        else {
            HashMap<String, String> map = new HashMap<>();
            map.put(Var.KEY_NUMBER, String.valueOf(number));
            map.put(Var.KEY_ANIMAL_ID, String.valueOf(Animal_ID));
            map.put(Var.KEY_SEX, String.valueOf(sex_id));
            map.put(Var.KEY_HEALTH_INDEX, String.valueOf(healthindex));
            map.put(Var.KEY_WEIGHT, String.valueOf(weight));
            map.put(Var.KEY_SOURCE, source);
            map.put(Var.KEY_ACCOUNT, Account);
            map.put(Var.KEY_DATE, DateFormat.getDateInstance().format(date));
            try {
                loadJson.sendDataToServer(this, Var.METHOD_ADD_LIST, map);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            editWeight.setText("");
            editHealthIndex.setText("");
        }
    }
}
