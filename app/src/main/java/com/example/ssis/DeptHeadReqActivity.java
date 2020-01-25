package com.example.ssis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class DeptHeadReqActivity extends AppCompatActivity {
Button Logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_head_req);
        Logout=findViewById(R.id.LogoutReq);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DeptHeadReqActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
        // list view of stationery requisition.
         ListView rListView=findViewById(R.id.listViewReq);
        Item i1=new Item();
        i1.setDescription("pen");
        i1.setUnit(20);
        Item i2=new Item();
        i2.setDescription("pencil");
        i2.setUnit(100);
        ArrayList<Item>itemList=new ArrayList<>();
        itemList.add(i1);
        itemList.add(i2);
        Requisition r1=new Requisition("James","2017-2-10",itemList);

        Requisition r2=new Requisition("Justin","2017-2-10",itemList);

        final ArrayList<Requisition>rList=new ArrayList<>();
        rList.add(r1);
        rList.add(r2);
         RequisitionListAdapter adapter=new RequisitionListAdapter(this,R.layout.adapter_requisition,rList);

        rListView.setAdapter(adapter);
        rListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

                    Intent i=new Intent(DeptHeadReqActivity.this,DeptHeadReqDetailActivity.class);
                    Requisition r=rList.get(pos);
                    i.putExtra("ReqDetail",r);
                Toast.makeText(DeptHeadReqActivity.this,"Requested by: "+r.getName(),Toast.LENGTH_LONG ).show();
                startActivity(i);


            }
        });
    }
}
