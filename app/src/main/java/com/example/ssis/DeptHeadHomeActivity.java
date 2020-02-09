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

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_head_home);
        //@Shutong
        id= getIntent().getIntExtra("id",0);
        authenticateUser(id);

        viewRequisition=(Button) findViewById(R.id.viewReq);
        viewNotification=(Button)findViewById(R.id.viewNoti);
        delegate=(Button) findViewById(R.id.delegate);
        changeRepCP=(Button) findViewById(R.id.changeRepCP);
        Logout=(Button)findViewById(R.id.Logout1);
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

            }
        });
        viewNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DeptHeadHomeActivity.this,DeptHeadNotificationActivity.class);
                i.putExtra("id",id);
                startActivity(i);

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

    //@Shutong
    public void authenticateUser(int id){
        if(id==0){
            Intent i=new Intent(DeptHeadHomeActivity.this,MainActivity.class);
            startActivity(i);
            finish();
        }
    }
}
