package com.example.ssis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class StoreClerkDisbursementActivity extends AppCompatActivity {
    Button Logout;
    int id;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_clerk_disbursment);

        Logout=findViewById(R.id.LogoutDisbursement);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(StoreClerkDisbursementActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });



        Intent i=getIntent();
        id=i.getIntExtra("id",0);
        authenticateUser(id);
        CollectionPoint cP=(CollectionPoint) i.getSerializableExtra("cp");

        final String location=cP.getName();

        final ArrayList<Department> deptList=cP.getDepartmentList();

        // List View and Adapter is working but for the different button clicks to pass intent seems fail.--> due to inappropriate initialisation
        // problem is solved.
        ListView deptListView=findViewById(R.id.deptListView);

        DepartmentListAdapter adapter=new DepartmentListAdapter(this,R.layout.adapter_departmentlist,deptList);

        // put adapter into listview
        deptListView.setAdapter(adapter);
        deptListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

                Intent i=new Intent(StoreClerkDisbursementActivity.this,StoreClerkDisbursementDetailActivity.class);
                Department d=deptList.get(pos);
                i.putExtra("Department",d);
                i.putExtra("location",location);
                i.putExtra("id",id);
                Toast.makeText(StoreClerkDisbursementActivity.this,"Requested by: "+d.getDeptName(),Toast.LENGTH_LONG ).show();
                startActivity(i);


            }
        });
    }
    public void authenticateUser(int id){
        if(id==0){
            intent=new Intent(StoreClerkDisbursementActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
