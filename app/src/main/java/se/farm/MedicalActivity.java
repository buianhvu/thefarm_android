package se.farm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MedicalActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout btnAnimal, btnFinance, btnMedical, btnFood;
    private RelativeLayout healthy_pets, sick_pets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical);
        btnAnimal = (LinearLayout) findViewById(R.id.btnAnimal);
        btnFinance = (LinearLayout) findViewById(R.id.btnFinace);
        btnFood = (LinearLayout) findViewById(R.id.btnFood);
        btnMedical = (LinearLayout) findViewById(R.id.btnMedical);
        healthy_pets = (RelativeLayout) findViewById(R.id.btnHealthy_pets);
        sick_pets = (RelativeLayout) findViewById(R.id.btnSick_Pets);
        btnAnimal.setOnClickListener(this);
        btnMedical.setOnClickListener(this);
        btnFood.setOnClickListener(this);
        btnFinance.setOnClickListener(this);
        healthy_pets.setOnClickListener(this);
        sick_pets.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
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
        if (id == R.id.btnHealthy_pets){

        }
        if (id == R.id.btnSick_Pets){
            Intent intent = new Intent(this, MedicalListActivity.class);
            startActivity(intent);
        }

    }
}
