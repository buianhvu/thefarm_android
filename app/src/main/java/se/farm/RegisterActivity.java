package se.farm;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, LoadJson.OnFinishLoadJSonListener {
    private EditText newUserName;
    private EditText newPassword;
    private Button btnSignup;
    private LoadJson loadJson;
    private ProgressDialog progressDialog;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        newUserName = (EditText) findViewById(R.id.newUsername);
        newPassword = (EditText) findViewById(R.id.newPassword);
        btnSignup = (Button) findViewById(R.id.btnRegister);
        btnSignup.setOnClickListener(this);
        context = this;
        loadJson = new LoadJson();
        loadJson.setOnFinishLoadJSonListener(this);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait !");


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btnRegister){
            if(newUserName.getText().toString().trim().length() < 6 )
                Var.showToast(context, "Username must be longer than 6 letters");
            else if(newPassword.getText().toString().trim().length() < 6) {
                Var.showToast(context, "Password must be at least 6 letters");
                newPassword.setText("");
            }
            else
                register();
        }
    }
    public void register(){
        String account = newUserName.getText().toString().trim();
        String password = newPassword.getText().toString().trim();
        HashMap<String, String> map = new HashMap<>();
        map.put(Var.KEY_ACCOUNT, account);
        map.put(Var.KEY_PASS, password);

        try {
            loadJson.sendDataToServer(this,Var.METHOD_REGISTER, map);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
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
                if (jsonObject.getBoolean(Var.KEY_REGISTER)) {
                    Var.showToast(context, "REGISTER SUCCESS");
                    finish();
                } else {
                    Var.showToast(context, "REGISTER FAIL");
                }
            } else {
                Var.showToast(context, error);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
