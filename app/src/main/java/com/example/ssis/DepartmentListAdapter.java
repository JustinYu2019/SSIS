package com.example.ssis;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class DepartmentListAdapter extends ArrayAdapter<Department> {
    private Context rContext;
    private int mResource;
    public int chosenId=-1;

    public DepartmentListAdapter(Context context, int resource, ArrayList<Department> objects) {
        super(context, resource, objects);
        this.rContext = context;
        this.mResource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        // getItem is the global method

        String deptName=getItem(position).getDeptName();
        String id=getItem(position).getRequisitionId();
        LayoutInflater inflater = (LayoutInflater) rContext.getSystemService(
                Activity.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(R.layout.adapter_departmentlist, null);


        TextView v1=convertView.findViewById(R.id.dept);
        TextView v2=convertView.findViewById(R.id.dept2);

        v1.setText(deptName);
        v2.setText(id);

        return convertView;
    }

}
