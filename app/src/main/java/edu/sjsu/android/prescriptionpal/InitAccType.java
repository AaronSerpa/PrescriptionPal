package edu.sjsu.android.prescriptionpal;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class InitAccType extends AppCompatActivity {
    Button Patient;
    Button Doctor;
    Button Pharmacist;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userID;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_acc_type);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userID = user.getUid();


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.showOverflowMenu();

        Patient = findViewById(R.id.Patient);
        Doctor = findViewById(R.id.Doctor);
        Pharmacist = findViewById(R.id.Pharmacist);

        Patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int accType = 0;//temporary means of holding account type. still not grasping firebase completely.
                Bundle b = new Bundle();
                b.putInt("Account Type", accType);
                DocumentReference documentReference = fstore.collection("patientAccountForm").document(userID);

                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                startActivity(new Intent(getApplicationContext(), PatientHomePage.class));
                            } else {
                                startActivity(new Intent(getApplicationContext(), SingleAccountSignup.class));
                            }
                        }
                    }
                });
            }
        });
        Doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int accType = 1;
                Bundle b = new Bundle();
                b.putInt("Account Type", accType);
                Intent i = new Intent(getApplicationContext(), DoctorAccountPage.class);
                i.putExtras(b);
                startActivity(i);
            }
        });
        Pharmacist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int accType = 2;//temporary means of holding account type. still not grasping firebase completely.
                Bundle b = new Bundle();
                b.putInt("Account Type", accType);
                Intent i = new Intent(getApplicationContext(), InitialLoggedinScreen.class);
                i.putExtras(b);
                startActivity(i);
            }
        });

    }

//    public void clickOnDoctor(View view){
//        Intent intent = new Intent(this, DoctorAccountPage.class);
//        startActivity(intent);
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_log_out: {
                logout();
                break;
            }
        }
        return true;
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }

}
