package com.example.ssis;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class ContactListAdapter extends ArrayAdapter<Department> {
    private Context rContext;
    private int mResource;
    public int chosenId=-1;
    public ContactListAdapter(Context context, int resource, ArrayList<Department> objects) {
        super(context, resource, objects);
        this.rContext = context;
        this.mResource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        // getItem is the global method
        String dept=getItem(position).getDeptName();
        String deptRep=getItem(position).getDeptRep();
        String contact=getItem(position).getContactNumber();
        String cP=getItem(position).getCollectionPoint();




//        LayoutInflater inflater=LayoutInflater.from(rContext);
//        convertView=inflater.inflate(mResource,parent,false);
        LayoutInflater inflater = (LayoutInflater) rContext.getSystemService(
                Activity.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(R.layout.adapter_contactlist, null);


        TextView v1=convertView.findViewById(R.id.cDeptName);
        TextView v2=convertView.findViewById(R.id.cDeptRep);
        TextView v3=convertView.findViewById(R.id.cContactNumber);
        TextView v4=convertView.findViewById(R.id.cCP);

        v1.setTextColor(Color.BLACK);
        v1.setText(dept);
        v2.setText(deptRep);
        v3.setText(contact);
        v4.setText(cP);
        return convertView;
    }
}
