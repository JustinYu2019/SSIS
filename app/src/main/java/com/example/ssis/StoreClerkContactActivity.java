package com.example.ssis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class StoreClerkContactActivity extends AppCompatActivity {
ArrayList<Department> deptList;
Button Logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_clerk_contact);
        Logout=findViewById(R.id.LogoutCP);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(StoreClerkContactActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

        Intent i=getIntent();
        CollectionPoint cP=(CollectionPoint) i.getSerializableExtra("cp");
        deptList=cP.getDepartmentList();


        ListView deptListView=findViewById(R.id.contactListView);

        ContactListAdapter adapter=new ContactListAdapter(this,R.layout.adapter_contactlist,deptList);

        // put adapter into listview
        deptListView.setAdapter(adapter);
    }
}
