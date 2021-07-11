package edu.sjsu.android.prescriptionpal;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class additional_patient extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText firstName, lastName, age, contact, address, insurance, hsafsa;
    Spinner pharmacy, doctor, relationship;
    Button signupBtn, addBtn, backBtn;
    FirebaseFirestore fstore;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userID;
    ArrayList<String> pharmacyList = new ArrayList<>();
    ArrayList<String> doctorList = new ArrayList<>();
    ArrayList<String> relationshipList = new ArrayList<>();
    Map<String,Object> pharmacyMap = new HashMap<>();
    Map<String,Object> doctorMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_patient);

        pharmacyList.add("PHARMACY (REQUIRED)");
        doctorList.add("DOCTOR (REQUIRED)");
        relationshipList.add("Spouse");
        relationshipList.add("Child");
        relationshipList.add("Grandparent");
        relationshipList.add("Friend/Other");
        fstore = FirebaseFirestore.getInstance();

        fstore.collection("pharmacy_list").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshot, FirebaseFirestoreException e) {
                for (DocumentSnapshot snapshot : documentSnapshot) {
                    for (int i = 1; i <= 5; i++) {
                        String record_name = "PH0000" + i;
                        String temp = snapshot.getString(record_name + ".Pharmacy_Name") + "\n"
                                + "Address:" + snapshot.getString(record_name + ".Pharmacy_Address");
                        pharmacyList.add(temp);
                    }
                }
            }
        });

        fstore.collection("doctor_list").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshot, FirebaseFirestoreException e) {
                for (DocumentSnapshot snapshot : documentSnapshot) {
                    for (int i = 1; i <= 3; i++) {
                        String record_name = "DR0000" + i;
                        String temp = snapshot.getString(record_name + ".Doctor_Name") + "\n"
                                + "Contact-Number:" + snapshot.getString(record_name + ".Contact_Number");
                        doctorList.add(temp);
                    }
                }
            }
        });

        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        age = findViewById(R.id.age);
        contact = findViewById(R.id.contact_number);
        address = findViewById(R.id.address);
        final Spinner pharmacySpinner = (Spinner) findViewById(R.id.pharmacy);
        pharmacy = findViewById(R.id.pharmacy);
        final Spinner doctorSpinner = (Spinner) findViewById(R.id.doctor);
        doctor = findViewById(R.id.doctor);
        insurance = findViewById(R.id.insurance);
        hsafsa = findViewById(R.id.hsafsa);
        final Spinner mySpinner2 = (Spinner) findViewById(R.id.relationship);
        relationship = findViewById(R.id.relationship);
        addBtn = findViewById(R.id.add_people);
        backBtn = findViewById(R.id.button_single_account_back);
        signupBtn = findViewById(R.id.button_signup);

        final ArrayAdapter<String> pharmacyAdapter = new ArrayAdapter<String>(additional_patient.this,
                android.R.layout.simple_list_item_1, pharmacyList);
        pharmacyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pharmacy.setAdapter(pharmacyAdapter);

        final ArrayAdapter<String> doctorAdapter = new ArrayAdapter<String>(additional_patient.this,
                android.R.layout.simple_list_item_1, doctorList);
        doctorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        doctor.setAdapter(doctorAdapter);

        final ArrayAdapter<String> relationshipAdapter = new ArrayAdapter<String>(additional_patient.this,
                android.R.layout.simple_list_item_1, relationshipList);
        relationshipAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        relationship.setAdapter(relationshipAdapter);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userID = user.getUid();
                DocumentReference documentReference = fstore.collection("patientAccountForm").document(userID);
                Map<String,Object> updates = new HashMap<>();
                updates.put("Additional_People", FieldValue.delete());
                documentReference.update(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
                previousScreen();
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstNameInput = firstName.getText().toString().trim();
                String lastNameInput = lastName.getText().toString().trim();
                String ageInput = age.getText().toString().trim();
                String contactInput = contact.getText().toString().trim();
                String addressInput = address.getText().toString().trim();
                // These inputs may be empty as they were optional
                String insuranceInput = insurance.getText().toString().trim();
                String hsafsaInput = hsafsa.getText().toString().trim();
                String relationshipInput = mySpinner2.getSelectedItem().toString();

                String[] nameAndAddress = pharmacySpinner.getSelectedItem().toString().split("Address:",2);
                if(!((pharmacySpinner.getSelectedItem().toString()).contains("REQUIRED"))) {
                    pharmacyMap.put("Pharmacy_Name", nameAndAddress[0]);
                    pharmacyMap.put("Pharmacy_Address", nameAndAddress[1]);
                }

                String[] numberAndName = doctorSpinner.getSelectedItem().toString().split("Contact-Number:",2);
                if(!((doctorSpinner.getSelectedItem().toString()).contains("REQUIRED"))) {
                    doctorMap.put("Doctor_Name", numberAndName[0]);
                    doctorMap.put("Contact_Number", numberAndName[1]);
                }

                // Requirements and restrictions for inputs
                if (TextUtils.isEmpty(firstNameInput)) {
                    firstName.setError("First Name is required.");
                    return;
                }
                if (TextUtils.isEmpty(lastNameInput)) {
                    lastName.setError("Last Name is required.");
                    return;
                }
                if (TextUtils.isEmpty(ageInput)) {
                    age.setError("Age is required.");
                    return;
                }
                if (TextUtils.isEmpty(contactInput)) {
                    contact.setError("Contact # is required.");
                    return;
                }
                if (contactInput.length() < 10 || contactInput.length() > 11) {
                    contact.setError("Please enter a valid contact #");
                    return;
                }
                if (TextUtils.isEmpty(addressInput)) {
                    address.setError("Address is required.");
                    return;
                }
                if (!(Character.isDigit(addressInput.charAt(0)) && Character.isDigit(addressInput.charAt(addressInput.length()-1)))) {
                    address.setError("Please enter a valid address");
                    return;
                }
                if((pharmacySpinner.getSelectedItem().toString()).contains("REQUIRED")) {
                    TextView errorText = (TextView)pharmacySpinner.getSelectedView();
                    errorText.setError("");
                    errorText.setTextColor(Color.RED);//just to highlight that this is an error
                    errorText.setText("Please select a pharmacy");//changes the selected item text to this
                    return;
                }
                if((doctorSpinner.getSelectedItem().toString()).contains("REQUIRED")) {
                    TextView errorText = (TextView)doctorSpinner.getSelectedView();
                    errorText.setError("");
                    errorText.setTextColor(Color.RED);//just to highlight that this is an error
                    errorText.setText("Please select a pharmacy");//changes the selected item text to this
                    return;
                }
                if (!(TextUtils.isEmpty(hsafsaInput))) {
                    if (!(hsafsaInput.contains("HSA")) && !(hsafsaInput.contains("FSA"))) {
                        hsafsa.setError("Please enter a valid HSA/FSA Account #");
                        return;
                    }
                }


                userID = user.getUid();
                DocumentReference documentReference = fstore.collection("patientAccountForm").document(userID);
                    documentReference.update(
                            "Additional_People.first_name", firstNameInput,
                            "Additional_People.last_name", lastNameInput,
                            "Additional_People.age", ageInput,
                            "Additional_People.contact-number", contactInput,
                            "Additional_People.postal-address", addressInput,
                            "Additional_People.pharmacy", pharmacyMap,
                            "Additional_People.doctor", doctorMap,
                            "Additional_People.insurance", insuranceInput,
                            "Additional_People.HSA_or_FSA_ACC_#", hsafsaInput,
                            "Additional_People.Relationship", relationshipInput
                    );

                nextScreen();

            }
        });

    }

    public void previousScreen() {
        Intent intent = new Intent(this, SingleAccountSignup.class);
        startActivity(intent);
    }

    public void nextScreen() {
        Intent intent = new Intent(this, PatientHomePage.class);
        startActivity(intent);
    }
}
