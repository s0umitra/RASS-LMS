package com.example.lms;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class EmpPage extends AppCompatActivity {
    Database db;
    private Button addleave;
    private Button leavede;
    private Button logout;
    String stat;

    TextView curr;
    String empid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reempage);
        empid = getIntent().getStringExtra("EID");
        db = new Database(this);

        curr = findViewById(R.id.textView10);

        Cursor emp = db.EgetDataFromId(empid);

        emp.moveToFirst();

        curr.setText("Current Employee : "+emp.getString(1));

        addleave = findViewById(R.id.empadl);
        addleave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent8 = new Intent(EmpPage.this,LeaveDetails.class);
                intent8.putExtra("EID", empid);
                startActivity(intent8);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

        leavede = findViewById(R.id.empld);
        leavede.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewAll();
            }
        });

        logout = findViewById(R.id.emplog);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent10 = new Intent(EmpPage.this,Homepage.class);
                intent10.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent10);
            }
        });
    }

    public void ViewAll() {
        Cursor res = db.LgetData(empid);
        if (res.getCount() == 0) {
            ShowMessage("Error", "Nothing Found");
            return;
        }
        res.moveToFirst();
        StringBuffer buffer = new StringBuffer();
        while (!res.isAfterLast()){
            if(res.getInt(6)==0){
                stat = "Pending";
            }else if(res.getInt(6)==1){
                stat = "Approved";
            }else{
                stat = "Rejected";
            }
            buffer.append("Date : " + res.getString(2) + " to " + res.getString(3) + "\n");
            buffer.append("Reason : " + res.getString(5) + "\n");
            buffer.append("Status : " + stat + "\n\n");
            res.moveToNext();
        }
        ShowMessage("My Leaves", buffer.toString());
    }

    public void ShowMessage(String Title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(Title);
        builder.setMessage(Message);
        builder.show();
    }
}
