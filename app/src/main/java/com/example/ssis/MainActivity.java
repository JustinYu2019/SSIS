package com.example.ssis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements AsyncToServer.IServerResponse {
    Button login;
    EditText username;
    EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Login  page for Clerk, dept rep and dept head
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //client side validation
                String usernameinput=username.getText().toString();
                String passwordinput=password.getText().toString();
                if(usernameinput.isEmpty()||passwordinput.isEmpty()){
                    //display the toast
                    Toast msg = Toast.makeText(MainActivity.this,"Username and password are required ", Toast.LENGTH_LONG);
                    msg.show();
                }
                else{
                    // authentication required to identify who is that
                    JSONObject jsonObj = new JSONObject();
                    try {
                        jsonObj.put("username", usernameinput);
                        jsonObj.put("password", passwordinput);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                    Command cmd = new Command(MainActivity.this, "login","http://10.0.2.2:59591/Home/MobileLogin",jsonObj);
                    new AsyncToServer().execute(cmd);
                }
            }
        });
    }

    @Override
    public void onServerResponse(JSONObject jsonObj) {
        if (jsonObj == null) {
            Toast msg = Toast.makeText(MainActivity.this,"Server No response ", Toast.LENGTH_LONG);
            msg.show();
        }
        try {
            String context = (String) jsonObj.get("context");
            if (context.compareTo("login") == 0) {
                String loginStatus=(String)jsonObj.get("status");//success or fail
                if(loginStatus.equals("success")){
                    String role = (String) jsonObj.get("role");
                    int id = (int) jsonObj.get("id");
                    //redirect to different page
                    if(role.equals("Representative")){
                        Intent i= new Intent(MainActivity.this,DeptRepHomeActivity.class);
                        i.putExtra("id",id);
                        startActivity(i);
                    }else if(role.equals("Head")){
                        Intent i= new Intent(MainActivity.this,DeptHeadHomeActivity.class);
                        i.putExtra("id",id);
                        startActivity(i);
                    }else if(role.equals("StockClerk")){
                        Intent i= new Intent(MainActivity.this,StoreClerkHomeActivity.class);
                        i.putExtra("id",id);
                        startActivity(i);
                    }else{
                        Toast msg = Toast.makeText(MainActivity.this,"Invalid User. ", Toast.LENGTH_LONG);
                        msg.show();
                    }
                }else{
                    Toast msg = Toast.makeText(MainActivity.this,"Username and password are incorrect. ", Toast.LENGTH_LONG);
                    msg.show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
