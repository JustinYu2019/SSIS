package com.example.ssis;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DeptHeadNotificationActivity extends AppCompatActivity implements AsyncToServer.IServerResponse {
    Button Logout;
    int id;
    ArrayList<Requisition> rList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_head_notification);

        //@Shutong
        id= getIntent().getIntExtra("id",0);
        authenticateUser(id);


        Logout=findViewById(R.id.LogoutNoti);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DeptHeadNotificationActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        // table layout view --> may not be good--> use listview
        // list view of stationery requisition.
        renderListView();
    }
    //@Shutong
    public void authenticateUser(int id){
        if(id==0){
            Intent i=new Intent(DeptHeadNotificationActivity.this,MainActivity.class);
            startActivity(i);
            finish();
        }
    }
    //@Shutong
    public void renderListView() {

        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("id", id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Command cmd = new Command(DeptHeadNotificationActivity.this, "findNotifications", "http://10.0.2.2:59591/Home/FindNotifications", jsonObj);
        new AsyncToServer().execute(cmd);
    }
    //@Shutong
    @Override
    public void onServerResponse(JSONObject jsonObj){
        if (jsonObj == null) {
            Toast msg = Toast.makeText(DeptHeadNotificationActivity.this,"Server No response ", Toast.LENGTH_LONG);
            msg.show();
        }
        try {
            String context = (String) jsonObj.get("context");
            if (context.compareTo("findNotifications") == 0) {
                JSONArray array = jsonObj.getJSONArray("notifications");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                     rList.add(new Requisition(object.getString("message"),object.getString("date")));
                }
                ListView rListView=(ListView) findViewById(R.id.listViewNoti);
                NotificationListAdapter adapter=new NotificationListAdapter(this,R.layout.adapter_notification,rList);
                rListView.setAdapter(adapter);
            }
        }catch (Exception e) {

            e.printStackTrace();
        }
    }
}
