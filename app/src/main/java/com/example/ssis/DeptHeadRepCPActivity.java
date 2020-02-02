package com.example.ssis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DeptHeadRepCPActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton radioButton;
    Button applyChangeRepC,cancel,Logout;
    int radioId;
    String selectedCollectionPoint;
    String selectedRep;
    Calendar calendar;
    TextView textSelectedRep;
    TextView textSelectedCP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_head_rep_cp);
        Logout=findViewById(R.id.LogoutRepCP);
        cancel=findViewById(R.id.cancelRepCP);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DeptHeadRepCPActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DeptHeadRepCPActivity.this,DeptHeadHomeActivity.class);
                startActivity(i);
            }
        });

        textSelectedRep=findViewById(R.id.textRepSelected);
        textSelectedCP=findViewById(R.id.textCPSelected);
        //spinner for rep
        final Spinner spinner=findViewById(R.id.spinnerRep);
        final ArrayList<String> namelist=new ArrayList<>();
        namelist.add("Justin");
        namelist.add("Jenny");
        namelist.add("Branny");
        namelist.add("Canny");
        namelist.add("Danny");

        ArrayAdapter<String> myAdapter=new ArrayAdapter<String>(DeptHeadRepCPActivity.this,
                android.R.layout.simple_list_item_1,namelist);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);

//        String text = spinner.getSelectedItem().toString();
//        TextView v1=findViewById(R.id.textView);
//        v1.setText(text);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRep=parent.getItemAtPosition(position).toString();

                textSelectedRep.setText(selectedRep);
                //Toast.makeText(parent.getContext(),"selected: "+selectedRep,Toast.LENGTH_LONG).show();;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // get current date of change
        calendar = Calendar.getInstance();
        String currentDate= DateFormat.getDateInstance().format(calendar.getTime());
        TextView dateText=findViewById(R.id.textDate);
        dateText.setText(currentDate);

        // collection point
        radioGroup=findViewById(R.id.radioGroup);

        applyChangeRepC=findViewById(R.id.applyButton);
        applyChangeRepC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // selected value will be put here
//                i.putExtra("selectedCP",selectedCollectionPoint);
//                i.putExtra("selectedRep",selectedRep);

                //startActivity(i);
            }
        });
    }
    public void checkButton(View v){
        radioId=radioGroup.getCheckedRadioButtonId();
        radioButton=findViewById(radioId);
        selectedCollectionPoint=radioButton.getText().toString();
        textSelectedCP.setText(""+selectedCollectionPoint);
        //Toast.makeText(this,"You have selected: "+radioButton.getText(),Toast.LENGTH_SHORT).show();
    }
}
