package se.farm;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, LoadJson.OnFinishLoadJSonListener {
    private EditText editName, editPass;
    private Button btnLogin, btnSignup;
    private LoadJson loadJson;
    private ProgressDialog progressDialog;
    private String nick, pass;
    private Context context;
//    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        loadJson = new LoadJson();
        loadJson.setOnFinishLoadJSonListener(this);
        editName = (EditText) findViewById(R.id.editName);
        editPass = (EditText) findViewById(R.id.editPassWord);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignup = (Button) findViewById(R.id.btnsignup);
        btnLogin.setOnClickListener(this);
        btnSignup.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait for checking");
//        toast.makeText(MainActivity.this, "You enter wrong Username or Password", Toast.LENGTH_SHORT);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btnLogin){
            try {
                login();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        if(id == R.id.btnsignup){
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }


    }
    private void login() throws UnsupportedEncodingException {
        nick = editName.getText().toString().trim();
        pass = editPass.getText().toString().trim();

        // not enter nick name
        if (nick.length() == 0) {
            editName.requestFocus();
            Var.showToast(context, "Please input ID");
            return;
        }

        // not enter pass
        if (pass.length() == 0) {
            editPass.requestFocus();
            Var.showToast(context, "Please input Password");
            return;
        }

        HashMap<String, String> map = new HashMap<>();
        map.put(Var.KEY_ACCOUNT, nick);
        map.put(Var.KEY_PASS, pass);

        loadJson.sendDataToServer(this, Var.METHOD_LOGIN, map);
//        System.out.println(loadJson.toString());
        progressDialog.show();
    }


    @Override
    public void finishLoadJSon(String error, String json) {
        if (progressDialog.isShowing()) {
            progressDialog.hide();
        }
        try {
            if (json != null) {
                JSONObject jsonObject = new JSONObject(json);
                if (jsonObject.getBoolean(Var.KEY_LOGIN)) {
                    Var.showToast(this, "Login successfully");
                    Var.balance = jsonObject.getDouble("Balance");
                    Var.account = nick;
                    Intent intent = new Intent(this, PetsActivity.class);
                    intent.putExtra(Var.KEY_ACCOUNT, editName.getText().toString().trim());
                    startActivity(intent);

                    finish();
                } else {
                    Var.showToast(this, "Login failed");
                }
            } else {
                Var.showToast(this, error);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
