package com.sdaemon.oakstudiotv.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.model.CommunicatonPref_list;

import java.util.ArrayList;

public class CommunicationPrefList_Adapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context context;
    Animation animation;
    private ArrayList<CommunicatonPref_list> communicatonPref_lists;
    String msg="";
    CheckBox checkBox;

    public CommunicationPrefList_Adapter(Context context, ArrayList<CommunicatonPref_list> communicatonPref_lists) {
        // TODO Auto-generated constructor stub
        this.communicatonPref_lists = communicatonPref_lists;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return communicatonPref_lists.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.communication_preferences_list_row, null);
        holder.tvcommunication = (TextView) rowView.findViewById(R.id.tv_communication);
//        checkBox = (CheckBox) rowView.findViewById(R.id.checkbox_preferences);

        holder.tvcommunication.setText(communicatonPref_lists.get(position).getPrefName());

//        checkBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context, ""+communicatonPref_lists.get(position).getPrefName(), Toast.LENGTH_SHORT).show();
//
//            }
//        });

//        if (position==0){
//           if (holder.checkBox.isChecked()){
//               msg = msg +communicatonPref_lists.get(position).getPrefName();
//        Toast.makeText(context, ""+msg, Toast.LENGTH_SHORT).show();

//           }

//        }
        return rowView;
    }

    public class Holder {
        TextView tvcommunication;

    }

    public void Check(int position)
    {
        String msg="";

        // Concatenation of the checked options in if

        // isChecked() is used to check whether
        // the CheckBox is in true state or not.

        if(checkBox.isChecked())
            msg = msg + communicatonPref_lists.get(position).getPrefName();

        // Toast is created to display the
        // message using show() method.
        Toast.makeText(context, msg + "are selected",Toast.LENGTH_LONG).show();
    }

}
