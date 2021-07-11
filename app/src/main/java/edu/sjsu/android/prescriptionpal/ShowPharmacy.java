package edu.sjsu.android.prescriptionpal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class ShowPharmacy extends AppCompatActivity {
    private TextView pharmacyName, pharmacyAddress, pharmacyHours;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pharmacy);

        pharmacyName = findViewById(R.id.textView_pharmacy_name);
        pharmacyAddress = findViewById(R.id.textView_pharmacy_address);
        pharmacyHours = findViewById(R.id.textView_pharmacy_hours);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        // documentReference contains the data in our database
        DocumentReference documentReference = fStore.collection("patientAccountForm").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                pharmacyName.setText(documentSnapshot.getString("pharmacy.Pharmacy_Name"));
                pharmacyAddress.setText(documentSnapshot.getString("pharmacy.Pharmacy_Address"));
                pharmacyHours.setText(documentSnapshot.getString("pharmacy.Pharmacy_Hours"));
            }
        });
    }

    public void changePharmacy(View view){
        Intent intent = new Intent(this, ChangePharmacy.class);
        startActivity(intent);
    }

    public void clickOnBack(View view){
        Intent intent = new Intent(this, PatientHomePage.class);
        startActivity(intent);
    }
}