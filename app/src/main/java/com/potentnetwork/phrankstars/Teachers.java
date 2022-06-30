package com.potentnetwork.phrankstars;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.potentnetwork.phrankstars.PHS.PHSTeacherAdapter;
import com.potentnetwork.phrankstars.PHS.PHSTeachers;
import com.potentnetwork.phrankstars.PHS.RecyclerViewDataPass;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Teachers extends AppCompatActivity implements TeacherAdapter.OnItemClickListener {
    RecyclerView teacherRecyclerView;
    List<PCA> mTeacher;
    TeacherAdapter teacherAdapter;
    Toolbar teacherToolbar;
    RecyclerViewDataPass recyclerViewDataPass;

    DatabaseReference teacherDbReference;
    DatabaseReference dbReference;
    ValueEventListener valueEventListener;

    FirebaseFirestore db;

    PCA pca;

    ProgressBar progressBar;
    String loginId;

    double remainingLoanUpdate,StaffLoanPaid;
    long diff,seconds,minutes,hours,days;
    int count = 1;
    int count2 = 1;

    WriteBatch batch;
    double saving,savingPerMth;
    String currentDate,trackingDate;
    boolean isFinished = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers);

        loginId = getIntent().getStringExtra("User");

        teacherToolbar = findViewById(R.id.teachers_toolBar);
        setSupportActionBar(teacherToolbar);
        getSupportActionBar().setTitle("Basic School Staff");

       progressBar = findViewById(R.id.spin_kit1);
        Sprite wave = new Wave();
        progressBar.setIndeterminateDrawable(wave);

        db = FirebaseFirestore.getInstance();
        teacherDbReference = FirebaseDatabase.getInstance().getReference();
        dbReference = teacherDbReference.child("TeacherProfile");


        mTeacher = new ArrayList<>();
        teacherRecyclerView = findViewById(R.id.teacher_recyclerView);
        teacherAdapter = new TeacherAdapter(this,mTeacher,this,recyclerViewDataPass);
        teacherRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        teacherRecyclerView.setAdapter(teacherAdapter);




        db.collection("PCA").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()){
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : list) {
                        // after getting this list we are passing
                        // that list to our object class.
                        PCA pc = d.toObject(PCA.class);
                        progressBar.setVisibility(View.INVISIBLE);
//                        pc.setId(d.getId());

                        mTeacher.add(pc);

                        // and we will pass this object class
                        // inside our arraylist which we have
                        // created for recycler view.

                    }
                    teacherAdapter.notifyDataSetChanged();

                }else{
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(Teachers.this, "No staff yet", Toast.LENGTH_LONG).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Teachers.this, "Failed to get data", Toast.LENGTH_SHORT).show();
            }
        });



//       if (!mTeacher.isEmpty()){
//           RecyclerViewDataPass recyclerViewDataPass = new RecyclerViewDataPass() {
//               @Override
//               public void pass(String trackingDate, String staffLoan, String remainingLoan, String staffLoanPaid, double Payable,
//                                String loanPayPerMonth, String id, String staffSavings, String staffSavingsPerMonth,String staffDeduction,String staffName) {
//
//
//                   Date date = new Date();
//                   Date newDate = new Date(date.getTime());
//                   SimpleDateFormat dt = new SimpleDateFormat("EEE,dd MMM, yy");
//                   currentDate = dt.format(newDate);
//
//
//
//                   Calendar calendar = Calendar.getInstance();
//                   calendar.add(Calendar.DAY_OF_YEAR, 1);
//                   Date tomorrow = calendar.getTime();
//
//
//                   String string_date = trackingDate;
//                   SimpleDateFormat f = new SimpleDateFormat("EEE,dd MMM, yy");
//                   SimpleDateFormat f2 = new SimpleDateFormat("EEE,dd MMM, yy");
//                   try {
//                       Date d = f.parse(string_date);
//                       Date d2 = f2.parse(currentDate);
//                       long milliseconds = d.getTime();
//                       long milliseconds2 = d2.getTime();
//
//
//                       long milliseconds3 = tomorrow.getTime();
//
//                       diff = milliseconds3 - milliseconds;
//                       seconds = diff / 1000;
//                       minutes = seconds / 60;
//                       hours = minutes / 60;
//                       days = (hours / 24) + 1;
//                       Log.d("days", "" + days);
////            staffLoanPaid12.setText(String.valueOf(days));
//                   } catch (ParseException e) {
//                       e.printStackTrace();
//                   }
//
//
//
//
//
//                   if (!trackingDate.isEmpty()){
//                       if (days >= 2 && !staffLoan.isEmpty()) {
//                           // clear staff debt,add staff debt to payable,
//                           //update remaining loan
//                           // update total savings; total savings + savings per month
//                           // update total loan paid,add it to firebase
//                           // update payable+debt
//                           // update trackingDate to dateOfUpdate
////                customProgressDialog.show();
//
//                           remainingLoanUpdate = Double.parseDouble(remainingLoan);
//                           StaffLoanPaid = Double.parseDouble(staffLoanPaid);
//
//                           date = new Date();
//                           newDate = new Date(date.getTime());
//                           dt = new SimpleDateFormat("EEE,dd MMM, yy");
//                           trackingDate = dt.format(newDate);
//
//                           count++;
//                           isFinished = true;
//                           StaffLoanPaid += Double.parseDouble(loanPayPerMonth);
//                           remainingLoanUpdate -= Double.parseDouble(loanPayPerMonth);
//
//
//
//                           batch = db.batch();
//                           // Update the population of 'SF'
//                           DocumentReference sfRef = db.collection("PCA").document(id);
//
//
//
//                           double payableUpdate = Payable;
//                           if (!staffDeduction.equals("")){
//                               payableUpdate += Double.parseDouble(staffDeduction);
//                           }
//
//                           batch.update(sfRef, "staffDeduction1", "");
//                           batch.update(sfRef, "staffBonus1", "");
//
//                           batch.update(sfRef, "payable", payableUpdate);
//                           batch.update(sfRef, "loanPaid",String.valueOf(StaffLoanPaid));
//                           batch.update(sfRef, "remainingLoan",String.valueOf(remainingLoanUpdate));
//                           batch.update(sfRef, "trackingDate",trackingDate);
//
//// Delete the city 'LA'
////            DocumentReference laRef = db.collection("cities").document("LA");
////            batch.delete(laRef);
//
//// Commit the batch
//                           batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
//                               @Override
//                               public void onComplete(@NonNull Task<Void> task) {
//                                   // ...
//                                   if (task.isSuccessful()){
////                            teacherRecyclerView.removeAllViews();
//                                   }
//                               }
//                           });
//
//
//                           if (remainingLoanUpdate < 0) {
//                               isFinished = true;
//                               payableUpdate += Double.parseDouble(loanPayPerMonth);
//                               WriteBatch batch2;
//                               batch2 = db.batch();
//                               DocumentReference sfRef2 = db.collection("PCA").document(id);
//                               batch2.update(sfRef2, "staffLoan1","");
//                               batch2.update(sfRef2, "staffLoanPayPerMonth1","");
//                               batch2.update(sfRef2, "remainingLoan","");
//                               batch2.update(sfRef2, "payable",payableUpdate);
//                               batch2.update(sfRef2, "loanPaid","");
//                               batch2.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                   @Override
//                                   public void onComplete(@NonNull Task<Void> task) {
//                                       // ...
//                                       if (task.isSuccessful()){
////                                            teacherRecyclerView.removeAllViews();
//                                           Toast.makeText(Teachers.this, staffName+" Loan Cleared", Toast.LENGTH_SHORT).show();
//
//                                       }
//                                   }
//                               });
//
//                           }
//
//                       }
//
//                       if (days >= 2 && !staffSavings.isEmpty()){
//                           //Increase staff savings using savings per month
//                           count2++;
////                customProgressDialog.show();
//
//                           saving = Double.parseDouble(staffSavings);
//                           savingPerMth = Double.parseDouble(staffSavingsPerMonth);
//                           if (!staffSavings.isEmpty()){
//                               isFinished = true;
//
//                               saving += savingPerMth;
//                               WriteBatch batch3;
//                               batch3 = db.batch();
//                               DocumentReference sfRef3 = db.collection("PCA").document(id);
//                               batch3.update(sfRef3, "staffSavings1",String.valueOf(saving));
//                               batch3.update(sfRef3, "trackingDate",trackingDate);
//                               batch3.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                   @Override
//                                   public void onComplete(@NonNull Task<Void> task) {
//                                       // ...
//                                       if (task.isSuccessful()){
////                                             teacherRecyclerView.removeAllViews();
////                                customProgressDialog.dismiss();
////                                Intent i = new Intent(PrimaryStaffProfile.this, Teachers.class);
////                                startActivity(i);
////                                finish();
//
//                                       }
//                                   }
//                               });
//                           }else {
//                               saving = Double.parseDouble("");
//                               savingPerMth = Double.parseDouble("");
//                           }
//
//                       }
//                   }
//                   isFinished = false;
//
//
//               }
//           };
//           teacherAdapter.notifyDataSetChanged();
//       }





    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.teachers_menu, menu);
        MenuItem item=menu.findItem(R.id.Staff_name_search);
        SearchView searchView=(SearchView)item.getActionView();
        searchView.setQueryHint("Search name");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ProcessSearch(query);
                return false;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                ProcessSearch(newText);
                return false;
            }
        });
//        return super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.add:
                    Intent addTeacher = new Intent(Teachers.this,PrimaryStaff.class);
                    startActivity(addTeacher);
                    break;
            }

        return super.onOptionsItemSelected(item);
    }


    private void ProcessSearch(String s) {
        ArrayList<PCA> mainlist = new ArrayList<>();
        for (PCA pca:mTeacher){
            if (pca.getStaff_name1().toLowerCase().contains(s.toLowerCase())){
                mainlist.add(pca);

            }
        }
        TeacherAdapter teacherAdapter= new TeacherAdapter(Teachers.this,mainlist,this,recyclerViewDataPass);
        teacherRecyclerView.setAdapter(teacherAdapter);




    }


    @Override
    public void onItemclick(int position) {
        pca = mTeacher.get(position);

        if (loginId.equals("BOSS")){
            loginId = "BOSS";
        }else {
            loginId = "ADMIN";
        }

        Intent staffProfile = new Intent(Teachers.this,PrimaryStaffProfile.class);
        staffProfile.putExtra("primary_staff",pca);
        staffProfile.putExtra("User",loginId);
       startActivity(staffProfile);

    }


}