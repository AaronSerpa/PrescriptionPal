package edu.sjsu.android.prescriptionpal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class PrescriptionAdd extends AppCompatActivity {
    EditText prescriptionRxEntry, prescriptionNameEntry, patientNameEntry, refillNumEntry;
    String prescriptionRx, prescriptionName, patientName;
    int refillNum;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    private static final String TAG = "PrescriptionAdd";
    Prescription prescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_add);

        prescriptionRxEntry = findViewById(R.id.prescription_rx);
        prescriptionNameEntry = findViewById(R.id.prescription_name);
        patientNameEntry = findViewById(R.id.patient_name);
        refillNumEntry = findViewById(R.id.refill_num);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();
    }

    public void addPrescription(View view) {
        prescriptionRx = prescriptionRxEntry.getText().toString().trim();
        prescriptionName = prescriptionNameEntry.getText().toString().trim();
        patientName = patientNameEntry.getText().toString().trim();
        refillNum = Integer.parseInt(refillNumEntry.getText().toString().trim());

        DocumentReference documentReference = fStore.collection("patient_test").document(userId);

        prescription = new Prescription(prescriptionRx, prescriptionName, patientName, refillNum);

        documentReference.collection("Prescriptions")
                .add(prescription)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    public void onSuccess(DocumentReference documentReference1) {
                        Toast.makeText(PrescriptionAdd.this,"Prescription Added", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PrescriptionAdd.this,"Error", Toast.LENGTH_SHORT).show();
                    }
                });

        startActivity(new Intent(getApplicationContext(), PrescriptionDisplay.class));
    }

    public void cancelAddPrescription(View view) {
        startActivity(new Intent(getApplicationContext(), PrescriptionDisplay.class));
    }
}