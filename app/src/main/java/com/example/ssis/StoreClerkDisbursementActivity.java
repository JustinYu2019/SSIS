package com.example.ssis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StoreClerkDisbursementActivity extends AppCompatActivity implements AsyncToServer.IServerResponse {
    Button Logout;
    int id;
    String location;
    ArrayList<Department> deptList=new ArrayList<>();
    Department d;
    Intent intent;
    ListView deptListView;
    CollectionPoint cP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_clerk_disbursment);

        Logout=findViewById(R.id.LogoutDisbursement);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(StoreClerkDisbursementActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });



        Intent i=getIntent();
        id = i.getIntExtra("id",0);
        location=i.getStringExtra("location");
        authenticateUser(id);
        cP=(CollectionPoint) i.getSerializableExtra("cp");
        if(cP==null){
            setCP();
        }else{
            location=cP.getName();
            deptList=cP.getDepartmentList();
        }
        // List View and Adapter is working but for the different button clicks to pass intent seems fail.--> due to inappropriate initialisation
        // problem is solved.
        deptListView=(ListView) findViewById(R.id.deptListView);

        DepartmentListAdapter adapter=new DepartmentListAdapter(this,R.layout.adapter_departmentlist,deptList);

        // put adapter into listview
        deptListView.setAdapter(adapter);
        deptListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

                intent =new Intent(StoreClerkDisbursementActivity.this,StoreClerkDisbursementDetailActivity.class);
                d=deptList.get(pos);
                intent.putExtra("Department",d);
                intent.putExtra("location",location);
                intent.putExtra("id",StoreClerkDisbursementActivity.this.id);
                Toast.makeText(StoreClerkDisbursementActivity.this,"Requested by: "+d.getDeptName(),Toast.LENGTH_LONG ).show();
                startActivity(intent);


            }
        });
    }
    public void authenticateUser(int id){
        if(id==0){
            intent=new Intent(StoreClerkDisbursementActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void setCP(){
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("id", id);
            jsonObj.put("location",location);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Command cmd = new Command(StoreClerkDisbursementActivity.this, "setCP", "http://10.0.2.2:59591/Home/FindCollectionPoint", jsonObj);
        new AsyncToServer().execute(cmd);
    }
    @Override
    public void onServerResponse(JSONObject jsonObj){
        if (jsonObj == null) {
            Toast msg = Toast.makeText(StoreClerkDisbursementActivity.this,"Server No response ", Toast.LENGTH_LONG);
            msg.show();
        }
        try {
            String context = (String) jsonObj.get("context");
            if (context.compareTo("setCP")==0) {
                String name = jsonObj.getString("location");
                JSONArray departments = jsonObj.getJSONArray("departments");
                ArrayList<Department> departmentList = new ArrayList<>();
                for (int i = 0; i < departments.length(); i++) {
                    JSONObject dept = departments.getJSONObject(i);
                    ArrayList<Item> itemList = new ArrayList<>();
                    try {
                        if(dept.getJSONObject("items")!=null){
                            JSONArray items = dept.getJSONArray("items");

                            for (int j = 0; j < items.length(); j++) {
                                JSONObject item = items.getJSONObject(j);
                                String description = item.getString("description");
                                int unit = item.getInt("unit");
                                itemList.add(new Item(description, unit));
                            }
                        }else{
                            itemList=new ArrayList<>();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    String deptName = dept.getString("deptName");
                    String deptRep = dept.getString("deptRep");
                    String disId = dept.getString("disId");
                    if (disId.equals("null")) {
                        disId = null;
                    }
                    if (deptRep.equals("null")) {
                        deptRep = null;
                    }
                    String contact = dept.getString("contact");
                    departmentList.add(new Department(deptName, deptRep, disId, contact, itemList));
                    cP = new CollectionPoint();
                    cP.setName(name);
                    cP.setDepartmentList(departmentList);
                    location=cP.getName();
                    deptList=cP.getDepartmentList();
                }
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
    }
}
