package com.example.ssis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StoreClerkHomeActivity extends AppCompatActivity implements AsyncToServer.IServerResponse, View.OnClickListener {
/*    CollectionPoint c1;CollectionPoint c2;CollectionPoint c3;
    CollectionPoint c4;CollectionPoint c5;CollectionPoint c6;*/
    CollectionPoint cp;
    int id=0;
    Button Logout;
    Intent i=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_clerk_home);
        initUI();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.contactStationery:
                findCollectionPointAtStationery();
                break;
            case R.id.disburseStationery:
                findCollectionPointAtStationeryD();
                break;
            case R.id.contactManagement:
                findCollectionPointAtManagement();
                break;
            case R.id.disburseManagement:
                findCollectionPointAtManagementD();
                break;
            case R.id.contactMedical:
                findCollectionPointAtMedical();
                break;
            case R.id.disburseMedical:
                findCollectionPointAtMedicalD();
                break;
            case R.id.contactEngineering:
                findCollectionPointAtEngineering();
                break;
            case R.id.disburseEngineering:
                findCollectionPointAtEngineeringD();
                break;
            case R.id.contactScience:
                findCollectionPointAtScience();
                break;
            case R.id.disburseScience:
                findCollectionPointAtScienceD();
                break;
            case R.id.contactUniversity:
                findCollectionPointAtUniversityHospital();
                break;
            case R.id.disburseUniversity:
                findCollectionPointAtUniversityHospitalD();
                break;
            case R.id.LogoutStoreHome:
                i =new Intent(this,MainActivity.class);
                startActivity(i);
                finish();
                break;
        }

    }

    public void initUI(){

        id=getIntent().getIntExtra("id",0);
        authenticateUser(id);

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
    public void onServerResponse(JSONObject jsonObj){
        if (jsonObj == null) {
            Toast msg = Toast.makeText(StoreClerkHomeActivity.this,"Server No response ", Toast.LENGTH_LONG);
            msg.show();
        }
        try {
            String context = (String) jsonObj.get("context");
            if (context.startsWith("c")) {
                String name=jsonObj.getString("location");
                JSONArray departments = jsonObj.getJSONArray("departments");
                ArrayList<Department> departmentList=new ArrayList<>();
                for (int i = 0; i < departments.length(); i++) {
                    JSONObject dept=departments.getJSONObject(i);
                    ArrayList<Item> itemList=new ArrayList<>();
                    try{
                        JSONArray items=dept.getJSONArray("items");
                        for(int j=0;j<items.length();i++){
                            JSONObject item=items.getJSONObject(i);
                            String description=item.getString("description");
                            int unit=item.getInt("unit");
                            itemList.add(new Item(description,unit));
                        }
                    }catch(JSONException e){
                        e.printStackTrace();
                    }

                    String deptName=dept.getString("deptName");
                    String deptRep=dept.getString("deptRep");
                    String disId=dept.getString("disId");
                    if(disId.equals("null")){
                        disId=null;
                    }
                    if(deptRep.equals("null")){
                        deptRep=null;
                    }
                    String contact=dept.getString("contact");
                    departmentList.add(new Department(deptName,deptRep,disId,contact,itemList));
                    cp=new CollectionPoint();
                    cp.setName(name);
                    cp.setDepartmentList(departmentList);
                }

                if(context.equals("c1")){
                    i=new Intent(this,StoreClerkContactActivity.class);
                    // put object here to pass to Contacts Ui
                    i.putExtra("cp",cp);
                    i.putExtra("id",id);
                    startActivity(i);
                }else if(context.equals("c2")){
                    i=new Intent(this, StoreClerkDisbursementActivity.class);
                    // put object here to pass to disbursment Ui
                    i.putExtra("cp",cp);
                    i.putExtra("id",id);
                    startActivity(i);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void authenticateUser(int id){
        if(id==0){
            i=new Intent(StoreClerkHomeActivity.this,MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    public void findCollectionPointAtStationery(){
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("id", id);
            jsonObj.put("location","Stationery Store");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Command cmd = new Command(StoreClerkHomeActivity.this, "c1", "http://10.0.2.2:59591/Home/FindCollectionPoint", jsonObj);
        new AsyncToServer().execute(cmd);
    }
    public void findCollectionPointAtStationeryD(){
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("id", id);
            jsonObj.put("location","Stationery Store");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Command cmd = new Command(StoreClerkHomeActivity.this, "c2", "http://10.0.2.2:59591/Home/FindCollectionPoint", jsonObj);
        new AsyncToServer().execute(cmd);
    }
    public void findCollectionPointAtManagement(){
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("id", id);
            jsonObj.put("location","Management School");

        } catch (Exception e) {
            e.printStackTrace();
        }
        Command cmd = new Command(StoreClerkHomeActivity.this, "c1", "http://10.0.2.2:59591/Home/FindCollectionPoint", jsonObj);
        new AsyncToServer().execute(cmd);
    }
    public void findCollectionPointAtManagementD(){
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("id", id);
            jsonObj.put("location","Management School");

        } catch (Exception e) {
            e.printStackTrace();
        }
        Command cmd = new Command(StoreClerkHomeActivity.this, "c2", "http://10.0.2.2:59591/Home/FindCollectionPoint", jsonObj);
        new AsyncToServer().execute(cmd);
    }

    public void findCollectionPointAtMedical(){
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("id", id);
            jsonObj.put("location","Medical School");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Command cmd = new Command(StoreClerkHomeActivity.this, "c1", "http://10.0.2.2:59591/Home/FindCollectionPoint", jsonObj);
        new AsyncToServer().execute(cmd);
    }
    public void findCollectionPointAtMedicalD(){
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("id", id);
            jsonObj.put("location","Medical School");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Command cmd = new Command(StoreClerkHomeActivity.this, "c2", "http://10.0.2.2:59591/Home/FindCollectionPoint", jsonObj);
        new AsyncToServer().execute(cmd);
    }

    public void findCollectionPointAtEngineering(){
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("id", id);
            jsonObj.put("location","Engineering School");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Command cmd = new Command(StoreClerkHomeActivity.this, "c1", "http://10.0.2.2:59591/Home/FindCollectionPoint", jsonObj);
        new AsyncToServer().execute(cmd);
    }

    public void findCollectionPointAtEngineeringD(){
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("id", id);
            jsonObj.put("location","Engineering School");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Command cmd = new Command(StoreClerkHomeActivity.this, "c2", "http://10.0.2.2:59591/Home/FindCollectionPoint", jsonObj);
        new AsyncToServer().execute(cmd);
    }

    public void findCollectionPointAtScience(){
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("id", id);
            jsonObj.put("location","Science School");

        } catch (Exception e) {
            e.printStackTrace();
        }
        Command cmd = new Command(StoreClerkHomeActivity.this, "c1", "http://10.0.2.2:59591/Home/FindCollectionPoint", jsonObj);
        new AsyncToServer().execute(cmd);
    }

    public void findCollectionPointAtScienceD(){
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("id", id);
            jsonObj.put("location","Science School");

        } catch (Exception e) {
            e.printStackTrace();
        }
        Command cmd = new Command(StoreClerkHomeActivity.this, "c2", "http://10.0.2.2:59591/Home/FindCollectionPoint", jsonObj);
        new AsyncToServer().execute(cmd);
    }

    public void findCollectionPointAtUniversityHospital(){
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("id", id);
            jsonObj.put("location","University Hospital");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Command cmd = new Command(StoreClerkHomeActivity.this, "c1", "http://10.0.2.2:59591/Home/FindCollectionPoint", jsonObj);
        new AsyncToServer().execute(cmd);
    }


    public void findCollectionPointAtUniversityHospitalD(){
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("id", id);
            jsonObj.put("location","University Hospital");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Command cmd = new Command(StoreClerkHomeActivity.this, "c2", "http://10.0.2.2:59591/Home/FindCollectionPoint", jsonObj);
        new AsyncToServer().execute(cmd);
    }

}
