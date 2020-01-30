package com.example.ssis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class StoreClerkDisbursementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_clerk_disbursment);

        Intent i=getIntent();
        CollectionPoint cP=(CollectionPoint) i.getSerializableExtra("cp");

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
                Toast.makeText(StoreClerkDisbursementActivity.this,"Requested by: "+d.getDeptName(),Toast.LENGTH_LONG ).show();
                startActivity(i);


            }
        });
    }
}
