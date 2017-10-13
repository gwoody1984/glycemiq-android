package com.eveningoutpost.dexdrip.Glycemiq.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eveningoutpost.dexdrip.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.util.Objects;

/**
 * Created by Greg on 10/10/2017.
 */

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.toString();
    private static final String LOGIN_URL_PREF = "glycemiq_api_login";
    private static final int REQUEST_SIGNUP = 0;
    private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    SharedPreferences prefs;
    EditText emailEditText;
    EditText passwordEditText;
    TextView registerLink;
    Button loginButton;

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        setContentView(R.layout.activity_splash);
        getControls();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to go to registration page
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    private void getControls() {
        emailEditText = (EditText) findViewById(R.id.input_email);
        passwordEditText = (EditText) findViewById(R.id.input_password);
        registerLink = (TextView) findViewById(R.id.link_signup);
        loginButton = (Button) findViewById(R.id.btn_login);
    }

    public void login(){
        Log.d(TAG, "Login");

        if (!validate()){
            onLoginFailed();
            return;
        }

        String url = getString(R.string.glycemiq_api_login);
        if (Objects.equals(url, "")){
            Toast.makeText(getBaseContext(), "Invalid configuration - " + LOGIN_URL_PREF, Toast.LENGTH_LONG)
                    .show();
            return;
        }

        loginButton.setEnabled(false);

        progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", emailEditText.getText().toString());
        jsonObject.addProperty("password", passwordEditText.getText().toString());

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new com.squareup.okhttp.Callback(){
            @Override
            public void onFailure(Request request, IOException e) {
                runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        onLoginFailed();
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {

                if (response.code() == 200) {
                    // save username and response in shared pref
                    ResponseBody body = response.body();
                    LoginActivity.LoginResponse loginResponse = new Gson().fromJson(body.charStream(),
                            LoginActivity.LoginResponse.class);

                    SharedPreferences.Editor prefEditor = prefs.edit();
                    prefEditor.putString("user_id", loginResponse.user_id);
                    prefEditor.putString("api_key", loginResponse.api_key);
                    prefEditor.apply();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            onLoginSuccess();
                        }
                    });
                }
                else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            onLoginFailed();
                        }
                    });
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                String email = data.getStringExtra("email");
                String password = data.getStringExtra("password");

                emailEditText.setText(email);
                passwordEditText.setText(password);

                login();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        loginButton.setEnabled(true);
        Intent intent = getIntent();
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        loginButton.setEnabled(true);
        progressDialog.dismiss();
    }

    public boolean validate() {
        boolean valid = true;

        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("enter a valid email address");
            valid = false;
        } else {
            emailEditText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordEditText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordEditText.setError(null);
        }

        return valid;
    }

    public class LoginResponse{
        String user_id;
        String api_key;

        public String getUser_id(){
            return user_id;
        }

        public String getApi_key(){
            return api_key;
        }

        public void setUser_id(String user_id){
            this.user_id = user_id;
        }

        public void setApi_key(String api_key){
            this.api_key = api_key;
        }
    }
}
