package com.example.ssis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DeptHeadReqActivity extends AppCompatActivity implements AsyncToServer.IServerResponse {
    Button Logout;
    int userId=0;
    ArrayList<Requisition> rList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_head_req);
        Logout=(Button)findViewById(R.id.LogoutReq);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DeptHeadReqActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        //@Shutong
        userId= getIntent().getIntExtra("id",0);
        authenticateUser(userId);
        renderListView();
        //end of @Shutong
    }
    //@Shutong
    public void authenticateUser(int id){
        if(id==0){
            Intent i=new Intent(DeptHeadReqActivity.this,MainActivity.class);
            startActivity(i);
            finish();
        }
    }
    //@Shutong
    public void renderListView() {

        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("id",userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Command cmd = new Command(DeptHeadReqActivity.this, "findRequisitions", "http://10.0.2.2:59591/Home/FindRequisitions", jsonObj);
        new AsyncToServer().execute(cmd);
    }
    //@Shutong
    @Override
    public void onServerResponse(JSONObject jsonObj){
        if (jsonObj == null) {
            Toast msg = Toast.makeText(DeptHeadReqActivity.this,"Server No response ", Toast.LENGTH_LONG);
            msg.show();
        }
        try {
            String context = (String) jsonObj.get("context");
            if (context.compareTo("findRequisitions") == 0) {
                JSONArray array = jsonObj.getJSONArray("requisitions");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    int reqId=object.getInt("requisitionId");
                    String name=object.getString("name");
                    String date=object.getString("date");
                    String remark=object.getString("remark");
                    ArrayList<Item> itemsList=new ArrayList<>();
                    JSONArray items=object.getJSONArray("items");
                    for(int j=0;j<items.length();j++){
                        JSONObject object1=items.getJSONObject(j);
                        Item item=new Item();
                        item.setDescription(object1.getString("description"));
                        item.setUnit(object1.getInt("unit"));
                        itemsList.add(item);
                    }
                    Requisition req=new Requisition(name,date,itemsList);
                    req.setReqId(reqId);
                    req.setRemarks(remark);
                    rList.add(req);
                }
                // list view of stationery requisition.
                ListView rListView=(ListView) findViewById(R.id.listViewReq);
                RequisitionListAdapter adapter=new RequisitionListAdapter(this,R.layout.adapter_requisition,rList);

                // put adapter into listview
                rListView.setAdapter(adapter);
                rListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                        Intent i=new Intent(DeptHeadReqActivity.this,DeptHeadReqDetailActivity.class);
                        Requisition r=rList.get(pos);
                        i.putExtra("ReqDetail",r);
                        i.putExtra("id",userId);
                        Toast.makeText(DeptHeadReqActivity.this,"Requested by: "+r.getName(),Toast.LENGTH_LONG ).show();
                        startActivity(i);
                        finish();
                    }
                });
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
