package com.example.medicinetime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medicinetime.medicine.MedicineActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    public static  String PREFS_NAME = "MyPrefsFile";
    String TAG = "Firebase";
    private TextView tvSignin;
    private Button btnLogin;
    private String email, password;
    private EditText etEmail, etPassword;
    String emailPattren = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
    ProgressDialog progressDialog;

    FirebaseAuth nAuth;
    FirebaseUser nUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        progressDialog = new ProgressDialog(this);
        nAuth = FirebaseAuth.getInstance();
        nUser = nAuth.getCurrentUser();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loginValidation()) {
                    Intent intent = new Intent(LoginActivity.this, MedicineActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Please enter details correctly", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvSignin= findViewById(R.id.tvSign);
        tvSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    private boolean loginValidation() {
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
        if (!email.matches(emailPattren)){
            etEmail.setError("Email is not valid");
            return false;
        }
        if (password.isEmpty() || password.length()<7) {
            etPassword.setError("Password cannot be blank");
            return false;
        }
        else{
            progressDialog.setMessage("Please wait while Login");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            nAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(LoginActivity.this,"Login Sucessfull",Toast.LENGTH_SHORT).show();

                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this,""+task.getException(),Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
        return true;
    }

    private void sendUserToNextActivity() {
        Intent intent=new Intent(LoginActivity.this,MedicineActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);startActivity(intent);
    }
}

