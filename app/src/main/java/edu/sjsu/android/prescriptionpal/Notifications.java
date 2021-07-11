package edu.sjsu.android.prescriptionpal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

public class Notifications extends AppCompatActivity {

    Toolbar toolbar;
    Switch s1;
    Switch s2;
    Switch s3;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        s1 = (Switch)findViewById(R.id.NewPres);
        s2 = (Switch)findViewById(R.id.Refills);
        s3 = (Switch)findViewById(R.id.Updates);

        Context c = this;
        sp = c.getSharedPreferences("SharedPref", 0);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.showOverflowMenu();
        editor = sp.edit();
        s1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean("Switch5", s1.isChecked());
                editor.commit();
            }
        });
        s2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean("Switch6", s2.isChecked());
                editor.commit();
            }
        });
        s3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean("Switch7", s3.isChecked());
                editor.commit();
            }
        });
        s1.setChecked(sp.getBoolean("Switch5", false));
        s2.setChecked(sp.getBoolean("Switch6", false));
        s3.setChecked(sp.getBoolean("Switch7", false));

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
        startActivity(new Intent(getApplicationContext(), InitialLoggedinScreen.class));
        finish();
    }
}
