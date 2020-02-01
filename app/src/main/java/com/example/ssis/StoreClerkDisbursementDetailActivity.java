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

import java.util.ArrayList;

public class StoreClerkDisbursementDetailActivity extends AppCompatActivity {
String name;String collectionPoint;String contactNumber;String deptRep;String reqId;
    ArrayList<Item>itemList;
    LinearLayout dChartLayout;
    TableLayout dTableLayout;
    TextView desc;
    EditText quanEdit;
    EditText reasonEdit;
    TextView qty;
    Button Approve,Confirm,Logout;
    TextView tDeptName,tDeptRep,tDeptReqId,tDeptContact,tCollectionPoint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_clerk_disbursement_detail);

        Intent i=getIntent();
        Department d=(Department) i.getSerializableExtra("Department");
        name=d.getDeptName();
        collectionPoint=d.getCollectionPoint();
        contactNumber=d.getContactNumber();
        deptRep=d.getDeptRep();
        reqId=d.getRequisitionId();

        tDeptName=findViewById(R.id.deptName);tDeptRep=findViewById(R.id.deptRep);tDeptReqId=findViewById(R.id.deptCode);
        tDeptContact=findViewById(R.id.deptContact);tCollectionPoint=findViewById(R.id.collectionPoint);

        tDeptName.setText(""+name);tDeptRep.setText(""+deptRep);tDeptReqId.setText(""+reqId);tDeptContact.setText(""+contactNumber);
        tCollectionPoint.setText(""+collectionPoint);
        // set up confirm Button, upon click, will send notification to the clerk..--> this needs to learn..
        Approve=findViewById(R.id.ApproveDis);
        Confirm=findViewById(R.id.confirmDis);
        Logout=findViewById(R.id.LogoutDisburseDetail);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(StoreClerkDisbursementDetailActivity.this,MainActivity.class);
                startActivity(i);
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
                TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT)
        );

        for (Item item: itemList){
            final TableRow row=new TableRow(StoreClerkDisbursementDetailActivity.this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT
                    ,TableRow.LayoutParams.WRAP_CONTENT));

            String description=item.getDescription();
            int quantity=item.getUnit();
            desc= new TextView(StoreClerkDisbursementDetailActivity.this);

            desc.setText("  "+description);
            desc.setTextColor(Color.BLACK);
            desc.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT
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
                    TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT
            ));
            row.addView(quanEdit);



            dTableLayout.addView(row);
        }
        dChartLayout.addView(dTableLayout);
    }
}
