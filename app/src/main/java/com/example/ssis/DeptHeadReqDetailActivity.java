package com.example.ssis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class DeptHeadReqDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_head_req_detail);
        //Requisition rdetail=getIntent().getSerializableExtra("ReqDetail");
        Intent i=getIntent();
        Requisition rdetail=(Requisition) i.getSerializableExtra("ReqDetail");

        // i think will be another list view but tgt with approve/reject/logout feature
        // to be continued.

    }
}
