package se.farm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar menuToolbar;
    private RelativeLayout pets, food, money;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        menuToolbar = (Toolbar) findViewById(R.id.tlb_actionbar);
        pets = (RelativeLayout) findViewById(R.id.petsitem);
        food = (RelativeLayout) findViewById(R.id.fooditem);
        money = (RelativeLayout) findViewById(R.id.moneyitem);
        Bundle extras = getIntent().getExtras();
        name = extras.getString(Var.KEY_ACCOUNT);
//        menuToolbar.setTitle("MAIN MENU");
//        menuToolbar.setSubtitle("You have 1250$");
//        setSupportActionBar(menuToolbar);
        pets.setOnClickListener(this);
        food.setOnClickListener(this);
        money.setOnClickListener(this);

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
        if (id == R.id.fooditem){
            Intent intent = new Intent(this, FoodActivity.class);
            startActivity(intent);
        }
        if(id == R.id.petsitem){
            Intent intent = new Intent(this, PetsActivity.class);
            intent.putExtra(Var.KEY_ACCOUNT, name);
            startActivity(intent);
        }
        if(id == R.id.moneyitem){
            Intent intent = new Intent(this, FinanceActivity.class);
            intent.putExtra(Var.KEY_ACCOUNT, name);
            startActivity(intent);
        }
    }
}
