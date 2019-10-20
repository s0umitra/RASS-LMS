package com.example.lms;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AdminPage extends AppCompatActivity {
    Database db;
    private Button addemp;
    private Button empdet;
    private Button leave;
    private Button logout;

    int aid;
    TextView cura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.readminpage);
        db = new Database(this);
        aid = getIntent().getIntExtra("AID",aid);
        cura = findViewById(R.id.textView11);
        Cursor emp = db.AgetDataFromId(aid);
        emp.moveToFirst();
        cura.setText("Current Admin : "+emp.getString(1));

        addemp = findViewById(R.id.button2);
        addemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(AdminPage.this,AddEmp.class);
                intent3.putExtra("AID",aid);
                startActivity(intent3);
                overridePendingTransition(R.anim.slidoutr, R.anim.slidinr);
            }
        });

        empdet = findViewById(R.id.button3);
        empdet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewAll();
            }
        });

        leave = findViewById(R.id.button4);
        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent5 = new Intent(AdminPage.this,AdminLeaveReq.class);
                startActivity(intent5);
                overridePendingTransition(R.anim.slidin, R.anim.slidout);
            }
        });

        logout = findViewById(R.id.button5);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent6 = new Intent(AdminPage.this,Homepage.class);
                intent6.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent6);
            }
        });
    }

    public void viewAll() {
        Cursor res = db.EgetData();
        if (res.getCount() == 0) {
            //show message
            showMessage("Error", "Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            buffer.append("ID : " + res.getString(0) + "\n");
            buffer.append("Name : " + res.getString(1) + "\n");
            buffer.append("Username : " + res.getString(2) + "\n");
            buffer.append("Email : " + res.getString(4) + "\n\n");
        }
        //show all data
        showMessage("All Employees", buffer.toString());
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
