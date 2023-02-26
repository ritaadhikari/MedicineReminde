package com.example.medicinetime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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

public class RegisterActivity extends AppCompatActivity {
    TextView btn ;


    private Button btnSignUp;
    private EditText etUsername, etFullName, etEmail, etPassword, etConfirmPassword;
    private String username, fullname, email, password, confirmPassword;
    String emailPattren = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
    ProgressDialog progressDialog;

    FirebaseAuth nAuth;
    FirebaseUser nUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn = findViewById(R.id.tvAlready);
        btnSignUp = findViewById(R.id.btnSignUp);
        etUsername = findViewById(R.id.etUsername);
        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        progressDialog = new ProgressDialog(this);
        nAuth = FirebaseAuth.getInstance();
        nUser = nAuth.getCurrentUser();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (signupValidation()) {
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    // to write msg after button is clicked
                    Toast.makeText(RegisterActivity.this, "Please enter details correctly", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        setAppBar();
    }
    private void setAppBar(){

        if(getSupportActionBar()!= null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

    }

    private boolean signupValidation() {
        username = etUsername.getText().toString();
        fullname = etFullName.getText().toString();
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
        confirmPassword = etConfirmPassword.getText().toString();
        if (username.isEmpty() || username.length()<7) {
            etUsername.setError("Username is not valid");
            return false;
        }
        if (fullname.isEmpty()) {
            etFullName.setError("FullName cannot be blank");
            return false;
        }
        if (!email.matches(emailPattren)){
            etEmail.setError("Email is not valid");
            return false;
        }
        if (password.isEmpty() || password.length()<7) {
            etPassword.setError("Password cannot be blank");
            return false;
        }
        if(!password.equals(confirmPassword)){
            etConfirmPassword.setError("password should be similar");
        }
        else{
            progressDialog.setMessage("Please wait while Registration");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            nAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(RegisterActivity.this,"Registration",Toast.LENGTH_SHORT).show();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this,""+task.getException(),Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
        return true;
    }

    private void sendUserToNextActivity() {
        Intent intent=new Intent(RegisterActivity.this, MedicineActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);startActivity(intent);
    }
    @Override
//back button lea kam garna ko lagi back ko id saddhai yei nai hunxa
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){//id liyako
            onBackPressed();//event call
        }
        return super.onOptionsItemSelected(item);//default generated
    }
}
