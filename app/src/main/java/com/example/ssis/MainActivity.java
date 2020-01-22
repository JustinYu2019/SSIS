package com.example.ssis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
Button Login;
Button LDeptHead,LDeptRep,LDeptStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Login  page for Clerk, dept rep and dept head

        Login=findViewById(R.id.login);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // authentication required to identify who is that
            }
        });

        // temporary for redirect to different user roles for now
        LDeptHead=findViewById(R.id.loginDeptHead);
        LDeptHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this,DeptHeadHomeActivity.class);
                startActivity(i);
            }
        });
        LDeptRep=findViewById(R.id.LoginDeptRep);
        LDeptRep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this,DeptRepHomeActivity.class);
                startActivity(i);
            }
        });
        LDeptStore=findViewById(R.id.LoginStoreClerk);
        LDeptStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this,StoreClerkHomeActivity.class);
                startActivity(i);
            }
        });

    }
}
