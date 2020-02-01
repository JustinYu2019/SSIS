package com.example.ssis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class StoreClerkHomeActivity extends AppCompatActivity implements View.OnClickListener {
    CollectionPoint c1;CollectionPoint c2;CollectionPoint c3;
    CollectionPoint c4;CollectionPoint c5;CollectionPoint c6;
    Button Logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_clerk_home);
        Button stationeryContact=findViewById(R.id.contactStationery);
        Button stationeryDisburse=findViewById(R.id.disburseStationery);
        Button managementContact=findViewById(R.id.contactManagement);
        Button managementDisburse=findViewById(R.id.disburseManagement);
        Button medicalContact=findViewById(R.id.contactMedical);
        Button medicalDisburse=findViewById(R.id.disburseMedical);
        Button engineeringContact=findViewById(R.id.contactEngineering);
        Button engineeringDisburse=findViewById(R.id.disburseEngineering);
        Button scienceContact=findViewById(R.id.contactScience);
        Button scienceDisburse=findViewById(R.id.disburseScience);
        Button universityContact=findViewById(R.id.contactUniversity);
        Button universityDisburse=findViewById(R.id.disburseUniversity);

        Logout=findViewById(R.id.LogoutStoreHome);

        // get Items, Collection Point and departments
        // temporary use this data
        //ArrayList<Item> itemList=new ArrayList<Item>(Arrays.asList(new Item("pen",10)));
        ArrayList<Item> itemList=new ArrayList<Item>();
        itemList.add(new Item("ruler",10));
        itemList.add(new Item("pen",20));
        itemList.add(new Item("eraser",30));

        ArrayList<Item> itemList2=new ArrayList<Item>();
        itemList2.add(new Item("ruler",10));
        itemList2.add(new Item("pen",20));
        itemList2.add(new Item("Ink",50));
        itemList2.add(new Item("Highlighter",10));

        ArrayList<Department>stationeryDeptList=new ArrayList<>();
        Department d1=new Department("Science Dept","Stationery Store","Jenny","S123","874321",itemList);
        Department d2=new Department("Math Dept","Stationery Store","Branny","M123","66991",itemList2);
        stationeryDeptList.add(d1);stationeryDeptList.add(d2);
        c1=new CollectionPoint();
        c1.setName("Stationery Store");
        c1.setDepartmentList(stationeryDeptList);


        ArrayList<Department>managementDeptList=new ArrayList<>();
        Department d3=new Department("English Dept","Management School","Jenny","S123","874321",itemList);
        Department d4=new Department("Pyschology Dept","Management School","Branny","M123","66991",itemList2);
        managementDeptList.add(d3);managementDeptList.add(d4);
        c2=new CollectionPoint();
        c2.setName("Management School");
        c2.setDepartmentList(managementDeptList);

        ArrayList<Department>medicalDeptList=new ArrayList<>();
        Department d5=new Department("Arts Dept","Medical School","Jenny","S123","874321",itemList);
        Department d6=new Department("Architect Dept","Medical School","Branny","M123","66991",itemList2);
        medicalDeptList.add(d5);medicalDeptList.add(d6);
        c3=new CollectionPoint();
        c3.setName("Medical School");
        c3.setDepartmentList(medicalDeptList);

        ArrayList<Department>engineeringDeptList=new ArrayList<>();
        Department d7=new Department("Arts Dept","Engineering School","Jenny","S123","874321",itemList);
        Department d8=new Department("Architect Dept","Engineering School","Branny","M123","66991",itemList2);
        engineeringDeptList.add(d7);engineeringDeptList.add(d8);
        c4=new CollectionPoint();
        c4.setName("Engineering School");
        c4.setDepartmentList(engineeringDeptList);

        ArrayList<Department>scienceDeptList=new ArrayList<>();
        Department d9=new Department("Arts Dept","Science School","Jenny","S123","874321",itemList);
        Department d10=new Department("Architect Dept","Science School","Branny","M123","66991",itemList2);
        scienceDeptList.add(d9);scienceDeptList.add(d10);
        c5=new CollectionPoint();
        c5.setName("Science School");
        c5.setDepartmentList(engineeringDeptList);

        ArrayList<Department>universityDeptList=new ArrayList<>();
        Department d11=new Department("Arts Dept","University Hospital","Jenny","S123","874321",itemList);
        Department d12=new Department("Architect Dept","University Hospital","Branny","M123","66991",itemList2);
        universityDeptList.add(d12);universityDeptList.add(d11);
        c6=new CollectionPoint();
        c6.setName("University Hospital");
        c6.setDepartmentList(universityDeptList);

        // Initialize
        stationeryContact.setOnClickListener(this);
        stationeryDisburse.setOnClickListener(this);
        managementContact.setOnClickListener(this);
        managementDisburse.setOnClickListener(this);
        medicalContact.setOnClickListener(this);
        medicalDisburse.setOnClickListener(this);
        engineeringContact.setOnClickListener(this);
        engineeringDisburse.setOnClickListener(this);
        scienceContact.setOnClickListener(this);
        scienceDisburse.setOnClickListener(this);
        universityContact.setOnClickListener(this);
        universityDisburse.setOnClickListener(this);
        Logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i=null;
        switch (v.getId()){
            case R.id.contactStationery:
                i=new Intent(this,StoreClerkContactActivity.class);
                // put object here to pass to Contacts Ui
                i.putExtra("cp",c1);
                break;
            case R.id.disburseStationery:
                i=new Intent(this, StoreClerkDisbursementActivity.class);
                // put object here to pass to disbursment Ui
                i.putExtra("cp",c1);
                break;
            case R.id.contactManagement:
                i=new Intent(this,StoreClerkContactActivity.class);
                // put object here to pass to Contacts Ui
                i.putExtra("cp",c2);
                break;
            case R.id.disburseManagement:
                i=new Intent(this, StoreClerkDisbursementActivity.class);
                // put object here to pass to disbursment Ui
                i.putExtra("cp",c2);
                break;
            case R.id.contactMedical:
                i=new Intent(this,StoreClerkContactActivity.class);
                // put object here to pass to Contacts Ui
                i.putExtra("cp",c3);
                break;
            case R.id.disburseMedical:
                i=new Intent(this, StoreClerkDisbursementActivity.class);
                // put object here to pass to disbursment Ui
                i.putExtra("cp",c3);
                break;
            case R.id.contactEngineering:
                i=new Intent(this,StoreClerkContactActivity.class);
                i.putExtra("cp",c4);
                break;
            case R.id.disburseEngineering:
                i=new Intent(this, StoreClerkDisbursementActivity.class);
                // put object here to pass to disbursment Ui
                i.putExtra("cp",c4);
                break;
            case R.id.contactScience:
                i=new Intent(this,StoreClerkContactActivity.class);
                i.putExtra("cp",c5);
                break;
            case R.id.disburseScience:
                i=new Intent(this, StoreClerkDisbursementActivity.class);
                // put object here to pass to disbursment Ui
                i.putExtra("cp",c5);
                break;
            case R.id.contactUniversity:
                i=new Intent(this,StoreClerkContactActivity.class);
                i.putExtra("cp",c6);
                break;
            case R.id.disburseUniversity:
                i=new Intent(this, StoreClerkDisbursementActivity.class);
                // put object here to pass to disbursment Ui
                i.putExtra("cp",c6);
                break;
            case R.id.LogoutStoreHome:
                i =new Intent(this,MainActivity.class);
                break;
        }
        if(i.resolveActivity(getPackageManager())!=null){
            startActivity(i);
        }

    }
}
