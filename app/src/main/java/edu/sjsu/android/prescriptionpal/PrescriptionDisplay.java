package edu.sjsu.android.prescriptionpal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PrescriptionDisplay extends AppCompatActivity {
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_display);

        final ListView listView = (ListView) findViewById(R.id.listview_prescription);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        final DocumentReference documentReference = fStore.collection("patient_test").document(userId);
        documentReference.collection("Prescriptions").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                ArrayList<String> prescriptionsDisplay = new ArrayList<>();
                final ArrayList<Prescription> prescriptions = new ArrayList<>();

                for (QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots) {
                    Prescription prescription = documentSnapshot.toObject(Prescription.class);

                    String data = "";
                    data += "  Prescription: " + prescription.getName() + "\n  For: " + prescription.getPatient() + "\n  Refills Left: " + prescription.getRefills()
                            + "\n  RX#: " + prescription.getRx();
                    prescriptionsDisplay.add(data);

                    prescriptions.add(new Prescription(prescription.getRx(), prescription.getName(), prescription.getPatient(), prescription.getRefills()));
                }

                ArrayAdapter adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.activity_listview, prescriptionsDisplay);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Prescription selected = prescriptions.get(position);
                        Intent intent = new Intent(getApplicationContext(), PrescriptionDetail.class);
                        intent.putExtra("rx", selected.getRx());
                        intent.putExtra("name", selected.getName());
                        intent.putExtra("patient", selected.getPatient());
                        intent.putExtra("refills", selected.getRefills());
                        startActivity(intent);
                    }
                });
            }
        });
    }

    public void navToAdd(View view) {
        startActivity(new Intent(this, PrescriptionAdd.class));
    }
}