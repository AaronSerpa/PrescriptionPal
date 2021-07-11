package edu.sjsu.android.prescriptionpal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChangePharmacy extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ListView listView;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    String newPharmacyName;
    String newPharmacyAddr;
    String newPharmacyHours;
    ArrayList<String> arrayList = new ArrayList<>();
    Map<String, String> pharmacyMap = new HashMap<>();
    Map<String, String> pharmacyHour = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pharmacy);
        listView = (ListView) findViewById(R.id.listView_pharmacy_list);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();

        //DocumentReference documentReference = fStore.collection("pharmacy_list").document(userId);
        fStore.collection("pharmacy_list").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshot, FirebaseFirestoreException e) {
                for(DocumentSnapshot snapshot : documentSnapshot){
                    for(int i = 1; i <= 5; i++){
                        String record_name = "PH0000" + i;
                        String tempPharmacyName = snapshot.getString( record_name + ".Pharmacy_Name");
                        String tempPharmacyAddr = snapshot.getString(record_name + ".Pharmacy_Address");
                        String tempPharmacyHours = snapshot.getString(record_name + ".Hours");
//                        String temp = snapshot.getString( record_name + ".Pharmacy_Name") + "\n"
//                               + snapshot.getString(record_name + ".Pharmacy_Address");
//                                + "\n" + snapshot.getString(record_name + ".Hours");
                        arrayList.add(tempPharmacyName + "\n" + tempPharmacyAddr);
                        pharmacyMap.put(tempPharmacyName, tempPharmacyAddr);
                        pharmacyHour.put(tempPharmacyName,tempPharmacyHours);
                    }
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String> (getApplicationContext(), android.R.layout.simple_selectable_list_item, arrayList);
                arrayAdapter.notifyDataSetChanged();
                listView.setAdapter(arrayAdapter);
            }
        });

        // get the current pharmacy details
        DocumentReference documentReference = fStore.collection("patientAccountForm").document(userId);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    newPharmacyName = document.getString("pharmacy.Pharmacy_Name");
//                    newPharmacyAddr = document.getString("Pharmacy.Pharmacy_Address");
//                    newPharmacyHours = document.getString("Pharmacy.Pharmacy_Hours");
                }
            }
        });

        // Dropdown list
        List<String> pharmacyList = new ArrayList<String>();
//        for(String key: pharmacyMap.keySet()){
//            pharmacyList.add(key);
//        }
        pharmacyList.add("Select a new pharmacy store");
        pharmacyList.add("ABC Pharmacy");
        pharmacyList.add("Medicine Here");
        pharmacyList.add("Costco Pharmacy");
        pharmacyList.add("Pharmacy Store");
        pharmacyList.add("San Mateo Pharmacy");

        Spinner dropdown = findViewById(R.id.spinner1);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, pharmacyList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(dataAdapter);
        dropdown.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(!parent.getItemAtPosition(position).toString().equals("Select a new pharmacy store")){
            newPharmacyName = parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // do nothing
    }

    public void saveNewPharmacy(View view){
        fStore.collection("patientAccountForm").document(userId)
                .update(
                        "pharmacy.Pharmacy_Name", newPharmacyName,
                        "pharmacy.Pharmacy_Address", pharmacyMap.get(newPharmacyName),
                        "pharmacy.Pharmacy_Hours", pharmacyHour.get(newPharmacyName)
                );
        Intent intent = new Intent(this, ShowPharmacy.class);
        startActivity(intent);
    }

    public void goBack(View view){
        Intent intent = new Intent(this, ShowPharmacy.class);
        startActivity(intent);
    }
}