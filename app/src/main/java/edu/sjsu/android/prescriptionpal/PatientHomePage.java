package edu.sjsu.android.prescriptionpal;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;

public class PatientHomePage extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home_page);

        toolbar = findViewById(R.id.mToolbar);
        setSupportActionBar(toolbar);
        toolbar.showOverflowMenu();
    }

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

    public void goPharmacyPage(View view){
        Intent intent = new Intent(this, ShowPharmacy.class);
        startActivity(intent);
    }

    public void goPreferencePage(View view){
        Intent intent = new Intent(this, ShowPreference.class);
        startActivity(intent);
    }

    public void goPrescriptionPage(View view){
        Intent intent = new Intent(this, PrescriptionDisplay.class);
        startActivity(intent);
    }

    public void clickOnBack(View view){
        Intent intent = new Intent(this, InitAccType.class);
        startActivity(intent);
    }

}