package com.hacktiv8.finalproject4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private EditText txtRegistEmail, txtRegistPassword, txtRegistConfirmPassword;
    private TextView loginTV;
    private Button btnRegister;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        txtRegistEmail = findViewById(R.id.txtRegisterEmail);
        txtRegistPassword = findViewById(R.id.txtRegisterPassword);
        txtRegistConfirmPassword = findViewById(R.id.txtRegisterConfirmPassword);
        btnRegister = findViewById(R.id.btn_createaccount);

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Silahkan Menunggu");
        progressDialog.setCancelable(false);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hiding our progress bar.
                progressDialog.show();
                //getting data fro =m our edit text.
                String userName = txtRegistEmail.getText().toString();
                String pwd = txtRegistPassword.getText().toString();
                String cnfPwd = txtRegistConfirmPassword.getText().toString();
                //checking if the password and confirm password is equal or not.
                if (!pwd.equals(cnfPwd)) {
                    Toast.makeText(RegisterActivity.this, "Please check both having same password..", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(userName) && TextUtils.isEmpty(pwd) && TextUtils.isEmpty(cnfPwd)) {
                    //checking if the text fields are empty or not.
                    Toast.makeText(RegisterActivity.this, "Please enter your credentials..", Toast.LENGTH_SHORT).show();
                } else {
                    //on below line we are creating a new user by passing email and password.
                    mAuth.createUserWithEmailAndPassword(userName, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //on below line we are checking if the task is success or not.
                            if (task.isSuccessful()) {
                                //in on success method we are hiding our progress bar and opening a login activity.
                                progressDialog.dismiss();
                                Toast.makeText(RegisterActivity.this, "User Registered..", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(i);
                                finish();
                            } else {
                                //in else condition we are displaying a failure toast message.
                                progressDialog.dismiss();
                                Toast.makeText(RegisterActivity.this, "Fail to register user..", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}