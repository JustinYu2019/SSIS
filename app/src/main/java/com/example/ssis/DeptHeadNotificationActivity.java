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

public class DeptHeadNotificationActivity extends AppCompatActivity {
Button Logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_head_notification);
        Logout=findViewById(R.id.LogoutNoti);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DeptHeadNotificationActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
        // table layout view --> may not be good--> use listview
        // list view of stationery requisition.
        ListView rListView=findViewById(R.id.listViewNoti);


        // data received from web should already been sorted according to date..
        // should use forward when arraylist of requision is sent
        // use getters and setters to set data
        String s=" has sent a notfication";
        Requisition r1=new Requisition("James"+s,"2017-2-10");

        Requisition r2=new Requisition("Tom"+s,"2017-2-10");

        Requisition r3=new Requisition("Jenny"+s,"2017-2-9");
        final ArrayList<Requisition>rList=new ArrayList<>();
        rList.add(r1);
        rList.add(r2);
        rList.add(r3);
        RequisitionListAdapter adapter=new RequisitionListAdapter(this,R.layout.adapter_notification,rList);

        rListView.setAdapter(adapter);


    }
}
