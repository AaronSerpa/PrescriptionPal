package edu.sjsu.android.prescriptionpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class PrescriptionDetail extends AppCompatActivity {
    EditText prescriptionRxEntry, prescriptionNameEntry, patientNameEntry, refillNumEntry;
    String rx, name, patient;
    int refills;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("", "in PrescriptionDetail");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_detail);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        rx = getIntent().getStringExtra("rx");
        name = getIntent().getStringExtra("name");
        patient = getIntent().getStringExtra("patient");
        refills = getIntent().getIntExtra("refills", 0);

        prescriptionRxEntry = findViewById(R.id.detail_RX_entry);
        prescriptionNameEntry = findViewById(R.id.detail_name_entry);
        patientNameEntry = findViewById(R.id.detail_patient_entry);
        refillNumEntry = findViewById(R.id.detail_refills_entry);

        prescriptionRxEntry.setText(rx);
        prescriptionNameEntry.setText(name);
        patientNameEntry.setText(patient);
        refillNumEntry.setText(String.valueOf(refills));
    }

    public void updatePrescription(View view) {
        final String newRx = prescriptionRxEntry.getText().toString().trim();
        final String newName = prescriptionNameEntry.getText().toString().trim();
        final String newPatient = patientNameEntry.getText().toString().trim();
        final int newRefill = Integer.parseInt(refillNumEntry.getText().toString().trim());

        fStore.collection("patient_test").document(userId).collection("Prescriptions")
                .whereEqualTo("rx", rx)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                document.getReference().update(
                                        "rx", newRx,
                                        "name", newName,
                                        "patient", newPatient,
                                        "refills", newRefill
                                );
                                Toast.makeText(PrescriptionDetail.this,"Prescription Updated", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), PrescriptionDisplay.class));
                            }
                        } else {
                            Toast.makeText(PrescriptionDetail.this,"Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void deletePrescription(View view) {
        fStore.collection("patient_test").document(userId).collection("Prescriptions")
                .whereEqualTo("rx", rx)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                document.getReference().delete();
                                Toast.makeText(PrescriptionDetail.this,"Prescription Deleted", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), PrescriptionDisplay.class));
                            }
                        } else {
                            Toast.makeText(PrescriptionDetail.this,"Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}