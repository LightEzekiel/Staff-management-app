package com.potentnetwork.phrankstars;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;

public class TeacherProfile extends AppCompatActivity {
    Toolbar teacherToolbar;
    MenuItem createAccount;
    ImageView roundedimageView;
    private  Uri imageViewUri = null;
    CardView cardView;
    EditText teacherName,teacherClass,teacherSalary,accountName,accountNumber
            ,bankName,teacherPhoneNumber,teacherLoan,teacherLoanPercent,teacherDebt,teacherNote;

    DatabaseReference dbreference;
    StorageReference storageReference;
    String teacherKey;
    String teacherPhoto;
    Uri teacherImageUrl = null;
    double loanPerMonth ;

    String teacherImageUri;
    double salary;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);
        cardView = findViewById(R.id.cardView);

        roundedimageView = findViewById(R.id.roundedimageView);
        teacherName = findViewById(R.id.teach_name);
        teacherClass = findViewById(R.id.teacher_class);
        teacherSalary = findViewById(R.id.teacher_salary);
        accountName = findViewById(R.id.teacher_account_name);
        accountNumber = findViewById(R.id.teacher_account_number);
        bankName = findViewById(R.id.teacher_bank_name);
        teacherPhoneNumber = findViewById(R.id.teacher_phoneNumber);
        teacherLoan = findViewById(R.id.teacher_loanAmount);
        teacherLoanPercent = findViewById(R.id.teacher_loan_percent);
        teacherDebt = findViewById(R.id.teacher_debtAmount);
        teacherNote = findViewById(R.id.teacher_notes);





        teacherToolbar = findViewById(R.id.teachers_account_toolBar);
        setSupportActionBar(teacherToolbar);
        getSupportActionBar().setTitle("Create new account");
        dbreference = FirebaseDatabase.getInstance().getReference("TeacherProfile").push();
        teacherKey = dbreference.getKey();



        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImageImage();
            }
        });
       

        teacherSalary.addTextChangedListener(onTextChangedListener());
        teacherLoan.addTextChangedListener(onTextChangedListener1());
        teacherDebt.addTextChangedListener(onTextChangedListener2());






    }

    private void getImageImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ContextCompat.checkSelfPermission(TeacherProfile.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(TeacherProfile.this, "Permission denied", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(TeacherProfile.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
            }else {

                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);

            }
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageViewUri = result.getUri();
                roundedimageView.setImageURI(imageViewUri);
                if (imageViewUri == null){
                    teacherImageUri = "";
                }else {
                    updateUri();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_teacher:
                updateTeacher();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.teachers_account,menu);
        createAccount = menu.findItem(R.id.add_teacher);
       createAccount.setVisible(false);

        teacherSalary.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(!(s.toString().trim().length() < 2)){
                    createAccount.setVisible(false);
                } else {
                    createAccount.setVisible(true);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length() < 4){
                    createAccount.setVisible(false);
                } else {
                    createAccount.setVisible(true);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return super.onCreateOptionsMenu(menu);
    }
   public void updateUri(){
       final StorageReference fileRef  = FirebaseStorage.getInstance().getReference().child("teacher_image").child(imageViewUri.getLastPathSegment());

       fileRef.putFile(imageViewUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
           @Override
           public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
               if (task.isSuccessful()){
                   fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                       @Override
                       public void onSuccess(Uri uri) {
                           teacherImageUri = String.valueOf(uri);
                       }
                   });

               }
           }
       });
   }


    public void updateTeacher(){
        double res;


       String teachername = teacherName.getText().toString();
        String teacherclass = teacherClass.getText().toString();
        String teachersalary = teacherSalary.getText().toString().replaceAll(",", "");
        String  accountname = accountName.getText().toString();
        String  accountnumber = accountNumber.getText().toString();
        String bankname = bankName.getText().toString();
        String teacherPhone = teacherPhoneNumber.getText().toString();
        String teacherloan = teacherLoan.getText().toString().replaceAll(",", "");
        String loanpercent = teacherLoanPercent.getText().toString();
        String teacherdebt = teacherDebt.getText().toString().replaceAll(",", "");
        String teachernote = teacherNote.getText().toString();

//        NumberFormat.getNumberInstance(Locale.US).format(teachersalary);

        salary = Double.parseDouble(teachersalary);
        if (teachersalary.isEmpty()){
            teacherSalary.setError("This field cannot be empty");
            teacherSalary.requestFocus();
            return;
        }

        if (teachername.isEmpty()){
            teacherName.setError("This field cannot be empty");
            teacherName.requestFocus();
            return;
        }
        if (teacherclass.isEmpty()){
            teacherClass.setError("Class cannot be empty");
            teacherClass.requestFocus();
            return;
        }

        if(!teacherloan.isEmpty() && !loanpercent.isEmpty() ) {
            double amount = Double.parseDouble(teacherloan);
            res = (amount / 100.0f) * Double.parseDouble(loanpercent);
            loanPerMonth = res;
            salary = salary -res;
        }else{
            teacherloan = "";
            loanpercent = "";
        }
       String teacherLoanPerMonth = String.valueOf(loanPerMonth);
        if (teacherLoanPerMonth.isEmpty()){
            teacherLoanPerMonth = "";
        }
        if (!teacherdebt.isEmpty()){
            double debt = Double.parseDouble(teacherdebt);
              salary -= debt;
        }



        if (TextUtils.isEmpty(accountname)){
            accountname = "";
        }
        if (accountnumber.isEmpty()){
            accountnumber = "";
        }else if (accountnumber.length() > 10){
            accountNumber.setError("Invalid account number");
            accountNumber.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(bankname)){
            bankname = "";
        }


        if (teacherPhone.length() > 11){
            teacherPhoneNumber.setError("Wrong Phone number");
            teacherPhoneNumber.requestFocus();
            return;
        }

        if (!teachernote.isEmpty()){
            teachernote = "";
        }

        if(imageViewUri == null){
            imageViewUri = Uri.parse("");
            teacherPhoto = imageViewUri.toString();
        }else {
           teacherPhoto = imageViewUri.toString();
        }



        ProgressDialog mProgressDiaglog = new ProgressDialog(TeacherProfile.this);
        mProgressDiaglog.setCancelable(false);
        mProgressDiaglog.setCanceledOnTouchOutside(false);
        mProgressDiaglog.setMessage("Adding new Teacher..");
        mProgressDiaglog.show();
        Date date = new Date();
        Date newDate = new Date(date.getTime());
        SimpleDateFormat dt = new SimpleDateFormat("EEE,dd MMM, yy");
        String stringdate = dt.format(newDate);


        final HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Mainsalary",teachersalary);
        hashMap.put("Payable",String.valueOf(salary));
        hashMap.put("Teacherclass",teacherclass);
        hashMap.put("Teachername",teachername);
        hashMap.put("Bankname",bankname);
        hashMap.put("Bankaccount",accountnumber);
        hashMap.put("Accountname",accountname);
        hashMap.put("TeacherphoneNumber",teacherPhone);
        hashMap.put("Teacherloan",teacherloan);
        hashMap.put("Teacherpercent",loanpercent);
        hashMap.put("Teacherdebt",teacherdebt);
        hashMap.put("Teachernote",teachernote);
        hashMap.put("Teacherphoto",teacherImageUri);
        hashMap.put("TeacherloanPerMonth",teacherLoanPerMonth);
        hashMap.put("Updatedtime",stringdate);
        hashMap.put("teacherKey",teacherKey);
        dbreference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    mProgressDiaglog.dismiss();
                    Toast.makeText(TeacherProfile.this, "New Teacher successfully added", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(TeacherProfile.this,Teachers.class);
                    startActivity(i);
                    finish();
                }
            }
        });



    }
    private TextWatcher onTextChangedListener() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                teacherSalary.removeTextChangedListener(this);


                try {
                    String originalString = s.toString();

                    Long longval;
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalString);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    formatter.applyPattern("#,###,###,###");
                    String formattedString = formatter.format(longval);

                    //setting text after format to EditText
                    teacherSalary.setText(formattedString);
                    teacherSalary.setSelection(teacherSalary.getText().length());



                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                teacherSalary.addTextChangedListener(this);

            }
        };
    }
    private TextWatcher onTextChangedListener1() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                teacherLoan.removeTextChangedListener(this);


                try {
                    String originalString = s.toString();

                    Long longval;
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalString);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    formatter.applyPattern("#,###,###,###");
                    String formattedString = formatter.format(longval);

                    //setting text after format to EditText

                    teacherLoan.setText(formattedString);
                    teacherLoan.setSelection(teacherLoan.getText().length());


                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }


                teacherLoan.addTextChangedListener(this);

            }
        };
    }
    private TextWatcher onTextChangedListener2() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                teacherDebt.removeTextChangedListener(this);

                try {
                    String originalString = s.toString();

                    Long longval;
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalString);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    formatter.applyPattern("#,###,###,###");
                    String formattedString = formatter.format(longval);

                    //setting text after format to EditText


                    teacherDebt.setText(formattedString);
                    teacherDebt.setSelection(teacherDebt.getText().length());

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }


                teacherDebt.addTextChangedListener(this);
            }
        };
    }


}