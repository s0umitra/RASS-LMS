package com.example.lms;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class    Register extends AppCompatActivity {
    Database db;
    EditText mTextUsername;
    EditText mTextPassword;
    EditText mTextCnfPassword;
    Button mButtonRegister;
    CheckBox showpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reregister);
        mTextUsername = findViewById(R.id.from);
        mTextPassword = findViewById(R.id.to);
        mTextCnfPassword = findViewById(R.id.edittext_cnf_password);
        mButtonRegister = findViewById(R.id.Submit);
        showpassword = findViewById(R.id.showpassword2);
        showpassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mTextCnfPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mTextPassword.setSelection(mTextPassword.getText().length());
                    mTextCnfPassword.setSelection(mTextCnfPassword.getText().length());
                }
                else {
                    mTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mTextCnfPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mTextPassword.setSelection(mTextPassword.getText().length());
                    mTextCnfPassword.setSelection(mTextCnfPassword.getText().length());
                }
            }
        });

        db = new Database(this);
        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mTextCnfPassword.getText().toString().equals(mTextPassword.getText().toString())){
                    Toast.makeText(Register.this, "Password does not MATCH!", Toast.LENGTH_LONG).show();
                    return;
                }
                AdminInfo ad = new AdminInfo();
                if (mTextPassword.getText().toString().isEmpty() && mTextUsername.getText().toString().isEmpty()){
                    Toast.makeText(Register.this, "Enter Username and Password!", Toast.LENGTH_LONG).show();
                }else if (mTextUsername.getText().toString().isEmpty()){
                    Toast.makeText(Register.this, "Enter Username!", Toast.LENGTH_LONG).show();
                }else if (mTextPassword.getText().toString().isEmpty()){
                    Toast.makeText(Register.this, "Enter Password!", Toast.LENGTH_LONG).show();
                }else{
                    ad.setUsername(mTextUsername.getText().toString());
                    ad.setPass(mTextPassword.getText().toString());
                    boolean isInserted = db.addAdmin(ad);
                    if (isInserted)
                        Toast.makeText(Register.this, "Registration Successful", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(Register.this, "Registration Failed", Toast.LENGTH_LONG).show();
                    Intent buttonIntent = new Intent(Register.this, Homepage.class);
                    startActivity(buttonIntent);
                }
            }
        });
    }
}