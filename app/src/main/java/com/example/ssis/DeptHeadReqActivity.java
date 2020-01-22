package com.example.ssis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class DeptHeadReqActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_head_req);

        // list view of stationery requisition.
        final ListView rListView=findViewById(R.id.listViewReq);
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
        final RequisitionListAdapter adapter=new RequisitionListAdapter(this,R.layout.adapter_requisition,rList);

        rListView.setAdapter(adapter);
        rListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

                adapter.chosenId = pos;
                Intent i=new Intent(DeptHeadReqActivity.this,DeptHeadReqDetailActivity.class);
                Requisition rdetail=rList.get(pos);
                i.putExtra("ReqDetail",rdetail);
                startActivity(i);

            }
        });
    }
}
