package com.example.lms;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AdminLeaveReq extends AppCompatActivity {

    Database db;

    Button mApprove;
    Button mReject;
    Button mInfo;

    EditText mid;

    String stat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.readlereq);

        db = new Database(this);
        mApprove = findViewById(R.id.appr);
        mApprove.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                        db.setStatusA("1",mid.getText().toString());
                                            Toast.makeText(AdminLeaveReq.this,"Leave Approved",Toast.LENGTH_LONG).show();

                                        }
                                    });
        mReject = findViewById(R.id.rejt);
        mReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.setStatusA("2",mid.getText().toString());
                Toast.makeText(AdminLeaveReq.this,"Leave Rejected",Toast.LENGTH_LONG).show();
            }
        });

        mInfo = findViewById(R.id.minfo);
        mid = findViewById(R.id.id2);

        ViewAll();

    }

    public void ViewAll() {
        mInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = db.LAllgetData();
                if (res.getCount() == 0) {
                    ShowMessage("Error", "Nothing Found");
                    return;
                }
                res.moveToFirst();
                StringBuffer buffer = new StringBuffer();
                while (!res.isAfterLast()) {
                    if (res.getInt(6) == 0) {
                        stat = "Pending";
                    } else if (res.getInt(6) == 1) {
                        stat = "Approved";
                    } else {
                        stat = "Rejected";
                    }
                    Cursor cur = db.EgetDataFromId(res.getString(1));
                    cur.moveToFirst();
                    buffer.append("ID :" + res.getString(0) + "\n")
                        .append("Name :" + cur.getString(1) + "\n")
                        .append("Date : " + res.getString(2) + " to " + res.getString(3) + "\n")
                        .append("Reason : " + res.getString(5) + "\n")
                        .append("Status : " + stat + "\n\n");
                    res.moveToNext();
                }

                //show all
                ShowMessage("All Leaves", buffer.toString());
            }
        });
    }

    public void ShowMessage(String Title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(Title);
        builder.setMessage(Message);
        builder.show();
    }
}

