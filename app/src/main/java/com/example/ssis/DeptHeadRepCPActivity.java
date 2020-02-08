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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DeptHeadRepCPActivity extends AppCompatActivity implements AsyncToServer.IServerResponse{
    RadioGroup radioGroup;
    RadioButton radioButton;
    Button applyChangeRepC,cancel,Logout;
    int radioId;
    String selectedCollectionPoint;
    String selectedRep;
    Calendar calendar;
    TextView textSelectedRep;
    TextView textSelectedCP;
    TextView currentRep;
    TextView currentCP;
    int id;
    ArrayList<String> namelist=new ArrayList<>();
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_head_rep_cp);
        id= getIntent().getIntExtra("id",0);
        authenticateUser(id);

        Logout=(Button)findViewById(R.id.LogoutRepCP);
        cancel=(Button)findViewById(R.id.cancelRepCP);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(DeptHeadRepCPActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DeptHeadRepCPActivity.this,DeptHeadHomeActivity.class);
                i.putExtra("id",id);
                startActivity(i);
                finish();
            }
        });

        textSelectedRep=(TextView) findViewById(R.id.textRepSelected);
        textSelectedCP=(TextView)findViewById(R.id.textCPSelected);

        //put a list of Employees name into spinner
        findEmployeeNamesById(id);
        //put current cp and rep
        findCurrentRepAndCPById(id);
//        String text = spinner.getSelectedItem().toString();
//        TextView v1=findViewById(R.id.textView);
//        v1.setText(text);

        // get current date of change
        calendar = Calendar.getInstance();
        String currentDate= DateFormat.getDateInstance().format(calendar.getTime());
        TextView dateText=findViewById(R.id.textDate);
        dateText.setText(currentDate);

        // collection point
        radioGroup=(RadioGroup) findViewById(R.id.radioGroup);

        applyChangeRepC=(Button) findViewById(R.id.applyButton);
        applyChangeRepC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedRep.equals("")||selectedCollectionPoint.equals("")||selectedRep==null||selectedCollectionPoint==null){
                    Toast.makeText(DeptHeadRepCPActivity.this,"Must Select Both Collection Point and Representative",Toast.LENGTH_LONG).show();
                }else{
                    changeRepAndCollectionPoint(selectedRep,selectedCollectionPoint);
                }

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
    @Override
    public void onServerResponse(JSONObject jsonObj) {
        if (jsonObj == null) {
            Toast msg = Toast.makeText(DeptHeadRepCPActivity.this,"Server No response ", Toast.LENGTH_LONG);
            msg.show();
        }
        try {
            String context = (String) jsonObj.get("context");
            if (context.compareTo("findEmployees") == 0) {
                JSONArray array =jsonObj.getJSONArray("namelist");
                for(int i=0;i<array.length();i++)
                {
                    JSONObject object= array.getJSONObject(i);
                    namelist.add(object.getString("name"));
                }
                //spinner for rep
                spinner=(Spinner)findViewById(R.id.spinnerRep);
                ArrayAdapter<String> myAdapter=new ArrayAdapter<String>(DeptHeadRepCPActivity.this,
                        android.R.layout.simple_list_item_1,namelist);
                myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(myAdapter);
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
            }
            if (context.compareTo("findRepCP") == 0) {
                currentRep=(TextView)findViewById(R.id.currentRep);
                currentCP=(TextView)findViewById(R.id.currentCP);
                textSelectedCP.setText(jsonObj.getString("CP"));
                textSelectedRep.setText(jsonObj.getString("Rep"));
                currentCP.setText(jsonObj.getString("CP"));
                currentRep.setText(jsonObj.getString("Rep"));
            }
            if (context.compareTo("changeRepAndCP") == 0) {
                recreate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void findEmployeeNamesById(int id){
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("id",id);
        }catch (Exception e) {
            e.printStackTrace();
        }
        Command cmd = new Command(DeptHeadRepCPActivity.this, "findEmployees","http://10.0.2.2:59591/Home/FindEmployees",jsonObj);
        new AsyncToServer().execute(cmd);
    }

    public void changeRepAndCollectionPoint(String selectedRep,String selectedCollectionPoint){

        if(selectedRep.equals(currentRep.getText())&&selectedCollectionPoint.equals(currentCP.getText())){
            Toast.makeText(this,"Nothing changed.",Toast.LENGTH_LONG).show();
        }else {
            JSONObject jsonObj = new JSONObject();
            try {
                jsonObj.put("newRep", selectedRep);
                jsonObj.put("newCP", selectedCollectionPoint.split("\\(")[0]);
                jsonObj.put("id",id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Command cmd = new Command(DeptHeadRepCPActivity.this, "changeRepAndCP", "http://10.0.2.2:59591/Home/ChangeRepAndCP", jsonObj);
            new AsyncToServer().execute(cmd);
        }
    }
    public void findCurrentRepAndCPById(int id){
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("id",id);
        }catch (Exception e) {
            e.printStackTrace();
        }
        Command cmd = new Command(DeptHeadRepCPActivity.this, "findRepCP","http://10.0.2.2:59591/Home/FindCurrentRepAndCP",jsonObj);
        new AsyncToServer().execute(cmd);
    }

    public void authenticateUser(int id){
        if(id==0){
            Intent i=new Intent(DeptHeadRepCPActivity.this,MainActivity.class);
            startActivity(i);
            finish();
        }
    }


}
