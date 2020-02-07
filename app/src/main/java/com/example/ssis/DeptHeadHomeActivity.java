package com.example.ssis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DeptHeadHomeActivity extends AppCompatActivity {
    int id=0;
    Button viewRequisition,viewNotification,delegate,Logout,changeRepCP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        id= getIntent().getIntExtra("id",0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_head_home);

        viewRequisition=findViewById(R.id.viewReq);
        viewNotification=findViewById(R.id.viewNoti);
        delegate=findViewById(R.id.delegate);
        changeRepCP=findViewById(R.id.changeRepCP);
        Logout=findViewById(R.id.Logout1);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DeptHeadHomeActivity.this,MainActivity.class);
                i.putExtra("id",id);
                startActivity(i);
                finish();
            }
        });
        changeRepCP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DeptHeadHomeActivity.this,DeptHeadRepCPActivity.class);
                i.putExtra("id",id);
                startActivity(i);
                finish();
            }
        });
        viewRequisition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DeptHeadHomeActivity.this,DeptHeadReqActivity.class);
                i.putExtra("id",id);
                startActivity(i);
                finish();
            }
        });
        viewNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DeptHeadHomeActivity.this,DeptHeadNotificationActivity.class);
                i.putExtra("id",id);
                startActivity(i);
                finish();
            }
        });
        delegate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DeptHeadHomeActivity.this,DeptHeadDelegateActivity.class);
                i.putExtra("id",id);
                startActivity(i);
                finish();
            }
        });
    }
}
