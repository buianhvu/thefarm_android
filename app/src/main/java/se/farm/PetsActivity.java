package se.farm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class PetsActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView pig, cow, chicken, buffalo;
    private LinearLayout btnAnimal, btnFinance, btnMedical, btnFood;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.petsmenu);
//        menuToolbar = (Toolbar) findViewById(R.id.tlb_actionbar);
        pig = (ImageView) findViewById(R.id.image_pig);
        cow = (ImageView) findViewById(R.id.image_cow);
        buffalo = (ImageView) findViewById(R.id.image_buffalo);
        chicken = (ImageView) findViewById(R.id.image_chicken);
        btnAnimal = (LinearLayout) findViewById(R.id.btnAnimal);
        btnFinance = (LinearLayout) findViewById(R.id.btnFinace);
        btnFood = (LinearLayout) findViewById(R.id.btnFood);
        btnMedical = (LinearLayout) findViewById(R.id.btnMedical);
//        menuToolbar.setTitle("PETS");
//        menuToolbar.setSubtitle("You have 1250$");
//        setSupportActionBar(menuToolbar);
        Bundle extras = getIntent().getExtras();
        name = extras.getString(Var.KEY_ACCOUNT);
        pig.setOnClickListener(this);
        chicken.setOnClickListener(this);
        buffalo.setOnClickListener(this);
        cow.setOnClickListener(this);
        btnAnimal.setOnClickListener(this);
        btnMedical.setOnClickListener(this);
        btnFood.setOnClickListener(this);
        btnFinance.setOnClickListener(this);

    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.actionbar, menu);
//        return true;
//    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.image_pig){
            Intent intent = new Intent(this, AnimalActivity.class);
            intent.putExtra(Var.KEY_ANIMAL_ID, Var.PIG_ID);
            intent.putExtra(Var.KEY_ACCOUNT, name);
            startActivity(intent);
        }
        if(id == R.id.image_buffalo){
            Intent intent = new Intent(this, AnimalActivity.class);
            intent.putExtra(Var.KEY_ANIMAL_ID, Var.BUFFALO_ID);
            intent.putExtra(Var.KEY_ACCOUNT, name);
            startActivity(intent);
        }
        if(id == R.id.image_cow){
            Intent intent = new Intent(this, AnimalActivity.class);
            intent.putExtra(Var.KEY_ANIMAL_ID, Var.COW_ID);
            intent.putExtra(Var.KEY_ACCOUNT, name);
            startActivity(intent);
        }
        if(id == R.id.image_chicken){
            Intent intent = new Intent(this, AnimalActivity.class);
            intent.putExtra(Var.KEY_ANIMAL_ID, Var.CHICKEN_ID);
            intent.putExtra(Var.KEY_ACCOUNT, name);
            startActivity(intent);
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
