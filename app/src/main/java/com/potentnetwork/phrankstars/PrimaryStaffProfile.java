package com.potentnetwork.phrankstars;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.widget.NestedScrollView;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PrimaryStaffProfile extends AppCompatActivity {


    FloatingActionButton main_button, printButton, editButton, deleteButton;
    boolean isOpen = false;

    TextView staffName12, staffPosition12, staffPhoneNumber12, createdDate12, currentDate12,
            mainSalary12, payable12, salary_increment12, bonus12, debt12, loan12, loanPayPerMonth12, savings12,
            savingsPerMonth12, bankAccountName12, bankAccountNumber12, bankName12, commentary12,staffLoanPaid12;

    TextView printed_date, staffName13, staffPosition13, staffPhoneNumber13, deduction13, bonus13, savings13,
            savingsPerMonth13, loan13, toalLoanPaid13, loanPerMnth13, accountName13, accountNumber13, bank13, mainSalary13, payable13;
    ImageView staffImage12, staffImage13;
    String currentDate,trackingDate,today;
    boolean isFinished = false;



    Bitmap bitmap;
    Bitmap bitmap1;




    String file_name = "Screenshot";
    File myPath;

    String mPath;
    File imageFile;
    PCA pca1;
    boolean boolean_save;

    private Uri imageViewUri = null;
    String teacherImageUri;
    ImageView update_roundedimageView1;

    FirebaseFirestore db;

    CardView update_staff_image;

    double Psalary, PsalaryIncrement, bonus;
    String teacherPhoto;
    Dialog dialog;
    ImageButton update_primary_staff,delete_staff;

    String update_staff_name11, update_staff_position11, update_staff_basic_salary11, staff_basic_salary_increment11, update_staff_phone_number11, update_staff_bonus11,
            update_staff_deduction11, update_staff_savings11, update_staff_savings_per_month11, update_staff_loan11, update_staff_loan_pay_per_month11,
            update_staff_bank_account_name11, update_staff_bank_account_number11, update_staff_bank_name11, update_staff_commentary11, createdDate, id,trackingDate1,
            remainingLoan,loanPaid;

    TextInputEditText update_staff_name, update_staff_position, update_staff_basic_salary, staff_basic_salary_increment, update_staff_phone_number,
            update_staff_bonus, update_staff_deduction, update_staff_savings, update_staff_savings_per_month, update_staff_loan, update_staff_loan_pay_per_month,
            update_staff_bank_account_name, update_staff_bank_account_number, update_staff_bank_name, update_staff_commentary;

    TextInputLayout update_staff_name22, update_staff_position22, update_staff_basic_salary22, staff_basic_salary_increment22, update_staff_phone_number22,
            update_staff_bonus22, update_staff_deduction22, update_staff_savings22, update_staff_savings_per_month22, update_staff_loan22, update_staff_loan_pay_per_month22,
            update_staff_bank_account_name22, update_staff_bank_account_number22, update_staff_bank_name22, update_staff_commentary22;

    String printedDate;
    double remainingLoanUpdate,StaffLoanPaid,MainBasicSalary;
    long diff,seconds,minutes,hours,days;
    int count = 1;
    int count2 = 1;

    WriteBatch batch;
    double saving,savingPerMth;
    TextView disAbledstaff;
    String boss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary_staff_profile);

        CustomProgressDialog customProgressDialog = new CustomProgressDialog(this);

        pca1 = (PCA) getIntent().getSerializableExtra("primary_staff");
         boss = getIntent().getStringExtra("User");




        db = FirebaseFirestore.getInstance();


        main_button = findViewById(R.id.main_button);
        printButton = findViewById(R.id.printButton);
        editButton = findViewById(R.id.editButton);
        deleteButton = findViewById(R.id.deleteButton);
        Animation openAnim = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        Animation closeAnim = AnimationUtils.loadAnimation(this, R.anim.fab_close);
        disAbledstaff = findViewById(R.id.disAbledstaff);

        staffName12 = findViewById(R.id.staffName12);
        staffPosition12 = findViewById(R.id.staffPosition12);
        staffPhoneNumber12 = findViewById(R.id.staffPhoneNumber12);
        staffImage12 = findViewById(R.id.staffImage12);
        createdDate12 = findViewById(R.id.createdDate12);
        currentDate12 = findViewById(R.id.currentDate12);
        mainSalary12 = findViewById(R.id.mainSalary12);
        payable12 = findViewById(R.id.payable12);
        salary_increment12 = findViewById(R.id.salary_increment12);
        bonus12 = findViewById(R.id.bonus12);
        debt12 = findViewById(R.id.debt12);
        loan12 = findViewById(R.id.loan12);
        loanPayPerMonth12 = findViewById(R.id.loanPayPerMonth12);
        savings12 = findViewById(R.id.savings12);
        savingsPerMonth12 = findViewById(R.id.savingsPerMonth12);
        bankAccountName12 = findViewById(R.id.bankAccountName12);
        bankAccountNumber12 = findViewById(R.id.bankAccountNumber12);
        bankName12 = findViewById(R.id.bankName12);
        commentary12 = findViewById(R.id.commentary12);
        staffLoanPaid12 = findViewById(R.id.staffLoanPaid12);
        delete_staff = findViewById(R.id.delete_staff);

        Date date = new Date();
        Date newDate = new Date(date.getTime());
        SimpleDateFormat dt = new SimpleDateFormat("EEE,dd MMM, yy");
        currentDate = dt.format(newDate);



        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();


        String string_date = pca1.getTrackingDate();
        SimpleDateFormat f = new SimpleDateFormat("EEE,dd MMM, yy");
        SimpleDateFormat f2 = new SimpleDateFormat("EEE,dd MMM, yy");
        try {
            Date d = f.parse(string_date);
            Date d2 = f2.parse(currentDate);
            long milliseconds = d.getTime();
            long milliseconds2 = d2.getTime();


            long milliseconds3 = tomorrow.getTime();

            diff = milliseconds2 - milliseconds;
            seconds = diff / 1000;
            minutes = seconds / 60;
            hours = minutes / 60;
            days = (hours / 24) + 1;
            Log.d("days", "" + days);
            staffLoanPaid12.setText(String.valueOf(days) );
        } catch (ParseException e) {
            e.printStackTrace();
        }





        if (!pca1.getTrackingDate().isEmpty()){
            if (days >= 25 && !pca1.getStaffLoan1().isEmpty()) {
                // clear staff debt,add staff debt to payable,
                //update remaining loan
                // update total savings; total savings + savings per month
                // update total loan paid,add it to firebase
                // update payable+debt
                // update trackingDate to dateOfUpdate
                customProgressDialog.show();
                remainingLoanUpdate = Double.parseDouble(pca1.getRemainingLoan());
                StaffLoanPaid = Double.parseDouble(pca1.getLoanPaid());

                 date = new Date();
                 newDate = new Date(date.getTime());
                 dt = new SimpleDateFormat("EEE,dd MMM, yy");
                 trackingDate = dt.format(newDate);

                count++;
                isFinished = true;
                StaffLoanPaid += Double.parseDouble(pca1.getStaffLoanPayPerMonth1());
                remainingLoanUpdate -= Double.parseDouble(pca1.getStaffLoanPayPerMonth1());



                 batch = db.batch();
                // Update the population of 'SF'
                DocumentReference sfRef = db.collection("PCA").document(pca1.getId());



                double payableUpdate = pca1.getPayable();
                if (!pca1.getStaffDeduction1().equals("")){
                    payableUpdate += Double.parseDouble(pca1.getStaffDeduction1());
                }

                batch.update(sfRef, "staffDeduction1", "");
                batch.update(sfRef, "staffBonus1", "");

                batch.update(sfRef, "payable", payableUpdate);
                batch.update(sfRef, "loanPaid",String.valueOf(StaffLoanPaid));
                batch.update(sfRef, "remainingLoan",String.valueOf(remainingLoanUpdate));
                batch.update(sfRef, "trackingDate",trackingDate);

// Delete the city 'LA'
//            DocumentReference laRef = db.collection("cities").document("LA");
//            batch.delete(laRef);

// Commit the batch
                batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                        if (task.isSuccessful()){
                            customProgressDialog.dismiss();
                            Intent i = new Intent(PrimaryStaffProfile.this, Teachers.class);
                            i.putExtra("User",boss);
                            startActivity(i);
                            finish();

                        }
                    }
                });


                if (remainingLoanUpdate < 0) {
                    isFinished = true;
                    payableUpdate += Double.parseDouble(pca1.getStaffLoanPayPerMonth1());
                    WriteBatch batch2;
                    batch2 = db.batch();
                    DocumentReference sfRef2 = db.collection("PCA").document(pca1.getId());
                    batch2.update(sfRef2, "staffLoan1","");
                    batch2.update(sfRef2, "staffLoanPayPerMonth1","");
                    batch2.update(sfRef2, "remainingLoan","");
                    batch2.update(sfRef2, "payable",payableUpdate);
                    batch2.update(sfRef2, "loanPaid","");
                    batch2.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            // ...
                            if (task.isSuccessful()){
                                customProgressDialog.dismiss();
                                Intent i = new Intent(PrimaryStaffProfile.this, Teachers.class);
                                i.putExtra("User",boss);
                                startActivity(i);
                                finish();
                                Toast.makeText(PrimaryStaffProfile.this, pca1.getStaff_name1()+" Loan Cleared", Toast.LENGTH_LONG).show();

                            }
                        }
                    });

//                    DocumentReference ClearStaffLoan = db.collection("PCA").document(pca1.getId());
//                    ClearStaffLoan.update("staffLoan1","").addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void unused) {
//                            customProgressDialog.dismiss();
//                            Toast.makeText(PrimaryStaffProfile.this, "Staff Loan Cleared", Toast.LENGTH_SHORT).show();
//                        }
//                    });




                    // if staffloanpaid is greater than staffloan, clear them from database
                    //add every details subtracted when creating the staff account



                    //  if deduction or bonus is not empty,clear them

                    //clear staff loan and the loan pay per month from database
                    // refresh the database
                    // Update Admin that total loan has been paid by staff(using Toast)
                    // update staff savings to database


                }

            }

            if (days >= 25 && !pca1.getStaffSavings1().isEmpty()){
                //Increase staff savings using savings per month
                count2++;
                customProgressDialog.show();
                saving = Double.parseDouble(pca1.getStaffSavings1());
                savingPerMth = Double.parseDouble(pca1.getStaffSavingsPerMonth1());
                if (!pca1.getStaffSavings1().isEmpty()){
                    isFinished = true;

                    saving += savingPerMth;
                    WriteBatch batch3;
                    batch3 = db.batch();
                    DocumentReference sfRef3 = db.collection("PCA").document(pca1.getId());
                    batch3.update(sfRef3, "staffSavings1",String.valueOf(saving));
                    batch3.update(sfRef3, "trackingDate",trackingDate);
                    batch3.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            // ...
                            if (task.isSuccessful()){
                                customProgressDialog.dismiss();
                                Intent i = new Intent(PrimaryStaffProfile.this, Teachers.class);
                                i.putExtra("User",boss);
                                startActivity(i);
                                finish();

                            }
                        }
                    });
                }else {
                    saving = Double.parseDouble("");
                    savingPerMth = Double.parseDouble("");
                }

            }
        }
        isFinished = false;

        if (boss.equals("ADMIN")){
            mainSalary12.setVisibility(View.INVISIBLE);
            payable12.setVisibility(View.INVISIBLE);
            delete_staff.setVisibility(View.INVISIBLE);
        }
        createdDate12.setText(pca1.getCreatedDate());
        staffName12.setText(pca1.getStaff_name1());
        staffPosition12.setText(pca1.getStaff_position1());
        mainSalary12.setText("#" + pca1.getStaffSalary1());
        String payable = String.valueOf(pca1.getPayable());
        staffPhoneNumber12.setText(pca1.getStaff_phoneNumber1());
        payable12.setText("#" + payable);
        currentDate12.setText(currentDate);
        Glide.with(PrimaryStaffProfile.this).load(pca1.getTeacherImageUri()).centerCrop().into(staffImage12);
        bonus12.setText(pca1.getStaffBonus1());
        salary_increment12.setText(pca1.getStaff_salary_increment1());
        debt12.setText(pca1.getStaffDeduction1());
        loan12.setText(pca1.getStaffLoan1());
        loanPayPerMonth12.setText(pca1.getStaffLoanPayPerMonth1());
        savings12.setText(pca1.getStaffSavings1());
        savingsPerMonth12.setText(pca1.getStaffSavingsPerMonth1());
        bankAccountName12.setText(pca1.getStaff_bankAccountName1());
        bankName12.setText(pca1.getStaff_bankName1());
        bankAccountNumber12.setText(pca1.getStaff_accountNumber1());
        commentary12.setText(pca1.getStaff_commentary1());
        staffLoanPaid12.setText(pca1.getLoanPaid());

        if (pca1.getTrackingDate().isEmpty()){
            disAbledstaff.setVisibility(View.VISIBLE);
        }




        if (staffImage12 != null) {
            teacherImageUri = pca1.getTeacherImageUri();
        } else {
            teacherImageUri = imageViewUri.toString();
        }

        commentary12.setText(pca1.getStaff_commentary1());

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdatePrimaryStaffDialog();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                deletStaff(pca1);
                disableStaff(pca1);

            }
        });


        printButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomDialog();
//                startActivity(new Intent(PrimaryStaffProfile.this,Printing_Layout.class));

//                                createPDFFile(Common.getAppPath(PrimaryStaffProfile.this)+"test_pdf.pdf");
            }
        });

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                })
                .check();


        main_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayFloatBtn();
            }
        });

        delete_staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletStaff(pca1);
            }
        });


    }

    private void disableStaff(PCA pca1) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(PrimaryStaffProfile.this);
        builder.setIcon(R.drawable.ic_warning);
        builder.setTitle("DISABLE STAFF?");
        builder.setMessage("Staff account will be deactivated");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ProgressDialog deleteDialog = new ProgressDialog(PrimaryStaffProfile.this);
                deleteDialog.setCancelable(false);
                deleteDialog.setIcon(R.mipmap.ic_launcher);
                deleteDialog.setCanceledOnTouchOutside(false);
                deleteDialog.setMessage("Deactivating Staff\n Please wait..");
                deleteDialog.show();
                db.collection("PCA").document(pca1.getId()).update("trackingDate","").addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    deleteDialog.dismiss();
                                    Toast.makeText(PrimaryStaffProfile.this, "Staff deactivated", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(PrimaryStaffProfile.this, Teachers.class);
                                    i.putExtra("User",boss);
                                    startActivity(i);

                                    if (!teacherImageUri.isEmpty()) {
                                        StorageReference photoRef = FirebaseStorage.getInstance().getReference().child("teacher_image").child(
                                                String.valueOf(Uri.parse(teacherImageUri)));
                                        photoRef.delete();
                                    }

                                }
                            }
                        }
                );

            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public void deletStaff(PCA pca1) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(PrimaryStaffProfile.this);
        builder.setIcon(R.drawable.ic_warning);
        builder.setTitle("DELETE STAFF?");
        builder.setMessage("Staff will be permanently deleted");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ProgressDialog deleteDialog = new ProgressDialog(PrimaryStaffProfile.this);
                deleteDialog.setCancelable(false);
                deleteDialog.setIcon(R.mipmap.ic_launcher);
                deleteDialog.setCanceledOnTouchOutside(false);
                deleteDialog.setMessage("Deleting Staff...");
                deleteDialog.show();
                db.collection("PCA").document(pca1.getId()).delete().addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    deleteDialog.dismiss();
                                    Toast.makeText(PrimaryStaffProfile.this, "Staff deleted", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(PrimaryStaffProfile.this, Teachers.class);
                                    i.putExtra("User",boss);
                                    startActivity(i);
                                    if (!teacherImageUri.isEmpty()) {
                                        StorageReference photoRef = FirebaseStorage.getInstance().getReference().child("teacher_image").child(
                                                String.valueOf(Uri.parse(teacherImageUri)));
                                        photoRef.delete();
                                    }

                                }
                            }
                        }
                );

            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }



    private void displayFloatBtn() {
        if (isOpen) {
//                    printButton.setAnimation(closeAnim);
//                    editButton.setAnimation(closeAnim);
//                    deleteButton.setAnimation(closeAnim);

            printButton.setVisibility(View.INVISIBLE);
            editButton.setVisibility(View.INVISIBLE);
            deleteButton.setVisibility(View.INVISIBLE);

            isOpen = false;
        } else {
            printButton.setVisibility(View.VISIBLE);
            editButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.VISIBLE);
////
//                    printButton.setAnimation(openAnim);
//                    editButton.setAnimation(openAnim);
//                    deleteButton.setAnimation(openAnim);

            isOpen = true;
        }
    }

    private void showBottomDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.print_layout);
        ImageButton print_Pdf_Btn = dialog.findViewById(R.id.btn_printPDF1);
        TextView disabledTxtView = dialog.findViewById(R.id.disabled);


        printed_date = dialog.findViewById(R.id.printed_date);
        staffName13 = dialog.findViewById(R.id.staffName13);
        staffPosition13 = dialog.findViewById(R.id.staffPosition13);
        staffPhoneNumber13 = dialog.findViewById(R.id.staffPhoneNumber13);
        staffImage13 = dialog.findViewById(R.id.staffImage13);
        deduction13 = dialog.findViewById(R.id.deduction13);
        bonus13 = dialog.findViewById(R.id.bonus13);
        savings13 = dialog.findViewById(R.id.savings13);
        savingsPerMonth13 = dialog.findViewById(R.id.savingsPerMonth13);
        loan13 = dialog.findViewById(R.id.loan13);
        toalLoanPaid13 = dialog.findViewById(R.id.toalLoanPaid13);
        loanPerMnth13 = dialog.findViewById(R.id.loanPerMnth13);
        accountName13 = dialog.findViewById(R.id.accountName13);
        accountNumber13 = dialog.findViewById(R.id.accountNumber13);
        bank13 = dialog.findViewById(R.id.bank13);
        mainSalary13 = dialog.findViewById(R.id.mainSalary13);
        payable13 = dialog.findViewById(R.id.payable13);
        Glide.with(PrimaryStaffProfile.this).load(pca1.getTeacherImageUri()).centerCrop().into(staffImage13);
        Date date = new Date();
        Date newDate = new Date(date.getTime());
        SimpleDateFormat dt = new SimpleDateFormat("EEE,dd MMM, yy");
        printedDate = dt.format(newDate);
        printed_date.setText("Printed on \n" + printedDate);
        staffName13.setText(pca1.getStaff_name1());
        staffPosition13.setText(pca1.getStaff_position1());
        staffPhoneNumber13.setText(pca1.getStaff_phoneNumber1());
        accountName13.setText(pca1.getStaff_bankAccountName1());
        accountNumber13.setText(pca1.getStaff_accountNumber1());
        bank13.setText(pca1.getStaff_bankName1());
        mainSalary13.setText("#" + pca1.getStaffSalary1());
        payable13.setText("#" + pca1.getPayable());

        mainSalary13.setVisibility(View.INVISIBLE);
        payable13.setVisibility(View.INVISIBLE);

        if (pca1.getTrackingDate().isEmpty()){
            disabledTxtView.setVisibility(View.VISIBLE);
        }
        if (!pca1.getStaffDeduction1().isEmpty()){
            deduction13.setText("#" + pca1.getStaffDeduction1());
        }else {
            deduction13.setText("");
        }
        if (!pca1.getStaffBonus1().isEmpty()){
            bonus13.setText("#" + pca1.getStaffBonus1());
        }else{
            bonus13.setText("");
        }

        if (!pca1.getStaffSavings1().isEmpty() && !pca1.getStaffSavingsPerMonth1().isEmpty()){
            savings13.setText("Total: \n#" + pca1.getStaffSavings1());
            savingsPerMonth13.setText("Monthly\n#" + pca1.getStaffSavingsPerMonth1());
        }else {
            savings13.setText("");
            savingsPerMonth13.setText("");
        }
        if (!pca1.getStaffLoan1().isEmpty() && !pca1.getStaffLoanPayPerMonth1().isEmpty()){
            loan13.setText("Total Loaned:\n #" + pca1.getStaffLoan1());
            loanPerMnth13.setText("Monthly \n#" + pca1.getStaffLoanPayPerMonth1());
            toalLoanPaid13.setText("Loan Paid \n#" + pca1.getLoanPaid());
        }else {
            loan13.setText("");
            loanPerMnth13.setText("");
            toalLoanPaid13.setText("");
        }




        print_Pdf_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                print_Pdf_Btn.setVisibility(View.GONE);
                mainSalary13.setVisibility(View.VISIBLE);
                payable13.setVisibility(View.VISIBLE);

                Date now = new Date();
                android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

                try {

                    // image naming and path  to include sd card  appending name you choose for file
                    mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

                    // create bitmap screen capture
                    NestedScrollView ticket = dialog.findViewById(R.id.ticket);

                    View v1 = ticket;
                    v1.setDrawingCacheEnabled(true);
                    bitmap1 = Bitmap.createBitmap(v1.getDrawingCache());
                    v1.setDrawingCacheEnabled(false);

                    imageFile = new File(mPath);

                    String extr = Environment.getExternalStorageDirectory() + "/PrintOut/";
                    String fileName = file_name + ".jpg";
                    myPath = new File(extr, fileName);
                    FileOutputStream outputStream = new FileOutputStream(imageFile);
                    int quality = 100;
                    bitmap1.compress(Bitmap.CompressFormat.PNG, quality, outputStream);
                    outputStream.flush();
                    outputStream.close();

                } catch (Throwable e) {
                    e.printStackTrace();
                }

                PdfDocument document = new PdfDocument();
                PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(bitmap1.getWidth(), bitmap1.getHeight(), 1).create();
                PdfDocument.Page page = document.startPage(pageInfo);

                Canvas canvas = page.getCanvas();


                Paint paint = new Paint();
                paint.setColor(Color.parseColor("#ffffff"));
                canvas.drawPaint(paint);



                bitmap = Bitmap.createScaledBitmap(bitmap1, bitmap1.getWidth(), bitmap1.getHeight(), true);

                paint.setColor(Color.BLUE);
                canvas.drawBitmap(bitmap, 0, 0 , null);
                document.finishPage(page);



                // write the document content
                File pathFile = new File(Environment.getExternalStorageDirectory() + "/PCA_STAFF_PDF");
                if (!pathFile.exists())
                    pathFile.mkdirs();

                File file = new File(pathFile,"/"+pca1.getStaff_name1()+".pdf");


                try {
                    document.writeTo(new FileOutputStream(file));
                         openPDF(file);
//                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                    intent.setType("application/pdf");
//                    startActivityForResult(intent, 1);
//                    finish();
                    // close the document
                    document.close();
                    boolean_save=true;
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
                }

                File filedelete = new File(mPath);
                boolean deleted = filedelete.delete();

                print_Pdf_Btn.setVisibility(View.VISIBLE);
                mainSalary13.setVisibility(View.INVISIBLE);
                payable13.setVisibility(View.INVISIBLE);
                dialog.dismiss();

            }
        });


        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.show();

    }

    private void openPDF(File file) {

        // Get the File location and file name.
        // Get the URI Path of file.
        Uri uriPdfPath = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", file);
        Log.d("pdfPath", "" + uriPdfPath);

        // Start Intent to View PDF from the Installed Applications.
        Intent pdfOpenIntent = new Intent(Intent.ACTION_VIEW);
        pdfOpenIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pdfOpenIntent.setClipData(ClipData.newRawUri("", uriPdfPath));
        pdfOpenIntent.setDataAndType(uriPdfPath, "application/pdf");
        pdfOpenIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION |  Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        try {
            startActivity(pdfOpenIntent);
        } catch (ActivityNotFoundException activityNotFoundException) {
            Toast.makeText(this,"There is no app to load corresponding PDF",Toast.LENGTH_LONG).show();

        }
    }


    public void UpdatePrimaryStaffDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_ACTION_BAR);
        dialog.setContentView(R.layout.update_primary_staff_layout);


        update_primary_staff = dialog.findViewById(R.id.update_primary_staff);

        db = FirebaseFirestore.getInstance();

        update_staff_name = dialog.findViewById(R.id.update_staff_name1);
        update_staff_position = dialog.findViewById(R.id.update_staff_position1);
        update_staff_basic_salary = dialog.findViewById(R.id.update_staff_basic_salary1);
        staff_basic_salary_increment = dialog.findViewById(R.id.staff_basic_salary_increment1);
        update_staff_phone_number = dialog.findViewById(R.id.update_staff_phone_number1);
        update_staff_bonus = dialog.findViewById(R.id.update_staff_bonus1);
        update_staff_deduction = dialog.findViewById(R.id.update_staff_deduction1);
        update_staff_savings = dialog.findViewById(R.id.update_staff_savings1);
        update_staff_savings_per_month = dialog.findViewById(R.id.update_staff_savings_per_month1);
        update_staff_loan = dialog.findViewById(R.id.update_staff_loan1);
        update_staff_loan_pay_per_month = dialog.findViewById(R.id.update_staff_loan_pay_per_month1);
        update_staff_bank_account_name = dialog.findViewById(R.id.update_staff_bank_account_name1);
        update_staff_bank_account_number = dialog.findViewById(R.id.update_staff_bank_account_number1);
        update_staff_bank_name = dialog.findViewById(R.id.update_staff_bank_name1);
        update_staff_commentary = dialog.findViewById(R.id.update_staff_commentary1);
        update_roundedimageView1 = dialog.findViewById(R.id.update_roundedimageView1);
        update_staff_image = dialog.findViewById(R.id.update_staff_image);

        //TextInputLayout
        update_staff_basic_salary22 = dialog.findViewById(R.id.update_staff_basic_salary);
        staff_basic_salary_increment22 = dialog.findViewById(R.id.staff_basic_salary_increment);


        update_staff_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImageImage1();
                dialog.dismiss();
            }
        });


        if (imageViewUri != null) {
            update_staff_image.setVisibility(View.INVISIBLE);
        } else {
            update_staff_image.setVisibility(View.VISIBLE);
        }
        //BOSS
        if (boss.equals("ADMIN")){
            update_staff_basic_salary22.setVisibility(View.INVISIBLE);
            staff_basic_salary_increment22.setVisibility(View.INVISIBLE);
        }

        Glide.with(PrimaryStaffProfile.this).load(pca1.getTeacherImageUri()).centerCrop().into(update_roundedimageView1);
        update_staff_name.setText(pca1.getStaff_name1());
        update_staff_position.setText(pca1.getStaff_position1());
        update_staff_basic_salary.setText(pca1.getStaffSalary1());
        staff_basic_salary_increment.setText(pca1.getStaff_salary_increment1());
        update_staff_phone_number.setText(pca1.getStaff_phoneNumber1());
        update_staff_bonus.setText(pca1.getStaffBonus1());
        update_staff_deduction.setText(pca1.getStaffDeduction1());
        update_staff_savings.setText(pca1.getStaffSavings1());
        update_staff_savings_per_month.setText(pca1.getStaffSavingsPerMonth1());
        update_staff_loan.setText(pca1.getStaffLoan1());
        update_staff_loan_pay_per_month.setText(pca1.getStaffLoanPayPerMonth1());
        update_staff_bank_account_name.setText(pca1.getStaff_bankAccountName1());
        update_staff_bank_account_number.setText(pca1.getStaff_accountNumber1());
        update_staff_bank_name.setText(pca1.getStaff_bankName1());
        update_staff_commentary.setText(pca1.getStaff_commentary1());


//         update_staff_name22 = dialog.findViewById(R.id.update_staff_name); update_staff_position22 = dialog.findViewById(R.id.update_staff_position);

//         update_staff_phone_number22 = dialog.findViewById(R.id.update_staff_phone_number); update_staff_bonus22 = dialog.findViewById(R.id.update_staff_bonus);
//         update_staff_deduction22 = dialog.findViewById(R.id.update_staff_deduction); update_staff_savings22 = dialog.findViewById(R.id.update_staff_savings);
//         update_staff_savings_per_month22 = dialog.findViewById(R.id.update_staff_savings_per_month); update_staff_loan22 = dialog.findViewById(R.id.update_staff_loan);
//         update_staff_loan_pay_per_month22 = dialog.findViewById(R.id.update_staff_loan_pay_per_month); update_staff_bank_account_name22 = dialog.findViewById(R.id.update_staff_bank_account_name);
//         update_staff_bank_account_number22 = dialog.findViewById(R.id.update_staff_bank_account_number); update_staff_bank_name22 = dialog.findViewById(R.id.update_staff_bank_name);
//         update_staff_commentary22 = dialog.findViewById(R.id.update_staff_commentary);


        update_primary_staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePrimaryStaffProfile();
            }
        });


        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.show();
    }

    private void updatePrimaryStaffProfile() {
        update_staff_name11 = update_staff_name.getText().toString();
        update_staff_position11 = update_staff_position.getText().toString().trim();
        update_staff_basic_salary11 = update_staff_basic_salary.getText().toString().trim();
        staff_basic_salary_increment11 = staff_basic_salary_increment.getText().toString().trim();
        update_staff_phone_number11 = update_staff_phone_number.getText().toString().trim();
        update_staff_bonus11 = update_staff_bonus.getText().toString().trim();
        update_staff_deduction11 = update_staff_deduction.getText().toString().trim();
        update_staff_savings11 = update_staff_savings.getText().toString().trim();
        update_staff_savings_per_month11 = update_staff_savings_per_month.getText().toString().trim();
        update_staff_loan11 = update_staff_loan.getText().toString().trim();
        update_staff_loan_pay_per_month11 = update_staff_loan_pay_per_month.getText().toString().trim();
        update_staff_bank_account_name11 = update_staff_bank_account_name.getText().toString().trim();
        update_staff_bank_account_number11 = update_staff_bank_account_number.getText().toString().trim();
        update_staff_bank_name11 = update_staff_bank_name.getText().toString().trim();
        update_staff_commentary11 = update_staff_commentary.getText().toString().trim();
        id =pca1.getId();
        remainingLoan = pca1.getRemainingLoan();
        loanPaid = pca1.getLoanPaid();
        createdDate = pca1.getCreatedDate();
        trackingDate1 =pca1.getTrackingDate();
        remainingLoan = pca1.getRemainingLoan();



        Psalary = Double.parseDouble(update_staff_basic_salary11);
        MainBasicSalary = Double.parseDouble(update_staff_basic_salary11);

        if (!staff_basic_salary_increment11.isEmpty() && !staff_basic_salary_increment11.equals(pca1.staff_salary_increment1)) {
            PsalaryIncrement = Double.parseDouble(staff_basic_salary_increment11);
            MainBasicSalary += PsalaryIncrement;
            Psalary += PsalaryIncrement;
            update_staff_basic_salary11 = String.valueOf(MainBasicSalary);

        }else if (!staff_basic_salary_increment11.isEmpty()){
            staff_basic_salary_increment11 = pca1.getStaff_salary_increment1();
        }
        else {
            staff_basic_salary_increment11 = "";
        }

        Psalary = Psalary - (Psalary / 100.0f) * 10;

        if (update_staff_basic_salary11.isEmpty()) {
            update_staff_basic_salary.setError("Staff salary required");
            update_staff_basic_salary.requestFocus();
            return;
        }

        if (update_staff_name11.isEmpty()) {
            update_staff_name.setError("Staff name required");
            update_staff_name.requestFocus();
            return;
        }
        if (update_staff_position11.isEmpty()) {
            update_staff_position.setError("Staff position required");
            update_staff_position.requestFocus();
            return;
        }

        if (!update_staff_loan11.isEmpty() && !update_staff_loan_pay_per_month11.isEmpty() && !update_staff_loan11.equals(pca1.getStaffLoan1()) &&
                !update_staff_loan_pay_per_month11.equals(pca1.getStaffLoanPayPerMonth1())) {
            double dstaffLoan = Double.parseDouble(update_staff_loan11);
            double dstaffLoanPayPerMonth = Double.parseDouble(update_staff_loan_pay_per_month11);

            Psalary = Psalary - dstaffLoanPayPerMonth;
            double loanBalance = dstaffLoan - dstaffLoanPayPerMonth;
            remainingLoan = String.valueOf(loanBalance);
            loanPaid = String.valueOf(dstaffLoanPayPerMonth);


        }else if (!update_staff_loan11.isEmpty() && !update_staff_loan_pay_per_month11.isEmpty() ){
            update_staff_loan11 = pca1.getStaffLoan1();
            update_staff_loan_pay_per_month11 = pca1.getStaffLoanPayPerMonth1();
        }
        else if (!update_staff_loan11.isEmpty() && update_staff_loan_pay_per_month11.isEmpty()) {
            update_staff_loan_pay_per_month.setError("Staff loan pay per month required");
            update_staff_loan_pay_per_month.requestFocus();
            return;
        }else if (update_staff_loan11.isEmpty() && !update_staff_loan_pay_per_month11.isEmpty()){
            update_staff_loan.setError("Staff loan amount required");
            update_staff_loan.requestFocus();
            return;
        }else {
            update_staff_loan11 = "";
            update_staff_loan_pay_per_month11 = "";
            remainingLoan = "";
            loanPaid = "";
        }




        if (!update_staff_deduction11.isEmpty() && !update_staff_deduction11.equals(pca1.getStaffDeduction1())) {
            double debt = Double.parseDouble(update_staff_deduction11);
            Psalary -= debt;
        }else if (!update_staff_deduction11.isEmpty()){
            update_staff_deduction11 = pca1.getStaffDeduction1();
        }
        else {
            update_staff_deduction11 = "";
        }


        if (TextUtils.isEmpty(update_staff_bank_account_name11)) {
            update_staff_bank_account_name11 = "";
        }
        if (update_staff_bank_account_number11.isEmpty()) {
            update_staff_bank_account_number11 = "";
        } else if (update_staff_bank_account_number11.length() != 10) {
            update_staff_bank_account_number.setError("Invalid account number");
            update_staff_bank_account_number.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(update_staff_bank_name11)) {
            update_staff_bank_name11 = "";
        }

        if (update_staff_phone_number11.isEmpty()) {
            update_staff_phone_number11 = "";
        } else if (update_staff_phone_number11.length() != 11) {
            update_staff_phone_number.setError("Invalid phone number");
            update_staff_phone_number.requestFocus();
            return;
        }

        if (update_staff_commentary11.isEmpty()) {
            update_staff_commentary11 = "";
        }

        if (imageViewUri == null) {
            imageViewUri = Uri.parse("");
            teacherPhoto = imageViewUri.toString();
        } else {
            teacherPhoto = imageViewUri.toString();
        }
        if (!update_staff_savings11.isEmpty() && !update_staff_savings_per_month11.isEmpty() && !update_staff_savings11.equals(pca1.getStaffSavings1())
        && !update_staff_savings_per_month11.equals(pca1.getStaffSavingsPerMonth1())) {
            Psalary -= Double.parseDouble(update_staff_savings_per_month11);
        }else if (!update_staff_savings11.isEmpty() && !update_staff_savings_per_month11.isEmpty()){
            update_staff_savings11 = pca1.getStaffSavings1();
            update_staff_savings_per_month11 = pca1.getStaffSavingsPerMonth1();
        }
        else if (!update_staff_savings11.isEmpty() && update_staff_savings_per_month11.isEmpty()){
            update_staff_savings_per_month.setError("Input staff savings per Month");
            update_staff_savings_per_month.requestFocus();
            return;
        }else if (update_staff_savings11.isEmpty() && !update_staff_savings_per_month11.isEmpty()){
            update_staff_savings.setError("Input staff Total savings");
            update_staff_savings.requestFocus();
            return;
        }else{
            update_staff_savings11 = "";
            update_staff_savings_per_month11 = "";
        }

        if (!update_staff_bonus11.isEmpty() && !update_staff_bonus11.equals(pca1.getStaffBonus1()) ) {
            bonus = Double.parseDouble(update_staff_bonus11);
            Psalary += bonus;
        }else if (!update_staff_bonus11.isEmpty()){
            update_staff_bonus11 = pca1.getStaffBonus1();
        }
        else {
            update_staff_bonus11 = "";
        }



        updateStaffProfile(pca1, update_staff_name11, update_staff_position11, update_staff_phone_number11, update_staff_basic_salary11, update_staff_bonus11,
                update_staff_deduction11, update_staff_savings11, update_staff_savings_per_month11, update_staff_loan11, update_staff_loan_pay_per_month11,
                update_staff_bank_account_name11, update_staff_bank_account_number11, update_staff_bank_name11, update_staff_commentary11, createdDate, teacherImageUri,
                staff_basic_salary_increment11, id, Psalary,trackingDate1,remainingLoan,loanPaid);
    }

    private void updateStaffProfile(PCA pca, String update_staff_name11, String update_staff_position11, String update_staff_phone_number11, String update_staff_basic_salary11,
                                    String update_staff_bonus11, String update_staff_deduction11, String update_staff_savings11,
                                    String update_staff_savings_per_month11, String update_staff_loan11, String update_staff_loan_pay_per_month11,
                                    String update_staff_bank_account_name11, String update_staff_bank_account_number11, String update_staff_bank_name11,
                                    String update_staff_commentary11, String createdDate, String teacherImageUri,
                                    String staff_basic_salary_increment11, String id, double psalary,String trackingDate,String remainingLoan,String loanPaid) {
        PCA updateCourse = new PCA(update_staff_name11, update_staff_position11, update_staff_phone_number11, update_staff_basic_salary11, update_staff_bonus11,
                update_staff_deduction11, update_staff_savings11, update_staff_savings_per_month11, update_staff_loan11, update_staff_loan_pay_per_month11,
                update_staff_bank_account_name11, update_staff_bank_account_number11, update_staff_bank_name11, update_staff_commentary11, createdDate, teacherImageUri,
                staff_basic_salary_increment11, id, psalary,trackingDate,remainingLoan,loanPaid);


        ProgressDialog mProgressDiaglog = new ProgressDialog(PrimaryStaffProfile.this);
        mProgressDiaglog.setCancelable(false);
        mProgressDiaglog.setCanceledOnTouchOutside(false);
        mProgressDiaglog.setMessage("Updating Staff Profile...");
        mProgressDiaglog.show();

        db.collection("PCA").document(pca.getId()).set(updateCourse).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                mProgressDiaglog.dismiss();
                dialog.dismiss();
                Toast.makeText(PrimaryStaffProfile.this, "Staff Profile Updated", Toast.LENGTH_LONG).show();
                Intent rfsh = new Intent(PrimaryStaffProfile.this, Teachers.class);
                rfsh.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                rfsh.putExtra("User",boss);
                startActivity(rfsh);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mProgressDiaglog.dismiss();
                dialog.dismiss();
                Toast.makeText(PrimaryStaffProfile.this, "Staff updating failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getImageImage1() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(PrimaryStaffProfile.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(PrimaryStaffProfile.this, "Permission denied", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(PrimaryStaffProfile.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            } else {

                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
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
                update_roundedimageView1.setImageURI(imageViewUri);
                if (imageViewUri == null) {
                    teacherImageUri = "";
                } else {
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

    public void updateUri() {
        final StorageReference fileRef = FirebaseStorage.getInstance().getReference().child("teacher_image").child(imageViewUri.getLastPathSegment());

        fileRef.putFile(imageViewUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            teacherImageUri = String.valueOf(uri);
                            Toast.makeText(getApplication(), "Processing image\n Click proceed icon to save image", Toast.LENGTH_LONG).show();
                            UpdatePrimaryStaffDialog();
                        }
                    });

                }
            }
        });
    }



}