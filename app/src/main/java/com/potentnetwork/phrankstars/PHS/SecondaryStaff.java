package com.potentnetwork.phrankstars.PHS;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.potentnetwork.phrankstars.PCA;
import com.potentnetwork.phrankstars.PrimaryStaff;
import com.potentnetwork.phrankstars.R;
import com.potentnetwork.phrankstars.Teachers;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SecondaryStaff extends AppCompatActivity {

    Toolbar secondary_staff_toolBar;
    CardView staffImage;
    TextInputLayout staffName,staffPosition,staffSalary,staffPhoneNumber,
            staffBonus,staffDeduction,staffTotalSavings,staffSavingsPerMonth,
            staffLoan,staffLoanPayPerMonth,staffBankAccountName,staffBankAccountNumber,
            staffBankName,staffCommentary;

    TextInputEditText staff_Salary1,staff_bonus1,staff_deduction1,staff_savings1,staff_savings_per_month1,
            staff_loan1,staff_loan_pay_per_month1;

    String staff_name1,staff_position1,staff_phoneNumber1,staff_bankAccountName1,
            staff_AccountNumber1,staff_BankName1,staff_commentary1,staffSalary1,staffBonus1,
            staffDeduction1,staffSavings1,staffSavingsPerMonth1,staffLoan1,staffLoanPayPerMonth1,
            staff_salary_increment1,trackingDate,remainingLoan,loanPaid;

    MenuItem createAccount;



    double loanBalance;
    double bonus;


    double Psalary;
    String teacherPhoto;
    String createdDate;

    FirebaseFirestore db;
    private Uri imageViewUri = null;
    String teacherImageUri;
    ImageView roundedimageView;
    StorageReference fileRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary_staff);

        roundedimageView = findViewById(R.id.phs_roundedimageView1);

        secondary_staff_toolBar = findViewById(R.id.secondary_staff_action_bar);
        staffImage = findViewById(R.id.phs_staff_image);
        staffName = findViewById(R.id.phs_staff_name);
        staffPosition = findViewById(R.id.phs_staff_position);
        staffSalary = findViewById(R.id.phs_staff_basic_salary);
        staffPhoneNumber = findViewById(R.id.phs_staff_phone_number);
        staffBonus = findViewById(R.id.phs_staff_bonus);
        staffDeduction = findViewById(R.id.phs_staff_deduction);
        staffTotalSavings = findViewById(R.id.phs_staff_savings);
        staffSavingsPerMonth = findViewById(R.id.phs_staff_savings_per_month);
        staffLoan = findViewById(R.id.phs_staff_loan);
        staffLoanPayPerMonth = findViewById(R.id.phs_staff_loan_pay_per_month);
        staffBankAccountName = findViewById(R.id.phs_staff_bank_account_name);
        staffBankAccountNumber = findViewById(R.id.phs_staff_bank_account_number);
        staffBankName = findViewById(R.id.phs_staff_bank_name);
        staffCommentary = findViewById(R.id.phs_staff_commentary);

        staff_Salary1 = findViewById(R.id.phs_staff_basic_salary1);
        staff_bonus1 = findViewById(R.id.phs_staff_bonus1);
        staff_deduction1 = findViewById(R.id.phs_staff_deduction1);
        staff_savings1 = findViewById(R.id.phs_staff_savings1);
        staff_savings_per_month1 = findViewById(R.id.phs_staff_savings_per_month1);
        staff_loan1 = findViewById(R.id.phs_staff_loan1);
        staff_loan_pay_per_month1 = findViewById(R.id.phs_staff_loan_pay_per_month1);




        db = FirebaseFirestore.getInstance();


        setSupportActionBar(secondary_staff_toolBar);
        getSupportActionBar().setTitle("Add new Staff");
        staffImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImageImage1();
            }
        });

        staff_Salary1.addTextChangedListener(onTextChangedListener());
        staff_bonus1.addTextChangedListener(onTextChangedListener1());
        staff_deduction1.addTextChangedListener(onTextChangedListener2());
        staff_savings1.addTextChangedListener(onTextChangedListener3());
        staff_savings_per_month1.addTextChangedListener(onTextChangedListener4());
        staff_loan1.addTextChangedListener(onTextChangedListener5());
        staff_loan_pay_per_month1.addTextChangedListener(onTextChangedListener6());
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.phs_add_teacher:
                updateTeacher();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    private void updateTeacher() {
        staff_name1 = staffName.getEditText().getText().toString().trim();
        staff_position1 = staffPosition.getEditText().getText().toString().trim();
        staff_phoneNumber1 = staffPhoneNumber.getEditText().getText().toString().trim();
        staff_bankAccountName1 = staffBankAccountName.getEditText().getText().toString().trim();
        staff_AccountNumber1 = staffBankAccountNumber.getEditText().getText().toString().trim();
        staff_BankName1 = staffBankName.getEditText().getText().toString().trim();
        staff_commentary1 = staffCommentary.getEditText().getText().toString().trim();

        staffSalary1 = staff_Salary1.getText().toString().replaceAll(",", "");
        staffBonus1 = staff_bonus1.getText().toString().replaceAll(",", "");
        staffDeduction1 = staff_deduction1.getText().toString().replaceAll(",", "");
        staffSavings1 = staff_savings1.getText().toString().replaceAll(",", "");
        staffSavingsPerMonth1 = staff_savings_per_month1.getText().toString().replaceAll(",", "");
        staffLoan1 = staff_loan1.getText().toString().replaceAll(",", "");
        staffLoanPayPerMonth1 = staff_loan_pay_per_month1.getText().toString().replaceAll(",", "");
        staff_salary_increment1 ="";



        Psalary = Double.parseDouble(staffSalary1);
        Psalary = Psalary - (Psalary / 100.0f) * 10;
        if (staffSalary1.isEmpty()){
            staff_Salary1.setError("Staff salary required");
            staff_Salary1.requestFocus();
            return;
        }

        if (staff_name1.isEmpty()){
            staffName.setError("Staff name required");
            staffName.requestFocus();
            return;
        }
        if (staff_position1.isEmpty()){
            staffPosition.setError("Staff position required");
            staffPosition.requestFocus();
            return;
        }

        if(!staffLoan1.isEmpty() && !staffLoanPayPerMonth1.isEmpty() ) {
            double dstaffLoan = Double.parseDouble(staffLoan1);
            double dstaffLoanPayPerMonth = Double.parseDouble(staffLoanPayPerMonth1);

            loanBalance = dstaffLoan - dstaffLoanPayPerMonth;
            Psalary = Psalary - dstaffLoanPayPerMonth;
            remainingLoan = String.valueOf(loanBalance);
            loanPaid = String.valueOf(dstaffLoanPayPerMonth);



        }else if (!staffLoan1.isEmpty() && staffLoanPayPerMonth1.isEmpty()){
            staff_loan_pay_per_month1.setError("Staff loan pay per month required");
            staff_loan_pay_per_month1.requestFocus();
            return;
        }else if (staffLoan1.isEmpty() && !staffLoanPayPerMonth1.isEmpty()){
            staff_loan1.setError("Staff loan amount required");
            staff_loan1.requestFocus();
            return;
        }
        else{
            staffLoan1 = "";
            staffLoanPayPerMonth1 = "";

            remainingLoan = "";
            loanPaid = "";
        }

        if (!staffDeduction1.isEmpty()){
            double debt = Double.parseDouble(staffDeduction1);
            Psalary -= debt;
        }else {
            staffDeduction1 = "";
        }



        if (TextUtils.isEmpty(staff_bankAccountName1)){
            staff_bankAccountName1 = "";
        }
        if (staff_AccountNumber1.isEmpty()){
            staff_AccountNumber1 = "";
        }else if (staff_AccountNumber1.length() != 10){
            staffBankAccountNumber.setError("Invalid account number");
            staffBankAccountNumber.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(staff_BankName1)){
            staff_BankName1 = "";
        }

        if (staff_phoneNumber1.isEmpty()){
            staff_phoneNumber1 = "";
        }else if (staff_phoneNumber1.length() != 11){
            staffPhoneNumber.setError("Invalid Phone number");
            staffPhoneNumber.requestFocus();
            return;
        }

        if (staff_commentary1.isEmpty()){
            staff_commentary1 = "";
        }

        if(imageViewUri == null){
            imageViewUri = Uri.parse("");
            teacherPhoto = imageViewUri.toString();
        }else {
            teacherPhoto = imageViewUri.toString();
        }
        if (!staffSavings1.isEmpty() && !staffSavingsPerMonth1.isEmpty() ){
            Psalary -= Double.parseDouble(staffSavingsPerMonth1);

        }else if (!staffSavings1.isEmpty() &&  staffSavingsPerMonth1.isEmpty()){
            staff_savings_per_month1.setError("Input staff savings per Month");
            staff_savings_per_month1.requestFocus();
            return;
        }else if (staffSavings1.isEmpty() && !staffSavingsPerMonth1.isEmpty()){
            staff_savings1.setError("Input staff Total savings");
            staff_savings1.requestFocus();
            return;
        }else {
            staffSavings1 = "";
            staffSavingsPerMonth1 = "";
        }

        if (!staffBonus1.isEmpty()){
            bonus = Double.parseDouble(staffBonus1);
            Psalary += bonus;
        }else {
            staffBonus1 = "";
        }


        Date date = new Date();
        Date newDate = new Date(date.getTime());
        SimpleDateFormat dt = new SimpleDateFormat("EEE,dd MMM, yy");
        createdDate = dt.format(newDate);
        trackingDate = createdDate;

        String id = "";





        addToFireStore(staff_name1,staff_position1,staff_phoneNumber1,staffSalary1,staffBonus1,staffDeduction1,staffSavings1,staffSavingsPerMonth1,
                staffLoan1,staffLoanPayPerMonth1,
                staff_bankAccountName1,staff_AccountNumber1,
                staff_BankName1,staff_commentary1,createdDate,teacherImageUri,Psalary,staff_salary_increment1,id,trackingDate,remainingLoan,loanPaid);


    }
    private void addToFireStore(String staff_name1, String staff_position1, String staff_phoneNumber1, String staffSalary1, String staffBonus1,
                                String staffDeduction1,
                                String staffSavings1, String staffSavingsPerMonth1, String staffLoan1, String staffLoanPayPerMonth1, String staff_bankAccountName1,
                                String staff_accountNumber1, String staff_bankName1, String staff_commentary1, String createdDate, String teacherImageUri,double payable,String staff_salary_increment1,String id,
                                String trackingDate,String remainingLoan,String loanPaid) {

        ProgressDialog mProgressDiaglog = new ProgressDialog(SecondaryStaff.this);
        mProgressDiaglog.setCancelable(false);
        mProgressDiaglog.setCanceledOnTouchOutside(false);
        mProgressDiaglog.setMessage("Adding new Staff..");
        mProgressDiaglog.show();

        CollectionReference dbPCAStaff = db.collection("PHS");


        PHS phs = new PHS(staff_name1,staff_position1,staff_phoneNumber1,staffSalary1,staffBonus1,staffDeduction1,staffSavings1,staffSavingsPerMonth1,
                staffLoan1,staffLoanPayPerMonth1,staff_bankAccountName1,staff_accountNumber1,staff_bankName1,staff_commentary1,
                createdDate,teacherImageUri,staff_salary_increment1,id,payable,trackingDate,remainingLoan,loanPaid);

        dbPCAStaff.add(phs).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                DocumentReference updateId = db.collection("PHS").document(documentReference.getId());
                updateId.update("id",documentReference.getId());

                mProgressDiaglog.dismiss();
                Toast.makeText(SecondaryStaff.this, "New Staff Added", Toast.LENGTH_SHORT).show();
                Intent in = new Intent(SecondaryStaff.this, PHSTeachers.class);
                startActivity(in);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SecondaryStaff.this, "Fail to add staff \n" + e, Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.phs_teacher_add_account,menu);
        createAccount = menu.findItem(R.id.phs_add_teacher);
        createAccount.setVisible(false);

        staff_Salary1.addTextChangedListener(new TextWatcher() {
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
    private void getImageImage1() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ContextCompat.checkSelfPermission(SecondaryStaff.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(SecondaryStaff.this, "Permission denied", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(SecondaryStaff.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
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

    public void updateUri(){
        fileRef  = FirebaseStorage.getInstance().getReference().child("secondary_teacher_image").child(imageViewUri.getLastPathSegment());


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
                staff_Salary1.removeTextChangedListener(this);


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
                    staff_Salary1.setText(formattedString);
                    staff_Salary1.setSelection(staff_Salary1.getText().length());



                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                staff_Salary1.addTextChangedListener(this);

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

                staff_bonus1.removeTextChangedListener(this);


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

                    staff_bonus1.setText(formattedString);
                    staff_bonus1.setSelection(staff_bonus1.getText().length());


                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }


                staff_bonus1.addTextChangedListener(this);

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

                staff_deduction1.removeTextChangedListener(this);

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


                    staff_deduction1.setText(formattedString);
                    staff_deduction1.setSelection(staff_deduction1.getText().length());

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }


                staff_deduction1.addTextChangedListener(this);
            }
        };
    }
    private TextWatcher onTextChangedListener3() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                staff_savings1.removeTextChangedListener(this);

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


                    staff_savings1.setText(formattedString);
                    staff_savings1.setSelection(staff_savings1.getText().length());

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }


                staff_savings1.addTextChangedListener(this);
            }
        };
    }

    private TextWatcher onTextChangedListener4() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                staff_savings_per_month1.removeTextChangedListener(this);

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


                    staff_savings_per_month1.setText(formattedString);
                    staff_savings_per_month1.setSelection(staff_savings_per_month1.getText().length());

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }


                staff_savings_per_month1.addTextChangedListener(this);
            }
        };
    }

    private TextWatcher onTextChangedListener5() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                staff_loan1.removeTextChangedListener(this);

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


                    staff_loan1.setText(formattedString);
                    staff_loan1.setSelection(staff_loan1.getText().length());

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }


                staff_loan1.addTextChangedListener(this);
            }
        };
    }

    private TextWatcher onTextChangedListener6() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                staff_loan_pay_per_month1.removeTextChangedListener(this);

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


                    staff_loan_pay_per_month1.setText(formattedString);
                    staff_loan_pay_per_month1.setSelection(staff_loan_pay_per_month1.getText().length());

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }


                staff_loan_pay_per_month1.addTextChangedListener(this);
            }
        };
    }

}