package edu.sjsu.android.prescriptionpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText email, password;
    Button loginpBtn;
    FirebaseAuth fAuth;
    Bundle b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_pw);
        loginpBtn = findViewById(R.id.button_login);
        b = getIntent().getExtras();
        fAuth = FirebaseAuth.getInstance();

        loginpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailInput = email.getText().toString().trim();
                String passwordInput = password.getText().toString().trim();
                if (TextUtils.isEmpty(emailInput)) {
                    email.setError("Email is required.");
                    return;
                }
                if (TextUtils.isEmpty(passwordInput)) {
                    password.setError("Password is required.");
                    return;
                }
                fAuth.signInWithEmailAndPassword(emailInput, passwordInput).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Login.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), InitialLoggedinScreen.class);
                          //  i.putExtras(b);
                            startActivity(i);
                            
                        }
                        else {
                            Toast.makeText(Login.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    public void switchToSignup(View view) {
        Intent intent = new Intent(this, Signup.class);
        startActivity(intent);
    }
}
