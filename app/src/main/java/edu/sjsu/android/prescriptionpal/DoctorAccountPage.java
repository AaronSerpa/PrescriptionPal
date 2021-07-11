package edu.sjsu.android.prescriptionpal;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.FirebaseFirestore;

public class DoctorAccountPage extends AppCompatActivity {

    EditText doctorname, doctorID;
    Button addDocBtn;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        doctorname = findViewById(R.id.doctorname);
        doctorID = findViewById(R.id.doctorID);
        addDocBtn = findViewById(R.id.addDocBtn);


//        addDocBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String name = doctorname.getText().toString().trim();
//                String id = doctorID.getText().toString().trim();
//
//                // Requirements and restrictions for inputs
//                if (TextUtils.isEmpty(name)) {
//                    doctorname.setError("Doctor's name is required.");
//                    return;
//                }
//                if (TextUtils.isEmpty(id)) {
//                    doctorID.setError("Doctor's License # is required.");
//                    return;
//                }
//
//                mAuth.createUserWithEmailAndPassword(name,id).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(DoctorAccountPage.this, "Doctor Added", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                        } else {
//                            Toast.makeText(DoctorAccountPage.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//            }
//        });
    }
}
