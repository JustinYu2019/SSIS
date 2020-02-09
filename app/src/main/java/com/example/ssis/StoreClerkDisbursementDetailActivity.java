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
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class StoreClerkDisbursementDetailActivity extends AppCompatActivity implements AsyncToServer.IServerResponse {
    String name;String collectionPoint;String contactNumber;String deptRep;String reqId;
    int id;
    ArrayList<Item>itemList;
    LinearLayout dChartLayout;
    TableLayout dTableLayout;
    TextView desc;
    EditText quanEdit;
    EditText reasonEdit;
    TextView qty;
    Button Approve,Confirm,Logout;
    TextView tDeptName,tDeptRep,tDeptReqId,tDeptContact,tCollectionPoint;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_clerk_disbursement_detail);

        Intent i=getIntent();
        id=i.getIntExtra("id",0);
        authenticateUser(id);

        Department d=(Department) i.getSerializableExtra("Department");
        name=d.getDeptName();
        collectionPoint=i.getStringExtra("location");
        contactNumber=d.getContactNumber();
        deptRep=d.getDeptRep();
        reqId=d.getDisbursementId();

        tDeptName=findViewById(R.id.deptName);tDeptRep=findViewById(R.id.deptRep);tDeptReqId=findViewById(R.id.deptCode);
        tDeptContact=findViewById(R.id.deptContact);tCollectionPoint=findViewById(R.id.collectionPoint);

        tDeptName.setText(""+name);
        tDeptRep.setText(""+deptRep);
        if(reqId!=null){
            tDeptReqId.setText("Disbursement Id: "+reqId);
        }
        tDeptContact.setText(""+contactNumber);
        tCollectionPoint.setText(""+collectionPoint);
        // set up confirm Button, upon click, will send notification to the clerk..--> this needs to learn..
        Approve=findViewById(R.id.ApproveDis);
        Approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acknowledge();
            }
        });
        Confirm=findViewById(R.id.confirmDis);
        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmQuantity();
            }
        });
        Logout=findViewById(R.id.LogoutDisburseDetail);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(StoreClerkDisbursementDetailActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        // use table layout
        //itemList=new ArrayList<>();
        itemList=d.getItemList();
        dChartLayout=findViewById(R.id.chart_layoutDisburse);
        dTableLayout=new TableLayout(StoreClerkDisbursementDetailActivity.this);
        displayCharTableDisburse();
    }

    public void displayCharTableDisburse(){
        dTableLayout.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT)
        );

        for (Item item: itemList){
            final TableRow row=new TableRow(StoreClerkDisbursementDetailActivity.this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT
                    ,TableRow.LayoutParams.WRAP_CONTENT));

            String description=item.getDescription();
            int quantity=item.getUnit();
            desc= new TextView(StoreClerkDisbursementDetailActivity.this);

            desc.setText(""+description);
            desc.setTextColor(Color.BLACK);
            desc.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT
            ));
            desc.setPaddingRelative(0,0,30,0);
            row.addView(desc);

            qty=new TextView(StoreClerkDisbursementDetailActivity.this);
            qty.setText("Qty: ");
            //qty.setPaddingRelative(0,0,30,0);
            row.addView(qty);

            //TextView valueTV2= new TextView(StoreClerkDisbursementDetailActivity.this);
            quanEdit=new EditText(StoreClerkDisbursementDetailActivity.this);
            quanEdit.setTextColor(Color.BLACK);
            quanEdit.setText(""+quantity); // adjust here if quantity do not align
            quanEdit.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT
            ));
            row.addView(quanEdit);

            dTableLayout.addView(row);
        }
        dChartLayout.addView(dTableLayout);
    }
    public void acknowledge(){
        reasonEdit=(EditText)findViewById(R.id.disbursementRemark);
        if(reqId==null||itemList.size()==0){
            Toast.makeText(this,"No Disbursement available for you to acknowledge.",Toast.LENGTH_LONG).show();
        }else{
            JSONObject jsonObj = new JSONObject();
            try {
                jsonObj.put("id", id);
                jsonObj.put("disbursementId",reqId);
                jsonObj.put("remark",reasonEdit.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            Command cmd = new Command(StoreClerkDisbursementDetailActivity.this, "acknowledge", "http://10.0.2.2:59591/Home/AcknowledgeDisbursement", jsonObj);
            new AsyncToServer().execute(cmd);
        }
    }
    public void confirmQuantity() {
        if (reqId == null||itemList.size()==0){
            Toast.makeText(this, "No Disbursement available for you to update quantity.", Toast.LENGTH_LONG).show();
        }
        else {
            int rows = dTableLayout.getChildCount();
            JSONArray array = new JSONArray();
            for (int i = 0; i < rows; i++) {
                View view = dTableLayout.getChildAt(i);
                JSONObject jsonObject = new JSONObject();
                if (view instanceof TableRow) {
                    TableRow row = (TableRow) view;
                    View v1 = row.getChildAt(0);
                    View v3 = row.getChildAt(2);
                    TextView description = new TextView(this);
                    EditText quantity = new EditText(this);
                    if (v1 instanceof TextView) {
                        description = (TextView) v1;
                    }
                    if (v3 instanceof EditText) {
                        quantity = (EditText) v3;
                    }

                    try {
                        jsonObject.put("description", description.getText().toString());
                        jsonObject.put("quantity", quantity.getText().toString());
                        array.put(i, jsonObject);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            JSONObject jsonObj = new JSONObject();
            try {
                jsonObj.put("id", id);
                jsonObj.put("disbursementId", reqId);
                jsonObj.put("items", array);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Command cmd = new Command(StoreClerkDisbursementDetailActivity.this, "confirm", "http://10.0.2.2:59591/Home/UpdateQuantity", jsonObj);
            new AsyncToServer().execute(cmd);
        }
    }
    @Override
    public void onServerResponse(JSONObject jsonObj){
        if (jsonObj == null) {
            Toast msg = Toast.makeText(StoreClerkDisbursementDetailActivity.this,"Server No response ", Toast.LENGTH_LONG);
            msg.show();
        }
        try {
            String context = (String) jsonObj.get("context");
            if (context.compareTo("acknowledge") == 0) {
                try{
                    String status=jsonObj.getString("status");
                    if(status.equals("Bad")){
                        Toast.makeText(StoreClerkDisbursementDetailActivity.this,"You cannot acknowledge the disbursement Now",Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(StoreClerkDisbursementDetailActivity.this,"You have acknowledge the disbursement",Toast.LENGTH_LONG).show();
                        Intent i=new Intent(StoreClerkDisbursementDetailActivity.this,StoreClerkDisbursementActivity.class);
                        i.putExtra("id",id);
                        i.putExtra("location",collectionPoint);
                        startActivity(i);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
            if (context.compareTo("confirm") == 0) {

                try{
                    String status=jsonObj.getString("status");
                    if(status.equals("Bad")){
                        Toast.makeText(StoreClerkDisbursementDetailActivity.this,"You cannot update the quantity Now",Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(StoreClerkDisbursementDetailActivity.this,"New quanities are updated",Toast.LENGTH_LONG).show();
                        Intent i=new Intent(StoreClerkDisbursementDetailActivity.this,StoreClerkDisbursementActivity.class);
                        i.putExtra("id",id);
                        i.putExtra("location",collectionPoint);
                        startActivity(i);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void authenticateUser(int id){
        if(id==0){
            intent=new Intent(StoreClerkDisbursementDetailActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
