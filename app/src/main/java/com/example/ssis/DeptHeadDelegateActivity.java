package com.example.ssis;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DeptHeadDelegateActivity extends AppCompatActivity {
Button btnLogout,btnStartDate,btnEndDate,btnApprove,btnReject;
TextView textStartDate,textEndDate;
EditText messageEditText;
    String selectedEmp;
Spinner spinnerName;
String date1,date2;
int year,month,dayOfMonth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_head_delegate);
        // initialize
        btnLogout=findViewById(R.id.LogoutDelegate);
        btnStartDate=findViewById(R.id.startDateBtn);
        btnEndDate=findViewById(R.id.endDateBtn);
        textStartDate = findViewById(R.id.textStartDate);
        textEndDate = findViewById(R.id.textEndDate);
        spinnerName=findViewById(R.id.spinner1);
        btnLogout=findViewById(R.id.LogoutDelegate);
        btnApprove=findViewById(R.id.ApproveDele);
        btnReject=findViewById(R.id.RejectDele);

        btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   // after approve, need send notifications of delegate details to the user
            }
        });
        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DeptHeadDelegateActivity.this,DeptHeadHomeActivity.class);
                startActivity(i);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DeptHeadDelegateActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
        // Start of Spinner
        final Spinner spinner=findViewById(R.id.spinner1);
        // neeed the actual name from database.
        final ArrayList<String> namelist=new ArrayList<>();
        namelist.add("Justin");
        namelist.add("Jenny");
        namelist.add("Branny");
        namelist.add("Canny");
        namelist.add("Danny");

        ArrayAdapter<String> myAdapter=new ArrayAdapter<String>(DeptHeadDelegateActivity.this,
                android.R.layout.simple_list_item_1,namelist);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // selected name are store in name variable
                selectedEmp=parent.getItemAtPosition(position).toString();

//                TextView v1=findViewById(R.id.textView);
//                v1.setText(name);
                Toast.makeText(parent.getContext(),"selected employee: "+selectedEmp,Toast.LENGTH_LONG).show();;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // End of Spinner

        //Start of Calendar
        // this block of code is to iniitialize and set the date to current year month and day of calender
        Calendar calendar = Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        dayOfMonth=calendar.get(Calendar.DAY_OF_MONTH);

        // Calender listener
        View.OnClickListener showDatePicker=new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final View vv = v;

                DatePickerDialog datePickerDialog= new DatePickerDialog(DeptHeadDelegateActivity.this, new DatePickerDialog.OnDateSetListener() {

                    //                                        public Dialog onCreateDialog(Bundle savedInstanceState){
//                        Calendar c=Calendar.getInstance();
//                        int year=c.get(Calendar.YEAR);
//                        int month=c.get(Calendar.MONTH);
//                        int dayOfMonth=c.get(Calendar.DAY_OF_MONTH);
//
//                        return new DatePickerDialog(MainActivity.this,(DatePickerDialog.OnDateSetListener) getApplicationContext(),year,month,dayOfMonth);
//                    }
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        if (vv.getId() == R.id.startDateBtn) {
                            Calendar c=Calendar.getInstance();
                            Date currentTime=new Date();
                            c.setTimeInMillis(currentTime.getTime());
                            c.set(Calendar.YEAR,year);
                            //c.add(Calendar.YEAR,Calendar.YEAR+100);

                            c.set(Calendar.MONTH,month);
                            c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                            date1= DateFormat.getDateInstance().format(c.getTime());
                            Toast.makeText(DeptHeadDelegateActivity.this,"Starting date: "+date1,Toast.LENGTH_SHORT).show();
                            textStartDate.setText(date1);
                        } else {
                            Calendar c=Calendar.getInstance();
                           // c.add(Calendar.YEAR,Calendar.YEAR+100);
                            c.set(Calendar.YEAR,year);
                            c.set(Calendar.MONTH,month);
                            c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                            date2=DateFormat.getDateInstance().format(c.getTime());
                            Toast.makeText(DeptHeadDelegateActivity.this,"Ending date: "+date2,Toast.LENGTH_SHORT).show();
                            textEndDate.setText(date2);
                        }
                    }
                },year,month,dayOfMonth);
                datePickerDialog.show();
            }
        };
        btnStartDate.setOnClickListener(showDatePicker);
        btnEndDate.setOnClickListener(showDatePicker);
        // End of Calendar
    }
}
