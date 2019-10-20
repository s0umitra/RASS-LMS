package com.example.lms;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddEmp extends AppCompatActivity {

    static final String[] Name = new String[] {
            "Shubhada", "Shaurya", "Snehal", "Soumitra", "Aishwarya", "Rucha"
    };

    static final String[] Email = new String[] {
      "shubh@gmail.com", "shaurya@gmail.com", "snehal@gmail.com", "soumitra@gmail.com", "aish@gmail.com", "rucha@gmail.com"
    };

    Database db;

    //EditText mTextName;
    //EditText mTextEmail;
    EditText mTextUsername;
    EditText mTextPass;

    Button mButtonSubmit;
    int aid;

    AutoCompleteTextView editText;
    AutoCompleteTextView editMail;

    EmpInfo em;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.readdemp);
        db = new Database(this);

        //mTextName = findViewById(R.id.editText_name);
        //mTextEmail = findViewById(R.id.editText_email);
        mTextUsername = findViewById(R.id.editText_username);
        mTextPass = findViewById(R.id.editText_pass);

        mButtonSubmit = findViewById(R.id.btsub);
        aid = getIntent().getIntExtra("AID",aid);

        editText = (AutoCompleteTextView)findViewById(R.id.name);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,Name);
        editText.setAdapter(adapter);

        editMail = (AutoCompleteTextView)findViewById(R.id.email);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,Email);
        editMail.setAdapter(adapter1);

        AddEmployee();
    }


    public void AddEmployee() {
        mButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                em = new EmpInfo();

                if (editMail.getText().toString().isEmpty()){
                    Toast.makeText(AddEmp.this, "Enter Email", Toast.LENGTH_LONG).show();
                }else if (editText.getText().toString().isEmpty()){
                    Toast.makeText(AddEmp.this, "Enter Name", Toast.LENGTH_LONG).show();
                }else if (mTextUsername.getText().toString().isEmpty()){
                    Toast.makeText(AddEmp.this, "Enter Username", Toast.LENGTH_LONG).show();
                }else if (mTextPass.getText().toString().isEmpty()){
                    Toast.makeText(AddEmp.this, "Enter Password", Toast.LENGTH_LONG).show();
                }else{
                    em.setUsername(mTextUsername.getText().toString());
                    em.setPass(mTextPass.getText().toString());
                    em.setName(editText.getText().toString());
                    em.setEmail(editMail.getText().toString());

                    boolean isInserted = db.addEmpl(em);

                    if (isInserted == true)
                        Toast.makeText(AddEmp.this, "Employee Added", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(AddEmp.this, "Employee NOT Added", Toast.LENGTH_LONG).show();

                    Intent buttonIntent = new Intent(AddEmp.this, AdminPage.class);
                    buttonIntent.putExtra("AID",aid);
                    startActivity(buttonIntent);
                }
            }
        });
    }
}
