package com.example.ssis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DeptHeadHomeActivity extends AppCompatActivity {
Button viewRequisition,viewNotification,delegate,Logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_head_home);

        viewRequisition=findViewById(R.id.viewReq);
        viewNotification=findViewById(R.id.viewNoti);
        delegate=findViewById(R.id.delegate);
        Logout=findViewById(R.id.Logout1);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DeptHeadHomeActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
        viewRequisition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DeptHeadHomeActivity.this,DeptHeadReqActivity.class);
                startActivity(i);
            }
        });
        viewNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DeptHeadHomeActivity.this,DeptHeadNotificationActivity.class);
                startActivity(i);
            }
        });
        delegate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DeptHeadHomeActivity.this,DeptHeadDelegateActivity.class);
                startActivity(i);
            }
        });
    }
}
