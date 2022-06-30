package com.potentnetwork.phrankstars;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class SignUpActivity extends AppCompatActivity {
    TextInputLayout Email,Password,confirmPassword;
    Button SignUp;
    TextInputEditText email_pcs;


    DatabaseReference dbReference;

    float v = 0;

    FirebaseAuth mAuth;
    CustomProgressDialog customProgressDialog;

    private static final int NONE = 0;
    private static final int SWIPE = 1;
    private int mode = NONE;
    private float startY, stopY;
    // We will only detect a swipe if the difference is at least 100 pixels
    private static final int TRESHOLD = 100;
    ConstraintLayout ConstraintSwipe;
    ImageView imageViewChangeOTP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Email = findViewById(R.id.email2);
        Password = findViewById(R.id.password2);
        confirmPassword = findViewById(R.id.confirmPassword2);
        SignUp = findViewById(R.id.signupBtn);
        email_pcs = findViewById(R.id.email_pcs);
        ConstraintSwipe = findViewById(R.id.ConstraintSwipe);
        imageViewChangeOTP = findViewById(R.id.imageViewChangeOTP);


        mAuth = FirebaseAuth.getInstance();
        dbReference = FirebaseDatabase.getInstance().getReference();

        Email.setTranslationY(800);
        Password.setTranslationY(800);
        confirmPassword.setTranslationY(800);
        SignUp.setTranslationY(800);

        Email.setAlpha(v);
        Password.setAlpha(v);
        SignUp.setAlpha(v);
        confirmPassword.setAlpha(v);

        Email.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
        Password.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();
        SignUp.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();
        confirmPassword.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();

         customProgressDialog = new CustomProgressDialog(this);



        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });



        imageViewChangeOTP.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(
                        SignUpActivity.this);
                // Get the layout inflater
                LayoutInflater inflater = SignUpActivity.this.getLayoutInflater();
                View mView = inflater.inflate(R.layout.dialog_otp, null);
                final EditText  txtUrl = mView.findViewById(R.id.otpTextView);
                builder.setView(mView)
                        .setTitle("Enter/Reset OTP")
                        .setMessage("Input three Digits only")
                        // Add action buttons
                        .setPositiveButton("SAVE",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        String otpText =  txtUrl.getText().toString();
                                        if (!otpText.isEmpty()){
                                            saveOtp(otpText);
                                            dbReference.child("OTP").setValue(otpText).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()){
                                                        Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                        Toast.makeText(SignUpActivity.this, "OTP saved successfully", Toast.LENGTH_SHORT).show();
                                                        dialog.dismiss();
                                                    }
                                                }
                                            });
                                        }else{
                                            Toast.makeText(SignUpActivity.this, "OTP needed", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                })
                        .setNegativeButton("EXIT",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.cancel();
                                    }
                                });
                // return builder.create();
                AlertDialog alertDialog = builder.create();
                alertDialog.show();


                return true;
            }
        });

    }

    private void createUser() {
        String email = Email.getEditText().getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String password = Password.getEditText().getText().toString().trim();
        String confirmPass = confirmPassword.getEditText().getText().toString().trim();
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
        }else if (password.length() < 4){
            Password.setError("Password is weak.");
            Password.requestFocus();
            return;
        }


        if (confirmPass.isEmpty()){
            confirmPassword.setError("Confirm password is required");
            confirmPassword.requestFocus();
            return;
        }else if (!password.matches(confirmPass)){
            confirmPassword.setError("Password does not match");
            confirmPassword.requestFocus();
            return;
        }

        customProgressDialog.show();
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
              if (task.isSuccessful()){
                  customProgressDialog.dismiss();
                  Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                  startActivity(intent);
                  finish();
              }else {
                  customProgressDialog.dismiss();
                  String e = task.getException().getMessage();
                  Toast.makeText(SignUpActivity.this, e, Toast.LENGTH_SHORT).show();
              }
            }
        });
    }
    public void saveOtp(String number) {
        SharedPreferences sharedPref = this.getSharedPreferences("application", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("OTP", number);
        editor.apply();
    }


}