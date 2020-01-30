package com.example.ssis;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class NotificationListAdapter extends ArrayAdapter<Requisition> {

    private Context rContext;
    private int mResource;
    public int chosenId=-1;
    public NotificationListAdapter(Context context, int resource, ArrayList<Requisition> objects) {
        super(context, resource, objects);
        this.rContext = context;
        this.mResource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        // getItem is the global method
        String date=getItem(position).getRaiseDate();
        String name=getItem(position).getName();
        //ArrayList<Item> itemList=getItem(position).getItemList();
        //Requisition r=new Requisition(date,name,itemList);

//        LayoutInflater inflater=LayoutInflater.from(rContext);
//        convertView=inflater.inflate(mResource,parent,false);
        LayoutInflater inflater = (LayoutInflater) rContext.getSystemService(
                Activity.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(R.layout.adapter_notification, null);


        TextView v1=convertView.findViewById(R.id.rDateNoti);
        TextView v2=convertView.findViewById(R.id.rNameNoti);

        v1.setText(date);
       // String s=name+" has send a notification"; does not work when set text
        v2.setText(name);
        return convertView;
    }
}
