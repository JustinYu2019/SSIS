package com.example.ssis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class StoreClerkContactActivity extends AppCompatActivity {
ArrayList<Department> deptList;
Button Logout;
String location;
int id;
Intent intent;
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
        id=i.getIntExtra("id",0);
        authenticateUser(id);
        CollectionPoint cP=(CollectionPoint) i.getSerializableExtra("cp");
        location=cP.getName();
        TextView tLocation=(TextView)findViewById(R.id.location);
        tLocation.setText(location);
        deptList=cP.getDepartmentList();


        ListView deptListView=findViewById(R.id.contactListView);

        ContactListAdapter adapter=new ContactListAdapter(this,R.layout.adapter_contactlist,deptList);

        // put adapter into listview
        deptListView.setAdapter(adapter);

        deptListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                Department d=deptList.get(pos);
                String contact=d.getContactNumber();
                String tel="tel:("+contact.substring(0,3)+")"+contact.substring(3);//tel:(+65)12345678"
                Uri uri = Uri.parse(tel);
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                if (intent.resolveActivity(getPackageManager()) != null) startActivity(intent);
            }
        });
    }

    public void authenticateUser(int id){
        if(id==0){
            intent=new Intent(StoreClerkContactActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
