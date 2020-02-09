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

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DeptHeadDelegateActivity extends AppCompatActivity implements AsyncToServer.IServerResponse{

    int id=0;
    Button btnLogout,btnStartDate,btnEndDate,btnApprove,btnReject;
    TextView textStartDate,textEndDate;
    EditText messageEditText;
    String selectedEmp;
    Spinner spinnerName;
    String date1,date2;
    int year,month,dayOfMonth;
    ArrayList<String> namelist=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_head_delegate);
        // initialize
        initUI();
    }
    public void initUI(){

        //@Shutong
        id= getIntent().getIntExtra("id",0);
        authenticateUser(id);

        btnLogout=(Button)findViewById(R.id.LogoutDelegate);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DeptHeadDelegateActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        btnStartDate=(Button) findViewById(R.id.startDateBtn);
        btnEndDate=(Button) findViewById(R.id.endDateBtn);

        btnApprove=(Button)findViewById(R.id.ApproveDele);
        btnReject=(Button)findViewById(R.id.RejectDele);

        textStartDate =(TextView) findViewById(R.id.textStartDate);
        textEndDate = (TextView)findViewById(R.id.textEndDate);
        spinnerName=(Spinner) findViewById(R.id.spinner1);

        //@Shutong
        btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String employeeName=selectedEmp;
                String startDate=textStartDate.getText().toString();
                String endDate=textEndDate.getText().toString();
                if(startDate==null||endDate==null||employeeName==null||startDate.isEmpty()||endDate.isEmpty()||employeeName.isEmpty()){
                    Toast.makeText(DeptHeadDelegateActivity.this,"There is a missing field",Toast.LENGTH_SHORT).show();
                }else{
                    messageEditText=(EditText)findViewById(R.id.delegateMessage);
                    String message=messageEditText.getText().toString();
                    createDelegation(employeeName,startDate,endDate,message);
                }
            }
        });
        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DeptHeadDelegateActivity.this,DeptHeadHomeActivity.class);
                i.putExtra("id",id);
                startActivity(i);
                finish();
            }
        });

        //@Shutong
        findEmployeeNamesById(id);
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

    //@Shutong
    @Override
    public void onServerResponse(JSONObject jsonObj){
        if (jsonObj == null) {
            Toast msg = Toast.makeText(DeptHeadDelegateActivity.this,"Server No response ", Toast.LENGTH_LONG);
            msg.show();
        }
        try {
            String context = (String) jsonObj.get("context");
            if (context.compareTo("findEmployees") == 0) {
                JSONArray array =jsonObj.getJSONArray("namelist");
                for(int i=0;i<array.length();i++)
                {
                    JSONObject object= array.getJSONObject(i);
                    namelist.add(object.getString("name"));
                }
                //spinner for rep


                ArrayAdapter<String> myAdapter=new ArrayAdapter<String>(DeptHeadDelegateActivity.this,
                        android.R.layout.simple_list_item_1,namelist);
                myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerName.setAdapter(myAdapter);
                spinnerName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
            }
            if (context.compareTo("createDelegation") == 0) {
                Toast.makeText(DeptHeadDelegateActivity.this,"Delegation record has been record successfully.",Toast.LENGTH_LONG).show();;
                Intent i=new Intent(DeptHeadDelegateActivity.this,DeptHeadHomeActivity.class);
                i.putExtra("id",id);
                startActivity(i);
                finish();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //@Shutong
    public void authenticateUser(int id){
        if(id==0){
            Intent i=new Intent(DeptHeadDelegateActivity.this,MainActivity.class);
            startActivity(i);
            finish();
        }
    }
    //@Shutong
    public void findEmployeeNamesById(int id){
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("id",id);
        }catch (Exception e) {
            e.printStackTrace();
        }
        Command cmd = new Command(DeptHeadDelegateActivity.this, "findEmployees","http://10.0.2.2:59591/Home/FindEmployees",jsonObj);
        new AsyncToServer().execute(cmd);
    }
    //@Shutong
    public void createDelegation(String employeeName,String startDate,String endDate,String message){
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("id",id);
            jsonObj.put("employeeName",employeeName);
            jsonObj.put("startDate",startDate);
            jsonObj.put("endDate",endDate);
            jsonObj.put("message",message);
        }catch (Exception e) {
            e.printStackTrace();
        }
        Command cmd = new Command(DeptHeadDelegateActivity.this, "createDelegation","http://10.0.2.2:59591/Home/CreateDelegation",jsonObj);
        new AsyncToServer().execute(cmd);
    }
}
