package com.example.ssis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DeptHeadReqDetailActivity extends AppCompatActivity implements AsyncToServer.IServerResponse{
    LinearLayout mChartLayout;
    TableLayout mTableLayout;
    int len;
    String name;
    String raiseDate;
    String remarks;
    ArrayList<Item> itemList;
    TextView empName;
    TextView dateRequest;
    Button approve,reject,Logout;
    int id=0;
    int requisitionId=0;
    EditText remarksBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_head_req_detail);
        initUI();
    }
    public void displayCharTable(){
        mTableLayout.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT)
        );

        for (Item item: itemList){
            TableRow row=new TableRow(DeptHeadReqDetailActivity.this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT
                    ,TableRow.LayoutParams.WRAP_CONTENT));

            String description=item.getDescription();
            int quantity=item.getUnit();
            TextView valueTV= new TextView(DeptHeadReqDetailActivity.this);
            valueTV.setText("  "+description);
            valueTV.setTextColor(Color.BLACK);
            valueTV.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT
            ));
            row.addView(valueTV);

            TextView valueTV2= new TextView(DeptHeadReqDetailActivity.this);
            valueTV2.setTextColor(Color.BLACK);
            valueTV2.setText("                                                "+quantity); // adjust here if quantity do not align
            valueTV2.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT
            ));
            row.addView(valueTV2);

            mTableLayout.addView(row);
        }
        mChartLayout.addView(mTableLayout);
    }

    public void initUI(){
        Logout=findViewById(R.id.LogoutReqDetail);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DeptHeadReqDetailActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

        mChartLayout=findViewById(R.id.chart_layout);
        mTableLayout=new TableLayout(DeptHeadReqDetailActivity.this);

        remarksBox=(EditText)findViewById(R.id.reqRemarks);


        Intent i=getIntent();
        Requisition rdetail=(Requisition) i.getSerializableExtra("ReqDetail");
        id=i.getIntExtra("id",0);
        authenticateUser(id);

        itemList=rdetail.getItemList();
        requisitionId=rdetail.getReqId();
        name=rdetail.getName();
        raiseDate=rdetail.getRaiseDate();
        remarks=rdetail.getRemarks();
        if(remarks!=null && !remarks.isEmpty()){
            remarksBox.setText(remarks);
        }
        empName=(TextView) findViewById(R.id.employeeName);
        dateRequest=(TextView) findViewById(R.id.dateRequest);
        empName.setText("Employee Name:             "+name);
        dateRequest.setText("Date of Request:             "+raiseDate);
        displayCharTable();
        approve=(Button)findViewById(R.id.Approve);
        reject=(Button)findViewById(R.id.Reject);
        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approveRequisition(requisitionId,remarksBox.getText().toString());
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rejectRequisition(requisitionId,remarksBox.getText().toString());
            }
        });

        // or once reject or approve is clicked, next time when click back
        // set both button to false --> reject.setClickable(false);
        // create a text beneath the approve reject button and set the text to its status



    }
    public void authenticateUser(int id){
        if(id==0){
            Intent i=new Intent(DeptHeadReqDetailActivity.this,MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    public void approveRequisition(int requisitionId,String remarks){
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("requisitionId", requisitionId);
            jsonObj.put("remark",remarks);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Command cmd = new Command(DeptHeadReqDetailActivity.this, "approveRequisition", "http://10.0.2.2:59591/Home/ApproveRequisition", jsonObj);
        new AsyncToServer().execute(cmd);
    }


    public void rejectRequisition(int requisitionID,String remarks){
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("requisitionId", requisitionID);
            jsonObj.put("remark",remarks);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Command cmd = new Command(DeptHeadReqDetailActivity.this, "rejectRequisition", "http://10.0.2.2:59591/Home/RejectRequisition", jsonObj);
        new AsyncToServer().execute(cmd);
    }

    @Override
    public void onServerResponse(JSONObject jsonObj){
        if (jsonObj == null) {
            Toast msg = Toast.makeText(DeptHeadReqDetailActivity.this,"Server No response ", Toast.LENGTH_LONG);
            msg.show();
        }
        try {
            String context = (String) jsonObj.get("context");
            if (context.compareTo("approveRequisition") == 0) {
                Toast.makeText(DeptHeadReqDetailActivity.this,"You have approved the request",Toast.LENGTH_LONG).show();
                Intent i=new Intent(DeptHeadReqDetailActivity.this,DeptHeadReqActivity.class);
                i.putExtra("id",id);
                startActivity(i);
            }
            if (context.compareTo("rejectRequisition") == 0) {
                Toast.makeText(DeptHeadReqDetailActivity.this,"You have rejected the request",Toast.LENGTH_LONG).show();
                Intent i=new Intent(DeptHeadReqDetailActivity.this,DeptHeadReqActivity.class);
                i.putExtra("id",id);
                startActivity(i);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
