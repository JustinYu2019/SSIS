package com.example.ssis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class DeptHeadReqDetailActivity extends AppCompatActivity {
    LinearLayout mChartLayout;
    TableLayout mTableLayout;
    int len;
    String name;
    String raiseDate;
    ArrayList<Item> itemList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_head_req_detail);
        mChartLayout=findViewById(R.id.chart_layout);
        mTableLayout=new TableLayout(DeptHeadReqDetailActivity.this);

        //Requisition rdetail=getIntent().getSerializableExtra("ReqDetail");
        Intent i=getIntent();
        Requisition rdetail=(Requisition) i.getSerializableExtra("ReqDetail");
        //len=rdetail.getItemList().size();
        itemList=rdetail.getItemList();
        name=rdetail.getName();
        raiseDate=rdetail.getRaiseDate();
        // i think will be another list view but tgt with approve/reject/logout feature
        // to be continued.
        displayCharTable();

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
            valueTV.setText("       "+description);
            valueTV.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT
            ));
            row.addView(valueTV);

            TextView valueTV2= new TextView(DeptHeadReqDetailActivity.this);
            valueTV2.layout(20,20,20,30);
            valueTV2.setText("      "+quantity);
            valueTV2.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT
            ));
            row.addView(valueTV2);


            mTableLayout.addView(row);
        }
        mChartLayout.addView(mTableLayout);
    }
}
