package com.potentnetwork.phrankstars.PHS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.potentnetwork.phrankstars.PCA;
import com.potentnetwork.phrankstars.PrimaryStaff;
import com.potentnetwork.phrankstars.PrimaryStaffProfile;
import com.potentnetwork.phrankstars.R;
import com.potentnetwork.phrankstars.TeacherAdapter;
import com.potentnetwork.phrankstars.Teachers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PHSTeachers extends AppCompatActivity implements PHSTeacherAdapter.OnItemClickListener {

    RecyclerView phsTeacherRecyclerView;
    List<PHS> phsTeacher;
    PHSTeacherAdapter phsTeacherAdapter;
    Toolbar teacherToolbar;
    FirebaseFirestore db;

    PHS phs;

    ProgressBar progressBar;
    String loginId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phsteachers);


        teacherToolbar = findViewById(R.id.phs_teachers_toolBar);
        setSupportActionBar(teacherToolbar);
        getSupportActionBar().setTitle("High School Staff");

        progressBar = findViewById(R.id.phs_spin_kit1);
        Sprite wave = new Wave();
        progressBar.setIndeterminateDrawable(wave);

        db = FirebaseFirestore.getInstance();
        loginId = getIntent().getStringExtra("User");



        phsTeacher = new ArrayList<>();
        phsTeacherRecyclerView = findViewById(R.id.phs_teacher_recyclerView);
        phsTeacherAdapter = new PHSTeacherAdapter(this,phsTeacher,this);
        phsTeacherRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        phsTeacherRecyclerView.setAdapter(phsTeacherAdapter);








        db.collection("PHS").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()){
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : list) {
                        // after getting this list we are passing
                        // that list to our object class.
                        PHS phs = d.toObject(PHS.class);
                        progressBar.setVisibility(View.INVISIBLE);
//                        pc.setId(d.getId());

                        phsTeacher.add(phs);

                        // and we will pass this object class
                        // inside our arraylist which we have
                        // created for recycler view.

                    }
                    phsTeacherAdapter.notifyDataSetChanged();

                }else{
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(PHSTeachers.this, "No staff yet", Toast.LENGTH_LONG).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PHSTeachers.this, "Failed to get data", Toast.LENGTH_SHORT).show();
            }
        });





    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.phs_teacher_account, menu);
        MenuItem item=menu.findItem(R.id.Phs_Staff_name_search);
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
            case R.id.phs_add:
                Intent addTeacher = new Intent(PHSTeachers.this, SecondaryStaff.class);
                startActivity(addTeacher);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private void ProcessSearch(String s) {
        ArrayList<PHS> mainlist = new ArrayList<>();
        for (PHS phs: phsTeacher){
            if (phs.getStaff_name1().toLowerCase().contains(s.toLowerCase())){
                mainlist.add(phs);

            }
        }
        PHSTeacherAdapter phsTeacherAdapter= new PHSTeacherAdapter(PHSTeachers.this,mainlist,this);
        phsTeacherRecyclerView.setAdapter(phsTeacherAdapter);


    }


    @Override
    public void onItemclick(int position) {
        phs = phsTeacher.get(position);

        if (loginId.equals("BOSS")){
            loginId = "BOSS";
        }else {
            loginId = "ADMIN";
        }
        Intent staffProfile = new Intent(PHSTeachers.this, SecondaryStaffProfile.class);
        staffProfile.putExtra("phs_primary_staff", phs);
        staffProfile.putExtra("User",loginId);
        startActivity(staffProfile);

    }

}