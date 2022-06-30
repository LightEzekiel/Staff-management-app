package com.potentnetwork.phrankstars;

import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;
import java.util.concurrent.Executor;

public class LoginActivity extends AppCompatActivity {


    private static final int REQUEST_CODE = 1;
    TextInputLayout Email,Password;

    Button Login;
    EditText textOtp;
    TextView fingerprint, createAccount,boss;
    Switch Switch;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    String otp;
    String textEdit,textView;
    boolean btnchecked = false;

    float v = 0;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    CustomProgressDialog customProgressDialog;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        Login = findViewById(R.id.btnLogin);
        textOtp = findViewById(R.id.textOtp);
        fingerprint = findViewById(R.id.fingerprint);
        createAccount = findViewById(R.id.createAccount);
        boss = findViewById(R.id.boss);
        Switch = findViewById(R.id.switch2);

        progressBar = findViewById(R.id.signInprogressBar);
        mAuth = FirebaseAuth.getInstance();

      customProgressDialog = new CustomProgressDialog(LoginActivity.this);

        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
        boolean isLogin = sharedPreferences.getBoolean("isLogin",false);
        if (isLogin){
         fingerprint.setVisibility(View.VISIBLE);
        }

        animate();



        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate(BIOMETRIC_STRONG | DEVICE_CREDENTIAL)) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                Log.d("MY_APP_TAG", "App can authenticate using biometrics.");
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Toast.makeText(this, "Fingerprint does not exist", Toast.LENGTH_SHORT).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Toast.makeText(this, "Sensor not available", Toast.LENGTH_SHORT).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                // Prompts the user to create credentials that your app accepts.
                final Intent enrollIntent = new Intent(Settings.ACTION_BIOMETRIC_ENROLL);
                enrollIntent.putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                        BIOMETRIC_STRONG | DEVICE_CREDENTIAL);
                startActivityForResult(enrollIntent, REQUEST_CODE);
                break;
        }


        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(LoginActivity.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
//                Toast.makeText(getApplicationContext(),
//                        "Authentication error: " + errString, Toast.LENGTH_SHORT)
//                        .show();
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                String email = sharedPreferences.getString("email","");
                String password = sharedPreferences.getString("password","");

                textEdit = textOtp.getText().toString().trim();
                if (textEdit.matches(otp)) {
                    signInBoss(email,password);

                }else {

                    signInUser(email,password);

                }
                textOtp.setText("");

                Toast.makeText(getApplicationContext(),
                        "Authentication succeeded!", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Login to Basic")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Use account password")
                .build();

        // Prompt appears when user clicks "Log in".
        // Consider integrating with the keystore to unlock cryptographic operations,
        // if needed by your app.

        fingerprint.setOnClickListener(view -> {
            biometricPrompt.authenticate(promptInfo);
        });


        textView = textOtp.getText().toString().trim();

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textEdit = textOtp.getText().toString().trim();
                if (textEdit.matches(otp)) {
                   
//                  signInBoss();
                    Intent intent = new Intent(LoginActivity.this,Teachers.class);
                    intent.putExtra("User","BOSS");
                    startActivity(intent);

                }else {
                    String password = Password.getEditText().getText().toString().trim();
                    String email = Email.getEditText().getText().toString().trim();
                    signInUser(email,password);

                }
                textOtp.setText("");

            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    textOtp.setVisibility(View.VISIBLE);
                    Login.setEnabled(false);
                    fingerprint.setEnabled(false);
                }else {
                    textOtp.setVisibility(View.INVISIBLE);
                    textOtp.setText("");
                }
                if (isChecked == false){
                    Login.setEnabled(true);
                    fingerprint.setEnabled(true);
                }
            }
        });

        textOtp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(!(s.toString().trim().length() < 2)){
//                    Login.setEnabled(true);
                    fingerprint.setEnabled(true);
                } else {
                   Login.setEnabled(false);
                   fingerprint.setEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length() < 3){
                    Login.setEnabled(false);
                    fingerprint.setEnabled(false);
                } else {
                    Login.setEnabled(false);
                    fingerprint.setEnabled(true);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }
    void animate(){
        Email.setTranslationX(800);
        Password.setTranslationX(800);
        Login.setTranslationX(800);
        textOtp.setTranslationX(800);
        fingerprint.setTranslationX(800);
        Switch.setTranslationX(800);
        createAccount.setTranslationX(800);
        boss.setTranslationX(800);

        Email.setAlpha(v);
        Password.setAlpha(v);
        Login.setAlpha(v);
        textOtp.setAlpha(v);
        fingerprint.setAlpha(v);
        createAccount.setAlpha(v);
        boss.setAlpha(v);
        Switch.setAlpha(v);

        Email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        Password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        Login.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        textOtp.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(600).start();
        createAccount.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(600).start();
        boss.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(600).start();
        fingerprint.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        Switch.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(800).start();
    }

    private void signInUser(String email,String password) {

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


        if (email.isEmpty()){
            Email.setError("Email is required");
            Email.requestFocus();
            return;
        }else if (!email.matches(emailPattern)){
            Email.setError("Invalid email address");
            Email.requestFocus();
            return;
        }

        if (password.isEmpty()){
            Password.setError("Password is required");
            Password.requestFocus();
            return;
        }


        customProgressDialog.show();
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    customProgressDialog.dismiss();
                    SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
                    editor.putString("email",email);
                    editor.putString("password",password);
                    editor.putBoolean("isLogin",true);
                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this,Teachers.class);
                    intent.putExtra("User","ADMIN");
                    startActivity(intent);
                    finish();
                }else {
//                    progressBar.setVisibility(View.INVISIBLE);
                    customProgressDialog.dismiss();
                   String message = task.getException().getMessage();
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void signInBoss( String email,String password) {


        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


        if (email.isEmpty()){
            Email.setError("Email is required");
            Email.requestFocus();
            return;
        }else if (!email.matches(emailPattern)){
            Email.setError("Invalid email address");
            Email.requestFocus();
            return;
        }

        if (password.isEmpty()){
            Password.setError("Password is required");
            Password.requestFocus();
            return;
        }


        customProgressDialog.show();
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    customProgressDialog.dismiss();
                    SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
                    editor.putString("email",email);
                    editor.putString("password",password);
                    editor.putBoolean("isLogin",true);
                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this,Teachers.class);
                    intent.putExtra("User","BOSS");
                    startActivity(intent);
                    finish();
                }else {

                    customProgressDialog.dismiss();
                    String message = task.getException().getMessage();
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        SharedPreferences sharedPref = getSharedPreferences("application", Context.MODE_PRIVATE);
       otp =  sharedPref.getString("OTP","111");

        super.onStart();
    }
}