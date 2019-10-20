package com.example.lms;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Homepage extends AppCompatActivity {
    Button login;
    TextView register;
    EditText uuser;
    EditText upass;
    CheckBox showpass;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rehomepage);
        uuser = findViewById(R.id.muser);
        upass = findViewById(R.id.mpass);
        login = findViewById(R.id.btlog);
        register = findViewById(R.id.there);
        showpass = findViewById(R.id.spass2);
        db = new Database(this);
        showpass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    upass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    upass.setSelection(upass.getText().length());
                }
                else {
                    upass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    upass.setSelection(upass.getText().length());
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cus = db.AgetData();
                for (cus.moveToFirst(); !cus.isAfterLast(); cus.moveToNext()) {
                    String aduser = cus.getString(1);
                    if (aduser == null) {
                        continue;
                    }
                    if (uuser.getText().toString().equals(aduser)) {
                        String adpass = cus.getString(2);
                        if (upass.getText().toString().equals(adpass)) {
                            int aid = cus.getInt(0);
                            Intent loginIntent = new Intent(Homepage.this, AdminPage.class);
                            loginIntent.putExtra("AID",aid);
                            Log.d("teseeeeeee",""+aid);
                            startActivity(loginIntent);
                            return;
                        }
                    }
                }
                Cursor res = db.EgetData();
                for (res.moveToFirst(); !res.isAfterLast(); res.moveToNext()) {
                    String aduser = res.getString(2);
                    if (aduser == null) {
                        continue;
                    }
                    if (uuser.getText().toString().equals(aduser)) {
                        String adpass = res.getString(3);
                        if (upass.getText().toString().equals(adpass)) {
                            String empid = (res.getInt(0)+"");
//                            Log.d("tes",""+empid);
                            Intent loginIntent = new Intent(Homepage.this, EmpPage.class);
                            loginIntent.putExtra("EID", empid);
                            startActivity(loginIntent);
                            return;
                        }
                    }
                }
                Toast.makeText(Homepage.this, "Incorrect Username/Password", Toast.LENGTH_LONG).show();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regIntent = new Intent(Homepage.this, Register.class);
                startActivity(regIntent);
            }
        });
    }
}
