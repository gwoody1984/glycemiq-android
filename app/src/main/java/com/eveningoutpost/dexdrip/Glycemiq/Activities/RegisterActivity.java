package com.eveningoutpost.dexdrip.Glycemiq.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eveningoutpost.dexdrip.R;
import com.google.gson.Gson;
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

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = RegisterActivity.class.toString();
    private static final String REGISTER_URL_PREF = "glycemmiq_api_register";
    private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText reenterPasswordEditText;
    private TextView loginLink;
    private Button registerButton;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getControls();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });

    }

    private void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        String url = getString(R.string.glycemiq_api_register);
        if (Objects.equals(url, "")){
            Toast.makeText(getBaseContext(), "Invalid configuration - " + REGISTER_URL_PREF, Toast.LENGTH_LONG)
                    .show();
            return;
        }

        registerButton.setEnabled(false);

        progressDialog = new ProgressDialog(RegisterActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", email);
        jsonObject.addProperty("password", password);

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new com.squareup.okhttp.Callback(){
            @Override
            public void onFailure(Request request, IOException e) {
                // show failure to user
                runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onSignupFailed();
                                progressDialog.dismiss();
                            }
                        });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                // success go back to login view
                runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onSignupSuccess();
                                progressDialog.dismiss();
                            }
                        });
            }
        });
    }


    public void onSignupSuccess() {
        registerButton.setEnabled(true);
        Intent intent = getIntent();
        intent.putExtra("email", emailEditText.getText().toString());
        intent.putExtra("password", passwordEditText.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        registerButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String reenterPassword = reenterPasswordEditText.getText().toString();

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

        if (reenterPassword.isEmpty() || !Objects.equals(reenterPassword, password)){
            reenterPasswordEditText.setError("Passwords must match");
            valid = false;
        }
        else {
            reenterPasswordEditText.setError(null);
        }

        return valid;
    }

    private void getControls() {
        emailEditText = (EditText) findViewById(R.id.input_email);
        passwordEditText = (EditText) findViewById(R.id.input_password);
        reenterPasswordEditText = (EditText) findViewById(R.id.input_reenter_password);
        loginLink = (TextView) findViewById(R.id.link_signup);
        registerButton = (Button) findViewById(R.id.btn_signup);
    }
}
