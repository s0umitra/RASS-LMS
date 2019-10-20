package com.example.lms;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.MonthDay;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class LeaveDetails extends AppCompatActivity {
    Database db;

    TextView mTextfrom;
    TextView mTextto;
    TextView rea;

    EditText mReason;

    Spinner sp1, sp2;
    String spin;

    Button mSubmit;
    String datef="";
    String datei="";
    String empid;

    ImageView iv1;
    ImageView iv2;

    int setflag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.readdleave);

        empid = getIntent().getStringExtra("EID");
        db = new Database(this);

        mTextfrom = findViewById(R.id.from);
        mTextto = findViewById(R.id.to);

        sp1 = findViewById(R.id.spinner1);
//        addListenerOnSpinnerItemSelection();
//        sp1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        TextView rea = (TextView)sp1.getSelectedView();

        iv1 = findViewById(R.id.iv1);
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent from = new Intent(LeaveDetails.this, CalendarInfo.class);
                startActivityForResult(from, 1);
            }
        });

        iv2 = findViewById(R.id.iv2);
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent to = new Intent(LeaveDetails.this, CalendarInfo.class);
                startActivityForResult(to, 2);
            }
        });

        mSubmit = findViewById(R.id.Submit);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LeaveInfo li = new LeaveInfo();
                long diff = 0;
                int flag = 0;
                spin = sp1.getSelectedItem().toString();

                li.setEid(Integer.parseInt(empid));

                SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy");
                try {
                        Date di = format.parse(datei);
                        Date df = format.parse(datef);
                        diff = getDateDiff(df, di, TimeUnit.DAYS);
                        if (diff > 10) {
                            flag = 1;
                        }

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (flag == 1) {
                    StringBuffer buffer = new StringBuffer();
                    buffer.append("No TEN or more leaves allowed, have a nice day!");
                    showMessage("Limit Exceeded", buffer.toString());
                } else if (flag == 0) {

                    if(!mTextfrom.getText().toString().isEmpty()||!mTextto.getText().toString().isEmpty()){
                        li.setDatefrom(mTextfrom.getText().toString());
                        li.setDateto(mTextto.getText().toString());
                        li.setReason(spin);
                        li.setStatus(0);
                    }
                    else{
                        Toast.makeText(LeaveDetails.this,"Select Dates",Toast.LENGTH_LONG).show();
                        return;
                    }

                    boolean IsInserted = db.addLeave(li);
                    if (IsInserted == true) {
                        Toast.makeText(LeaveDetails.this, "Leave Request Successful", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(LeaveDetails.this, "Leave Request Failed", Toast.LENGTH_LONG).show();
                    }
                    Intent i = new Intent(LeaveDetails.this, EmpPage.class);
                    i.putExtra("EID", empid);
                    startActivity(i);
                }
            }
        });

    }

    public long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        long dd = timeUnit.MILLISECONDS.toDays(diffInMillies);
        return dd;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                datef = data.getStringExtra("DATE");
                mTextfrom.setText(datef);
            }
        }
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                datei = data.getStringExtra("DATE");
                mTextto.setText(datei);
            }
        }
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}

