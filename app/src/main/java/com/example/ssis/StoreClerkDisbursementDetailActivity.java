package com.example.ssis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class StoreClerkDisbursementDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_clerk_disbursement_detail);

        Intent i=getIntent();
        Department d=(Department) i.getSerializableExtra("Department");

        // try to use adapter to do and see if it allows bottom space.

    }
}
