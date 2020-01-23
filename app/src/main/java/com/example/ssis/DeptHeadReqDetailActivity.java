package com.example.ssis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DeptHeadReqDetailActivity extends AppCompatActivity {
    LinearLayout mChartLayout;
    TableLayout mTableLayout;
    int len;
    String name;
    String raiseDate;
    ArrayList<Item> itemList;
    TextView empName;
    TextView dateRequest;
    Button approve,reject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_head_req_detail);
        mChartLayout=findViewById(R.id.chart_layout);
        mTableLayout=new TableLayout(DeptHeadReqDetailActivity.this);


        Intent i=getIntent();
        Requisition rdetail=(Requisition) i.getSerializableExtra("ReqDetail");

        itemList=rdetail.getItemList();
        name=rdetail.getName();
        raiseDate=rdetail.getRaiseDate();

        empName=findViewById(R.id.employeeName);
        dateRequest=findViewById(R.id.dateRequest);
        empName.setText("Employee Name:             "+name);
        dateRequest.setText("Date of Request:             "+raiseDate);
        displayCharTable();
        approve=findViewById(R.id.Approve);
        reject=findViewById(R.id.Reject);
        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DeptHeadReqDetailActivity.this,"You have approved the request",Toast.LENGTH_LONG).show();
                Intent i=new Intent(DeptHeadReqDetailActivity.this,DeptHeadReqActivity.class);
                startActivity(i);

                // need to check if the approved request is disappear or not (1 method)
                // or make the approved or reject button invisible and set the status of requision to be approved or rejected
                // and then have a text view set that status.
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DeptHeadReqDetailActivity.this,"You have rejected the request",Toast.LENGTH_LONG).show();
                Intent i=new Intent(DeptHeadReqDetailActivity.this,DeptHeadReqActivity.class);
                startActivity(i);
            }
        });

        // or once reject or approve is clicked, next time when click back
        // set both button to false --> reject.setClickable(false);
        // create a text beneath the approve reject button and set the text to its status



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
            valueTV2.setText("                                      "+quantity);
            valueTV2.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT
            ));
            row.addView(valueTV2);


            mTableLayout.addView(row);
        }
        mChartLayout.addView(mTableLayout);
    }
}
